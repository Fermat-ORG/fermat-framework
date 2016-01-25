package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.start_negotiation;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.StartNegotiationAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.dialogs.ClauseTextDialog;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.EmptyCustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Modified by Yordin Alayn 21.01.16
 */
public class StartNegotiationActivityFragment extends AbstractFermatFragment<CryptoCustomerWalletSession, ResourceProviderManager>
        implements FooterViewHolder.OnFooterButtonsClickListener, ClauseViewHolder.Listener {

    private static final String TAG = "StartNegotiationFrag";

    // UI
    private ImageView brokerImage;
    private FermatTextView sellingDetails;
    private FermatTextView brokerName;
    private RecyclerView recyclerView;
    private StartNegotiationAdapter adapter;

    private CryptoCustomerWalletManager walletManager;
    private ErrorManager errorManager;
    private EmptyCustomerBrokerNegotiationInformation negotiationInfo;
    private ArrayList<String> paymentMethods; // test data
    private ArrayList<Currency> currencies; // test data


    public static StartNegotiationActivityFragment newInstance() {
        return new StartNegotiationActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        paymentMethods = new ArrayList<>();
        paymentMethods.add("Cash");
        paymentMethods.add("Bank");
        paymentMethods.add("Crypto");

        currencies = new ArrayList<>();
        currencies.add(FiatCurrency.VENEZUELAN_BOLIVAR);
        currencies.add(FiatCurrency.US_DOLLAR);
        currencies.add(CryptoCurrency.BITCOIN);

        try {
            CryptoCustomerWalletModuleManager moduleManager = appSession.getModuleManager();
            walletManager = moduleManager.getCryptoCustomerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            negotiationInfo = createNewEmptyNegotiationInfo();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.ccw_fragment_start_negotiation_activity, container, false);

        configureToolbar();
        initViews(layout);
        bindData();

        return layout;
    }

    @Override
    public void onClauseCLicked(final Button triggerView, final ClauseInformation clause, final int position) {
        SimpleListDialogFragment dialogFragment;
        final ClauseType type = clause.getType();
        switch (type) {
            case BROKER_CURRENCY:
                dialogFragment = new SimpleListDialogFragment<>();
                dialogFragment.configure("Currencies", currencies);
                dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<Currency>() {
                    @Override
                    public void onItemSelected(Currency selectedItem) {
                        negotiationInfo.putClause(clause, selectedItem.getCode());
                        adapter.changeDataSet(negotiationInfo);
                    }
                });

                dialogFragment.show(getFragmentManager(), "brokerCurrenciesDialog");
                break;

            case CUSTOMER_PAYMENT_METHOD:
                dialogFragment = new SimpleListDialogFragment<>();
                dialogFragment.configure("Payment Methods", paymentMethods);
                dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(String selectedItem) {
                        negotiationInfo.putClause(clause, selectedItem);
                        adapter.changeDataSet(negotiationInfo);
                    }
                });

                dialogFragment.show(getFragmentManager(), "paymentMethodsDialog");
                break;

            case BROKER_PAYMENT_METHOD:
                dialogFragment = new SimpleListDialogFragment<>();

                dialogFragment.configure("Reception Methods", paymentMethods);
                dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(String selectedItem) {
                        negotiationInfo.putClause(clause, selectedItem);
                        adapter.changeDataSet(negotiationInfo);
                    }
                });

                dialogFragment.show(getFragmentManager(), "paymentMethodsDialog");
                break;

            default:
                ClauseTextDialog clauseTextDialog = new ClauseTextDialog(getActivity(), appSession, appResourcesProviderManager);
                clauseTextDialog.setAcceptBtnListener(new ClauseTextDialog.OnClickAcceptListener() {
                    @Override
                    public void onClick(String newValue) {
                        negotiationInfo.putClause(clause, newValue);

                        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

                        final BigDecimal exchangeRate = new BigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue());
                        final BigDecimal amountToBuy = new BigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue());
                        final BigDecimal amountToSell = amountToBuy.multiply(exchangeRate);

                        final String amountToSellStr = DecimalFormat.getInstance().format(amountToSell.doubleValue());
                        final ClauseInformation brokerCurrencyQuantity = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);
                        negotiationInfo.putClause(brokerCurrencyQuantity, amountToSellStr);

                        adapter.changeDataSet(negotiationInfo);
                    }
                });

                clauseTextDialog.setEditTextValue(clause.getValue());
                clauseTextDialog.configure(
                        type.equals(ClauseType.EXCHANGE_RATE) ? R.string.ccw_your_exchange_rate : R.string.ccw_amount_to_buy,
                        type.equals(ClauseType.EXCHANGE_RATE) ? R.string.amount : R.string.ccw_value);

                clauseTextDialog.show();
                break;
        }
    }

    @Override
    public void onSendButtonClicked() {

        try {

            Map<ClauseType, ClauseInformation> mapClauses = negotiationInfo.getClauses();
            Collection<ClauseInformation> clauses = new ArrayList<>();
            String customerPublicKey = "customerPublicKey";
            String brokerPublicKey = negotiationInfo.getBroker().getPublicKey();

            if (mapClauses != null) {

                if (validateClauses(mapClauses)) {

                    clauses = getClause(mapClauses);

                    if(walletManager.startNegotiation(customerPublicKey, brokerPublicKey, clauses)) {
                        Toast.makeText(getActivity(), "Send negotiation. " + getClauseTest(mapClauses) + " CUSTOMER_PUBLICKEY: "+ customerPublicKey +" BROKER_PUBLICKEY: "+ brokerPublicKey, Toast.LENGTH_LONG).show();
                        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME, this.appSession.getAppPublicKey());
                    } else {
                        Toast.makeText(getActivity(), "Error send negotiation. " + getClauseTest(mapClauses) + " CUSTOMER_PUBLICKEY: "+ customerPublicKey +" BROKER_PUBLICKEY: "+ brokerPublicKey, Toast.LENGTH_LONG).show();
                    }

                }

            } else {
                Toast.makeText(getActivity(), "Error in the information. Is null.", Toast.LENGTH_LONG).show();
            }

        } catch (CouldNotStartNegotiationException | CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException e){
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

    }

    @Override
    public void onAddNoteButtonClicked() {
        // DO NOTHING..
    }

    private void initViews(View rootView) {

        brokerImage = (ImageView) rootView.findViewById(R.id.ccw_broker_image);
        brokerName = (FermatTextView) rootView.findViewById(R.id.ccw_broker_name);
        sellingDetails = (FermatTextView) rootView.findViewById(R.id.ccw_selling_summary);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.ccw_negotiation_steps_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    private void bindData() {
        ActorIdentity broker = appSession.getSelectedBrokerIdentity();
        Currency currencyToBuy = appSession.getCurrencyToBuy();

        //Negotiation Summary
        Drawable brokerImg = getImgDrawable(broker.getProfileImage());
        brokerImage.setImageDrawable(brokerImg);
        brokerName.setText(broker.getAlias());
        sellingDetails.setText(getResources().getString(R.string.ccw_start_selling_details, currencyToBuy.getFriendlyName()));

        adapter = new StartNegotiationAdapter(getActivity(), negotiationInfo);
        adapter.setFooterListener(this);
        adapter.setClauseListener(this);

        recyclerView.setAdapter(adapter);
    }

    private EmptyCustomerBrokerNegotiationInformation createNewEmptyNegotiationInfo() {
        try {
            EmptyCustomerBrokerNegotiationInformation negotiationInfo = TestData.newEmptyNegotiationInformation();
            negotiationInfo.setStatus(NegotiationStatus.WAITING_FOR_BROKER);

            final Currency currency = appSession.getCurrencyToBuy();
            negotiationInfo.putClause(ClauseType.CUSTOMER_CURRENCY, currency.getCode());
            negotiationInfo.putClause(ClauseType.BROKER_CURRENCY, currencies.get(0).getCode());
            negotiationInfo.putClause(ClauseType.CUSTOMER_CURRENCY_QUANTITY, "0.0");
            negotiationInfo.putClause(ClauseType.BROKER_CURRENCY_QUANTITY, "0.0");
            negotiationInfo.putClause(ClauseType.EXCHANGE_RATE, "0.0");
            negotiationInfo.putClause(ClauseType.CUSTOMER_PAYMENT_METHOD, paymentMethods.get(0));
            negotiationInfo.putClause(ClauseType.BROKER_PAYMENT_METHOD, paymentMethods.get(0));

            final ActorIdentity brokerIdentity = appSession.getSelectedBrokerIdentity();
            if (brokerIdentity != null)
                negotiationInfo.setBroker(brokerIdentity);

            final CryptoCustomerIdentity customerIdentity = walletManager.getAssociatedIdentity();
            if (customerIdentity != null)
                negotiationInfo.setCustomer(customerIdentity);

            return negotiationInfo;

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        return null;
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        Resources res = getResources();

        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }

    //VALIDATE CLAUSE
    private Boolean validateClauses(Map<ClauseType, ClauseInformation> clauses){


        if(clauses != null) {

            ClauseInformation information = null;

            for (Map.Entry<ClauseType, ClauseInformation> clauseInformation : clauses.entrySet()) {

                information = clauseInformation.getValue();

                if (information == null) {
                    Toast.makeText(getActivity(), "Please completed all information.", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (information.getType().getCode().equals(ClauseType.CUSTOMER_CURRENCY_QUANTITY.getCode())) {

                    if (Double.parseDouble(information.getValue()) <= 0) {
                        Toast.makeText(getActivity(), "The currency quantity not have menor of 0.", Toast.LENGTH_LONG).show();
//                        mBrokerName.requestFocus();
                        return false;
                    }

                } else if (information.getType().getCode().equals(ClauseType.BROKER_CURRENCY_QUANTITY.getCode())) {

                    if (Double.parseDouble(information.getValue()) <= 0) {
                        Toast.makeText(getActivity(), "The  payment quantity not have menor of 0.", Toast.LENGTH_LONG).show();
                        return false;
                    }

                } else if (information.getType().getCode().equals(ClauseType.EXCHANGE_RATE.getCode())) {

                    if (Double.parseDouble(information.getValue()) <= 0) {
                        Toast.makeText(getActivity(), "The exchange rate quantity not have menor of 0.", Toast.LENGTH_LONG).show();
                        return false;
                    }

                }
            }

        } else { return false; }

        return true;
    }

    //GET CLAUSE INFORMATION
    private Collection<ClauseInformation> getClause(Map<ClauseType, ClauseInformation> mapClauses){

        Collection<ClauseInformation> clauses = new ArrayList<>();

        if(mapClauses != null) {

            for (Map.Entry<ClauseType, ClauseInformation> clauseInformation : mapClauses.entrySet()) {

                clauses.add(clauseInformation.getValue());

            }

        }

        return clauses;
    }

    //GET CLAUSE INFORMATION TEST
    private String getClauseTest(Map<ClauseType, ClauseInformation> mapClauses){

        String clauses = "";
        ClauseInformation information;

        if(mapClauses != null) {

            for (Map.Entry<ClauseType, ClauseInformation> clauseInformation : mapClauses.entrySet()) {

                information = clauseInformation.getValue();
                clauses = information.getType().getCode() + ": " + information.getValue() + ", " + clauses;

            }

        }

        return clauses;
    }

}
