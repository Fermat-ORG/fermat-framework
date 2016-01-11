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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetAssociatedCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details.FooterViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.EmptyCustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartNegotiationActivityFragment extends AbstractFermatFragment<CryptoCustomerWalletSession, ResourceProviderManager>
        implements FooterViewHolder.OnFooterButtonsClickListener {

    private static final String TAG = "StartNegotiationFrag";

    // UI
    private ImageView brokerImage;
    private FermatTextView sellingDetails;
    private FermatTextView brokerName;
    private RecyclerView recyclerView;

    private CryptoCustomerWalletManager walletmanager;
    private ErrorManager errorManager;
    private CustomerBrokerNegotiationInformation negotiationInfo;


    public static StartNegotiationActivityFragment newInstance() {
        return new StartNegotiationActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            CryptoCustomerWalletModuleManager moduleManager = appSession.getModuleManager();
            walletmanager = moduleManager.getCryptoCustomerWallet(appSession.getAppPublicKey());
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

        // negotiationSteps = walletManager.getSteps(negotiationInfo);
        // StartNegotiationAdapter adapter = new StartNegotiationAdapter(getActivity(), appSession, walletManager, negotiationInfo, negotiationSteps);
        // adapter.setFooterListener(this);

        // recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAddNoteButtonClicked() {
        // TODO
    }

    @Override
    public void onSendButtonClicked() {
        // TODO
    }

    private CustomerBrokerNegotiationInformation createNewEmptyNegotiationInfo() {
        try {
            EmptyCustomerBrokerNegotiationInformation negotiationInfo = TestData.newEmptyNegotiationInformation();
            negotiationInfo.setStatus(NegotiationStatus.WAITING_FOR_BROKER);

            String currencyToBuy = (appSession.getCurrencyToBuy() != null) ? appSession.getCurrencyToBuy().getCode() : null;
            negotiationInfo.putClause(ClauseType.CUSTOMER_CURRENCY, currencyToBuy);
            negotiationInfo.putClause(ClauseType.BROKER_CURRENCY, null);
            negotiationInfo.putClause(ClauseType.CUSTOMER_CURRENCY_QUANTITY, null);
            negotiationInfo.putClause(ClauseType.BROKER_CURRENCY_QUANTITY, null);
            negotiationInfo.putClause(ClauseType.EXCHANGE_RATE, null);
            negotiationInfo.putClause(ClauseType.CUSTOMER_PAYMENT_METHOD, null);

            final ActorIdentity brokerIdentity = appSession.getSelectedBrokerIdentity();
            if (brokerIdentity != null)
                negotiationInfo.setBroker(brokerIdentity);

            final CryptoCustomerIdentity customerIdentity = walletmanager.getAssociatedIdentity();
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
