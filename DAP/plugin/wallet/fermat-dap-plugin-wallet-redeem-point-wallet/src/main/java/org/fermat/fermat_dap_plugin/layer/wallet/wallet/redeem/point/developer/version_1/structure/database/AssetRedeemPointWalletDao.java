package org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
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
import org.fermat.fermat_dap_api.layer.all_definition.util.ActorUtils;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantGetRedeemPointStatisticsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantSaveRedeemPointStatisticException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.AssetRedeemPointWalletPluginRoot;
import org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.exceptions.CantGetBalanceRecordException;
import org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.functional.AssetRedeemPointWalletBalance;
import org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.functional.AssetRedeemPointWalletTransactionWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 14/10/15.
 */
public class AssetRedeemPointWalletDao {

    private final PluginFileSystem pluginFileSystem;
    private final UUID plugin;
    private final Database database;
    private final ActorAssetUserManager actorAssetUserManager;
    private final ActorAssetIssuerManager issuerManager;
    private final ActorAssetRedeemPointManager redeemPointManager;

    public AssetRedeemPointWalletDao(Database database,
                                     PluginFileSystem pluginFileSystem,
                                     UUID plugin,
                                     ActorAssetUserManager actorAssetUserManager,
                                     ActorAssetIssuerManager issuerManager,
                                     ActorAssetRedeemPointManager redeemPointManager) {
        this.database = database;
        this.pluginFileSystem = pluginFileSystem;
        this.plugin = plugin;
        this.actorAssetUserManager = actorAssetUserManager;
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
                balanceAmount += record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            }
        } else {
            for (DatabaseTableRecord record : getBalancesRecord()) {
                balanceAmount += record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            }
        }
        return balanceAmount;
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

    private DatabaseTable getBalancesTable() {
        DatabaseTable databaseTable = database.getTable(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME);
        return databaseTable; //database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME);
    }

    private DatabaseTable getStatisticTable() {
        return database.getTable(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_STATISTIC_TABLE_NAME);
    }

    /*
 * getBookBalance must get actual Book Balance global of Asset Redeem Point wallet, select record from balances table
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

    private List<AssetRedeemPointWalletList> getCurrentBalanceByAsset() throws CantGetBalanceRecordException {
        List<AssetRedeemPointWalletList> redeemPointWalletBalances = new ArrayList<>();
        for (DatabaseTableRecord record : getBalancesRecord()) {
            AssetRedeemPointWalletList redeemPointIssuerWalletBalance = new AssetRedeemPointWalletBalance();
            String assetPublicKey = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ASSET_PUBLIC_KEY_COLUMN_NAME);
            redeemPointIssuerWalletBalance.setAvailableBalance(record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME));
            redeemPointIssuerWalletBalance.setBookBalance(record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME));
            redeemPointIssuerWalletBalance.setQuantityBookBalance(record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME));
            redeemPointIssuerWalletBalance.setQuantityAvailableBalance(record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME));

            try {
                PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(plugin, AssetRedeemPointWalletPluginRoot.PATH_DIRECTORY, assetPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                DigitalAsset asset = (DigitalAsset) XMLParser.parseXML(pluginTextFile.getContent(), new DigitalAsset());
                redeemPointIssuerWalletBalance.setDigitalAsset(asset);
            } catch (FileNotFoundException | CantCreateFileException e) {
                e.printStackTrace();
            }

            redeemPointWalletBalances.add(redeemPointIssuerWalletBalance);
        }
        return redeemPointWalletBalances;
    }

    private boolean isTransactionInTable(final String transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable assetIssuerWalletTable = getAssetRedeemPointWalletTable();
        assetIssuerWalletTable.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.loadToMemory();
        return !assetIssuerWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getAssetRedeemPointWalletTable() {
        DatabaseTable databaseTable = database.getTable(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_NAME);
        return databaseTable;
    }

    private long calculateAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }

    private long getCurrentBalanceByAsset(BalanceType balanceType, String assetPublicKey) {
        try {
            long balanceAmount = 0;
            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            return balanceAmount;
        } catch (Exception exception) {
            return 0;
        }
    }

    private DatabaseTableRecord getBalancesByAssetRecord(String assetPublicKey) throws CantGetBalanceRecordException {
        try {
            DatabaseTable balancesTable = database.getTable(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME);
            balancesTable.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            balancesTable.loadToMemory();
            if (!balancesTable.getRecords().isEmpty()) {
                return balancesTable.getRecords().get(0);
            } else return balancesTable.getEmptyRecord();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record", exception, "Can't load balance table", "");
        }
    }

    private long calculateBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException {
        return getCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

    private void executeTransaction(final AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance, final long quantityAvailableRunningBalance, final long quantityBookRunningBalance) throws org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.exceptions.CantExecuteAssetRedeemPointTransactionException {
        try {
            DatabaseTableRecord assetRedeemPointWalletRecord = constructAssetRdeemPointWalletRecord(assetRedeemPointWalletTransactionRecord, transactionType, balanceType, availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord assetBalanceRecord = constructAssetBalanceRecord(assetRedeemPointWalletTransactionRecord.getDigitalAsset(), availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(getAssetRedeemPointWalletTable(), assetRedeemPointWalletRecord);

            DatabaseTable databaseTable = getBalancesTable();
            databaseTable.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            if (databaseTable.getRecords().isEmpty()) {
                transaction.addRecordToInsert(databaseTable, assetBalanceRecord);
            } else {
                transaction.addRecordToUpdate(databaseTable, assetBalanceRecord);
            }


            String digitalAssetInnerXML = assetRedeemPointWalletTransactionRecord.getDigitalAsset().toString();
            PluginTextFile assetTextFile = pluginFileSystem.createTextFile(plugin, AssetRedeemPointWalletPluginRoot.PATH_DIRECTORY, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            assetTextFile.setContent(digitalAssetInnerXML);
            assetTextFile.persistToMedia();

            String assetMetadataXML = XMLParser.parseObject(assetRedeemPointWalletTransactionRecord.getDigitalAssetMetadata());
            PluginTextFile metadataTextFile = pluginFileSystem.createTextFile(plugin, AssetRedeemPointWalletPluginRoot.PATH_DIRECTORY, assetRedeemPointWalletTransactionRecord.getDigitalAssetMetadata().getGenesisTransaction(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            metadataTextFile.setContent(assetMetadataXML);
            metadataTextFile.persistToMedia();

            database.executeTransaction(transaction);

        } catch (Exception e) {
            e.printStackTrace();
            throw new org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.exceptions.CantExecuteAssetRedeemPointTransactionException("Error to get balances record", e, "Can't load balance table", "");
        }
    }

    private DatabaseTableRecord constructAssetRdeemPointWalletRecord(final AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) {
        DatabaseTableRecord record = getAssetRedeemPointWalletTable().getEmptyRecord();
        record.setUUIDValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_ID_COLUMN_NAME, UUID.randomUUID());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ASSET_PUBLIC_KEY_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_VERIFICATION_ID_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getIdTransaction());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME, transactionType.getCode());
        record.setLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_AMOUNT_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getAmount());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_MEMO_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getMemo());
        record.setLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getTimestamp());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TRANSACTION_HASH_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getDigitalAssetMetadataHash());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_FROM_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getAddressFrom().getAddress());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_TO_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getAddressTo().getAddress());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getActorFromPublicKey());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getActorToPublicKey());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_TYPE_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getActorFromType().getCode());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_TYPE_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getActorToType().getCode());
        return record;
    }

    private DatabaseTableRecord constructAssetBalanceRecord(DigitalAsset digitalAsset, long availableRunningBalance, long bookRunningBalance, long quantityAvailableRunningBalance, long quantityBookRunningBalance) {

        DatabaseTableRecord record = getBalancesTable().getEmptyRecord();
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, digitalAsset.getPublicKey());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME_COLUMN_NAME, digitalAsset.getName());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME, digitalAsset.getDescription());
        record.setLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME, quantityAvailableRunningBalance);
        record.setLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME, quantityBookRunningBalance);

        return record;

    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException {
        return getCurrentBalance(BalanceType.AVAILABLE);
    }

    /*
    * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
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
     * getBalance must get actual Balance global of Asset Issuer wallet, select record from balances table
     */
    public List<AssetRedeemPointWalletList> getBalanceByAssets() throws CantCalculateBalanceException {
        try {
            return getCurrentBalanceByAsset();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");

        }
    }

    /*
     * Add a new debit transaction.
     */
    public void addDebit(final AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            System.out.println("Agregando Debito-----------------------------------------------------------");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetRedeemPointWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetRedeemPointWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(-availableAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(-bookAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());

            long quantityAvailableAmount = balanceType.equals(BalanceType.AVAILABLE) ? 1 : 0L;
            long quantityBookAmount = balanceType.equals(BalanceType.BOOK) ? 1 : 0L;
            long quantityAvailableRunningBalance = calculateQuantityAvailableRunningBalanceByAsset(-quantityAvailableAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long quantityBookRunningBalance = calculateQuantityBookRunningBalanceByAsset(-quantityBookAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());

            executeTransaction(assetRedeemPointWalletTransactionRecord, TransactionType.DEBIT, balanceType, availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
        } catch (CantGetBalanceRecordException | org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.exceptions.CantExecuteAssetRedeemPointTransactionException exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
     * Add a new credit transaction.
     */
    public void addCredit(final AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterCreditException {

        try {
            System.out.println("Agregando Credito-----------------------------------------------------------");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetRedeemPointWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetRedeemPointWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(availableAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(bookAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());

            long quantityAvailableAmount = balanceType.equals(BalanceType.AVAILABLE) ? 1 : 0L;
            long quantityBookAmount = balanceType.equals(BalanceType.BOOK) ? 1 : 0L;
            long quantityAvailableRunningBalance = calculateQuantityAvailableRunningBalanceByAsset(quantityAvailableAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long quantityBookRunningBalance = calculateQuantityBookRunningBalanceByAsset(quantityBookAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());

            executeTransaction(assetRedeemPointWalletTransactionRecord, TransactionType.CREDIT, balanceType, availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);

        } catch (CantGetBalanceRecordException | org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.exceptions.CantExecuteAssetRedeemPointTransactionException exception) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<AssetRedeemPointWalletTransaction> listsTransactionsByAssetsAll(BalanceType balanceType, TransactionType transactionType, String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableRedeemPointWallet = getAssetRedeemPointWalletTable();
            databaseTableRedeemPointWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

            databaseTableRedeemPointWallet.loadToMemory();
            return createTransactionList(databaseTableRedeemPointWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of All Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<AssetRedeemPointWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableRedeemPointWallet = getAssetRedeemPointWalletTable();

            databaseTableRedeemPointWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.addFilterOrder(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            databaseTableRedeemPointWallet.setFilterTop(String.valueOf(max));
            databaseTableRedeemPointWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableRedeemPointWallet.loadToMemory();

            return createTransactionList(databaseTableRedeemPointWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }

    }

    public List<AssetRedeemPointWalletTransaction> listsTransactionsByAssets(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerIssuerWallet = getAssetRedeemPointWalletTable();
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
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


    public List<AssetRedeemPointWalletTransaction> listsTransactionsByAssets(BalanceType balanceType, TransactionType transactionType, String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable redeemPointWalletTable = getAssetRedeemPointWalletTable();
            redeemPointWalletTable.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            redeemPointWalletTable.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            redeemPointWalletTable.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

            redeemPointWalletTable.loadToMemory();
            return createTransactionList(redeemPointWalletTable.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<AssetRedeemPointWalletTransaction> getTransactionsByTransactionType(TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableAssuerIssuerWallet = getAssetRedeemPointWalletTable();

            databaseTableAssuerIssuerWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.addFilterOrder(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

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

    public void updateMemoField(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {
        try {
            DatabaseTable databaseTableAssuerRedeemPointWalletBalance = getBalancesTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */

            databaseTableAssuerRedeemPointWalletBalance.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
            for (DatabaseTableRecord record : databaseTableAssuerRedeemPointWalletBalance.getRecords()) {
                record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_MEMO_COLUMN_NAME, memo);
                databaseTableAssuerRedeemPointWalletBalance.updateRecord(record);
            }
            databaseTableAssuerRedeemPointWalletBalance.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error", cantLoadTableToMemory, "Error load Transaction table" + transactionID.toString(), "");

        } catch (CantUpdateRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error", cantUpdateRecord, "Error update memo of Transaction " + transactionID.toString(), "");
        } catch (Exception exception) {
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private List<AssetRedeemPointWalletTransaction> listsTransactionsByAsset(String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableRedeemPointWallet = getAssetRedeemPointWalletTable();
            databaseTableRedeemPointWallet.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);

            databaseTableRedeemPointWallet.loadToMemory();
            return createTransactionList(databaseTableRedeemPointWallet.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    // Read record data and create transactions list
    private List<AssetRedeemPointWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records) throws InvalidParameterException {

        List<AssetRedeemPointWalletTransaction> transactions = new ArrayList<>();

        for (DatabaseTableRecord record : records)
            transactions.add(constructAssetRedeemPointWalletTransactionFromRecord(record));

        return transactions;
    }


    private AssetRedeemPointWalletTransaction constructAssetRedeemPointWalletTransactionFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        String transactionId = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_ID_COLUMN_NAME);
        String assetPublicKey = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME);
        String transactionHash = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TRANSACTION_HASH_COLUMN_NAME);
        TransactionType transactionType = TransactionType.getByCode(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME));
        CryptoAddress addressFrom = new CryptoAddress();
        addressFrom.setAddress(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_FROM_COLUMN_NAME));
        CryptoAddress addressTo = new CryptoAddress();
        addressTo.setAddress(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_TO_COLUMN_NAME));
        String actorFromPublicKey = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_COLUMN_NAME);
        String actorToPublicKey = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_COLUMN_NAME);
        Actors actorFromType = Actors.getByCode(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType = Actors.getByCode(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_TYPE_COLUMN_NAME));
        DAPActor actorFrom = ActorUtils.getActorFromPublicKey(actorFromPublicKey, actorFromType, actorAssetUserManager, redeemPointManager, issuerManager);
        DAPActor actorTo = ActorUtils.getActorFromPublicKey(actorToPublicKey, actorToType, actorAssetUserManager, redeemPointManager, issuerManager);
        BalanceType balanceType = BalanceType.getByCode(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME));
        long amount = record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_AMOUNT_COLUMN_NAME);
        long runningBookBalance = record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance = record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp = record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME);
        String memo = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_MEMO_COLUMN_NAME);
        return new AssetRedeemPointWalletTransactionWrapper(transactionId, transactionHash, assetPublicKey, transactionType, actorFrom, actorTo, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo);
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
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME);
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

    public void newAssetRedeemed(DigitalAssetMetadata digitalAssetMetadata, String userPublicKey) throws CantSaveRedeemPointStatisticException {
        String context = "User Pk: " + userPublicKey + " - Genesis Tx: " + digitalAssetMetadata.getGenesisTransaction();
        try {
            DatabaseTable statisticTable = getStatisticTable();
            DatabaseTableRecord recordToInsert = statisticTable.getEmptyRecord();
            recordToInsert.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_ID_COLUMN_NAME, digitalAssetMetadata.getMetadataId().toString());
            recordToInsert.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_GENESIS_TRANSACTION_KEY_COLUMN_NAME, digitalAssetMetadata.getGenesisTransaction());
            recordToInsert.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_ASSET_PUBLIC_KEY_COLUMN_NAME, digitalAssetMetadata.getDigitalAsset().getPublicKey());
            recordToInsert.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_STATISTIC_USER_PUBLICKEY_COLUMN_NAME, userPublicKey);
            recordToInsert.setLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_STATISTIC_REDEMPTION_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
            statisticTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantSaveRedeemPointStatisticException(e, context, null);
        }
    }

    public List<RedeemPointStatistic> getAllStatistics() throws RecordsNotFoundException, CantGetRedeemPointStatisticsException {
        return getStatisticsByFilters();
    }

    public List<RedeemPointStatistic> getStatisticsForUser(String userPk) throws RecordsNotFoundException, CantGetRedeemPointStatisticsException {
        return getStatisticsByFilters(getUserPublicKeyFilter(userPk));
    }

    public List<RedeemPointStatistic> getStatisticsForAsset(String assetPk) throws RecordsNotFoundException, CantGetRedeemPointStatisticsException {
        return getStatisticsByFilters(getAssetPublicKeyFilter(assetPk));
    }

    public List<RedeemPointStatistic> getStatisticForAssetAndUser(String userPk, String assetPk) throws RecordsNotFoundException, CantGetRedeemPointStatisticsException {
        return getStatisticsByFilters(getAssetPublicKeyFilter(assetPk), getUserPublicKeyFilter(userPk));
    }

    private DatabaseTableFilter getUserPublicKeyFilter(String userPk) {
        DatabaseTableFilter filter = getStatisticTable().getEmptyTableFilter();
        filter.setType(DatabaseFilterType.EQUAL);
        filter.setValue(userPk);
        filter.setColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_STATISTIC_USER_PUBLICKEY_COLUMN_NAME);
        return filter;
    }

    private DatabaseTableFilter getAssetPublicKeyFilter(String assetPk) {
        DatabaseTableFilter filter = getStatisticTable().getEmptyTableFilter();
        filter.setType(DatabaseFilterType.EQUAL);
        filter.setValue(assetPk);
        filter.setColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_ASSET_PUBLIC_KEY_COLUMN_NAME);
        return filter;
    }

    private List<RedeemPointStatistic> getStatisticsByFilters(DatabaseTableFilter... filters) throws RecordsNotFoundException, CantGetRedeemPointStatisticsException {
        DatabaseTable statistictable = getStatisticTable();
        for (DatabaseTableFilter filter : filters) {
            statistictable.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        }
        try {
            statistictable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        }
        List<RedeemPointStatistic> listToReturn = new ArrayList<>();
        for (DatabaseTableRecord record : statistictable.getRecords()) {
            listToReturn.add(constructStatisticById(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_ID_COLUMN_NAME)));
        }
        return listToReturn;
    }

    private RedeemPointStatistic constructStatisticById(String uuid) throws RecordsNotFoundException, CantGetRedeemPointStatisticsException {
        String context = "UUID: " + uuid;
        try {
            DatabaseTable statisticTable = getStatisticTable();
            statisticTable.addStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_ID_COLUMN_NAME, uuid, DatabaseFilterType.EQUAL);

            statisticTable.loadToMemory();


            if (statisticTable.getRecords().isEmpty())
                throw new RecordsNotFoundException(null, uuid, null);

            DatabaseTableRecord record = statisticTable.getRecords().get(0);

            org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.functional.RedeemPointStatisticImpl statistic = new org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.functional.RedeemPointStatisticImpl();
            statistic.setStatisticId(uuid);
            statistic.setAssetMetadata(getDigitalAssetMetadata(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_GENESIS_TRANSACTION_KEY_COLUMN_NAME)));
            statistic.setActorAssetUser(getActorAssetUser(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_STATISTIC_USER_PUBLICKEY_COLUMN_NAME)));
            statistic.setRedemptionDate(record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_STATISTIC_REDEMPTION_TIMESTAMP_COLUMN_NAME));
            return statistic;
        } catch (CantLoadTableToMemoryException | CantGetDigitalAssetFromLocalStorageException e) {
            throw new CantGetRedeemPointStatisticsException(e, context, null);
        }
    }

    public DigitalAssetMetadata getDigitalAssetMetadata(String genesisTransaction) throws CantGetDigitalAssetFromLocalStorageException {
        String context = "Path: " + AssetRedeemPointWalletPluginRoot.PATH_DIRECTORY + " - Genesis Transaction: " + genesisTransaction;
        try {
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(plugin, AssetRedeemPointWalletPluginRoot.PATH_DIRECTORY, genesisTransaction, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            return (DigitalAssetMetadata) XMLParser.parseXML(pluginTextFile.getContent(), new DigitalAssetMetadata());
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetDigitalAssetFromLocalStorageException(e, context, "The path could be wrong or there was an error creating the file.");
        }
    }

    private ActorAssetUser getActorAssetUser(final String userPublicKey) {
        ActorAssetUser actorAssetUser = null;
        try {
            actorAssetUser = actorAssetUserManager.getActorByPublicKey(userPublicKey);
        } catch (CantGetAssetUserActorsException | CantAssetUserActorNotFoundException e) {
            System.out.println("COULDN'T FIND THE ACTOR ASSET USER, RETRIEVING AN UNKNOWN USER.");
            e.printStackTrace();
        }
        return actorAssetUser;
    }
}
