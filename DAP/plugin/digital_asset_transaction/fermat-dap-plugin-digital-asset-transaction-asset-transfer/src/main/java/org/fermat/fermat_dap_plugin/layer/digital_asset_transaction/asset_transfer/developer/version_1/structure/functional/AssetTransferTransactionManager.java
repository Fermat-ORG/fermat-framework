package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_transfer.exceptions.CantTransferDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_transfer.interfaces.AssetTransferManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.database.AssetTransferDAO;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Víctor Mars (marsvicam@gmail.com) on 08/01/16.
 */
public class AssetTransferTransactionManager implements AssetTransferManager {

    AssetVaultManager assetVaultManager;
    DigitalAssetTransferer digitalAssetTransferer;
    ErrorManager errorManager;
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;
    //ActorAssetIssuerManager actorAssetIssuerManager;
    //DigitalAssetDistributionVault digitalAssetDistributionVault;

    public AssetTransferTransactionManager(AssetVaultManager assetVaultManager,
                                           ErrorManager errorManager,
                                           UUID pluginId,
                                           PluginDatabaseSystem pluginDatabaseSystem,
                                           PluginFileSystem pluginFileSystem,
                                           BitcoinNetworkManager bitcoinNetworkManager,
                                           org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.functional.DigitalAssetTransferVault digitalAssetTransferVault,
                                           AssetTransferDAO assetTransferDAO,
                                           AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager,
                                           ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {

        this.digitalAssetTransferer = new DigitalAssetTransferer(assetVaultManager,
                errorManager,
                pluginId,
                pluginFileSystem,
                bitcoinNetworkManager);

        setAssetVaultManager(assetVaultManager);
        setPluginId(pluginId);
        setPluginDatabaseSystem(pluginDatabaseSystem);
        setPluginFileSystem(pluginFileSystem);
        setAssetVaultManager(assetVaultManager);
        setDigitalAssetDistributionVault(digitalAssetTransferVault);
        setAssetTransferDatabaseDao(assetTransferDAO);
        setAssetTransmissionNetworkServiceManager(assetTransmissionNetworkServiceManager);
        setBitcoinManager(bitcoinNetworkManager);
        setActorAssetUserManager(actorAssetUserManager);

    }

    public void setAssetTransmissionNetworkServiceManager(AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager) throws CantSetObjectException {
        if (assetTransmissionNetworkServiceManager == null) {
            throw new CantSetObjectException("assetTransmissionNetworkServiceManager is null");
        }
        this.digitalAssetTransferer.setAssetTransmissionNetworkServiceManager(assetTransmissionNetworkServiceManager);
    }

    public void setAssetTransferDatabaseDao(AssetTransferDAO assetDistributionDatabaseDao) throws CantSetObjectException {
        this.digitalAssetTransferer.setAssetTransferDao(assetDistributionDatabaseDao);
    }

    public void setDigitalAssetDistributionVault(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.functional.DigitalAssetTransferVault digitalAssetTransferVault) throws CantSetObjectException {
        this.digitalAssetTransferer.setDigitalAssetTransferVault(digitalAssetTransferVault);
    }

    public void setBitcoinManager(BitcoinNetworkManager bitcoinNetworkManager) {
        this.digitalAssetTransferer.setBitcoinCryptoNetworkManager(bitcoinNetworkManager);
    }

    public void setPluginId(UUID pluginId) throws CantSetObjectException {
        if (pluginId == null) {
            throw new CantSetObjectException("PluginId is null");
        }
        this.pluginId = pluginId;
    }

    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) throws CantSetObjectException {
        if (pluginDatabaseSystem == null) {
            throw new CantSetObjectException("pluginDatabaseSystem is null");
        }
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException {
        if (pluginFileSystem == null) {
            throw new CantSetObjectException("pluginFileSystem is null");
        }
        this.pluginFileSystem = pluginFileSystem;
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException {
        if (errorManager == null) {
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager = errorManager;
    }

    public void setAssetVaultManager(AssetVaultManager assetVaultManager) throws CantSetObjectException {
        if (assetVaultManager == null) {
            throw new CantSetObjectException("AssetVaultManager is null");
        }
        this.assetVaultManager = assetVaultManager;
    }

    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        if (actorAssetUserManager == null) {
            throw new CantSetObjectException("actorAssetIssuerManager is null");
        }
        try {
            this.digitalAssetTransferer.setActorAssetUserManager(actorAssetUserManager);
        } catch (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantGetActorAssetIssuerException exception) {
            throw new CantSetObjectException(exception, "Setting the Actor Asset Issuer Manager", "Getting the Actor Asset Issuer");
        }

    }

    @Override
    public void transferAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute, String walletPublicKey) throws CantTransferDigitalAssetsException {
        try {
            this.digitalAssetTransferer.setWalletPublicKey(walletPublicKey);
            this.digitalAssetTransferer.transferAssets(digitalAssetsToDistribute);
        } catch (Exception exception) {
            throw new CantTransferDigitalAssetsException(exception, "Distributing Assets", "Unexpected exception");
        }
    }
}
