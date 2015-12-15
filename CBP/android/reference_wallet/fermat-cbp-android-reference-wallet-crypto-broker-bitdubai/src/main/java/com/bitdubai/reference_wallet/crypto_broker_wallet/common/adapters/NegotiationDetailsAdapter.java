package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.AmountToSellStepViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.DateTimeStepViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.ExchangeRateStepViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.NoteViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.SingleChoiceStepViewHolder;

import java.util.List;
import java.util.NoSuchElementException;


public class NegotiationDetailsAdapter extends FermatAdapter<NegotiationStep, FermatViewHolder> {
    private static final int NO_TYPE = Integer.MIN_VALUE;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_SINGLE_CHOICE = 1;
    private static final int TYPE_ITEM_DATE_TIME = 2;
    private static final int TYPE_ITEM_EXCHANGE_RATE = 3;
    private static final int TYPE_ITEM_AMOUNT_TO_SELL = 4;
    private static final int TYPE_FOOTER = 5;

    private final CustomerBrokerNegotiationInformation data;
    private ExchangeRateStepViewHolder exchangeRateViewHolder;
    private boolean haveNote;

    public NegotiationDetailsAdapter(Activity activity, CustomerBrokerNegotiationInformation data, List<NegotiationStep> dataSet) {
        super(activity, dataSet);
        haveNote = false;
        this.data = data;

        haveNote = (data.getMemo() != null);
    }

    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {
        if (type == TYPE_HEADER)
            return new NoteViewHolder(itemView);
        if (type == TYPE_ITEM_DATE_TIME)
            return new DateTimeStepViewHolder(this, itemView, (Activity) context);
        if (type == TYPE_ITEM_SINGLE_CHOICE)
            return new SingleChoiceStepViewHolder(this, itemView, (Activity) context);
        if (type == TYPE_ITEM_EXCHANGE_RATE)
            return new ExchangeRateStepViewHolder(this, itemView);
        if (type == TYPE_ITEM_AMOUNT_TO_SELL)
            return new AmountToSellStepViewHolder(this, itemView);
        if (type == TYPE_FOOTER)
            return new FooterViewHolder(itemView, data, dataSet, (Activity) context);

        throw new NoSuchElementException("Incorrect type value");
    }

