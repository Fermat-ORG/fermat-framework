package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.AddonNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.DeveloperNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.LayerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.PlatformNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.PluginNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.LayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_core.FermatSystemContext</code>
 * the system context hold all the  references of the mains components of fermat.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/10/2015.
 */
public final class FermatSystemContext {

    private final Map<PlatformReference, AbstractPlatform> platforms;

    private final Object osContext;

    /**
     * Constructor without params, initializes the platforms Map with an empty concurrent hash map.
     * The platforms array contains all the references to the platforms.
     * The key is an element of the Platforms enum, and the value is the Platform in-self.
     */
    public FermatSystemContext(final Object osContext) {

        this.osContext = osContext;
        this.platforms = new ConcurrentHashMap<>();
    }

    /**
     * Through the method <code>registerLayer</code> you can add new layers to the platform.
     * Here we'll corroborate too that the layer is not added twice.
     *
     * @param abstractPlatform  platform instance.
     *
     * @throws CantRegisterPlatformException if something goes wrong.
     */
    public final void registerPlatform(AbstractPlatform abstractPlatform) throws CantRegisterPlatformException {

        PlatformReference platformReference = abstractPlatform.getPlatformReference();

        try {

            if(platforms.containsKey(platformReference))
                throw new CantRegisterPlatformException("platform: " + platformReference.toString(), "platform already exists in the system context.");

            abstractPlatform.start();

            platforms.put(
                    platformReference,
                    abstractPlatform
            );

        } catch (final CantStartPlatformException e) {

            throw new CantRegisterPlatformException(e, "platform: " + platformReference.toString(), "Error trying to start the platform.");
        }
    }

    /**
     * Through the method <code>getAddonVersion</code> you can get a addon version instance passing like parameter a version reference instance.
     *
     * @param addonVersionReference addon version reference data.
     *
     * @return a addon version instance.
     *
     * @throws VersionNotFoundException   if we can't find a addon version with the given version reference parameters.
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
     *
     * @return a addon developer instance.
     *
     * @throws DeveloperNotFoundException   if we can't find a addon developer with the given developer reference parameters.
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

            throw new AddonNotFoundException(e, "addon: "+addonReference.toString(), "layer not found for the specified addon.");
        }
    }

    /**
     * Through the method <code>getPluginVersion</code> you can get a plugin version instance passing like parameter a version reference instance.
     *
     * @param pluginVersionReference plugin version reference data.
     *
     * @return a plugin version instance.
     *
     * @throws VersionNotFoundException   if we can't find a plugin version with the given version reference parameters.
     */
    public final AbstractPlugin getPluginVersion(final PluginVersionReference pluginVersionReference) throws VersionNotFoundException {

        try {

            return getPluginDeveloper(pluginVersionReference.getPluginDeveloperReference()).getPluginByVersion(pluginVersionReference);

        } catch (DeveloperNotFoundException e) {

            throw new VersionNotFoundException(e, pluginVersionReference.toString(), "version not found in the platform of the system context.");
        }
    }

    /**
     * Through the method <code>getPluginDeveloper</code> you can get a pluginDeveloper instance passing like parameter a developer reference instance.
     *
     * @param pluginDeveloperReference plugin developer reference data.
     *
     * @return a plugin developer instance.
     *
     * @throws DeveloperNotFoundException   if we can't find a plugin developer with the given developer reference parameters.
     */
    public final AbstractPluginDeveloper getPluginDeveloper(final PluginDeveloperReference pluginDeveloperReference) throws DeveloperNotFoundException {

        try {

            return getPluginSubsystem(pluginDeveloperReference.getPluginReference()).getDeveloperByReference(pluginDeveloperReference);

        } catch (PluginNotFoundException e) {

            throw new DeveloperNotFoundException(e, pluginDeveloperReference.toString(), "plugin not found in the platform of the system context.");
        }
    }

    /**
     * Through the method <code>getPluginSubsystem</code> you can get a subsystem instance passing like parameter a plugin reference instance.
     *
     * @param pluginReference plugin reference data.
     *
     * @return a plugin subsystem instance.
     *
     * @throws PluginNotFoundException   if we can't find a plugin with the given plugin reference parameters.
     */
    public final AbstractPluginSubsystem getPluginSubsystem(final PluginReference pluginReference) throws PluginNotFoundException {

        try {

            return getLayer(pluginReference.getLayerReference()).getPlugin(pluginReference);

        } catch (LayerNotFoundException e) {

            throw new PluginNotFoundException(e, "plugin: " + pluginReference.toString(), "layer not found in the platform of the system context.");
        }
    }

    /**
     * Through the method <code>getLayer</code> you can get a Layer instance passing like parameter a layer reference instance.
     *
     * @param layerReference layer reference data.
     *
     * @return a layer instance.
     *
     * @throws LayerNotFoundException   if we can't find a layer with the given layer reference parameters.
     */
    public final AbstractLayer getLayer(final LayerReference layerReference) throws LayerNotFoundException {

        try {

            return getPlatform(layerReference.getPlatformReference()).getLayer(layerReference);

        } catch (final PlatformNotFoundException e) {

            throw new LayerNotFoundException(e, "layer: " + layerReference.toString(), "the platform of the layer was not founded in the system context.");
        }
    }

    /**
     * Through the method <code>getPlatform</code> you can get a Platform instance passing like parameter a platform reference instance.
     *
     * @param platformReference platform reference data.
     *
     * @return a platform instance.
     *
     * @throws PlatformNotFoundException   if we can't find a platform with the given platform reference parameters.
     */
    public final AbstractPlatform getPlatform(final PlatformReference platformReference) throws PlatformNotFoundException {

        if (platforms.containsKey(platformReference)) {
            return platforms.get(platformReference);
        } else {
            throw new PlatformNotFoundException("platform: " + platformReference.toString(), "platform not found in the system context.");
        }
    }

    public final ConcurrentHashMap<AddonVersionReference, AbstractAddon> listAddonVersions() {

        final ConcurrentHashMap<AddonVersionReference, AbstractAddon> versions = new ConcurrentHashMap<>();

        for(ConcurrentHashMap.Entry<PlatformReference, AbstractPlatform> platform : platforms.entrySet())
            versions.putAll(platform.getValue().listAddonVersions());

        return versions;
    }

    public final ConcurrentHashMap<PluginVersionReference, AbstractPlugin> listPluginVersions() {

        final ConcurrentHashMap<PluginVersionReference, AbstractPlugin> versions = new ConcurrentHashMap<>();

        for(ConcurrentHashMap.Entry<PlatformReference, AbstractPlatform> platform : platforms.entrySet())
            versions.putAll(platform.getValue().listPluginVersions());

        return versions;
    }

    public final Object getOsContext() {
        return osContext;
    }

}
