package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletsListFailedToLoadException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
//import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.WalletsListFailedToLoadException;
//import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.DealsWithAssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.bitdubai.version_1.structure.AssetFactorySupAppModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Franklin on 07/09/15.
 */
public class AssetFactorySubAppModulePluginRoot implements DealsWithAssetFactory, AssetFactoryModuleManager, DealsWithLogger, LogManagerForDevelopers, Plugin, Service {

    UUID pluginId;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<FermatEventListener> listenersAdded = new ArrayList<>();

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    AssetFactorySupAppModuleManager assetFactorySupAppModuleManager;
    AssetFactoryManager assetFactoryManager;

    @Override
    public void start() throws CantStartPluginException {
        assetFactorySupAppModuleManager = new AssetFactorySupAppModuleManager(assetFactoryManager);
        //test();
        //System.out.println("******* Asset Factory Module Init ******");
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.fermat_dmp_plugin.layer.module.wallet_factory.developer.bitdubai.version_1.WalletFactoryModulePluginRoot");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (AssetFactorySubAppModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetFactorySubAppModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetFactorySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetFactorySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    @Override
    public IdentityAssetIssuer getLoggedIdentityAssetIssuer() {
        //TODO: Immplementar preguntar a Nerio
        return null;
    }

    @Override
    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
        assetFactorySupAppModuleManager.saveAssetFactory(assetFactory);
    }

    @Override
    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException {
        assetFactorySupAppModuleManager.removeAssetFactory(publicKey);
    }

    @Override
    public void publishAsset(AssetFactory assetFactory, BlockchainNetworkType blockchainNetworkType) throws CantSaveAssetFactoryException {
        assetFactorySupAppModuleManager.publishAssetFactory(assetFactory, blockchainNetworkType);
    }

    @Override
    public AssetFactory newAssetFactoryEmpty() throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException {
        return assetFactorySupAppModuleManager.newAssetFactoryEmpty();
    }

    @Override
    public AssetFactory getAssetFactoryByPublicKey(String assetPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactorySupAppModuleManager.getAssetFactory(assetPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactorySupAppModuleManager.getAssetsFactoryByIssuer(issuerIdentityPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByState(State state) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactorySupAppModuleManager.getAssetsFactoryByState(state);
    }

    @Override
    public List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactorySupAppModuleManager.getAssetsFactoryAll();
    }

    @Override
    public List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException {
        return assetFactorySupAppModuleManager.getInstallWallets();
    }

    @Override
    public boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy {
        return assetFactorySupAppModuleManager.isReadyToPublish(assetPublicKey);
    }

    public List<AssetFactory> test(){
        List<AssetFactory> assetFactory = null;
        try {
            assetFactory = getAssetFactoryAll();

        }catch (Exception e){
            System.out.println("******* Test Asset Factory Module, Error. Franklin ******" );
            e.printStackTrace();
        }
        return assetFactory;
    }

    @Override
    public void setAssetFactoryManager(AssetFactoryManager assetFactoryManager) {
        this.assetFactoryManager = assetFactoryManager;
    }
}
