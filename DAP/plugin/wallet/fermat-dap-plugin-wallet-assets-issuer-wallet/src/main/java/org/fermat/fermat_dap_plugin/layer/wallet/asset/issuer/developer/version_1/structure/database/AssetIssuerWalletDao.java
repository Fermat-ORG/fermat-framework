package org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
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
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType;
import org.fermat.fermat_dap_api.layer.all_definition.util.ActorUtils;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantSaveStatisticException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionSummary;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetMovement;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.exceptions.CantExecuteAssetIssuerTransactionException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.exceptions.CantFindTransactionException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.exceptions.CantGetBalanceRecordException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional.AssetIssuerWalletBalance;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional.AssetIssuerWalletTransactionWrapper;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional.AssetMovementImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 28/09/15.
 */
public class AssetIssuerWalletDao {
    //TODO: Manejo de excepciones
    public static final String PATH_DIRECTORY = "asset-issuer-swap/";//digital-asset-swap/"
    PluginFileSystem pluginFileSystem;
    UUID plugin;
    private ActorAssetUserManager actorAssetUserManager;
    private ActorAssetIssuerManager actorAssetIssuerManager;
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    private Database database;

    public AssetIssuerWalletDao(Database database, PluginFileSystem pluginFileSystem, UUID pluginId, ActorAssetUserManager actorAssetUserManager, ActorAssetIssuerManager actorAssetIssuerManager, ActorAssetRedeemPointManager actorAssetRedeemPointManager) {
        this.database = database;
        this.pluginFileSystem = pluginFileSystem;
        this.plugin = pluginId;
        this.actorAssetIssuerManager = actorAssetIssuerManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.actorAssetRedeemPointManager = actorAssetRedeemPointManager;
    }

    /*
     * getBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
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
     * getBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
     */
    public List<AssetIssuerWalletList> getBalanceByAssets() throws CantCalculateBalanceException {
        try {
            return getCurrentBalanceByAssets();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");

        }
    }

