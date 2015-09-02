package com.bitdubai.sub_app.intra_user.fragments;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.PaintActionBar;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantLoginIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantShowLoginIdentitiesException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserSearch;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.intra_user.adapters.CheckBoxListItem;
import com.bitdubai.sub_app.intra_user.adapters.ListAdapter;
import com.bitdubai.sub_app.intra_user.common.Views.Utils;
import com.bitdubai.sub_app.intra_user.common.adapters.IntraUserConnectionsAdapter;
import com.bitdubai.sub_app.intra_user.common.models.IntraUserConnectionListItem;
import com.bitdubai.sub_app.intra_user.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user.util.CommonLogger;
import com.intra_user.bitdubai.R;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Matias Furszyfer on 2015.08.31..
 */

public class ConnectionsListFragment extends FermatListFragment<IntraUserConnectionListItem> implements FermatListItemListeners<IntraUserConnectionListItem>, SearchView.OnQueryTextListener, SearchView.OnCloseListener, ActionBar.OnNavigationListener {

    IntraUserModuleManager intraUserModuleManager;
    private ErrorManager errorManager;
    private ArrayList<IntraUserConnectionListItem> intraUserItemList;

    private SearchView mSearchView;
    private boolean isStartList=false;
    private int mNotificationsCount=0;

    public static ConnectionsListFragment newInstance(){
        ConnectionsListFragment fragment = new ConnectionsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // setting up  module
            intraUserModuleManager = ((IntraUserSubAppSession) subAppsSession).getIntraUserModuleManager();
            errorManager = subAppsSession.getErrorManager();
            intraUserItemList = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
            isStartList = true;

//            System.out.println("ACAAAAAA");
//            System.out.println(System.currentTimeMillis());
           // paintCheckBoxInActionBar();
//            System.out.println(System.currentTimeMillis());
//            System.out.println("ACAAAAAA");

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.intra_user_menu, menu);

        //MenuItem menuItem = new SearchView(getActivity());

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setShowAsAction(searchItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnCloseListener(this);

        //MenuItem action_connection_request = menu.findItem(R.id.action_connection_request);
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(getActivity(), icon, mNotificationsCount);


//        List<String> lst = new ArrayList<String>();
//        lst.add("Matias");
//        lst.add("Work");
//        ArrayAdapter<String> itemsAdapter =
//                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lst);
//        MenuItem item = menu.findItem(R.id.spinner);
//        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
//
//        spinner.setAdapter(itemsAdapter); // set the adapter to provide layout of rows and content
//        //s.setOnItemSelectedListener(onItemSelectedListener); // set the listener, to perform actions based on item selection

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            CharSequence itemTitle = item.getTitle();

            // Esto podria ser un enum de item menu que correspondan a otro menu
            if(itemTitle.equals("New Identity")){
                ((FermatScreenSwapper)getActivity()).changeActivity(Activities.CWP_INTRA_USER_CREATE_ACTIVITY.getCode(),null);

            }
//            if(id == R.id.action_connection_request){
//                Toast.makeText(getActivity(),"Intra user request",Toast.LENGTH_SHORT).show();
//            }
            if (item.getItemId() == R.id.action_notifications) {
                // TODO: display unread notifications.
                // Run a task to fetch the notifications count
                new FetchCountTask().execute();
                return true;
            }


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    Updates the count of notifications in the ActionBar.
     */
    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        getActivity().invalidateOptionsMenu();
    }

    private void paintCheckBoxInActionBar(){

        try {
            List<IntraUserLoginIdentity> availableIdentities =intraUserModuleManager.showAvailableLoginIdentities();

            List<CheckBoxListItem> lstCheckBox = new ArrayList<CheckBoxListItem>();

            for(IntraUserLoginIdentity intraUserLoginIdentity: availableIdentities){
                lstCheckBox.add(new CheckBoxListItem(null,intraUserLoginIdentity));
            }

            ListAdapter listAdapter = new ListAdapter(getActivity(),R.layout.itemlistrow,lstCheckBox);


            ((PaintActionBar) getActivity()).paintComboBoxInActionBar(listAdapter, this);
        } catch (CantShowLoginIdentitiesException e) {
            e.printStackTrace();
        }

    }



