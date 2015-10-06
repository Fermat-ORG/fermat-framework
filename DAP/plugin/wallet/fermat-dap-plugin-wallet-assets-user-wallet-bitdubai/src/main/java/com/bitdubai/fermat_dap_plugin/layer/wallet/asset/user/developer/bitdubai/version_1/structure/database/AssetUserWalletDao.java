package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionSummary;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.exceptions.CantExecuteAssetUserTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.exceptions.CantGetBalanceRecordException;


import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 05/10/15.
 */
public class AssetUserWalletDao {
    //TODO: Manejo de excepciones
    private Database database;

    public AssetUserWalletDao(Database database) {
        this.database = database;
    }


    /*
     * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
     */
    public long getBookBalance() throws CantCalculateBalanceException {
        return 0;
    }


    /*
     * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
     */
    public List<AssetUserWalletList> getBookBalanceByAssets() throws CantCalculateBalanceException {
        return null;
    }


    /*
    * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
    */
    public long getAvailableBalance() throws CantCalculateBalanceException {
        return 0;
    }

    /*
    * getBookBalance must get actual Book Balance global of Asset Issuer wallet, select record from balances table
    */
    public List<AssetUserWalletList> getAvailableBalanceByAsset() throws CantCalculateBalanceException {
        return null;
    }

    /*
    * Add a new debit transaction.
    */
    public void addDebit(final AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {

    }

    /*
    * Add a new credit transaction.
    */
    public void addCredit(final AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, final BalanceType balanceType) throws CantRegisterCreditException {

    }


    public List<AssetUserWalletTransaction> listsTransactionsByAssets(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
        return null;
    }

    public List<AssetUserWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        return null;
    }

    public List<AssetUserWalletTransaction> getTransactionsByTransactionType(TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
        return null;
    }

    public AssetUserWalletTransactionSummary getActorTransactionSummary(String actorPublicKey, BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        return null;
    }

    public void updateMemoField(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {

    }

    // Read record data and create transactions list
    private List<AssetUserWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records) {
        return null;
    }

    private AssetUserWalletTransaction constructAssetIssuerWalletTransactionFromRecord(DatabaseTableRecord record) {
        return null;
    }

    private void executeTransaction(final AssetUserWalletTransactionRecord assetIssuerWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) throws CantExecuteAssetUserTransactionException {

    }

    private DatabaseTableRecord constructAssetBalanceRecord(DigitalAsset digitalAsset, long availableRunningBalance, long bookRunningBalance) {
        return null;
    }

    private DatabaseTableRecord constructAssetIssuerWalletRecord(final AssetUserWalletTransactionRecord assetIssuerWalletTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) {
        return null;
    }

    private long calculateAvailableRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getCurrentAvailableBalanceByAsset(assetPublicKey) + transactionAmount;
    }


    private long calculateBookRunningBalanceByAsset(final long transactionAmount, String assetPublicKey) throws CantGetBalanceRecordException{
        return  getCurrentBookBalanceByAsset(assetPublicKey) + transactionAmount;
    }

    private long getCurrentBookBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return 0;//getCurrentBalanceByAsset(BalanceType.BOOK, assetPublicKey);
    }

    private DatabaseTable getAssetUserWalletTable(){
        return database.getTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TABLE_NAME);
    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException{
        return 0; //getCurrentBalance(BalanceType.AVAILABLE);
    }

    private long getCurrentAvailableBalanceByAsset(String assetPublicKey) throws CantGetBalanceRecordException{
        return 0;//getCurrentBalanceByAsset(BalanceType.AVAILABLE, assetPublicKey);
    }
}