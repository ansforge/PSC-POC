/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.asipsante.api.sign.bean.errors.ErreurCA;
import fr.asipsante.api.sign.bean.errors.ErreurSignature;
import fr.asipsante.api.sign.bean.rapports.RapportCA;
import fr.asipsante.api.sign.enums.ErreurCAType;
import fr.asipsante.api.sign.enums.ErreurSignatureType;

/**
 * The Class RapportCATest.
 */
public class RapportCATest {

    /**
     * Cas nominal instance object test.
     */
    @Test
    public void casNominal_instanceObjectTest() {
        final List<ErreurCA> listeErreursCA = new ArrayList<>();
        final ErreurCA erreurChargementCA = new ErreurCA(ErreurCAType.CHARGEMENT_CERTIFICAT_IMPOSSIBLE);
        final ErreurCA erreurSyntaxeCA = new ErreurCA(ErreurCAType.ANALYSE_SYNTAXE_CERTIFICAT_IMPOSSIBLE);
        listeErreursCA.add(erreurChargementCA);
        listeErreursCA.add(erreurSyntaxeCA);
        final RapportCA rapportCa = new RapportCA();
        rapportCa.setCerDN("DN certificat");
        rapportCa.setValide(true);
        rapportCa.setListeErreursCA(listeErreursCA);
        assertTrue("le rapport est valide", rapportCa.isValide());
        assertNotNull("le DN du certificat n'est pas null", rapportCa.getCerDN());
        assertNotNull("le message d'erreur n'est pas null", rapportCa.getListeErreursCA().get(0).getMessage());
        assertNotNull("le code d'erreur n'est pas null", rapportCa.getListeErreursCA().get(0).getCode());
        final RapportCA rapportAvecArgsCA = new RapportCA(erreurChargementCA, rapportCa.getCerDN(), true);
        assertNotNull("rapport ca couverture", rapportAvecArgsCA);
        final RapportCA rapportAvecArgsCAListe = new RapportCA(listeErreursCA, rapportCa.getCerDN(), true);
        assertNotNull("liste rapport CA test ", rapportAvecArgsCAListe);
    }

    /**
     * Cas nominal instance object erreur test.
     */
    @Test
    public void casNominal_instanceObjectErreurTest() {

        final ErreurCA erreurChargementCA = new ErreurCA(ErreurCAType.ANALYSE_SYNTAXE_CERTIFICAT_IMPOSSIBLE);
        erreurChargementCA.setMessage("changer le message");
        assertNotNull("affichage code erreur not null", erreurChargementCA.getCode());
        assertNotNull("affichage message erreur not null", erreurChargementCA.getMessage());
        assertNotNull("affichage type erreur not null", erreurChargementCA.getType());
        assertNotNull("affichage type erreur not null", erreurChargementCA.toString());

    }

    /**
     * Cas nominal instance object erreur signature test.
     */
    @Test
    public void casNominal_instanceObjectErreurSignatureTest() {

        final ErreurSignature signature = new ErreurSignature(
                ErreurSignatureType.ANALYSE_SYNTAXE_CERTIFICAT_ET_SIGNATURE_IMPOSSIBLE);
        assertNotNull("tester object signature message n'est pas null ", signature.getMessage());
        assertNotNull("tester object signature code n'est pas null ", signature.getCode());
        assertNotNull("tester object signature type n'est pas null ", signature.getType());
        signature.setMessage("changer le message");
        assertNotNull("tester les messages d'erreurs", signature.getMessage());
        assertNotNull("tester object signature n'est pas null ", signature.toString());

    }

}
