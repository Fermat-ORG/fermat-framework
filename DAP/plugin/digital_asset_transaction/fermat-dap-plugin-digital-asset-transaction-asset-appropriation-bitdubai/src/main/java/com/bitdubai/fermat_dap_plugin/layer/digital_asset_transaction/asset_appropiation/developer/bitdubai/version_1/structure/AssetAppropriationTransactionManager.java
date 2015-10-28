package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantExecuteAppropriationTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by frank on 16/10/15.
 */
public class AssetAppropriationTransactionManager implements AssetAppropriationManager {

    AssetVaultManager assetVaultManager;
    ErrorManager errorManager;
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;

    public AssetAppropriationTransactionManager(AssetVaultManager assetVaultManager,
                                                ErrorManager errorManager,
                                                UUID pluginId,
                                                PluginDatabaseSystem pluginDatabaseSystem,
                                                PluginFileSystem pluginFileSystem) throws CantSetObjectException, CantExecuteDatabaseOperationException {
        setErrorManager(errorManager);
        setPluginId(pluginId);
        setPluginDatabaseSystem(pluginDatabaseSystem);
        setPluginFileSystem(pluginFileSystem);
    }

    public void setPluginId(UUID pluginId) throws CantSetObjectException{
        if(pluginId == null){
            throw new CantSetObjectException("pluginId is null");
        }
        this.pluginId = pluginId;
    }

    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem)throws CantSetObjectException{
        if(pluginDatabaseSystem == null){
            throw new CantSetObjectException("pluginDatabaseSystem is null");
        }
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException{
        if(pluginFileSystem == null){
            throw new CantSetObjectException("pluginFileSystem is null");
        }
        this.pluginFileSystem = pluginFileSystem;
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException {
        if(errorManager == null){
            throw new CantSetObjectException("errorManager is null");
        }
        this.errorManager = errorManager;
    }

    public void setAssetVaultManager(AssetVaultManager assetVaultManager) throws CantSetObjectException{
        if(assetVaultManager==null){
            throw new CantSetObjectException("AssetVaultManager is null");
        }
        this.assetVaultManager=assetVaultManager;
    }

    @Override
    public void appropriateAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToAppropriate, String walletPublicKey) throws CantExecuteAppropriationTransactionException {
        //TODO implement
    }
}
