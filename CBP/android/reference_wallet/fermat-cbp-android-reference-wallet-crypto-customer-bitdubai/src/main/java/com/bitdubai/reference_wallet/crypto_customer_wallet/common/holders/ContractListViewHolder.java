package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by nelson on 21/10/15.
 */
public class ContractListViewHolder extends FermatViewHolder {
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private Resources res;
    private View itemView;

    public ImageView brokerImage;
    public FermatTextView brokerName;
    public FermatTextView soldQuantityAndCurrency;
    public FermatTextView exchangeRateAmountAndCurrency;
    public FermatTextView lastUpdateDate;
    public FermatTextView statusHistoryCustomer;

    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public ContractListViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();

        brokerImage = (ImageView) itemView.findViewById(R.id.ccw_customer_image);
        brokerName = (FermatTextView) itemView.findViewById(R.id.ccw_customer_name);
        soldQuantityAndCurrency = (FermatTextView) itemView.findViewById(R.id.ccw_sold_quantity_and_currency);
        exchangeRateAmountAndCurrency = (FermatTextView) itemView.findViewById(R.id.ccw_exchange_rate_amount_and_currency);
        lastUpdateDate = (FermatTextView) itemView.findViewById(R.id.ccw_last_update_date);
        statusHistoryCustomer = (FermatTextView) itemView.findViewById(R.id.ccw_customer_status_history);
    }

    public void bind(ContractBasicInformation itemInfo) {
        ContractStatus contractStatus = itemInfo.getStatus();

        itemView.setBackgroundColor(getStatusBackgroundColor(contractStatus));
        brokerName.setText(itemInfo.getCryptoBrokerAlias());
        brokerImage.setImageDrawable(getImgDrawable(itemInfo.getCryptoBrokerImage()));

        statusHistoryCustomer.setText(contractStatus.getFriendlyName());
        String soldQuantityAndCurrencyText = getSoldQuantityAndCurrencyText(itemInfo, contractStatus);
        soldQuantityAndCurrency.setText(soldQuantityAndCurrencyText);

        String exchangeRateAmountAndCurrencyText = getExchangeRateAmountAndCurrencyText(itemInfo);
        exchangeRateAmountAndCurrency.setText(exchangeRateAmountAndCurrencyText);

        CharSequence date = DateFormat.format("dd MMM yyyy", itemInfo.getLastUpdate());
        lastUpdateDate.setText(date);
    }

    @NonNull
    private String getSoldQuantityAndCurrencyText(ContractBasicInformation itemInfo, ContractStatus contractStatus) {
        String sellingOrSoldText = getSellingOrSoldText(contractStatus);
        String amount = decimalFormat.format(itemInfo.getAmount());
        String merchandise = itemInfo.getMerchandise();

        return res.getString(R.string.ccw_contract_history_sold_quantity_and_currency, sellingOrSoldText, amount, merchandise);
    }

    @NonNull
    private String getExchangeRateAmountAndCurrencyText(ContractBasicInformation itemInfo) {
        String merchandise = itemInfo.getMerchandise();
        String exchangeAmount = decimalFormat.format(itemInfo.getExchangeRateAmount());
        String paymentCurrency = itemInfo.getPaymentCurrency();

        return res.getString(R.string.ccw_contract_history_exchange_rate_amount_and_currency, merchandise, exchangeAmount, paymentCurrency);
    }

    private int getStatusBackgroundColor(ContractStatus status) {
        int color = -1;

//        if (status == ContractStatus.PENDING_PAYMENT)
//            return R.color.waiting_for_customer_list_item_background;

        if (status == ContractStatus.CANCELLED)
            color = Color.parseColor("#c6c6c6");//R.color.contract_cancelled_list_item_background);

        if (status == ContractStatus.COMPLETED)
            color = Color.parseColor("#f3f3f3");//res.getColor(R.color.contract_completed_list_item_background);

        return color;//R.color.waiting_for_broker_list_item_background;
    }

    private String getSellingOrSoldText(ContractStatus status) {
        if (status == ContractStatus.COMPLETED)
            return res.getString(R.string.bought);
        return res.getString(R.string.selling);
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }
}