    @Override
    protected int getCardViewResource(int type) {
        switch (type) {
            case TYPE_HEADER:
                return R.layout.notes_item;
            case TYPE_ITEM_SINGLE_CHOICE:
                return R.layout.single_choice_item;
            case TYPE_ITEM_DATE_TIME:
                return R.layout.date_time_item;
            case TYPE_ITEM_EXCHANGE_RATE:
                return R.layout.exchange_rate_item;
            case TYPE_ITEM_AMOUNT_TO_SELL:
                return R.layout.amount_to_sell_item;
            case TYPE_FOOTER:
                return R.layout.footer_item;
            default:
                throw new NoSuchElementException("Incorrect type value");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int holderType = getItemViewType(position);
        int itemPosition = getItemPosition(position);
        int stepNumber = itemPosition + 1;

        switch (holderType) {
            case TYPE_HEADER:
                NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
                noteViewHolder.bind(data.getMemo());
                break;
            case TYPE_ITEM_SINGLE_CHOICE:
                SingleValueStep singleValueStep = (SingleValueStep) dataSet.get(itemPosition);
                SingleChoiceStepViewHolder singleChoiceViewHolder = (SingleChoiceStepViewHolder) holder;
                bindSingleChoiceViewHolder(
                        stepNumber,
                        singleChoiceViewHolder,
                        singleValueStep);
                break;
            case TYPE_ITEM_DATE_TIME:
                singleValueStep = (SingleValueStep) dataSet.get(itemPosition);
                DateTimeStepViewHolder dateTimeViewHolder = (DateTimeStepViewHolder) holder;
                bindDateTimeViewHolder(
                        stepNumber,
                        dateTimeViewHolder,
                        singleValueStep);
                break;
            case TYPE_ITEM_EXCHANGE_RATE:
                ExchangeRateStep exchangeRateStep = (ExchangeRateStep) dataSet.get(itemPosition);
                exchangeRateViewHolder = (ExchangeRateStepViewHolder) holder;
                exchangeRateViewHolder.bind(
                        stepNumber,
                        exchangeRateStep.getCurrencyToSell(),
                        exchangeRateStep.getCurrencyToReceive(),
                        exchangeRateStep.getExchangeRate(),
                        exchangeRateStep.getSuggestedExchangeRate());
                break;
            case TYPE_ITEM_AMOUNT_TO_SELL:
                AmountToSellStep amountToSellStep = (AmountToSellStep) dataSet.get(itemPosition);
                AmountToSellStepViewHolder amountToSellViewHolder = (AmountToSellStepViewHolder) holder;
                amountToSellViewHolder.bind(
                        stepNumber,
                        amountToSellStep.getCurrencyToSell(),
                        amountToSellStep.getCurrencyToReceive(),
                        amountToSellStep.getAmountToSell(),
                        amountToSellStep.getAmountToReceive(),
                        amountToSellStep.getExchangeRateValue());

                if (exchangeRateViewHolder != null) {
                    exchangeRateViewHolder.setOnExchangeValueChangeListener(amountToSellViewHolder);
                }

                break;
        }
    }

    @Override
    protected void bindHolder(RecyclerView.ViewHolder holder, NegotiationStep data, int position) {
        // DO NOTHING...
    }

    @Override
    public int getItemCount() {
        final int size = dataSet.size();
        return haveNote ? size + 2 : size + 1;
    }

    @Override
    public int getItemViewType(int itemPosition) {
        if (itemPosition == 0 && haveNote) {
            return TYPE_HEADER;
        }

        if (itemPosition == getFooterPosition()) {
            return TYPE_FOOTER;
        }

        itemPosition = getItemPosition(itemPosition);
        NegotiationStep step = dataSet.get(itemPosition);
        NegotiationStepType type = step.getType();

        switch (type) {
            case EXCHANGE_RATE:
                return TYPE_ITEM_EXCHANGE_RATE;
            case AMOUNT_TO_SALE:
                return TYPE_ITEM_AMOUNT_TO_SELL;
            case PAYMENT_METHOD:
                return TYPE_ITEM_SINGLE_CHOICE;
            case BROKER_BANK_ACCOUNT:
                return TYPE_ITEM_SINGLE_CHOICE;
            case BROKER_LOCATION:
                return TYPE_ITEM_SINGLE_CHOICE;
            case CUSTOMER_BANK_ACCOUNT:
                return TYPE_ITEM_SINGLE_CHOICE;
            case CUSTOMER_LOCATION:
                return TYPE_ITEM_SINGLE_CHOICE;
            case DATE_TIME_TO_DELIVER:
                return TYPE_ITEM_DATE_TIME;
            case DATE_TIME_TO_PAY:
                return TYPE_ITEM_DATE_TIME;
            case EXPIRATION_DATE_TIME:
                return TYPE_ITEM_DATE_TIME;
        }

        return NO_TYPE;
    }

    public NegotiationStep getDataSetItem(int position) {
        return dataSet.get(position);
    }

    private int getItemPosition(int position) {
        return haveNote ? position - 1 : position;
    }

    private int getFooterPosition() {
        return getItemCount() - 1;
    }

    private void bindSingleChoiceViewHolder(int stepNumber, SingleChoiceStepViewHolder viewHolder, SingleValueStep step) {
        NegotiationStepType type = step.getType();

        switch (type) {
            case PAYMENT_METHOD:
                viewHolder.bind(
                        stepNumber,
                        R.string.payment_methods_title,
                        R.string.payment_method,
                        step.getValue(),
                        ModuleManager.PAYMENT_METHODS);
                break;
            case BROKER_BANK_ACCOUNT:
                viewHolder.bind(
                        stepNumber,
                        R.string.broker_bank_account_title,
                        R.string.selected_bank_account,
                        step.getValue(),
                        ModuleManager.BROKER_BANK_ACCOUNTS);
                break;
            case BROKER_LOCATION:
                viewHolder.bind(
                        stepNumber,
                        R.string.broker_locations_title,
                        R.string.selected_location,
                        step.getValue(),
                        ModuleManager.BROKER_LOCATIONS);
                break;
            case CUSTOMER_BANK_ACCOUNT:
                viewHolder.bind(
                        stepNumber,
                        R.string.customer_bank_account_title,
                        R.string.selected_bank_account,
                        step.getValue(),
                        ModuleManager.CUSTOMER_BANK_ACCOUNTS);
                break;
            case CUSTOMER_LOCATION:
                viewHolder.bind(
                        stepNumber,
                        R.string.customer_locations_title,
                        R.string.selected_location,
                        step.getValue(),
                        ModuleManager.CUSTOMER_LOCATIONS);
                break;
        }
    }

    private void bindDateTimeViewHolder(int stepNumber, DateTimeStepViewHolder viewHolder, SingleValueStep step) {
        NegotiationStepType type = step.getType();

        switch (type) {
            case DATE_TIME_TO_DELIVER:
                viewHolder.bind(
                        stepNumber,
                        R.string.delivery_date_title,
                        R.string.delivery_date_text,
                        step.getValue());
                break;
            case DATE_TIME_TO_PAY:
                viewHolder.bind(
                        stepNumber,
                        R.string.payment_date_title,
                        R.string.payment_date_text,
                        step.getValue());
                break;
            case EXPIRATION_DATE_TIME:
                viewHolder.bind(
                        stepNumber,
                        R.string.expiration_date_title,
                        R.string.expiration_date_text,
                        step.getValue());
                break;
        }
    }
}
