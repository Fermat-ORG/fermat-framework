package com.bitdubai.reference_niche_wallet.fermat_wallet.app_connection;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;

/**
 * Created by nerio on 2/7/2016.
 */
public class FermatWalletSearcher extends ResourceSearcher {

    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int resId = 0;

        switch (fermatDrawable.getId()){
            case 1:
                resId = R.drawable.background_navigation_drawer;
                break;
            case 2:
                resId = R.drawable.fw_help_icon;
                break;
            case 4:
                resId = R.drawable.fmt_icon_withdrawall;
                break;
            case 3:
                resId = R.drawable.fw_withdrawall_icon;
                break;

        }
        return DrawableUtils.resToDrawable(context, resId);
    }
}
