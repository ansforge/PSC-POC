# CaApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getCA**](CaApi.md#getCA) | **GET** /ca | Liste des Autorités de Certification de confiance utilisées par la plateforme.

<a name="getCA"></a>
# **getCA**
> List&lt;String&gt; getCA()

Liste des Autorités de Certification de confiance utilisées par la plateforme.

Opération qui permet au client de prendre connaissance des Autorités de Certification de confiance utilisées par la plateforme. 

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.CaApi;


CaApi apiInstance = new CaApi();
try {
    List<String> result = apiInstance.getCA();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CaApi#getCA");
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

