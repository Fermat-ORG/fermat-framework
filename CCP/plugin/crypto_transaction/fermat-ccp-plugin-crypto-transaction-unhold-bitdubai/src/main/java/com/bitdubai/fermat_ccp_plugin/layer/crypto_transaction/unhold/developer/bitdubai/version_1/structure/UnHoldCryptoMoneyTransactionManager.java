package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.interfaces.CryptoUnholdTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.database.UnHoldCryptoMoneyTransactionDatabaseDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.MissingUnHoldCryptoDataException;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>UnHoldCryptoMoneyTransactionManager</code>
 * contains all the business logic of Bank Money Transaction
 *
 * Created by franklin on 17/11/15.
 */
public class UnHoldCryptoMoneyTransactionManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem  database system reference.
     * @param pluginId              of this module.
     */
    public UnHoldCryptoMoneyTransactionManager(final PluginDatabaseSystem pluginDatabaseSystem,
                                               final UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    private UnHoldCryptoMoneyTransactionDatabaseDao getUnHoldCrytoMoneyDao() {

        return new UnHoldCryptoMoneyTransactionDatabaseDao(pluginDatabaseSystem, pluginId);
    }

    public void saveUnHoldCryptoMoneyTransactionData(CryptoUnholdTransaction cryptoUnholdTransaction) throws DatabaseOperationException, MissingUnHoldCryptoDataException {
        getUnHoldCrytoMoneyDao().saveUnHoldCryptoTransactionData(cryptoUnholdTransaction);
    }

    public List<CryptoUnholdTransaction> getUnHoldCryptoMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException{
        return getUnHoldCrytoMoneyDao().getUnHoldCryptoTransactionList(filter);
    }
}
