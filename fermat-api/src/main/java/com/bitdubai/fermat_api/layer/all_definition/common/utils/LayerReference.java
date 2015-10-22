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

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private       Platforms platform;
    private final Layers    layer   ;

    public LayerReference(final Layers    layer   ) {

        this.layer    = layer   ;
    }

    public LayerReference(final Platforms platform,
                          final Layers    layer   ) {

        this.platform = platform;
        this.layer    = layer   ;
    }

    public Platforms getPlatform() {
        return platform;
    }

    public void setPlatform(Platforms platform) {
        this.platform = platform;
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
        int c = 0;

        c += layer .hashCode();

        if (platform != null)
            c += platform.hashCode();

        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return "LayerReference{" +
                "platform=" + platform +
                ", layer=" + layer +
                '}';
    }
}
