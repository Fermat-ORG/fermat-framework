/*
 * @#CorePlatformContext.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.PlatformContext;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformLayers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_core.CorePlatformContext</code> implements
 * the core platform context and hold all references of the mains components
 * <p/>
 *
 * Created by ciencias on 2/12/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CorePlatformContext implements PlatformContext,Serializable {

    /**
     * Hold the referent of the existing addons
     */
    Map<Addons, Addon> addons;

    /**
     * Hold the referent of the existing plugins
     */
    Map<Plugins, Plugin> plugins;

    /**
     * Hold the referent of the existing platform layers
     */
    Map<PlatformLayers, PlatformLayer> platformLayers;

    /**
     * Constructor
     */
    public CorePlatformContext(){
        super();

        this.addons = new HashMap<>();
        this.plugins = new HashMap<>();
        this.platformLayers = new HashMap<>();
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#registerAddon(Addon, Addons)
     */
    public void registerAddon(Addon addon, Addons key) {
        addons.put(key,addon);
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#getAddon(Addons)
     */
    public Addon getAddon (Addons key){
        return addons.get(key);
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#registerPlugin(Plugin, Plugins)
     */
    public void registerPlugin(Plugin plugin, Plugins key) {
        plugins.put(key,plugin);
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#getPlugin(Plugins)
     */
    public Plugin getPlugin (Plugins key){
        return plugins.get(key);
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#registerPlatformLayer(PlatformLayer, PlatformLayers)
     */
    public void registerPlatformLayer(PlatformLayer platformLayer, PlatformLayers key) {
        platformLayers.put(key,platformLayer);
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#getPlatformLayer(PlatformLayers)
     */
    public PlatformLayer getPlatformLayer (PlatformLayers key){
        return (PlatformLayer) platformLayers.get(key);
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#getRegisteredAddonskeys()
     */
    public Collection<Addons> getRegisteredAddonskeys(){
        return addons.keySet();
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#getRegisteredPluginskeys()
     */
    public Collection<Plugins> getRegisteredPluginskeys(){
        return plugins.keySet();
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#getRegisteredPlatformLayerskeys()
     */
    public Collection<PlatformLayers> getRegisteredPlatformLayerskeys(){ return platformLayers.keySet();}


    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#isRegister(Addons)
     */
    public boolean isRegister(Addons key){
        return addons.containsKey(key);
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#isRegister(Plugins)
     */
    public boolean isRegister(Plugins key){
        return plugins.containsKey(key);
    }

    /**
     * (non-Javadoc)
     *
     * @see PlatformContext#isRegister(PlatformLayers)
     */
    public boolean isRegister(PlatformLayers key){
        return platformLayers.containsKey(key);
    }


}
