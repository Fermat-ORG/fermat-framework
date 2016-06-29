package com.bitdubai.android_core.app.common.version_1.settings_slider;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.03.25..
 */
public class SettingsSlider {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FermatActivity activity;
    private List<SettingsItem> lstItems;
    private SettingsAdapter adapter;
    private View txt_more_settings;

    public SettingsSlider(FermatActivity activity,List<SettingsItem> lstItems) {
        this.activity = activity;
        this.lstItems = lstItems;
        setUp();
    }

    private void setUp() {
        txt_more_settings = activity.findViewById(R.id.more_settings);
        recyclerView = (RecyclerView) activity.findViewById(R.id.settings_recycler_view);
        if(recyclerView!=null) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new SettingsAdapter(activity, lstItems, null);;
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(4,20));
        }

        txt_more_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.changeActivity(Activities.DESKTOP_MORE_SETTINGS.getCode(),null,null);
            }
        });
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
