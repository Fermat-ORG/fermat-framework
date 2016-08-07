package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.ContractDetail;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contract_detail.ContractDetailActivityFragment;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/02/16
 */
public class ContractDetailViewHolder extends FermatViewHolder implements View.OnClickListener {

    //Constants
    private static final int PAYMENT_RECEPTION_IN_PROCESS = 1;
    private static final int MERCHANDISE_DELIVERY_IN_PROCESS = 2;
    private static final int PAYMENT_CONFIRMED = 2;
    private static final int MERCHANDISE_SENT = 3;

    //Managers
    ErrorManager errorManager;
    protected CryptoBrokerWalletModuleManager walletManager;
    protected ReferenceAppFermatSession walletSession;

    //Data
    protected ContractDetail contractDetail;
    private ContractDetailActivityFragment fragment;
    int inProcessStatus = 0;
    private NumberFormat numberFormat= DecimalFormat.getInstance();

    //UI
    private Resources res;
    private CardView cardView;
    private ContractDetailActivityFragment parentFragment;
    public ImageView stepNumber;
    public FermatTextView stepTitle;
    public FermatTextView textAction;
    public FermatTextView textAmountAndMethod;
    public FermatTextView textDescriptionDate;
    public FermatTextView textDescriptionPending;
    public FermatButton confirmButton;


