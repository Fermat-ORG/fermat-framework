package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_cht_android_sub_app_chat_identity_bitdubai.R;

/**
 * Created by mati on 2016.07.02..
 */
public class ChatIdentityResourceSearcher extends ResourceSearcher {

    public int obtainResDrawable(Context context, int id) {
        int resId = 0;

        switch (id) {
            case 7:
                resId = R.drawable.cht_ic_back_buttom;
                ;
                break;
            case 1:
                resId = R.drawable.cht_ic_menu_help;
                break;
        }
        return resId;
    }
}
