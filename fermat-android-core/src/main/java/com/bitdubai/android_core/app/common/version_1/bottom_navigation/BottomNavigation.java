package com.bitdubai.android_core.app.common.version_1.bottom_navigation;

import android.app.Activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Scroller;
import android.widget.Toast;

import com.bitdubai.fermat.R;

import com.bitdubai.sub_app.wallet_manager.commons.helpers.OnStartDragListener;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.SimpleItemTouchHelperCallback;
import com.bitdubai.sub_app.wallet_manager.holder.DesktopHolderClickCallback;
import com.bitdubai.sub_app.wallet_manager.structure.Item;

import java.util.List;

/**
 * Created by mati on 2015.11.25..
 */
public class BottomNavigation implements DesktopHolderClickCallback<Item>,OnStartDragListener {


    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private ItemTouchHelper mItemTouchHelper;
    private Activity activity;
    private List<Item> lstItems;
    BottomNavigationAdapter adapter;

    public BottomNavigation(Activity activity,List<Item> lstItems) {
        this.activity = activity;
        this.lstItems = lstItems;
        setUp();
    }

    private void setUp() {
        recyclerView = (RecyclerView) activity.findViewById(com.bitdubai.fermat.R.id.bottom_navigation_recycler);
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
        layoutManager = new GridLayoutManager(activity, 5, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BottomNavigationAdapter(activity, lstItems,this);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void onHolderItemClickListener(Item data, int position) {
        Toast.makeText(activity,"Comming soon",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
