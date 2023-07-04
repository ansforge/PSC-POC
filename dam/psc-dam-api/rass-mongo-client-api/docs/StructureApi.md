# StructureApi

All URIs are relative to *http://psc-dam-api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getIds**](StructureApi.md#getIds) | **GET** /ids/get/{arrayTechnicalId} | get ids endpoint

<a name="getIds"></a>
# **getIds**
> List&lt;StructureIds&gt; getIds(arrayTechnicalId)

get ids endpoint

Returns a list of StructureIds for the list of TechnicalStructureId (ie TechnicalStructureIds extracted from Prosante Connect UserInfo versus id &#x27;LieuDeTravail&#x27; used on CG database).

### Example
```java
// Import classes:
//import fr.ans.psc.api.client.structure.reader.invoker.ApiException;
//import fr.ans.psc.api.client.structure.reader.StructureApi;


StructureApi apiInstance = new StructureApi();
List<String> arrayTechnicalId = Arrays.asList("arrayTechnicalId_example"); // List<String> | List of Structure Technical ID
try {
    List<StructureIds> result = apiInstance.getIds(arrayTechnicalId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StructureApi#getIds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **arrayTechnicalId** | [**List&lt;String&gt;**](String.md)| List of Structure Technical ID |

### Return type

[**List&lt;StructureIds&gt;**](StructureIds.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

