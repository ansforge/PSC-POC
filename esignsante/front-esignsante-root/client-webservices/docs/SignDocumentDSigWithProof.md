# SignDocumentDSigWithProof

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**secret** | **String** | Secret |  [optional]
**idSignConf** | **Long** | Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la signature (appel de l&#x27;opération \&quot;/configurations\&quot;). | 
**file** | [**File**](File.md) | Document à signer. | 
**idVerifSignConf** | **Long** | Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la vérification de signature (appel de l&#x27;opération \&quot;/configurations\&quot;). | 
**requestId** | **String** | Identifiant de la demande pour renseigner l&#x27;élément RequestId de la preuve. | 
**proofTag** | **String** | Tag utilisé pour renseigner l&#x27;élément Tag de la preuve. | 
**applicantId** | **String** | Identifiant du demandeur utilisé pour renseigner le champ applicantId de la preuve. | 
