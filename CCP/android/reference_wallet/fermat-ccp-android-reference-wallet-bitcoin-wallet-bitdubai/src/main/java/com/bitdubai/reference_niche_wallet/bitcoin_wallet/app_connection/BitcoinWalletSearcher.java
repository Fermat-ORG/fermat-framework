package com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;

/**
 * Created by nerio on 2/7/2016.
 */
public class BitcoinWalletSearcher extends ResourceSearcher {

    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int resId = 0;

        switch (fermatDrawable.getId()){
            case 1:

                break;
            case 2:
                resId = R.drawable.help_icon;
                break;
            case 4:
                resId = R.drawable.sendbtc_icon;
                break;
            case 3:

                break;

        }
        return DrawableUtils.resToDrawable(context, resId);
    }
}
