package com.mati.fermat_osa_addon_android_loader.structure;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

/**
 * Created by Matias Furszyfer on 2016.06.24..
 */
public class ObjectInvocationHandler implements InvocationHandler {

    private Object object;

    public ObjectInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object objectToReturn = null;
        try {
            Class<?>[] parameterTypes = null;
            if (args != null) {
                if(args.length>0) {
                    parameterTypes = new Class[args.length];
                    for (int i = 0; i < args.length; i++) {
                        parameterTypes[i] = args[i].getClass();
                    }
                }
            }
            Method m = null;
            Object result = null;
            if (parameterTypes == null) {
                m = object.getClass().getMethod(method.getName());
                result = m.invoke(object);
            } else {
                m = object.getClass().getMethod(method.getName(), parameterTypes);
                result = m.invoke(object, args);
            }
            Class<?> returnTypeClazz = method.getReturnType();
            if(returnTypeClazz==null || returnTypeClazz.equals(Void.TYPE)){
                System.out.println("devolviendo void/null");
                return null;
            }
            if(!returnTypeClazz.isPrimitive() && !ClassUtils.isPrimitiveOrWrapper(returnTypeClazz)) {
                if(!returnTypeClazz.isEnum()) {
                    objectToReturn = loadObject(result, returnTypeClazz);
                }else{
                    System.out.println("******\n");
                    System.out.println("Object to return is enum, please see this..");
                    System.out.println("++++++\n");
                    try {
                        objectToReturn = Enum.valueOf((Class<Enum>) returnTypeClazz, result.toString());
                    }catch (Exception e){
                        System.out.println("Error al cargar el enum, enum: "+returnTypeClazz.getName());
                        e.printStackTrace();
                    }
                }
            }else{
                System.out.println("devolviendo primitivo");
                return result;
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
            System.out.println("object to convert null, class: "+returnTypeClazz.getName()+", returning null");
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
                                    System.out.println("Object to set is constant, jumping one loop");
                                    continue;
                                }
                            }
                            if (Collection.class.isAssignableFrom(field.getType())) {
                                System.out.println("Object is a collection, type: "+field.getType()+", ObjectToConvert: "+objectToConvert+", returnTypeClass: "+returnTypeClazz);
//                                SerializationUtils.clone((Serializable) field.get(objectToReturn))
                            }else {
                                String fieldName = field.getName();
                                field.setAccessible(true);
                                Object fieldObject = field.get(objectToConvert);
                                try {
                                    Field fieldToReturn = objectToReturn.getClass().getDeclaredField(fieldName);
                                    fieldToReturn.setAccessible(true);
                                    Object fieldConvertedObject = loadObject(fieldObject, fieldToReturn.getType());
                                    fieldToReturn.set(objectToReturn, fieldConvertedObject);
                                } catch (IllegalArgumentException e) {
                                    System.out.print("IllegalArgumentException: fieldToReturn: " + field.getName() + ", Object to return name: " + objectToReturn+".m");
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("field name: " + field.getName() + " object to return: " + objectToReturn);
                            e.printStackTrace();
                        }
                    }
                }else{
                    System.out.println("Enum class, type: "+returnTypeClazz+". returning without load class");
                    try {
                        objectToReturn = Enum.valueOf((Class<Enum>) returnTypeClazz, objectToConvert.toString());
                    }catch (Exception e){
                        System.out.println("Forma de cargar el enum falló, enum: "+returnTypeClazz.getName());
                        e.printStackTrace();
                    }
                }
            }else{
                System.out.println("is not Object class, type: "+returnTypeClazz+". returning without load class");
            }
            return objectToReturn;


        } catch (NoSuchMethodException e) {
            System.out.println("object returned that launch the exception: " + returnTypeClazz.getName() + " object to convert: " + ((objectToConvert != null) ? objectToConvert.toString() : "null"));
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
