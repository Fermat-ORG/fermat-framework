package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 04/12/15.
 */
public class CantGetIssuerNetworkServiceMessageListException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "THERE WAS A PROBLEM WHILE RETRIEVING THE INCOMING MESSAGE LIST.";

    //CONSTRUCTORS

    public CantGetIssuerNetworkServiceMessageListException(final Exception cause, final String context) {
        super(DEFAULT_MESSAGE, cause, context, null);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
