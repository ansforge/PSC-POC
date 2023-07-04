/*
 * eSignSante
 * API du composant eSignSante. <br>Ce composant dit de \"signature\" mutualise et homogénéise la mise en oeuvre des besoins autour de la signature. <br>Il permet de signer des documents ainsi que de vérifier la validité d'une signature ou d'un certificat.    <br>
 *
 * OpenAPI spec version: 2.5.0.11
 * Contact: esignsante@asipsante.fr
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package fr.ans.esignsante.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.File;
import java.io.Serializable;
/**
 * VerifSignDocumentWithProof
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")
public class VerifSignDocumentWithProof implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("idVerifSignConf")
  private Long idVerifSignConf = null;

  @JsonProperty("file")
  private File file = null;

  @JsonProperty("requestId")
  private String requestId = null;

  @JsonProperty("proofTag")
  private String proofTag = null;

  @JsonProperty("applicantId")
  private String applicantId = null;

  @JsonProperty("idProofConf")
  private Long idProofConf = null;

  public VerifSignDocumentWithProof idVerifSignConf(Long idVerifSignConf) {
    this.idVerifSignConf = idVerifSignConf;
    return this;
  }

   /**
   * Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la vérification de signature (appel de l&#x27;opération \&quot;/configurations\&quot;).
   * @return idVerifSignConf
  **/
  @Schema(required = true, description = "Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la vérification de signature (appel de l'opération \"/configurations\").")
  public Long getIdVerifSignConf() {
    return idVerifSignConf;
  }

  public void setIdVerifSignConf(Long idVerifSignConf) {
    this.idVerifSignConf = idVerifSignConf;
  }

  public VerifSignDocumentWithProof file(File file) {
    this.file = file;
    return this;
  }

   /**
   * Document à vérifier.
   * @return file
  **/
  @Schema(required = true, description = "Document à vérifier.")
  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public VerifSignDocumentWithProof requestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

   /**
   * Identifiant de la demande pour renseigner l&#x27;élément RequestId de la preuve.
   * @return requestId
  **/
  @Schema(required = true, description = "Identifiant de la demande pour renseigner l'élément RequestId de la preuve.")
  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public VerifSignDocumentWithProof proofTag(String proofTag) {
    this.proofTag = proofTag;
    return this;
  }

   /**
   * Tag utilisé pour renseigner l&#x27;élément Tag de la preuve.
   * @return proofTag
  **/
  @Schema(required = true, description = "Tag utilisé pour renseigner l'élément Tag de la preuve.")
  public String getProofTag() {
    return proofTag;
  }

  public void setProofTag(String proofTag) {
    this.proofTag = proofTag;
  }

  public VerifSignDocumentWithProof applicantId(String applicantId) {
    this.applicantId = applicantId;
    return this;
  }

   /**
   * Identifiant du demandeur utilisé pour renseigner le champ applicantId de la preuve.
   * @return applicantId
  **/
  @Schema(required = true, description = "Identifiant du demandeur utilisé pour renseigner le champ applicantId de la preuve.")
  public String getApplicantId() {
    return applicantId;
  }

  public void setApplicantId(String applicantId) {
    this.applicantId = applicantId;
  }

  public VerifSignDocumentWithProof idProofConf(Long idProofConf) {
    this.idProofConf = idProofConf;
    return this;
  }

   /**
   * Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la génération de la preuve (appel de l&#x27;opération \&quot;/configurations\&quot;).
   * @return idProofConf
  **/
  @Schema(required = true, description = "Identifiant de configuration à sélectionner parmi la liste des configurations disponibles pour la génération de la preuve (appel de l'opération \"/configurations\").")
  public Long getIdProofConf() {
    return idProofConf;
  }

  public void setIdProofConf(Long idProofConf) {
    this.idProofConf = idProofConf;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VerifSignDocumentWithProof verifSignDocumentWithProof = (VerifSignDocumentWithProof) o;
    return Objects.equals(this.idVerifSignConf, verifSignDocumentWithProof.idVerifSignConf) &&
        Objects.equals(this.file, verifSignDocumentWithProof.file) &&
        Objects.equals(this.requestId, verifSignDocumentWithProof.requestId) &&
        Objects.equals(this.proofTag, verifSignDocumentWithProof.proofTag) &&
        Objects.equals(this.applicantId, verifSignDocumentWithProof.applicantId) &&
        Objects.equals(this.idProofConf, verifSignDocumentWithProof.idProofConf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idVerifSignConf, Objects.hashCode(file), requestId, proofTag, applicantId, idProofConf);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VerifSignDocumentWithProof {\n");
    
    sb.append("    idVerifSignConf: ").append(toIndentedString(idVerifSignConf)).append("\n");
    sb.append("    file: ").append(toIndentedString(file)).append("\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    proofTag: ").append(toIndentedString(proofTag)).append("\n");
    sb.append("    applicantId: ").append(toIndentedString(applicantId)).append("\n");
    sb.append("    idProofConf: ").append(toIndentedString(idProofConf)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
