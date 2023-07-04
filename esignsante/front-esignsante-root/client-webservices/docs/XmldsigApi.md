# XmldsigApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**signatureXMLdsig**](XmldsigApi.md#signatureXMLdsig) | **POST** /signatures/xmldsig | Signature d&#x27;un document au format XMLdsig
[**signatureXMLdsigWithProof**](XmldsigApi.md#signatureXMLdsigWithProof) | **POST** /signatures/xmldsigwithproof | Signature d&#x27;un document au format XMLdsig avec preuve
[**verifSignatureXMLdsig**](XmldsigApi.md#verifSignatureXMLdsig) | **POST** /validation/signatures/xmldsig | Vérification de signature au format XMLdsig.
[**verifSignatureXMLdsigWithProof**](XmldsigApi.md#verifSignatureXMLdsigWithProof) | **POST** /validation/signatures/xmldsigwithproof | Vérification de signature au format XMLdsig avec preuve

<a name="signatureXMLdsig"></a>
# **signatureXMLdsig**
> ESignSanteSignatureReport signatureXMLdsig(secret, idSignConf, file)

Signature d&#x27;un document au format XMLdsig

L&#x27;opération permet au client de signer un document au format XMLDsig-core-1. 

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.XmldsigApi;


XmldsigApi apiInstance = new XmldsigApi();
String secret = "secret_example"; // String | 
Long idSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
try {
    ESignSanteSignatureReport result = apiInstance.signatureXMLdsig(secret, idSignConf, file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling XmldsigApi#signatureXMLdsig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **secret** | **String**|  |
 **idSignConf** | **Long**|  |
 **file** | **File**|  |

### Return type

[**ESignSanteSignatureReport**](ESignSanteSignatureReport.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="signatureXMLdsigWithProof"></a>
# **signatureXMLdsigWithProof**
> ESignSanteSignatureReportWithProof signatureXMLdsigWithProof(secret, idSignConf, file, idVerifSignConf, requestId, proofTag, applicantId)

Signature d&#x27;un document au format XMLdsig avec preuve

L&#x27;opération permet au client de signer un document au format XMLDsig-core-1 ainsi que de générer une preuve de vérification de signature. &lt;br&gt;Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;    &amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;   &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.XmldsigApi;


XmldsigApi apiInstance = new XmldsigApi();
String secret = "secret_example"; // String | 
Long idSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
Long idVerifSignConf = 789L; // Long | 
String requestId = "requestId_example"; // String | 
String proofTag = "proofTag_example"; // String | 
String applicantId = "applicantId_example"; // String | 
try {
    ESignSanteSignatureReportWithProof result = apiInstance.signatureXMLdsigWithProof(secret, idSignConf, file, idVerifSignConf, requestId, proofTag, applicantId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling XmldsigApi#signatureXMLdsigWithProof");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **secret** | **String**|  |
 **idSignConf** | **Long**|  |
 **file** | **File**|  |
 **idVerifSignConf** | **Long**|  |
 **requestId** | **String**|  |
 **proofTag** | **String**|  |
 **applicantId** | **String**|  |

### Return type

[**ESignSanteSignatureReportWithProof**](ESignSanteSignatureReportWithProof.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="verifSignatureXMLdsig"></a>
# **verifSignatureXMLdsig**
> ESignSanteValidationReport verifSignatureXMLdsig(idVerifSignConf, file)

Vérification de signature au format XMLdsig.

L&#x27;opération permet au client de vérifier une signature au format XMLDsig-core-1. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.XmldsigApi;


XmldsigApi apiInstance = new XmldsigApi();
Long idVerifSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
try {
    ESignSanteValidationReport result = apiInstance.verifSignatureXMLdsig(idVerifSignConf, file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling XmldsigApi#verifSignatureXMLdsig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idVerifSignConf** | **Long**|  |
 **file** | **File**|  |

### Return type

[**ESignSanteValidationReport**](ESignSanteValidationReport.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="verifSignatureXMLdsigWithProof"></a>
# **verifSignatureXMLdsigWithProof**
> ESignSanteValidationReportWithProof verifSignatureXMLdsigWithProof(idVerifSignConf, file, requestId, proofTag, applicantId, idProofConf)

Vérification de signature au format XMLdsig avec preuve

L&#x27;opération permet au client de vérifier une signature au format XMLDsig-core-1 ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.XmldsigApi;


XmldsigApi apiInstance = new XmldsigApi();
Long idVerifSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
String requestId = "requestId_example"; // String | 
String proofTag = "proofTag_example"; // String | 
String applicantId = "applicantId_example"; // String | 
Long idProofConf = 789L; // Long | 
try {
    ESignSanteValidationReportWithProof result = apiInstance.verifSignatureXMLdsigWithProof(idVerifSignConf, file, requestId, proofTag, applicantId, idProofConf);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling XmldsigApi#verifSignatureXMLdsigWithProof");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idVerifSignConf** | **Long**|  |
 **file** | **File**|  |
 **requestId** | **String**|  |
 **proofTag** | **String**|  |
 **applicantId** | **String**|  |
 **idProofConf** | **Long**|  |

### Return type

[**ESignSanteValidationReportWithProof**](ESignSanteValidationReportWithProof.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

