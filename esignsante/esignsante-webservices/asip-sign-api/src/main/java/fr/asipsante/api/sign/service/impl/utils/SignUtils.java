/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl.utils;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.DSSException;
import eu.europa.esig.dss.model.ToBeSigned;
import eu.europa.esig.dss.pades.PAdESSignatureParameters;
import eu.europa.esig.dss.pades.signature.PAdESService;
import eu.europa.esig.dss.token.DSSPrivateKeyEntry;
import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.xades.XAdESSignatureParameters;
import eu.europa.esig.dss.xades.signature.XAdESService;
import fr.asipsante.api.sign.utils.AsipSignClientException;

/**
 * The Class SignUtils.
 */
public class SignUtils {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SignUtils.class);

    /**
     * Constructeur privé pour empécher l'instanciation.
     */
    private SignUtils() {
    }

    /**
     * Sélection de la première clé privée rencontrée dans une liste.
     *
     * @param keys Liste de clé privée
     * @return première clé privée de la liste
     * @throws AsipSignClientException the asip sign client exception
     */
    protected static DSSPrivateKeyEntry getFirstSigner(List<DSSPrivateKeyEntry> keys) throws AsipSignClientException {
        final DSSPrivateKeyEntry selectedKey; // private key entry
        if (Utils.isCollectionEmpty(keys)) {
            throw new AsipSignClientException("Aucune clé privée trouvée.");
        } else {
            selectedKey = keys.get(0); // get first entry
        }
        return selectedKey;
    }

    /**
     * Get the Xades SignedInfo XML segment that need to be signed.
     * 
     * @param toSignDocument toSignDocument
     * @param parameters parameters
     * @param service service
     * @return dataToSign
     * @throws AsipSignClientException AsipSignClientException
     */
    protected static ToBeSigned getSignedInfoXades(final DSSDocument toSignDocument, final XAdESSignatureParameters parameters,
            final XAdESService service) throws AsipSignClientException {
        final ToBeSigned dataToSign;
        try {
            dataToSign = service.getDataToSign(toSignDocument, parameters); // get data to sign
        } catch (final DSSException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            throw new AsipSignClientException(e);
        }
        return dataToSign;
    }
     
    /**
     * Get the Pades SignedInfo XML segment that need to be signed.
     * 
     * @param toSignDocument toSignDocument
     * @param parameters parameters
     * @param service service
     * @return dataToSign
     * @throws AsipSignClientException AsipSignClientException
     */
    protected static ToBeSigned getSignedInfoPades(final DSSDocument toSignDocument, final PAdESSignatureParameters parameters,
            final PAdESService service) throws AsipSignClientException {
        final ToBeSigned dataToSign;
        try {
            dataToSign = service.getDataToSign(toSignDocument, parameters); // get data to sign
        } catch (final DSSException e) {
            LOG.error(ExceptionUtils.getStackTrace(e));
            throw new AsipSignClientException(e);
        }
        return dataToSign;
    }

    /**
     * Creates the key info.
     *
     * @param fac      the factory
     * @param keyEntry the key entry
     * @return the key info
     * @throws KeyException the key exception
     */
    protected static KeyInfo createKeyInfo(XMLSignatureFactory fac, PrivateKeyEntry keyEntry) throws KeyException {
        final X509Certificate cert = (X509Certificate) keyEntry.getCertificate();
        final PublicKey pk = keyEntry.getCertificate().getPublicKey();
        final List<XMLStructure> contentToAdd = new ArrayList<>();

        // Create the KeyInfo containing the X509Data.
        final KeyInfoFactory kif = fac.getKeyInfoFactory();
        final List<Serializable> x509Content = new ArrayList<>();
        x509Content.add(cert); // add certificate to list
        final X509Data xd = kif.newX509Data(x509Content);
        contentToAdd.add(xd); // add x509 data

        // Add the KeyValue
        final KeyValue kv = kif.newKeyValue(pk);
        contentToAdd.add(kv);

        return kif.newKeyInfo(contentToAdd);
    }

    /**
     * Retourne la liste des clés privées contenus dans un KeyStore.
     *
     * @param keyStore keyStore
     * @param password mot de passe du keyStore
     * @return la liste des clés privées contenus dans un KeyStore
     * @throws GeneralSecurityException the general security exception
     */
    protected static List<PrivateKeyEntry> getKeys(final KeyStore keyStore, final PasswordProtection password)
            throws GeneralSecurityException {
        final List<PrivateKeyEntry> list = new ArrayList<>();

        final Enumeration<String> aliases = keyStore.aliases(); // List of all the alias names of this keystore.
        while (aliases.hasMoreElements()) {
            final String alias = aliases.nextElement();
            if (keyStore.isKeyEntry(alias)) {
                // get private key entry from alias and password
                final PrivateKeyEntry entry = (PrivateKeyEntry) keyStore.getEntry(alias, password);
                list.add(entry);
            } else {
                LOG.debug("Pas de clé trouvé pour l'alias '{}'", alias);
            }
        }
        return list;
    }
}
