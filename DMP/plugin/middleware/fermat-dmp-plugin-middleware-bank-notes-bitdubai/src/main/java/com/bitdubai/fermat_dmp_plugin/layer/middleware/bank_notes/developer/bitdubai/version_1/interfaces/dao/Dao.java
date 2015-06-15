/*
 * @(#Dao.java 05/10/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.interfaces.dao;


// Packages and classes to import of jdk 1.7
import java.util.List;


/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.interfaces.dao.Dao</code> is a interface
 *     that define the methods for management the CRUD operations.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/10/2015
 * */
public interface Dao<I, O> {


    // Public instance methods declarations.
    /**
     *
     *  <p>Method that find an object by id.
     *
     *  @param id Object id.
     *  @return Object found.
     * */
    O get (I id);

    /**
     *
     *  <p>Method that list the all objects.
     *
     *  @return All objects.
     * */
    List<O> findAll ();

    /**
     *
     *  <p>Method that create a new object.
     *
     *  @param O Object to create.
     * */
    void create (O target);

    /**
     *
     *  <p>Method that update an object.
     *
     *  @param O Object to update.
     * */
    void update (O target);

    /**
     *
     *  <p>Method that delete an object.
     *
     *  @param id Object id.
     * */
    void delete (I id);
}