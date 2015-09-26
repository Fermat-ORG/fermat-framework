/*
 * @(#Bill.java 05/10/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto;


// Packages and classes to import of jdk 1.7
import java.util.Map;

// Packages and classes to import of Middleware Bank Notes API.
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.commons.Features;


/**
 *
 *  <p>The class <code>com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.beans.dto.Bill</code> is a POJOs object for
 *     management the Bill DTO component.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/10/2015
 *  @see     {@link com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto.AbstractItem}
 * */
public final class Bill extends AbstractItem<Long> {


    // Private instance fields.
    /* Wallet type. */
    private Wallet wallet = null;


    // Public constructor declaration.
    /**
     *
     *  <p>Constructor declaration with arguments.
     *
     * */
    public Bill() {

        // Call to super class.
        super ();
    }

    /**
     *
     *  <p>Constructor declaration with arguments.
     *
     *  @param id Id item.
     *  @param name Name item.
     *  @param description  Description item.
     *  @param creation Creation date.
     *  @param update Update date.
     *  @param features Features item.
     *  @param wallet Wallet type.
     * */
    public Bill(Long id, String name, String description,
                Long creation, Long update, Map<Features, Object> features,
                Wallet wallet) {

        // Call to super class.
        super (id, name, description,
                creation, update, features);

        // Set the internal values.
        this.wallet = wallet;
    }


    // Public instance method declarations.
    /**
     *
     *  <p>Method that return the wallet type.
     *
     *  @return The wallet type.
     * */
    public Wallet getWallet () {

        // Return the value.
        return this.wallet;
    }

    /**
     *
     *  <p>Method that set the wallet type.
     *
     *  @param wallet The wallet type.
     * */
    public void setWallet (Wallet wallet) {

        // Set the value.
        this.wallet = wallet;
    }


    // Public instance methods declarations extends of java.lang.Object.
	/* (non-Javadoc)
	 * @see java.lang.Object#toString ()
	 */
    @Override
    public String toString () {

        // Custom implementations.
        return "Bill [Id=" + this.getId () + ", Name=" + this.getName () + "]";
    }
}