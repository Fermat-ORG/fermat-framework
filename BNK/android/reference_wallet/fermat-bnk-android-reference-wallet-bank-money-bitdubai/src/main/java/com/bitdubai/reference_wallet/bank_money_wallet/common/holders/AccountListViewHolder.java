package com.bitdubai.reference_wallet.bank_money_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

import java.text.DecimalFormat;

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

    private ImageView imageView;

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
        imageView = (ImageView) itemView.findViewById(R.id.bw_account_image);
    }

    public void bind(BankAccountNumber itemInfo,int pos){
        itemView.setBackgroundColor(335);
        account.setText(itemInfo.getAccount());
        alias.setText(itemInfo.getAlias());
        //currency.setText(itemInfo.getMoneyType().getCode());
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        availableBalance.setText(df.format(moduleManager.getAvailableBalance(itemInfo.getAccount())) + " " + itemInfo.getCurrencyType().getCode());
        bookBalance.setText(df.format(moduleManager.getBookBalance(itemInfo.getAccount())) + " " + itemInfo.getCurrencyType().getCode());
        if(availableBalance.getText().equals(bookBalance.getText())){
            bookBalance.setVisibility(View.GONE);
            bookText.setVisibility(View.GONE);
            balanceText.setTextColor(itemView.getResources().getColor(R.color.text_color_soft_blue));
        }else {
            balanceText.setTextColor(itemView.getResources().getColor(R.color.soft_purple));
        }
        imageView.setImageResource(getResource(pos));
        imageView.setVisibility(View.VISIBLE);
    }


    public static int getResource(int pos){
        switch (pos){
            case 0: return R.drawable.bw_bg_detail_number_01;
            case 1: return R.drawable.bw_bg_detail_number_02;
            case 2: return R.drawable.bw_bg_detail_number_03;
            case 3: return R.drawable.bw_bg_detail_number_04;
            case 4: return R.drawable.bw_bg_detail_number_05;
            case 5: return R.drawable.bw_bg_detail_number_06;
            default: return R.drawable.bw_bg_detail_number_01;
        }
    }

}
