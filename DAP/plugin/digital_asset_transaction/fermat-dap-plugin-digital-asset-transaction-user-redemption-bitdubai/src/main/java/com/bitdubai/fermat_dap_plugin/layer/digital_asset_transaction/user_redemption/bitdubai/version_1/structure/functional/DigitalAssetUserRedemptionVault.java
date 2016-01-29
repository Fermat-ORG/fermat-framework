package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/11/15.
 */
public class DigitalAssetUserRedemptionVault extends AbstractDigitalAssetVault {

    public DigitalAssetUserRedemptionVault(UUID pluginId,
                                           PluginFileSystem pluginFileSystem,
                                           AssetUserWalletManager assetUserWalletManager,
                                           ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setAssetUserWalletManager(assetUserWalletManager);
        setActorAssetUserManager(actorAssetUserManager);
        LOCAL_STORAGE_PATH = "digital-asset-user-redemption/";
    }

    public AssetUserWallet getUserWallet() throws CantLoadWalletException {
        return assetUserWalletManager.loadAssetUserWallet("walletPublicKeyTest");
    }

    /**
     * This method get the XML file and cast the DigitalAssetMetadata object
     *
     * @param internalId AssetIssuing: Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @return
     * @throws CantGetDigitalAssetFromLocalStorageException
     */
    @Override
    public DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String internalId) throws CantGetDigitalAssetFromLocalStorageException {
        try {
            return getUserWallet().getDigitalAssetMetadata(internalId);
        } catch (CantLoadWalletException e) {
            throw new CantGetDigitalAssetFromLocalStorageException();
        }
    }
}
