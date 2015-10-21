package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer</code>
 * contains all the basic functionality of a layer class.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractLayer {

    private Map<FermatPluginsEnum, Plugin> plugins;

    public AbstractLayer() {

        this.plugins = new ConcurrentHashMap<>();
    }

    public abstract void start() throws CantStartLayerException;

    /**
     * Throw the method <code>addPlugin</code> you can add new plugins to the layer.
     * Here we'll corroborate too that the plugin is not added twice.
     *
     * @param plugin             plugin descriptor (element of enum).
     * @param abstractSubsystem  subsystem of the plugin).
     *
     * @throws CantStartLayerException if something goes wrong.
     */
    protected final void addPlugin(final FermatPluginsEnum plugin           ,
                                   final AbstractSubsystem abstractSubsystem) throws CantStartLayerException {

        try {

            if(plugins.get(plugin) != null) {
                String context =
                        "plugin: "      + plugin.toString() +
                                " - layer: "    + plugin.getLayer() +
                                " - platform: " + plugin.getPlatform();
                throw new CantStartLayerException(context, "Plugin already exists in this layer.");
            }

            abstractSubsystem.start();

            plugins.put(
                    plugin,
                    abstractSubsystem.getPlugin()
            );

        } catch (final CantStartSubsystemException e) {

            String context =
                    "plugin: "      + plugin.toString() +
                    " - layer: "    + plugin.getLayer() +
                    " - platform: " + plugin.getPlatform();
            throw new CantStartLayerException(e, context, "Error trying to start the layer.");
        }
    }

    public final Plugin getPlugin(FermatPluginsEnum plugin) {
        return plugins.get(plugin);
    }

}
