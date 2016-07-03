package com.bitdubai.reference_wallet.crypto_broker_wallet.app_connection;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class CryptoBrokerWalletResourceSearcher extends ResourceSearcher {


    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int res = 0;
        switch (fermatDrawable.getId()) {
            case FragmentsCommons.CONTRACT_HISTORY_FILTER_OPTION_MENU_ID:
                res = R.drawable.ic_action_filters;
                break;
            default:
                res = 0;
                break;
        }
        return DrawableUtils.resToDrawable(context,res);
    }
}
