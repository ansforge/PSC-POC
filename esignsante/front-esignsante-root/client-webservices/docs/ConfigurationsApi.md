# ConfigurationsApi

All URIs are relative to *https://esignsante.asipsante.fr/{basePath}*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getConfigurations**](ConfigurationsApi.md#getConfigurations) | **GET** /configurations | Liste des configurations disponibles.

<a name="getConfigurations"></a>
# **getConfigurations**
> Conf getConfigurations()

Liste des configurations disponibles.

Opération qui permet au client de récupérer les configurations disponibles afin qu&#x27;il puisse utiliser la configuration qui correspond à l&#x27;usage souhaité.   &lt;br&gt;

### Example
```java
// Import classes:
//import fr.ans.esignsante.ApiException;
//import fr.ans.esignsante.api.ConfigurationsApi;


ConfigurationsApi apiInstance = new ConfigurationsApi();
try {
    Conf result = apiInstance.getConfigurations();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ConfigurationsApi#getConfigurations");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Conf**](Conf.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

