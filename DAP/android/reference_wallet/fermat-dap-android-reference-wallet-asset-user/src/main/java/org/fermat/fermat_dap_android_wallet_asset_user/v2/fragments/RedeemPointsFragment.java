package org.fermat.fermat_dap_android_wallet_asset_user.v2.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.SizeUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import org.fermat.fermat_dap_android_wallet_asset_user.models.RedeemPoint;
import org.fermat.fermat_dap_android_wallet_asset_user.util.CommonLogger;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.common.adapters.RedeemPointAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.common.data.DataManager;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 3/3/16.
 */
public class RedeemPointsFragment extends FermatWalletListFragment<RedeemPoint>
        implements FermatListItemListeners<RedeemPoint> {

    //UI
    private Resources res;
    private View rootView;
    private View noTransactionsView;
    private Toolbar toolbar;

    //DATA
    private DataManager dataManager;
    private List<RedeemPoint> redeemPoints;

    //FERMAT
    private ErrorManager errorManager;
    private AssetUserWalletSubAppModuleManager moduleManager;

    public RedeemPointsFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        redeemPoints = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);Activity activity = getActivity();
        moduleManager = (AssetUserWalletSubAppModuleManager) appSession.getModuleManager();
        dataManager = new DataManager(moduleManager);
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

    private void configureToolbar() {
        toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.dap_user_wallet_principal));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            toolbar.setTitle(res.getString(R.string.dap_user_wallet_action_redeem_title));
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.dap_user_wallet_principal));
            }
        }
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
        View container = layoutInflater.inflate(R.layout.dap_v2_wallet_asset_user_redeem_points_header, header, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            container.getLayoutParams().height = SizeUtils.convertDpToPixels(50, getActivity());
        }
        return container;
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        rootView = configureActivityHeader(layoutInflater);

        res = rootView.getResources();

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

        noTransactionsView = layout.findViewById(R.id.dap_v2_wallet_asset_user_redeem_points_no_assets);
        showOrHideNoTransactionsView(redeemPoints.isEmpty());
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
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_v2_wallet_asset_user_redeem_points;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.dap_v2_wallet_asset_user_redeem_points_swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_v2_wallet_asset_user_redeem_points_recycler_view;
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
                redeemPoints = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(redeemPoints);

                showOrHideNoTransactionsView(redeemPoints.isEmpty());
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
            adapter = new RedeemPointAdapter(getActivity(), redeemPoints, moduleManager);
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
    public void onItemClickListener(RedeemPoint data, int position) {

    }

    @Override
    public void onLongItemClickListener(RedeemPoint data, int position) {

    }

    @Override
    public List<RedeemPoint> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<RedeemPoint> redeemPoints = new ArrayList<>();
        if (dataManager != null) {
            try {
                Asset asset = (Asset) appSession.getData("asset");
                if (asset != null) {
                    redeemPoints = dataManager.getConnectedRedeemPoints(asset.getDigitalAsset().getPublicKey());
                } else {
                    throw new Exception("Can't get the asset from session");
                }
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
        return redeemPoints;
    }
}
