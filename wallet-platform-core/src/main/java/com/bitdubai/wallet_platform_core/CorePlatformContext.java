package com.bitdubai.wallet_platform_core;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.PlatformContext;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Addons;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Plugins;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        plugins.put(descriptor.getIndex(),plugin);

    }


    public Plugin getPlugin (Plugins descriptor){

        return (Plugin) plugins.get(descriptor.getIndex());
    }


}
