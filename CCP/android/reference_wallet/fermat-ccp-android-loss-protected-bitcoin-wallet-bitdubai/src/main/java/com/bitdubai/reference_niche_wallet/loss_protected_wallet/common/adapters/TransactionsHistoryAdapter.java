package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.TransactionListItemViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType.CREDIT;

/**
 * Created by Joaquin Carrasquero on 05/04/16.
 */
public class TransactionsHistoryAdapter extends FermatAdapter<LossProtectedWalletTransaction, TransactionListItemViewHolder> {

    private com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList onRefreshList;
    LossProtectedWallet cryptoWallet;
    ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession;
    Typeface tf;

    public TransactionsHistoryAdapter(Context context, List<LossProtectedWalletTransaction> dataSet, LossProtectedWallet cryptoWallet, ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession,onRefreshList onRefresh) {
        super(context, dataSet);
        this.cryptoWallet = cryptoWallet;
        this.referenceWalletSession =referenceWalletSession;
        this.onRefreshList = onRefresh;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }


    @Override
    protected TransactionListItemViewHolder createHolder(View itemView, int type) {
        return new TransactionListItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.list_item_trasaction_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(TransactionListItemViewHolder holder, LossProtectedWalletTransaction data, int position) {

        final int MAX_DECIMAL_FOR_BALANCE_TRANSACTION = 8;
        final int MIN_DECIMAL_FOR_BALANCE_TRANSACTION = 2;


        //formatter for date transaction
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
        holder.getTransaction_date().setText(context.getResources().getString(R.string.Date_text)+" " + sdf.format(data.getTimestamp()) + ".");

        //Validate Involved Actor for contact name
        String contactName = "";
        if (data.getInvolvedActor() != null)
                contactName = data.getInvolvedActor().getName();
        else
        if (data.getTransactionType() == CREDIT)
            if (data.getActorFromType()== Actors.BITCOIN_BASIC_USER)
                contactName = "Bitcoin Wallet";
            else
                contactName = "Unknown";
        else
            contactName = "Unknown";

        long TransactionAmount = 0;

        //Validate if the transaction is credit or debit
        if (data.getTransactionType() == CREDIT) {
            holder.getTransaction_user().setText(context.getResources().getString(R.string.From_text)+" " + contactName + ".");
            TransactionAmount = data.getAmount();
        }
        else {
            holder.getTransaction_user().setText(context.getResources().getString(R.string.To_text)+" "  + contactName + ".");
            TransactionAmount = data.getTotal();
        }

        //set Amount transaction
        holder.getTransaction_amount().setText(
                WalletUtils.formatBalanceStringWithDecimalEntry(
                        TransactionAmount,
                        MAX_DECIMAL_FOR_BALANCE_TRANSACTION,
                        MIN_DECIMAL_FOR_BALANCE_TRANSACTION,
                        ShowMoneyType.BITCOIN.getCode())+ " BTC");

        //Set transaction note
        holder.getTransaction_note().setText(context.getResources().getString(R.string.Note_text)+" "  + data.getMemo() + ".");

    }
}
