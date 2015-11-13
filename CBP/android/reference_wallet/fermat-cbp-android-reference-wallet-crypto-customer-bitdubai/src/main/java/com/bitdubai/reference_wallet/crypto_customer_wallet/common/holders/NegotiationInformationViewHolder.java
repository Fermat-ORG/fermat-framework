package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.util.Map;

/**
 * Created by nelson on 28/10/15.
 */
public class NegotiationInformationViewHolder extends ChildViewHolder {
    public ImageView brokerImage;
    public FermatTextView brokerName;
    public FermatTextView merchandiseAmount;
    public FermatTextView merchandise;
    public FermatTextView paymentMethod;
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
    public NegotiationInformationViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();

        brokerImage = (ImageView) itemView.findViewById(R.id.ccw_broker_image);
        brokerName = (FermatTextView) itemView.findViewById(R.id.ccw_broker_name);
        merchandiseAmount = (FermatTextView) itemView.findViewById(R.id.ccw_merchandise_amount);
        merchandise = (FermatTextView) itemView.findViewById(R.id.ccw_merchandise);
        paymentMethod = (FermatTextView) itemView.findViewById(R.id.ccw_type_of_payment);
        exchangeRateAmount = (FermatTextView) itemView.findViewById(R.id.ccw_exchange_rate_amount);
        paymentCurrency = (FermatTextView) itemView.findViewById(R.id.ccw_payment_currency);
        lastUpdateDate = (FermatTextView) itemView.findViewById(R.id.ccw_update_date);
        status = (FermatTextView) itemView.findViewById(R.id.ccw_negotiation_status);
    }

    public void bind(CustomerBrokerNegotiationInformation itemInfo) {

        CharSequence date = DateFormat.format("dd MMM yyyy", itemInfo.getLastUpdate());
        lastUpdateDate.setText(date);

        ActorIdentity broker = itemInfo.getBroker();
        brokerImage.setImageDrawable(getImgDrawable(broker.getProfileImage()));
        brokerName.setText(broker.getAlias());

        NegotiationStatus negotiationStatus = itemInfo.getStatus();
        itemView.setBackgroundColor(getStatusBackgroundColor(negotiationStatus));
        status.setText(getStatusStringRes(negotiationStatus));

        Map<ClauseType, String> negotiationSummary = itemInfo.getNegotiationSummary();
        merchandiseAmount.setText(negotiationSummary.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY));
        exchangeRateAmount.setText(negotiationSummary.get(ClauseType.EXCHANGE_RATE));
        merchandise.setText(negotiationSummary.get(ClauseType.CUSTOMER_CURRENCY));
        paymentMethod.setText(negotiationSummary.get(ClauseType.BROKER_PAYMENT_METHOD));
        paymentCurrency.setText(negotiationSummary.get(ClauseType.BROKER_CURRENCY));
    }

    private int getStatusBackgroundColor(NegotiationStatus status) {
        if (status == NegotiationStatus.WAITING_FOR_CUSTOMER || status == NegotiationStatus.SENT_TO_CUSTOMER)
            return res.getColor(R.color.waiting_for_customer_list_item_background);

        if (status == NegotiationStatus.WAITING_FOR_BROKER || status == NegotiationStatus.SENT_TO_BROKER)
            return res.getColor(R.color.waiting_for_broker_list_item_background);

        if (status == NegotiationStatus.CLOSED)
            return res.getColor(R.color.negotiation_closed_list_item_background);

        return res.getColor(R.color.negotiation_cancelled_list_item_background);
    }

    private int getStatusStringRes(NegotiationStatus status) {
        if (status == NegotiationStatus.WAITING_FOR_CUSTOMER || status == NegotiationStatus.SENT_TO_CUSTOMER)
            return R.string.waiting_for_you;

        if (status == NegotiationStatus.WAITING_FOR_BROKER || status == NegotiationStatus.SENT_TO_BROKER)
            return R.string.waiting_for_broker;

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
