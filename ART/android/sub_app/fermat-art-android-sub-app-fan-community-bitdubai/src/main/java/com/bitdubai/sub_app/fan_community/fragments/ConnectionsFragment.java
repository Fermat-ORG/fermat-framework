package com.bitdubai.sub_app.fan_community.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.ArtCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantGetFanListException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySearch;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.fan_community.R;
import com.bitdubai.sub_app.fan_community.adapters.AppListAdapter;
import com.bitdubai.sub_app.fan_community.commons.popups.ConnectDialog;
import com.bitdubai.sub_app.fan_community.commons.utils.FermatAnimationUtils;
import com.bitdubai.sub_app.fan_community.sessions.FanCommunitySubAppSessionReferenceApp;
import com.bitdubai.sub_app.fan_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 14/04/16.
 */
public class ConnectionsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<FanCommunityModuleManager>,SubAppResourcesProviderManager> implements SearchView.OnCloseListener,
        SearchView.OnQueryTextListener,
        ActionBar.OnNavigationListener,
        AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<FanCommunityInformation> {

    private static final int MAX = 20;
    /**
     * MANAGERS
     */
    private static FanCommunityModuleManager moduleManager;
    private static ErrorManager errorManager;
    protected final String TAG = "Recycler Base";
    private List<FanCommunityInformation> artistCommunityInformationList;
    private int offset = 0;

    private int mNotificationsCount = 0;
    private SearchView mSearchView;

    private AppListAdapter adapter;
    private boolean isStartList;

    // recycler
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    //private ActorAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    // flags
    private boolean isRefreshing = false;
    private View rootView;

    private ProgressDialog dialog;
    private LinearLayout emptyView;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ConnectionsFragment newInstance() {
        return new ConnectionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setHasOptionsMenu(true);
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            mNotificationsCount = moduleManager.listFansPendingRemoteAction(moduleManager.getSelectedActorIdentity(), MAX, offset).size();

            // TODO: display unread notifications.
            // Run a task to fetch the notifications count
            new FetchCountTask().execute();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    /**
     * Fragment Class implementation.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {

            rootView = inflater.inflate(R.layout.afc_fragment_connections_world, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.afc_gridView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new AppListAdapter(getActivity(), artistCommunityInformationList);
            recyclerView.setAdapter(adapter);
            adapter.setFermatListEventListener(this);

            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.afc_swipe);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#000b12"));

            emptyView = (LinearLayout) rootView.findViewById(R.id.afc_empty_view);
            showEmpty(true, rootView);
            //onRefresh();

            if (dialog != null)
                dialog.dismiss();
            dialog = null;
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Loading connections");
            dialog.setMessage("Please wait...");
            dialog.show();
            FermatAnimationUtils.showView(false, null);
            FermatAnimationUtils.showEmpty(isAttached, null, artistCommunityInformationList);

            onRefresh();


        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }


        return rootView;
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
                    dialog.dismiss();
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity() != null && adapter != null) {
                            artistCommunityInformationList = (ArrayList<FanCommunityInformation>) result[0];
                            adapter.changeDataSet(artistCommunityInformationList);
                        }
                    }else
                        showEmpty(true, emptyView);

                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    isRefreshing = false;
                    dialog.dismiss();
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



//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        try {
//
//            CharSequence itemTitle = item.getTitle();
//
//            // Esto podria ser un enum de item menu que correspondan a otro menu
//            if (itemTitle.equals("New Identity")) {
//                changeActivity(Activities.CWP_INTRA_USER_CREATE_ACTIVITY.getCode(), appSession.getAppPublicKey());
//
//            }
//            if (item.getId() == R.id.action_notifications) {
//                changeActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_REQUEST.getCode(), appSession.getAppPublicKey());
//                return true;
//            }
//
//
//        } catch (Exception e) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
//            makeText(getActivity(), "Oooops! recovering from system error",
//                    LENGTH_LONG).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

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
    public boolean onQueryTextSubmit(String alias) {

        try {
            FanCommunitySearch fanaticSearch = moduleManager.getFanaticSearch();
            fanaticSearch.addAlias(alias);

        } catch(Exception e) {
            e.printStackTrace();
        }

        // This method does not exist
        mSearchView.onActionViewCollapsed();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        return s.length() == 0 && isStartList;
    }

    @Override
    public boolean onClose() {
        if (!mSearchView.isActivated()) {
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int position, long idItem) {

        try {
            FanCommunitySelectableIdentity selectableIdentity = moduleManager.listSelectableIdentities().get(position);
            selectableIdentity.select();
            return true;
        } catch (CantSelectIdentityException |CantListIdentitiesToSelectException e) {
            CommonLogger.exception(TAG, e.getMessage(), e);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
        }
        return false;
    }


    private synchronized List<ArtCommunityInformation> getMoreData() {
        List<ArtCommunityInformation> dataSet = new ArrayList<>();

        try {

            dataSet = moduleManager.listAllConnectedFans(moduleManager.getSelectedActorIdentity(), MAX, offset);
            offset = dataSet.size();
        } catch (CantGetSelectedActorIdentityException e) {
            CommonLogger.exception(TAG, e.getMessage(), e);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
        } catch (CantGetFanListException e) {
            CommonLogger.exception(TAG, e.getMessage(), e);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
        } catch (ActorIdentityNotSelectedException e) {
            CommonLogger.exception(TAG, e.getMessage(), e);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return dataSet;
    }

    @Override
    public void onItemClickListener(FanCommunityInformation data, int position) {

        try {
            ConnectDialog connectDialog = new ConnectDialog(getActivity(), appSession, appResourcesProviderManager, data, moduleManager.getSelectedActorIdentity());
            connectDialog.show();
        } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
            CommonLogger.exception(TAG, e.getMessage(), e);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, e);
        }

    }

    @Override
    public void onLongItemClickListener(FanCommunityInformation data, int position) {

    }

    public void showEmpty(boolean show, View emptyView) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show &&
                (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            if (adapter != null)
                adapter.changeDataSet(null);
        } else if (!show && emptyView.getVisibility() == View.VISIBLE) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.GONE);
        }
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
