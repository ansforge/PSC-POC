/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.proof;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.diagnostic.CertificateWrapper;
import eu.europa.esig.dss.diagnostic.DiagnosticData;
import eu.europa.esig.dss.diagnostic.jaxb.XmlSignature;
import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.x509.CommonTrustedCertificateSource;
import eu.europa.esig.dss.validation.reports.AbstractReports;
import fr.asipsante.api.sign.bean.errors.ErreurSignature;
import fr.asipsante.api.sign.bean.parameters.ProofParameters;
import fr.asipsante.api.sign.bean.proof.CABean;
import fr.asipsante.api.sign.bean.proof.CRLBean;
import fr.asipsante.api.sign.bean.proof.ProofBeanLibInfos;
import fr.asipsante.api.sign.bean.proof.ProofBeanObjectInfos;
import fr.asipsante.api.sign.bean.proof.ProofBeanRequestInfos;
import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.Vars;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * The Class ProofBuilder.
 */
public class ProofBuilder {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ProofBuilder.class);

    /**
     * Proof from verif sign.
     *
     * @param signedDocumentBytes the signed document's bytes
     * @param rapport        the rapport
     * @param bundleCA       the bundle CA
     * @param crls           the crls
     * @param pParams        the params
     * @return the string
     */
    public String proofFromVerifSign(final byte[] signedDocumentBytes, RapportValidationSignature rapport,
                                     CommonTrustedCertificateSource bundleCA, Collection<byte[]> crls,
                                     ProofParameters pParams) {
        LOG.info("Début de la génération de preuve.");

        // Configuration du format de date à utiliser
        final SimpleDateFormat sdfIso8601;
        sdfIso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdfIso8601.setTimeZone(TimeZone.getTimeZone("UTC"));

        // configuration de l'outil de templating (freemarker)
        final Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = null;
        try {
            final InputStream templateIS = this.getClass().getClassLoader()
                    .getResourceAsStream(Vars.CONFDIR.getVar() + Vars.PROOF_TEMPLATE_SIGN.getVar());

            final File tempFile = File.createTempFile("signTemp", null);

            tempFile.deleteOnExit();

            cfg.setDirectoryForTemplateLoading(tempFile.getParentFile());

            FileUtils.copyInputStreamToFile(templateIS, tempFile);

            /* Sélection du template à utiliser pour générer la preuve */
            template = cfg.getTemplate(tempFile.getName());

            templateIS.close();
        } catch (final IOException e1) {
            LOG.error(ExceptionUtils.getStackTrace(e1));
        }
        
        // Beans stockant les données requises pour générer la preuve.
        final ProofBeanLibInfos proofLibInfos = new ProofBeanLibInfos();
        final ProofBeanRequestInfos proofRequestInfos = new ProofBeanRequestInfos();
        final ProofBeanObjectInfos proofObjectInfos = new ProofBeanObjectInfos();
        // RequestInfos
        proofRequestInfos.setRequestType(pParams.getRequestType());
        proofRequestInfos.setProofTag(pParams.getProofTag());
        proofRequestInfos.setRequestid(pParams.getRequestid());
        proofRequestInfos.setApplicantId(pParams.getApplicantId());
        proofRequestInfos.setProofId(UUID.randomUUID().toString());
        proofRequestInfos.setOpenidBeans(pParams.getOpenidTokens());
        final Date now = new Date();
        proofRequestInfos.setCreatedDate(sdfIso8601.format(now));
        // Document
        final String objet64 = Base64.getEncoder().encodeToString(signedDocumentBytes);
        LOG.info("Transformation du document signé en base 64 terminée.");
        final DiagnosticData diagData = rapport.getReports().getDiagnosticData();
        proofObjectInfos.setObject(objet64);

        // ObjectInfos
        if (diagData.getFirstSignatureFormat() != null) {
            String signatureFormat = diagData.getFirstSignatureFormat().toString();
            // Correction fiche 29831
            if ("XML-NOT-ETSI".equals(signatureFormat)) {
                signatureFormat = "XMLDSig";
            }
            proofObjectInfos.setType(signatureFormat + " Signature");
        } else {
            proofObjectInfos.setType("");
        }

        if (diagData.getFirstSignatureId() != null &&
                !diagData.getSignatureById(diagData.getFirstSignatureId()).getCertificateChain().isEmpty()) {
            final String signatureId = diagData.getFirstSignatureId();
            final String signingCertificateId = diagData.getSigningCertificateId(signatureId);
            proofObjectInfos.setCertSubject(diagData.getCertificateDN(signingCertificateId));
        } else {
            proofObjectInfos.setCertSubject("");
        }

        proofObjectInfos.setCertSignBase64Encoded(getCertificateBase64Encoded(rapport.getReports()));

        // Ajout des autorités de confiance
        final List<CABean> listCa = new ArrayList<>();
        for (final CertificateToken cToken : bundleCA.getCertificates()) {
            final CABean caBean = new CABean();
            if (cToken.getCertificate().getIssuerX500Principal() != null) {
                caBean.setIssuerName(cToken.getCertificate().getIssuerDN().getName());
            }
            caBean.setSerialNumber(cToken.getSerialNumber().toString());
            caBean.setBase64Encoded(Base64.getEncoder().encodeToString(cToken.getEncoded()));
            caBean.setDigestAlgo(DigestAlgorithm.SHA256.getName());
            caBean.setDigestValue(Base64.getEncoder().encodeToString(cToken.getDigest(DigestAlgorithm.SHA256)));
            listCa.add(caBean);

        }
        proofObjectInfos.setCas(listCa);

        // Ajout des CRL utilisées pour vérifier la signature.
        final List<CRLBean> listCrl = new ArrayList<>();
        final InputStream[] valuesCRL = crls.stream().map(ByteArrayInputStream::new)
                .toArray(ByteArrayInputStream[]::new);

        for (final InputStream inStream : valuesCRL) {
            try {
                final CRLBean crlBean = new CRLBean();
                final CertificateFactory cf = CertificateFactory.getInstance("X.509");
                final X509CRL crl = (X509CRL) cf.generateCRL(inStream);
                crlBean.setBase64Encoded(Base64.getEncoder().encodeToString(crl.getEncoded()));
                crlBean.setIssuerName(crl.getIssuerDN().getName());
                crlBean.setIssueTime(sdfIso8601.format(crl.getThisUpdate()));
                final MessageDigest md = MessageDigest.getInstance("SHA-256");
                final byte[] digestValueByte = md.digest(crl.getEncoded());
                final String digestValue = Base64.getEncoder().encodeToString(digestValueByte);
                crlBean.setDigestAlgo(org.apache.xml.security.algorithms.MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA256);
                crlBean.setDigestValue(digestValue);
                listCrl.add(crlBean);
                inStream.close();
            } catch (GeneralSecurityException | IOException e) {
                LOG.error(ExceptionUtils.getStackTrace(e));
            }
        }

        proofObjectInfos.setCrls(listCrl);
        
        final String rulesConf = "rules=" + rapport.getRules().stream().map(c -> c.getClass().getSimpleName())
                .collect(Collectors.joining(","));
        final String responseError = rapport.getListeErreurSignature().stream().map(ErreurSignature::getMessage)
                .collect(Collectors.joining("; "));
        final String encodedRespErr = new String(Base64.getEncoder().encode(responseError.getBytes()));
        
        // Alimentation du template
        final Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("comp", proofLibInfos.getComp());
        valueMap.put("compVersionMajor", pParams.getVersion().getMajor());
        valueMap.put("compVersionMinor", pParams.getVersion().getMinor());
        valueMap.put("compVersionPatch", pParams.getVersion().getPatch());
        valueMap.put("compVersionIteration", pParams.getVersion().getIteration());
        valueMap.put("compVersionFull", pParams.getVersion().getFull());
        valueMap.put("proofId", proofRequestInfos.getProofId());
        valueMap.put("createdDate", proofRequestInfos.getCreatedDate());
        valueMap.put("rulesConf", rulesConf);
        valueMap.put("object", proofObjectInfos.getObject());
        valueMap.put("type", proofObjectInfos.getType());
        valueMap.put("certSubject", proofObjectInfos.getCertSubject());
        valueMap.put("certSignBase64Encoded", proofObjectInfos.getCertSignBase64Encoded());
        valueMap.put("CAs", proofObjectInfos.getCas());
        valueMap.put("CRLs", proofObjectInfos.getCrls());
        valueMap.put("responseError", encodedRespErr);
        valueMap.put("requesttype", proofRequestInfos.getRequestType());
        valueMap.put("requestid", proofRequestInfos.getRequestid());
        valueMap.put("prooftag", proofRequestInfos.getProofTag());
        valueMap.put("applicantId", proofRequestInfos.getApplicantId());
        valueMap.put("operationName", pParams.getOpName());
        valueMap.put("responseStatus", "valide:" + rapport.isValide());
        valueMap.put("OpenIdTokens", proofRequestInfos.getOpenidBeans());

        final StringWriter out = new StringWriter();
        try {
            assert template != null;
            template.process(valueMap, out);
            out.close();

        } catch (TemplateException | IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }

        LOG.info("Fin de la génération de preuve.");

        return out.toString();
    }

    /**
     * Proof from verif cert.
     *
     * @param rapport      the rcw
     * @param bundleCA the bundle CA
     * @param crls     the crls
     * @param pParams  the params
     * @return the string
     */
    public String proofFromVerifCert(RapportValidationCertificat rapport, CommonTrustedCertificateSource bundleCA,
            Collection<byte[]> crls, ProofParameters pParams) {

        // Configuration du format de date à utiliser
        final SimpleDateFormat sdfIso8601;
        sdfIso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdfIso8601.setTimeZone(TimeZone.getTimeZone("UTC"));

        // configuration de l'outil de templating (freemarker)
        final Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = null;
        try {
            final InputStream templateIS = this.getClass().getClassLoader()
                    .getResourceAsStream(Vars.CONFDIR.getVar() + Vars.PROOF_TEMPLATE_CERT.getVar());

            final File tempFile = File.createTempFile("certTemp", null);

            tempFile.deleteOnExit();

            cfg.setDirectoryForTemplateLoading(tempFile.getParentFile());

            FileUtils.copyInputStreamToFile(templateIS, tempFile);

            /* Sélection du template à utiliser pour générer la preuve */
            template = cfg.getTemplate(tempFile.getName());

            templateIS.close();
        } catch (final IOException e1) {
            LOG.error(ExceptionUtils.getStackTrace(e1));
        }

        // Beans stockant les données requises pour générer la preuve.
        final ProofBeanLibInfos proofLibInfos = new ProofBeanLibInfos();
        final ProofBeanRequestInfos proofRequestInfos = new ProofBeanRequestInfos();
        final ProofBeanObjectInfos proofObjectInfos = new ProofBeanObjectInfos();
        // RequestInfos
        proofRequestInfos.setRequestType(pParams.getRequestType());
        proofRequestInfos.setProofTag(pParams.getProofTag());
        proofRequestInfos.setRequestid(pParams.getRequestid());
        proofRequestInfos.setApplicantId(pParams.getApplicantId());

        proofRequestInfos.setProofId(UUID.randomUUID().toString());
        final Date now = new Date();
        proofRequestInfos.setCreatedDate(sdfIso8601.format(now));
        // ObjectInfos
        proofObjectInfos.setType("CERT");

        if (!rapport.getRapportCertificatDSS().getDiagnosticDataJaxb().getUsedCertificates().isEmpty()) {
            final String certificateId = rapport.getRapportCertificatDSS().getDiagnosticDataJaxb()
                    .getUsedCertificates().get(0).getId();
            proofObjectInfos.setCertSubject(rapport.getRapportCertificatDSS().getDiagnosticData()
                    .getCertificateDN(certificateId));
            proofObjectInfos.setObject(getCertificateBase64Encoded(rapport.getRapportCertificatDSS()));
        }

        // Autorités de confiance
        final List<CABean> listCa = new ArrayList<>();
        for (final CertificateToken cToken : bundleCA.getCertificates()) {
            final CABean caBean = new CABean();
            if (cToken.getCertificate().getIssuerX500Principal() != null) {
                caBean.setIssuerName(cToken.getCertificate().getIssuerDN().getName());
            }
            caBean.setSerialNumber(cToken.getSerialNumber().toString());
            caBean.setBase64Encoded(Base64.getEncoder().encodeToString(cToken.getEncoded()));
            caBean.setDigestAlgo(DigestAlgorithm.SHA256.getName());
            caBean.setDigestValue(Base64.getEncoder().encodeToString(cToken.getDigest(DigestAlgorithm.SHA256)));
            listCa.add(caBean);

        }
        proofObjectInfos.setCas(listCa);
        // CRL
        final List<CRLBean> listCrl = new ArrayList<>();
        final InputStream[] valuesCRL = crls.stream().map(ByteArrayInputStream::new)
                .toArray(ByteArrayInputStream[]::new);

        for (final InputStream inStream : valuesCRL) {
            try {
                final CRLBean crlBean = new CRLBean();
                final CertificateFactory cf;

                cf = CertificateFactory.getInstance("X.509");

                final X509CRL crl = (X509CRL) cf.generateCRL(inStream);
                crlBean.setBase64Encoded(Base64.getEncoder().encodeToString(crl.getEncoded()));
                crlBean.setIssuerName(crl.getIssuerDN().getName());
                crlBean.setIssueTime(sdfIso8601.format(crl.getThisUpdate()));
                final MessageDigest md = MessageDigest.getInstance("SHA-256");
                final byte[] digestValueByte = md.digest(crl.getEncoded());
                final String digestValue = Base64.getEncoder().encodeToString(digestValueByte);
                crlBean.setDigestAlgo(org.apache.xml.security.algorithms.MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA256);
                crlBean.setDigestValue(digestValue);
                listCrl.add(crlBean);
                inStream.close();
            } catch (GeneralSecurityException | IOException e) {
                LOG.error(ExceptionUtils.getStackTrace(e));
            }
        }

        final String rulesConf = "rules=" + rapport.getRules().stream().map(c -> c.getClass().getSimpleName())
                .collect(Collectors.joining(","));
        proofObjectInfos.setCrls(listCrl);
        // Alimentation du template
        final Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("comp", proofLibInfos.getComp());
        valueMap.put("compVersionMajor", pParams.getVersion().getMajor());
        valueMap.put("compVersionMinor", pParams.getVersion().getMinor());
        valueMap.put("compVersionPatch", pParams.getVersion().getPatch());
        valueMap.put("compVersionIteration", pParams.getVersion().getIteration());
        valueMap.put("compVersionFull", pParams.getVersion().getFull());
        valueMap.put("proofId", proofRequestInfos.getProofId());
        valueMap.put("createdDate", proofRequestInfos.getCreatedDate());
        valueMap.put("rulesConf", rulesConf);
        valueMap.put("object", proofObjectInfos.getObject());
        valueMap.put("type", proofObjectInfos.getType());
        valueMap.put("certSubject", proofObjectInfos.getCertSubject());
        valueMap.put("CAs", proofObjectInfos.getCas());
        valueMap.put("CRLs", proofObjectInfos.getCrls());
        valueMap.put("requesttype", proofRequestInfos.getRequestType());
        valueMap.put("requestid", proofRequestInfos.getRequestid());
        valueMap.put("prooftag", proofRequestInfos.getProofTag());
        valueMap.put("applicantId", proofRequestInfos.getApplicantId());
        valueMap.put("responseStatus", "valide:" + rapport.isValide());

        final StringWriter out = new StringWriter();
        try {
            assert template != null;
            template.process(valueMap, out);
            out.close();
        } catch (TemplateException | IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
        }

        return out.toString();
    }

    /**
     * Gets the certificate base 64 encoded.
     *
     * @param dssReport the dss report
     * @return the certificate base 64 encoded
     */
    private String getCertificateBase64Encoded(AbstractReports dssReport) {
        final List<XmlSignature> signatures = dssReport.getDiagnosticDataJaxb().getSignatures();
        final StringBuilder sb = new StringBuilder();
        for (final XmlSignature signature : signatures) {
            if (signature.getSigningCertificate() != null) {
                // Id du certificat à récupérer
                final String id = signature.getSigningCertificate().getCertificate().getId();
                // On récupère tous les certificats de signature
                final CertificateWrapper certificate = dssReport.getDiagnosticData().getUsedCertificateById(id);
                // gérer la concaténation dans le cas où il y a plusieurs
                // signatures.
                sb.append(Base64.getEncoder().encodeToString(certificate.getBinaries()));
            }
        }
        return sb.toString();
    }
}
