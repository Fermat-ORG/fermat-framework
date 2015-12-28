package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.database.HoldCryptoMoneyTransactionDatabaseDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.MissingHoldCryptoDataException;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>HoldCryptoMoneyTransactionManager</code>
 * contains all the business logic of Bank Money Transaction
 *
 * Created by franklin on 17/11/15.
 */
public class HoldCryptoMoneyTransactionManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem  database system reference.
     * @param pluginId              of this module.
     */
    public HoldCryptoMoneyTransactionManager(final PluginDatabaseSystem pluginDatabaseSystem,
                                             final UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    private HoldCryptoMoneyTransactionDatabaseDao getHoldCrytoMoneyDao() {

        return new HoldCryptoMoneyTransactionDatabaseDao(pluginDatabaseSystem, pluginId);
    }

    public void saveHoldCryptoMoneyTransactionData(CryptoHoldTransaction cryptoHoldTransaction) throws DatabaseOperationException, MissingHoldCryptoDataException {
        getHoldCrytoMoneyDao().saveHoldCryptoTransactionData(cryptoHoldTransaction);
    }

    public List<CryptoHoldTransaction> getHoldCryptoMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException{
        return getHoldCrytoMoneyDao().getHoldCryptoTransactionList(filter);
    }
}
