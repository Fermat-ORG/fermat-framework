package com.bitdubai.reference_wallet.bank_money_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.common.holders.AccountListViewHolder;

import java.util.List;

/**
 * Created by memo on 10/12/15.
 */
public class AccountListAdapter extends FermatAdapter<BankAccountNumber,AccountListViewHolder> {

    private BankMoneyWalletModuleManager moduleManager;

    public AccountListAdapter(Context context, List<BankAccountNumber> dataSet, BankMoneyWalletModuleManager moduleManager) {
        super(context, dataSet);
        this.moduleManager = moduleManager;
    }

    @Override
    protected AccountListViewHolder createHolder(View itemView, int type) {
        return new AccountListViewHolder(itemView,moduleManager);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.bw_account_list_item;
    }

    @Override
    protected void bindHolder(AccountListViewHolder holder, BankAccountNumber data, int position) {
        holder.bind(data);
    }
}
