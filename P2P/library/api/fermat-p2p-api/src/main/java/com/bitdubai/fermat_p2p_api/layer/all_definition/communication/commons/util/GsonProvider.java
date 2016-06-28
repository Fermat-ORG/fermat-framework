package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;

/**
 * Created by rrequena on 25/04/16.
 */
public class GsonProvider {

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the instance
     */
    private static final GsonProvider instance = new GsonProvider();

    /**
     * Constructor
     */
    private GsonProvider(){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Timestamp.class, new TimestampAdapter());
        builder.registerTypeAdapter(Profile.class, new InterfaceAdapter<Profile>());
        builder.registerTypeAdapter(Location.class, new LocationAdapter());
        gson = builder.create();
    }

    /**
     * Get the gson instance
     * @return
     */
    public static Gson getGson() {
        return instance.gson;
    }
}
