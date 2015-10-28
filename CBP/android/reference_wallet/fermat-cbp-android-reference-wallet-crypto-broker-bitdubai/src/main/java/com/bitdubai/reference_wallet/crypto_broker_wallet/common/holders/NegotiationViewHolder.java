package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.NegotiationBasicInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by nelson on 21/10/15.
 */
public class NegotiationViewHolder extends ChildViewHolder {
    public ImageView customerImage;
    public FermatTextView customerName;
    public FermatTextView merchandiseAmount;
    public FermatTextView merchandise;
    public FermatTextView typeOfPayment;
    public FermatTextView exchangeRateAmount;
    public FermatTextView paymentCurrency;
    public FermatTextView lastUpdateDate;
    public FermatTextView status;
    private Resources res;
    private View itemView;


    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public NegotiationViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();

        customerImage = (ImageView) itemView.findViewById(R.id.cbw_customer_image);
        customerName = (FermatTextView) itemView.findViewById(R.id.cbw_customer_name);
        merchandiseAmount = (FermatTextView) itemView.findViewById(R.id.cbw_merchandise_amount);
        merchandise = (FermatTextView) itemView.findViewById(R.id.cbw_merchandise);
        typeOfPayment = (FermatTextView) itemView.findViewById(R.id.cbw_type_of_payment);
        exchangeRateAmount = (FermatTextView) itemView.findViewById(R.id.cbw_exchange_rate_amount);
        paymentCurrency = (FermatTextView) itemView.findViewById(R.id.cbw_payment_currency);
        lastUpdateDate = (FermatTextView) itemView.findViewById(R.id.cbw_update_date);
        status = (FermatTextView) itemView.findViewById(R.id.cbw_negotiation_status);
    }

    public void bind(NegotiationBasicInformation itemInfo) {

        NegotiationStatus negotiationStatus = itemInfo.getStatus();
        itemView.setBackgroundColor(getStatusBackgroundColor(negotiationStatus));
        status.setText(getStatusStringRes(negotiationStatus));

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
        merchandiseAmount.setText(decimalFormat.format(itemInfo.getAmount()));
        exchangeRateAmount.setText(decimalFormat.format(itemInfo.getExchangeRateAmount()));

        CharSequence date = DateFormat.format("dd MMM yyyy", itemInfo.getLastUpdate());
        lastUpdateDate.setText(date);

        customerName.setText(itemInfo.getCryptoCustomerAlias());
        merchandise.setText(itemInfo.getMerchandise());
        typeOfPayment.setText(itemInfo.getTypeOfPayment());
        paymentCurrency.setText(itemInfo.getPaymentCurrency());
        customerImage.setImageDrawable(getImgDrawable(itemInfo.getCryptoCustomerImage()));
    }

    private int getStatusBackgroundColor(NegotiationStatus status) {
        if (status == NegotiationStatus.WAITING_FOR_BROKER || status == NegotiationStatus.SENT_TO_BROKER)
            return res.getColor(R.color.waiting_for_broker_list_item_background);

        if (status == NegotiationStatus.WAITING_FOR_CUSTOMER || status == NegotiationStatus.SENT_TO_CUSTOMER)
            return res.getColor(R.color.waiting_for_customer_list_item_background);

        if (status == NegotiationStatus.CLOSED)
            return res.getColor(R.color.negotiation_closed_list_item_background);

        return res.getColor(R.color.negotiation_cancelled_list_item_background);
    }

    private int getStatusStringRes(NegotiationStatus status) {
        if (status == NegotiationStatus.WAITING_FOR_BROKER || status == NegotiationStatus.SENT_TO_BROKER)
            return R.string.waiting_for_you;

        if (status == NegotiationStatus.WAITING_FOR_CUSTOMER || status == NegotiationStatus.SENT_TO_CUSTOMER)
            return R.string.waiting_for_the_customer;

        if (status == NegotiationStatus.CLOSED)
            return R.string.negotiation_closed;

        return R.string.negotiation_cancelled;
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }
}
