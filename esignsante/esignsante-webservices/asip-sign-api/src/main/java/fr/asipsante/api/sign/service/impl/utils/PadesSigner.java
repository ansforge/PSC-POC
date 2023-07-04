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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.model.SignatureValue;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.pades.PAdESSignatureParameters;
import eu.europa.esig.dss.pades.signature.PAdESService;
import eu.europa.esig.dss.token.AsipKeyStoreSignatureToken;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.token.SignatureTokenConnection;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.enums.Vars;
import fr.asipsante.api.sign.utils.AsipSignClientException;

/**
 * The Class PadesSigner.
 */
public class PadesSigner implements ISigner {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PadesSigner.class);

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

            final PAdESSignatureParameters parameters = new PAdESSignatureParameters();
            // We choose the level of the signature (-B, -T, -LT, -LTA).
            parameters.setSignatureLevel(SignatureLevel.PAdES_BASELINE_B);
            // We choose the type of the signature packaging (ENVELOPED, ENVELOPING, DETACHED).
            //parameters.setSignaturePackaging(SignaturePackaging.valueOf(sParams.getSignPackaging().toString()));

            // Digest and Canonicalization algorithms
            parameters.setDigestAlgorithm(DigestAlgorithm.forName(sParams.getDigestAlgo().toString()));

            // We set the signing certificate
            parameters.setSigningCertificate(privateKey.getCertificate());
            // We set the certificate chain
            parameters.setCertificateChain(privateKey.getCertificateChain());

            // Add the roles
            if (sParams.getRoles() != null) {
            	parameters.setReason(sParams.getRoles().toString());
            }

            // Create common certificate verifier
            final CommonCertificateVerifier commonCertificateVerifier = new CommonCertificateVerifier();

            // Create XAdES service for signature
            final PAdESService service = new PAdESService(commonCertificateVerifier);

            // Get the SignedInfo XML segment that need to be signed.
            final ToBeSigned dataToSign = SignUtils.getSignedInfoPades(toSignDocument, parameters, service);
            // This function obtains the signature value for signed information
            // using the private key and specified algorithm
            final SignatureValue signatureValue = signingToken.sign(dataToSign, parameters.getDigestAlgorithm(),
                    privateKey);
            
            // We invoke the service to sign the document with the signature value obtained
            // in the previous step.
            final DSSDocument signedDocument = service.signDocument(toSignDocument, parameters, signatureValue);
            InputStream signedDocIs = signedDocument.openStream();
            result.setDocSigneBytes(IOUtils.toByteArray(signedDocIs));
            signedDocIs.close();   
        } catch (final IOException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            throw new AsipSignClientException(Vars.CERTIFICAT_SIGN_MESSAGE.getVar());
        }
        LOG.debug("Signature du fichier en Pades Baseline B terminée.");
        return result;
    }
    
}
