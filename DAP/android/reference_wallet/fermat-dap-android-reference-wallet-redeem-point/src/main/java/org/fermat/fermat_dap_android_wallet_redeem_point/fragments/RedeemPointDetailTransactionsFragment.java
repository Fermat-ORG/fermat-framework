package org.fermat.fermat_dap_android_wallet_redeem_point.fragments;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.SizeUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import org.fermat.fermat_dap_android_wallet_redeem_point.adapters.AssetDetailTransactionAdapter;
import org.fermat.fermat_dap_android_wallet_redeem_point.models.Data;
import org.fermat.fermat_dap_android_wallet_redeem_point.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_redeem_point.models.Transaction;
import org.fermat.fermat_dap_android_wallet_redeem_point.sessions.RedeemPointSession;
import org.fermat.fermat_dap_android_wallet_redeem_point.sessions.SessionConstantsRedeemPoint;
import org.fermat.fermat_dap_android_wallet_redeem_point.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by frank on 12/15/15.
 */
public class RedeemPointDetailTransactionsFragment extends FermatWalletListFragment<Transaction>
        implements FermatListItemListeners<Transaction> {

    private RedeemPointSession redeemPointSession;
    private AssetRedeemPointWalletSubAppModule moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;

    private ImageView assetImageDetail;
    private FermatTextView assetDetailNameText;
    private FermatTextView assetDetailExpDateText;
    private FermatTextView availableText;
    private FermatTextView pendingText;
    private FermatTextView assetDetailBtcText;
//    private FermatTextView assetDetailRedeemText;

    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;

    SettingsManager<RedeemPointSettings> settingsManager;

    private View noTransactionsView;
    private List<Transaction> transactions;

    public RedeemPointDetailTransactionsFragment() {

    }

    public static RedeemPointDetailTransactionsFragment newInstance() {
        return new RedeemPointDetailTransactionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        redeemPointSession = (RedeemPointSession) appSession;
        moduleManager = redeemPointSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        settingsManager = appSession.getModuleManager().getSettingsManager();

        transactions = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        Activity activity = getActivity();
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        rootView = configureActivityHeader(layoutInflater);

        res = rootView.getResources();

        setupUI();
        setupUIData();
        setupRecyclerView();

        configureToolbar();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            });
        }
        
        noTransactionsView = layout.findViewById(R.id.dap_wallet_asset_redeem_point_no_transactions);
        showOrHideNoTransactionsView(transactions.isEmpty());
    }

    private void setupRecyclerView() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int mScrollOffset = 4;
                if (Math.abs(dy) > mScrollOffset) {
                    if (rootView != null) {
                        if (dy > 0) {
                            rootView.setVisibility(View.GONE);
                        } else {
                            rootView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    private View configureActivityHeader(LayoutInflater layoutInflater) {
        RelativeLayout header = getToolbarHeader();
        try {
            header.removeAllViews();
        } catch (Exception exception) {
            CommonLogger.exception(TAG, "Error removing all views from header ", exception);
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, exception);
        }
        header.setVisibility(View.VISIBLE);
        View container = layoutInflater.inflate(R.layout.dap_wallet_asset_redeem_point_detail, header, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            container.getLayoutParams().height = SizeUtils.convertDpToPixels(428, getActivity());
        }
        return container;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void showOrHideNoTransactionsView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noTransactionsView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noTransactionsView.setVisibility(View.GONE);
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_wallet_asset_redeem_point_detail_transaction;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_redeem_point_asset_detail_transaction_sent_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.dap_wallet_asset_user_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsRedeemPoint.IC_ACTION_REDEEM_HELP_DETAIL, 0, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
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
            makeText(getActivity(), R.string.dap_redeem_point_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpHelpAssetRedeemDetail(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_redeem_point_wallet)
                    .setIconRes(R.drawable.redeem_point)
                    .setVIewColor(R.color.dap_redeem_point_view_color)
                    .setTitleTextColor(R.color.dap_redeem_point_view_color)
                    .setSubTitle(R.string.dap_redeem_wallet_detail_subTitle)
                    .setBody(R.string.dap_redeem_wallet_detail_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void setupUIData() {
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

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
            toolbar.setBackgroundColor(getResources().getColor(R.color.dap_redeem_point_wallet_principal));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
//            toolbar.setTitle(digitalAsset.getName());
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.dap_redeem_point_wallet_principal));
            }
        }
    }

    @Override
    public void onItemClickListener(Transaction data, int position) {

    }

    @Override
    public void onLongItemClickListener(Transaction data, int position) {

    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                transactions = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(transactions);

                showOrHideNoTransactionsView(transactions.isEmpty());
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
            adapter = new AssetDetailTransactionAdapter(getActivity(), transactions, moduleManager);
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
    public List<Transaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<Transaction> transactions = new ArrayList<>();
        if (moduleManager != null) {
            try {
                transactions = Data.getTransactions(moduleManager, digitalAsset);

                appSession.setData("transactions_sent", transactions);

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
                    getResources().getString(R.string.dap_redeem_point_wallet_system_error),
                    Toast.LENGTH_SHORT).
                    show();
        }
        return transactions;
    }
}
