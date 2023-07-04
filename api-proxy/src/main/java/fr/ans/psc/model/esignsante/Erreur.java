package fr.ans.psc.model.esignsante;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Erreur {

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
