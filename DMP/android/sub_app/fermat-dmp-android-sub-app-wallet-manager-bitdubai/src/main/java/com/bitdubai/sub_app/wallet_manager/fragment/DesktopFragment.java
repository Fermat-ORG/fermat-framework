package com.bitdubai.sub_app.wallet_manager.fragment;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantGetUserWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.wallet_manager.adapter.DesktopAdapter;
import com.bitdubai.sub_app.wallet_manager.commons.EmptyItem;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.OnStartDragListener;
import com.bitdubai.sub_app.wallet_manager.commons.helpers.SimpleItemTouchHelperCallback;
import com.bitdubai.sub_app.wallet_manager.holder.DesktopHolderClickCallback;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;
import com.bitdubai.sub_app.wallet_manager.structure.Item;
import com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Matias Furszyfer on 15/09/15.
 */


public class DesktopFragment extends FermatFragment implements SearchView.OnCloseListener,
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
    //private ActorAdapter adapter;

    private View rootView;

    private String searchName;
    private List<InstalledWallet> lstInstalledWallet;
    private DesktopSession desktopSession;
    private WalletManager moduleManager;

    ArrayList<Item> lstItems;

    private boolean started=false;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static DesktopFragment newInstance() {
        return new DesktopFragment();
    }

    /**
     * Provisory method
     */
    @Deprecated
    public static DesktopFragment newInstance(WalletManager manager) {
        DesktopFragment desktopFragment = new DesktopFragment();
        desktopFragment.setModuleManager(manager);
        return desktopFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            // setting up  module
            //desktopSession = ((DesktopSession) subAppsSession);
            //moduleManager = desktopSession.getModuleManager();
            //errorManager = subAppsSession.getErrorManager();

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
            adapter = new DesktopAdapter(getActivity(), lstItems,this);
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

        }





        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRefresh();
    }

    private void setUpData() {
        if (moduleManager != null)
            try {
                lstInstalledWallet = moduleManager.getUserWallets();
            } catch (CantGetUserWalletException e) {
                e.printStackTrace();
            }
        if(lstInstalledWallet!=null)
            for(InstalledWallet installedWallet: lstInstalledWallet){
                Item item = new Item(installedWallet);
                item.setIconResource(R.drawable.bitcoin_wallet);
                lstItems.add(item);
            }

        InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY,null,null,"intra_user_identity_sub_app","Intra user Identity","public_key_ccp_intra_user_identity","intra_user_identity_sub_app",new Version(1,0,0));
        Item item = new Item(installedSubApp);
        item.setIconResource(R.drawable.intra_user);
        lstItems.add(item);

        installedSubApp = new InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY,null,null,"intra_user_community_sub_app","Intra user Community","public_key_intra_user_commmunity","intra_user_community_sub_app",new Version(1,0,0));
        Item item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.intra_user);
        lstItems.add(item1);
    }


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
        if(s.length()==0 && isStartList){
            //((IntraUserConnectionsAdapter)adapter).setAddButtonVisible(false);
            //adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));
            return true;
        }
        return false;
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

            lstInstalledWallet = moduleManager.getUserWallets();
            List<Item> lstItemsWithIcon = new ArrayList<>();
            Item[] arrItemsWithoutIcon = new Item[16];


            for(InstalledWallet installedWallet: lstInstalledWallet) {
                Item item = new Item(installedWallet);
                item.setIconResource(R.drawable.bitcoin_wallet);
                item.setPosition(12);
                lstItemsWithIcon.add(item);
            }


            InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY,null,null,"intra_user_identity_sub_app","Intra user Identity","public_key_ccp_intra_user_identity","intra_user_identity_sub_app",new Version(1,0,0));
            Item item2 = new Item(installedSubApp);
            item2.setIconResource(R.drawable.intra_user);
            item2.setPosition(5);
            lstItemsWithIcon.add(item2);

            installedSubApp = new InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY,null,null,"intra_user_community_sub_app","Intra user Community","public_key_intra_user_commmunity","intra_user_community_sub_app",new Version(1,0,0));
            Item item1 = new Item(installedSubApp);
            item1.setIconResource(R.drawable.intra_user);
            item1.setPosition(7);
            lstItemsWithIcon.add(item1);


            for(int i=0;i<16;i++){
                Item emptyItem = new Item(new EmptyItem(0,i));
                arrItemsWithoutIcon[i] = emptyItem;
            }

            for(Item item: lstItemsWithIcon){
                arrItemsWithoutIcon[item.getPosition()]= item;
            }

            dataSet.addAll(Arrays.asList(arrItemsWithoutIcon));

        } catch (CantGetUserWalletException e) {
                    e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return dataSet;
    }



    public void setModuleManager(WalletManager moduleManager) {
        this.moduleManager = moduleManager;
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
                    selectSubApp((InstalledSubApp) data.getInterfaceObject());
                    break;
                case WALLET:
                    selectWallet((InstalledWallet) data.getInterfaceObject());
                    break;
                case EMPTY:
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}




