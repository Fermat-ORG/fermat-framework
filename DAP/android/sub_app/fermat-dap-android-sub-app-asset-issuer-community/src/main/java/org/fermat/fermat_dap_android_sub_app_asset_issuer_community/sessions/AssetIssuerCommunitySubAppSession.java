package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;

import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by francisco on 21/10/15.
 */
public class AssetIssuerCommunitySubAppSession extends AbstractFermatSession<InstalledSubApp,AssetIssuerCommunitySubAppModuleManager,SubAppResourcesProviderManager> {

    public static final String BASIC_DATA = "catalog_item_issuer";
    public static final String PREVIEW_IMGS = "preview_images_issuer";
    public static final String DEVELOPER_NAME = "developer_name_issuer";

//    private final InstalledWallet installedWallet;

    public AssetIssuerCommunitySubAppSession() {
        data = new HashMap<String, Object>();
//        installedWallet = null;
    }

    public AssetIssuerCommunitySubAppSession(String publicKey,
                                           InstalledSubApp fermatApp,
                                           ErrorManager errorManager,
                                             AssetIssuerCommunitySubAppModuleManager moduleManager,
                                           SubAppResourcesProviderManager resourceProviderManager) {

        super(publicKey, fermatApp, errorManager, moduleManager, resourceProviderManager);
    }
    private AssetIssuerCommunitySubAppModuleManager manager;


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

    public void setSettings(WalletSettings settings) {
        this.settings = settings;
    }

    public AssetIssuerCommunitySubAppModuleManager getManager() {
        return manager;
    }

    //    private AssetIssuerCommunitySubAppModuleManager manager;
//
//    private ErrorManager errorManager;
//    private SubApps subAppType;
//    private Map<String, Object> data;
//
//    public AssetIssuerCommunitySubAppSession(InstalledSubApp subApp, ErrorManager errorManager, AssetIssuerCommunitySubAppModuleManager manager) {
//        super(subApp.getAppPublicKey(),subApp,errorManager,manager,null);
//        this.errorManager = errorManager;
//        this.manager = manager;
//    }
//
//    @Override
//    public void setData(String key, Object object) {
//        if (data == null)
//            data = new HashMap<>();
//        data.put(key, object);
//    }
//
//    @Override
//    public Object getData(String key) {
//        return data.get(key);
//    }
//
//    @Override
//    public ErrorManager getErrorManager() {
//        return errorManager;
//    }
//

}
