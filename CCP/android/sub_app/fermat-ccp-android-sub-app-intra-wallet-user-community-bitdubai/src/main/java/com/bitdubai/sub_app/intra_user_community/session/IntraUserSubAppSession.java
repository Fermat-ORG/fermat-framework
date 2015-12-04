package com.bitdubai.sub_app.intra_user_community.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class IntraUserSubAppSession extends AbstractFermatSession<InstalledSubApp,IntraUserModuleManager,SubAppResourcesProviderManager> implements SubAppsSession{
    public static final String BASIC_DATA = "catalog item";
    public static final String PREVIEW_IMGS = "preview images";
    public static final String DEVELOPER_NAME = "developer name";

    /**
     * SubApps type
     */
    SubApps subApps;

    /**
     * Active objects in wallet session
     */
    Map<String, Object> data;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

    /**
     * Wallet Store Module
     */
    private IntraUserModuleManager intraUserModuleManager;



    /**
     * Create a session for the Wallet Store SubApp
     *

     * @param errorManager             the error manager
     * @param intraUserModuleManager the module of this SubApp
     */
    public IntraUserSubAppSession(InstalledSubApp subApp, ErrorManager errorManager, IntraUserModuleManager intraUserModuleManager) {
        super(subApp.getAppPublicKey(),subApp,errorManager,intraUserModuleManager,null);
        this.subApps = subApps;
        data = new HashMap<String, Object>();
        this.errorManager = errorManager;
        this.intraUserModuleManager = intraUserModuleManager;
    }


    /**
     * Return the SubApp type
     *
     * @return SubApps instance indicating the type
     */
    @Override
    public InstalledSubApp getSubAppSessionType() {
        return getFermatApp();
    }

    /**
     * Store any data you need to hold between the fragments of the sub app
     *
     * @param key    key to reference the object
     * @param object the object yo want to store
     */
    @Override
    public void setData(String key, Object object) {
        data.put(key, object);
    }

    /**
     * Return the data referenced by the key
     *
     * @param key the key to access de data
     * @return the data you want
     */
    @Override
    public Object getData(String key) {
        return data.get(key);
    }

    /**
     * Return the Error Manager
     *
     * @return reference to the Error Manager
     */
    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }


    /**
     * Return the Wallet Store Module
     *
     * @return reference to the Wallet Store Module
     */
    public IntraUserModuleManager getIntraUserModuleManager() {
        return intraUserModuleManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntraUserSubAppSession that = (IntraUserSubAppSession) o;

        return subApps == that.subApps;

    }

    @Override
    public int hashCode() {
        return subApps.hashCode();
    }
}
