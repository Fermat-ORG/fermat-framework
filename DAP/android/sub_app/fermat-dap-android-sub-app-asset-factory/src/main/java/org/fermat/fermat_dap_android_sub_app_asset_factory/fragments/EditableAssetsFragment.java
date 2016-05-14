package org.fermat.fermat_dap_android_sub_app_asset_factory.fragments;

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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.CryptoVault;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.software.shell.fab.ActionButton;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.fermat.fermat_dap_android_sub_app_asset_factory.adapters.AssetFactoryAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_factory.interfaces.PopupMenu;
import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.AssetFactorySession;
import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.SessionConstantsAssetFactory;
import org.fermat.fermat_dap_android_sub_app_asset_factory.util.CommonLogger;
import org.fermat.fermat_dap_android_sub_app_asset_factory.util.Utils;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.AssetFactorySettings;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.NotAvailableKeysToPublishAssetsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Main Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class EditableAssetsFragment extends AbstractFermatFragment implements
        FermatWorkerCallBack, SwipeRefreshLayout.OnRefreshListener, android.widget.PopupMenu.OnMenuItemClickListener {

    private static AssetFactory selectedAsset;
    private final String TAG = "DapMain";
    private ArrayList<AssetFactory> dataSet;
    private AssetFactoryModuleManager moduleManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AssetFactoryAdapter adapter;
    private ErrorManager errorManager;
    AssetFactorySession assetFactorySession;
    AssetFactorySettings settings = null;
    //    private MenuItem menuHelp;
//    private Menu menu;
    // custom inflater
    private ViewInflater viewInflater;

//    SettingsManager<AssetFactorySettings> settingsManager;

    private boolean isRefreshing = false;
    private long satoshisWalletBalance;
    private ActionButton create;

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
            assetFactorySession = ((AssetFactorySession) appSession);
            moduleManager = assetFactorySession.getModuleManager();
            errorManager = appSession.getErrorManager();
            //viewInflater = new ViewInflater(getActivity(), appResourcesProviderManager);

            satoshisWalletBalance = moduleManager.getBitcoinWalletBalance(Utils.getBitcoinWalletPublicKey(moduleManager));
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
                        if (!moduleManager.isReadyToPublish(selectedAsset.getAssetPublicKey())) {
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
        create = (ActionButton) layout.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* create new asset factory project */
                selectedAsset = null;
//                menuHelp = menu.findItem(R.id.action_asset_factory_help);
//                menuHelp.setVisible(false);
                changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), appSession.getAppPublicKey(), getAssetForEdit());
            }
        });
        create.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_jump_from_down));
        create.setVisibility(View.VISIBLE);
        create.setEnabled(false);

        showPresentationDialog();
    }

    private void showPresentationDialog() {

        //Initialize settings
//        settingsManager = appSession.getModuleManager().getSettingsManager();
        try {
            settings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            settings = null;
        }
        if (settings == null) {
            int position = 0;
            settings = new AssetFactorySettings();
            settings.setIsContactsHelpEnabled(true);
            settings.setIsPresentationHelpEnabled(true);
            settings.setNotificationEnabled(true);

            settings.setBlockchainNetwork(Arrays.asList(BlockchainNetworkType.values()));
            for (BlockchainNetworkType networkType : Arrays.asList(BlockchainNetworkType.values())) {
                if (Objects.equals(networkType.getCode(), BlockchainNetworkType.getDefaultBlockchainNetworkType().getCode())) {
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

        final AssetFactorySettings assetFactorySettingsTemp = settings;

        try {
            if (moduleManager.getActiveAssetIssuerIdentity() == null) {
                Handler handlerTimer = new Handler();
                handlerTimer.postDelayed(new Runnable() {
                    public void run() {
                        if (assetFactorySettingsTemp.isPresentationHelpEnabled()) {
                            setUpPresentation(false);
                        }
                    }
                }, 500);
            } else {
                create.setEnabled(true);
            }
        } catch (CantGetIdentityAssetIssuerException e) {
            e.printStackTrace();
        }

    }

    private void setUpPresentation(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_factory)
                    .setIconRes(R.drawable.asset_factory)
                    .setImageLeft(R.drawable.asset_issuer_identity)
                    .setVIewColor(R.color.dap_asset_factory_view_color)
                    .setTitleTextColor(R.color.dap_asset_factory_view_color)
                    .setTextNameLeft(R.string.dap_asset_factory_welcome_name_left)
                    .setSubTitle(R.string.dap_asset_factory_welcome_subTitle)
                    .setBody(R.string.dap_asset_factory_welcome_body)
                    .setTextFooter(R.string.dap_asset_factory_welcome_Footer)
                    .setTemplateType((moduleManager.getActiveAssetIssuerIdentity() == null) ? PresentationDialog.TemplateType.DAP_TYPE_PRESENTATION : PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
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

                    try {
                        IdentityAssetIssuer identityAssetIssuer = moduleManager.getActiveAssetIssuerIdentity();
                        if (identityAssetIssuer == null) {
                            getActivity().onBackPressed();
                        } else {
                            invalidate();
                        }
                    } catch (CantGetIdentityAssetIssuerException e) {
                        e.printStackTrace();
                    }
                    create.setEnabled(true);
                }
            });

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsAssetFactory.IC_ACTION_HELP_FACTORY, 0, "help").setIcon(R.drawable.dap_asset_factory_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetFactory.IC_ACTION_HELP_FACTORY) {
                setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
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
        List<AssetFactory> draftItems = moduleManager.getAssetFactoryByState(State.DRAFT);
        List<AssetFactory> pendingFinalItems = moduleManager.getAssetFactoryByState(State.PENDING_FINAL);
        if (draftItems != null && !draftItems.isEmpty())
            items.addAll(draftItems);
        if (pendingFinalItems != null && !pendingFinalItems.isEmpty())
            items.addAll(pendingFinalItems);
        List<Resource> resources;
        for (AssetFactory item : items) {
            resources = item.getResources();
            for (Resource resource : resources) {
                resource.setResourceBinayData(moduleManager.getAssetFactoryResource(resource).getContent());
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
            if (validate()) {
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
            }
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

    private boolean validate() {
        try {
            AssetFactory assetFactory = getAssetForEdit();
            long satoshis = assetFactory.getAmount();
            if (CryptoVault.isDustySend(satoshis)) {
                Toast.makeText(getActivity(), "The minimum monetary amount for any Asset is " + BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND + " satoshis.\n" +
                        " \n This is needed to pay the fee of bitcoin transactions during delivery of the assets.", Toast.LENGTH_LONG).show();
                return false;
            }
            long quantity = assetFactory.getQuantity();
            if (satoshis * quantity > satoshisWalletBalance) {
                double bitcoinWalletBalance = BitcoinConverter.convert(satoshisWalletBalance, SATOSHI, BITCOIN);
                Toast.makeText(getActivity(), String.format("There are insufficient available funds to perform the transaction. The bitcoin wallet balance is %.2f BTC", bitcoinWalletBalance), Toast.LENGTH_SHORT).show();
                return false;
            }
            String description = assetFactory.getDescription();
            if (description.length() == 0)
            {
                Toast.makeText(getActivity(), "Invalid Asset Description.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (quantity == 0)
            {
                Toast.makeText(getActivity(), "Invalid Quantity of Assets", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (assetFactory.getExpirationDate() != null && assetFactory.getExpirationDate().before(new Date())) {
                Toast.makeText(getActivity(), "Expiration date can't be in the past. Please modify the expiration date.", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity(), "Invalid monetary amount.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void publishAsset() {
        try {
            if (moduleManager.isReadyToPublish(selectedAsset.getAssetPublicKey())) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Asset Factory");
                dialog.setMessage("Publishing asset, please wait...");
                dialog.setCancelable(false);
                dialog.show();
                FermatWorker worker = new FermatWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        // for test
                        for (InstalledWallet wallet : moduleManager.getInstallWallets()) {
                            selectedAsset.setWalletPublicKey(wallet.getWalletPublicKey());
                            break;
                        }
                        moduleManager.publishAsset(getAssetForEdit());
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
                        Toast.makeText(getActivity(), "The publishing process has been started successfully.\n\n " +
                                "You will be able to distribute this asset in a few minutes from your Asset Issuer Wallet.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onErrorOccurred(Exception ex) {
                        dialog.dismiss();
                        selectedAsset = null;

                        /**
                         * If there was an exception, I will search first if I ran out of keys
                         * to show the appropiated message
                         */
                        Throwable rootException = ExceptionUtils.getRootCause(ex);
                        if (rootException instanceof NotAvailableKeysToPublishAssetsException) {
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(), rootException.getMessage(), Toast.LENGTH_LONG).show();
                                onRefresh();
                            }
                        } else {
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(), "You need to define all mandatory properties in your asset before publishing it.", Toast.LENGTH_LONG).show();
                                onRefresh();
                            }
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
                moduleManager.removeAssetFactory(selectedAsset.getAssetPublicKey());
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
            identity = moduleManager.getSelectedActorIdentity();
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
