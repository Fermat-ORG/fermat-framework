package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPluginDeveloper</code>
 * haves all the main functionality of a plugin developer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public abstract class AbstractPluginDeveloper {

    private final AbstractPlugin plugin;

    public AbstractPluginDeveloper(final AbstractPlugin plugin) {

        this.plugin = plugin;
    }

    public final AbstractPlugin getPlugin() {
        return plugin;
    }

}
