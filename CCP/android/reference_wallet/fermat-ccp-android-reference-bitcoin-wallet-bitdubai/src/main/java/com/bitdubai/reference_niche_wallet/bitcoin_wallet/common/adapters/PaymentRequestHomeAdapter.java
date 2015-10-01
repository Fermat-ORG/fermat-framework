package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.PaymentHistoryItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.PaymentHomeItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 */
public class PaymentRequestHomeAdapter extends FermatAdapter<PaymentRequest, PaymentHomeItemViewHolder> implements View.OnClickListener {

    CryptoWallet cryptoWallet;
    ReferenceWalletSession referenceWalletSession;

    protected PaymentRequestHomeAdapter(Context context) {
        super(context);
    }

    public PaymentRequestHomeAdapter(Context context, List<PaymentRequest> dataSet, CryptoWallet cryptoWallet, ReferenceWalletSession referenceWalletSession) {
        super(context, dataSet);
        this.cryptoWallet = cryptoWallet;
        this.referenceWalletSession =referenceWalletSession;
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
    protected PaymentHomeItemViewHolder createHolder(View itemView, int type) {
        return new PaymentHomeItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.home_request_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(PaymentHomeItemViewHolder holder, PaymentRequest data, int position) {

        holder.getContactIcon().setImageResource(R.drawable.mati_profile);



        holder.getTxt_amount().setText(formatBalanceString(data.getAmount(), referenceWalletSession.getTypeAmount()));

        holder.getTxt_contactName().setText(data.getContact().getActorName());//data.getContact().getActorName());

        holder.getTxt_notes().setText(data.getReason());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.getTxt_time().setText(sdf.format(data.getDate()));

        holder.getBtn_accept_request().setOnClickListener(this);

        holder.getBtn_refuse_request().setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_accept_request){
            Toast.makeText(context,"aceptado request",Toast.LENGTH_SHORT).show();
        }else if (view.getId() == R.id.btn_refuse_request){
            Toast.makeText(context,"denegado request",Toast.LENGTH_SHORT).show();
        }

    }
}
