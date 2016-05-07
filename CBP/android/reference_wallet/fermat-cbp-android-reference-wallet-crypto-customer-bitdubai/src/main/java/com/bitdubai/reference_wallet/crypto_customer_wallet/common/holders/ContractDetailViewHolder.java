package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail.ContractDetailActivityFragment;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/01/16.
 * Modified by Alejandro Bicelis on 22/02/2016
 */
public class ContractDetailViewHolder extends FermatViewHolder implements View.OnClickListener {

    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

    //Constants
    private static final int PAYMENT_SENDING_IN_PROCESS = 1;
    private static final int MERCHANDISE_RECEPTION_IN_PROCESS = 2;

    //Managers
    ErrorManager errorManager;
    protected CryptoCustomerWalletModuleManager walletManager;
    protected CryptoCustomerWalletSession walletSession;

    //Data
    protected ContractDetail contractDetail;
    protected int itemPosition;
    private ContractDetailActivityFragment fragment;
    int inProcessStatus = 0;


    //UI
    private Resources res;
    private View itemView;
    private ContractDetailActivityFragment parentFragment;
    public ImageView stepNumber;
    public FermatTextView stepTitle;
    public FermatTextView textDescription;
    public FermatTextView textDescription2;
    public FermatTextView textDescriptionDate;
    public FermatTextView textDescriptionPending;
    public FermatButton textButton;
    public FermatButton confirmButton;


    public ContractDetailViewHolder(View itemView, ContractDetailActivityFragment fragment) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();


