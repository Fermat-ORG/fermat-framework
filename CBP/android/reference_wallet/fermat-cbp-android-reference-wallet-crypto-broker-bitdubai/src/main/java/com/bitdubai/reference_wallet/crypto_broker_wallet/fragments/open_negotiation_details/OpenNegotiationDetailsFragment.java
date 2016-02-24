package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.open_negotiation_details;


import android.app.Fragment;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.exceptions.CantSendNegotiationToCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.NewOpenNegotiationDetailsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs.ClauseDateTimeDialog;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs.TextValueDialog;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.ExpirationTimeViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.MathUtils;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNegotiationDetailsFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager>
        implements FooterViewHolder.OnFooterButtonsClickListener, ClauseViewHolder.Listener, ExpirationTimeViewHolder.Listener {

    private static final String TAG = "OpenNegotiationDetails";
    private static final NumberFormat numberFormat = DecimalFormat.getInstance();

    // DATA
    private NegotiationWrapper negotiationWrapper;

    // Fermat Managers
    private CryptoBrokerWalletManager walletManager;
    private ErrorManager errorManager;
    private NewOpenNegotiationDetailsAdapter adapter;

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
            negotiationWrapper = new NegotiationWrapper(negotiationInfo, appSession);

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
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

        final String merchandise = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
        final String exchangeAmount = clauses.get(ClauseType.EXCHANGE_RATE).getValue();
        final String paymentCurrency = clauses.get(ClauseType.BROKER_CURRENCY).getValue();
        final String amount = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue();

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
        adapter.setExpirationDatetimeListener(this);

        // TODO colocar el precio de referencia, es decir la cotizacion

        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cbw_open_negotiation_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cbw_action_cancel_negotiation) {
            TextValueDialog dialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);
            dialog.configure(R.string.cbw_cancel_negotiation, R.string.cbw_reason);
            dialog.setTextFreeInputType(true);
            dialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
                @Override
                public void onClick(String editTextValue) {
                    try {
                        walletManager.cancelNegotiation(negotiationWrapper.getNegotiationInfo(), editTextValue);
                        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());

                    } catch (FermatException e) {
                        Toast.makeText(getActivity(), "Oopss, an error ocurred", Toast.LENGTH_SHORT).show();
                        if (errorManager != null)
                            errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                                    UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
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
            case CUSTOMER_DATE_TIME_TO_DELIVER:
                datetimeToPayEventAction(triggerView, clause);
                break;
            case BROKER_DATE_TIME_TO_DELIVER:
                datetimeToDeliverEventAction(triggerView, clause);
                break;
        }
    }

    @Override
    public void onExpirationDatetimeValueClicked(Button triggerView) {
        ClauseDateTimeDialog clauseDateTimeDialog = new ClauseDateTimeDialog(getActivity(), negotiationWrapper.getExpirationDate());
        if (triggerView.getId() == R.id.cbw_date_value)
            clauseDateTimeDialog.showDateDialog();
        else
            clauseDateTimeDialog.showTimeDialog();

        clauseDateTimeDialog.setAcceptBtnListener(new ClauseDateTimeDialog.OnClickAcceptListener() {
            @Override
            public void getDate(long newValue) {
                negotiationWrapper.setExpirationTime(newValue);
                adapter.changeDataSet(negotiationWrapper);
            }
        });
    }

    @Override
    public void onExpirationDatetimeConfirmButtonClicked() {
        negotiationWrapper.setExpirationDatetimeConfirmButtonClicked();
        adapter.changeDataSet(negotiationWrapper);
    }

    @Override
    public void onConfirmClauseButtonClicked(ClauseInformation clause) {
        negotiationWrapper.setClauseAsConfirmed(clause);
        adapter.changeDataSet(negotiationWrapper);
    }

    @Override
    public void onAddNoteButtonClicked() {
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_ADD_NOTE, appSession.getAppPublicKey());
    }

    @Override
    public void onSendButtonClicked() {
        try {
            walletManager.sendNegotiation(negotiationWrapper.getNegotiationInfo());
            changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());

        } catch (CantSendNegotiationToCryptoCustomerException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

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

    private void exchangeRateEventAction(final ClauseInformation clause, final Map<ClauseType, ClauseInformation> clauses) {
        TextValueDialog clauseTextDialog;
        clauseTextDialog = new TextValueDialog(getActivity(), appSession, appResourcesProviderManager);
        clauseTextDialog.setEditTextValue(clause.getValue());
        clauseTextDialog.configure(R.string.cbw_your_exchange_rate, R.string.amount);
        clauseTextDialog.setAcceptBtnListener(new TextValueDialog.OnClickAcceptListener() {
            @Override
            public void onClick(String newValue) {
                final BigDecimal exchangeRate = MathUtils.getBigDecimal(clause);
                final BigDecimal amountToSell = MathUtils.getBigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY));
                final double amountToReceiveValue = exchangeRate.multiply(amountToSell).doubleValue();
                final ClauseInformation amountToReceiveClause = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);
                negotiationWrapper.changeClauseValue(clause, newValue);
                negotiationWrapper.changeClauseValue(amountToReceiveClause, numberFormat.format(amountToReceiveValue));

                adapter.changeDataSet(negotiationWrapper);
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
                final BigDecimal amountToSell = MathUtils.getBigDecimal(clause);
                final BigDecimal exchangeRate = MathUtils.getBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE));

                final double amountToReceiveValue = exchangeRate.multiply(amountToSell).doubleValue();
                final ClauseInformation amountToReceiveClause = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);

                negotiationWrapper.changeClauseValue(clause, newValue);
                negotiationWrapper.changeClauseValue(amountToReceiveClause, numberFormat.format(amountToReceiveValue));

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
                final BigDecimal amountToReceive = MathUtils.getBigDecimal(clause);
                final BigDecimal exchangeRate = MathUtils.getBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE));

                final double amountToSellValue = amountToReceive.divide(exchangeRate, 8, RoundingMode.HALF_UP).doubleValue();
                final ClauseInformation amountToSellClause = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY);

                negotiationWrapper.changeClauseValue(clause, newValue);
                negotiationWrapper.changeClauseValue(amountToSellClause, numberFormat.format(amountToSellValue));

                adapter.changeDataSet(negotiationWrapper);
            }
        });
        clauseTextDialog.show();
    }

    private void paymentMethodEventAction(final ClauseInformation clause, final Map<ClauseType, ClauseInformation> clauses) {
        try {

            SimpleListDialogFragment dialogFragment;
            ClauseInformation brokerCurrency = clauses.get(ClauseType.BROKER_CURRENCY);
            List<MoneyType> paymentMethods = walletManager.getPaymentMethods(brokerCurrency.getValue(), appSession.getAppPublicKey());

            dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("Payment Methods", paymentMethods);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<MoneyType>() {
                @Override
                public void onItemSelected(MoneyType newValue) {
                    negotiationWrapper.changeClauseValue(clause, newValue.getCode());

                    if (newValue == MoneyType.BANK && clauses.get(ClauseType.BROKER_BANK_ACCOUNT) == null) {

                        final String currencyToReceive = clauses.get(ClauseType.BROKER_CURRENCY).getValue();
                        List<String> bankAccounts;
                        try {
                            bankAccounts = walletManager.getAccounts(currencyToReceive, appSession.getAppPublicKey());
                        } catch (FermatException e) {
                            bankAccounts = new ArrayList<>();
                        }
                        String bankAccount = bankAccounts.isEmpty() ? "No Bank Accounts" : bankAccounts.get(0);
                        negotiationWrapper.addClause(ClauseType.BROKER_BANK_ACCOUNT, bankAccount);

                    } else if (newValue == MoneyType.CASH_ON_HAND || newValue == MoneyType.CASH_DELIVERY &&
                            clauses.get(ClauseType.BROKER_PLACE_TO_DELIVER) == null) {

                        List<NegotiationLocations> locations;
                        try {
                            locations = Lists.newArrayList(walletManager.getAllLocations(NegotiationType.SALE));
                        } catch (FermatException e) {
                            locations = new ArrayList<>();
                        }
                        String location = locations.isEmpty() ? "No Locations" : locations.get(0).getLocation();
                        negotiationWrapper.addClause(ClauseType.BROKER_PLACE_TO_DELIVER, location);
                    }

                    adapter.changeDataSet(negotiationWrapper);
                }
            });
            dialogFragment.show(getFragmentManager(), "paymentMethodsDialog");

        } catch (FermatException ex) {
            errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    private void brokerLocationsEventAction(final ClauseInformation clause) {
        List<NegotiationLocations> locations;

        try {
            locations = Lists.newArrayList(walletManager.getAllLocations(NegotiationType.SALE));
        } catch (FermatException e) {
            locations = new ArrayList<>();
        }

        if (locations.isEmpty())
            Toast.makeText(getActivity(), "You don't have Locations. Add one in the Wallet Settings.", Toast.LENGTH_LONG).show();
        else {
            final SimpleListDialogFragment dialogFragment = new SimpleListDialogFragment<>();
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
        final String currencyToReceive = clauses.get(ClauseType.BROKER_CURRENCY).getValue();
        List<String> bankAccounts;

        try {
            bankAccounts = walletManager.getAccounts(currencyToReceive, appSession.getAppPublicKey());
        } catch (FermatException e) {
            bankAccounts = new ArrayList<>();
        }

        if (bankAccounts.isEmpty())
            Toast.makeText(getActivity(), "You don't have Bank Accounts. Add one in the Wallet Settings.", Toast.LENGTH_LONG).show();
        else {
            final SimpleListDialogFragment dialogFragment = new SimpleListDialogFragment<>();
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
}
