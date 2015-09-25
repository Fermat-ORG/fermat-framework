/*
 * @(#Depot.java 05/14/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_ccp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto;


// Packages and classes to import of jdk 1.7
import java.util.Map;

// Packages and classes to import of Middleware Bank Notes API.
import com.bitdubai.fermat_ccp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.commons.Features;


/**
 *
 *  <p>The class <code>com.bitdubai.fermat_ccp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.beans.dto.Depot</code> is a POJOs object for
 *     management the Depot DTO component.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/14/2015
 *  @see     {@link AbstractItem}
 * */
public final class Depot extends AbstractItem<Long> {


    // Public constructor declaration.
    /**
     *
     *  <p>Constructor declaration with arguments.
     *
     * */
    public Depot() {

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
     * */
    public Depot(Long id, String name, String description,
                 Long creation, Long update, Map<Features, Object> features) {

        // Call to super class.
        super (id, name, description,
               creation, update, features);
    }


    // Public instance methods declarations extends of java.lang.Object.
	/* (non-Javadoc)
	 * @see java.lang.Object#toString ()
	 */
    @Override
    public String toString () {

        // Custom implementations.
        return "Depot [Id=" + this.getId () + ", Name=" + this.getName () + "]";
    }
}