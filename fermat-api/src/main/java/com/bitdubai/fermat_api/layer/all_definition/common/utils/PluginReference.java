package com.bitdubai.fermat_api.layer.all_definition.common.utils;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference</code>
 * haves all the information of a PluginReference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class PluginReference {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private       LayerReference    layerReference;
    private       Version           version       ;
    private final FermatPluginsEnum plugin        ;

    public PluginReference(final FermatPluginsEnum plugin) {

        this.plugin = plugin;
    }

    public PluginReference(final LayerReference    layerReference,
                           final FermatPluginsEnum plugin        ) {

        this.layerReference = layerReference;
        this.plugin         = plugin        ;
    }

    public PluginReference(final LayerReference    layerReference,
                           final FermatPluginsEnum plugin        ,
                           final Version           version       ) {

        this.layerReference = layerReference;
        this.plugin         = plugin        ;
        this.version        = version       ;
    }

    public final FermatPluginsEnum getPlugin() {
        return plugin;
    }

    public final Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public final LayerReference getLayerReference() {
        return layerReference;
    }

    public final void setLayerReference(LayerReference layerReference) {
        this.layerReference = layerReference;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof PluginReference))
            return false;
        PluginReference compare = (PluginReference) o;

        return plugin.equals(compare.getPlugin()) &&
                ((version == null && compare.getVersion() == null) || (version != null && version.equals(compare.getVersion()))) &&
                ((layerReference == null && compare.getLayerReference() == null) || (layerReference != null && layerReference.equals(compare.getLayerReference())));
    }

    @Override
    public int hashCode() {
        int c = 0;

        c += plugin .hashCode();

        if (version != null)

            c += version.hashCode();

        if (layerReference != null)
            c += layerReference.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return "PluginReference{" +
                "layerReference=" + layerReference +
                ", plugin=" + plugin +
                ", version=" + version +
                '}';
    }
}
