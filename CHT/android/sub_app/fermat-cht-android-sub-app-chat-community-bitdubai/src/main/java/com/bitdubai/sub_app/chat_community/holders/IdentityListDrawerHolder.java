package com.bitdubai.sub_app.chat_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.chat_community.R;

/**
 * IdentitiesListDrawerHolder
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class IdentityListDrawerHolder extends FermatViewHolder {

    public FermatTextView userIdentityName;
    public ImageView userIdentityImage;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    protected IdentityListDrawerHolder(View itemView) {
        super(itemView);

        userIdentityImage = (ImageView) itemView.findViewById(R.id.imageView_avatar);
        userIdentityName = (FermatTextView) itemView.findViewById(R.id.textView_identity);

    }
}
