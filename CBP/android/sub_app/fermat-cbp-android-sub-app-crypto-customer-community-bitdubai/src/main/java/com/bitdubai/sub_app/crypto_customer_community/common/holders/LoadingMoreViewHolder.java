package com.bitdubai.sub_app.crypto_customer_community.common.holders;

import android.view.View;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_customer_community.R;


/**
 * Created by Nelson Ramirez - (nelsonalfo@gmail.com) on 22/06/2016.
 *
 * @author nelsonalfo
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
