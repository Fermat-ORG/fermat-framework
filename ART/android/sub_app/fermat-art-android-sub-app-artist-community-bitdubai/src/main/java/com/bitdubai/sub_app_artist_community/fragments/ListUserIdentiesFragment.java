package com.bitdubai.sub_app_artist_community.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySubAppModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.adapters.AppSelectableIdentitiesListAdapter;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSessionReferenceApp;
import com.bitdubai.sub_app_artist_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 17/05/16.
 */
public class ListUserIdentiesFragment extends AbstractFermatFragment<ReferenceAppFermatSession<ArtistCommunitySubAppModuleManager>, SubAppResourcesProviderManager> implements SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<ArtistCommunitySelectableIdentity> {

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
    private ArtistCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private List<ArtistCommunitySelectableIdentity> artistCommunitySelectableIdentities;

    public static ListUserIdentiesFragment newInstance(){
        return new ListUserIdentiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        artistCommunitySelectableIdentities = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.aac_fragment_list_identities, container, false);
            try {
                artistCommunitySelectableIdentities = moduleManager.listSelectableIdentities();
            } catch (final CantListIdentitiesToSelectException cantListIdentitiesToSelectException) {
                appSession.getErrorManager().reportUnexpectedUIException(
                        UISource.ADAPTER,
                        UnexpectedUIExceptionSeverity.UNSTABLE,
                        cantListIdentitiesToSelectException
                );
            }

            adapter = new AppSelectableIdentitiesListAdapter(getActivity(), artistCommunitySelectableIdentities);
            adapter.setFermatListEventListener(this);

            adapter.changeDataSet(artistCommunitySelectableIdentities);

            recyclerView = (RecyclerView) rootView.findViewById(R.id.accrecycler_view);
            emptyView = (LinearLayout) rootView.findViewById(R.id.aac_empty_view);
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
        configureToolbar();
        return rootView;
    }


    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClickListener(ArtistCommunitySelectableIdentity data, int position) {
        moduleManager.setSelectedActorIdentity(data);
        changeActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(ArtistCommunitySelectableIdentity data, int position) {
        moduleManager.setSelectedActorIdentity(data);
        changeActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_WORLD.getCode(), appSession.getAppPublicKey());
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.degrade_colorj, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.degrade_colorj));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }
}
