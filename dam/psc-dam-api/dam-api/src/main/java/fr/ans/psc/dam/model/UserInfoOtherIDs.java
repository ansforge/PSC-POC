package fr.ans.psc.dam.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * UserInfoOtherIDs
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-02-16T15:25:18.132611100+01:00[Europe/Paris]")@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)

public class UserInfoOtherIDs   {
  @JsonProperty("identifiant")
  private String identifiant;

  @JsonProperty("origine")
  private String origine;

  @JsonProperty("qualite")
  private Integer qualite;

  public UserInfoOtherIDs identifiant(String identifiant) {
    this.identifiant = identifiant;
    return this;
  }

  /**
   * Get identifiant
   * @return identifiant
  */
  @ApiModelProperty(value = "")


  public String getIdentifiant() {
    return identifiant;
  }

  public void setIdentifiant(String identifiant) {
    this.identifiant = identifiant;
  }

  public UserInfoOtherIDs origine(String origine) {
    this.origine = origine;
    return this;
  }

  /**
   * Get origine
   * @return origine
  */
  @ApiModelProperty(value = "")


  public String getOrigine() {
    return origine;
  }

  public void setOrigine(String origine) {
    this.origine = origine;
  }

  public UserInfoOtherIDs qualite(Integer qualite) {
    this.qualite = qualite;
    return this;
  }

  /**
   * Get qualite
   * @return qualite
  */
  @ApiModelProperty(value = "")


  public Integer getQualite() {
    return qualite;
  }

  public void setQualite(Integer qualite) {
    this.qualite = qualite;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfoOtherIDs userInfoOtherIDs = (UserInfoOtherIDs) o;
    return Objects.equals(this.identifiant, userInfoOtherIDs.identifiant) &&
        Objects.equals(this.origine, userInfoOtherIDs.origine) &&
        Objects.equals(this.qualite, userInfoOtherIDs.qualite);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifiant, origine, qualite);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfoOtherIDs {\n");
    
    sb.append("    identifiant: ").append(toIndentedString(identifiant)).append("\n");
    sb.append("    origine: ").append(toIndentedString(origine)).append("\n");
    sb.append("    qualite: ").append(toIndentedString(qualite)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

