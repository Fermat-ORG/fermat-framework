package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * It has the main functionality of a Fermat Add-On.
 * <p/>
 * Created by ciencias on 2/6/15.
 * Updated by lnacosta (laion.cj91@gmail.com) on 23/11/2015.
 */
public interface Addon {

    /**
     * Through this method we can get an instance of the manager of that add-on.
     * This way, we don't give other plug-ins the complete management of a fermat add-on like service methods or many other things.
     *
     * @return an instance of FermatManager.
     */
    FermatManager getManager();

}
