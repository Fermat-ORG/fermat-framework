package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CabtStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantInitializeBitcoinWalletBasicException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletBasicWallet implements BitcoinWallet ,DealsWithErrors, DealsWithPluginDatabaseSystem {

    long balance =123;
    private Database database;



    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentityInterface member variables.
     */
    private UUID pluginId;


    /**
     * Constructor.
     */
    public BitcoinWalletBasicWallet(UUID pluginId){
        /**
         * The only one who can set the pluginId is the Plugin Root.
         */

        this.pluginId = pluginId;


    }

    //metodo create para crear la base de datos
    // metodo initialize abre la table, y si no existe da un error

    public void initialize(UUID walletId) throws CantInitializeBitcoinWalletBasicException {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
        }
        catch (CantOpenDatabaseException cantOpenDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeBitcoinWalletBasicException();
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, databaseNotFoundException);
            throw new CantInitializeBitcoinWalletBasicException();
        }

    }

    /**
     * BitcoinWallet member variables.
     */

    public void create(UUID walletId) throws CantInitializeBitcoinWalletBasicException {
        /*
         * Este método crea la base de datos que se asocia a esta nueva wallet
         * Si la DB existe debería dar una excepción
         */

        BitcoinWalletDatabaseFactory databaseFactory = new BitcoinWalletDatabaseFactory();
        databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
//creo la base de datos con un nuevo id de wallet interno
// ver como se asocia el id interno con el externo.
            this.database =  databaseFactory.createDatabase(this.pluginId, walletId);

        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * The database cannot be created. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
            throw new CantInitializeBitcoinWalletBasicException();
        }

    }



    @Override
    public UUID getWalletId() {
        return UUID.fromString("25428311-deb3-4064-93b2-69093e859871");
        //crear una id propia
        //cuando creo la wallet me llega el id de la wallet
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        //suma los debitos y los creditos los resta


        return balance;
    }
    //si ya llame al metodo con ese id ignoro
    //sino guardo la transaccion de debito o credito en la tabla

    @Override
    public void debit(BitcoinTransaction cryptoTransaction) throws CantRegisterDebitDebitException {
        this.balance -= cryptoTransaction.getAmount();

    }

    @Override
    public void credit(BitcoinTransaction cryptoTransaction) throws CantRegisterCreditException {
        this.balance += cryptoTransaction.getAmount();
    }

    @Override
    public List<BitcoinTransaction> getTransactions(int max, int offset) throws CantGetTransactionsException {

        return new ArrayList<>();
    }

    @Override
    public void setDescription(UUID transactionID, String memo) throws CabtStoreMemoException, CantFindTransactionException {
        //actualizar el memo
    }

    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }




}
