package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.closed_negotiation_details;

import android.content.res.Resources;
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

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.ClosedNegotiationDetailsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.util.Map;


/**
 * Created by Lozadaa on 22/02/16.
 */

public class ClosedNegotiationDetailsFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager> {

    private static final String TAG = "ClosedNegDetails";

    // DATA
    private NegotiationWrapper negotiationWrapper;

    // Fermat Managers
    private ErrorManager errorManager;


    public static ClosedNegotiationDetailsFragment newInstance() {
        return new ClosedNegotiationDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
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

        final View rootView = inflater.inflate(R.layout.cbw_fragment_closed_negotiation_details_activity, container, false);

        final ImageView customerImage = (ImageView) rootView.findViewById(R.id.cbw_customer_image);
        final FermatTextView customerName = (FermatTextView) rootView.findViewById(R.id.cbw_customer_name);
        final FermatTextView buyingDetails = (FermatTextView) rootView.findViewById(R.id.cbw_buying_summary);
        final FermatTextView exchangeRateSummary = (FermatTextView) rootView.findViewById(R.id.cbw_selling_exchange_rate);
        final FermatTextView lastUpdateDate = (FermatTextView) rootView.findViewById(R.id.cbw_last_update_date);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.cbw_closed_negotiation_details_recycler_view);
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

        final ClosedNegotiationDetailsAdapter adapter = new ClosedNegotiationDetailsAdapter(getActivity(), negotiationWrapper);
        recyclerView.setAdapter(adapter);

        return rootView;
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

}
