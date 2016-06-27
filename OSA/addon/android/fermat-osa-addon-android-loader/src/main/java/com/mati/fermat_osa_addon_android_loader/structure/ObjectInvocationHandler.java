package com.mati.fermat_osa_addon_android_loader.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.MfClassUtils;
import com.mati.fermat_osa_addon_android_loader.LoaderManager;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang3.ClassUtils;

import java.io.Serializable;
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
        Object object = null;
        try {
            object = callMethod(method, args);
        }catch (Throwable e){
            e.printStackTrace();
        }
        if(object instanceof Throwable){
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
                System.err.println("InvocationException: Method: " + m.getName() + ", object: " + object.getClass() + ", args: " + ((parameterTypes != null) ? Arrays.toString(args) : null) + "." + "\n");
                e.getTargetException().printStackTrace();
                return e.getTargetException();
            }
            Class<?> returnTypeClazz = method.getReturnType();
            if (returnTypeClazz == null || returnTypeClazz.equals(Void.TYPE)) {
                System.out.println("devolviendo void/null" + "\n");
                return null;
            }
            if (method.getName().equals("getVersions")) {
                System.out.println("**************************\n");
                System.out.println("Method getVersions");
                System.out.println("**************************\n");
            }
            if (method.getName().equals("getPluginByVersionMati")) {
                return result;
            } else {
                if (!returnTypeClazz.isPrimitive() && !ClassUtils.isPrimitiveOrWrapper(returnTypeClazz)) {
                    if (!returnTypeClazz.isEnum()) {
                        if (!returnTypeClazz.equals(String.class)) {
                            if (!Collection.class.isAssignableFrom(returnTypeClazz)) {
                                objectToReturn = loadObject(result, returnTypeClazz);
                            } else {
                                if (method.getName().equals("listVersionsMati")) {
                                    //todo: esto no es necesario, está a modo de prueba, hay que borrarlo
                                    List<PluginVersionReference> pluginVersionReferences = new ArrayList<>();
                                    List<Object> list = (List) result;
                                    for (Object o : list) {
                                        PluginVersionReference pluginVersionReference = (PluginVersionReference) loadObject(o, PluginVersionReference.class);
                                        pluginVersionReferences.add(pluginVersionReference);
                                    }
                                    objectToReturn = pluginVersionReferences;
                                } else {
                                    System.out.println("Object is a collection, type: " + result.getClass().getName() + ", ObjectToConvert: " + result + ", returnTypeClass: " + returnTypeClazz + "\n");
                                    if (returnTypeClazz.equals(List.class) || returnTypeClazz.equals(ArrayList.class)) {
                                        System.out.println("is a list/arraylist" + "\n");
                                        List list = (List) result;
                                        List<Object> listToReturn = new ArrayList<>();
                                        if (!list.isEmpty()) {
                                            System.out.println("list is not empty" + "\n");
                                            for (Object o : list) {
                                                Class<?> clazzListElement = getClass().getClassLoader().loadClass(o.getClass().getName());
                                                System.out.println("list elements:" + clazzListElement.getName() + "\n");
                                                Object objectConverted = loadObject(o, clazzListElement);
                                                System.out.println("object returned from loadObject loading list::" + objectConverted + "\n");
                                                listToReturn.add(objectConverted);
                                            }
                                        }
                                        objectToReturn = listToReturn;
                                    }else {
                                        System.out.println("Trying to return a collection, please check this" + "\n");
                                        objectToReturn = rebuild(result);
                                    }
                                }
                            }
                        } else {
                            try {
                                System.out.println("Object is a String, type: " + result.getClass().getName() + ", ObjectToConvert: " + result + ", returnTypeClass: " + returnTypeClazz + "\n");
                                objectToReturn = result.toString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            objectToReturn = Enum.valueOf((Class<Enum>) returnTypeClazz, result.toString());
                        } catch (Exception e) {
                            System.err.println("Error al cargar el enum, enum: " + returnTypeClazz.getName() + "\n");
                            e.printStackTrace();
                            objectToReturn = e;
                        }
                    }
                } else {
                    System.out.println("devolviendo primitivo" + "\n");
                    return result;
                }
            }

        }catch (InvocationTargetException e){
            System.err.println("InvocationException: (bottom catch) Method: " + method.getName() + ", object: " + object.getClass() + ", args: " + ((args != null) ? Arrays.toString(args) : null) + "." + "\n");
            e.printStackTrace();
            return e.getTargetException();
        }catch (Exception e){
            System.err.println("Exception unknown");
            e.printStackTrace();
            throw new Exception("Exception in ObjectInvocationHandler",e);
        } catch (Throwable throwable) {
            System.err.println("Exception unknown");
            throwable.printStackTrace();
            throw new Exception("Exception in loadObject",throwable);
        }
        return objectToReturn;
    }

    private Object loadObject(Object objectToConvert,Class returnTypeClazz) throws Throwable {
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
                                if (Modifier.isFinal(modifiers)) {
                                    System.out.println("Object to set is constant, jumping one loop" + "\n");
                                    continue;
                                }
                            }
                            if (Collection.class.isAssignableFrom(field.getType())) {
                                System.out.println("Object is a collection, type: " + field.getGenericType() + ", ObjectToConvert: " + objectToConvert + ", returnTypeClass: " + returnTypeClazz + "\n");
//                                SerializationUtils.clone((Serializable) field.get(objectToReturn))
                                objectToReturn = rebuild(objectToConvert);
                            } else {
                                String fieldName = field.getName();
                                field.setAccessible(true);
                                Object fieldObject = field.get(objectToConvert);
                                if (fieldObject != null) {
                                    try {
                                        Field fieldToReturn = objectToReturn.getClass().getDeclaredField(fieldName);
                                        fieldToReturn.setAccessible(true);
                                        Object fieldConvertedObject = null;
                                        if (ClassUtils.isPrimitiveOrWrapper(field.getType())) {
                                            fieldConvertedObject = fieldObject;
                                        } else if (fieldToReturn.getType().equals(String.class)) {
                                            fieldConvertedObject = fieldObject.toString();
                                        } else {
                                            fieldConvertedObject = loadObject(fieldObject, fieldToReturn.getType());
                                        }
                                        fieldToReturn.set(objectToReturn, fieldConvertedObject);
                                    } catch (IllegalArgumentException e) {
                                        System.err.print("IllegalArgumentException: fieldToReturn: " + field.getName() + ", Object to return class name: " + returnTypeClazz + ".\n");
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }catch (InvocationTargetException e){
                            e.getTargetException().printStackTrace();
                            objectToReturn = e.getTargetException();
                        } catch (Exception e) {
                            System.err.println("InvocationTargetException: field name: " + field.getName() + " object to return: " + objectToReturn);
                            e.printStackTrace();
                        }
                    }
                }else{
                    System.out.println("Enum class, type: "+returnTypeClazz+". returning without load class\n");
                    try {
                        objectToReturn = Enum.valueOf((Class<Enum>) returnTypeClazz, objectToConvert.toString());
                    }catch (Exception e){
                        System.err.println("EnumException: Forma de cargar el enum falló, enum: "+returnTypeClazz.getName()+"\n");
                        e.printStackTrace();
                    }
                }
            }else{
                System.out.println("is primitive class, type: "+returnTypeClazz+". returning without load class\n");
            }
            return objectToReturn;


        } catch (NoSuchMethodException e) {
            System.err.println("NoSuchMethodException: object returned that launch the exception: " + returnTypeClazz.getName() + " object to convert: " + ((objectToConvert != null) ? objectToConvert.toString() : "null") + "\n");
            e.printStackTrace();
            objectToReturn = rebuild(objectToConvert);
            if (objectToReturn==null) e.printStackTrace();
            else System.out.println("Devolviendo objeto re construido!");
            //throw new Exception("Error in ObjectInvocationHandler, object type: "+clazz.getName(),e);
        } catch (InvocationTargetException e) {
            System.err.print("IllegalArgumentException: object to convert: " + objectToConvert + ", Object to return class name: " + returnTypeClazz + ".\n");
            e.getTargetException().printStackTrace();
            objectToReturn = e.getTargetException();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return objectToReturn;
    }

    private Object rebuild(Object objectToConvert){
        Object objectToReturn = null;
        try {
            byte[] serializable = null;
            //test
            serializable = SerializationUtils.serialize((Serializable) objectToConvert);
            objectToReturn = SerializationUtils.deserialize(serializable);
        }catch (Exception e1){
            e1.printStackTrace();
        }
        return objectToReturn;
    }
}
