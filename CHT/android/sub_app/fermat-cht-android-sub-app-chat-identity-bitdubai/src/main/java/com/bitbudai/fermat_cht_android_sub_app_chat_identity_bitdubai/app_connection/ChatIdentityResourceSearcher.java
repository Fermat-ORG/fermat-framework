package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.app_connection;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_cht_android_sub_app_chat_identity_bitdubai.R;

/**
 * Created by mati on 2016.07.02..
 */
public class ChatIdentityResourceSearcher extends ResourceSearcher {

    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int resId = 0;

        switch (fermatDrawable.getId()){
            case 7:
                resId = R.drawable.cht_ic_back_buttom;;
                break;
            case 1:
                resId = R.drawable.cht_ic_menu_help;
                break;
        }

        return DrawableUtils.resToDrawable(context, resId);
    }
}
