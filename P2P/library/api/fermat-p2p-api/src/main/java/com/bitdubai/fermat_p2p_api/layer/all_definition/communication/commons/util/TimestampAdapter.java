package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.TimestampAdapter</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/03/16.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 23/06/2016.
 *
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class TimestampAdapter extends TypeAdapter<Timestamp> {

    @Override
    public Timestamp read(final JsonReader in) throws IOException {

        Timestamp timeStamp = null;
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "time":
                    timeStamp = new Timestamp(Long.valueOf(in.nextString()));
            }
        }
        in.endObject();

        return timeStamp;
    }

    @Override
    public void write(final JsonWriter out, final Timestamp book) throws IOException {
        out.beginObject();

        if (book != null)
            out.name("time").value(book.getTime());

        out.endObject();
    }
}
