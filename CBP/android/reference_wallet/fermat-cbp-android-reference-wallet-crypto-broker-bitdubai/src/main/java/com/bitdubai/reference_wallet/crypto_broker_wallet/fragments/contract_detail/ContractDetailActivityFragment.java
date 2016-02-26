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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.ContractDetailAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/02/16.
 */
public class ContractDetailActivityFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager> {

    private static final String TAG = "ContractDetailFrag";

    private CryptoBrokerWalletManager walletManager;
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
            CryptoBrokerWalletModuleManager moduleManager = appSession.getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
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
                appSession.setData(CryptoBrokerWalletSession.NEGOTIATION_DATA, data);
                String a = appSession.getAppPublicKey();
                try {
                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS, a);
                }catch (Exception e)
                {
                    System.out.println(e+"");
                }




            }
        });
        String paymentCurrency=data.getPaymentCurrency();
        brokerName.setText(data.getCryptoCustomerAlias());
        sellingSummary.setText("SELLING "+paymentCurrency);
        Date date=new Date(data.getLastUpdate());
        //TODO: we can introduce locale for date format
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yy");
        detailDate.setText("Date:\n"+formatter.format(date));
        //detailRate.setText("1 BTC @ 254 USD");
        double exchangeRateAmount= getFormattedNumber(data.getExchangeRateAmount());
        double amount= getFormattedNumber(data.getAmount());


        //Create adapter
        adapter = new ContractDetailAdapter(
                getActivity(),
                contractInformation,
                appSession,
                walletManager);
        //adapter.setFooterListener(this);
        //adapter.setClauseListener(this);

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
        /**
         * TODO: this contract details is only for testing, please, implement this date from database.
         */
        ContractDetail contractDetail;
        //Testing Broker
        contractDetail=new ContractDetail(
                ContractDetailType.BROKER_DETAIL,
                MoneyType.CRYPTO.getCode(),
                CryptoCurrency.BITCOIN.getFriendlyName(),
                12,
                ContractStatus.PENDING_MERCHANDISE,
                "BTC Broker",
                getByteArrayFromImageView(customerImage),
                1961,
                2016,
                UUID.randomUUID());
        //contractDetails.add(contractDetail);
        //Customer Broker
        contractDetail=new ContractDetail(
                ContractDetailType.CUSTOMER_DETAIL,
                MoneyType.BANK.getCode(),
                FiatCurrency.CHINESE_YUAN.getFriendlyName(),
                12,
                ContractStatus.PAYMENT_SUBMIT,
                "BTC Customer",
                getByteArrayFromImageView(customerImage),
                1961,
                2016,
                UUID.randomUUID());
        //contractDetails.add(contractDetail);

        /**
         * Get the wallet module manager
         */
        //TODO: when the module is finished, use the followings lines to create contract details.
        CryptoBrokerWalletModuleManager cryptoCustomerWalletModuleManager=
                appSession.getModuleManager();
        if(cryptoCustomerWalletModuleManager!=null){

            try{
                CryptoBrokerWalletManager cryptoCustomerWalletManager=
                        cryptoCustomerWalletModuleManager.getCryptoBrokerWallet(
                                appSession.getAppPublicKey()
                        );
                //ContractDetail contractDetail;
                CustomerBrokerContractSale customerBrokerContractPurchase=
                        cryptoCustomerWalletManager.
                                getCustomerBrokerContractSaleByNegotiationId(
                                        data.getNegotiationId().toString());
                //Customer
                contractDetail=new ContractDetail(
                        ContractDetailType.CUSTOMER_DETAIL,
                        data.getTypeOfPayment(),
                        data.getPaymentCurrency(),
                        data.getAmount(),
                        customerBrokerContractPurchase.getStatus(),
                        data.getCryptoCustomerAlias(),
                        data.getCryptoCustomerImage(),
                        data.getLastUpdate(),
                        data.getExchangeRateAmount(),
                        data.getContractId());
                contractDetails.add(contractDetail);
                //Broker
                contractDetail=new ContractDetail(
                        ContractDetailType.BROKER_DETAIL,
                        data.getTypeOfPayment(),
                        data.getMerchandise(),
                        data.getAmount(),
                        customerBrokerContractPurchase.getStatus(),
                        data.getCryptoCustomerAlias(),
                        data.getCryptoCustomerImage(),
                        data.getLastUpdate(),
                        data.getExchangeRateAmount(),
                        data.getContractId());
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

}

