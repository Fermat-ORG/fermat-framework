package com.bitdubai.sub_app.chat_community.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.adapters.ContactsListAdapter;
import com.bitdubai.sub_app.chat_community.session.ChatUserSubAppSession;
import com.bitdubai.sub_app.chat_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * ContactsListFragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ContactsListFragment
        extends AbstractFermatFragment<ChatUserSubAppSession, SubAppResourcesProviderManager>
        implements SwipeRefreshLayout.OnRefreshListener,
        FermatListItemListeners<ChatActorCommunityInformation> {

    //Managers
    private ChatActorCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatActorCommunitySettings> settingsManager;
    private ChatUserSubAppSession chatUserSubAppSession;
    public static final String CHAT_USER_SELECTED = "chat_user";
    private static final int MAX = 20;
    protected final String TAG = "ContactsListFragment";
    private int offset = 0;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout empty;
    private View rootView;
    private ContactsListAdapter adapter;
    private LinearLayout emptyView;
    private ArrayList<ChatActorCommunityInformation> lstChatUserInformations;//cryptoBrokerCommunityInformationArrayList;
    private ChatActorCommunitySettings appSettings;
    TextView noDatalabel;
    ImageView noData;
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
            chatUserSubAppSession = ((ChatUserSubAppSession) appSession);
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            settingsManager = moduleManager.getSettingsManager();
            moduleManager.setAppPublicKey(appSession.getAppPublicKey());
        lstChatUserInformations = new ArrayList<>();
            //Obtain Settings or create new Settings if first time opening subApp
            appSettings = null;
            try {
                appSettings = this.settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
            }catch (Exception e){ appSettings = null; }

            if(appSettings == null){
                appSettings = new ChatActorCommunitySettings();
                appSettings.setIsPresentationHelpEnabled(true);
                try {
                    settingsManager.persistSettings(appSession.getAppPublicKey(), appSettings);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //Check if a default identity is configured
            try{
                moduleManager.getSelectedActorIdentity();
            }catch (CantGetSelectedActorIdentityException e){
                //There are no identities in device
                launchActorCreationDialog = true;
            }catch (ActorIdentityNotSelectedException e){
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
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new ContactsListAdapter(getActivity(), lstChatUserInformations);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);
            noDatalabel = (TextView) rootView.findViewById(R.id.nodatalabel);
            noData=(ImageView) rootView.findViewById(R.id.nodata);
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
            showEmpty(true, emptyView);
            onRefresh();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException, CantGetSelectedActorIdentityException {
//        addNavigationHeader(FragmentsCommons.setUpHeaderScreen(layoutInflater, getActivity(), appSession.getModuleManager().getSelectedActorIdentity()));
        //AppNavigationAdapter appNavigationAdapter = new AppNavigationAdapter(getActivity(), null);
//        setNavigationDrawer(appNavigationAdapter);
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            final ProgressDialog connectionsProgressDialog = new ProgressDialog(getActivity());
            connectionsProgressDialog.setMessage("Loading Connections");
            connectionsProgressDialog.setCancelable(false);
            connectionsProgressDialog.show();
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
                    connectionsProgressDialog.dismiss();
                    isRefreshing = false;
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
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
                    connectionsProgressDialog.dismiss();
                    try {
                        isRefreshing = false;
                        if (swipeRefresh != null)
                            swipeRefresh.setRefreshing(false);
                        if (getActivity() != null)
                            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        ex.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            worker.execute();
        }
    }

    private synchronized List<ChatActorCommunityInformation> getMoreData() {
        List<ChatActorCommunityInformation> dataSet = new ArrayList<>();
        try {
            dataSet.addAll(moduleManager.listAllConnectedChatActor(moduleManager.getSelectedActorIdentity(), MAX, offset));
        } catch (CantListChatActorException | CantGetSelectedActorIdentityException |ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }

        return dataSet;
    }
    public void showEmpty(boolean show, View emptyView) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show /*&&
                (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)*/) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            noData.setAnimation(anim);
            emptyView.setBackgroundResource(R.drawable.fondo);
            noDatalabel.setAnimation(anim);
            noData.setVisibility(View.VISIBLE);
            noDatalabel.setVisibility(View.VISIBLE);
            rootView.setBackgroundResource(R.drawable.fondo);
            if (adapter != null)
                adapter.changeDataSet(null);
        } else if (!show /*&& emptyView.getVisibility() == View.VISIBLE*/) {
            emptyView.setAnimation(anim);
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
    }

    @Override
    public void onItemClickListener(ChatActorCommunityInformation data, int position) {
        appSession.setData(CHAT_USER_SELECTED, data);
        changeActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {

    }
}
//
//public class ContactsListFragment extends AbstractFermatFragment
//        implements SwipeRefreshLayout.OnRefreshListener,
//        FermatListItemListeners<ChatActorCommunityInformation> {
//
//    public static final String CHAT_USER_SELECTED = "chat_user";
//    private static final int MAX = 20;
//    protected final String TAG = "ContactsListFragment";
//    private int offset = 0;
//    private RecyclerView recyclerView;
//    private LinearLayoutManager layoutManager;
//    private SwipeRefreshLayout swipeRefresh;
//    private LinearLayout empty;
//    private boolean isRefreshing = false;
//    private View rootView;
//    private ContactsListAdapter adapter;
//    private ChatUserSubAppSession chatUserSubAppSession;
//    private LinearLayout emptyView;
//    private ChatActorCommunitySubAppModuleManager moduleManager;
//    private ErrorManager errorManager;
//    private List<ChatActorCommunityInformation> lstChatUserInformations;
//
//    public static ContactsListFragment newInstance() {
//        return new ContactsListFragment();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        chatUserSubAppSession = ((ChatUserSubAppSession) appSession);
//        moduleManager = chatUserSubAppSession.getModuleManager();
//        errorManager = appSession.getErrorManager();
//        lstChatUserInformations = new ArrayList<>();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        try {
//            rootView = inflater.inflate(R.layout.cht_comm_connections_list_fragment, container, false);
//            setUpScreen(inflater);
//            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
//            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
//            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setHasFixedSize(true);
//            adapter = new ContactsListAdapter(getActivity(), lstChatUserInformations);
//            adapter.setFermatListEventListener(this);
//            recyclerView.setAdapter(adapter);
//            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
//            swipeRefresh.setOnRefreshListener(this);
//            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
//            onRefresh();
//        } catch (Exception ex) {
//            CommonLogger.exception(TAG, ex.getMessage(), ex);
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
//        }
//        return rootView;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
//    }
//
//    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetSelectedActorIdentityException {
//    }
//
//    @Override
//    public void onRefresh() {
//        if (!isRefreshing) {
//            isRefreshing = true;
//            final ProgressDialog connectionsProgressDialog = new ProgressDialog(getActivity());
//            connectionsProgressDialog.setMessage("Loading Connections");
//            connectionsProgressDialog.setCancelable(false);
//            connectionsProgressDialog.show();
//            FermatWorker worker = new FermatWorker() {
//                @Override
//                protected Object doInBackground() throws Exception {
//                    return getMoreData();
//                }
//            };
//            worker.setContext(getActivity());
//            worker.setCallBack(new FermatWorkerCallBack() {
//                @SuppressWarnings("unchecked")
//                @Override
//                public void onPostExecute(Object... result) {
//                    connectionsProgressDialog.dismiss();
//                    isRefreshing = false;
//                    if (swipeRefresh != null)
//                        swipeRefresh.setRefreshing(false);
//                    if (result != null &&
//                            result.length > 0) {
//                        if (getActivity() != null && adapter != null) {
//                            lstChatUserInformations = (ArrayList<ChatActorCommunityInformation>) result[0];
//                            adapter.changeDataSet(lstChatUserInformations);
//                            if (lstChatUserInformations.isEmpty()) {
//                                showEmpty(true, emptyView);
//                            } else {
//                                showEmpty(false, emptyView);
//                            }
//                        }
//                    } else
//                        showEmpty(adapter.getSize() < 0, emptyView);
//                }
//
//                @Override
//                public void onErrorOccurred(Exception ex) {
//                    connectionsProgressDialog.dismiss();
//                    try {
//                        isRefreshing = false;
//                        if (swipeRefresh != null)
//                            swipeRefresh.setRefreshing(false);
//                        if (getActivity() != null)
//                            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
//                        ex.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            worker.execute();
//        }
//    }
//
//    private synchronized List<ChatActorCommunityInformation> getMoreData() {
//        List<ChatActorCommunityInformation> dataSet = new ArrayList<>();
//        try {
//            dataSet.addAll(moduleManager.listAllConnectedChatActor(moduleManager.getSelectedActorIdentity(), MAX, offset));
//        } catch (CantGetSelectedActorIdentityException
//                | ActorIdentityNotSelectedException
//                | CantListChatActorException e) {
//            e.printStackTrace();
//        }
//        return dataSet;
//    }
//
//    public void showEmpty(boolean show, View emptyView) {
//        Animation anim = AnimationUtils.loadAnimation(getActivity(),
//                show ? android.R.anim.fade_in : android.R.anim.fade_out);
//        if (show &&
//                (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)) {
//            emptyView.setAnimation(anim);
//            emptyView.setVisibility(View.VISIBLE);
//            if (adapter != null)
//                adapter.changeDataSet(null);
//        } else if (!show && emptyView.getVisibility() == View.VISIBLE) {
//            emptyView.setAnimation(anim);
//            emptyView.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void onItemClickListener(ChatActorCommunityInformation data, int position) {
//        appSession.setData(CHAT_USER_SELECTED, data);
//        changeActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(), appSession.getAppPublicKey());
//    }
//
//    @Override
//    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {}
//}
