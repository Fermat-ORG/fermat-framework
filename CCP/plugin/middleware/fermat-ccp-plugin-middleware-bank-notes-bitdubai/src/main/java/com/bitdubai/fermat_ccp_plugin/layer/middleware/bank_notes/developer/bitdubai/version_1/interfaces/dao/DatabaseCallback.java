/*
 * @(#DatabaseCallback.java 05/16/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_ccp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.interfaces.dao;


// Packages and classes to import of bitDubai API.
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;

// Packages and classes to import of Middleware Bank Notes API.
import com.bitdubai.fermat_ccp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.beans.dto.Record;


/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_ccp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.interfaces.dao.DatabaseCallback</code> is a
 *     Callback interface for Database code.
 *     To be used with {@link com.bitdubai.fermat_ccp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.structure.dao.DatabaseTemplate}'s execution methods,
 *     often as anonymous classes within a method implementation.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@mac.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   05/16/2015
 * */
public interface DatabaseCallback {


    // Public methods declarations.
    /**
     *
     *  <p>Gets called by DatabaseTemplate.execute with an active
     *  Database session.
     *
     * <p>Allows for returning a result object created within the callback,
     * i.e. a domain object or a collection of domain objects.
     *
     * @param database Database object.
     * @return a result object (Table and record)
     */
    Record doExecute (Database database);
}