package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;


/**
 * Created by Matias Furszyfer on 2/14/15.
 */
public interface SubAppRuntimeManager extends RuntimeManager {


    /**
     *  Get the last active SubApp
     *
     * @return Wallet in use
     */

    public SubApp getLastSubApp ();

    /**
     *  Search SubApp in the subApp installed list
     *
     * @param subApps type of SubApps
     * @return  The installed SubApps
     */

    //public SubApp getSubApp(SubApps subApps);

    /**
     *  Search HomeScreen SubApp
     *
     * @return SubApp HomeScreen
     */

    public SubApp getSubAppByPublicKey(String subAppPublicKey);



}
