package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterImproved;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.AmountViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.DateTimeViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.ExchangeRateViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.NoteViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.SingleChoiceViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * Created by nelson on 16/02/16.
 */
public class OpenNegotiationDetailsAdapter extends FermatAdapterImproved<ClauseInformation, FermatViewHolder> {

    public static final int TYPE_AMOUNT_TO_SELL = 4;
    public static final int TYPE_AMOUNT_TO_RECEIVE = 7;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_SINGLE_CHOICE = 1;
    private static final int TYPE_DATE_TIME = 2;
    private static final int TYPE_EXCHANGE_RATE = 3;
    private static final int TYPE_FOOTER = 5;

    private NegotiationWrapper negotiationWrapper;
    private FooterViewHolder.OnFooterButtonsClickListener footerListener;
    private ClauseViewHolder.Listener clauseListener;
    private List<IndexInfoSummary> marketRateList;
    private Quote quote;
    private float spread;
    private boolean quoteLoaded;

    private boolean haveNote;


    public OpenNegotiationDetailsAdapter(Context context, NegotiationWrapper negotiationInformation) {

        super(context);

        this.negotiationWrapper = negotiationInformation;

        dataSet = new ArrayList<>();
        dataSet.addAll(buildListOfItems());

        haveNote = negotiationInformation.haveNote();
    }

    public void changeDataSet(NegotiationWrapper negotiationWrapper) {

        this.negotiationWrapper = negotiationWrapper;

        dataSet = new ArrayList<>();
        dataSet.addAll(buildListOfItems());
        haveNote = negotiationWrapper.haveNote();

        final List<ClauseInformation> items = buildListOfItems();
        super.changeDataSet(items);
    }

    public void setClauseListener(ClauseViewHolder.Listener clauseListener) {
        this.clauseListener = clauseListener;
    }

    public void setFooterListener(FooterViewHolder.OnFooterButtonsClickListener footerListener) {
        this.footerListener = footerListener;
    }

    public void setMarketRateList(List<IndexInfoSummary> marketRateList) {
        this.marketRateList = marketRateList;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
        this.quoteLoaded = true;
        notifyItemChanged(haveNote ? 1 : 0);
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }

    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {
        switch (type) {
            case TYPE_HEADER:
                return new NoteViewHolder(itemView, TYPE_HEADER);
            case TYPE_DATE_TIME:
                return new DateTimeViewHolder(itemView, TYPE_DATE_TIME);
            case TYPE_SINGLE_CHOICE:
                return new SingleChoiceViewHolder(itemView, TYPE_SINGLE_CHOICE);
            case TYPE_EXCHANGE_RATE:
                final ExchangeRateViewHolder exchangeRateViewHolder = new ExchangeRateViewHolder(itemView, TYPE_SINGLE_CHOICE);
                exchangeRateViewHolder.setMarketRateList(marketRateList);
                exchangeRateViewHolder.setSuggestedRate(quote, quoteLoaded);
                exchangeRateViewHolder.setSpread(spread);
                return exchangeRateViewHolder;
            case TYPE_AMOUNT_TO_SELL:
                return new AmountViewHolder(itemView, TYPE_AMOUNT_TO_SELL);
            case TYPE_AMOUNT_TO_RECEIVE:
                return new AmountViewHolder(itemView, TYPE_AMOUNT_TO_RECEIVE);
            case TYPE_FOOTER:
                final FooterViewHolder footerViewHolder = new FooterViewHolder(itemView, TYPE_FOOTER);
                footerViewHolder.setListener(footerListener);

                if (negotiationWrapper.getNegotiationInfo().getStatus() == NegotiationStatus.SENT_TO_CUSTOMER || negotiationWrapper.getNegotiationInfo().getStatus() == NegotiationStatus.WAITING_FOR_CUSTOMER || negotiationWrapper.getNegotiationInfo().getStatus() == NegotiationStatus.WAITING_FOR_CLOSING) {
                    footerViewHolder.HideButtons();
                }

//                if(negotiationWrapper.isWalletUser() == false){
//                    footerViewHolder.HideButtonsWalletUser();
//                }

                return footerViewHolder;
            default:
                throw new IllegalArgumentException("Cant recognise the given value");
        }
    }

    @Override
    protected int getCardViewResource(int type) {
        switch (type) {
            case TYPE_HEADER:
                return R.layout.cbw_notes_item;
            case TYPE_DATE_TIME:
                return R.layout.cbw_clause_date_time_item;
            case TYPE_SINGLE_CHOICE:
                return R.layout.cbw_clause_single_choice_item;
            case TYPE_EXCHANGE_RATE:
                return R.layout.cbw_clause_exchange_rate_item;
            case TYPE_AMOUNT_TO_SELL:
                return R.layout.cbw_clause_amount_item;
            case TYPE_AMOUNT_TO_RECEIVE:
                return R.layout.cbw_clause_amount_item;
            case TYPE_FOOTER:
                return R.layout.cbw_footer_item;
            default:
                throw new NoSuchElementException("Incorrect type value");
        }
    }

