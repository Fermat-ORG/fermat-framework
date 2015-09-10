package com.bitdubai.fermat_core.layer.dap_middleware;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.dap_middleware.asset_factory.AssetFactorySubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantStartSubsystemException;

/**
 * Created by franklin on 10/09/15.
 */
public class DAPMiddlewareLayer implements PlatformLayer {
    private Plugin mAssetFactoryPlugin;

    @Override
    public void start() throws CantStartLayerException {
        mAssetFactoryPlugin = getPlugin( new AssetFactorySubsystem());
    }

    private Plugin getPlugin(AssetFactorySubsystem assetFactorySubsystem) throws CantStartLayerException {
        try {
            assetFactorySubsystem.star();
            return assetFactorySubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }
}
