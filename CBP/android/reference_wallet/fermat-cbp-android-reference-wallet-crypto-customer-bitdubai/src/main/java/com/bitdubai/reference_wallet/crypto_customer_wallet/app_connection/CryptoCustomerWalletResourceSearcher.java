package com.bitdubai.reference_wallet.crypto_customer_wallet.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoCustomerWalletResourceSearcher extends ResourceSearcher {

    @Override
    public int obtainResDrawable(Context context,int id){
        int resId = 0;
        switch (id) {
            case FragmentsCommons.CONTRACT_HISTORY_FILTER_OPTION_MENU_ID:
                resId = R.drawable.ccw_action_filters;
                break;
            default:
                resId = 0;
                break;
        }
        return resId;
    }
}
