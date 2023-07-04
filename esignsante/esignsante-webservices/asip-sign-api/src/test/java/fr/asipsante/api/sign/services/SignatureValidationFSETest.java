package fr.asipsante.api.sign.services;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getCACRLWrapper;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.DigestDocument;
import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.bean.parameters.FSESignatureParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureValidationParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.MetaDataType;
import fr.asipsante.api.sign.service.ISignatureValidationService;
import fr.asipsante.api.sign.service.impl.ProofGenerationServiceImpl;
import fr.asipsante.api.sign.service.impl.SignatureValidationServiceImpl;
import fr.asipsante.api.sign.service.impl.utils.FSESigner;
import fr.asipsante.api.sign.utils.AsipSignException;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;
import fr.asipsante.api.sign.validation.signature.rules.impl.DocumentIntact;
import fr.asipsante.api.sign.validation.signature.rules.impl.FormatSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.SignatureNonVide;
import fr.asipsante.api.sign.enums.DigestAlgorithm;

/**
 * The Class ProofGeneratorServiceTest.
 */
public class SignatureValidationFSETest {

	/** The service. */
	private ISignatureValidationService service = new SignatureValidationServiceImpl();

	/** The params signature. */
	private static SignatureValidationParameters paramsSignature = new SignatureValidationParameters();

	/** ProofGeneraorService */
	private ProofGenerationServiceImpl proofService = new ProofGenerationServiceImpl();

	private byte[] fse;

	private byte[] digest;

	private byte[] signature;

	/**
	 * Test init.
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws AsipSignException 
	 * @throws KeyStoreException 
	 * @throws CertificateException 
	 */
	@Before
	public void init() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, AsipSignException {

		// lecture de la signature
//		File signatureFile = new File("src/test/resources/fichiers/HashSigneCalc_esignsante-apcv.cms");
//		FileInputStream fileInputStream = new FileInputStream(signatureFile);
//		signature = new byte[(int) signatureFile.length()];
//		fileInputStream.read(signature);
//		fileInputStream.close();

		// calcul du hash du fichier source
		// hash qui a été signé
//		File fseFile = new File("src/test/resources/fichiers/cal.txt");
//		FileInputStream fileInputStreamd = new FileInputStream(fseFile);

//		fse = new byte[(int) fseFile.length()];
//		fileInputStreamd.read(fse);
//		fileInputStreamd.close();

		// Calcul de SHA256 du buffer
//		MessageDigest md = MessageDigest.getInstance("SHA-256");
//		md.update(fse);
//		digest = md.digest();
		
		//calcul du hash et signature
		signature = signHash();

		// Règles de validation
		final List<IVisitor> signRules = new ArrayList<>();

		signRules.add(new FormatSignature());
		signRules.add(new SignatureNonVide());
		signRules.add(new DocumentIntact());
		paramsSignature.setRules(signRules);

		final List<MetaDatum> metaDataSignature = new ArrayList<>();
		metaDataSignature.add(new MetaDatum(MetaDataType.DATE_SIGNATURE, null));
		metaDataSignature.add(new MetaDatum(MetaDataType.RAPPORT_DIAGNOSTIQUE, null));
		metaDataSignature.add(new MetaDatum(MetaDataType.RAPPORT_DSS, null));
		paramsSignature.setMetaData(metaDataSignature);
	}

