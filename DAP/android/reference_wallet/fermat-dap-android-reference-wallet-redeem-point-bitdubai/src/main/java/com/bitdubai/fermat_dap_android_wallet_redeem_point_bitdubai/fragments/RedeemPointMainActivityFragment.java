package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.adapters.MyAssetsAdapter;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.RedeemPointSession;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.SessionConstantsRedeemPoint;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * Created by frank on 12/14/15.
 */
public class RedeemPointMainActivityFragment extends FermatWalletListFragment<DigitalAsset>
        implements FermatListItemListeners<DigitalAsset> {

    // Constants
    private static final String TAG = "RedeemPointMainActivityFragment";

    // Fermat Managers
    private AssetRedeemPointWalletSubAppModule moduleManager;
    private ErrorManager errorManager;
    SettingsManager<RedeemPointSettings> settingsManager;
    // Data
    private List<DigitalAsset> digitalAssets;

    //UI
    private View noAssetsView;

    public static RedeemPointMainActivityFragment newInstance() {
        return new RedeemPointMainActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            moduleManager = ((RedeemPointSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
            settingsManager = appSession.getModuleManager().getSettingsManager();

            digitalAssets = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.DAP_REDEEM_POINT_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        setupBackgroundBitmap(layout);
        configureToolbar();
        noAssetsView = layout.findViewById(R.id.dap_wallet_no_assets);
        showOrHideNoAssetsView(digitalAssets.isEmpty());

        //Initialize settings
        settingsManager = appSession.getModuleManager().getSettingsManager();
        RedeemPointSettings settings = null;
        try {
            settings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            settings = null;
        }
        if (settings == null) {
            settings = new RedeemPointSettings();
            settings.setIsContactsHelpEnabled(true);
            settings.setIsPresentationHelpEnabled(true);
            try {
                settingsManager.persistSettings(appSession.getAppPublicKey(), settings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        }

        final RedeemPointSettings redeemPointSettingsTemp = settings;


        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable() {
            public void run() {
                if (redeemPointSettingsTemp.isPresentationHelpEnabled()) {
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
                        .setBannerRes(R.drawable.banner_redeem_point)
                        .setIconRes(R.drawable.redeem_point)
                        .setVIewColor(R.color.dap_redeem_point_view_color)
                        .setTitleTextColor(R.color.dap_redeem_point_view_color)
                        .setSubTitle("Welcome to the RedeemPoint Wallet.")
                        .setBody("From this wallet you will be able to verify the assets that users redeem with you and get statistic from them.")
                        .setTextFooter("We will be creating an avatar for you in order to identify you in the system as a Redeem Point, name and more details later in the Redeem Point Identity sub app.")
                        .setTemplateType((moduleManager.getActiveAssetRedeemPointIdentity() == null) ? PresentationDialog.TemplateType.TYPE_PRESENTATION : PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                        .setIsCheckEnabled(checkButton)
                        .build();

                presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Object o = appSession.getData(SessionConstantsRedeemPoint.PRESENTATION_IDENTITY_CREATED);
                        if (o != null) {
                            if ((Boolean) (o)) {
                                //invalidate();
                                appSession.removeData(SessionConstantsRedeemPoint.PRESENTATION_IDENTITY_CREATED);
                            }
                        }
                        try {
                            RedeemPointIdentity redeemPointIdentity = moduleManager.getActiveAssetRedeemPointIdentity();
                            if (redeemPointIdentity == null) {
                                getActivity().onBackPressed();
                            } else {
                                invalidate();
                            }
                        } catch (CantGetIdentityRedeemPointException e) {
                            e.printStackTrace();
                        }
                    }
                });

                presentationDialog.show();
            }
        } catch (CantGetIdentityRedeemPointException e) {
            e.printStackTrace();
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dap_wallet_asset_redeem_home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            if (item.getItemId() == R.id.action_wallet_redeem_help) {
                setUpPresentation(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset Redeem Point system error",
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
//            toolbar.setBackgroundColor(Color.parseColor("#1d1d25"));
            toolbar.setTitleTextColor(Color.WHITE);
//            toolbar.setBackgroundColor(Color.TRANSPARENT);
//            toolbar.setBottom(Color.WHITE);
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//                Window window = getActivity().getWindow();
//                window.setStatusBarColor(Color.parseColor("#1d1d25"));
//            }
            Drawable drawable = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_redeem_point_action_bar_gradient_colors, null);
                toolbar.setElevation(0);
            } else {
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_redeem_point_action_bar_gradient_colors);
            }

            toolbar.setBackground(drawable);
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
                            getResources(), R.drawable.bg_image_redeem_point, options);
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
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_wallet_asset_redeem_point_my_assets_activity;
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
                if (adapter != null)
                    adapter.changeDataSet(digitalAssets);

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
        changeActivity(Activities.DAP_WALLET_REDEEM_POINT_DETAILS_ACTIVITY, appSession.getAppPublicKey());
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


            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            Toast.makeText(getActivity(),
                    "Sorry, an error happened in BrokerListActivityFragment (Module == null)",
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
}
