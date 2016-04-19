package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.header;

import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;

/**
 * Created by mati on 2015.12.15..
 */
public class LossProtectedWalletHeaderPainter implements HeaderViewPainter {




    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        LossProtectedWalletHeaderFactory.constructHeader(viewGroup);
    }
}
