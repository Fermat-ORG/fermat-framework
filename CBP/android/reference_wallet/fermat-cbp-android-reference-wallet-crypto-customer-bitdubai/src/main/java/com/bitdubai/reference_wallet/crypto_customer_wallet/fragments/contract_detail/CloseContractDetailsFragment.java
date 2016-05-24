package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class CloseContractDetailsFragment extends AbstractFermatFragment<CryptoCustomerWalletSession, ResourceProviderManager> {
    private static final String TAG = "CloseContractDetails";



    public static CloseContractDetailsFragment newInstance() {
        return new CloseContractDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ccw_close_contract_details, container, false);

        configureToolbar();

        initViews(rootView);

        return rootView;
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));

        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    private void initViews(View rootView) {

        final ContractBasicInformation contractBasicInfo = (ContractBasicInformation) appSession.getData(CryptoCustomerWalletSession.CONTRACT_DATA);
        ContractStatus status = contractBasicInfo.getStatus();

        ImageView brokerImage = (ImageView) rootView.findViewById(R.id.ccw_customer_image);
        BitmapWorkerTask imgLoader = new BitmapWorkerTask(brokerImage, getResources(), R.drawable.person, false);
        imgLoader.execute(contractBasicInfo.getCryptoBrokerImage());

        FermatTextView brokerName = (FermatTextView) rootView.findViewById(R.id.ccw_customer_name);
        brokerName.setText(contractBasicInfo.getCryptoBrokerAlias());

        FermatTextView amountSoldOrToSellTitle = (FermatTextView) rootView.findViewById(R.id.ccw_amount_bought_or_wanted_to_buy_title);
        amountSoldOrToSellTitle.setText(status.equals(ContractStatus.CANCELLED) ? R.string.ccw_wanted_to_buy : R.string.ccw_you_bought);

        FermatTextView amountSoldOrToSellValue = (FermatTextView) rootView.findViewById(R.id.ccw_amount_bought_or_wanted_to_buy_value);
        String amountToSell = DecimalFormat.getInstance().format(contractBasicInfo.getAmount());
        amountSoldOrToSellValue.setText(String.format("%1$s %2$s", amountToSell, contractBasicInfo.getMerchandise()));

        FermatTextView priceValue = (FermatTextView) rootView.findViewById(R.id.ccw_contract_details_price_value);
        String price = NumberFormat.getInstance().format(contractBasicInfo.getExchangeRateAmount());
        priceValue.setText(String.format("%1$s %2$s/%3$s", price, contractBasicInfo.getMerchandise(), contractBasicInfo.getPaymentCurrency()));

        FermatTextView paymentMethod = (FermatTextView) rootView.findViewById(R.id.ccw_contract_details_payment_method);
        String typeOfPaymentStr = "";
        try{
            typeOfPaymentStr = MoneyType.getByCode(contractBasicInfo.getTypeOfPayment()).getFriendlyName();
        }catch (InvalidParameterException e) {}
        paymentMethod.setText(typeOfPaymentStr);

        LinearLayout cancellationReasonContainer = (LinearLayout) rootView.findViewById(R.id.ccw_cancellation_reason_container);
        if (status.equals(ContractStatus.CANCELLED)) {
            cancellationReasonContainer.setVisibility(View.VISIBLE);
            FermatTextView cancellationReasonText = (FermatTextView) rootView.findViewById(R.id.ccw_cancellation_reason_text);
            cancellationReasonText.setText(contractBasicInfo.getCancellationReason());
        }

        FermatTextView contractDetailsCloseDate = (FermatTextView) rootView.findViewById(R.id.ccw_contract_details_close_date);
        CharSequence formattedDate = DateFormat.getDateFormat(getActivity()).format(contractBasicInfo.getLastUpdate());
        contractDetailsCloseDate.setText(getResources().getString(R.string.ccw_contract_details_last_update_date, formattedDate));

        FermatButton checkNegotiationDetails = (FermatButton) rootView.findViewById(R.id.ccw_contract_details_check_negotiation_details);
        checkNegotiationDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appSession.setNegotiationId(contractBasicInfo.getNegotiationId());
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS_CLOSE_CONTRACT, appSession.getAppPublicKey());
            }
        });
    }
}
