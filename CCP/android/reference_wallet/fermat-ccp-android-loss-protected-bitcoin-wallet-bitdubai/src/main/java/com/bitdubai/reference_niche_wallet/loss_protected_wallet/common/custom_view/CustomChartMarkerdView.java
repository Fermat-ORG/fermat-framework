package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.custom_view;

import android.content.Context;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.Wallet;
import com.bitdubai.fermat_ccp_api.all_definition.util.WalletUtils;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

/**
 * Created by root on 19/05/16.
 */
public class CustomChartMarkerdView extends MarkerView {

    private TextView marker_element;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomChartMarkerdView(Context context, int layoutResource) {
        super(context, layoutResource);

        marker_element = (TextView) findViewById(R.id.marker_element);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry)
        {
            CandleEntry ce =(CandleEntry) e;

            marker_element.setText("U$D " + Utils.formatNumber(ce.getHigh(), 2, true));
        }else
        {
            marker_element.setText("U$D " + Utils.formatNumber(e.getVal(), 2, true));
        }
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() /2);    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }
}
