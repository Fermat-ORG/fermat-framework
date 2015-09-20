package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.DealsWithAssetFactory;

/**
 * Created by franklin on 20/09/15.
 */
public class AssetFactorySupAppModuleManager implements DealsWithAssetFactory {
    AssetFactoryManager assetFactoryManager;
    @Override
    public void setAssetFactoryManager(AssetFactoryManager assetFactoryManager) {
        this.assetFactoryManager = assetFactoryManager;
    }
    /**
     * constructor
     * @param assetFactoryManager
     */
    public AssetFactorySupAppModuleManager(AssetFactoryManager assetFactoryManager) {
        this.assetFactoryManager = assetFactoryManager;
    }
}
