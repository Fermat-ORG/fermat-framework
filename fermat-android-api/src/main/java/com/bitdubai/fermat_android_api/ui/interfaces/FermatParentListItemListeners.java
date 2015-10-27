package com.bitdubai.fermat_android_api.ui.interfaces;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentListItem;

/**
 * Item Listener to be used when you want listen parent item's click and long click events in the ExpandableRecyclerView
 *
 * @author Nelson Ramirez
 * @since 25/08/15
 */
public interface FermatParentListItemListeners<M extends ParentListItem> {

    /**
     * Item Click Listener event
     *
     * @param data     the item's data
     * @param position the item's position
     */
    void onItemClickListener(M data, int position);

    /**
     * Item Long Click Listener event
     *
     * @param data
     * @param position
     */
    void onLongItemClickListener(M data, int position);

}
