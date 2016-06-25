package com.mati.fermat_osa_addon_android_loader;

import android.app.Application;

import com.bitdubai.fermat_api.FermatContext;
import com.mati.fermat_osa_addon_android_loader.structure.ObjectInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by Matias Furszyfer on 2016.06.24..
 */
public class LoaderManager<O extends Application & FermatContext> {

    private ClassLoaderManager<O> classLoaderManager;

    public LoaderManager(O fermatContext) {
        classLoaderManager = new ClassLoaderManager<>(fermatContext);
    }

    public Object objectProxyFactory(String moduleName,ClassLoader interfaceLoader,Class[] interfaces,Object returnInterface){
        return objectToProxyFactory(load(moduleName),interfaceLoader,interfaces,returnInterface);
    }

    public Object load(String moduleName){
        return classLoaderManager.load(moduleName);
    }

    public Object objectToProxyFactory(Object base, ClassLoader interfaceLoader, Class[] interfaces, Object returnInterface) {
        InvocationHandler invocationHandler = new ObjectInvocationHandler(base);
        return Proxy.newProxyInstance(
                interfaceLoader,
                interfaces,
                invocationHandler);
    }


}
