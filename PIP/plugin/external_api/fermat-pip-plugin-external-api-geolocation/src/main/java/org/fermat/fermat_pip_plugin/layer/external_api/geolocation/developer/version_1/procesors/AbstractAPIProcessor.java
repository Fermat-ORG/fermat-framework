package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public abstract class AbstractAPIProcessor {

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
    protected static long getLongFromJsonObject(JsonObject jSonObject, String field){
        JsonElement jsonElement=jSonObject.get(field);
        if(jsonElement==null){
            return 0;
        }
        return jsonElement.getAsLong();
    }

    /**
     * This method returns a long String from a JsonObject by a given field.
     * The named field must be defined in any class from the package config.
     * @param jSonObject
     * @param field
     * @return
     */
    protected static Date getDateFromJsonObject(JsonObject jSonObject, String field){
        JsonElement jsonElement=jSonObject.get(field);
        if(jsonElement==null){
            return new Date(1961);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        String dateString = jsonElement.getAsString().replace("T", "");
        Date dateFromJson;
        try {
            java.util.Date utilDate = simpleDateFormat.parse(dateString);
            dateFromJson = new Date(utilDate.getTime());
        } catch (ParseException e) {
            //Default date
            return new Date(2016);
        }
        return dateFromJson;
    }

    /**
     * This method returns a double String from a JsonObject by a given field.
     * The named field must be defined in any class from the package config.
     * @param jSonObject
     * @param field
     * @return
     */
    protected static double getDoubleFromJsonObject(JsonObject jSonObject, String field){
        JsonElement jsonElement=jSonObject.get(field);
        if(jsonElement==null){
            return 0.0;
        }
        return jsonElement.getAsDouble();
    }

    /**
     * This method returns a String Array from a JsonObject by a given field.
     * The named field must be defined in any class from the package config.
     * @param jSonObject
     * @param field
     * @return
     */
    protected static String[] getArrayStringFromJsonObject(
            JsonObject jSonObject,
            String field){
        JsonElement jsonElement = jSonObject.get(field);
        if(jsonElement==null){
            return new String[]{"null"};
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        String[] valuesArray = new String[jsonArray.size()];
        int counter = 0;
        for(JsonElement jsonElementFromArray : jsonArray){
            valuesArray[counter] = jsonElementFromArray.getAsString();
            counter++;
        }
        return valuesArray;
    }

    /**
     * This method returns a float Array from a JsonObject by a given field.
     * The named field must be defined in any class from the package config.
     * @param jSonObject
     * @param field
     * @return
     */
    protected static float[] getArrayFloatFromJsonObject(
            JsonObject jSonObject,
            String field){
        JsonElement jsonElement = jSonObject.get(field);
        if(jsonElement==null){
            return new float[]{0};
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        float[] valuesArray = new float[jsonArray.size()];
        int counter = 0;
        for(JsonElement jsonElementFromArray : jsonArray){
            valuesArray[counter] = jsonElementFromArray.getAsFloat();
            counter++;
        }
        return valuesArray;
    }

    /**
     * This method returns a jsonObject from a JsonObject by a given field.
     * The named field must be defined in any class from the package config.
     * @param jsonObject
     * @param field
     * @return
     */
    protected static JsonObject getJsonObjectFromJsonObject(
            JsonObject jsonObject,
            String field){
        JsonElement jsonElement = jsonObject.get(field);
        if(jsonElement==null){
            return null;
        }
        return jsonElement.getAsJsonObject();
    }

    /**
     * This method returns a String from a JsonElement by a given field.
     * The named field must be defined in any class from the package config.
     * @param jsonElement
     * @param field
     * @return
     */
    protected static String getStringFromJsonElement(
            JsonElement jsonElement,
            String field){
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return getStringFromJsonObject(jsonObject, field);
    }

    /**
     * This method returns a long from a JsonElement by a given field.
     * The named field must be defined in any class from the package config.
     * @param jsonElement
     * @param field
     * @return
     */
    protected static long getLongFromJsonElement(
            JsonElement jsonElement,
            String field){
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return getLongFromJsonObject(jsonObject, field);
    }

}
