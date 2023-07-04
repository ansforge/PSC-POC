# SignaturesApiControllerApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**signatureFSEWithProof**](SignaturesApiControllerApi.md#signatureFSEWithProof) | **POST** /signatures/fseWithProof | Signature du hash d&#x27;une feuille de soin électronique avec preuve.
[**signaturePades**](SignaturesApiControllerApi.md#signaturePades) | **POST** /signatures/padesbaselineb | Signature d&#x27;un document au format Pades Baseline B.
[**signaturePadesWithProof**](SignaturesApiControllerApi.md#signaturePadesWithProof) | **POST** /signatures/padesbaselinebwithproof | Signature d&#x27;un document PDF au format Pades Baseline B avec preuve.
[**signatureXMLdsig**](SignaturesApiControllerApi.md#signatureXMLdsig) | **POST** /signatures/xmldsig | Signature d&#x27;un document au format XMLdsig
[**signatureXMLdsigWithProof**](SignaturesApiControllerApi.md#signatureXMLdsigWithProof) | **POST** /signatures/xmldsigwithproof | Signature d&#x27;un document au format XMLdsig avec preuve
[**signatureXades**](SignaturesApiControllerApi.md#signatureXades) | **POST** /signatures/xadesbaselineb | Signature d&#x27;un document au format Xades Baseline B.
[**signatureXadesWithProof**](SignaturesApiControllerApi.md#signatureXadesWithProof) | **POST** /signatures/xadesbaselinebwithproof | Signature d&#x27;un document au format Xades Baseline B avec preuve.

<a name="signatureFSEWithProof"></a>
# **signatureFSEWithProof**
> ESignSanteSignatureReportWithProof signatureFSEWithProof(secret, idSignConf, hash, idFacturationPS, typeFlux, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken)

Signature du hash d&#x27;une feuille de soin électronique avec preuve.

L&#x27;opération permet au client de signer le hash d&#x27;un document (non transmis), par exemple le hash d&#x27;une feuille de soin électronique, en pkcs7 détaché ainsi que de générer une preuve de vérification de signature.

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.SignaturesApiControllerApi;


SignaturesApiControllerApi apiInstance = new SignaturesApiControllerApi();
String secret = "secret_example"; // String | 
Long idSignConf = 789L; // Long | 
String hash = "hash_example"; // String | 
String idFacturationPS = "idFacturationPS_example"; // String | 
String typeFlux = "typeFlux_example"; // String | 
List<String> signers = Arrays.asList("signers_example"); // List<String> | 
Long idVerifSignConf = 789L; // Long | 
String requestId = "requestId_example"; // String | 
String proofTag = "proofTag_example"; // String | 
String applicantId = "applicantId_example"; // String | 
List<OpenidToken> xOpenidToken = Arrays.asList(new OpenidToken()); // List<OpenidToken> | Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.<br>Exemple: curl -X POST [...] -H \"accept: application/json\" -H 'openidTokens: {\"introspectionResponse\":\"xxx\",\"userInfo\":\"xxxx\",\"accessToken\":\"xxxxx\"}' -H 'openidTokens: {\"introspectionResponse\":\"xxxxxx\",\"userInfo\":\"xxxxxxx\",\"accessToken\":\"xxxxxxxxx\"}' -H \"Content-Type: multipart/form-data\" [...]
try {
    ESignSanteSignatureReportWithProof result = apiInstance.signatureFSEWithProof(secret, idSignConf, hash, idFacturationPS, typeFlux, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SignaturesApiControllerApi#signatureFSEWithProof");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **secret** | **String**|  | [optional]
 **idSignConf** | **Long**|  | [optional]
 **hash** | **String**|  | [optional]
 **idFacturationPS** | **String**|  | [optional]
 **typeFlux** | **String**|  | [optional]
 **signers** | [**List&lt;String&gt;**](String.md)|  | [optional]
 **idVerifSignConf** | **Long**|  | [optional]
 **requestId** | **String**|  | [optional]
 **proofTag** | **String**|  | [optional]
 **applicantId** | **String**|  | [optional]
 **xOpenidToken** | [**List&lt;OpenidToken&gt;**](OpenidToken.md)| Un openidToken doit être passé au format JSON encodé en base 64. Pour passer plusieurs tokens, il faut utiliser plusieurs paramètres de header. Attention aux simples et doubles guillemets lors du passage de paramètres JSON.&lt;br&gt;Exemple: curl -X POST [...] -H \&quot;accept: application/json\&quot; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxx\&quot;}&#x27; -H &#x27;openidTokens: {\&quot;introspectionResponse\&quot;:\&quot;xxxxxx\&quot;,\&quot;userInfo\&quot;:\&quot;xxxxxxx\&quot;,\&quot;accessToken\&quot;:\&quot;xxxxxxxxx\&quot;}&#x27; -H \&quot;Content-Type: multipart/form-data\&quot; [...] | [optional]

### Return type

[**ESignSanteSignatureReportWithProof**](ESignSanteSignatureReportWithProof.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="signaturePades"></a>
# **signaturePades**
> ESignSanteSignatureReport signaturePades(secret, idSignConf, file, signers)

Signature d&#x27;un document au format Pades Baseline B.

L&#x27;opération permet au client de signer un document PDF au format PADES Baseline B. 

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.SignaturesApiControllerApi;


SignaturesApiControllerApi apiInstance = new SignaturesApiControllerApi();
String secret = "secret_example"; // String | 
Long idSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
List<String> signers = Arrays.asList("signers_example"); // List<String> | 
try {
    ESignSanteSignatureReport result = apiInstance.signaturePades(secret, idSignConf, file, signers);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SignaturesApiControllerApi#signaturePades");
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
//import fr.ans.esignsante.api.SignaturesApiControllerApi;


SignaturesApiControllerApi apiInstance = new SignaturesApiControllerApi();
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
    System.err.println("Exception when calling SignaturesApiControllerApi#signaturePadesWithProof");
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

<a name="signatureXMLdsig"></a>
# **signatureXMLdsig**
> ESignSanteSignatureReport signatureXMLdsig(secret, idSignConf, file)

Signature d&#x27;un document au format XMLdsig

L&#x27;opération permet au client de signer un document au format XMLDsig-core-1. 

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.SignaturesApiControllerApi;


SignaturesApiControllerApi apiInstance = new SignaturesApiControllerApi();
String secret = "secret_example"; // String | 
Long idSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
try {
    ESignSanteSignatureReport result = apiInstance.signatureXMLdsig(secret, idSignConf, file);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SignaturesApiControllerApi#signatureXMLdsig");
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
//import fr.ans.esignsante.api.SignaturesApiControllerApi;


SignaturesApiControllerApi apiInstance = new SignaturesApiControllerApi();
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
    System.err.println("Exception when calling SignaturesApiControllerApi#signatureXMLdsigWithProof");
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

<a name="signatureXades"></a>
# **signatureXades**
> ESignSanteSignatureReport signatureXades(secret, idSignConf, file, signers)

Signature d&#x27;un document au format Xades Baseline B.

L&#x27;opération permet au client de signer un document au format XADES Baseline B. 

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.SignaturesApiControllerApi;


SignaturesApiControllerApi apiInstance = new SignaturesApiControllerApi();
String secret = "secret_example"; // String | 
Long idSignConf = 789L; // Long | 
File file = new File("file_example"); // File | 
List<String> signers = Arrays.asList("signers_example"); // List<String> | 
try {
    ESignSanteSignatureReport result = apiInstance.signatureXades(secret, idSignConf, file, signers);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SignaturesApiControllerApi#signatureXades");
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

<a name="signatureXadesWithProof"></a>
# **signatureXadesWithProof**
> ESignSanteSignatureReportWithProof signatureXadesWithProof(secret, idSignConf, file, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken)

Signature d&#x27;un document au format Xades Baseline B avec preuve.

L&#x27;opération permet au client de signer un document au format XADES Baseline B ainsi que de générer une preuve de vérification de signature.&lt;br&gt; Le client peut demander la vérification des règles applicables suivantes:&lt;br&gt;  &amp;nbsp;&amp;nbsp;&amp;nbsp;validité de la signature du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence de la balise \&quot;SigningTime\&quot; dans la signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp; présence du certificat utilisé dans la signature; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas expiré; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;usage de la clé du certificat correspond à un usage de signature électronique et de non répudiation;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le certificat de signature n&#x27;est pas révoqué; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;présence d&#x27;une signature qui n&#x27;est pas vide; &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;l&#x27;Autorité de Certification est reconnue;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le contenu de la signature est valide;&lt;br&gt;&lt;br&gt;  Le client peut également demander l&#x27;extraction des métadonnées suivantes: &lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le DN du certificat;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;la date de signature;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;le rapport du diagnostique;&lt;br&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;le document original non signé.&lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.SignaturesApiControllerApi;


SignaturesApiControllerApi apiInstance = new SignaturesApiControllerApi();
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
    ESignSanteSignatureReportWithProof result = apiInstance.signatureXadesWithProof(secret, idSignConf, file, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SignaturesApiControllerApi#signatureXadesWithProof");
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

