package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.view.View;
import android.widget.ProgressBar;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;


/**
 * Created by aabreu1 on 08/09/16.
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
