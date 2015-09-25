/*
 * @(#ItemByAccount.java 05/16/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_ccp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto;


// Packages and classes to import of Middleware Bank Notes API.
import static com.bitdubai.fermat_ccp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.commons.Default._STRING;


/**
 *
 *  <p>The class <code>com.bitdubai.fermat_ccp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.beans.dto.ItemByAccount</code> is a POJOs object for
 *     management the ItemByAccount DTO component.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/16/2015
 *  @see     {@link AbstractItem}
 *  @see     {@link Depot}
 * */
public final class ItemByAccount extends AbstractItem<Long> {


    // Private instance fields declarations.
    /* Item. */
    private AbstractItem<Long> item = null;

    /* Depot. */
    private Depot   depot = null;

    /* Account. */
    private String account = _STRING;


    // Public constructor declaration.
    /**
     *
     *  <p>Constructor declaration with arguments.
     *
     * */
    public ItemByAccount() {

        // Call to super class.
        super ();
    }

    /**
     *
     *  <p>Constructor declaration with arguments.
     *
     *  @param id Id item.
     *  @param item Item.
     *  @param depot  depot.
     *  @param account Account id.
     *  @param creation Creation date.
     *  @param update Update date.
     * */
    public ItemByAccount(Long id, AbstractItem<Long> item, Depot depot,
                         String account, Long creation, Long update) {

        // Call to super class.
        super (id, null, null,
               creation, update, null);
    }


    // Public instance method declarations.
    /**
     *
     *  <p>Method that return the Item.
     *
     *  @return Item.
     * */
    public AbstractItem<Long> getItem () {

        // Return the value.
        return this.item;
    }

    /**
     *
     *  <p>Method that set the item.
     *
     *  @param item Item.
     * */
    public void setItem (AbstractItem<Long> item) {

        // Set the value.
        this.item = item;
    }

    /**
     *
     *  <p>Method that return the Depot.
     *
     *  @return Depot.
     * */
    public Depot getDepot () {

        // Return the value.
        return this.depot;
    }

    /**
     *
     *  <p>Method that set the Depot.
     *
     *  @param depot Depot.
     * */
    public void setDepot (Depot depot) {

        // Set the value.
        this.depot = depot;
    }

    /**
     *
     *  <p>Method that return the account id.
     *
     *  @return Account id.
     * */
    public String getAccount () {

        // Return the value.
        return this.account;
    }

    /**
     *
     *  <p>Method that set the account id.
     *
     *  @param account Account id.
     * */
    public void setDepot (String account) {

        // Set the value.
        this.account = account;
    }


    // Public instance methods declarations extends of java.lang.Object.
	/* (non-Javadoc)
	 * @see java.lang.Object#toString ()
	 */
    @Override
    public String toString () {

        // Custom implementations.
        return "Account [Id=" + this.getId () + ", Item=" + this.item + ", Depot=" + this.depot + ", account id= " + this.account + "]";
    }
}