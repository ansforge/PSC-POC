package fr.ans.psc.dam.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * UserInfoSubjectRefProExercices
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-02-16T15:25:18.132611100+01:00[Europe/Paris]")@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)

public class UserInfoSubjectRefProExercices   {
  @JsonProperty("codeProfession")
  private String codeProfession;

  @JsonProperty("codeCategorieProfessionnelle")
  private String codeCategorieProfessionnelle;

  @JsonProperty("codeCiviliteDexercice")
  private String codeCiviliteDexercice;

  @JsonProperty("nomDexercice")
  private String nomDexercice;

  @JsonProperty("prenomDexercice")
  private String prenomDexercice;

  @JsonProperty("codeTypeSavoirFaire")
  private String codeTypeSavoirFaire;

  @JsonProperty("codeSavoirFaire")
  private String codeSavoirFaire;

  @JsonProperty("activities")
  @Valid
  private List<UserInfoSubjectRefProActivities> activities = null;

  /**
   * Get codeProfession
   * @return codeProfession
  */
  @ApiModelProperty(value = "")


  public String getCodeProfession() {
    return codeProfession;
  }

  public UserInfoSubjectRefProExercices codeCategorieProfessionnelle(String codeCategorieProfessionnelle) {
    this.codeCategorieProfessionnelle = codeCategorieProfessionnelle;
    return this;
  }

  /**
   * Get codeCategorieProfessionnelle
   * @return codeCategorieProfessionnelle
  */
  @ApiModelProperty(value = "")


  public String getCodeCategorieProfessionnelle() {
    return codeCategorieProfessionnelle;
  }

  /**
   * Get codeCiviliteDexercice
   * @return codeCiviliteDexercice
  */
  @ApiModelProperty(value = "")


  public String getCodeCiviliteDexercice() {
    return codeCiviliteDexercice;
  }


  /**
   * Get nomDexercice
   * @return nomDexercice
  */
  @ApiModelProperty(value = "")


  public String getNomDexercice() {
    return nomDexercice;
  }

  /**
   * Get prenomDexercice
   * @return prenomDexercice
  */
  @ApiModelProperty(value = "")


  public String getPrenomDexercice() {
    return prenomDexercice;
  }

  /**
   * Get codeTypeSavoirFaire
   * @return codeTypeSavoirFaire
  */
  @ApiModelProperty(value = "")


  public String getCodeTypeSavoirFaire() {
    return codeTypeSavoirFaire;
  }

  /**
   * Get codeSavoirFaire
   * @return codeSavoirFaire
  */
  @ApiModelProperty(value = "")


  public String getCodeSavoirFaire() {
    return codeSavoirFaire;
  }

   /**
   * Get activities
   * @return activities
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<UserInfoSubjectRefProActivities> getActivities() {
    return activities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfoSubjectRefProExercices userInfoSubjectRefProExercices = (UserInfoSubjectRefProExercices) o;
    return Objects.equals(this.codeProfession, userInfoSubjectRefProExercices.codeProfession) &&
        Objects.equals(this.codeCategorieProfessionnelle, userInfoSubjectRefProExercices.codeCategorieProfessionnelle) &&
        Objects.equals(this.codeCiviliteDexercice, userInfoSubjectRefProExercices.codeCiviliteDexercice) &&
        Objects.equals(this.nomDexercice, userInfoSubjectRefProExercices.nomDexercice) &&
        Objects.equals(this.prenomDexercice, userInfoSubjectRefProExercices.prenomDexercice) &&
        Objects.equals(this.codeTypeSavoirFaire, userInfoSubjectRefProExercices.codeTypeSavoirFaire) &&
        Objects.equals(this.codeSavoirFaire, userInfoSubjectRefProExercices.codeSavoirFaire) &&
        Objects.equals(this.activities, userInfoSubjectRefProExercices.activities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codeProfession, codeCategorieProfessionnelle, codeCiviliteDexercice, nomDexercice, prenomDexercice, codeTypeSavoirFaire, codeSavoirFaire, activities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfoSubjectRefProExercices {\n");
    
    sb.append("    codeProfession: ").append(toIndentedString(codeProfession)).append("\n");
    sb.append("    codeCategorieProfessionnelle: ").append(toIndentedString(codeCategorieProfessionnelle)).append("\n");
    sb.append("    codeCiviliteDexercice: ").append(toIndentedString(codeCiviliteDexercice)).append("\n");
    sb.append("    nomDexercice: ").append(toIndentedString(nomDexercice)).append("\n");
    sb.append("    prenomDexercice: ").append(toIndentedString(prenomDexercice)).append("\n");
    sb.append("    codeTypeSavoirFaire: ").append(toIndentedString(codeTypeSavoirFaire)).append("\n");
    sb.append("    codeSavoirFaire: ").append(toIndentedString(codeSavoirFaire)).append("\n");
    sb.append("    activities: ").append(toIndentedString(activities)).append("\n");
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

