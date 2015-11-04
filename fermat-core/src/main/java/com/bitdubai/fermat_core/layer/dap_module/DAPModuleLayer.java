package com.bitdubai.fermat_core.layer.dap_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.dap_module.Asset_factory.AssetFactoryModuleSubSystem;
import com.bitdubai.fermat_core.layer.dap_module.Wallet.AssetIssuerWalletModuleSubSystem;
import com.bitdubai.fermat_core.layer.dap_module.Wallet.AssetRedeemPointWalletModuleSubSystem;
import com.bitdubai.fermat_core.layer.dap_module.Wallet.AssetUserWalletModuleSubSystem;
import com.bitdubai.fermat_dap_api.layer.dap_module.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_module.DAPModuleSubsystem;

/**
 * Created by franklin on 20/09/15.
 */
public class DAPModuleLayer implements PlatformLayer {
    private Plugin mSubAppAssetfactory;
    private Plugin mModuleAssetIssuerWallet;
    private Plugin mModuleAssetUserWallet;
    private Plugin mModuleAssetRedeemPointWallet;
    @Override
    public void start() throws CantStartLayerException {
        mSubAppAssetfactory             = getPlugin(new AssetFactoryModuleSubSystem());
        mModuleAssetIssuerWallet        = getPlugin(new AssetIssuerWalletModuleSubSystem());
        mModuleAssetUserWallet          = getPlugin(new AssetUserWalletModuleSubSystem());
        mModuleAssetRedeemPointWallet   = getPlugin(new AssetRedeemPointWalletModuleSubSystem());
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
    public Plugin getPluginModuleAssetIssuerWallet()       {return mModuleAssetIssuerWallet;}
    public Plugin getPluginModuleAssetUserWallet()         {return mModuleAssetUserWallet;}
    public Plugin getPluginModuleAssetRedeemPointrWallet() {return mModuleAssetRedeemPointWallet;}
}
