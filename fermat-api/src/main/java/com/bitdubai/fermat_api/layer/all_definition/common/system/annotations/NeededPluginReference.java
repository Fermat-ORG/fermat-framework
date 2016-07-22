package com.bitdubai.fermat_api.layer.all_definition.common.system.annotations;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation <code>NeededPluginReference</code>
 * contains all the data needed to build the plugin version reference to assign it to the plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/10/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Inherited
public @interface NeededPluginReference {

    Platforms platform();

    Layers layer();

    Plugins plugin();

    Developers developer() default Developers.BITDUBAI;

    String version() default "1.0.0";

}
