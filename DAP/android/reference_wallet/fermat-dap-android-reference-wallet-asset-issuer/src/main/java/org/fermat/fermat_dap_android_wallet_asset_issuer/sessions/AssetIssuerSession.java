package org.fermat.fermat_dap_android_wallet_asset_issuer.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.HashMap;
import java.util.Map;

/**
 * AssetIssuer SubApp Session
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class AssetIssuerSession extends AbstractFermatSession<InstalledWallet,AssetIssuerWalletSupAppModuleManager,WalletResourcesProviderManager> {

    public static final String BASIC_DATA = "catalog_item_wallet_issuer";
    public static final String PREVIEW_IMGS = "preview_images_wallet_issuer";
    public static final String DEVELOPER_NAME = "developer_name_wallet_issuer";

    private final InstalledWallet installedWallet;
    /**
     * Issuer Manager
     */
    private AssetIssuerWalletSupAppModuleManager manager;

    /**
     * Active objects in wallet session
     */
    private Map<String, Object> data;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

    /**
     *  Wallet Settings
     */
    private WalletSettings settings;

    /**
     * Constructor
     *
     * @param
     * @param errorManager Error Manager
     * @param manager      AssetIssuerWallet Manager
     */
    public AssetIssuerSession(InstalledWallet installedWallet, ErrorManager errorManager, AssetIssuerWalletSupAppModuleManager manager) {
        super(installedWallet.getWalletPublicKey(), installedWallet, errorManager, manager, null);
        this.installedWallet = installedWallet;
        data = new HashMap<String, Object>();
        this.errorManager = errorManager;
        this.manager = manager;
    }

    public AssetIssuerSession() {
        data = new HashMap<String, Object>();
        installedWallet = null;
    }

    public InstalledWallet getWalletSessionType() {
        return installedWallet;
    }

    @Override
    public void setData(String key, Object object) {
        data.put(key, object);
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }

    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

//    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
//        return null;
//    }

    /**
     * Get Asset Issuer Wallet Manager instance
     *
     * @return AssetIssuerWalletManager object
     */
    public AssetIssuerWalletSupAppModuleManager getManager() {
        return manager;
    }

    public void setSettings(WalletSettings settings) {
        this.settings = settings;
    }

}
