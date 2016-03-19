package com.bitdubai.sub_app.wallet_manager.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
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
public class CommunitiesExpandibleFragment extends FermatWalletExpandableListFragment<GrouperItem,DesktopSession,ResourceProviderManager>
        implements FermatListItemListeners<InstalledApp> {



    private View rootView;
    private List<GrouperItem> grouperList;
    private List<InstalledApp> installedApps;


    public static CommunitiesExpandibleFragment newInstance() {
        return new CommunitiesExpandibleFragment();
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

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

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
            adapter = new CommunitiesExpandableAdapter(getActivity(), grouperList,getResources());
            // setting up event listeners
            adapter.setChildItemFermatEventListeners(this);
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
        Map<Platforms,List<InstalledApp>> listMap = new HashMap<>();
        try {
            for (InstalledApp installedApp : installedApps) {
                if(listMap.containsKey(installedApp.getPlatform())){
                    listMap.get(installedApp.getPlatform()).add(installedApp);
                }else{
                    List<InstalledApp> list = new ArrayList<>();
                    list.add(installedApp);
                    listMap.put(installedApp.getPlatform(),list);
                }

            }

            for (Platforms platforms : listMap.keySet()) {
                GrouperItem<InstalledApp, Platforms> grouperItem = new GrouperItem<InstalledApp, Platforms>(listMap.get(platforms), false, platforms);
                data.add(grouperItem);
            }

//                if(!data.isEmpty()){
//                    FermatAnimationsUtils.showEmpty(getActivity(),false,emptyListViewsContainer);
//                }
        } catch (Exception e){
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
        ((DesktopAppSelector)getActivity()).selectSubApp((com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp) data);
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
                if (adapter != null)
                    adapter.changeDataSet(grouperList);
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



    private void loadProvisoryData(){
        installedApps = new ArrayList<>();

        com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp installedSubApp = new InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY,null,null,"intra_user_community_sub_app","Wallet Users","public_key_intra_user_commmunity","intra_user_community_sub_app",new Version(1,0,0),Platforms.CRYPTO_CURRENCY_PLATFORM);
        installedSubApp.setIconResource(R.drawable.cryptou_community);
        installedSubApp.setBanner(R.drawable.crypto_wallet_user_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_ISSUER, null, null, "sub-app-asset-community-issuer", "Asset Issuers", "public_key_dap_issuer_community", "sub-app-asset-community-issuer", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM);
        installedSubApp.setIconResource(R.drawable.aissuer_community);
        installedSubApp.setBanner(R.drawable.asset_issuer_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_USER, null, null, "sub-app-asset-community-user", "Asset Users", "public_key_dap_user_community", "sub-app-asset-community-user", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM);
        installedSubApp.setIconResource(R.drawable.auser_community);
        installedSubApp.setBanner(R.drawable.asset_user_community);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT, null, null, "sub-app-asset-community-redeem-point", "Redeem Points", "public_key_dap_redeem_point_community", "sub-app-asset-community-redeem-point", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM);
        installedSubApp.setIconResource(R.drawable.reddem_point_community);
        installedSubApp.setBanner(R.drawable.r_point_comuunity);
        installedApps.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_broker_community",
                "Brokers",
                "public_key_crypto_broker_community",
                "sub_app_crypto_broker_community",
                new Version(1, 0, 0),
                Platforms.CRYPTO_BROKER_PLATFORM);
        installedSubApp.setIconResource(R.drawable.crypto_broker_community_final);
        installedSubApp.setBanner(R.drawable.crypto_broker_community_final);
        installedApps.add(installedSubApp);




        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_customer_community",
                "Customers",
                "public_key_crypto_customer_community",
                "sub_app_crypto_customer_community",
                new Version(1, 0, 0),
                Platforms.CRYPTO_BROKER_PLATFORM);
        installedSubApp.setIconResource(R.drawable.crypto_customer_community_final);
        installedSubApp.setBanner(R.drawable.crypto_customer_community_final);
        installedApps.add(installedSubApp);

    }


}
