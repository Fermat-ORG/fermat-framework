package com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public abstract class AbstractFermatSession<A extends FermatApp,M extends ModuleManager,R extends ResourceProviderManager> implements FermatSession{

    private String publicKey;
    private A fermatApp;
    private M moduleManager;
    private R resourceProviderManager;
    private ErrorManager errorManager;
    private Map<String,Object> data;


    public AbstractFermatSession(String publicKey, A fermatApp, ErrorManager errorManager,M moduleManager,R resourceProviderManager) {
        this.publicKey = publicKey;
        this.fermatApp = fermatApp;
        this.moduleManager = moduleManager;
        this.errorManager = errorManager;
        this.resourceProviderManager = resourceProviderManager;
        this.data = new HashMap<>();
    }

    /**
     *
     * @param key
     * @param object
     */
    public void setData(String key, Object object){
        data.put(key,object);
    }

    /**
     *
     * @param key
     * @return
     */
    public Object getData(String key){
        return data.get(key);
    }

    /**
     *
     * @return
     */
    public ErrorManager getErrorManager(){
        return errorManager;
    }


    public String getAppPublicKey(){
        return publicKey;
    }

    public A getFermatApp() {
        return fermatApp;
    }

    public M getModuleManager() {
        return moduleManager;
    }

    public R getResourceProviderManager() {
        return resourceProviderManager;
    }
}
