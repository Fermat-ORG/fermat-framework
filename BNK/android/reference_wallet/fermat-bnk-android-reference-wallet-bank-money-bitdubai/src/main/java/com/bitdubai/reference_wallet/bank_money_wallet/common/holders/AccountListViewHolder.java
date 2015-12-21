package com.bitdubai.reference_wallet.bank_money_wallet.common.holders;

import android.view.View;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

/**
 * Created by memo on 10/12/15.
 */
public class AccountListViewHolder extends FermatViewHolder {

    private View itemView;
    private FermatTextView account;
    private FermatTextView alias;
    private FermatTextView accountType;
    private FermatTextView currency;

    public AccountListViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        account = (FermatTextView) itemView.findViewById(R.id.account);
        alias =  (FermatTextView) itemView.findViewById(R.id.account_alias);
        accountType = (FermatTextView) itemView.findViewById(R.id.account_type);
        currency = (FermatTextView) itemView.findViewById(R.id.currency);
    }

    public void bind(BankAccountNumber itemInfo){
        itemView.setBackgroundColor(335);
        account.setText(itemInfo.getAccount());
        alias.setText(itemInfo.getAlias());
        accountType.setText(itemInfo.getAccountType().getCode());
        currency.setText(itemInfo.getCurrencyType().getCode());
    }

}
