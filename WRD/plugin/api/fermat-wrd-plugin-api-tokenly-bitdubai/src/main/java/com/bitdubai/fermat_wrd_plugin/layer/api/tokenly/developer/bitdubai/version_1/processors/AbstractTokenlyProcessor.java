package com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.processors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public abstract class AbstractTokenlyProcessor {

    /**
     * This method returns a String from a JsonObject by a given field.
     * The named field must be defined in any class from the package config.
     * @param jSonObject
     * @param field
     * @return
     */
    protected static String getStringFromJsonObject(JsonObject jSonObject, String field){
        JsonElement jsonElement=jSonObject.get(field);
        if(jsonElement==null){
            return "null";
        }
        return jsonElement.getAsString();
    }

    /**
     * This method returns a long String from a JsonObject by a given field.
     * The named field must be defined in any class from the package config.
     * @param jSonObject
     * @param field
     * @return
     */
    protected static String getLongStringFromJsonObject(JsonObject jSonObject, String field){
        JsonElement jsonElement=jSonObject.get(field);
        if(jsonElement==null){
            return "0";
        }
        return ""+jsonElement.getAsLong();
    }
    /**
     * This method returns a long String from a JsonObject by a given field.
     * The named field must be defined in any class from the package config.
     * @param jSonObject
     * @param field
     * @return
     */
    protected static String getDateStringFromJsonObject(JsonObject jSonObject, String field){
        JsonElement jsonElement=jSonObject.get(field);
        if(jsonElement==null){
            return "2016-03-07T12:18:43.016Z";
        }
        return jsonElement.getAsString();
    }

}
