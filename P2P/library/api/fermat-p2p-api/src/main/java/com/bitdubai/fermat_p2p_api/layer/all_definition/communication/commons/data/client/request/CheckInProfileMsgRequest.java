package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.PackageContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileMsgRequest</code>
 * represent the message to request to register a profile with the node<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckInProfileMsgRequest extends PackageContent {

    /**
     * Represent the profileToRegister to check in
     */
    private Profile profileToRegister;

    /**
     * Constructor with parameters
     *
     * @param profileToRegister
     */
    public CheckInProfileMsgRequest(Profile profileToRegister) {
        this.profileToRegister = profileToRegister;
    }

    /**
     * Gets the value of profileToRegister and returns
     *
     * @return profileToRegister
     */
    public Profile getProfileToRegister() {
        return profileToRegister;
    }

    @Override
    public String toJson() {

        return getGsonInstance().toJson(this, getClass());
    }

    public static CheckInProfileMsgRequest parseContent(String content) {

        return getGsonInstance().fromJson(content, CheckInProfileMsgRequest.class);
    }

    private static Gson getGsonInstance() {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Profile.class, new InterfaceAdapter<Profile>());
        builder.registerTypeAdapter(Location.class, new InterfaceAdapter<Location>());
        return builder.create();
    }
}
