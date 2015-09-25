/*
 * @(#Wallet.java 05/10/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_ccp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto;


/**
 *
 *  <p>The class <code>com.bitdubai.fermat_ccp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.beans.dto.Wallet</code> is a POJOs object for
 *     management the Wallet DTO component.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/10/2015
 *  @see     {@link com.bitdubai.fermat_ccp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto.AbstractItem}
 * */
public final class Wallet extends AbstractItem<Long> {


    // Public constructor declaration.
    /**
     *
     *  <p>Constructor declaration with arguments.
     *
     * */
    public Wallet () {

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
     * */
    public Wallet (Long id, String name, String description,
                   Long creation, Long update) {

        // Call to super class.
        super (id, name, description,
               creation, update);
    }


    // Public instance methods declarations extends of java.lang.Object.
	/* (non-Javadoc)
	 * @see java.lang.Object#toString ()
	 */
    @Override
    public String toString () {

        // Custom implementations.
        return "Wallet [Id=" + this.getId () + ", Name=" + this.getName () + "]";
    }
}