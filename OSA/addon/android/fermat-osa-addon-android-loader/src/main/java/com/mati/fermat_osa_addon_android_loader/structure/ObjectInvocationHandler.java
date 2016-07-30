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
                System.err.println(new StringBuilder().append("InvocationException: Method: ").append(m.getName()).append(", object: ").append(object.getClass()).append(", args: ").append((parameterTypes != null) ? Arrays.toString(args) : null).append(". This exception is controlled by the plugin that make the call").append("\n").toString());
//                e.getTargetException().printStackTrace();
                return e.getTargetException();
            }
            if (result == null) {
                return null;
            }
//            if(method.getName().equals("createDatabase")){
//                return result;
//            }
            objectToReturn = result;
//            if(result instanceof Serializable){
//                try {
//                    byte[] o = Serializer.prepareData(result);
//                    //class loader de la plataforma por ahora
//                    if (o!=null) objectToReturn = Serializer.desearialize(o, getClass().getClassLoader());
//                }catch (Exception e){
////                    e.printStackTrace();
//                    System.out.println("Objeto no serializable");
//                }
////                objectToReturn = rebuild(result);
//            }

            //this is not necessary, i have to move it to another place.
            if (objectToReturn == null) {
                Class<?> returnTypeClazz = method.getReturnType();
                if (returnTypeClazz == null || returnTypeClazz.equals(Void.TYPE)) {
                    System.out.println(new StringBuilder().append("devolviendo void/null").append("\n").toString());
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
                                        System.out.println(new StringBuilder().append("Object is a collection, type: ").append(result.getClass().getName()).append(", ObjectToConvert: ").append(result).append(", returnTypeClass: ").append(returnTypeClazz).append("\n").toString());
                                        if (returnTypeClazz.equals(List.class) || returnTypeClazz.equals(ArrayList.class)) {
                                            System.out.println(new StringBuilder().append("is a list/arraylist").append("\n").toString());
                                            List list = (List) result;
                                            List<Object> listToReturn = new ArrayList<>();
                                            if (!list.isEmpty()) {
                                                System.out.println(new StringBuilder().append("list is not empty").append("\n").toString());
                                                for (Object o : list) {
                                                    Class<?> clazzListElement = getClass().getClassLoader().loadClass(o.getClass().getName());
                                                    System.out.println(new StringBuilder().append("list elements:").append(clazzListElement.getName()).append("\n").toString());
                                                    Object objectConverted = loadObject(o, clazzListElement);
                                                    System.out.println(new StringBuilder().append("object returned from loadObject loading list::").append(objectConverted).append("\n").toString());
                                                    listToReturn.add(objectConverted);
                                                }
                                            }
                                            objectToReturn = listToReturn;
                                        } else {
                                            System.out.println(new StringBuilder().append("Trying to return a collection, please check this").append("\n").toString());
                                            objectToReturn = rebuild(result);
                                        }
                                    }
                                }
                            } else {
                                try {
                                    System.out.println(new StringBuilder().append("Object is a String, type: ").append(result.getClass().getName()).append(", ObjectToConvert: ").append(result).append(", returnTypeClass: ").append(returnTypeClazz).append("\n").toString());
                                    objectToReturn = result.toString();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            try {
                                objectToReturn = Enum.valueOf((Class<Enum>) returnTypeClazz, result.toString());
                            } catch (Exception e) {
                                System.err.println(new StringBuilder().append("Error al cargar el enum, enum: ").append(returnTypeClazz.getName()).append("\n").toString());
                                e.printStackTrace();
                                objectToReturn = e;
                            }
                        }
                    } else {
                        System.out.println(new StringBuilder().append("devolviendo primitivo").append("\n").toString());
                        return result;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Exception unknown");
            e.printStackTrace();
            throw new Exception("Exception in ObjectInvocationHandler", e);
        }
        return objectToReturn;
    }

    private Object loadObject(Object objectToConvert, Class returnTypeClazz) {
        Constructor<?> constructor = null;
        Object objectToReturn = null;
        if (objectToConvert == null) {
            System.out.println(new StringBuilder().append("object to convert null, class: ").append(returnTypeClazz.getName()).append(", returning null").append("\n").toString());
            return null;
        }
        try {
            if (!ClassUtils.isPrimitiveOrWrapper(returnTypeClazz)) {
                if (!returnTypeClazz.isEnum()) {
                    constructor = returnTypeClazz.getDeclaredConstructor();
                    objectToReturn = constructor.newInstance();
                    Field[] resultClassFields = objectToConvert.getClass().getDeclaredFields();
                    for (Field field : resultClassFields) {
                        try {
                            int modifiers = field.getModifiers();
                            if (Modifier.isStatic(modifiers)) {
                                if (Modifier.isFinal(modifiers)) {
                                    continue;
                                }
                            }
                            if (Collection.class.isAssignableFrom(field.getType())) {
                                System.out.println(new StringBuilder().append("Object is a collection, type: ").append(field.getGenericType()).append(", ObjectToConvert: ").append(objectToConvert).append(", returnTypeClass: ").append(returnTypeClazz).append("\n").toString());
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
                                        System.err.print(new StringBuilder().append("IllegalArgumentException: fieldToReturn: ").append(field.getName()).append(", Object to return class name: ").append(returnTypeClazz).append(".\n").toString());
                                        e.printStackTrace();
                                    }
                                }
                            }

//                            }
//                        catch (InvocationTargetException e){
//                            e.getTargetException().printStackTrace();
//                            objectToReturn = e.getTargetException();
                        } catch (Exception e) {
                            System.err.println(new StringBuilder().append("Exception: (loading fields) field name: ").append(field.getName()).append(" object to return: ").append(objectToReturn).toString());
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println(new StringBuilder().append("Enum class, type: ").append(returnTypeClazz).append(". returning without load class\n").toString());
                    try {
                        objectToReturn = Enum.valueOf((Class<Enum>) returnTypeClazz, objectToConvert.toString());
                    } catch (Exception e) {
                        System.err.println(new StringBuilder().append("EnumException: Forma de cargar el enum falló, enum: ").append(returnTypeClazz.getName()).append("\n").toString());
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println(new StringBuilder().append("is primitive class, type: ").append(returnTypeClazz).append(". returning without load class\n").toString());
            }
        } catch (NoSuchMethodException e) {
            System.err.println(new StringBuilder().append("NoSuchMethodException: object returned that launch the exception: ").append(returnTypeClazz.getName()).append(" object to convert: ").append((objectToConvert != null) ? objectToConvert.toString() : "null").append("\n").toString());
            objectToReturn = rebuild(objectToConvert);
            if (objectToReturn == null) e.printStackTrace();
            else System.out.println("Devolviendo objeto re construido!");
            //throw new Exception("Error in ObjectInvocationHandler, object type: "+clazz.getName(),e);
        } catch (InvocationTargetException e) {
            System.err.print(new StringBuilder().append("IllegalArgumentException: object to convert: ").append(objectToConvert).append(", Object to return class name: ").append(returnTypeClazz).append(".\n").toString());
            e.getTargetException().printStackTrace();
            objectToReturn = e.getTargetException();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectToReturn;
    }

    private Object rebuild(Object objectToConvert) {
        Object objectToReturn = null;
        try {
            byte[] serializable = null;
            //test
            serializable = SerializationUtils.serialize((Serializable) objectToConvert);
            objectToReturn = SerializationUtils.deserialize(serializable);
        } catch (Exception e1) {
            // if is serializable this will works
        }
        return objectToReturn;
    }
}
