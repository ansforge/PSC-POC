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
 * ConfSign
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-07-04T13:37:15.229Z[GMT]")
public class ConfSign implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("idSignConf")
  private Long idSignConf = null;

  @JsonProperty("associatedProofId")
  private Long associatedProofId = null;

  @JsonProperty("description")
  private String description = null;

  /**
   * Gets or Sets signaturePackaging
   */
  public enum SignaturePackagingEnum {
    ENVELOPING("ENVELOPING"),
    ENVELOPED("ENVELOPED");

    private String value;

    SignaturePackagingEnum(String value) {
      this.value = value;
    }
    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    @JsonCreator
    public static SignaturePackagingEnum fromValue(String input) {
      for (SignaturePackagingEnum b : SignaturePackagingEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }

  }  @JsonProperty("signaturePackaging")
  private SignaturePackagingEnum signaturePackaging = null;

  /**
   * Gets or Sets digestAlgorithm
   */
  public enum DigestAlgorithmEnum {
    SHA1("SHA1"),
    SHA256("SHA256"),
    SHA512("SHA512");

    private String value;

    DigestAlgorithmEnum(String value) {
      this.value = value;
    }
    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    @JsonCreator
    public static DigestAlgorithmEnum fromValue(String input) {
      for (DigestAlgorithmEnum b : DigestAlgorithmEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }

  }  @JsonProperty("digestAlgorithm")
  private DigestAlgorithmEnum digestAlgorithm = null;

  /**
   * Gets or Sets canonicalisationAlgorithm
   */
  public enum CanonicalisationAlgorithmEnum {
    TR_2001_REC_XML_C14N_20010315("http://www.w3.org/TR/2001/REC-xml-c14n-20010315"),
    TR_2001_REC_XML_C14N_20010315_WITHCOMMENTS("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments"),
    _2001_10_XML_EXC_C14N_("http://www.w3.org/2001/10/xml-exc-c14n#"),
    _2001_10_XML_EXC_C14N_WITHCOMMENTS("http://www.w3.org/2001/10/xml-exc-c14n#WithComments");

    private String value;

    CanonicalisationAlgorithmEnum(String value) {
      this.value = value;
    }
    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    @JsonCreator
    public static CanonicalisationAlgorithmEnum fromValue(String input) {
      for (CanonicalisationAlgorithmEnum b : CanonicalisationAlgorithmEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }

  }  @JsonProperty("canonicalisationAlgorithm")
  private CanonicalisationAlgorithmEnum canonicalisationAlgorithm = null;

  @JsonProperty("dn")
  private String dn = null;

  public ConfSign idSignConf(Long idSignConf) {
    this.idSignConf = idSignConf;
    return this;
  }

   /**
   * Identifiant de configuration. Cet identifiant est utilisé par les services de signature.
   * @return idSignConf
  **/
  @Schema(required = true, description = "Identifiant de configuration. Cet identifiant est utilisé par les services de signature.")
  public Long getIdSignConf() {
    return idSignConf;
  }

  public void setIdSignConf(Long idSignConf) {
    this.idSignConf = idSignConf;
  }

  public ConfSign associatedProofId(Long associatedProofId) {
    this.associatedProofId = associatedProofId;
    return this;
  }

   /**
   * Identifiant de configuration de la preuve de validation associée.
   * @return associatedProofId
  **/
  @Schema(required = true, description = "Identifiant de configuration de la preuve de validation associée.")
  public Long getAssociatedProofId() {
    return associatedProofId;
  }

  public void setAssociatedProofId(Long associatedProofId) {
    this.associatedProofId = associatedProofId;
  }

  public ConfSign description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description de la configuration de signature.
   * @return description
  **/
  @Schema(required = true, description = "Description de la configuration de signature.")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ConfSign signaturePackaging(SignaturePackagingEnum signaturePackaging) {
    this.signaturePackaging = signaturePackaging;
    return this;
  }

   /**
   * Get signaturePackaging
   * @return signaturePackaging
  **/
  @Schema(required = true, description = "")
  public SignaturePackagingEnum getSignaturePackaging() {
    return signaturePackaging;
  }

  public void setSignaturePackaging(SignaturePackagingEnum signaturePackaging) {
    this.signaturePackaging = signaturePackaging;
  }

  public ConfSign digestAlgorithm(DigestAlgorithmEnum digestAlgorithm) {
    this.digestAlgorithm = digestAlgorithm;
    return this;
  }

   /**
   * Get digestAlgorithm
   * @return digestAlgorithm
  **/
  @Schema(required = true, description = "")
  public DigestAlgorithmEnum getDigestAlgorithm() {
    return digestAlgorithm;
  }

  public void setDigestAlgorithm(DigestAlgorithmEnum digestAlgorithm) {
    this.digestAlgorithm = digestAlgorithm;
  }

  public ConfSign canonicalisationAlgorithm(CanonicalisationAlgorithmEnum canonicalisationAlgorithm) {
    this.canonicalisationAlgorithm = canonicalisationAlgorithm;
    return this;
  }

   /**
   * Get canonicalisationAlgorithm
   * @return canonicalisationAlgorithm
  **/
  @Schema(required = true, description = "")
  public CanonicalisationAlgorithmEnum getCanonicalisationAlgorithm() {
    return canonicalisationAlgorithm;
  }

  public void setCanonicalisationAlgorithm(CanonicalisationAlgorithmEnum canonicalisationAlgorithm) {
    this.canonicalisationAlgorithm = canonicalisationAlgorithm;
  }

  public ConfSign dn(String dn) {
    this.dn = dn;
    return this;
  }

   /**
   * DN du certificat qui sera utilisé pour signer le document
   * @return dn
  **/
  @Schema(required = true, description = "DN du certificat qui sera utilisé pour signer le document")
  public String getDn() {
    return dn;
  }

  public void setDn(String dn) {
    this.dn = dn;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConfSign confSign = (ConfSign) o;
    return Objects.equals(this.idSignConf, confSign.idSignConf) &&
        Objects.equals(this.associatedProofId, confSign.associatedProofId) &&
        Objects.equals(this.description, confSign.description) &&
        Objects.equals(this.signaturePackaging, confSign.signaturePackaging) &&
        Objects.equals(this.digestAlgorithm, confSign.digestAlgorithm) &&
        Objects.equals(this.canonicalisationAlgorithm, confSign.canonicalisationAlgorithm) &&
        Objects.equals(this.dn, confSign.dn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idSignConf, associatedProofId, description, signaturePackaging, digestAlgorithm, canonicalisationAlgorithm, dn);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfSign {\n");
    
    sb.append("    idSignConf: ").append(toIndentedString(idSignConf)).append("\n");
    sb.append("    associatedProofId: ").append(toIndentedString(associatedProofId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    signaturePackaging: ").append(toIndentedString(signaturePackaging)).append("\n");
    sb.append("    digestAlgorithm: ").append(toIndentedString(digestAlgorithm)).append("\n");
    sb.append("    canonicalisationAlgorithm: ").append(toIndentedString(canonicalisationAlgorithm)).append("\n");
    sb.append("    dn: ").append(toIndentedString(dn)).append("\n");
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
