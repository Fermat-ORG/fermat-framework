package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.fragments;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.SizeUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters.AssetDetailTransactionAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Transaction;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.SessionConstantsAssetUser;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Asset;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Jinmy Bohorquez on 04/02/16.
 */
public class TransactionsFragment extends FermatWalletListFragment<Transaction>
        implements FermatListItemListeners<Transaction> {

    private AssetUserSession assetUserSession;
    private AssetUserWalletSubAppModuleManager moduleManager;


    private Toolbar toolbar;
    private Resources res;
//    private View assetDetailRedeemLayout;
//    private View assetDetailAppropriateLayout;
//    private View assetDetailTransferLayout;

    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;

    SettingsManager<AssetUserSettings> settingsManager;

    private View noTransactionsView;
    private List<Transaction> transactions;

    public TransactionsFragment() {

    }

    public static TransactionsFragment newInstance() {
        return new TransactionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        assetUserSession = (AssetUserSession) appSession;
        moduleManager = assetUserSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        moduleManager.clearDeliverList();
        settingsManager = appSession.getModuleManager().getSettingsManager();
        appSession.setData("users_to_transfer", null);

        transactions = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);

        appSession.setData("sell_info", null);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        Activity activity = getActivity();
        LayoutInflater layoutInflater = activity.getLayoutInflater();


        res = getResources();


        setupBackgroundBitmap(layout);
        setupUIData();
        configureToolbar();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            });
        }
        
        noTransactionsView = layout.findViewById(R.id.dap_wallet_asset_user_no_transactions);
        showOrHideNoTransactionsView(transactions.isEmpty());
    }


//    private View configureActivityHeader(LayoutInflater layoutInflater) {
//        RelativeLayout header = getToolbarHeader();
//        try {
//            header.removeAllViews();
//        } catch (Exception exception) {
//            CommonLogger.exception(TAG, "Error removing all views from header ", exception);
//            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, exception);
//        }
//        header.setVisibility(View.VISIBLE);
//        View container = layoutInflater.inflate(R.layout.dap_wallet_asset_user_asset_detail, header, true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            container.getLayoutParams().height = SizeUtils.convertDpToPixels(428, getActivity());
//        }
//        return container;
//    }

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
        return R.layout.dap_wallet_asset_user_asset_detail_transaction;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_issuer_user_asset_detail_transaction_sent_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    private void setUpHelpAssetDetail(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_user_wallet)
                    .setIconRes(R.drawable.asset_user_wallet)
                    .setVIewColor(R.color.dap_user_view_color)
                    .setTitleTextColor(R.color.dap_user_view_color)
                    .setSubTitle(R.string.dap_user_wallet_detail_subTitle)
                    .setBody(R.string.dap_user_wallet_detail_body)
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
//        inflater.inflate(R.menu.dap_wallet_asset_user_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_DETAIL, 4, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_DETAIL) {
                setUpHelpAssetDetail(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            } else if (id == SessionConstantsAssetUser.IC_ACTION_USER_ASSET_REDEEM) {
                changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_REDEEM, appSession.getAppPublicKey());
                return true;
            } else if (id == SessionConstantsAssetUser.IC_ACTION_USER_ASSET_APPROPRIATE) {
                new ConfirmDialog.Builder(getActivity(), appSession)
                        .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                        .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_sure))
                        .setColorStyle(getResources().getColor(R.color.dap_user_wallet_principal))
                        .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                            @Override
                            public void onClick() {
                                //doAppropriate(digitalAsset.getAssetPublicKey());
                            }
                        }).build().show();
                return true;
            } else if (id == SessionConstantsAssetUser.IC_ACTION_USER_ASSET_TRANSFER) {
//                new ConfirmDialog.Builder(getActivity(), appSession)
//                        .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
//                        .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_sure))
//                        .setColorStyle(getResources().getColor(R.color.dap_user_wallet_principal))
//                        .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
//                            @Override
//                            public void onClick() {
//                                transferAsset(digitalAsset.getAssetPublicKey());
//                            }
//                        }).build().show();
                changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_TRANSFER_ACTIVITY, appSession.getAppPublicKey());
                return true;

            } else if (id == SessionConstantsAssetUser.IC_ACTION_USER_ITEM_SELL) {
                changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_SELL_ACTIVITY , appSession.getAppPublicKey());
                return true;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    private void setupBackgroundBitmap(View view) {
        AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

            WeakReference<ViewGroup> view;

            @Override
            protected void onPreExecute() {
                view = new WeakReference(view);
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

    private void setupUIData() {
        String digitalAssetPublicKey = ((Asset) appSession.getData("asset_data")).getDigitalAsset().getPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }
    }



    private void configureToolbar() {
        toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.dap_user_wallet_principal));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
//            toolbar.setTitle(digitalAsset.getName());
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.dap_user_wallet_principal));
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
                    getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).
                    show();
        }
        return transactions;
    }
}
