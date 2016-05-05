package com.bitdubai.sub_app.wallet_manager.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResources;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.interfaces.WalletManagerModule;
import com.bitdubai.sub_app.wallet_manager.adapter.DesktopAdapter;
import com.bitdubai.sub_app.wallet_manager.commons.EmptyItem;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.OnStartDragListener;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.SimpleItemTouchHelperCallback;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by mati on 2016.03.09..
 */
public class DesktopSocialApssFragment extends AbstractDesktopFragment<DesktopSession,SubAppResources> implements SearchView.OnCloseListener,
        SearchView.OnQueryTextListener,
        SwipeRefreshLayout.OnRefreshListener,
        OnStartDragListener,
        DesktopHolderClickCallback<Item> {


    private ItemTouchHelper mItemTouchHelper;

    /**
     * MANAGERS
     */
    private  static ErrorManager errorManager;

    private SearchView mSearchView;

    private DesktopAdapter adapter;
    private boolean isStartList;

    // recycler
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private View rootView;

    private String searchName;
    private List<FermatApp> lstInstalledApps;


    ArrayList<Item> lstItems;

    private boolean started=false;
    private List<Item> lstItemsWithIcon;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static DesktopSocialApssFragment newInstance() {
        return new DesktopSocialApssFragment();
    }

    /**
     * Provisory method
     */
    @Deprecated
    public static DesktopSocialApssFragment newInstance(WalletManagerModule manager) {
        return new DesktopSocialApssFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            // setting up  module
            //desktopSession = ((DesktopSession) appSession);
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

            rootView = inflater.inflate(R.layout.desktop_main, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new DesktopAdapter(getActivity(), lstItems,this,DesktopAdapter.DEKSTOP);
            recyclerView.setAdapter(adapter);
            rootView.setBackgroundColor(Color.TRANSPARENT);

            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(recyclerView);

            //adapter.setFermatListEventListener(this);

        } catch(Exception ex) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            //         Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();

        }catch (OutOfMemoryError outOfMemoryError){
            outOfMemoryError.printStackTrace();
        }



        lstInstalledApps = new ArrayList<>();

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
    }

//    private void setUpData() {
//        if (appSession.getModuleManager() != null)
//            try {
//                lstInstalledApps = appSession.getModuleManager().getInstalledWallets();
//
//            } catch (WalletsListFailedToLoadException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        if(lstInstalledApps !=null)
//            for(InstalledWallet installedWallet: lstInstalledApps){
//                if(installedWallet.getWalletPublicKey().equals("reference_wallet")) {
//                    Item item = new Item(installedWallet);
//                    item.setIconResource(R.drawable.bitcoin_wallet);
//                    lstItems.add(item);
//                }
//
//            }
//
//        InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY,null,null,"intra_user_identity_sub_app","Intra user Identity","public_key_ccp_intra_user_identity","intra_user_identity_sub_app",new Version(1,0,0));
//        Item item = new Item(installedSubApp);
//        item.setIconResource(R.drawable.intra_user_identity);
//        lstItems.add(item);
//
//        installedSubApp = new InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY,null,null,"intra_user_community_sub_app","Intra user Community","public_key_intra_user_commmunity","intra_user_community_sub_app",new Version(1,0,0));
//        Item item1 = new Item(installedSubApp);
//        item1.setIconResource(R.drawable.intra_user_community);
//        lstItems.add(item1);
//    }


    @Override
    public void onRefresh() {
        if(!started) {
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
        if(!mSearchView.isActivated()){
            //adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));
        }

        return true;
    }



    private synchronized List<Item> getMoreData() {
        List<Item> dataSet = new ArrayList<>();

        try {
            lstItems = new ArrayList<>();

            //lstInstalledApps = appSession.getModuleManager().getInstalledWallets();
            lstItemsWithIcon = new ArrayList<>();
            Item[] arrItemsWithoutIcon = new Item[12];


//            for(InstalledWallet installedWallet: lstInstalledApps) {
//                if(installedWallet.getWalletPublicKey().equals("reference_wallet")) {
//                    Item item = new Item(installedWallet);
//                    item.setIconResource(R.drawable.bitcoin_wallet);
//                    item.setPosition(0);
//                    lstItemsWithIcon.add(item);
//                }
//            }
//            InstalledApp installedApp = new InstalledApp("Uber","uber_public_key",new Version(),R.drawable.fermat,0,0, AppsStatus.getDefaultStatus());
//
//            lstInstalledApps.add(installedApp);
//            Item item = new Item(installedApp);
//            item.setIconResource(R.drawable.fermat);
//            item.setPosition(0);
//            lstItemsWithIcon.add(item);
//
//            installedApp = new InstalledApp("Airbnb","Airbnb_public_key",new Version(),R.drawable.fermat,0,0, AppsStatus.getDefaultStatus());
//            lstInstalledApps.add(installedApp);
//            item = new Item(installedApp);
//            item.setIconResource(R.drawable.fermat);
//            item.setPosition(1);
//            lstItemsWithIcon.add(item);
//
//            installedApp = new InstalledApp("eBay","eBay_public_key",new Version(),R.drawable.fermat,0,0, AppsStatus.getDefaultStatus());
//            lstInstalledApps.add(installedApp);
//            item = new Item(installedApp);
//            item.setIconResource(R.drawable.fermat);
//            item.setPosition(2);
//            lstItemsWithIcon.add(item);
//
//            installedApp = new InstalledApp("Tinder","Tinder_public_key",new Version(),R.drawable.fermat,0,0, AppsStatus.getDefaultStatus());
//            lstInstalledApps.add(installedApp);
//            item = new Item(installedApp);
//            item.setIconResource(R.drawable.fermat);
//            item.setPosition(3);
//            lstItemsWithIcon.add(item);


            for(int i=0;i<12;i++){
                Item emptyItem = new Item(new EmptyItem(0,i));
                emptyItem.setIconResource(-1);
                arrItemsWithoutIcon[i] = emptyItem;
            }

            for(Item itemIcon: lstItemsWithIcon){
                arrItemsWithoutIcon[itemIcon.getPosition()]= itemIcon;
            }

            dataSet.addAll(Arrays.asList(arrItemsWithoutIcon));

        } catch (Exception e){
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

    }

    private void select(AppsStatus appsStatus){
        List<Item> list = new ArrayList<>();
        for (Item installedWallet : lstItemsWithIcon) {
            if(appsStatus.isAppStatusAvailable(((InstalledWallet) installedWallet.getInterfaceObject()).getAppStatus())){
                list.add(installedWallet);
            }
        }
        Item[] arrItemsWithoutIcon = new Item[12];
        for(int i=0;i<12;i++){
            Item emptyItem = new Item(new EmptyItem(0,i));
            emptyItem.setIconResource(-1);
            arrItemsWithoutIcon[i] = emptyItem;
        }

        for(Item itemIcon: list){
            arrItemsWithoutIcon[itemIcon.getPosition()]= itemIcon;
        }

        if(adapter!=null) {
            adapter.changeDataSet(Arrays.asList(arrItemsWithoutIcon));
            adapter.notifyDataSetChanged();
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
        AppsStatus appsStatus = AppsStatus.getByCode(code);
        switch (appsStatus){
            case RELEASE:
                break;
            case BETA:
                break;
            case ALPHA:
                break;
            case DEV:
                break;
        }

        select(appsStatus);
        super.onUpdateViewOnUIThread(code);
    }

}
