package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
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
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.bitdubai.version_1.structure.AssetFactorySupAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import java.util.List;

/**
 * TODO explain here the main functionality of the plug-in.
 *
 * Created by Franklin on 07/09/15.
 */
public final class AssetFactorySubAppModulePluginRoot extends AbstractPlugin implements
        AssetFactoryModuleManager {

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.ASSET_FACTORY)
    private AssetFactoryManager assetFactoryManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    // TODO ADDED ERROR MANAGER REFERENCE, PLEASE MAKE USE OF THE ERROR MANAGER.

    public AssetFactorySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private AssetFactorySupAppModuleManager assetFactorySupAppModuleManager;

    @Override
    public void start() throws CantStartPluginException {

        assetFactorySupAppModuleManager = new AssetFactorySupAppModuleManager(assetFactoryManager);
        //test();
        this.serviceStatus = ServiceStatus.STARTED;
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
        assetFactorySupAppModuleManager.publishAssetFactory(assetFactory, BlockchainNetworkType.DEFAULT);
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
}
