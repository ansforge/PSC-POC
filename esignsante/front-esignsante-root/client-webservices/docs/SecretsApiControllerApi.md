# SecretsApiControllerApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**generateSecureSecretHash**](SecretsApiControllerApi.md#generateSecureSecretHash) | **POST** /secrets | Generation d&#x27;un Hash sécurisé à partir du secret

<a name="generateSecureSecretHash"></a>
# **generateSecureSecretHash**
> HashedSecret generateSecureSecretHash(body)

Generation d&#x27;un Hash sécurisé à partir du secret

L&#x27;opération permet au client de générer un Hash à partir du secret. 

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.SecretsApiControllerApi;


SecretsApiControllerApi apiInstance = new SecretsApiControllerApi();
Secret body = new Secret(); // Secret | 
try {
    HashedSecret result = apiInstance.generateSecureSecretHash(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SecretsApiControllerApi#generateSecureSecretHash");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Secret**](Secret.md)|  |

### Return type

[**HashedSecret**](HashedSecret.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

