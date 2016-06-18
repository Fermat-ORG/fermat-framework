package org.fermat.fermat_dap_android_wallet_redeem_point.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Redeem Point Wallet Session
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class RedeemPointSessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledWallet, AssetRedeemPointWalletSubAppModule, WalletResourcesProviderManager> {

    public static final String BASIC_DATA = "catalog_item_wallet_redeem";
    public static final String PREVIEW_IMGS = "preview_images_wallet_redeem";
    public static final String DEVELOPER_NAME = "developer_name_wallet_redeem";

//    private final InstalledWallet installedWallet;
    /**
     * Issuer Manager
     */
//    private AssetRedeemPointWalletSubAppModule manager;

    /**
     * Active objects in wallet session
     */
    private Map<String, Object> data;

    /**
     * Error manager
     */
//    private ErrorManager errorManager;

//    private WalletSettings settings;
    public RedeemPointSessionReferenceApp(String publicKey, InstalledWallet installedWallet, ErrorManager errorManager, AssetRedeemPointWalletSubAppModule manager, WalletResourcesProviderManager resourceProviderManager) {
        super(publicKey, installedWallet, errorManager, manager, resourceProviderManager);
//        this.installedWallet = installedWallet;
        data = new HashMap<String, Object>();
//        this.errorManager = errorManager;
//        this.manager = manager;
    }

    public RedeemPointSessionReferenceApp() {
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
//        return null;
//    }

    /**
     * Get Asset Issuer Wallet Manager instance
     *
     * @return AssetIssuerWalletManager object
     */
//    public AssetRedeemPointWalletSubAppModule getManager() {
//        return manager;
//    }

//    public void setSettings(WalletSettings settings) {
//        this.settings = settings;
//    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RedeemPointSessionReferenceApp that = (RedeemPointSessionReferenceApp) o;

        return getFermatApp() == that.getFermatApp();
    }

    @Override
    public int hashCode() {
        return getFermatApp().hashCode();
    }
}
