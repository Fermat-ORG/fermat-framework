package com.bitdubai.fermat_api.layer.all_definition.common.system.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatPluginsEnum;

import java.io.Serializable;

/**
 * The class <code>PluginReference</code>
 * haves all the information of a PluginReference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class PluginReference implements Serializable {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private LayerReference layerReference;
    private FermatPluginsEnum plugin;

    public PluginReference(final Plugins plugin) {

        this.plugin = plugin;
    }

    public PluginReference(final LayerReference layerReference,
                           final FermatPluginsEnum plugin) {

        this.layerReference = layerReference;
        this.plugin = plugin;
    }

    public PluginReference() {
    }

    public final FermatPluginsEnum getPlugin() {
        return plugin;
    }

    public final LayerReference getLayerReference() {
        return layerReference;
    }

    public final void setLayerReference(LayerReference layerReference) {
        this.layerReference = layerReference;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof PluginReference))
            return false;
        PluginReference compare = (PluginReference) o;

        return plugin.equals(compare.getPlugin()) &&
                ((layerReference == null && compare.getLayerReference() == null) || (layerReference != null && layerReference.equals(compare.getLayerReference())));
    }

    @Override
    public final int hashCode() {
        int c = 0;
        if (plugin != null)
            c += plugin.hashCode();
        if (layerReference != null)
            c += layerReference.hashCode();

        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public final String toString() {
        return new StringBuilder()
                .append("PluginReference{")
                .append("layerReference=").append(layerReference)
                .append(", plugin=").append(plugin)
                .append('}').toString();
    }
}
