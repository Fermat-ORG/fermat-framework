package com.mati.fermat_preference_settings.settings.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.mati.fermat_preference_settings.R;

import java.util.List;

/**
 * Created by mati on 2016.02.08..
 */
public class SettingsDialog extends Dialog implements FermatListItemListeners<String>{


    private List<String> options;
    RecyclerView recyclerView;
    ContextMenuAdapter contextMenuAdapter;


    public SettingsDialog(Context context,List<String> options) {
        super(context);
        this.options = options;
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
        recyclerView.setAdapter(contextMenuAdapter);
    }


    @Override
    public void onItemClickListener(String data, int position) {
        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongItemClickListener(String data, int position) {

    }
}
