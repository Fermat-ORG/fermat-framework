package com.bitdubai.fermat_core.layer.dap_middleware;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.DAPMiddlewareSubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.AssetFactorySubsystem;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;


/**
 * Created by franklin on 10/09/15.
 */
public class DAPMiddlewareLayer implements PlatformLayer {
    private Plugin mAssetFactoryPlugin;

    @Override
    public void start() throws CantStartLayerException {
        mAssetFactoryPlugin = getPlugin(new com.bitdubai.fermat_core.layer.dap_middleware.Asset_factory.AssetFactorySubsystem());
    }

    private Plugin getPlugin(DAPMiddlewareSubsystem dapMiddlewareSubsystem) throws CantStartLayerException
    {
        try {
            dapMiddlewareSubsystem.start();
            return  dapMiddlewareSubsystem.getPlugin();
        }
        catch (CantStartSubsystemException e)
        {
            throw new CantStartLayerException();
        }
    }

    public Plugin getPluginAssetFactory() {return mAssetFactoryPlugin;}

}
