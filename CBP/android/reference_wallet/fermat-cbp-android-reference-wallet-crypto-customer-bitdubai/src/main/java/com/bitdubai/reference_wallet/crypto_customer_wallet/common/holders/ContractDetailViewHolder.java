package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contract_detail.ContractDetailActivityFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/01/16.
 * Modified by Alejandro Bicelis on 22/02/2016
 */
public class ContractDetailViewHolder extends FermatViewHolder implements View.OnClickListener {

    //Constants
    private static final int PAYMENT_SENDING_IN_PROCESS = 1;
    private static final int MERCHANDISE_RECEPTION_IN_PROCESS = 2;

    //Managers
    ErrorManager errorManager;
    protected CryptoCustomerWalletModuleManager walletManager;
    protected ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> walletSession;

    //Data
    protected ContractDetail contractDetail;
    private ContractDetailActivityFragment fragment;
    int inProcessStatus = 0;


    //UI
    private Resources res;
    private CardView cardView;
    private ContractDetailActivityFragment parentFragment;
    public ImageView stepNumber;
    public FermatTextView stepTitle;
    public FermatTextView textDescription;
    public FermatTextView textDescriptionDate;
    public FermatTextView textDescriptionPending;
    public FermatTextView amountAndMethodTextView;
    public FermatButton confirmButton;


    public ContractDetailViewHolder(View itemView, ContractDetailActivityFragment fragment) {
        super(itemView, 0);

        this.cardView = (CardView) itemView.findViewById(R.id.contract_detail_card_view);
        res = itemView.getResources();

        this.fragment = fragment;
        stepNumber = (ImageView) itemView.findViewById(R.id.ccw_contract_detail_step);
        stepTitle = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_card_view_title);
        textDescription = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_description_text);
        textDescriptionDate = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_description_date);
        textDescriptionPending = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_description_pending);
        amountAndMethodTextView = (FermatTextView) itemView.findViewById(R.id.ccw_contract_detail_amount_and_method);
        confirmButton = (FermatButton) itemView.findViewById(R.id.ccw_contract_detail_confirm_button);
        confirmButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ccw_contract_detail_confirm_button) {
            executeContractAction();
        }
    }

    protected void executeContractAction() {
        try {

            switch (contractDetail.getContractStep()) {
                case 1:
                    //Send the payment to the broker
                    this.walletManager.sendPayment(contractDetail.getContractId());

                    Toast.makeText(this.parentFragment.getActivity(), "The payment has been delivered", Toast.LENGTH_SHORT).show();

                    //Set internal status of this contract to PAYMENT_SENDING_IN_PROCESS
                    walletSession.setData(contractDetail.getContractId(), PAYMENT_SENDING_IN_PROCESS);

                    fragment.goToWalletHome();
                    break;

                case 4:
                    //Confirm the reception of the broker's merchandise
                    this.walletManager.ackMerchandise(contractDetail.getContractId());

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


    @SuppressWarnings("deprecation")
    @SuppressLint("SetTextI18n")
    public void bind(ContractDetail itemInfo) {

        //Locally save contractDetail
        this.contractDetail = itemInfo;

        //Get "in_process" internal status of contract
        // This is done because contract status changes do not get processed immediately.
        Object aux = walletSession.getData(contractDetail.getContractId());
        if (aux != null && aux instanceof Number)
            inProcessStatus = (int) aux;

        final String amount = itemInfo.getPaymentOrMerchandiseAmount();
        final String currencyCode = itemInfo.getPaymentOrMerchandiseCurrencyCode();
        final String typeOfPayment = itemInfo.getPaymentOrMerchandiseTypeOfPayment();

        amountAndMethodTextView.setText(String.format("%1$s %2$s using %3$s", amount, currencyCode, typeOfPayment));

        switch (itemInfo.getContractStep()) {
            case 1:
                stepNumber.setImageResource(R.drawable.bg_detail_number_01);
                stepTitle.setText("Payment Delivery");

                if (stockInWallet(contractDetail.getContractId())) {
                    switch (itemInfo.getContractStatus()) {
                        case PENDING_PAYMENT:

                            //Check internal "in_process" status (If broker clicked Confirm button already but the status has not yet changed)
                            if (inProcessStatus == PAYMENT_SENDING_IN_PROCESS) {
                                textDescription.setText("You sent:");
                                textDescriptionDate.setVisibility(View.INVISIBLE);
                                //                            textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));

                                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_changed));
                                stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_changed));
                                textDescription.setTextColor(res.getColor(R.color.description_text_status_changed));
                                //                            textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_changed));
                                amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_changed));

                                confirmButton.setVisibility(View.VISIBLE);
                                confirmButton.setText("CONFIRMED");
                                confirmButton.setEnabled(false);
                            } else {
                                textDescription.setText("Send:");
                                textDescriptionDate.setVisibility(View.INVISIBLE);
                                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                                confirmButton.setVisibility(View.VISIBLE);
                            }
                            break;
                        default:
                            textDescription.setText("You sent:");
                            textDescriptionDate.setText(new StringBuilder().append("on ").append(getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate())).toString());
                            cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                            stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_accepted));
                            textDescription.setTextColor(res.getColor(R.color.description_text_status_accepted));
                            textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_accepted));
                            amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_accepted));
                    }
                    break;
                } else {
                    textDescription.setText("You send:");
                    textDescriptionPending.setVisibility(View.VISIBLE);
                    textDescriptionPending.setText("NOT HAD ENOUGH STOCK IN THE WALLET");
                    cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                }
            case 2:
                stepNumber.setImageResource(R.drawable.bg_detail_number_02);
                stepTitle.setText("Payment Reception");
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                        textDescription.setText("Broker receives:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                        break;
                    case PAYMENT_SUBMIT:
                        textDescription.setText("Broker receives:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        textDescriptionPending.setVisibility(View.VISIBLE);
                        confirmButton.setVisibility(View.GONE);
                        break;
                    default:
                        textDescription.setText("Broker received:");
                        textDescriptionDate.setText(new StringBuilder().append("on ").append(getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate())).toString());
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                        stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_accepted));
                        textDescription.setTextColor(res.getColor(R.color.description_text_status_accepted));
                        textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_accepted));
                        amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_accepted));
                }
                break;

            case 3:
                stepNumber.setImageResource(R.drawable.bg_detail_number_03);
                stepTitle.setText("Merchandise Delivery");
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                    case PAYMENT_SUBMIT:
                        textDescription.setText("Broker sends:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                        break;
                    case PENDING_MERCHANDISE:
                        textDescription.setText("Broker sends:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        textDescriptionPending.setVisibility(View.VISIBLE);
                        confirmButton.setVisibility(View.GONE);
                        break;
                    default:
                        textDescription.setText("Broker sent:");
                        textDescriptionDate.setText(new StringBuilder().append("on ").append(getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate())).toString());
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                        stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_accepted));
                        textDescription.setTextColor(res.getColor(R.color.description_text_status_accepted));
                        textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_accepted));
                        amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_accepted));
                }
                break;

            case 4:
                stepNumber.setImageResource(R.drawable.bg_detail_number_04);
                stepTitle.setText("Merchandise Reception");
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                    case PAYMENT_SUBMIT:
                    case PENDING_MERCHANDISE:
                        textDescription.setText("You receive:");
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                        break;
                    case MERCHANDISE_SUBMIT:
                        //Check internal "in_process" status (If broker clicked Confirm button already but the status has not yet changed)
                        if (inProcessStatus == MERCHANDISE_RECEPTION_IN_PROCESS) {
                            textDescription.setText("You received:");
                            textDescriptionDate.setVisibility(View.INVISIBLE);
//                            textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                            confirmButton.setVisibility(View.VISIBLE);
                            confirmButton.setText("CONFIRMED");
                            confirmButton.setEnabled(false);

                            cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_changed));
                            stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_changed));
                            textDescription.setTextColor(res.getColor(R.color.description_text_status_changed));
