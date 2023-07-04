/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.metadata;

import fr.asipsante.api.sign.enums.MetaDataType;

/**
 * The Class MetaDatum.
 */
public class MetaDatum {

    /** The type. */
    private final MetaDataType type;

    /** The value. */
    private String value;

    /**
     * Instantiates a new meta datum.
     *
     * @param type     the type
     * @param newValue the new value
     */
    public MetaDatum(final MetaDataType type, String newValue) {
        this.type = type;
        this.value = newValue;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public MetaDataType getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MetaData [\ntype=" + type + ", \nvalue=\n" + value + "\n]";
    }
}
