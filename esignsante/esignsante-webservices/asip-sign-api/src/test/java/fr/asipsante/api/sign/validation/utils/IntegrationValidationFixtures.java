package fr.asipsante.api.sign.validation.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import eu.europa.esig.dss.detailedreport.jaxb.XmlSignature;
import eu.europa.esig.dss.enumerations.Indication;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import org.apache.commons.io.IOUtils;

import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.service.ICACRLService;
import fr.asipsante.api.sign.service.impl.CACRLServiceImpl;
import fr.asipsante.api.sign.validation.signature.DSSSignatureValidator;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * The Class IntegrationValidationFixtures.
 */
public final class IntegrationValidationFixtures {

    /** The Constant requestType. */
    private static final String requestType = "verifSign";

    /** The Constant requestid. */
    private static final String requestid = "001";

    /** The Constant proofTag. */
    private static final String proofTag = "pvf";

    /** The Constant applicantId. */
    private static final String applicantId = "RPPS";

    /** The Constant CONFPATH. */
    private static final String CONFPATH = "sign.properties";

    /** The Constant VERIFCONFPATH. */
    private static final String VERIFCONFPATH = "verifsign.properties";

    /** The Constant PROOFCONFPATH. */
    private static final String PROOFCONFPATH = "proof.properties";

    /** The Constant BUNDLE_CA_FILE_PATH_STRING. */
    // Debut:Jeux de données
    final static String BUNDLE_CA_FILE_PATH_STRING = "ca-bundle.crt";

    /** The Constant BUNDLE_CRL_FILE_PATH_STRING. */
    final static String BUNDLE_CRL_FILE_PATH_STRING = "ca-bundle.crl";

    // Fin:Jeux de données

    /**
     * Gets the CACRL wrapper.
     *
     * @return the CACRL wrapper
     * @throws Exception the exception
     */
    public static CACRLWrapper getCACRLWrapper() throws Exception {
        return getCACRLWrapper(BUNDLE_CA_FILE_PATH_STRING);
    }

    /**
     * Gets the CACRL wrapper.
     *
     * @param bundleCaPath bundle path
     * @return the CACRL wrapper
     * @throws Exception the exception
     */
    public static CACRLWrapper getCACRLWrapper(String bundleCaPath) throws Exception {
        final ICACRLService cacrlService = new CACRLServiceImpl();
        final File bundleCaFile = new File(
                Thread.currentThread().getContextClassLoader().getResource(bundleCaPath).toURI());
        final File bundleCrlFile = new File(
                Thread.currentThread().getContextClassLoader().getResource(BUNDLE_CRL_FILE_PATH_STRING).toURI());
        cacrlService.loadCA(bundleCaFile);
        cacrlService.loadCRL(bundleCrlFile);
        return cacrlService.getCacrlWrapper();
    }

    /**
     * Check rule.
     *
     * @param document the document
     * @param rule     the rule
     * @return the rapport validation signature
     * @throws Exception the exception
     */
    public static RapportValidationSignature checkRule(String document, IVisitor rule, SignatureLevel signatureLevel)
            throws Exception {
        final DSSSignatureValidator validator = new DSSSignatureValidator();
        final RapportValidationSignature rapport = new RapportValidationSignature();
        if (rapport.getRules() == null){
            rapport.setRules(new ArrayList<>());
        }
        rapport.setSignatureLevel(signatureLevel);
        rapport.getRules().add(rule);

        validator.validateSignature(rapport, getDocumentAsBytes(document), getCACRLWrapper());
        
        new CACRLWrapper(getCACRLWrapper().getBundleCA(), getCACRLWrapper().getBundleCRL());
        rapport.setValide(isValid(rapport));
        rapport.accept(rule);
        return rapport;
    }

    private static boolean isValid(RapportValidationSignature rapport) {

        boolean isValid = true;
        for (final XmlSignature signature : rapport.getReports().getDetailedReportJaxb().getSignatures()) {
            if (Indication.PASSED
                    .equals(signature.getValidationProcessBasicSignatures().getConclusion().getIndication())) {
            } else {
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * Gets the document.
     *
     * @param filename the filename
     * @return the document
     * @throws Exception the exception
     */
    public static byte[] getDocument(String filename) throws Exception {
        final String filePath = Thread.currentThread().getContextClassLoader().getResource(filename).getFile();

        File file = new File(filePath);
        InputStream fileIs = new FileInputStream(file);
        // Conversion du fichier XML en une chaine de caractères
        return Files.readAllBytes(file.toPath());
    }

    /**
     * Gets the document.
     *
     * @param filename the filename
     * @return the document
     * @throws Exception the exception
     */
    public static byte[] getDocumentAsBytes(String filename) throws Exception {
        final String filePath = Thread.currentThread().getContextClassLoader().getResource(filename).getFile();
        File file = new File(filePath);
        return Files.readAllBytes(Paths.get(String.valueOf(file.toPath())));
    }

    /**
     * Gets the DER cert.
     *
     * @param filename the filename
     * @return the DER cert
     * @throws Exception the exception
     */
    public static byte[] getDERCert(String filename) throws Exception {
        final InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        return IOUtils.toByteArray(stream);

    }

    /**
     * Gets the sign conf.
     *
     * @return the sign conf
     * @throws Exception the exception
     */
    public static Properties getSignConf() throws Exception {
        final Properties signConf = new Properties();
        signConf.load(ClassLoader.getSystemResourceAsStream(CONFPATH));
        return signConf;

    }

    /**
     * Gets the requesttype.
     *
     * @return the requesttype
     */
    public static String getRequesttype() {
        return requestType;
    }

    /**
     * Gets the requestid.
     *
     * @return the requestid
     */
    public static String getRequestid() {
        return requestid;
    }

    /**
     * Gets the prooftag.
     *
     * @return the prooftag
     */
    public static String getProoftag() {
        return proofTag;
    }

    /**
     * Gets the applicantid.
     *
     * @return the applicantid
     */
    public static String getApplicantid() {
        return applicantId;
    }

    /**
     * Gets the verif conf.
     *
     * @return the verif conf
     * @throws Exception the exception
     */
    public Properties getVerifConf() throws Exception {
        final Properties verifConf = new Properties();
        verifConf.load(ClassLoader.getSystemResourceAsStream(VERIFCONFPATH));
        return verifConf;
    }

    /**
     * Gets the proof conf.
     *
     * @return the proof conf
     * @throws Exception the exception
     */
    public Properties getProofConf() throws Exception {
        final Properties proofConf = new Properties();
        proofConf.load(ClassLoader.getSystemResourceAsStream(PROOFCONFPATH));
        return proofConf;
    }

}
