/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.certificat.rules;

import fr.asipsante.api.sign.bean.rapports.RapportValidationCertificat;

/**
 * L'interface Visitor permet d'associer une règle à partir du fichier de règles
 * à un contrôle à effectuer.
 */
public interface ICertificatVisitor {

    /**
     * Méthode permettant à un objet Visitor d'effectuer un contrôle si l'objet a
     * accepté le visiteur.
     *
     * @param rapportCertif the rapport certif
     */
    public void visit(RapportValidationCertificat rapportCertif);
}
