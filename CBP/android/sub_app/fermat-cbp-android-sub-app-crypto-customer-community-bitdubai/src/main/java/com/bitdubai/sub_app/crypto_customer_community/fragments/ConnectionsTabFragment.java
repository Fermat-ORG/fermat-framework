package com.bitdubai.sub_app.crypto_customer_community.fragments;

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
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.adapters.AppFriendsListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 04/02/2016.
 */
public class ConnectionsTabFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager>, SubAppResourcesProviderManager> implements SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<CryptoCustomerCommunityInformation> {

    public static final String ACTOR_SELECTED = "actor_selected";
    private static final int MAX = 20;
    protected final String TAG = "RequestsTabFragment";
    private int offset = 0;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout empty;
    private boolean isRefreshing = false;
    private View rootView;
    private AppFriendsListAdapter adapter;
    private LinearLayout emptyView;
    private CryptoCustomerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationArrayList;
    TextView noDatalabel;
    ImageView noData;
    public static ConnectionsTabFragment newInstance() {
        return new ConnectionsTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        cryptoCustomerCommunityInformationArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_connections_list, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new AppFriendsListAdapter(getActivity(), cryptoCustomerCommunityInformationArrayList);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);
            noDatalabel = (TextView) rootView.findViewById(R.id.nodatalabel);
            noData=(ImageView) rootView.findViewById(R.id.nodata);
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
            rootView.setBackgroundColor(Color.parseColor("#F9F9F9"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            emptyView.setBackgroundColor(Color.parseColor("#F9F9F9"));
            onRefresh();
        } catch (Exception ex) {
            appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);

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
                            cryptoCustomerCommunityInformationArrayList = (ArrayList<CryptoCustomerCommunityInformation>) result[0];
                            adapter.changeDataSet(cryptoCustomerCommunityInformationArrayList);
                            if (cryptoCustomerCommunityInformationArrayList.isEmpty()) {
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

    private synchronized List<CryptoCustomerCommunityInformation> getMoreData() {
        List<CryptoCustomerCommunityInformation> dataSet = new ArrayList<>();
        try {
            dataSet.addAll(moduleManager.listAllConnectedCryptoCustomers(moduleManager.getSelectedActorIdentity(), MAX, offset));
        } catch (CantGetCryptoCustomerListException | CantGetSelectedActorIdentityException |ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }

        return dataSet;
    }
    public void showEmpty(boolean show, View emptyView) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show/* &&
                (emptyView.getShowAsAction() == View.GONE || emptyView.getShowAsAction() == View.INVISIBLE)*/) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            noData.setAnimation(anim);
            //emptyView.setBackgroundResource(R.drawable.cht_comm_background);
            noDatalabel.setAnimation(anim);
            noData.setVisibility(View.VISIBLE);
            noDatalabel.setVisibility(View.VISIBLE);
            //rootView.setBackgroundResource(R.drawable.cht_comm_background);
            if (adapter != null)
                adapter.changeDataSet(null);
        } else if (!show /*&& emptyView.getShowAsAction() == View.VISIBLE*/) {
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
    public void onItemClickListener(CryptoCustomerCommunityInformation data, int position) {
        appSession.setData(ACTOR_SELECTED, data);
        changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(CryptoCustomerCommunityInformation data, int position) {

    }
}
