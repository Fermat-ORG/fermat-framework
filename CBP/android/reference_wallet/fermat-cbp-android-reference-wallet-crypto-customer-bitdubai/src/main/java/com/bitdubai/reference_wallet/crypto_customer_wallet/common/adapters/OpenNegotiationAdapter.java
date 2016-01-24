package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation.AmountToBuyViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation.ExchangeRateViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation.SingleChoiceViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.open_negotiation_details.OpenNegotiationDetailsFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 *Created by Yordin Alayn on 22.01.16.
 * Based in StartNegotiationAdapter of Star_negotiation by nelson
 */
public class OpenNegotiationAdapter extends FermatAdapter<ClauseInformation, FermatViewHolder> {

    private static final int TYPE_ITEM_SINGLE_CHOICE = 1;
    private static final int TYPE_ITEM_EXCHANGE_RATE = 3;
    private static final int TYPE_ITEM_AMOUNT_TO_BUY = 4;
    private static final int TYPE_FOOTER = 5;

    public static final String CASH_IN_HAND = "Cash on Hand";
    public static final String CASH_DELIVERY = "Cash Delivery";
    public static final String BANK_TRANSFER = "Bank Transfer";
    public static final String CRYPTO_TRANSFER = "Crypto Transfer";

    private CustomerBrokerNegotiationInformation negotiationInformation;
    private OpenNegotiationDetailsFragment footerListener;
    private ClauseViewHolder.Listener clauseListener;

    public OpenNegotiationAdapter (Context context, CustomerBrokerNegotiationInformation negotiationInformation) {

        super(context);

        this.negotiationInformation = negotiationInformation;

        dataSet = new ArrayList<>();
        dataSet.addAll(buildListOfItems());
    }

    public void changeDataSet(CustomerBrokerNegotiationInformation negotiationInfo) {

        this.negotiationInformation = negotiationInfo;

        final List<ClauseInformation> items = buildListOfItems();
        super.changeDataSet(items);

    }

    @Override
    public FermatViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {

        return createHolder(LayoutInflater.from(context).inflate(getCardViewResource(type), viewGroup, false), type);

    }

    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {

        switch (type) {
            case TYPE_ITEM_SINGLE_CHOICE:
                return new SingleChoiceViewHolder(itemView);

            case TYPE_ITEM_EXCHANGE_RATE:
                return new ExchangeRateViewHolder(itemView);

            case TYPE_ITEM_AMOUNT_TO_BUY:
                return new AmountToBuyViewHolder(itemView);

            case TYPE_FOOTER:
                final FooterViewHolder footerViewHolder = new FooterViewHolder(itemView);
                footerViewHolder.setListener(footerListener);
                return footerViewHolder;

            default:
                throw new IllegalArgumentException("Cant recognise the given value");
        }

    }

    private int getCardViewResource(int type) {

        switch (type) {
            case TYPE_ITEM_SINGLE_CHOICE:
                return R.layout.ccw_single_choice_item;
            case TYPE_ITEM_EXCHANGE_RATE:
                return R.layout.ccw_exchange_rate_item;
            case TYPE_ITEM_AMOUNT_TO_BUY:
                return R.layout.ccw_amount_to_buy_item;
            case TYPE_FOOTER:
                return R.layout.ccw_footer_item;
            default:
                throw new NoSuchElementException("Incorrect type value");
        }

    }

    @Override
    protected int getCardViewResource() {
        return 0;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (isFooterPosition(position))
            return TYPE_FOOTER;

        ClauseType type = dataSet.get(position).getType();
        switch (type) {
            case CUSTOMER_CURRENCY_QUANTITY:
                return TYPE_ITEM_AMOUNT_TO_BUY;
            case EXCHANGE_RATE:
                return TYPE_ITEM_EXCHANGE_RATE;
            default:
                return TYPE_ITEM_SINGLE_CHOICE;
        }

    }

    @Override
    public void onBindViewHolder(FermatViewHolder holder, int position) {

        if (!isFooterPosition(position))
            super.onBindViewHolder(holder, position);

    }

