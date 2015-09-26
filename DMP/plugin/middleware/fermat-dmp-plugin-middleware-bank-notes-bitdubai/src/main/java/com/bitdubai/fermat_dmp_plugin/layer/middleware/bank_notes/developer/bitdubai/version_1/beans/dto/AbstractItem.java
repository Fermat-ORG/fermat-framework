/*
 * @(#AbstractItem.java 05/10/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto;


// Packages and classes to import of jdk 1.7
import java.util.Map;

// Packages and classes to import of Middleware Bank Notes API.
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.commons.Features;
import static com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.commons.Default._STRING;


/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.beans.dto.AbstractItem</code> is a POJOs object for
 *     management the commons item information.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/10/2015
 * */
public abstract class AbstractItem<T> {


    // Private instance fields.
    /* Id item. */
    private T id = null;

    /* Name item. */
    private String name = _STRING;

    /* Description item. */
    private String description = _STRING;

    /* Creation item. */
    private Long creation = null;

    /* Update item. */
    private Long   update = null;

    /* Features. */
    private Map<Features, Object> features = null;


    // Public constructor declaration.
    /**
     *
     *  <p>Constructor declaration without arguments.
     *
     * */
    public AbstractItem () {

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
    public AbstractItem (T id, String name, String description,
                         Long creation, Long update) {

        // Call to super class.
        super ();

        // Set the internal values.
        this.id   = id;
        this.name = name;
        this.description = description;
        this.creation    = creation;
        this.update      = update;
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
    public AbstractItem (T id, String name, String description,
                         Long creation, Long update, Map<Features, Object> features) {

        // Call to super class.
        super ();

        // Set the internal values.
        this.id   = id;
        this.name = name;
        this.description = description;
        this.creation    = creation;
        this.update      = update;
        this.features    = features;
    }


    // Public instance method declarations.
    /**
     *
     *  <p>Method that return the item id.
     *
     *  @return Item id.
     * */
    public T getId () {

        // Return the value.
        return this.id;
    }

    /**
     *
     *  <p>Method that set the item id.
     *
     *  @param id Item id.
     * */
    public void setId (T id) {

        // Set the value.
        this.id = id;
    }

    /**
     *
     *  <p>Method that return the item name.
     *
     *  @return Item name.
     * */
    public String getName () {

        // Return the value.
        return this.name;
    }

    /**
     *
     *  <p>Method that set the item name.
     *
     *  @param name Item name.
     * */
    public void setName (String name) {

        // Set the value.
        this.name = name;
    }

    /**
     *
     *  <p>Method that return the item description.
     *
     *  @return Item description.
     * */
    public String getDescription () {

        // Return the value.
        return this.description;
    }

    /**
     *
     *  <p>Method that set the item description.
     *
     *  @param description Item description.
     * */
    public void setDescription (String description) {

        // Set the value.
        this.description = description;
    }

    /**
     *
     *  <p>Method that return the item creation.
     *
     *  @return Item creation.
     * */
    public Long getCreation () {

        // Return the value.
        return this.creation;
    }

    /**
     *
     *  <p>Method that set the item creation.
     *
     *  @param creation Item creation.
     * */
    public void setCreation (Long creation) {

        // Set the value.
        this.creation = creation;
    }

    /**
     *
     *  <p>Method that return the item update.
     *
     *  @return Item update.
     * */
    public Long getUpdate () {

        // Return the value.
        return this.update;
    }

    /**
     *
     *  <p>Method that set the item update.
     *
     *  @param update Item update.
     * */
    public void setUpdate (Long update) {

        // Set the value.
        this.update = update;
    }

    /**
     *
     *  <p>Method that return the item features.
     *
     *  @return Item features.
     * */
    public Map<Features, Object> getFeatures () {

        // Return values.
        return this.features;
    }

    /**
     *
     *  <p>Method that set the item features.
     *
     *  @param features features.
     * */
    public void setFeatures (Map<Features, Object> features) {

        // Set values.
        this.features = features;
    }
}