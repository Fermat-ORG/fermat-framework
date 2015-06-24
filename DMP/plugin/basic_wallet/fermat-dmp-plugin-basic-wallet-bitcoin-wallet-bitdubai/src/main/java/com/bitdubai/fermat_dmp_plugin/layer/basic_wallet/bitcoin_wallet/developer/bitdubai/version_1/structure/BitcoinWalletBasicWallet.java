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
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCreateWalletException;
/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletBasicWallet implements BitcoinWallet ,DealsWithErrors, DealsWithPluginDatabaseSystem,DealsWithPluginFileSystem {

    /**
     * BitcoinWalletBasicWallet member variables.
     */

    long balance =123;
    private Database database;
    UUID internalWalletId;

    private final String WALLET_IDS_FILE_NAME = "walletsIds";

    private Map<UUID, UUID> walletIds =  new HashMap();

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

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

    public void create(UUID walletId) throws CantCreateWalletException {
        /*
         * Este método crea la base de datos que se asocia a esta nueva wallet
         * Si la DB existe debería dar una excepción
         */

        BitcoinWalletDatabaseFactory databaseFactory = new BitcoinWalletDatabaseFactory();
        databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

        this.internalWalletId = UUID.randomUUID();

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            //I will create wallet data base with new internal wallet id
            this.database =  databaseFactory.createDatabase(this.pluginId, this.internalWalletId);

        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * The database cannot be created. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
            throw new CantCreateWalletException();
        }

        /**
         * Now I will add this wallet to the list of wallets managed by the plugin.
         */
        walletIds.put(walletId,this.internalWalletId);

        PluginTextFile walletIdsFile = null;

        try{
            walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        }
        catch (CantCreateFileException cantCreateFileException ) {

            /**
             * If I can not save this file, then this plugin shouldn't be running at all.
             */
            System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
            cantCreateFileException.printStackTrace();
            throw new CantCreateWalletException();
        }

        /**
         * I will generate the file content.
         */
        StringBuilder stringBuilder = new StringBuilder(walletIds.size() * 72);

        Iterator iterator = walletIds.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            stringBuilder.append(pair.getKey().toString() + "," + pair.getValue().toString() + ";");
            iterator.remove();
        }


        /**
         * Now I set the content.
         */
        walletIdsFile.setContent(stringBuilder.toString());

        try{
            walletIdsFile.persistToMedia();
        }
        catch (CantPersistFileException cantPersistFileException) {
            /**
             * If I can not save the id of the new wallet created, then this method fails.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
            throw new CantCreateWalletException();
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


    /*
     * NOTA:
     *  El debit y el credit debería mirar primero si la tramsacción que
     *  se quiere aplicar existe. Si no existe aplica los cambios normalmente, pero si existe
     *  debería ignorar la transacción.
     */
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

    /**
     * DealsWithPluginFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }


}
