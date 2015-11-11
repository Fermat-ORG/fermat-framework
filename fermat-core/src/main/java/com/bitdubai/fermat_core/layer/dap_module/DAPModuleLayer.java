package com.bitdubai.fermat_core.layer.dap_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.dap_module.Asset_factory.AssetFactoryModuleSubSystem;
import com.bitdubai.fermat_dap_api.layer.dap_module.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_module.DAPModuleSubsystem;

/**
 * Created by franklin on 20/09/15.
 */
public class DAPModuleLayer implements PlatformLayer {
    private Plugin mSubAppAssetfactory;
    @Override
    public void start() throws CantStartLayerException {
        mSubAppAssetfactory             = getPlugin(new AssetFactoryModuleSubSystem());
    }

    private Plugin getPlugin(DAPModuleSubsystem dapModuleSubsystem) throws CantStartLayerException{
        try{
            dapModuleSubsystem.start();
            return dapModuleSubsystem.getPlugin();
        }catch (CantStartSubsystemException e){
            throw new CantStartLayerException();
        }
    }
    public Plugin getPluginAssetFactoryModule()            {return mSubAppAssetfactory;}
}
