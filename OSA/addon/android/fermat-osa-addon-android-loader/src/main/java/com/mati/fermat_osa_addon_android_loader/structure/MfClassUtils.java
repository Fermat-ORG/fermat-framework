package com.mati.fermat_osa_addon_android_loader.structure;

/**
 * Created by Matias Furszfer on 2016.06.25..
 */
public class MfClassUtils {


    public static Class<?>[] getTypes(Object[] args){
        Class<?>[] parameterTypes = null;
        if (args != null) {
            if(args.length>0) {
                parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = args[i].getClass();
                }
            }
        }
        return parameterTypes;
    }

    public static Class<?>[] getTypes(Object[] args,ClassLoader classLoader){
        Class<?>[] parameterTypes = null;
        if (args != null) {
            if(args.length>0) {
                parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    Class<?> clazz =  args[i].getClass();
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
