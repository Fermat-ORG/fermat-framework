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
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.BrokerCurrencyQuotation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.BrokerCurrencyQuotationImpl;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.EmptyCustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private BrokerCurrencyQuotation brokerCurrencyQuotation;

    private CryptoCustomerWalletManager walletManager;
    private ErrorManager errorManager;
    private EmptyCustomerBrokerNegotiationInformation negotiationInfo;
    private ArrayList<String> paymentMethods; // test data
    private ArrayList<Currency> currencies; // test data
    private List <BrokerCurrencyQuotationImpl> brokerCurrencyQuotationlist;


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
        currencies.add(FiatCurrency.ARGENTINE_PESO);
        currencies.add(CryptoCurrency.BITCOIN);
        currencies.add(CryptoCurrency.LITECOIN);

        try {
            CryptoCustomerWalletModuleManager moduleManager = appSession.getModuleManager();
            walletManager = moduleManager.getCryptoCustomerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            //NEGOTIATION INFORMATION
            negotiationInfo = createNewEmptyNegotiationInfo();

            //LIST OF MAKET RATE OF BROKER
            brokerCurrencyQuotationlist = TestData.getMarketRateForCurrencyTest();
            brokerCurrencyQuotation = new BrokerCurrencyQuotation(brokerCurrencyQuotationlist);

            //REMOVE CURRENCY TO PAY OF CURRENCY LIST
            removeCurrency();

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
        ClauseTextDialog clauseTextDialog = null;
        switch (type) {
            case BROKER_CURRENCY:
                dialogFragment = new SimpleListDialogFragment<>();
                dialogFragment.configure("Currencies", currencies);
                dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<Currency>() {
                    @Override
                    public void onItemSelected(Currency selectedItem) { actionListenerBrokerCurrency(clause, selectedItem); }
                });

                dialogFragment.show(getFragmentManager(), "brokerCurrenciesDialog");
                break;
            case BROKER_CURRENCY_QUANTITY:
                clauseTextDialog = new ClauseTextDialog(getActivity(), appSession, appResourcesProviderManager);

                clauseTextDialog.setAcceptBtnListener(new ClauseTextDialog.OnClickAcceptListener() {
                    @Override
                    public void onClick(String newValue) { actionListenerBrokerCurrencyQuantity(clause, newValue); }
                });

                clauseTextDialog.setEditTextValue(clause.getValue());
                clauseTextDialog.configure(
                        type.equals(ClauseType.EXCHANGE_RATE) ? R.string.ccw_your_exchange_rate : R.string.ccw_amount_to_buy,
                        type.equals(ClauseType.EXCHANGE_RATE) ? R.string.amount : R.string.ccw_value);

                clauseTextDialog.show();
                break;

            /*case CUSTOMER_PAYMENT_METHOD:
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
                break;*/

            default:
                clauseTextDialog = new ClauseTextDialog(getActivity(), appSession, appResourcesProviderManager);
                clauseTextDialog.setAcceptBtnListener(new ClauseTextDialog.OnClickAcceptListener() {
                    @Override
                    public void onClick(String newValue) { actionListener(clause, newValue); }
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

    /*PRIVATE METHOD*/
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

        //NEGOTIATION SUMMARY
        Drawable brokerImg = getImgDrawable(broker.getProfileImage());
        brokerImage.setImageDrawable(brokerImg);
        brokerName.setText(broker.getAlias());
        sellingDetails.setText(getResources().getString(R.string.ccw_start_selling_details, currencyToBuy.getFriendlyName()));

        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

        //GET MARKET RATE
        String brokerMarketRate = brokerCurrencyQuotation.getExchangeRate(
                clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue(),
                clauses.get(ClauseType.BROKER_CURRENCY).getValue()
        );

        if(brokerMarketRate != null) brokerMarketRate = getDecimalFormat(getBigDecimal(brokerMarketRate));
        else brokerMarketRate = "0.0";

        final ClauseInformation exchangeRate = clauses.get(ClauseType.EXCHANGE_RATE);
        negotiationInfo.putClause(exchangeRate, brokerMarketRate);

        //ADAPTER
        adapter = new StartNegotiationAdapter(getActivity(), negotiationInfo);
        adapter.setFooterListener(this);
        adapter.setClauseListener(this);
        adapter.setMarketRateList(brokerCurrencyQuotationlist);

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

    //ACTION LISTENER FOR CLAUSE BROKER CURRNCY QUANTTY
    private void actionListenerBrokerCurrencyQuantity(ClauseInformation clause, String newValue){

        if(validateExchangeRate()) {

            final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

            //ASIGNAMENT NEW VALUE
            newValue = getDecimalFormat(getBigDecimal(newValue));
            negotiationInfo.putClause(clause, newValue);

            //CALCULATE CUSTOMER CURRENCY QUANTITY
            final BigDecimal exchangeRate   = getBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue());
            final BigDecimal amountToPay    = getBigDecimal(clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY).getValue());
            final BigDecimal amountToBuy    = amountToPay.divide(exchangeRate,8, RoundingMode.HALF_UP);

            //ASIGNAMENT CUSTOMER CURRENCY QUANTITY
            final String amountToBuyStr = getDecimalFormat(amountToBuy);
            final ClauseInformation brokerCurrencyQuantity = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY);
            negotiationInfo.putClause(brokerCurrencyQuantity, amountToBuyStr);

            adapter.changeDataSet(negotiationInfo);

        }

    }

    //ACTION LISTENER FOR CLAUSE DEFAULT
    private void actionListener(ClauseInformation clause, String newValue){

        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

        //ASIGNAMENT NEW VALUE
        newValue = getDecimalFormat(getBigDecimal(newValue));
        negotiationInfo.putClause(clause, newValue);

        //CALCULATE BROKER CURRENCY
        final BigDecimal exchangeRate   = new BigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue().replace(",", ""));
        final BigDecimal amountToBuy    = new BigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue().replace("," ,""));
        final BigDecimal amountToPay    = amountToBuy.multiply(exchangeRate);

        //ASIGNAMENT BROKER CURRENCY
        final String amountToPayStr = DecimalFormat.getInstance().format(amountToPay.doubleValue());
        final ClauseInformation brokerCurrencyQuantityClause = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);
        negotiationInfo.putClause(brokerCurrencyQuantityClause, amountToPayStr);

        adapter.changeDataSet(negotiationInfo);
    }

    //ACTION LISTENER FOR CLAUSE BROKER CURRNCY
    private void actionListenerBrokerCurrency(ClauseInformation clause, Currency selectedItem){

        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

        if ((clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue()) != (clauses.get(ClauseType.BROKER_CURRENCY).getValue())) {

            //ASIGNAMENT NEW VALUE
            negotiationInfo.putClause(clause, selectedItem.getCode());

            //GET MARKET RATE
            String brokerMarketRate = brokerCurrencyQuotation.getExchangeRate(
                    clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue(),
                    clauses.get(ClauseType.BROKER_CURRENCY).getValue()
            );

            if (brokerMarketRate != null) {

                BigDecimal exchangeRate = getBigDecimal(brokerMarketRate);

                //CALCULATE NEW PAY
                final BigDecimal amountToBuy = getBigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue());
                final BigDecimal amountToPay = amountToBuy.multiply(exchangeRate);

                //ASINAMENT NEW EXCHANGE RATE
                final String amountToexchangeRateStr = getDecimalFormat(exchangeRate);
                final ClauseInformation exchangeRateClause = clauses.get(ClauseType.EXCHANGE_RATE);
                negotiationInfo.putClause(exchangeRateClause, amountToexchangeRateStr);

                //ASIGNAMENT NEW PAY
                final String amountToPayStr = getDecimalFormat(amountToPay);
                final ClauseInformation brokerCurrencyQuantityClause = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);
                negotiationInfo.putClause(brokerCurrencyQuantityClause, amountToPayStr);

                //            Toast.makeText(getActivity(), "The exchange rate:" +
                //                    "BROKER CURRENCY: " +clauses.get(ClauseType.BROKER_CURRENCY).getValue()+
                //                    ", CUSTOMER CURRENCY: "+clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue()+
                //                    ", EXCHANGE RATE: "+brokerMarketRate
                //                    , Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getActivity(), "The exchange rate not fount for the currency to pay selected.", Toast.LENGTH_LONG).show();
            }

            adapter.changeDataSet(negotiationInfo);

        }else{
            Toast.makeText(getActivity(), "The currency to pay is equal to currency buy.", Toast.LENGTH_LONG).show();
        }

    }

        //VALIDATE CLAUSE
        private Boolean validateClauses(Map<ClauseType, ClauseInformation> clauses){

            if(clauses != null) {

                final BigDecimal exchangeRate   = getBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue());
                final BigDecimal amountToBuy    = getBigDecimal(clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue());
                final BigDecimal amountToPay    = getBigDecimal(clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY).getValue());

                if(exchangeRate.compareTo(BigDecimal.ZERO) <= 0){
                    Toast.makeText(getActivity(), "The exchange must be greater than zero.", Toast.LENGTH_LONG).show();
                    return false;
                }

                if(amountToBuy.compareTo(BigDecimal.ZERO) <= 0){
                    Toast.makeText(getActivity(), "The  buying must be greater than zero.", Toast.LENGTH_LONG).show();
                    return false;
                }

                if(amountToPay.compareTo(BigDecimal.ZERO) <= 0){
                    Toast.makeText(getActivity(), "The  paying must be greater than zero.", Toast.LENGTH_LONG).show();
                    return false;
                }

                if ((clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue()) == (clauses.get(ClauseType.BROKER_CURRENCY).getValue())) {
                    Toast.makeText(getActivity(), "The currency to pay is equal to currency buy.", Toast.LENGTH_LONG).show();
                    return false;
                }

            } else {
                Toast.makeText(getActivity(), "Error. Information is null.", Toast.LENGTH_LONG).show();
                return false;
            }

            return true;
        }

    //GET CLAUSE INFORMATION
    private Collection<ClauseInformation> getClause(Map<ClauseType, ClauseInformation> mapClauses){

        Collection<ClauseInformation> clauses = new ArrayList<>();

        if(mapClauses != null)
            for (Map.Entry<ClauseType, ClauseInformation> clauseInformation : mapClauses.entrySet())
                clauses.add(clauseInformation.getValue());

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

    //VALIDATE EXCHANGE RATE NOT IS ZERO
    private boolean validateExchangeRate(){

        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

        final BigDecimal exchangeRate = getBigDecimal(clauses.get(ClauseType.EXCHANGE_RATE).getValue());

        if(exchangeRate.compareTo(BigDecimal.ZERO) <= 0){
            Toast.makeText(getActivity(), "The exchange rate must be greater than zero.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

        //REMOVE CURRENCY TO PAY
        private void removeCurrency(){

            final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();

            String currencyPay = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();

            for (Currency item: currencies)
                if(currencyPay.equals(item.getCode())) currencies.remove(item);

        }

    private BigDecimal getBigDecimal(String value){
        return new BigDecimal(value.replace(",", ""));
    }

    private String getDecimalFormat(BigDecimal value){
        return DecimalFormat.getInstance().format(value.doubleValue());
    }
    /*END PRIVATE METHOD*/

}
