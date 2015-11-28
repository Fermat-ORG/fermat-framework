package com.bitdubai.sub_app.wallet_manager.popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bitdubai.fermat_android_api.ui.Views.SpacesItemDecoration;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.wallet_manager.adapter.DesktopAdapter;
import com.bitdubai.sub_app.wallet_manager.holder.DesktopHolderClickCallback;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;
import com.bitdubai.sub_app.wallet_manager.structure.Item;

import java.util.List;

/**
 * Created by mati on 2015.11.27..
 */
public class FolderDialog extends FermatDialog<DesktopSession,SubAppResourcesProviderManager>{


    private final List<Item> lstItems;
    private final DesktopHolderClickCallback desktopHolderClickCallback;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private DesktopAdapter adapter;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public FolderDialog(Activity activity, DesktopSession fermatSession, SubAppResourcesProviderManager resources,List<Item> lstItems,DesktopHolderClickCallback desktopHolderClickCallback) {
        super(activity, fermatSession, resources);
        this.lstItems = lstItems;
        this.desktopHolderClickCallback = desktopHolderClickCallback;
    }

    public FolderDialog(Activity activity, int themeResId, DesktopSession fermatSession, SubAppResourcesProviderManager resources, List<Item> lstItems, DesktopHolderClickCallback desktopHolderClickCallback) {
        super(activity, themeResId, fermatSession, resources);
        this.lstItems = lstItems;
        this.desktopHolderClickCallback = desktopHolderClickCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.gridView);
//        if(lstItems.size()>3)
//        recyclerView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        else if (lstItems.size()<3){
//            recyclerView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        }

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DesktopAdapter(getActivity(), lstItems,desktopHolderClickCallback,DesktopAdapter.FOLDER);
        recyclerView.setAdapter(adapter);
        int spacingInPixels = 20;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

    }

    @Override
    protected int setLayoutId() {
        return R.layout.folder_main;
    }

    @Override
    protected int setWindowFeacture() {
        return Window.FEATURE_NO_TITLE;
    }
}
