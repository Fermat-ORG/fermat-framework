package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.helpers;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angel on 6/05/16.
 */

public class AdapterPlatformsSupported {

    public static String getPlatformsSupported(List<Platforms> platforms) {
        String result = "";

        for (Platforms p : platforms) {
            result += new StringBuilder().append(p.getCode()).append(":").toString();
        }

        return result;
    }

    public static List<Platforms> getPlatformsSupported(String platforms) throws InvalidParameterException {

        List<Platforms> result = new ArrayList<>();

        String[] plats = platforms.split(":");

        for (int i = 0; i < plats.length; i++) {

            result.add(Platforms.getByCode(plats[i]));

        }

        return result;
    }

}
