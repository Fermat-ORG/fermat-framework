package com.bitdubai.fermat_api.layer.core;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Matias Furszyfer
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
public @interface PluginInfo {

    public enum Dificulty {
        LOW, MEDIUM, HIGH
    }

    Dificulty difficulty() default Dificulty.MEDIUM;

    String[] tags() default "";

    String createdBy();

    String maintainerMail();

    String lastModified() default "15/03/2016";

    Platforms platform();

    Layers layer();

    Plugins plugin();

    Developers developer() default Developers.BITDUBAI;

    String version() default "1.0.0";


}