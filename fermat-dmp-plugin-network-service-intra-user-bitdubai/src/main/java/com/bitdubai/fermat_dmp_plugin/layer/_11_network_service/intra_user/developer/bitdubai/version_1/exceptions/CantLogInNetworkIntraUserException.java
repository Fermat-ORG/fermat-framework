/*
 * @#CantLogInNetworkIntraUserException.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.exceptions;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.exceptions.CantLogInNetworkIntraUserException</code> is
 * throw when error occurred initialize the data base
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantLogInNetworkIntraUserException extends Exception {

    /**
     * Constructor
     */
    public CantLogInNetworkIntraUserException(){
        super();
    }

    /**
     * Constructor whit parameter
     * @param msj
     */
    public CantLogInNetworkIntraUserException(String msj){
        super(msj);
    }
}
