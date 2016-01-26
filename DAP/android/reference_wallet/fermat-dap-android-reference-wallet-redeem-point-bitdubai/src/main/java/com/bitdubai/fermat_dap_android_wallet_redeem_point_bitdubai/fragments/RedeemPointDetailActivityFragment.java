package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.UserRedeemed;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.RedeemPointSession;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.SessionConstantsRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.adapters.UserRedeemedPointListAdapter;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.functional.RedeemPointStatisticImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.apache.commons.collections.ArrayStack;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by jinmy on 01/12/16.
 */
public class RedeemPointDetailActivityFragment extends FermatWalletListFragment<UserRedeemed> {

    private RedeemPointSession assetUserSession;
    private AssetRedeemPointWalletSubAppModule moduleManager;
    private Resources res;

    private List<UserRedeemed> users;
    private View rootView;
    private Toolbar toolbar;
    private View noUsersView;
    private ErrorManager errorManager;

    private ImageView assetImageDetail;
    private FermatTextView assetDetailNameText;
    private FermatTextView assetDetailExpDateText;
    private FermatTextView availableText;
    private FermatTextView pendingText;
    private FermatTextView assetDetailBtcText;

    private DigitalAsset digitalAsset;

    SettingsManager<RedeemPointSettings> settingsManager;

    public RedeemPointDetailActivityFragment() {

    }

    public static RedeemPointDetailActivityFragment newInstance() {
        return new RedeemPointDetailActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            assetUserSession = (RedeemPointSession) appSession;
            moduleManager = assetUserSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            settingsManager = appSession.getModuleManager().getSettingsManager();

            users = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
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

        //setupBackgroundBitmap(layout);
        configureToolbar();

        rootView = layout;
        setupUI();
        setupUIData();

        noUsersView = layout.findViewById(R.id.dap_wallet_asset_issuer_no_users);

        showOrHideNoUsersView(users.isEmpty());
    }

    private void setUpHelpAssetRedeemDetail(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_redeem_point)
                    .setIconRes(R.drawable.redeem_point)
                    .setVIewColor(R.color.dap_redeem_point_view_color)
                    .setTitleTextColor(R.color.dap_redeem_point_view_color)
                    .setSubTitle("Assets Detail.")
                    .setBody("You can review in more detail the asset that has been redeemed by an user.\n\n" +
                            "An asset might be redeemed by many users so statistics and tracking of the users is shown here.\n\n " +
                            "Every asset redeem at you, is also notified to the original creator of the Asset.")
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
        menu.add(0, SessionConstantsRedeemPoint.IC_ACTION_REDEEM_HELP_DETAIL, 0, "help").setIcon(R.drawable.dap_asset_redeem_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsRedeemPoint.IC_ACTION_REDEEM_HELP_DETAIL) {
                setUpHelpAssetRedeemDetail(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset Redeem Point system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_redeem_point_detail, container, false);
        res = rootView.getResources();

        setupUI();
        setupUIData();


        return rootView;
    }
*/
    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_wallet_asset_redeem_point_detail;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_redeem_point_activity_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    private void setupUI() {
        setupBackgroundBitmap();

        assetImageDetail = (ImageView) rootView.findViewById(R.id.asset_image_detail);
        assetDetailNameText = (FermatTextView) rootView.findViewById(R.id.assetDetailNameText);
        assetDetailExpDateText = (FermatTextView) rootView.findViewById(R.id.assetDetailExpDateText);
        availableText = (FermatTextView) rootView.findViewById(R.id.assetAvailable1);
        pendingText = (FermatTextView) rootView.findViewById(R.id.assetAvailable2);
        assetDetailBtcText = (FermatTextView) rootView.findViewById(R.id.assetDetailBtcText);

    }

    private void setupBackgroundBitmap() {
        AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

            WeakReference<ViewGroup> view;

            @Override
            protected void onPreExecute() {
                view = new WeakReference(rootView) ;
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap drawable = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = true;
                    options.inSampleSize = 5;
                    drawable = BitmapFactory.decodeResource(
                            getResources(), R.drawable.bg_image_redeem_point,options);
                }catch (OutOfMemoryError error){
                    error.printStackTrace();
                }
                return drawable;
            }

            @Override
            protected void onPostExecute(Bitmap drawable) {
                if (drawable!= null) {
                    view.get().setBackground(new BitmapDrawable(getResources(),drawable));
                }
            }
        } ;
        asyncTask.execute();
    }

    private void setupUIData() {
        //TODO review this
        digitalAsset = (DigitalAsset) appSession.getData("asset_data");


        toolbar.setTitle(digitalAsset.getName());

//        if (digitalAsset.getImage() != null) {
//            assetImageDetail.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
//        } else {
//            assetImageDetail.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
//        }
        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetImageDetail, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        assetDetailNameText.setText(digitalAsset.getName());
        assetDetailExpDateText.setText(digitalAsset.getFormattedExpDate());

        long available = digitalAsset.getAvailableBalanceQuantity();
        long book = digitalAsset.getBookBalanceQuantity();
        availableText.setText(availableText(available));
        if (available == book) {
            pendingText.setVisibility(View.INVISIBLE);
        } else {
            long pendingValue = Math.abs(available - book);
            pendingText.setText(pendingText(pendingValue));
            pendingText.setVisibility(View.VISIBLE);
        }

        assetDetailBtcText.setText(digitalAsset.getFormattedAvailableBalanceBitcoin() + " BTC");
    }

    private String pendingText(long pendingValue) {
        return "(" + pendingValue + " pending confirmation)";
    }

    private String availableText(long available) {
        return available + ((available == 1) ? " Asset" : " Assets");
    }

    private void configureToolbar() {

        toolbar = getToolbar();
        if (toolbar != null) {

            toolbar.setBackgroundColor(Color.parseColor("#015581"));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(Color.parseColor("#015581"));
            }
        }
    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                users = (ArrayList) result[0];
                if (getAdapter() != null)
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
            adapter = new UserRedeemedPointListAdapter(getActivity(), users, moduleManager);
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
    public List<UserRedeemed> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<UserRedeemed> users = new ArrayList<>();
        if (moduleManager != null) {
            try {
                if (digitalAsset == null) digitalAsset = (DigitalAsset) appSession.getData("asset_data");

                users = Data.getUserRedeemedPointList("walletPublicKeyTest",digitalAsset,moduleManager);

            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.DAP_REDEEM_POINT_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            Toast.makeText(getActivity(),
                    "Sorry, an error happened in BrokerListActivityFragment (Module == null)",
                    Toast.LENGTH_SHORT).
                    show();
        }
        return users;
    }

}
