package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.common.header;

import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;

/**
 * Created by Nerio on 17/12/15.
 */
public class WalletRedeemPointHeaderPainter implements HeaderViewPainter {

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        WalletAssetUserHeaderFactory.constructHeader(viewGroup);
    }
}