/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore.PasswordProtection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.enumerations.SignaturePackaging;
import eu.europa.esig.dss.model.BLevelParameters;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.token.AsipKeyStoreSignatureToken;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.SignatureTokenConnection;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.xades.XAdESSignatureParameters;
import eu.europa.esig.dss.xades.signature.XAdESService;
import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.enums.Vars;
import fr.asipsante.api.sign.utils.AsipSignClientException;

/**
 * The Class XadesSigner.
 */
public class XadesSigner implements ISigner {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(XadesSigner.class);

    @Override
    public RapportSignature sign(final byte[] doc, final SignatureParameters sParams)
            throws AsipSignClientException {
    	// Instantiate signature report
        final RapportSignature result = new RapportSignature();
       
        final DSSPrivateKeyEntry privateKey;
        
        final DSSDocument toSignDocument = new InMemoryDocument(doc); // doc to DSSDocument

        try (SignatureTokenConnection signingToken = new AsipKeyStoreSignatureToken(sParams.getKeyStore(),
                new PasswordProtection(sParams.getPassword().toCharArray()));) {
            final List<DSSPrivateKeyEntry> keys = signingToken.getKeys();
            privateKey = SignUtils.getFirstSigner(keys); // get first signature

            final XAdESSignatureParameters parameters = new XAdESSignatureParameters();
            // We choose the level of the signature (-B, -T, -LT, -LTA).
            parameters.setSignatureLevel(SignatureLevel.XAdES_BASELINE_B);
            // We choose the type of the signature packaging (ENVELOPED, ENVELOPING, DETACHED).
            parameters.setSignaturePackaging(SignaturePackaging.valueOf(sParams.getSignPackaging().toString()));

            // Digest and Canonicalization algorithms
            parameters.setDigestAlgorithm(DigestAlgorithm.forName(sParams.getDigestAlgo().toString()));
            parameters.setSignedInfoCanonicalizationMethod(sParams.getCanonAlgo());

            // We set the signing certificate
            parameters.setSigningCertificate(privateKey.getCertificate());
            // We set the certificate chain
            parameters.setCertificateChain(privateKey.getCertificateChain());

            // Add the X509 subject name
            parameters.setAddX509SubjectName(true);
            
            // Add the roles (the roles' list can be null or empty, since it is optional)
            BLevelParameters bLevelParams = parameters.bLevel();
            bLevelParams.setClaimedSignerRoles(sParams.getRoles());
            parameters.setBLevelParams(bLevelParams);

            // Create common certificate verifier
            final CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();

            // Create XAdES service for signature
            final XAdESService service = new XAdESService(commonCertificateVerifier);

            // Get the SignedInfo XML segment that need to be signed.
            final ToBeSigned dataToSign = SignUtils.getSignedInfoXades(toSignDocument, parameters, service);
            // This function obtains the signature value for signed information
            // using the private key and specified algorithm
            final SignatureValue signatureValue = signingToken.sign(dataToSign, parameters.getDigestAlgorithm(),
                    privateKey);

            // We invoke the service to sign the document with the signature value obtained
            // in the previous step.
            final DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);

            // signed doc to string
            InputStream signedDocIs = signedDocument.openStream();
            String charSet = UniversalDetector.detectCharset(signedDocIs);
            LOG.debug("charSet found: "+charSet);
            signedDocIs.close();
            
            signedDocIs = signedDocument.openStream();
            
            result.setDocSigne(IOUtils.toString(signedDocIs,
            		charSet));
            LOG.debug("result" +result.getDocSigne());            
            signedDocIs.close();   
        } catch (final IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            throw new AsipSignClientException(Vars.CERTIFICAT_SIGN_MESSAGE.getVar());
        }
        LOG.debug("Signature du fichier en Xades Baseline B terminée.");
        return result;
    }
    
}
