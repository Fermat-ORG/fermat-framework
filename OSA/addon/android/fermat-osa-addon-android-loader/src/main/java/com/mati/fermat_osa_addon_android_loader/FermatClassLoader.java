package com.mati.fermat_osa_addon_android_loader;

import android.util.Log;

/**
 * Created by Matias Furszyfer on 2016.06.22..
 */
public class FermatClassLoader extends ClassLoader {

    private static final String TAG = "FermatClassLoader";
    private ClassLoader classLoader;
    private ClassLoader classLoaderParent;


    public FermatClassLoader(ClassLoader classLoader, ClassLoader classLoaderParent) {
        this.classLoader = classLoader;
        this.classLoaderParent = classLoaderParent;
    }

    public FermatClassLoader(ClassLoader parentLoader, ClassLoader classLoader, ClassLoader classLoaderParent) {
        super(parentLoader);
        this.classLoader = classLoader;
        this.classLoaderParent = classLoaderParent;
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {

        try {
            if (classLoader != null) {
                Class<?> c = classLoader.loadClass(className);
                if (c != null)
                    return c;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//            Log.i(TAG,"Clase no encontrada");
            try {
                if (classLoaderParent != null) {
                    Class<?> c = classLoaderParent.loadClass(className);
                    if (c != null)
                        return c;
                }
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
//                Log.i(TAG,"Clase padre no encontrada");
                try {
                    Class<?> c = getSystemClassLoader().loadClass(className);
                    if (c != null)
                        return c;
                    else
                        return super.loadClass(className);
                } catch (Exception e2) {
                    Log.i(TAG, "Clase base no encontrada");
                    e2.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.loadClass(className);
    }
}
