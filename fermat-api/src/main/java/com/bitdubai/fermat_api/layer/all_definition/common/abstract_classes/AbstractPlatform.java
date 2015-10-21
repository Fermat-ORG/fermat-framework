package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPluginIdsManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.LayerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.PluginNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;

import java.util.HashMap;
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

    protected final PlatformFileSystem platformFileSystem;

    public AbstractPlatform(final PlatformFileSystem platformFileSystem) {

        this.layers             = new ConcurrentHashMap<>();
        this.platformFileSystem = platformFileSystem;
    }

    /**
     * Throw the method <code>addLayer</code> you can add new layers to the platform.
     * Here we'll corroborate too that the layer is not added twice.
     *
     * @param layer          layer descriptor (element of enum).
     * @param abstractLayer  layer instance.
     *
     * @throws CantStartPlatformException if something goes wrong.
     */
    protected final void addLayer(final Layers        layer           ,
                                  final AbstractLayer abstractLayer) throws CantStartPlatformException {

        try {

            if(layers.get(layer) != null)
                throw new CantStartPlatformException("layer: " + layer.toString(), "Layer already exists in this platform.");

            abstractLayer.start();

            layers.put(
                    layer,
                    abstractLayer
            );

        } catch (final CantStartLayerException e) {

            throw new CantStartPlatformException(e, "layer: " + layer.toString(), "Error trying to start the platform.");
        }
    }

    public final AbstractLayer getLayer(Layers layer) throws LayerNotFoundException {
        if (layers.containsKey(layer))
            return layers.get(layer);
        else
            throw new LayerNotFoundException("layer: "+layer, "layer not found.");
    }

    public final Plugin getPlugin(final FermatPluginsEnum plugin) throws PluginNotFoundException {

        try {

            return getLayer(plugin.getLayer()).getPlugin(plugin);

        } catch (LayerNotFoundException e) {

            String context =
                    "plugin: "      + plugin.toString() +
                    " - layer: "    + plugin.getLayer() +
                    " - platform: " + plugin.getPlatform();
            throw new PluginNotFoundException(e, context, "layer not found for the specified plugin.");
        }
    }

    public abstract void start() throws CantStartPlatformException;

    public abstract AbstractPluginIdsManager getPluginIdsManager() throws CantStartPluginIdsManagerException;

}
