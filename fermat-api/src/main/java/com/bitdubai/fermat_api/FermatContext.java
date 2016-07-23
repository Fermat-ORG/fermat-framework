package com.bitdubai.fermat_api;

/**
 * Created by Matias Furszyfer on 2016.06.22..
 */
public interface FermatContext {

    ClassLoader getBaseClassLoader();

    ClassLoader getExternalLoader(String name);

    Object loadObject(String pluginName);

    Object objectToProxyfactory(Object base, ClassLoader interfaceLoader, Class[] interfaces, Object returnInterface);

    Object loadProxyObject(String moduleName, ClassLoader interfaceLoader, Class[] interfaces, Object returnInterface, Object... parameters);

    void registerReceiver(FermatIntentFilter filter, FermatBroadcastReceiver fermatBroadcastReceiver, String appPublicKey);

    void unregisterReceiver(FermatBroadcastReceiver fermatBroadcastReceiver, String appPublicKey);


}
