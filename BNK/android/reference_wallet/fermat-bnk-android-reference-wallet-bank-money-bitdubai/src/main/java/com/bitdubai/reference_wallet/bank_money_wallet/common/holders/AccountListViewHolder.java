package com.bitdubai.reference_wallet.bank_money_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

/**
 * Created by memo on 10/12/15.
 */
public class AccountListViewHolder extends FermatViewHolder {

    private View itemView;
    private FermatTextView account;
    private FermatTextView alias;
    //private FermatTextView accountType;
    private FermatTextView currency;
    private FermatTextView availableBalance;
    private FermatTextView bookBalance;

    private FermatTextView balanceText;
    private FermatTextView availableText;
    private FermatTextView bookText;

    private BankMoneyWalletModuleManager moduleManager;

    public AccountListViewHolder(View itemView,BankMoneyWalletModuleManager moduleManager) {
        super(itemView);
        this.itemView = itemView;
        this.moduleManager = moduleManager;
        account = (FermatTextView) itemView.findViewById(R.id.account);
        alias =  (FermatTextView) itemView.findViewById(R.id.account_alias);
        //currency = (FermatTextView) itemView.findViewById(R.id.currency);
        availableBalance = (FermatTextView) itemView.findViewById(R.id.available_balance);
        bookBalance = (FermatTextView) itemView.findViewById(R.id.book_balance);

        balanceText = (FermatTextView) itemView.findViewById(R.id.balance_text);
        availableText = (FermatTextView) itemView.findViewById(R.id.available_text);
        bookText = (FermatTextView) itemView.findViewById(R.id.book_text);
        balanceText.setText("Balance");
        bookText.setText("Book");
        availableText.setText("Available");
    }

    public void bind(BankAccountNumber itemInfo){
        itemView.setBackgroundColor(335);
        account.setText(itemInfo.getAccount());
        alias.setText(itemInfo.getAlias());
        //currency.setText(itemInfo.getCurrencyType().getCode());
        availableBalance.setText(String.valueOf(moduleManager.getBankingWallet().getAvailableBalance(itemInfo.getAccount()))+ "  "+itemInfo.getCurrencyType().getCode());
        bookBalance.setText(String.valueOf(moduleManager.getBankingWallet().getBookBalance(itemInfo.getAccount()))+ "  "+itemInfo.getCurrencyType().getCode());
    }

}
