package com.bitdubai.fermat_dap_plugin.layer.module.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantExecuteAppropriationTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.TransactionAlreadyStartedException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces.UserRedemptionManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.module.asset.user.developer.bitdubai.version_1.structure.AssetUserWalletModule;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.List;

/**
 * TODO ADD A LITTLE EXPLANATION ABOUT THE MAIN FUNCTIONALITY OF THE PLUG-IN
 *
 * Created by Franklin on 07/09/15.
 */
public class AssetUserWalletModulePluginRoot extends AbstractPlugin implements
        AssetUserWalletSubAppModuleManager {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM   , layer = Layers.WALLET, plugin = Plugins.ASSET_USER         )
    private AssetUserWalletManager assetUserWalletManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM   , layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_APPROPRIATION)
    private AssetAppropriationManager assetAppropriationManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM   , layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.USER_REDEMPTION)
    private UserRedemptionManager userRedemptionManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM   , layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE         , plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletMiddlewareManager;


    //TODO MAKE USE OF THE ERROR MANAGER

    private AssetUserWalletModule assetUserWalletModule;

    public AssetUserWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            assetUserWalletModule = new AssetUserWalletModule(assetUserWalletManager, assetAppropriationManager, userRedemptionManager);
            System.out.println("******* Asset User Wallet Module Init ******");
            this.serviceStatus = ServiceStatus.STARTED;
        }catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalancesAvailable(String publicKey) throws CantLoadWalletException {
        //TODO MAKE USE OF THE ERROR MANAGER

        return assetUserWalletModule.getAssetUserWalletBalancesAvailable(publicKey);
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalancesBook(String publicKey) throws CantLoadWalletException {
        //TODO MAKE USE OF THE ERROR MANAGER

        return assetUserWalletModule.getAssetUserWalletBalancesBook(publicKey);
    }

    @Override
    public void redeemAssetToRedeemPoint(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint) throws CantRedeemDigitalAssetException {
        try {
            ActorAssetRedeemPoint hardcodedActorAssetRedeemPoint = actorAssetRedeemPointManager.getAllRedeemPointActorConnected().get(0);
            assetUserWalletModule.redeemAssetToRedeemPoint(digitalAssetMetadata, hardcodedActorAssetRedeemPoint);
        } catch (CantGetAssetRedeemPointActorsException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void appropriateAsset(DigitalAsset digitalAsset, String bitcoinWalletPublicKey) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException {
        try {
            com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet installedWallet = walletMiddlewareManager.getInstalledWallets().get(0);
            assetUserWalletModule.appropriateAsset(digitalAsset, installedWallet.getWalletPublicKey());
        } catch (CantListWalletsException e) {
            e.printStackTrace();
        }

    }
}
