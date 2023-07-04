package fr.ans.psc.dam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;


/**
 * UserInfo
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-02-16T15:25:18.132611100+01:00[Europe/Paris]")@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)

public class UsefulUserInfo   {
  @JsonProperty("Secteur_Activite")
  private String secteurActivite;

  @JsonProperty("sub")
  private String sub;
  
  @JsonProperty("Added")
  private String nexistepas;

  @JsonProperty("email_verified")
  private Boolean emailVerified;

  @JsonProperty("SubjectOrganization")
  private String subjectOrganization;

  @JsonProperty("Mode_Acces_Raison")
  private String modeAccesRaison;

  @JsonProperty("preferred_username")
  private String preferredUsername;

  @JsonProperty("given_name")
  private String givenName;

  @JsonProperty("Palier_Authentification")
  private String palierAuthentification;

  @JsonProperty("SubjectRefPro")
  private UsefulSubjectRefPro subjectRefPro;

  @JsonProperty("SubjectOrganizationID")
  private String subjectOrganizationID;

  @JsonProperty("SubjectRole")
  @Valid
  private List<String> subjectRole = null;

  @JsonProperty("SubjectNameID")
  private String subjectNameID;

  @JsonProperty("family_name")
  private String familyName;

  /**
   * Get secteurActivite
   * @return secteurActivite
  */
  @ApiModelProperty(value = "")
  public String getSecteurActivite() {
    return secteurActivite;
  }

  /**
   * Get sub
   * @return sub
  */
  @ApiModelProperty(value = "")
  public String getSub() {
    return sub;
  }

  /**
   * Get emailVerified
   * @return emailVerified
  */
  @ApiModelProperty(value = "")
  public Boolean getEmailVerified() {
    return emailVerified;
  }

  /**
   * Get subjectOrganization
   * @return subjectOrganization
  */
  @ApiModelProperty(value = "")
  public String getSubjectOrganization() {
    return subjectOrganization;
  }

  /**
   * Get modeAccesRaison
   * @return modeAccesRaison
  */
  @ApiModelProperty(value = "")
  public String getModeAccesRaison() {
    return modeAccesRaison;
  }

  /**
   * Get preferredUsername
   * @return preferredUsername
  */
  @ApiModelProperty(value = "")
  public String getPreferredUsername() {
    return preferredUsername;
  }

   /**
   * Get givenName
   * @return givenName
  */
  @ApiModelProperty(value = "")
  public String getGivenName() {
    return givenName;
  }

 

  /**
   * Get palierAuthentification
   * @return palierAuthentification
  */
  @ApiModelProperty(value = "")
  public String getPalierAuthentification() {
    return palierAuthentification;
  }


  /**
   * Get subjectOrganizationID
   * @return subjectOrganizationID
  */
  @ApiModelProperty(value = "")
  public String getSubjectOrganizationID() {
    return subjectOrganizationID;
  }

  /**
   * Get subjectRole
   * @return subjectRole
  */
  @ApiModelProperty(value = "")
  public List<String> getSubjectRole() {
    return subjectRole;
  }


  /**
   * Get subjectNameID
   * @return subjectNameID
  */
  @ApiModelProperty(value = "")
  public String getSubjectNameID() {
    return subjectNameID;
  }

   /**
   * Get familyName
   * @return familyName
  */
  @ApiModelProperty(value = "")
  public String getFamilyName() {
    return familyName;
  }

   @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsefulUserInfo userInfo = (UsefulUserInfo) o;
    return Objects.equals(this.secteurActivite, userInfo.secteurActivite) &&
        Objects.equals(this.sub, userInfo.sub) &&
        Objects.equals(this.emailVerified, userInfo.emailVerified) &&
        Objects.equals(this.subjectOrganization, userInfo.subjectOrganization) &&
        Objects.equals(this.modeAccesRaison, userInfo.modeAccesRaison) &&
        Objects.equals(this.preferredUsername, userInfo.preferredUsername) &&
        Objects.equals(this.givenName, userInfo.givenName) &&

        Objects.equals(this.palierAuthentification, userInfo.palierAuthentification) &&
        Objects.equals(this.subjectRefPro, userInfo.subjectRefPro) &&
        Objects.equals(this.subjectOrganizationID, userInfo.subjectOrganizationID) &&
        Objects.equals(this.subjectRole, userInfo.subjectRole) &&
     
        Objects.equals(this.subjectNameID, userInfo.subjectNameID) &&
        Objects.equals(this.familyName, userInfo.familyName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(secteurActivite, sub, emailVerified, subjectOrganization, modeAccesRaison, preferredUsername, givenName, palierAuthentification, subjectRefPro, subjectOrganizationID, subjectRole,  subjectNameID, familyName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfo {\n");
    
    sb.append("    secteurActivite: ").append(toIndentedString(secteurActivite)).append("\n");
    sb.append("    sub: ").append(toIndentedString(sub)).append("\n");
    sb.append("    emailVerified: ").append(toIndentedString(emailVerified)).append("\n");
    sb.append("    subjectOrganization: ").append(toIndentedString(subjectOrganization)).append("\n");
    sb.append("    modeAccesRaison: ").append(toIndentedString(modeAccesRaison)).append("\n");
    sb.append("    preferredUsername: ").append(toIndentedString(preferredUsername)).append("\n");
    sb.append("    givenName: ").append(toIndentedString(givenName)).append("\n");
    sb.append("    palierAuthentification: ").append(toIndentedString(palierAuthentification)).append("\n");
    sb.append("    subjectRefPro: ").append(toIndentedString(subjectRefPro)).append("\n");
    sb.append("    subjectOrganizationID: ").append(toIndentedString(subjectOrganizationID)).append("\n");
    sb.append("    subjectRole: ").append(toIndentedString(subjectRole)).append("\n");
    sb.append("    subjectNameID: ").append(toIndentedString(subjectNameID)).append("\n");
    sb.append("    familyName: ").append(toIndentedString(familyName)).append("\n");
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

