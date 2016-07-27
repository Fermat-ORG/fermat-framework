package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.core.MethodDetail;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2016.03.29..
 */
public class ProxyInvocationHandlerAIDL<T extends ModuleManager> implements InvocationHandler {

    private static final String TAG = "ProxyHandler";

    private ClientSystemBrokerServiceAIDL clientSystemBrokerService;
    private PluginVersionReference pluginVersionReference;


    private Map<Integer, Method> methodsIdentifiers;


    public ProxyInvocationHandlerAIDL(ClientSystemBrokerServiceAIDL clientSystemBrokerService, PluginVersionReference pluginVersionReference) {
        this.clientSystemBrokerService = clientSystemBrokerService;
        this.pluginVersionReference = pluginVersionReference;
        this.methodsIdentifiers = new HashMap<>();
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {


//        Log.i(TAG,"object: "+ proxy.getClass().getInterfaces());
//        Log.i(TAG,"method: "+ method.getName());
//        Log.i(TAG, "args: " + args);

        final MethodDetail methodDetail = method.getAnnotation(MethodDetail.class);

        int quantity = 0;
        if (methodDetail != null) quantity = methodDetail.methodParallelQuantity();

//        if(quantity>0){
//            synchronized (this) {
//                if (methodsIdentifiers.containsKey(method.hashCode())) {
//                    throw new MethodParallelQuantityExceedException();
//                } else {
//                    methodsIdentifiers.put(method.hashCode(), method);
//                }
//            }
//        }
        Object returnedObject = clientSystemBrokerService.sendMessage(
                pluginVersionReference,
                proxy,
                method,
                methodDetail,
                args);

//        if (quantity>0){
//            if (methodsIdentifiers.containsKey(method.hashCode())){
//                methodsIdentifiers.remove(method.hashCode());
//            }else{
//                Log.e(TAG,"Acá hay algo mal, contactar a furszy");
//            }
//        }
        if (returnedObject instanceof Exception) {
            throw (Throwable) returnedObject;
        } else {
            return returnedObject;
        }

    }

    public PluginVersionReference getPluginVersionReference() {
        return pluginVersionReference;
    }
}
