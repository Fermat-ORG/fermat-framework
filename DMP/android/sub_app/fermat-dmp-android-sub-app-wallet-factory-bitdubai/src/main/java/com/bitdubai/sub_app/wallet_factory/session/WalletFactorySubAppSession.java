package com.bitdubai.sub_app.wallet_factory.session;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class WalletFactorySubAppSession implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession{


    /**
     * SubApps type
     */
    SubApps subApps;

    /**
     * Active objects in wallet session
     */
    Map<String,Object> data;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

    /**
     *  WalletFactoryProjectManager
     */
    private WalletFactoryProjectManager walletFactoryProjectManager;

    /**
     *  Projects opened
     */


    /**
     * Event manager.
     */
    // Ver si esto va acá
    //private EventManager eventManager;



    public WalletFactorySubAppSession(SubApps subApps, ErrorManager errorManager,WalletFactoryProjectManager walletFactoryProjectManager){
        this.subApps=subApps;
        data= new HashMap<String,Object>();
        this.errorManager=errorManager;
        this.walletFactoryProjectManager = walletFactoryProjectManager;
    }

    public WalletFactorySubAppSession(SubApps subApps) {
        this.subApps = subApps;
    }

    @Override
    public SubApps getSubAppSessionType() {
        return subApps;
    }

    @Override
    public void setData(String key, Object object) {
        data.put(key,object);
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }
    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }


    public WalletFactoryProjectManager getWalletFactoryProjectManager() {
        return walletFactoryProjectManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WalletFactorySubAppSession that = (WalletFactorySubAppSession) o;

        return subApps == that.subApps;

    }

    @Override
    public int hashCode() {
        return subApps.hashCode();
    }
}