        this.fragment = fragment;
        stepNumber = (ImageView) itemView.findViewById(R.id.ccw_contract_detail_step);
        stepTitle = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_card_view_title);
        textDescription = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_description_text);
        textDescription2 = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_description_text_2);
        textDescriptionDate = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_description_date);
        textDescriptionPending = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_description_pending);
        textButton = (FermatButton) itemView.findViewById(R.id.ccw_contract_detail_text_button);
        confirmButton = (FermatButton) itemView.findViewById(R.id.ccw_contract_detail_confirm_button);
        confirmButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.ccw_contract_detail_confirm_button)
        {
            executeContractAction();
        }
    }

    protected void executeContractAction(){
        try{

            switch (contractDetail.getContractStep()) {
                case 1:
                    //Send the payment to the broker
                    this.walletManager.sendPayment(contractDetail.getContractId());

                    //itemView.setBackgroundColor(res.getColor(R.color.card_background_status_changed));
                    //confirmButton.setVisibility(View.INVISIBLE);
                    //textDescription.setText("You sent:");
                    Toast.makeText(this.parentFragment.getActivity(), "The payment has been delivered", Toast.LENGTH_SHORT).show();

                    //Set internal status of this contract to PAYMENT_SENDING_IN_PROCESS
                    walletSession.setData(contractDetail.getContractId(), PAYMENT_SENDING_IN_PROCESS);

                    fragment.goToWalletHome();
                    break;

                case 4:
                    //Confirm the reception of the broker's merchandise
                    this.walletManager.ackMerchandise(contractDetail.getContractId());

                    //itemView.setBackgroundColor(res.getColor(R.color.card_background_status_changed));
                    //confirmButton.setVisibility(View.INVISIBLE);
                    //textDescription.setText("You received:");
                    Toast.makeText(this.parentFragment.getActivity(), "The merchandise has been accepted", Toast.LENGTH_SHORT).show();

                    //Set internal status of this contract to MERCHANDISE_RECEPTION_IN_PROCESS
                    walletSession.setData(contractDetail.getContractId(), MERCHANDISE_RECEPTION_IN_PROCESS);


                    fragment.goToWalletHome();
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


    public void bind(ContractDetail itemInfo) {

        //Locally save contractDetail
        this.contractDetail = itemInfo;

        //Get "in_process" internal status of contract
        // This is done because contract status changes do not get processed immediately.
        Object aux = walletSession.getData(contractDetail.getContractId());
        if(aux != null && aux instanceof Number)
            inProcessStatus = (int) aux;

        switch (itemInfo.getContractStep()){
            case 1:
                stepNumber.setImageResource(R.drawable.bg_detail_number_01);
                stepTitle.setText("Payment Delivery");
                textButton.setText(getFormattedAmount(itemInfo.getPaymentOrMerchandiseAmount(), itemInfo.getPaymentOrMerchandiseCurrencyCode()));
                textDescription2.setText("using " + itemInfo.getPaymentOrMerchandiseTypeOfPayment());
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:

                        //Check internal "in_process" status (If broker clicked Confirm button already but the status has not yet changed)
                        if(inProcessStatus == PAYMENT_SENDING_IN_PROCESS)
                        {
                            textDescription.setText("You sent:");
                            textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                            itemView.setBackgroundColor(res.getColor(R.color.card_background_status_changed));
                            confirmButton.setVisibility(View.VISIBLE);
                            confirmButton.setText("CONFIRMED");
                            confirmButton.setEnabled(false);
                        }
                        else {
                            textDescription.setText("Send:");
                            textDescriptionDate.setVisibility(View.INVISIBLE);
                            itemView.setBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                            confirmButton.setVisibility(View.VISIBLE);
                        }
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
                textDescription2.setText("using " + itemInfo.getPaymentOrMerchandiseTypeOfPayment());
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                        textDescription.setText("Broker receives:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                        break;
                    case PAYMENT_SUBMIT:
                        textDescription.setText("Broker receives:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        textDescriptionPending.setVisibility(View.VISIBLE);
                        confirmButton.setVisibility(View.GONE);
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
                textDescription2.setText("using " + itemInfo.getPaymentOrMerchandiseTypeOfPayment());
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                    case PAYMENT_SUBMIT:
                        textDescription.setText("Broker sends:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                        break;
                    case PENDING_MERCHANDISE:
                        textDescription.setText("Broker sends:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        textDescriptionPending.setVisibility(View.VISIBLE);
                        confirmButton.setVisibility(View.GONE);
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
                textDescription2.setText("using " + itemInfo.getPaymentOrMerchandiseTypeOfPayment());
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                    case PAYMENT_SUBMIT:
                    case PENDING_MERCHANDISE:
                        textDescription.setText("You receive:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                        break;
                    case MERCHANDISE_SUBMIT:
                        //Check internal "in_process" status (If broker clicked Confirm button already but the status has not yet changed)
                        if(inProcessStatus == MERCHANDISE_RECEPTION_IN_PROCESS)
                        {
                            textDescription.setText("You received:");
                            textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                            itemView.setBackgroundColor(res.getColor(R.color.card_background_status_changed));
                            confirmButton.setVisibility(View.VISIBLE);
                            confirmButton.setText("CONFIRMED");
                            confirmButton.setEnabled(false);
                        }
                        else
                        {
                            textDescription.setText("You receive:");
                            textDescriptionDate.setVisibility(View.INVISIBLE);
                            itemView.setBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                            if (itemInfo.getPaymentMethodType() != MoneyType.CRYPTO) {
                                confirmButton.setText("Confirm");
                                confirmButton.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    default:
                        textDescription.setText("You received:");
                        textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                        itemView.setBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                }
                break;

        }
    }



    /* SETTERS */
    public void setErrorManager(ErrorManager errorManager){
        this.errorManager=errorManager;
    }

    public void setParentFragment(ContractDetailActivityFragment parentFragment){
        this.parentFragment=parentFragment;
    }

    public void setWalletModuleManager(CryptoCustomerWalletModuleManager walletManager){
        this.walletManager=walletManager;
    }

    public void setSession(CryptoCustomerWalletSession session){
        this.walletSession = session;
    }




    /* HELPER FUNCTIONS */
    @NonNull
    private String getFormattedAmount(String amount, String currencyCode) {
        return amount + " " + currencyCode;
    }

    @NonNull
    private String getFormattedDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yy");
        return df2.format(date);
    }


}
