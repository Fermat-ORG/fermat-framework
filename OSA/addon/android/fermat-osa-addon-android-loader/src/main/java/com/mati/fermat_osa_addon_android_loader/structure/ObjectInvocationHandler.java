package com.mati.fermat_osa_addon_android_loader.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.MfClassUtils;
import com.mati.fermat_osa_addon_android_loader.LoaderManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Matias Furszyfer on 2016.06.24..
 */
public class ObjectInvocationHandler implements InvocationHandler {

    private LoaderManager loaderManager;
    private Object object;

    public ObjectInvocationHandler(LoaderManager loaderManager, Object object) {
        this.loaderManager = loaderManager;
        this.object = object;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = null;
        try {
            object = callMethod(method, args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (object instanceof Throwable) {
            throw (Throwable) object;
        }
        return object;
    }

    private Object callMethod(Method method, Object[] args) throws Exception {
        Object objectToReturn = null;
        try {
            Class<?>[] parameterTypes = MfClassUtils.getTypes(args);
            Method m = null;
            Object result = null;
            try {
                if (parameterTypes == null) {
                    m = object.getClass().getMethod(method.getName());
                    result = m.invoke(object);
                } else {
                    m = object.getClass().getMethod(method.getName(), method.getParameterTypes());
                    result = m.invoke(object, args);
                }
            } catch (InvocationTargetException e) {
                System.err.println("InvocationException: Method: " + m.getName() + ", object: " + object.getClass() + ", args: " + ((parameterTypes != null) ? Arrays.toString(args) : null) + ". This exception is controlled by the plugin that make the call" + "\n");
//                e.getTargetException().printStackTrace();
                return e.getTargetException();
            }
            if (result == null) {
                return null;
            }
            objectToReturn = result;
        } catch (Exception e) {
            System.err.println("Exception unknown");
            e.printStackTrace();
            throw new Exception("Exception in ObjectInvocationHandler", e);
        }
        return objectToReturn;
    }

}
