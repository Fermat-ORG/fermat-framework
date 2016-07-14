package com.bitdubai.sub_app.intra_user_community.holders;

import android.view.View;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;
/**
 * Created by natalia on 13/07/16.
 */
public class LoadingMoreViewHolder extends FermatViewHolder {

    public ProgressBar progressBar;

    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     */
    public LoadingMoreViewHolder(View itemView, int type) {
        super(itemView, type);
        progressBar = (ProgressBar) itemView.findViewById(R.id.ccp_progress_bar);
    }
}
