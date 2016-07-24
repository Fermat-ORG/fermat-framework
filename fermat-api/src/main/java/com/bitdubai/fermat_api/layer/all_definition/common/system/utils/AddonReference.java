package com.bitdubai.fermat_api.layer.all_definition.common.system.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;

import java.io.Serializable;

/**
 * The class <code>AddonReference</code>
 * haves all the information of a AddonReference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class AddonReference implements Serializable {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private LayerReference layerReference;
    private Addons addon;

    public AddonReference(final Addons addon) {

        this.addon = addon;
    }

    public AddonReference() {
    }

    public AddonReference(final LayerReference layerReference,
                          final Addons addon) {

        this.layerReference = layerReference;
        this.addon = addon;
    }

    public final Addons getAddon() {
        return addon;
    }

    public final LayerReference getLayerReference() {
        return layerReference;
    }

    public final void setLayerReference(final LayerReference layerReference) {
        this.layerReference = layerReference;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AddonReference))
            return false;
        AddonReference compare = (AddonReference) o;

        return addon.equals(compare.getAddon()) &&
                ((layerReference == null && compare.getLayerReference() == null) || (layerReference != null && layerReference.equals(compare.getLayerReference())));
    }

    @Override
    public int hashCode() {
        int c = 0;
        if (addon != null)
            c += addon.hashCode();
        if (layerReference != null)
            c += layerReference.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("AddonReference{")
                .append("layerReference=").append(layerReference)
                .append(", addon=").append(addon)
                .append('}').toString();
    }
}
