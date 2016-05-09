package com.mati.fermat_preference_settings.drawer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.utils.FermatScreenCalculator;
import com.mati.fermat_preference_settings.R;
import com.mati.fermat_preference_settings.drawer.interfaces.DialogCallback;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsTextPlusRadioItem;

import java.util.List;

/**
 * Created by mati on 2016.02.08..
 */
public class SettingsDialog extends Dialog implements FermatListItemListeners<PreferenceSettingsTextPlusRadioItem>{


    private List<PreferenceSettingsTextPlusRadioItem> options;
    RecyclerView recyclerView;
    ContextMenuAdapter contextMenuAdapter;
    private DialogCallback callBack;


    public SettingsDialog(Context context,DialogCallback dialogCallback,List<PreferenceSettingsTextPlusRadioItem> options) {
        super(context);
        this.options = options;
        this.callBack = dialogCallback;
    }

    public SettingsDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SettingsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings_open_dialog);
        recyclerView = (RecyclerView) findViewById(R.id.conext_menu_recicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        contextMenuAdapter = new ContextMenuAdapter(getContext(),this.callBack,options);
        contextMenuAdapter.setFermatListEventListener(this);
        recyclerView.setAdapter(contextMenuAdapter);
        recyclerView.getLayoutParams().height = getDps(options.size());
        //recyclerView.addItemDecoration(new FermatDividerItemDecoration(getContext()));
    }


    @Override
    public void onItemClickListener(PreferenceSettingsTextPlusRadioItem data, int position) {
        callBack.optionSelected(data,position);


    }

    @Override
    public void onLongItemClickListener(PreferenceSettingsTextPlusRadioItem data, int position) {

    }


    //TODO: esto tiene que ser otra clase

    private final float dps_min = 120;
    private final float dps_medium = 200;
    private final float dps_large = 280;


    private int getDps(int optionsSize){
        float dps = dps_large;
        if(optionsSize>0 && optionsSize<2){
            dps = dps_min;
        }else if(optionsSize>2 && optionsSize<4){
            dps = dps_medium;
        } else if(optionsSize>4){
            dps = dps_large;
        }
        return  FermatScreenCalculator.getPx(getContext(),dps);

    }

}
