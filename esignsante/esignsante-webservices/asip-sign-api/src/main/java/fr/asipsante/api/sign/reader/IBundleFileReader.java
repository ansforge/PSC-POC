/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.reader;

import java.io.File;
import java.util.List;

/**
 * Composant de chargement du bundle contenu dans un fichier vers un objet Java.
 *
 * @author Sopra Steria
 * @param <T> type de retour
 * @param <R> type de rapports à alimenter
 */
public interface IBundleFileReader<T, R> {

    /**
     * Lit un fichier de bundle et retourne un objet java de type <tt>R</tt>.
     * Alimente une liste de rapport afin de remonter les éventuelles erreurs
     * rencontrées.
     *
     * @param bundleFile            fichier contenant le bundle à charger
     * @param reportsListToPopulate liste des rapports à alimenter en cas d'erreur
     * @return un objet Java portant le contenu du fichier lu.
     */
    T loadFileToBundle(final File bundleFile, final List<R> reportsListToPopulate);
}
