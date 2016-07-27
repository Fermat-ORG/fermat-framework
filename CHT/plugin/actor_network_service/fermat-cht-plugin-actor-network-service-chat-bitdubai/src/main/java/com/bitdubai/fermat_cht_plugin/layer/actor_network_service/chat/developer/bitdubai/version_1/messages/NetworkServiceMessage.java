package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.enums.MessageTypes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 07/04/16.
 */
public class NetworkServiceMessage {

    public static final Gson customGson = new GsonBuilder().registerTypeHierarchyAdapter(byte[].class,
            new ByteArrayToBase64TypeAdapter()).create();

    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }

    private MessageTypes messageType;

    public NetworkServiceMessage() {
    }

    public NetworkServiceMessage(final MessageTypes messageType) {
        this.messageType = messageType;
    }

    public String toJson() {

        return NetworkServiceMessage.customGson.toJson(this);
    }

    public MessageTypes getMessageType() {
        return messageType;
    }

    private NetworkServiceMessage(JsonObject jsonObject, Gson gson) {

        this.messageType = gson.fromJson(jsonObject.get("messageType").getAsString(), MessageTypes.class);

    }

    public static NetworkServiceMessage fromJson(String jsonString) {

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        return new NetworkServiceMessage(jsonObject, gson);
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append("NetworkServiceMessage{")
                .append("messageType=").append(messageType)
                .append('}').toString();
    }
}
