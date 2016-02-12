package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.header;

import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;

/**
 * Created by mati on 2015.12.15..
 */
public class BitcoinWalletHeaderPainter implements HeaderViewPainter {




    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        BitcoinWalletHeaderFactory.constructHeader(viewGroup);
    }
}
