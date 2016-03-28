package com.bitdubai.android_core.app.common.version_1.settings_slider;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.03.25..
 */
public class SettingsSlider {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Activity activity;
    private List<SettingsItem> lstItems;
    private SettingsAdapter adapter;

    public SettingsSlider(Activity activity,List<SettingsItem> lstItems) {
        this.activity = activity;
        this.lstItems = lstItems;
        setUp();
    }

    private void setUp() {
        recyclerView = (RecyclerView) activity.findViewById(com.bitdubai.fermat.R.id.settings_recycler_view);
        if(recyclerView!=null) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


            recyclerView.setLayoutManager(layoutManager);
            adapter = new SettingsAdapter(activity, lstItems, null);;
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(4,20));
        }
    }

    public void setClickCallback(SettingsCallback<SettingsItem> settingsCallback) {
        adapter.setClickCallback(settingsCallback);
    }


    public void reset(){
        recyclerView.clearOnChildAttachStateChangeListeners();
        recyclerView.clearOnScrollListeners();
        adapter =null;
        activity = null;
        recyclerView = null;
        layoutManager = null;
        lstItems = null;
    }

    public void changeIcon(SettingsType settingsType, int res) {
        for (int i=0;i<adapter.getItemCount();i++){
            if(adapter.getItem(i).getSettingsType() == settingsType){
                adapter.getItem(i).setImageRes(res);
                break;
            }
        }
        adapter.notifyDataSetChanged();

    }
}
