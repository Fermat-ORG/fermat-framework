package org.fermat.fermat_dap_android_sub_app_redeem_point_community.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nerio on 21/10/15.
 */
public class AssetRedeemPointCommunitySubAppSession extends AbstractFermatSession<InstalledSubApp, RedeemPointCommunitySubAppModuleManager, SubAppResourcesProviderManager> {

    public static final String BASIC_DATA = "catalog_item_redeem";
    public static final String PREVIEW_IMGS = "preview_images_redeem";
    public static final String DEVELOPER_NAME = "developer_name_redeem";

    private Map<String, Object> data;

    public AssetRedeemPointCommunitySubAppSession() {
        data = new HashMap<String, Object>();
    }

    //    private RedeemPointCommunitySubAppModuleManager manager;
//
//    private ErrorManager errorManager;
//    private SubApps sessionType;
//    private Map<String, Object> data;
//
    public AssetRedeemPointCommunitySubAppSession(String publicKey, InstalledSubApp installedSubApp, ErrorManager errorManager, RedeemPointCommunitySubAppModuleManager manager, SubAppResourcesProviderManager subAppResourcesProviderManager) {
        super(publicKey, installedSubApp, errorManager, manager, subAppResourcesProviderManager);
        data = new HashMap<String, Object>();
//        this.errorManager = errorManager;
//        this.manager = manager;
    }
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
//    public RedeemPointCommunitySubAppModuleManager getManager() {
//        return manager;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetRedeemPointCommunitySubAppSession that = (AssetRedeemPointCommunitySubAppSession) o;

        return getFermatApp() == that.getFermatApp();
    }

    @Override
    public int hashCode() {
        return getFermatApp().hashCode();
    }
}
