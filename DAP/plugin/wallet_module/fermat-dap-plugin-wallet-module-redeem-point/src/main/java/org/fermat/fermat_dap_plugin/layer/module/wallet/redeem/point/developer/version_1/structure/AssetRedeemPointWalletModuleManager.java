package org.fermat.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantGetRedeemPointIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantGetRedeemPointStatisticsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.version_1.AssetRedeemPointWalletModulePluginRoot;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 16/10/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.WALLET_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.REDEEM_POINT)
public class AssetRedeemPointWalletModuleManager extends ModuleManagerImpl<RedeemPointSettings> implements AssetRedeemPointWalletSubAppModule, Serializable {

    private final AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    private final RedeemPointIdentityManager redeemPointIdentityManager;
    private final ErrorManager errorManager;
    private final EventManager eventManager;
    private final Broadcaster broadcaster;
    //    private final UUID                                      pluginId;
//    private final PluginFileSystem                          pluginFileSystem;
    private final AssetRedeemPointWalletModulePluginRoot assetRedeemPointWalletModulePluginRoot;

    //    private SettingsManager<RedeemPointSettings> settingsManager;
    RedeemPointSettings settings = null;
    private BlockchainNetworkType selectedNetwork;
    String publicKeyApp;

    public AssetRedeemPointWalletModuleManager(AssetRedeemPointWalletManager assetRedeemPointWalletManager,
                                               RedeemPointIdentityManager redeemPointIdentityManager,
                                               UUID pluginId,
                                               PluginFileSystem pluginFileSystem,
                                               ErrorManager errorManager,
                                               EventManager eventManager,
                                               Broadcaster broadcaster,
                                               AssetRedeemPointWalletModulePluginRoot assetRedeemPointWalletModulePluginRoot) {

        super(pluginFileSystem, pluginId);

        this.assetRedeemPointWalletManager = assetRedeemPointWalletManager;
        this.redeemPointIdentityManager = redeemPointIdentityManager;
//        this.pluginId                               = pluginId;
//        this.pluginFileSystem                       = pluginFileSystem;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.broadcaster = broadcaster;
        this.assetRedeemPointWalletModulePluginRoot = assetRedeemPointWalletModulePluginRoot;
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

    @Override
    public List<AssetRedeemPointWalletList> getAssetRedeemPointWalletBalances(String publicKey) throws CantLoadWalletException {
        // TODO MAKE USE OF THE ERROR MANAGER
        return this.getAssetRedeemPointWalletBalances(publicKey, selectedNetwork);
    }

    @Override
    public AssetRedeemPointWallet loadAssetRedeemPointWallet(String walletPublicKey) throws CantLoadWalletException {
        return assetRedeemPointWalletManager.loadAssetRedeemPointWallet(walletPublicKey, selectedNetwork);
    }

    @Override
    public List<RedeemPointStatistic> getStatisticsByAssetPublicKey(String walletPublicKey, String assetPublicKey) throws CantGetRedeemPointStatisticsException, RecordsNotFoundException, CantLoadWalletException {
        try {
            return assetRedeemPointWalletManager.loadAssetRedeemPointWallet(walletPublicKey, selectedNetwork).getStatisticsByAssetPublicKey(assetPublicKey);
        } catch (CantGetRedeemPointStatisticsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetRedeemPointStatisticsException(e);
        } catch (CantLoadWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadWalletException(e);
        }
    }

    @Override
    public List<RedeemPointStatistic> getAllStatisticsByWallet(String walletPublicKey) throws CantGetRedeemPointStatisticsException, RecordsNotFoundException, CantLoadWalletException {
        try {
            return assetRedeemPointWalletManager.loadAssetRedeemPointWallet(walletPublicKey, selectedNetwork).getAllStatistics();
        } catch (CantGetRedeemPointStatisticsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetRedeemPointStatisticsException(e);
        } catch (CantLoadWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadWalletException(e);
        }
    }

    @Override
    public void createWalletAssetRedeemPoint(String walletPublicKey) throws CantCreateWalletException {
        assetRedeemPointWalletManager.createWalletAssetRedeemPoint(walletPublicKey, selectedNetwork);
    }

    @Override
    public RedeemPointIdentity getActiveAssetRedeemPointIdentity() throws CantGetIdentityRedeemPointException {
        try {
            return redeemPointIdentityManager.getIdentityAssetRedeemPoint();
        } catch (CantGetRedeemPointIdentitiesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetIdentityRedeemPointException(e);
        }
    }

    @Override
    public void changeNetworkType(BlockchainNetworkType networkType) {
        if (networkType == null) {
            selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
        } else {
            selectedNetwork = networkType;
        }
    }

    @Override
    public BlockchainNetworkType getSelectedNetwork() {
//        if (selectedNetwork == null) {
//            try {
//                if (settings == null) {
//                    settingsManager = getSettingsManager();
//                }
//                settings = settingsManager.loadAndGetSettings(WalletsPublicKeys.DAP_REDEEM_WALLET.getCode());
//                selectedNetwork = settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition());
//            } catch (CantGetSettingsException exception) {
//                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//                exception.printStackTrace();
//            } catch (SettingsNotFoundException e) {
//                //TODO: Only enter while the Active Actor Wallet is not open.
//                selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
////                e.printStackTrace();
//            }
//        }
        return selectedNetwork;
    }

    @Override
    public List<AssetRedeemPointWalletTransaction> getTransactionsForDisplay(String walletPublicKey, String assetPublicKey) throws CantGetTransactionsException, CantLoadWalletException {
        try {
            return assetRedeemPointWalletManager.loadAssetRedeemPointWallet(walletPublicKey, selectedNetwork).getTransactionsForDisplay(assetPublicKey);
        } catch (CantLoadWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantLoadWalletException("Error load Wallet ", exception, "Method: getTransactionsForDisplay", "Class: AssetIssuerWalletModuleManager");
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetTransactionsException("Error getting transactions ", exception, "Method: getTransactionsForDisplay", "Class: AssetIssuerWalletModuleManager");
        }
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            List<RedeemPointIdentity> identities = this.getActiveIdentities();
            return (identities == null || identities.isEmpty()) ? null : identities.get(0);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        redeemPointIdentityManager.createNewRedeemPoint(name, profile_img,
                redeemPointIdentityManager.getAccuracyDataDefault(),
                redeemPointIdentityManager.getFrequencyDataDefault());
    }


    @Override
    public void setAppPublicKey(String publicKey) {
        this.publicKeyApp = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
