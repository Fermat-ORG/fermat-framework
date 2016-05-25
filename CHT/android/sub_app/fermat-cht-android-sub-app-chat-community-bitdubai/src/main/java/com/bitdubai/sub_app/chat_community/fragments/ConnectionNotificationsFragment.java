package com.bitdubai.sub_app.chat_community.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.adapters.NotificationAdapter;
import com.bitdubai.sub_app.chat_community.common.popups.AcceptDialog;
import com.bitdubai.sub_app.chat_community.session.ChatUserSubAppSession;
import com.bitdubai.sub_app.chat_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;

/**
 * ConnectionNotificationsFragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})

public class ConnectionNotificationsFragment
        extends AbstractFermatFragment<ChatUserSubAppSession, SubAppResourcesProviderManager>
        implements SwipeRefreshLayout.OnRefreshListener,
        FermatListItemListeners<ChatActorCommunityInformation>, AcceptDialog.OnDismissListener {

    private ChatActorCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatActorCommunitySettings> settingsManager;
    private ChatUserSubAppSession chatUserSubAppSession;
    public static final String CHAT_USER_SELECTED = "chat_user";
    private static final int MAX = 20;
    protected final String TAG = "ConnectionNotificationsFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing = false;
    private View rootView;
    private NotificationAdapter adapter;
    private LinearLayout emptyView;
    private int offset = 0;
    private ChatActorCommunityInformation chatUserInformation;
    private List<ChatActorCommunityInformation> lstChatUserInformations;//cryptoBrokerInformationList;
    private ChatActorCommunitySettings appSettings;
    ImageView noData;
    TextView noDatalabel;
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;
    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ConnectionNotificationsFragment newInstance() {
        return new ConnectionNotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setHasOptionsMenu(true);

            // setting up  module
            chatUserInformation = (ChatActorCommunityInformation) appSession.getData(CHAT_USER_SELECTED);
            chatUserSubAppSession = ((ChatUserSubAppSession) appSession);
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            moduleManager.setAppPublicKey(appSession.getAppPublicKey());

            lstChatUserInformations = new ArrayList<>();

            //Obtain Settings or create new Settings if first time opening subApp
            appSettings = null;
            try {
                appSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            }catch (Exception e){ appSettings = null; }

            if(appSettings == null){
                appSettings = new ChatActorCommunitySettings();
                appSettings.setIsPresentationHelpEnabled(true);
                try {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), appSettings);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.cht_comm_notifications_fragment, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new NotificationAdapter(getActivity(), lstChatUserInformations);
            adapter.setFermatListEventListener(this);
            //rootView.setBackgroundResource(R.drawable.cht_comm_background_empty_screen);

            recyclerView.setAdapter(adapter);
            noData = (ImageView) rootView.findViewById(R.id.nodata);
            noDatalabel = (TextView) rootView.findViewById(R.id.nodatalabel);
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#F9F9F9"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            showEmpty(true, emptyView);
            onRefresh();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

        return rootView;
    }

    private synchronized ArrayList<ChatActorCommunityInformation> getMoreData() {

        ArrayList<ChatActorCommunityInformation> dataSet = new ArrayList<>();

        try {
            dataSet.addAll(moduleManager.listChatActorPendingLocalAction(moduleManager.getSelectedActorIdentity(), MAX, offset));
        } catch (CantListChatActorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSet;
    }
    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException, CantGetChatUserIdentityException {
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            final ProgressDialog notificationsProgressDialog = new ProgressDialog(getActivity());
            notificationsProgressDialog.setMessage("Loading Notifications");
            notificationsProgressDialog.setCancelable(false);
            notificationsProgressDialog.show();
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
                    notificationsProgressDialog.dismiss();
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
                    notificationsProgressDialog.dismiss();
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


    @Override
    public void onItemClickListener(ChatActorCommunityInformation data, int position) {
        try {
            Toast.makeText(getActivity(), "Connection Accepted", Toast.LENGTH_LONG).show();
            //moduleManager.acceptCryptoBroker(moduleManager.getSelectedActorIdentity(), data.getName(), data.getPublicKey(), data.getProfileImage());
            AcceptDialog notificationAcceptDialog = new AcceptDialog(getActivity(), appSession , null, data, moduleManager.getSelectedActorIdentity());
            notificationAcceptDialog.setOnDismissListener(this);
            notificationAcceptDialog.show();
        } catch (CantGetSelectedActorIdentityException|ActorIdentityNotSelectedException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Connection Accepted but.. ERROR! ->", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {

    }

    /**
     * @param show
     */
    public void showEmpty(boolean show, View emptyView) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show/* &&
                (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)*/) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            noData.setAnimation(anim);
            emptyView.setBackgroundResource(R.drawable.cht_comm_background);
            noDatalabel.setAnimation(anim);
            noData.setVisibility(View.VISIBLE);
            noDatalabel.setVisibility(View.VISIBLE);
            rootView.setBackgroundResource(R.drawable.cht_comm_background);
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
    public void onDismiss(DialogInterface dialog) {
        onRefresh();
    }
}
//
//public class ConnectionNotificationsFragment
//        extends AbstractFermatFragment
//        implements SwipeRefreshLayout.OnRefreshListener,
//        FermatListItemListeners<ChatActorCommunityInformation> {
//
//    public static final String CHAT_USER_SELECTED = "chat_user";
//    private static final int MAX = 20;
//    protected final String TAG = "ConnectionNotificationsFragment";
//    private RecyclerView recyclerView;
//    private LinearLayoutManager layoutManager;
//    private SwipeRefreshLayout swipeRefresh;
//    private boolean isRefreshing = false;
//    private View rootView;
//    private NotificationAdapter adapter;
//    private ChatUserSubAppSession chatUserSubAppSession;
//    private LinearLayout emptyView;
//    private ChatActorCommunitySubAppModuleManager moduleManager;
//    private ErrorManager errorManager;
//    private int offset = 0;
//    private ChatActorCommunityInformation chatUserInformation;
//    private List<ChatActorCommunityInformation> lstChatUserInformations;
//    private ChatActorCommunitySelectableIdentity identity;
//    private ProgressDialog dialog;
//
//    /**
//     * Create a new instance of this fragment
//     *
//     * @return InstalledFragment instance object
//     */
//    public static ConnectionNotificationsFragment newInstance() {
//        return new ConnectionNotificationsFragment();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        // setting up  module
//        chatUserSubAppSession = ((ChatUserSubAppSession) appSession);
//        chatUserInformation = (ChatActorCommunityInformation) appSession.getData(CHAT_USER_SELECTED);
//        moduleManager = chatUserSubAppSession.getModuleManager();
//        errorManager = appSession.getErrorManager();
//        lstChatUserInformations = new ArrayList<>();
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        try {
//            rootView = inflater.inflate(R.layout.cht_comm_notifications_fragment, container, false);
//            setUpScreen(inflater);
//            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
//            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setHasFixedSize(true);
////            adapter = new NotificationAdapter(getActivity(), lstChatUserInformations);
////            adapter.setFermatListEventListener(this);
//            //recyclerView.setAdapter(adapter);
//            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
//            //swipeRefresh.setOnRefreshListener(this);
//            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
//            rootView.setBackgroundColor(Color.parseColor("#000b12"));
//            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
//            onRefresh();
//
//        } catch (Exception ex) {
//            CommonLogger.exception(TAG, ex.getMessage(), ex);
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
//        }
//        return rootView;
//    }
//
//    private synchronized ArrayList<ChatActorCommunityInformation> getMoreData() {
//        ArrayList<ChatActorCommunityInformation> dataSet = new ArrayList<>();
//        try {
//                dataSet.addAll(moduleManager.
//                        getChatActorWaitingYourAcceptanceCount(moduleManager.
//                                getSelectedActorIdentity().getPublicKey(), MAX, offset));
//            } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return dataSet;
//    }
//
//    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetChatUserIdentityException {}
//
//    //@Override
//    public void onRefresh() {
//        if (!isRefreshing) {
//            isRefreshing = true;
//            final ProgressDialog notificationsProgressDialog = new ProgressDialog(getActivity());
//            notificationsProgressDialog.setMessage("Loading Notifications");
//            notificationsProgressDialog.setCancelable(false);
//            notificationsProgressDialog.show();
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
//                    notificationsProgressDialog.dismiss();
//                    isRefreshing = false;
//                    if (swipeRefresh != null)
//                        swipeRefresh.setRefreshing(false);
//                    if (result != null &&
//                            result.length > 0) {
//                        if (getActivity() != null && adapter != null) {
//                            lstChatUserInformations = (ArrayList<ChatActorCommunityInformation>) result[0];
//                           // adapter.changeDataSet(lstChatUserInformations);
//                            if (lstChatUserInformations.isEmpty()) {
//                                showEmpty(true, emptyView);
//                            } else {
//                                showEmpty(false, emptyView);
//                            }
//                        }
//                    } //else
//                        //showEmpty(adapter.getSize() < 0, emptyView);
//                }
//
//                @Override
//                public void onErrorOccurred(Exception ex) {
//                    notificationsProgressDialog.dismiss();
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
//    @Override
//    public void onItemClickListener(ChatActorCommunityInformation data, int position) {
//        try {
//            AcceptDialog notificationAcceptDialog = new AcceptDialog(getActivity(),
//                    chatUserSubAppSession,null, data,
//                    moduleManager.getSelectedActorIdentity());// .getActiveChatUserIdentity());
//            notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    Object o = appSession.getData(SessionConstants.NOTIFICATION_ACCEPTED);
//                    try {
//                        if ((Boolean) o) {
//                            onRefresh();
//                            appSession.removeData(SessionConstants.NOTIFICATION_ACCEPTED);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        onRefresh();
//                    }
//                }
//            });
//            notificationAcceptDialog.show();
//
//        } catch (CantGetSelectedActorIdentityException
//                | ActorIdentityNotSelectedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {}
//
//    public void showEmpty(boolean show, View emptyView) {
//        Animation anim = AnimationUtils.loadAnimation(getActivity(),
//                show ? android.R.anim.fade_in : android.R.anim.fade_out);
//        if (show &&
//                (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)) {
//            emptyView.setAnimation(anim);
//            emptyView.setVisibility(View.VISIBLE);
//            if (adapter != null){}
//                //adapter.changeDataSet(null);
//        } else if (!show && emptyView.getVisibility() == View.VISIBLE) {
//            emptyView.setAnimation(anim);
//            emptyView.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
//    }
//}
