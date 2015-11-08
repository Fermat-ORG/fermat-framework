package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.PaymentHistoryItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 */
public class PaymentRequestHistoryAdapter  extends FermatAdapter<PaymentRequest, PaymentHistoryItemViewHolder> {

    private View.OnClickListener mOnClickListener;
    CryptoWallet cryptoWallet;
    ReferenceWalletSession referenceWalletSession;
    Typeface tf;
    protected PaymentRequestHistoryAdapter(Context context) {
        super(context);
    }

    public PaymentRequestHistoryAdapter(Context context, List<PaymentRequest> dataSet, CryptoWallet cryptoWallet, ReferenceWalletSession referenceWalletSession,View.OnClickListener onClickListener) {
        super(context, dataSet);
        this.cryptoWallet = cryptoWallet;
        this.referenceWalletSession =referenceWalletSession;
        this.mOnClickListener = onClickListener;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
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
    protected void bindHolder(PaymentHistoryItemViewHolder holder, PaymentRequest data, int position) {

        try {
            holder.getContactIcon().setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), data.getContact().getProfilePicture()));
        }catch (Exception e){
            holder.getContactIcon().setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), R.drawable.celine_profile_picture));
        }

        holder.getTxt_amount().setText(formatBalanceString(data.getAmount(), referenceWalletSession.getTypeAmount()));
        holder.getTxt_amount().setTypeface(tf);

        holder.getTxt_contactName().setText("Mati");//data.getContact().getActorName());
        holder.getTxt_contactName().setTypeface(tf);

        holder.getTxt_notes().setText(data.getReason());
        holder.getTxt_notes().setTypeface(tf);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.getTxt_time().setText(data.getDate());
        holder.getTxt_time().setTypeface(tf);

        //TOOD: sacado para mostrar
//        if(data.getState() != null) {
//            holder.getLinear_layour_container_buttons().setVisibility(View.GONE);
//            holder.getLinear_layour_container_state().setVisibility(View.VISIBLE);
//            holder.getTxt_state().setText(data.getState());
//            holder.getTxt_state().setTypeface(tf);
//        }else{
//            holder.getLinear_layour_container_state().setVisibility(View.GONE);
//            holder.getLinear_layour_container_buttons().setVisibility(View.VISIBLE);
//        }

        holder.getBtn_accept_request().setOnClickListener(mOnClickListener);
        holder.getBtn_refuse_request().setOnClickListener(mOnClickListener);

    }


}
