package org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.LockStatus;
import org.fermat.fermat_dap_api.layer.all_definition.util.ActorUtils;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantExecuteLockOperationException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.AssetUserWalletPluginRoot;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.exceptions.CantExecuteAssetUserTransactionException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.exceptions.CantGetBalanceRecordException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.functional.AssetUserWalletTransactionWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by franklin on 05/10/15.
 */
public final class AssetUserWalletDao {

    private final PluginFileSystem pluginFileSystem;
    private final UUID plugin;
    private final ActorAssetUserManager userManager;
    private final ActorAssetIssuerManager issuerManager;
    private final ActorAssetRedeemPointManager redeemPointManager;
    private final Database database;

    public AssetUserWalletDao(Database database,
                              PluginFileSystem pluginFileSystem,
                              UUID pluginId,
                              ActorAssetUserManager userManager,
                              ActorAssetIssuerManager issuerManager,
                              ActorAssetRedeemPointManager redeemPointManager) {

        this.pluginFileSystem = pluginFileSystem;
        this.database = database;
        this.plugin = pluginId;
        this.userManager = userManager;
        this.issuerManager = issuerManager;
        this.redeemPointManager = redeemPointManager;
    }

    private long getCurrentBookBalance() throws CantGetBalanceRecordException {
        return getCurrentBalance(BalanceType.BOOK);
    }

    private long getCurrentBalance(final BalanceType balanceType) throws CantGetBalanceRecordException {
        long balanceAmount = 0;
        if (balanceType == BalanceType.AVAILABLE) {
            for (DatabaseTableRecord record : getBalancesRecord()) {
                balanceAmount += record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            }
        } else {
            for (DatabaseTableRecord record : getBalancesRecord()) {
                balanceAmount += record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            }
        }
        return balanceAmount;
    }

