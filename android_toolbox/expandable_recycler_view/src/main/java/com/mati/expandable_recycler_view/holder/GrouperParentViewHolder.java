package com.mati.expandable_recycler_view.holder;

import android.view.View;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentListItem;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentViewHolder;

/**
 * Created by mati on 2016.03.15..
 */
public abstract class GrouperParentViewHolder<GROUPER_ITEM extends ParentListItem> extends ParentViewHolder {

    private int holderId = 0;
    private int holderType;
    private int holderLayoutRes;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public GrouperParentViewHolder(View itemView) {
        super(itemView);
    }

    public GrouperParentViewHolder(View itemView, int holderId, int holderType, int holderLayoutRes) {
        super(itemView);
        this.holderId = holderId;
        this.holderType = holderType;
        this.holderLayoutRes = holderLayoutRes;
    }

    public abstract void bind(int childCount, GROUPER_ITEM item);


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
