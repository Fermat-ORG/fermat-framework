package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.NewGrouperHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.transactionHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.models.GrouperItem;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Joaquin Carrasquero on 04/05/16.
 */
public class provisionalAdapter extends ExpandableRecyclerAdapter<NewGrouperHolder, transactionHolder, GrouperItem, LossProtectedWalletTransaction> {


    private LayoutInflater mInflater;

    private Resources res;

    /**
     * Public primary constructor.
     *
     * @param context        the activity context where the RecyclerView is going to be displayed
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     */
    public provisionalAdapter(Context context, List<GrouperItem> parentItemList, Resources res) {
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
    public NewGrouperHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.grouper, parent, false);
        return new NewGrouperHolder(view,res);
    }

    /**
     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public transactionHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.transaction_list_item, parent, false);
        return new transactionHolder(view);
    }



    /**
     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
     * parent view should be performed here.
     *
     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
     * @param position         the position in the RecyclerView of the item
     */
    @Override
    public void onBindParentViewHolder(NewGrouperHolder parentViewHolder, int position, GrouperItem parentListItem) {
        parentViewHolder.bind(parentListItem.getChildCount(),(LossProtectedWalletTransaction) parentListItem.getCryptoWalletTransaction());
    }

    /**
     * OnBindViewHolder implementation for child items. Any data or view modifications of the
     * child view should be performed here.
     *
     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
     * @param position        the position in the RecyclerView of the item
     */
    @Override
    public void onBindChildViewHolder(transactionHolder childViewHolder, int position, LossProtectedWalletTransaction childListItem) {
        childViewHolder.bind(childListItem);
    }
}