    private List<DatabaseTableRecord> getBalancesRecord() throws CantGetBalanceRecordException {
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            //return balancesTable.getRecords().get(0);
            return balancesTable.getRecords();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record", exception, "Can't load balance table", "");
        }
    }

    private long getCurrentBalanceByAsset(BalanceType balanceType, String assetPublicKey) {
        try {
            long balanceAmount = 0;
            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            return balanceAmount;
        } catch (Exception exception) {
            return 0;
        }
    }

    private DatabaseTable getBalancesTable() {
        DatabaseTable databaseTable = database.getTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_NAME);
        return databaseTable;
    }

    private List<AssetUserWalletList> getCurrentBalanceByAssets() throws CantGetBalanceRecordException {
        return getCurrentBalanceByAsset();
    }

    private List<AssetUserWalletList> getCurrentBalanceByAsset() throws CantGetBalanceRecordException {
        List<AssetUserWalletList> userWalletBalances = new ArrayList<>();
        for (DatabaseTableRecord record : getBalancesRecord()) {
            AssetUserWalletList assetUserWalletBalance = new org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.functional.AssetUserWalletBalance();
            String assetPublicKey = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME);
            assetUserWalletBalance.setQuantityBookBalance(record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME));
            assetUserWalletBalance.setQuantityAvailableBalance(record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME));
            assetUserWalletBalance.setAvailableBalance(record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME));
            assetUserWalletBalance.setBookBalance(record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME));

            try {
                Set<CryptoAddress> cryptoAddresses = getAvailableAddressesForAsset(assetPublicKey);
                assetUserWalletBalance.setAddresses(cryptoAddresses);
            } catch (CantGetTransactionsException e) {
                e.printStackTrace();
            }
            try {
                PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(plugin, AssetUserWalletPluginRoot.PATH_DIRECTORY, assetPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                DigitalAsset asset = (DigitalAsset) XMLParser.parseXML(pluginTextFile.getContent(), new DigitalAsset());
                assetUserWalletBalance.setDigitalAsset(asset);
                List<AssetUserWalletTransaction> allTx = listsTransactionsByAssets(asset.getGenesisAddress());
                int lockedAssets = 0;
                Set<String> genesisTx = new HashSet<>();
                for (AssetUserWalletTransaction tx : allTx) {
                    genesisTx.add(tx.getGenesisTransaction());
                }
                for (String txHash : genesisTx) {
                    if (getLockStatusForMetadata(txHash) == LockStatus.LOCKED) {
                        lockedAssets++;
                    }
                }
                assetUserWalletBalance.setLockedAssets(lockedAssets);
            } catch (FileNotFoundException | CantCreateFileException | CantGetTransactionsException | RecordsNotFoundException | CantExecuteLockOperationException e) {
                e.printStackTrace();
            }
            userWalletBalances.add(assetUserWalletBalance);
        }
        return userWalletBalances;
    }

    /*
     * getBookBalance must get actual Book Balance global of Asset User wallet, select record from balances table
     */
    public long getBookBalance() throws CantCalculateBalanceException {
        try {
            return getCurrentBookBalance();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");

        }
    }

    /*
     * getBookBalance must get actual Book Balance global of Asset User wallet, select record from balances table
     */
    public List<AssetUserWalletList> getBalanceByAssets() throws CantCalculateBalanceException {
        try {
            return getCurrentBalanceByAssets();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");

        }
    }


    /*
    * getBookBalance must get actual Book Balance global of Asset User wallet, select record from balances table
    */
    public long getAvailableBalance() throws CantCalculateBalanceException {
        try {
            return getCurrentAvailableBalance();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
    * Add a new debit transaction.
    */
    public void addDebit(final AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetUserWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetUserWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(-availableAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(-bookAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());

            long quantityAvailableAmount = balanceType.equals(BalanceType.AVAILABLE) ? 1 : 0L;
            long quantityBookAmount = balanceType.equals(BalanceType.BOOK) ? 1 : 0L;
            long quantityAvailableRunningBalance = calculateQuantityAvailableRunningBalanceByAsset(-quantityAvailableAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long quantityBookRunningBalance = calculateQuantityBookRunningBalanceByAsset(-quantityBookAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());

            executeTransaction(assetUserWalletTransactionRecord, TransactionType.DEBIT, balanceType, availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
        } catch (CantGetBalanceRecordException | CantExecuteAssetUserTransactionException exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    /*
    * Add a new credit transaction.
    */
    public void addCredit(final AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterCreditException {
        try {
            startAssetLock(assetUserWalletTransactionRecord.getDigitalAssetMetadata());
            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetUserWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetUserWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(availableAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(bookAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());

            long quantityAvailableAmount = balanceType.equals(BalanceType.AVAILABLE) ? 1 : 0L;
            long quantityBookAmount = balanceType.equals(BalanceType.BOOK) ? 1 : 0L;
            long quantityAvailableRunningBalance = calculateQuantityAvailableRunningBalanceByAsset(quantityAvailableAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long quantityBookRunningBalance = calculateQuantityBookRunningBalanceByAsset(quantityBookAmount, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());

            executeTransaction(assetUserWalletTransactionRecord, TransactionType.CREDIT, balanceType, availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
        } catch (CantGetBalanceRecordException | CantExecuteAssetUserTransactionException exception) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetUserWalletTransaction> listsTransactionsByAssets(CryptoAddress cryptoAddress) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerUsetWallet = getAssetUserWalletTable();
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ASSET_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);

            databaseTableAssuerUsetWallet.loadToMemory();
            return createTransactionList(databaseTableAssuerUsetWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetUserWalletTransaction> listsTransactionsByAssets(BalanceType balanceType, TransactionType transactionType, CryptoAddress cryptoAddress) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerUsetWallet = getAssetUserWalletTable();
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ASSET_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

            databaseTableAssuerUsetWallet.loadToMemory();
            return createTransactionList(databaseTableAssuerUsetWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetUserWalletTransaction> listsTransactionsByAssets(BalanceType balanceType, TransactionType transactionType, String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerUsetWallet = getAssetUserWalletTable();
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

            databaseTableAssuerUsetWallet.loadToMemory();
            return createTransactionList(databaseTableAssuerUsetWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<AssetUserWalletTransaction> listsTransactionsByAssets(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerUsetWallet = getAssetUserWalletTable();
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUsetWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUsetWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerUsetWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerUsetWallet.loadToMemory();
            return createTransactionList(databaseTableAssuerUsetWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetUserWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerUserWallet = getAssetUserWalletTable();

            databaseTableAssuerUserWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerUserWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerUserWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUserWallet.addFilterOrder(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            databaseTableAssuerUserWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerUserWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerUserWallet.loadToMemory();

            return createTransactionList(databaseTableAssuerUserWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetUserWalletTransaction> getTransactionsByTransactionType(TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerUserWallet = getAssetUserWalletTable();

            databaseTableAssuerUserWallet.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerUserWallet.addFilterOrder(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            databaseTableAssuerUserWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerUserWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerUserWallet.loadToMemory();

            return createTransactionList(databaseTableAssuerUserWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public ActorAssetIssuer getActorByAsset(DigitalAsset digitalAsset) {
        String publicKey = digitalAsset.getIdentityAssetIssuer().getPublicKey();
        return (ActorAssetIssuer) ActorUtils.getActorFromPublicKey(publicKey, Actors.DAP_ASSET_ISSUER, userManager, redeemPointManager, issuerManager);
    }

    private void newAddress(DigitalAsset asset, Boolean available) throws CantExecuteLockOperationException {
        try {
            DatabaseTable addressesTable = getAddressesTable();
            addressesTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME, asset.getGenesisAddress().getAddress(), DatabaseFilterType.EQUAL);
            addressesTable.loadToMemory();
            if (!addressesTable.getRecords().isEmpty()) {
                return; //We already registered this asset
            }
            DatabaseTableRecord record = addressesTable.getEmptyRecord();
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_ID_COLUMN_NAME, UUID.randomUUID().toString());
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_ASSET_PUBLICKEY_COLUMN_NAME, asset.getPublicKey());
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME, asset.getGenesisAddress().getAddress());
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME, asset.getGenesisAddress().getCryptoCurrency().getCode());
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_AVAILABLE_COLUMN_NAME, available.toString());
            addressesTable.insertRecord(record);
        } catch (Exception e) {
            throw new CantExecuteLockOperationException(e);
        }
    }

    private void addressSpent(DigitalAsset asset) throws CantExecuteLockOperationException {
        try {
            DatabaseTable addressesTable = getAddressesTable();
            addressesTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME, asset.getGenesisAddress().getAddress(), DatabaseFilterType.EQUAL);
            addressesTable.loadToMemory();
            if (addressesTable.getRecords().isEmpty()) {
                //We don't have this address?
                newAddress(asset, Boolean.FALSE);
                return;
            }
            DatabaseTableRecord record = addressesTable.getRecords().get(0);
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_AVAILABLE_COLUMN_NAME, Boolean.FALSE.toString());
            addressesTable.updateRecord(record);
        } catch (Exception e) {
            throw new CantExecuteLockOperationException(e);
        }
    }

    private Set<CryptoAddress> getAvailableAddressesForAsset(String assetPk) throws CantGetTransactionsException {
        try {
            DatabaseTable addressesTable = getAddressesTable();
            addressesTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_ASSET_PUBLICKEY_COLUMN_NAME, assetPk, DatabaseFilterType.EQUAL);
            addressesTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_AVAILABLE_COLUMN_NAME, Boolean.TRUE.toString(), DatabaseFilterType.EQUAL);
            addressesTable.loadToMemory();

            Set<CryptoAddress> toReturn = new HashSet<>();
            for (DatabaseTableRecord record : addressesTable.getRecords()) {
                String address = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME);
                CryptoCurrency cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME));
                CryptoAddress cryptoAddress = new CryptoAddress(address, cryptoCurrency);
                toReturn.add(cryptoAddress);
            }
            return toReturn;
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    private void startAssetLock(DigitalAssetMetadata metadata) throws CantExecuteLockOperationException {
        try {
            DatabaseTable lockTable = getLockTable();
            lockTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_GENESIS_TX_COLUMN_NAME, metadata.getGenesisTransaction(), DatabaseFilterType.EQUAL);
            lockTable.loadToMemory();
            if (!lockTable.getRecords().isEmpty()) {
                return; //We already registered this asset
            }
            DatabaseTableRecord record = lockTable.getEmptyRecord();
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_METADATA_ID_COLUMN_NAME, metadata.getMetadataId().toString());
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_GENESIS_TX_COLUMN_NAME, metadata.getGenesisTransaction());
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_STATUS_COLUMN_NAME, LockStatus.UNLOCKED.getCode());
            lockTable.insertRecord(record);
        } catch (Exception e) {
            throw new CantExecuteLockOperationException(e);
        }
    }

    public void lockFunds(DigitalAssetMetadata assetMetadata) throws RecordsNotFoundException, CantExecuteLockOperationException {
        updateLockStatus(assetMetadata.getGenesisTransaction(), LockStatus.LOCKED);
    }

    public void unlockFunds(DigitalAssetMetadata assetMetadata) throws RecordsNotFoundException, CantExecuteLockOperationException {
        updateLockStatus(assetMetadata.getGenesisTransaction(), LockStatus.UNLOCKED);
    }

    private void updateLockStatus(String genesisTx, LockStatus lockStatus) throws RecordsNotFoundException, CantExecuteLockOperationException {
        try {
            DatabaseTable lockTable = getLockTable();
            lockTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_GENESIS_TX_COLUMN_NAME, genesisTx, DatabaseFilterType.EQUAL);
            lockTable.loadToMemory();
            if (lockTable.getRecords().isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = lockTable.getRecords().get(0);
            record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_STATUS_COLUMN_NAME, lockStatus.getCode());
            lockTable.updateRecord(record);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException e) {
            throw new CantExecuteLockOperationException(e);
        }
    }

    private LockStatus getLockStatusForMetadata(String genesisTx) throws RecordsNotFoundException, CantExecuteLockOperationException {
        try {
            DatabaseTable lockTable = getLockTable();
            lockTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_GENESIS_TX_COLUMN_NAME, genesisTx, DatabaseFilterType.EQUAL);
            lockTable.loadToMemory();
            if (lockTable.getRecords().isEmpty()) throw new RecordsNotFoundException();
            DatabaseTableRecord record = lockTable.getRecords().get(0);
            return LockStatus.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_STATUS_COLUMN_NAME));
        } catch (CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new CantExecuteLockOperationException(e);
        }
    }

    public DigitalAssetMetadata getDigitalAssetMetadata(String transactionHash) throws CantGetDigitalAssetFromLocalStorageException {
        String context = "Path: " + AssetUserWalletPluginRoot.PATH_DIRECTORY + " - Tx Hash: " + transactionHash;
        try {
            String metadataXML = pluginFileSystem.getTextFile(plugin, AssetUserWalletPluginRoot.PATH_DIRECTORY, transactionHash, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT).getContent();
            return (DigitalAssetMetadata) XMLParser.parseXML(metadataXML, new DigitalAssetMetadata());
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetDigitalAssetFromLocalStorageException(e, context, "The path could be wrong or there was an error creating the file.");
        }
    }

    public DigitalAsset getDigitalAsset(String assetPublicKey) throws CantGetDigitalAssetFromLocalStorageException {
        String context = "Path: " + AssetUserWalletPluginRoot.PATH_DIRECTORY + " - Asset PK: " + assetPublicKey;
        try {
            String assetXML = pluginFileSystem.getTextFile(plugin, AssetUserWalletPluginRoot.PATH_DIRECTORY, assetPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT).getContent();
            return (DigitalAsset) XMLParser.parseXML(assetXML, new DigitalAsset());
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetDigitalAssetFromLocalStorageException(e, context, "The path could be wrong or there was an error creating the file.");
        }
    }

    public void updateMemoField(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {
        try {
            DatabaseTable databaseTableAssuerUserWalletBalance = getBalancesTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */

            databaseTableAssuerUserWalletBalance.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
            for (DatabaseTableRecord record : databaseTableAssuerUserWalletBalance.getRecords()) {
                record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_MEMO_COLUMN_NAME, memo);
                databaseTableAssuerUserWalletBalance.updateRecord(record);
            }
            databaseTableAssuerUserWalletBalance.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error", cantLoadTableToMemory, "Error load Transaction table" + transactionID.toString(), "");

        } catch (CantUpdateRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error", cantUpdateRecord, "Error update memo of Transaction " + transactionID.toString(), "");
        } catch (Exception exception) {
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private long calculateQuantityAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getQuantityCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getQuantityCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getQuantityCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }

    private long getQuantityCurrentBalanceByAsset(BalanceType balanceType, String assetPublicKey) {
        try {
            long balanceAmount = 0;
            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME);
            return balanceAmount;
        } catch (Exception exception) {
            return 0;
        }
    }

    private long calculateQuantityBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentQuantityBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentQuantityBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getQuantityCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

    private boolean isTransactionInTable(final String transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable assetUserWalletTable = getAssetUserWalletTable();
        assetUserWalletTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        assetUserWalletTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        assetUserWalletTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        assetUserWalletTable.loadToMemory();
        return !assetUserWalletTable.getRecords().isEmpty();
    }

    // Read record data and create transactions list
    private List<AssetUserWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records) throws InvalidParameterException, RecordsNotFoundException, CantExecuteLockOperationException {
        List<AssetUserWalletTransaction> transactions = new ArrayList<>();

        for (DatabaseTableRecord record : records)
            transactions.add(constructAsseUserWalletTransactionFromRecord(record));

        return transactions;
    }

    private AssetUserWalletTransaction constructAsseUserWalletTransactionFromRecord(DatabaseTableRecord record) throws InvalidParameterException, RecordsNotFoundException, CantExecuteLockOperationException {
        String transactionId = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_VERIFICATION_ID_COLUMN_NAME);
        String assetPublicKey = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME);
        String transactionHash = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TRANSACTION_HASH_COLUMN_NAME);
        TransactionType transactionType = TransactionType.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME));
        CryptoAddress addressFrom = new CryptoAddress();
        addressFrom.setAddress(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESS_FROM_COLUMN_NAME));
        CryptoAddress addressTo = new CryptoAddress();
        addressTo.setAddress(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESS_TO_COLUMN_NAME));
        String actorFromPublicKey = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_COLUMN_NAME);
        String actorToPublicKey = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_COLUMN_NAME);
        Actors actorFromType = Actors.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType = Actors.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_TYPE_COLUMN_NAME));
        DAPActor actorFrom = ActorUtils.getActorFromPublicKey(actorFromPublicKey, actorFromType, userManager, redeemPointManager, issuerManager);
        DAPActor actorTo = ActorUtils.getActorFromPublicKey(actorToPublicKey, actorToType, userManager, redeemPointManager, issuerManager);
        BalanceType balanceType = BalanceType.getByCode(record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME));
        long amount = record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_AMOUNT_COLUMN_NAME);
        long runningBookBalance = record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance = record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp = record.getLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TIME_STAMP_COLUMN_NAME);
        String memo = record.getStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_MEMO_COLUMN_NAME);
        boolean isLocked = getLockStatusForMetadata(transactionHash) == LockStatus.LOCKED;
        return new AssetUserWalletTransactionWrapper(transactionId, transactionHash, assetPublicKey, transactionType, actorFrom, actorTo, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo, isLocked);

    }

    private void executeTransaction(final AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance, final long quantityAvailableRunningBalance, final long quantityBookRunningBalance) throws CantExecuteAssetUserTransactionException {
        try {
            DatabaseTableRecord assetUserWalletRecord = constructAssetUserWalletRecord(assetUserWalletTransactionRecord, transactionType, balanceType, availableRunningBalance, bookRunningBalance);//DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord assetBalanceRecord = constructAssetBalanceRecord(assetUserWalletTransactionRecord.getDigitalAsset(), availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
            manageAddress(assetUserWalletTransactionRecord.getDigitalAsset(), transactionType, balanceType);
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(getAssetUserWalletTable(), assetUserWalletRecord);

            DatabaseTable databaseTable = getBalancesTable();
            databaseTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            if (databaseTable.getRecords().isEmpty()) {
                transaction.addRecordToInsert(databaseTable, assetBalanceRecord);
            } else {
                transaction.addRecordToUpdate(databaseTable, assetBalanceRecord);
            }

            String assetMetadataInnerXML = XMLParser.parseObject(assetUserWalletTransactionRecord.getDigitalAssetMetadata());
            PluginTextFile metadataTextFile = pluginFileSystem.createTextFile(plugin, AssetUserWalletPluginRoot.PATH_DIRECTORY, assetUserWalletTransactionRecord.getGenesisTransaction(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            metadataTextFile.setContent(assetMetadataInnerXML);
            metadataTextFile.persistToMedia();

            PluginTextFile assetTextFile = pluginFileSystem.createTextFile(plugin, AssetUserWalletPluginRoot.PATH_DIRECTORY, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            assetTextFile.setContent(assetUserWalletTransactionRecord.getDigitalAsset().toString());
            assetTextFile.persistToMedia();

            database.executeTransaction(transaction);


        } catch (Exception e) {
            e.printStackTrace();
            throw new CantExecuteAssetUserTransactionException("Error to get balances record", e, "Can't load balance table", "");
        }
    }

    private void manageAddress(DigitalAsset digitalAsset, TransactionType transactionType, BalanceType balanceType) throws CantExecuteLockOperationException {
        newAddress(digitalAsset, Boolean.TRUE);
        if (transactionType == TransactionType.DEBIT && balanceType == BalanceType.BOOK) {
            addressSpent(digitalAsset);
        }
    }

    private DatabaseTableRecord getBalancesByAssetRecord(String assetPublicKey) throws CantGetBalanceRecordException {
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.addStringFilter(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record", exception, "Can't load balance table", "");
        }
    }

    private DatabaseTableRecord constructAssetBalanceRecord(DigitalAsset digitalAsset, long availableRunningBalance, long bookRunningBalance, long quantityAvailableRunningBalance, long quantityBookRunningBalance) {
        DatabaseTableRecord record = getBalancesTable().getEmptyRecord();
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, digitalAsset.getPublicKey());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_NAME_COLUMN_NAME, digitalAsset.getName());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME, digitalAsset.getDescription());
        record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME, quantityAvailableRunningBalance);
        record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME, quantityBookRunningBalance);
        return record;
    }

    private DatabaseTableRecord constructAssetUserWalletRecord(final AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) {
        DatabaseTableRecord record = getAssetUserWalletTable().getEmptyRecord();
        record.setUUIDValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TABLE_ID_COLUMN_NAME, UUID.randomUUID());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ASSET_PUBLIC_KEY_COLUMN_NAME, assetUserWalletTransactionRecord.getDigitalAsset().getPublicKey());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ASSET_CRYPTO_ADDRESS_COLUMN_NAME, assetUserWalletTransactionRecord.getDigitalAsset().getGenesisAddress().getAddress());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_VERIFICATION_ID_COLUMN_NAME, assetUserWalletTransactionRecord.getIdTransaction());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME, transactionType.getCode());
        record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_AMOUNT_COLUMN_NAME, assetUserWalletTransactionRecord.getAmount());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_MEMO_COLUMN_NAME, assetUserWalletTransactionRecord.getMemo());
        record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TIME_STAMP_COLUMN_NAME, assetUserWalletTransactionRecord.getTimestamp());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TRANSACTION_HASH_COLUMN_NAME, assetUserWalletTransactionRecord.getGenesisTransaction());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESS_FROM_COLUMN_NAME, assetUserWalletTransactionRecord.getAddressFrom().getAddress());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESS_TO_COLUMN_NAME, assetUserWalletTransactionRecord.getAddressTo().getAddress());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_RUNNING_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_COLUMN_NAME, assetUserWalletTransactionRecord.getActorFromPublicKey());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_COLUMN_NAME, assetUserWalletTransactionRecord.getActorToPublicKey());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_TYPE_COLUMN_NAME, assetUserWalletTransactionRecord.getActorFromType().getCode());
        record.setStringValue(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_TYPE_COLUMN_NAME, assetUserWalletTransactionRecord.getActorToType().getCode());
        return record;
    }

    private long calculateAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }


    private long calculateBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

    private DatabaseTable getAssetUserWalletTable() {
        return database.getTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TABLE_NAME);
    }

    private DatabaseTable getLockTable() {
        return database.getTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_TABLE_NAME);
    }

    private DatabaseTable getAddressesTable() {
        return database.getTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_TABLE_NAME);
    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException {
        return getCurrentBalance(BalanceType.AVAILABLE);
    }

    private long getCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }
}