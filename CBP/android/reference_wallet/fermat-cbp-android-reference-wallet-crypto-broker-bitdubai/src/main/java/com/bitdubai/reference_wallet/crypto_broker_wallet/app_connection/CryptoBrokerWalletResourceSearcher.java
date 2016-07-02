package com.bitdubai.reference_wallet.crypto_broker_wallet.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoBrokerWalletResourceSearcher extends ResourceSearcher {

    @Override
    public int obtainResDrawable(Context context, int id) {
        switch (id) {
            case FragmentsCommons.CONTRACT_HISTORY_FILTER_OPTION_MENU_ID:
                return R.drawable.ic_action_filters;
            default:
                return 0;
        }
    }
}
