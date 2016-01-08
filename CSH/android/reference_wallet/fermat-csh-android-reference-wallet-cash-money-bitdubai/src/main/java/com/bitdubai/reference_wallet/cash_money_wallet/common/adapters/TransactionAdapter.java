package com.bitdubai.reference_wallet.cash_money_wallet.common.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.reference_wallet.cash_money_wallet.common.holders.DepositViewHolder;
import com.bitdubai.reference_wallet.cash_money_wallet.common.holders.WithdrawalViewHolder;

import java.util.List;
import java.util.NoSuchElementException;


public class TransactionAdapter extends RecyclerView.Adapter<FermatViewHolder> {
    private static final int NO_TYPE = Integer.MIN_VALUE;
    private static final int HOLDER_TYPE_DEPOSIT = 0;
    private static final int HOLDER_TYPE_WITHDRAWAL = 1;

    private List<CashMoneyWalletTransaction> transactionsList;
    private Activity activity;

public TransactionAdapter(Activity activity, List<CashMoneyWalletTransaction> transactionsList) {
        this.activity = activity;
        this.transactionsList = transactionsList;
    }

    private FermatViewHolder createHolder(View itemView, int type) {
        if (type == HOLDER_TYPE_DEPOSIT)
            return new DepositViewHolder(itemView);
        if (type == HOLDER_TYPE_WITHDRAWAL)
            return new WithdrawalViewHolder(itemView);

        throw new NoSuchElementException("Incorrect type value");
    }

    private int getCardViewResource(int type) {
        switch (type) {
            case HOLDER_TYPE_DEPOSIT:
                return R.layout.csh_deposit_list_item;
            case HOLDER_TYPE_WITHDRAWAL:
                return R.layout.csh_withdrawal_list_item;
            default:
                throw new NoSuchElementException("Incorrect type value");
        }
    }

    @Override
    public FermatViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return createHolder(LayoutInflater.from(activity).inflate(getCardViewResource(type), viewGroup, false), type);
    }

    @Override
    public void onBindViewHolder(FermatViewHolder holder, int position) {
        int holderType = getItemViewType(position);

        switch (holderType) {
            case HOLDER_TYPE_DEPOSIT:
                DepositViewHolder depositViewHolder = (DepositViewHolder) holder;
                depositViewHolder.bind(transactionsList.get(position));
                break;
            case HOLDER_TYPE_WITHDRAWAL:
                WithdrawalViewHolder withdrawalViewHlder = (WithdrawalViewHolder) holder;
                withdrawalViewHlder.bind(transactionsList.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    @Override
    public int getItemViewType(int itemPosition) {
        if(transactionsList.get(itemPosition).getTransactionType() == TransactionType.CREDIT)
            return HOLDER_TYPE_DEPOSIT;
        else if(transactionsList.get(itemPosition).getTransactionType() == TransactionType.DEBIT)
            return HOLDER_TYPE_WITHDRAWAL;
        else
            return NO_TYPE;
    }
}
