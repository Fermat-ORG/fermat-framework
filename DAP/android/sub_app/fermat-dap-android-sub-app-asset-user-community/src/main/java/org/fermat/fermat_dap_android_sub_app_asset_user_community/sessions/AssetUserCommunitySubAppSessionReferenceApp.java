package org.fermat.fermat_dap_android_sub_app_asset_user_community.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by francisco on 14/10/15.
 */
public class AssetUserCommunitySubAppSessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledSubApp, AssetUserCommunitySubAppModuleManager, SubAppResourcesProviderManager> {

    public static final String BASIC_DATA = "catalog_item_user";
    public static final String PREVIEW_IMGS = "preview_images_user";
    public static final String DEVELOPER_NAME = "developer_name_user";

    private Map<String, Object> data;

    public AssetUserCommunitySubAppSessionReferenceApp() {
        data = new HashMap<String, Object>();
    }

    public AssetUserCommunitySubAppSessionReferenceApp(String publicKey, InstalledSubApp installedSubApp, ErrorManager errorManager, AssetUserCommunitySubAppModuleManager manager, SubAppResourcesProviderManager subAppResourcesProviderManager) {
        super(publicKey, installedSubApp, errorManager, manager, subAppResourcesProviderManager);
        data = new HashMap<String, Object>();
    }

//    private AssetUserCommunitySubAppModuleManager manager;

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

//    public AssetUserCommunitySubAppModuleManager getManager() {
//        return manager;
//    }

//    public CryptoWalletIntraUserIdentity getIntraUserModuleManager() throws CantListCryptoWalletIntraUserIdentityException, CantGetCryptoWalletException {
//        List<CryptoWalletIntraUserIdentity> lst =getModuleManager().getCryptoWallet().getAllIntraWalletUsersFromCurrentDeviceUser();
//        return (lst.isEmpty()) ? null : lst.get(0);
//    }
//
//    public String getCommunityConnection() {
//        //return searchConnectionPublicKey(SubApps.CCP_INTRA_USER_COMMUNITY.getCode());
//        return "public_key_intra_user_commmunity";
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetUserCommunitySubAppSessionReferenceApp that = (AssetUserCommunitySubAppSessionReferenceApp) o;

        return getFermatApp() == that.getFermatApp();
    }

    @Override
    public int hashCode() {
        return getFermatApp().hashCode();
    }
}
