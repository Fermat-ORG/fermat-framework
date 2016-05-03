package com.bitdubai.sub_app.wallet_manager.commons.model;


import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentListItem;

import java.util.List;


public class GrouperItem<CHILD_TYPE,ITEM> implements ParentListItem {

    private List<CHILD_TYPE> childItemList;
    private int childCount;
    private boolean initiallyExpanded;

    private ITEM item;

    public GrouperItem( List<CHILD_TYPE> childItemList, boolean initiallyExpanded,ITEM item) {
        this.initiallyExpanded = initiallyExpanded;
        this.childItemList = childItemList;
        this.item = item;
    }

    public GrouperItem(String text, boolean initiallyExpanded) {
        this.initiallyExpanded = initiallyExpanded;
    }

    public int getChildCount() {
        if (childItemList != null)
            // Le resto uno porque el ultimo es el total
            childCount = this.childItemList.size();
        return childCount;
    }

    /**
     * Getter method for the list of children associated with this parent list item
     *
     * @return list of all children associated with this specific parent list item
     */
    @Override
    public List<CHILD_TYPE> getChildItemList() {
        return childItemList;
    }

    /**
     * Setter method for the list of children associated with this parent list item
     *
     * @param childItemList the list of all children associated with this parent list item
     */
    public void setChildItemList(List<CHILD_TYPE> childItemList) {
        this.childItemList = childItemList;
        if (childItemList != null)
            childCount = this.childItemList.size();
    }

    @Override
    public boolean isInitiallyExpanded() {
        return initiallyExpanded;
    }

    public void setInitiallyExpanded(boolean initiallyExpanded) {
        this.initiallyExpanded = initiallyExpanded;
    }

    public ITEM getItem() {
        return item;
    }
}