package com.bitdubai.fermat_api.layer.all_definition.common.utils;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonsEnum;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference</code>
 * haves all the information of a AddonReference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class AddonReference {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private       LayerReference   layerReference;

    private final FermatAddonsEnum addon         ;
    private final Version          version       ;

    public AddonReference(final FermatAddonsEnum  addon         ,
                          final Version           version       ) {

        this.addon          = addon         ;
        this.version        = version       ;
    }

    public final FermatAddonsEnum getAddon() {
        return addon;
    }

    public final Version getVersion() {
        return version;
    }

    public final LayerReference getLayerReference() {
        return layerReference;
    }

    public final void setLayerReference(final LayerReference layerReference) {
        this.layerReference = layerReference;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof PluginReference))
            return false;
        PluginReference compare = (PluginReference) o;

        return addon.equals(compare.getPlugin()) &&
                version.equals(compare.getVersion()) &&
                layerReference.equals(compare.getLayerReference());
    }

    @Override
    public int hashCode() {
        int c = 0;
        c += addon .hashCode();
        c += version.hashCode();
        c += layerReference.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return "AddonReference{" +
                "layerReference=" + layerReference +
                ", addon=" + addon +
                ", version=" + version +
                '}';
    }
}
