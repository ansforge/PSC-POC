# ConfProof

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**idProofConf** | **Long** | Identifiant de configuration. Cet identifiant est utilisé par le service de génération de preuve. | 
**description** | **String** | Description de la configuration de la génération de la preuve. | 
**signaturePackagingForProof** | [**SignaturePackagingForProofEnum**](#SignaturePackagingForProofEnum) |  | 
**digestAlgorithmForProof** | [**DigestAlgorithmForProofEnum**](#DigestAlgorithmForProofEnum) |  | 
**canonicalisationAlgorithmForProof** | [**CanonicalisationAlgorithmForProofEnum**](#CanonicalisationAlgorithmForProofEnum) |  | 
**dn** | **String** | DN du certificat qui sera utilisé pour signer la preuve. | 

<a name="SignaturePackagingForProofEnum"></a>
## Enum: SignaturePackagingForProofEnum
Name | Value
---- | -----
ENVELOPING | &quot;ENVELOPING&quot;
ENVELOPED | &quot;ENVELOPED&quot;

<a name="DigestAlgorithmForProofEnum"></a>
## Enum: DigestAlgorithmForProofEnum
Name | Value
---- | -----
SHA1 | &quot;SHA1&quot;
SHA256 | &quot;SHA256&quot;
SHA512 | &quot;SHA512&quot;

<a name="CanonicalisationAlgorithmForProofEnum"></a>
## Enum: CanonicalisationAlgorithmForProofEnum
Name | Value
---- | -----
TR_2001_REC_XML_C14N_20010315 | &quot;http://www.w3.org/TR/2001/REC-xml-c14n-20010315&quot;
TR_2001_REC_XML_C14N_20010315_WITHCOMMENTS | &quot;http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments&quot;
_2001_10_XML_EXC_C14N_ | &quot;http://www.w3.org/2001/10/xml-exc-c14n#&quot;
_2001_10_XML_EXC_C14N_WITHCOMMENTS | &quot;http://www.w3.org/2001/10/xml-exc-c14n#WithComments&quot;
