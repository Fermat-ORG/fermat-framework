package com.bitdubai.sub_app.crypto_customer_community.app_connection;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoCustomerCommunityResourceSearcher extends ResourceSearcher {


    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int resId = 0;
        switch (fermatDrawable.getId()) {
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
        return DrawableUtils.resToDrawable(context, resId);
    }
}