    public ContractDetailViewHolder(View itemView, ContractDetailActivityFragment fragment) {
        super(itemView, 0);

        this.cardView = (CardView) itemView.findViewById(R.id.contract_detail_card_view);
        res = itemView.getResources();


        stepNumber = (ImageView) itemView.findViewById(R.id.cbw_contract_detail_step);
        stepTitle = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_card_view_title);
        textAction = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_action_text);
        textAmountAndMethod = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_amount_and_method);
        textDescriptionDate = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_description_date);
        textDescriptionPending = (FermatTextView) itemView.findViewById(R.id.cbw_contract_detail_description_pending);
        confirmButton = (FermatButton) itemView.findViewById(R.id.cbw_contract_detail_confirm_button);
        confirmButton.setOnClickListener(this);
        confirmButton.setVisibility(View.INVISIBLE);
        this.fragment = fragment;


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cbw_contract_detail_confirm_button) {
            executeContractAction();
        }
    }

    protected void executeContractAction() {
        try {

            switch (contractDetail.getContractStep()) {
                case PAYMENT_CONFIRMED:
                    //Confirm the payment from the customer
                    walletManager.ackPayment(contractDetail.getContractId());

                    Toast.makeText(this.parentFragment.getActivity(), R.string.payment_confirmed, Toast.LENGTH_SHORT).show();

                    //Set internal status of this contract to PAYMENT_RECEPTION_IN_PROCESS
                    walletSession.setData(contractDetail.getContractId(), PAYMENT_RECEPTION_IN_PROCESS);

                    fragment.goToWalletHome();
                    break;
                case MERCHANDISE_SENT:
                    //Send the merchandise to the customer
                    walletManager.submitMerchandise(contractDetail.getContractId());

                    Toast.makeText(this.parentFragment.getActivity(), R.string.merchandise_sent, Toast.LENGTH_SHORT).show();

                    //Set internal status of this contract to MERCHANDISE_DELIVERY_IN_PROCESS
                    walletSession.setData(contractDetail.getContractId(), MERCHANDISE_DELIVERY_IN_PROCESS);

                    fragment.goToWalletHome();
                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(this.parentFragment.getActivity(), R.string.error_try, Toast.LENGTH_SHORT).show();

            Log.e(this.parentFragment.getTag(), ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void setTextColorToAccepted() {
        stepTitle.setTextColor(res.getColor(R.color.description_text_status_accepted));
        textAction.setTextColor(res.getColor(R.color.description_text_status_accepted));
        textAmountAndMethod.setTextColor(res.getColor(R.color.description_text_status_accepted));
        textDescriptionDate.setTextColor(res.getColor(R.color.description_text_status_accepted));
    }

    @SuppressWarnings("deprecation")
    public void bind(ContractDetail itemInfo) {

        //Locally save contractDetail
        this.contractDetail = itemInfo;
        StringBuilder stringBuilder = new StringBuilder();
        //Get "in_process" internal status of contract
        // This is done because contract status changes do not get processed immediately.
        Object aux = walletSession.getData(contractDetail.getContractId());
        if (aux != null && aux instanceof Number)
            inProcessStatus = (int) aux;

        switch (itemInfo.getContractStep()) {
            case 1:
                stepNumber.setImageResource(R.drawable.bg_detail_number_01);
                stepTitle.setText(R.string.payment_delivery);
                stringBuilder.append(getFormattedAmount(fixFormat(itemInfo.getPaymentOrMerchandiseAmount()), itemInfo.getPaymentOrMerchandiseCurrencyCode()))
                        .append(", using ")
                        .append(itemInfo.getPaymentOrMerchandiseTypeOfPayment());
                textAmountAndMethod.setText(stringBuilder.toString());
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                        textAction.setText(R.string.customer_sends);
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        textDescriptionPending.setVisibility(View.VISIBLE);
                        confirmButton.setVisibility(View.GONE);
                        break;
                    default:
                        setTextColorToAccepted();
                        textAction.setText(R.string.customer_sent);
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("on ").append(getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                        textDescriptionDate.setText(stringBuilder.toString());
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                }
                break;

            case 2:
                stepNumber.setImageResource(R.drawable.bg_detail_number_02);
                stepTitle.setText(R.string.payment_reception);
                stringBuilder.append(getFormattedAmount(fixFormat(itemInfo.getPaymentOrMerchandiseAmount()), itemInfo.getPaymentOrMerchandiseCurrencyCode()))
                        .append(", using ")
                        .append(itemInfo.getPaymentOrMerchandiseTypeOfPayment());
                textAmountAndMethod.setText(stringBuilder.toString());
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                        textAction.setText(R.string.your_recieve);
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                        break;
                    case PAYMENT_SUBMIT:

                        //Check internal "in_process" status (If broker clicked Confirm button already but the status has not yet changed)
                        if (inProcessStatus == PAYMENT_RECEPTION_IN_PROCESS) {
                            setTextColorToAccepted();
                            textAction.setText(R.string.you_received);
//                            textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                            textDescriptionDate.setVisibility(View.INVISIBLE);
                            confirmButton.setEnabled(false);
                            confirmButton.setVisibility(View.VISIBLE);
                            confirmButton.setText(R.string.confirmed);
                            cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_changed));
                        } else {
                            textAction.setText(R.string.your_recieve);
                            textDescriptionDate.setVisibility(View.INVISIBLE);
                            cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                            if (itemInfo.getPaymentMethodType() != MoneyType.CRYPTO) {
                                confirmButton.setText(R.string.confirm);
                                confirmButton.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    default:
                        setTextColorToAccepted();
                        textAction.setText(R.string.you_received);
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("on ").append(getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                        textDescriptionDate.setText(stringBuilder.toString());
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                }
                break;

            case 3:
                stepNumber.setImageResource(R.drawable.bg_detail_number_03);
                stepTitle.setText(R.string.merchandise_delivery_title);
                stringBuilder.append(getFormattedAmount(fixFormat(itemInfo.getPaymentOrMerchandiseAmount()), itemInfo.getPaymentOrMerchandiseCurrencyCode()))
                        .append(", using ")
                        .append(itemInfo.getPaymentOrMerchandiseTypeOfPayment());
                textAmountAndMethod.setText(stringBuilder.toString());
                if (stockInWallet(contractDetail.getContractId())) {
                    switch (itemInfo.getContractStatus()) {
                        case PENDING_PAYMENT:
                        case PAYMENT_SUBMIT:
                            textAction.setText(R.string.you_send);
                            textDescriptionDate.setVisibility(View.INVISIBLE);
                            cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                            break;
                        case PENDING_MERCHANDISE:
                            //Check internal "in_process" status (If broker clicked Confirm button already but the status has not yet changed)
                            if (inProcessStatus == MERCHANDISE_DELIVERY_IN_PROCESS) {
                                setTextColorToAccepted();
                                textAction.setText(R.string.you_sent);
//                                textDescriptionDate.setText("on " + getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                                textDescriptionDate.setVisibility(View.INVISIBLE);
                                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_changed));
                                confirmButton.setEnabled(false);
                                confirmButton.setText(R.string.confirmed);
                                confirmButton.setVisibility(View.VISIBLE);
                            } else {
                                textAction.setText(R.string.you_send);
                                textDescriptionDate.setVisibility(View.INVISIBLE);
                                cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                                confirmButton.setText(R.string.confirm);
                                confirmButton.setVisibility(View.VISIBLE);
                            }

                            break;
                        default:
                            setTextColorToAccepted();
                            textAction.setText(R.string.you_sent);
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("on ").append(getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                            textDescriptionDate.setText(stringBuilder.toString());
                            cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));
                    }
                } else {
                    textAction.setText(R.string.you_send);
                    textDescriptionPending.setVisibility(View.VISIBLE);
                    textDescriptionPending.setText(R.string.no_enough_stock);
                    cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                }
                break;
            case 4:
                stepNumber.setImageResource(R.drawable.bg_detail_number_04);
                stepTitle.setText(R.string.merchandise_reception);
                stringBuilder.append(getFormattedAmount(fixFormat(itemInfo.getPaymentOrMerchandiseAmount()), itemInfo.getPaymentOrMerchandiseCurrencyCode()))
                        .append(", using ")
                        .append(itemInfo.getPaymentOrMerchandiseTypeOfPayment());
                textAmountAndMethod.setText(stringBuilder.toString());
                switch (itemInfo.getContractStatus()) {
                    case PENDING_PAYMENT:
                    case PAYMENT_SUBMIT:
                    case PENDING_MERCHANDISE:
                        textAction.setText(R.string.merchandise_reception);
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_inactive));
                        break;
                    case MERCHANDISE_SUBMIT:
                        textAction.setText(R.string.customer_receives);
                        textDescriptionDate.setVisibility(View.INVISIBLE);
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_confirm));
                        textDescriptionPending.setVisibility(View.VISIBLE);
                        confirmButton.setVisibility(View.GONE);
                        break;
                    default:
                        setTextColorToAccepted();
                        textAction.setText(R.string.customer_received);
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("on ").append(getFormattedDate(itemInfo.getPaymentOrMerchandiseDeliveryDate()));
                        textDescriptionDate.setText(stringBuilder.toString());
                        cardView.setCardBackgroundColor(res.getColor(R.color.card_background_status_accepted));

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

    public void setWalletModuleManager(CryptoBrokerWalletModuleManager walletManager) {
        this.walletManager = walletManager;
    }

    public void setSession(ReferenceAppFermatSession session) {
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yy");
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
            Toast.makeText(this.parentFragment.getActivity(), R.string.error_try, Toast.LENGTH_SHORT).show();

            Log.e(this.parentFragment.getTag(), ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
        return Boolean.FALSE;

    }

    private String fixFormat(String value) {


        if (compareLessThan1(value)) {
            numberFormat.setMaximumFractionDigits(8);
        } else {
            numberFormat.setMaximumFractionDigits(2);
        }
        return numberFormat.format(new BigDecimal(Double.valueOf(value)));


    }

    private Boolean compareLessThan1(String value) {
        Boolean lessThan1 = true;

        if (BigDecimal.valueOf(Double.valueOf(value)).
                compareTo(BigDecimal.ONE) == -1) {
            lessThan1 = true;
        } else {
            lessThan1 = false;
        }
        return lessThan1;
    }


}
