package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

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

    public AssetUserWallet getUserWallet(BlockchainNetworkType networkType) throws CantLoadWalletException {
        return assetUserWalletManager.loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY, networkType);
    }


    @Override
    public DigitalAssetMetadata updateMetadataTransactionChain(String genesisTx, String txHash, String blockHash, BlockchainNetworkType networkType) throws CantCreateDigitalAssetFileException, CantGetDigitalAssetFromLocalStorageException {
        DigitalAssetMetadata digitalAssetMetadata = getDigitalAssetMetadataFromWallet(genesisTx, networkType);
        digitalAssetMetadata.addNewTransaction(txHash, blockHash);
        persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, genesisTx);
        return digitalAssetMetadata;
    }

    public DigitalAssetMetadata getDigitalAssetMetadataFromWallet(String internalId, BlockchainNetworkType networkType) throws CantGetDigitalAssetFromLocalStorageException {
        try {
            return getUserWallet(networkType).getDigitalAssetMetadata(internalId);
        } catch (CantLoadWalletException e) {
            throw new CantGetDigitalAssetFromLocalStorageException();
        }
    }
}
