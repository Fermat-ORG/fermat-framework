package com.bitdubai.android_core.app.common.version_1.settings_slider;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by Matias Furszyfer on 2016.03.25..
 */
public class SettingsHolder extends FermatViewHolder {

    private ImageView img;
    private FermatTextView text;
    private FermatTextView subText;


    public SettingsHolder(View itemView, int holderType) {
        super(itemView, holderType);
        img = (ImageView) itemView.findViewById(R.id.btn_image);
        text = (FermatTextView) itemView.findViewById(R.id.text);
        subText = (FermatTextView) itemView.findViewById(R.id.sub_text);
    }

    public ImageView getImg() {
        return img;
    }

    public FermatTextView getText() {
        return text;
    }

    public FermatTextView getSubText() {
        return subText;
    }
}
