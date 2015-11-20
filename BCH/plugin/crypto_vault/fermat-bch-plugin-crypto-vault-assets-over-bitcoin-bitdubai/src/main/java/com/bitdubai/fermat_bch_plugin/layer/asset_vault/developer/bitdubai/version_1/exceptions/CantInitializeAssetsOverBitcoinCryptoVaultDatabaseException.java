package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.exceptions.CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE ASSETSOVERBITCOIN CRYPTOVAULT DATABASE EXCEPTION";

    public CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}