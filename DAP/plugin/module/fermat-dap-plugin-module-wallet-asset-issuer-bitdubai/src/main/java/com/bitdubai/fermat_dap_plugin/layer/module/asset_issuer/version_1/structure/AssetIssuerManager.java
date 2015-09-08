package com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.exceptions.CantCreateAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.exceptions.CantSaveAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.interfaces.AssetIssuer;

import java.util.UUID;
import java.util.logging.ErrorManager;

/**
 * Created by franklin on 07/09/15.
 */
//Falta implementar DealsWithError
public class AssetIssuerManager implements DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
    /**
     * AssetIssuerManager member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface mmeber variables
     */
    LogManager logManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Constructor
     *
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public AssetIssuerManager(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }
    //Aca colocar todos los metodos con la logica propia que contendra el manejo del pluggin.
    //Esta clase sera una instancia en un metodo del root del plugin

    public void IssueAsset(DigitalAsset digitalAsset) {

    }

    public long getEstimatedFeeValue(DigitalAsset digitalAsset) {
        return 0;
    }

    public boolean verifiedGenesisAmount(DigitalAsset digitalAsset) {
        return false;
    }

    public void createAssetIssuer(AssetIssuer assetIssuer) throws CantCreateAssetIssuerException {

    }

    public void saveAssetIssuer(AssetIssuer assetIssuer) throws CantSaveAssetIssuerException {

    }

    public void removeAssetIssuer(AssetIssuer assetIssuer) {

    }

}
