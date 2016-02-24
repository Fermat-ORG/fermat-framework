package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contract_detail.ContractDetailActivityFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/02/16.
 */
public class ContractDetailViewHolder extends FermatViewHolder {

    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private Resources res;
    private View itemView;
    private CardView containerCardView;
    /**
     * Contract item
     */
    protected UUID contractId;
    protected CryptoBrokerWalletManager walletManager;
    private ContractDetailActivityFragment parentFragment;

    public ImageView customerImage;
    public ImageView stepNumber;
    public FermatTextView customerName;
    public FermatTextView soldQuantityAndCurrency;
    public FermatTextView exchangeRateAmountAndCurrency;
    public FermatTextView lastUpdateDate;
    public FermatTextView stepTitle;
    public FermatTextView textDescription;
    public FermatButton textButton;
    public FermatButton confirmButton;
    protected int itemPosition;

    ErrorManager errorManager;

    public ContractDetailViewHolder(View itemView, int holderType) {
        super(itemView, holderType);
        this.itemView = itemView;
        res = itemView.getResources();
        containerCardView = (CardView) this.itemView;
        stepNumber = (ImageView) itemView.findViewById(R.id.cbw_contract_detail_step);
        stepTitle = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_card_view_title);
        textDescription = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_description_text);
        textButton = (FermatButton) itemView.findViewById(R.id.cbw_contract_detail_text_button);
        confirmButton = (FermatButton) itemView.findViewById(R.id.cbw_contract_detail_confirm_button);
        configButton();
    }

    /**
     * Constructor
     *
     * @param itemView
     */




    /*public ContractDetailViewHolder(View itemView) {
        super(itemView);


        /*customerImage = (ImageView) itemView.findViewById(R.id.ccw_customer_image);
        customerName = (FermatTextView) itemView.findViewById(R.id.ccw_customer_name);
        soldQuantityAndCurrency = (FermatTextView) itemView.findViewById(R.id.ccw_sold_quantity_and_currency);
        exchangeRateAmountAndCurrency = (FermatTextView) itemView.findViewById(R.id.ccw_exchange_rate_amount_and_currency);
        lastUpdateDate = (FermatTextView) itemView.findViewById(R.id.ccw_last_update_date);

    }*/

    private void configButton(){
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonTest = confirmButton.getText().toString();
                executeContractAction(buttonTest);
            }
        });
    }

    protected void executeContractAction(String buttonText){
        try{
            if(buttonText.equals("CONFIRM")){
                //TODO: Ack Payment
                //In this case, I will ack the payment to Customer
                this.walletManager.ackPayment(this.contractId.toString());
                updateBackground(
                        this.contractId.toString(),
                        ContractDetailType.BROKER_DETAIL);
            }
            if(buttonText.equals("SEND")){
                //TODO: Send Merchandise
                //In this case, I will send the broker merchandise
                this.walletManager.submitMerchandise(this.contractId.toString());
                updateBackground(
                        this.contractId.toString(),
                        ContractDetailType.CUSTOMER_DETAIL);
            }
            //TODO: catch the proper exception when the wallet module is ready (the catch is commented)
        //} catch (Exception ex) {
        } catch (FermatException ex) {
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

    private void updateBackground(String contractHash, ContractDetailType contractDetailType){
        try{
            //TODO: remove the hardcode when the wallet module is ready
            //ContractStatus contractStatus=ContractStatus.PENDING_PAYMENT;
            ContractStatus contractStatus=this.walletManager.getContractStatus(contractHash);
            ContractStatus backgroundContractStatus=getContractStatusByContractDetailType(
                    contractStatus,
                    contractDetailType);
            itemView.setBackgroundColor(getStatusBackgroundColor(backgroundContractStatus));
            textButton.setText(contractStatus.getFriendlyName());
            //TODO: catch the proper exception when the wallet module is ready (the catch is commented)
        //} catch (Exception ex) {
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
        this.contractId=itemInfo.getContractId();
        ContractStatus contractStatus = itemInfo.getContractStatus();
        ContractDetailType contractDetailType=itemInfo.getContractDetailType();
        ContractStatus visualContractStatus=getContractStatusByContractDetailType(
                contractStatus,
                contractDetailType
        );
        itemView.setBackgroundColor(getStatusBackgroundColor(visualContractStatus));
        switch (contractDetailType){
            case CUSTOMER_DETAIL:
                stepNumber.setImageResource(R.drawable.bg_detail_number_01);
                textDescription.setText("Customer");
                confirmButton.setText("CONFIRM");
                break;
            case BROKER_DETAIL:
                stepNumber.setImageResource(R.drawable.bg_detail_number_02);
                textDescription.setText("Broker");
                confirmButton.setText("SEND");
                break;

        }
        //TODO: here we can see the contract status
        textButton.setText(visualContractStatus.getFriendlyName());
        /*customerName.setText(itemInfo.getCryptoCustomerAlias());
        customerImage.setImageDrawable(getImgDrawable(itemInfo.getCryptoCustomerImage()));

        String soldQuantityAndCurrencyText = getSoldQuantityAndCurrencyText(itemInfo, contractStatus);
        soldQuantityAndCurrency.setText(soldQuantityAndCurrencyText);

        String exchangeRateAmountAndCurrencyText = getExchangeRateAmountAndCurrencyText(itemInfo);
        exchangeRateAmountAndCurrency.setText(exchangeRateAmountAndCurrencyText);

        CharSequence date = DateFormat.format("dd MMM yyyy", itemInfo.getLastUpdate());
        lastUpdateDate.setText(date);*/
    }

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

    @NonNull
    private String getSoldQuantityAndCurrencyText(ContractDetail itemInfo, ContractStatus contractStatus) {
        String sellingOrSoldText = getSellingOrSoldText(contractStatus);
        String amount = decimalFormat.format(itemInfo.getCurrencyAmount());
        String merchandise = itemInfo.getCurrencyCode();

        return res.getString(R.string.cbw_contract_history_sold_quantity_and_currency, sellingOrSoldText, amount, merchandise);
    }

    @NonNull
    private String getExchangeRateAmountAndCurrencyText(ContractDetail itemInfo) {
        String merchandise = itemInfo.getCurrencyCode();
        String exchangeAmount = decimalFormat.format(itemInfo.getExchangeRateAmount());
        String paymentCurrency = itemInfo.getCurrencyCode();

        return res.getString(R.string.cbw_contract_history_exchange_rate_amount_and_currency, merchandise, exchangeAmount, paymentCurrency);
    }

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
