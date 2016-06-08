package com.bitdubai.sub_app.wallet_manager.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.DesktopAppSelector;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_wpd.wallet_manager.R;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.wallet_manager.adapter.CommunitiesScreenAdapter;
import com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.03.10..
 */
public class CommunitiesFragment extends FermatWalletListFragment<InstalledApp,ReferenceAppFermatSession,ResourceProviderManager> implements FermatListItemListeners<InstalledApp> {


    private static final Integer MAX_USER_SHOW = 20;
    private int offset = 0;
    private CryptoWallet moduleManager;
    private ErrorManager errorManager;
    private ArrayList<InstalledApp> lstInstalledApp;


    public static CommunitiesFragment newInstance() {
        return new CommunitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module

            lstInstalledApp = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        try {
            //RecyclerView.ItemDecoration itemDecoration = new ItemDecorationAlbumColumns(20,300);
            //recyclerView.addItemDecoration(itemDecoration);

            layout.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });

        } catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Intra User List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.communities_base;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return 0;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.communities_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    public void onPostExecute(Object... result) {
        if (isAttached) {
            if (result != null && result.length > 0) {
                lstInstalledApp = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstInstalledApp);
            }
        }
    }


    @Override
    public void onErrorOccurred(Exception ex) {
        if (isAttached) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new CommunitiesScreenAdapter(getActivity(), lstInstalledApp);
            adapter.setFermatListEventListener(this); // setting up event listeners
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new GridLayoutManager(getActivity(),2);
        }
        return layoutManager;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getActivity(), "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public List<InstalledApp> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<InstalledApp> data = new ArrayList<>();

        com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp installedSubApp;

        installedSubApp = new InstalledSubApp(
                SubApps.CCP_INTRA_USER_COMMUNITY,
                null,
                null,
                "intra_user_community_sub_app",
                "Wallet Users",
                SubAppsPublicKeys.CCP_COMMUNITY.getCode(),
                "intra_user_community_sub_app",
                new Version(1,0,0),
                Platforms.CRYPTO_CURRENCY_PLATFORM, AppsStatus.DEV);

        installedSubApp.setIconResource(R.drawable.cryptou_community);
        data.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_COMMUNITY_ISSUER,
                null,
                null,
                "sub-app-asset-community-issuer",
                "Asset Issuers",
                SubAppsPublicKeys.DAP_COMMUNITY_ISSUER.getCode(),
                "sub-app-asset-community-issuer",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);

        installedSubApp.setIconResource(R.drawable.aissuer_community);
        data.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_COMMUNITY_USER,
                null,
                null,
                "sub-app-asset-community-user",
                "Asset Users",
                SubAppsPublicKeys.DAP_COMMUNITY_USER.getCode(),
                "sub-app-asset-community-user",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);

        installedSubApp.setIconResource(R.drawable.auser_community);
        data.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT,
                null,
                null,
                "sub-app-asset-community-redeem-point",
                "Redeem Points",
                SubAppsPublicKeys.DAP_COMMUNITY_REDEEM.getCode(),
                "sub-app-asset-community-redeem-point",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);

        installedSubApp.setIconResource(R.drawable.reddem_point_community);
        data.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_broker_community",
                "Brokers",
                SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode(),
                "sub_app_crypto_broker_community",
                new Version(1, 0, 0)
                ,Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.cbroker_community);
        data.add(installedSubApp);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_customer_community",
                "Customers",
                SubAppsPublicKeys.CBP_CUSTOMER_COMMUNITY.getCode(),
                "sub_app_crypto_customer_community",
                new Version(1, 0, 0),
                Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.ccustomer_community);
        data.add(installedSubApp);

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
                AppsStatus.ALPHA);

        installedSubApp.setIconResource(R.drawable.communities_bar);
        data.add(installedSubApp);

        return data;
    }

    @Override
    public void onItemClickListener(InstalledApp data, int position) {
        //intraUserIdentitySubAppSession.setData(SessionConstants.IDENTITY_SELECTED,data);
        //changeActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
        //data.setSelected(!data.isSelected());
        //adapter.notifyDataSetChanged();

        ((DesktopAppSelector)getActivity()).selectSubApp((com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp) data);

    }

    @Override
    public void onLongItemClickListener(InstalledApp data, int position) {

    }

}
