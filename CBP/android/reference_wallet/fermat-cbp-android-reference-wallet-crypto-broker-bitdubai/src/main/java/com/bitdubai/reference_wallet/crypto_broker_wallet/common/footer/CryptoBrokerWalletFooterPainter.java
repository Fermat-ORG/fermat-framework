package com.bitdubai.reference_wallet.crypto_broker_wallet.common.footer;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by Nelson Ramirez
 *
 * @since 17/12/15.
 */
public class CryptoBrokerWalletFooterPainter implements FooterViewPainter {
    @Override
    public ViewGroup addFooterViewContainer(LayoutInflater layoutInflater, ViewGroup footer_container) {
        return (ViewGroup) layoutInflater.inflate(R.layout.fragment_main, footer_container, true);
    }

    @Override
    public View addNavigationViewFooterElementVisible(LayoutInflater layoutInflater, FrameLayout slide_container) {
        return layoutInflater.inflate(R.layout.cbw_footer_view_bar, slide_container, true);
    }

    @Override
    public Drawable addBodyBackground() {
        return null;
    }

    @Override
    public int addBodyBackgroundColor() {
        return Color.parseColor("#AAAAAA");
    }
}
