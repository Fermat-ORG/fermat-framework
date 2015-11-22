package com.bitdubai.sub_app.wallet_manager.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.ItemTouchHelperViewHolder;

/**
 * Created by mati on 2015.10.18..
 */
public class FermatAppHolder extends FermatViewHolder implements ItemTouchHelperViewHolder,View.OnClickListener{

    public ImageView thumbnail;
    public FermatTextView name;
    /**
     * Constructor
     *
     * @param itemView
     */
    public FermatAppHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        thumbnail = (ImageView) itemView.findViewById(R.id.image_view);
        name = (FermatTextView) itemView.findViewById(R.id.company_text_view);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }

    @Override
    public void onClick(View view) {

    }
}
