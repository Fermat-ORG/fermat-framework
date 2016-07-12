package com.bitdubai.android_core.app.common.version_1.dialogs;

import android.view.View;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by mati on 2016.04.05..
 */
public class DialogViewPagerHolder extends FermatViewHolder{

    public FermatTextView txt_title;
    public FermatTextView first_paragraph;
    public FermatTextView second_paragraph;
    public FermatTextView third_paragraph;


    protected DialogViewPagerHolder(View itemView, int holderType) {
        super(itemView, holderType);
        txt_title = (FermatTextView) itemView.findViewById(R.id.txt_title);
        first_paragraph = (FermatTextView) itemView.findViewById(R.id.txt_first_paragraph);
        second_paragraph = (FermatTextView) itemView.findViewById(R.id.txt_second_paragraph);
        third_paragraph = (FermatTextView) itemView.findViewById(R.id.txt_third_paragraph);
    }
}
