package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.ProfileAdapter</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ProfileAdapter extends TypeAdapter<Profile> {

    @Override
    public Profile read(final JsonReader in) throws IOException {

        Profile profile = null;

        try {
            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "type":
                        
                        switch (ProfileTypes.getByCode(in.nextString())) {
                            case ACTOR:
                                profile = ActorProfile.readJson(in);
                                break;
                            case CLIENT:
                                profile = ClientProfile.readJson(in);
                                break;
                            case NETWORK_SERVICE:
                                profile = NetworkServiceProfile.readJson(in);
                                break;
                            case NODE:
                                profile = NodeProfile.readJson(in);
                                break;
                        }

                        break;
                }
            }
            in.endObject();
        } catch (InvalidParameterException invalidParameterException) {

            throw new IOException("Malformed json.");
        }

        return profile;
    }

    @Override
    public void write(final JsonWriter out, final Profile profile) throws IOException {
        out.beginObject();
        profile.writeJson(out);
        out.endObject();
    }
}
