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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.AmountToBuyViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.ExchangeRateViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.SingleChoiceViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.EmptyCustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.start_negotiation.StartNegotiationActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


public class StartNegotiationAdapter extends FermatAdapter<ClauseInformation, FermatViewHolder> {

    private static final int TYPE_ITEM_SINGLE_CHOICE = 1;
    private static final int TYPE_ITEM_EXCHANGE_RATE = 3;
    private static final int TYPE_ITEM_AMOUNT_TO_BUY = 4;
    private static final int TYPE_ITEM_AMOUNT_TO_PAY = 6;
    private static final int TYPE_FOOTER = 5;


    private CustomerBrokerNegotiationInformation negotiationInformation;
    private StartNegotiationActivityFragment footerListener;
    ClauseViewHolder.Listener clauseListener;
    private List<IndexInfoSummary> marketRateList;
    private boolean walletUser = false;


    public StartNegotiationAdapter(Context context, CustomerBrokerNegotiationInformation negotiationInformation, boolean walletUser) {
        super(context);

        this.negotiationInformation = negotiationInformation;
        this.walletUser = walletUser;

        dataSet = new ArrayList<>();
        dataSet.addAll(buildListOfItems());
    }

    public void changeDataSet(EmptyCustomerBrokerNegotiationInformation negotiationInfo) {
        this.negotiationInformation = negotiationInfo;

        final List<ClauseInformation> items = buildListOfItems();
        super.changeDataSet(items);
    }

    public void changeDataSet(EmptyCustomerBrokerNegotiationInformation negotiationInfo, boolean walletUser) {
        this.negotiationInformation = negotiationInfo;
        this.walletUser = walletUser;

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
//                return new ExchangeRateViewHolder(itemView);
                final ExchangeRateViewHolder exchangeRateViewHolder = new ExchangeRateViewHolder(itemView);
                exchangeRateViewHolder.setMarketRateList(marketRateList);
                return exchangeRateViewHolder;

            case TYPE_ITEM_AMOUNT_TO_BUY:
                return new AmountToBuyViewHolder(itemView);

            case TYPE_ITEM_AMOUNT_TO_PAY:

                final AmountToBuyViewHolder amountToPayViewHolder = new AmountToBuyViewHolder(itemView);
                amountToPayViewHolder.setPaymentBuy(Boolean.FALSE);
                return amountToPayViewHolder;


            case TYPE_FOOTER:
                final FooterViewHolder footerViewHolder = new FooterViewHolder(itemView);
                footerViewHolder.setListener(footerListener);

//                if(this.walletUser == false)
//                    footerViewHolder.HideButtonsWalletUser();

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
                return R.layout.ccw_amount_to_buy_item_single;
            case TYPE_ITEM_AMOUNT_TO_PAY:
                return R.layout.ccw_amount_to_buy_item_single;
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
            case EXCHANGE_RATE:
                return TYPE_ITEM_EXCHANGE_RATE;
            case CUSTOMER_CURRENCY_QUANTITY:
                return TYPE_ITEM_AMOUNT_TO_BUY;
            case BROKER_CURRENCY_QUANTITY:
                return TYPE_ITEM_AMOUNT_TO_PAY;
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
        clauseViewHolder.getConfirmButton().setVisibility(View.GONE);
        clauseViewHolder.setListener(clauseListener);

        final int clauseNumber = position + 1;
        final int clauseNumberImageRes = FragmentsCommons.getClauseNumberImageRes(clauseNumber);

        switch (clause.getType()) {
            case BROKER_CURRENCY:
                clauseViewHolder.setViewResources(R.string.ccw_currency_to_pay, clauseNumberImageRes, R.string.ccw_currency_description);
                break;
            case EXCHANGE_RATE:
                clauseViewHolder.setViewResources(R.string.exchange_rate_reference, clauseNumberImageRes);
                break;
            case CUSTOMER_CURRENCY_QUANTITY:
                clauseViewHolder.setViewResources(R.string.ccw_amount_to_buy, clauseNumberImageRes, R.string.ccw_amount_title);
                break;
            case BROKER_CURRENCY_QUANTITY:
                clauseViewHolder.setViewResources(R.string.ccw_amount_to_pay, clauseNumberImageRes, R.string.ccw_amount_title);
                break;
//            case CUSTOMER_PAYMENT_METHOD:
//                clauseViewHolder.setViewResources(R.string.payment_methods_title, clauseNumberImageRes, R.string.payment_method);
//                break;
//            case BROKER_PAYMENT_METHOD:
//                clauseViewHolder.setViewResources(R.string.reception_methods_title, clauseNumberImageRes, R.string.payment_method);
//                break;
        }
    }

    public void changeItem(int position, ClauseInformation clause) {
        dataSet.set(position, clause);
        notifyItemChanged(position);
    }

    public void setFooterListener(StartNegotiationActivityFragment footerListener) {
        this.footerListener = footerListener;
    }

    public void setClauseListener(ClauseViewHolder.Listener clauseListener) {
        this.clauseListener = clauseListener;
    }

    public void setMarketRateList(List<IndexInfoSummary> marketRateList) {
        this.marketRateList = marketRateList;
    }

    private List<ClauseInformation> buildListOfItems() {
        final int TOTAL_STEPS = 4;

        Map<ClauseType, ClauseInformation> clauses = negotiationInformation.getClauses();
        final ClauseInformation[] data = new ClauseInformation[TOTAL_STEPS];


        data[0] = clauses.get(ClauseType.BROKER_CURRENCY);
        data[1] = clauses.get(ClauseType.EXCHANGE_RATE);
        data[2] = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY);
        data[3] = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);
//        data[3] = clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD);
//        data[4] = clauses.get(ClauseType.BROKER_PAYMENT_METHOD);

        return Arrays.asList(data);
    }

    private boolean isFooterPosition(int position) {
        return position == getItemCount() - 1;
    }
}
