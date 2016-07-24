package com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import java.util.HashMap;

/**
 * Created by mati on 2016.06.03..
 */
public class BaseFermatSession<A extends FermatApp, R extends ResourceProviderManager> {

    private String publicKey;
    private A fermatApp;
    private R resourceProviderManager;
    private ErrorManager errorManager;
    private HashMap<String, Object> data;


    public BaseFermatSession(String publicKey, A fermatApp, R resourceProviderManager, ErrorManager errorManager) {
        this.publicKey = publicKey;
        this.fermatApp = fermatApp;
        this.resourceProviderManager = resourceProviderManager;
        this.errorManager = errorManager;
        this.data = new HashMap<>();
    }

    public BaseFermatSession() {
        this.data = new HashMap<>();
    }

    /**
     * @param key
     * @param object
     */
    public void setData(String key, Object object) {
        data.put(key, object);
    }

    /**
     * @param key
     * @return
     */
    public Object getData(String key) {
        return data.get(key);
    }

    public Object getData(String key, Object defaultParamenter) throws IllegalAccessException {
        if (data.containsKey(key)) {
            return data.get(key);
        } else {
            return defaultParamenter;
        }
    }


    /**
     * @return
     */
    public ErrorManager getErrorManager() {
        return errorManager;
    }


    public String getAppPublicKey() {
        return publicKey;
    }

    public A getFermatApp() {
        return fermatApp;
    }


    public R getResourceProviderManager() {
        return resourceProviderManager;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setFermatApp(A fermatApp) {
        this.fermatApp = fermatApp;
    }

    public void setResourceProviderManager(R resourceProviderManager) {
        this.resourceProviderManager = resourceProviderManager;
    }

    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    public void removeData(String string) {
        data.remove(string);
    }

    public void reportError() {
//        errorManager.reportUnexpectedUIException();
    }
}
