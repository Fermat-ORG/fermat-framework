package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.open_negotiation_details;


import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListLocationsSaleException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.NewOpenNegotiationDetailsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs.ClauseDateTimeDialog;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs.ClauseTextDialog;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.ExpirationTimeViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNegotiationDetailsFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager>
        implements FooterViewHolder.OnFooterButtonsClickListener, ClauseViewHolder.Listener, ExpirationTimeViewHolder.Listener {

    private static final String TAG = "OpenNegotiationDetails";

    // UI
    private ImageView customerImage;
    private FermatTextView buyingDetails;
    private FermatTextView exchangeRateSummary;
    private FermatTextView customerName;
    private FermatTextView lastUpdateDate;
    private RecyclerView recyclerView;

    // DATA
    private NegotiationWrapper negotiationWrapper;

    // Fermat Managers
    private CryptoBrokerWalletManager walletManager;
    private ErrorManager errorManager;
    private NewOpenNegotiationDetailsAdapter adapter;
    private boolean valuesHasChanged;


    public static OpenNegotiationDetailsFragment newInstance() {
        return new OpenNegotiationDetailsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            CryptoBrokerWalletModuleManager moduleManager = appSession.getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            CustomerBrokerNegotiationInformation negotiationInfo = appSession.getNegotiationData();
            negotiationWrapper = new NegotiationWrapper(negotiationInfo);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cbw_fragment_open_negotiation_details_activity, container, false);

        configureToolbar();
        initViews(rootView);
        bindData();

        return rootView;
    }

    @Override
    public void onClauseClicked(Button triggerView, final ClauseInformation clause, int clausePosition) {
        final Map<ClauseType, ClauseInformation> clauses = negotiationWrapper.getNegotiationInformation().getClauses();
        final ClauseType type = clause.getType();

        SimpleListDialogFragment dialogFragment;
        ClauseTextDialog clauseTextDialog;
        ClauseDateTimeDialog clauseDateTimeDialog;

        switch (type) {

            case EXCHANGE_RATE:
                clauseTextDialog = new ClauseTextDialog(getActivity(), appSession, appResourcesProviderManager);
                clauseTextDialog.setEditTextValue(clause.getValue());
                clauseTextDialog.configure(R.string.cbw_your_exchange_rate, R.string.amount);
                clauseTextDialog.setAcceptBtnListener(new ClauseTextDialog.OnClickAcceptListener() {
                    @Override
                    public void onClick(String newValue) {
                        actionListenerBrokerCurrencyQuantity(clause, newValue);
                    }
                });

                clauseTextDialog.show();
                break;

            case CUSTOMER_CURRENCY_QUANTITY:
                clauseTextDialog = new ClauseTextDialog(getActivity(), appSession, appResourcesProviderManager);
                clauseTextDialog.setEditTextValue(clause.getValue());
                clauseTextDialog.configure(R.string.cbw_amount_to_sell, R.string.cbw_value);
                clauseTextDialog.setAcceptBtnListener(new ClauseTextDialog.OnClickAcceptListener() {
                    @Override
                    public void onClick(String newValue) {
                        actionListenerBrokerCurrencyQuantity(clause, newValue);
                    }
                });

                clauseTextDialog.show();
                break;

            case BROKER_CURRENCY_QUANTITY:
                clauseTextDialog = new ClauseTextDialog(getActivity(), appSession, appResourcesProviderManager);
                clauseTextDialog.setEditTextValue(clause.getValue());
                clauseTextDialog.configure(R.string.cbw_amount_to_receive, R.string.cbw_value);
                clauseTextDialog.setAcceptBtnListener(new ClauseTextDialog.OnClickAcceptListener() {
                    @Override
                    public void onClick(String newValue) {
                        actionListenerBrokerCurrencyQuantity(clause, newValue);
                    }
                });

                clauseTextDialog.show();
                break;

            case CUSTOMER_PAYMENT_METHOD:
                try {
                    ClauseInformation brokerCurrency = clauses.get(ClauseType.BROKER_CURRENCY);
                    List<String> paymentMethods = walletManager.getPaymentMethods(brokerCurrency.getValue(), appSession.getAppPublicKey());

                    dialogFragment = new SimpleListDialogFragment<>();
                    dialogFragment.configure("Payment Methods", paymentMethods);
                    dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<String>() {
                        @Override
                        public void onItemSelected(String newValue) {
                            // actionListenerCustomerPaymentMethod(clause, newValue);
                        }
                    });

                    dialogFragment.show(getFragmentManager(), "paymentMethodsDialog");

                } catch (FermatException e) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
                break;

            case BROKER_BANK_ACCOUNT:
                try {
                    List<BankAccountNumber> bankAccounts = walletManager.getAccounts(appSession.getAppPublicKey());
                    if (bankAccounts.size() > 0) {
                        dialogFragment = new SimpleListDialogFragment<>();
                        dialogFragment.configure("bankAccount", bankAccounts);
                        dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<BankAccountNumber>() {
                            @Override
                            public void onItemSelected(BankAccountNumber newValue) {
                                // putClause(clause, newValue.getAccount(), getStatusClauseChange(clause.getStatus().getCode()));
                                adapter.changeDataSet(negotiationWrapper);
                            }
                        });

                        dialogFragment.show(getFragmentManager(), "bankAccountDialog");

                    } else
                        Toast.makeText(getActivity(), "The Bank Account List is Empty. Add Your Bank Account in the Wallet Settings.", Toast.LENGTH_LONG).show();

                } catch (CantLoadBankMoneyWalletException e) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
                break;

            case BROKER_PLACE_TO_DELIVER:
                try {
                    List<NegotiationLocations> locations = new ArrayList<>();
                    locations.addAll(walletManager.getAllLocations(NegotiationType.SALE));
                    if (locations.size() > 0) {
                        dialogFragment = new SimpleListDialogFragment<>();
                        dialogFragment.configure("placeToDelivery", locations);
                        dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<String>() {
                            @Override
                            public void onItemSelected(String newValue) {
                                // putClause(clause, newValue, getStatusClauseChange(clause.getStatus().getCode()));
                                adapter.changeDataSet(negotiationWrapper);
                            }
                        });
                        dialogFragment.show(getFragmentManager(), "placeToDeliveryDialog");

                    } else
                        Toast.makeText(getActivity(), "The Locations List is Empty. Add Your Locations in the Settings Wallet.", Toast.LENGTH_LONG).show();

                } catch (CantGetListLocationsPurchaseException | CantGetListLocationsSaleException e) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
                break;

            case CUSTOMER_DATE_TIME_TO_DELIVER:
                clauseDateTimeDialog = new ClauseDateTimeDialog(getActivity(), Long.valueOf(clause.getValue()));
                if (triggerView.getId() == R.id.cbw_date_value)
                    clauseDateTimeDialog.showDateDialog();
                else
                    clauseDateTimeDialog.showTimeDialog();

                clauseDateTimeDialog.setAcceptBtnListener(new ClauseDateTimeDialog.OnClickAcceptListener() {
                    @Override
                    public void getDate(long newValue) {
                        // actionListenerDatetime(clause, String.valueOf(newValue));
                    }
                });
                break;

            case BROKER_DATE_TIME_TO_DELIVER:
                clauseDateTimeDialog = new ClauseDateTimeDialog(getActivity(), Long.valueOf(clause.getValue()));
                if (triggerView.getId() == R.id.cbw_date_value)
                    clauseDateTimeDialog.showDateDialog();
                else
                    clauseDateTimeDialog.showTimeDialog();

                clauseDateTimeDialog.setAcceptBtnListener(new ClauseDateTimeDialog.OnClickAcceptListener() {
                    @Override
                    public void getDate(long newValue) {
                        // actionListenerDatetime(clause, String.valueOf(newValue));
                    }
                });

                break;
        }
    }

    @Override
    public void onValueClicked(Button triggerView) {

    }

    @Override
    public void onConfirmButtonClicked(ClauseInformation clause) {

    }

    private void initViews(View rootView) {

        customerImage = (ImageView) rootView.findViewById(R.id.cbw_customer_image);
        customerName = (FermatTextView) rootView.findViewById(R.id.cbw_customer_name);
        buyingDetails = (FermatTextView) rootView.findViewById(R.id.cbw_buying_summary);
        exchangeRateSummary = (FermatTextView) rootView.findViewById(R.id.cbw_selling_exchange_rate);
        lastUpdateDate = (FermatTextView) rootView.findViewById(R.id.cbw_last_update_date);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cbw_open_negotiation_details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
        CustomerBrokerNegotiationInformation negotiationInfo = negotiationWrapper.getNegotiationInformation();
        ActorIdentity customer = negotiationInfo.getCustomer();
        Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

        String merchandise = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
        String exchangeAmount = clauses.get(ClauseType.EXCHANGE_RATE).getValue();
        String paymentCurrency = clauses.get(ClauseType.BROKER_CURRENCY).getValue();
        String amount = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue();

        //Negotiation Summary
        customerImage.setImageDrawable(getImgDrawable(customer.getProfileImage()));
        customerName.setText(customer.getAlias());
        lastUpdateDate.setText(DateFormat.format("dd MMM yyyy", negotiationInfo.getLastNegotiationUpdateDate()));
        exchangeRateSummary.setText(getResources().getString(R.string.cbw_exchange_rate_summary, merchandise, exchangeAmount, paymentCurrency));
        buyingDetails.setText(getResources().getString(R.string.cbw_buying_details, amount, merchandise));

        adapter = new NewOpenNegotiationDetailsAdapter(getActivity(), negotiationWrapper);
        adapter.setMarketRateList(appSession.getActualExchangeRates());
        adapter.setFooterListener(this);
        adapter.setClauseListener(this);

        recyclerView.setAdapter(adapter);
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        Resources res = getResources();

        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }

    @Override
    public void onAddNoteButtonClicked() {
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_ADD_NOTE, appSession.getAppPublicKey());
    }

    @Override
    public void onSendButtonClicked() {
        // TODO
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());

