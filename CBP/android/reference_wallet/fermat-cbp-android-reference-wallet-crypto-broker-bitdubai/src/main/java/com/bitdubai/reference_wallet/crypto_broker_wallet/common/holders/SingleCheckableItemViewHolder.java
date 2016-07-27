package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by nelson on 30/12/15.
 */
public abstract class SingleCheckableItemViewHolder<T> extends FermatViewHolder {

    protected FermatCheckBox checkBox;


    protected SingleCheckableItemViewHolder(View itemView) {
        super(itemView);

        checkBox = (FermatCheckBox) itemView.findViewById(getCheckboxResource());
    }

    public abstract void bind(T data);

    public abstract int getCheckboxResource();

    public FermatCheckBox getCheckBox() {
        return checkBox;
    }
}
