package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.adapters.AssetFactoryAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.interfaces.PopupMenu;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.sessions.AssetFactorySession;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.sessions.SessionConstantsAssetFactory;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.AssetFactorySettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * Main Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class EditableAssetsFragment extends AbstractFermatFragment implements
        FermatWorkerCallBack, SwipeRefreshLayout.OnRefreshListener, android.widget.PopupMenu.OnMenuItemClickListener {

    /**
     * asset to edit
     */
    private static AssetFactory selectedAsset;
    private final String TAG = "DapMain";
    private ArrayList<AssetFactory> dataSet;
    private AssetFactoryModuleManager manager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AssetFactoryAdapter adapter;
    private ErrorManager errorManager;

    // custom inflater
    private ViewInflater viewInflater;

    SettingsManager<AssetFactorySettings> settingsManager;


    private boolean isRefreshing = false;


    public static EditableAssetsFragment newInstance() {
        return new EditableAssetsFragment();
    }

    public static AssetFactory getAssetForEdit() {
        return selectedAsset;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            selectedAsset = null;
            manager = ((AssetFactorySession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();

            settingsManager = appSession.getModuleManager().getSettingsManager();
            //viewInflater = new ViewInflater(getActivity(), appResourcesProviderManager);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_assets_draf_fragment, container, false);
        configureToolbar();
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (swipeRefreshLayout != null && !isRefreshing) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        selectedAsset = null;
    }

    private void configureToolbar() {
        Toolbar toolbar = getPaintActivtyFeactures().getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(Color.parseColor("#1d1d25"));
            toolbar.setTitleTextColor(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(Color.parseColor("#1d1d25"));
            }
        }
    }

    /**
     * Get the status bar height for kitkat, lollipop and m devices
     *
     * @return int height in pixels
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    protected void initViews(View layout) {
        Log.i(TAG, "recycler view setup");
        if (layout == null)
            return;
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_assets);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new AssetFactoryAdapter(getActivity());
            adapter.setMenuItemClick(new PopupMenu() {
                @Override
                public void onMenuItemClickListener(View menuView, AssetFactory project, int position) {
                    selectedAsset = project;
                    /*Showing up popup menu*/
                    android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getActivity(), menuView);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.asset_factory_main, popupMenu.getMenu());
                    try {
                        if (!manager.isReadyToPublish(selectedAsset.getAssetPublicKey())) {
                            popupMenu.getMenu().findItem(R.id.action_publish).setVisible(false);
                        }
                    } catch (CantPublishAssetFactoy cantPublishAssetFactoy) {
                        cantPublishAssetFactoy.printStackTrace();
                        popupMenu.getMenu().findItem(R.id.action_publish).setVisible(false);
                    }
                    popupMenu.setOnMenuItemClickListener(EditableAssetsFragment.this);
                    popupMenu.show();
                }
            });
            recyclerView.setAdapter(adapter);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_assets);
        if (swipeRefreshLayout != null) {
            isRefreshing = false;
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
            swipeRefreshLayout.setOnRefreshListener(this);
        }

        // fab action button create
        ActionButton create = (ActionButton) layout.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* create new asset factory project */
                selectedAsset = null;
                changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), appSession.getAppPublicKey(), getAssetForEdit());
            }
        });
        create.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_jump_from_down));
        create.setVisibility(View.VISIBLE);

        showPresentationDialog();
    }

    private void showPresentationDialog() {
        //Initialize settings
        settingsManager = appSession.getModuleManager().getSettingsManager();
        AssetFactorySettings settings = null;
        try {
            settings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            settings = null;
        }
        if (settings == null) {
            settings = new AssetFactorySettings();
            settings.setIsContactsHelpEnabled(true);
            settings.setIsPresentationHelpEnabled(true);
            try {
                settingsManager.persistSettings(appSession.getAppPublicKey(), settings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        }

        final AssetFactorySettings assetFactorySettingsTemp = settings;

        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable() {
            public void run() {
                if (assetFactorySettingsTemp.isPresentationHelpEnabled()) {
                    setUpPresentation(false);
                }
            }
        }, 500);
    }

    private void setUpPresentation(boolean checkButton) {
        try {
            boolean isPresentationHelpEnabled = settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled();

            if (isPresentationHelpEnabled) {
                PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                        .setBannerRes(R.drawable.banner_asset_factory)
                        .setIconRes(R.drawable.asset_factory)
                        .setVIewColor(R.color.dap_asset_factory_view_color)
                        .setTitleTextColor(R.color.dap_asset_factory_view_color)
                        .setSubTitle("Welcome to the Asset Factory application.")
                        .setBody("From here you will be able to create, define and publish all your assets.")
                        .setTextFooter("We will be creating an avatar for you in order to identify you in the system as an Asset Issuer, name and more details later in the Asset Issuer Identity sub app.")
                        .setTemplateType((manager.getLoggedIdentityAssetIssuer() == null) ? PresentationDialog.TemplateType.TYPE_PRESENTATION : PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                        .setIsCheckEnabled(checkButton)
                        .build();

                presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Object o = appSession.getData(SessionConstantsAssetFactory.PRESENTATION_IDENTITY_CREATED);
                        if (o != null) {
                            if ((Boolean) (o)) {
                                //invalidate();
                                appSession.removeData(SessionConstantsAssetFactory.PRESENTATION_IDENTITY_CREATED);
                            }
                        }
                        IdentityAssetIssuer identityAssetIssuer = manager.getLoggedIdentityAssetIssuer();
                        if (identityAssetIssuer == null) {
                            getActivity().onBackPressed();
                        } else {
                            invalidate();
                        }
                    }
                });

                presentationDialog.show();
            }
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dap_asset_factory_home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            if (item.getItemId() == R.id.action_asset_factory_help) {
                setUpPresentation(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset Issuer system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                dataSet = (ArrayList<AssetFactory>) result[0];
                adapter.changeDataSet(dataSet);
                if (dataSet == null || dataSet.isEmpty()) {
                    /* create new asset */
                    //changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), getAssetForEdit());
                }
            } else if (result != null) {
                dataSet = new ArrayList<>();
                adapter.changeDataSet(dataSet);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(false);
        }
    }

    public List<AssetFactory> getMoreDataAsync() throws CantGetAssetFactoryException, CantCreateFileException, FileNotFoundException {
        List<AssetFactory> items = new ArrayList<>();
        List<AssetFactory> draftItems = manager.getAssetFactoryByState(State.DRAFT);
        List<AssetFactory> pendingFinalItems = manager.getAssetFactoryByState(State.PENDING_FINAL);
        if (draftItems != null && !draftItems.isEmpty())
            items.addAll(draftItems);
        if (pendingFinalItems != null && !pendingFinalItems.isEmpty())
            items.addAll(pendingFinalItems);
        List<Resource> resources;
        for (AssetFactory item : items) {
            resources = item.getResources();
            for (Resource resource : resources) {
                resource.setResourceBinayData(manager.getAssetFactoryResource(resource).getContent());
            }
        }
        return items;
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(true);
            FermatWorker worker = new FermatWorker(getActivity(), this) {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreDataAsync();
                }
            };
            worker.execute();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_edit) {
            editAsset();
        } else if (menuItem.getItemId() == R.id.action_publish) {
            new ConfirmDialog.Builder(getActivity(), appSession)
                    .setTitle("Confirm")
                    .setMessage("Are you sure you are ready to publish your Asset? Once published you won't be able to perform any changes to it.")
                    .setColorStyle(getResources().getColor(R.color.bg_asset_factory))
                    .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                        @Override
                        public void onClick() {
                            publishAsset();
                        }
                    }).build().show();
        } else if (menuItem.getItemId() == R.id.action_delete) {
//            new ConfirmDialog.Builder(getActivity(), appSession)
//                    .setTitle("Confirm")
//                    .setMessage("Are you sure you want to delete this Asset? This action can't be undo.")
//                    .setColorStyle(getResources().getColor(R.color.bg_asset_factory))
//                    .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
//                        @Override
//                        public void onClick() {
//                            deleteAsset();
//                        }
//                    }).build().show();
            deleteAsset();
        }
        return false;
    }

    private void editAsset() {
        if (getAssetForEdit() != null && getAssetForEdit().getState() == State.DRAFT)
            changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), appSession.getAppPublicKey(), getAssetForEdit());
        else
            selectedAsset = null;
    }

    private void publishAsset() {
        try {
            if (manager.isReadyToPublish(selectedAsset.getAssetPublicKey())) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Asset Factory");
                dialog.setMessage("Publishing asset, please wait...");
                dialog.setCancelable(false);
                dialog.show();
                FermatWorker worker = new FermatWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        // for test
                        for (InstalledWallet wallet : manager.getInstallWallets()) {
                            selectedAsset.setWalletPublicKey(wallet.getWalletPublicKey());
                            break;
                        }
                        manager.publishAsset(getAssetForEdit(), BlockchainNetworkType.TEST);
                        selectedAsset = null;
                        return true;
                    }
                };
                worker.setContext(getActivity());
                worker.setCallBack(new FermatWorkerCallBack() {
                    @Override
                    public void onPostExecute(Object... result) {
                        dialog.dismiss();
                        selectedAsset = null;
                        if (getActivity() != null) {
                            onRefresh();
                        }
                        Toast.makeText(getActivity(), "The asset was successfully published.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onErrorOccurred(Exception ex) {
                        dialog.dismiss();
                        selectedAsset = null;
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "You need to define all mandatory properties in your asset before publishing it.", Toast.LENGTH_LONG).show();
                            onRefresh();
                        }
                        ex.printStackTrace();
                    }
                });
                worker.execute();
            }
        } catch (CantPublishAssetFactoy cantPublishAssetFactoy) {
            cantPublishAssetFactoy.printStackTrace();
            Toast.makeText(getActivity(), "You cannot publish this asset", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteAsset() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Deleting asset");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                manager.removeAssetFactory(selectedAsset.getAssetPublicKey());
                return true;
            }
        };
        worker.setContext(getActivity());
        worker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "Asset deleted successfully", Toast.LENGTH_SHORT).show();
                    changeActivity(Activities.DAP_MAIN.getCode(), appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (getActivity() != null) {
                    CommonLogger.exception(TAG, ex.getMessage(), ex);
                    Toast.makeText(getActivity(), "There was an error deleting this asset", Toast.LENGTH_SHORT).show();
                }
            }
        });
        worker.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        checkIdentity();
    }

    private void checkIdentity() {
        ActiveActorIdentityInformation identity = null;
        try {
            identity = manager.getSelectedActorIdentity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (identity == null) {
            makeText(getActivity(), "Identity must be created",
                    LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
    }
}
