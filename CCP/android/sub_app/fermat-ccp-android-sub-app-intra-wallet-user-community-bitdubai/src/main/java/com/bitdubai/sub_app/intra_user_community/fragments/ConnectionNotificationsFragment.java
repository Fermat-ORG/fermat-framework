package com.bitdubai.sub_app.intra_user_community.fragments;

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

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.adapters.AppNotificationAdapter;
import com.bitdubai.sub_app.intra_user_community.common.navigation_drawer.NavigationViewAdapter;
import com.bitdubai.sub_app.intra_user_community.common.utils.FragmentsCommons;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josemanueldsds on 29/11/15.
 */
public class ConnectionNotificationsFragment extends FermatFragment implements SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<IntraUserInformation> {

    private static final int MAX = 20;
    protected final String TAG = "ConnectionNotificationsFragment";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout empty;
    private boolean isRefreshing = false;
    private View rootView;
    private AppNotificationAdapter adapter;
    private IntraUserSubAppSession intraUserSubAppSession;
    private LinearLayout emptyView;
    private IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;
    private int offset = 0;
    private List<IntraUserInformation> lstIntraUserInformations;
    private ProgressDialog dialog;

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
        intraUserSubAppSession = ((IntraUserSubAppSession) subAppsSession);
        moduleManager = intraUserSubAppSession.getModuleManager();
        errorManager = subAppsSession.getErrorManager();
        lstIntraUserInformations = new ArrayList<>();
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

            rootView = inflater.inflate(R.layout.intra_user_notification_center, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new AppNotificationAdapter(getActivity(),lstIntraUserInformations);
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

    private synchronized ArrayList<IntraUserInformation> getMoreData() {
        ArrayList<IntraUserInformation> dataSet = new ArrayList<>();

        try {

            dataSet.addAll(moduleManager.getIntraUsersWaitingYourAcceptance(moduleManager.getActiveIntraUserIdentity().getPublicKey(), MAX, offset));
            //offset = dataSet.size();
//
//            lstIntraUserInformations.addAll(moduleManager.getIntraUsersWaitingYourAcceptance(moduleManager.getActiveIntraUserIdentity().getPublicKey(), MAX, offset));
//            adapter.notifyDataSetChanged();

        } catch (CantGetIntraUsersListException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSet;
    }
    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
//        /**
//         * add navigation header
//         */
//        addNavigationHeader(FragmentsCommons.setUpHeaderScreen(layoutInflater, getActivity(), intraUserSubAppSession.getIntraUserModuleManager().getActiveIntraUserIdentity()));
//
//        /**
//         * Navigation view items
//         */
//        NavigationViewAdapter navigationViewAdapter = new NavigationViewAdapter(getActivity(),null);
//        setNavigationDrawer(navigationViewAdapter);
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
                            List<IntraUserInformation> lst = (ArrayList<IntraUserInformation>) result[0];
                            lstIntraUserInformations = lst;
                            adapter.changeDataSet(lstIntraUserInformations);
                            if (lstIntraUserInformations.isEmpty()) {
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
    public void onItemClickListener(IntraUserInformation data, int position) {
        try {
            moduleManager.acceptIntraUser(moduleManager.getActiveIntraUserIdentity().getPublicKey(),data.getName(),data.getPublicKey(),null);
            Toast.makeText(getActivity(),"Aceptado,\njose esto lo hice porque me lo dejaste mal y tengo que probar lo mio",Toast.LENGTH_SHORT).show();
        } catch (CantAcceptRequestException e) {
            e.printStackTrace();
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLongItemClickListener(IntraUserInformation data, int position) {

    }
}
