/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.rapports;

import java.util.ArrayList;
import java.util.List;

import fr.asipsante.api.sign.bean.errors.ErreurCRL;

/**
 * La classe RapportCRL a pour but de construire l'objet de sortie qui contient
 * l'analyse sur les vérifications effectuées dans le bundle CRL<br>
 * .
 *
 * @author Sopra Steria
 */
public class RapportCRL {

    /**
     * la liste d'erreur CRL.
     */
    private List<ErreurCRL> listeErreursCRL;
    /**
     * identifiant CRL.
     */
    private String identifiantCrl;
    /**
     * emetteur du certificats CRL.
     */
    private String emetteurCrl;
    /**
     * objet de validation CRL.
     */
    private boolean valide;

    /**
     * Instancie un RapportCRL.
     */
    public RapportCRL() {

    }

    /**
     * Instancie un RapportCRL avec une liste d'erreur, l'identifiant du CRL,
     * l'émetteur du CRL et l'information valide true/false.
     *
     * @param erreurCRLList  liste d'erreur liée au CRL concerné par le rapport
     * @param identifiantCRL identifiant du CRL concerné par le rapport
     * @param emetteurCRL    émetteur du CRL concerné par le rapport
     * @param valide         true si le CRL est valide, false sinon
     */
    public RapportCRL(final List<ErreurCRL> erreurCRLList, final String identifiantCRL, final String emetteurCRL,
            final boolean valide) {
        this.listeErreursCRL = erreurCRLList;
        this.identifiantCrl = identifiantCRL;
        this.emetteurCrl = emetteurCRL;
        this.valide = valide;
    }

    /**
     * Instancie un RapportCRL avec une erreur, l'identifiant du CRL, l'émetteur du
     * CRL et l'information valide true/false.
     *
     * @param erreurCRL      erreur liée au CRL concerné par le rapport
     * @param identifiantCRL identifiant du CRL concerné par le rapport
     * @param emetteurCRL    émetteur du CRL concerné par le rapport
     * @param valide         true si le CRL est valide, false sinon
     */
    public RapportCRL(final ErreurCRL erreurCRL, final String identifiantCRL, final String emetteurCRL,
            final boolean valide) {

        final List<ErreurCRL> erreurCRLList = new ArrayList<>();
        erreurCRLList.add(erreurCRL);
        this.listeErreursCRL = erreurCRLList;

        this.identifiantCrl = identifiantCRL;
        this.emetteurCrl = emetteurCRL;
        this.valide = valide;
    }

    /**
     * Gets the identifiant crl.
     *
     * @return identifiantCrl.
     */
    public String getIdentifiantCrl() {
        return this.identifiantCrl;
    }

    /**
     * Sets the identifiant crl.
     *
     * @param identifiantCrl le serial number du certificat.
     */
    public void setIdentifiantCrl(final String identifiantCrl) {
        this.identifiantCrl = identifiantCrl;
    }

    /**
     * Gets the emetteur crl.
     *
     * @return emetteurCrl.
     */
    public String getEmetteurCrl() {
        return this.emetteurCrl;
    }

    /**
     * Sets the emetteur crl.
     *
     * @param emetteurCrl le CN emetteur du certificat.
     */

    public void setEmetteurCrl(final String emetteurCrl) {
        this.emetteurCrl = emetteurCrl;
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
     * @param valide - retourne true si le rapport CRL est valide.
     */
    public void setValide(final boolean valide) {
        this.valide = valide;
    }

    /**
     * Gets the liste erreurs CRL.
     *
     * @return listeErreursCRL.
     */
    public List<ErreurCRL> getListeErreursCRL() {
        return this.listeErreursCRL;
    }

    /**
     * Sets the liste erreurs CRL.
     *
     * @param listeErreursCRL - liste d'object de type ErreurCRL.
     */
    public void setListeErreursCRL(final List<ErreurCRL> listeErreursCRL) {
        this.listeErreursCRL = listeErreursCRL;
    }

}
