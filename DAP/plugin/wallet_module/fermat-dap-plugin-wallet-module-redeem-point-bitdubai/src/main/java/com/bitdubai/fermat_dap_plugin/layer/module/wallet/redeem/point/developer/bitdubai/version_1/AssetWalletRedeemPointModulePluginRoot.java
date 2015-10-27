package com.bitdubai.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.DealsWithAssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.bitdubai.version_1.structure.AssetRedeemPointWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Franklin on 07/09/15.
 */
public class AssetWalletRedeemPointModulePluginRoot implements Plugin, DealsWithAssetRedeemPointWallet, Service, DealsWithLogger, LogManagerForDevelopers, DealsWithErrors, AssetRedeemPointWalletSubAppModule {

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
    AssetRedeemPointWalletManager assetRedeemPointWalletManager;
    AssetRedeemPointWalletModuleManager assetRedeemPointWalletModuleManager;
    UUID pluginId;
    /**
     * DealsWithErros interface member variable
     */
    ErrorManager errorManager;
    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
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
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
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
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.bitdubai.version_1.AssetWalletRedeemPointModulePluginRoot");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (AssetWalletRedeemPointModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetWalletRedeemPointModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetWalletRedeemPointModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetWalletRedeemPointModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    @Override
    public void setAssetReddemPointManager(AssetRedeemPointWalletManager assetRedeemPointWalletManager) {
        this.assetRedeemPointWalletManager = assetRedeemPointWalletManager;
    }
}
