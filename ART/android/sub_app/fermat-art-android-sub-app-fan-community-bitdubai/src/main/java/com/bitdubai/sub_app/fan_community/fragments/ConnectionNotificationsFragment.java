package com.bitdubai.sub_app.fan_community.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.ArtCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.LinkedFanIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.fan_community.R;
import com.bitdubai.sub_app.fan_community.adapters.AppNotificationAdapter;
import com.bitdubai.sub_app.fan_community.commons.popups.AcceptDialog;
import com.bitdubai.sub_app.fan_community.sessions.FanCommunitySubAppSessionReferenceApp;
import com.bitdubai.sub_app.fan_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class ConnectionNotificationsFragment extends
        AbstractFermatFragment<
                ReferenceAppFermatSession<FanCommunityModuleManager>,
                SubAppResourcesProviderManager>
        implements
        SwipeRefreshLayout.OnRefreshListener,
        FermatListItemListeners<LinkedFanIdentity>, AcceptDialog.OnDismissListener {

    public static final String ACTOR_SELECTED = "actor_selected";
    private static final int MAX = 20;
    protected final String TAG = "ConnectionNotificationsFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing = false;
    private View rootView;
    private AppNotificationAdapter adapter;
    //private FanCommunitySubAppSessionReferenceApp fanCommunitySubAppSession;
    private LinearLayout emptyView;
    private FanCommunityModuleManager moduleManager;
    private ErrorManager errorManager;
    private int offset = 0;
    private ArtCommunityInformation fanCommunityInformation;
    private List<LinkedFanIdentity> linkedFanIdentities;

    /**
     * Create a new instance of this fragment     *
     * @return InstalledFragment instance object
     */
    public static ConnectionNotificationsFragment newInstance() {
        return new ConnectionNotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting up  module
        //fanCommunitySubAppSession = appSession;
        fanCommunityInformation = (ArtCommunityInformation) appSession.getData(ACTOR_SELECTED);
        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        linkedFanIdentities = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.afc_fragment_connections_notificactions, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.afc_recycler_view);
            layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new AppNotificationAdapter(getActivity(), linkedFanIdentities);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);

            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.afc_swipeRefresh);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#F1F2F2"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.afc_empty_view);

            onRefresh();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();

        }

        return rootView;
    }

    private synchronized ArrayList<LinkedFanIdentity> getMoreData() {

        ArrayList<LinkedFanIdentity> dataSet = new ArrayList<>();

        try {

            dataSet.addAll(
                    moduleManager.listFansPendingLocalAction(
                            moduleManager.getSelectedActorIdentity(), MAX, offset));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSet;
    }


    private void setUpScreen(LayoutInflater layoutInflater) throws
            CantGetActiveLoginIdentityException {
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
                            linkedFanIdentities = (ArrayList<LinkedFanIdentity>) result[0];
                            adapter.changeDataSet(linkedFanIdentities);
                            if (linkedFanIdentities.isEmpty()) {
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
    public void onItemClickListener(LinkedFanIdentity data, int position) {
        try {
            AcceptDialog notificationAcceptDialog = new AcceptDialog(
                    getActivity(),
                    appSession,
                    null,
                    data,
                    moduleManager.getSelectedActorIdentity());
            notificationAcceptDialog.setOnDismissListener(this);
            notificationAcceptDialog.show();
        } catch (CantGetSelectedActorIdentityException |ActorIdentityNotSelectedException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "TODO ACCEPT but.. ERROR! ->", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLongItemClickListener(LinkedFanIdentity data, int position) {
    }

    /**
     * @param show
     */
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

    @Override
    public void onDismiss(DialogInterface dialog) {
        onRefresh();
    }
}

