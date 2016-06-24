package com.bitdubai.sub_app.crypto_broker_community.fragments;

import android.app.ProgressDialog;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.common.adapters.AppFriendsListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ConnectionsListFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager>, SubAppResourcesProviderManager> implements SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<CryptoBrokerCommunityInformation> {

    public static final String ACTOR_SELECTED = "actor_selected";
    private static final int MAX = 20;
    protected final String TAG = "ConnectionNotificationsFragment";
    private SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing = false;
    private View rootView;
    private AppFriendsListAdapter adapter;
    private LinearLayout emptyView;
    private CryptoBrokerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationArrayList;

    public static ConnectionsListFragment newInstance() {
        return new ConnectionsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        cryptoBrokerCommunityInformationArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_connections_list, container, false);
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new AppFriendsListAdapter(getActivity(), cryptoBrokerCommunityInformationArrayList);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
            onRefresh();
        } catch (Exception ex) {
            errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);

            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
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
                            cryptoBrokerCommunityInformationArrayList = (ArrayList<CryptoBrokerCommunityInformation>) result[0];
                            adapter.changeDataSet(cryptoBrokerCommunityInformationArrayList);
                            if (cryptoBrokerCommunityInformationArrayList.isEmpty()) {
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
                        errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                    }
                }
            });
            worker.execute();
        }
    }

    private synchronized List<CryptoBrokerCommunityInformation> getMoreData() {
        List<CryptoBrokerCommunityInformation> dataSet = new ArrayList<>();
        try {
            int offset = 0;
            dataSet.addAll(moduleManager.listAllConnectedCryptoBrokers(moduleManager.getSelectedActorIdentity(), MAX, offset));
        } catch (CantListCryptoBrokersException | CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        return dataSet;
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

    @Override
    public void onItemClickListener(CryptoBrokerCommunityInformation data, int position) {
        appSession.setData(ACTOR_SELECTED, data);
        changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerCommunityInformation data, int position) {

    }
}
