package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantGetActorAssetIssuerException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/09/15.
 */
public class AssetDistributionTransactionManager implements AssetDistributionManager {

    AssetVaultManager assetVaultManager;
    DigitalAssetDistributor digitalAssetDistributor;
    ErrorManager errorManager;
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;
    //ActorAssetIssuerManager actorAssetIssuerManager;
    //DigitalAssetDistributionVault digitalAssetDistributionVault;

    public AssetDistributionTransactionManager(AssetVaultManager assetVaultManager,
                                               ErrorManager errorManager,
                                               UUID pluginId,
                                               PluginDatabaseSystem pluginDatabaseSystem,
                                               PluginFileSystem pluginFileSystem,
                                               BitcoinNetworkManager bitcoinNetworkManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {
        setAssetVaultManager(assetVaultManager);
        setPluginId(pluginId);
        setPluginDatabaseSystem(pluginDatabaseSystem);
        setPluginFileSystem(pluginFileSystem);
        this.digitalAssetDistributor=new DigitalAssetDistributor(/*assetVaultManager,*/
                errorManager,
                pluginId,
                pluginFileSystem,
                bitcoinNetworkManager);
        this.digitalAssetDistributor.setAssetVaultManager(assetVaultManager);
    }

    public void setAssetTransmissionNetworkServiceManager(AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager) throws CantSetObjectException{
        if(assetTransmissionNetworkServiceManager==null){
            throw new CantSetObjectException("assetTransmissionNetworkServiceManager is null");
        }
        this.digitalAssetDistributor.setAssetTransmissionNetworkServiceManager(assetTransmissionNetworkServiceManager);
    }

    public void setAssetDistributionDatabaseDao(AssetDistributionDao assetDistributionDatabaseDao) throws CantSetObjectException {
        this.digitalAssetDistributor.setAssetDistributionDao(assetDistributionDatabaseDao);
    }

    public void setDigitalAssetDistributionVault(DigitalAssetDistributionVault digitalAssetDistributionVault) throws CantSetObjectException{
        this.digitalAssetDistributor.setDigitalAssetDistributionVault(digitalAssetDistributionVault);
    }

    public void setBitcoinManager(BitcoinNetworkManager bitcoinNetworkManager){
        this.digitalAssetDistributor.setBitcoinCryptoNetworkManager(bitcoinNetworkManager);
    }

    public void setPluginId(UUID pluginId) throws CantSetObjectException{
        if(pluginId==null){
            throw new CantSetObjectException("PluginId is null");
        }
        this.pluginId=pluginId;
    }

    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem)throws CantSetObjectException{
        if(pluginDatabaseSystem==null){
            throw new CantSetObjectException("pluginDatabaseSystem is null");
        }
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException{
        if(pluginFileSystem==null){
            throw new CantSetObjectException("pluginFileSystem is null");
        }
        this.pluginFileSystem=pluginFileSystem;
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException {
        if(errorManager==null){
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager=errorManager;
    }

    public void setAssetVaultManager(AssetVaultManager assetVaultManager) throws CantSetObjectException{
        if(assetVaultManager==null){
            throw new CantSetObjectException("AssetVaultManager is null");
        }
        this.assetVaultManager=assetVaultManager;
    }

    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException{
        if(actorAssetIssuerManager==null){
            throw new CantSetObjectException("actorAssetIssuerManager is null");
        }try{
            this.digitalAssetDistributor.setActorAssetIssuerManager(actorAssetIssuerManager);
        } catch (CantGetActorAssetIssuerException exception) {
            throw new CantSetObjectException(exception, "Setting the Actor Asset Issuer Manager", "Getting the Actor Asset Issuer" );
        }

    }

    @Override
    public void distributeAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute, String walletPublicKey) throws CantDistributeDigitalAssetsException {
        try {
            this.digitalAssetDistributor.setWalletPublicKey(walletPublicKey);
            this.digitalAssetDistributor.distributeAssets(digitalAssetsToDistribute);
        } catch (CantSetObjectException exception) {
            throw new CantDistributeDigitalAssetsException(exception, "Distributing Assets", "The wallet public key is null");
        } catch(Exception exception){
            throw new CantDistributeDigitalAssetsException(exception, "Distributing Assets", "Unexpected exception");
        }

    }
}
