package com.bitdubai.reference_wallet.bank_money_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

import java.math.BigDecimal;
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
    private static final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");


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

        BigDecimal available = moduleManager.getAvailableBalance(itemInfo.getAccount());
        BigDecimal book = moduleManager.getBookBalance(itemInfo.getAccount());

        availableBalance.setText(moneyFormat.format(available) + " " + itemInfo.getCurrencyType().getCode());
        bookBalance.setText(moneyFormat.format(book) + " " + itemInfo.getCurrencyType().getCode());

        //Hide book balance if equal
        if(availableBalance.getText().equals(bookBalance.getText())){
            bookBalance.setVisibility(View.GONE);
            bookText.setVisibility(View.GONE);
            balanceText.setTextColor(itemView.getResources().getColor(R.color.text_color_soft_blue));
        }else {
            balanceText.setTextColor(itemView.getResources().getColor(R.color.soft_purple));
        }
        imageView.setImageResource(getResource(itemInfo.getAccountImageId()));
        imageView.setVisibility(View.VISIBLE);
    }


    public static int getResource(String imageId){
        switch (imageId){
            case "Cube": return R.drawable.bw_bg_detail_number_01;
            case "Safe": return R.drawable.bw_bg_detail_number_02;
            case "Money": return R.drawable.bw_bg_detail_number_03;
            case "Coins": return R.drawable.bw_bg_detail_number_04;
            case "Coins 2": return R.drawable.bw_bg_detail_number_05;
            case "Money 2": return R.drawable.bw_bg_detail_number_06;
            default: return R.drawable.bw_bg_detail_number_01;
        }
    }

}
