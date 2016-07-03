package org.fermat.fermat_dap_android_wallet_redeem_point.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

/**
 * Created by nerio on 2/7/2016.
 */
public class WalletRedeemPointSearcher extends ResourceSearcher {


    public int obtainResDrawable(Context context,int id){
        int resId = 0;

        switch (id){
            case 2:
                resId = R.drawable.ic_menu_search;
                break;
        }
        return resId;
    }
}