//                            textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_changed));
                            amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_changed));

                        } else {
                            textDescription.setText("You receive:");
                            textDescriptionDate.setVisibility(View.INVISIBLE);
                            cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                            if (itemInfo.getPaymentMethodType() != MoneyType.CRYPTO) {
                                confirmButton.setText("Confirm");
                                confirmButton.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    default:
                        textDescription.setText("You received:");
                        textDescriptionDate.setText(new StringBuilder().append("on ").append(getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate())).toString());
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                        stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_accepted));
                        textDescription.setTextColor(res.getColor(R.color.description_text_status_accepted));
                        textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_accepted));
                        amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_accepted));
                }
                break;

        }
    }


    /* SETTERS */
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    public void setParentFragment(ContractDetailActivityFragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    public void setWalletModuleManager(CryptoCustomerWalletModuleManager walletManager) {
        this.walletManager = walletManager;
    }

    public void setSession(ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> session) {
        this.walletSession = session;
    }


    /* HELPER FUNCTIONS */

    @NonNull
    private String getFormattedDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        return df2.format(date);
    }

    /**
     * This method validate if has enough stock for send a merchandise according the contract elements.
     *
     * @param contractId the contract ID
     */
    private boolean stockInWallet(String contractId) {
        try {
            return walletManager.stockInTheWallet(contractId);
        } catch (CantSubmitMerchandiseException ex) {
            Toast.makeText(this.parentFragment.getActivity(), "Please try again, an error occurred.", Toast.LENGTH_SHORT).show();

            Log.e(this.parentFragment.getTag(), ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
        return Boolean.FALSE;

    }
}
