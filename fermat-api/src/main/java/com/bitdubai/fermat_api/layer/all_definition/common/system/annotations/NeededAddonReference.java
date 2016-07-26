package com.bitdubai.fermat_api.layer.all_definition.common.system.annotations;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation <code>com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference</code>
 * contains all the data needed to build the addon version reference to assign it to the addon/plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface NeededAddonReference {

    Platforms platform();

    Layers layer();

    Addons addon();

    Developers developer() default Developers.BITDUBAI;

    String version() default "1.0.0";

}
