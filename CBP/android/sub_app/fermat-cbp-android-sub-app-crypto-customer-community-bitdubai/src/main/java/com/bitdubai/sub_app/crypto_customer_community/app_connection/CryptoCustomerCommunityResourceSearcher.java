package com.bitdubai.sub_app.crypto_customer_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoCustomerCommunityResourceSearcher extends ResourceSearcher {

    @Override
    public int obtainResDrawable(Context context, int id) {
        switch (id) {
            case FragmentsCommons.HELP_OPTION_MENU_ID:
                return R.drawable.ccc_help_icon;

            case FragmentsCommons.LOCATION_FILTER_OPTION_MENU_ID:
                return R.drawable.ccc_location_icon_white;

            case FragmentsCommons.SEARCH_FILTER_OPTION_MENU_ID:
                return R.drawable.ccc_search_icon_withe;

            case FragmentsCommons.CCC_BACKGROUND_TAB_ID:
                return R.drawable.ccc_action_bar_gradient_colors;
            default:
                return 0;
        }
    }
}
