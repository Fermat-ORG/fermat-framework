package com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public abstract class AbstractFermatSession<A extends FermatApp,M extends ModuleManager,R extends ResourceProviderManager> implements FermatSession<A,M>{

    private String publicKey;
    private A fermatApp;
    private M moduleManager;
    private R resourceProviderManager;
    private ErrorManager errorManager;
    //private FermatBundle data;
    private HashMap<String,Object> data;

    /**
     * Map with subApps and public that wallet can connect
     */
    protected List<FermatApp> fermatAppsPosibleConnections;


    public AbstractFermatSession(String publicKey, A fermatApp, ErrorManager errorManager,M moduleManager,R resourceProviderManager) {
        this.publicKey = publicKey;
        this.fermatApp = fermatApp;
        this.moduleManager = moduleManager;
        this.errorManager = errorManager;
        this.resourceProviderManager = resourceProviderManager;
        this.data = new HashMap<>();
    }

    protected AbstractFermatSession() {
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

    public Object getData(String key,Object defaultParamenter) throws IllegalAccessException {
        if (data.containsKey(key)){
            return data.get(key);
        }else{
            return defaultParamenter;
        }
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

    public void setFermatAppsPosibleConnections(List<FermatApp> fermatAppsPosibleConnections) {
        this.fermatAppsPosibleConnections = fermatAppsPosibleConnections;
    }

    public List<FermatApp> getPosibleConnections(){
        return fermatAppsPosibleConnections;
    }


    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setFermatApp(A fermatApp) {
        this.fermatApp = fermatApp;
    }

    public void setModuleManager(M moduleManager) {
        this.moduleManager = moduleManager;
    }

    public void setResourceProviderManager(R resourceProviderManager) {
        this.resourceProviderManager = resourceProviderManager;
    }

    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    public void removeData(String string){
        data.remove(string);
    }

    public void reportError(){
//        errorManager.reportUnexpectedUIException();
    }


}
