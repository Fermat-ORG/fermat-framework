package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contract_detail.ContractDetailActivityFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/02/16.
 */
public class ContractDetailViewHolder extends FermatViewHolder implements View.OnClickListener {
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

    //Managers
    ErrorManager errorManager;
    protected CryptoBrokerWalletManager walletManager;

    //Data
    protected ContractDetail contractDetail;
    protected int itemPosition;

    //UI
    private Resources res;
    private View itemView;
    private ContractDetailActivityFragment parentFragment;
    public ImageView stepNumber;
    public FermatTextView stepTitle;
    public FermatTextView textDescription;
    public FermatTextView textDescription2;
    public FermatTextView textDescriptionDate;
    public FermatButton textButton;
    public FermatButton confirmButton;


    public ContractDetailViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();



        stepNumber = (ImageView) itemView.findViewById(R.id.cbw_contract_detail_step);
        stepTitle = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_card_view_title);
        textDescription = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_description_text);
        textDescription2 = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_description_text_2);
        textDescriptionDate = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_description_date);
        textButton = (FermatButton) itemView.findViewById(R.id.cbw_contract_detail_text_button);
        confirmButton = (FermatButton) itemView.findViewById(R.id.cbw_contract_detail_confirm_button);
        confirmButton.setOnClickListener(this);
        confirmButton.setVisibility(View.INVISIBLE);
        /*customerImage = (ImageView) itemView.findViewById(R.id.cbw_customer_image);
        customerName = (FermatTextView) itemView.findViewById(R.id.cbw_customer_name);
        soldQuantityAndCurrency = (FermatTextView) itemView.findViewById(R.id.cbw_sold_quantity_and_currency);
        exchangeRateAmountAndCurrency = (FermatTextView) itemView.findViewById(R.id.cbw_exchange_rate_amount_and_currency);
        lastUpdateDate = (FermatTextView) itemView.findViewById(R.id.cbw_last_update_date);*/

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.cbw_contract_detail_confirm_button)
        {
            executeContractAction();
        }
    }

    protected void executeContractAction(){
        try{

            switch (contractDetail.getContractStep()) {
                case 2:
                    //Send the payment to the Broker and update CardView background
                    walletManager.ackPayment(contractDetail.getContractId().toString());
                    itemView.setBackgroundColor(res.getColor(R.color.card_background_status_changed));
                    confirmButton.setVisibility(View.INVISIBLE);
                    //Toast.makeText(this.parentFragment.getActivity(), "The payment has been delivered", Toast.LENGTH_SHORT).show();


                    //updateBackground(contractDetail.getContractId().toString(), ContractDetailType.CUSTOMER_DETAIL);
                    break;
                case 3:
                    //Confirm the reception of the broker's merchandise
                    walletManager.submitMerchandise(contractDetail.getContractId().toString());
                    itemView.setBackgroundColor(res.getColor(R.color.card_background_status_changed));
                    confirmButton.setVisibility(View.INVISIBLE);
                    //Toast.makeText(this.parentFragment.getActivity(), "The merchandise has been accepted", Toast.LENGTH_SHORT).show();

                    //updateBackground(this.contractId.toString(), ContractDetailType.BROKER_DETAIL);
                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(this.parentFragment.getActivity(), "Please try again, an error occurred.", Toast.LENGTH_SHORT).show();

            Log.e(this.parentFragment.getTag(), ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }

    }

    private void updateBackground(String contractHash, ContractDetailType contractDetailType){
        try{
            ContractStatus contractStatus=this.walletManager.getContractStatus(contractHash);
            ContractStatus backgroundContractStatus=getContractStatusByContractDetailType(
                    contractStatus,
                    contractDetailType);
            itemView.setBackgroundColor(getStatusBackgroundColor(backgroundContractStatus));
            textButton.setText(contractStatus.getFriendlyName());
        } catch (CantGetListCustomerBrokerContractSaleException ex) {
            Toast.makeText(this.parentFragment.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(this.parentFragment.getTag(), ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }

    public void setErrorManager(ErrorManager errorManager){
        this.errorManager=errorManager;
    }

    public void setParentFragment(ContractDetailActivityFragment parentFragment){
        this.parentFragment=parentFragment;
    }

    public void setWalletModuleManager(CryptoBrokerWalletManager walletManager){
        this.walletManager=walletManager;
    }

    public void bind(ContractDetail itemInfo) {

        //Locally save contractDetail
        this.contractDetail = itemInfo;

        //ContractStatus contractStatus = itemInfo.getContractStatus();
        //ContractDetailType contractDetailType=itemInfo.getContractDetailType();


        //TODO: fk is this for?
        //ContractStatus visualContractStatus=getContractStatusByContractDetailType(contractStatus, contractDetailType);
        //itemView.setBackgroundColor(getStatusBackgroundColor(visualContractStatus));



        switch (itemInfo.getContractStep()){
            case 1:
                stepNumber.setImageResource(R.drawable.bg_detail_number_01);
                stepTitle.setText("Payment Delivery");
                textButton.setText(getFormattedAmount(itemInfo.getPaymentOrMerchandiseAmount(), itemInfo.getPaymentOrMerchandiseCurrencyCode()));
                try {
                    textDescription2.setText("using " + MoneyType.getByCode(itemInfo.getPaymentOrMerchandiseTypeOfPayment()).getFriendlyName() );
                }catch (FermatException ex){
                    Toast.makeText(this.parentFragment.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

                    Log.e(this.parentFragment.getTag(), ex.getMessage(), ex);
                    if (errorManager != null) {
                        errorManager.reportUnexpectedWalletException(
                                Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                                ex);
                    }
                }
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                        textDescription.setText("Send:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        confirmButton.setVisibility(View.INVISIBLE);
                        break;

                    default:
                        textDescription.setText("You sent:");
                        textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_accepted));

                }
                break;

            case 2:
                stepNumber.setImageResource(R.drawable.bg_detail_number_02);
                stepTitle.setText("Payment Reception");
                textButton.setText(getFormattedAmount(itemInfo.getPaymentOrMerchandiseAmount(), itemInfo.getPaymentOrMerchandiseCurrencyCode()));
                try {
                    textDescription2.setText("using " + MoneyType.getByCode(itemInfo.getPaymentOrMerchandiseTypeOfPayment()).getFriendlyName() );
                }catch (FermatException ex){
                    Toast.makeText(this.parentFragment.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

                    Log.e(this.parentFragment.getTag(), ex.getMessage(), ex);
                    if (errorManager != null) {
                        errorManager.reportUnexpectedWalletException(
                                Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                                ex);
                    }
                }

                confirmButton.setVisibility(View.INVISIBLE);
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                    case PAYMENT_SUBMIT:
                        textDescription.setText("Broker receives:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        confirmButton.setText("Confirm");
                        break;

                    default:
                        textDescription.setText("Broker received:");
                        textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                }
                break;

            case 3:
                stepNumber.setImageResource(R.drawable.bg_detail_number_03);
                stepTitle.setText("Merchandise Delivery");
                textButton.setText(getFormattedAmount(itemInfo.getPaymentOrMerchandiseAmount(), itemInfo.getPaymentOrMerchandiseCurrencyCode()));
                try {
                    textDescription2.setText("using " + MoneyType.getByCode(itemInfo.getPaymentOrMerchandiseTypeOfPayment()).getFriendlyName() );
                }catch (FermatException ex){
                    Toast.makeText(this.parentFragment.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

                    Log.e(this.parentFragment.getTag(), ex.getMessage(), ex);
                    if (errorManager != null) {
                        errorManager.reportUnexpectedWalletException(
                                Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                                ex);
                    }
                }
                confirmButton.setVisibility(View.INVISIBLE);
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                    case PAYMENT_SUBMIT:
                    case PENDING_MERCHANDISE:
                        textDescription.setText("Broker sends:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        confirmButton.setText("Confirm");
                        break;

                    default:
                        textDescription.setText("Broker sent:");
                        textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                }
                break;

            case 4:
                stepNumber.setImageResource(R.drawable.bg_detail_number_04);
                stepTitle.setText("Merchandise reception");
                textButton.setText(getFormattedAmount(itemInfo.getPaymentOrMerchandiseAmount(), itemInfo.getPaymentOrMerchandiseCurrencyCode()));
                try {
                    textDescription2.setText("using " + MoneyType.getByCode(itemInfo.getPaymentOrMerchandiseTypeOfPayment()).getFriendlyName() );
                }catch (FermatException ex){
                    Toast.makeText(this.parentFragment.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

                    Log.e(this.parentFragment.getTag(), ex.getMessage(), ex);
                    if (errorManager != null) {
                        errorManager.reportUnexpectedWalletException(
                                Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                                ex);
                    }
                }
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                    case PAYMENT_SUBMIT:
                    case PENDING_MERCHANDISE:
                    case MERCHANDISE_SUBMIT:
                        textDescription.setText("You receive:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_confirm));

                        break;

                    default:
                        textDescription.setText("You received:");
                        textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_accepted));

                }
                break;

        }
        //TODO: here we can see the contract status
        //textButton.setText(visualContractStatus.getFriendlyName());
        /*customerName.setText(itemInfo.getCryptoCustomerAlias());
        customerImage.setImageDrawable(getImgDrawable(itemInfo.getCryptoCustomerImage()));

        String soldQuantityAndCurrencyText = getSoldQuantityAndCurrencyText(itemInfo, contractStatus);
        soldQuantityAndCurrency.setText(soldQuantityAndCurrencyText);

        String exchangeRateAmountAndCurrencyText = getExchangeRateAmountAndCurrencyText(itemInfo);
        exchangeRateAmountAndCurrency.setText(exchangeRateAmountAndCurrencyText);

        CharSequence date = DateFormat.format("dd MMM yyyy", itemInfo.getLastUpdate());
        lastUpdateDate.setText(date);*/
    }





    /* HELPER FUNCTIONS */




    /**
     * This method returns the friendly name from a contract status by contract detail type.
     * @param contractStatus
     * @param contractDetailType
     * @return
     */
    private ContractStatus getContractStatusByContractDetailType(
            ContractStatus contractStatus,
            ContractDetailType contractDetailType){
        switch (contractStatus){
            case CANCELLED:
                return ContractStatus.CANCELLED;
            case COMPLETED:
                return ContractStatus.COMPLETED;
            case MERCHANDISE_SUBMIT:
                switch (contractDetailType){
                    case BROKER_DETAIL:
                        return ContractStatus.MERCHANDISE_SUBMIT;
                    case CUSTOMER_DETAIL:
                        return ContractStatus.PAYMENT_SUBMIT;
                }
            case PAUSED:
                return ContractStatus.PAUSED;
            case PENDING_MERCHANDISE:
                switch (contractDetailType){
                    case BROKER_DETAIL:
                        return ContractStatus.PENDING_MERCHANDISE;
                    case CUSTOMER_DETAIL:
                        return ContractStatus.PAYMENT_SUBMIT;
                }
            case PENDING_PAYMENT:
                switch (contractDetailType){
                    case BROKER_DETAIL:
                        return ContractStatus.PENDING_MERCHANDISE;
                    case CUSTOMER_DETAIL:
                        return ContractStatus.PENDING_PAYMENT;
                }
            case PAYMENT_SUBMIT:
                switch (contractDetailType){
                    case BROKER_DETAIL:
                        return ContractStatus.PENDING_MERCHANDISE;
                    case CUSTOMER_DETAIL:
                        return ContractStatus.PAYMENT_SUBMIT;
                }
            case READY_TO_CLOSE:
                return ContractStatus.READY_TO_CLOSE;
            default:
                return ContractStatus.PAUSED;
        }
    }

//    @NonNull
//    private String getSoldQuantityAndCurrencyText(ContractDetail itemInfo, ContractStatus contractStatus) {
//        String sellingOrSoldText = getSellingOrSoldText(contractStatus);
//        String amount = decimalFormat.format(itemInfo.getCurrencyAmount());
//        String merchandise = itemInfo.getCurrencyCode();
//
//        return res.getString(R.string.ccw_contract_history_sold_quantity_and_currency, sellingOrSoldText, amount, merchandise);
//    }

    @NonNull
    private String getFormattedAmount(float amount, String currencyCode) {
        return (decimalFormat.format(amount) + " " + currencyCode);
    }

    @NonNull
    private String getFormattedDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yy");
        return df2.format(date);
    }


//    @NonNull
//    private String getExchangeRateAmountAndCurrencyText(ContractDetail itemInfo) {
//        String merchandise = itemInfo.getCurrencyCode();
//        String exchangeAmount = decimalFormat.format(itemInfo.getExchangeRateAmount());
//        String paymentCurrency = itemInfo.getCurrencyCode();
//
//        return res.getString(R.string.ccw_contract_history_exchange_rate_amount_and_currency, merchandise, exchangeAmount, paymentCurrency);
//    }

    private int getStatusBackgroundColor(ContractStatus status) {

        switch (status){
            case COMPLETED:
                return res.getColor(R.color.contract_completed_list_item_background);
            case CANCELLED:
                return res.getColor(R.color.contract_cancelled_list_item_background);
            case READY_TO_CLOSE:
                return res.getColor(R.color.contract_completed_list_item_background);
            case PAUSED:
                return res.getColor(R.color.contract_paused_list_item_background);
            case PENDING_PAYMENT:
                return res.getColor(R.color.waiting_for_customer_list_item_background);
            case PAYMENT_SUBMIT:
                return res.getColor(R.color.contract_completed_list_item_background);
            case PENDING_MERCHANDISE:
                return res.getColor(R.color.waiting_for_broker_list_item_background);
            case MERCHANDISE_SUBMIT:
                return res.getColor(R.color.contract_completed_list_item_background);
            default:
                return res.getColor(R.color.waiting_for_broker_list_item_background);

        }
        /*if (status == ContractStatus.PENDING_PAYMENT)
            return res.getColor(R.color.waiting_for_customer_list_item_background);

        if (status == ContractStatus.CANCELLED)
            return res.getColor(R.color.contract_cancelled_list_item_background);

        if (status == ContractStatus.COMPLETED)
            return res.getColor(R.color.contract_completed_list_item_background);

        return res.getColor(R.color.waiting_for_broker_list_item_background);*/
    }

    private String getSellingOrSoldText(ContractStatus status) {
        if (status == ContractStatus.COMPLETED)
            return res.getString(R.string.bought);
        return res.getString(R.string.selling);
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }

    protected int getClauseNumberImageRes(int clauseNumber) {
        switch (clauseNumber) {
            case 1:
                return R.drawable.bg_detail_number_01;
            case 2:
                return R.drawable.bg_detail_number_02;
            case 3:
                return R.drawable.bg_detail_number_03;
            case 4:
                return R.drawable.bg_detail_number_04;
            case 5:
                return R.drawable.bg_detail_number_05;
            case 6:
                return R.drawable.bg_detail_number_06;
            case 7:
                return R.drawable.bg_detail_number_07;
            case 8:
                return R.drawable.bg_detail_number_08;
            default:
                return R.drawable.bg_detail_number_09;
        }
    }

}
