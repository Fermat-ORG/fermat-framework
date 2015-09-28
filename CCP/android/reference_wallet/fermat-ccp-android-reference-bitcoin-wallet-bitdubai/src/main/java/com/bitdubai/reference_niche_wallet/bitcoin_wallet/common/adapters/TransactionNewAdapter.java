package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterNew;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.PaymentRequestItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.TransactionItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del MainFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Matias Furszyfer
 */
public class TransactionNewAdapter extends FermatAdapter<CryptoWalletTransaction, TransactionItemViewHolder> {


    protected TransactionNewAdapter(Context context) {
        super(context);
    }

    public TransactionNewAdapter(Context context, List<CryptoWalletTransaction> dataSet) {
        super(context, dataSet);
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
    protected TransactionItemViewHolder createHolder(View itemView, int type) {
        return new TransactionItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.receive_list_item_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(TransactionItemViewHolder holder, CryptoWalletTransaction data, int position) {

        holder.getContactIcon().setImageResource(R.drawable.mati_profile);

        holder.getTxt_amount().setText(formatBalanceString(data.getBitcoinWalletTransaction().getAmount(), ReferenceWalletSession.typeAmountSelected));

        holder.getTxt_contactName().setText(data.getInvolvedActor().getName());//data.getContact().getActorName());

        holder.getTxt_notes().setText(data.getBitcoinWalletTransaction().getMemo());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.getTxt_time().setText(sdf.format(data.getBitcoinWalletTransaction().getTimestamp()));

    }
}
