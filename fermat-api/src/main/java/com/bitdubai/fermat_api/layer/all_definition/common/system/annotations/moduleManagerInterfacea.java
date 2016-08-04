package com.bitdubai.fermat_api.layer.all_definition.common.system.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mati on 2016.04.23..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface moduleManagerInterfacea {

    /**
     * Interface implemented by the module manager,
     * this interface has to be the same class that the developer will have in his android plugin to consume methods.
     *
     * @return
     */
    Class moduleManager();

}