    @Override
    public int getItemCount() {
        return haveNote ? super.getItemCount() + 2 : super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position))
            return TYPE_HEADER;

        if (isFooterPosition(position))
            return TYPE_FOOTER;

        final int itemPosition = getItemPosition(position);
        final ClauseType type = dataSet.get(itemPosition).getType();

        switch (type) {
            case EXCHANGE_RATE:
                return TYPE_EXCHANGE_RATE;
            case CUSTOMER_CURRENCY_QUANTITY:
                return TYPE_AMOUNT_TO_SELL;
            case BROKER_CURRENCY_QUANTITY:
                return TYPE_AMOUNT_TO_RECEIVE;
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
        int holderType = holder.getHolderType();

        if (holderType == TYPE_FOOTER)
            return;

        switch (holderType) {
            case TYPE_HEADER:
                final NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
                noteViewHolder.bind(negotiationWrapper.getNegotiationInfo().getMemo());
                break;
            default:
                position = getItemPosition(position);
                super.onBindViewHolder(holder, position);
                break;
        }
    }

    @Override
    protected void bindHolder(FermatViewHolder holder, ClauseInformation clause, int position) {
        final ClauseViewHolder clauseViewHolder = (ClauseViewHolder) holder;

        clauseViewHolder.bindData(negotiationWrapper, clause, position);
        clauseViewHolder.confirmButton.setVisibility(View.VISIBLE);
        clauseViewHolder.setListener(clauseListener);

        setViewResources(clause.getType(), position, clauseViewHolder);

        if (negotiationWrapper.getNegotiationInfo().getStatus() == NegotiationStatus.SENT_TO_CUSTOMER || negotiationWrapper.getNegotiationInfo().getStatus() == NegotiationStatus.WAITING_FOR_CUSTOMER || negotiationWrapper.getNegotiationInfo().getStatus() == NegotiationStatus.WAITING_FOR_CLOSING) {
//            if(negotiationWrapper.getNegotiationInfo().getStatus() == NegotiationStatus.SENT_TO_CUSTOMER || negotiationWrapper.getNegotiationInfo().getStatus() == NegotiationStatus.WAITING_FOR_CUSTOMER || negotiationWrapper.getNegotiationInfo().getStatus() == NegotiationStatus.WAITING_FOR_CLOSING || (negotiationWrapper.isWalletUser() == false)){
            clauseViewHolder.confirmButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setViewResources(ClauseType type, int position, ClauseViewHolder clauseViewHolder) {
        final int clauseNumber = position + 1;
        final int clauseNumberImageRes = FragmentsCommons.getClauseNumberImageRes(clauseNumber);

        switch (type) {
            case EXCHANGE_RATE:
                clauseViewHolder.setViewResources(R.string.exchange_rate_reference, clauseNumberImageRes);
                break;
            case CUSTOMER_CURRENCY_QUANTITY:
                clauseViewHolder.setViewResources(R.string.cbw_amount_to_sell, clauseNumberImageRes, R.string.selling);
                break;
            case BROKER_CURRENCY_QUANTITY:
                clauseViewHolder.setViewResources(R.string.cbw_amount_to_receive, clauseNumberImageRes, R.string.receiving);
                break;
            case CUSTOMER_PAYMENT_METHOD:
                clauseViewHolder.setViewResources(R.string.payment_methods_title, clauseNumberImageRes, R.string.payment_method);
                break;
            case BROKER_PAYMENT_METHOD:
                clauseViewHolder.setViewResources(R.string.reception_method_title, clauseNumberImageRes, R.string.reception_method);
                break;
            case CUSTOMER_BANK_ACCOUNT:
                clauseViewHolder.setViewResources(R.string.cbw_bank_account_customer, clauseNumberImageRes, R.string.cbw_bank_account_title);
                break;
            case BROKER_BANK_ACCOUNT:
                clauseViewHolder.setViewResources(R.string.cbw_bank_account_broker, clauseNumberImageRes, R.string.cbw_bank_account_title);
                break;
            case CUSTOMER_PLACE_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.cbw_cash_place_to_deliver, clauseNumberImageRes, R.string.selected_location);
                break;
            case BROKER_PLACE_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.cbw_cash_place_to_receive, clauseNumberImageRes, R.string.selected_location);
                break;
            case CUSTOMER_DATE_TIME_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.delivery_date_title, clauseNumberImageRes, R.string.delivery_date_text);
                break;
            case BROKER_DATE_TIME_TO_DELIVER:
                clauseViewHolder.setViewResources(R.string.payment_date_title, clauseNumberImageRes, R.string.payment_date_text);
                break;
        }
    }

    private List<ClauseInformation> buildListOfItems() {
        CustomerBrokerNegotiationInformation negotiationInformation = negotiationWrapper.getNegotiationInfo();

        Map<ClauseType, ClauseInformation> clauses = negotiationInformation.getClauses();

        ClauseInformation receptionMethod = clauses.get(ClauseType.BROKER_PAYMENT_METHOD);
        ClauseInformation brokerPaymentMethodDetail = getBrokerPaymentMethodDetail(clauses);
        ClauseInformation customerReceptionMethodDetail = getCustomerReceptionMethodDetail(clauses);

        final List<ClauseInformation> data = new ArrayList<>();

        data.add(clauses.get(ClauseType.EXCHANGE_RATE));
        data.add(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY));
        data.add(clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY));
        data.add(clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD));
        if (brokerPaymentMethodDetail != null) data.add(brokerPaymentMethodDetail);
        if (receptionMethod != null) data.add(receptionMethod);
        if (customerReceptionMethodDetail != null) data.add(customerReceptionMethodDetail);
        data.add(clauses.get(ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER));
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
