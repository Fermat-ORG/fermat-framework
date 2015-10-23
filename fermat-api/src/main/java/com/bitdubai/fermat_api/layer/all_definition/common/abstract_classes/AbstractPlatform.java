package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.AddonNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPluginIdsManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.LayerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.PluginNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.LayerReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlatform</code>
 * contains all the basic functionality of a Platform class.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractPlatform {

    private Map<LayerReference, AbstractLayer> layers;

    private final PlatformReference platformReference;

    public AbstractPlatform(final Platforms platformEnum) {

        this.layers   = new ConcurrentHashMap<>();
        this.platformReference = new PlatformReference(platformEnum);
    }

    /**
     * Throw the method <code>registerLayer</code> you can add new layers to the platform.
     * Here we'll corroborate too that the layer is not added twice.
     *
     * @param abstractLayer  layer instance.
     *
     * @throws CantRegisterLayerException if something goes wrong.
     */
    protected final void registerLayer(final AbstractLayer abstractLayer) throws CantRegisterLayerException {

        LayerReference layerReference = abstractLayer.getLayerReference();
        layerReference.setPlatformReference(platformReference);

        try {

            if(layers.containsKey(layerReference))
                throw new CantRegisterLayerException("layer: " + layerReference.toString(), "Layer already exists in this platform.");

            abstractLayer.start();

            layers.put(
                    layerReference,
                    abstractLayer
            );

        } catch (final CantStartLayerException e) {

            throw new CantRegisterLayerException(e, "layer: " + layerReference.toString(), "Error trying to start the layer.");
        }
    }

    public final AbstractLayer getLayer(final LayerReference layerReference) throws LayerNotFoundException {
        if (layers.containsKey(layerReference))
            return layers.get(layerReference);
        else
            throw new LayerNotFoundException("layer: "+layerReference.getLayer(), "layer not found.");
    }

    public final AbstractAddon getAddon(final AddonReference addonReference) throws AddonNotFoundException {

        try {

            return getLayer(addonReference.getLayerReference()).getAddon(addonReference);

        } catch (LayerNotFoundException e) {

            throw new AddonNotFoundException(e, "addon:"+addonReference.toString(), "layer not found for the specified addon.");
        }
    }

    public final AbstractPlugin getPlugin(final PluginReference pluginReference) throws PluginNotFoundException {

        try {

            return getLayer(pluginReference.getLayerReference()).getPlugin(pluginReference);

        } catch (LayerNotFoundException e) {

            throw new PluginNotFoundException(e, "plugin: "+pluginReference.toString(), "layer not found for the specified plugin.");
        }
    }

    public final PlatformReference getPlatformReference() {
        return platformReference;
    }

    public abstract void start() throws CantStartPlatformException;

    public abstract AbstractPluginIdsManager getPluginIdsManager(final PlatformFileSystem platformFileSystem) throws CantStartPluginIdsManagerException;

}
