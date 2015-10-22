package com.bitdubai.fermat_api.layer.all_definition.common.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.utils.LayerReference</code>
 * haves all the information of a Layer Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class LayerReference {

    private final Platforms platform;
    private final Layers    layer   ;

    public LayerReference(final Platforms platform,
                          final Layers    layer   ) {

        this.platform = platform;
        this.layer    = layer   ;
    }

    public Platforms getPlatform() {
        return platform;
    }

    public Layers getLayer() {
        return layer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LayerReference)) return false;

        LayerReference that = (LayerReference) o;

        return platform.equals(that.platform) && layer.equals(that.layer);
    }

    @Override
    public int hashCode() {
        int result = platform.hashCode();
        result = 31 * result + layer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LayerReference{" +
                "platform=" + platform +
                ", layer=" + layer +
                '}';
    }
}
