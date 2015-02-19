package com.bitdubai.wallet_platform_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.App;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Apps;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.SubApp;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.SubApps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeApp implements App {

    Apps type;
    Map<SubApps, SubApp> subApps = new HashMap<SubApps, SubApp>() ;

    /**
     * RuntimeApp interface implementation.
     */

    public void setType(Apps type) {
        this.type = type;
    }
    
    public void addSubApp (SubApp subApp){
        subApps.put(subApp.getType(), subApp);
     }


    /**
     * App interface implementation.
     */

    @Override
    public Apps getType() {
        return type;
    }

    @Override
    public Map<SubApps, SubApp> getSubApps(){
        return subApps;
    }
            
    

    
    
}
