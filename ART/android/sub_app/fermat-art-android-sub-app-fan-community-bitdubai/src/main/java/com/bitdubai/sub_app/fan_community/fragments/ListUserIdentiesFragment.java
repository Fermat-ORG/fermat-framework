package com.bitdubai.sub_app.fan_community.fragments;

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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.fan_community.R;
import com.bitdubai.sub_app.fan_community.adapters.AppSelectableIdentitiesListAdapter;
import com.bitdubai.sub_app.fan_community.sessions.FanCommunitySubAppSession;
import com.bitdubai.sub_app.fan_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 17/05/16.
 */
public class ListUserIdentiesFragment extends AbstractFermatFragment<FanCommunitySubAppSession, SubAppResourcesProviderManager> implements SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<FanCommunitySelectableIdentity> {

    public static final String ACTOR_SELECTED = "actor_selected";
    private static final int MAX = 20;
    protected final String TAG = "ListUserIdentiesFragment";
    private int offset = 0;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout empty;
    private boolean isRefreshing = false;
    private View rootView;
    private AppSelectableIdentitiesListAdapter adapter;
    private LinearLayout emptyView;
    private FanCommunityModuleManager moduleManager;
    private ErrorManager errorManager;
    private List<FanCommunitySelectableIdentity> fanCommunitySelectableIdentities;

    public static ListUserIdentiesFragment newInstance(){
        return new ListUserIdentiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        fanCommunitySelectableIdentities = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.afc_fragment_list_identities, container, false);
            try {
                fanCommunitySelectableIdentities = moduleManager.listSelectableIdentities();
            } catch (final CantListIdentitiesToSelectException cantListIdentitiesToSelectException) {
                appSession.getErrorManager().reportUnexpectedUIException(
                        UISource.ADAPTER,
                        UnexpectedUIExceptionSeverity.UNSTABLE,
                        cantListIdentitiesToSelectException
                );
            }

            adapter = new AppSelectableIdentitiesListAdapter(getActivity(), fanCommunitySelectableIdentities);
            adapter.setFermatListEventListener(this);

            adapter.changeDataSet(fanCommunitySelectableIdentities);

            recyclerView = (RecyclerView) rootView.findViewById(R.id.afcrecycler_view);
            emptyView = (LinearLayout) rootView.findViewById(R.id.afc_empty_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(
                    getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }


    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClickListener(FanCommunitySelectableIdentity data, int position) {
        moduleManager.setSelectedActorIdentity(data);
        changeActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(FanCommunitySelectableIdentity data, int position) {
        moduleManager.setSelectedActorIdentity(data);
        changeActivity(Activities.ART_SUB_APP_FAN_COMMUNITY_CONNECTION_WORLD.getCode(), appSession.getAppPublicKey());
    }
}
