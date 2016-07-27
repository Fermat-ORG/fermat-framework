package com.bitdubai.sub_app.crypto_broker_identity.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoBrokerIdentityResourceSearcher extends ResourceSearcher {


    @Override
    public int obtainResDrawable(Context context, int id) {
        int resId = 0;
        switch (id) {
            case FragmentsCommons.ADD_IDENTITY_OPTION_MENU_ID:
                resId = R.drawable.add_identity;
                break;

            case FragmentsCommons.HELP_OPTION_MENU_ID:
                resId = R.drawable.help_icon_identity_broker;
                break;

            case FragmentsCommons.GEOLOCATION_SETTINGS_OPTION_MENU_ID:
                resId = R.drawable.cbp_id_geolocation_icon;
                break;

            default:
                resId = 0;
                break;
        }
        return resId;
    }

}
