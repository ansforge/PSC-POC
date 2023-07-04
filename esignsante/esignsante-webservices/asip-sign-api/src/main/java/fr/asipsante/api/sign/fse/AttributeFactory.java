package fr.asipsante.api.sign.fse;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERNumericString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.pkcs.Attribute;

public class AttributeFactory {

    public static final String TYPE_FLUX_OID = "1.2.250.1.186.500.1.2";
    public static final String ID_FACTURATION_PS_OID = "1.2.250.1.186.500.1.5";
 
    private AttributeFactory() {}

    public static Attribute idFacturationPSOID(String nir) {
        ASN1ObjectIdentifier idFacturationPSASN1 = new ASN1ObjectIdentifier(ID_FACTURATION_PS_OID);
        return new Attribute(idFacturationPSASN1, new DERSet(new DERIA5String(nir)));
    }

    public static Attribute typeFluxOID(String typeFlux) {
        ASN1ObjectIdentifier typeFluxASN1 = new ASN1ObjectIdentifier(TYPE_FLUX_OID);
        return new Attribute(typeFluxASN1, new DERSet(new DERIA5String(typeFlux)));
    }

}
