/*
 * @(#Features.java 05/10/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.commons;


/**
 *
 *  <p>The class <code>com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.commons.Features</code> is an enum object
 *     that define the item's features.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/10/2015
 * */
public enum Features {


    // Define the features.
    Features ("ImageMiddleware");


    // Private instance fields declarations.
    /* Feature name. */
    private final String name;


    // Private constructor declarations.
    /**
     *
     *  <p>Unique constructor with arguments.
     *
     *  @param s Feature name.
     * */
    private Features (String s) {

        // Set the internal values.
        name = s;
    }


    // Public instance method declarations.
    /**
     *
     *  <p>Method that indicate if the same name.
     *
     *  @param otherName The name to check.
     *  @return Indicate if is the same name.
     * */
    public boolean equalsName (String otherName){

        // Return value.
        return (otherName == null) ? false : name.equals (otherName);
    }

    /**
     *
     *  <p>Method that indicate if the same name.
     *
     *  @return Indicate if is the same name.
     * */
    public String toString () {

        // Return the value.
        return name;
    }
}