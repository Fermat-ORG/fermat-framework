package com.bitdubai.fermat_dap_plugin.layer.module.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.DealsWithAssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.module.asset.user.developer.bitdubai.version_1.structure.AssetUserWalletModule;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * Created by Franklin on 07/09/15.
 */
public class AssetUserWalletModulePluginRoot extends AbstractPlugin implements
        DealsWithAssetUserWallet,
        AssetUserWalletSubAppModuleManager {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    AssetUserWalletModule assetUserWalletModule;

    // TODO INIT MISSING REFERENCES (ongoing migration)
    AssetUserWalletManager assetUserWalletManager;
    // TODO INIT MISSING REFERENCES (ongoing migration)


    public AssetUserWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            assetUserWalletModule = new AssetUserWalletModule(assetUserWalletManager);
            System.out.println("******* Asset User Wallet Module Init ******");
            this.serviceStatus = ServiceStatus.STARTED;
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    @Override
    public void setAssetUserManager(AssetUserWalletManager assetUserWalletManager) {
        this.assetUserWalletManager = assetUserWalletManager;
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalancesAvailable(String publicKey) throws CantLoadWalletException {
        return assetUserWalletModule.getAssetUserWalletBalancesAvailable(publicKey);
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalancesBook(String publicKey) throws CantLoadWalletException {
        return assetUserWalletModule.getAssetUserWalletBalancesBook(publicKey);
    }
}
