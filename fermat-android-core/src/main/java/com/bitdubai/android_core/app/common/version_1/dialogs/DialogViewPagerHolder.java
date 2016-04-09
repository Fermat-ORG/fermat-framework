package com.bitdubai.android_core.app.common.version_1.dialogs;

import android.view.View;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by mati on 2016.04.05..
 */
public class DialogViewPagerHolder extends FermatViewHolder{



    protected DialogViewPagerHolder(View itemView, int holderType) {
        super(itemView, holderType);
    }

    protected DialogViewPagerHolder(View itemView, int holderId, int holderType) {
        super(itemView, holderId, holderType);
    }

    public DialogViewPagerHolder(View itemView, int holderId, int holderType, int holderLayoutRes) {
        super(itemView, holderId, holderType, holderLayoutRes);
    }
}