//        boolean nothingLeftToConfirm = walletManager.isNothingLeftToConfirm(negotiationSteps);
//
//        if (nothingLeftToConfirm) {
//            walletManager.sendNegotiationSteps(negotiationInfo, negotiationSteps);
//            changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
//        }
    }

    private void actionListenerBrokerCurrencyQuantity(ClauseInformation clause, String newValue) {
        CustomerBrokerNegotiationInformation negotiationInfo = negotiationWrapper.getNegotiationInformation();

        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();
        final NumberFormat numberFormat = DecimalFormat.getInstance();

        try {
            Number number = numberFormat.parse(clauses.get(ClauseType.EXCHANGE_RATE).getValue());

            if (number.doubleValue() < 0)
                Toast.makeText(getActivity(), "The exchange rate must be greater than zero.", Toast.LENGTH_LONG).show();

            else {
                valuesHasChanged = clause.getValue().equals(newValue);

                ClauseStatus status = valuesHasChanged && clause.getStatus() == ClauseStatus.ACCEPTED ? ClauseStatus.CHANGED : clause.getStatus();
                // putClause(clause, newValue, status);

                final BigDecimal exchangeRate = new BigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue());
                final BigDecimal amountToPay = new BigDecimal(clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY).getValue());
                final BigDecimal amountToBuy = amountToPay.divide(exchangeRate, 8, RoundingMode.HALF_UP);

                final String amountToBuyStr = numberFormat.format(amountToBuy);
                final ClauseInformation brokerCurrencyQuantity = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY);
                // putClause(brokerCurrencyQuantity, amountToBuyStr);

                adapter.changeDataSet(negotiationWrapper);
            }

        } catch (ParseException ex) {
            Toast.makeText(getActivity(), "Please set a correct value.", Toast.LENGTH_LONG).show();
        }
    }

    private ClauseStatus getStatusClauseChange(ClauseStatus statusClause) {

        if (valuesHasChanged && statusClause == ClauseStatus.ACCEPTED)
            return ClauseStatus.CHANGED;

        return statusClause;
    }


}
