package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.exceptions.CantGetBalanceRecordException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 14/10/15.
 */
public class AssetRedeemPointWalletDao implements DealsWithPluginFileSystem {
    //TODO: Manejo de excepciones
    public static final String PATH_DIRECTORY = "asset-redeem-point-swap/";//digital-asset-swap/"
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

}
