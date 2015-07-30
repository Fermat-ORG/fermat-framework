package com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.exceptions.CantInitializeWalletRuntimeEngineDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Fursyfer Matias - (matiasfurszyfer@gmail.com) on 30/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeWalletRuntimeEngineDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE WALLET RUNTIME ENGINE DATABASE EXCEPTION";

    public CantInitializeWalletRuntimeEngineDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeWalletRuntimeEngineDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeWalletRuntimeEngineDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeWalletRuntimeEngineDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}