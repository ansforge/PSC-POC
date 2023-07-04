/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service;

import java.io.File;
import java.util.List;

import fr.asipsante.api.sign.bean.cacrl.CACRLWrapper;
import fr.asipsante.api.sign.bean.rapports.RapportCA;
import fr.asipsante.api.sign.bean.rapports.RapportCRL;

/**
 * The Interface ICACRLService.
 */
public interface ICACRLService {

    /**
     * Gets the cacrl wrapper.
     *
     * @return the cacrl wrapper
     */
    CACRLWrapper getCacrlWrapper();

    /**
     * Gets the ca.
     *
     * @return the ca
     */
    public List<String> getCa();

    /**
     * Load CA.
     *
     * @param bundleCaFile the bundle ca file
     * @return the list
     */
    List<RapportCA> loadCA(File bundleCaFile);

    /**
     * Load CRL.
     *
     * @param bundleCrlFile the bundle crl file
     * @return the list
     */
    List<RapportCRL> loadCRL(File bundleCrlFile);

}
