# FseApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**signatureFSEWithProof**](FseApi.md#signatureFSEWithProof) | **POST** /signatures/fseWithProof | Signature du hash d&#x27;une feuille de soin électronique avec preuve.

<a name="signatureFSEWithProof"></a>
# **signatureFSEWithProof**
> ESignSanteSignatureReportWithProof signatureFSEWithProof(secret, idSignConf, hash, idFacturationPS, typeFlux, signers, idVerifSignConf, requestId, proofTag, applicantId, xOpenidToken)

Signature du hash d&#x27;une feuille de soin électronique avec preuve.

L&#x27;opération permet au client de signer le hash d&#x27;un document (non transmis), par exemple le hash d&#x27;une feuille de soin électronique, en pkcs7 détaché ainsi que de générer une preuve de vérification de signature.

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.FseApi;


FseApi apiInstance = new FseApi();
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
    System.err.println("Exception when calling FseApi#signatureFSEWithProof");
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

