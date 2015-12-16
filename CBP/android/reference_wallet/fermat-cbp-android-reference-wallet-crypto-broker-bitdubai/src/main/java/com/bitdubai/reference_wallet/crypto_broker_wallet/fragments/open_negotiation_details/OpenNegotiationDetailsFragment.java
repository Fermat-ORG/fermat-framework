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
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.NegotiationDetailsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNegotiationDetailsFragment extends FermatWalletFragment {
    private static final String TAG = "OpenNegotiationDetails";

    // UI
    private ImageView customerImage;
    private FermatTextView buyingDetails;
    private FermatTextView exchangeRateSummary;
    private FermatTextView customerName;
    private FermatTextView lastUpdateDate;
    private RecyclerView recyclerView;

    // DATA
    private CustomerBrokerNegotiationInformation negotiationInfo;

    // Fermat Managers
    private CryptoBrokerWalletManager walletManager;
    private ErrorManager errorManager;


    public static OpenNegotiationDetailsFragment newInstance() {
        return new OpenNegotiationDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CryptoBrokerWalletModuleManager moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            CryptoBrokerWalletSession session = (CryptoBrokerWalletSession) appSession;
            negotiationInfo = (CustomerBrokerNegotiationInformation) session.getData(CryptoBrokerWalletSession.NEGOTIATION_DATA);
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

    private void initViews(View rootView) {

        customerImage = (ImageView) rootView.findViewById(R.id.cbw_customer_image);
        customerName = (FermatTextView) rootView.findViewById(R.id.cbw_customer_name);
        buyingDetails = (FermatTextView) rootView.findViewById(R.id.cbw_buying_summary);
        exchangeRateSummary = (FermatTextView) rootView.findViewById(R.id.cbw_selling_exchange_rate);
        lastUpdateDate = (FermatTextView) rootView.findViewById(R.id.cbw_last_update_date);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cbw_open_negotiation_details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        bindData();
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

        NegotiationDetailsAdapter adapter = new NegotiationDetailsAdapter(getActivity(), walletManager, negotiationInfo, walletManager.getSteps(negotiationInfo));
        recyclerView.setAdapter(adapter);
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        Resources res = getResources();

        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }
}
