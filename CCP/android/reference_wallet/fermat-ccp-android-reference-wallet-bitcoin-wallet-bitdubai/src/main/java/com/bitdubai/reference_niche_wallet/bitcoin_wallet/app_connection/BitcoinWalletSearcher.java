package com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection;

import android.content.Context;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.core.ResourceSearcher;

/**
 * Created by nerio on 2/7/2016.
 */
public class BitcoinWalletSearcher extends ResourceSearcher {

    public int obtainResDrawable(Context context,int id){
        int resId = 0;
        switch (id){
            case 1:

                break;
            case 2:
                resId = R.drawable.help_icon;
                break;
            case 4:
                resId = R.drawable.sendbtc_icon;
                break;
            case 3:
                resId = R.drawable.icon_comunity_action_bar;
                break;

            case 5:
                resId = R.drawable.add_contact_icon;
                break;
            case 6:
                resId = R.drawable.btc_wallet_send_icon_action_bar;
                break;
        }
        return resId;
    }
}
