/*
 * @#FermatInterfaceAdapter.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.util.FermatInterfaceAdapter</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class FermatInterfaceAdapter implements JsonSerializer<Object>, JsonDeserializer<Object> {

    private static final String _CLASS = "_class";

    @Override
    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObj = jsonElement.getAsJsonObject();
        String className = jsonObj.get(_CLASS).getAsString();

        try {

            Class<?> clz = Class.forName(className);
            return jsonDeserializationContext.deserialize(jsonElement, clz);

        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement jsonEle = jsonSerializationContext.serialize(object, object.getClass());
        jsonEle.getAsJsonObject().addProperty(_CLASS, object.getClass().getCanonicalName());
        return jsonEle;
    }

}