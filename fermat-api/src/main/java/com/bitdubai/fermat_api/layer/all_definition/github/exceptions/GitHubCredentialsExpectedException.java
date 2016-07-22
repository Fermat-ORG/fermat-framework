package com.bitdubai.fermat_api.layer.all_definition.github.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Exception <code>com.bitdubai.fermat_api.layer.middleware.wallet_language.GitHubCredentialsExpectedException</code>
 * is thrown when a we cannot add a language string.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class GitHubCredentialsExpectedException extends FermatException {

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public GitHubCredentialsExpectedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
