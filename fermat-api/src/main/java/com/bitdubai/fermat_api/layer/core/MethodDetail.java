package com.bitdubai.fermat_api.layer.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Matias furszyfer on 2016.05.14..
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //on class level
public @interface MethodDetail {

    public enum LoopType{
        MAIN,BACKGROUND
    }

    LoopType looType();

}
