package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetActorTransactionHistoryException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.ActorTransactionSummary;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.TransactionHistoryItemViewHolder;
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
public class TransactionHistoryAdapter extends FermatAdapter<CryptoWalletTransaction, TransactionHistoryItemViewHolder> {


    CryptoWallet cryptoWallet;
    ReferenceWalletSession referenceWalletSession;

    protected TransactionHistoryAdapter(Context context) {
        super(context);
    }

    public TransactionHistoryAdapter(Context context, List<CryptoWalletTransaction> dataSet, CryptoWallet cryptoWallet, ReferenceWalletSession referenceWalletSession) {
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
    protected TransactionHistoryItemViewHolder createHolder(View itemView, int type) {
        return new TransactionHistoryItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.transaction_history_item_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(TransactionHistoryItemViewHolder holder, CryptoWalletTransaction data, int position) {

        try
        {
            //holder.getContactIcon().setImageResource(R.drawable.mati_profile);

            holder.getTxt_amount().setText(formatBalanceString(data.getAmount(), referenceWalletSession.getTypeAmount()));

            holder.getTxt_contactName().setText(data.getInvolvedActor().getName());//data.getContact().getActorName());

            holder.getTxt_notes().setText(data.getMemo());

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            holder.getTxt_time().setText(sdf.format(data.getTimestamp()));


            ActorTransactionSummary actorTransactionSummary = null;

            try{
                actorTransactionSummary = cryptoWallet.getActorTransactionHistory(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), referenceWalletSession.getWalletSessionType().getWalletPublicKey(), data.getInvolvedActor().getActorPublicKey());

            } catch (CantGetActorTransactionHistoryException e) {
                e.printStackTrace();
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context,"Error en Adapter bindHolder",Toast.LENGTH_LONG).show();
        }

    }
}
