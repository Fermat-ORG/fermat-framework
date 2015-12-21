package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class CloseContractDetailsFragment extends FermatWalletFragment {


    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    public static CloseContractDetailsFragment newInstance() {
        return new CloseContractDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cbw_close_contract_details, container, false);

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        CryptoBrokerWalletSession brokerWalletSession = (CryptoBrokerWalletSession) this.appSession;
        moduleManager = brokerWalletSession.getModuleManager();
        errorManager = brokerWalletSession.getErrorManager();

        ContractBasicInformation contractBasicInfo = (ContractBasicInformation) appSession.getData(CryptoBrokerWalletSession.CONTRACT_DATA);
        ContractStatus status = contractBasicInfo.getStatus();

        ImageView customerImage = (ImageView) rootView.findViewById(R.id.cbw_customer_image);
        BitmapWorkerTask imgLoader = new BitmapWorkerTask(customerImage, getResources(), R.drawable.person, false);
        imgLoader.execute(contractBasicInfo.getCryptoCustomerImage());

        FermatTextView customerName = (FermatTextView) rootView.findViewById(R.id.cbw_customer_name);
        customerName.setText(contractBasicInfo.getCryptoCustomerAlias());

        FermatTextView amountSoldOrToSellTitle = (FermatTextView) rootView.findViewById(R.id.cbw_amount_sold_or_to_sell_title);
        amountSoldOrToSellTitle.setText(status.equals(ContractStatus.CANCELLED) ? R.string.cbw_amount_to_receive : R.string.cbw_amount_sold);

        FermatTextView amountSoldOrToSellValue = (FermatTextView) rootView.findViewById(R.id.cbw_amount_sold_or_to_sell_value);
        String amountToSell = DecimalFormat.getInstance().format(contractBasicInfo.getAmount());
        amountSoldOrToSellValue.setText(String.format("$1%s %2$s", amountToSell, contractBasicInfo.getMerchandise()));

        FermatTextView amountReceivedOrToReceiveTitle = (FermatTextView) rootView.findViewById(R.id.cbw_amount_received_to_receive_title);
        amountReceivedOrToReceiveTitle.setText(status.equals(ContractStatus.CANCELLED) ? R.string.cbw_amount_to_receive : R.string.cbw_amount_sold);

        FermatTextView amountReceivedOrToReceiveValue = (FermatTextView) rootView.findViewById(R.id.cbw_amount_received_to_receive_value);
        String amountToReceive = getAmountToReceive(contractBasicInfo);
        amountReceivedOrToReceiveValue.setText(String.format("$1%s %2$s", amountToReceive, contractBasicInfo.getPaymentCurrency()));

        FermatTextView paymentMethod = (FermatTextView) rootView.findViewById(R.id.cbw_contract_details_payment_method);
        paymentMethod.setText(contractBasicInfo.getTypeOfPayment());

        LinearLayout cancellationReasonContainer = (LinearLayout) rootView.findViewById(R.id.cbw_cancellation_reason_container);
        if (status.equals(ContractStatus.CANCELLED)) {
            cancellationReasonContainer.setVisibility(View.GONE);
        } else {
            FermatTextView cancellationReasonText = (FermatTextView) rootView.findViewById(R.id.cbw_cancellation_reason_text);
            cancellationReasonText.setText(contractBasicInfo.getCancellationReason());
        }
        FermatTextView contractDetailsCloseDate = (FermatTextView) rootView.findViewById(R.id.cbw_contract_details_close_date);
        FermatButton checkNegotiationDetails = (FermatButton) rootView.findViewById(R.id.cbw_contract_details_check_negotiation_details);
    }

    private String getAmountToReceive(ContractBasicInformation contractBasicInfo) {
        BigDecimal amountToSell = BigDecimal.valueOf(contractBasicInfo.getAmount());
        BigDecimal exchangeRate = BigDecimal.valueOf(contractBasicInfo.getExchangeRateAmount());

        double amountToReceive = amountToSell.multiply(exchangeRate).doubleValue();
        return DecimalFormat.getInstance().format(amountToReceive);
    }


}
