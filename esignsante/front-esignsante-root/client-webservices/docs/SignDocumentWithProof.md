# SignDocumentWithProof

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**secret** | **String** | Secret |  [optional]
**idSignConf** | **Long** | Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la signature (appel de l&#x27;opération \&quot;/configurations\&quot;). | 
**file** | [**File**](File.md) | Document à signer. | 
**signers** | **List&lt;String&gt;** | Liste des signataires délégataires. L&#x27;IHM swagger ne gère qu&#x27;1 seul signer - pour gérer plusieurs signers, rajouter des paramètres à la requête CURL.&lt;br&gt;Exemple: curl -X POST [...] -F \&quot;signers&#x3D;Dupont\&quot; -F \&quot;signers&#x3D;Dupond\&quot; |  [optional]
**idVerifSignConf** | **Long** | Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la vérification de signature (appel de l&#x27;opération \&quot;/configurations\&quot;). | 
**requestId** | **String** | Identifiant de la demande pour renseigner l&#x27;élément RequestId de la preuve. | 
**proofTag** | **String** | Tag utilisé pour renseigner l&#x27;élément Tag de la preuve. | 
**applicantId** | **String** | Identifiant du demandeur utilisé pour renseigner le champ applicantId de la preuve. |  [optional]
