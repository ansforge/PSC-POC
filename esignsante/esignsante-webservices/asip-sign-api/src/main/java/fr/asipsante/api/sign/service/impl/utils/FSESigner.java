/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl.utils;


import java.io.IOException;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.OperatorCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.parameters.FSESignatureParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.fse.AttributsCMS;
import fr.asipsante.api.sign.fse.SignatureFSE;
import fr.asipsante.api.sign.utils.AsipSignClientException;
import fr.asipsante.api.sign.utils.AsipSignException;
import fr.asipsante.api.sign.utils.AsipSignServerException;



/**
 * The Class FSESigner.
 */
public class FSESigner implements ISigner {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(FSESigner.class);

    
    @Override
    public RapportSignature sign(final byte[] hashASigner, final SignatureParameters sParams )
            throws AsipSignException {
    	// Instantiate signature report

    	final RapportSignature result = new RapportSignature();
        
        try {
            SignatureFSE fseSigner = SignatureFSE.getInstance();

            if (!(sParams instanceof FSESignatureParameters)) {
            	throw new AsipSignServerException("L objet transmis pour les paramètres/attributs de signature n'est pas du type attendu");
            }
            FSESignatureParameters fseSignatureParameters = (FSESignatureParameters)sParams;
            
            if (!Arrays.equals(hashASigner, fseSignatureParameters.getEmpreinte())) {
            	throw new AsipSignServerException("la valeur du hash dans les attributs ne correspond pas à la valeur du hash à signer ");
            }
            
            if (!(fseSignatureParameters.getTypeFlux().equals("T") )&& !(fseSignatureParameters.getTypeFlux().equals("R")))
            		{
            			throw new AsipSignServerException("Le type de flux d'une FSE doit être 'T' ou 'R'");
            		}
            
            if ( (fseSignatureParameters.getIdFacturationPS() == null)
            		 || (fseSignatureParameters.getIdFacturationPS().isEmpty()))
    		{
    			throw new AsipSignServerException("L'identifiant de facturation du PS doit être renseigné");
    		}
            
            
            // Load the KeyStore and get the signing key and certificate.
            final KeyStore.PrivateKeyEntry keyEntry;
            try {
                // Gets the key entry.
                keyEntry = SignUtils.getKeys(sParams.getKeyStore(),
                        new PasswordProtection(sParams.getPassword().toCharArray())).get(0);
                if (keyEntry == null) {
                    throw new AsipSignServerException("Impossible d'extraire la clef de signature du keystore");
                }
            } catch (GeneralSecurityException e) {
                LOG.info(ExceptionUtils.getStackTrace(e));
                throw new AsipSignServerException(e);
            }

            
            X509Certificate  ca = (X509Certificate) keyEntry.getCertificate();
            PrivateKey privateKey = keyEntry.getPrivateKey();
            
            //setting des attributs à signer
            AttributsCMS attributsCMS = new AttributsCMS(hashASigner,fseSignatureParameters.getTypeFlux(),
            		fseSignatureParameters.getIdFacturationPS() ,convertToBase64PEMString(ca));


       
                        
            //on signe
            byte[] signedDataCms = fseSigner.signer(ca,
                                                        privateKey,
                                                        attributsCMS);
            result.setDocSigneBytes(signedDataCms);
           
   
        } catch (CertificateException |CMSException | OperatorCreationException | NoSuchAlgorithmException | IllegalArgumentException | IOException e) {
            e.printStackTrace();
            throw new AsipSignClientException(e);
        }
        return result;   
    }
  
    public String convertToBase64PEMString(X509Certificate x509Cert) throws IOException {
        StringWriter sw = new StringWriter();
        try (JcaPEMWriter  pw = new JcaPEMWriter (sw)) {
            pw.writeObject(x509Cert);
        }
        return sw.toString();
    }
}
