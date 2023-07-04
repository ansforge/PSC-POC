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

public class UserInfo2   {
  @JsonProperty("Secteur_Activite")
  private String secteurActivite;

  @JsonProperty("sub")
  private String sub;

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

  @JsonProperty("Acces_Regulation_Medicale")
  private String accesRegulationMedicale;

  @JsonProperty("UITVersion")
  private String uiTVersion;

  @JsonProperty("authMode")
  private String authMode;

  @JsonProperty("Palier_Authentification")
  private String palierAuthentification;

  @JsonProperty("SubjectRefPro")
  private UserInfoSubjectRefPro subjectRefPro;

  @JsonProperty("SubjectOrganizationID")
  private String subjectOrganizationID;

  @JsonProperty("SubjectRole")
  @Valid
  private List<String> subjectRole = null;

  @JsonProperty("PSI_Locale")
  private String psILocale;

  @JsonProperty("otherIDs")
  @Valid
  private List<UserInfoOtherIDs> otherIDs = null;

  @JsonProperty("SubjectNameID")
  private String subjectNameID;

  @JsonProperty("family_name")
  private String familyName;

  public UserInfo2 secteurActivite(String secteurActivite) {
    this.secteurActivite = secteurActivite;
    return this;
  }

  /**
   * Get secteurActivite
   * @return secteurActivite
  */
  @ApiModelProperty(value = "")


  public String getSecteurActivite() {
    return secteurActivite;
  }

  public void setSecteurActivite(String secteurActivite) {
    this.secteurActivite = secteurActivite;
  }

  public UserInfo2 sub(String sub) {
    this.sub = sub;
    return this;
  }

  /**
   * Get sub
   * @return sub
  */
  @ApiModelProperty(value = "")


  public String getSub() {
    return sub;
  }

  public void setSub(String sub) {
    this.sub = sub;
  }

  public UserInfo2 emailVerified(Boolean emailVerified) {
    this.emailVerified = emailVerified;
    return this;
  }

  /**
   * Get emailVerified
   * @return emailVerified
  */
  @ApiModelProperty(value = "")


