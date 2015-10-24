package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.AddonNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterAddonException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.PluginNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.LayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer</code>
 * contains all the basic functionality of a layer class.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractLayer {

    private final Map<AddonReference , AbstractAddonSubsystem > addons ;
    private final Map<PluginReference, AbstractPluginSubsystem> plugins;

    private final LayerReference layerReference;

    public AbstractLayer(final Layers layerEnum) {

        this.layerReference = new LayerReference(layerEnum);

        this.addons  = new ConcurrentHashMap<>();
        this.plugins = new ConcurrentHashMap<>();
    }

    public abstract void start() throws CantStartLayerException;

    /**
     * Throw the method <code>registerAddon</code> you can add new addons to the layer.
     * Here we'll corroborate too that the addon is not added twice.
     *
     * @param abstractAddonSubsystem  subsystem of the addon.
     *
     * @throws CantRegisterAddonException if something goes wrong.
     */
    protected final void registerAddon(final AbstractAddonSubsystem abstractAddonSubsystem) throws CantRegisterAddonException {

        AddonReference addonReference = abstractAddonSubsystem.getAddonReference();

        addonReference.setLayerReference(this.layerReference);

        try {

            if(addons.containsKey(addonReference)) {
                throw new CantRegisterAddonException(addonReference.toString(), "addon already exists in this layer.");
            }

            abstractAddonSubsystem.start();

            addons.put(
                    addonReference,
                    abstractAddonSubsystem
            );

        } catch (final CantStartSubsystemException e) {

            throw new CantRegisterAddonException(e, addonReference.toString(), "Error trying to start the layer.");
        }
    }

    /**
     * Throw the method <code>registerPlugin</code> you can add new plugins to the layer.
     * Here we'll corroborate too that the plugin is not added twice.
     *
     * @param abstractPluginSubsystem  subsystem of the plugin).
     *
     * @throws CantRegisterPluginException if something goes wrong.
     */
    protected final void registerPlugin(AbstractPluginSubsystem abstractPluginSubsystem) throws CantRegisterPluginException {

        PluginReference pluginReference = abstractPluginSubsystem.getPluginReference();

        pluginReference.setLayerReference(this.layerReference);

        try {

            if(plugins.containsKey(pluginReference)) {

                throw new CantRegisterPluginException(pluginReference.toString(), "Plugin already exists in this layer.");
            }

            abstractPluginSubsystem.start();

            plugins.put(
                    pluginReference,
                    abstractPluginSubsystem
            );

        } catch (final CantStartSubsystemException e) {

            throw new CantRegisterPluginException(e, pluginReference.toString(), "Error trying to start the layer.");
        }
    }

    public final AbstractAddonSubsystem getAddon(AddonReference addonReference) throws AddonNotFoundException {

        if (addons.containsKey(addonReference)) {
            return addons.get(addonReference);
        } else {

            throw new AddonNotFoundException("addon: "+addonReference, "addon not found in the specified layer.");
        }
    }

    public final AbstractPluginSubsystem getPlugin(PluginReference pluginReference) throws PluginNotFoundException {

        if (plugins.containsKey(pluginReference)) {
            return plugins.get(pluginReference);
        } else {

            throw new PluginNotFoundException("plugin: "+pluginReference, "plugin not found in the specified layer.");
        }
    }

    public final LayerReference getLayerReference() {
        return layerReference;
    }

}
