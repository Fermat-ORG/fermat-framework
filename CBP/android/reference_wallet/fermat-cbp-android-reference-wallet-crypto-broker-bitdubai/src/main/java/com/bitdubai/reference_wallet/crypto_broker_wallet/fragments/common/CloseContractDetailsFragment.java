package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class CloseContractDetailsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, ResourceProviderManager> {
    private static final String TAG = "CloseContractDetails";

    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;


    public static CloseContractDetailsFragment newInstance() {
        return new CloseContractDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cbw_close_contract_details, container, false);

        configureToolbar();

        initViews(rootView);

        return rootView;
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));

        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    private void initViews(View rootView) {
        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        final ContractBasicInformation contractBasicInfo = (ContractBasicInformation) appSession.getData(FragmentsCommons.CONTRACT_DATA);
        ContractStatus status = contractBasicInfo.getStatus();

        ImageView customerImage = (ImageView) rootView.findViewById(R.id.cbw_customer_image);
        BitmapWorkerTask imgLoader = new BitmapWorkerTask(customerImage, getResources(), R.drawable.person, false);
        imgLoader.execute(contractBasicInfo.getCryptoCustomerImage());

        FermatTextView customerName = (FermatTextView) rootView.findViewById(R.id.cbw_customer_name);
        customerName.setText(contractBasicInfo.getCryptoCustomerAlias());

        FermatTextView amountSoldOrToSellTitle = (FermatTextView) rootView.findViewById(R.id.cbw_amount_sold_or_to_sell_title);
        amountSoldOrToSellTitle.setText(status.equals(ContractStatus.CANCELLED) ? R.string.cbw_amount_to_sell : R.string.cbw_amount_sold);

        FermatTextView amountSoldOrToSellValue = (FermatTextView) rootView.findViewById(R.id.cbw_amount_sold_or_to_sell_value);
        String amountToSell = DecimalFormat.getInstance().format(contractBasicInfo.getAmount());
        amountSoldOrToSellValue.setText(String.format("%1$s %2$s", amountToSell, contractBasicInfo.getMerchandise()));

        FermatTextView amountReceivedOrToReceiveTitle = (FermatTextView) rootView.findViewById(R.id.cbw_amount_received_to_receive_title);
        amountReceivedOrToReceiveTitle.setText(status.equals(ContractStatus.CANCELLED) ? R.string.cbw_amount_to_receive : R.string.cbw_amount_received);

        FermatTextView amountReceivedOrToReceiveValue = (FermatTextView) rootView.findViewById(R.id.cbw_amount_received_to_receive_value);
        String amountToReceive = getAmountToReceive(contractBasicInfo);
        amountReceivedOrToReceiveValue.setText(String.format("%1$s %2$s", amountToReceive, contractBasicInfo.getPaymentCurrency()));

        FermatTextView paymentMethod = (FermatTextView) rootView.findViewById(R.id.cbw_contract_details_payment_method);
        String typeOfPaymentStr = "";
        try {
            typeOfPaymentStr = MoneyType.getByCode(contractBasicInfo.getTypeOfPayment()).getFriendlyName();
        } catch (InvalidParameterException e) {
        }
        paymentMethod.setText(typeOfPaymentStr);

        LinearLayout cancellationReasonContainer = (LinearLayout) rootView.findViewById(R.id.cbw_cancellation_reason_container);
        if (status.equals(ContractStatus.CANCELLED)) {
            cancellationReasonContainer.setVisibility(View.VISIBLE);
            FermatTextView cancellationReasonText = (FermatTextView) rootView.findViewById(R.id.cbw_cancellation_reason_text);
            cancellationReasonText.setText(contractBasicInfo.getCancellationReason());
        }

        FermatTextView contractDetailsCloseDate = (FermatTextView) rootView.findViewById(R.id.cbw_contract_details_close_date);
        CharSequence formattedDate = DateFormat.getDateFormat(getActivity()).format(contractBasicInfo.getLastUpdate());
        contractDetailsCloseDate.setText(getResources().getString(R.string.cbw_contract_details_last_update_date, formattedDate));

        FermatButton checkNegotiationDetails = (FermatButton) rootView.findViewById(R.id.cbw_contract_details_check_negotiation_details);
        checkNegotiationDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerBrokerNegotiationInformation info;
                try {
                    info = moduleManager.getNegotiationInformation(contractBasicInfo.getNegotiationId());
                    appSession.setData(FragmentsCommons.NEGOTIATION_DATA, info);
                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS_CLOSE_CONTRACT, appSession.getAppPublicKey());

                } catch (FermatException ex) {
                    // TODO Just for testing. Add a Toast later
                    info = TestData.getOpenNegotiations(NegotiationStatus.WAITING_FOR_BROKER).get(0);
                    appSession.setData(FragmentsCommons.NEGOTIATION_DATA, info);
                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS_CLOSE_CONTRACT, appSession.getAppPublicKey());

                    if (errorManager != null)
                        errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                    else
                        Log.e(TAG, ex.getMessage(), ex);
                }
            }
        });
    }

    private String getAmountToReceive(ContractBasicInformation contractBasicInfo) {
        BigDecimal amountToSell = BigDecimal.valueOf(contractBasicInfo.getAmount());
        BigDecimal exchangeRate = BigDecimal.valueOf(contractBasicInfo.getExchangeRateAmount());

        double amountToReceive = amountToSell.multiply(exchangeRate).doubleValue();
        return NumberFormat.getInstance().format(amountToReceive);
    }


}
