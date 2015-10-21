package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.AddonNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.PluginNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer</code>
 * contains all the basic functionality of a layer class.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractLayer {

    private Map<FermatAddonsEnum,  Addon > addons ;
    private Map<FermatPluginsEnum, Plugin> plugins;

    public AbstractLayer() {

        this.addons  = new ConcurrentHashMap<>();
        this.plugins = new ConcurrentHashMap<>();
    }

    public abstract void start() throws CantStartLayerException;

    /**
     * Throw the method <code>registerAddon</code> you can add new addons to the layer.
     * Here we'll corroborate too that the addon is not added twice.
     *
     * @param addon              addon descriptor (element of enum).
     * @param abstractSubsystem  subsystem of the addon.
     *
     * @throws CantStartLayerException if something goes wrong.
     */
    protected final void registerAddon(final FermatAddonsEnum       addon            ,
                                       final AbstractAddonSubsystem abstractSubsystem) throws CantStartLayerException {

        try {

            if(addons.get(addon) != null) {
                String context =
                        "addon: "      + addon.toString() +
                        " - layer: "    + addon.getLayer() +
                        " - platform: " + addon.getPlatform();
                throw new CantStartLayerException(context, "addon already exists in this layer.");
            }

            abstractSubsystem.start();

            addons.put(
                    addon,
                    abstractSubsystem.getAddon()
            );

        } catch (final CantStartSubsystemException e) {

            String context =
                    "addon: "      + addon.toString() +
                    " - layer: "    + addon.getLayer() +
                    " - platform: " + addon.getPlatform();
            throw new CantStartLayerException(e, context, "Error trying to start the layer.");
        }
    }

    /**
     * Throw the method <code>registerPlugin</code> you can add new plugins to the layer.
     * Here we'll corroborate too that the plugin is not added twice.
     *
     * @param plugin                   plugin descriptor (element of enum).
     * @param abstractPluginSubsystem  subsystem of the plugin).
     *
     * @throws CantStartLayerException if something goes wrong.
     */
    protected final void registerPlugin(final FermatPluginsEnum       plugin                 ,
                                        final AbstractPluginSubsystem abstractPluginSubsystem) throws CantStartLayerException {

        try {

            if(plugins.get(plugin) != null) {
                String context =
                        "plugin: "      + plugin.toString() +
                        " - layer: "    + plugin.getLayer() +
                        " - platform: " + plugin.getPlatform();
                throw new CantStartLayerException(context, "Plugin already exists in this layer.");
            }

            abstractPluginSubsystem.start();

            plugins.put(
                    plugin,
                    abstractPluginSubsystem.getPlugin()
            );

        } catch (final CantStartSubsystemException e) {

            String context =
                    "plugin: "      + plugin.toString() +
                    " - layer: "    + plugin.getLayer() +
                    " - platform: " + plugin.getPlatform();
            throw new CantStartLayerException(e, context, "Error trying to start the layer.");
        }
    }

    public final Addon getAddon(FermatAddonsEnum addon) throws AddonNotFoundException {

        if (addons.containsKey(addon)) {
            return addons.get(addon);
        } else {
            String context =
                    "addon: " + addon.toString() +
                    " - layer: " + addon.getLayer() +
                    " - platform: " + addon.getPlatform();
            throw new AddonNotFoundException(context, "addon not found in the specified layer.");
        }
    }

    public final Plugin getPlugin(FermatPluginsEnum plugin) throws PluginNotFoundException {

        if (plugins.containsKey(plugin)) {
            return plugins.get(plugin);
        } else {
            String context =
                    "plugin: " + plugin.toString() +
                    " - layer: " + plugin.getLayer() +
                    " - platform: " + plugin.getPlatform();
            throw new PluginNotFoundException(context, "plugin not found in the specified layer.");
        }
    }

}
