package com.bitdubai.fermat_android_api.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * FermatViewHolder Base Class
 *
 * @author Francisco Vásquez & Matias Furszyfer
 * @version 2.0
 */
public abstract class FermatViewHolder extends RecyclerView.ViewHolder {

    private int holderType;

    /**
     * Constructor
     *
     * @param itemView
     */
    @Deprecated
    protected FermatViewHolder(View itemView) {
        super(itemView);
    }

    /**
     *
     * @param itemView
     * @param holderType
     */
    protected FermatViewHolder(View itemView,int holderType) {
        super(itemView);
        this.holderType = holderType;
    }


    public int getHolderType() {
        return holderType;
    }
}
