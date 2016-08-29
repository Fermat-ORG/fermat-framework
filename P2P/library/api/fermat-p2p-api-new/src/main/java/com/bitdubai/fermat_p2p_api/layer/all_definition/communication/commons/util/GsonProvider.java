package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.sql.Timestamp;



/**
 * Created by rrequena on 25/04/16.
 */
public class GsonProvider {


    /**
     * Represent the gson
     */

    /**
     * Represent the gson
     */
//    private final Gson gsonExposeAnnotation;

    /**
     * Represent the jsonParser
     */
//    private final JsonParser jsonParser;

    private final GsonBuilder builder;

    /**
     * Represent the instance
     */
    private static final GsonProvider instance = new GsonProvider();

    /**
     * Constructor
     */
    private GsonProvider(){
        builder  = new GsonBuilder();
        builder.registerTypeAdapter(Timestamp.class, new TimestampAdapter());
        builder.registerTypeAdapter(Profile.class, new InterfaceAdapter<Profile>());
        builder.registerTypeAdapter(Location.class, new LocationAdapter());
//        this.gsonExposeAnnotation = builder.excludeFieldsWithoutExposeAnnotation().create();
//        this.jsonParser  =
    }

    /**
     * Get the gson instance
     * @return
     */
    public static Gson getGson() {
        return instance.builder.create();
    }

    /**
     * Get the gson instance
     * @return
     */
    public static Gson getGsonExposeAnnotation() {
        return instance.builder.excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * Get the jsonParser instance
     * @return JsonParser
     */
    public static JsonParser getJsonParser(){
        return new JsonParser();
    }

}
