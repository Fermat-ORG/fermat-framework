package com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletModuleTransaction;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.holders.TransactionViewHolder;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.WalletUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class ReceivetransactionsAdapter
        extends FermatAdapter<FermatWalletModuleTransaction, TransactionViewHolder> {

    private LayoutInflater mInflater;

    private Resources res;

    /**
     * Public primary constructor.
     *  @param context        the activity context where the RecyclerView is going to be displayed
     * @param dataSet the list of FermatWalletModuleTransaction to be displayed in the RecyclerView
     */
    public ReceivetransactionsAdapter(Context context, List<FermatWalletModuleTransaction> dataSet, Resources res) {
        super(context,dataSet);
        mInflater = LayoutInflater.from(context);
        this.res = res;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.fermat_wallet_transaction_item_row;
    }

    @Override
    protected TransactionViewHolder createHolder(View itemView, int type) {
        return new TransactionViewHolder(itemView);
    }

    @Override
    protected void bindHolder(TransactionViewHolder holder, FermatWalletModuleTransaction data, int position) {
        final int MAX_DECIMAL_FOR_TRANSACTION_BALANCE = 4;
        final int MIN_DECIMAL_FOR_TRANSACTION_BALANCE = 2;

        //Set amount Transaction
        holder.getTxt_amount().setText(
                WalletUtils.formatBalanceStringWithDecimalEntry(
                        data.getAmount(),
                        MAX_DECIMAL_FOR_TRANSACTION_BALANCE,
                        MIN_DECIMAL_FOR_TRANSACTION_BALANCE,
                        ShowMoneyType.BITCOIN.getCode()));

        //Set format and Date Transaction
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a ", Locale.US);
        holder.getTxt_date().setText(sdf.format(data.getTimestamp()));

        //Set user transaction
        if (data.getTransactionType()== TransactionType.CREDIT)
            holder.getTxt_from_or_to_transaction().setText("From");
        else holder.getTxt_from_or_to_transaction().setText("To");

        //Validate Involved Actor for contact name
        String contactName = "";
        if (data.getInvolvedActor() != null)
            contactName = data.getInvolvedActor().getName();
        else
        if (data.getTransactionType() == TransactionType.CREDIT)
            if (data.getActorFromType()== Actors.BITCOIN_BASIC_USER)
                contactName = "Bitcoin Wallet";
            else
                contactName = "Unknown";
        else
            contactName = "Unknown";

        holder.getTxt_user().setText(contactName);

        if (data.getMemo()!=null)
            holder.getTxt_memo().setText(data.getMemo());
        else holder.getTxt_memo().setText("Not Notes");


    }
}