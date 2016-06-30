package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Data;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Group;
import org.fermat.fermat_dap_android_wallet_asset_issuer.util.CommonLogger;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters.DeliverGroupAdapter;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.filters.DeliverGroupAdapterFilter;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliverGroupFragment extends FermatWalletListFragment<Group, ReferenceAppFermatSession<AssetIssuerWalletSupAppModuleManager>, ResourceProviderManager>
        implements FermatListItemListeners<Group> {

    // Constants
    private static final String TAG = "AssetDeliverySelectGroupsFragment";
    // Fermat Managers
    private AssetIssuerWalletSupAppModuleManager moduleManager;
    private ErrorManager errorManager;
    // Data
    private List<Group> groups;
    //UI
    private View noGroupsView;
    private DigitalAsset digitalAsset;
    private SearchView searchView;
    private RelativeLayout buttonPanelLayout;
    private FermatButton okButton;

    private int menuItemSize;

    public static DeliverGroupFragment newInstance() {
        return new DeliverGroupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            errorManager = appSession.getErrorManager();
            moduleManager = appSession.getModuleManager();

            groups = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
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

        noGroupsView = layout.findViewById(R.id.dap_wallet_asset_issuer_delivery_no_groups);

        buttonPanelLayout = (RelativeLayout) layout.findViewById(R.id.buttonPanelLayout);
        okButton = (FermatButton) layout.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                searchView.setIconified(true);
            }
        });

        digitalAsset = (DigitalAsset) appSession.getData("asset_data");

//        if (swipeRefreshLayout != null) {
//            swipeRefreshLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    onRefresh();
//                }
//            });
//        }

        buttonPanelLayout.setVisibility(View.GONE);

        showOrHideNoUsersView(groups.isEmpty());

        onRefresh();
    }

    private void setUpHelpAssetDeliverUsers(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_issuer_wallet)
                    .setIconRes(R.drawable.asset_issuer)
                    .setVIewColor(R.color.dap_issuer_view_color)
                    .setTitleTextColor(R.color.dap_issuer_view_color)
                    .setSubTitle(R.string.dap_issuer_wallet_redeemed_users_subTitle)
                    .setBody(R.string.dap_issuer_wallet_redeemed_users_body)
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
//        inflater.inflate(R.menu.dap_asset_issuer_deliver_menu, menu);
//        searchView = (SearchView) menu.findItem(R.id.action_wallet_issuer_deliver_search).getActionView();
        if (menuItemSize == 0 || menuItemSize == menu.size()) {
            menuItemSize = menu.size();

            searchView = (SearchView) menu.findItem(1).getActionView();
            searchView.setQueryHint(getResources().getString(R.string.dap_issuer_wallet_search_deliver_group_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    buttonPanelLayout.setVisibility((s.length() > 0) ? View.VISIBLE : View.GONE);
                    if (s.equals(searchView.getQuery().toString())) {
                        ((DeliverGroupAdapterFilter) ((DeliverGroupAdapter) getAdapter()).getFilter()).filter(s);
                    }
                    return false;
                }
            });
        }
//        menu.add(0, SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_GROUP, 0, "Help")
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        menu.add(1, SessionConstantsAssetIssuer.IC_ACTION_ISSUER_DELIVER, 1, "")
//                .setIcon(R.drawable.ic_send)
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
                case 2://IC_ACTION_ISSUER_DELIVER
                    if (validateDistributeToGroups()) {
                        new ConfirmDialog.Builder(getActivity(), appSession)
                                .setTitle(getResources().getString(R.string.dap_issuer_wallet_confirm_title))
                                .setMessage(getResources().getString(R.string.dap_issuer_wallet_v3_distribute_confirm))
                                .setColorStyle(getResources().getColor(R.color.dap_issuer_wallet_v3_dialog))
                                .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                    @Override
                                    public void onClick() {
                                        doDistributeToGroups(digitalAsset.getAssetPublicKey(), getSelectedGroups(), getSelectedGroupsCount());
                                    }
                                }).build().show();
                    }
                    break;
                case 3://IC_ACTION_ISSUER_HELP_GROUP
                    setUpHelpAssetDeliverUsers(appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
            }


