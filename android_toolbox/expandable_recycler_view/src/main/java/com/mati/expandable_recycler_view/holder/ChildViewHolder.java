package com.mati.expandable_recycler_view.holder;

import android.view.View;

/**
 * Created by mati on 2016.03.15..
 */
public abstract class ChildViewHolder<ITEM> extends com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder {

    private int holderId = 0;
    private int holderType;
    private int holderLayoutRes;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public ChildViewHolder(View itemView) {
        super(itemView);
    }

    public ChildViewHolder(View itemView, int holderId, int holderType, int holderLayoutRes) {
        super(itemView);
        this.holderId = holderId;
        this.holderType = holderType;
        this.holderLayoutRes = holderLayoutRes;
    }

    public abstract void bind(ITEM childListItem);


    public int getHolderId() {
        return holderId;
    }

    public int getHolderType() {
        return holderType;
    }

    public int getHolderLayoutRes() {
        return holderLayoutRes;
    }

    public void setHolderLayoutRes(int holderLayoutRes) {
        this.holderLayoutRes = holderLayoutRes;
    }
}
