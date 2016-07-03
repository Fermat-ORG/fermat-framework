package com.bitdubai.sub_app.crypto_broker_community.app_connection;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoBrokerCommunityResourceSearcher extends ResourceSearcher {


    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int resId = 0;
        switch (fermatDrawable.getId()) {
            case FragmentsCommons.HELP_OPTION_MENU_ID:
                resId = R.drawable.interrogacion_blanco;
                break;
            case FragmentsCommons.LOCATION_FILTER_OPTION_MENU_ID:
                resId = R.drawable.localizacion_blanco;
                break;
            case FragmentsCommons.SEARCH_FILTER_OPTION_MENU_ID:
                resId = R.drawable.lupa;
                break;
            case FragmentsCommons.CBC_BACKGROUND_TAB_ID:
                resId = R.drawable.cbc_action_bar_gradient_colors;
                break;
            default:
                resId = 0;
                break;
        }
        return DrawableUtils.resToDrawable(context, resId);
    }

}
