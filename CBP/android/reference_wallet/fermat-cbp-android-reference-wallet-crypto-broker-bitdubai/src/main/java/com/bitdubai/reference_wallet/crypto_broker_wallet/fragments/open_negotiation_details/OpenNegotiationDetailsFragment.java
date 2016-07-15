package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.open_negotiation_details;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_api.layer.world.interfaces.CurrencyHelper;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.common.exceptions.CantSendNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.OpenNegotiationDetailsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs.ClauseDateTimeDialog;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs.TextValueDialog;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.MathUtils;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets.CBP_CRYPTO_BROKER_WALLET;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_BANK_ACCOUNT;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_CURRENCY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_CURRENCY_QUANTITY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_DATE_TIME_TO_DELIVER;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_PLACE_TO_DELIVER;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_CURRENCY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_CURRENCY_QUANTITY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.EXCHANGE_RATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNegotiationDetailsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, ResourceProviderManager>
        implements FooterViewHolder.OnFooterButtonsClickListener, ClauseViewHolder.Listener {

    private static final String TAG = "OpenNegotiationDetails";


    // DATA
    private NegotiationWrapper negotiationWrapper;
    private float spread = 1;

    private String sellType;
    private String reciveType;
    private NumberFormat numberFormat = DecimalFormat.getInstance();


    // Fermat Managers
    private ErrorManager errorManager;
    private OpenNegotiationDetailsAdapter adapter;
    private CryptoBrokerWalletModuleManager moduleManager;

    public static OpenNegotiationDetailsFragment newInstance() {
        return new OpenNegotiationDetailsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            numberFormat.setMaximumFractionDigits(8);

            Object data = appSession.getData(FragmentsCommons.NEGOTIATION_DATA);
            CustomerBrokerNegotiationInformation negotiationInfo = (data != null) ? (CustomerBrokerNegotiationInformation) data : null;
            negotiationWrapper = new NegotiationWrapper(negotiationInfo, appSession);
            boolean walletUser = isCreateIdentityIntraUser(negotiationWrapper.getClauses());
            negotiationWrapper.setWalletUser(walletUser);

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET,
                        DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        configureToolbar();

        final View rootView = inflater.inflate(R.layout.cbw_fragment_open_negotiation_details_activity, container, false);

        final ImageView customerImage = (ImageView) rootView.findViewById(R.id.cbw_customer_image);
        final FermatTextView customerName = (FermatTextView) rootView.findViewById(R.id.cbw_customer_name);
        final FermatTextView buyingDetails = (FermatTextView) rootView.findViewById(R.id.cbw_buying_summary);
        final FermatTextView exchangeRateSummary = (FermatTextView) rootView.findViewById(R.id.cbw_selling_exchange_rate);
        final FermatTextView lastUpdateDate = (FermatTextView) rootView.findViewById(R.id.cbw_last_update_date);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.cbw_open_negotiation_details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        final CustomerBrokerNegotiationInformation negotiationInfo = negotiationWrapper.getNegotiationInfo();

        final ActorIdentity customer = negotiationInfo.getCustomer();
        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

        sellType= CryptoCurrency.codeExists(clauses.get(CUSTOMER_CURRENCY).getValue())?
                PaymentType.CRYPTO_MONEY.getCode() : PaymentType.FIAT_MONEY.getCode();
        reciveType= CryptoCurrency.codeExists(clauses.get(BROKER_CURRENCY).getValue())?
                PaymentType.CRYPTO_MONEY.getCode() : PaymentType.FIAT_MONEY.getCode();

        if(sellType.equals(PaymentType.FIAT_MONEY.getCode())){
            numberFormat.setMaximumFractionDigits(2);
        }else{
            numberFormat.setMaximumFractionDigits(8);
        }

        final String merchandise= clauses.get(CUSTOMER_CURRENCY).getValue();
        final String exchangeAmount =convertToStringFormat(clauses.get(EXCHANGE_RATE).getValue());

        if(reciveType.equals(PaymentType.FIAT_MONEY.getCode())){
            numberFormat.setMaximumFractionDigits(2);
        }else{
            numberFormat.setMaximumFractionDigits(8);
        }

        final String paymentCurrency = clauses.get(BROKER_CURRENCY).getValue();
        final String amount = convertToStringFormat(clauses.get(CUSTOMER_CURRENCY_QUANTITY).getValue());

        //Negotiation Summary
        customerImage.setImageDrawable(getImgDrawable(customer.getProfileImage()));
        customerName.setText(customer.getAlias());
        lastUpdateDate.setText(DateFormat.format("dd MMM yyyy", negotiationInfo.getLastNegotiationUpdateDate()));
        exchangeRateSummary.setText(getResources().getString(R.string.cbw_exchange_rate_summary, merchandise, exchangeAmount, paymentCurrency));
        buyingDetails.setText(getResources().getString(R.string.cbw_buying_details, amount, merchandise));

        adapter = new OpenNegotiationDetailsAdapter(getActivity(), negotiationWrapper);
        adapter.setMarketRateList(getActualExchangeRates());

        if (negotiationWrapper.getNegotiationInfo().getStatus() != NegotiationStatus.SENT_TO_CUSTOMER && negotiationWrapper.getNegotiationInfo().getStatus() != NegotiationStatus.WAITING_FOR_CUSTOMER && negotiationWrapper.getNegotiationInfo().getStatus() != NegotiationStatus.WAITING_FOR_CLOSING) {
            adapter.setFooterListener(this);
            adapter.setClauseListener(this);
        }
        setSuggestedExchangeRateInAdapter(adapter);

        //Get Spread from settings, send it to adapter
        try {
            CryptoBrokerWalletSettingSpread spreadSettings = moduleManager.getCryptoBrokerWalletSpreadSetting(appSession.getAppPublicKey());
            spread = spreadSettings.getSpread();
        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
        adapter.setSpread(spread);

        recyclerView.setAdapter(adapter);

        return rootView;
    }



    private List<IndexInfoSummary> getActualExchangeRates() {
        Object data = appSession.getData(FragmentsCommons.EXCHANGE_RATES);
        return (data != null) ? (List<IndexInfoSummary>) data : null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == FragmentsCommons.CANCEL_NEGOTIATION_OPTION_MENU_ID) {
            TextValueDialog dialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);
            dialog.configure(R.string.cbw_cancel_negotiation, R.string.cbw_reason);
            dialog.setTextFreeInputType(true);
            dialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
                @Override
                public void onClick(String editTextValue) {
                    try {
                        moduleManager.cancelNegotiation(negotiationWrapper.getNegotiationInfo(), editTextValue);
                        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());

                    } catch (FermatException e) {
                        Toast.makeText(getActivity(), "Oopss, an error ocurred", Toast.LENGTH_SHORT).show();
                        if (errorManager != null)
                            errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET,
                                    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        else
                            Log.e(TAG, e.getMessage(), e);
                    }
                }
            });
            dialog.show();
            return true;
        }
        return false;
    }

    @Override
    public void onClauseClicked(Button triggerView, final ClauseInformation clause, int clausePosition) {
        final Map<ClauseType, ClauseInformation> clauses = negotiationWrapper.getNegotiationInfo().getClauses();
        final ClauseType type = clause.getType();

        switch (type) {
            case EXCHANGE_RATE:
                exchangeRateEventAction(clause, clauses);
                break;
            case CUSTOMER_CURRENCY_QUANTITY:
                amountToSellEventAction(clause, clauses);
                break;
            case BROKER_CURRENCY_QUANTITY:
                amountToReceiveEventAction(clause, clauses);
                break;
            case CUSTOMER_PAYMENT_METHOD:
                paymentMethodEventAction(clause, clauses);
                break;
            case BROKER_BANK_ACCOUNT:
                brokerBankAccountEventAction(clause, clauses);
                break;
            case BROKER_PLACE_TO_DELIVER:
                brokerLocationsEventAction(clause);
                break;
            case BROKER_PAYMENT_METHOD:
                Toast.makeText(getActivity(), "This is selected by the Customer", Toast.LENGTH_SHORT).show();
                break;
            case CUSTOMER_BANK_ACCOUNT:
                Toast.makeText(getActivity(), "This is selected by the Customer", Toast.LENGTH_SHORT).show();
                break;
            case CUSTOMER_PLACE_TO_DELIVER:
                Toast.makeText(getActivity(), "This is selected by the Customer", Toast.LENGTH_SHORT).show();
                break;
            case CUSTOMER_DATE_TIME_TO_DELIVER:
                datetimeToPayEventAction(triggerView, clause);
                break;
            case BROKER_DATE_TIME_TO_DELIVER:
                datetimeToDeliverEventAction(triggerView, clause);
                break;
        }
    }

    @Override
    public void onConfirmClauseButtonClicked(ClauseInformation clause) {
        final Map<ClauseType, ClauseInformation> clauses = negotiationWrapper.getNegotiationInfo().getClauses();
        final ClauseType type = clause.getType();


        if (type == EXCHANGE_RATE || type == CUSTOMER_CURRENCY_QUANTITY || type == BROKER_CURRENCY_QUANTITY) {
            try {
                if (numberFormat.parse(clause.getValue()).doubleValue() < 0)
                    Toast.makeText(getActivity(), "The value must be higher than 0", Toast.LENGTH_SHORT).show();
                else {
                    negotiationWrapper.confirmClauseChanges(clause);
                    adapter.changeDataSet(negotiationWrapper);
                }
            } catch (ParseException e) {
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET,
                            DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                else
                    Log.e(TAG, e.getMessage(), e);
            }

        } else if (type == BROKER_BANK_ACCOUNT || type == BROKER_PLACE_TO_DELIVER) {
            if (clause.getValue() != null && !clause.getValue().isEmpty()) {
                negotiationWrapper.confirmClauseChanges(clause);
                adapter.changeDataSet(negotiationWrapper);
            } else {
                String msg = type == BROKER_BANK_ACCOUNT ? "Need to select a bank account" : "Need to select a location";
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }

        } else if (type == CUSTOMER_DATE_TIME_TO_DELIVER) {
            final ClauseInformation paymentDatetime = clauses.get(BROKER_DATE_TIME_TO_DELIVER);
            final long paymentDatetimeValue = Long.parseLong(paymentDatetime.getValue());
            final long deliveryDatetimeValue = Long.parseLong(clause.getValue());
            final Calendar calendar = Calendar.getInstance();

            if (deliveryDatetimeValue < calendar.getTimeInMillis()) {
                Toast.makeText(getActivity(), "Merchandise delivery date must be in the future", Toast.LENGTH_SHORT).show();
            } else if (deliveryDatetimeValue < paymentDatetimeValue) {
                Toast.makeText(getActivity(), "The Merchandise Delivery date must be after the Payment date", Toast.LENGTH_SHORT).show();
            } else {
                negotiationWrapper.confirmClauseChanges(clause);
                adapter.changeDataSet(negotiationWrapper);
            }

        } else if (type == BROKER_DATE_TIME_TO_DELIVER) {
            final ClauseInformation deliverDatetime = clauses.get(CUSTOMER_DATE_TIME_TO_DELIVER);
            final long deliverDatetimeValue = Long.parseLong(deliverDatetime.getValue());
            final long paymentDatetimeValue = Long.parseLong(clause.getValue());
            final Calendar calendar = Calendar.getInstance();

            if (paymentDatetimeValue < calendar.getTimeInMillis()) {
                Toast.makeText(getActivity(), "Payment date must be in the future", Toast.LENGTH_SHORT).show();
            } else if (paymentDatetimeValue > deliverDatetimeValue) {
                Toast.makeText(getActivity(), "The Payment date must be before the Merchandise Delivery Date", Toast.LENGTH_SHORT).show();
            } else {
                negotiationWrapper.confirmClauseChanges(clause);
                adapter.changeDataSet(negotiationWrapper);
            }
        } else {
            negotiationWrapper.confirmClauseChanges(clause);
            adapter.changeDataSet(negotiationWrapper);
        }
    }

    @Override
    public void onAddNoteButtonClicked() {
        final CustomerBrokerNegotiationInformation negotiationInfo = negotiationWrapper.getNegotiationInfo();

        TextValueDialog dialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);
        dialog.configure(R.string.notes, R.string.cbw_insert_note);
        dialog.setTextCount(200);
        dialog.setTextFreeInputType(true);
        dialog.setEditTextValue(negotiationInfo.getMemo());
        dialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
            @Override
            public void onClick(String editTextValue) {
                negotiationInfo.setMemo(editTextValue);
                adapter.changeDataSet(negotiationWrapper);
            }
        });
        dialog.show();
    }

    @Override
    public void onSendButtonClicked() {
        try {

            if (negotiationWrapper.isClausesConfirmed()) {

                if(isCreateIdentityIntraUser(negotiationWrapper.getClauses())) {

                    moduleManager.sendNegotiation(negotiationWrapper.getNegotiationInfo());
                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());

                } else {
                    Toast.makeText(getActivity(), "Need to register THE WALLET USER for user BTC ", Toast.LENGTH_LONG).show();
                }

            } else
                Toast.makeText(getActivity(), "Need to confirm ALL the clauses", Toast.LENGTH_LONG).show();

        } catch (CantSendNegotiationToCryptoCustomerException e) {
            errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET,
                    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (CantSendNegotiationException e){
            errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET,
                    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        Resources res = getResources();

        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }

    private void setSuggestedExchangeRateInAdapter(final OpenNegotiationDetailsAdapter adapter) {
        FermatWorker fermatWorker = new FermatWorker(getActivity()) {
            @Override
            protected Object doInBackground() throws Exception {
                final Map<ClauseType, ClauseInformation> clauses = negotiationWrapper.getClauses();

                final String merchandiseCode = clauses.get(CUSTOMER_CURRENCY).getValue();
                final Currency merchandise = CurrencyHelper.getCurrency(merchandiseCode);

                final String currencyToReceiveCode = clauses.get(BROKER_CURRENCY).getValue();
                final Currency currencyPayment = CurrencyHelper.getCurrency(currencyToReceiveCode);

                return moduleManager.getQuote(merchandise, currencyPayment, appSession.getAppPublicKey());
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {
                    adapter.setMarketRateList(getActualExchangeRates());
                    adapter.setQuote((Quote) result[0]);
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                adapter.setMarketRateList(getActualExchangeRates());
                adapter.setQuote(null);

                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET,
                            DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                else
                    Log.e(TAG, ex.getMessage(), ex);
            }
        });

        fermatWorker.execute();
    }

    private void exchangeRateEventAction(final ClauseInformation clause, final Map<ClauseType, ClauseInformation> clauses) {
        TextValueDialog clauseTextDialog;
        clauseTextDialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);
        clauseTextDialog.setEditTextValue(clause.getValue());
        clauseTextDialog.configure(R.string.cbw_your_exchange_rate, R.string.amount);
        clauseTextDialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
            @Override
            public void onClick(String newValue) {
                //lostwood
          /*      final BigDecimal exchangeRate = MathUtils.getBigDecimal(newValue);
                final BigDecimal amountToSell = MathUtils.getBigDecimal(clauses.get(CUSTOMER_CURRENCY_QUANTITY));
                final double amountToReceiveValue = exchangeRate.multiply(amountToSell).doubleValue();*/

                if (reciveType.equals(PaymentType.FIAT_MONEY.getCode())) {
                    numberFormat.setMaximumFractionDigits(2);
                } else {
                    numberFormat.setMaximumFractionDigits(8);
                }
                final BigDecimal exchangeRate = convertToBigDecimal(newValue);

                if (sellType.equals(PaymentType.FIAT_MONEY.getCode())) {
                    numberFormat.setMaximumFractionDigits(2);
                } else {
                    numberFormat.setMaximumFractionDigits(8);
                }

                final BigDecimal amountToSell = convertToBigDecimal(String.valueOf(clauses.get(CUSTOMER_CURRENCY_QUANTITY).getValue()));
                final BigDecimal amountToReceiveValue = exchangeRate.multiply(amountToSell);
                final ClauseInformation amountToReceiveClause = clauses.get(BROKER_CURRENCY_QUANTITY);

                if (reciveType.equals(PaymentType.FIAT_MONEY.getCode())) {
                    numberFormat.setMaximumFractionDigits(2);
                } else {
                    numberFormat.setMaximumFractionDigits(8);
                }

                negotiationWrapper.changeClauseValue(clause, newValue);
                negotiationWrapper.changeClauseValue(amountToReceiveClause, convertToStringFormat(String.valueOf(amountToReceiveValue)));

                adapter.changeDataSet(negotiationWrapper);


                //BigDecimal marketRateReferenceValue = getMarketRateValue(clauses);
                //BigDecimal suggestedMaxExchangeRate = new BigDecimal(marketRateReferenceValue.doubleValue() * (1 + (spread / 100)));


                BigDecimal marketRateReferenceValue = convertToBigDecimal(String.valueOf(getMarketRateValue(clauses)));
                BigDecimal suggestedMaxExchangeRate = convertToBigDecimal(String.valueOf(marketRateReferenceValue.doubleValue() * (1 + (spread / 100))));

                if (exchangeRate.compareTo(suggestedMaxExchangeRate) == 1)
                    Toast.makeText(

                            getActivity(),

                            "Warning: Selected Rate is higher than suggested!", Toast.LENGTH_LONG).

                            show();

                if (exchangeRate.compareTo(marketRateReferenceValue) == -1)
                    Toast.makeText(

                            getActivity(),

                            "Warning: Selected Rate is lower than suggested!", Toast.LENGTH_LONG).

                            show();

            }
        });
        clauseTextDialog.show();
    }

    private void amountToSellEventAction(final ClauseInformation clause, final Map<ClauseType, ClauseInformation> clauses) {
        TextValueDialog clauseTextDialog;
        clauseTextDialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);
        clauseTextDialog.setEditTextValue(clause.getValue());
        clauseTextDialog.configure(R.string.cbw_amount_to_sell, R.string.cbw_value);
        clauseTextDialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
            @Override
            public void onClick(String newValue) {
                //lostwood
               /* final BigDecimal amountToSell = MathUtils.getBigDecimal(newValue);
                final BigDecimal exchangeRate = MathUtils.getBigDecimal(clauses.get(EXCHANGE_RATE));

                final double amountToReceiveValue = exchangeRate.multiply(amountToSell).doubleValue();*/

                if(sellType.equals(PaymentType.FIAT_MONEY.getCode())){
                    numberFormat.setMaximumFractionDigits(2);
                }else{
                    numberFormat.setMaximumFractionDigits(8);
                }

                final BigDecimal amountToSell = convertToBigDecimal(newValue);
                final BigDecimal exchangeRate = convertToBigDecimal(String.valueOf(clauses.get(EXCHANGE_RATE).getValue()));

                final BigDecimal amountToReceiveValue = convertToBigDecimal(String.valueOf(exchangeRate.multiply(amountToSell)));
                final ClauseInformation amountToReceiveClause = clauses.get(BROKER_CURRENCY_QUANTITY);


                negotiationWrapper.changeClauseValue(clause, newValue);
                negotiationWrapper.changeClauseValue(amountToReceiveClause, convertToStringFormat(String.valueOf(amountToReceiveValue)));

                adapter.changeDataSet(negotiationWrapper);
            }
        });
        clauseTextDialog.show();
    }

    private void amountToReceiveEventAction(final ClauseInformation clause, final Map<ClauseType, ClauseInformation> clauses) {
        TextValueDialog clauseTextDialog;
        clauseTextDialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);
        clauseTextDialog.setEditTextValue(clause.getValue());
        clauseTextDialog.configure(R.string.cbw_amount_to_receive, R.string.cbw_value);
        clauseTextDialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
            @Override
            public void onClick(String newValue) {

                if(reciveType.equals(PaymentType.FIAT_MONEY.getCode())){
                    numberFormat.setMaximumFractionDigits(2);
                }else{
                    numberFormat.setMaximumFractionDigits(8);
                }

              /*  final BigDecimal amountToReceive = MathUtils.getBigDecimal(newValue);
                final BigDecimal exchangeRate = MathUtils.getBigDecimal(clauses.get(EXCHANGE_RATE));
                final double amountToSellValue = amountToReceive.divide(exchangeRate, 8, RoundingMode.HALF_UP).doubleValue();*/


                final BigDecimal amountToReceive = convertToBigDecimal(newValue);
                final BigDecimal exchangeRate = convertToBigDecimal(String.valueOf(clauses.get(EXCHANGE_RATE).getValue()));
                final BigDecimal amountToSellValue = convertToBigDecimal(String.valueOf(amountToReceive.divide(exchangeRate, 8, RoundingMode.HALF_UP).doubleValue()));
                final ClauseInformation amountToSellClause = clauses.get(CUSTOMER_CURRENCY_QUANTITY);



                negotiationWrapper.changeClauseValue(clause, newValue);
                negotiationWrapper.changeClauseValue(amountToSellClause, convertToStringFormat(String.valueOf(amountToSellValue)));

                adapter.changeDataSet(negotiationWrapper);
            }
        });
        clauseTextDialog.show();
    }

    private void paymentMethodEventAction(final ClauseInformation clause, final Map<ClauseType, ClauseInformation> clauses) {
        try {
            SimpleListDialogFragment<MoneyType> dialogFragment;
            ClauseInformation brokerCurrency = clauses.get(BROKER_CURRENCY);
            List<MoneyType> paymentMethods = moduleManager.getPaymentMethods(brokerCurrency.getValue(), appSession.getAppPublicKey());

            dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("Payment Methods", paymentMethods);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<MoneyType>() {
                @Override
                public void onItemSelected(MoneyType selectedPaymentMethod) {
                    negotiationWrapper.changeClauseValue(clause, selectedPaymentMethod.getCode());

                    switch (selectedPaymentMethod) {
                        case CRYPTO:
                            clauses.remove(BROKER_BANK_ACCOUNT);
                            clauses.remove(BROKER_PLACE_TO_DELIVER);
                            break;
                        case BANK:
                            clauses.remove(BROKER_PLACE_TO_DELIVER);

                            if (clauses.get(BROKER_BANK_ACCOUNT) == null) {
                                List<String> bankAccounts;
                                try {
                                    final String currencyToReceive = clauses.get(BROKER_CURRENCY).getValue();
                                    bankAccounts = moduleManager.getAccounts(currencyToReceive, appSession.getAppPublicKey());
                                } catch (FermatException e) {
                                    bankAccounts = new ArrayList<>();
                                }
                                final String bankAccount = bankAccounts.isEmpty() ? "" : bankAccounts.get(0);
                                negotiationWrapper.addClause(BROKER_BANK_ACCOUNT, bankAccount);
                            }
                            break;
                        default:
                            clauses.remove(BROKER_BANK_ACCOUNT);

                            if (clauses.get(BROKER_PLACE_TO_DELIVER) == null) {
                                List<NegotiationLocations> locations;
                                try {
                                    locations = Lists.newArrayList(moduleManager.getAllLocations(NegotiationType.SALE));
                                } catch (FermatException e) {
                                    locations = new ArrayList<>();
                                }
                                final String location = locations.isEmpty() ? "" : locations.get(0).getLocation();
                                negotiationWrapper.addClause(BROKER_PLACE_TO_DELIVER, location);
                            }
                    }

                    adapter.changeDataSet(negotiationWrapper);
                }
            });
            dialogFragment.show(getFragmentManager(), "paymentMethodsDialog");

        } catch (FermatException ex) {
            errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET,
                    DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    private void brokerLocationsEventAction(final ClauseInformation clause) {
        List<NegotiationLocations> locations;

        try {
            locations = Lists.newArrayList(moduleManager.getAllLocations(NegotiationType.SALE));
        } catch (FermatException e) {
            locations = new ArrayList<>();
        }

        if (locations.isEmpty())
            Toast.makeText(getActivity(), "You don't have Locations. Add one in the Wallet Settings.", Toast.LENGTH_LONG).show();
        else {
            final SimpleListDialogFragment<NegotiationLocations> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("placeToDelivery", locations);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<NegotiationLocations>() {
                @Override
                public void onItemSelected(NegotiationLocations newValue) {
                    negotiationWrapper.changeClauseValue(clause, newValue.getLocation());
                    adapter.changeDataSet(negotiationWrapper);
                }
            });
            dialogFragment.show(getFragmentManager(), "placeToDeliveryDialog");
        }
    }

    private void brokerBankAccountEventAction(final ClauseInformation clause, Map<ClauseType, ClauseInformation> clauses) {
        final String currencyToReceive = clauses.get(BROKER_CURRENCY).getValue();
        List<String> bankAccounts;

        try {
            bankAccounts = moduleManager.getAccounts(currencyToReceive, appSession.getAppPublicKey());
        } catch (FermatException e) {
            bankAccounts = new ArrayList<>();
        }

        if (bankAccounts.isEmpty())
            Toast.makeText(getActivity(), "You don't have Bank Accounts. Add one in the Wallet Settings.", Toast.LENGTH_LONG).show();
        else {
            final SimpleListDialogFragment<String> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("bankAccount", bankAccounts);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<String>() {
                @Override
                public void onItemSelected(String newValue) {
                    negotiationWrapper.changeClauseValue(clause, newValue);
                    adapter.changeDataSet(negotiationWrapper);
                }
            });
            dialogFragment.show(getFragmentManager(), "bankAccountDialog");
        }
    }

    private void datetimeToDeliverEventAction(Button triggerView, final ClauseInformation clause) {
        ClauseDateTimeDialog clauseDateTimeDialog;
        clauseDateTimeDialog = new ClauseDateTimeDialog(getActivity(), Long.valueOf(clause.getValue()));
        if (triggerView.getId() == R.id.cbw_date_value)
            clauseDateTimeDialog.showDateDialog();
        else
            clauseDateTimeDialog.showTimeDialog();

        clauseDateTimeDialog.setAcceptBtnListener(new ClauseDateTimeDialog.OnClickAcceptListener() {
            @Override
            public void getDate(long newValue) {
                negotiationWrapper.changeClauseValue(clause, Long.toString(newValue));
                adapter.changeDataSet(negotiationWrapper);
            }
        });
    }

    private void datetimeToPayEventAction(Button triggerView, final ClauseInformation clause) {
        ClauseDateTimeDialog clauseDateTimeDialog;
        clauseDateTimeDialog = new ClauseDateTimeDialog(getActivity(), Long.valueOf(clause.getValue()));
        if (triggerView.getId() == R.id.cbw_date_value)
            clauseDateTimeDialog.showDateDialog();
        else
            clauseDateTimeDialog.showTimeDialog();

        clauseDateTimeDialog.setAcceptBtnListener(new ClauseDateTimeDialog.OnClickAcceptListener() {
            @Override
            public void getDate(long newValue) {
                negotiationWrapper.changeClauseValue(clause, Long.toString(newValue));
                adapter.changeDataSet(negotiationWrapper);
            }
        });
    }


    private BigDecimal getMarketRateValue(Map<ClauseType, ClauseInformation> clauses) {

        String currencyOver = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
        String currencyUnder = clauses.get(ClauseType.BROKER_CURRENCY).getValue();

        BigDecimal exchangeRate = new BigDecimal(0);

        ExchangeRate currencyQuotation = getExchangeRate(currencyOver, currencyUnder);

        if (currencyQuotation == null) {
            currencyQuotation = getExchangeRate(currencyUnder, currencyOver);
            if (currencyQuotation != null) {
                exchangeRate = new BigDecimal(currencyQuotation.getSalePrice());
                exchangeRate = (new BigDecimal(1)).divide(exchangeRate, 8, RoundingMode.HALF_UP);
            }
        } else {
            exchangeRate = new BigDecimal(currencyQuotation.getSalePrice());
        }

        return exchangeRate;
    }

    private ExchangeRate getExchangeRate(String currencyAlfa, String currencyBeta) {

        final List<IndexInfoSummary> actualExchangeRates = getActualExchangeRates();

        if (actualExchangeRates != null)
            for (IndexInfoSummary item : actualExchangeRates) {
                final ExchangeRate exchangeRateData = item.getExchangeRateData();
                final String toCurrency = exchangeRateData.getToCurrency().getCode();
                final String fromCurrency = exchangeRateData.getFromCurrency().getCode();

                if (fromCurrency.equals(currencyAlfa) && toCurrency.equals(currencyBeta))
                    return exchangeRateData;
            }

        return null;
    }

    private boolean isCreateIdentityIntraUser(Map<ClauseType, ClauseInformation> clauses) throws CantSendNegotiationException {

        String customerCurrency = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
        String brokerCurrency   = clauses.get(ClauseType.BROKER_CURRENCY).getValue();
        String currencyBTC      = "BTC";

        if(customerCurrency != null){
            if(currencyBTC.equals(customerCurrency)) return moduleManager.isCreateIdentityIntraUser();
        }

        if(brokerCurrency != null){
            if(currencyBTC.equals(brokerCurrency)) return moduleManager.isCreateIdentityIntraUser();
        }

        return true;
    }

    private BigDecimal convertToBigDecimal(String value){

        BigDecimal convertion=new BigDecimal(0);
        try {
            convertion = new BigDecimal(String.valueOf(numberFormat.parse(numberFormat.format(Double.valueOf(value)))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertion;
    }

    private String convertToStringFormat(String value){

        String convertion="0";
        try {
            convertion = String.valueOf(new BigDecimal(String.valueOf(numberFormat.parse(numberFormat.format(Double.valueOf(value))))));;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertion;
    }
}
