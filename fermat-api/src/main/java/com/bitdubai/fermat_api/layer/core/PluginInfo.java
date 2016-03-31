package com.bitdubai.fermat_api.layer.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
public @interface PluginInfo {

	public enum Dificulty {
		LOW, MEDIUM, HIGH
	}

	Dificulty difficulty() default Dificulty.MEDIUM;
	
	String[] tags() default "";
	
	String createdBy() default "furszy";
	
	String lastModified() default "15/03/2016";



}