package com.bitdubai.fermat_api.layer.all_definition.common.system.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;

import java.io.Serializable;

/**
 * The class <code>LayerReference</code>
 * haves all the information of a Layer Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class LayerReference implements Serializable {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private PlatformReference platformReference;
    private Layers layer;

    public LayerReference(final Layers layer) {

        this.layer = layer;
    }

    public LayerReference() {
    }

    public LayerReference(final PlatformReference platformReference,
                          final Layers layer) {

        this.platformReference = platformReference;
        this.layer = layer;
    }

    public LayerReference(final Platforms platform,
                          final Layers layer) {

        this.platformReference = new PlatformReference(platform);
        this.layer = layer;
    }

    public PlatformReference getPlatformReference() {
        return platformReference;
    }

    public void setPlatformReference(PlatformReference platformReference) {
        this.platformReference = platformReference;
    }

    public Layers getLayer() {
        return layer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LayerReference)) return false;

        LayerReference that = (LayerReference) o;

        return layer.equals(that.layer) &&
                ((platformReference == null && that.getPlatformReference() == null) || (platformReference != null && platformReference.equals(that.getPlatformReference())));
    }

    @Override
    public int hashCode() {
        int c = 0;

        if (layer != null)
            c += layer.hashCode();

        if (platformReference != null)
            c += platformReference.hashCode();

        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("LayerReference{")
                .append("platformReference=").append(platformReference)
                .append(", layer=").append(layer)
                .append('}').toString();
    }

    public String toString3() {
        return new StringBuilder()
                .append("Layer{")
                .append(platformReference.getPlatform())
                .append(", ").append(layer)
                .append('}').toString();
    }
}
