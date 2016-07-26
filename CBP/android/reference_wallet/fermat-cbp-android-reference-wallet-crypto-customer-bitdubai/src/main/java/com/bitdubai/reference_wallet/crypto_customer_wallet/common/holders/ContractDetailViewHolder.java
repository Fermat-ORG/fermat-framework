package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
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
public class ContractDetailViewHolder extends FermatViewHolder {

    //Constants
    private static final int PAYMENT_SENDING_IN_PROCESS = 1;
    private static final int MERCHANDISE_RECEPTION_IN_PROCESS = 2;
    private static final int PAYMENT_SENT = 1;
    private static final int MERCHANDISE_ACCEPTED = 4;

    //Managers
    private ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> walletSession;

    //Data
    private ContractDetail contractDetail;
    private ContractDetailActivityFragment fragment;
    private int inProcessStatus = 0;


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
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.ccw_contract_detail_confirm_button)
                    executeContractAction();
            }
        });
    }

    protected void executeContractAction() {
        try {
            switch (contractDetail.getContractStep()) {
                case PAYMENT_SENT:
                    //Send the payment to the broker
                    walletSession.getModuleManager().sendPayment(contractDetail.getContractId());

                    Toast.makeText(this.parentFragment.getActivity(), R.string.ccw_the_payment_has_been_delivered, Toast.LENGTH_SHORT).show();

                    //Set internal status of this contract to PAYMENT_SENDING_IN_PROCESS
                    walletSession.setData(contractDetail.getContractId(), PAYMENT_SENDING_IN_PROCESS);

                    fragment.goToWalletHome();
                    break;

                case MERCHANDISE_ACCEPTED:
                    //Confirm the reception of the broker's merchandise
                    walletSession.getModuleManager().ackMerchandise(contractDetail.getContractId());

                    Toast.makeText(this.parentFragment.getActivity(), R.string.ccw_the_merchandise_has_been_accepted, Toast.LENGTH_SHORT).show();

                    //Set internal status of this contract to MERCHANDISE_RECEPTION_IN_PROCESS
                    walletSession.setData(contractDetail.getContractId(), MERCHANDISE_RECEPTION_IN_PROCESS);

                    fragment.goToWalletHome();
                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(this.parentFragment.getActivity(), R.string.ccw_please_try_again_an_error_ocurred, Toast.LENGTH_SHORT).show();

            walletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

    }

    /**
     * Bind the given information to this view holder
     *
     * @param itemInfo      the information to bind
     * @param walletSession the app session to execute actions in the module or to use the Error Manager
     */
    @SuppressLint("SetTextI18n")
    @SuppressWarnings("deprecation")
    public void bind(ContractDetail itemInfo, ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> walletSession) {
        this.walletSession = walletSession;
        this.contractDetail = itemInfo;

        //Get "in_process" internal status of contract. This is done because contract status changes do not get processed immediately.
        Object aux = walletSession.getData(contractDetail.getContractId());
        if (aux != null && aux instanceof Number) inProcessStatus = (int) aux;

        final String amount = itemInfo.getPaymentOrMerchandiseAmount();
        final String currencyCode = itemInfo.getPaymentOrMerchandiseCurrencyCode();
        final String typeOfPayment = itemInfo.getPaymentOrMerchandiseTypeOfPayment();
        amountAndMethodTextView.setText(res.getString(R.string.ccw_amount_and_method_text, amount, currencyCode, typeOfPayment));

        final String formattedDate = getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate());
        final String onFormattedDateText = res.getString(R.string.ccw_on_formatted_date_text, formattedDate);
        final int contractStep = itemInfo.getContractStep();

        switch (contractStep) {
            case 1:
                bindStepSendPayment(itemInfo, onFormattedDateText);
                break;
            case 2:
                bindStepPaymentConfirmed(itemInfo, onFormattedDateText);
                break;

            case 3:
                bindStepMerchandiseDelivered(itemInfo, onFormattedDateText);
                break;

            case 4:
                bindStepMerchandiseConfirmed(itemInfo, onFormattedDateText);
                break;
        }
    }

    /**
     * Set the Parent Fragment of Recycler View containing this View Holder
     *
     * @param parentFragment the parent fragment reference
     */
    public void setParentFragment(ContractDetailActivityFragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    /**
     * Bind the data for the step Send Payment
     *
     * @param itemInfo        the item information to bind
     * @param onFormattedDate the formatted date when the step was completed
     */
    @SuppressWarnings("deprecation")
    private void bindStepSendPayment(ContractDetail itemInfo, String onFormattedDate) {
        final String contractId = contractDetail.getContractId();

        stepNumber.setImageResource(R.drawable.bg_detail_number_01);
        stepTitle.setText(R.string.ccw_payment_delivery);

        if (!stockInWallet(contractId)) {
            switch (itemInfo.getContractStatus()) {
                case PENDING_PAYMENT:

                    //Check internal "in_process" status (If broker clicked Confirm button already but the status has not yet changed)
                    if (inProcessStatus == PAYMENT_SENDING_IN_PROCESS) {
                        textDescription.setText(R.string.ccw_you_sent);
                        textDescriptionDate.setVisibility(View.INVISIBLE);

                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_changed));
                        stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_changed));
                        textDescription.setTextColor(res.getColor(R.color.description_text_status_changed));
                        amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_changed));

                        confirmButton.setVisibility(View.VISIBLE);
                        confirmButton.setText(R.string.ccw_status_confirmed);
                        confirmButton.setEnabled(false);

                    } else {
                        textDescription.setText(R.string.ccw_send);
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        confirmButton.setVisibility(View.VISIBLE);
                    }
                    break;

                default:
                    textDescription.setText(R.string.ccw_you_sent);
                    textDescriptionDate.setText(onFormattedDate);
                    cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                    stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_accepted));
                    textDescription.setTextColor(res.getColor(R.color.description_text_status_accepted));
                    textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_accepted));
                    amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_accepted));
                    break;
            }

        } else {
            textDescription.setText(R.string.ccw_you_send);
            textDescriptionPending.setVisibility(View.VISIBLE);
            textDescriptionPending.setText(R.string.ccw_not_had_enough_stock_in_the_wallet);
            cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
        }
    }

    /**
     * Bind the data for the step Payment Confirmed
     *
     * @param itemInfo        the item information to bind
     * @param onFormattedDate the formatted date when the step was completed
     */
    @SuppressWarnings("deprecation")
    private void bindStepPaymentConfirmed(ContractDetail itemInfo, String onFormattedDate) {
        final ContractStatus contractStatus = itemInfo.getContractStatus();

        stepNumber.setImageResource(R.drawable.bg_detail_number_02);
        stepTitle.setText(R.string.ccw_payment_reception);

        switch (contractStatus) {
            case PENDING_PAYMENT:
                textDescription.setText(R.string.ccw_broker_receives);
                textDescriptionDate.setVisibility(View.INVISIBLE);
                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                break;

            case PAYMENT_SUBMIT:
                textDescription.setText(R.string.ccw_broker_receives);
                textDescriptionDate.setVisibility(View.INVISIBLE);
                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                textDescriptionPending.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.GONE);
                break;

            default:
                textDescription.setText(R.string.ccw_broker_received);
                textDescriptionDate.setText(onFormattedDate);
                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_accepted));
                textDescription.setTextColor(res.getColor(R.color.description_text_status_accepted));
                textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_accepted));
                amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_accepted));
                break;
        }
    }

    /**
     * Bind the data for the step Merchandise Delivered
     *
     * @param itemInfo        the item information to bind
     * @param onFormattedDate the formatted date when the step was completed
     */
    @SuppressWarnings("deprecation")
    private void bindStepMerchandiseDelivered(ContractDetail itemInfo, String onFormattedDate) {
        final ContractStatus contractStatus = itemInfo.getContractStatus();

        stepNumber.setImageResource(R.drawable.bg_detail_number_03);
        stepTitle.setText(R.string.ccw_merchandise_delivery);

        switch (contractStatus) {
            case PAYMENT_SUBMIT:
                textDescription.setText(R.string.ccw_broker_receives);
                textDescriptionDate.setVisibility(View.INVISIBLE);
                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                break;

            case PENDING_MERCHANDISE:
                textDescription.setText(R.string.ccw_broker_sends);
                textDescriptionDate.setVisibility(View.INVISIBLE);
                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                textDescriptionPending.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.GONE);
                break;

            default:
                textDescription.setText(R.string.ccw_broker_sent);
                textDescriptionDate.setText(onFormattedDate);
                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_accepted));
                textDescription.setTextColor(res.getColor(R.color.description_text_status_accepted));
                textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_accepted));
                amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_accepted));
                break;
        }
    }

    /**
     * Bind the data for the step Merchandise Confirmed
     *
     * @param itemInfo        the item information to bind
     * @param onFormattedDate the formatted date when the step was completed
     */
    @SuppressWarnings("deprecation")
    private void bindStepMerchandiseConfirmed(ContractDetail itemInfo, String onFormattedDate) {
        final ContractStatus contractStatus = itemInfo.getContractStatus();

        stepNumber.setImageResource(R.drawable.bg_detail_number_04);
        stepTitle.setText(R.string.ccw_merchandise_reception);

        switch (contractStatus) {
            case PENDING_MERCHANDISE:
                textDescription.setText(R.string.ccw_you_receive);
                textDescriptionDate.setVisibility(View.INVISIBLE);
                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                break;

            case MERCHANDISE_SUBMIT:
                //Check internal "in_process" status (If broker clicked Confirm button already but the status has not yet changed)
                if (inProcessStatus == MERCHANDISE_RECEPTION_IN_PROCESS) {
                    textDescription.setText(R.string.ccw_you_received);
                    textDescriptionDate.setVisibility(View.INVISIBLE);
                    confirmButton.setVisibility(View.VISIBLE);
                    confirmButton.setText(R.string.ccw_status_confirmed);
                    confirmButton.setEnabled(false);

                    cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_changed));
                    stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_changed));
                    textDescription.setTextColor(res.getColor(R.color.description_text_status_changed));
                    amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_changed));

                } else {
                    textDescription.setText(R.string.ccw_you_receive);
                    textDescriptionDate.setVisibility(View.INVISIBLE);
                    cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));

                    if (itemInfo.getPaymentMethodType() != MoneyType.CRYPTO) {
                        confirmButton.setText(R.string.status_confirm);
                        confirmButton.setVisibility(View.VISIBLE);
                    }
                }
                break;

            default:
                textDescription.setText(R.string.ccw_you_received);
                textDescriptionDate.setText(onFormattedDate);
                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                stepTitle.setTextColor(res.getColor(R.color.card_title_color_status_accepted));
                textDescription.setTextColor(res.getColor(R.color.description_text_status_accepted));
                textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_accepted));
                amountAndMethodTextView.setTextColor(res.getColor(R.color.description_text_status_accepted));
                break;
        }
    }

    /**
     * Return a string with the formatted date
     *
     * @param timestamp the date in millis
     *
     * @return the formatted date
     */
    @NonNull
    private String getFormattedDate(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
        return dateFormat.format(new Date(timestamp));
    }

    /**
     * This method validate if has enough stock for send a merchandise according the contract elements.
     *
     * @param contractId the contract ID
     */
    private boolean stockInWallet(String contractId) {
        try {
            return walletSession.getModuleManager().stockInTheWallet(contractId);

        } catch (CantSubmitMerchandiseException ex) {
            walletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        return false;
    }
}
