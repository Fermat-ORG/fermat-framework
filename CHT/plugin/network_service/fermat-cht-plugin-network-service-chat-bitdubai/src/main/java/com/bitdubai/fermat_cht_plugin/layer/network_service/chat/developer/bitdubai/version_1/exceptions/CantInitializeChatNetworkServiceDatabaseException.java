package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;

/**
 * The Class <code>package com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Gabriel Araujo - (gabe_512@hotmail.com) on 30/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeChatNetworkServiceDatabaseException extends CHTException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CHAT NETWORK SERVICE DATABASE EXCEPTION";

    public CantInitializeChatNetworkServiceDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeChatNetworkServiceDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeChatNetworkServiceDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeChatNetworkServiceDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
