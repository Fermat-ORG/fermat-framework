package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces.UserRedemptionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/10/15.
 */
public class UserRedemptionTransactionManager implements UserRedemptionManager {

    AssetVaultManager assetVaultManager;
    UserRedemptionRedeemer userRedemptionRedeemer;
    ErrorManager errorManager;
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;

    public UserRedemptionTransactionManager(AssetVaultManager assetVaultManager,
                                            ErrorManager errorManager,
                                            UUID pluginId,
                                            PluginDatabaseSystem pluginDatabaseSystem,
                                            PluginFileSystem pluginFileSystem) throws CantSetObjectException, CantExecuteDatabaseOperationException {
        setAssetVaultManager(assetVaultManager);
        setPluginId(pluginId);
        setPluginDatabaseSystem(pluginDatabaseSystem);
        setPluginFileSystem(pluginFileSystem);
        this.userRedemptionRedeemer =new UserRedemptionRedeemer(/*assetVaultManager,*/
                errorManager,
                pluginId,
                pluginFileSystem);
        this.userRedemptionRedeemer.setAssetVaultManager(assetVaultManager);
    }

    public void setAssetTransmissionNetworkServiceManager(AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager) throws CantSetObjectException{
        if(assetTransmissionNetworkServiceManager==null){
            throw new CantSetObjectException("assetTransmissionNetworkServiceManager is null");
        }
        this.userRedemptionRedeemer.setAssetTransmissionNetworkServiceManager(assetTransmissionNetworkServiceManager);
    }

    public void setUserRedemptionDao(UserRedemptionDao userRedemptionDao) throws CantSetObjectException {
        this.userRedemptionRedeemer.setUserRedemptionDao(userRedemptionDao);
    }

    public void setDigitalAssetDistributionVault(DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault) throws CantSetObjectException{
        this.userRedemptionRedeemer.setDigitalAssetUserRedemptionVault(digitalAssetUserRedemptionVault);
    }

    public void setBitcoinManager(BitcoinNetworkManager bitcoinNetworkManager){
        this.userRedemptionRedeemer.setBitcoinCryptoNetworkManager(bitcoinNetworkManager);
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

    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantGetAssetUserActorsException {
        this.userRedemptionRedeemer.setActorAssetUserManager(actorAssetUserManager);
    }

    @Override
    public void redeemAssetToRedeemPoint(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint, String walletPublicKey) throws CantRedeemDigitalAssetException {
        try {
            this.userRedemptionRedeemer.setWalletPublicKey(walletPublicKey);
            this.userRedemptionRedeemer.deliverDigitalAssetToRemoteDevice(digitalAssetMetadata, actorAssetRedeemPoint);
        } catch (CantSetObjectException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Starting the redeem process", "The wallet public key is null");
        }

    }
}
