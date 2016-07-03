package org.fermat.fermat_dap_android_sub_app_redeem_point_community.app_connection;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;

/**
 * Created by nerio on 2/7/2016.
 */
public class RedeemCommunitySearcher extends ResourceSearcher {

    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int resId = 0;

        switch (fermatDrawable.getId()){
            case 1:
                resId = R.drawable.ic_geolacation;
                break;
            case 2:
                resId = R.drawable.ic_menu_search;
                break;
        }

        return DrawableUtils.resToDrawable(context, resId);
    }
}
