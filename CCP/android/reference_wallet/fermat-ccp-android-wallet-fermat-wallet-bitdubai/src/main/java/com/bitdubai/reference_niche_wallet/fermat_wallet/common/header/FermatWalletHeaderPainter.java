package com.bitdubai.reference_niche_wallet.fermat_wallet.common.header;

import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;

/**
 * Created by mati on 2015.12.15..
 */
public class FermatWalletHeaderPainter implements HeaderViewPainter {




    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        FermatWalletHeaderFactory.constructHeader(viewGroup);
    }
}
