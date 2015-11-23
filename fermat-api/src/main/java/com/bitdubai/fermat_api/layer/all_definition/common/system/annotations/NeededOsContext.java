package com.bitdubai.fermat_api.layer.all_definition.common.system.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation <code>com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededOsContext</code>
 * indicates that the add-on needs the os context.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NeededOsContext {
}
