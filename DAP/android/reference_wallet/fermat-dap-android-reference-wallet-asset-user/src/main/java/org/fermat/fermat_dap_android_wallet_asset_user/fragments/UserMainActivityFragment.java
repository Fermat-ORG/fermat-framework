package org.fermat.fermat_dap_android_wallet_asset_user.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.adapters.MyAssetsAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.filters.MyAssetsAdapterFilter;
import org.fermat.fermat_dap_android_wallet_asset_user.models.Data;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.SessionConstantsAssetUser;
import org.fermat.fermat_dap_android_wallet_asset_user.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserMainActivityFragment extends FermatWalletListFragment<DigitalAsset, ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager>, ResourceProviderManager>
        implements FermatListItemListeners<DigitalAsset> {


    private List<AssetNegotiation> assetNegotiations;
    private AssetNegotiation assetNegotiation;

    // Constants
    private static final String TAG = "UserMainActivityFragment";

    // Fermat Managers
    private AssetUserWalletSubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    AssetUserSettings settings = null;
    // Data
    private List<DigitalAsset> digitalAssets;

    //UI
    private View noAssetsView;
    private SearchView searchView;

    public static UserMainActivityFragment newInstance() {
        return new UserMainActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            appSession.setData("redeem_points", null);

            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

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

        //Initialize settings
        try {
            settings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            settings = null;
        }

        if (settings == null) {
            int position = 0;
            settings = new AssetUserSettings();
            settings.setIsContactsHelpEnabled(true);
            settings.setIsPresentationHelpEnabled(true);
            settings.setNotificationEnabled(true);

            settings.setBlockchainNetwork(Arrays.asList(BlockchainNetworkType.values()));
            for (BlockchainNetworkType networkType : Arrays.asList(BlockchainNetworkType.values())) {
                if (networkType.getCode().equals(BlockchainNetworkType.getDefaultBlockchainNetworkType().getCode())) {
                    settings.setBlockchainNetworkPosition(position);
                    break;
                } else {
                    position++;
                }
            }

            try {
                if (moduleManager != null) {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), settings);
                    moduleManager.setAppPublicKey(appSession.getAppPublicKey());
                    moduleManager.changeNetworkType(settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition()));
                }
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        } else {
            if (moduleManager != null) {
                moduleManager.changeNetworkType(settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition()));
            }
        }

        final AssetUserSettings assetUserSettingsTemp = settings;


        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable() {
            public void run() {
                if (assetUserSettingsTemp.isPresentationHelpEnabled()) {
                    setUpPresentation(false);
                }
            }
        }, 500);

        setupBackgroundBitmap(layout);
        configureToolbar();
        noAssetsView = layout.findViewById(R.id.dap_wallet_no_assets);

        digitalAssets = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        showOrHideNoAssetsView(digitalAssets.isEmpty());

        onRefresh();
    }

    private void setUpPresentation(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_user_wallet)
                    .setIconRes(R.drawable.asset_user_wallet)
                    .setImageLeft(R.drawable.profile_actor)
                    .setVIewColor(R.color.dap_user_view_color)
                    .setTitleTextColor(R.color.dap_user_view_color)
                    .setTextNameLeft(R.string.dap_user_wallet_welcome_name_left)
                    .setSubTitle(R.string.dap_user_wallet_welcome_subTitle)
                    .setBody(R.string.dap_user_wallet_welcome_body)
                    .setTextFooter(R.string.dap_user_wallet_welcome_Footer)
                    .setTemplateType((moduleManager.getActiveAssetUserIdentity() == null) ? PresentationDialog.TemplateType.TYPE_PRESENTATION_WITH_ONE_IDENTITY : PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Object o = appSession.getData(SessionConstantsAssetUser.PRESENTATION_IDENTITY_CREATED);
                    if (o != null) {
                        if ((Boolean) (o)) {
                            //invalidate();
                            appSession.removeData(SessionConstantsAssetUser.PRESENTATION_IDENTITY_CREATED);
                        }
                    }
                    try {
                        IdentityAssetUser identityAssetUser = moduleManager.getActiveAssetUserIdentity();
                        if (identityAssetUser == null) {
                            getActivity().onBackPressed();
                        } else {
                            invalidate();
                        }
                    } catch (CantGetIdentityAssetUserException e) {
                        e.printStackTrace();
                    }
                }
            });

            presentationDialog.show();
        } catch (CantGetIdentityAssetUserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dap_wallet_asset_user_home_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.action_wallet_user_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.dap_user_wallet_search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals(searchView.getQuery().toString())) {
                    ((MyAssetsAdapterFilter) ((MyAssetsAdapter) getAdapter()).getFilter()).filter(s);
                }
                return false;
            }
        });
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_PRESENTATION, 2, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_PRESENTATION) {
                setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_user_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        checkIdentity();
    }

//    private void checkIdentity() {
//        ActiveActorIdentityInformation identity = null;
//        try {
//            identity = moduleManager.getSelectedActorIdentity();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (identity == null) {
//            makeText(getActivity(), "Identity must be created",
//                    LENGTH_SHORT).show();
//            getActivity().onBackPressed();
//        }
//    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
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

    private void setupBackgroundBitmap(final View rootView) {
        AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

            WeakReference<ViewGroup> view;

            @Override
            protected void onPreExecute() {
                view = new WeakReference(rootView);
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap drawable = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = true;
                    options.inSampleSize = 5;
                    drawable = BitmapFactory.decodeResource(
                            getResources(), R.drawable.bg_app_image_user, options);
                } catch (OutOfMemoryError error) {
                    error.printStackTrace();
                }
                return drawable;
            }

            @Override
            protected void onPostExecute(Bitmap drawable) {
                if (drawable != null) {
                    view.get().setBackground(new BitmapDrawable(getResources(), drawable));
                }
            }
        };
        asyncTask.execute();
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_wallet_asset_user_my_assets_activity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_issuer_my_assets_activity_recycler_view;
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
                digitalAssets = (ArrayList) result[0];
                if (adapter != null) {
                    adapter.changeDataSet(digitalAssets);
                    ((MyAssetsAdapterFilter) ((MyAssetsAdapter) getAdapter()).getFilter()).filter(searchView.getQuery().toString());
                }
                showOrHideNoAssetsView(digitalAssets.isEmpty());
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
            adapter = new MyAssetsAdapter(getActivity(), digitalAssets, moduleManager);
            adapter.setFermatListEventListener(this);
        } else {
            adapter.changeDataSet(digitalAssets);
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
    public void onItemClickListener(DigitalAsset data, int position) {
        appSession.setData("asset_data", data);
        if (data.getUserAssetNegotiation() != null) {
            changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_NEGOTIATION_DETAIL_ACTIVITY, appSession.getAppPublicKey());
        } else {
            changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_DETAIL, appSession.getAppPublicKey());
        }
    }

    @Override
    public void onLongItemClickListener(DigitalAsset data, int position) {
    }

    @Override
    public List<DigitalAsset> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        if (moduleManager != null) {
            try {
                digitalAssets = Data.getAllDigitalAssets(moduleManager);
                digitalAssets.addAll(Data.getAllPendingNegotiations(moduleManager));

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
                    R.string.dap_user_wallet_system_error,
                    Toast.LENGTH_SHORT).
                    show();
        }
        return digitalAssets;
    }

    private void showOrHideNoAssetsView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noAssetsView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noAssetsView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code) {
            case DAPConstants.DAP_UPDATE_VIEW_ANDROID:
                onRefresh();
                break;
            default:
                super.onUpdateViewOnUIThread(code);
        }
    }
}