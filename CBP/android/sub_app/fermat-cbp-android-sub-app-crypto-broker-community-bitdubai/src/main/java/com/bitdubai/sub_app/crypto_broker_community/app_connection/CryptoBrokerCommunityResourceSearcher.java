package com.bitdubai.sub_app.crypto_broker_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoBrokerCommunityResourceSearcher extends ResourceSearcher {

    @Override
    public int obtainResDrawable(Context context, int id) {
        switch (id) {
            case FragmentsCommons.HELP_OPTION_MENU_ID:
                return R.drawable.interrogacion_blanco;

            case FragmentsCommons.LOCATION_FILTER_OPTION_MENU_ID:
                return R.drawable.localizacion_blanco;

            case FragmentsCommons.SEARCH_FILTER_OPTION_MENU_ID:
                return R.drawable.lupa;

            default:
                return 0;
        }
    }

}