    @Override
    protected void bindHolder(FermatViewHolder holder, ClauseInformation clause, int position) {

        final ClauseViewHolder clauseViewHolder = (ClauseViewHolder) holder;
        clauseViewHolder.bindData(negotiationInformation, clause, position);
        clauseViewHolder.getConfirmButton().setVisibility(View.VISIBLE);
        clauseViewHolder.setListener(clauseListener);

        final int clauseNumber = position + 1;
        final int clauseNumberImageRes = FragmentsCommons.getClauseNumberImageRes(clauseNumber);

        switch (clause.getType()) {
            //BASIC CLAUSES
            case CUSTOMER_CURRENCY_QUANTITY:
                clauseViewHolder.setViewResources(R.string.ccw_amount_to_buy, clauseNumberImageRes);
                break;
            case EXCHANGE_RATE:
                clauseViewHolder.setViewResources(R.string.exchange_rate_reference, clauseNumberImageRes);
                break;
            case BROKER_CURRENCY:
                clauseViewHolder.setViewResources(R.string.ccw_currency_to_pay, clauseNumberImageRes, R.string.ccw_currency_description);
                break;
            case CUSTOMER_PAYMENT_METHOD:
                //TODO ACA DIO EXCEPTION REVISAR
                clauseViewHolder.setViewResources(R.string.payment_methods_title, clauseNumberImageRes, R.string.payment_method);
                break;
            case BROKER_PAYMENT_METHOD:
                clauseViewHolder.setViewResources(R.string.reception_methods_title, clauseNumberImageRes, R.string.payment_method);
                break;
            //BASIC CLAUSES (NOT USER)
            case BROKER_CURRENCY_QUANTITY:
                clauseViewHolder.setViewResources(R.string.ccw_amount_to_pay, clauseNumberImageRes, R.string.payment_method);
                break;
            case CUSTOMER_CURRENCY:
                clauseViewHolder.setViewResources(R.string.ccw_currency_to_buy, clauseNumberImageRes, R.string.payment_method);
                break;
            //CRYPTO CLAUSES
            case CUSTOMER_CRYPTO_ADDRESS:
                clauseViewHolder.setViewResources(R.string.ccw_crypto_address_customer, clauseNumberImageRes);
                break;
            case BROKER_CRYPTO_ADDRESS:
                clauseViewHolder.setViewResources(R.string.ccw_crypto_address_broker, clauseNumberImageRes);
                break;
            //BANK CLAUSES
            case CUSTOMER_BANK_ACCOUNT:
                clauseViewHolder.setViewResources(R.string.ccw_bank_account_customer, clauseNumberImageRes);
                break;
            case BROKER_BANK_ACCOUNT:
                clauseViewHolder.setViewResources(R.string.ccw_bank_account_broker, clauseNumberImageRes);
                break;
            //CASH CLAUSES
            case CUSTOMER_PLACE_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.ccw_cash_place_to_delivery_customer, clauseNumberImageRes);
                break;
            case BROKER_PLACE_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.ccw_cash_place_to_delivery_broker, clauseNumberImageRes);
                break;
            //DATE CLAUSES
            case CUSTOMER_DATE_TIME_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.ccw_date_to_delivery_customer, clauseNumberImageRes);
                break;
            case BROKER_DATE_TIME_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.ccw_date_to_delivery_broker, clauseNumberImageRes);
                break;
        }

    }

    public void setFooterListener(OpenNegotiationDetailsFragment footerListener) {
        this.footerListener = footerListener;
    }

    public void setClauseListener(ClauseViewHolder.Listener clauseListener) {
        this.clauseListener = clauseListener;
    }

    private List<ClauseInformation> buildListOfItems() {

        int cont = 5;

        Map<ClauseType, ClauseInformation> clauses = negotiationInformation.getClauses();

        final int TOTAL_STEPS = getTotalSteps(clauses);

        final ClauseInformation[] data = new ClauseInformation[TOTAL_STEPS];

        //BASIC CLAUSES
        data[0] = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY);
        data[1] = clauses.get(ClauseType.EXCHANGE_RATE);
        data[2] = clauses.get(ClauseType.BROKER_CURRENCY);
        data[3] = clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD);
        data[4] = clauses.get(ClauseType.BROKER_PAYMENT_METHOD);
        data[5] = getCustomerPaymentInfo(clauses);
        data[6] = getBrokerPaymentInfo(clauses);
        data[7] = clauses.get(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER);
        data[8] = clauses.get(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER);

        return Arrays.asList(data);
    }

    private ClauseInformation getCustomerPaymentInfo(Map<ClauseType, ClauseInformation> clauses){

        ClauseInformation currencyEquals = clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD);
        ClauseInformation clause = null;

        if(currencyEquals.getValue().equals(CRYPTO_TRANSFER))
            clause = clauses.get(ClauseType.BROKER_CRYPTO_ADDRESS);

        else if(currencyEquals.getValue().equals(BANK_TRANSFER))
            clause = clauses.get(ClauseType.BROKER_BANK_ACCOUNT);

        else if(currencyEquals.getValue().equals(CASH_DELIVERY) || currencyEquals.getValue().equals(CASH_IN_HAND))
            clause = clauses.get(ClauseType.BROKER_PLACE_TO_DELIVER);

        return clause;
    }

    private ClauseInformation getBrokerPaymentInfo(Map<ClauseType, ClauseInformation> clauses){

        ClauseInformation currencyEquals = clauses.get(ClauseType.BROKER_PAYMENT_METHOD);
        ClauseInformation clause = null;

        if(currencyEquals.getValue().equals(CRYPTO_TRANSFER))
            clause = clauses.get(ClauseType.CUSTOMER_CRYPTO_ADDRESS);

        else if(currencyEquals.getValue().equals(BANK_TRANSFER))
            clause = clauses.get(ClauseType.CUSTOMER_BANK_ACCOUNT);

        else if(currencyEquals.getValue().equals(CASH_DELIVERY) || currencyEquals.getValue().equals(CASH_IN_HAND))
            clause = clauses.get(ClauseType.CUSTOMER_PLACE_TO_DELIVER);

        return clause;
    }

    private int getTotalSteps(Map<ClauseType, ClauseInformation> clauses){

//        int cont = 0;
//        if(clauses != null)
//            for (Map.Entry<ClauseType, ClauseInformation> clauseInformation : clauses.entrySet()) if(clauseInformation != null) cont++;
//
//        return cont;
        return 9;

    }

    private boolean isFooterPosition(int position) {
        return position == getItemCount() - 1;
    }
}
