package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders;

import android.view.View;
import android.widget.ProgressBar;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;


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
