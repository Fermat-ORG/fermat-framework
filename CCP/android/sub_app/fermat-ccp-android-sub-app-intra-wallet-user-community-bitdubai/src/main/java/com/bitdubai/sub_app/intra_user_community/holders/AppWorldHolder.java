package com.bitdubai.sub_app.intra_user_community.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by mati on 2015.10.18..
 * Modified by Jose Manuel De Sousa Dos Santos
 */
public class AppWorldHolder extends FermatViewHolder {

    public SquareImageView thumbnail;
    public FermatTextView name;
    public ImageView connectionState;
    public FermatTextView row_connection_state;
    public ProgressBar progressBar;

    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     */
    public AppWorldHolder(View itemView) {
        super(itemView);
        connectionState = (ImageView) itemView.findViewById(R.id.connection_state);
        row_connection_state = (FermatTextView) itemView.findViewById(R.id.connection_state_user);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.profile_image);
        name = (FermatTextView) itemView.findViewById(R.id.community_name);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}
