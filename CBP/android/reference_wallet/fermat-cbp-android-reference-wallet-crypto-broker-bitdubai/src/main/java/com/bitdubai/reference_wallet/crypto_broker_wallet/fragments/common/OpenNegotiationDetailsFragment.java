package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;


import android.app.DialogFragment;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNegotiationDetailsFragment extends FermatWalletFragment {
    private static final String TAG = "OpenNegotiationDetails";
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

    // UI
    private ImageView customerImage;
    private FermatTextView buyingDetails;
    private FermatTextView exchangeRateSummary;
    private FermatTextView customerName;
    private FermatTextView lastUpdateDate;
    private Button paymentDateValue;
    private Button paymentTimeValue;
    private Button deliveryDateValue;
    private Button deliveryTimeValue;
    private Button expirationDateValue;
    private Button expirationTimeValue;
    private FermatTextView exchangeRateReference;
    private CardView exchangeRateCardView;
    private FermatTextView markerRateReference;
    private FermatTextView yourExchangeRateValueLeftSide;
    private FermatTextView yourExchangeRateValueRightSide;
    private FermatEditText yourExchangeRateValue;
    private CardView amountToSellCardView;
    private CardView brokerLocationsCardView;
    private CardView brokerBankAccountsCardView;
    private CardView customerLocationsCardView;
    private CardView customerBankAccountsCardView;
    private FermatEditText sellingValue;
    private FermatTextView currencyToSell;
    private FermatTextView youWillReceiveValue;
    private CardView merchandiseDeliveryCardView;
    private Button merchandiseDeliveryValue;
    private Button brokerLocationsValue;
    private Button brokerBankAccountsValue;
    private Button customerLocationsValue;
    private Button customerBankAccountsValue;
    private CardView paymentDateCardView;
    private CardView deliveryDateCardView;
    private CardView negotiationExpirationTimeCardView;
    private CardView negotiationNoteCardView;
    private FermatTextView noteText;

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private CryptoBrokerWallet walletManager;
    private ErrorManager errorManager;


    public static OpenNegotiationDetailsFragment newInstance() {
        return new OpenNegotiationDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CryptoBrokerWalletSession) walletSession).getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(walletSession.getAppPublicKey());

            errorManager = walletSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cbw_fragment_negotiation_details_activity, container, false);

        configureToolbar();
        initViews(rootView);
        bindData();

        return rootView;
    }

    private void initViews(View rootView) {

        // header con summary
        customerImage = (ImageView) rootView.findViewById(R.id.cbw_customer_image);
        buyingDetails = (FermatTextView) rootView.findViewById(R.id.cbw_buying_details);
        exchangeRateSummary = (FermatTextView) rootView.findViewById(R.id.cbw_exchange_rate_summary);
        customerName = (FermatTextView) rootView.findViewById(R.id.cbw_customer_name);
        lastUpdateDate = (FermatTextView) rootView.findViewById(R.id.cbw_last_update_date);

        negotiationNoteCardView = (CardView) rootView.findViewById(R.id.negotiation_note_card_view);
        noteText = (FermatTextView) rootView.findViewById(R.id.note_text);

        // Clause EXCHANGE_RATE
        exchangeRateCardView = (CardView) rootView.findViewById(R.id.exchange_rate_card_view);
        exchangeRateReference = (FermatTextView) rootView.findViewById(R.id.exchange_rate_reference_value);
        markerRateReference = (FermatTextView) rootView.findViewById(R.id.market_rate_reference_value);
        yourExchangeRateValueLeftSide = (FermatTextView) rootView.findViewById(R.id.your_exchange_rate_value_left_side);
        yourExchangeRateValueRightSide = (FermatTextView) rootView.findViewById(R.id.your_exchange_rate_value_right_side);
        yourExchangeRateValue = (FermatEditText) rootView.findViewById(R.id.your_exchange_rate_value);

        // Clause CUSTOMER_CURRENCY_QUANTITY
        amountToSellCardView = (CardView) rootView.findViewById(R.id.amount_to_sell_card_view);
        sellingValue = (FermatEditText) rootView.findViewById(R.id.selling_value);
        currencyToSell = (FermatTextView) rootView.findViewById(R.id.currency_to_sell);
        youWillReceiveValue = (FermatTextView) rootView.findViewById(R.id.you_will_receive_value);

        // Clause BROKER_PAYMENT_METHOD
        merchandiseDeliveryCardView = (CardView) rootView.findViewById(R.id.merchandise_delivery_card_view);
        merchandiseDeliveryValue = (Button) rootView.findViewById(R.id.merchandise_delivery_value);
        merchandiseDeliveryValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(), "To implement..", Toast.LENGTH_SHORT).show();
            }
        });

        // Clause BROKER_PLACE_TO_DELIVER
        brokerLocationsCardView = (CardView) rootView.findViewById(R.id.broker_locations_card_view);
        brokerLocationsValue = (Button) rootView.findViewById(R.id.broker_locations_value);
        brokerLocationsValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(), "To implement..", Toast.LENGTH_SHORT).show();
            }
        });

        // Clause BROKER_BANK_ACCOUNT
        brokerBankAccountsCardView = (CardView) rootView.findViewById(R.id.broker_bank_accounts_card_view);
        brokerBankAccountsValue = (Button) rootView.findViewById(R.id.broker_bank_accounts_value);
        brokerBankAccountsValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(), "To implement..", Toast.LENGTH_SHORT).show();
            }
        });

        // Clause CUSTOMER_PLACE_TO_DELIVER
        customerLocationsCardView = (CardView) rootView.findViewById(R.id.customer_locations_card_view);
        customerLocationsValue = (Button) rootView.findViewById(R.id.customer_locations_value);
        customerLocationsValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(), "To implement..", Toast.LENGTH_SHORT).show();
            }
        });

        // Clause CUSTOMER_BANK_ACCOUNT
        customerBankAccountsCardView = (CardView) rootView.findViewById(R.id.customer_bank_accounts_card_view);
        customerBankAccountsValue = (Button) rootView.findViewById(R.id.customer_bank_accounts_value);
        customerBankAccountsValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(), "To implement..", Toast.LENGTH_SHORT).show();
            }
        });

        // Clause BROKER_DATE_TIME_TO_DELIVER
        paymentDateCardView = (CardView) rootView.findViewById(R.id.payment_date_card_view);
        paymentDateValue = (Button) rootView.findViewById(R.id.payment_date_value);
        paymentTimeValue = (Button) rootView.findViewById(R.id.payment_time_value);
        paymentDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = DatePickerFragment.getNewInstance(paymentDateValue);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });
        paymentTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = TimePickerFragment.getNewInstance(paymentTimeValue);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });

        // Clause CUSTOMER_DATE_TIME_TO_DELIVER
        deliveryDateCardView = (CardView) rootView.findViewById(R.id.delivery_date_card_view);
        deliveryDateValue = (Button) rootView.findViewById(R.id.delivery_date_value);
        deliveryTimeValue = (Button) rootView.findViewById(R.id.delivery_time_value);
        deliveryDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = DatePickerFragment.getNewInstance(deliveryDateValue);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });
        deliveryTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = TimePickerFragment.getNewInstance(deliveryTimeValue);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });

        // Clause CUSTOMER_PLACE_TO_DELIVER
        negotiationExpirationTimeCardView = (CardView) rootView.findViewById(R.id.negotiation_expiration_time_card_view);
        expirationDateValue = (Button) rootView.findViewById(R.id.expiration_date_value);
        expirationTimeValue = (Button) rootView.findViewById(R.id.expiration_time_value);
        expirationDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = DatePickerFragment.getNewInstance(expirationDateValue);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });
        expirationTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = TimePickerFragment.getNewInstance(expirationTimeValue);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });


        CardView addNoteCardView = (CardView) rootView.findViewById(R.id.add_a_note_card_view);
        addNoteCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(), "To implement..", Toast.LENGTH_SHORT).show();
            }
        });

        FermatTextView sendButton = (FermatTextView) rootView.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, walletSession.getAppPublicKey());
            }
        });
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    private void bindData() {
        CryptoBrokerWalletSession session = (CryptoBrokerWalletSession) walletSession;
        CustomerBrokerNegotiationInformation data = (CustomerBrokerNegotiationInformation) session.getData(CryptoBrokerWalletSession.NEGOTIATION_DATA);

        ActorIdentity customer = data.getCustomer();

        String exchangeRateAmount = getClauseValue(data, ClauseType.EXCHANGE_RATE);
        String currencyToSell = getClauseValue(data, ClauseType.CUSTOMER_CURRENCY);
        String amountToBuy = getClauseValue(data, ClauseType.CUSTOMER_CURRENCY_QUANTITY);
        String currencyToReceive = getClauseValue(data, ClauseType.BROKER_CURRENCY);
        String dateToPay = getClauseValue(data, ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER);
        String dateToDeliver = getClauseValue(data, ClauseType.BROKER_DATE_TIME_TO_DELIVER);

        //Negotiation Summary
        customerImage.setImageDrawable(getImgDrawable(customer.getProfileImage()));
        customerName.setText(customer.getAlias());
        exchangeRateSummary.setText(getExchangeRateAmountAndCurrencyText(data));
        buyingDetails.setText(getBuyingQuantityAndCurrencyText(data));
        lastUpdateDate.setText(DateFormat.format("dd MMM yyyy", data.getLastUpdate()));

        // Clause EXCHANGE_RATE
        exchangeRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToSell, decimalFormat.format(215.25), currencyToReceive));
        markerRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToSell, decimalFormat.format(212.48), currencyToReceive));
        yourExchangeRateValueLeftSide.setText(String.format("1 %1$s", currencyToSell));
        yourExchangeRateValue.setText(exchangeRateAmount);
        yourExchangeRateValueRightSide.setText(String.format("%1$s", currencyToReceive));

        // Clause CUSTOMER_CURRENCY_QUANTITY
        sellingValue.setText(amountToBuy);
        this.currencyToSell.setText(currencyToSell);
        double amountTouBuyDoubleVal = Double.parseDouble(amountToBuy);
        double exchangeRateAmountDoubleVal = Double.parseDouble(exchangeRateAmount);
        String productValStr = decimalFormat.format(amountTouBuyDoubleVal * exchangeRateAmountDoubleVal);
        youWillReceiveValue.setText(String.format("%1s %2s", productValStr, currencyToReceive));

        // Clause CUSTOMER_DATE_TIME_TO_DELIVER
        paymentDateValue.setText(dateToPay);
        paymentTimeValue.setText("9:35");

        // Clause BROKER_DATE_TIME_TO_DELIVER
        deliveryDateValue.setText(dateToDeliver);
        deliveryTimeValue.setText("11:30");

        expirationDateValue.setText(DateFormat.format("dd MMM yyyy", System.currentTimeMillis()));
        expirationTimeValue.setText(DateFormat.format("HH:mm", System.currentTimeMillis()));
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        Resources res = getResources();

        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }

    @NonNull
    private String getExchangeRateAmountAndCurrencyText(CustomerBrokerNegotiationInformation itemInfo) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        String merchandise = getClauseValue(itemInfo, ClauseType.CUSTOMER_CURRENCY);
        String exchangeAmount = decimalFormat.format(getClauseValue(itemInfo, ClauseType.EXCHANGE_RATE));
        String paymentCurrency = getClauseValue(itemInfo, ClauseType.BROKER_CURRENCY);

        return getResources().getString(R.string.cbw_exchange_rate_amount_and_currency, merchandise, exchangeAmount, paymentCurrency);
    }

    @NonNull
    private String getBuyingQuantityAndCurrencyText(CustomerBrokerNegotiationInformation itemInfo) {
        Map<ClauseType, String> negotiationSummary = itemInfo.getNegotiationSummary();
        String amount = decimalFormat.format(negotiationSummary.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY));
        String merchandise = negotiationSummary.get(ClauseType.CUSTOMER_CURRENCY);

        return String.format("Buying %1$s %2$s", amount, merchandise);
    }

    private String getClauseValue(CustomerBrokerNegotiationInformation data, final ClauseType type) {
        Collection<ClauseInformation> clauses = data.getClauses();
        for (ClauseInformation clause : clauses)
            if (clause.getType().equals(type))
                return clause.getValue();

        return null;
    }
}
