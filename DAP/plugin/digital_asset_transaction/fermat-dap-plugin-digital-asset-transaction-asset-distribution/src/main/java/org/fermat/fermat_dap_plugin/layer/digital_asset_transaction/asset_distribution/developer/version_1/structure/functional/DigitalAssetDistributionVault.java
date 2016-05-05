package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/10/15.
 */
public class DigitalAssetDistributionVault extends AbstractDigitalAssetVault {
    ErrorManager errorManager;

    public DigitalAssetDistributionVault(UUID pluginId,
                                         PluginFileSystem pluginFileSystem,
                                         ErrorManager errorManager,
                                         AssetIssuerWalletManager assetIssuerWalletManager,
                                         ActorAssetIssuerManager actorAssetIssuerManager,
                                         BitcoinNetworkManager bitcoinNetworkManager) throws CantSetObjectException {
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setErrorManager(errorManager);
        LOCAL_STORAGE_PATH = "digital-asset-transmission/";
        setAssetIssuerWalletManager(assetIssuerWalletManager);
        setActorAssetIssuerManager(actorAssetIssuerManager);
        setBitcoinCryptoNetworkManager(bitcoinNetworkManager);
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException {
        if (errorManager == null) {
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager = errorManager;
    }

    public AssetIssuerWallet getIssuerWallet(BlockchainNetworkType networkType) throws CantLoadWalletException {
        return this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey, networkType);
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
            return getIssuerWallet(networkType).getDigitalAssetMetadata(internalId);
        } catch (CantLoadWalletException e) {
            throw new CantGetDigitalAssetFromLocalStorageException();
        }
    }
}
