package com.bitdubai.fermat_cbp_api.all_definition.adapters;

import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_api.layer.world.interfaces.CurrencyHelper;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;


/**
 * Created by Nelson Ramirez - (nelsonalfo@gmail.com) on 20/07/2016
 */
public class CurrencyTypeHierarchyAdapter implements JsonSerializer<Currency>, JsonDeserializer<Currency> {

    @Override
    public JsonElement serialize(Currency bObj, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject result = new JsonObject();
        result.add("type", context.serialize(bObj.getType(), CurrencyTypes.class));
        result.add("code", context.serialize(bObj.getCode(), String.class));

        return result;
    }

    @Override
    public Currency deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject object = json.getAsJsonObject();
            CurrencyTypes type = context.deserialize(object.get("type"), CurrencyTypes.class);
            String code = context.deserialize(object.get("code"), String.class);

            return CurrencyHelper.getCurrency(type, code);

        } catch (InvalidParameterException invalidParameterException) {
            throw new JsonParseException("Problem trying to parse the currency.", invalidParameterException);
        }
    }
}