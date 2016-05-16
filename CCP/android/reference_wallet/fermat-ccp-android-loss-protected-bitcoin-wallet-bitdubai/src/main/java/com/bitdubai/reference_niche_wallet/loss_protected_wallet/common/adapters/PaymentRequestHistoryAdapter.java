package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.PaymentHistoryItemViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.Confirm_Send_Payment_Dialog;

import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatBalanceString;
import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 */
public class PaymentRequestHistoryAdapter  extends FermatAdapter<LossProtectedPaymentRequest, PaymentHistoryItemViewHolder>  {

    private onRefreshList onRefreshList;

    LossProtectedWallet lossProtectedWallet;
    LossProtectedWalletSession appSession;
    Typeface tf;

    boolean lossProtectedEnabled;
    BlockchainNetworkType blockchainNetworkType;
    SettingsManager<LossProtectedWalletSettings> settingsManager;


    protected PaymentRequestHistoryAdapter(Context context) {
        super(context);
    }

    public PaymentRequestHistoryAdapter(Context context, List<LossProtectedPaymentRequest> dataSet, LossProtectedWallet cryptoWallet, LossProtectedWalletSession referenceWalletSession,onRefreshList onRefresh) {
        super(context, dataSet);
        this.lossProtectedWallet = cryptoWallet;
        this.appSession =referenceWalletSession;
        this.onRefreshList = onRefresh;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    public void setOnClickListerAcceptButton(View.OnClickListener onClickListener){


    }

    public void setOnClickListerRefuseButton(View.OnClickListener onClickListener){

    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    @Override
    protected PaymentHistoryItemViewHolder createHolder(View itemView, int type) {
        return new PaymentHistoryItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.loss_history_request_row;
    }
    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(final PaymentHistoryItemViewHolder holder, final LossProtectedPaymentRequest data, int position) {

        try {
            holder.getContactIcon().setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), data.getContact().getProfilePicture()));
        }catch (Exception e){
            holder.getContactIcon().setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), R.drawable.ic_profile_male));
        }

        holder.getTxt_amount().setText(formatBalanceString(data.getAmount(), ShowMoneyType.BITCOIN.getCode())+" BTC");
        holder.getTxt_amount().setTypeface(tf) ;

        if(data.getContact() != null)
            holder.getTxt_contactName().setText(data.getContact().getActorName());
        else
            holder.getTxt_contactName().setText("Unknown");

        holder.getTxt_contactName().setTypeface(tf);

        holder.getTxt_notes().setText(data.getReason());
        holder.getTxt_notes().setTypeface(tf);

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.US);
        holder.getTxt_time().setText(data.getDate() + " hs");
        holder.getTxt_time().setTypeface(tf);

        String state = "";
        switch (data.getState()){
            case WAITING_RECEPTION_CONFIRMATION:
                state = "Waiting for response";
                break;
            case APPROVED:
                state = "Accepted";
                break;
            case PAID:
                state = "Paid";
                break;
            case PENDING_RESPONSE:
                state = "Pending response";
                break;
            case ERROR:
                state = "Error";
                break;
            case NOT_SENT_YET:
                state = "Not sent yet";
                break;
            case PAYMENT_PROCESS_STARTED:
                state = "Payment process started";
                break;
            case DENIED_BY_INCOMPATIBILITY:
                state = "Denied by incompatibility";
                break;
            case IN_APPROVING_PROCESS:
                state = "In approving process";
                break;
            case REFUSED:
                state = "Denied";
                break;
            default:
                state = "Error, contact with support";
                break;

        }


        if(data.getType() == 0) //SEND
        {
            holder.getLinear_layour_container_buttons().setVisibility(View.GONE);
            holder.getLinear_layour_container_state().setVisibility(View.VISIBLE);
            holder.getTxt_state().setText(state);
            holder.getTxt_state().setTypeface(tf);
        }
        else
        {
            if(data.getState().equals(CryptoPaymentState.APPROVED) || data.getState().equals(CryptoPaymentState.REFUSED) || data.getState().equals(CryptoPaymentState.ERROR)) {
                holder.getLinear_layour_container_buttons().setVisibility(View.GONE);
                holder.getLinear_layour_container_state().setVisibility(View.VISIBLE);

                holder.getTxt_state().setText(state);
                holder.getTxt_state().setTypeface(tf);
            }
            else
            {
                holder.getLinear_layour_container_buttons().setVisibility(View.VISIBLE);
                holder.getLinear_layour_container_state().setVisibility(View.GONE);

                holder.getTxt_state().setText(state);
                holder.getTxt_state().setTypeface(tf);
            }
        }



            holder.getBtn_accept_request().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        //verify loss protected settings
                        if(appSession.getActualExchangeRate() != 0){
                            settingsManager = appSession.getModuleManager().getSettingsManager();
                            LossProtectedWalletSettings bitcoinWalletSettings = null;
                            bitcoinWalletSettings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());

                            blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();

                            lossProtectedWallet = appSession.getModuleManager();

                            lossProtectedEnabled = bitcoinWalletSettings.getLossProtectedEnabled();

                            long availableBalance = lossProtectedWallet.getBalance(BalanceType.AVAILABLE, appSession.getAppPublicKey(), blockchainNetworkType, String.valueOf(appSession.getActualExchangeRate()));


                            if( data.getAmount() > availableBalance) //the amount is greater than the available
                            {
                                //check loss protected settings
                                if (!lossProtectedEnabled) {
                                    //show dialog confirm
                                    Confirm_Send_Payment_Dialog confirm_send_dialog = new Confirm_Send_Payment_Dialog(context,
                                            data.getAmount(),
                                            data.getRequestId(), appSession,blockchainNetworkType,lossProtectedWallet);
                                    confirm_send_dialog.show();
                                }
                                else
                                {
                                    Toast.makeText(context, "Action not allowed, You do not have enough funds", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                //aprove payment request
                                lossProtectedWallet.approveRequest(data.getRequestId()
                                        , appSession.getIntraUserModuleManager().getPublicKey());
                                Toast.makeText(context, "Request accepted", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                onRefreshList.onRefresh();
                            }
                        }
                        else
                        {
                            Toast.makeText(context, "Action not allowed.Could not retrieve the dollar exchange rate.\nCheck your internet connection.. ", Toast.LENGTH_LONG).show();

                        }



                    } catch (Exception e) {
                        showMessage(context, "Cant Accept or Denied Receive Payment Exception- " + e.getMessage());
                    }

                }
            });

        holder.getBtn_refuse_request().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    lossProtectedWallet.refuseRequest(data.getRequestId());
                    Toast.makeText(context, "Request denied", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    onRefreshList.onRefresh();
                } catch (Exception e) {
                    showMessage(context, "Cant Accept or Denied Receive Payment Exception- " + e.getMessage());
                }
            }
        });
    }



}
