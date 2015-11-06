package com.bitdubai.sub_app.intra_user_identity.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class IntraUserIdentitySubAppSession implements SubAppsSession {

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
    private IntraWalletUserIdentityManager moduleManager;


    /**
     * Create a session for the Wallet Store SubApp
     * @param subApps                  the SubApp type
     * @param errorManager             the error manager
     * @param moduleManager the module of this SubApp
     */
    public IntraUserIdentitySubAppSession(SubApps subApps, ErrorManager errorManager, IntraWalletUserIdentityManager moduleManager) {
        this.subApps = subApps;
        data = new HashMap<String, Object>();
        this.errorManager = errorManager;
        this.moduleManager = moduleManager;
    }

    /**
     * Create a session for the Wallet Store SubApp
     *
     * @param subApps the SubApp type
     */
    public IntraUserIdentitySubAppSession(SubApps subApps) {
        this.subApps = subApps;
    }


    /**
     * Return the SubApp type
     *
     * @return SubApps instance indicating the type
     */
    @Override
    public SubApps getSubAppSessionType() {
        return subApps;
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
    public IntraWalletUserIdentityManager getModuleManager() {
        return moduleManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntraUserIdentitySubAppSession that = (IntraUserIdentitySubAppSession) o;

        return subApps == that.subApps;

    }

    @Override
    public int hashCode() {
        return subApps.hashCode();
    }
}
