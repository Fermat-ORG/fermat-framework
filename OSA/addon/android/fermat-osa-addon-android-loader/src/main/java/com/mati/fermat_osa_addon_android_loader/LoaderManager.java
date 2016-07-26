package com.mati.fermat_osa_addon_android_loader;

import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_api.FermatContext;
import com.mati.fermat_osa_addon_android_loader.structure.ObjectInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by Matias Furszyfer on 2016.06.24..
 */
public class LoaderManager<O extends FermatApplicationSession & FermatContext> {

    private ClassLoaderManager<O> classLoaderManager;

    public LoaderManager(O fermatContext) {
        classLoaderManager = new ClassLoaderManager<>(fermatContext);
    }

    public Object objectProxyFactory(String moduleName, ClassLoader interfaceLoader, Class[] interfaces, Object returnInterface, Object... args) {
        return objectToProxyFactory(load(moduleName, args), interfaceLoader, interfaces, returnInterface);
    }

    public Object load(String moduleName, Object... args) {
        return classLoaderManager.load(moduleName, args);
    }

    public Object objectToProxyFactory(Object base, ClassLoader interfaceLoader, Class[] interfaces, Object returnInterface) {
        InvocationHandler invocationHandler = new ObjectInvocationHandler(this, base);
        return Proxy.newProxyInstance(
                interfaceLoader,
                interfaces,
                invocationHandler);
    }


    public ClassLoader getExternalLoader(String name) {
        return classLoaderManager.getExternalLoader(name);
    }
}
