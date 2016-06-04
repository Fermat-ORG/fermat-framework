package org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.procesors;

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

    protected static JsonObject getJsonObjectFromJsonObject(
            JsonObject jsonObject,
            String field){
        JsonElement jsonElement = jsonObject.get(field);
        if(jsonElement==null){
            return null;
        }
        return jsonElement.getAsJsonObject();
    }

    protected static String getStringFromJsonElement(
            JsonElement jsonElement,
            String field){
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return getStringFromJsonObject(jsonObject, field);
    }

}
