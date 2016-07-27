package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by nelson on 28/12/15.
 */
public class BankAccountViewHolder extends SingleDeletableItemViewHolder<BankAccountNumber> {
    private FermatTextView banco;
    private TextView alias;
    private FermatTextView numero;


    public BankAccountViewHolder(View itemView) {
        super(itemView);

        banco = (FermatTextView) itemView.findViewById(R.id.ccw_title);
        alias = (TextView) itemView.findViewById(R.id.textView);
        numero = (FermatTextView) itemView.findViewById(R.id.ccw_sub_title);
    }

    @Override
    public void bind(BankAccountNumber data) {
        banco.setText(data.getBankName());
        alias.setText(data.getCurrencyType().getCode());
        numero.setText(data.getAccount());
    }

    @Override
    public int getCloseButtonResource() {
        return R.id.ccw_close_img_button;
    }
}
