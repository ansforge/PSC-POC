package fr.asipsante.api.sign.fse;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSAbsentContent;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import org.bouncycastle.cms.SignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

import fr.asipsante.api.sign.fse.oid.AttributeFactory;

public class SignatureFSE {

    private static final SignatureFSE ourInstance = new SignatureFSE();

    private SignatureFSE() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static SignatureFSE getInstance() {
        return ourInstance;
    }

    /**
     * Signature CMS
     * @param certificate Le certificat.
     * @param privateKey La Clé privée
     * @param attributsCMS Le contenu de la facture à signer
     * @return Signature de l'empreinte.
     * @throws OperatorCreationException
     * @throws CertificateEncodingException
     * @throws IOException
     * @throws CMSException
     * @throws NoSuchAlgorithmException
     */
    public byte[] signer(X509Certificate certificate, PrivateKey privateKey, AttributsCMS attributsCMS) throws OperatorCreationException, CertificateEncodingException, IOException, CMSException, NoSuchAlgorithmException, IllegalArgumentException {

        DefaultSignedAttributeTableGenerator signedAttributeGenerator = defaultSignedAttributeTableGenerator(
                attributsCMS);

        SignerInfoGeneratorBuilder signerInfoBuilder = new SignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build());

        signerInfoBuilder.setSignedAttributeGenerator(signedAttributeGenerator);

        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
        JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("sha256WithRSAEncryption").setProvider(BouncyCastleProvider.PROVIDER_NAME);
        
        ContentSigner contentSigner = contentSignerBuilder.build(privateKey);

        generator.addSignerInfoGenerator(signerInfoBuilder.build(contentSigner, new X509CertificateHolder(certificate.getEncoded())));
       
	    //CMSAbsentContent =>  a class representing null or absent content
        return generator.generate(new CMSAbsentContent(), true).getEncoded();
    }

        
    /**
     * Ajout des attributs.
     * @param attributsCMS les attributs
     * @return les attributs signés.
     */
    private DefaultSignedAttributeTableGenerator defaultSignedAttributeTableGenerator(AttributsCMS attributsCMS) throws NoSuchAlgorithmException {
        // La liste des attributs
        ASN1EncodableVector signedAttributes = new ASN1EncodableVector();

        signedAttributes.add(AttributeFactory.typeFluxOID(attributsCMS.getTypeFlux()));
        signedAttributes.add(AttributeFactory.idFacturationPSOID(attributsCMS.getIdFacturationPS()));
        signedAttributes.add(AttributeFactory.pemPublicKeyOID(attributsCMS.getPemPublicKey()));
      
        signedAttributes.add(new Attribute(PKCSObjectIdentifiers.pkcs_9_at_messageDigest, new DERSet(new DEROctetString(attributsCMS.getHash()))));

        AttributeTable signedAttributesTable = new AttributeTable(signedAttributes);

        /* Construct signed attributes */
        return new DefaultSignedAttributeTableGenerator(signedAttributesTable);
    }

}

