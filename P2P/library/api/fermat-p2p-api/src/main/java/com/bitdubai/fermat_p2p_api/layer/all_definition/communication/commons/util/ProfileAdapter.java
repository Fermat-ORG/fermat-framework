package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.ProfileAdapter</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ProfileAdapter implements JsonSerializer<Profile>, JsonDeserializer<Profile> {

    @Override
    public Profile deserialize(final JsonElement                element,
                               final Type                       type   ,
                               final JsonDeserializationContext context) {

        JsonObject jsonObject = element.getAsJsonObject();

        switch (ProfileTypes.getByCode(jsonObject.get("typ").getAsString())) {

            case ACTOR          : return ActorProfile.deserialize(jsonObject);
            case CLIENT         : return ClientProfile.deserialize(jsonObject);
            case NETWORK_SERVICE: return NetworkServiceProfile.deserialize(jsonObject);
            case NODE           : return NodeProfile.deserialize(jsonObject);
            default             : return null;
        }
    }

    @Override
    public JsonElement serialize(final Profile                  element,
                                 final Type                     type   ,
                                 final JsonSerializationContext context) {

        return element.serialize();
    }
}
