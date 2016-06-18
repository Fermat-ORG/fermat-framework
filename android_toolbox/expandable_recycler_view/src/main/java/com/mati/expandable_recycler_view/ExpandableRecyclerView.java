package com.mati.expandable_recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentListItem;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.mati.expandable_recycler_view.holder.ChildViewHolder;
import com.mati.expandable_recycler_view.holder.GrouperParentViewHolder;

import java.util.List;

/**
 * Created by mati on 2016.02.23..
 */
public class ExpandableRecyclerView<M extends ParentListItem,S extends ReferenceAppFermatSession,RE extends ResourceProviderManager> extends FermatWalletExpandableListFragment<M,S,RE> {


    private ExpandableAdapter expandableAdapter;
    private boolean hasMenu;



    private void ExpandableRecyclerView(){}


    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.base_main;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.open_contracts_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return false;
    }

    @Override
    public List<M> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        return null;
    }

    @Override
    public void onPostExecute(Object... result) {

    }

    @Override
    public void onErrorOccurred(Exception ex) {

    }

    @Override
    public ExpandableRecyclerAdapter getAdapter() {
        return expandableAdapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    public void setAdapter(ExpandableAdapter adapter) {
        this.expandableAdapter = adapter;
    }

    public static class Builder<GROUPER_ITEM extends ParentListItem>{

        private Context context;
        private boolean hasMenu = false;
        private GrouperParentViewHolder parentHolder;
        private ChildViewHolder childHolder;
        private List<GROUPER_ITEM> grouperItemList;


        public Builder setHasMenu(boolean hasMenu) {
            this.hasMenu = hasMenu;
            return this;
        }

        public Builder setChildHolder(ChildViewHolder childHolder) {
            this.childHolder = childHolder;
            return this;
        }

        public Builder setParentHolder(GrouperParentViewHolder parentHolder) {
            this.parentHolder = parentHolder;
            return this;
        }

        public ExpandableRecyclerView build() {
            ExpandableRecyclerView expandableRecyclerView = new ExpandableRecyclerView();
            ExpandableAdapter expandableAdapter = new ExpandableAdapter(context, grouperItemList, context.getResources(), parentHolder, childHolder);
            expandableRecyclerView.setAdapter(expandableAdapter);
            return expandableRecyclerView;
        }

    }

}
