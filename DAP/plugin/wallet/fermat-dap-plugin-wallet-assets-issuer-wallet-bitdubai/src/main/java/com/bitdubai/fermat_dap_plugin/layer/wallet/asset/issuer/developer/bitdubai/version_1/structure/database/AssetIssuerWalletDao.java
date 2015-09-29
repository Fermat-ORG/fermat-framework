package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.exceptions.CantGetBalanceRecordException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franklin on 28/09/15.
 */
public class AssetIssuerWalletDao {
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
    public List<AssetIssuerWalletBalance> getBookBalanceByAsset() throws CantCalculateBalanceException {
        try {
            return getCurrentBookBalanceByAsset();
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
    public List<AssetIssuerWalletBalance> getAvailableBalanceByAsset() throws CantCalculateBalanceException {
        try{
            return getCurrentAvailableBalanceByAsset();
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

    }

    /*
    * Add a new credit transaction.
    */
    public void addCredit(final AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterCreditException{

    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE);
    }

    private List<AssetIssuerWalletBalance> getCurrentAvailableBalanceByAsset() throws CantGetBalanceRecordException{
        return getCurrentBalanceByAsset(BalanceType.AVAILABLE);
    }

    private long getCurrentBookBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.BOOK);
    }

    private List<AssetIssuerWalletBalance> getCurrentBookBalanceByAsset() throws CantGetBalanceRecordException{
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

    private List<AssetIssuerWalletBalance> getCurrentBalanceByAsset(final BalanceType balanceType) throws CantGetBalanceRecordException {
        List<AssetIssuerWalletBalance> issuerWalletBalances= new ArrayList<>();
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                AssetIssuerWalletBalance assetIssuerWalletBalance = new AssetIssuerWalletBalance();
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

    private DatabaseTableRecord getBalancesByAssetRecord() throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            //balancesTable.getNewFilter(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseFilterType.EQUAL, assetPublicKey);
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