package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantInitializeBitcoinLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantListSpendingException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantRevertLossProtectedTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionSummary;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

/**
 * Created by eze on 2015.06.23..
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 18/09/15.
 */
public class BitcoinWalletLossProtectedWallet implements BitcoinLossProtectedWallet {

    private static final String WALLET_IDS_FILE_NAME = "walletsIds";

    private Database database;

    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;
    private final Broadcaster broadcaster;
    private UUID exchangeProviderId;
    private CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter;



    public BitcoinWalletLossProtectedWallet(final ErrorManager errorManager,
                                    final PluginDatabaseSystem pluginDatabaseSystem,
                                    final PluginFileSystem pluginFileSystem,
                                    final UUID pluginId,
                                    final  Broadcaster broadcaster,
                                     final UUID exchangeProviderId,
                                    final CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter) {

        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.broadcaster = broadcaster;
        this.exchangeProviderId = exchangeProviderId;
        this.exchangeProviderFilterManagerproviderFilter = exchangeProviderFilterManagerproviderFilter;
    }

    //metodo create para crear la base de datos
    // metodo initialize abre la table, y si no existe da un error

    /* This methid is called by the plug-in Root with the internal walletId as parameter
     * The wallet tries to open it's database. If it fails it is because the wallet was not
     * properly created before, so we end with an error in that case
     */
    public void initialize(UUID walletId) throws CantInitializeBitcoinLossProtectedWalletException {
        if (walletId == null)
            throw new CantInitializeBitcoinLossProtectedWalletException("InternalId is null", null, "Parameter walletId is null", "loadWallet didn't find the asociated id");

        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeBitcoinLossProtectedWalletException("I can't open database", cantOpenDatabaseException, "WalletId: " + walletId.toString(), "");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CantInitializeBitcoinLossProtectedWalletException("Database does not exists", databaseNotFoundException, "WalletId: " + walletId.toString(), "");
        } catch (Exception exception) {
            throw new CantInitializeBitcoinLossProtectedWalletException(CantInitializeBitcoinLossProtectedWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


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

    public UUID create(String walletId) throws CantCreateWalletException {
        try {
            // TODO: Until the Wallet MAnager create the wallets, we will use this internal id
            //       We need to change this in the near future
            UUID internalWalletId = UUID.randomUUID();
            createWalletDatabase(internalWalletId);
            PluginTextFile walletIdsFile = createIdsFile();
            Map<String, UUID> walletsIdMap = getWalletIdsMap(walletIdsFile);
            walletsIdMap.put(walletId, internalWalletId);
            persistWalletIds(walletIdsFile, walletsIdMap);
            return internalWalletId;
        } catch (CantCreateWalletException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantCreateWalletException(CantCreateWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }

    }

    @Override
    public List<BitcoinLossProtectedWalletTransaction> listTransactions(BalanceType balanceType,
                                                           TransactionType transactionType,
                                                           int max,
                                                           int offset) throws CantListTransactionsException {

        try {
            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);

            return BitcoinWalletLossProtectedWalletDao.listTransactions(
                    balanceType,
                    transactionType,
                    max,
                    offset
            );

        } catch (CantListTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<BitcoinLossProtectedWalletSpend> listTransactionsSpending(UUID transactionId) throws CantListSpendingException {

        try {
            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);

            return BitcoinWalletLossProtectedWalletDao.listTransactionsSpending(transactionId);

        } catch (CantListTransactionsException exception) {
            throw new CantListSpendingException(CantListTransactionsException.DEFAULT_MESSAGE, exception, null, null);

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantListSpendingException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<BitcoinLossProtectedWalletSpend> listAllWalletSpending(BlockchainNetworkType blockchainNetworkType) throws CantListSpendingException {
        try {
            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);

            return BitcoinWalletLossProtectedWalletDao.listAllWalletSpending(blockchainNetworkType);

        } catch (CantListTransactionsException exception) {
            throw new CantListSpendingException(CantListTransactionsException.DEFAULT_MESSAGE, exception, null, null);

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantListSpendingException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<BitcoinLossProtectedWalletTransaction> listTransactionsByActor(final String actorPublicKey,
                                                                  final BalanceType balanceType,
                                                                  final int max,
                                                                  final int offset) throws CantListTransactionsException {

        try {

            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);

            return BitcoinWalletLossProtectedWalletDao.listTransactionsByActor(
                    actorPublicKey,
                    balanceType,
                    max,
                    offset
            );

        } catch (CantListTransactionsException exception) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<BitcoinLossProtectedWalletTransaction> listTransactionsByActorAndType(final String actorPublicKey,
                                                                         final BalanceType balanceType,
                                                                         final TransactionType transactionType,
                                                                         final int max,
                                                                         final int offset) throws CantListTransactionsException {

        try {

            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);

            return BitcoinWalletLossProtectedWalletDao.listTransactionsByActorAndType(
                    actorPublicKey,
                    balanceType,
                    transactionType,
                    max,
                    offset
            );

        } catch (CantListTransactionsException exception) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<BitcoinLossProtectedWalletTransaction> listLastActorTransactionsByTransactionType(final BalanceType balanceType,
                                                                                     final TransactionType transactionType,
                                                                                     final int max,
                                                                                     final int offset,
                                                                                      final BlockchainNetworkType blockchainNetworkType) throws CantListTransactionsException {

        try {
            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);

            return BitcoinWalletLossProtectedWalletDao.listLastActorTransactionsByTransactionType(
                    balanceType,
                    transactionType,
                    max,
                    offset,
                    blockchainNetworkType
            );
        } catch (CantListTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void setTransactionDescription(final UUID transactionID,
                                          final String memo) throws CantStoreMemoException,
            CantFindTransactionException {
        try {
            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);

            BitcoinWalletLossProtectedWalletDao.updateMemoFiled(
                    transactionID,
                    memo
            );

        } catch (CantStoreMemoException | CantFindTransactionException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public BitcoinLossProtectedWalletBalance getBalance(final BalanceType balanceType) {

        switch (balanceType) {
               case AVAILABLE:
                return new BitcoinWalletLossProtectedWalletAvailableBalance(database,this.broadcaster,exchangeProviderId,exchangeProviderFilterManagerproviderFilter);
            case BOOK:
                return new BitcoinWalletLossProtectedWalletBookBalance(database,this.broadcaster,exchangeProviderId,exchangeProviderFilterManagerproviderFilter);
            default:
                return new BitcoinWalletLossProtectedWalletAvailableBalance(database,this.broadcaster,exchangeProviderId,exchangeProviderFilterManagerproviderFilter);
        }
    }


    @Override
    public BitcoinLossProtectedWalletTransactionSummary getActorTransactionSummary(final String actorPublicKey,
                                                                      final BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        try {
            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);

            return BitcoinWalletLossProtectedWalletDao.getActorTransactionSummary(
                    actorPublicKey,
                    balanceType
            );

        } catch (CantGetActorTransactionSummaryException exception) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetActorTransactionSummaryException(CantGetActorTransactionSummaryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private void createWalletDatabase(final UUID internalWalletId) throws CantCreateWalletException {
        try {
            BitcoinLossProtectedWalletDatabaseFactory databaseFactory = new BitcoinLossProtectedWalletDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
            database = databaseFactory.createDatabase(this.pluginId, internalWalletId);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw new CantCreateWalletException("Database could not be created", cantCreateDatabaseException, "internalWalletId: " + internalWalletId.toString(), "");
        }
    }

    private PluginTextFile createIdsFile() throws CantCreateWalletException {
        try {
            return pluginFileSystem.getTextFile(pluginId, DeviceDirectory.LOCAL_WALLETS.getName(), WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException cantCreateFileException) {
            throw new CantCreateWalletException("File could not be created (?)", cantCreateFileException, "File Name: " + WALLET_IDS_FILE_NAME, "");
        } catch (FileNotFoundException e) {
            throw new CantCreateWalletException("File could not be found", e, "File Name: " + WALLET_IDS_FILE_NAME, "");
        }
    }

    private Map<String, UUID> getWalletIdsMap(final PluginTextFile walletIdsFile) throws CantCreateWalletException {

        try {

            Map<String, UUID> walletIds = new HashMap<>();
            walletIdsFile.loadFromMedia();
            String[] stringWalletIds = walletIdsFile.getContent().split(";");

            for (String stringWalletId : stringWalletIds) {

                if (!stringWalletId.equals("")) {
                    String[] idPair = stringWalletId.split(",");
                    walletIds.put(idPair[0], UUID.fromString(idPair[1]));
                }
            }

            return walletIds;

        } catch (CantLoadFileException exception) {
            throw new CantCreateWalletException("Can't load file content from media", exception, "", "");
        }
    }

    private void persistWalletIds(final PluginTextFile walletIdsFile,
                                  final Map<String, UUID> walletsIdMap) throws CantCreateWalletException {

        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry pair : walletsIdMap.entrySet()) {
            stringBuilder
                    .append(pair.getKey().toString())
                    .append(",")
                    .append(pair.getValue().toString())
                    .append(";");
        }

        walletIdsFile.setContent(stringBuilder.toString());

        try {
            walletIdsFile.persistToMedia();
        } catch (CantPersistFileException cantPersistFileException) {
            throw new CantCreateWalletException("Could not persist in file", cantPersistFileException, "stringBuilder: " + stringBuilder.toString(), "");
        }
    }

    @Override
    public void deleteTransaction(UUID transactionID) throws CantFindTransactionException {
        try {
            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);
            BitcoinWalletLossProtectedWalletDao.deleteTransaction(transactionID);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
        }
    }

    @Override
    public void revertTransaction(BitcoinLossProtectedWalletTransactionRecord transactionRecord, boolean credit) throws CantRevertLossProtectedTransactionException {
        try {

            //update transaction like reversed
            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);
            BitcoinWalletLossProtectedWalletDao.updateTransactionState(transactionRecord.getTransactionId(), TransactionState.REVERSED);
            //change balance

            if(credit)
                this.getBalance(BalanceType.AVAILABLE).revertCredit(transactionRecord);
            else
                this.getBalance(BalanceType.BOOK).revertCredit(transactionRecord);

        } catch (Exception exception) {
            throw new CantRevertLossProtectedTransactionException("Could not revert transaction", exception, "Database error" , "");
        }
    }

    @Override
    public BitcoinLossProtectedWalletTransaction getTransactionById(UUID transactionID) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException{
        try {
            BitcoinWalletLossProtectedWalletDao BitcoinWalletLossProtectedWalletDao = new BitcoinWalletLossProtectedWalletDao(database);
            return BitcoinWalletLossProtectedWalletDao.selectTransaction(transactionID);
        } catch (CantFindTransactionException e) {
            throw new CantFindTransactionException("Could not get transaction information", e, "Database error" , "");
        }
    }

}