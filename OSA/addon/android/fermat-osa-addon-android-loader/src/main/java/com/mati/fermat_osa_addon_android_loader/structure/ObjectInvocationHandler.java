package com.mati.fermat_osa_addon_android_loader.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.mati.fermat_osa_addon_android_loader.LoaderManager;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.06.24..
 */
public class ObjectInvocationHandler implements InvocationHandler {

    private LoaderManager loaderManager;
    private Object object;

    public ObjectInvocationHandler(LoaderManager loaderManager,Object object) {
        this.loaderManager = loaderManager;
        this.object = object;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object objectToReturn = null;
        try {
            Class<?>[] parameterTypes = MfClassUtils.getTypes(args);
//            if (args != null) {
//                if(args.length>0) {
//                    parameterTypes = new Class[args.length];
//                    for (int i = 0; i < args.length; i++) {
//                        parameterTypes[i] = args[i].getClass();
//                    }
//                }
//            }
            Method m = null;
            Object result = null;
            try {
                if (parameterTypes == null) {
                    m = object.getClass().getMethod(method.getName());
                    result = m.invoke(object);
                } else {
                    m = object.getClass().getMethod(method.getName(), parameterTypes);
                    result = m.invoke(object, args);
                }
            }catch (InvocationTargetException e){
                System.out.println("Method: " + m.getName() + ", object: " + object.getClass() + ", args: " +((parameterTypes!=null)? Arrays.toString(args):null)+ "."+"\n");
                throw new Exception(e);
            }
            Class<?> returnTypeClazz = method.getReturnType();
            if(returnTypeClazz==null || returnTypeClazz.equals(Void.TYPE)){
                System.out.println("devolviendo void/null"+"\n");
                return null;
            }
//            if(returnTypeClazz.getSuperclass().getName().equals("com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin")){
//                System.out.println("########################\n");
//                System.out.println("Es un abstractPlugin el objeto a devolver!");
//                System.out.println("########################\n");
//            }
//            if(method.getName().equals("listVersionsMati")){
//                List<PluginVersionReference> pluginVersionReferences = new ArrayList<>();
//                List<Object> list = (List) result;
//                for (Object o : list) {
//                    Object abstractPlugin = loaderManager.objectToProxyFactory(o,getClass().getClassLoader(),o.getClass().getInterfaces(), AbstractPlugin.class);
//                    pluginVersionReferences.add(abstractPlugin);
//                }
//            }
            if(method.getName().equals("getVersions")){
                System.out.println("**************************\n");
                System.out.println("Method getVersions, please check this. si llega hasta acá vamos mejor que antes");
                System.out.println("**************************\n");
            }if(method.getName().equals("getPluginByVersionMati")){
                System.out.println("**************************\n");
                System.out.println("Method getPluginByVersionMati, devolviendo version");
                System.out.println("**************************\n");
                return result;
            } else {
                if (!returnTypeClazz.isPrimitive() && !ClassUtils.isPrimitiveOrWrapper(returnTypeClazz)) {
                    if (!returnTypeClazz.isEnum()) {
                        if(!returnTypeClazz.equals(String.class)) {
                            if (!Collection.class.isAssignableFrom(returnTypeClazz)) {
                                objectToReturn = loadObject(result, returnTypeClazz);
                            } else {
                                if (method.getName().equals("listVersionsMati")) {
                                    List<PluginVersionReference> pluginVersionReferences = new ArrayList<>();
                                    List<Object> list = (List) result;
                                    for (Object o : list) {
                                        PluginVersionReference pluginVersionReference = (PluginVersionReference) loadObject(o, PluginVersionReference.class);
                                        pluginVersionReferences.add(pluginVersionReference);
                                    }
                                    objectToReturn = pluginVersionReferences;
                                }
                                System.out.println("Object is a collection, type: " + result.getClass().getName() + ", ObjectToConvert: " + result + ", returnTypeClass: " + returnTypeClazz + "\n");
                            }
                        }else {
                            try {
                                System.out.println("Object is a String, type: " + result.getClass().getName() + ", ObjectToConvert: " + result + ", returnTypeClass: " + returnTypeClazz + "\n");
                                objectToReturn = result.toString();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    } else {
                        System.out.println("******\n");
                        System.out.println("Object to return is enum, please see this..");
                        System.out.println("++++++\n");
                        try {
                            objectToReturn = Enum.valueOf((Class<Enum>) returnTypeClazz, result.toString());
                        } catch (Exception e) {
                            System.out.println("Error al cargar el enum, enum: " + returnTypeClazz.getName()+"\n");
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("devolviendo primitivo"+"\n");
                    return result;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error in ObjectInvocationHandler",e);
        }
        return objectToReturn;
    }

    private Object loadObject(Object objectToConvert,Class returnTypeClazz){
        Constructor<?> constructor = null;
        Object objectToReturn = null;
        if(objectToConvert==null){
            System.out.println("object to convert null, class: "+returnTypeClazz.getName()+", returning null"+"\n");
            return null;
        }
        try {
            if(!ClassUtils.isPrimitiveOrWrapper(returnTypeClazz)) {
                if(!returnTypeClazz.isEnum()) {
                    constructor = returnTypeClazz.getDeclaredConstructor();
                    objectToReturn = constructor.newInstance();
                    Field[] resultClassFields = objectToConvert.getClass().getDeclaredFields();
                    for (Field field : resultClassFields) {
                        try {
                            int modifiers = field.getModifiers();
                            if (Modifier.isStatic(modifiers)) {
                                if(Modifier.isFinal(modifiers)){
                                    System.out.println("Object to set is constant, jumping one loop"+"\n");
                                    continue;
                                }
                            }
                            if (Collection.class.isAssignableFrom(field.getType())) {
                                System.out.println("Object is a collection, type: " + field.getType() + ", ObjectToConvert: " + objectToConvert + ", returnTypeClass: " + returnTypeClazz+"\n");
//                                SerializationUtils.clone((Serializable) field.get(objectToReturn))
                            }else {
                                String fieldName = field.getName();
                                field.setAccessible(true);
                                Object fieldObject = field.get(objectToConvert);
                                if(fieldObject!=null) {
                                    try {
                                        Field fieldToReturn = objectToReturn.getClass().getDeclaredField(fieldName);
                                        fieldToReturn.setAccessible(true);
                                        Object fieldConvertedObject = null;
                                        if(fieldToReturn.getType().equals(String.class)){
                                            fieldConvertedObject = fieldObject.toString();
                                        }else {
                                            fieldConvertedObject = loadObject(fieldObject, fieldToReturn.getType());
                                        }
                                        fieldToReturn.set(objectToReturn, fieldConvertedObject);
                                    } catch (IllegalArgumentException e) {
                                        System.out.print("IllegalArgumentException: fieldToReturn: " + field.getName() + ", Object to return class name: " + returnTypeClazz + ".\n");
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("field name: " + field.getName() + " object to return: " + objectToReturn);
                            e.printStackTrace();
                        }
                    }
                }else{
                    System.out.println("Enum class, type: "+returnTypeClazz+". returning without load class\n");
                    try {
                        objectToReturn = Enum.valueOf((Class<Enum>) returnTypeClazz, objectToConvert.toString());
                    }catch (Exception e){
                        System.out.println("Forma de cargar el enum falló, enum: "+returnTypeClazz.getName()+"\n");
                        e.printStackTrace();
                    }
                }
            }else{
                System.out.println("is not Object class, type: "+returnTypeClazz+". returning without load class\n");
            }
            return objectToReturn;


        } catch (NoSuchMethodException e) {
            System.out.println("object returned that launch the exception: " + returnTypeClazz.getName() + " object to convert: " + ((objectToConvert != null) ? objectToConvert.toString() : "null")+"\n");
            e.printStackTrace();
            return objectToConvert;
            //throw new Exception("Error in ObjectInvocationHandler, object type: "+clazz.getName(),e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
