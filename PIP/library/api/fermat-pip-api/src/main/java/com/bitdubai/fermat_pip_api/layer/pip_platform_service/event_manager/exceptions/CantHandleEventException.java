package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorgegonzalez on 2015.08.02..
 */
public class CantHandleEventException extends FermatException {

    private static final long serialVersionUID = 4150733208425219871L;
    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE THE EVENT";

    public CantHandleEventException(final String message, final Exception cause, final String context, final String possibleReason){
        super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
    }

    public CantHandleEventException(final String message, final Exception cause){
        this(DEFAULT_MESSAGE + message, cause, "", "");
    }

    public CantHandleEventException(final String message){
        this(message, null);
    }

    public CantHandleEventException(){
        this("");
    }
}
