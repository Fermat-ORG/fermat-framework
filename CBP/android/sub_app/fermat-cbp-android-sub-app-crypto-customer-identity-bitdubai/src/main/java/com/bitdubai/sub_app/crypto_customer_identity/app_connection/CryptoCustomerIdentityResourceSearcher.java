package com.bitdubai.sub_app.crypto_customer_identity.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoCustomerIdentityResourceSearcher extends ResourceSearcher {

    @Override
    public int obtainResDrawable(Context context, int id) {
        switch (id) {
            case FragmentsCommons.ADD_IDENTITY_OPTION_MENU_ID:
                return R.drawable.new_identity_button;

            case FragmentsCommons.HELP_OPTION_MENU_ID:
                return R.drawable.help_button;

            case FragmentsCommons.CREATE_IDENTITY_MENU_ID:
                return R.drawable.save_changes_button;

            case FragmentsCommons.GEOLOCATION_SETTINGS_OPTION_MENU_ID:
                return R.drawable.cbp_id_geolocation_icon;

            default:
                return 0;
        }
    }
}
