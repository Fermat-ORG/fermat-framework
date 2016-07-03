package com.bitdubai.sub_app.intra_user_identity.app_connection;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.sub_app.intra_user_identity.R;

/**
 * Created by nerio on 2/7/2016.
 */
public class CryptoWalletUserIdentitySearcher extends ResourceSearcher {

    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int resId = 0;

        switch (fermatDrawable.getId()){
            case 1:
                resId = R.drawable.help_icon;
                break;
            case 2:
                resId = R.drawable.cht_id_geolocation_icon;
                break;
        }
        return DrawableUtils.resToDrawable(context, resId);
    }
}
