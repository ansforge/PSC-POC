/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service.impl.utils;

import java.text.ParseException;

/**
 * the class Version.
 */
public final class Version {

    /**
     * radix.
     */
    private static final int RADIX = 10;

    /**
     * Major version number.
     */
    private int major;

    /**
     * Minor version number.
     */
    private int minor;

    /**
     * Patch level.
     */
    private int patch;

    /**
     * Iteration level. This is an Integer cause it can be null.
     */
    private Integer iteration;

    /**
     * release state (RELEASE or SNAPSHOT).
     */
    private String releaseState = "";

    /**
     * error position.
     */
    private int errPos;

    /**
     * input string split into chars.
     */
    private char[] input;

    /**
     * Construct a new plain version object.
     *
     * @param major
     *          major version number. Must not be negative
     * @param minor
     *          minor version number. Must not be negative
     * @param patch
     *          patch level. Must not be negative.
     * @param iteration
     *          iteration. Must not be negative.
     */
    public Version(int major, int minor, int patch, int iteration) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.iteration = iteration;
    }

    /**
     * Construct a version object by parsing a string.
     *
     * @param version
     *          version in flat string format
     * @throws ParseException
     *           if the version string does not conform to specs.
     */
    public Version(String version) throws ParseException {
        input = version.toCharArray();
        if (!stateMajor()) { // Start recursive descend
            throw new ParseException(version, errPos);
        }
    }

    // Parser implementation below

    private boolean stateMajor() {
        int pos = 0;
        final boolean stateMajorOk;
        while (pos < input.length && input[pos] >= '0' && input[pos] <= '9') {
            pos++; // match [0..9]+
        }
        if (pos == 0 || (input[0] == '0' && pos > 1)) { // Empty String -> Error, or leading zero
            stateMajorOk = false;
        } else if (input[pos] == '.') {
            major = Integer.parseInt(new String(input, 0, pos), RADIX);
            stateMajorOk = stateMinor(pos + 1);
        } else { // We have junk
            errPos = pos;
            stateMajorOk = false;
        }
        return stateMajorOk;
    }

    private boolean stateMinor(int index) {
        int pos = index;
        final boolean stateMinorOk;
        while (pos < input.length && input[pos] >= '0' && input[pos] <= '9') {
            pos++; // match [0..9]+
        }
        if (pos == index || (input[0] == '0' && pos - index > 1)) { // Empty String -> Error, or leading zero
            errPos = index;
            stateMinorOk = false;
        } else if (input[pos] == '.') {
            minor = Integer.parseInt(new String(input, index, pos - index), RADIX);
            stateMinorOk = statePatch(pos + 1);
        } else { // We have junk
            errPos = pos;
            stateMinorOk = false;
        }
        return stateMinorOk;
    }

    private boolean statePatch(int index) {
        int pos = index;
        final boolean statePatchOk;
        while (pos < input.length && input[pos] >= '0' && input[pos] <= '9') {
            pos++; // match [0..9]+
        }
        if (pos == index || (input[0] == '0' && pos - index > 1)) { // Empty String -> Error, or Leading zero
            errPos = index;
            statePatchOk = false;
        } else if (pos == input.length) { // We have a clean version string
            patch = Integer.parseInt(new String(input, index, pos - index), RADIX);
            statePatchOk = true;
        } else if (input[pos] == '.') {
            patch = Integer.parseInt(new String(input, index, pos - index), RADIX);
            statePatchOk = stateIteration(pos + 1);
        } else if (input[pos] == '-') { // We have release tags -> descend
            patch = Integer.parseInt(new String(input, index, pos - index), RADIX);
            iteration = null;
            statePatchOk = stateRelease(pos + 1);
        } else { // We have junk
            errPos = pos;
            statePatchOk = false;
        }
        return statePatchOk;
    }

    private boolean stateIteration(int index) {
        int pos = index;
        final boolean stateIterationOk;
        while (pos < input.length && input[pos] >= '0' && input[pos] <= '9') {
            pos++; // match [0..9]+
        }
        if (pos == index || (input[0] == '0' && pos - index > 1)) { // Empty String -> Error
            errPos = index;
            stateIterationOk = false;
        } else if (pos == input.length) { // We have a clean version string
            iteration = Integer.parseInt(new String(input, index, pos - index), RADIX);
            stateIterationOk = true;
        } else if (input[pos] == '-') { // We have release tag -> descend
            iteration = Integer.parseInt(new String(input, index, pos - index), RADIX);
            stateIterationOk = stateRelease(pos + 1);
        } else { // We have junk
            errPos = pos;
            stateIterationOk = false;
        }
        return stateIterationOk;
    }

    private boolean stateRelease(int index) {
        if (index < input.length) {
            releaseState = String.valueOf(input).substring(index);
        }
        // Empty String -> Error
        return "RELEASE".equals(releaseState) || "SNAPSHOT".equals(releaseState) || releaseState.startsWith("RC");
    }

    /**
     * getMajor method.
     * @return  major version
     */
    public String getMajor() {
        return Integer.toString(major);
    }

    /**
     * getMinor method.
     * @return  minor version
     */
    public String getMinor() {
        return Integer.toString(minor);
    }

    /**
     * getPatch method.
     * @return  patch phase
     */
    public String getPatch() {
        return Integer.toString(patch);
    }

    /**
     * getIteration method.
     * @return  iteration step
     */
    public String getIteration() {
        String i = "";
        if (iteration != null) {
            i = Integer.toString(iteration);
        }
        return i;
    }

    /**
     * getFull method.
     * @return  full version
     */
    public String getFull() {
        final StringBuilder ret = getVersionBuilder();
        if (releaseState.length() > 0) {
            ret.append('-').append(releaseState);
        }
        return ret.toString();
    }

    /**
     * getVersion method.
     * @return  full version omitting release state
     */
    public String getVersion() {
        final StringBuilder ret = getVersionBuilder();
        return ret.toString();
    }

    /**
     * getVersionBuilder method.
     * @return  version as StringBuilder
     */
    public StringBuilder getVersionBuilder() {
        final StringBuilder ret = new StringBuilder();
        ret.append(major);
        ret.append('.').append(minor);
        ret.append('.').append(patch);
        if (iteration != null) {
            ret.append('.').append(iteration);
        }
        return ret;
    }

}
