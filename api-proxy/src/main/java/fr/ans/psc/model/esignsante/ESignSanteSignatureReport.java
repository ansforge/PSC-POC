package fr.ans.psc.model.esignsante;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ESignSanteSignatureReport {

    @JsonProperty("erreurs")
    private List<Erreur> erreurs = new ArrayList<Erreur>();

    @JsonProperty("docSigne")
    private String docSigne = null;

    public ESignSanteSignatureReport erreurs(List<Erreur> erreurs) {
        this.erreurs = erreurs;
        return this;
    }

    public ESignSanteSignatureReport addErreursItem(Erreur erreursItem) {
        this.erreurs.add(erreursItem);
        return this;
    }

    /**
     * Get erreurs
     * @return erreurs
     **/
    public List<Erreur> getErreurs() {
        return erreurs;
    }

    public void setErreurs(List<Erreur> erreurs) {
        this.erreurs = erreurs;
    }

    public ESignSanteSignatureReport docSigne(String docSigne) {
        this.docSigne = docSigne;
        return this;
    }

    /**
     * Le document signé encodé en base 64.
     * @return docSigne
     **/
    public String getDocSigne() {
        return docSigne;
    }

    public void setDocSigne(String docSigne) {
        this.docSigne = docSigne;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ESignSanteSignatureReport esignSanteSignatureReport = (ESignSanteSignatureReport) o;
        return Objects.equals(this.erreurs, esignSanteSignatureReport.erreurs) &&
                Objects.equals(this.docSigne, esignSanteSignatureReport.docSigne);
    }

    @Override
    public int hashCode() {
        return Objects.hash(erreurs, docSigne);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ESignSanteSignatureReport {\n");

        sb.append("    erreurs: ").append(toIndentedString(erreurs)).append("\n");
        sb.append("    docSigne: ").append(toIndentedString(docSigne)).append("\n");
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
