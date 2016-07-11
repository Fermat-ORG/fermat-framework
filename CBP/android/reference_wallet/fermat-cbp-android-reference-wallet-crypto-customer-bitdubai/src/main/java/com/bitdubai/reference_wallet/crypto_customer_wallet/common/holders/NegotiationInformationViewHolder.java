package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.util.Map;

import static com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus.SENT_TO_BROKER;


/**
 * Created by nelson on 28/10/15.
 */
public class NegotiationInformationViewHolder extends ChildViewHolder {
    public final ImageView brokerImage;
    public final FermatTextView brokerName;
    public final FermatTextView sellingText;
    public final FermatTextView paymentMethod;
    public final FermatTextView lastUpdateDate;
    public final FermatTextView status;
    public final ProgressBar sendingProgressBar;
    private final FermatTextView exchangeRateUnit;
    private final Resources res;
    private final View itemView;


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
        sellingText = (FermatTextView) itemView.findViewById(R.id.ccw_selling_text);
        paymentMethod = (FermatTextView) itemView.findViewById(R.id.ccw_type_of_payment);
        lastUpdateDate = (FermatTextView) itemView.findViewById(R.id.ccw_update_date);
        status = (FermatTextView) itemView.findViewById(R.id.ccw_negotiation_status);
        sendingProgressBar = (ProgressBar) itemView.findViewById(R.id.ccw_sending_progress_bar);
        exchangeRateUnit = (FermatTextView) itemView.findViewById(R.id.ccw_merchandise_unit);
    }

    public void bind(CustomerBrokerNegotiationInformation itemInfo) {

        CharSequence date = DateFormat.format("dd MMM yyyy", itemInfo.getLastNegotiationUpdateDate());
        lastUpdateDate.setText(date);


        ActorIdentity broker = itemInfo.getBroker();
        brokerImage.setImageDrawable(getImgDrawable(broker.getProfileImage()));
        brokerName.setText(broker.getAlias());

        NegotiationStatus negotiationStatus = itemInfo.getStatus();

        itemView.setBackgroundColor(getStatusBackgroundColor(negotiationStatus));
        status.setText(getStatusStringRes(negotiationStatus));

        int visibility = (negotiationStatus == SENT_TO_BROKER) ? View.VISIBLE : View.INVISIBLE;
        sendingProgressBar.setVisibility(visibility);

        Map<ClauseType, String> negotiationSummary = itemInfo.getNegotiationSummary();
        String exchangeRate = negotiationSummary.get(ClauseType.EXCHANGE_RATE);
        String merchandise = negotiationSummary.get(ClauseType.CUSTOMER_CURRENCY);
        String paymentCurrency = negotiationSummary.get(ClauseType.BROKER_CURRENCY);
        String merchandiseAmount = negotiationSummary.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY);

        exchangeRateUnit.setText(String.format("1 %1$s @ %2$s %3$s", merchandise, exchangeRate, paymentCurrency));
        sellingText.setText(String.format("Selling %1$s %2$s", merchandiseAmount, merchandise));
    }

    private int getStatusBackgroundColor(NegotiationStatus status) {
        if (status == NegotiationStatus.WAITING_FOR_CUSTOMER || status == NegotiationStatus.SENT_TO_CUSTOMER)
            return res.getColor(R.color.waiting_for_customer_list_item_background);

        if (status == NegotiationStatus.WAITING_FOR_BROKER || status == NegotiationStatus.SENT_TO_BROKER)
            return res.getColor(R.color.waiting_for_broker_list_item_background);

        if (status == NegotiationStatus.WAITING_FOR_CLOSING)
            return res.getColor(R.color.waiting_for_closing_list_item_background);

        if (status == NegotiationStatus.CLOSED)
            return res.getColor(R.color.negotiation_closed_list_item_background);

        return res.getColor(R.color.negotiation_cancelled_list_item_background);

    }

    protected int getStatusStringRes(NegotiationStatus status) {
        if (status == NegotiationStatus.WAITING_FOR_CUSTOMER || status == NegotiationStatus.SENT_TO_CUSTOMER)
            return R.string.waiting_for_you;

        if (status == NegotiationStatus.WAITING_FOR_BROKER)
            return R.string.waiting_for_broker;

        if (status == NegotiationStatus.WAITING_FOR_CLOSING)
            return R.string.waiting_for_closing;

        return R.string.sending_to_the_broker;
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }
}
