/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.validation.signature.rules;

import fr.asipsante.api.sign.bean.rapports.RapportValidationSignature;

/**
 * L'interface Visitor permet d'associer une règle à partir du fichier de règles
 * à un contrôle à effectuer.
 */
public interface IVisitor {

    /**
     * Méthode permettant à un objet Visitor d'effectuer un contrôle si l'objet a
     * accepté le visiteur.
     *
     * @param rapport the rapport
     */
    public void visit(RapportValidationSignature rapport);
}
