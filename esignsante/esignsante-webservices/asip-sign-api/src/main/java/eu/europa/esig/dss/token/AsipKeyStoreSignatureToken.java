package eu.europa.esig.dss.token;

import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;

import javax.security.auth.DestroyFailedException;

import eu.europa.esig.dss.model.DSSException;

public class AsipKeyStoreSignatureToken
        extends AbstractKeyStoreTokenConnection {

    private final PasswordProtection password;

    private final KeyStore keyStore;

    public AsipKeyStoreSignatureToken(KeyStore keyStore, PasswordProtection password) {
        this.password = password;
        this.keyStore = keyStore;
    }

    @Override
    public void close() {
        if (password != null) {
            try {
                password.destroy();
            } catch (DestroyFailedException e) {
                LOG.error("Unable to destroy password", e);
            }
        }
    }

    @Override
    KeyStore getKeyStore() throws DSSException {
        return keyStore;
    }

    @Override
    PasswordProtection getKeyProtectionParameter() {
        return password;
    }

}
