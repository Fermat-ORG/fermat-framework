package com.bitdubai.fermat_api.layer.all_definition.common.system.annotations;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation <code>NeededLayerReference</code>
 * contains all the data needed to assign a layer for the plugin.
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 03/12/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NeededLayerReference {

    Platforms platform();

    Layers layer();

}
