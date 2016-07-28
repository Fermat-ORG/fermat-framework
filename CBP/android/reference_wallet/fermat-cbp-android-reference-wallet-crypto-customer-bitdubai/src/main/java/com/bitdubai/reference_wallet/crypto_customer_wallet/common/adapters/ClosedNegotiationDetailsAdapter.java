package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterImproved;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details.NoteViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.AmountToBuyViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.DateTimeViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.ExchangeRateViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.SingleChoiceViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * Created by nelson on 16/02/16.
 */
public class ClosedNegotiationDetailsAdapter extends FermatAdapterImproved<ClauseInformation, FermatViewHolder> {

    public static final int TYPE_AMOUNT_TO_BUY = 4;
    public static final int TYPE_AMOUNT_TO_PAY = 7;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_SINGLE_CHOICE = 1;
    private static final int TYPE_DATE_TIME = 2;
    private static final int TYPE_EXCHANGE_RATE = 3;
    private static final int TYPE_FOOTER = 5;

    private CustomerBrokerNegotiationInformation negotiationInfo;

    private boolean haveNote;


    public ClosedNegotiationDetailsAdapter(Context context, CustomerBrokerNegotiationInformation negotiationInformation) {

        super(context);
        this.negotiationInfo = negotiationInformation;
        dataSet = new ArrayList<>();
        dataSet.addAll(buildListOfItems());
        haveNote = negotiationInfo.getMemo() != null && !negotiationInfo.getMemo().isEmpty();
    }

    public void changeDataSet(CustomerBrokerNegotiationInformation negotiationInformation) {
        this.negotiationInfo = negotiationInformation;
        dataSet = new ArrayList<>();
        dataSet.addAll(buildListOfItems());
        final List<ClauseInformation> items = buildListOfItems();
        super.changeDataSet(items);
    }

    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {
        switch (type) {
            case TYPE_HEADER:
                return new NoteViewHolder(itemView);
            case TYPE_DATE_TIME:
                return new DateTimeViewHolder(itemView);
            case TYPE_SINGLE_CHOICE:
                return new SingleChoiceViewHolder(itemView);
            case TYPE_EXCHANGE_RATE:
                final ExchangeRateViewHolder exchangeRateViewHolder = new ExchangeRateViewHolder(itemView);
                exchangeRateViewHolder.getMarkerRateReferenceContainer().setVisibility(View.GONE);
                return exchangeRateViewHolder;
            case TYPE_AMOUNT_TO_BUY:
                final AmountToBuyViewHolder amountToBuyViewHolder = new AmountToBuyViewHolder(itemView);
                amountToBuyViewHolder.setPaymentBuy(true);
                return amountToBuyViewHolder;
            case TYPE_AMOUNT_TO_PAY:
                final AmountToBuyViewHolder amountToPayViewHolder = new AmountToBuyViewHolder(itemView);
                amountToPayViewHolder.setPaymentBuy(false);
                return amountToPayViewHolder;

            default:
                throw new IllegalArgumentException("Cant recognise the given value");
        }
    }

    @Override
    protected int getCardViewResource(int type) {
        switch (type) {
            case TYPE_HEADER:
                return R.layout.ccw_notes_item_close;
            case TYPE_DATE_TIME:
                return R.layout.ccw_date_time_item_close;
            case TYPE_SINGLE_CHOICE:
                return R.layout.ccw_single_choice_item_close;
            case TYPE_EXCHANGE_RATE:
                return R.layout.ccw_exchange_rate_item_close;
            case TYPE_AMOUNT_TO_BUY:
                return R.layout.ccw_amount_to_buy_item_single_close;
            case TYPE_AMOUNT_TO_PAY:
                return R.layout.ccw_amount_to_buy_item_single_close;
            default:
                throw new NoSuchElementException("Incorrect type value");
        }
    }

