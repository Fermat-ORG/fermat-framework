/*
 * @#CantDeleteRecordDataBaseException.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException</code> is
 * throw when error occurred deleting new record in a table of the data base
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantDeleteRecordDataBaseException extends Exception {

    /**
     * Constructor
     */
    public CantDeleteRecordDataBaseException(){
        super();
    }

    /**
     * Constructor whit parameter
     * @param msj
     */
    public CantDeleteRecordDataBaseException(String msj){
        super(msj);
    }
}
