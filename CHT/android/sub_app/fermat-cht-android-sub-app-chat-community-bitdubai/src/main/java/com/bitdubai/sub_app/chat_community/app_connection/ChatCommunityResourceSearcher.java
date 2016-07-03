package com.bitdubai.sub_app.chat_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.sub_app.chat_community.R;

/**
 * Created by mati on 2016.07.02..
 */
public class ChatCommunityResourceSearcher extends ResourceSearcher {

    public int obtainResDrawable(Context context,int id){
        int resId = 0;
        switch (id){
            case 1:
                resId = R.drawable.cht_help_icon;
                break;
            case 2:
                resId = R.drawable.cht_comm_ubication_icon;
                break;
            case 3:
                resId = R.drawable.cht_comm_search_icon;
                break;
        }
        return resId;
    }
}
