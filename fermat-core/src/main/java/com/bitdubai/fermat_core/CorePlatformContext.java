package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.PlatformContext;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by ciencias on 2/12/15.
 */
public class CorePlatformContext implements PlatformContext {

    Map addons = new HashMap();
    Map plugins = new HashMap();
    
    public void addAddon (Addon addon, Addons descriptor) {

        addons.put(descriptor.getKey(),addon);

    }
    
    
    public Addon getAddon (Addons descriptor){

        return (Addon) addons.get(descriptor.getKey());

    }


    public void addPlugin (Plugin plugin, Plugins descriptor) {

        plugins.put(descriptor.getKey(),plugin);

    }


    public Plugin getPlugin (Plugins descriptor){

        return (Plugin) plugins.get(descriptor.getKey());
    }


}
