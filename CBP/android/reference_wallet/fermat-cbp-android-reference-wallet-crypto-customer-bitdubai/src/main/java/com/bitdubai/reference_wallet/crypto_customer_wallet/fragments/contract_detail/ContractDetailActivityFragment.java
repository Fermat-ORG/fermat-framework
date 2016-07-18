package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.ContractDetailAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.CommonLogger;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/01/16.
 * Modified by Alejandro Bicelis on 22/02/2016
 */
public class ContractDetailActivityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, ResourceProviderManager> {

    //Constants
    private static final String TAG = "ContractDetailFrag";

    // Managers
    private ErrorManager errorManager;
    private CryptoCustomerWalletModuleManager moduleManager;

    //Data
    private List<ContractDetail> contractInformation;
    private ContractBasicInformation data;

    private NumberFormat numberFormat= DecimalFormat.getInstance();


    // UI
    private ImageView brokerImage;
    private FermatTextView sellingSummary;
    private FermatTextView detailDate;
    private FermatTextView detailRate;
    private FermatTextView brokerName;
    private RecyclerView recyclerView;

    public static ContractDetailActivityFragment newInstance() {
        return new ContractDetailActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            //Capture contract data from ContractsTabFragment's onClick event
            data = (ContractBasicInformation) appSession.getData("contract_data");
            contractInformation = prepareContractInfo();


            //TODO: Figure this out, wtf is this hack done for?
            appSession.setData("ContractDetailFragment", this);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.ccw_fragment_contract_detail_activity, container, false);

        configureToolbar();
        initViews(layout);
        bindData();

        return layout;
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

    private void initViews(View rootView) {

        brokerImage = (ImageView) rootView.findViewById(R.id.ccw_contract_details_broker_image);
        brokerName = (FermatTextView) rootView.findViewById(R.id.ccw_contract_details_broker_name);
        sellingSummary = (FermatTextView) rootView.findViewById(R.id.ccw_contract_details_selling_summary);
        detailDate = (FermatTextView) rootView.findViewById(R.id.ccw_contract_details_date);
        detailRate = (FermatTextView) rootView.findViewById(R.id.ccw_contract_details_rate);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.ccw_contract_details_contract_steps_recycler_view);

        //Configure recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ContractDetailAdapter(getActivity(), contractInformation, appSession, moduleManager, this));

