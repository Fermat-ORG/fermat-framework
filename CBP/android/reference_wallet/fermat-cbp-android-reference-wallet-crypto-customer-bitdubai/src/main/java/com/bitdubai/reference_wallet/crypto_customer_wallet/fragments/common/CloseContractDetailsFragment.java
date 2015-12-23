package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common;

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

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class CloseContractDetailsFragment extends FermatWalletFragment {
    private static final String TAG = "CloseContractDetails";

    private CryptoCustomerWalletModuleManager moduleManager;
    private ErrorManager errorManager;


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
        CryptoCustomerWalletSession customerWalletSession = (CryptoCustomerWalletSession) this.appSession;
        moduleManager = customerWalletSession.getModuleManager();
        errorManager = customerWalletSession.getErrorManager();

        final ContractBasicInformation contractBasicInfo = (ContractBasicInformation) appSession.getData(CryptoCustomerWalletSession.CONTRACT_DATA);
        ContractStatus status = contractBasicInfo.getStatus();

        ImageView customerImage = (ImageView) rootView.findViewById(R.id.ccw_customer_image);
        BitmapWorkerTask imgLoader = new BitmapWorkerTask(customerImage, getResources(), R.drawable.person, false);
        imgLoader.execute(contractBasicInfo.getCryptoCustomerImage());

        FermatTextView customerName = (FermatTextView) rootView.findViewById(R.id.ccw_customer_name);
        customerName.setText(contractBasicInfo.getCryptoCustomerAlias());

        FermatTextView amountSoldOrToSellTitle = (FermatTextView) rootView.findViewById(R.id.ccw_amount_bought_or_wanted_to_buy_title);
        amountSoldOrToSellTitle.setText(status.equals(ContractStatus.CANCELLED) ? R.string.ccw_wanted_to_buy : R.string.ccw_you_bought);

        FermatTextView amountSoldOrToSellValue = (FermatTextView) rootView.findViewById(R.id.ccw_amount_bought_or_wanted_to_buy_value);
        String amountToSell = DecimalFormat.getInstance().format(contractBasicInfo.getAmount());
        amountSoldOrToSellValue.setText(String.format("%1$s %2$s", amountToSell, contractBasicInfo.getMerchandise()));

        FermatTextView priceValue = (FermatTextView) rootView.findViewById(R.id.ccw_contract_details_price_value);
        String price = NumberFormat.getInstance().format(contractBasicInfo.getExchangeRateAmount());
        priceValue.setText(String.format("%1$s %2$s/%3$s", price, contractBasicInfo.getMerchandise(), contractBasicInfo.getPaymentCurrency()));

        FermatTextView paymentMethod = (FermatTextView) rootView.findViewById(R.id.ccw_contract_details_payment_method);
        paymentMethod.setText(contractBasicInfo.getTypeOfPayment());

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
                try {
                    CryptoCustomerWalletManager walletManager = moduleManager.getCryptoCustomerWallet(appSession.getAppPublicKey());
                    CustomerBrokerNegotiationInformation negotiationInformation = walletManager.getNegotiationInformation(contractBasicInfo.getNegotiationId());

                    appSession.setData(CryptoCustomerWalletSession.NEGOTIATION_DATA, negotiationInformation);
                    changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS, appSession.getAppPublicKey());

                } catch (CantGetCryptoCustomerWalletException | CantGetNegotiationInformationException ex) {
                    Log.e(TAG, CantGetCryptoBrokerWalletException.DEFAULT_MESSAGE, ex);
                    if (errorManager != null) {
                        errorManager.reportUnexpectedWalletException(
                                Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                                ex);
                    }

                }

            }
        });
    }
}
