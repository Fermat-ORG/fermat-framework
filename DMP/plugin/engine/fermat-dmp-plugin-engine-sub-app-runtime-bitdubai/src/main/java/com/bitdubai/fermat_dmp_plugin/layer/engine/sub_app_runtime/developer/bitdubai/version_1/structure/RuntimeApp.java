package com.bitdubai.fermat_dmp_plugin.layer.engine.sub_app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.App;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Apps;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;

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