    /**
     * Determine if this fragment use menu
     *
     * @return true if this fragment has menu, otherwise false
     */
    @Override
    protected boolean hasMenu() {
        return true;
    }

    /**
     * Get layout resource
     *
     * @return int layout resource Ex: R.layout.fragment_view
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.intra_user_conecction_list;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.connections_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    public ArrayList<IntraUserConnectionListItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<IntraUserConnectionListItem> data=null;

        try {
            List<IntraUserInformation> lstIntraUser = intraUserModuleManager.getAllIntraUsers();
            //List<WalletStoreCatalogueItem> catalogueItems = catalogue.getWalletCatalogue(0, 0);

            data = new ArrayList<>();
            for (IntraUserInformation intraUserInformation : lstIntraUser) {
                IntraUserConnectionListItem item = new IntraUserConnectionListItem(intraUserInformation.getName(),null,intraUserInformation.getProfileImage(),"connected");
                data.add(item);
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

            data = IntraUserConnectionListItem.getTestData(getResources());
        }
        data = IntraUserConnectionListItem.getTestData(getResources());

        return data;
    }

    /**
     * implement this function to handle the result object through dynamic array
     *
     * @param result array of native object (handle result field with result[0], result[1],... result[n]
     */
    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                intraUserItemList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(intraUserItemList);
            }
        }
    }

    /**
     * Implement this function to handle errors during the execution of any fermat worker instance
     *
     * @param ex Throwable object
     */
    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new IntraUserConnectionsAdapter(getActivity(), intraUserItemList);
            adapter.setFermatListEventListener(this); // setting up event listeners
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }


    /**
     * onItem click listener event
     *
     * @param data
     * @param position
     */
    @Override
    public void onItemClickListener(IntraUserConnectionListItem data, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hubo un problema");
        builder.setMessage("No se pudieron obtener los detalles de la wallet seleccionada");
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    /**
     * On Long item Click Listener
     *
     * @param data
     * @param position
     */
    @Override
    public void onLongItemClickListener(IntraUserConnectionListItem data, int position) {

    }

    @Override
    public boolean onQueryTextSubmit(String name) {

        IntraUserSearch intraUserSearch = intraUserModuleManager.searchIntraUser();
        intraUserSearch.setNameToSearch(name);
        //TODO: cuando esté el network service, esto va a descomentarse
//        try {
//            adapter.changeDataSet(intraUserSearch.getResult());
//
//        } catch (CantGetIntraUserSearchResult cantGetIntraUserSearchResult) {
//            cantGetIntraUserSearchResult.printStackTrace();
//        }


        ((IntraUserConnectionsAdapter)adapter).setAddButtonVisible(true);
        adapter.changeDataSet(IntraUserConnectionListItem.getTestDataExample(getResources()));

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //Toast.makeText(getActivity(), "Probando busqueda completa", Toast.LENGTH_SHORT).show();
        if(s.length()==0 && isStartList){
            ((IntraUserConnectionsAdapter)adapter).setAddButtonVisible(false);
            adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));
            return true;
        }
        return false;
    }

    @Override
    public boolean onClose() {
        if(!mSearchView.isActivated()){
            adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int position, long idItem) {
        try {
            IntraUserLoginIdentity intraUserLoginIdentity = intraUserModuleManager.showAvailableLoginIdentities().get(position);
            intraUserModuleManager.login(intraUserLoginIdentity.getPublicKey());
            //TODO: para despues
            //adapter.changeDataSet(intraUserModuleManager.getAllIntraUsers());

            //mientras tanto testeo
            adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));

            return true;
        } catch (CantShowLoginIdentitiesException e) {
            e.printStackTrace();
        } catch (CantLoginIntraUserException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    Sample AsyncTask to fetch the notifications count
    */
    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            mNotificationsCount+=1;
            return mNotificationsCount;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }
}
