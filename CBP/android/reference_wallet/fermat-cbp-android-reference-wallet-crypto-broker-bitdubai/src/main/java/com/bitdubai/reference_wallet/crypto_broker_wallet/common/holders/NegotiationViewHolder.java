package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.NegotiationBasicInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by nelson on 21/10/15.
 */
public class NegotiationViewHolder extends ChildViewHolder {
    private Resources res;

    public ImageView customerImage;
    public FermatTextView customerName;
    public FermatTextView merchandiseAmount;
    public FermatTextView merchandise;
    public FermatTextView typeOfPayment;
    public FermatTextView merchandiseCurrency;
    public FermatTextView exchangeRateAmount;
    public FermatTextView paymentCurrency;


    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public NegotiationViewHolder(View itemView) {
        super(itemView);

        res = itemView.getResources();

        customerImage = (ImageView) itemView.findViewById(R.id.cbw_customer_image);
        customerName = (FermatTextView) itemView.findViewById(R.id.cbw_customer_name);
        merchandiseAmount = (FermatTextView) itemView.findViewById(R.id.cbw_merchandise_amount);
        merchandise = (FermatTextView) itemView.findViewById(R.id.cbw_merchandise);
        typeOfPayment = (FermatTextView) itemView.findViewById(R.id.cbw_type_of_payment);
        merchandiseCurrency = (FermatTextView) itemView.findViewById(R.id.cbw_merchandise_currency);
        exchangeRateAmount = (FermatTextView) itemView.findViewById(R.id.cbw_exchange_rate_amount);
        paymentCurrency = (FermatTextView) itemView.findViewById(R.id.cbw_payment_currency);
    }

    public void bind(NegotiationBasicInformation itemInfo) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();

        merchandiseAmount.setText(df.format(itemInfo.getAmount()));
        exchangeRateAmount.setText(df.format(itemInfo.getExchangeRateAmount()));

        customerName.setText(itemInfo.getCryptoCustomerAlias());
        merchandise.setText(itemInfo.getMerchandise());
        typeOfPayment.setText(itemInfo.getTypeOfPayment());
        merchandiseCurrency.setText(itemInfo.getMerchandise());
        paymentCurrency.setText(itemInfo.getPaymentCurrency());

        byte[] customerImg = itemInfo.getCryptoCustomerImage();
        if (customerImg != null && customerImg.length > 0) {
            this.customerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, customerImg));
        } else {
            this.customerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, R.drawable.person));
        }
    }
}
