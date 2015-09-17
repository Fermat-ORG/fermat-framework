package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.*;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssetFactoryMiddlewareDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 07/09/15.
 */
public class AssetFactoryMiddlewareManager implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
    /**
     * AssetFactoryMiddlewareManager member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager;

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
    public AssetFactoryMiddlewareManager(com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    private AssetFactoryMiddlewareDao getAssetFactoryMiddlewareDao()
    {
        AssetFactoryMiddlewareDao dao = new AssetFactoryMiddlewareDao(pluginDatabaseSystem, pluginId);
        return dao;
    }

    //TODO:El metodo privado debe de construir un objeto List<ContractProperty> contractProperties = new ArrayList<>()
    //De esa forma poder almacenarlo en la tabla de contract seteando la variable assetFactory.setContractProperties
    //Asi mismo cuando se vaya a enviar el DigitalAsset a la transaccion traer el objeto AssetFactory lleno, y las propiedades del contrato
    //asignarselas mas adelante al objeto DigitalAssetContract, que a su vez sera seteado a ala propiedad setContract del DigitalAsset
    //private void saveAssetFactoryInDatabase(As assetFactory) {
//        DigitalAsset digitalAsset = new DigitalAsset();
//        DigitalAssetContract digitalAssetContract = new DigitalAssetContract();
//        List<ContractProperty> contractProperties = new ArrayList<>();
//        ContractProperty redeemable;
//        ContractProperty expirationDate;
//
//        redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, assetFactory.getReemable());
//        expirationDate = new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, getExpirationDate());
//
//        contractProperties.add(redeemable);
//        contractProperties.add(expirationDate);
//
//        assetFactory.setContractProperties(contractProperties);
    //}


    @Override
    public void setErrorManager(com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager) {
        this.errorManager = errorManager;
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

}