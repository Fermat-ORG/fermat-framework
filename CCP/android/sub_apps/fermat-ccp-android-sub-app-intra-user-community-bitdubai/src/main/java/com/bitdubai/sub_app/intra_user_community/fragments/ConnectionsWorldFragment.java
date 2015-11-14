package com.bitdubai.sub_app.intra_user_community.fragments;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.SearchView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantLoginIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantShowLoginIdentitiesException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserSearch;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.intra_user_community.adapters.AppListAdapter;
import com.bitdubai.sub_app.intra_user_community.common.Views.Utils;
import com.bitdubai.sub_app.intra_user_community.common.navigation_drawer.NavigationViewAdapter;
import com.bitdubai.sub_app.intra_user_community.common.popups.ConnectDialog;
import com.bitdubai.sub_app.intra_user_community.common.utils.FragmentsCommons;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;
import com.bitdubai.sub_app.intra_user_community.R;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Matias Furszyfer on 15/09/15.
 */


public class ConnectionsWorldFragment  extends FermatFragment implements SearchView.OnCloseListener,
        SearchView.OnQueryTextListener,
        ActionBar.OnNavigationListener,
        AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<IntraUserInformation> {



    /**
     * MANAGERS
     */
    private  static IntraUserModuleManager moduleManager;
    private  static ErrorManager errorManager;

    private List<IntraUserInformation> lstIntraUserInformations;


    protected final String TAG = "Recycler Base";

    private static final int MAX = 20;

    private int offset = 0;

    private int mNotificationsCount=0;
    private SearchView mSearchView;

    private AppListAdapter adapter;
    private boolean isStartList;


    private ProgressDialog mDialog;


    // recycler
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    //private ActorAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    // flags
    private boolean isRefreshing = false;
    private View rootView;
    private IntraUserSubAppSession intraUserSubAppSession;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ConnectionsWorldFragment newInstance() {
        return new ConnectionsWorldFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setHasOptionsMenu(true);
            // setting up  module
            intraUserSubAppSession = ((IntraUserSubAppSession) subAppsSession);
            moduleManager = intraUserSubAppSession.getIntraUserModuleManager();
            errorManager = subAppsSession.getErrorManager();

            mNotificationsCount = moduleManager.getIntraUsersWaitingYourAcceptanceCount();

            // TODO: display unread notifications.
            // Run a task to fetch the notifications count
            new FetchCountTask().execute();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,UnexpectedUIExceptionSeverity.CRASH,ex);
        }
    }

    /**
     * Fragment Class implementation.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {

            rootView = inflater.inflate(R.layout.world_main, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new AppListAdapter(getActivity(), lstIntraUserInformations);
            recyclerView.setAdapter(adapter);
            adapter.setFermatListEventListener(this);

            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#000b12"));


            onRefresh();

        } catch(Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }





        return rootView;
    }
    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
        /**
         * add navigation header
         */
        addNavigationHeader(FragmentsCommons.setUpHeaderScreen(layoutInflater, getActivity(), intraUserSubAppSession.getIntraUserModuleManager().getActiveIntraUserIdentity()));

        /**
         * Navigation view items
         */
        NavigationViewAdapter navigationViewAdapter = new NavigationViewAdapter(getActivity(),null);
        setNavigationDrawer(navigationViewAdapter);
    }
    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
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
                    isRefreshing = false;
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity() != null && adapter != null) {
                            lstIntraUserInformations = (ArrayList<IntraUserInformation>) result[0];
                            adapter.changeDataSet(lstIntraUserInformations);
                        }
                    }
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    isRefreshing = false;
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            });
            worker.execute();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.intra_user_menu, menu);

        //MenuItem menuItem = new SearchView(getActivity());

//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        MenuItemCompat.setShowAsAction(searchItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
//        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        mSearchView.setOnQueryTextListener(this);
//        mSearchView.setSubmitButtonEnabled(true);
//        mSearchView.setOnCloseListener(this);



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
                changeActivity(Activities.CWP_INTRA_USER_CREATE_ACTIVITY.getCode());

            }
//            if(id == R.id.action_connection_request){
//                Toast.makeText(getActivity(),"Intra user request",Toast.LENGTH_SHORT).show();
//            }
            if (item.getItemId() == R.id.action_notifications) {
                changeActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_REQUEST.getCode());
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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onQueryTextSubmit(String name) {
        //swipeRefreshLayout.setRefreshing(true);
        IntraUserSearch intraUserSearch = moduleManager.searchIntraUser();
        intraUserSearch.setNameToSearch(name);

        // This method does not exist
        mSearchView.onActionViewCollapsed();
        //TODO: cuando esté el network service, esto va a descomentarse
//        try {
//            adapter.changeDataSet(intraUserSearch.getResult());
//
//        } catch (CantGetIntraUserSearchResult cantGetIntraUserSearchResult) {
//            cantGetIntraUserSearchResult.printStackTrace();
//        }


        //adapter.setAddButtonVisible(true);
        //adapter.changeDataSet(IntraUserConnectionListItem.getTestDataExample(getResources()));
        //swipeRefreshLayout.setRefreshing(false);

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

    @Override
    public boolean onNavigationItemSelected(int position, long idItem) {
        try {
            IntraUserLoginIdentity intraUserLoginIdentity = moduleManager.showAvailableLoginIdentities().get(position);
            moduleManager.login(intraUserLoginIdentity.getPublicKey());
            //TODO: para despues
            //adapter.changeDataSet(intraUserModuleManager.getAllIntraUsers());

            //mientras tanto testeo
            //adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));

            return true;
        } catch (CantShowLoginIdentitiesException e) {
            e.printStackTrace();
        } catch (CantLoginIntraUserException e) {
            e.printStackTrace();
        }
        return false;
    }




    private synchronized List<IntraUserInformation> getMoreData() {
        List<IntraUserInformation> dataSet = new ArrayList<>();

        try {

            dataSet.addAll( moduleManager.getSuggestionsToContact(MAX,offset));
            offset = dataSet.size();

        } catch (CantGetIntraUsersListException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return dataSet;
    }

    @Override
    public void onItemClickListener(IntraUserInformation data, int position) {
        ConnectDialog connectDialog = null;
        try {
            connectDialog = new ConnectDialog(getActivity(),(IntraUserSubAppSession)subAppsSession,subAppResourcesProviderManager,data,moduleManager.getActiveIntraUserIdentity());
            connectDialog.show();
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLongItemClickListener(IntraUserInformation data, int position) {

    }


    /*
    Sample AsyncTask to fetch the notifications count
    */
    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            return mNotificationsCount;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }




}




