package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_pip_plugin.layer.network_service.system_monitor.developer.bitdubai.version_1.exceptions.CantInitializeSystemMonitorNetworkServiceDataBaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Miguel Celedon - (miguelceledon@outlook.com) on 14/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeSystemMonitorNetworkServiceDataBaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE SYSTEM MONITOR NETWORK SERVICE DATABASE EXCEPTION";

    public CantInitializeSystemMonitorNetworkServiceDataBaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeSystemMonitorNetworkServiceDataBaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeSystemMonitorNetworkServiceDataBaseException(final String message) {
        this(message, null);
    }

    public CantInitializeSystemMonitorNetworkServiceDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}
