/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.asipsante.api.sign.bean.parameters.FSESignatureParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.enums.DigestAlgorithm;
import fr.asipsante.api.sign.service.ISignatureService;
import fr.asipsante.api.sign.service.impl.SignatureServiceImpl;
import fr.asipsante.api.sign.service.impl.utils.FSESigner;
import fr.asipsante.api.sign.utils.AsipSignServerException;

/**
 * The Class SignatureFSEServiceTest.
 * Test FSE specific signature
 */
public class SignatureFSEServiceTest {

    /**
     * Logger pour la classe.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SignatureFSEServiceTest.class);

    /** the service */
    ISignatureService service = new SignatureServiceImpl();
    
    /** The signer. */
    private FSESigner signer = new FSESigner();

    /** The params. */
    //private FSESignatureParameters params = new FSESignatureParameters();
    SignatureParameters params = new SignatureParameters();
    
    FSESignatureParameters fseSignatureParameters = null;
    
    /** hash of the FSE to sign */
    private byte[] digest = null;

    /**
     * Inits the commons parameters.
     *
     * @throws Exception the exception
     */
    @Before
    public void initCommonsParameters() throws Exception {
    	
    	final KeyStore keyStore = KeyStore.getInstance("PKCS12");
       
        keyStore.load(new FileInputStream(ClassLoader.getSystemResource(
                "esignsante-apcv.p12").getPath()),
                "Password01+2016".toCharArray());    
        
        File a_file = new File("src/test/resources/fichiers/cal.txt");
        FileInputStream fileInputStream =  new FileInputStream(a_file);

        byte[] fichier =  new byte[(int)a_file.length()];
        fileInputStream.read(fichier);
        fileInputStream.close();

        // Calcul de SHA256 du buffer
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(fichier);
        digest = md.digest();         
           
        params.setCanonAlgo("http://www.w3.org/2001/10/xml-exc-c14n#");
        params.setDigestAlgo(DigestAlgorithm.SHA256);
        params.setObjectId("Tao_Sig_Id_SignedDocument");
        params.setSignId("Tao_Sig_Id_SIG");
        params.setKeyStore(keyStore);
        params.setPassword("Password01+2016");
        params.setSignValueId("1");
        List<String> roles = new ArrayList<String>();
        roles.add("délégataire de signature pour M. Spok");
        roles.add("délégataire de signature pour M. Haddok");
        params.setRoles(roles);
        
        fseSignatureParameters = new FSESignatureParameters(params, "751000017", "T", digest);
        }

    /**
     * Signature d'un hash d'une FSE.
     * Cas passant. Verification des attributs de la CNAM
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureFSESignerTest() throws Exception {
        RapportSignature rapport = null;
       
        //verif de la construction de fseSignatureParameters
        if (!Arrays.equals(digest, fseSignatureParameters.getEmpreinte())) {
        	throw new AsipSignServerException("la valeur du hash dans les attributs ne correspond pas à la valeur du hash à signer ");
        }
        assertEquals(2,fseSignatureParameters.getRoles().size());
        assertEquals("délégataire de signature pour M. Spok",fseSignatureParameters.getRoles().get(0));
        assertEquals("T",fseSignatureParameters.getTypeFlux());
        assertEquals("751000017",fseSignatureParameters.getIdFacturationPS());
           
        rapport = signer.sign(digest,fseSignatureParameters);
        assertNotNull("Le rapport de signature ne doit pas être null", rapport);
        assertTrue("La signature ne doit pas être vide",rapport.getDocSigneBytes().length>0);
        assertTrue("Le rapport contient des erreurs alors qu'il devrait être vide",
                rapport.getListeErreurSignature().isEmpty());
        
        //On vérifie la présences des attributs signés 
        CMSSignedData datas = new CMSSignedData(rapport.getDocSigneBytes());
        SignerInformationStore store = datas.getSignerInfos();
        Collection<SignerInformation> signers = store.getSigners();
        assertTrue("Un seul SignerInformation attendu",signers.size() == 1 );       
        for (SignerInformation signerInformation : signers) {
        	//signing time
        	Attribute signingTime = signerInformation.getSignedAttributes().get(CMSAttributes.signingTime);
        	assertNotNull(signingTime.getAttrValues().getObjectAt(0));
        	
        	//digest
        	Attribute digest = signerInformation.getSignedAttributes()
        			.get(CMSAttributes.messageDigest);
        	assertNotNull(digest);
        	
        	//type de Flux
        	Attribute typeFlux = signerInformation.getSignedAttributes().get(new ASN1ObjectIdentifier("1.2.250.1.186.500.1.2"));
        	assertEquals("T", typeFlux.getAttrValues().getObjectAt(0).toString());
        	
        	//id Facturation
        	Attribute idFacturation = signerInformation.getSignedAttributes().get(new ASN1ObjectIdentifier("1.2.250.1.186.500.1.5"));
        	assertEquals("751000017", idFacturation.getAttrValues().getObjectAt(0).toString());   	
		}
    }
    
    /**
     * Signature via l'interface.
     * Cas passant
     *
     * @throws Exception the exception
     */    
    @Test
    public void signatureFSEInterfaceTest() throws Exception {
        RapportSignature rapport = null;
       
                 
        rapport = service.signFSE(digest,"123456", "T", params);
        assertNotNull("Le rapport de signature ne doit pas être null", rapport);
        assertTrue("La signature ne doit pas être vide",rapport.getDocSigneBytes().length>0);
        assertTrue("Le rapport contient des erreurs alors qu'il devrait être vide",
                rapport.getListeErreurSignature().isEmpty());
        
        
//        FileOutputStream fo = new FileOutputStream("NEWhashcal.txt.signe.cms");
//        fo.write(rapport.getDocSigneBytes());
//        fo.close();
    }
    
    /**
     * Cas non passant signature FSE 
     * Problème avec les CMSAttributs additionnels
     *
     * @throws Exception the exception
     */
    @Test
    public void signatureFSEMissingArgument() throws Exception {
        RapportSignature rapport = null;
     
        try {
        	rapport = service.signFSE(digest, "751000017", "S", params);
        	assertTrue(false);
		} catch (AsipSignServerException e) {
			assertTrue(e instanceof AsipSignServerException);
			//OK
		}
        
        try {
        	rapport = service.signFSE(digest, "", "T", params);
        	assertTrue(false);
		} catch (AsipSignServerException e) {
			assertTrue(e instanceof AsipSignServerException);
			//OK
		}
        
        try {
        	rapport = service.signFSE(digest, null, "T", params);
        	assertTrue(false);
		} catch (AsipSignServerException e) {
			assertTrue(e instanceof AsipSignServerException);
			//OK
		}
        
        try {
        	rapport = service.signFSE(digest, "123", "", params);
        	assertTrue(false);
		} catch (AsipSignServerException e) {
			//OK
		}
        
        try {
        	rapport = service.signFSE(digest, "123", "T", params);
        	//OK
		} catch (Exception e) {			
			assertTrue(false);
		}
        
        try {
        	rapport = service.signFSE(null, "123", "T", params);
        	assertTrue(false);
		} catch (Exception e) {			
			//OK
		}
    }
    

    
}
    
  

