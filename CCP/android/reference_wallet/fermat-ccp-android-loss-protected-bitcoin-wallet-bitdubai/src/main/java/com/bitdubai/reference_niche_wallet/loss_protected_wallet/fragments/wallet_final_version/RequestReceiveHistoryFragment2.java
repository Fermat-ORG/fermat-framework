
package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;

import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.PaymentRequestHistoryAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;

/**
 * Created by Joaquin Carrasquero on 27/04/16.
 */
public class RequestReceiveHistoryFragment2 extends FermatWalletListFragment<LossProtectedPaymentRequest> implements FermatListItemListeners<LossProtectedPaymentRequest>, onRefreshList {

    /**
     * Session
     */
    LossProtectedWalletSession referenceWalletSession;
    String walletPublicKey = "loss_protected_wallet";
    /**
     * MANAGERS
     */
    private LossProtectedWallet lossProtectedWalletManager;
    /**
     * DATA
     */
    private List<LossProtectedPaymentRequest> lstPaymentRequest;
    private LossProtectedPaymentRequest selectedItem;
    /**
     * Executor Service
     */
    private ExecutorService executor;
    private int MAX_TRANSACTIONS = 20;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;
    com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton;
    FloatingActionMenu actionMenu;
    private LossProtectedWalletSettings lossProtectedWalletSettings;

    BlockchainNetworkType blockchainNetworkType;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static RequestReceiveHistoryFragment2 newInstance() {
        return new RequestReceiveHistoryFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        referenceWalletSession = (LossProtectedWalletSession) appSession;

        lstPaymentRequest = new ArrayList<>();
        try {
            lossProtectedWalletManager = referenceWalletSession.getModuleManager();

            lstPaymentRequest = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data

            try {
                lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());
                this.blockchainNetworkType = lossProtectedWalletSettings.getBlockchainNetworkType();
            } catch (Exception e) {

            }

            onRefresh();
        } catch (Exception ex) {
            ex.printStackTrace();

            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
            recyclerView.addItemDecoration(itemDecoration);
            empty = (LinearLayout) rootView.findViewById(R.id.empty);
            setUp();
            return rootView;
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return container;
    }

    private void setUp() {
        FrameLayout frameLayout = new FrameLayout(getActivity());

        FrameLayout.LayoutParams lbs = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        frameLayout.setLayoutParams(lbs);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_REQUEST_FORM_ACTIVITY);
            }
        };
        View view = new View(getActivity());
        view.setLayoutParams(lbs);

        frameLayout.addView(view);
        frameLayout.setOnClickListener(onClickListener);
        view.setOnClickListener(onClickListener);
      actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(getActivity())
                .setContentView(frameLayout).setBackgroundDrawable(R.drawable.floatbutton_sendbitcoin)
                .build();

        actionMenu = new FloatingActionMenu.Builder(getActivity())
                .attachTo(actionButton)
                .build();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int mScrollOffset = 4;
                if (Math.abs(dy) > mScrollOffset) {
                    if (actionButton != null) {
                        if (dy > 0) {
                            actionButton.setVisibility(View.GONE);
                        } else {
                            actionButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            lstPaymentRequest = new ArrayList<>();
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Override
    public void onDrawerOpen() {
        actionButton.setVisibility(View.GONE);

    }

    @Override
    public void onDrawerClose() {
        FermatAnimationsUtils.showEmpty(getActivity(), true, actionMenu.getActivityContentView());
        actionButton.setVisibility(View.VISIBLE);

    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.loss_fragment_transaction_main;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.transactions_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    @SuppressWarnings("unchecked")
    public FermatAdapter getAdapter() {
        if (adapter == null) {
             adapter = new PaymentRequestHistoryAdapter(getActivity(), lstPaymentRequest, lossProtectedWalletManager, referenceWalletSession, this);
            adapter.setFermatListEventListener(this); // setting up event listeners

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
    public List<LossProtectedPaymentRequest> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<LossProtectedPaymentRequest> lstPaymentRequest = new ArrayList<LossProtectedPaymentRequest>();

        if(blockchainNetworkType != null)
        {
            try {
                //when refresh offset set 0
                if (refreshType.equals(FermatRefreshTypes.NEW))
                    offset = 0;
                lstPaymentRequest = lossProtectedWalletManager.listReceivedPaymentRequest(walletPublicKey, blockchainNetworkType, 10, offset);
                offset += MAX_TRANSACTIONS;
            } catch (Exception e) {
                referenceWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                e.printStackTrace();
            }
        }


        return lstPaymentRequest;
    }

    @Override
    public void onItemClickListener(LossProtectedPaymentRequest item, int position) {
        selectedItem = item;
        onRefresh();
    }

    /**
     * On Long item Click Listener
     *
     * @param data
     * @param position
     */
    @Override
    public void onLongItemClickListener(LossProtectedPaymentRequest data, int position) {

    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                lstPaymentRequest = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstPaymentRequest);
                if (lstPaymentRequest.isEmpty())
                    FermatAnimationsUtils.showEmpty(getActivity(), true, empty);
                else FermatAnimationsUtils.showEmpty(getActivity(), false, empty);

            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    public void setReferenceWalletSession(LossProtectedWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }


}
