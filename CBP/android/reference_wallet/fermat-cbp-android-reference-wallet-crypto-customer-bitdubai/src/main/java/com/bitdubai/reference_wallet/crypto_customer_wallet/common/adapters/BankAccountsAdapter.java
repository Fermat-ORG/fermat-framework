package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.BankAccountViewHolder;

import java.util.List;

/**
 * Created by nelson on 28/12/15.
 */
public class BankAccountsAdapter extends SingleDeletableItemAdapter<BankAccountNumber, BankAccountViewHolder> {

    public BankAccountsAdapter(Context context, List<BankAccountNumber> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected BankAccountViewHolder createHolder(View itemView, int type) {
        return new BankAccountViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccw_wizard_recycler_view_item;
    }
}
