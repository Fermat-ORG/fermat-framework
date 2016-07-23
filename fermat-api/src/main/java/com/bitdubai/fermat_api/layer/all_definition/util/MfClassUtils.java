package com.bitdubai.fermat_api.layer.all_definition.util;

import java.lang.reflect.Proxy;

/**
 * Created by Matias Furszfer on 2016.06.25..
 */
public class MfClassUtils {


    public static Class<?>[] getTypes(Object[] args) {
        Class<?>[] parameterTypes = null;
        if (args != null) {
            if (args.length > 0) {
                parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    Class clazz = args[i].getClass();
                    if (Proxy.isProxyClass(clazz)) {
                        if (clazz.getInterfaces().length > 0)
                            clazz = clazz.getInterfaces()[0];
                    }
                    parameterTypes[i] = clazz;
                }
            }
        }
        return parameterTypes;
    }

    public static Class<?>[] getTypes(Object[] args, ClassLoader classLoader) {
        Class<?>[] parameterTypes = null;
        if (args != null) {
            if (args.length > 0) {
                parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    Class<?> clazz = args[i].getClass();
                    try {
                        //todo: ver si el clazz.getName devuelve todo el nombre o solo una parte
                        parameterTypes[i] = classLoader.loadClass(clazz.getName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return parameterTypes;
    }

    public static Class<?>[] getTypes(Class[] args, ClassLoader classLoader) {
        Class<?>[] parameterTypes = null;
        if (args != null) {
            if (args.length > 0) {
                parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    Class<?> clazz = args[i];
                    try {
                        //todo: ver si el clazz.getName devuelve todo el nombre o solo una parte
                        parameterTypes[i] = classLoader.loadClass(clazz.getName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return parameterTypes;
    }

}
