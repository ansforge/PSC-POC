# ConfSign

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**idSignConf** | **Long** | Identifiant de configuration. Cet identifiant est utilisé par les services de signature. | 
**associatedProofId** | **Long** | Identifiant de configuration de la preuve de validation associée. | 
**description** | **String** | Description de la configuration de signature. | 
**signaturePackaging** | [**SignaturePackagingEnum**](#SignaturePackagingEnum) |  | 
**digestAlgorithm** | [**DigestAlgorithmEnum**](#DigestAlgorithmEnum) |  | 
**canonicalisationAlgorithm** | [**CanonicalisationAlgorithmEnum**](#CanonicalisationAlgorithmEnum) |  | 
**dn** | **String** | DN du certificat qui sera utilisé pour signer le document | 

<a name="SignaturePackagingEnum"></a>
## Enum: SignaturePackagingEnum
Name | Value
---- | -----
ENVELOPING | &quot;ENVELOPING&quot;
ENVELOPED | &quot;ENVELOPED&quot;

<a name="DigestAlgorithmEnum"></a>
## Enum: DigestAlgorithmEnum
Name | Value
---- | -----
SHA1 | &quot;SHA1&quot;
SHA256 | &quot;SHA256&quot;
SHA512 | &quot;SHA512&quot;

<a name="CanonicalisationAlgorithmEnum"></a>
## Enum: CanonicalisationAlgorithmEnum
Name | Value
---- | -----
TR_2001_REC_XML_C14N_20010315 | &quot;http://www.w3.org/TR/2001/REC-xml-c14n-20010315&quot;
TR_2001_REC_XML_C14N_20010315_WITHCOMMENTS | &quot;http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments&quot;
_2001_10_XML_EXC_C14N_ | &quot;http://www.w3.org/2001/10/xml-exc-c14n#&quot;
_2001_10_XML_EXC_C14N_WITHCOMMENTS | &quot;http://www.w3.org/2001/10/xml-exc-c14n#WithComments&quot;
