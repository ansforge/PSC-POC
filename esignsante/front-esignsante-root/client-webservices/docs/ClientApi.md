# ClientApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**generateSecureSecretHash**](ClientApi.md#generateSecureSecretHash) | **POST** /secrets | Generation d&#x27;un Hash sécurisé à partir du secret
[**getOperations**](ClientApi.md#getOperations) | **GET** / | Liste des opérations disponibles.

<a name="generateSecureSecretHash"></a>
# **generateSecureSecretHash**
> HashedSecret generateSecureSecretHash(body)

Generation d&#x27;un Hash sécurisé à partir du secret

L&#x27;opération permet au client de générer un Hash à partir du secret. 

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.ClientApi;


ClientApi apiInstance = new ClientApi();
Secret body = new Secret(); // Secret | 
try {
    HashedSecret result = apiInstance.generateSecureSecretHash(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#generateSecureSecretHash");
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

<a name="getOperations"></a>
# **getOperations**
> List&lt;String&gt; getOperations()

Liste des opérations disponibles.

Opération qui permet au client de lister les opérations offertes par le composant ANS Sign.               &lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.ClientApi;


ClientApi apiInstance = new ClientApi();
try {
    List<String> result = apiInstance.getOperations();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientApi#getOperations");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