    /*
    * getBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
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
    * getBalanceByAsset must get actual Balance global of Asset Issuer wallet, select record from balances table
    */
    public List<AssetIssuerWalletList> getBalanceByAsset() throws CantCalculateBalanceException {
        try {
            return getCurrentBalanceByAssets();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
    * Add a new debit transaction.
    */
    public void addDebit(final AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            System.out.println("Agregando Debito-----------------------------------------------------------");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetIssuerWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetIssuerWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(-availableAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(-bookAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());

            long quantityAvailableAmount = balanceType.equals(BalanceType.AVAILABLE) ? 1 : 0L;
            long quantityBookAmount = balanceType.equals(BalanceType.BOOK) ? 1 : 0L;
            long quantityAvailableRunningBalance = calculateQuantityAvailableRunningBalanceByAsset(-quantityAvailableAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long quantityBookRunningBalance = calculateQuantityBookRunningBalanceByAsset(-quantityBookAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());

            executeTransaction(assetIssuerWalletTransactionRecord, TransactionType.DEBIT, balanceType, availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
        } catch (CantGetBalanceRecordException | CantExecuteAssetIssuerTransactionException exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
    * Add a new credit transaction.
    */
    public void addCredit(final AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterCreditException {

        try {
            System.out.println("Agregando Credito-----------------------------------------------------------");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetIssuerWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetIssuerWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(availableAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(bookAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());

            long quantityAvailableAmount = balanceType.equals(BalanceType.AVAILABLE) ? 1 : 0L;
            long quantityBookAmount = balanceType.equals(BalanceType.BOOK) ? 1 : 0L;
            long quantityAvailableRunningBalance = calculateQuantityAvailableRunningBalanceByAsset(quantityAvailableAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long quantityBookRunningBalance = calculateQuantityBookRunningBalanceByAsset(quantityBookAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());

            executeTransaction(assetIssuerWalletTransactionRecord, TransactionType.CREDIT, balanceType, availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
        } catch (CantGetBalanceRecordException | CantExecuteAssetIssuerTransactionException exception) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetIssuerWalletTransaction> listsTransactionsByAssetsAll(BalanceType balanceType, TransactionType transactionType, String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerIssuerWallet = getAssetIssuerWalletTable();
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

            databaseTableAssuerIssuerWallet.loadToMemory();
            return createTransactionList(databaseTableAssuerIssuerWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of All Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetIssuerWalletTransaction> listsTransactionsByAssets(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerIssuerWallet = getAssetIssuerWalletTable();
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerIssuerWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerIssuerWallet.loadToMemory();
            return createTransactionList(databaseTableAssuerIssuerWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetIssuerWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerIssuerWallet = getAssetIssuerWalletTable();

            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.addFilterOrder(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            databaseTableAssuerIssuerWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerIssuerWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerIssuerWallet.loadToMemory();

            return createTransactionList(databaseTableAssuerIssuerWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }

    }

    public List<AssetIssuerWalletTransaction> getTransactionsByTransactionType(TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerIssuerWallet = getAssetIssuerWalletTable();

            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.addFilterOrder(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            databaseTableAssuerIssuerWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerIssuerWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerIssuerWallet.loadToMemory();

            return createTransactionList(databaseTableAssuerIssuerWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }

    }

    public AssetIssuerWalletTransactionSummary getActorTransactionSummary(String actorPublicKey, BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        return null;
    }

    public List<AssetIssuerWalletTransaction> distributeAssets(String assetPublicKey) throws CantGetTransactionsException {
        try {
            List<AssetIssuerWalletTransaction> assetIssuerWalletTransactions = listsTransactionsByAsset(assetPublicKey);
            return assetIssuerWalletTransactions;
        } catch (CantGetTransactionsException e) {
            throw new CantGetTransactionsException("Get List of Transactions", e, "Error load wallet table ", "Method: distributeAssets()");
        }

    }

    public void updateMemoField(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {
        try {
            DatabaseTable databaseTableAssuerIssuerWalletBalance = getBalancesTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */

            databaseTableAssuerIssuerWalletBalance.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
            for (DatabaseTableRecord record : databaseTableAssuerIssuerWalletBalance.getRecords()) {
                record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_MEMO_COLUMN_NAME, memo);
                databaseTableAssuerIssuerWalletBalance.updateRecord(record);
            }
            databaseTableAssuerIssuerWalletBalance.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error", cantLoadTableToMemory, "Error load Transaction table" + transactionID.toString(), "");

        } catch (CantUpdateRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error", cantUpdateRecord, "Error update memo of Transaction " + transactionID.toString(), "");
        } catch (Exception exception) {
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private List<AssetIssuerWalletTransaction> listsTransactionsByAsset(String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerIssuerWallet = getAssetIssuerWalletTable();
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);

            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TYPE_COLUMN_NAME, BalanceType.AVAILABLE.getCode(), DatabaseFilterType.EQUAL);

            databaseTableAssuerIssuerWallet.loadToMemory();
            return createTransactionList(databaseTableAssuerIssuerWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    // Read record data and create transactions list
    private List<AssetIssuerWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records) throws InvalidParameterException {

        List<AssetIssuerWalletTransaction> transactions = new ArrayList<>();

        for (DatabaseTableRecord record : records)
            transactions.add(constructAssetIssuerWalletTransactionFromRecord(record));

        return transactions;
    }


    private AssetIssuerWalletTransaction constructAssetIssuerWalletTransactionFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        String transactionId = record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_VERIFICATION_ID_COLUMN_NAME);
        String assetPublicKey = record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME);
        String transactionHash = record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TRANSACTION_HASH_COLUMN_NAME);
        TransactionType transactionType = TransactionType.getByCode(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TYPE_COLUMN_NAME));
        CryptoAddress addressFrom = new CryptoAddress();
        addressFrom.setAddress(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ADDRESS_FROM_COLUMN_NAME));
        CryptoAddress addressTo = new CryptoAddress();
        addressTo.setAddress(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ADDRESS_TO_COLUMN_NAME));
        String actorFromPublicKey = record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_FROM_COLUMN_NAME);
        String actorToPublicKey = record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_TO_COLUMN_NAME);
        Actors actorFromType = Actors.getByCode(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType = Actors.getByCode(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_TO_TYPE_COLUMN_NAME));
        DAPActor actorFrom = ActorUtils.getActorFromPublicKey(actorFromPublicKey, actorFromType, actorAssetUserManager, actorAssetRedeemPointManager, actorAssetIssuerManager);
        DAPActor actorTo = ActorUtils.getActorFromPublicKey(actorToPublicKey, actorToType, actorAssetUserManager, actorAssetRedeemPointManager, actorAssetIssuerManager);
        BalanceType balanceType = BalanceType.getByCode(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TYPE_COLUMN_NAME));
        long amount = record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_AMOUNT_COLUMN_NAME);
        long runningBookBalance = record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance = record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp = record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TIME_STAMP_COLUMN_NAME);
        String memo = record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_MEMO_COLUMN_NAME);
        return new AssetIssuerWalletTransactionWrapper(transactionId, transactionHash, assetPublicKey, transactionType, actorFrom, actorTo, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo);
    }

    private void executeTransaction(final AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance, final long quantityAvailableRunningBalance, final long quantityBookRunningBalance) throws CantExecuteAssetIssuerTransactionException {
        try {
            DatabaseTableRecord assetIssuerWalletRecord = constructAssetIssuerWalletRecord(assetIssuerWalletTransactionRecord, transactionType, balanceType, availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord assetBalanceRecord = constructAssetBalanceRecord(assetIssuerWalletTransactionRecord.getDigitalAsset(), availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(getAssetIssuerWalletTable(), assetIssuerWalletRecord);

            DatabaseTable databaseTable = getBalancesTable();
            databaseTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            if (databaseTable.getRecords().isEmpty()) {
                transaction.addRecordToInsert(databaseTable, assetBalanceRecord);
            } else {
                transaction.addRecordToUpdate(databaseTable, assetBalanceRecord);
            }

            String digitalAssetInnerXML = assetIssuerWalletTransactionRecord.getDigitalAsset().toString();
            PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(plugin, PATH_DIRECTORY, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            pluginTextFile.setContent(digitalAssetInnerXML);
            pluginTextFile.persistToMedia();

            /**
             * I'm also saving to file the DigitalAssetMetadata of this digital Asset.
             */
            pluginTextFile = pluginFileSystem.createTextFile(plugin, PATH_DIRECTORY, assetIssuerWalletTransactionRecord.getDigitalAssetMetadataHash(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            pluginTextFile.setContent(assetIssuerWalletTransactionRecord.getDigitalAssetMetadata().toString());
            pluginTextFile.persistToMedia();

            database.executeTransaction(transaction);


        } catch (Exception e) {
            e.printStackTrace();
            throw new CantExecuteAssetIssuerTransactionException("Error to get balances record", e, "Can't load balance table", "");
        }
    }

    private DatabaseTableRecord constructAssetBalanceRecord(DigitalAsset digitalAsset, long availableRunningBalance, long bookRunningBalance, long quantityAvailableRunningBalance, long quantityBookRunningBalance) {

        DatabaseTableRecord record = getBalancesTable().getEmptyRecord();
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, digitalAsset.getPublicKey());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME_COLUMN_NAME, digitalAsset.getName());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME, digitalAsset.getDescription());
        record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME, quantityAvailableRunningBalance);
        record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME, quantityBookRunningBalance);
        return record;

    }

    private DatabaseTableRecord constructAssetIssuerWalletRecord(final AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) {
        DatabaseTableRecord record = getAssetIssuerWalletTable().getEmptyRecord();
        record.setUUIDValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TABLE_ID_COLUMN_NAME, UUID.randomUUID());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ASSET_PUBLIC_KEY_COLUMN_NAME, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_VERIFICATION_ID_COLUMN_NAME, assetIssuerWalletTransactionRecord.getIdTransaction());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TYPE_COLUMN_NAME, transactionType.getCode());
        record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_AMOUNT_COLUMN_NAME, assetIssuerWalletTransactionRecord.getAmount());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_MEMO_COLUMN_NAME, assetIssuerWalletTransactionRecord.getMemo());
        record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TIME_STAMP_COLUMN_NAME, assetIssuerWalletTransactionRecord.getTimestamp());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TRANSACTION_HASH_COLUMN_NAME, assetIssuerWalletTransactionRecord.getDigitalAssetMetadataHash());

        if (assetIssuerWalletTransactionRecord.getAddressFrom() != null)
            record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ADDRESS_FROM_COLUMN_NAME, assetIssuerWalletTransactionRecord.getAddressFrom().getAddress());

        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ADDRESS_TO_COLUMN_NAME, assetIssuerWalletTransactionRecord.getAddressTo().getAddress());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_RUNNING_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_FROM_COLUMN_NAME, assetIssuerWalletTransactionRecord.getActorFromPublicKey());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_TO_COLUMN_NAME, assetIssuerWalletTransactionRecord.getActorToPublicKey());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_FROM_TYPE_COLUMN_NAME, assetIssuerWalletTransactionRecord.getActorFromType().getCode());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_TO_TYPE_COLUMN_NAME, assetIssuerWalletTransactionRecord.getActorToType().getCode());
        return record;
    }

    private long calculateAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long calculateQuantityAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getQuantityCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }


    private long calculateBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

    private long calculateQuantityBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentQuantityBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentQuantityBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getQuantityCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

    private boolean isTransactionInTable(final String transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable assetIssuerWalletTable = getAssetIssuerWalletTable();
        assetIssuerWalletTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.loadToMemory();
        return !assetIssuerWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getAssetIssuerWalletTable() {
        DatabaseTable databaseTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TABLE_NAME);
        return databaseTable;
    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException {
        return getCurrentBalance(BalanceType.AVAILABLE);
    }

    private long getCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }

    private long getQuantityCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getQuantityCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }

    private long getCurrentBalanceByAsset(BalanceType balanceType, String assetPublicKey) {
        long balanceAmount = 0;
        try {

            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);

            return balanceAmount;
        } catch (Exception exception) {
            return balanceAmount;
        }
    }

    private long getQuantityCurrentBalanceByAsset(BalanceType balanceType, String assetPublicKey) {
        long balanceAmount = 0;
        try {
            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME);
            return balanceAmount;
        } catch (Exception exception) {
            return balanceAmount;
        }
    }

    private long getCurrentBookBalance() throws CantGetBalanceRecordException {
        return getCurrentBalance(BalanceType.BOOK);
    }

    private List<AssetIssuerWalletList> getCurrentBalanceByAssets() throws CantGetBalanceRecordException {
        return getCurrentBalanceByAsset();
    }

    private long getCurrentBalance(final BalanceType balanceType) throws CantGetBalanceRecordException {
        long balanceAmount = 0;
        if (balanceType == BalanceType.AVAILABLE) {
            for (DatabaseTableRecord record : getBalancesRecord()) {
                balanceAmount += record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            }
        } else {
            for (DatabaseTableRecord record : getBalancesRecord()) {
                balanceAmount += record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            }
        }
        return balanceAmount;
    }

    private List<AssetIssuerWalletList> getCurrentBalanceByAsset() throws CantGetBalanceRecordException {
        List<AssetIssuerWalletList> issuerWalletBalances = new ArrayList<>();
        for (DatabaseTableRecord record : getBalancesRecord()) {
            AssetIssuerWalletList assetIssuerWalletBalance = new AssetIssuerWalletBalance();
            DigitalAsset asset;
            try {
                PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(plugin, PATH_DIRECTORY, record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                asset = (DigitalAsset) XMLParser.parseXML(pluginTextFile.getContent(), new DigitalAsset());
            } catch (FileNotFoundException | CantCreateFileException e) {
                asset = new DigitalAsset();
                asset.setName(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME_COLUMN_NAME));
                asset.setDescription(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME));
                asset.setPublicKey(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME));
            }
            assetIssuerWalletBalance.setDigitalAsset(asset);
            assetIssuerWalletBalance.setBookBalance(record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME));
            assetIssuerWalletBalance.setAvailableBalance(record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME));
            assetIssuerWalletBalance.setQuantityBookBalance(record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME));
            assetIssuerWalletBalance.setQuantityAvailableBalance(record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME));
            issuerWalletBalances.add(assetIssuerWalletBalance);
        }
        return issuerWalletBalances;
    }

    private List<DatabaseTableRecord> getBalancesRecord() throws CantGetBalanceRecordException {
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            return balancesTable.getRecords();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record", exception, "Can't load balance table", "");
        }
    }

    private DatabaseTableRecord getBalancesByAssetRecord(String assetPublicKey) throws CantGetBalanceRecordException {
        try {
            DatabaseTable balancesTable = getBalancesTable();//database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME);;
            balancesTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            balancesTable.loadToMemory();
            if (!balancesTable.getRecords().isEmpty()) {
                return balancesTable.getRecords().get(0);
            } else {
                //return balancesTable.getEmptyRecord();
                return balancesTable.getRecords().get(0);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record", exception, "Can't load balance table", "");
        }
    }

    private DatabaseTable getBalancesTable() {
        DatabaseTable databaseTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME);
        return databaseTable; //database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME);
    }

    // ************** ASSET STATISTIC TABLE METHODS ***************

    // PUBLIC METHODS
    public void createdNewAsset(DigitalAssetMetadata metadata) throws CantSaveStatisticException {
        String context = "Metadata: " + metadata;
        try {
            DatabaseTable databaseTable = this.database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TABLE_NAME);
            DatabaseTableRecord assetStatisticRecord = databaseTable.getEmptyRecord();

            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_ID_COLUMN_NAME, metadata.getMetadataId().toString());
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_HASH_COLUMN_NAME, metadata.getGenesisTransaction());
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_PUBLIC_KEY_COLUMN_NAME, metadata.getDigitalAsset().getPublicKey());
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_NAME_COLUMN_NAME, metadata.getDigitalAsset().getName());
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_CURRENT_STATUS_COLUMN_NAME, AssetCurrentStatus.ASSET_CREATED.getCode());

            databaseTable.insertRecord(assetStatisticRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveStatisticException(exception, context, "Cannot insert a record in Asset Statistic Table");
        }
    }

    public void assetDistributed(UUID transactionId, String actorAssetUserPublicKey) throws RecordsNotFoundException, CantGetAssetStatisticException {
        updateStringFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_CURRENT_STATUS_COLUMN_NAME, AssetCurrentStatus.ASSET_UNUSED.getCode(), transactionId);
        updateStringFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ACTOR_USER_PUBLIC_KEY_COLUMN_NAME, actorAssetUserPublicKey, transactionId);
        updateLongFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_DISTRIBUTION_DATE_COLUMN_NAME, System.currentTimeMillis(), transactionId);
    }

    //Even when I already stored the user public key when I distributed the asset, there's a chance that the user that redeemed the asset is a different one.
    public void assetRedeemed(UUID transactionId, String userPublicKey, String redeemPointPublicKey) throws RecordsNotFoundException, CantGetAssetStatisticException {
        updateStringFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_CURRENT_STATUS_COLUMN_NAME, AssetCurrentStatus.ASSET_REDEEMED.getCode(), transactionId);
        updateStringFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, redeemPointPublicKey, transactionId);
        if (userPublicKey != null) {
            updateStringFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ACTOR_USER_PUBLIC_KEY_COLUMN_NAME, userPublicKey, transactionId);
        }
        updateLongFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_USAGE_DATE_COLUMN_NAME, System.currentTimeMillis(), transactionId);
    }

    //Even when I already stored the user public key when I distributed the asset, there's a chance that the user that appropriated the asset is a different one.
    public void assetAppropriated(UUID transactionId, String userPublicKey) throws RecordsNotFoundException, CantGetAssetStatisticException {
        updateStringFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_CURRENT_STATUS_COLUMN_NAME, AssetCurrentStatus.ASSET_APPROPRIATED.getCode(), transactionId);
        updateStringFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ACTOR_USER_PUBLIC_KEY_COLUMN_NAME, userPublicKey, transactionId);
        updateLongFieldByAssetPublicKey(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_USAGE_DATE_COLUMN_NAME, System.currentTimeMillis(), transactionId);
    }

    public void newMovement(String assetPk, String fromPk, Actors fromType, String toPk, Actors toType, AssetMovementType type) throws CantSaveStatisticException {
        String context = "ASSET PK: " + assetPk;
        try {
            DatabaseTable databaseTable = this.database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_TABLE_NAME);
            DatabaseTableRecord assetStatisticRecord = databaseTable.getEmptyRecord();

            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ENTRY_ID, UUID.randomUUID().toString());
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ASSET_PUBLIC_KEY, assetPk);
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ACTOR_FROM_PUBLIC_KEY, fromPk);
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ACTOR_FROM_TYPE, fromType.getCode());
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ACTOR_TO_PUBLIC_KEY, toPk);
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ACTOR_TO_TYPE, toType.getCode());
            assetStatisticRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_TYPE, type.getCode());
            assetStatisticRecord.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_TIMESTAMP, System.currentTimeMillis());

            databaseTable.insertRecord(assetStatisticRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveStatisticException(exception, context, "Cannot insert a record in Asset Statistic Table");
        }
    }

    public DigitalAsset getAssetByPublicKey(String assetPublicKey) {
        try {
            return (DigitalAsset) XMLParser.parseXML(pluginFileSystem.getTextFile(plugin, PATH_DIRECTORY, assetPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT).getContent(), new DigitalAsset());
        } catch (FileNotFoundException | CantCreateFileException e) {
            return null;
        }
    }

    //Methods for construct the AssetStatistic object.
    public String getUserPublicKey(UUID txId) throws RecordsNotFoundException, CantGetAssetStatisticException {
        return getAssetStatisticStringFieldByPk(txId, AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ACTOR_USER_PUBLIC_KEY_COLUMN_NAME);
    }

    public String getRedeemPointPublicKey(UUID txId) throws RecordsNotFoundException, CantGetAssetStatisticException {
        return getAssetStatisticStringFieldByPk(txId, AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME);
    }

    public AssetCurrentStatus getStatus(UUID txId) {
        try {
            return AssetCurrentStatus.getByCode(getAssetStatisticStringFieldByPk(txId, AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_CURRENT_STATUS_COLUMN_NAME));
        } catch (InvalidParameterException | CantGetAssetStatisticException | RecordsNotFoundException e) {
            return AssetCurrentStatus.ASSET_CREATED;
        }
    }

    public String getGenesisTransaction(UUID txId) {
        try {
            return getAssetStatisticStringFieldByPk(txId, AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_HASH_COLUMN_NAME);
        } catch (CantGetAssetStatisticException | RecordsNotFoundException e) {
            return Validate.DEFAULT_STRING;
        }
    }

    public String getAssetName(UUID txId) {
        try {
            return getAssetStatisticStringFieldByPk(txId, AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_NAME_COLUMN_NAME);
        } catch (CantGetAssetStatisticException | RecordsNotFoundException e) {
            return Validate.DEFAULT_STRING;
        }
    }

    public String getAssetPublicKey(UUID txId) {
        try {
            return getAssetStatisticStringFieldByPk(txId, AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_PUBLIC_KEY_COLUMN_NAME);
        } catch (CantGetAssetStatisticException | RecordsNotFoundException e) {
            return Validate.DEFAULT_STRING;
        }
    }

    public Date getDistributionDate(UUID txId) {
        try {
            return new Date(getAssetStatisticLongFieldByPk(txId, AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_DISTRIBUTION_DATE_COLUMN_NAME));
        } catch (CantGetAssetStatisticException | RecordsNotFoundException e) {
            return null;
        }
    }

    public Date getUsageDate(UUID txId) {
        try {
            return new Date(getAssetStatisticLongFieldByPk(txId, AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_USAGE_DATE_COLUMN_NAME));
        } catch (CantGetAssetStatisticException | RecordsNotFoundException e) {
            return null;
        }
    }

    public List<AssetMovement> getAllMovementsForAsset(String aseetPk) throws CantGetAssetStatisticException {
        String context = "Asset PK: " + aseetPk;
        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_TABLE_NAME);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ASSET_PUBLIC_KEY, aseetPk, DatabaseFilterType.EQUAL);
            assetStatisticTable.addFilterOrder(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_TIMESTAMP, DatabaseFilterOrder.DESCENDING);
            assetStatisticTable.loadToMemory();
            List<DatabaseTableRecord> records = assetStatisticTable.getRecords();
            if (records.isEmpty()) {
                return Collections.EMPTY_LIST;
            }
            List<AssetMovement> toReturn = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                toReturn.add(constructAssetMovementByRecord(record));
            }
            return toReturn;
        } catch (Exception e) {
            throw new CantGetAssetStatisticException(e, context, "Database error.");
        }
    }

    public List<AssetMovement> getMovementsForType(AssetMovementType type) throws CantGetAssetStatisticException {

        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_TABLE_NAME);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_TYPE, type.toString(), DatabaseFilterType.EQUAL);
            assetStatisticTable.addFilterOrder(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_TIMESTAMP, DatabaseFilterOrder.DESCENDING);
            assetStatisticTable.loadToMemory();
            List<DatabaseTableRecord> records = assetStatisticTable.getRecords();
            if (records.isEmpty()) {
                return Collections.EMPTY_LIST;
            }
            List<AssetMovement> toReturn = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                toReturn.add(constructAssetMovementByRecord(record));
            }
            return toReturn;
        } catch (Exception e) {
            throw new CantGetAssetStatisticException(e, null, "Database error.");
        }
    }

    //Query Methods.
    public List<UUID> getAllAssetPublicKeyForAssetName(String assetName) throws CantGetAssetStatisticException {
        String context = "Asset Name: " + assetName;
        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TABLE_NAME);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_NAME_COLUMN_NAME, assetName, DatabaseFilterType.EQUAL);
            assetStatisticTable.loadToMemory();

            if (assetStatisticTable.getRecords().isEmpty()) {
                return Collections.EMPTY_LIST;
            }

            List<UUID> returnList = new ArrayList<>();

            for (DatabaseTableRecord record : assetStatisticTable.getRecords()) {
                returnList.add(UUID.fromString(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_ID_COLUMN_NAME)));
            }
            return returnList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetAssetStatisticException(exception, context, "Cannot load table to memory.");
        }
    }

    public List<UUID> getAllAssetPublicKeyForAssetNameAndStatus(String assetName, AssetCurrentStatus status) throws CantGetAssetStatisticException {
        String context = "Asset Name: " + assetName;
        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TABLE_NAME);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_NAME_COLUMN_NAME, assetName, DatabaseFilterType.EQUAL);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_CURRENT_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
            assetStatisticTable.loadToMemory();

            if (assetStatisticTable.getRecords().isEmpty()) {
                return Collections.EMPTY_LIST;
            }

            List<UUID> returnList = new ArrayList<>();

            for (DatabaseTableRecord record : assetStatisticTable.getRecords()) {
                returnList.add(UUID.fromString(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_ID_COLUMN_NAME)));
            }
            return returnList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetAssetStatisticException(exception, context, "Cannot load table to memory.");
        }
    }

    public List<UUID> getAllTransactionIds() throws CantGetAssetStatisticException {
        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TABLE_NAME);
            assetStatisticTable.loadToMemory();

            if (assetStatisticTable.getRecords().isEmpty()) {
                return Collections.EMPTY_LIST;
            }

            List<UUID> returnList = new ArrayList<>();

            for (DatabaseTableRecord record : assetStatisticTable.getRecords()) {
                returnList.add(UUID.fromString(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_ID_COLUMN_NAME)));
            }
            return returnList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetAssetStatisticException(exception, null, "Cannot load table to memory.");
        }
    }

    public List<UUID> getAllAssetPublicKeyForStatus(AssetCurrentStatus status) throws CantGetAssetStatisticException {
        String context = "Asset Status: " + status.getCode();
        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TABLE_NAME);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_ASSET_CURRENT_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
            assetStatisticTable.loadToMemory();

            if (assetStatisticTable.getRecords().isEmpty()) {
                return Collections.EMPTY_LIST;
            }

            List<UUID> returnList = new ArrayList<>();

            for (DatabaseTableRecord record : assetStatisticTable.getRecords()) {
                returnList.add(UUID.fromString(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_ID_COLUMN_NAME)));
            }
            return returnList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetAssetStatisticException(exception, context, "Cannot load table to memory.");
        }
    }


    //PRIVATE METHODS

    private void updateLongFieldByAssetPublicKey(String columnName, long value, UUID transactionId) throws CantGetAssetStatisticException, RecordsNotFoundException {
        String context = "Column Name: " + columnName + " - Tx Id: " + transactionId + " - Value: " + value;
        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TABLE_NAME);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
            assetStatisticTable.loadToMemory();

            if (assetStatisticTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            for (DatabaseTableRecord record : assetStatisticTable.getRecords()) {
                record.setLongValue(columnName, value);
                assetStatisticTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetAssetStatisticException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new CantGetAssetStatisticException(exception, context, "Cannot update record.");
        }
    }

    private String getAssetStatisticStringFieldByPk(UUID txId, String column) throws CantGetAssetStatisticException, RecordsNotFoundException {
        String context = "Transaction ID: " + txId;
        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TABLE_NAME);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_ID_COLUMN_NAME, txId.toString(), DatabaseFilterType.EQUAL);
            assetStatisticTable.loadToMemory();

            if (assetStatisticTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            return assetStatisticTable.getRecords().get(0).getStringValue(column);
        } catch (RecordsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CantGetAssetStatisticException(e, context, "Database error.");
        }
    }

    private long getAssetStatisticLongFieldByPk(UUID txId, String column) throws CantGetAssetStatisticException, RecordsNotFoundException {
        String context = "Transaction ID: " + txId;
        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TABLE_NAME);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_ID_COLUMN_NAME, txId.toString(), DatabaseFilterType.EQUAL);
            assetStatisticTable.loadToMemory();
            DatabaseTableRecord record = assetStatisticTable.getRecords().get(0);

            if (record == null) {
                throw new RecordsNotFoundException(null, context, "");
            }

            return record.getLongValue(column);
        } catch (RecordsNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CantGetAssetStatisticException(e, context, "Database error.");
        }
    }

    private AssetMovement constructAssetMovementByRecord(DatabaseTableRecord record) {
        AssetMovementImpl assetMovement = new AssetMovementImpl();
        String actorFromPublicKey = record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ACTOR_FROM_PUBLIC_KEY);
        Actors actorFromType = Actors.getByCode(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ACTOR_FROM_TYPE));
        String actorToPublicKey = record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ACTOR_TO_PUBLIC_KEY);
        Actors actorToType = Actors.getByCode(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_ACTOR_TO_TYPE));
        DAPActor actorFrom = findActorByTypeAndPk(actorFromType, actorFromPublicKey);
        DAPActor actorTo = findActorByTypeAndPk(actorToType, actorToPublicKey);
        Date timeStamp = new Date(record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_MOVEMENTS_TIMESTAMP));
        assetMovement.setActorFrom(actorFrom);
        assetMovement.setActorTo(actorTo);
        assetMovement.setTimestamp(timeStamp);
        return assetMovement;
    }

    private DAPActor findActorByTypeAndPk(final Actors type, final String publicKey) {
        try {
            switch (type) {
                case DAP_ASSET_ISSUER:
                    return actorAssetIssuerManager.getActorByPublicKey(publicKey);
                case DAP_ASSET_USER:
                    return actorAssetUserManager.getActorByPublicKey(publicKey);
                case DAP_ASSET_REDEEM_POINT:
                    return actorAssetRedeemPointManager.getActorByPublicKey(publicKey);
                default:
                    throw new RuntimeException("UNKNOWN TYPE!!!");
            }
        } catch (Exception e) {
            return constructUnknownActor(type, publicKey);
        }
    }


    private DAPActor constructUnknownActor(final Actors type, final String publicKey) {
        return new DAPActor() {
            @Override
            public String getActorPublicKey() {
                return publicKey;
            }

            @Override
            public String getName() {
                return Validate.DEFAULT_STRING + " " + type.getCode();
            }

            @Override
            public Actors getType() {
                return Actors.DAP_ASSET_ISSUER;
            }

            @Override
            public byte[] getProfileImage() {
                return new byte[0];
            }
        };
    }


    private void updateStringFieldByAssetPublicKey(String columnName, String value, UUID transactionId) throws CantGetAssetStatisticException, RecordsNotFoundException {
        String context = "Column Name: " + columnName + " - Transaction Id: " + transactionId + " - Value: " + value;
        try {
            DatabaseTable assetStatisticTable;
            assetStatisticTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TABLE_NAME);
            assetStatisticTable.addStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_STATISTIC_TRANSACTION_ID_COLUMN_NAME, transactionId.toString(), DatabaseFilterType.EQUAL);
            assetStatisticTable.loadToMemory();

            if (assetStatisticTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            DatabaseTableRecord record = assetStatisticTable.getRecords().get(0);

            record.setStringValue(columnName, value);
            assetStatisticTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetAssetStatisticException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new CantGetAssetStatisticException(exception, context, "Cannot update record.");
        }
    }
}