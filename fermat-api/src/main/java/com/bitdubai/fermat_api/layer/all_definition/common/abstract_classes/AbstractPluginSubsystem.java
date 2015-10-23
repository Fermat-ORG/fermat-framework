package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPluginSubsystem</code>
 * contains all the basic functionality of a plugin Subsystem class.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractPluginSubsystem {

    private final PluginReference pluginReference;

    protected AbstractPlugin plugin;

    public AbstractPluginSubsystem(final FermatPluginsEnum pluginEnum) {

        this.pluginReference = new PluginReference(pluginEnum);
    }

    public AbstractPluginSubsystem(final PluginReference pluginReference) {

        this.pluginReference = pluginReference;
    }

    public final AbstractPlugin getPlugin() {
        return plugin;
    }

    public PluginReference getPluginReference() {
        return pluginReference;
    }

    public abstract void start() throws CantStartSubsystemException;

}
