package com.bitdubai.sub_app.crypto_customer_identity.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoCustomerIdentityResourceSearcher extends ResourceSearcher {


    public int obtainResDrawable(Context context, int id) {
        int resId = 0;
        switch (id) {
            case FragmentsCommons.ADD_IDENTITY_OPTION_MENU_ID:
                resId = R.drawable.new_identity_button;
                break;
            case FragmentsCommons.HELP_OPTION_MENU_ID:
                resId = R.drawable.help_button;
                break;
            case FragmentsCommons.CREATE_IDENTITY_MENU_ID:
                resId = R.drawable.save_changes_button;
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
