# CertificatsApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**verifCertificat**](CertificatsApi.md#verifCertificat) | **POST** /validation/certificats | Vérification d&#x27;un certificat au format DER ou PEM.
[**verifCertificatWithProof**](CertificatsApi.md#verifCertificatWithProof) | **POST** /validation/certificatswithproof | Vérification de certificat au format DER ou PEM avec preuve.

<a name="verifCertificat"></a>
# **verifCertificat**
> ESignSanteValidationReport verifCertificat(idVerifCertConf, file)

Vérification d&#x27;un certificat au format DER ou PEM.

L&#x27;opération permet au client de vérifier la validité d&#x27;un certificat. &lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas expiré;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas révoqué;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la signature du certificat est valide;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue.&lt;br&gt;&lt;br&gt; Le client peut également demander l&#x27;extraction des métadonnées suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.CertificatsApi;


CertificatsApi apiInstance = new CertificatsApi();
Long idVerifCertConf = 789L; // Long | 
File file = new File("file_example"); // File | 
try {
    ESignSanteValidationReport result = apiInstance.verifCertificat(idVerifCertConf, file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CertificatsApi#verifCertificat");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idVerifCertConf** | **Long**|  |
 **file** | **File**|  |

### Return type

[**ESignSanteValidationReport**](ESignSanteValidationReport.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="verifCertificatWithProof"></a>
# **verifCertificatWithProof**
> ESignSanteValidationReportWithProof verifCertificatWithProof(idVerifCertConf, file, requestId, proofTag, applicantId, idProofConf)

Vérification de certificat au format DER ou PEM avec preuve.

L&#x27;opération permet au client de vérifier la validité d&#x27;un certificat ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Le certificat n&#x27;est pas expiré;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas révoqué;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la signature du certificat est valide;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue.&lt;br&gt; Le client peut également demander l&#x27;extraction des métadonnées suivantes:&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.CertificatsApi;


CertificatsApi apiInstance = new CertificatsApi();
Long idVerifCertConf = 789L; // Long | 
File file = new File("file_example"); // File | 
String requestId = "requestId_example"; // String | 
String proofTag = "proofTag_example"; // String | 
String applicantId = "applicantId_example"; // String | 
Long idProofConf = 789L; // Long | 
try {
    ESignSanteValidationReportWithProof result = apiInstance.verifCertificatWithProof(idVerifCertConf, file, requestId, proofTag, applicantId, idProofConf);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CertificatsApi#verifCertificatWithProof");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **idVerifCertConf** | **Long**|  |
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

