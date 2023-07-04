package fr.asipsante.api.sign.services;

import static fr.asipsante.api.sign.validation.utils.IntegrationValidationFixtures.getCACRLWrapper;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.util.Store;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.DigestDocument;
import eu.europa.esig.dss.model.x509.CertificateToken;
import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.metadata.MetaDatum;
import fr.asipsante.api.sign.bean.parameters.CertificateValidationParameters;
import fr.asipsante.api.sign.bean.parameters.ProofParameters;
import fr.asipsante.api.sign.bean.parameters.SignatureValidationParameters;
import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;
import fr.asipsante.api.sign.enums.MetaDataType;
import fr.asipsante.api.sign.service.ICertificateValidationService;
import fr.asipsante.api.sign.service.ISignatureValidationService;
import fr.asipsante.api.sign.service.impl.CertificateValidationServiceImpl;
import fr.asipsante.api.sign.service.impl.ProofGenerationServiceImpl;
import fr.asipsante.api.sign.service.impl.SignatureValidationServiceImpl;
import fr.asipsante.api.sign.service.impl.utils.SignUtils;
import fr.asipsante.api.sign.service.impl.utils.Version;
import fr.asipsante.api.sign.utils.AsipSignServerException;
import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;
import fr.asipsante.api.sign.validation.signature.rules.IVisitor;
import fr.asipsante.api.sign.validation.signature.rules.impl.DocumentIntact;
import fr.asipsante.api.sign.validation.signature.rules.impl.FormatSignature;
import fr.asipsante.api.sign.validation.signature.rules.impl.SignatureNonVide;

/**
 * The Class ProofGeneratorServiceTest.
 */
public class ProofGeneratorFSEServiceTest {

	/** The service. */
	private ISignatureValidationService service = new SignatureValidationServiceImpl();

	/** The params signature. */
	private static SignatureValidationParameters paramsSignature = new SignatureValidationParameters();

	/** ProofGeneraorService */
	private ProofGenerationServiceImpl proofService = new ProofGenerationServiceImpl();

	private static byte[] fse;

	private static byte[] digest;

	private static byte[] signature;

	/**
	 * Test init.
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	@BeforeClass
	public static void init() throws IOException, NoSuchAlgorithmException {

		// lecture de la signature
		File signatureFile = new File("src/test/resources/fichiers/HashSigneCalc_esignsante-apcv.cms");
		FileInputStream fileInputStream = new FileInputStream(signatureFile);
		signature = new byte[(int) signatureFile.length()];
		fileInputStream.read(signature);
		fileInputStream.close();

		// calcul du hash du fichier source
		// hash qui a été signé
		File fseFile = new File("src/test/resources/fichiers/cal.txt");
		FileInputStream fileInputStreamd = new FileInputStream(fseFile);

		fse = new byte[(int) fseFile.length()];
		fileInputStreamd.read(fse);
		fileInputStreamd.close();

		// Calcul de SHA256 du buffer
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(fse);
		digest = md.digest();

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
	public void proofBuilderSignFSETest() throws Exception {
		String digestBase64 = Base64.getEncoder().encodeToString(digest);
		DSSDocument doc = new DigestDocument(DigestAlgorithm.SHA256, digestBase64, "nomDuDoc");

		// validation de la signature
		final RapportValidationSignature rapport = service.validateFSESignature(signature, paramsSignature,
				getCACRLWrapper() /* wrapper */, doc);

		// génération de la preuve
		final String proof = proofService.generateSignVerifProof(rapport,
				new ProofParameters("Sign", "1", "TAG", "FSE", "/signatures/fse", new Version("1.0.0.0")),
				getCACRLWrapper());
		assertFalse("la preuve ne devrait pas être vide", proof.isEmpty());
		final List<MetaDatum> metadata = rapport.getMetaData();
		for (final MetaDatum metaDatum : metadata) {
			assertFalse("Metadonnée vide", metaDatum.getValue().isEmpty());
		}
	}

}
