package org.fermat.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 16/10/15.
 */
public class AssetRedeemPointWalletModuleManager {
    AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    RedeemPointIdentityManager redeemPointIdentityManager;
    UUID pluginId;
    PluginFileSystem pluginFileSystem;
    ErrorManager errorManager;

    public AssetRedeemPointWalletModuleManager(AssetRedeemPointWalletManager assetRedeemPointWalletManager,
                                               RedeemPointIdentityManager redeemPointIdentityManager,
                                               UUID pluginId,
                                               PluginFileSystem pluginFileSystem,
                                               ErrorManager errorManager) {

        this.assetRedeemPointWalletManager = assetRedeemPointWalletManager;
        this.redeemPointIdentityManager = redeemPointIdentityManager;
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
        this.errorManager = errorManager;
    }

    public List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalances(String publicKey, BlockchainNetworkType networkType) throws CantLoadWalletException {
        try {
            return assetRedeemPointWalletManager.loadAssetRedeemPointWallet(publicKey, networkType).getBalance().getAssetIssuerWalletBalances();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantLoadWalletException("Error load Wallet Balances Available", exception, "Method: getAssetIssuerWalletBalancesAvailable", "Class: AssetIssuerWalletModuleManager");
        }
    }

    public List<RedeemPointIdentity> getActiveIdentities() {

        try {
            return redeemPointIdentityManager.getRedeemPointsFromCurrentDeviceUser();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
        }
        return null;
    }
}
