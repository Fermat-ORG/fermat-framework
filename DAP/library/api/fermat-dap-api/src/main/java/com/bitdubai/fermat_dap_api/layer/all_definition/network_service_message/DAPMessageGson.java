package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message;

import com.bitdubai.fermat_api.layer.all_definition.util.GenericGsonAdapter;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.DAPContentMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 4/01/16.
 */
public class DAPMessageGson {

    //VARIABLE DECLARATION

    //CONSTRUCTORS

    //PUBLIC METHODS

    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(DAPContentMessage.class, new GenericGsonAdapter<DAPContentMessage>())
                .registerTypeAdapter(DAPActor.class, new GenericGsonAdapter<DAPActor>());
        return builder.create();
    }

    //PRIVATE METHODS
    //GETTER AND SETTERS

    //INNER CLASSES
}
