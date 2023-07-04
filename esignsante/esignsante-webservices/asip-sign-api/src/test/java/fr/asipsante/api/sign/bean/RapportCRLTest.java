/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import fr.asipsante.api.sign.bean.errors.ErreurCRL;
import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.bean.rapports.RapportCRL;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.ErreurCRLType;
import fr.asipsante.api.sign.enums.MetaDataType;

/**
 * The Class RapportCRLTest.
 */
public class RapportCRLTest {

    /**
     * Cas nominal instance object test.
     */
    @Test
    public void casNominal_instanceObjectTest() {

        final List<ErreurCRL> listeErreursCRL = new ArrayList<>();
        final ErreurCRL erreurChargementCRL = new ErreurCRL(ErreurCRLType.ANALYSE_SYNTAXE_CRL_IMPOSSIBLE);
        final ErreurCRL erreurSyntaxeCRL = new ErreurCRL(ErreurCRLType.BUNDLE_CA_NON_CHARGE);
        listeErreursCRL.add(erreurChargementCRL);
        listeErreursCRL.add(erreurSyntaxeCRL);
        final RapportCRL rapportCRL = new RapportCRL();
        rapportCRL.setEmetteurCrl("emetteur du certificat CRL");
        rapportCRL.setIdentifiantCrl("identifiant du crl");
        rapportCRL.setValide(true);
        rapportCRL.setListeErreursCRL(listeErreursCRL);
        assertTrue("le rapport est valide", rapportCRL.isValide());
        assertNotNull("le rapport emetteur crl n'est pas null", rapportCRL.getEmetteurCrl());
        assertNotNull("le rapport identifiant crl n'est pas null", rapportCRL.getIdentifiantCrl());
        assertTrue("le rapport du CRL est valide", rapportCRL.isValide());
        final RapportCRL rapportAvecArgsCRL = new RapportCRL(erreurChargementCRL, rapportCRL.getEmetteurCrl(),
                rapportCRL.getIdentifiantCrl(), true);
        assertNotNull("verifier argument CRL", rapportAvecArgsCRL);
        final RapportCRL rapportAvecArgsCRLListe = new RapportCRL(listeErreursCRL, rapportCRL.getEmetteurCrl(),
                rapportCRL.getIdentifiantCrl(), true);
        assertNotNull("verifier liste argument crl", rapportAvecArgsCRLListe);
        assertNotNull("verifier liste message erreur", rapportAvecArgsCRL.getListeErreursCRL().get(0).getMessage());

    }

    /**
     * Cas nominal instance object erreur test.
     */
    @Test
    public void casNominal_instanceObjectErreurTest() {

        final ErreurCRL erreurChargementCRL = new ErreurCRL(ErreurCRLType.ANALYSE_SYNTAXE_CRL_IMPOSSIBLE);
        erreurChargementCRL.setMessage("changer le message");
        assertNotNull("affichage code erreur not null", erreurChargementCRL.getCode());
        assertNotNull("affichage message erreur not null", erreurChargementCRL.getMessage());
        assertNotNull("affichage type erreur not null", erreurChargementCRL.getType());
        assertNotNull("affichage type erreur not null", erreurChargementCRL.toString());
    }

    /**
     * Cas nominal instance object erreur signature test.
     *
     * @throws Exception the exception
     */
    @Test
    public void casNominal_instanceObjectErreurSignatureTest() throws Exception {

        final RapportValidationSignature base = new RapportValidationSignature();

        final Properties props = new Properties();
        props.load(ClassLoader.getSystemResourceAsStream("verifsign.properties"));

        final List<MetaDatum> metadatas = base.getMetaData();
        metadatas.add(new MetaDatum(MetaDataType.DATE_SIGNATURE, "01-01-2019"));
        assertNotNull("verification meta data", metadatas.get(0).getValue());

    }
}
