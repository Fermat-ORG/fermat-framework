package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.JsonDateAdapter</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class JsonDateAdapter implements JsonDeserializer<Timestamp>, JsonSerializer<Timestamp> {

    @Override
    public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json == null ? null : new Timestamp(json.getAsLong());
    }

    @Override
    public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? null : new JsonPrimitive(src.getTime());
    }

}
