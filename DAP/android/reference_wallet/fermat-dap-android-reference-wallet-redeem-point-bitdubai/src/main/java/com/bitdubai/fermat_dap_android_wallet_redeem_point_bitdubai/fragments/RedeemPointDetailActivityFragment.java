package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.UserRedeemed;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.RedeemPointSession;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.adapters.UserRedeemedPointListAdapter;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jinmy on 01/12/16.
 */
public class RedeemPointDetailActivityFragment extends FermatWalletListFragment<UserRedeemed> {

    private RedeemPointSession assetUserSession;
    private AssetRedeemPointWalletSubAppModule moduleManager;

    private List<UserRedeemed> users;
    private View rootView;
    private Toolbar toolbar;
    private View assetDetailRedeemLayout;
    private View assetDetailAppropriateLayout;
    private View noUsersView;
    private ErrorManager errorManager;

    private ImageView assetImageDetail;
    private FermatTextView assetDetailNameText;
    private FermatTextView assetDetailExpDateText;
    private FermatTextView assetDetailAvailableText;
    private FermatTextView assetDetailBookText;
    private FermatTextView assetDetailBtcText;


    private DigitalAsset digitalAsset;

    public RedeemPointDetailActivityFragment() {

    }

    public static RedeemPointDetailActivityFragment newInstance() {
        return new RedeemPointDetailActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        configureToolbar();


        try {
            assetUserSession = (RedeemPointSession) appSession;
            moduleManager = assetUserSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            users = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.DAP_ASSET_ISSUER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_redeem_point_detail, container, false);


        setupUI();
        setupUIData();


        return rootView;
    }

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
        assetDetailAvailableText = (FermatTextView) rootView.findViewById(R.id.assetDetailAvailableText);
        assetDetailBookText = (FermatTextView) rootView.findViewById(R.id.assetDetailBookText);
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

        if (digitalAsset.getImage() != null) {
            assetImageDetail.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
        } else {
            assetImageDetail.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
        }

        assetDetailNameText.setText(digitalAsset.getName());
        assetDetailExpDateText.setText(digitalAsset.getFormattedExpDate());
        assetDetailAvailableText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetDetailBookText.setText(digitalAsset.getBookBalanceQuantity() + "");
        assetDetailBtcText.setText(digitalAsset.getFormattedAvailableBalanceBitcoin() + " BTC");
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
                //users = Data.getUserRedeemedPointList("walletPublicKeyTest", digitalAsset, moduleManager);
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.DAP_ASSET_ISSUER_WALLET,
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