//            if (id == SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_GROUP) {
//                setUpHelpAssetDeliverUsers(appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            } else if (id == SessionConstantsAssetIssuer.IC_ACTION_ISSUER_DELIVER) {
//                if (validateDistributeToGroups()) {
//                    new ConfirmDialog.Builder(getActivity(), appSession)
//                            .setTitle(getResources().getString(R.string.dap_issuer_wallet_confirm_title))
//                            .setMessage(getResources().getString(R.string.dap_issuer_wallet_v3_distribute_confirm))
//                            .setColorStyle(getResources().getColor(R.color.dap_issuer_wallet_v3_dialog))
//                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
//                                @Override
//                                public void onClick() {
//                                    doDistributeToGroups(digitalAsset.getAssetPublicKey(), getSelectedGroups(), getSelectedGroupsCount());
//                                }
//                            }).build().show();
//                }
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_issuer_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private int getSelectedGroupsCount() {
        int count = 0;
        for (Group group : groups) {
            if (group.isSelected()) count++;
        }
        return count;
    }

    private List<Group> getSelectedGroups() {
        List<Group> selectedUsers = new ArrayList<>();
        for (Group group : groups) {
            if (group.isSelected()) selectedUsers.add(group);
        }
        return selectedUsers;
    }

    private boolean thereIsGroupSelected() {
        for (Group group : groups) {
            if (group.isSelected()) return true;
        }
        return false;
    }

    private boolean validateDistributeToGroups() {
        if (!thereIsGroupSelected()) {
            Toast.makeText(getActivity(), R.string.dap_issuer_wallet_validate_no_users_groups, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        try {
//            IssuerWalletNavigationViewPainter navigationViewPainter = new IssuerWalletNavigationViewPainter(getActivity(), null);
//            getPaintActivtyFeactures().addNavigationView(navigationViewPainter);
//        } catch (Exception e) {
//            makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
//            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
//        }
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
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
        return R.layout.dap_wallet_asset_issuer_deliver_group;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_issuer_asset_delivery_select_groups_activity_recycler_view;
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
                groups = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(groups);

                showOrHideNoUsersView(groups.isEmpty());
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
            adapter = new DeliverGroupAdapter(getActivity(), groups, moduleManager);
            adapter.setFermatListEventListener(this);
        } else {
            adapter.changeDataSet(groups);
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

    @Override
    public void onItemClickListener(Group data, int position) {
        if (data.isSelected() || digitalAsset.getAvailableBalanceQuantity() > getSelectedGroupsCount()) {
            data.setSelected(!data.isSelected());
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), R.string.dap_issuer_wallet_select_group, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLongItemClickListener(Group data, int position) {
    }

    @Override
    public List<Group> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<Group> groups = new ArrayList<>();
        if (moduleManager != null) {
            try {
                groups = Data.getGroups(moduleManager, groups);

                appSession.setData("groups", groups);

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
        return groups;
    }

    private void showOrHideNoUsersView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noGroupsView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noGroupsView.setVisibility(View.GONE);
        }
    }

    private void doDistributeToGroups(final String assetPublicKey, final List<Group> groups, final int assetsAmount) {
        final Activity activity = getActivity();
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_issuer_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                for (Group group : groups) {
                    if (group.isSelected()) {
                        moduleManager.addGroupToDeliver(group.getActorAssetUserGroup());
                    }
                }
                if (groups.size() > 0) {
                    moduleManager.distributionAssets(assetPublicKey, WalletUtilities.WALLET_PUBLIC_KEY, assetsAmount);
                }
                return true;
            }
        };

        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
                    Toast.makeText(activity, R.string.dap_issuer_wallet_deliver_ok, Toast.LENGTH_LONG).show();
                    changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY, appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null) {
                    Toast.makeText(activity, R.string.dap_issuer_wallet_exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        task.execute();
    }
}

