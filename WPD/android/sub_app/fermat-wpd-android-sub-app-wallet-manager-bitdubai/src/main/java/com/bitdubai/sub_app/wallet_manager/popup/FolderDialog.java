package com.bitdubai.sub_app.wallet_manager.popup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SpacesItemDecoration;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.fermat_wpd.wallet_manager.R;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.wallet_manager.adapter.DesktopAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2015.11.27..
 */
public class FolderDialog extends FermatDialog<FermatSession,SubAppResourcesProviderManager>{


    private AbstractFermatFragment.ScreenSize screenSize;
    private List<Item> lstItems;
    private final DesktopHolderClickCallback desktopHolderClickCallback;
    private AppsStatus appStatus;
    private String title;
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
    public FolderDialog(Activity activity, FermatSession fermatSession, SubAppResourcesProviderManager resources,List<Item> lstItems,DesktopHolderClickCallback desktopHolderClickCallback) {
        super(activity, fermatSession, resources);
        this.lstItems = lstItems;
        this.desktopHolderClickCallback = desktopHolderClickCallback;
    }

    public FolderDialog(Activity activity, int themeResId, FermatSession fermatSession, SubAppResourcesProviderManager resources,String title,List<Item> lstItems, DesktopHolderClickCallback desktopHolderClickCallback,AppsStatus appsStatus,AbstractFermatFragment.ScreenSize screenSize) {
        super(activity, themeResId, fermatSession, resources);
        this.lstItems = lstItems;
        this.desktopHolderClickCallback = desktopHolderClickCallback;
        this.title = title;
        this.appStatus = appsStatus;
        this.screenSize = screenSize;
    }
    public FolderDialog(Activity activity, FermatSession fermatSession, SubAppResourcesProviderManager resources,String title,List<Item> lstItems, DesktopHolderClickCallback desktopHolderClickCallback,AppsStatus appsStatus,AbstractFermatFragment.ScreenSize screenSize) {
        super(activity, fermatSession, resources);
        this.lstItems = lstItems;
        this.desktopHolderClickCallback = desktopHolderClickCallback;
        this.title = title;
        this.appStatus = appsStatus;
        this.screenSize = screenSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.gridView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        cleanNotUsedItems();
        adapter = new DesktopAdapter(getActivity(), lstItems,desktopHolderClickCallback,DesktopAdapter.FOLDER,screenSize);
        recyclerView.setAdapter(adapter);
        int spacingInPixels = 20;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        FermatTextView txt_title = (FermatTextView) findViewById(R.id.txt_title);
        txt_title.setText(title);

    }

    private void cleanNotUsedItems(){
        List<Item> items = new ArrayList<>();
        for (Item lstItem : lstItems) {
            if(lstItem.getAppStatus()==appStatus){
                items.add(lstItem);
            }
        }
        lstItems = items;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.folder_main;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }
}
