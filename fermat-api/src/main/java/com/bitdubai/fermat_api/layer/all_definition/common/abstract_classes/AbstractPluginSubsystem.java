package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPluginSubsystem</code>
 * contains all the basic functionality of a plugin Subsystem class.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractPluginSubsystem {

    private final FermatPluginsEnum pluginEnum;

    protected Plugin plugin;

    public AbstractPluginSubsystem(final FermatPluginsEnum pluginEnum) {

        this.pluginEnum = pluginEnum;
    }

    public final Plugin getPlugin() {
        return plugin;
    }

    public FermatPluginsEnum getPluginEnum() {
        return pluginEnum;
    }

    public abstract void start() throws CantStartSubsystemException;

}
