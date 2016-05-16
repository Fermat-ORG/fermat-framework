package com.bitdubai.sub_app.intra_user_community.fragments;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraUserWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.adapters.AppListAdapter;
import com.bitdubai.sub_app.intra_user_community.common.popups.ErrorConnectingFermatNetworkDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.PresentationIntraUserCommunityDialog;
import com.bitdubai.sub_app.intra_user_community.constants.Constants;
import com.bitdubai.sub_app.intra_user_community.interfaces.ErrorConnectingFermatNetwork;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Matias Furszyfer on 15/09/15.
 * modified by Jose Manuel De Sousa Dos Santos on 08/12/2015
 */

public class ConnectionsWorldFragment extends AbstractFermatFragment implements
        AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<IntraUserInformation> {


    public static final String INTRA_USER_SELECTED = "intra_user";

    private static final int MAX = 10;
    /**
     * MANAGERS
     */
    private static IntraUserModuleManager moduleManager;
    private static ErrorManager errorManager;

    protected final String TAG = "Recycler Base";
    FermatWorker worker;
    IntraUserWalletSettings intraUserWalletSettings = null;
    private ErrorConnectingFermatNetwork errorConnectingFermatNetwork;
    private int offset = 0;
    private int mNotificationsCount = 0;
    private SearchView mSearchView;
    private AppListAdapter adapter;
    private boolean isStartList = false;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private View searchView;
    // flags
    private boolean isRefreshing = false;
    private View rootView;
    private IntraUserSubAppSession intraUserSubAppSession;
    private String searchName;
    private LinearLayout emptyView;
    private ArrayList<IntraUserInformation> lstIntraUserInformations = new ArrayList<>();
    private List<IntraUserInformation> dataSet = new ArrayList<>();
    private android.support.v7.widget.Toolbar toolbar;
    private EditText searchEditText;
    private List<IntraUserInformation> dataSetFiltered;
    private ImageView closeSearch;
    private LinearLayout searchEmptyView;
    private LinearLayout noNetworkView;
    private LinearLayout noFermatNetworkView;
    private Handler handler = new Handler();
    List<IntraUserInformation> userCacheList = new ArrayList<>();
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
            intraUserSubAppSession = ((IntraUserSubAppSession) appSession);
            moduleManager = intraUserSubAppSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            try {
                intraUserWalletSettings = intraUserSubAppSession.getModuleManager().loadAndGetSettings(intraUserSubAppSession.getAppPublicKey());
            } catch (Exception e) {
                intraUserWalletSettings = null;
            }

            if (intraUserSubAppSession.getAppPublicKey() != null) //the identity not exist yet
            {
                if (intraUserWalletSettings == null) {
                    intraUserWalletSettings = new IntraUserWalletSettings();
                    intraUserWalletSettings.setIsPresentationHelpEnabled(true);
                    intraUserSubAppSession.getModuleManager().persistSettings(intraUserSubAppSession.getAppPublicKey(), intraUserWalletSettings);
                }
            }

            mNotificationsCount = moduleManager.getIntraUsersWaitingYourAcceptanceCount();
            new FetchCountTask().execute();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }

    /**
     * Fragment Class implementation.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_connections_world, container, false);
            toolbar = getToolbar();
            toolbar.setTitle("Cripto wallet users");
            setUpScreen(inflater);
            searchView = inflater.inflate(R.layout.search_edit_text, null);
            setUpReferences();
            switch (getFermatNetworkStatus()) {
                case CONNECTED:
                   // setUpReferences();
                    break;
                case DISCONNECTED:
                    showErrorFermatNetworkDialog();
                    break;
            }

        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    public void setUpReferences() {
        dataSet = new ArrayList<>();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    worker.shutdownNow();
                    return true;
                }
                return false;
            }
        });
        searchEditText = (EditText) searchView.findViewById(R.id.search);
        closeSearch = (ImageView) searchView.findViewById(R.id.close_search);
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
        emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
        searchEmptyView = (LinearLayout) rootView.findViewById(R.id.search_empty_view);
        noNetworkView = (LinearLayout) rootView.findViewById(R.id.no_connection_view);
        noFermatNetworkView = (LinearLayout) rootView.findViewById(R.id.no_fermat_connection_view);

            userCacheList = getSuggestionCache();
            if(userCacheList!=null) {
                dataSet.addAll(userCacheList);
            }

        if (intraUserWalletSettings.isPresentationHelpEnabled()) {
            showDialogHelp();
        } else {
            showCriptoUsersCache();
        }
    }

    public void showErrorNetworkDialog() {
        ErrorConnectingFermatNetworkDialog errorConnectingFermatNetworkDialog = new ErrorConnectingFermatNetworkDialog(getActivity(), intraUserSubAppSession, null);
        errorConnectingFermatNetworkDialog.setDescription("You are not connected  \n to the Fermat Network");
        errorConnectingFermatNetworkDialog.setRightButton("Connect", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        errorConnectingFermatNetworkDialog.setLeftButton("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        errorConnectingFermatNetworkDialog.show();
    }

    public void showErrorFermatNetworkDialog() {
        final ErrorConnectingFermatNetworkDialog errorConnectingFermatNetworkDialog = new ErrorConnectingFermatNetworkDialog(getActivity(), intraUserSubAppSession, null);
        errorConnectingFermatNetworkDialog.setDescription("The access to the Fermat Network is disabled.");
        errorConnectingFermatNetworkDialog.setRightButton("Enable", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorConnectingFermatNetworkDialog.dismiss();
                try {
                    if (getFermatNetworkStatus() == NetworkStatus.DISCONNECTED) {
                        Toast.makeText(getActivity(), "Wait a minute please, trying to reconnect...", Toast.LENGTH_SHORT).show();
                        //getActivity().onBackPressed();
                    }
                } catch (CantGetCommunicationNetworkStatusException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        errorConnectingFermatNetworkDialog.setLeftButton("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorConnectingFermatNetworkDialog.dismiss();
            }
        });
        errorConnectingFermatNetworkDialog.show();
    }

    @Override
    public void onRefresh() {

        offset = 0;
        if (!isRefreshing) {
            isRefreshing = true;
            worker = new FermatWorker() {
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
                            if (lstIntraUserInformations.isEmpty()) {
                                try {
                                    List list = moduleManager.getCacheSuggestionsToContact(MAX, offset);
                                    if(list!=null) {
                                        if (!list.isEmpty()) {
                                            lstIntraUserInformations.addAll(moduleManager.getCacheSuggestionsToContact(MAX, offset));
                                            showEmpty(false, emptyView);
                                            showEmpty(false, searchEmptyView);
                                        } else {
                                            showEmpty(true, emptyView);
                                            showEmpty(false, searchEmptyView);
                                        }
                                    }
                                } catch (CantGetIntraUsersListException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                showEmpty(false, emptyView);
                                showEmpty(false, searchEmptyView);
                            }
                        }
                    } else {
                        try {
                            showEmpty(false, emptyView);
                            showEmpty(false, searchEmptyView);
                            lstIntraUserInformations.addAll(moduleManager.getCacheSuggestionsToContact(MAX, offset));
                        } catch (CantGetIntraUsersListException e) {
                            e.printStackTrace();
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
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cripto_users_menu, menu);

        try {
            final MenuItem searchItem = menu.findItem(R.id.action_search);
            menu.findItem(R.id.action_help).setVisible(true);
            menu.findItem(R.id.action_search).setVisible(true);
            searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    menu.findItem(R.id.action_help).setVisible(false);
                    menu.findItem(R.id.action_search).setVisible(false);
                    toolbar = getToolbar();
                    toolbar.setTitle("");
                    toolbar.addView(searchView);
                    if (closeSearch != null)
                        closeSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                menu.findItem(R.id.action_help).setVisible(true);
                                menu.findItem(R.id.action_search).setVisible(true);
                                toolbar = getToolbar();
                                toolbar.removeView(searchView);
                                toolbar.setTitle("Cripto wallet users");
                                onRefresh();
                            }
                        });

                    if (searchEditText != null) {
                        searchEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                                if (s.length() > 0) {
                                    worker = new FermatWorker() {
                                        @Override
                                        protected Object doInBackground() throws Exception {
                                            return getQueryData(s);
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
                                                    dataSetFiltered = (ArrayList<IntraUserInformation>) result[0];
                                                    adapter.changeDataSet(dataSetFiltered);
                                                    if (dataSetFiltered != null) {
                                                        if (dataSetFiltered.isEmpty()) {
                                                            showEmpty(true, searchEmptyView);
                                                            showEmpty(false, emptyView);

                                                        } else {
                                                            showEmpty(false, searchEmptyView);
                                                            showEmpty(false, emptyView);
                                                        }
                                                    } else {
                                                        showEmpty(true, searchEmptyView);
                                                        showEmpty(false, emptyView);
                                                    }
                                                }
                                            } else {
                                                showEmpty(true, searchEmptyView);
                                                showEmpty(false, emptyView);
                                            }
                                        }

                                        @Override
                                        public void onErrorOccurred(Exception ex) {
                                            isRefreshing = false;
                                            if (swipeRefresh != null)
                                                swipeRefresh.setRefreshing(false);
                                            showEmpty(true, searchEmptyView);
                                            if (getActivity() != null)
                                                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                            ex.printStackTrace();

                                        }
                                    });
                                    worker.execute();
                                } else {
                                    menu.findItem(R.id.action_help).setVisible(true);
                                    menu.findItem(R.id.action_search).setVisible(true);
                                    toolbar = getToolbar();
                                    toolbar.removeView(searchView);
                                    toolbar.setTitle("Cripto wallet users");
                                    onRefresh();
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                    return false;
                }
            });

        } catch (Exception e) {

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == R.id.action_help)
                showDialogHelp();

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;
        if(getActivity()!=null) {
            getActivity().invalidateOptionsMenu();
        }else{
            Log.e(TAG,"updateNotificationsBadge activity null, please check this, class"+getClass().getName()+" line: "+new Throwable().getStackTrace()[0].getLineNumber());
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private synchronized List<IntraUserInformation> getQueryData(final CharSequence charSequence) {
        if (dataSet != null && !dataSet.isEmpty()) {
            if (searchEditText != null && !searchEditText.getText().toString().isEmpty()) {
                //noinspection unchecked
                /*dataSetFiltered = (List<IntraUserInformation>) CollectionUtils.find(dataSet, new org.apache.commons.collections.Predicate() {
                    @Override
                    public boolean evaluate(Object object) {
                        IntraUserInformation intraUserInformation = (IntraUserInformation) object;
                        return intraUserInformation.getName().toLowerCase().contains(charSequence);
                    }
                });*/


                dataSetFiltered = new ArrayList<IntraUserInformation>();
                for (IntraUserInformation intraUser : dataSet) {

                    if(intraUser.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        dataSetFiltered.add(intraUser);

                }


            } else {
                dataSetFiltered = null;
            }
        }
        return dataSetFiltered;
    }


    private synchronized List<IntraUserInformation> getMoreData() {
        List<IntraUserInformation> dataSet = new ArrayList<>();

         try {


            List<IntraUserInformation> userList = moduleManager.getSuggestionsToContact(MAX, offset);
            dataSet.addAll(userList);

        } catch (CantGetIntraUsersListException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSet;
    }


    private List<IntraUserInformation> getSuggestionCache() {

        List<IntraUserInformation> userCacheList = new ArrayList<>();
        try {

            userCacheList = moduleManager.getCacheSuggestionsToContact(MAX, offset);
            return userCacheList;

        } catch (CantGetIntraUsersListException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userCacheList;
    }

    @Override
    public void onItemClickListener(IntraUserInformation data, int position) {
        try {
            if (moduleManager.getActiveIntraUserIdentity() != null) {
                if (!moduleManager.getActiveIntraUserIdentity().getPublicKey().isEmpty())
                    appSession.setData(INTRA_USER_SELECTED, data);
                changeActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(), appSession.getAppPublicKey());
            }
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLongItemClickListener(IntraUserInformation data, int position) {

    }


    private void showDialogHelp() {
        try {
            if (moduleManager.getActiveIntraUserIdentity() != null) {
                if (!moduleManager.getActiveIntraUserIdentity().getPublicKey().isEmpty()) {
                    PresentationIntraUserCommunityDialog presentationIntraUserCommunityDialog = new PresentationIntraUserCommunityDialog(getActivity(),
                            intraUserSubAppSession,
                            null,
                            moduleManager,
                            PresentationIntraUserCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES);
                    presentationIntraUserCommunityDialog.show();
                    presentationIntraUserCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            showCriptoUsersCache();
                        }
                    });
                } else {
                    PresentationIntraUserCommunityDialog presentationIntraUserCommunityDialog = new PresentationIntraUserCommunityDialog(getActivity(),
                            intraUserSubAppSession,
                            null,
                            moduleManager,
                            PresentationIntraUserCommunityDialog.TYPE_PRESENTATION);
                    presentationIntraUserCommunityDialog.show();
                    presentationIntraUserCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Boolean isBackPressed = (Boolean) intraUserSubAppSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
                            if (isBackPressed != null) {
                                if (isBackPressed) {
                                    getActivity().finish();
                                }
                            } else {
                                showCriptoUsersCache();
                                invalidate();
                            }
                        }
                    });
                }
            } else {
                PresentationIntraUserCommunityDialog presentationIntraUserCommunityDialog = new PresentationIntraUserCommunityDialog(getActivity(),
                        intraUserSubAppSession,
                        null,
                        moduleManager,
                        PresentationIntraUserCommunityDialog.TYPE_PRESENTATION);
                presentationIntraUserCommunityDialog.show();
                presentationIntraUserCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Boolean isBackPressed = null;
                        try {
                            isBackPressed = (Boolean) intraUserSubAppSession.getData(Constants.PRESENTATION_DIALOG_DISMISS,Boolean.TRUE);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        if (isBackPressed != null) {
                            if (isBackPressed) {
                                getActivity().onBackPressed();
                            }
                        } else
                            showCriptoUsersCache();
                    }
                });
            }
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
    }

    private void showCriptoUsersCache() {

        IntraUserModuleManager moduleManager = intraUserSubAppSession.getModuleManager();
        if(moduleManager==null){
            getActivity().onBackPressed();
        }else{
            invalidate();
        }if (dataSet.isEmpty()) {
            showEmpty(true, emptyView);
            swipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefresh.setRefreshing(true);
                    onRefresh();
                }

            });
        } else {
            adapter.changeDataSet(dataSet);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            }, 1500);
        }
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

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {

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

    @Override
    public void onUpdateViewOnUIThread(String code){
        try
        {
            //update intra user list
            if(code.equals("ACCEPTED_CONEXION"))
              onRefresh();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}