  public Boolean getEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(Boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public UserInfo2 subjectOrganization(String subjectOrganization) {
    this.subjectOrganization = subjectOrganization;
    return this;
  }

  /**
   * Get subjectOrganization
   * @return subjectOrganization
  */
  @ApiModelProperty(value = "")


  public String getSubjectOrganization() {
    return subjectOrganization;
  }

  public void setSubjectOrganization(String subjectOrganization) {
    this.subjectOrganization = subjectOrganization;
  }

  public UserInfo2 modeAccesRaison(String modeAccesRaison) {
    this.modeAccesRaison = modeAccesRaison;
    return this;
  }

  /**
   * Get modeAccesRaison
   * @return modeAccesRaison
  */
  @ApiModelProperty(value = "")


  public String getModeAccesRaison() {
    return modeAccesRaison;
  }

  public void setModeAccesRaison(String modeAccesRaison) {
    this.modeAccesRaison = modeAccesRaison;
  }

  public UserInfo2 preferredUsername(String preferredUsername) {
    this.preferredUsername = preferredUsername;
    return this;
  }

  /**
   * Get preferredUsername
   * @return preferredUsername
  */
  @ApiModelProperty(value = "")


  public String getPreferredUsername() {
    return preferredUsername;
  }

  public void setPreferredUsername(String preferredUsername) {
    this.preferredUsername = preferredUsername;
  }

  public UserInfo2 givenName(String givenName) {
    this.givenName = givenName;
    return this;
  }

  /**
   * Get givenName
   * @return givenName
  */
  @ApiModelProperty(value = "")


  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public UserInfo2 accesRegulationMedicale(String accesRegulationMedicale) {
    this.accesRegulationMedicale = accesRegulationMedicale;
    return this;
  }

  /**
   * Get accesRegulationMedicale
   * @return accesRegulationMedicale
  */
  @ApiModelProperty(value = "")


  public String getAccesRegulationMedicale() {
    return accesRegulationMedicale;
  }

  public void setAccesRegulationMedicale(String accesRegulationMedicale) {
    this.accesRegulationMedicale = accesRegulationMedicale;
  }

  public UserInfo2 uiTVersion(String uiTVersion) {
    this.uiTVersion = uiTVersion;
    return this;
  }

  /**
   * Get uiTVersion
   * @return uiTVersion
  */
  @ApiModelProperty(value = "")


  public String getUiTVersion() {
    return uiTVersion;
  }

  public void setUiTVersion(String uiTVersion) {
    this.uiTVersion = uiTVersion;
  }

  public UserInfo2 authMode(String authMode) {
    this.authMode = authMode;
    return this;
  }

  /**
   * Get authMode
   * @return authMode
  */
  @ApiModelProperty(value = "")


  public String getAuthMode() {
    return authMode;
  }

  public void setAuthMode(String authMode) {
    this.authMode = authMode;
  }

  public UserInfo2 palierAuthentification(String palierAuthentification) {
    this.palierAuthentification = palierAuthentification;
    return this;
  }

  /**
   * Get palierAuthentification
   * @return palierAuthentification
  */
  @ApiModelProperty(value = "")


  public String getPalierAuthentification() {
    return palierAuthentification;
  }

  public void setPalierAuthentification(String palierAuthentification) {
    this.palierAuthentification = palierAuthentification;
  }

  public UserInfo2 subjectRefPro(UserInfoSubjectRefPro subjectRefPro) {
    this.subjectRefPro = subjectRefPro;
    return this;
  }

  /**
   * Get subjectRefPro
   * @return subjectRefPro
  */
  @ApiModelProperty(value = "")

  @Valid

  public UserInfoSubjectRefPro getSubjectRefPro() {
    return subjectRefPro;
  }

  public void setSubjectRefPro(UserInfoSubjectRefPro subjectRefPro) {
    this.subjectRefPro = subjectRefPro;
  }

  public UserInfo2 subjectOrganizationID(String subjectOrganizationID) {
    this.subjectOrganizationID = subjectOrganizationID;
    return this;
  }

  /**
   * Get subjectOrganizationID
   * @return subjectOrganizationID
  */
  @ApiModelProperty(value = "")


  public String getSubjectOrganizationID() {
    return subjectOrganizationID;
  }

  public void setSubjectOrganizationID(String subjectOrganizationID) {
    this.subjectOrganizationID = subjectOrganizationID;
  }

  public UserInfo2 subjectRole(List<String> subjectRole) {
    this.subjectRole = subjectRole;
    return this;
  }

  public UserInfo2 addSubjectRoleItem(String subjectRoleItem) {
    if (this.subjectRole == null) {
      this.subjectRole = new ArrayList<String>();
    }
    this.subjectRole.add(subjectRoleItem);
    return this;
  }

  /**
   * Get subjectRole
   * @return subjectRole
  */
  @ApiModelProperty(value = "")


  public List<String> getSubjectRole() {
    return subjectRole;
  }

  public void setSubjectRole(List<String> subjectRole) {
    this.subjectRole = subjectRole;
  }

  public UserInfo2 psILocale(String psILocale) {
    this.psILocale = psILocale;
    return this;
  }

  /**
   * Get psILocale
   * @return psILocale
  */
  @ApiModelProperty(value = "")


  public String getPsILocale() {
    return psILocale;
  }

  public void setPsILocale(String psILocale) {
    this.psILocale = psILocale;
  }

  public UserInfo2 otherIDs(List<UserInfoOtherIDs> otherIDs) {
    this.otherIDs = otherIDs;
    return this;
  }

  public UserInfo2 addOtherIDsItem(UserInfoOtherIDs otherIDsItem) {
    if (this.otherIDs == null) {
      this.otherIDs = new ArrayList<UserInfoOtherIDs>();
    }
    this.otherIDs.add(otherIDsItem);
    return this;
  }

  /**
   * Get otherIDs
   * @return otherIDs
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<UserInfoOtherIDs> getOtherIDs() {
    return otherIDs;
  }

  public void setOtherIDs(List<UserInfoOtherIDs> otherIDs) {
    this.otherIDs = otherIDs;
  }

  public UserInfo2 subjectNameID(String subjectNameID) {
    this.subjectNameID = subjectNameID;
    return this;
  }

  /**
   * Get subjectNameID
   * @return subjectNameID
  */
  @ApiModelProperty(value = "")


  public String getSubjectNameID() {
    return subjectNameID;
  }

  public void setSubjectNameID(String subjectNameID) {
    this.subjectNameID = subjectNameID;
  }

  public UserInfo2 familyName(String familyName) {
    this.familyName = familyName;
    return this;
  }

  /**
   * Get familyName
   * @return familyName
  */
  @ApiModelProperty(value = "")


  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfo2 userInfo = (UserInfo2) o;
    return Objects.equals(this.secteurActivite, userInfo.secteurActivite) &&
        Objects.equals(this.sub, userInfo.sub) &&
        Objects.equals(this.emailVerified, userInfo.emailVerified) &&
        Objects.equals(this.subjectOrganization, userInfo.subjectOrganization) &&
        Objects.equals(this.modeAccesRaison, userInfo.modeAccesRaison) &&
        Objects.equals(this.preferredUsername, userInfo.preferredUsername) &&
        Objects.equals(this.givenName, userInfo.givenName) &&
        Objects.equals(this.accesRegulationMedicale, userInfo.accesRegulationMedicale) &&
        Objects.equals(this.uiTVersion, userInfo.uiTVersion) &&
        Objects.equals(this.authMode, userInfo.authMode) &&
        Objects.equals(this.palierAuthentification, userInfo.palierAuthentification) &&
        Objects.equals(this.subjectRefPro, userInfo.subjectRefPro) &&
        Objects.equals(this.subjectOrganizationID, userInfo.subjectOrganizationID) &&
        Objects.equals(this.subjectRole, userInfo.subjectRole) &&
        Objects.equals(this.psILocale, userInfo.psILocale) &&
        Objects.equals(this.otherIDs, userInfo.otherIDs) &&
        Objects.equals(this.subjectNameID, userInfo.subjectNameID) &&
        Objects.equals(this.familyName, userInfo.familyName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(secteurActivite, sub, emailVerified, subjectOrganization, modeAccesRaison, preferredUsername, givenName, accesRegulationMedicale, uiTVersion, authMode, palierAuthentification, subjectRefPro, subjectOrganizationID, subjectRole, psILocale, otherIDs, subjectNameID, familyName);
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
    sb.append("    accesRegulationMedicale: ").append(toIndentedString(accesRegulationMedicale)).append("\n");
    sb.append("    uiTVersion: ").append(toIndentedString(uiTVersion)).append("\n");
    sb.append("    authMode: ").append(toIndentedString(authMode)).append("\n");
    sb.append("    palierAuthentification: ").append(toIndentedString(palierAuthentification)).append("\n");
    sb.append("    subjectRefPro: ").append(toIndentedString(subjectRefPro)).append("\n");
    sb.append("    subjectOrganizationID: ").append(toIndentedString(subjectOrganizationID)).append("\n");
    sb.append("    subjectRole: ").append(toIndentedString(subjectRole)).append("\n");
    sb.append("    psILocale: ").append(toIndentedString(psILocale)).append("\n");
    sb.append("    otherIDs: ").append(toIndentedString(otherIDs)).append("\n");
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

