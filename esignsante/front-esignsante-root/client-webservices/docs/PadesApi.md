# PadesApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**signaturePades**](PadesApi.md#signaturePades) | **POST** /signatures/padesbaselineb | Signature d&#x27;un document au format Pades Baseline B.
[**signaturePadesWithProof**](PadesApi.md#signaturePadesWithProof) | **POST** /signatures/padesbaselinebwithproof | Signature d&#x27;un document PDF au format Pades Baseline B avec preuve.
[**verifSignaturePades**](PadesApi.md#verifSignaturePades) | **POST** /validation/signatures/padesbaselineb | Vérification de signature au format Pades Baseline B.
[**verifSignaturePadesWithProof**](PadesApi.md#verifSignaturePadesWithProof) | **POST** /validation/signatures/padesbaselinebwithproof | Vérification de signature au format Pades Baseline B avec preuve.

<a name="signaturePades"></a>
# **signaturePades**
> ESignSanteSignatureReport signaturePades(secret, idSignConf, file, signers)

Signature d&#x27;un document au format Pades Baseline B.

L&#x27;opération permet au client de signer un document PDF au format PADES Baseline B. 

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.PadesApi;


PadesApi apiInstance = new PadesApi();
String secret = "secret_example"; // String | 
Long idSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
List<String> signers = Arrays.asList("signers_example"); // List<String> | 
try {
    ESignSanteSignatureReport result = apiInstance.signaturePades(secret, idSignConf, file, signers);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PadesApi#signaturePades");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **secret** | **String**|  |
 **idSignConf** | **Long**|  |
 **file** | **File**|  |
 **signers** | [**List&lt;String&gt;**](String.md)|  |

### Return type

[**ESignSanteSignatureReport**](ESignSanteSignatureReport.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="signaturePadesWithProof"></a>
# **signaturePadesWithProof**
> ESignSanteSignatureReportWithProof signaturePadesWithProof(secret, idSignConf, file, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken)

Signature d&#x27;un document PDF au format Pades Baseline B avec preuve.

L&#x27;opération permet au client de signer un document au format PADES Baseline B ainsi que de générer une preuve de vérification de signature.&lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp; présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.PadesApi;


PadesApi apiInstance = new PadesApi();
String secret = "secret_example"; // String | 
Long idSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
List<String> signers = Arrays.asList("signers_example"); // List<String> | 
Long idVerifSignConf = 789L; // Long | 
String requestId = "requestId_example"; // String | 
String proofTag = "proofTag_example"; // String | 
String applicantId = "applicantId_example"; // String | 
List<OpenidToken> xOpenidToken = Arrays.asList(new OpenidToken()); // List<OpenidToken> | Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.<br>Exemple: curl -X POST [...] -H \"accept: application/json\" -H 'openidTokens: {\"introspectionResponse\":\"xxx\",\"userInfo\":\"xxxx\",\"accessToken\":\"xxxxx\"}' -H 'openidTokens: {\"introspectionResponse\":\"xxxxxx\",\"userInfo\":\"xxxxxxx\",\"accessToken\":\"xxxxxxxxx\"}' -H \"Content-Type: multipart/form-data\" [...]
try {
    ESignSanteSignatureReportWithProof result = apiInstance.signaturePadesWithProof(secret, idSignConf, file, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PadesApi#signaturePadesWithProof");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **secret** | **String**|  |
 **idSignConf** | **Long**|  |
 **file** | **File**|  |
 **signers** | [**List&lt;String&gt;**](String.md)|  |
 **idVerifSignConf** | **Long**|  |
 **requestId** | **String**|  |
 **proofTag** | **String**|  |
 **applicantId** | **String**|  |
 **xOpenidToken** | [**List&lt;OpenidToken&gt;**](OpenidToken.md)| Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.&lt;br&gt;Exemple: curl -X POST [...] -H \&quot;accept: application/json\&quot; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxx\&quot;}&#x27; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxxxxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxxxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxxxxxx\&quot;}&#x27; -H \&quot;Content-Type: multipart/form-data\&quot; [...] | [optional]

### Return type

[**ESignSanteSignatureReportWithProof**](ESignSanteSignatureReportWithProof.md)

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
//import fr.ans.esignsante.api.PadesApi;


PadesApi apiInstance = new PadesApi();
Long idVerifSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
try {
    ESignSanteValidationReport result = apiInstance.verifSignaturePades(idVerifSignConf, file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PadesApi#verifSignaturePades");
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
//import fr.ans.esignsante.api.PadesApi;


PadesApi apiInstance = new PadesApi();
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
    System.err.println("Exception when calling PadesApi#verifSignaturePadesWithProof");
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

