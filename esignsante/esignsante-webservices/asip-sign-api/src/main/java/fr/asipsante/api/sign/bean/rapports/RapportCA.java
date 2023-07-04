/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.rapports;

import java.util.ArrayList;
import java.util.List;

import fr.asipsante.api.sign.bean.errors.ErreurCA;

/**
 * La classe RapportCA a pour but de construire l'objet de sortie qui contient
 * l'analyse sur les vérifications effectuées sur le bundle CA.
 *
 *
 * <br>
 *
 * @author Sopra Steria
 */
public class RapportCA {

    /**
     * liste des erreurs CA.
     */
    private List<ErreurCA> listeErreursCA;
    /**
     * le DN du certificat.
     */
    private String cerDN;
    /**
     * valeur de validation du rapport CA.
     */
    private boolean valide;

    /**
     * Instancie un RapportCA.
     */
    public RapportCA() {

    }

    /**
     * Instancie un RapportCA avec une liste d'erreur, le DN du certificat et
     * l'information valide true/false.
     *
     * @param erreurCAList liste d'erreur liée au CA concerné par le rapport.
     * @param cerDN        DN du CA concerné par le rapport.
     * @param valide       true si le CA est valide, false sinon.
     */
    public RapportCA(final List<ErreurCA> erreurCAList, final String cerDN, final boolean valide) {
        this.listeErreursCA = erreurCAList;
        this.cerDN = cerDN;
        this.valide = valide;
    }

    /**
     * Instancie un RapportCA avec une erreur, le DN du certificat et l'information
     * valide true/false.
     *
     * @param erreurCA erreur liée au CA concerné par le rapport.
     * @param cerDN    DN du CA concerné par le rapport.
     * @param valide   true si le CA est valide, false sinon.
     */
    public RapportCA(final ErreurCA erreurCA, final String cerDN, final boolean valide) {
        final List<ErreurCA> erreurCAList = new ArrayList<>();
        erreurCAList.add(erreurCA);
        this.listeErreursCA = erreurCAList;

        this.cerDN = cerDN;
        this.valide = valide;
    }

    /**
     * Gets the liste erreurs CA.
     *
     * @return listeErreursCA.
     */
    public List<ErreurCA> getListeErreursCA() {
        return this.listeErreursCA;
    }

    /**
     * Sets the liste erreurs CA.
     *
     * @param listeErreursCA la liste des objets ErreurCA.
     */
    public void setListeErreursCA(final List<ErreurCA> listeErreursCA) {
        this.listeErreursCA = listeErreursCA;
    }

    /**
     * Gets the cer DN.
     *
     * @return cerDN.
     */
    public String getCerDN() {
        return this.cerDN;
    }

    /**
     * Sets the cer DN.
     *
     * @param cerDN valeur générique de recuperation du DN de CA.
     */
    public void setCerDN(final String cerDN) {
        this.cerDN = cerDN;
    }

    /**
     * Checks if is valide.
     *
     * @return valide.
     */
    public boolean isValide() {
        return this.valide;
    }

    /**
     * Sets the valide.
     *
     * @param valide retourne true si le rapport CA est valide.
     */
    public void setValide(final boolean valide) {
        this.valide = valide;
    }

}
