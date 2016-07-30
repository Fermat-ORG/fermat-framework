package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;

/**
 * The Class <code>package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.developer.chat.version_1.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatManagerException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/02/16
 * @version 1.0
 */
public class CantInitializeChatSupAppModuleManagerException extends CHTException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CHAT SUPAPP MODULE MANAGER EXCEPTION";

    public CantInitializeChatSupAppModuleManagerException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeChatSupAppModuleManagerException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeChatSupAppModuleManagerException(final String message) {
        this(message, null);
    }

    public CantInitializeChatSupAppModuleManagerException() {
        this(DEFAULT_MESSAGE);
    }
}
