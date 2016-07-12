package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Data;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.UserDelivery;
import org.fermat.fermat_dap_android_wallet_asset_issuer.sessions.SessionConstantsAssetIssuer;
import org.fermat.fermat_dap_android_wallet_asset_issuer.util.CommonLogger;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters.StatsAdapter;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters.StatsSpinnerAdapter;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.filters.StatsAdapterFilter;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 12/22/15.
 */
public class StatsFragment extends FermatWalletListFragment<UserDelivery, ReferenceAppFermatSession<AssetIssuerWalletSupAppModuleManager>, ResourceProviderManager> {

    // Constants
    private static final String TAG = "StatsFragment";

    // Fermat Managers
    private AssetIssuerWalletSupAppModuleManager moduleManager;
    private ErrorManager errorManager;
    // Data
    private List<UserDelivery> users;
    private DigitalAsset digitalAsset;
    //UI
    private View noUsersView;
    private Spinner spinner;

    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            errorManager = appSession.getErrorManager();

            moduleManager = appSession.getModuleManager();

            users = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.DAP_ASSET_ISSUER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        configureToolbar();
        noUsersView = layout.findViewById(R.id.dap_wallet_asset_issuer_stats_no_users);
        showOrHideNoUsersView(users.isEmpty());
        onRefresh();
    }

    private void setUpHelpAssetDeliveryList(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_issuer_wallet)
                    .setIconRes(R.drawable.asset_issuer)
                    .setVIewColor(R.color.dap_issuer_view_color)
                    .setTitleTextColor(R.color.dap_issuer_view_color)
                    .setSubTitle(R.string.dap_issuer_wallet_delivery_list_subTitle)
                    .setBody(R.string.dap_issuer_wallet_delivery_list_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
                case 1://IC_ACTION_ISSUER_HELP_DELIVER
                    setUpHelpAssetDeliveryList(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
            }

            if (id == SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_DELIVER) {
                setUpHelpAssetDeliveryList(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getActivity(), R.string.dap_issuer_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        spinner = new Spinner(getActivity());
        ArrayAdapter<String> myAdapter = new StatsSpinnerAdapter(getActivity(), R.layout.dap_wallet_asset_issuer_stats_spinner_item, Data.getStatsOptions());
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getAdapter().changeDataSet(users);
                ((StatsAdapterFilter) ((StatsAdapter) getAdapter()).getFilter()).filter((CharSequence) spinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toolbar.addView(spinner);
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.dap_issuer_wallet_v3_toolbar));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.dap_issuer_wallet_v3_toolbar));
            }
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_wallet_asset_issuer_stats;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_issuer_stats_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                users = (ArrayList) result[0];
                if (adapter != null) {
                    adapter.changeDataSet(users);
                    ((StatsAdapterFilter) ((StatsAdapter) getAdapter()).getFilter()).filter(spinner.getSelectedItem().toString());
                }

                showOrHideNoUsersView(users.isEmpty());
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new StatsAdapter(getActivity(), users, moduleManager);
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    private void showOrHideNoUsersView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noUsersView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noUsersView.setVisibility(View.GONE);
        }
    }

    @Override
    public List<UserDelivery> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<UserDelivery> users = new ArrayList<>();
        if (moduleManager != null) {
            try {
                if (digitalAsset == null)
                    digitalAsset = (DigitalAsset) appSession.getData("asset_data");
                users = Data.getStats(WalletUtilities.WALLET_PUBLIC_KEY, digitalAsset, moduleManager);
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.DAP_ASSET_ISSUER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            Toast.makeText(getActivity(),
                    R.string.dap_issuer_wallet_system_error,
                    Toast.LENGTH_SHORT).
                    show();
        }
        return users;
    }
}
