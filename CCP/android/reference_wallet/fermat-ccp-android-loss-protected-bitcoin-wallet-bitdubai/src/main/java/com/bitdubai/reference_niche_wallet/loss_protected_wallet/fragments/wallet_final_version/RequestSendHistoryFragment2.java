
package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
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
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.PaymentRequestHistoryAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.Payment_Request_Help_Dialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Joaquin Carrasquero on 27/04/16.
 */
public class RequestSendHistoryFragment2 extends FermatWalletListFragment<LossProtectedPaymentRequest,ReferenceAppFermatSession,ResourceProviderManager> implements FermatListItemListeners<LossProtectedPaymentRequest>, View.OnClickListener, onRefreshList, OnLoadMoreDataListener {

    /**
     * Session
     */
    ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession;
    String walletPublicKey = "loss_protected_wallet";
    /**
     * MANAGERS
     */
    private LossProtectedWallet lossProtectedWalletManager;
    //private LossProtectedWalletSettings lossProtectedWalletSettings;
    /**
     * DATA
     */
    private List<LossProtectedPaymentRequest> lstPaymentRequest;
    private LossProtectedPaymentRequest selectedItem;
    /**
     * Executor Service
     */
    //private ExecutorService executor;

    private int MAX= 10;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;

    private PaymentRequestHistoryAdapter adapter;
    private FermatWorker fermatWorker;


    BlockchainNetworkType blockchainNetworkType;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static RequestSendHistoryFragment2 newInstance() {
        return new RequestSendHistoryFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        referenceWalletSession = (ReferenceAppFermatSession<LossProtectedWallet>) appSession;

        lstPaymentRequest = new ArrayList<LossProtectedPaymentRequest>();

       try {
            lossProtectedWalletManager = referenceWalletSession.getModuleManager();

            if(appSession.getData(SessionConstant.BLOCKCHANIN_TYPE) != null)
                blockchainNetworkType = (BlockchainNetworkType)appSession.getData(SessionConstant.BLOCKCHANIN_TYPE);
            else
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

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

            return rootView;
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.Whooops_text, Toast.LENGTH_SHORT).show();
        }

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        return container;
    }

    @Override
    public void onFragmentFocus() {
        super.onFragmentFocus();
        isRefreshing = false;
        onRefresh();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            lstPaymentRequest = new ArrayList<LossProtectedPaymentRequest>();

            hideSoftKeyboard(getActivity());
        } catch (Exception e) {
            makeText(getActivity(), R.string.Whooops_text, Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
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
            //WalletStoreItemPopupMenuListener listener = getWalletStoreItemPopupMenuListener();
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
    public List<LossProtectedPaymentRequest> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<LossProtectedPaymentRequest> lstPaymentRequest = new ArrayList<LossProtectedPaymentRequest>();
       /* if(blockchainNetworkType != null)
        {
            try {
                //when refresh offset set 0
                if (refreshType.equals(FermatRefreshTypes.NEW))*/
            try{
                offset = pos;
                lstPaymentRequest = lossProtectedWalletManager.listSentPaymentRequest(walletPublicKey, blockchainNetworkType, MAX, offset);

            } catch (Exception e) {
                referenceWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                e.printStackTrace();
            }
      //}

        return lstPaymentRequest;
    }

    @Override
    public void onItemClickListener(LossProtectedPaymentRequest item, int position) {
        selectedItem = item;
        //showDetailsActivityFragment(selectedItem);
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
            adapter.setLoadingData(false);
            if (result != null && result.length > 0) {
                if (adapter != null) {
                    if (offset == 0) {
                        lstPaymentRequest.clear();
                        lstPaymentRequest.addAll((ArrayList) result[0]);
                        adapter.changeDataSet(lstPaymentRequest);
                        ((EndlessScrollListener) scrollListener).notifyDataSetChanged();
                    } else {
                        for (LossProtectedPaymentRequest info : (List<LossProtectedPaymentRequest>) result[0]) {
                            if (notInList(info)) {
                                lstPaymentRequest.add(info);
                            }
                        }
                        //lstPaymentRequest.addAll((ArrayList) result[0]);
                        adapter.notifyItemRangeInserted(offset, lstPaymentRequest.size() - 1);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
        FermatAnimationsUtils.showEmpty(getActivity(), false, empty);
    }

    private boolean notInList(LossProtectedPaymentRequest info) {
        for (LossProtectedPaymentRequest contact : lstPaymentRequest) {
            if (contact.getRequestId().equals(info.getRequestId()))
                return false;
        }
        return true;
    }
    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }


    public void setReferenceWalletSession(ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }

    @Override
    public void onClick(View v) {
        try {
            LossProtectedPaymentRequest paymentRequest = (LossProtectedPaymentRequest) referenceWalletSession.getData(SessionConstant.LAST_REQUEST_CONTACT);
            int id = v.getId();
            if (id == R.id.btn_refuse_request) {

                lossProtectedWalletManager.refuseRequest(paymentRequest.getRequestId());
                Toast.makeText(getActivity(), R.string.denied, Toast.LENGTH_SHORT).show();
            } else if (id == R.id.btn_accept_request) {
                lossProtectedWalletManager.approveRequest(paymentRequest.getRequestId(), lossProtectedWalletManager.getSelectedActorIdentity().getPublicKey());
                Toast.makeText(getActivity(), R.string.accepted_text, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            WalletUtils.showMessage(getActivity(), R.string.Cant_accept + e.getMessage());
        }

    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
