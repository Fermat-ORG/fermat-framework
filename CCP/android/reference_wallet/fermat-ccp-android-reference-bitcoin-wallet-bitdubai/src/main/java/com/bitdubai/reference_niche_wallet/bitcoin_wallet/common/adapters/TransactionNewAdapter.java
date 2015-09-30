package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetActorTransactionHistoryException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.ActorTransactionSummary;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.TransactionItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.text.SimpleDateFormat;
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


    CryptoWallet cryptoWallet;
    ReferenceWalletSession referenceWalletSession;

    protected TransactionNewAdapter(Context context) {
        super(context);
    }

    public TransactionNewAdapter(Context context, List<CryptoWalletTransaction> dataSet,CryptoWallet cryptoWallet,ReferenceWalletSession referenceWalletSession) {
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

        holder.getTxt_amount().setText(formatBalanceString(data.getBitcoinWalletTransaction().getAmount(), referenceWalletSession.getTypeAmount()));

        holder.getTxt_contactName().setText(data.getInvolvedActor().getName());//data.getContact().getActorName());

        holder.getTxt_notes().setText(data.getBitcoinWalletTransaction().getMemo());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.getTxt_time().setText(sdf.format(data.getBitcoinWalletTransaction().getTimestamp()));

        ActorTransactionSummary actorTransactionSummary = null;

        try{
            actorTransactionSummary = cryptoWallet.getActorTransactionHistory(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), referenceWalletSession.getWalletSessionType().getWalletPublicKey(), data.getInvolvedActor().getActorPublicKey());

        } catch (CantGetActorTransactionHistoryException e) {
            e.printStackTrace();
        }

//        holder.getTxt_total_number_transactions().setText(String.valueOf(actorTransactionSummary.getReceivedTransactionsNumber()));
//
//        holder.getTxt_total_balance().setText(formatBalanceString(actorTransactionSummary.getReceivedAmount(), referenceWalletSession.getTypeAmount()));
//
//        holder.getImageView_see_all_transactions().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context,"estoy tocando esto",Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
