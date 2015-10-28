package com.bitdubai.fermat_api.layer.all_definition.common.annotations;

import com.bitdubai.fermat_api.layer.all_definition.common.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation <code>com.bitdubai.fermat_api.layer.all_definition.common.annotations.NeededAddonReference</code>
 * haves all the data needed to build the addon version reference to assign it to the addon/plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NeededAddonReference {

    OperativeSystems operativeSystem() default OperativeSystems.INDIFFERENT;
    Platforms        platform();
    Layers           layer();
    Addons           addon();
    Developers       developer()       default Developers.BITDUBAI;
    String           version()         default "1.0.0";

    Class<? extends FermatManager> referenceManagerClass();

}
