package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.GrouperViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.ListItemTransactionChild;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.ListItemTransactionParent;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.TransactionViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.models.GrouperItem;

import java.util.List;

/**
 * Created by Gian Barboza on 17/05/16.
 */
public class ReceiveTransactionsExpandableAdapter
        extends ExpandableRecyclerAdapter<ListItemTransactionParent, ListItemTransactionChild, GrouperItem, LossProtectedWalletTransaction> {

    private LayoutInflater mInflater;

    private Resources res;

    /**
     * Public primary constructor.
     *
     * @param context        the activity context where the RecyclerView is going to be displayed
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     */
    public ReceiveTransactionsExpandableAdapter(Context context, List<GrouperItem> parentItemList, Resources res) {
        super(parentItemList);
        mInflater = LayoutInflater.from(context);
        this.res = res;
    }



    /**
     * OnCreateViewHolder implementation for parent items. The desired ParentViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public ListItemTransactionParent onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_item_trasaction_parent, parent, false);
        return new ListItemTransactionParent(view,res);
    }

    /**
     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public ListItemTransactionChild onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.list_item_transaction_child, parent, false);
        return new ListItemTransactionChild(view);
    }


    /**
     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
     * parent view should be performed here.
     *
     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
     * @param position         the position in the RecyclerView of the item
     */
    @Override
    public void onBindParentViewHolder(ListItemTransactionParent parentViewHolder, int position, GrouperItem parentListItem) {
        parentViewHolder.bind((LossProtectedWalletTransaction) parentListItem.getCryptoWalletTransaction());
    }

    /**
     * OnBindViewHolder implementation for child items. Any data or view modifications of the
     * child view should be performed here.
     *
     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
     * @param position        the position in the RecyclerView of the item
     */
    @Override
    public void onBindChildViewHolder(ListItemTransactionChild childViewHolder, int position, LossProtectedWalletTransaction childListItem) {
        childViewHolder.bind(childListItem);
    }

}