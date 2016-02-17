package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterImproved;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.open_negotiation_details.OpenNegotiationDetailsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by nelson on 16/02/16.
 */
public class NewOpenNegotiationDetailsAdapter extends FermatAdapterImproved<ClauseInformation, ClauseViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_SINGLE_CHOICE = 1;
    private static final int TYPE_ITEM_DATE_TIME = 2;
    private static final int TYPE_ITEM_EXCHANGE_RATE = 3;
    private static final int TYPE_ITEM_AMOUNT_TO_BUY = 4;
    private static final int TYPE_ITEM_AMOUNT_TO_PAY = 6;
    private static final int TYPE_FOOTER = 5;

    private CustomerBrokerNegotiationInformation negotiationInformation;
    private OpenNegotiationDetailsFragment footerListener;
    private ClauseViewHolder.Listener clauseListener;
    private List <IndexInfoSummary> marketRateList;

    private boolean haveNote;

    public NewOpenNegotiationDetailsAdapter (Context context, CustomerBrokerNegotiationInformation negotiationInformation) {

        super(context);

        this.negotiationInformation = negotiationInformation;

        dataSet = new ArrayList<>();
        dataSet.addAll(buildListOfItems());

        haveNote = false;
        haveNote = (!negotiationInformation.getMemo().isEmpty());
    }

    public void changeDataSet(CustomerBrokerNegotiationInformation negotiationInfo) {

        this.negotiationInformation = negotiationInfo;

        dataSet = new ArrayList<>();
        dataSet.addAll(buildListOfItems());

        final List<ClauseInformation> items = buildListOfItems();
        super.changeDataSet(items);

    }

    @Override
    protected ClauseViewHolder createHolder(View itemView, int type) {
        return null;
    }

    @Override
    protected int getCardViewResource(int type) {
        return 0;
    }

    @Override
    protected void bindHolder(ClauseViewHolder holder, ClauseInformation data, int position) {

    }

    private List<ClauseInformation> buildListOfItems() {

        Map<ClauseType, ClauseInformation> clauses = negotiationInformation.getClauses();

//        final int TOTAL_STEPS = getTotalSteps(clauses);
        int TOTAL_STEPS = 8;
        int contInd = TOTAL_STEPS - 1;
        ClauseInformation brokerPaymentMethod = getCustomerPaymentInfo(clauses);
        ClauseInformation customerReceivedMethod = getBrokerPaymentInfo(clauses);

        if(brokerPaymentMethod != null)     TOTAL_STEPS = TOTAL_STEPS + 1;
        if(customerReceivedMethod != null)  TOTAL_STEPS = TOTAL_STEPS + 1;


        final ClauseInformation[] data = new ClauseInformation[TOTAL_STEPS];

        data[0] = clauses.get(ClauseType.BROKER_CURRENCY);
        data[1] = clauses.get(ClauseType.EXCHANGE_RATE);
        data[2] = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY);
        data[3] = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);
        data[4] = clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD);
        data[5] = clauses.get(ClauseType.BROKER_PAYMENT_METHOD);
        data[6] = clauses.get(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER);
        data[7] = clauses.get(ClauseType.BROKER_DATE_TIME_TO_DELIVER);

        if(brokerPaymentMethod != null){
            contInd = contInd + 1;
            data[contInd] = brokerPaymentMethod;
        }

        if(customerReceivedMethod != null){
            contInd = contInd + 1;
            data[contInd] = customerReceivedMethod;
        }
//
        return Arrays.asList(data);
    }

    private ClauseInformation getCustomerPaymentInfo(Map<ClauseType, ClauseInformation> clauses){

        String currencyType = clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD).getValue();
        ClauseInformation clause = null;

        if(currencyType != null) {
            if (currencyType.equals(MoneyType.CRYPTO.getFriendlyName()))
                clause = clauses.get(ClauseType.BROKER_CRYPTO_ADDRESS);

            else if (currencyType.equals(MoneyType.BANK.getFriendlyName()))
                clause = clauses.get(ClauseType.BROKER_BANK_ACCOUNT);

            else if (currencyType.equals(MoneyType.CASH_DELIVERY.getFriendlyName()) || (currencyType.equals(MoneyType.CASH_ON_HAND.getFriendlyName())))
                clause = clauses.get(ClauseType.BROKER_PLACE_TO_DELIVER);
        }

        return clause;
    }

    private ClauseInformation getBrokerPaymentInfo(Map<ClauseType, ClauseInformation> clauses){

        String currencyType = clauses.get(ClauseType.BROKER_PAYMENT_METHOD).getValue();
        ClauseInformation clause = null;

        if(currencyType != null) {
            if (currencyType.equals(MoneyType.CRYPTO.getFriendlyName()))
                clause = clauses.get(ClauseType.CUSTOMER_CRYPTO_ADDRESS);

            else if (currencyType.equals(MoneyType.BANK.getFriendlyName()))
                clause = clauses.get(ClauseType.CUSTOMER_BANK_ACCOUNT);

            else if (currencyType.equals(MoneyType.CASH_DELIVERY.getFriendlyName()) || (currencyType.equals(MoneyType.CASH_ON_HAND.getFriendlyName())))
                clause = clauses.get(ClauseType.CUSTOMER_PLACE_TO_DELIVER);
        }

        return clause;
    }
}
