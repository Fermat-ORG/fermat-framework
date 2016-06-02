package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Matias Furszyfer on 2016.03.29..
 */
public class ProxyInvocationHandlerAIDL<T extends ModuleManager> implements InvocationHandler {

    private static final String TAG = "ProxyHandler";

    private ClientSystemBrokerServiceAIDL clientSystemBrokerService;
    private PluginVersionReference pluginVersionReference;


    public ProxyInvocationHandlerAIDL(ClientSystemBrokerServiceAIDL clientSystemBrokerService, PluginVersionReference pluginVersionReference) {
        this.clientSystemBrokerService = clientSystemBrokerService;
        this.pluginVersionReference = pluginVersionReference;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {


//        Log.i(TAG,"object: "+ proxy.getClass().getInterfaces());
//        Log.i(TAG,"method: "+ method.getName());
//        Log.i(TAG, "args: " + args);


        Object returnedObject = clientSystemBrokerService.sendMessage(
                                    pluginVersionReference,
                                   proxy,
                                   method,
                                   args);
        if(returnedObject instanceof Exception){
            throw (Throwable) returnedObject;
        }else {
            return returnedObject;
        }

    }

    public PluginVersionReference getPluginVersionReference() {
        return pluginVersionReference;
    }
}
