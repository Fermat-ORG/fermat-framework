package org.fermat.fermat_dap_android_wallet_asset_user.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.HashMap;
import java.util.Map;

/**
 * AssetIssuer SubApp Session
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class AssetUserSession extends AbstractFermatSession<InstalledWallet, AssetUserWalletSubAppModuleManager, WalletResourcesProviderManager> {

    public static final String BASIC_DATA = "catalog_item_wallet_user";
    public static final String PREVIEW_IMGS = "preview_images_wallet_user";
    public static final String DEVELOPER_NAME = "developer_name_wallet_user";

//    private WalletResourcesProviderManager resourceManager;
//    private AssetUserWalletSubAppModuleManager walletManager;
//    private InstalledWallet installedWallet;
//    private ErrorManager errorManager;
    private Map<String, Object> data;

//    private WalletSettings settings;

    /**
     * Constructor
     *
     * @param resourceManager Wallet Resource manager
     * @param errorManager    Error Manager
     * @param manager         AssetIssuerWallet Manager
     */
    public AssetUserSession(String publicKey, InstalledWallet installedWallet, ErrorManager errorManager, AssetUserWalletSubAppModuleManager manager, WalletResourcesProviderManager resourceProviderManager) {
        super(publicKey, installedWallet, errorManager, manager, resourceProviderManager);
//        this.installedWallet = installedWallet;
        data = new HashMap<String, Object>();
//        this.errorManager = errorManager;
//        this.walletManager = manager;
//        this.resourceManager = resourceManager;
    }

    public AssetUserSession() {
        data = new HashMap<String, Object>();
//        installedWallet = null;
    }

//    public InstalledWallet getWalletSessionType() {
//        return installedWallet;
//    }

    @Override
    public void setData(String key, Object object) {
        data.put(key, object);
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }

//    @Override
//    public ErrorManager getErrorManager() {
//        return errorManager;
//    }


//    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
//        return resourceManager;
//    }

//    public AssetUserWalletSubAppModuleManager getWalletManager() {
//        return walletManager;
//    }

//    public void setSettings(WalletSettings settings) {
//        this.settings = settings;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetUserSession that = (AssetUserSession) o;

        return getFermatApp() == that.getFermatApp();
    }

    @Override
    public int hashCode() {
        return getFermatApp().hashCode();
    }
}
