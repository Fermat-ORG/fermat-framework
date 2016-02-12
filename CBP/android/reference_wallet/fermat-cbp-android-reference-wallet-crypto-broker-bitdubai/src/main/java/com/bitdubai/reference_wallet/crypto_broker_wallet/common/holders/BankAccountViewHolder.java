package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;


/**
 * Created by nelson on 28/12/15.
 */
public class BankAccountViewHolder extends SingleDeletableItemViewHolder<BankAccountNumber> {
    private FermatTextView title;
    private FermatTextView subTitle;


    public BankAccountViewHolder(View itemView) {
        super(itemView);

        title = (FermatTextView) itemView.findViewById(R.id.cbw_title);
        subTitle = (FermatTextView) itemView.findViewById(R.id.cbw_sub_title);
    }

    @Override
    public void bind(BankAccountNumber data) {
        subTitle.setText(data.getAlias());
        title.setText(data.getCurrencyType().getCode());
    }

    @Override
    public int getCloseButtonResource() {
        return R.id.cbw_close_img_button;
    }
}
