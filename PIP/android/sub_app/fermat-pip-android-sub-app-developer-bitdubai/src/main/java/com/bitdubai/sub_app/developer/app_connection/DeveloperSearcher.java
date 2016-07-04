package com.bitdubai.sub_app.developer.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.sub_app.developer.R;

/**
 * Created by nerio on 2/7/2016.
 */
public class DeveloperSearcher extends ResourceSearcher {

    @Override
    public int obtainResDrawable(Context context,int id){
        int resId = 0;

        switch (id){
            case 1:
                resId = R.drawable.ic_menu_search;
                break;
        }
        return resId;
    }
}
