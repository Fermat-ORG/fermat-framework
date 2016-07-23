package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.BrokerExchangeRatesAdapter;

/**
 * View Holder for the {@link com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.BrokerListAdapter}
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 24/11/2015
 */
public class BrokerListViewHolder extends FermatViewHolder {
    private Resources res;
    private BrokerExchangeRatesAdapter quotesAdapter;

    public ImageView brokerImage;
    public FermatTextView brokerName;
    public FermatTextView merchandiseToSell;
    public RecyclerView exchangeRates;

    /**
     * Create a view holder for the {@link com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.BrokerListAdapter}
     *
     * @param itemView the item view
     */
    public BrokerListViewHolder(View itemView) {
        super(itemView);
        res = itemView.getResources();

        brokerImage = (ImageView) itemView.findViewById(R.id.ccw_broker_image);
        brokerName = (FermatTextView) itemView.findViewById(R.id.ccw_broker_name);
        merchandiseToSell = (FermatTextView) itemView.findViewById(R.id.ccw_merchandise_to_sell);
        exchangeRates = (RecyclerView) itemView.findViewById(R.id.ccw_broker_exchange_rates);


        final Context context = itemView.getContext();
        quotesAdapter = new BrokerExchangeRatesAdapter(context);
        exchangeRates.setAdapter(quotesAdapter);
        exchangeRates.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    public void bind(BrokerIdentityBusinessInfo data) {
        brokerName.setText(data.getAlias());

        String currency = data.getMerchandise().getCode();
        String text = res.getString(R.string.ccw_selling_merchandise, currency);
        merchandiseToSell.setText(text);

        final byte[] profileImage = data.getProfileImage();

        Drawable brokerImg = getImgDrawable(profileImage);

        brokerImage.setImageDrawable(brokerImg);

        loadDataInAdapter(data);
    }

    private void loadDataInAdapter(BrokerIdentityBusinessInfo data) {
        if (quotesAdapter.getItemCount() == 0) {
            quotesAdapter.changeDataSet(data.getQuotes());
        }
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.ic_profile_male);
    }
}
