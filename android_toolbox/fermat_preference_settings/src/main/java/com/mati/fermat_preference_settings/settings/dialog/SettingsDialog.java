package com.mati.fermat_preference_settings.settings.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.mati.fermat_preference_settings.R;
import com.mati.fermat_preference_settings.settings.interfaces.DialogCallback;
import com.mati.fermat_preference_settings.settings.models.PreferenceSettingsDialogItem;

import java.util.List;

/**
 * Created by mati on 2016.02.08..
 */
public class SettingsDialog extends Dialog implements FermatListItemListeners<PreferenceSettingsDialogItem>{


    private List<PreferenceSettingsDialogItem> options;
    RecyclerView recyclerView;
    ContextMenuAdapter contextMenuAdapter;
    private DialogCallback callBack;


    public SettingsDialog(Context context,DialogCallback dialogCallback,List<PreferenceSettingsDialogItem> options) {
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
        contextMenuAdapter = new ContextMenuAdapter(getContext(),options);
        contextMenuAdapter.setFermatListEventListener(this);
        recyclerView.setAdapter(contextMenuAdapter);
    }


    @Override
    public void onItemClickListener(PreferenceSettingsDialogItem data, int position) {
        callBack.optionSelected(data,position);
    }

    @Override
    public void onLongItemClickListener(PreferenceSettingsDialogItem data, int position) {

    }
}
