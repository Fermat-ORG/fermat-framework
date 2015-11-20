package com.bitdubai.fermat_dap_plugin.layer.module.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 06/10/15.
 */
public class AssetIssuerWalletModuleManager {
    //TODO: Documentar
    public static final String PATH_DIRECTORY = "assetissuer/assets";
    AssetIssuerWalletManager assetIssuerWalletManager;
    ActorAssetUserManager    actorAssetUserManager;
    AssetDistributionManager assetDistributionManager;
    UUID                     pluginId;
    PluginFileSystem         pluginFileSystem;

    /**
     * constructor
     * @param assetIssuerWalletManager
     */
    public AssetIssuerWalletModuleManager(AssetIssuerWalletManager assetIssuerWalletManager,ActorAssetUserManager actorAssetUserManager, AssetDistributionManager assetDistributionManager, UUID pluginId, PluginFileSystem pluginFileSystem) {
        this.assetIssuerWalletManager = assetIssuerWalletManager;
        this.actorAssetUserManager    = actorAssetUserManager;
        this.assetDistributionManager = assetDistributionManager;
        this.pluginId                 = pluginId;
        this.pluginFileSystem         = pluginFileSystem;
    }

    public List<AssetIssuerWalletList>  getAssetIssuerWalletBalancesAvailable(String publicKey) throws CantLoadWalletException{
        try{
            return assetIssuerWalletManager.loadAssetIssuerWallet(publicKey).getBookBalance(BalanceType.AVAILABLE).getAssetIssuerWalletBalancesAvailable();
        }catch (Exception exception){
            throw new CantLoadWalletException("Error load Wallet Balances Available", exception, "Method: getAssetIssuerWalletBalancesAvailable", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public List<AssetIssuerWalletList>  getAssetIssuerWalletBalancesBook(String publicKey) throws CantLoadWalletException{
        try{
            return assetIssuerWalletManager.loadAssetIssuerWallet(publicKey).getBookBalance(BalanceType.BOOK).getAssetIssuerWalletBalancesBook();
        }catch (Exception exception){
            throw new CantLoadWalletException("Error load Wallet Balances Book", exception, "Method: getAssetIssuerWalletBalancesBook", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public void distributionAssets(String assetPublicKey, String walletPublicKey, List<ActorAssetUser> actorAssetUsers) throws CantDistributeDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, CantLoadWalletException {
        try {
            if (getAllAssetUserActorConnected().size() > 0){
                System.out.println("******* ASSET DISTRIBUTION TEST (Init Distribution)******");
                walletPublicKey = "walletPublicKeyTest"; //TODO: Solo para la prueba del Distribution
                HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = createMapDistribution(walletPublicKey, assetPublicKey, getAllAssetUserActorConnected());

                assetDistributionManager.distributeAssets(hashMap, walletPublicKey);
            }else{
                System.out.println("******* ASSET DISTRIBUTION TEST (The list must contain at least one Actor User)******");
            }
        }catch (Exception exception){
            throw new CantLoadWalletException("Error distribution Assets", exception, "Method: distributionAssets", "Class: AssetIssuerWalletModuleManager");
        }
    }
    public void setAssetIssuerManager(AssetIssuerWalletManager assetIssuerWalletManager) {
        this.assetIssuerWalletManager = assetIssuerWalletManager;
    }

    public List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException {
        try {
            return actorAssetUserManager.getAllAssetUserActorConnected();
        }catch (Exception exception){
            throw new CantGetAssetUserActorsException("Error Get Actor Connected", exception, "Method: getAllAssetUserActorConnected", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public List<AssetIssuerWalletTransaction>  getTransactionsAssetAll(String walletPublicKey, String assetPublicKey) throws CantGetTransactionsException {
        try {
            return assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey).getTransactionsAssetAll(assetPublicKey);
        }catch (Exception exception){
            throw new CantGetTransactionsException("Error Error load Wallet Asset Transaction", exception, "Method: getTransactionsAssetAll", "Class: AssetIssuerWalletModuleManager");
        }
    }

    private HashMap<DigitalAssetMetadata, ActorAssetUser> createMapDistribution(String walletPublicKey, String assetPublicKey, List<ActorAssetUser> actorAssetUsers) throws CantGetTransactionsException, FileNotFoundException, CantCreateFileException, CantLoadWalletException, CantGetDigitalAssetFromLocalStorageException {
        List<AssetIssuerWalletTransaction> assetIssuerWalletTransactions = getTransactionsAssetAll(walletPublicKey, assetPublicKey);
        //TODO: Comentado para la prueba del Distribution no Borrar
        HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = new HashMap<>();
        int i = 0;
        for (AssetIssuerWalletTransaction assetIssuerWalletTransactionList : assetIssuerWalletTransactions){
            //TODO: Optimizar para que vea el registro de la tabla Balance Wallet
            DigitalAssetMetadata digitalAssetMetadata = assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey).getDigitalAssetMetadata(assetIssuerWalletTransactionList.getAssetPublicKey());
            digitalAssetMetadata.setGenesisTransaction(assetIssuerWalletTransactionList.getTransactionHash());
            hashMap.put(digitalAssetMetadata, actorAssetUsers.get(i));

            if (i > actorAssetUsers.size()){
                break;
            }

            i++;
        }
        return hashMap;
    }
}
