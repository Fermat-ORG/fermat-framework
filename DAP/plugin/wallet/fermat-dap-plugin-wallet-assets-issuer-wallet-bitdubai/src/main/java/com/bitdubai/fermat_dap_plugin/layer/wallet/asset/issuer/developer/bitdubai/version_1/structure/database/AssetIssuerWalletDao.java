package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.exceptions.CantExecuteAssetIssuerTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.exceptions.CantGetBalanceRecordException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 28/09/15.
 */
public class AssetIssuerWalletDao {
    //TODO: Manejo de excepciones
    private Database database;

    public AssetIssuerWalletDao(Database database){
        this.database = database;
    }


    /*
     * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
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
     * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
     */
    public List<AssetIssuerWalletList> getBookBalanceByAssets() throws CantCalculateBalanceException {
        try {
            return getCurrentBookBalanceByAssets();
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");

        }
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

    /*
    * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
    */
    public List<AssetIssuerWalletList> getAvailableBalanceByAsset() throws CantCalculateBalanceException {
        try{
            return getCurrentAvailableBalanceByAssets();
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }
     /*
     * Add a new debit transaction.
     */
    public void addDebit(final AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterDebitException{
        try {
            if (isTransactionInTable(assetIssuerWalletTransactionRecord.getIdTransaction(), TransactionType.CREDIT, balanceType))
                throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetIssuerWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetIssuerWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(-availableAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(-bookAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            executeTransaction(assetIssuerWalletTransactionRecord,TransactionType.DEBIT ,balanceType, availableRunningBalance, bookRunningBalance);
        }catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteAssetIssuerTransactionException exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
    * Add a new credit transaction.
    */
    public void addCredit(final AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterCreditException{

        try {
            if (isTransactionInTable(assetIssuerWalletTransactionRecord.getIdTransaction(), TransactionType.CREDIT, balanceType))
                throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? assetIssuerWalletTransactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? assetIssuerWalletTransactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalanceByAsset(availableAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            long bookRunningBalance = calculateBookRunningBalanceByAsset(bookAmount, assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            executeTransaction(assetIssuerWalletTransactionRecord,TransactionType.CREDIT ,balanceType, availableRunningBalance, bookRunningBalance);
        }catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteAssetIssuerTransactionException exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    private void executeTransaction(final AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) throws CantExecuteAssetIssuerTransactionException{
        try {
            DatabaseTableRecord assetIssuerWalletRecord = constructAssetIssuerWalletRecord(assetIssuerWalletTransactionRecord, transactionType, balanceType, availableRunningBalance, bookRunningBalance);//DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord assetBalanceRecord = constructAssetBalanceRecord(assetIssuerWalletTransactionRecord.getDigitalAsset(), availableRunningBalance, bookRunningBalance);
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(getAssetIssuerWalletTable(), assetIssuerWalletRecord);
            DatabaseTableRecord record = getBalancesByAssetRecord(assetIssuerWalletTransactionRecord.getDigitalAsset().getPublicKey());
            if (record == null) {
                transaction.addRecordToInsert(getBalancesTable(), assetBalanceRecord);
            } else {
                transaction.addRecordToUpdate(getBalancesTable(), assetBalanceRecord);
            }
        }catch (Exception exception){

        }
    }

    private DatabaseTableRecord constructAssetBalanceRecord(DigitalAsset digitalAsset, long  availableRunningBalance, long bookRunningBalance)
    {
        try {
            DatabaseTableRecord record = getBalancesByAssetRecord(digitalAsset.getPublicKey()); //Si esto devuelve null, entonces creamos un registro en blanco
            if (record == null)
            {
                record = getBalancesTable().getEmptyRecord();
            }
            record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
            record.setLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
            return record;
        }catch (Exception exception){
            return  null;
        }
    }
    private DatabaseTableRecord constructAssetIssuerWalletRecord(final AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance){
        DatabaseTableRecord record = getAssetIssuerWalletTable().getEmptyRecord();
        record.setUUIDValue  (AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TABLE_ID_COLUMN_NAME                   , UUID.randomUUID());
        record.setUUIDValue  (AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__VERIFICATION_ID_COLUMN_NAME           , assetIssuerWalletTransactionRecord.getIdTransaction());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__TYPE_COLUMN_NAME                      , transactionType.getCode());
        record.setLongValue  (AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__AMOUNT_COLUMN_NAME                    , assetIssuerWalletTransactionRecord.getAmount());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__MEMO_COLUMN_NAME                      , assetIssuerWalletTransactionRecord.getMemo());
        record.setLongValue  (AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__TIME_STAMP_COLUMN_NAME                , assetIssuerWalletTransactionRecord.getTimestamp());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__TRANSACTION_HASH_COLUMN_NAME          , assetIssuerWalletTransactionRecord.getDigitalAssetMetadataHash());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ADDRESS_FROM_COLUMN_NAME              , assetIssuerWalletTransactionRecord.getAddressFrom().getAddress());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ADDRESS_TO_COLUMN_NAME                , assetIssuerWalletTransactionRecord.getAddressTo().getAddress());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__BALANCE_TYPE_COLUMN_NAME              , balanceType.getCode());
        record.setLongValue  (AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__RUNNING_AVAILABLE_BALANCE_COLUMN_NAME , availableRunningBalance);
        record.setLongValue  (AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__RUNNING_BOOK_BALANCE_COLUMN_NAME      , bookRunningBalance);
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ACTOR_FROM_COLUMN_NAME                , assetIssuerWalletTransactionRecord.getActorFromPublicKey());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ACTOR_TO_COLUMN_NAME                  , assetIssuerWalletTransactionRecord.getActorToPublicKey());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ACTOR_FROM_TYPE_COLUMN_NAME           , assetIssuerWalletTransactionRecord.getActorFromType().getCode());
        record.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ACTOR_TO_TYPE_COLUMN_NAME             , assetIssuerWalletTransactionRecord.getActorToType().getCode());
        return record;
    }

