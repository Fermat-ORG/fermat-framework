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

    private Map<Layers, AbstractLayer> layers;

    private final Platforms platformEnum;

    public AbstractPlatform(final Platforms platformEnum) {

        this.layers   = new ConcurrentHashMap<>();
        this.platformEnum = platformEnum;
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

        Layers layerEnum = abstractLayer.getLayerEnum();

        try {

            if(layers.containsKey(layerEnum))
                throw new CantRegisterLayerException("layer: " + layerEnum.toString(), "Layer already exists in this platform.");

            abstractLayer.start();

            layers.put(
                    layerEnum,
                    abstractLayer
            );

        } catch (final CantStartLayerException e) {

            throw new CantRegisterLayerException(e, "layer: " + layerEnum.toString(), "Error trying to start the layer.");
        }
    }

    public final AbstractLayer getLayer(final Layers layerEnum) throws LayerNotFoundException {
        if (layers.containsKey(layerEnum))
            return layers.get(layerEnum);
        else
            throw new LayerNotFoundException("layer: "+layerEnum, "layer not found.");
    }

    public final Addon getAddon(final FermatAddonsEnum addonEnum) throws AddonNotFoundException {

        try {

            return getLayer(addonEnum.getLayer()).getAddon(addonEnum);

        } catch (LayerNotFoundException e) {

            String context =
                    "addon: "      + addonEnum.toString() +
                    " - layer: "    + addonEnum.getLayer() +
                    " - platform: " + addonEnum.getPlatform();
            throw new AddonNotFoundException(e, context, "layer not found for the specified addon.");
        }
    }

    public final Plugin getPlugin(final FermatPluginsEnum pluginEnum) throws PluginNotFoundException {

        try {

            return getLayer(pluginEnum.getLayer()).getPlugin(pluginEnum);

        } catch (LayerNotFoundException e) {

            String context =
                    "plugin: "      + pluginEnum.toString() +
                    " - layer: "    + pluginEnum.getLayer() +
                    " - platform: " + pluginEnum.getPlatform();
            throw new PluginNotFoundException(e, context, "layer not found for the specified plugin.");
        }
    }

    public final Platforms getPlatformEnum() { return platformEnum; }

    public abstract void start() throws CantStartPlatformException;

    public abstract AbstractPluginIdsManager getPluginIdsManager(final PlatformFileSystem platformFileSystem) throws CantStartPluginIdsManagerException;

}
