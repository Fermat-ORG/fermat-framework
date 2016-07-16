package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.ClosedNegotiationDetailsAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets.CBP_CRYPTO_CUSTOMER_WALLET;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_CURRENCY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_CURRENCY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_CURRENCY_QUANTITY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.EXCHANGE_RATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClosedNegotiationDetailsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, ResourceProviderManager> {
    
    private static final String TAG = "ClosedNegDetailsFrag";
    
    private ErrorManager errorManager;

    private NumberFormat numberFormat= DecimalFormat.getInstance();
    
    private CustomerBrokerNegotiationInformation negotiationInfo;

    public static ClosedNegotiationDetailsFragment newInstance() {
        return new ClosedNegotiationDetailsFragment();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            final CryptoCustomerWalletModuleManager moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            
            negotiationInfo = moduleManager.getNegotiationInformation(getNegotiationId());

        } catch (Exception ex) {
            // TODO: Just for test purposes
            negotiationInfo = TestData.getOpenNegotiations(NegotiationStatus.WAITING_FOR_BROKER).get(0);

            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_CUSTOMER_WALLET, DISABLES_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        configureToolbar();
    
        final View rootView = inflater.inflate(R.layout.ccw_fragment_close_negotiation_details_activity, container, false);

        final ImageView brokerImage = (ImageView) rootView.findViewById(R.id.ccw_customer_image);
        final FermatTextView brokerName = (FermatTextView) rootView.findViewById(R.id.ccw_broker_name);
        final FermatTextView sellingDetails = (FermatTextView) rootView.findViewById(R.id.ccw_selling_summary);
        final FermatTextView exchangeRateSummary = (FermatTextView) rootView.findViewById(R.id.ccw_buying_exchange_rate);
        final FermatTextView lastUpdateDate = (FermatTextView) rootView.findViewById(R.id.ccw_last_update_date);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.ccw_open_negotiation_details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    
    
        final ActorIdentity broker = negotiationInfo.getBroker();
        final Map<ClauseType, ClauseInformation> clauses = negotiationInfo.getClauses();
    
        final String merchandise = clauses.get(CUSTOMER_CURRENCY).getValue();


        if(CryptoCurrency.codeExists(merchandise)){
            numberFormat.setMaximumFractionDigits(8);
        }else{
            numberFormat.setMaximumFractionDigits(2);
        }

        final String exchangeAmount = fixFormat(clauses.get(EXCHANGE_RATE).getValue());
        final String paymentCurrency = clauses.get(BROKER_CURRENCY).getValue();

        if(CryptoCurrency.codeExists(paymentCurrency)){
            numberFormat.setMaximumFractionDigits(8);
        }else{
            numberFormat.setMaximumFractionDigits(2);
        }


        final String amount = fixFormat(clauses.get(CUSTOMER_CURRENCY_QUANTITY).getValue());
    
        //Negotiation Summary
        brokerImage.setImageDrawable(getImgDrawable(broker.getProfileImage()));
        brokerName.setText(broker.getAlias());
        lastUpdateDate.setText(DateFormat.format("dd MMM yyyy", negotiationInfo.getLastNegotiationUpdateDate()));
        exchangeRateSummary.setText(getResources().getString(R.string.ccw_exchange_rate_summary, merchandise, exchangeAmount, paymentCurrency));
        sellingDetails.setText(getResources().getString(R.string.ccw_selling_details, amount, merchandise));
    
        final ClosedNegotiationDetailsAdapter adapter = new ClosedNegotiationDetailsAdapter(getActivity(), negotiationInfo);
        recyclerView.setAdapter(adapter);
    
        return rootView;
    }
    
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));
    }
    
    private Drawable getImgDrawable(byte[] customerImg) {
        Resources res = getResources();
        
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);
        
        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }

    private UUID getNegotiationId() {
        Object data = appSession.getData(FragmentsCommons.NEGOTIATION_ID);
        return (data != null) ? (UUID) data : null;
    }

    private String fixFormat(String value){

        try {
            return String.valueOf(new BigDecimal(String.valueOf(numberFormat.parse(numberFormat.format(Double.valueOf(value))))));
        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }

    }

}
