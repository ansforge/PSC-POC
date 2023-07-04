# ValidationApiControllerApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**verifCertificat**](ValidationApiControllerApi.md#verifCertificat) | **POST** /validation/certificats | Vérification d&#x27;un certificat au format DER ou PEM.
[**verifCertificatWithProof**](ValidationApiControllerApi.md#verifCertificatWithProof) | **POST** /validation/certificatswithproof | Vérification de certificat au format DER ou PEM avec preuve.
[**verifSignaturePades**](ValidationApiControllerApi.md#verifSignaturePades) | **POST** /validation/signatures/padesbaselineb | Vérification de signature au format Pades Baseline B.
[**verifSignaturePadesWithProof**](ValidationApiControllerApi.md#verifSignaturePadesWithProof) | **POST** /validation/signatures/padesbaselinebwithproof | Vérification de signature au format Pades Baseline B avec preuve.
[**verifSignatureXMLdsig**](ValidationApiControllerApi.md#verifSignatureXMLdsig) | **POST** /validation/signatures/xmldsig | Vérification de signature au format XMLdsig.
[**verifSignatureXMLdsigWithProof**](ValidationApiControllerApi.md#verifSignatureXMLdsigWithProof) | **POST** /validation/signatures/xmldsigwithproof | Vérification de signature au format XMLdsig avec preuve
[**verifSignatureXades**](ValidationApiControllerApi.md#verifSignatureXades) | **POST** /validation/signatures/xadesbaselineb | Vérification de signature au format Xades Baseline B.
[**verifSignatureXadesWithProof**](ValidationApiControllerApi.md#verifSignatureXadesWithProof) | **POST** /validation/signatures/xadesbaselinebwithproof | Vérification de signature au format Xades Baseline B avec preuve.

<a name="verifCertificat"></a>
# **verifCertificat**
> ESignSanteValidationReport verifCertificat(idVerifCertConf, file)

Vérification d&#x27;un certificat au format DER ou PEM.

L&#x27;opération permet au client de vérifier la validité d&#x27;un certificat. &lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas expiré;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat n&#x27;est pas révoqué;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la signature du certificat est valide;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue.&lt;br&gt;&lt;br&gt; Le client peut également demander l&#x27;extraction des métadonnées suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.ValidationApiControllerApi;


ValidationApiControllerApi apiInstance = new ValidationApiControllerApi();
Long idVerifCertConf = 789L; // Long | 
File file = new File("file_example"); // File | 
try {
    ESignSanteValidationReport result = apiInstance.verifCertificat(idVerifCertConf, file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ValidationApiControllerApi#verifCertificat");
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
//import fr.ans.esignsante.api.ValidationApiControllerApi;


ValidationApiControllerApi apiInstance = new ValidationApiControllerApi();
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
    System.err.println("Exception when calling ValidationApiControllerApi#verifCertificatWithProof");
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

<a name="verifSignaturePades"></a>
# **verifSignaturePades**
> ESignSanteValidationReport verifSignaturePades(idVerifSignConf, file)

Vérification de signature au format Pades Baseline B.

L&#x27;opération permet au client de vérifier une signature au format Pades Baseline B. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.ValidationApiControllerApi;


ValidationApiControllerApi apiInstance = new ValidationApiControllerApi();
Long idVerifSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
try {
    ESignSanteValidationReport result = apiInstance.verifSignaturePades(idVerifSignConf, file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ValidationApiControllerApi#verifSignaturePades");
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

<a name="verifSignaturePadesWithProof"></a>
# **verifSignaturePadesWithProof**
> ESignSanteValidationReportWithProof verifSignaturePadesWithProof(idVerifSignConf, file, requestId, proofTag, applicantId, idProofConf)

Vérification de signature au format Pades Baseline B avec preuve.

L&#x27;opération permet au client de vérifier une signature au format Pades Baseline B ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;   &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.ValidationApiControllerApi;


ValidationApiControllerApi apiInstance = new ValidationApiControllerApi();
Long idVerifSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
String requestId = "requestId_example"; // String | 
String proofTag = "proofTag_example"; // String | 
String applicantId = "applicantId_example"; // String | 
Long idProofConf = 789L; // Long | 
try {
    ESignSanteValidationReportWithProof result = apiInstance.verifSignaturePadesWithProof(idVerifSignConf, file, requestId, proofTag, applicantId, idProofConf);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ValidationApiControllerApi#verifSignaturePadesWithProof");
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

<a name="verifSignatureXMLdsig"></a>
# **verifSignatureXMLdsig**
> ESignSanteValidationReport verifSignatureXMLdsig(idVerifSignConf, file)

Vérification de signature au format XMLdsig.

L&#x27;opération permet au client de vérifier une signature au format XMLDsig-core-1. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.ValidationApiControllerApi;


ValidationApiControllerApi apiInstance = new ValidationApiControllerApi();
Long idVerifSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
try {
    ESignSanteValidationReport result = apiInstance.verifSignatureXMLdsig(idVerifSignConf, file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ValidationApiControllerApi#verifSignatureXMLdsig");
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
//import fr.ans.esignsante.api.ValidationApiControllerApi;


ValidationApiControllerApi apiInstance = new ValidationApiControllerApi();
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
    System.err.println("Exception when calling ValidationApiControllerApi#verifSignatureXMLdsigWithProof");
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

<a name="verifSignatureXades"></a>
# **verifSignatureXades**
> ESignSanteValidationReport verifSignatureXades(idVerifSignConf, file)

Vérification de signature au format Xades Baseline B.

L&#x27;opération permet au client de vérifier une signature au format Xades Baseline B. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;Présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.ValidationApiControllerApi;


ValidationApiControllerApi apiInstance = new ValidationApiControllerApi();
Long idVerifSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
try {
    ESignSanteValidationReport result = apiInstance.verifSignatureXades(idVerifSignConf, file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ValidationApiControllerApi#verifSignatureXades");
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

<a name="verifSignatureXadesWithProof"></a>
# **verifSignatureXadesWithProof**
> ESignSanteValidationReportWithProof verifSignatureXadesWithProof(idVerifSignConf, file, requestId, proofTag, applicantId, idProofConf)

Vérification de signature au format Xades Baseline B avec preuve.

L&#x27;opération permet au client de vérifier une signature au format Xades Baseline B ainsi que de générer une preuve de vérification. &lt;br&gt;  Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt;   &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.ValidationApiControllerApi;


ValidationApiControllerApi apiInstance = new ValidationApiControllerApi();
Long idVerifSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
String requestId = "requestId_example"; // String | 
String proofTag = "proofTag_example"; // String | 
String applicantId = "applicantId_example"; // String | 
Long idProofConf = 789L; // Long | 
try {
    ESignSanteValidationReportWithProof result = apiInstance.verifSignatureXadesWithProof(idVerifSignConf, file, requestId, proofTag, applicantId, idProofConf);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ValidationApiControllerApi#verifSignatureXadesWithProof");
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

