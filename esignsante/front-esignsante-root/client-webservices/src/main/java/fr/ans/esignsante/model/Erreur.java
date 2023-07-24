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
import java.io.Serializable;
/**
 * Erreur
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")
public class Erreur implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("codeErreur")
  private String codeErreur = null;

  @JsonProperty("message")
  private String message = null;

  public Erreur codeErreur(String codeErreur) {
    this.codeErreur = codeErreur;
    return this;
  }

   /**
   * Get codeErreur
   * @return codeErreur
  **/
  @Schema(required = true, description = "")
  public String getCodeErreur() {
    return codeErreur;
  }

  public void setCodeErreur(String codeErreur) {
    this.codeErreur = codeErreur;
  }

  public Erreur message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @Schema(required = true, description = "")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Erreur erreur = (Erreur) o;
    return Objects.equals(this.codeErreur, erreur.codeErreur) &&
        Objects.equals(this.message, erreur.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codeErreur, message);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Erreur {\n");
    
    sb.append("    codeErreur: ").append(toIndentedString(codeErreur)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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