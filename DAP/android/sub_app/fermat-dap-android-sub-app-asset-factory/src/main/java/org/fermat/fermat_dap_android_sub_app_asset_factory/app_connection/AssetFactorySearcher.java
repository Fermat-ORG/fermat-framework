package org.fermat.fermat_dap_android_sub_app_asset_factory.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;

/**
 * Created by nerio on 2/7/2016.
 */
public class AssetFactorySearcher extends ResourceSearcher {

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
