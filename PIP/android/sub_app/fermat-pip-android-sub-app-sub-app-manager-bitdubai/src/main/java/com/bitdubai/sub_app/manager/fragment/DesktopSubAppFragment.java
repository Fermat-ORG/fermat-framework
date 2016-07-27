package com.bitdubai.sub_app.manager.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractDesktopFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.sub_app.manager.R;
import com.bitdubai.sub_app.manager.fragment.provisory_classes.InstalledSubApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.DesktopAdapter;
import commons.EmptyItem;
import commons.helpers.OnStartDragListener;
import commons.helpers.SimpleItemTouchHelperCallback;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Matias Furszyfer on 15/09/15.
 */


public class DesktopSubAppFragment extends AbstractDesktopFragment implements SearchView.OnCloseListener,
        SearchView.OnQueryTextListener,
        SwipeRefreshLayout.OnRefreshListener,
        OnStartDragListener,
        DesktopHolderClickCallback<Item> {

    private static final String TAG = "DesktopSubAppFragment";
    private ItemTouchHelper mItemTouchHelper;

    /**
     * MANAGERS
     */
    private static ErrorManager errorManager;

    private SearchView mSearchView;

    private DesktopAdapter adapter;
    private boolean isStartList;

    // recycler
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private View rootView;

    private String searchName;
    private List<InstalledWallet> lstInstalledWallet;
    //private SubAppManager moduleManager;

    ArrayList<Item> lstItems;
    List<Item> lstItemsWithIcon;

    private boolean started = false;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static DesktopSubAppFragment newInstance() {
        return new DesktopSubAppFragment();
    }

    /**
     * Provisory method
     */
    @Deprecated
    public static DesktopSubAppFragment newInstance(WalletManager manager) {
        DesktopSubAppFragment desktopFragment = new DesktopSubAppFragment();
        //desktopFragment.setModuleManager(manager);
        return desktopFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            // setting up  module
            //desktopSession = ((DesktopSessionReferenceApp) appSession);
            //moduleManager = desktopSession.getModuleManager();
            //errorManager = appSession.getErrorManager();

//            //get search name if
//            searchName = getFermatScreenSwapper().connectBetweenAppsData()[0].toString();
            lstItems = new ArrayList<>();

        } catch (Exception ex) {
            //errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }

    /**
     * Fragment Class implementation.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {

            rootView = inflater.inflate(R.layout.desktop_sub_app_1_main, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new DesktopAdapter(getActivity(), lstItems, this, DesktopAdapter.DEKSTOP);
            recyclerView.setAdapter(adapter);
            rootView.setBackgroundColor(Color.TRANSPARENT);

            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(recyclerView);
            //adapter.setFermatListEventListener(this);

        } catch (Exception ex) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            //         Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();

        } catch (OutOfMemoryError error) {
            System.gc();
            error.printStackTrace();
        }


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRefresh();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getPaintActivtyFeactures().addDesktopCallBack(this);
    }

    private void setUpData() {
//        if (moduleManager != null)
//            try {
//                lstInstalledWallet = moduleManager.getUserWallets();
//            } catch (CantGetUserWalletException e) {
//                e.printStackTrace();
//            }
        if (lstInstalledWallet != null)
            for (InstalledWallet installedWallet : lstInstalledWallet) {
                Item item = new Item(installedWallet);
//                item.setIconResource(R.drawable.bitcoin_wallet);
                lstItems.add(item);
            }

    }


    @Override
    public void onRefresh() {
        if (!started) {
            FermatWorker worker = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreData();
                }
            };
            worker.setContext(getActivity());
            worker.setCallBack(new FermatWorkerCallBack() {
                @SuppressWarnings("unchecked")
                @Override
                public void onPostExecute(Object... result) {
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity() != null && adapter != null) {
                            lstItems = (ArrayList<Item>) result[0];
                            adapter.changeDataSet(lstItems);
                        }
                    }
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            });
            worker.execute();
            started = true;
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

//        inflater.inflate(R.menu.intra_user_menu, menu);
//
//        //MenuItem menuItem = new SearchView(getActivity());
//        try {
//            MenuItem searchItem = menu.findItem(R.id.action_search);
//            searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
//            //MenuItemCompat.setShowAsAction(searchItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
//            //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//            mSearchView = (SearchView) searchItem.getActionView();
//            mSearchView.setOnQueryTextListener(this);
//            mSearchView.setSubmitButtonEnabled(true);
//            mSearchView.setOnCloseListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            CharSequence itemTitle = item.getTitle();

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String name) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //Toast.makeText(getActivity(), "Probando busqueda completa", Toast.LENGTH_SHORT).show();
        return s.length() == 0 && isStartList;
    }

    @Override
    public boolean onClose() {
        if (!mSearchView.isActivated()) {
            //adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));
        }

        return true;
    }


    private synchronized List<Item> getMoreData() {
        List<Item> dataSet = new ArrayList<>();

        try {
            lstItems = new ArrayList<>();

            //lstInstalledWallet = moduleManager.getUserWallets();
            lstItemsWithIcon = new ArrayList<>();
            Item[] arrItemsWithoutIcon = new Item[12];


//            InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.CWP_WALLET_FACTORY, null, null, "wallet_factory", "Wallet Factory", SubAppsPublicKeys.CWP_FACTORY.getCode(), "wallet_factory", new Version(1, 0, 0));
//            installedSubApp.setAppStatus(AppsStatus.DEV);
//            Item item = new Item(installedSubApp);
//            item.setIconResource(R.drawable.wallet_factory);
//            item.setPosition(0);
//            installedSubApp.setPlatforms(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION);
//            lstItemsWithIcon.add(item);
//            installedSubApp = new InstalledSubApp(SubApps.CWP_WALLET_PUBLISHER, null, null, "wallet_publisher", "Wallet Publisher", SubAppsPublicKeys.CWP_PUBLISHER.getCode(), "wallet_publisher", new Version(1, 0, 0));
//            installedSubApp.setAppStatus(AppsStatus.DEV);
//            item = new Item(installedSubApp);
//            item.setIconResource(R.drawable.wallet_publisher);
//            item.setPosition(1);
//            installedSubApp.setPlatforms(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION);
//            lstItemsWithIcon.add(item);
//            installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_FACTORY, null, null, "sub-app-asset-factory", "Asset Factory", SubAppsPublicKeys.DAP_FACTORY.getCode(), "sub-app-asset-factory", new Version(1, 0, 0));
//            installedSubApp.setAppStatus(AppsStatus.ALPHA);
//            item = new Item(installedSubApp);
//            item.setIconResource(R.drawable.asset_factory);
//            item.setPosition(2);
//            installedSubApp.setPlatforms(Platforms.DIGITAL_ASSET_PLATFORM);
//            lstItemsWithIcon.add(item);
            InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.CWP_DEVELOPER_APP, null, null, "developer_sub_app", "Developer Tools", SubAppsPublicKeys.PIP_DEVELOPER.getCode(), "developer_sub_app", new Version(1, 0, 0));
            installedSubApp.setAppStatus(AppsStatus.ALPHA);
            Item item = new Item(installedSubApp);
            item.setIconResource(R.drawable.developer);
            item.setPosition(3);
            installedSubApp.setPlatforms(Platforms.PLUG_INS_PLATFORM);
            lstItemsWithIcon.add(item);

            for (int i = 0; i < 12; i++) {
                Item emptyItem = new Item(new EmptyItem(0, i));
                arrItemsWithoutIcon[i] = emptyItem;
            }

//            for (Item itemIcon : lstItemsWithIcon) {
//                arrItemsWithoutIcon[itemIcon.getPosition()] = itemIcon;
//            }

            int pos = 0;
            for (int i = 0; i < lstItemsWithIcon.size(); i++) {
                Item itemIcon = lstItemsWithIcon.get(i);
                if (itemIcon.getAppStatus() == getFermatActivityManager().getAppStatus()) {
                    arrItemsWithoutIcon[pos] = itemIcon;
                    pos++;
                }
            }


            dataSet.addAll(Arrays.asList(arrItemsWithoutIcon));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSet;
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onHolderItemClickListener(Item data, int position) {
        try {
            switch (data.getType()) {
                case SUB_APP:
                    if (((InstalledSubApp) data.getInterfaceObject()).getSubAppType().equals(SubApps.Scanner)) {
                        Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
                    } else selectSubApp((InstalledSubApp) data.getInterfaceObject());
                    break;
                case WALLET:
                    selectWallet((InstalledWallet) data.getInterfaceObject());
                    break;
                case EMPTY:
                    break;
                case FOLDER:
//                    FolderDialog folderDialog = new FolderDialog(getActivity(),R.style.AppThemeDialog,desktopSession,null,data.getName(),((FermatFolder)data.getInterfaceObject()).getLstFolderItems(),this);
////                    folderDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
////                    folderDialog.getWindow().setFormat(PixelFormat.TRANSLUCENT);
////                    WindowManager.LayoutParams lp = folderDialog.getWindow().getAttributes();
////                    lp.dimAmount=0.0f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
////                    folderDialog.getWindow().setAttributes(lp);
//                    folderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    folderDialog.show();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void select(AppsStatus appsStatus) {
        try {
            if(isAttached) {
                List<Item> list = new ArrayList<>();
                if (lstItemsWithIcon != null)
                    for (Item installedWallet : lstItemsWithIcon) {
                        if (appsStatus.isAppStatusAvailable(installedWallet.getAppStatus())) {
                            list.add(installedWallet);
                        }

                    }
                else Log.e(TAG, "List of subApps null, please check this");
                Item[] arrItemsWithoutIcon = new Item[12];
                for (int i = 0; i < 12; i++) {
                    Item emptyItem = new Item(new EmptyItem(0, i));
                    emptyItem.setIconResource(-1);
                    arrItemsWithoutIcon[i] = emptyItem;
                }

                int j = 0;
                for (Item itemIcon : list) {
                    arrItemsWithoutIcon[j] = itemIcon;
                    j++;
                }

                if (recyclerView != null) {
                    if (recyclerView.getAdapter() != null) {
                        ((DesktopAdapter) recyclerView.getAdapter()).changeDataSet(Arrays.asList(arrItemsWithoutIcon));
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {

        adapter = null;
        mItemTouchHelper = null;
        super.onDestroy();
    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        try {
            AppsStatus appsStatus = AppsStatus.getByCode(code);
            switch (appsStatus) {
                case RELEASE:
                    return;
                case BETA:
                    return;
                case ALPHA:
                    break;
                case DEV:
                    break;
            }

            select(appsStatus);
            super.onUpdateViewOnUIThread(code);
        }catch (Exception e){
            Log.e(TAG,"Desktop. No olvidar borrar esto, furszy");
        }
    }
}




