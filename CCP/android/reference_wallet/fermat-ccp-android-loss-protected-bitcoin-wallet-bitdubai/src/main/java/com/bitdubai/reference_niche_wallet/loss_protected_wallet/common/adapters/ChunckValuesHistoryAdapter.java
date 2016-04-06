package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.ChunckValuesHistoryItemViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.PaymentHistoryItemViewHolder;
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
public class ChunckValuesHistoryAdapter extends FermatAdapter<LossProtectedWalletTransaction, ChunckValuesHistoryItemViewHolder>  {

    private onRefreshList onRefreshList;
    // private View.OnClickListener mOnClickListener;
    LossProtectedWallet cryptoWallet;
    LossProtectedWalletSession referenceWalletSession;
    Typeface tf;
    protected ChunckValuesHistoryAdapter(Context context) {
        super(context);
    }

    public ChunckValuesHistoryAdapter(Context context, List<LossProtectedWalletTransaction> dataSet, LossProtectedWallet cryptoWallet, LossProtectedWalletSession referenceWalletSession, onRefreshList onRefresh) {
        super(context, dataSet);
        this.cryptoWallet = cryptoWallet;
        this.referenceWalletSession =referenceWalletSession;
        //this.mOnClickListener = onClickListener;
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
    protected ChunckValuesHistoryItemViewHolder createHolder(View itemView, int type) {
        return new ChunckValuesHistoryItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.chunck_values_history_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(final ChunckValuesHistoryItemViewHolder holder, final LossProtectedWalletTransaction data, int position) {


        holder.getTxt_amount().setText(formatBalanceString(data.getAmount(), referenceWalletSession.getTypeAmount()));
        holder.getTxt_amount().setTypeface(tf) ;

        if (data.getExchangeRate() <= referenceWalletSession.getActualExchangeRate()) {
            holder.getTxt_amount().setTextColor(Color.parseColor("#00c853"));
        }else {
            holder.getTxt_amount().setTextColor(Color.parseColor("#dd2c00"));
        }

        holder.getTxt_exchange_rate().setText("Exchange Rate: 1 BTC = "+ data.getExchangeRate());

     /*   if(data.getContact() != null)
            holder.getTxt_contactName().setText(data.getContact().getActorName());
        else
            holder.getTxt_contactName().setText("Unknown");

        holder.getTxt_contactName().setTypeface(tf);

        holder.getTxt_notes().setText(data.getReason());
        holder.getTxt_notes().setTypeface(tf);

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.US);
        holder.getTxt_time().setText(sdf.format(data.getDate()) + " hs");
        holder.getTxt_time().setTypeface(tf);
*/
        /*String state = "";
        switch (data.getTransactionState()){
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

        }*/


        /*if(data.() == 0) //SEND
        {
            holder.getLinear_layour_container_buttons().setVisibility(View.GONE);
            holder.getLinear_layour_container_state().setVisibility(View.VISIBLE);
            holder.getTxt_state().setText(state);
            holder.getTxt_state().setTypeface(tf);
        }
        else
        {
            if(data.getTransactionState().equals(CryptoPaymentState.APPROVED) || data.getTransactionState().equals(CryptoPaymentState.REFUSED) || data.getTransactionState().equals(CryptoPaymentState.ERROR)) {
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
*/


      /*      holder.getBtn_accept_request().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        cryptoWallet.approveRequest(data.getRequestId()
                                , referenceWalletSession.getIntraUserModuleManager().getPublicKey());
                        Toast.makeText(context, "Request accepted", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
//                        FermatAnimationsUtils.showEmpty(context, true, holder.getLinear_layour_container_state());
//                        FermatAnimationsUtils.showEmpty(context, false, holder.getLinear_layour_container_buttons());
                        onRefreshList.onRefresh();
                    } catch (Exception e) {
                        showMessage(context, "Cant Accept or Denied Receive Payment Exception- " + e.getMessage());
                    }

                }
            });

        holder.getBtn_refuse_request().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cryptoWallet.refuseRequest(data.getRequestId());
                    Toast.makeText(context, "Request denied", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
//                    FermatAnimationsUtils.showEmpty(context, true, holder.getLinear_layour_container_state());
//                    FermatAnimationsUtils.showEmpty(context, false, holder.getLinear_layour_container_buttons());
                    onRefreshList.onRefresh();
                } catch (Exception e) {
                    showMessage(context, "Cant Accept or Denied Receive Payment Exception- " + e.getMessage());
                }
            }
        });*/
    }



}
