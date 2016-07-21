package com.bitdubai.sub_app.intra_user_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by nerio on 2/7/2016.
 */
public class CryptoWalletUserSearcher extends ResourceSearcher {

    @Override
    public int obtainResDrawable(Context context,int id){
        int resId = 0;
        switch (id){
            case 1:
                resId = R.drawable.search_icon;
                break;
            case 2:
                resId = R.drawable.geolocalitation_icon;
                break;
            case 3:
                resId = R.drawable.help_icon;
                break;

        }
        return resId;
    }
}
