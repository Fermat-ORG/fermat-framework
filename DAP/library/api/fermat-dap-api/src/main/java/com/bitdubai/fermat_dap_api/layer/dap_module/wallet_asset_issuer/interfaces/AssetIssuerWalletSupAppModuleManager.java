package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces;


import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;

/**
 * Created by franklin on 11/09/15.
 */
public interface AssetIssuerWalletSupAppModuleManager extends ModuleManager {

    /**
     * (non-Javadoc)
     * @see List<AssetIssuerWalletList>() getAssetIssuerWalletBalancesAvailable(String publicKey)
     */
    List<AssetIssuerWalletList> getAssetIssuerWalletBalancesAvailable(String publicKey) throws CantLoadWalletException;
    /**
     * (non-Javadoc)
     * @see List<AssetIssuerWalletList> getAssetIssuerWalletBalancesBook(String publicKey)
     */
    List<AssetIssuerWalletList> getAssetIssuerWalletBalancesBook(String publicKey) throws CantLoadWalletException;


    void distributionAssets(String assetPublicKey, String walletPublicKey, List<ActorAssetUser> actorAssetUsers)  throws CantDistributeDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException, CantLoadWalletException;

    List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException;

    List<AssetIssuerWalletTransaction>  getTransactionsAssetAll(String walletPublicKey, String assetPublicKey) throws CantGetTransactionsException;
}