    private long calculateAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }


    private long calculateBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getCurrentBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

    private boolean isTransactionInTable(final UUID transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable assetIssuerWalletTable = getAssetIssuerWalletTable();
        assetIssuerWalletTable.setUUIDFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.setStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.setStringFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.loadToMemory();
        return !assetIssuerWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getAssetIssuerWalletTable(){
        return database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TABLE_NAME);
    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE);
    }

    private long getCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }

    private long getCurrentBalanceByAsset(BalanceType balanceType, String assetPublicKey)
    {
        try {
            long balanceAmount = 0;
            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByAssetRecord(assetPublicKey).getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            return balanceAmount;
        }
        catch (Exception exception){
            return 0;
        }
    }

    private List<AssetIssuerWalletList> getCurrentAvailableBalanceByAssets() throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE);
    }

    private long getCurrentBookBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.BOOK);
    }

    private List<AssetIssuerWalletList> getCurrentBookBalanceByAssets() throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.BOOK);
    }

    private long getCurrentBalance(final BalanceType balanceType) throws CantGetBalanceRecordException {
        long balanceAmount = 0;
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                balanceAmount += record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
            }
        }
        else{
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                balanceAmount += record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
            }
        }
        return balanceAmount;
    }

    private List<AssetIssuerWalletList> getCurrentBalanceByAsset(final BalanceType balanceType) throws CantGetBalanceRecordException {
        List<AssetIssuerWalletList> issuerWalletBalances= new ArrayList<>();
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                AssetIssuerWalletList assetIssuerWalletBalance = new AssetIssuerWalletBalance();
                assetIssuerWalletBalance.setName(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME_COLUMN_NAME));
                assetIssuerWalletBalance.setDescription(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME));
                assetIssuerWalletBalance.setAssetPublicKey(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME));
                assetIssuerWalletBalance.setBookBalance(0);
                assetIssuerWalletBalance.setAvailableBalance(record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME));
                issuerWalletBalances.add(assetIssuerWalletBalance);
            }
        }
        else{
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                AssetIssuerWalletBalance assetIssuerWalletBalance = new AssetIssuerWalletBalance();
                assetIssuerWalletBalance.setName(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME_COLUMN_NAME));
                assetIssuerWalletBalance.setDescription(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME));
                assetIssuerWalletBalance.setAssetPublicKey(record.getStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME));
                assetIssuerWalletBalance.setAvailableBalance(0);
                assetIssuerWalletBalance.setBookBalance(record.getLongValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME));
                issuerWalletBalances.add(assetIssuerWalletBalance);
            }

        }
        return issuerWalletBalances;
    }

    private List<DatabaseTableRecord> getBalancesRecord() throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            //return balancesTable.getRecords().get(0);
            return balancesTable.getRecords();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTableRecord getBalancesByAssetRecord(String assetPublicKey) throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.getNewFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseFilterType.EQUAL, assetPublicKey);
            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTable getBalancesTable(){
        return database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME);
    }
}