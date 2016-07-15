package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

import static com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus.*;


/**
 * Created by nelson on 28/10/15.
 */
public class NegotiationInformationViewHolder extends ChildViewHolder {
    public final ImageView customerImage;
    public final FermatTextView customerName;
    public final FermatTextView exchangeRateUnit;
    public final FermatTextView buyingText;
    public final FermatTextView lastUpdateDate;
    public final FermatTextView status;
    public final ProgressBar sendingProgressBar;
    private Resources res;
    private View itemView;
    NumberFormat numberFormat= DecimalFormat.getInstance();

    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public NegotiationInformationViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();

        sendingProgressBar = (ProgressBar) itemView.findViewById(R.id.cbw_sending_progress_bar);
        status = (FermatTextView) itemView.findViewById(R.id.cbw_negotiation_status);
        customerImage = (ImageView) itemView.findViewById(R.id.cbw_customer_image);
        customerName = (FermatTextView) itemView.findViewById(R.id.cbw_customer_name);
        lastUpdateDate = (FermatTextView) itemView.findViewById(R.id.cbw_update_date);
        exchangeRateUnit = (FermatTextView) itemView.findViewById(R.id.cbw_merchandise_unit);
        buyingText = (FermatTextView) itemView.findViewById(R.id.cbw_buying_text);
    }

    public void bind(CustomerBrokerNegotiationInformation itemInfo) {

        CharSequence date = DateFormat.format("dd MMM yyyy", itemInfo.getLastNegotiationUpdateDate());
        lastUpdateDate.setText(date);

        ActorIdentity customer = itemInfo.getCustomer();
        customerImage.setImageDrawable(getImgDrawable(customer.getProfileImage()));
        customerName.setText(customer.getAlias());

        NegotiationStatus negotiationStatus = itemInfo.getStatus();
        itemView.setBackgroundColor(getStatusBackgroundColor(negotiationStatus));
        status.setText(getStatusStringRes(negotiationStatus));

        int visibility = (negotiationStatus == SENT_TO_CUSTOMER) ? View.VISIBLE : View.INVISIBLE;
        sendingProgressBar.setVisibility(visibility);

        Map<ClauseType, String> negotiationSummary = itemInfo.getNegotiationSummary();

        String merchandise = negotiationSummary.get(ClauseType.CUSTOMER_CURRENCY);

        if(CryptoCurrency.codeExists(merchandise)){
            numberFormat.setMaximumFractionDigits(8);
        }else{
            numberFormat.setMaximumFractionDigits(2);
        }
        String exchangeRate = fixFormat(negotiationSummary.get(ClauseType.EXCHANGE_RATE));

        String paymentCurrency = negotiationSummary.get(ClauseType.BROKER_CURRENCY);

        if(CryptoCurrency.codeExists(paymentCurrency)){
            numberFormat.setMaximumFractionDigits(8);
        }else{
            numberFormat.setMaximumFractionDigits(2);
        }

        String merchandiseAmount = fixFormat(negotiationSummary.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY));


        exchangeRateUnit.setText(String.format("1 %1$s @ %2$s %3$s", merchandise, exchangeRate, paymentCurrency));
        buyingText.setText(String.format("Buying %1$s %2$s", merchandiseAmount, merchandise));
    }

    private int getStatusBackgroundColor(NegotiationStatus status) {
        if (status == WAITING_FOR_BROKER || status == SENT_TO_BROKER)
            return res.getColor(R.color.waiting_for_broker_list_item_background);

        if (status == WAITING_FOR_CUSTOMER || status == SENT_TO_CUSTOMER)
            return res.getColor(R.color.waiting_for_customer_list_item_background);

        if (status == WAITING_FOR_CLOSING)
            return res.getColor(R.color.waiting_for_closing_list_item_background);

        if (status == CLOSED)
            return res.getColor(R.color.negotiation_closed_list_item_background);

        return res.getColor(R.color.negotiation_cancelled_list_item_background);
    }

    private int getStatusStringRes(NegotiationStatus status) {
        if (status == WAITING_FOR_BROKER || status == SENT_TO_BROKER)
            return R.string.waiting_for_you;

        if (status == WAITING_FOR_CUSTOMER)
            return R.string.waiting_for_the_customer;

        if (status == WAITING_FOR_CLOSING)
            return R.string.waiting_for_closing;

        return R.string.sending_to_the_customer;
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }

    private String fixFormat(String value){

        try {
            return String.valueOf(new BigDecimal(String.valueOf(numberFormat.parse(numberFormat.format(Double.valueOf(value))))));
        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }

    }


}
