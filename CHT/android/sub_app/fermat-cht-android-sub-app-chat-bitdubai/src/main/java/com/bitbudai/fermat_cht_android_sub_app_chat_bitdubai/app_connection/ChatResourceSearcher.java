package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

/**
 * Created by Matias furszyfer on 2016.07.02..
 */
public class ChatResourceSearcher extends ResourceSearcher {


    public int obtainResDrawable(Context context, int id) {
        int resId = 0;

        switch (id) {
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
        return resId;
    }
}