        final View negotiationButton = rootView.findViewById(R.id.ccw_contract_details_negotiation_details);
        negotiationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSession.setData(FragmentsCommons.NEGOTIATION_ID, data.getNegotiationId());
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS_OPEN_CONTRACT, appSession.getAppPublicKey());
            }
        });
    }


    private void bindData() {
        final SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yy", Locale.getDefault());
        final String paymentCurrency = data.getPaymentCurrency();

        if(CryptoCurrency.codeExists(paymentCurrency)){
            numberFormat.setMaximumFractionDigits(8);
        }else{
            numberFormat.setMaximumFractionDigits(2);
        }


        final String merchandise = data.getMerchandise();
        final String exchangeRateAmount = fixFormat(String.valueOf(data.getExchangeRateAmount()));
        final Date lastUpdate = new Date(data.getLastUpdate());

        brokerImage.setImageDrawable(getImgDrawable(data.getCryptoBrokerImage()));
        brokerName.setText(data.getCryptoBrokerAlias());
        sellingSummary.setText(String.format("SELLING %1$s", merchandise));
        detailRate.setText(String.format("1 %1$s @ %2$s %3$s", merchandise, exchangeRateAmount, paymentCurrency));
        detailDate.setText(String.format("Date: %1$s", formatter.format(lastUpdate)));
    }


    private List<ContractDetail> prepareContractInfo() {

        List<ContractDetail> contractDetails = new ArrayList<>();
        ContractDetail contractDetail;

        if (moduleManager != null) {

            try {
                CustomerBrokerContractPurchase customerBrokerContractPurchase = moduleManager.getCustomerBrokerContractPurchaseByNegotiationId(data.getNegotiationId().toString());

                String exchangeRate = "MK";
                String paymentCurrency = "MK";
                String paymentAmount = "-1";
                String paymentPaymentMethod = "MK";
                String merchandiseCurrency = "MK";
                String merchandiseAmount = "-1";
                String merchandisePaymentMethod = "MK";
                MoneyType customerPaymentMethodType = MoneyType.BANK;
                MoneyType brokerPaymentMethodType = MoneyType.BANK;
                try{
                    Collection<Clause> clauses = moduleManager.getNegotiationClausesFromNegotiationId(data.getNegotiationId());

                    //Extract info from clauses
                    for(Clause clause : clauses)
                    {
                        if(clause.getType() == ClauseType.EXCHANGE_RATE)
                            exchangeRate = clause.getValue();

                        if(clause.getType() == ClauseType.BROKER_CURRENCY){
                            try {
                                if(FiatCurrency.codeExists(clause.getValue()))
                                    paymentCurrency = FiatCurrency.getByCode(clause.getValue()).getFriendlyName();
                                else if(CryptoCurrency.codeExists(clause.getValue()))
                                    paymentCurrency = CryptoCurrency.getByCode(clause.getValue()).getFriendlyName();
                            }catch(Exception e) {
                                paymentCurrency = clause.getValue();
                            }
                        }
                        if(clause.getType() == ClauseType.BROKER_CURRENCY_QUANTITY)
                            paymentAmount = clause.getValue();
                        if(clause.getType() == ClauseType.BROKER_PAYMENT_METHOD){
                            merchandisePaymentMethod  = MoneyType.getByCode(clause.getValue()).getFriendlyName();
                            brokerPaymentMethodType = MoneyType.getByCode(clause.getValue());
                        }
                        if(clause.getType() == ClauseType.CUSTOMER_CURRENCY) {
                            try {
                                if (FiatCurrency.codeExists(clause.getValue()))
                                    merchandiseCurrency = FiatCurrency.getByCode(clause.getValue()).getFriendlyName();
                                else if (CryptoCurrency.codeExists(clause.getValue()))
                                    merchandiseCurrency = CryptoCurrency.getByCode(clause.getValue()).getFriendlyName();
                            }catch(Exception e) {
                                merchandiseCurrency = clause.getValue();
                            }
                        }
                        if(clause.getType() == ClauseType.CUSTOMER_CURRENCY_QUANTITY)
                            merchandiseAmount = clause.getValue();
                        if(clause.getType() == ClauseType.CUSTOMER_PAYMENT_METHOD){
                            paymentPaymentMethod = MoneyType.getByCode(clause.getValue()).getFriendlyName();
                            customerPaymentMethodType = MoneyType.getByCode(clause.getValue());
                        }

                    }

                }catch(Exception e) {e.printStackTrace();}


                long paymentSubmitDate = moduleManager.getCompletionDateForContractStatus(data.getContractId(), ContractStatus.PAYMENT_SUBMIT, paymentPaymentMethod);
                long paymentAckDate = moduleManager.getCompletionDateForContractStatus(data.getContractId(), ContractStatus.PENDING_MERCHANDISE, paymentPaymentMethod);
                long merchandiseSubmitDate = moduleManager.getCompletionDateForContractStatus(data.getContractId(), ContractStatus.MERCHANDISE_SUBMIT, merchandisePaymentMethod);
                long merchandiseAckDate = moduleManager.getCompletionDateForContractStatus(data.getContractId(), ContractStatus.READY_TO_CLOSE, merchandisePaymentMethod);


                ContractStatus contractStatus = customerBrokerContractPurchase.getStatus();
                //ContractStatus contractStatus = ContractStatus.PENDING_PAYMENT;
                //ContractStatus contractStatus = ContractStatus.PAYMENT_SUBMIT;
                //ContractStatus contractStatus = ContractStatus.PENDING_MERCHANDISE;
                //ContractStatus contractStatus = ContractStatus.MERCHANDISE_SUBMIT;
                //ContractStatus contractStatus = ContractStatus.READY_TO_CLOSE;
                //ContractStatus contractStatus = ContractStatus.COMPLETED;
                //ContractStatus contractStatus = ContractStatus.CANCELLED;
                //ContractStatus contractStatus = ContractStatus.PAUSED;

                //Payment Delivery step
                contractDetail = new ContractDetail(
                        1,
                        contractStatus,
                        data.getContractId(),
                        data.getNegotiationId(),
                        paymentAmount,
                        paymentPaymentMethod,
                        paymentCurrency,
                        paymentSubmitDate,customerPaymentMethodType);
                contractDetails.add(contractDetail);

                //Payment Reception step
                contractDetail = new ContractDetail(
                        2,
                        contractStatus,
                        data.getContractId(),
                        data.getNegotiationId(),
                        paymentAmount,
                        paymentPaymentMethod,
                        paymentCurrency,
                        paymentAckDate,customerPaymentMethodType);
                contractDetails.add(contractDetail);

                //Merchandise Delivery step
                contractDetail = new ContractDetail(
                        3,
                        contractStatus,
                        data.getContractId(),
                        data.getNegotiationId(),
                        merchandiseAmount,
                        merchandisePaymentMethod,
                        merchandiseCurrency,
                        merchandiseSubmitDate,brokerPaymentMethodType);
                contractDetails.add(contractDetail);

                //Merchandise Reception step
                contractDetail = new ContractDetail(
                        4,
                        contractStatus,
                        data.getContractId(),
                        data.getNegotiationId(),
                        merchandiseAmount,
                        merchandisePaymentMethod,
                        merchandiseCurrency,
                        merchandiseAckDate,brokerPaymentMethodType);
                contractDetails.add(contractDetail);
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                }
            }

        } else {
            //If module is null, show an error
            Toast.makeText(getActivity(), "Sorry, an error happened in ContractDetailActivityFragment (CryptoCustomerWalletModuleManager == null)", Toast.LENGTH_SHORT).show();
        }

        return contractDetails;
    }


    //TODO: What the fk is this
    private byte[] getByteArrayFromImageView(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        return stream.toByteArray();
    }



    /* Misc methods */

    private Drawable getImgDrawable(byte[] customerImg) {
        Resources res = getResources();

        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }


    private double getFormattedNumber(float number) {
        int decimalPlaces = 2;
        BigDecimal bigDecimalNumber = new BigDecimal(number);
        bigDecimalNumber = bigDecimalNumber.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        return bigDecimalNumber.doubleValue();
    }


    public void goToWalletHome() {
        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME, appSession.getAppPublicKey());
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
