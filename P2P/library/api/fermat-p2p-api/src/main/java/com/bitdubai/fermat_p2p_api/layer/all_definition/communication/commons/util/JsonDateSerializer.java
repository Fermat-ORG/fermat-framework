/*
 * @#JsonDateSerializer.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util;


import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Timestamp;


/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.JsonDateSerializer</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class JsonDateSerializer implements JsonSerializer<Timestamp> {

    @Override
    public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? null : new JsonPrimitive(src.getTime());
    }
}
