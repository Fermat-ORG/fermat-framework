package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentListItem;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.VerticalChild;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.VerticalParent;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.GrouperParentViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.OpenNegotiationsChildViewHolder;

import java.util.List;

/**
 * An example custom implementation of the ExpandableRecyclerAdapter.
 *
 * @author Ryan Brooks
 * @version 1.0
 * @since 5/27/2015
 */
public class CbpVerticalExpandableAdapter extends ExpandableRecyclerAdapter<GrouperParentViewHolder, OpenNegotiationsChildViewHolder> {

    private LayoutInflater mInflater;

    /**
     * Public primary constructor.
     *
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     */
    public CbpVerticalExpandableAdapter(Context context, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * OnCreateViewHolder implementation for parent items. The desired ParentViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public GrouperParentViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.cbp_list_item_parent, parent, false);
        return new GrouperParentViewHolder(view);
    }

    /**
     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public OpenNegotiationsChildViewHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.cbp_list_item_child, parent, false);
        return new OpenNegotiationsChildViewHolder(view);
    }

    /**
     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
     * parent view should be performed here.
     *
     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
     * @param position         the position in the RecyclerView of the item
     */
    @Override
    public void onBindParentViewHolder(GrouperParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        VerticalParent verticalParent = (VerticalParent) parentListItem;
        parentViewHolder.bind(verticalParent.getParentNumber(), verticalParent.getParentText());
    }

    /**
     * OnBindViewHolder implementation for child items. Any data or view modifications of the
     * child view should be performed here.
     *
     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
     * @param position        the position in the RecyclerView of the item
     */
    @Override
    public void onBindChildViewHolder(OpenNegotiationsChildViewHolder childViewHolder, int position, Object childListItem) {
        VerticalChild verticalChild = (VerticalChild) childListItem;
        childViewHolder.bind(verticalChild.getChildText());
    }
}