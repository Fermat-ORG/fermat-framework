package com.bitdubai.sub_app.crypto_broker_community.holders;

import android.view.View;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_broker_community.R;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
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
        progressBar = (ProgressBar) itemView.findViewById(R.id.cbc_progress_bar);
    }
}
