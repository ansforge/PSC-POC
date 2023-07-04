/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.rapports;

import fr.asipsante.api.sign.validation.certificat.rules.ICertificatVisitor;

/**
 * L'interface Visitable permettant à un objet RapportSignature d'être visité
 * par un objet Rules.
 */
public interface ICertificatVisitable {

    /**
     * Méthode permettant à un objet RapportSignature de pouvoir effectuer une
     * vérification d'une règle d'un objet Rules.
     *
     * @param visitor Le visiteur - l'un des classes du package fr.asipsante.rules
     */
    public void accept(ICertificatVisitor visitor);

}
