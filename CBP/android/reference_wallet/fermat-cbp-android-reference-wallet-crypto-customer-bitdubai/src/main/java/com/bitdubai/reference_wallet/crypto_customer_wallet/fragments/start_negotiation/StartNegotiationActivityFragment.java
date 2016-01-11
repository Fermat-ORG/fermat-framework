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
import android.widget.EditText;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.StartNegotiationAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.ClauseViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.EmptyCustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartNegotiationActivityFragment extends AbstractFermatFragment<CryptoCustomerWalletSession, ResourceProviderManager>
        implements FooterViewHolder.OnFooterButtonsClickListener, ClauseViewHolder.Listener {

    private static final String TAG = "StartNegotiationFrag";

    // UI
    private ImageView brokerImage;
    private FermatTextView sellingDetails;
    private FermatTextView brokerName;
    private RecyclerView recyclerView;

    private CryptoCustomerWalletManager walletManager;
    private ErrorManager errorManager;
    private EmptyCustomerBrokerNegotiationInformation negotiationInfo;
    private StartNegotiationAdapter adapter;


    public static StartNegotiationActivityFragment newInstance() {
        return new StartNegotiationActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onClauseValueChanged(EditText triggerView, ClauseInformation clause, String newValue, int position) {
        final ClauseType type = clause.getType();
        final ClauseInformation newClause = negotiationInfo.putClause(clause, newValue);
        switch (type) {
            case EXCHANGE_RATE:
                final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();
                final ClauseInformation customerCurrencyQty = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY);
                final ClauseInformation brokerCurrencyQty = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);

                final BigDecimal exchangeRate = BigDecimal.valueOf(Double.valueOf(newValue));
                final BigDecimal amountToBuy = BigDecimal.valueOf(Double.valueOf(customerCurrencyQty.getValue()));
                final String format = DecimalFormat.getInstance().format(amountToBuy.multiply(exchangeRate).doubleValue());
                negotiationInfo.putClause(brokerCurrencyQty, format);

                break;
            case CUSTOMER_CURRENCY_QUANTITY:
                break;
        }

        adapter.setItem(position, newClause);
        triggerView.setText(newValue);
    }

    @Override
    public void onClauseCLicked(final Button triggerView, final ClauseInformation clause, int position) {
        SimpleListDialogFragment dialogFragment;
        final ClauseType type = clause.getType();
        switch (type) {
            case BROKER_CURRENCY:
                dialogFragment = new SimpleListDialogFragment<>();
                dialogFragment.configure("Currencies", new ArrayList<Currency>());
                dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<Currency>() {
                    @Override
                    public void onItemSelected(Currency selectedItem) {
                        negotiationInfo.putClause(clause, selectedItem.getCode());
                        triggerView.setText(selectedItem.getFriendlyName());
                        adapter.notifyDataSetChanged();
                    }
                });

                dialogFragment.show(getFragmentManager(), "brokerCurrenciesDialog");
                break;

            case CUSTOMER_PAYMENT_METHOD:
                dialogFragment = new SimpleListDialogFragment<>();
                dialogFragment.configure("Payment Methods", new ArrayList<String>());
                dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(String selectedItem) {
                        negotiationInfo.putClause(clause, selectedItem);
                        triggerView.setText(selectedItem);
                    }
                });

                dialogFragment.show(getFragmentManager(), "paymentMethodsDialog");
                break;
        }
    }

    @Override
    public void onSendButtonClicked() {
        // TODO
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

            String currencyToBuy = (appSession.getCurrencyToBuy() != null) ? appSession.getCurrencyToBuy().getCode() : null;
            negotiationInfo.putClause(ClauseType.CUSTOMER_CURRENCY, currencyToBuy);
            negotiationInfo.putClause(ClauseType.BROKER_CURRENCY, null);
            negotiationInfo.putClause(ClauseType.CUSTOMER_CURRENCY_QUANTITY, "0.0");
            negotiationInfo.putClause(ClauseType.BROKER_CURRENCY_QUANTITY, "0.0");
            negotiationInfo.putClause(ClauseType.EXCHANGE_RATE, "0.0");
            negotiationInfo.putClause(ClauseType.CUSTOMER_PAYMENT_METHOD, null);

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
}
