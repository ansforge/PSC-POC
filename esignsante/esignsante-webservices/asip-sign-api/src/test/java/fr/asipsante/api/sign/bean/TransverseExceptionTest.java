/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.asipsante.api.sign.utils.AsipSignException;

/**
 * class de test pour les exception de la librairie transverse.
 *
 * @author Sopra Steria
 */
public class TransverseExceptionTest {

    /**
     * Test transverse exception.
     */
    @Test
    public final void testTransverseException() {
        // verifier si l'objet ne retour pas null
        assertNotNull("verifier si l'object exception n'est pas null ",
                new AsipSignException("Erreur lors de l'instanciation "));// verifier

        final AsipSignException transverseException = new AsipSignException();
        final AsipSignException transverseExceptionMessageOneArgs = new AsipSignException("message d'erreur",
                new Throwable());
        final AsipSignException transverseExceptionOneArgs = new AsipSignException(new Throwable());
        assertNotNull("message d'erreur", transverseException);
        assertNotNull("message d'erreur", transverseExceptionMessageOneArgs);
        assertNotNull("message d'erreur", transverseExceptionOneArgs);

    }

}