    @Override
    public int getItemCount() {
        return haveNote ? super.getItemCount() + 1 : super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position))
            return TYPE_HEADER;

        final int itemPosition = getItemPosition(position);
        final ClauseType type = dataSet.get(itemPosition).getType();

        switch (type) {
            case EXCHANGE_RATE:
                return TYPE_EXCHANGE_RATE;
            case CUSTOMER_CURRENCY_QUANTITY:
                return TYPE_AMOUNT_TO_BUY;
            case BROKER_CURRENCY_QUANTITY:
                return TYPE_AMOUNT_TO_PAY;
            case CUSTOMER_DATE_TIME_TO_DELIVER:
                return TYPE_DATE_TIME;
            case BROKER_DATE_TIME_TO_DELIVER:
                return TYPE_DATE_TIME;
            default:
                return TYPE_SINGLE_CHOICE;
        }
    }

    @Override
    public void onBindViewHolder(FermatViewHolder holder, int position) {
        if (holder instanceof NoteViewHolder) {
            final NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
            noteViewHolder.bind(negotiationInfo.getMemo());
        } else {
            position = getItemPosition(position);
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    protected void bindHolder(FermatViewHolder holder, ClauseInformation clause, int position) {
        final ClauseViewHolder clauseViewHolder = (ClauseViewHolder) holder;

        clauseViewHolder.bindData(negotiationInfo, clause, position);
        clauseViewHolder.getConfirmButton().setVisibility(View.GONE);

        setViewResources(clause.getType(), position, clauseViewHolder);
    }

    private void setViewResources(ClauseType type, int position, ClauseViewHolder clauseViewHolder) {
        final int clauseNumber = position + 1;
        final int clauseNumberImageRes = FragmentsCommons.getClauseNumberImageRes(clauseNumber);

        switch (type) {
            case EXCHANGE_RATE:
                clauseViewHolder.setViewResources(R.string.exchange_rate_reference, clauseNumberImageRes);
                break;
            case CUSTOMER_CURRENCY_QUANTITY:
                clauseViewHolder.setViewResources(R.string.ccw_amount_to_buy, clauseNumberImageRes, R.string.buying_text);
                break;
            case BROKER_CURRENCY_QUANTITY:
                clauseViewHolder.setViewResources(R.string.ccw_amount_to_pay, clauseNumberImageRes, R.string.paying_text);
                break;
            case CUSTOMER_PAYMENT_METHOD:
                clauseViewHolder.setViewResources(R.string.payment_methods_title, clauseNumberImageRes, R.string.payment_method);
                break;
            case BROKER_PAYMENT_METHOD:
                clauseViewHolder.setViewResources(R.string.reception_methods_title, clauseNumberImageRes, R.string.payment_method);
                break;
            case CUSTOMER_BANK_ACCOUNT:
                clauseViewHolder.setViewResources(R.string.ccw_bank_account_customer, clauseNumberImageRes, R.string.ccw_bank_account_title);
                break;
            case BROKER_BANK_ACCOUNT:
                clauseViewHolder.setViewResources(R.string.ccw_bank_account_broker, clauseNumberImageRes, R.string.ccw_bank_account_title);
                break;
            case CUSTOMER_PLACE_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.ccw_cash_place_to_delivery_customer, clauseNumberImageRes, R.string.selected_location);
                break;
            case BROKER_PLACE_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.ccw_cash_place_to_delivery_broker, clauseNumberImageRes, R.string.selected_location);
                break;
            case CUSTOMER_DATE_TIME_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.ccw_delivery_date_title, clauseNumberImageRes, R.string.delivery_date_text);
                break;
            case BROKER_DATE_TIME_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.payment_date_title, clauseNumberImageRes, R.string.payment_date_text);
                break;
        }
    }

    private List<ClauseInformation> buildListOfItems() {

        Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

        ClauseInformation receptionMethod = clauses.get(ClauseType.BROKER_PAYMENT_METHOD);
        ClauseInformation brokerPaymentMethodDetail = getBrokerPaymentMethodDetail(clauses);
        ClauseInformation customerReceptionMethodDetail = getCustomerReceptionMethodDetail(clauses);

        final List<ClauseInformation> data = new ArrayList<>();

        if (clauses.get(ClauseType.EXCHANGE_RATE) != null)
            data.add(clauses.get(ClauseType.EXCHANGE_RATE));
        if (clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY) != null)
            data.add(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY));
        if (clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY) != null)
            data.add(clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY));
        if (clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD) != null)
            data.add(clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD));
        if (brokerPaymentMethodDetail != null) data.add(brokerPaymentMethodDetail);
        if (receptionMethod != null) data.add(receptionMethod);
        if (customerReceptionMethodDetail != null) data.add(customerReceptionMethodDetail);
        if (clauses.get(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER) != null)
            data.add(clauses.get(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER));
        if (clauses.get(ClauseType.BROKER_DATE_TIME_TO_DELIVER) != null)
            data.add(clauses.get(ClauseType.BROKER_DATE_TIME_TO_DELIVER));

        return data;
    }

    private ClauseInformation getBrokerPaymentMethodDetail(Map<ClauseType, ClauseInformation> clauses) {
        final ClauseInformation paymentMethod = clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD);

        if (paymentMethod != null) {
            String currencyType = paymentMethod.getValue();
            if (currencyType != null) {
                if (currencyType.equals(MoneyType.CRYPTO.getCode()))
                    return null;
                if (currencyType.equals(MoneyType.BANK.getCode()))
                    return clauses.get(ClauseType.BROKER_BANK_ACCOUNT);
                return clauses.get(ClauseType.BROKER_PLACE_TO_DELIVER);
            }
        }
        return null;
    }

    private ClauseInformation getCustomerReceptionMethodDetail(Map<ClauseType, ClauseInformation> clauses) {
        final ClauseInformation paymentMethod = clauses.get(ClauseType.BROKER_PAYMENT_METHOD);

        if (paymentMethod != null) {
            String currencyType = paymentMethod.getValue();
            if (currencyType != null) {
                if (currencyType.equals(MoneyType.CRYPTO.getCode()))
                    return null;
                if (currencyType.equals(MoneyType.BANK.getCode()))
                    return clauses.get(ClauseType.CUSTOMER_BANK_ACCOUNT);
                return clauses.get(ClauseType.CUSTOMER_PLACE_TO_DELIVER);
            }
        }
        return null;
    }

    private int getItemPosition(int position) {
        return haveNote ? position - 1 : position;
    }

    private boolean isHeaderPosition(int position) {
        return (position == 0) && (haveNote);
    }

    private boolean isFooterPosition(int position) {
        return position == getItemCount() - 1;
    }
}
