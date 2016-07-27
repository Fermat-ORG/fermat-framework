package com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes;

import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.LayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.AddonNotFoundException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.PluginNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The abstract class <code>AbstractLayer</code>
 * contains all the basic functionality of a layer class.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractLayer {

    private final Map<AddonReference, AbstractAddonSubsystem> addons;
    private final Map<PluginReference, AbstractPluginSubsystem> plugins;

    private final LayerReference layerReference;
    private FermatContext fermatContext;

    public AbstractLayer(final Layers layerEnum) {

        this.layerReference = new LayerReference(layerEnum);

        this.addons = new ConcurrentHashMap<>();
        this.plugins = new ConcurrentHashMap<>();
    }

    public AbstractLayer(Layers layerEnum, FermatContext fermatContext) {
        this.layerReference = new LayerReference(layerEnum);
        this.fermatContext = fermatContext;
        this.addons = new ConcurrentHashMap<>();
        this.plugins = new ConcurrentHashMap<>();
    }

    public Collection<AbstractPluginSubsystem> getPlugins() {
        return plugins.values();
    }

    public abstract void start() throws CantStartLayerException;

    /**
     * Through the method <code>registerAddon</code> you can add new addons to the layer.
     * Here we'll corroborate too that the addon is not added twice.
     *
     * @param abstractAddonSubsystem subsystem of the addon.
     * @throws CantRegisterAddonException if something goes wrong.
     */
    protected final void registerAddon(final AbstractAddonSubsystem abstractAddonSubsystem) throws CantRegisterAddonException {

        AddonReference addonReference = abstractAddonSubsystem.getAddonReference();

        if (addonReference == null)
            throw new CantRegisterAddonException("addonReference=null", "The addon does not contain a addon reference to recognize it.");

        addonReference.setLayerReference(this.layerReference);

        try {

            if (addons.containsKey(addonReference)) {
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
     * Through the method <code>registerPlugin</code> you can add new plugins to the layer.
     * Here we'll corroborate too that the plugin is not added twice.
     *
     * @param abstractPluginSubsystem subsystem of the plugin).
     * @throws CantRegisterPluginException if something goes wrong.
     */
    protected final void registerPlugin(AbstractPluginSubsystem abstractPluginSubsystem) throws CantRegisterPluginException {

        PluginReference pluginReference = abstractPluginSubsystem.getPluginReference();

        if (pluginReference == null)
            throw new CantRegisterPluginException("pluginReference=null", "The plugin does not contain a plugin reference to recognize it.");

        pluginReference.setLayerReference(this.layerReference);

        try {

            if (plugins.containsKey(pluginReference)) {

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

            throw new AddonNotFoundException(new StringBuilder().append("addon: ").append(addonReference).toString(), "addon not found in the specified layer.");
        }
    }

    public final AbstractPluginSubsystem getPlugin(PluginReference pluginReference) throws PluginNotFoundException {

        if (plugins.containsKey(pluginReference)) {
            return plugins.get(pluginReference);
        } else {

            throw new PluginNotFoundException(new StringBuilder().append("plugin: ").append(pluginReference).toString(), "plugin not found in the specified layer.");
        }
    }

    public final void fillAddonVersions(final ConcurrentHashMap<AddonVersionReference, AbstractAddon> versions) {

        for (ConcurrentHashMap.Entry<AddonReference, AbstractAddonSubsystem> addon : addons.entrySet())
            addon.getValue().fillVersions(versions);
    }

    public final void fillPluginVersions(final ConcurrentHashMap<PluginVersionReference, AbstractPlugin> versions) {

        for (ConcurrentHashMap.Entry<PluginReference, AbstractPluginSubsystem> plugin : plugins.entrySet())
            plugin.getValue().fillVersions(versions);
    }

    public final void fillPluginVersionsMati(final List<PluginVersionReference> versions) {

        for (ConcurrentHashMap.Entry<PluginReference, AbstractPluginSubsystem> plugin : plugins.entrySet())
            plugin.getValue().fillVersionsMati(versions);
    }

    public final LayerReference getLayerReference() {
        return layerReference;
    }

    public FermatContext getFermatContext() {
        return fermatContext;
    }
}
