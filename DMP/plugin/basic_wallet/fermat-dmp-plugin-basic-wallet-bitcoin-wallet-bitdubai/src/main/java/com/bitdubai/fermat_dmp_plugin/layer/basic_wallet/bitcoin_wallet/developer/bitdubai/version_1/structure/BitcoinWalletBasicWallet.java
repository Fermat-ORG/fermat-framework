package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantInitializeBitcoinWalletBasicException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCreateWalletException;
/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletBasicWallet implements BitcoinWalletWallet,DealsWithErrors, DealsWithPluginDatabaseSystem,DealsWithPluginFileSystem {

    /**
     * BitcoinWalletBasicWallet member variables.
     */
    private Database database;
    //private UUID internalWalletId;

    private final String WALLET_IDS_FILE_NAME = "walletsIds";

    Map<UUID, UUID> walletIds =  new HashMap<>();

    private BitcoinWalletBasicWalletDao bitcoinWalletBasicWalletDao;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

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

    /* This methid is called by the plug-in Root with the internal walletId as parameter
     * The wallet tries to open it's database. If it fails it is because the wallet was not
     * properly created before, so we end with an error in that case
     */
    public void initialize(UUID walletId) throws CantInitializeBitcoinWalletBasicException {

        if(walletId == null)
            throw new CantInitializeBitcoinWalletBasicException("InternalId is null",null,"Parameter walletId is null","loadWallet didn't find the asociated id");
        /**
         * I will try to open the wallets' database..
         */
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
            database.closeDatabase();
        }
        catch (CantOpenDatabaseException cantOpenDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            throw new CantInitializeBitcoinWalletBasicException("I can't open database",cantOpenDatabaseException,"WalletId: " + walletId.toString(),"");
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CantInitializeBitcoinWalletBasicException("Database does not exists",databaseNotFoundException,"WalletId: " + walletId.toString(),"");
        }
    }

    /**
     * BitcoinWalletWallet member variables.
     */

    /* At the moment of creation the plug-in root gives us the walletId
     * the external modules use ti identify this wallet. We will create
     * an internal walletId that we will store in a file.
     * This internal wallet Id will be used to name the database of this wallet
     * This is a security choise of implementation
     */
    // TODO: In this implementation it is not being considered how to solve problems while
    //       creating the wallet. For example, if the file persistToMemory method fails.
    //       The file insertion fail should delete the database created
    //       The internal Id generated should be checked before assigning it (check it was
    //       not previously assign)

    public UUID create(UUID walletId) throws CantCreateWalletException {
        /*
         * Este método crea la base de datos que se asocia a esta nueva wallet
         * Si la DB existe debería dar una excepción
         */

        BitcoinWalletDatabaseFactory databaseFactory = new BitcoinWalletDatabaseFactory();
        databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);

        // TODO: Until the Wallet MAnager create the wallets, we will use this internal id
        //       We need to change this in the near future
        UUID internalWalletId = UUID.randomUUID();
        //this.internalWalletId = UUID.fromString("35f1b7bc-886a-458a-8ae3-51a16a28889a"); //UUID.randomUUID();

        /**
         * We will create the database where I am going to store the information of this wallet.
         */
        try {
            // We will create wallet data base with new internal wallet id
            database =  databaseFactory.createDatabase(this.pluginId, internalWalletId);
            database.closeDatabase();
        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * The database cannot be created. I can not handle this situation.
             */
            throw new CantCreateWalletException("Database could not be created",cantCreateDatabaseException,"internalWalletId: "+internalWalletId.toString(),"");
        }

        PluginTextFile walletIdsFile;

        try{
            walletIdsFile = pluginFileSystem.getTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        }
        catch (CantCreateFileException cantCreateFileException ) {

            /**
             * If I can not save this file, then this plugin shouldn't be running at all.
             */

            throw new CantCreateWalletException("File could not be created (?)",cantCreateFileException,"File Name: "+WALLET_IDS_FILE_NAME,"");
        } catch (FileNotFoundException e) {
            throw new CantCreateWalletException("File could not be found",e,"File Name: "+WALLET_IDS_FILE_NAME,"");
        }

        try {
            walletIdsFile.loadFromMedia();

            /**
             * Now I read the content of the file and place it in memory.
             */
            String[] stringWalletIds = walletIdsFile.getContent().split(";" , -1);

            for (String stringWalletId : stringWalletIds ) {

                if(!stringWalletId.equals("")) {
                    /**
                     * Each record in the file has to values: the first is the external id of the wallet, and the
                     * second is the internal id of the wallet.
                     * * *
                     */
                    String[] idPair = stringWalletId.split(",", -1);
                    walletIds.put(UUID.fromString(idPair[0]),  UUID.fromString(idPair[1]));
                }
            }
        }
        catch (CantLoadFileException cantLoadFileException) {

            /**
             * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
             * to prevent the plug-in from running.
             *
             * In the future there should be implemented a method to deal with this situation.
             * * * *
             */
            throw new CantCreateWalletException("Can't load file content from media",cantLoadFileException,"","");
        }


        // We add the new Id pair
        walletIds.put(walletId,internalWalletId);

        /**
         * Now we will generate the file content.
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
            throw new CantCreateWalletException("Could not persist in file",cantPersistFileException,"stringBuilder: " + stringBuilder.toString(),"");
        }
        return internalWalletId;
    }


    @Override
    public UUID getWalletId() {

        // return this.internalWalletId;
        return UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    }





    @Override
    public List<BitcoinWalletTransactionRecord> getTransactions(int max, int offset) throws CantGetTransactionsException {
        try{
            database.openDatabase();
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(database);
            List<BitcoinWalletTransactionRecord> transactionRecords = bitcoinWalletBasicWalletDao.getTransactions(max, offset);
            database.closeDatabase();
            return transactionRecords;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetTransactionsException();
        } catch (CantGetTransactionsException exception){
            database.closeDatabase();
            throw exception;
        }
    }

    @Override
    public void setDescription(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {
        //update memo field
        try {
            database.openDatabase();
            bitcoinWalletBasicWalletDao = new BitcoinWalletBasicWalletDao(database);
            bitcoinWalletBasicWalletDao.updateMemoFiled(transactionID, memo);
            database.closeDatabase();
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, exception, "", "We couldn't open the database");
        } catch(CantStoreMemoException | CantFindTransactionException exception){
            database.closeDatabase();
            throw exception;
        }
    }

    @Override
    public BitcoinWalletBalance getAvailableBalance() {

        return new BitcoinWalletBasicWalletAvailableBalance(this.errorManager,this.pluginDatabaseSystem, this.database);
    }


    @Override
    public BitcoinWalletBalance getBookBalance() {
        return new BitcoinWalletBasicWalletBookBalance(this.errorManager, this.pluginDatabaseSystem, this.database);
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
