package com.bitdubai.sub_app.wallet_manager.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_wpd.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.commons.custom_view.CustomFolder;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.ItemTouchHelperViewHolder;

/**
 * Created by mati on 2015.10.18..
 */
public class FermatAppHolder extends FermatViewHolder implements ItemTouchHelperViewHolder{

    public ImageView thumbnail;
    public FermatTextView name;
    public CustomFolder folder;
    public RelativeLayout grid_container;

    /**
     * Constructor
     *
     * @param itemView
     */
    public FermatAppHolder(View itemView) {
        super(itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.image_view);
        name = (FermatTextView) itemView.findViewById(R.id.company_text_view);
        folder = (CustomFolder) itemView.findViewById(R.id.folder);
        grid_container = (RelativeLayout) itemView.findViewById(R.id.grid_container);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
