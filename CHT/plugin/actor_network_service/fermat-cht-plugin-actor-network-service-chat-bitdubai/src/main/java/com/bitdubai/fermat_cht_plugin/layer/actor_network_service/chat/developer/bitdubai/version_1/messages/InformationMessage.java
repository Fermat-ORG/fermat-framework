package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_cht_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.enums.MessageTypes;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 07/04/16.
 */
public class InformationMessage extends NetworkServiceMessage {

    private final UUID requestId;
    private final ConnectionRequestAction action;

    public InformationMessage(final UUID requestId,
                              final ConnectionRequestAction action) {

        super(MessageTypes.CONNECTION_INFORMATION);

        this.requestId = requestId;
        this.action = action;
    }

    private InformationMessage(JsonObject jsonObject, Gson gson) {

        super(MessageTypes.CONNECTION_INFORMATION);

        this.requestId = UUID.fromString(jsonObject.get("requestId").getAsString());
        this.action = gson.fromJson(jsonObject.get("action").getAsString(), ConnectionRequestAction.class);

    }

    public static InformationMessage fromJson(String jsonString) {

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        return new InformationMessage(jsonObject, gson);
    }

    @Override
    public String toJson() {

        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("messageType", getMessageType().toString());
        jsonObject.addProperty("requestId", requestId.toString());
        jsonObject.addProperty("action", action.toString());
        return gson.toJson(jsonObject);

    }

    public UUID getRequestId() {
        return requestId;
    }

    public ConnectionRequestAction getAction() {
        return action;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("InformationMessage{")
                .append("requestId=").append(requestId)
                .append(", action=").append(action)
                .append('}').toString();
    }
}
