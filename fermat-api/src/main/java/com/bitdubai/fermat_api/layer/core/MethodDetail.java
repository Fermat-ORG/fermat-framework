package com.bitdubai.fermat_api.layer.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Created by Matias furszyfer on 2016.05.14..
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //on class level
public @interface MethodDetail {

    public enum LoopType {
        MAIN, BACKGROUND
    }

    LoopType looType();

    /**
     * if the method take much time to the timeout, the method will be stopped and return null
     *
     * @return
     */
    long timeout() default -1;

    TimeUnit timeoutUnit() default TimeUnit.SECONDS;

    /**
     * This control how many times a methods is posibly used in parallel.
     * 0 = unlimited,
     *
     * @return
     */
    int methodParallelQuantity() default 0;

}
