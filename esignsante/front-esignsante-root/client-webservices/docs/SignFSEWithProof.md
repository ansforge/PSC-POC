# SignFSEWithProof

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**secret** | **String** | Secret | 
**idSignConf** | **Long** | Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la signature (appel de l&#x27;opération \&quot;/configurations\&quot;). | 
**hash** | **String** | hash à signer. | 
**idFacturationPS** | **String** | identifiant de facturation du personnel de Sante à l&#x27;origine de la demande de signature | 
**typeFlux** | **String** | type de flux (valeur T ou R) | 
**signers** | **List&lt;String&gt;** | Liste des signataires délégataires. L&#x27;IHM swagger ne gère qu&#x27;1 seul signer - pour gérer plusieurs signers, rajouter des paramètres à la requête CURL.&lt;br&gt;Exemple: curl -X POST [...] -F \&quot;signers&#x3D;Dupont\&quot; -F \&quot;signers&#x3D;Dupond\&quot; |  [optional]
**idVerifSignConf** | **Long** | Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la vérification de signature (appel de l&#x27;opération \&quot;/configurations\&quot;). | 
**requestId** | **String** | Identifiant de la demande pour renseigner l&#x27;élément RequestId de la preuve. |  [optional]
**proofTag** | **String** | Tag utilisé pour renseigner l&#x27;élément Tag de la preuve. |  [optional]
**applicantId** | **String** | Identifiant xxx du demandeur utilisé pour renseigner le champ applicantId de la preuve. |  [optional]
