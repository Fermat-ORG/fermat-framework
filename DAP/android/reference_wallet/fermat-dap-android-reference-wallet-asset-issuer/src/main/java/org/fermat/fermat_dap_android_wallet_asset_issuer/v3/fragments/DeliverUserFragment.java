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
import org.fermat.fermat_dap_android_wallet_asset_issuer.models.User;
import org.fermat.fermat_dap_android_wallet_asset_issuer.util.CommonLogger;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters.DeliverUserAdapter;
import org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.filters.DeliverUserAdapterFilter;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliverUserFragment extends FermatWalletListFragment<User, ReferenceAppFermatSession<AssetIssuerWalletSupAppModuleManager>, ResourceProviderManager>
        implements FermatListItemListeners<User> {

    // Constants
    private static final String TAG = "AssetDeliverySelectUsersFragment";

    // Fermat Managers
    private AssetIssuerWalletSupAppModuleManager moduleManager;
    private ErrorManager errorManager;
    // Data
    private List<User> users;
    //UI
    private View noUsersView;
    private DigitalAsset digitalAsset;
    private SearchView searchView;
    private RelativeLayout buttonPanelLayout;
    private FermatButton okButton;

    private int menuItemSize;

    public static DeliverUserFragment newInstance() {
        return new DeliverUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

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

        noUsersView = layout.findViewById(R.id.dap_wallet_asset_issuer_delivery_user_no_users);

        buttonPanelLayout = (RelativeLayout) layout.findViewById(R.id.buttonPanelLayout);
        okButton = (FermatButton) layout.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                searchView.setIconified(true);
            }
        });

        users = getMoreDataAsync(FermatRefreshTypes.NEW, 0);

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

        showOrHideNoUsersView(users.isEmpty());

        onRefresh();
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
            searchView.setQueryHint(getResources().getString(R.string.dap_issuer_wallet_search_deliver_user_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    buttonPanelLayout.setVisibility((s.length() > 0) ? View.VISIBLE : View.GONE);
                    if (s.equals(searchView.getQuery().toString())) {
                        ((DeliverUserAdapterFilter) ((DeliverUserAdapter) getAdapter()).getFilter()).filter(s);
                    }
                    return false;
                }
            });
        }
//        menu.add(0, SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_USER, 0, "Help")
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
                case 2://IC_ACTION_ISSUER_HELP_USER
                    if (validateDistributeToUsers()) {
                        new ConfirmDialog.Builder(getActivity(), appSession)
                                .setTitle(getResources().getString(R.string.dap_issuer_wallet_confirm_title))
                                .setMessage(getResources().getString(R.string.dap_issuer_wallet_v3_distribute_confirm))
                                .setColorStyle(getResources().getColor(R.color.dap_issuer_wallet_v3_dialog))
                                .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                    @Override
                                    public void onClick() {
                                        doDistributeToUsers(digitalAsset.getAssetPublicKey(), getSelectedUsers(), getSelectedUsersCount());
                                    }
                                }).build().show();
                    }
                    break;
                case 3://IC_ACTION_ISSUER_HELP_USER
                    setUpHelpAssetDeliverUsers(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
            }

//            if (id == SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_USER) {
//                setUpHelpAssetDeliverUsers(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            } else if (id == SessionConstantsAssetIssuer.IC_ACTION_ISSUER_DELIVER) {
//                if (validateDistributeToUsers()) {
//                    new ConfirmDialog.Builder(getActivity(), appSession)
//                            .setTitle(getResources().getString(R.string.dap_issuer_wallet_confirm_title))
//                            .setMessage(getResources().getString(R.string.dap_issuer_wallet_v3_distribute_confirm))
//                            .setColorStyle(getResources().getColor(R.color.dap_issuer_wallet_v3_dialog))
//                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
//                                @Override
//                                public void onClick() {
//                                    doDistributeToUsers(digitalAsset.getAssetPublicKey(), getSelectedUsers(), getSelectedUsersCount());
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

    private boolean thereIsUserSelected() {
        for (User user : users) {
            if (user.isSelected()) return true;
        }
        return false;
    }

    private boolean validateDistributeToUsers() {
        if (!thereIsUserSelected()) {
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
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_wallet_asset_issuer_deliver_user;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_issuer_delivery_user_recycler_view;
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
            adapter = new DeliverUserAdapter(getActivity(), users, moduleManager);
            adapter.setFermatListEventListener(this);
        } else {
            adapter.changeDataSet(users);
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
    public void onItemClickListener(User data, int position) {
        if (data.isSelected() || digitalAsset.getAvailableBalanceQuantity() > getSelectedUsersCount()) {
            data.setSelected(!data.isSelected());
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), R.string.dap_issuer_wallet_select_user, Toast.LENGTH_LONG).show();
        }
    }

    private int getSelectedUsersCount() {
        int count = 0;
        for (User user : users) {
            if (user.isSelected()) count++;
        }
        return count;
    }

    private List<User> getSelectedUsers() {
        List<User> selectedUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isSelected()) selectedUsers.add(user);
        }
        return selectedUsers;
    }

    @Override
    public void onLongItemClickListener(User data, int position) {
    }

    @Override
    public List<User> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<User> users = new ArrayList<>();
        if (moduleManager != null) {
            try {
                users = Data.getConnectedUsers(moduleManager, users);

                appSession.setData("users", users);

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

    private void doDistributeToUsers(final String assetPublicKey, final List<User> usersSelected, final int assetsAmount) {
        final Activity activity = getActivity();
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                for (User user : usersSelected) {
                    moduleManager.addUserToDeliver(user.getActorAssetUser());
                }
                if (usersSelected.size() > 0) {
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
//                    refreshUIData();
                    Toast.makeText(activity, R.string.dap_issuer_wallet_deliver_ok, Toast.LENGTH_LONG).show();
                    changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY, appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null)
                    Toast.makeText(activity, R.string.dap_issuer_wallet_exception,
                            Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
    }
}

