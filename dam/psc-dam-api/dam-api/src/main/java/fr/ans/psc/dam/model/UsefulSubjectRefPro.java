package fr.ans.psc.dam.model;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * UserInfoSubjectRefPro
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-02-16T15:25:18.132611100+01:00[Europe/Paris]")@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)

public class UsefulSubjectRefPro   {
  @JsonProperty("codeCivilite")
  private String codeCivilite;

  @JsonProperty("exercices")
  @Valid
  private List<UserInfoSubjectRefProExercices> exercices = null;

  public UsefulSubjectRefPro codeCivilite(String codeCivilite) {
    this.codeCivilite = codeCivilite;
    return this;
  }

  /**
   * Get codeCivilite
   * @return codeCivilite
  */
  @ApiModelProperty(value = "")


  public String getCodeCivilite() {
    return codeCivilite;
  }

  /**
   * Get exercices
   * @return exercices
  */
  @ApiModelProperty(value = "")
  @Valid

  public List<UserInfoSubjectRefProExercices> getExercices() {
    return exercices;
  }

   @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsefulSubjectRefPro userInfoSubjectRefPro = (UsefulSubjectRefPro) o;
    return Objects.equals(this.codeCivilite, userInfoSubjectRefPro.codeCivilite) &&
        Objects.equals(this.exercices, userInfoSubjectRefPro.exercices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codeCivilite, exercices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfoSubjectRefPro {\n");
    
    sb.append("    codeCivilite: ").append(toIndentedString(codeCivilite)).append("\n");
    sb.append("    exercices: ").append(toIndentedString(exercices)).append("\n");
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

