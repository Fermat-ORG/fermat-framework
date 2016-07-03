package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.app_connection;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

/**
 * Created by Matias furszyfer on 2016.07.02..
 */
public class ChatResourceSearcher extends ResourceSearcher {

    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int resId = 0;

        switch (fermatDrawable.getId()){
            case 1:
                resId = R.drawable.cht_help_icon;
                break;
            case 2:
                resId = R.drawable.cht_ic_action_search;
                break;
            case 3:
                resId = R.drawable.cht_people_conections;
                break;
            case 4:
                resId = R.drawable.cht_notifications;
                break;
        }

        return DrawableUtils.resToDrawable(context, resId);
    }
}
