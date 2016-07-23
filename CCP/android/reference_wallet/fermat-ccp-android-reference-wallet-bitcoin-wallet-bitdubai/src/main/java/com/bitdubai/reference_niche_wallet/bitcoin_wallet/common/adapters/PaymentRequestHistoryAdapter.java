package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.PaymentHistoryItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;


import java.util.List;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 */
public class PaymentRequestHistoryAdapter  extends FermatAdapter<PaymentRequest, PaymentHistoryItemViewHolder>  {

    private onRefreshList onRefreshList;
    // private View.OnClickListener mOnClickListener;
    CryptoWallet cryptoWallet;
    ReferenceAppFermatSession referenceWalletSession;
    Typeface tf;
    private BitcoinWalletSettings bitcoinWalletSettings = null;
    private String feeLevel = "NORMAL";
    BlockchainNetworkType blockchainNetworkType;

    protected PaymentRequestHistoryAdapter(Context context) {
        super(context);
    }

    public PaymentRequestHistoryAdapter(Context context, List<PaymentRequest> dataSet, CryptoWallet cryptoWallet, ReferenceAppFermatSession<CryptoWallet> referenceWalletSession,onRefreshList onRefresh) {
        super(context, dataSet);
        this.cryptoWallet = cryptoWallet;
        this.referenceWalletSession =referenceWalletSession;
        //this.mOnClickListener = onClickListener;
        this.onRefreshList = onRefresh;


        try {

            bitcoinWalletSettings = cryptoWallet.loadAndGetSettings(this.referenceWalletSession.getAppPublicKey());

            if (bitcoinWalletSettings.getFeedLevel() == null)
                bitcoinWalletSettings.setFeedLevel(BitcoinFee.NORMAL.toString());
            else
                feeLevel = bitcoinWalletSettings.getFeedLevel();

            if (bitcoinWalletSettings.getBlockchainNetworkType() == null) {
                bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
            }
            else
                blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();

            this.cryptoWallet.persistSettings(referenceWalletSession.getAppPublicKey(), bitcoinWalletSettings);


        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


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
        return R.layout.history_request_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(final PaymentHistoryItemViewHolder holder, final PaymentRequest data, int position) {

        try {
            holder.getContactIcon().setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), data.getContact().getProfilePicture()));
        }catch (Exception e){
            holder.getContactIcon().setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), R.drawable.ic_profile_male));
        }

        holder.getTxt_amount().setText(formatBalanceString(data.getAmount(), ((ShowMoneyType) referenceWalletSession.getData(SessionConstant.TYPE_AMOUNT_SELECTED)).getCode()));
        holder.getTxt_amount().setTypeface(tf) ;

        if(data.getContact() != null)
            holder.getTxt_contactName().setText(data.getContact().getActorName());
        else
            holder.getTxt_contactName().setText("Unknown");

        holder.getTxt_contactName().setTypeface(tf);

        holder.getTxt_notes().setText(data.getReason());
        holder.getTxt_notes().setTypeface(tf);


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
            if(data.getState().equals(CryptoPaymentState.APPROVED) || data.getState().equals(CryptoPaymentState.REFUSED)) {
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

                        //check amount + fee less than balance
                        long availableBalance = cryptoWallet.getBalance(com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType.AVAILABLE, referenceWalletSession.getAppPublicKey(), blockchainNetworkType);
                        if(data.getAmount() < availableBalance)
                        {
                            cryptoWallet.approveRequest(data.getRequestId()
                                    , cryptoWallet.getSelectedActorIdentity().getPublicKey(),
                                    BitcoinFee.valueOf(feeLevel).getFee(), FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS);
                            Toast.makeText(context, "Request accepted", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                        else
                            showMessage(context, "Insufficient funds - Can't Accept Receive Payment" );

//                        FermatAnimationsUtils.showEmpty(context, true, holder.getLinear_layour_container_state());
//                        FermatAnimationsUtils.showEmpty(context, false, holder.getLinear_layour_container_buttons());
                        onRefreshList.onRefresh();
                    } catch (Exception e) {
                        showMessage(context, "Cant Accept Receive Payment Exception- " + e.getMessage());
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
                    showMessage(context, "Cant Denied Receive Payment Exception- " + e.getMessage());
                }
            }
        });
    }



}
