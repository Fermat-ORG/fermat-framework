package org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.R;

/**
 * Created by nerio on 2/7/2016.
 */
public class AssetIssuerSearcher extends ResourceSearcher {

    public int obtainResDrawable(Context context,int id){
        int resId = 0;
        switch (id){
            case 1:
                resId = R.drawable.ic_geolacation;
                break;
        }
        return resId;
    }
}
