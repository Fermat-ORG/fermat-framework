package com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * <p>The exception <code>com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.exceptions.CantPersistDeviceUserException</code>
 * is thrown when i cannot persist the file with the device user information.
 * <p/>
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 27/06/2015.
 */
public class CantPersistDeviceUserException extends FermatException {
    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantPersistDeviceUserException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
