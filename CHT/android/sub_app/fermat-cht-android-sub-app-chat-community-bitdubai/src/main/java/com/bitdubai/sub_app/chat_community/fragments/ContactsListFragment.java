package com.bitdubai.sub_app.chat_community.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.adapters.ContactsListAdapter;
import com.bitdubai.sub_app.chat_community.common.popups.DisconnectDialog;
import com.bitdubai.sub_app.chat_community.common.popups.PresentationChatCommunityDialog;
import com.bitdubai.sub_app.chat_community.constants.Constants;
import com.bitdubai.sub_app.chat_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * ContactsListFragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ContactsListFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager>, SubAppResourcesProviderManager>
        implements SwipeRefreshLayout.OnRefreshListener,
        FermatListItemListeners<ChatActorCommunityInformation> {

    //Managers
    private ChatActorCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatActorCommunitySettings> settingsManager;
    public static final String CHAT_USER_SELECTED = "chat_user";
    private static final int MAX = 1000;
    protected final String TAG = "ContactsListFragment";
    private int offset = 0;
    private String cityAddress;
    private String stateAddress;
    private String countryAddress;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout empty;
    private ExecutorService executor;
    private View rootView;
    private SearchView searchView;
    private ContactsListAdapter adapter;
    private LinearLayout emptyView;
    private ArrayList<ChatActorCommunityInformation> lstChatUserInformations;
    private ChatActorCommunitySelectableIdentity identity;
    private ChatActorCommunitySettings appSettings;
    TextView noDatalabel;
    FermatApplicationCaller applicationsHelper;
    ImageView noData;
    private ProgressBar progressBar;
    private boolean isRefreshing = false;
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;

    public static ContactsListFragment newInstance() {
        return new ContactsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setHasOptionsMenu(true);
            //Get managers
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            moduleManager.setAppPublicKey(appSession.getAppPublicKey());
            lstChatUserInformations = new ArrayList<>();
            applicationsHelper = ((FermatApplicationSession) getActivity().getApplicationContext()).getApplicationManager();
            //Obtain Settings or create new Settings if first time opening subApp
            appSettings = null;
            try {
                appSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                appSettings = null;
            }

            if (appSettings == null) {
                appSettings = new ChatActorCommunitySettings();
                appSettings.setIsPresentationHelpEnabled(true);
                try {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), appSettings);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Check if a default identity is configured
            try {
                identity = moduleManager.getSelectedActorIdentity();
                if (identity == null)
                    launchListIdentitiesDialog = true;
            } catch (CantGetSelectedActorIdentityException e) {
                //There are no identities in device
                launchActorCreationDialog = true;
            } catch (ActorIdentityNotSelectedException e) {
                //There are identities in device, but none selected
                launchListIdentitiesDialog = true;
            }
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.cht_comm_connections_list_fragment, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new ContactsListAdapter(getActivity(), lstChatUserInformations, appSession, moduleManager);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);
            noDatalabel = (TextView) rootView.findViewById(R.id.nodatalabel);
            noData = (ImageView) rootView.findViewById(R.id.nodata);
            progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
//            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
//            swipeRefresh.setOnRefreshListener(this);
//            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
            showEmpty(true, emptyView);
            progressBar.setVisibility(View.VISIBLE);
            onRefresh();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(ex));
        }
        return rootView;
    }

    @Override
    public void onFragmentFocus() {
        offset = 0;
        onRefresh();
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException, CantGetSelectedActorIdentityException {
    }

    @Override
    public void onRefresh() {
        try {
            if (!isRefreshing) {
                isRefreshing = true;
                final FermatWorker worker = new FermatWorker() {
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
                       /* if (swipeRefresh != null)
                            swipeRefresh.setRefreshing(false);*/
                        if (result != null &&
                                result.length > 0) {
                            if (getActivity() != null && adapter != null) {
                                lstChatUserInformations = (ArrayList<ChatActorCommunityInformation>) result[0];
                                adapter.changeDataSet(lstChatUserInformations);
                                if (lstChatUserInformations.isEmpty()) {
                                    showEmpty(true, emptyView);
                                } else {
                                    showEmpty(false, emptyView);
                                }
                            }
                        } else
                            showEmpty(adapter.getSize() < 0, emptyView);
                    }

                    @Override
                    public void onErrorOccurred(Exception ex) {
                        try {
                            isRefreshing = false;
//                            if (swipeRefresh != null)
//                                swipeRefresh.setRefreshing(false);
                            worker.shutdownNow();
                            if (getActivity() != null)
                                errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                                        UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(ex));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                executor = worker.execute();
            }
        } catch (Exception ignore) {
            if (executor != null) {
                executor.shutdown();
                executor = null;
            }
        }
    }

    public class BackgroundAsyncTaskList extends
            AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPostExecute(Void result) {
            return;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (identity != null) {
                    List<ChatActorCommunityInformation> con =
                            moduleManager.listWorldChatActor(identity.getPublicKey(),
                                    identity.getActorType(), null, 0, "", MAX, offset);
                    if (con != null) {
                        int size = con.size();
                        if (size > 0) {
                            for (ChatActorCommunityInformation conta : con) {
                                if (conta.getConnectionState() != null) {
                                    if (conta.getConnectionState().getCode().equals(ConnectionState.CONNECTED.getCode())) {
                                        try {
                                            moduleManager.requestConnectionToChatActor(identity, conta);
                                        } catch (Exception e) {
                                            if (errorManager != null)
                                                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private synchronized List<ChatActorCommunityInformation> getMoreData() {
//        BackgroundAsyncTaskList backWorldList = new BackgroundAsyncTaskList();
//        backWorldList.execute();
        List<ChatActorCommunityInformation> dataSet = new ArrayList<>();
        try {
            List<ChatActorCommunityInformation> result;
            if (identity != null) {
                result = moduleManager.listAllConnectedChatActor(identity, MAX, offset);
                dataSet.addAll(result);
                //offset = dataSet.size();
            }
        } catch (CantListChatActorException e) {
            e.printStackTrace();
        }

        return dataSet;
    }

    public void showEmpty(boolean show, View emptyView) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            noData.setAnimation(anim);
            noDatalabel.setAnimation(anim);
            noData.setVisibility(View.VISIBLE);
            noDatalabel.setVisibility(View.VISIBLE);
            if (adapter != null)
                adapter.changeDataSet(null);
        } else {
            emptyView.setVisibility(View.GONE);
            noData.setAnimation(anim);
            emptyView.setBackgroundResource(0);
            noDatalabel.setAnimation(anim);
            noData.setVisibility(View.GONE);
            noDatalabel.setVisibility(View.GONE);
            rootView.setBackgroundResource(0);
            ColorDrawable bgcolor = new ColorDrawable(Color.parseColor("#F9F9F9"));
            emptyView.setBackground(bgcolor);
            rootView.setBackground(bgcolor);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClickListener(ChatActorCommunityInformation data, int position) {
        appSession.setData(CHAT_USER_SELECTED, data);
        if (Build.VERSION.SDK_INT < 23) {
            CommonLogger.info(TAG, "User connection state " +
                    data.getConnectionState());
            final DisconnectDialog disconnectDialog;
            try {
                disconnectDialog =
                        new DisconnectDialog(getActivity(), appSession, null,
                                data, moduleManager.getSelectedActorIdentity());
                disconnectDialog.setTitle("Disconnect");
                disconnectDialog.setDescription("Do you want to disconnect from");
                disconnectDialog.setUsername(new StringBuilder().append(data.getAlias()).append("?").toString());
                disconnectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            onRefresh();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                disconnectDialog.show();
            } catch (CantGetSelectedActorIdentityException
                    | ActorIdentityNotSelectedException e) {
                e.printStackTrace();
            }
        } else {
            CommonLogger.info(TAG, "User connection state " +
                    data.getConnectionState());
            final DisconnectDialog disconnectDialog;
            try {
                disconnectDialog =
                        new DisconnectDialog(getContext(), appSession, null,
                                data, moduleManager.getSelectedActorIdentity());
                disconnectDialog.setTitle("Disconnect");
                disconnectDialog.setDescription("Do you want to disconnect from");
                disconnectDialog.setUsername(new StringBuilder().append(data.getAlias()).append("?").toString());
                disconnectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            onRefresh();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                disconnectDialog.show();
            } catch (CantGetSelectedActorIdentityException
                    | ActorIdentityNotSelectedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
    }

    public void onOptionMenuPrepared(Menu menu) {
        MenuItem searchItem = menu.findItem(1);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setQueryHint(getResources().getString(R.string.description_search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equals(searchView.getQuery().toString())) {
                        adapter.changeDataSet(lstChatUserInformations);
                        adapter.getFilter().filter(s);
                    }
                    return false;
                }
            });
            if (appSession.getData("filterString") != null) {
                String filterString = (String) appSession.getData("filterString");
                if (filterString.length() > 0) {
                    searchView.setQuery(filterString, true);
                    searchView.setIconified(false);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 2:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    showDialogHelp();
                    break;
                case 1:
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogHelp() {
        try {
            moduleManager = appSession.getModuleManager();
            if (identity != null) {
                if (!identity.getPublicKey().isEmpty()) {
                    PresentationChatCommunityDialog presentationChatCommunityDialog =
                            new PresentationChatCommunityDialog(getActivity(),
                                    appSession,
                                    null,
                                    moduleManager,
                                    PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES
                            );
                    presentationChatCommunityDialog.show();
                } else {
                    PresentationChatCommunityDialog presentationChatCommunityDialog =
                            new PresentationChatCommunityDialog(getActivity(),
                                    appSession,
                                    null,
                                    moduleManager,
                                    PresentationChatCommunityDialog.TYPE_PRESENTATION
                            );
                    presentationChatCommunityDialog.show();
                    presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Boolean isBackPressed =
                                    (Boolean) appSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
                            if (isBackPressed != null) {
                                if (isBackPressed) {
                                    getActivity().finish();
                                }
                            } else {
                                invalidate();
                            }
                        }
                    });
                }
            } else {
                PresentationChatCommunityDialog presentationChatCommunityDialog =
                        new PresentationChatCommunityDialog(getActivity(),
                                appSession,
                                null,
                                moduleManager,
                                PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES
                        );
                presentationChatCommunityDialog.show();
                presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Boolean isBackPressed = (Boolean) appSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
                        if (isBackPressed != null) {
                            if (isBackPressed) {
                                getActivity().onBackPressed();
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            PresentationChatCommunityDialog presentationChatCommunityDialog =
                    new PresentationChatCommunityDialog(getActivity(),
                            appSession,
                            null,
                            moduleManager,
                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES
                    );
            presentationChatCommunityDialog.show();
            e.printStackTrace();
        }
    }
}