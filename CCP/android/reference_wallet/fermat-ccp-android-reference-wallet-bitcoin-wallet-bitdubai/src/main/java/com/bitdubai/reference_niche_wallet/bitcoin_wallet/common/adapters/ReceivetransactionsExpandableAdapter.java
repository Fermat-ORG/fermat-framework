package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.GrouperViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.TransactionViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.models.GrouperItem;

import java.util.List;


public class ReceivetransactionsExpandableAdapter
        extends ExpandableRecyclerAdapter<GrouperViewHolder, TransactionViewHolder, GrouperItem, CryptoWalletTransaction> {

    private LayoutInflater mInflater;

    private Resources res;

    /**
     * Public primary constructor.
     *
     * @param context        the activity context where the RecyclerView is going to be displayed
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     */
    public ReceivetransactionsExpandableAdapter(Context context, List<GrouperItem> parentItemList,Resources res) {
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
    public GrouperViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.ccp_grouper_list_item, parent, false);
        return new GrouperViewHolder(view,res);
    }

    /**
     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public TransactionViewHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.ccp_transaction_list_item, parent, false);
        return new TransactionViewHolder(view);
    }



    /**
     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
     * parent view should be performed here.
     *
     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
     * @param position         the position in the RecyclerView of the item
     */
    @Override
    public void onBindParentViewHolder(GrouperViewHolder parentViewHolder, int position, GrouperItem parentListItem) {
        parentViewHolder.bind(parentListItem.getChildCount(),(CryptoWalletTransaction) parentListItem.getCryptoWalletTransaction());
    }

    /**
     * OnBindViewHolder implementation for child items. Any data or view modifications of the
     * child view should be performed here.
     *
     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
     * @param position        the position in the RecyclerView of the item
     */
    @Override
    public void onBindChildViewHolder(TransactionViewHolder childViewHolder, int position, CryptoWalletTransaction childListItem) {

        String intraUser = childListItem.getInvolvedActor().getActorPublicKey();
        childViewHolder.bind(childListItem,intraUser);
    }
}