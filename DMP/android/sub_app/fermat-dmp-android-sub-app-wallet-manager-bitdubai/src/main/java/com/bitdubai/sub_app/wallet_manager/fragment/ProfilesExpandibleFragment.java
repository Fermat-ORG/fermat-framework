package com.bitdubai.sub_app.wallet_manager.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FontType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.DesktopAppSelector;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.adapter.CommunitiesExpandableAdapter;
import com.bitdubai.sub_app.wallet_manager.commons.model.GrouperItem;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;
import com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mati on 2016.03.14..
 */
public class ProfilesExpandibleFragment extends FermatWalletExpandableListFragment<GrouperItem, DesktopSession, ResourceProviderManager>
        implements FermatListItemListeners<InstalledApp> {


    private View rootView;
    private List<GrouperItem> grouperList;
    private List<InstalledApp> installedApps;
    private boolean isScrolled;


    public static ProfilesExpandibleFragment newInstance() {
        return new ProfilesExpandibleFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        loadProvisoryData();

        grouperList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 12;
            }
        };
        recyclerView.addItemDecoration(itemDecoration);

        layout.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ((FermatTextView)layout.findViewById(R.id.txt_title_communities)).setFont(FontType.CAVIAR_DREAMS);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!isScrolled){
                    isScrolled = true;
                    adapter.expandAllParents();
                }
            }
        });
//        if (intalledAppsList.isEmpty()) {
//            recyclerView.setVisibility(View.GONE);
//            emptyListViewsContainer =(LinearLayout) layout.findViewById(R.id.empty);
//            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
        //emptyListViewsContainer.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public ExpandableRecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new CommunitiesExpandableAdapter(getActivity(), grouperList, getResources());
            // setting up event listeners
            adapter.setChildItemFermatEventListeners(this);
            adapter.expandAllParents();
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null)
            layoutManager = new LinearLayoutManager(getActivity());
        return layoutManager;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.communities_base;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.communities_recycler_view;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return 0;
    }


    @Override
    public List<GrouperItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<GrouperItem> data = new ArrayList<>();
        Map<Platforms, List<InstalledApp>> listMap = new HashMap<>();
        try {
            for (InstalledApp installedApp : installedApps) {
                if (listMap.containsKey(installedApp.getPlatform())) {
                    listMap.get(installedApp.getPlatform()).add(installedApp);
                } else {
                    List<InstalledApp> list = new ArrayList<>();
                    list.add(installedApp);
                    listMap.put(installedApp.getPlatform(), list);
                }

            }

            for (Platforms platforms : listMap.keySet()) {
                GrouperItem<InstalledApp, Platforms> grouperItem = new GrouperItem<InstalledApp, Platforms>(listMap.get(platforms), false, platforms);
                data.add(grouperItem);
            }

//                if(!data.isEmpty()){
//                    FermatAnimationsUtils.showEmpty(getActivity(),false,emptyListViewsContainer);
//                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(InstalledApp data, int position) {
        ((DesktopAppSelector) getActivity()).selectSubApp((com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp) data);
    }

    @Override
    public void onLongItemClickListener(InstalledApp data, int position) {
    }

    public void setInstalledApps(List<InstalledApp> installedApps) {
        this.installedApps = installedApps;
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                grouperList = (ArrayList) result[0];
                if (adapter != null) {
                    adapter.changeDataSet(grouperList);
                    adapter.expandAllParents();
                    adapter.expandParent(0);
                }
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            ex.printStackTrace();
        }
    }


    private void loadProvisoryData() {
        installedApps = new ArrayList<>();

        InstalledApp installedSubApp;

        installedSubApp = new InstalledSubApp(
                SubApps.CCP_INTRA_USER_COMMUNITY,
                null,
                null,
                "intra_user_community_sub_app",
                "Wallet Users",
                SubAppsPublicKeys.CCP_COMMUNITY.getCode(),
                "intra_user_community_sub_app",
                new Version(1,0,0),
                Platforms.CRYPTO_CURRENCY_PLATFORM, AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.crypto_wallet_user_community);
        installedSubApp.setBanner(R.drawable.wallet_user_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_COMMUNITY_ISSUER,
                null,
                null,
                "sub-app-asset-community-issuer",
                "Asset Issuers",
                SubAppsPublicKeys.DAP_COMMUNITY_ISSUER.getCode(),
                "sub-app-asset-community-issuer",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.aissuer_community);
        installedSubApp.setBanner(R.drawable.asset_issuer_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_COMMUNITY_USER,
                null,
                null,
                "sub-app-asset-community-user",
                "Asset Users",
                SubAppsPublicKeys.DAP_COMMUNITY_USER.getCode(),
                "sub-app-asset-community-user",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.auser_community);
        installedSubApp.setBanner(R.drawable.asset_user_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT,
                null,
                null,
                "sub-app-asset-community-redeem-point",
                "Redeem Points",
                SubAppsPublicKeys.DAP_COMMUNITY_REDEEM.getCode(),
                "sub-app-asset-community-redeem-point",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.r_point_community);
        installedSubApp.setBanner(R.drawable.redeem_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_broker_community",
                "Brokers",
                SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode(),
                "sub_app_crypto_broker_community",
                new Version(1, 0, 0),
                Platforms.CRYPTO_BROKER_PLATFORM,
                AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.crypto_broker_community);
        installedSubApp.setBanner(R.drawable.broker_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_customer_community",
                "Customers",
                SubAppsPublicKeys.CBP_CUSTOMER_COMMUNITY.getCode(),
                "sub_app_crypto_customer_community",
                new Version(1, 0, 0),
                Platforms.CRYPTO_BROKER_PLATFORM,
                AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.crypto_customer_community);
        installedSubApp.setBanner(R.drawable.customer_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.CHT_COMMUNITY,
                null,
                null,
                "sub_app_cht_community",
                "Chat Users",
                SubAppsPublicKeys.CHT_COMMUNITY.getCode(),
                "sub_app_cht_community",
                new Version(1, 0, 0),
                Platforms.CHAT_PLATFORM,
                AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.chat_community);
        installedSubApp.setBanner(R.drawable.chat_banner_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.ART_ARTIST_COMMUNITY,
                null,
                null,
                "sub_app_artist_community",
                "Artist",
                SubAppsPublicKeys.ART_ARTIST_COMMUNITY.getCode(),
                "sub_app_artist_community",
                new Version(1, 0, 0),
                Platforms.ART_PLATFORM,
                AppsStatus.DEV);

        installedSubApp.setIconResource(R.drawable.artist_banner);
        installedSubApp.setBanner(R.drawable.artist_banner);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.ART_FAN_COMMUNITY,
                null,
                null,
                "sub_app_art_fan_community",
                "Fans",
                SubAppsPublicKeys.ART_FAN_COMMUNITY.getCode(),
                "sub_app_art_fan_community",
                new Version(1, 0, 0),
                Platforms.ART_PLATFORM,
                AppsStatus.DEV);

        installedSubApp.setIconResource(R.drawable.communities_icon);
        installedSubApp.setBanner(R.drawable.communities_bar);
        installedApps.add(installedSubApp);


        ArrayList list = new ArrayList();
        AppsStatus appsStatus = getFermatActivityManager().getAppStatus();

        for (InstalledApp installedApp : installedApps) {
            if(installedApp.getAppStatus()==appsStatus){
                list.add(installedApp);
            }
        }
        installedApps = list;

    }
}
