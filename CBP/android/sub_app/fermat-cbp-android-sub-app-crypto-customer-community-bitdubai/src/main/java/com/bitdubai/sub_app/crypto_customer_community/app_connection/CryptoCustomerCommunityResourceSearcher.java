package com.bitdubai.sub_app.crypto_customer_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoCustomerCommunityResourceSearcher extends ResourceSearcher {

    public int obtainResDrawable(Context context, int id) {
        int resId;
        switch (id) {
            case FragmentsCommons.HELP_OPTION_MENU_ID:
                resId = R.drawable.ccc_help_icon;
                break;
            case FragmentsCommons.LOCATION_FILTER_OPTION_MENU_ID:
                resId = R.drawable.ccc_location_icon_white;
                break;
            case FragmentsCommons.SEARCH_FILTER_OPTION_MENU_ID:
                resId = R.drawable.ccc_search_icon_withe;
                break;
            case FragmentsCommons.CCC_BACKGROUND_TAB_ID:
                resId = R.drawable.ccc_action_bar_gradient_colors;
                break;
            default:
                resId = 0;
                break;
        }
        return resId;
    }
}
