package com.bitdubai.sub_app.intra_user_community.fragments;

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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.adapters.AppFriendsListAdapter;
import com.bitdubai.sub_app.intra_user_community.common.navigation_drawer.NavigationViewAdapter;
import com.bitdubai.sub_app.intra_user_community.common.utils.FragmentsCommons;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;

/**
 * Created by josemanueldsds on 30/11/15.
 */
public class ConnectionFriendListFragment extends FermatFragment implements SwipeRefreshLayout.OnRefreshListener {


    protected final String TAG = "ConnectionNotificationsFragment";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout empty;
    private boolean isRefreshing = false;
    private View rootView;
    private AppFriendsListAdapter adapter;
    private IntraUserSubAppSession intraUserSubAppSession;
    private LinearLayout emptyView;
    private IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ConnectionFriendListFragment newInstance() {
        return new ConnectionFriendListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting up  module
        intraUserSubAppSession = ((IntraUserSubAppSession) subAppsSession);
        moduleManager = intraUserSubAppSession.getModuleManager();
        errorManager = subAppsSession.getErrorManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {

            rootView = inflater.inflate(R.layout.intra_user_connection_friend_list, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new AppFriendsListAdapter(getActivity(), null);
            recyclerView.setAdapter(adapter);

            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#000b12"));

            empty = (LinearLayout) rootView.findViewById(R.id.empty);
            empty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeActivity(Activities.CWP_INTRA_USER_ACTIVITY.getCode(),subAppsSession.getAppPublicKey(),null);
                }
            });
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            showEmpty(true, rootView);


        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }


        return rootView;
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
        /**
         * add navigation header
         */
        addNavigationHeader(FragmentsCommons.setUpHeaderScreen(layoutInflater, getActivity(), intraUserSubAppSession.getIntraUserModuleManager().getActiveIntraUserIdentity()));

        /**
         * Navigation view items
         */
        NavigationViewAdapter navigationViewAdapter = new NavigationViewAdapter(getActivity(), null);
        setNavigationDrawer(navigationViewAdapter);
    }

    @Override
    public void onRefresh() {

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
}
