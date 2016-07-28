package com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes;

import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.DeveloperPluginInterface;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.LayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.AddonNotFoundException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.DeveloperNotFoundException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.LayerNotFoundException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.PluginNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform</code>
 * contains all the basic functionality of a Platform class.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractPlatform {

    private FermatContext fermatContext;

    private Map<LayerReference, AbstractLayer> layers;

    private PlatformReference platformReference;

    public AbstractPlatform(final PlatformReference platformReference) {

        this.layers = new ConcurrentHashMap<>();
        this.platformReference = platformReference;
    }

    public AbstractPlatform(final PlatformReference platformReference,
                            final FermatContext fermatContext) {

        this.layers = new ConcurrentHashMap<>();
        this.fermatContext = fermatContext;
        this.platformReference = platformReference;
    }

    /**
     * Through the method <code>registerLayer</code> you can add new layers to the platform.
     * Here we'll corroborate too that the layer is not added twice.
     *
     * @param abstractLayer layer instance.
     * @throws CantRegisterLayerException if something goes wrong.
     */
    protected final void registerLayer(final AbstractLayer abstractLayer) throws CantRegisterLayerException {

        LayerReference layerReference = abstractLayer.getLayerReference();

        if (layerReference == null)
            throw new CantRegisterLayerException("layerReference=null", "The layer does not contain a layer reference to recognize it.");

        layerReference.setPlatformReference(platformReference);

        try {

            if (layers.containsKey(layerReference))
                throw new CantRegisterLayerException(new StringBuilder().append("layer: ").append(layerReference.toString()).toString(), "Layer already exists in this platform.");

            abstractLayer.start();

            layers.put(
                    layerReference,
                    abstractLayer
            );

        } catch (final CantStartLayerException e) {

            throw new CantRegisterLayerException(e, new StringBuilder().append("layer: ").append(layerReference.toString()).toString(), "Error trying to start the layer.");
        }
    }

    public final AbstractLayer getLayer(final LayerReference layerReference) throws LayerNotFoundException {
        if (layers.containsKey(layerReference))
            return layers.get(layerReference);
        else
            throw new LayerNotFoundException(new StringBuilder().append("layer: ").append(layerReference.getLayer()).toString(), "layer not found.");
    }

    /**
     * Through the method <code>getAddonVersion</code> you can get a addon version instance passing like parameter a version reference instance.
     *
     * @param addonVersionReference addon version reference data.
     * @return a addon version instance.
     * @throws VersionNotFoundException if we can't find a addon version with the given version reference parameters.
     */
    public final AbstractAddon getAddonVersion(final AddonVersionReference addonVersionReference) throws VersionNotFoundException {

        try {

            return getAddonDeveloper(addonVersionReference.getAddonDeveloperReference()).getAddonByVersion(addonVersionReference);

        } catch (DeveloperNotFoundException e) {

            throw new VersionNotFoundException(e, addonVersionReference.toString(), "addon version not found in the platform of the system context.");
        }
    }

    /**
     * Through the method <code>getAddonDeveloper</code> you can get a addonDeveloper instance passing like parameter a developer reference instance.
     *
     * @param addonDeveloperReference addon developer reference data.
     * @return a addon developer instance.
     * @throws DeveloperNotFoundException if we can't find a addon developer with the given developer reference parameters.
     */
    public final AbstractAddonDeveloper getAddonDeveloper(final AddonDeveloperReference addonDeveloperReference) throws DeveloperNotFoundException {

        try {

            return getAddonSubsystem(addonDeveloperReference.getAddonReference()).getDeveloperByReference(addonDeveloperReference);

        } catch (AddonNotFoundException e) {

            throw new DeveloperNotFoundException(e, addonDeveloperReference.toString(), "addon developer not found in the platform of the system context.");
        }
    }

    public final AbstractAddonSubsystem getAddonSubsystem(final AddonReference addonReference) throws AddonNotFoundException {

        try {

            return getLayer(addonReference.getLayerReference()).getAddon(addonReference);

        } catch (LayerNotFoundException e) {

            throw new AddonNotFoundException(e, new StringBuilder().append("addon: ").append(addonReference.toString()).toString(), "layer not found for the specified addon.");
        }
    }

    /**
     * Through the method <code>getPluginVersion</code> you can get a plugin version instance passing like parameter a version reference instance.
     *
     * @param pluginVersionReference plugin version reference data.
     * @return a plugin version instance.
     * @throws VersionNotFoundException if we can't find a plugin version with the given version reference parameters.
     */
    public final AbstractPlugin getPluginVersion(final PluginVersionReference pluginVersionReference) throws VersionNotFoundException {
        AbstractPlugin abstractPlugin = null;
        try {
            if (isPluginLoadedInTheNewWay(pluginVersionReference)) {
                abstractPlugin = (AbstractPlugin) getPluginDeveloper(pluginVersionReference.getPluginDeveloperReference()).
                        getPluginByVersionMati(
                                pluginVersionReference.getPlatform().getCode(),
                                pluginVersionReference.getLayers().getCode(),
                                pluginVersionReference.getPlugins().getCode(),
                                pluginVersionReference.getDeveloper().getCode(),
                                pluginVersionReference.getVersion().toString()
                        );
            } else
                abstractPlugin = getPluginDeveloper(pluginVersionReference.getPluginDeveloperReference()).getPluginByVersion(pluginVersionReference);

        } catch (DeveloperNotFoundException e) {

            throw new VersionNotFoundException(e, pluginVersionReference.toString(), "plugin version not found in the platform of the system context.");
        }
        return abstractPlugin;
    }

    private boolean isPluginLoadedInTheNewWay(PluginVersionReference pluginVersionReference) {
        PluginVersionReference pluginVersionReference2 = new PluginVersionReference(
                Platforms.BLOCKCHAINS,
                Layers.CRYPTO_NETWORK,
                Plugins.FERMAT_NETWORK,
                Developers.BITDUBAI,
                new Version());

        return pluginVersionReference.equals(pluginVersionReference2);
    }

    /**
     * Through the method <code>getPluginDeveloper</code> you can get a pluginDeveloper instance passing like parameter a developer reference instance.
     *
     * @param pluginDeveloperReference plugin developer reference data.
     * @return a plugin developer instance.
     * @throws DeveloperNotFoundException if we can't find a plugin developer with the given developer reference parameters.
     */
    public final DeveloperPluginInterface getPluginDeveloper(final PluginDeveloperReference pluginDeveloperReference) throws DeveloperNotFoundException {

        try {

            return getPluginSubsystem(pluginDeveloperReference.getPluginReference()).getDeveloperByReference(pluginDeveloperReference);

        } catch (PluginNotFoundException e) {

            throw new DeveloperNotFoundException(e, pluginDeveloperReference.toString(), "plugin developer not found in the platform of the system context.");
        }
    }

    public final AbstractPluginSubsystem getPluginSubsystem(final PluginReference pluginReference) throws PluginNotFoundException {

        try {

            return getLayer(pluginReference.getLayerReference()).getPlugin(pluginReference);

        } catch (LayerNotFoundException e) {

            throw new PluginNotFoundException(e, new StringBuilder().append("plugin: ").append(pluginReference.toString()).toString(), "layer not found for the specified plugin.");
        }
    }

    public final void fillAddonVersions(final ConcurrentHashMap<AddonVersionReference, AbstractAddon> versions) {

        for (ConcurrentHashMap.Entry<LayerReference, AbstractLayer> layer : layers.entrySet())
            layer.getValue().fillAddonVersions(versions);
    }

    public final void fillPluginVersions(final ConcurrentHashMap<PluginVersionReference, AbstractPlugin> versions) {

        for (ConcurrentHashMap.Entry<LayerReference, AbstractLayer> layer : layers.entrySet())
            layer.getValue().fillPluginVersions(versions);
    }

    public final void fillPluginVersionsMati(final List<PluginVersionReference> versions) {

        for (ConcurrentHashMap.Entry<LayerReference, AbstractLayer> layer : layers.entrySet())
            layer.getValue().fillPluginVersionsMati(versions);
    }

    public final PlatformReference getPlatformReference() {
        return platformReference;
    }

    public Collection<AbstractLayer> getLayers() {
        return layers.values();
    }

    public abstract void start() throws CantStartPlatformException;

    public ArrayList<AbstractPlugin> getPlugins() {
        ArrayList<AbstractPlugin> abstractPlugins = new ArrayList<>();
        Iterator<AbstractLayer> iterator = getLayers().iterator();
        while (iterator.hasNext()) {
            Iterator<AbstractPluginSubsystem> abstractPluginSubsystemIterator = iterator.next().getPlugins().iterator();
            while (abstractPluginSubsystemIterator.hasNext()) {
                Iterator<DeveloperPluginInterface> iterator1 = abstractPluginSubsystemIterator.next().getDevelopers().iterator();
                while (iterator1.hasNext()) {
                    Iterator<AbstractPlugin> iterator2 = iterator1.next().getVersions().iterator();
                    while (iterator2.hasNext()) {
                        abstractPlugins.add(iterator2.next());
                    }
                }
            }
        }
        return abstractPlugins;
    }

    public FermatContext getFermatContext() {
        return fermatContext;
    }
}
