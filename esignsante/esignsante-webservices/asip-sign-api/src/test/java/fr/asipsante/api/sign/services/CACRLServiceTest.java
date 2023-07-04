package fr.asipsante.api.sign.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import fr.asipsante.api.sign.bean.rapports.RapportCA;
import fr.asipsante.api.sign.bean.rapports.RapportCRL;
import fr.asipsante.api.sign.service.ICACRLService;
import fr.asipsante.api.sign.service.impl.CACRLServiceImpl;

/**
 * The Class CACRLServiceTest.
 */
public class CACRLServiceTest {

    /** The service. */
    ICACRLService service = new CACRLServiceImpl();

    /**
     * Bundle CA null test.
     */
    @Test
    public void bundleCANullTest() {
        final List<RapportCA> rapports = service.loadCA(null);
        assertFalse("Le rapport devrait être invalide", rapports.iterator().next().isValide());
        assertTrue("La liste devrait être vide", service.getCa().isEmpty());
    }

    /**
     * Bundle CA vide test.
     *
     * @throws Exception the exception
     */
    @Test
    public void bundleCAVideTest() throws Exception {
        final File bundleVide = new File(
                Thread.currentThread().getContextClassLoader().getResource("ca-bundle-Empty.crt").toURI().getPath());

        final List<RapportCA> rapports = service.loadCA(bundleVide);
        assertFalse("Le rapport devrait être invalide", rapports.iterator().next().isValide());
        assertTrue("La liste devrait être vide", service.getCa().isEmpty());
    }

    /**
     * Bundle CRL null test.
     */
    @Test
    public void bundleCRLNullTest() {
        final List<RapportCRL> rapports = service.loadCRL(null);
        assertFalse("Le rapport devrait être invalide", rapports.iterator().next().isValide());
    }

    /**
     * Crl expirees test.
     *
     * @throws Exception the exception
     */
    @Test
    public void crlExpireesTest() throws Exception {
        final File expiredCRl = new File(Thread.currentThread().getContextClassLoader()
                .getResource("ca-bundle-tomws.crl.expired").toURI().getPath());
        final List<RapportCRL> rapports = service.loadCRL(expiredCRl);
        assertFalse("Le rapport devrait être invalide", rapports.iterator().next().isValide());
    }

}
