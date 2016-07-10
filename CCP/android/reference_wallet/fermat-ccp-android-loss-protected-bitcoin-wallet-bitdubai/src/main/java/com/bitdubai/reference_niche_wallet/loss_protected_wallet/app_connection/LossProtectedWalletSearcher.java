package com.bitdubai.reference_niche_wallet.loss_protected_wallet.app_connection;

import android.content.Context;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.core.ResourceSearcher;

/**
 * Created by nerio on 2/7/2016.
 */
public class LossProtectedWalletSearcher extends ResourceSearcher {


    public int obtainResDrawable(Context context,int id){
        int resId = 0;

        switch (id){
            case 1:

                break;
            case 2:
                resId = R.drawable.loos_help_icon;
                break;
            case 4:
                resId = R.drawable.sendbtc_icon;
                break;
            case 3:

                break;

        }
        return resId;
    }
}
