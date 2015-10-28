package com.bitdubai.fermat_dap_plugin.layer.module.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 06/10/15.
 */
public class AssetIssuerWalletModuleManager {
    //TODO: Documentar
    AssetIssuerWalletManager assetIssuerWalletManager;
    ActorAssetUserManager actorAssetUserManager;

    /**
     * constructor
     * @param assetIssuerWalletManager
     */
    public AssetIssuerWalletModuleManager(AssetIssuerWalletManager assetIssuerWalletManager,ActorAssetUserManager actorAssetUserManager) {
        this.assetIssuerWalletManager = assetIssuerWalletManager;
        this.actorAssetUserManager    = actorAssetUserManager;
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
            //assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey).distributionAssets(assetPublicKey, walletPublicKey, actorAssetUsers);
            //TODO: Solo para prueba de Distribution
            assetIssuerWalletManager.loadAssetIssuerWallet("walletPublicKeyTest").distributionAssets(assetPublicKey, walletPublicKey, getAllAssetUserActorConnected());
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
}
