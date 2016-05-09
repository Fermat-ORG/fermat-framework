package com.bitdubai.sub_app.crypto_customer_community.fragments;

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
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.LinkedCryptoCustomerIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.adapters.AppNotificationAdapter;
import com.bitdubai.sub_app.crypto_customer_community.common.popups.AcceptDialog;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;
import com.bitdubai.sub_app.crypto_customer_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 02/02/2016.
 */
public class ConnectionNotificationsFragment extends AbstractFermatFragment<CryptoCustomerCommunitySubAppSession, SubAppResourcesProviderManager>
        implements SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<LinkedCryptoCustomerIdentity>, AcceptDialog.OnDismissListener {

    public static final String ACTOR_SELECTED = "actor_selected";

    private static final int MAX = 20;

    protected final String TAG = "ConnectionNotificationsFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing = false;
    private View rootView;
    private AppNotificationAdapter adapter;
    private CryptoCustomerCommunitySubAppSession cryptoCustomerCommunitySubAppSession;
    private LinearLayout emptyView;
    private CryptoCustomerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private int offset = 0;
    private CryptoCustomerCommunityInformation cryptoCustomerInformation;
    private List<LinkedCryptoCustomerIdentity> cryptoCustomerInformationList;

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

        // setting up  module
        cryptoCustomerCommunitySubAppSession = appSession;
        cryptoCustomerInformation = (CryptoCustomerCommunityInformation) appSession.getData(ACTOR_SELECTED);
        moduleManager = cryptoCustomerCommunitySubAppSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        cryptoCustomerInformationList = new ArrayList<>();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_connections_notifications, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new AppNotificationAdapter(getActivity(), cryptoCustomerInformationList);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);

            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#000b12"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);

            onRefresh();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

        return rootView;
    }

    private synchronized ArrayList<LinkedCryptoCustomerIdentity> getMoreData() {

        ArrayList<LinkedCryptoCustomerIdentity> dataSet = new ArrayList<>();

        try {

            dataSet.addAll(moduleManager.listCryptoCustomersPendingLocalAction(moduleManager.getSelectedActorIdentity(), MAX, offset));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //MOCK!
       /* for(int i = 1 ; i <= 5 ; i++){
            dataSet.add(new LinkedCryptoCustomerIdentityImpl(UUID.randomUUID(),"pk-"+i, "mock-"+i, new byte[0]));
        }*/

        return dataSet;
    }


    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
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
                            cryptoCustomerInformationList = (ArrayList<LinkedCryptoCustomerIdentity>) result[0];
                            adapter.changeDataSet(cryptoCustomerInformationList);
                            if (cryptoCustomerInformationList.isEmpty()) {
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
    public void onItemClickListener(LinkedCryptoCustomerIdentity data, int position) {
        try {
            //Toast.makeText(getActivity(), "TODO ACCEPT ->", Toast.LENGTH_LONG).show();
            //moduleManager.acceptCryptoCustomer(moduleManager.getSelectedActorIdentity(), data.getName(), data.getPublicKey(), data.getProfileImage());
            //TODO: Note, subAppResourcesProviderManager is badly casted as a WalletResourcesNetworkServicePluginRoot.. sending null, for now. so it doesnt throw a classCastException
            AcceptDialog notificationAcceptDialog = new AcceptDialog(getActivity(), cryptoCustomerCommunitySubAppSession, null, data, moduleManager.getSelectedActorIdentity());
            notificationAcceptDialog.setOnDismissListener(this);
            notificationAcceptDialog.show();
        } catch (CantGetSelectedActorIdentityException|ActorIdentityNotSelectedException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "TODO ACCEPT but.. ERROR! ->", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLongItemClickListener(LinkedCryptoCustomerIdentity data, int position) {

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
