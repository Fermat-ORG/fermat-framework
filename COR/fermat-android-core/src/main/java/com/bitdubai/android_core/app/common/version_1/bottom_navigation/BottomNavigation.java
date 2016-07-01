package com.bitdubai.android_core.app.common.version_1.bottom_navigation;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.OnStartDragListener;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.SimpleItemTouchHelperCallback;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.11.25..
 */
public class BottomNavigation implements  OnStartDragListener {


    private DesktopHolderClickCallback<Item> desktopHolderClickCallback;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private ItemTouchHelper mItemTouchHelper;
    private Activity activity;
    private List<Item> lstItems;
    BottomNavigationAdapter adapter;

    public BottomNavigation(Activity activity,List<Item> lstItems,DesktopHolderClickCallback<Item> desktopHolderClickCallback) {
        this.activity = activity;
        this.lstItems = lstItems;
        this.desktopHolderClickCallback = desktopHolderClickCallback;
        setUp();
    }

    private void setUp() {
        recyclerView = (RecyclerView) activity.findViewById(com.bitdubai.fermat.R.id.bottom_navigation_recycler);
        if(recyclerView!=null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutFrozen(true);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    // super.onScrolled(recyclerView, dx, dy);
                }
            });
            recyclerView.stopScroll();
            layoutManager = new GridLayoutManager(activity, 4, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new BottomNavigationAdapter(activity, lstItems, desktopHolderClickCallback);
            recyclerView.setAdapter(adapter);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }

    public void setDesktopHolderClickCallback(DesktopHolderClickCallback<Item> desktopHolderClickCallback) {
        adapter.setDesktopHolderClickCallback(desktopHolderClickCallback);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public void reset(){
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.clearOnScrollListeners();
        adapter =null;
        desktopHolderClickCallback = null;
        activity = null;
        mItemTouchHelper = null;
        recyclerView = null;
        layoutManager = null;
        lstItems = null;
    }
}
