package org.fermat.fermat_dap_android_wallet_asset_user.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
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

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
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
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.adapters.AssetTransferUserAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.SessionConstantsAssetUser;
import org.fermat.fermat_dap_android_wallet_asset_user.util.CommonLogger;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.common.data.DataManager;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Penelope Quintero on 18/02/2016.
 */
public class AssetTransferUserFragment extends FermatWalletListFragment<User, ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager>, ResourceProviderManager>
        implements FermatListItemListeners<User> {

    // Constants
    private static final String TAG = "AssetTransferSelectUsersFragment";

    // Fermat Managers
    private AssetUserWalletSubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    // Data
    private List<User> users;
    private User userSelected;
    private Asset assetToTransfer;
    String digitalAssetPublicKey;
    private DigitalAsset digitalAsset;
    //UI
    private View noUsersView;
    private Toolbar toolbar;
    private Activity activity;
    private FermatTextView titleUser;

    public static AssetTransferUserFragment newInstance() {
        return new AssetTransferUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            try {

                assetToTransfer = (Asset) appSession.getData("asset_data");
                digitalAssetPublicKey = assetToTransfer.getDigitalAsset().getPublicKey();

//                digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);

            } catch (Exception e) {
                e.printStackTrace();
            }

            users = getMoreDataAsync(FermatRefreshTypes.NEW, 0);

            activity = getActivity();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.DAP_ASSET_USER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();

        noUsersView = layout.findViewById(R.id.dap_wallet_asset_user_transfer_no_users);
        titleUser = (FermatTextView) layout.findViewById(R.id.select_user_to_transfer);

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
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
//                case 1://IC_ACTION_USER_HELP_TRANSFER_SELECT
//                    setUpHelpAssetRedeem(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                    break;
                case 2://IC_ACTION_USER_ASSET_TRANSFER
                    if (userSelected != null) {

                        new ConfirmDialog.Builder(getActivity(), appSession)
                                .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                                .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_transfer))
                                .setColorStyle(getResources().getColor(R.color.card_toolbar))
                                .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                    @Override
                                    public void onClick() {
                                        doTransfer(assetToTransfer.getDigitalAsset(), users, 1);
                                    }
                                }).build().show();

                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_validate_no_user), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_TRANSFER_SELECT) {
                setUpHelpAssetRedeem(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            } else if (id == SessionConstantsAssetUser.IC_ACTION_USER_ASSET_TRANSFER) {
                if (userSelected != null) {

                    new ConfirmDialog.Builder(getActivity(), appSession)
                            .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                            .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_transfer))
                            .setColorStyle(getResources().getColor(R.color.card_toolbar))
                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                @Override
                                public void onClick() {
                                    doTransfer(assetToTransfer.getDigitalAsset(), users, 1);
                                }
                            }).build().show();

                } else {
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_validate_no_user), Toast.LENGTH_SHORT).show();
                }

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
            toolbar.setBackgroundColor(getResources().getColor(R.color.card_toolbar));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.card_toolbar));
            }
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_v3_wallet_asset_user_asset_transfer_select_user;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh_v3_user;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_user_asset_trasnfer_select_user_activity_recycler_view;
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
            adapter = new AssetTransferUserAdapter(getActivity(), users, moduleManager);
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

        for (int i = 0; i < users.size(); i++) {
            if (i != position) {
                users.get(i).setSelected(false);
            }
        }
        users.get(position).setSelected(!users.get(position).isSelected());

        if (users.get(position).isSelected()) {
            userSelected = data;
        } else {
            userSelected = null;
        }
        getAdapter().changeDataSet(users);
    }

    @Override
    public void onLongItemClickListener(User data, int position) {
    }

    @Override
    public List<User> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<User> users = new ArrayList<>();
        if (moduleManager != null) {
            try {
//                users = Data.getConnectedUsers(moduleManager);
                users = DataManager.getConnectedUsers();
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
            titleUser.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noUsersView.setVisibility(View.GONE);
            titleUser.setVisibility(View.VISIBLE);
        }
    }

    private void doTransfer(final org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset asset, final List<User> users, final int assetAmount) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                for (User user : users) {
                    if (user.isSelected()) {
                        moduleManager.addUserToDeliver(user.getActorAssetUser());
                    }
                }
                moduleManager.transferAssets(asset, WalletUtilities.WALLET_PUBLIC_KEY, assetAmount);
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
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_transfer_ok), Toast.LENGTH_LONG).show();
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_V3_HOME, appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null)
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_exception_retry),
                            Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
    }
}

