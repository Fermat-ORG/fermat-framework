package org.fermat.fermat_dap_android_wallet_asset_user.fragments;


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
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import org.fermat.fermat_dap_android_wallet_asset_user.adapters.AssetTransferSelectUsersAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.models.Data;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.AssetUserSession;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.SessionConstantsAssetUser;
import org.fermat.fermat_dap_android_wallet_asset_user.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Jinmy Bohorquez on 18/02/2016.
 */
public class AssetTransferSelectUserFragment extends FermatWalletListFragment<User>
        implements FermatListItemListeners<User> {

    // Constants
    private static final String TAG = "AssetTransferSelectUsersFragment";

    // Fermat Managers
    private AssetUserWalletSubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<User> users;

    SettingsManager<AssetUserSettings> settingsManager;

    //UI
    private View noUsersView;
    private Toolbar toolbar;

    public static AssetTransferSelectUserFragment newInstance() {
        return new AssetTransferSelectUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((AssetUserSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();

            settingsManager = appSession.getModuleManager().getSettingsManager();

            users = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
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

        noUsersView = layout.findViewById(R.id.dap_wallet_asset_transfer_no_users);

        showOrHideNoUsersView(users.isEmpty());
    }

    private void setUpHelpAssetRedeem(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_user_wallet)
                    .setIconRes(R.drawable.asset_user_wallet)
                    .setVIewColor(R.color.dap_user_view_color)
                    .setTitleTextColor(R.color.dap_user_view_color)
                    .setSubTitle(R.string.dap_user_wallet_user_select_subTitle)
                    .setBody(R.string.dap_user_wallet_user_select_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_TRANSFER_SELECT, 0, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_TRANSFER_SELECT) {
                setUpHelpAssetRedeem(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
        toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.dap_user_wallet_principal));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.dap_user_wallet_principal));
            }
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_wallet_asset_user_asset_transfer_select_users;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_user_asset_transfer_select_users_activity_recycler_view;
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
                if (adapter != null)
                    adapter.changeDataSet(users);

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
            adapter = new AssetTransferSelectUsersAdapter(getActivity(), users, moduleManager);
            adapter.setFermatListEventListener(this);
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
        //TODO select user
       //appSession.setData("user_selected", data);
        //changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_TRANSFER_ACTIVITY, appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(User data, int position) {
    }

    @Override
    public List<User> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<User> users = new ArrayList<>();
        if (moduleManager != null) {
            try {
                DigitalAsset digitalAsset = (DigitalAsset) appSession.getData("asset_data");
                users = Data.getConnectedUsers(moduleManager);
                appSession.setData("users_to_transfer", users);
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.DAP_ASSET_USER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).
                    show();
        }
        return users;
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
}

