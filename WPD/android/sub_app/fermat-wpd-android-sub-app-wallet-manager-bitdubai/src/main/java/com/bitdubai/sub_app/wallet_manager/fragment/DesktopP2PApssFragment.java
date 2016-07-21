package com.bitdubai.sub_app.wallet_manager.fragment;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractDesktopFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.ComboAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.InstalledApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_wpd.wallet_manager.R;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.interfaces.WalletManagerModule;
import com.bitdubai.sub_app.wallet_manager.adapter.DesktopAdapter;
import com.bitdubai.sub_app.wallet_manager.commons.EmptyItem;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.OnStartDragListener;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.SimpleItemTouchHelperCallback;
import com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by mati on 2016.03.09..
 */
public class DesktopP2PApssFragment extends AbstractDesktopFragment<ReferenceAppFermatSession<WalletManager>, ResourceProviderManager> implements SearchView.OnCloseListener,
        SearchView.OnQueryTextListener,
        SwipeRefreshLayout.OnRefreshListener,
        OnStartDragListener,
        DesktopHolderClickCallback<Item> {


    private static final String TAG = "DesktopP2PFragment";
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
    public static DesktopP2PApssFragment newInstance() {
        return new DesktopP2PApssFragment();
    }

    /**
     * Provisory method
     */
    @Deprecated
    public static DesktopP2PApssFragment newInstance(WalletManagerModule manager) {
        return new DesktopP2PApssFragment();
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

            rootView = inflater.inflate(R.layout.desktop_p2p_main, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new DesktopAdapter(getActivity(), lstItems,this,DesktopAdapter.DEKSTOP,getScreenSize());
            recyclerView.setAdapter(adapter);
            rootView.setBackgroundColor(Color.TRANSPARENT);

            ((ImageView)rootView.findViewById(R.id.container_title)).setImageResource(R.drawable.title_p2p);


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
            InstalledApp installedApp = new InstalledApp("Dating","tinder_public_key",new Version(),R.drawable.datting,0,0, AppsStatus.DEV,null);

            lstInstalledApps.add(installedApp);
            Item item = new Item(installedApp);
            item.setIconResource(R.drawable.datting);
            item.setPosition(0);
            lstItemsWithIcon.add(item);

            installedApp = new InstalledApp("Jobs","Airbnb_public_key",new Version(),R.drawable.jobs,0,0,  AppsStatus.DEV,null);
            lstInstalledApps.add(installedApp);
            item = new Item(installedApp);
            item.setIconResource(R.drawable.jobs);
            item.setPosition(1);
            lstItemsWithIcon.add(item);

            installedApp = new InstalledApp("Market Place","eBay_public_key",new Version(),R.drawable.market_place_icon,0,0,  AppsStatus.DEV,null);
            lstInstalledApps.add(installedApp);
            item = new Item(installedApp);
            item.setIconResource(R.drawable.market_place_icon);
            item.setPosition(2);
            lstItemsWithIcon.add(item);

            installedApp = new InstalledApp("Household","mercado_libre_public_key",new Version(),R.drawable.house_hold,0,0,  AppsStatus.DEV,null);
            lstInstalledApps.add(installedApp);
            item = new Item(installedApp);
            item.setIconResource(R.drawable.house_hold);
            item.setPosition(3);
            lstItemsWithIcon.add(item);

            InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.CHT_CHAT, null, null, "chat_sub_app", "Chat", SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), "chat_sub_app", new Version(1, 0, 0), Platforms.CHAT_PLATFORM, AppsStatus.ALPHA);
            installedSubApp.setAppStatus(AppsStatus.ALPHA);
            item = new Item(installedSubApp);
            item.setIconResource(R.drawable.chat_subapp);
            item.setPosition(4);
            lstItemsWithIcon.add(item);

            installedSubApp = new InstalledSubApp(SubApps.CHT_CHAT, null, null, "chat_sub_app", "Combo Chat", ComboAppsPublicKeys.CHT_IDENTITY_COMMUNITY.getCode(), "chat_sub_app", new Version(1, 0, 0), Platforms.CHAT_PLATFORM, AppsStatus.ALPHA);
            installedSubApp.setAppStatus(AppsStatus.ALPHA);
            item = new Item(installedSubApp);
            item.setIconResource(R.drawable.communities_icon);
            item.setPosition(5);
            lstItemsWithIcon.add(item);

//            installedSubApp = new InstalledSubApp(SubApps.ART_MUSIC_PLAYER, null, null, "music_player_sub_app", "Music Player", SubAppsPublicKeys.ART_MUSIC_PLAYER.getCode(), "music_player_sub_app", new Version(1, 0, 0), Platforms.ART_PLATFORM, AppsStatus.DEV);
//            installedSubApp.setAppStatus(AppsStatus.ALPHA);
//            item = new Item(installedSubApp);
//            item.setIconResource(R.drawable.subapp_art_music_player);
//            item.setPosition(6);
//            lstItemsWithIcon.add(item);


            for(int i=0;i<12;i++){
                Item emptyItem = new Item(new EmptyItem(0,i));
                emptyItem.setIconResource(-1);
                arrItemsWithoutIcon[i] = emptyItem;
            }

//            for(Item itemIcon: lstItemsWithIcon){
//                arrItemsWithoutIcon[itemIcon.getPosition()]= itemIcon;
//            }

            int pos = 0;
            for(int i = 0;i<lstItemsWithIcon.size();i++){
                Item itemIcon = lstItemsWithIcon.get(i);
                if((itemIcon.getAppStatus() == getFermatActivityManager().getAppStatus())){
                    arrItemsWithoutIcon[pos]= itemIcon;
                    pos++;
                }
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
        //TODO: I put this here to test our Music player sub app. Manuel
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
        //TODO: End of experimental code. Manuel.
    }


    private void select(AppsStatus appsStatus){
        try {
            List<Item> list = new ArrayList<>();
            for (Item installedWallet : lstItemsWithIcon) {
                if(installedWallet.getAppStatus()!=null) {
                    if(appsStatus.isAppStatusAvailable(installedWallet.getAppStatus())) {
                        list.add(installedWallet);
                    }
                }
            }
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

            if (recyclerView.getAdapter() != null) {
                ((DesktopAdapter) recyclerView.getAdapter()).changeDataSet(Arrays.asList(arrItemsWithoutIcon));
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }catch (Exception e){
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
            Log.e(TAG,"Desktop. no olvidar borrar esto. furszy");
        }
    }

}
