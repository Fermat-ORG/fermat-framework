package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer._1_definition.enums.Addons;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;

/**
 * Created by ciencias on 2/12/15.
 */
public interface PlatformContext {

    public void addAddon (Addon addon, Addons descriptor);


    public Addon getAddon (Addons descriptor);


    public void addPlugin (Plugin plugin, Plugins descriptor);


    public Plugin getPlugin (Plugins descriptor);

    
}
