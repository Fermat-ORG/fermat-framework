package com.mati.horizontalscrollview;

import android.view.View;
import android.widget.ImageButton;


import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by mati on 2016.02.19..
 */
public class HorizontalRecyclerViewHolder extends FermatViewHolder {

    private ImageButton imageButton;
    private FermatTextView txtName;


    protected HorizontalRecyclerViewHolder(View itemView, int holderType) {
        super(itemView, holderType);
        imageButton = (ImageButton) itemView.findViewById(R.id.image_button);
        txtName = (FermatTextView) itemView.findViewById(R.id.txt_item_name);
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public FermatTextView getTxtName() {
        return txtName;
    }
}
