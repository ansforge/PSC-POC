package fr.asipsante.api.sign.service.impl.utils;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

/**
 * The Class VersionTest.
 */
public class VersionTest {

    /**
     * version test.
     */
    @Test
    public void versionTest() throws ParseException {
        Version version = new Version("2.0.0-SNAPSHOT");
        assertEquals("Version majeure doit être égale à 2", "2", version.getMajor());
        assertEquals("Version mineure doit être égale à 0", "0", version.getMinor());
        assertEquals("Version de patch doit être égale à 0", "0", version.getPatch());
        assertEquals("Version d'iteration doit être vide", "", version.getIteration());
        assertEquals("Version doit être égale à 2.0.0", "2.0.0", version.getVersion());
        assertEquals("Version full doit être égale à 2.0.0-SNAPSHOT", "2.0.0-SNAPSHOT", version.getFull());
    }

    /**
     * version test with wrong major.
     */
    @Test(expected = ParseException.class)
    public void versionTestWrongMajor() throws ParseException {
        new Version("X.0.0.0-SNAPSHOT");
    }

    /**
     * version test with wrong minor.
     */
    @Test(expected = ParseException.class)
    public void versionTestWrongMinor() throws ParseException {
        new Version("2.X.0.0-SNAPSHOT");
    }

    /**
     * version test with wrong patch.
     */
    @Test(expected = ParseException.class)
    public void versionTestWrongPatch() throws ParseException {
        new Version("2.0.X.0-SNAPSHOT");
    }

    /**
     * version test with wrong iteration.
     */
    @Test(expected = ParseException.class)
    public void versionTestWrongIteration() throws ParseException {
        new Version("2.0.0.X-SNAPSHOT");
    }

    /**
     * version test with wrong tag.
     */
    @Test(expected = ParseException.class)
    public void versionTestWrongTag() throws ParseException {
        new Version("2.0.0.0-XXXXX");
    }

}
