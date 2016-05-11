package org.fermat.fermat_dap_android_sub_app_asset_factory.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import org.fermat.fermat_dap_android_sub_app_asset_factory.adapters.AssetFactoryAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.AssetFactorySession;
import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.SessionConstantsAssetFactory;
import org.fermat.fermat_dap_android_sub_app_asset_factory.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.AssetFactorySettings;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static org.fermat.fermat_dap_api.layer.all_definition.enums.State.FINAL;

/**
 * Main Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class PublishedAssetsFragment extends AbstractFermatFragment implements
        FermatWorkerCallBack, SwipeRefreshLayout.OnRefreshListener, android.widget.PopupMenu.OnMenuItemClickListener {

    /**
     * asset to edit
     */
    private final String TAG = "DapPublish";
    private ArrayList<AssetFactory> dataSet;
    private AssetFactoryModuleManager manager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AssetFactoryAdapter adapter;
    private ErrorManager errorManager;

    // custom inflater
    private ViewInflater viewInflater;

    private boolean isRefreshing = false;

    SettingsManager<AssetFactorySettings> settingsManager;

    public static PublishedAssetsFragment newInstance() {
        return new PublishedAssetsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            manager = ((AssetFactorySession) appSession).getModuleManager();

            settingsManager = appSession.getModuleManager().getSettingsManager();

            errorManager = appSession.getErrorManager();

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
//        setUpHelpPublished(false);
        return rootView;
    }

    private void setUpHelpPublished(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_factory)
                    .setIconRes(R.drawable.asset_factory)
                    .setVIewColor(R.color.dap_asset_factory_view_color)
                    .setTitleTextColor(R.color.dap_asset_factory_view_color)
                    .setSubTitle(R.string.dap_asset_factory_published_subTitle)
                    .setBody(R.string.dap_asset_factory_published_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

//            presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    Object o = appSession.getData(SessionConstantsAssetFactory.PRESENTATION_IDENTITY_CREATED);
//                    if (o != null) {
//                        if ((Boolean) (o)) {
//                            //invalidate();
//                            appSession.removeData(SessionConstantsAssetFactory.PRESENTATION_IDENTITY_CREATED);
//                        }
//                    }
//                    IdentityAssetIssuer identityAssetIssuer = manager.getLoggedIdentityAssetIssuer();
//                    if (identityAssetIssuer == null) {
//                        getActivity().onBackPressed();
//                    } else {
//                        invalidate();
//                    }
//                }
//            });

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsAssetFactory.IC_ACTION_EDITOR_PUBLISHED, 0, "help").setIcon(R.drawable.dap_asset_factory_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetFactory.IC_ACTION_EDITOR_PUBLISHED) {
                setUpHelpPublished(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset Factory system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
            /*adapter.setMenuItemClick(new PopupMenu() {
                @Override
                public void onMenuItemClickListener(View menuView, AssetFactory project, int position) {
                    selectedAsset = project;
                    *//*Showing up popup menu*//*
                    android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getActivity(), menuView);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.asset_factory_main, popupMenu.getMenu());
                    try {
                        if (!manager.isReadyToPublish(selectedAsset.getPublicKey())) {
                            popupMenu.getMenu().findItem(R.id.action_publish).setVisible(false);
                        }
                    } catch (CantPublishAssetFactoy cantPublishAssetFactoy) {
                        cantPublishAssetFactoy.printStackTrace();
                        popupMenu.getMenu().findItem(R.id.action_publish).setVisible(false);
                    }
                    popupMenu.setOnMenuItemClickListener(PublishedAssetsFragment.this);
                    popupMenu.show();
                }
            });*/
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
        /*create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//* create new asset factory project *//*
                selectedAsset = null;
                changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), appSession.getAppPublicKey(), getAssetForEdit());
            }
        });*/
        create.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_jump_from_down));
        create.setVisibility(View.GONE);
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
        List<AssetFactory> assets = manager.getAssetFactoryByState(FINAL);
        List<Resource> resources;
        for(AssetFactory item : assets) {
            resources = item.getResources();
            for(Resource resource : resources) {
                resource.setResourceBinayData(manager.getAssetFactoryResource(resource).getContent());
            }
        }
        return assets;
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
            /*if (getAssetForEdit() != null && getAssetForEdit().getState() == State.DRAFT)
                changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), getAssetForEdit());
            else
                selectedAsset = null;*/
        } else if (menuItem.getItemId() == R.id.action_publish) {
            /*try {
                if (manager.isReadyToPublish(selectedAsset.getPublicKey())) {
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
                            manager.publishAsset(getAssetForEdit(), BlockchainNetworkType.getDefaultBlockchainNetworkType());
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
                                Toast.makeText(getActivity(), "Ups, some error occurred publishing this asset", Toast.LENGTH_SHORT).show();
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
            }*/
        }
        return false;
    }
}
