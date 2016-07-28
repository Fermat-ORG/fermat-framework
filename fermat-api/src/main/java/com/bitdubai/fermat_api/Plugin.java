package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import java.util.UUID;

/**
 * It has the main functionality of a Fermat Plug-In.
 * <p/>
 * Created by ciencias on 02.02.15.
 * Updated by lnacosta (laion.cj91@gmail.com) on 20/11/2015.
 */
public interface Plugin {

    /**
     * Through this method the core can assign the plug-in id that belongs to the plug-in.
     *
     * @param pluginId uuid representing the id of the plug-in.
     */
    void setId(UUID pluginId);

    /**
     * Through this method we can get an instance of the manager of that plug-in.
     * This way, we don't give other plug-ins the complete management of a fermat plug-in like service methods or many other things.
     *
     * @return an instance of FermatManager.
     */
    FermatManager getManager();

}
