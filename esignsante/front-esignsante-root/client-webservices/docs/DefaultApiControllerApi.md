# DefaultApiControllerApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getOperations**](DefaultApiControllerApi.md#getOperations) | **GET** / | Liste des opérations disponibles.

<a name="getOperations"></a>
# **getOperations**
> List&lt;String&gt; getOperations()

Liste des opérations disponibles.

Opération qui permet au client de lister les opérations offertes par le composant ANS Sign.               &lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.DefaultApiControllerApi;


DefaultApiControllerApi apiInstance = new DefaultApiControllerApi();
try {
    List<String> result = apiInstance.getOperations();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApiControllerApi#getOperations");
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

