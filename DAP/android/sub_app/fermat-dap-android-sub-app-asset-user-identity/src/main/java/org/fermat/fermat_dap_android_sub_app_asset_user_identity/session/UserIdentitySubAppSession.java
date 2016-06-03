package org.fermat.fermat_dap_android_sub_app_asset_user_identity.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_identity.interfaces.AssetUserIdentityModuleManager;

/**
 * Created by Francisco Vasquez
 */
public class UserIdentitySubAppSession extends AbstractFermatSession<InstalledSubApp, AssetUserIdentityModuleManager, SubAppResourcesProviderManager> {

    public UserIdentitySubAppSession() {
    }

    //    /**
//     * SubApps type
//     */
//    SubApps subApps;
//
//    /**
//     * Active objects in wallet session
//     */
//    Map<String, Object> data;
//
//    /**
//     * Error manager
//     */
//    private ErrorManager errorManager;
//
//    /**
//     * Wallet Store Module
//     */
//    private IdentityAssetUserManager moduleManager;
//
//
//    /**
//     * Create a session for the Wallet Store SubApp
//     *
//     * @param errorManager  the error manager
//     * @param moduleManager the module of this SubApp
//     */
//    public UserIdentitySubAppSession(InstalledSubApp subApp, ErrorManager errorManager, IdentityAssetUserManager moduleManager) {
//        super(subApp.getAppPublicKey(),subApp,errorManager,moduleManager,null);
//        this.subApps = subApps;
//        data = new HashMap<String, Object>();
//        this.errorManager = errorManager;
//        this.moduleManager = moduleManager;
//    }
//
//
//    /**
//     * Store any data you need to hold between the fragments of the sub app
//     *
//     * @param key    key to reference the object
//     * @param object the object yo want to store
//     */
//    @Override
//    public void setData(String key, Object object) {
//        data.put(key, object);
//    }
//
//    /**
//     * Return the data referenced by the key
//     *
//     * @param key the key to access de data
//     * @return the data you want
//     */
//    @Override
//    public Object getData(String key) {
//        return data.get(key);
//    }
//
//    /**
//     * Return the Error Manager
//     *
//     * @return reference to the Error Manager
//     */
//    @Override
//    public ErrorManager getErrorManager() {
//        return errorManager;
//    }
//
//    /**
//     * Return the Wallet Store Module
//     *
//     * @return reference to the Wallet Store Module
//     */
//    public IdentityAssetUserManager getModuleManager() {
//        return moduleManager;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        UserIdentitySubAppSession that = (UserIdentitySubAppSession) o;
//
//        return subApps == that.subApps;
//
//    }
//
//    @Override
//    public int hashCode() {
//        return subApps.hashCode();
//    }
}