	@Test
	public void validationSignFSETest() throws Exception {

//         CMSSignedData datas = new CMSSignedData(signature);
//         SignerInformationStore signersInfo = datas.getSignerInfos();

		String digestBase64 = Base64.getEncoder().encodeToString(digest);
		DSSDocument doc = new DigestDocument(eu.europa.esig.dss.enumerations.DigestAlgorithm.SHA256, 
				digestBase64, "nomDuDoc");

		// validation de la signature
		final RapportValidationSignature rapport = service.validateFSESignature(signature, paramsSignature,
				getCACRLWrapper() /* wrapper */, doc);
		assertTrue("Le document signé ne doit pas être vide", rapport.getDocSigneBytes().length > 0);
		assertTrue("Rapport invalide à tort", rapport.isValide());
		assertTrue("La liste des erreurs devraient être vide", rapport.getListeErreurSignature().isEmpty());

		assertTrue("Erreurs sur les metaData", rapport.getMetaData().size() == 3);
		for (MetaDatum metaData : rapport.getMetaData()) {
			assertTrue("Erreurs sur les metaData",
					(rapport.getMetaData().get(0).getType() == MetaDataType.DATE_SIGNATURE)
							|| (rapport.getMetaData().get(0).getType() == MetaDataType.RAPPORT_DIAGNOSTIQUE)
							|| (rapport.getMetaData().get(0).getType() == MetaDataType.RAPPORT_DSS));
		}
		assertTrue("Erreurs sur les metaData",
				rapport.getMetaData().get(0).getType() != rapport.getMetaData().get(1).getType());
		assertTrue("Erreurs sur les metaData",
				rapport.getMetaData().get(0).getValue() != rapport.getMetaData().get(1).getValue());

		assertTrue(rapport.getReports().getDetailedReport().getBasicBuildingBlocksNumber() == 1);
	}

	@Test
	public void validationSignFSEKO_SignatureVideTest() throws Exception {

		String digestBase64 = Base64.getEncoder().encodeToString(digest);
		DSSDocument doc = new DigestDocument(eu.europa.esig.dss.enumerations.DigestAlgorithm.SHA256, digestBase64, "nomDuDoc");

		try {
			final RapportValidationSignature rapport = service.validateFSESignature("".getBytes(), paramsSignature,
					getCACRLWrapper() /* wrapper */, doc);
			assertFalse("Rapport valide à tort", rapport.isValide());
		} catch (Exception e) {
			// OK;
		}

		try {
			final RapportValidationSignature rapport = service.validateFSESignature(null, paramsSignature,
					getCACRLWrapper() /* wrapper */, doc);
			assertFalse("Rapport valide à tort", rapport.isValide());
		} catch (Exception e) {
			// OK;
		}

	}

	@Test
	public void validationSignFSEKO_SignatureModifieeTest() throws IOException {

		String digestBase64 = Base64.getEncoder().encodeToString(digest);
		DSSDocument doc = new DigestDocument(eu.europa.esig.dss.enumerations.DigestAlgorithm.SHA256, digestBase64, "nomDuDoc");

		File signatureFile = new File("src/test/resources/fichiers/signatureFSE_nonIntacte.cms");
		FileInputStream fileInputStream = new FileInputStream(signatureFile);
		byte[] signatureNonIntacte = new byte[(int) signatureFile.length()];
		fileInputStream.read(signatureNonIntacte);
		fileInputStream.close();

		// écrasement des rules par défault
		final List<IVisitor> newSignRules = new ArrayList<>();
		newSignRules.add(new SignatureNonVide());
		paramsSignature.setRules(newSignRules);
		try {
			final RapportValidationSignature rapport = service.validateFSESignature(signatureNonIntacte,
					paramsSignature, getCACRLWrapper() /* wrapper */, doc);
			assertFalse("Rapport valide à tort", rapport.isValide());
		} catch (Exception e) {
			// OK
		}

	}

	@Test
	public void validationSignFSEKO_BadDigestTest() {
		byte[] badDigest = Arrays.copyOf(digest, digest.length);
		badDigest[digest.length - 1] = 12;
		String badDigestBase64 = Base64.getEncoder().encodeToString(badDigest);
		DSSDocument doc = new DigestDocument(eu.europa.esig.dss.enumerations.DigestAlgorithm.SHA256, badDigestBase64, "nomDuDoc");
		try {
			final RapportValidationSignature rapport = service.validateFSESignature(signature, paramsSignature,
					getCACRLWrapper() /* wrapper */, doc);
			assertFalse("Rapport valide à tort", rapport.isValide());
		} catch (Exception e) {
			// OK;
		}
	}
	
	  private byte[] signHash() throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException, AsipSignException {
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
	           
	        SignatureParameters params = new SignatureParameters();
	        
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
	        
	        FSESignatureParameters fseSignatureParameters = new FSESignatureParameters(params, "751000017", "T", digest);
	        FSESigner signer = new FSESigner();
	        RapportSignature rapport = signer.sign(digest,fseSignatureParameters);
	        return rapport.getDocSigneBytes();
	    }
	}

