package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionSummary;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.AssetRedeemPointWalletTransactionWrapper;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.exceptions.CantExecuteAssetRedeemPointTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.exceptions.CantGetBalanceRecordException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 14/10/15.
 */
public class AssetRedeemPointWalletDao implements DealsWithPluginFileSystem {
    //TODO: Manejo de excepciones y Documentar
    public static final String PATH_DIRECTORY = "asset-redeem-point-swap/";
    PluginFileSystem pluginFileSystem;
    UUID plugin;
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    public void setPlugin(UUID plugin){
        this.plugin = plugin;
    }

    private Database database;

    public AssetRedeemPointWalletDao(Database database){
        this.database = database;
    }

    private long getCurrentBookBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.BOOK);
    }

    private long getCurrentBalance(final BalanceType balanceType) throws CantGetBalanceRecordException {
        long balanceAmount = 0;
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                balanceAmount += record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            }
        }
        else{
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                balanceAmount += record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            }
        }
        return balanceAmount;
    }

    private List<DatabaseTableRecord> getBalancesRecord() throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            return balancesTable.getRecords();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTable getBalancesTable(){
        DatabaseTable databaseTable = database.getTable(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME);
        return databaseTable; //database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME);
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


    private List<AssetRedeemPointWalletList> getCurrentBookBalanceByAssets() throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.BOOK);
    }

    private List<AssetRedeemPointWalletList> getCurrentBalanceByAsset(final BalanceType balanceType) throws CantGetBalanceRecordException {
        List<AssetRedeemPointWalletList> redeemPointWalletBalances= new ArrayList<>();
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                AssetRedeemPointWalletList redeemPointIssuerWalletBalance = new com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.AssetRedeemPointWalletBalance();
                redeemPointIssuerWalletBalance.setName(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME_COLUMN_NAME));
                redeemPointIssuerWalletBalance.setDescription(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME));
                redeemPointIssuerWalletBalance.setAssetPublicKey(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME));
                redeemPointIssuerWalletBalance.setBookBalance(record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME));
                redeemPointIssuerWalletBalance.setAvailableBalance(record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME));
                redeemPointWalletBalances.add(redeemPointIssuerWalletBalance);
            }
        }
        else{
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                AssetRedeemPointWalletList redeemPointIssuerWalletBalance = new com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.AssetRedeemPointWalletBalance();
                redeemPointIssuerWalletBalance.setName(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME_COLUMN_NAME));
                redeemPointIssuerWalletBalance.setDescription(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME));
                redeemPointIssuerWalletBalance.setAssetPublicKey(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME));
                redeemPointIssuerWalletBalance.setBookBalance(record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME));
                redeemPointIssuerWalletBalance.setAvailableBalance(record.getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME));
                redeemPointWalletBalances.add(redeemPointIssuerWalletBalance);
            }

        }
        return redeemPointWalletBalances;
    }

    private boolean isTransactionInTable(final String transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable assetIssuerWalletTable = getAssetRedeemPointWalletTable();
        assetIssuerWalletTable.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.loadToMemory();
        return !assetIssuerWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getAssetRedeemPointWalletTable(){
        DatabaseTable databaseTable = database.getTable(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_NAME);
        return databaseTable;
    }

    private long calculateAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }

    private long getCurrentBalanceByAsset(BalanceType balanceType, String assetPublicKey)
    {
        try {
            long balanceAmount = 0;
            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            return balanceAmount;
        }
        catch (Exception exception){
            return 0;
        }
    }

    private DatabaseTableRecord getBalancesByAssetRecord(String assetPublicKey) throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = database.getTable(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME);;
            balancesTable.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            balancesTable.loadToMemory();
            if (!balancesTable.getRecords().isEmpty() ) {
                return balancesTable.getRecords().get(0);
            }
            else return balancesTable.getEmptyRecord();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private long calculateBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getCurrentBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

    private void executeTransaction(final AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance, final long quantityAvailableRunningBalance, final long quantityBookRunningBalance) throws CantExecuteAssetRedeemPointTransactionException {
        try {
            DatabaseTableRecord assetIssuerWalletRecord = constructAssetRdeemPointWalletRecord(assetRedeemPointWalletTransactionRecord, transactionType, balanceType, availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord assetBalanceRecord = constructAssetBalanceRecord(assetRedeemPointWalletTransactionRecord.getDigitalAsset(), availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(getAssetRedeemPointWalletTable(), assetIssuerWalletRecord);

            DatabaseTable databaseTable = getBalancesTable();
            databaseTable.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey(), DatabaseFilterType.EQUAL );
            databaseTable.loadToMemory();
            if (databaseTable.getRecords().isEmpty()){
                transaction.addRecordToInsert(databaseTable, assetBalanceRecord);
                String digitalAssetInnerXML = assetRedeemPointWalletTransactionRecord.getDigitalAsset().toString();
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(plugin, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey(), PATH_DIRECTORY, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(digitalAssetInnerXML);
                pluginTextFile.persistToMedia();
            }else{
                transaction.addRecordToUpdate(databaseTable, assetBalanceRecord);
            }

            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch(Exception e){
            e.printStackTrace();
            throw new CantExecuteAssetRedeemPointTransactionException("Error to get balances record",e,"Can't load balance table" , "");
        }
    }

    private DatabaseTableRecord constructAssetRdeemPointWalletRecord(final AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance){
        DatabaseTableRecord record = getAssetRedeemPointWalletTable().getEmptyRecord();
        record.setUUIDValue  (AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_ID_COLUMN_NAME                   , UUID.randomUUID());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ASSET_PUBLIC_KEY_COLUMN_NAME           , assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_VERIFICATION_ID_COLUMN_NAME, assetRedeemPointWalletTransactionRecord.getIdTransaction());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME                      , transactionType.getCode());
        record.setLongValue  (AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_AMOUNT_COLUMN_NAME                    , assetRedeemPointWalletTransactionRecord.getAmount());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_MEMO_COLUMN_NAME                      , assetRedeemPointWalletTransactionRecord.getMemo());
        record.setLongValue  (AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME                , assetRedeemPointWalletTransactionRecord.getTimestamp());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TRANSACTION_HASH_COLUMN_NAME          , assetRedeemPointWalletTransactionRecord.getDigitalAssetMetadataHash());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_FROM_COLUMN_NAME              , assetRedeemPointWalletTransactionRecord.getAddressFrom().getAddress());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_TO_COLUMN_NAME                , assetRedeemPointWalletTransactionRecord.getAddressTo().getAddress());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME              , balanceType.getCode());
        record.setLongValue  (AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME , availableRunningBalance);
        record.setLongValue  (AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_BOOK_BALANCE_COLUMN_NAME      , bookRunningBalance);
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_COLUMN_NAME                , assetRedeemPointWalletTransactionRecord.getActorFromPublicKey());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_COLUMN_NAME                  , assetRedeemPointWalletTransactionRecord.getActorToPublicKey());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_TYPE_COLUMN_NAME           , assetRedeemPointWalletTransactionRecord.getActorFromType().getCode());
        record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_TYPE_COLUMN_NAME             , assetRedeemPointWalletTransactionRecord.getActorToType().getCode());
        return record;
    }

    private DatabaseTableRecord constructAssetBalanceRecord(DigitalAsset digitalAsset, long  availableRunningBalance, long bookRunningBalance, long  quantityAvailableRunningBalance, long quantityBookRunningBalance)
    {

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
    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE);
    }

    private List<AssetRedeemPointWalletList> getCurrentAvailableBalanceByAssets() throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE);
    }
    /*
    * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
    */
    public long getAvailableBalance() throws CantCalculateBalanceException {
        try{
            return getCurrentAvailableBalance();
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<AssetRedeemPointWalletList> getAvailableBalanceByAsset() throws CantCalculateBalanceException {
        try{
            return getCurrentAvailableBalanceByAssets();
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
 * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
 */
    public List<AssetRedeemPointWalletList> getBookBalanceByAssets() throws CantCalculateBalanceException {
        try {
            return getCurrentBookBalanceByAssets();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");

        }
    }

    /*
* Add a new debit transaction.
*/
    public void addDebit(final AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterDebitException{
        try {
            System.out.println("Agregando Debito-----------------------------------------------------------");
            if (isTransactionInTable(assetRedeemPointWalletTransactionRecord.getIdTransaction(), TransactionType.DEBIT, balanceType))
                throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetRedeemPointWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetRedeemPointWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(-availableAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(-bookAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());

            long quantityAvailableRunningBalance = calculateQuantityAvailableRunningBalanceByAsset(-1, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long quantityBookRunningBalance = calculateQuantityBookRunningBalanceByAsset(-1, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());

            executeTransaction(assetRedeemPointWalletTransactionRecord,TransactionType.DEBIT ,balanceType, availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);
        }catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteAssetRedeemPointTransactionException exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
* Add a new credit transaction.
*/
    public void addCredit(final AssetRedeemPointWalletTransactionRecord assetRedeemPointWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterCreditException {

        try {
            System.out.println("Agregando Credito-----------------------------------------------------------");
            if (isTransactionInTable(assetRedeemPointWalletTransactionRecord.getIdTransaction(), TransactionType.CREDIT, balanceType))
                throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetRedeemPointWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetRedeemPointWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(availableAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(bookAmount, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());

            long quantityAvailableRunningBalance = calculateQuantityAvailableRunningBalanceByAsset(1, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long quantityBookRunningBalance = calculateQuantityBookRunningBalanceByAsset(1, assetRedeemPointWalletTransactionRecord.getDigitalAsset().getPublicKey());

            executeTransaction(assetRedeemPointWalletTransactionRecord, TransactionType.CREDIT, balanceType, availableRunningBalance, bookRunningBalance, quantityAvailableRunningBalance, quantityBookRunningBalance);

        }catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteAssetRedeemPointTransactionException exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<AssetRedeemPointWalletTransaction> listsTransactionsByAssetsAll(BalanceType balanceType, TransactionType transactionType, String assetPublicKey) throws CantGetTransactionsException {
        try {
            DatabaseTable databaseTableRedeemPointWallet = getAssetRedeemPointWalletTable();
            databaseTableRedeemPointWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

            databaseTableRedeemPointWallet.loadToMemory();
            return createTransactionList(databaseTableRedeemPointWallet.getRecords());
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of All Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        }
        catch (Exception exception){
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<AssetRedeemPointWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException
    {
        try {
            DatabaseTable databaseTableRedeemPointWallet = getAssetRedeemPointWalletTable();

            databaseTableRedeemPointWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableRedeemPointWallet.setFilterOrder(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            databaseTableRedeemPointWallet.setFilterTop(String.valueOf(max));
            databaseTableRedeemPointWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableRedeemPointWallet.loadToMemory();

            return createTransactionList(databaseTableRedeemPointWallet.getRecords());
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        }
        catch (Exception exception){
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }

    }

    public List<AssetRedeemPointWalletTransaction> listsTransactionsByAssets(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException{
        try {
            DatabaseTable databaseTableAssuerIssuerWallet = getAssetRedeemPointWalletTable();
            databaseTableAssuerIssuerWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerIssuerWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerIssuerWallet.loadToMemory();
            return createTransactionList(databaseTableAssuerIssuerWallet.getRecords());
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        }
        catch (Exception exception){
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<AssetRedeemPointWalletTransaction> getTransactionsByTransactionType(TransactionType transactionType, int max, int offset) throws CantGetTransactionsException
    {
        try {
            DatabaseTable databaseTableAssuerIssuerWallet = getAssetRedeemPointWalletTable();

            databaseTableAssuerIssuerWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            databaseTableAssuerIssuerWallet.setFilterOrder(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            databaseTableAssuerIssuerWallet.setFilterTop(String.valueOf(max));
            databaseTableAssuerIssuerWallet.setFilterOffSet(String.valueOf(offset));

            databaseTableAssuerIssuerWallet.loadToMemory();

            return createTransactionList(databaseTableAssuerIssuerWallet.getRecords());
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        }
        catch (Exception exception){
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }

    }

    public AssetRedeemPointWalletTransactionSummary getActorTransactionSummary(String actorPublicKey, BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        return null;
    }

    public List<AssetRedeemPointWalletTransaction> distributeAssets(String assetPublicKey) throws CantGetTransactionsException {
        try
        {
            List<AssetRedeemPointWalletTransaction> assetRedeemPointWalletTransactions = listsTransactionsByAsset(assetPublicKey);
            return assetRedeemPointWalletTransactions;
        }
        catch (CantGetTransactionsException e) {
            throw new CantGetTransactionsException("Get List of Transactions", e, "Error load wallet table ", "Method: distributeAssets()");
        }

    }

    public void updateMemoField(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {
        try
        {
            DatabaseTable databaseTableAssuerRedeemPointWalletBalance = getBalancesTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */

            databaseTableAssuerRedeemPointWalletBalance.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);
            for(DatabaseTableRecord record : databaseTableAssuerRedeemPointWalletBalance.getRecords()){
                record.setStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_MEMO_COLUMN_NAME,memo);
                databaseTableAssuerRedeemPointWalletBalance.updateRecord(record);
            }
            databaseTableAssuerRedeemPointWalletBalance.loadToMemory();
        }catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error",cantLoadTableToMemory,"Error load Transaction table" + transactionID.toString(), "");

        } catch (CantUpdateRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error",cantUpdateRecord,"Error update memo of Transaction " + transactionID.toString(), "");
        } catch(Exception exception){
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private List<AssetRedeemPointWalletTransaction> listsTransactionsByAsset(String assetPublicKey) throws CantGetTransactionsException{
        try {
            DatabaseTable databaseTableRedeemPointWallet = getAssetRedeemPointWalletTable();
            databaseTableRedeemPointWallet.setStringFilter(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);

            databaseTableRedeemPointWallet.loadToMemory();
            return createTransactionList(databaseTableRedeemPointWallet.getRecords());
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        }
        catch (Exception exception){
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    // Read record data and create transactions list
    private List<AssetRedeemPointWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records){

        List<AssetRedeemPointWalletTransaction> transactions = new ArrayList<>();

        for(DatabaseTableRecord record : records)
            transactions.add(constructAssetRedeemPointWalletTransactionFromRecord(record));

        return transactions;
    }



    private AssetRedeemPointWalletTransaction constructAssetRedeemPointWalletTransactionFromRecord(DatabaseTableRecord record){

        String transactionId              = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_ID_COLUMN_NAME);
        String assetPublicKey           = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME);
        String transactionHash          = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TRANSACTION_HASH_COLUMN_NAME);
        TransactionType transactionType = TransactionType.getByCode(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME));
        CryptoAddress addressFrom       = new CryptoAddress();
        addressFrom.setAddress(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_FROM_COLUMN_NAME));
        CryptoAddress addressTo         = new CryptoAddress();
        addressTo.setAddress(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_TO_COLUMN_NAME));
        String actorFromPublicKey       = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_COLUMN_NAME);
        String actorToPublicKey         = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_COLUMN_NAME);
        Actors actorFromType            = Actors.getByCode(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType              = Actors.getByCode(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_TYPE_COLUMN_NAME));
        BalanceType balanceType         = BalanceType.getByCode(record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME));
        long amount                     = record.getLongValue(  AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_AMOUNT_COLUMN_NAME);
        long runningBookBalance         = record.getLongValue(  AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance    = record.getLongValue(  AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp                  = record.getLongValue(  AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME);
        String memo                     = record.getStringValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_MEMO_COLUMN_NAME);
        return new AssetRedeemPointWalletTransactionWrapper(transactionId, transactionHash, assetPublicKey, transactionType, addressFrom, addressTo,
                actorFromPublicKey, actorToPublicKey, actorFromType, actorToType, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo);
    }


    private long calculateQuantityAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getQuantityCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getQuantityCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return getQuantityCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }

    private long getQuantityCurrentBalanceByAsset(BalanceType balanceType, String assetPublicKey)
    {
        try {
            long balanceAmount = 0;
            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME);
            return balanceAmount;
        }
        catch (Exception exception){
            return 0;
        }
    }

    private long calculateQuantityBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getCurrentQuantityBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentQuantityBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return getQuantityCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

}
