package com.bitdubai.sub_app.wallet_manager.commons.custom_view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_wpd.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.holder.FermatAppHolder;
import com.bitdubai.fermat_api.layer.desktop.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mati on 2015.11.28..
 */
public class CustomFolder extends LinearLayout{

    private RecyclerView mRecyclerView;
    private List<Item> lstItems;
    private FermatAdapter<Item, FermatAppHolder> mAdapter;
    private GridLayoutManager layoutManager;
    private OnClickListener onClickListener;


    public CustomFolder(Context context) {
        super(context);
        lstItems = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_folder_base, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.grid_folder_view);
        update();
    }

    public CustomFolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        lstItems = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_folder_base, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.grid_folder_view);
        update();
    }

    public void addItem(Item item){
        lstItems.add(item);
    }
    public void addItems(Collection<Item> collections){
        this.lstItems.addAll(collections);
        mAdapter.notifyDataSetChanged();
    }

    public void setAdapter(FermatAdapter<Item, FermatAppHolder> mAdapter) {
        this.mAdapter = mAdapter;
        mRecyclerView.swapAdapter(mAdapter, true);
        mRecyclerView.setOnClickListener(onClickListener)
        ;mAdapter.notifyDataSetChanged();
    }

    public FermatAdapter<Item, FermatAppHolder> getAdapter() {
        return mAdapter;
    }


    public void update(){
        if(lstItems.size()>4) layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        else layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        super.setOnClickListener(onClickListener);
    }




}
