package com.bitdubai.wallet_platform_core;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.PlatformContext;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Addons;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Plugins;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 2/12/15.
 */
public class CorePlatformContext implements PlatformContext {

    List<Addon> addons = new ArrayList<>();
    List<Plugin> plugins = new ArrayList<>();
    
    public void addAddon (Addon addon, Addons descriptor) {
        
        addons.add(descriptor.getIndex(),addon);
        
    }
    
    
    public Addon getAddon (Addons descriptor){
        
        return addons.get(descriptor.getIndex());
    }


    public void addPlugin (Plugin plugin, Plugins descriptor) {

        plugins.add(descriptor.getIndex(),plugin);

    }


    public Plugin getPlugin (Plugins descriptor){

        return plugins.get(descriptor.getIndex());
    }


}
