package com.bitdubai.fermat_api.layer.all_definition.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 4/01/16.
 * This class purpose is to help with the serialization/deserialization when a class
 * or some of its fields uses polymorphism.
 * <b>Example: The interface "Animal" have two implementations, Cat and Dog. The way
 * to use this class is the following: </b>
 * <p></p>
 * {@code             GsonBuilder builder = new GsonBuilder();
 * builder.registerTypeAdapter(Animal.class, new GenericGsonAdapter<Animal>());
 * Gson gson =  builder.create();}
 * Then you can just serialize then deserialize:
 * {@code             Animal animal = gson.fromJson(jsonString, Animal.class);
 * }
 * <p></p>
 * And use that animal as an interface.
 * If you have a container class that wraps several interfaces you can have to register
 * every interface as a type adapter like the following:
 * <p></p>
 * {@code GsonBuilder builder = new GsonBuilder();
 * builder.registerTypeAdapter(Animal.class, new GenericGsonAdapter<Animal>());
 * builder.registerTypeAdapter(Sound.class, new GenericGsonAdapter<Sound>());
 * builder.registerTypeAdapter(Color.class, new GenericGsonAdapter<Color>());
 * Gson gson =  builder.create();}
 */
public class GenericGsonAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    //VARIABLE DECLARATION
    private static final String CLASS_NAME = "CLASS_NAME";
    private static final String INSTANCE = "INSTANCE";
    //CONSTRUCTORS

    //PUBLIC METHODS

    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the
     * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws JsonParseException if json is not in the expected format of {@code typeofT}
     */
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive primitive = (JsonPrimitive) jsonObject.get(CLASS_NAME);
        String className = primitive.getAsString();

        Class<?> fieldClass = null;
        try {
            fieldClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject.get(INSTANCE), fieldClass);
    }

    /**
     * Gson invokes this call-back method during serialization when it encounters a field of the
     * specified type.
     * <p/>
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
     * non-trivial field of the {@code src} object. However, you should never invoke it on the
     * {@code src} object itself since that will cause an infinite loop (Gson will call your
     * call-back method again).</p>
     *
     * @param src       the object that needs to be converted to Json.
     * @param typeOfSrc the actual type (fully genericized version) of the source object.
     * @param context
     * @return a JsonElement corresponding to the specified object.
     */
    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        String className = src.getClass().getName();
        retValue.addProperty(CLASS_NAME, className);
        JsonElement elem = context.serialize(src);
        retValue.add(INSTANCE, elem);
        return retValue;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
