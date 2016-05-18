package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contract_detail;

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
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.ContractDetailAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/02/16.
 */
public class ContractDetailActivityFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager> {

    private static final String TAG = "ContractDetailFrag";

    private CryptoBrokerWalletModuleManager walletModuleManager;
    private ErrorManager errorManager;
    private List<ContractDetail> contractInformation;
    private ContractBasicInformation data;
    private ArrayList<String> paymentMethods; // test data
    private ArrayList<Currency> currencies; // test data

    private ImageView customerImage;
    private FermatTextView sellingSummary;
    private FermatTextView detailDate;
    private FermatTextView detailRate;
    private FermatTextView brokerName;
    private FermatButton negotiationButton;
    private RecyclerView recyclerView;
    private ContractDetailAdapter adapter;

    public static ContractDetailActivityFragment newInstance() {
        return new ContractDetailActivityFragment();
    }

    /**
     * This method will be execute at the screen start.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            walletModuleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            //TODO: load contract here
            data=(ContractBasicInformation) appSession.getData("contract_data");
            appSession.setData("ContractDetailFragment", this);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.cbw_fragment_contract_detail_activity, container, false);

        configureToolbar();
        initViews(layout);
        contractInformation=createContractDetails();
        bindData();

        return layout;
    }

    //TODO: analize the following methods
    private void initViews(View rootView) {

        customerImage = (ImageView) rootView.findViewById(R.id.cbw_contract_details_broker_image);
        brokerName = (FermatTextView) rootView.findViewById(R.id.cbw_contract_details_broker_name);
        sellingSummary = (FermatTextView) rootView.findViewById(R.id.cbw_contract_details_selling_summary);
        detailDate = (FermatTextView) rootView.findViewById(R.id.cbw_contract_details_date);
        detailRate = (FermatTextView) rootView.findViewById(R.id.cbw_contract_details_rate);
        negotiationButton = (FermatButton) rootView.findViewById(R.id.cbw_contract_details_negotiation_details);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.cbw_contract_details_contract_steps_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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

        negotiationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerBrokerNegotiationInformation negotiationInfo;
                try {
                    negotiationInfo = walletModuleManager.getNegotiationInformation(data.getNegotiationId());
                    appSession.setData(CryptoBrokerWalletSession.NEGOTIATION_DATA, negotiationInfo);
                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS_OPEN_CONTRACT, appSession.getAppPublicKey());

                } catch (FermatException ex) {
                    if (errorManager != null)
                        errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                    else
                        Log.e(TAG, ex.getMessage(), ex);

                    // TODO Just for testing. Add a Toast later
                    negotiationInfo = TestData.getOpenNegotiations(NegotiationStatus.WAITING_FOR_BROKER).get(0);
                    appSession.setData(CryptoBrokerWalletSession.NEGOTIATION_DATA, negotiationInfo);
                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS_OPEN_CONTRACT, appSession.getAppPublicKey());
                }
            }
        });
        String paymentCurrency = data.getPaymentCurrency();
        brokerName.setText(data.getCryptoCustomerAlias());
        customerImage.setImageDrawable(getImgDrawable(data.getCryptoCustomerImage()));
        sellingSummary.setText("BUYING " + data.getMerchandise());
        //TODO: averiguar el detailrate como escribirlo
        //detailRate.setText("1 BTC @"+ data.getExchangeRateAmount() + " "+ data.getPaymentCurrency());
        //detailRate.setText("1 BTC @ 254 USD");

        Date date = new Date(data.getLastUpdate());
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yy");
        detailDate.setText("Date:\n"+formatter.format(date));
        double exchangeRateAmount= getFormattedNumber(data.getExchangeRateAmount());
        double amount= getFormattedNumber(data.getAmount());
        adapter = new ContractDetailAdapter(getActivity(), contractInformation, appSession, walletModuleManager, this);
        detailRate.setText("1" + " " + data.getMerchandise() + " @ " + exchangeRateAmount + " " + paymentCurrency);
        recyclerView.setAdapter(adapter);
    }

    private double getFormattedNumber(float number){
        int decimalPlaces=2;
        BigDecimal bigDecimalNumber = new BigDecimal(number);
        bigDecimalNumber=bigDecimalNumber.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        return bigDecimalNumber.doubleValue();
    }

    private List<ContractDetail> createContractDetails(){
        List<ContractDetail> contractDetails=new ArrayList<>();
        ContractDetail contractDetail;
        //TODO: when the module is finished, use the followings lines to create contract details.
        CryptoBrokerWalletModuleManager cryptoBrokerWalletModuleManager= appSession.getModuleManager();
        if(walletModuleManager !=null){
            try{
                //ContractDetail contractDetail;
                CustomerBrokerContractSale customerBrokerContractSale= cryptoBrokerWalletModuleManager.getCustomerBrokerContractSaleByNegotiationId(data.getNegotiationId().toString());
                //Customer
                ContractStatus contractStatus = customerBrokerContractSale.getStatus();

                String exchangeRate = "MK";
                String paymentCurrency = "MK";
                String paymentAmount = "-1";
                String paymentPaymentMethod = "MK";
                String merchandiseCurrency = "MK";
                String merchandiseAmount = "-1";
                String merchandisePaymentMethod = "MK";
                MoneyType paymentMethodType = MoneyType.BANK;

                try{
                    Collection<Clause> clauses = walletModuleManager.getNegotiationClausesFromNegotiationId(data.getNegotiationId());

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
                            paymentMethodType= MoneyType.getByCode(clause.getValue());
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
                            paymentMethodType= MoneyType.getByCode(clause.getValue());
                        }
                    }

                }catch(Exception e) {e.printStackTrace();}


                long paymentSubmitDate = walletModuleManager.getCompletionDateForContractStatus(data.getContractId(), ContractStatus.PAYMENT_SUBMIT, paymentPaymentMethod);
                long paymentAckDate = walletModuleManager.getCompletionDateForContractStatus(data.getContractId(), ContractStatus.PENDING_MERCHANDISE, paymentPaymentMethod);
                long merchandiseSubmitDate = walletModuleManager.getCompletionDateForContractStatus(data.getContractId(), ContractStatus.MERCHANDISE_SUBMIT, merchandisePaymentMethod);
                long merchandiseAckDate = walletModuleManager.getCompletionDateForContractStatus(data.getContractId(), ContractStatus.READY_TO_CLOSE, merchandisePaymentMethod);


                //Payment delivery
                contractDetail = new ContractDetail(
                        1,
                        data.getStatus(),
                        data.getContractId(),
                        data.getNegotiationId(),
                        paymentAmount,
                        paymentPaymentMethod,
                        paymentCurrency,
                        paymentSubmitDate,paymentMethodType);
                contractDetails.add(contractDetail);

                //Payment Reception step
                contractDetail = new ContractDetail(
                        2,
                        data.getStatus(),
                        data.getContractId(),
                        data.getNegotiationId(),
                        paymentAmount,
                        paymentPaymentMethod,
                        paymentCurrency,
                        paymentAckDate,paymentMethodType);
                contractDetails.add(contractDetail);

                //Merchandise Delivery step
                contractDetail = new ContractDetail(
                        3,
                        data.getStatus(),
                        data.getContractId(),
                        data.getNegotiationId(),
                        merchandiseAmount,
                        merchandisePaymentMethod,
                        merchandiseCurrency,
                        merchandiseSubmitDate,paymentMethodType);
                contractDetails.add(contractDetail);

                //Merchandise Reception step
                contractDetail = new ContractDetail(
                        4,
                        data.getStatus(),
                        data.getContractId(),
                        data.getNegotiationId(),
                        merchandiseAmount,
                        merchandisePaymentMethod,
                        merchandiseCurrency,
                        merchandiseAckDate,paymentMethodType);
                contractDetails.add(contractDetail);
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
                }
            }

        } else{
            //If module is null, I cannot handle with this.
            Toast.makeText(
                    getActivity(),
                    "Sorry, an error happened in ContractDetailActivityFragment (CryptoCustomerWalletModuleManager == null)",
                    Toast.LENGTH_SHORT)
                    .show();
        }

        return contractDetails;
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        Resources res = getResources();

        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }

    /**
     * This method is for testing
     * @param image
     * @return
     */
    private byte[] getByteArrayFromImageView(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        return stream.toByteArray();
    }

    public void goToWalletHome() {
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());
    }
}

