package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyWalletDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeBankMoneyWalletDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BANK MONEY WALLET DATABASE EXCEPTION";

    public CantInitializeBankMoneyWalletDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBankMoneyWalletDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBankMoneyWalletDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBankMoneyWalletDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}