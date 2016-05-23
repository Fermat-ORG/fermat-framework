package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.AmountToSellStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ExchangeRateStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.SingleValueStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details.AmountToBuyStepViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details.DateTimeStepViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details.ExchangeRateStepViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details.NoteViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details.SingleChoiceStepViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.StepItemGetter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;


public class NegotiationDetailsAdapter extends RecyclerView.Adapter<FermatViewHolder> implements StepItemGetter {
    private static final int NO_TYPE = Integer.MIN_VALUE;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_SINGLE_CHOICE = 1;
    private static final int TYPE_ITEM_DATE_TIME = 2;
    private static final int TYPE_ITEM_EXCHANGE_RATE = 3;
    private static final int TYPE_ITEM_AMOUNT_TO_SELL = 4;
    private static final int TYPE_FOOTER = 5;

    private FermatSession session;
    private List<NegotiationStep> dataSet;
    private Activity activity;
    private CryptoCustomerWalletModuleManager walletManager;
    private FooterViewHolder.OnFooterButtonsClickListener footerListener;

    private final CustomerBrokerNegotiationInformation data;
    private ExchangeRateStepViewHolder exchangeRateViewHolder;
    private boolean haveNote;

    public NegotiationDetailsAdapter(Activity activity, FermatSession session, CryptoCustomerWalletModuleManager walletManager, CustomerBrokerNegotiationInformation data, List<NegotiationStep> dataSet) {
        this.activity = activity;
        this.session = session;
        this.dataSet = dataSet;

        this.walletManager = walletManager;

        haveNote = false;
        this.data = data;

        haveNote = (data.getMemo() != null);
    }

    private FermatViewHolder createHolder(View itemView, int type) {
        if (type == TYPE_HEADER)
            return new NoteViewHolder(itemView);
        if (type == TYPE_ITEM_DATE_TIME)
            return new DateTimeStepViewHolder(this, itemView, activity, walletManager);
        if (type == TYPE_ITEM_SINGLE_CHOICE)
            return new SingleChoiceStepViewHolder(this, itemView, activity, walletManager);
        if (type == TYPE_ITEM_EXCHANGE_RATE)
            return new ExchangeRateStepViewHolder(this, itemView, walletManager);
        if (type == TYPE_ITEM_AMOUNT_TO_SELL)
            return new AmountToBuyStepViewHolder(this, itemView, walletManager);
        if (type == TYPE_FOOTER)
            return new FooterViewHolder(itemView, data, dataSet, walletManager);

        throw new NoSuchElementException("Incorrect type value");
    }

    private int getCardViewResource(int type) {
        switch (type) {
            case TYPE_HEADER:
                return R.layout.ccw_notes_item;
            case TYPE_ITEM_SINGLE_CHOICE:
                return R.layout.ccw_single_choice_item;
            case TYPE_ITEM_DATE_TIME:
                return R.layout.ccw_date_time_item;
            case TYPE_ITEM_EXCHANGE_RATE:
                return R.layout.ccw_exchange_rate_item;
            case TYPE_ITEM_AMOUNT_TO_SELL:
                return R.layout.ccw_amount_to_buy_item;
            case TYPE_FOOTER:
                return R.layout.ccw_footer_item;
            default:
                throw new NoSuchElementException("Incorrect type value");
        }
    }

    @Override
    public FermatViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return createHolder(LayoutInflater.from(activity).inflate(getCardViewResource(type), viewGroup, false), type);
    }

    @Override
    public void onBindViewHolder(FermatViewHolder holder, int position) {
        int holderType = getItemViewType(position);
        int itemPosition = getItemPosition(position);
        int stepNumber = itemPosition + 1;

        switch (holderType) {
            case TYPE_FOOTER:
                FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                footerViewHolder.setListener(footerListener);
                break;
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
                AmountToBuyStepViewHolder amountToSellViewHolder = (AmountToBuyStepViewHolder) holder;
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

    /**
     * Get item
     *
     * @param position int position to getDate
     * @return Model object
     */
    @Override
    public NegotiationStep getItem(final int position) {
        return dataSet != null ? ((!dataSet.isEmpty() && position < dataSet.size()) ? dataSet.get(position) : null) : null;
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

    public void setFooterListener(FooterViewHolder.OnFooterButtonsClickListener listener) {
        footerListener = listener;
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
                String currencyToSell = data.getClauses().get(ClauseType.CUSTOMER_CURRENCY).getValue();
                viewHolder.bind(
                        stepNumber,
                        R.string.payment_methods_title,
                        R.string.payment_method,
                        step.getValue(),
                        null); //TODO walletManager.getPaymentMethods(currencyToSell));
                break;
            case BROKER_BANK_ACCOUNT:
                viewHolder.bind(
                        stepNumber,
                        R.string.broker_bank_account_title,
                        R.string.selected_bank_account,
                        step.getValue(),
                        null);//getBankAccounts());
                break;
            case BROKER_LOCATION:
                viewHolder.bind(
                        stepNumber,
                        R.string.broker_locations_title,
                        R.string.selected_location,
                        step.getValue(),
                        getLocations());
                break;
            case CUSTOMER_BANK_ACCOUNT:
                viewHolder.bind(
                        stepNumber,
                        R.string.customer_bank_account_title,
                        R.string.selected_bank_account,
                        step.getValue(),
                        null);
                break;
            case CUSTOMER_LOCATION:
                viewHolder.bind(
                        stepNumber,
                        R.string.customer_locations_title,
                        R.string.selected_location,
                        step.getValue(),
                        null);
                break;
        }
    }

    private void bindDateTimeViewHolder(int stepNumber, DateTimeStepViewHolder viewHolder, SingleValueStep step) {
        NegotiationStepType type = step.getType();

        switch (type) {
            case DATE_TIME_TO_DELIVER:
                viewHolder.bind(
                        stepNumber,
                        R.string.ccw_delivery_date_title,
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

    private List<String> getLocations() {
        List<String> data = new ArrayList<>();
        try {
            Collection<NegotiationLocations> locations = walletManager.getAllLocations(NegotiationType.PURCHASE);
            if (locations != null)
                for (NegotiationLocations location : locations)
                    data.add(location.getLocation());

        } catch (FermatException ex) {
            Log.e("NegotiationDetailsAdapt", ex.getMessage(), ex);
        }

        return data;
    }
}
