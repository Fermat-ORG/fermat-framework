package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.models;


import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentListItem;

import java.util.List;


public class GrouperItem<ITEM1,ITEM> implements ParentListItem {


    private int childCount;
    private boolean initiallyExpanded;

    private ITEM1 cryptoWalletTransaction1;
    private ITEM cryptoWalletTransaction;

    public GrouperItem( ITEM1 cryptoWalletTransaction1, boolean initiallyExpanded,ITEM cryptoWalletTransaction) {
        this.initiallyExpanded = initiallyExpanded;
        this.cryptoWalletTransaction1 = cryptoWalletTransaction1;
        this.cryptoWalletTransaction = cryptoWalletTransaction;
    }

    public GrouperItem(String text, boolean initiallyExpanded) {
        this.initiallyExpanded = initiallyExpanded;
    }

    public int getChildCount() {
        return childCount;
    }

    @Override
    public ITEM1 getItem() {
        return cryptoWalletTransaction1;
    }

    /**
     * Getter method for the list of children associated with this parent list item
     *
     * @return list of all children associated with this specific parent list item
     */
    @Override
    public List<ITEM1> getChildItemList() {
        return null;
    }

    /**
     * Setter method for the list of children associated with this parent list item
     *
     * @param childItem the item associated with this parent item
     */
    public void setChildItem(ITEM1 childItem) {
            childCount = 1;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return initiallyExpanded;
    }

    public void setInitiallyExpanded(boolean initiallyExpanded) {
        this.initiallyExpanded = initiallyExpanded;
    }

    public ITEM getCryptoWalletTransaction() {
        return cryptoWalletTransaction;
    }
}