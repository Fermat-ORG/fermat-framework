/*
 * @#PlatformContext.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformLayers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import java.util.Collection;

/**
 * The interface <code>com.bitdubai.fermat_api.PlatformContext</code> represent
 * the core platform context <p/>
 *
 * Created by ciencias on 2/12/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface PlatformContext {

    /**
     * Register a new addon to the references
     *
     * @param addon
     * @param key
     */
    void registerAddon(Addon addon, Addons key);

    /**
     * Get a addon instance
     *
     * @param key
     * @return Addon
     */
    Addon getAddon (Addons key);

    /**
     * Register a new plugin to the reference
     *
     * @param plugin
     * @param key
     */
    void registerPlugin(Plugin plugin, Plugins key);

    /**
     * Get a plugin instance
     *
     * @param key
     * @return Plugin
     */
    Plugin getPlugin (Plugins key);

    /**
     * Register a new platform layer to the reference
     *
     * @param platformLayer
     * @param key
     */
    void registerPlatformLayer(PlatformLayer platformLayer, PlatformLayers key);

    /**
     * Get a platformLayer instance
     *
     * @param key
     * @return PlatformLayer
     */
    PlatformLayer getPlatformLayer (PlatformLayers key);

    /**
     * Get the list of keys of the addons register
     *
     * @return Collection<Addons>
     */
    Collection<Addons> getRegisteredAddonskeys();

    /**
     * Get the list of keys of the plugins register
     *
     * @return Collection<Plugins>
     */
    Collection<Plugins> getRegisteredPluginskeys();

    /**
     * Get the list of keys of the platform layers register
     *
     * @return Collection<PlatformLayers>
     */
    Collection<PlatformLayers> getRegisteredPlatformLayerskeys();

    /**
     * Validate is register
     *
     * @param  key Addons
     * @return boolean
     */
    boolean isRegister(Addons key);

    /**
     * Validate is register
     *
     * @param  key Plugins
     * @return boolean
     */
    boolean isRegister(Plugins key);

    /**
     * Validate is register
     *
     * @param  key platformLayer
     * @return boolean
     */
    boolean isRegister(PlatformLayers key);

    
}
