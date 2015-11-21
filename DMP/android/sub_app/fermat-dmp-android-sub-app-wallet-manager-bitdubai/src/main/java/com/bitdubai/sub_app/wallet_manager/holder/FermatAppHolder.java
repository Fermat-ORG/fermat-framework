package com.bitdubai.sub_app.wallet_manager.holder;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dmp.wallet_manager.R;

/**
 * Created by mati on 2015.10.18..
 */
public class FermatAppHolder extends FermatViewHolder {

    public ImageView thumbnail;
    public FermatTextView name;
    /**
     * Constructor
     *
     * @param itemView
     */
    public FermatAppHolder(View itemView) {
        super(itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.image_view);
        name = (FermatTextView) itemView.findViewById(R.id.company_text_view);
    }
}
