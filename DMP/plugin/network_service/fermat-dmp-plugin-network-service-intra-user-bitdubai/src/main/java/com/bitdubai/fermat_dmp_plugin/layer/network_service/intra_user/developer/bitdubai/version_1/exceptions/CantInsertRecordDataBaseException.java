/*
 * @#CantInsertRecordDataBaseException.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException</code> is
 * throw when error occurred inserting new record in a table of the data base
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInsertRecordDataBaseException extends Exception {

    /**
     * Constructor
     */
    public CantInsertRecordDataBaseException(){
        super();
    }

    /**
     * Constructor whit parameter
     * @param msj
     */
    public CantInsertRecordDataBaseException(String msj){
        super(msj);
    }
}
