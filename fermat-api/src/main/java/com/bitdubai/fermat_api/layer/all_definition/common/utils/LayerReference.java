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

}
