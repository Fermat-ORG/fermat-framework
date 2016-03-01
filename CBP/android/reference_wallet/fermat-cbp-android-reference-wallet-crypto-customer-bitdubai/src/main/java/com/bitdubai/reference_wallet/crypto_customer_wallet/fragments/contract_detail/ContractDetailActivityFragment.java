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
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.ContractDetailAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.EmptyCustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;


import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.CommonLogger;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/01/16.
 * Modified by Alejandro Bicelis on 22/02/2016
 */
public class ContractDetailActivityFragment extends AbstractFermatFragment<CryptoCustomerWalletSession, ResourceProviderManager> {

    //Constants
    private static final String TAG = "ContractDetailFrag";

    // Managers
    CryptoCustomerWalletModuleManager moduleManager;
    private CryptoCustomerWalletManager walletManager;
    private ErrorManager errorManager;

    //Data
    private List<ContractDetail> contractInformation;
    private ContractBasicInformation data;
    private ArrayList<String> paymentMethods; // test data
    private ArrayList<Currency> currencies; // test data

    // UI
    private ImageView brokerImage;
    private FermatTextView sellingSummary;
    private FermatTextView detailDate;
    private FermatTextView detailRate;
    private FermatTextView brokerName;
    private FermatButton negotiationButton;
    private RecyclerView recyclerView;

    public static ContractDetailActivityFragment newInstance() {
        return new ContractDetailActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CryptoCustomerWalletModuleManager moduleManager = appSession.getModuleManager();
            walletManager = moduleManager.getCryptoCustomerWallet(appSession.getAppPublicKey());
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
        negotiationButton = (FermatButton) rootView.findViewById(R.id.ccw_contract_details_negotiation_details);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.ccw_contract_details_contract_steps_recycler_view);

        //Configure recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ContractDetailAdapter(getActivity(), contractInformation, appSession, walletManager));

        negotiationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSession.setNegotiationId(data.getNegotiationId());
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS_OPEN_CONTRACT, appSession.getAppPublicKey());
            }
        });
    }


    private void bindData() {

        //ActorIdentity broker = appSession.getSelectedBrokerIdentity();
        //Currency currencyToBuy = appSession.getCurrencyToBuy();

        //Test implementation
//        currencyToBuy=new Currency() {
//            @Override
//            public String getFriendlyName() {
//                return "BTC";
//            }
//
//            @Override
//            public String getCode() {
//                return MoneyType.CRYPTO.getCode();
//            }
//
//            @Override
//            public CurrencyTypes getType() {
//                return null;
//            }
//        };
        //Negotiation Summary
        /*Drawable brokerImg = getImgDrawable(broker.getProfileImage());
        brokerImage.setImageDrawable(brokerImg);
        brokerName.setText(broker.getAlias());
        sellingSummary.setText(getResources().getString(R.string.ccw_start_selling_details, currencyToBuy.getFriendlyName()));*/
        //Contract summary
        //Drawable brokerImg = getImgDrawable(broker.getProfileImage());
        //brokerImage.setImageDrawable(brokerImg);
        //brokerName.setText(broker.getAlias());
        //brokerName.setText("Broker Name");

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yy");

        String paymentCurrency = data.getPaymentCurrency();
        Date date = new Date(data.getLastUpdate());
        double exchangeRateAmount = getFormattedNumber(data.getExchangeRateAmount());
        double amount = getFormattedNumber(data.getAmount());

        brokerName.setText(data.getCryptoCustomerAlias());
        sellingSummary.setText("SELLING " + paymentCurrency);
        detailDate.setText("Date:\n" + formatter.format(date));
        //detailRate.setText("1 BTC @ 254 USD");

        detailRate.setText(exchangeRateAmount + " " + paymentCurrency + " @ " + amount + " " + data.getMerchandise());

    }


    private List<ContractDetail> prepareContractInfo() {

        List<ContractDetail> contractDetails = new ArrayList<>();
        ContractDetail contractDetail;

        if (walletManager != null) {

            try {
                CustomerBrokerContractPurchase customerBrokerContractPurchase = walletManager.getCustomerBrokerContractPurchaseByNegotiationId(data.getNegotiationId().toString());

                String exchangeRate = "MK.ER";
                String paymentCurrency = "MK.CUR";
                String paymentAmount = "123.45";
                String paymentPaymentMethod = "MK.PM";
                String merchandiseCurrency = "MK.CUR";
                String merchandiseAmount = "234.56";
                String merchandisePaymentMethod = "MK.PM";

                try{
                    //TODO: Esta linea explota, probablemente pues no existe ninguna negociacion... si tal, usar los mocks arriba....
                    CustomerBrokerNegotiationInformation negotiationInformation = walletManager.getNegotiationInformation(data.getNegotiationId());
                    Map<ClauseType, ClauseInformation> clauses = negotiationInformation.getClauses();

                    //Extract info from negotiation
                    exchangeRate = clauses.get(ClauseType.EXCHANGE_RATE).getValue();
                    paymentCurrency = clauses.get(ClauseType.BROKER_CURRENCY).getValue();
                    paymentAmount = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY).getValue();
                    paymentPaymentMethod = clauses.get(ClauseType.BROKER_PAYMENT_METHOD).getValue();

                    merchandiseCurrency = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
                    merchandiseAmount = clauses.get(ClauseType.CUSTOMER_CURRENCY_QUANTITY).getValue();
                    merchandisePaymentMethod = clauses.get(ClauseType.CUSTOMER_PAYMENT_METHOD).getValue();

                }catch(Exception e) {e.printStackTrace();}



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
                        data.getLastUpdate());              //TODO: falta el date que el customer envio el pago
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
                        data.getLastUpdate());              //TODO: falta el date que el broker acepto el pago
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
                        data.getLastUpdate());              //TODO: falta el date que el broker envio mercancia
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
                        data.getLastUpdate());              //TODO: falta el date que el customer recibio mercancia
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

    private EmptyCustomerBrokerNegotiationInformation createNewEmptyNegotiationInfo() {
        try {
            /*EmptyCustomerBrokerNegotiationInformation contractInformation = TestData.newEmptyNegotiationInformation();
            contractInformation.setStatus(NegotiationStatus.WAITING_FOR_BROKER);

            final Currency currency = appSession.getCurrencyToBuy();
            contractInformation.putClause(ClauseType.CUSTOMER_CURRENCY, currency.getCode());
            contractInformation.putClause(ClauseType.BROKER_CURRENCY, currencies.get(0).getCode());
            contractInformation.putClause(ClauseType.CUSTOMER_CURRENCY_QUANTITY, "0.0");
            contractInformation.putClause(ClauseType.BROKER_CURRENCY_QUANTITY, "0.0");
            contractInformation.putClause(ClauseType.EXCHANGE_RATE, "0.0");
            contractInformation.putClause(ClauseType.CUSTOMER_PAYMENT_METHOD, paymentMethods.get(0));
            contractInformation.putClause(ClauseType.BROKER_PAYMENT_METHOD, paymentMethods.get(0));

            final ActorIdentity brokerIdentity = appSession.getSelectedBrokerIdentity();
            if (brokerIdentity != null)
                contractInformation.setBroker(brokerIdentity);

            final CryptoCustomerIdentity customerIdentity = walletManager.getAssociatedIdentity();
            if (customerIdentity != null)
                contractInformation.setCustomer(customerIdentity);

            return contractInformation;*/

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        return null;
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


}
