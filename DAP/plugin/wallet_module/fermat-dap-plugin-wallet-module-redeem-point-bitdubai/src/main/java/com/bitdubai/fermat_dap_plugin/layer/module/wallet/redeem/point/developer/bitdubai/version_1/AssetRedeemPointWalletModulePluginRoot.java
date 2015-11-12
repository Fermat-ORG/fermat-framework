package com.bitdubai.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.DealsWithAssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.bitdubai.version_1.structure.AssetRedeemPointWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * Created by Franklin on 07/09/15.
 */
public class AssetRedeemPointWalletModulePluginRoot extends AbstractPlugin implements
        DealsWithAssetRedeemPointWallet,
        AssetRedeemPointWalletSubAppModule {

    // TODO INIT MISSING REFERENCES (ongoing migration)
    AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    // TODO INIT MISSING REFERENCES (ongoing migration)

    AssetRedeemPointWalletModuleManager assetRedeemPointWalletModuleManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    public AssetRedeemPointWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * (non-Javadoc)
     * @see Service#start()
     */
    @Override
    public void start() throws CantStartPluginException {
        try {
            assetRedeemPointWalletModuleManager = new AssetRedeemPointWalletModuleManager(assetRedeemPointWalletManager);
            System.out.println("******* Asset Redeem Point Wallet Module Init ******");
            this.serviceStatus = ServiceStatus.STARTED;
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    @Override
    public List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalancesAvailable(String publicKey) throws CantLoadWalletException {
        return assetRedeemPointWalletModuleManager.getAssetRedeemPointWalletBalancesAvailable(publicKey);
    }

    @Override
    public List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalancesBook(String publicKey) throws CantLoadWalletException {
        return assetRedeemPointWalletModuleManager.getAssetRedeemPointWalletBalancesBook(publicKey);
    }

    @Override
    public void setAssetReddemPointManager(AssetRedeemPointWalletManager assetRedeemPointWalletManager) {
        this.assetRedeemPointWalletManager = assetRedeemPointWalletManager;
    }
}
