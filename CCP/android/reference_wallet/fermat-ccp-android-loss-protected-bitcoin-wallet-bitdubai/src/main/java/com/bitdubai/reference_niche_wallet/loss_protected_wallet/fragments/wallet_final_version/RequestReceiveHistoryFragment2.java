
package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.OnLoadMoreDataListener;
import com.bitdubai.fermat_android_api.ui.util.EndlessScrollListener;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.PaymentRequestHistoryAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.Payment_Request_Help_Dialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;

/**
 * Created by Joaquin Carrasquero on 27/04/16.
 */
public class RequestReceiveHistoryFragment2 extends FermatWalletListFragment<LossProtectedPaymentRequest,ReferenceAppFermatSession<LossProtectedWallet>,ResourceProviderManager> implements FermatListItemListeners<LossProtectedPaymentRequest>, onRefreshList, OnLoadMoreDataListener {

    /**
     * Session
     */
    ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession;
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
    private int MAX_PAYMENT_REQUEST = 10;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;

    private FermatWorker fermatWorker;


    private PaymentRequestHistoryAdapter adapter;

    com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton;
    FloatingActionMenu actionMenu;
    private LossProtectedWalletSettings lossProtectedWalletSettings;

    private DialogInterface.OnDismissListener onDismissListener;

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

        referenceWalletSession = appSession;

        lstPaymentRequest = new ArrayList<>();
        try {
            lossProtectedWalletManager = appSession.getModuleManager();

            if(appSession.getData(SessionConstant.BLOCKCHANIN_TYPE) != null)
                blockchainNetworkType = (BlockchainNetworkType)appSession.getData(SessionConstant.BLOCKCHANIN_TYPE);
            else
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();


            try {
                boolean isHelpEnabled = (Boolean)appSession.getData(SessionConstant.PAYMENT_REQUEST_HELP_ENABLED);

                if (isHelpEnabled)
                    setUpTutorial(true);
                else
                    setUpTutorial(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
            onRefresh();
        } catch (Exception ex) {
            ex.printStackTrace();

            Toast.makeText(getActivity().getApplicationContext(), R.string.Whooops_text, Toast.LENGTH_SHORT).show();

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
            //setUp();
            return rootView;
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.Whooops_text, Toast.LENGTH_SHORT).show();
        }
        return container;
    }

    /*private void setUp() {
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
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            lstPaymentRequest = new ArrayList<LossProtectedPaymentRequest>();
        } catch (Exception e) {
            makeText(getActivity(), R.string.Whooops_text, Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Override
    public void onFragmentFocus() {
        super.onFragmentFocus();

        isRefreshing = false;
        onRefresh();
    }


    @Override
    protected boolean hasMenu() {
        return true;
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
    public RecyclerView.OnScrollListener getScrollListener() {
        if (scrollListener == null) {
            EndlessScrollListener endlessScrollListener = new EndlessScrollListener(getLayoutManager());
            endlessScrollListener.setOnLoadMoreDataListener(this);
            scrollListener = endlessScrollListener;
        }
        return scrollListener;
    }

    @Override
    public void onLoadMoreData(final int page, final int totalItemsCount) {
        adapter.setLoadingData(true);
        fermatWorker = new FermatWorker(getActivity(), this) {
            @Override
            protected Object doInBackground() throws Exception {
                return getMoreDataAsync(FermatRefreshTypes.NEW, page);
            }
        };

        fermatWorker.execute();
    }

    @Override
    public List<LossProtectedPaymentRequest> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<LossProtectedPaymentRequest> lstPaymentRequest = new ArrayList<LossProtectedPaymentRequest>();


            try {
                    offset = pos;
                lstPaymentRequest = lossProtectedWalletManager.listReceivedPaymentRequest(walletPublicKey, blockchainNetworkType, MAX_PAYMENT_REQUEST, offset);
                //offset += MAX_TRANSACTIONS;
            } catch (Exception e) {
                referenceWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                e.printStackTrace();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if(id == 1){
                changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_REQUEST_FORM_ACTIVITY,appSession.getAppPublicKey());
                return true;

            }else if (id==2){
                setUpTutorial(true);
                return true;
            }else {
                return true;
            }
        } catch (Exception e) {
            // errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.Whooops_text, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }



    private void setUpTutorial(boolean checkButton) throws CantGetSettingsException, SettingsNotFoundException {
        // if (isHelpEnabled) {
        Payment_Request_Help_Dialog payment_request_help_dialog = new Payment_Request_Help_Dialog(getActivity(), appSession, null, checkButton);
        payment_request_help_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Object b = appSession.getData(SessionConstant.PAYMENT_REQUEST_HELP_ENABLED);
                if (b != null) {
                    if ((Boolean) b) {
                        appSession.removeData(SessionConstant.PAYMENT_REQUEST_HELP_ENABLED);
                    }
                }

            }
        });
        payment_request_help_dialog.show();
        //  }
    }



    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            adapter.setLoadingData(false);
            if (result != null && result.length > 0) {
                    if (adapter != null){
                        if (offset==0){
                            lstPaymentRequest.clear();
                            lstPaymentRequest = (ArrayList) result[0];
                            adapter.changeDataSet(lstPaymentRequest);
                            ((EndlessScrollListener) scrollListener).notifyDataSetChanged();
                        }else {
                            for (LossProtectedPaymentRequest info : (List<LossProtectedPaymentRequest>) result[0]) {
                                if (notInList(info)) {
                                    lstPaymentRequest.add(info);
                                }
                            }
                            adapter.notifyItemRangeInserted(offset, lstPaymentRequest.size() - 1);
                        }
                    }
                    adapter.notifyDataSetChanged();
            }
        }
        FermatAnimationsUtils.showEmpty(getActivity(), lstPaymentRequest.isEmpty(), empty);

    }

    private boolean notInList(LossProtectedPaymentRequest info) {
        for (LossProtectedPaymentRequest request : lstPaymentRequest) {
            if (request.getRequestId().equals(info.getRequestId()))
                return false;
        }
        return true;
    }
    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    public void setReferenceWalletSession(ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }



}
