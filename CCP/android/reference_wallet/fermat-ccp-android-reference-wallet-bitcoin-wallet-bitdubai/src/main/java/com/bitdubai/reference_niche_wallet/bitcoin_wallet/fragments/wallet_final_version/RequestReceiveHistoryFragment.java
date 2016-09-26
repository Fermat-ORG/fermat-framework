package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.OnLoadMoreDataListener;
import com.bitdubai.fermat_android_api.ui.util.EndlessScrollListener;
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
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.PaymentRequestHistoryAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.Payment_Request_Help_Dialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by mati on 2015.09.30..
 */
public class RequestReceiveHistoryFragment extends FermatWalletListFragment<PaymentRequest,ReferenceAppFermatSession<CryptoWallet>,ResourceProviderManager> implements FermatListItemListeners<PaymentRequest>,onRefreshList,OnLoadMoreDataListener {

    /**
     * Session
     */

    String walletPublicKey = "reference_wallet";
    /**
     * MANAGERS
     */
    private CryptoWallet cryptoWallet;
    /**
     * DATA
     */
    private List<PaymentRequest> lstPaymentRequest;
    private PaymentRequest selectedItem;
    /**
     * Executor Service
     */
    private ExecutorService executor;
    private int MAX_PAYMENT_REQUEST = 10;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;

    private FermatWorker fermatWorker;


    private PaymentRequestHistoryAdapter adapter;

    BlockchainNetworkType blockchainNetworkType;
    com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton = null;
    FloatingActionMenu actionMenu = null;
    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static RequestReceiveHistoryFragment newInstance() {
        return new RequestReceiveHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        lstPaymentRequest = new ArrayList<PaymentRequest>();
        try {
            cryptoWallet = appSession.getModuleManager();

            //lstPaymentRequest = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data

            getExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        final Drawable drawable = getResources().getDrawable(R.drawable.background_gradient, null);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    getPaintActivtyFeactures().setActivityBackgroundColor(drawable);
                                } catch (OutOfMemoryError o) {
                                    o.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });



            if(appSession.getData(SessionConstant.BLOCKCHANIN_TYPE) != null)
                blockchainNetworkType = (BlockchainNetworkType)appSession.getData(SessionConstant.BLOCKCHANIN_TYPE);
            else
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

            if ((Boolean)appSession.getData(SessionConstant.PAYMENT_REQUEST_HELP_ENABLED)){
                setUpTutorial(false);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.error_std_message), Toast.LENGTH_SHORT).show();

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


            onRefresh();
            //setUp();
            return rootView;
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.error_std_message), Toast.LENGTH_SHORT).show();
        }
        return container;
    }
    private void setUp(){
        FrameLayout frameLayout = new FrameLayout(getActivity());

        FrameLayout.LayoutParams lbs = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        frameLayout.setLayoutParams(lbs);

        //ImageView icon = new ImageView(getActivity());  Create an icon
        //icon.setImageResource(R.drawable.btn_request_selector);
        //icon.setImageResource(R.drawable.ic_contact_newcontact);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY);
            }
        };
        View view = new View(getActivity());
        view.setLayoutParams(lbs);

        frameLayout.addView(view);
        frameLayout.setOnClickListener(onClickListener);
        view.setOnClickListener(onClickListener);
        actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(getActivity())
                .setContentView(frameLayout).setBackgroundDrawable(R.drawable.btn_request_selector)
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
            lstPaymentRequest = new ArrayList<PaymentRequest>();
        } catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.error_std_message), Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == 1) {
                changeActivity(Activities.CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY, appSession.getAppPublicKey());
                return true;
            }
            if (id == 2){
                setUpTutorial((Boolean) appSession.getData(SessionConstant.PAYMENT_REQUEST_HELP_ENABLED));

            }
        } catch (Exception e) {
            // errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.error_std_message), Toast.LENGTH_SHORT).show();
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
        return R.layout.fragment_transaction_main;
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
            adapter = new PaymentRequestHistoryAdapter(getActivity(), lstPaymentRequest,cryptoWallet,appSession,this);
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
    public List<PaymentRequest> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<PaymentRequest> lstPaymentRequest  = new ArrayList<PaymentRequest>();

        try {
            offset = pos;
            lstPaymentRequest = cryptoWallet.listReceivedPaymentRequest(walletPublicKey, this.blockchainNetworkType ,MAX_PAYMENT_REQUEST,offset);
        } catch (Exception e) {
            appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            e.printStackTrace();
        }


        return lstPaymentRequest;

    }

    @Override
    public void onItemClickListener(PaymentRequest item, int position) {
        selectedItem = item;
        onRefresh();
        //showDetailsActivityFragment(selectedItem);
    }

    /**
     * On Long item Click Listener
     *
     * @param data
     * @param position
     */
    @Override
    public void onLongItemClickListener(PaymentRequest data, int position) {

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
                        lstPaymentRequest.addAll((ArrayList) result[0]);
                        adapter.changeDataSet(lstPaymentRequest);
                        ((EndlessScrollListener) scrollListener).notifyDataSetChanged();

                    }else {
                        for (PaymentRequest info : (List<PaymentRequest>) result[0]) {
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
        showOrHideEmptyView();
    }

    private boolean notInList(PaymentRequest info) {
        for (PaymentRequest request : lstPaymentRequest) {
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
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    private void showOrHideEmptyView() {
        final boolean show = lstPaymentRequest.isEmpty();
        final int animationResourceId = show ? android.R.anim.fade_in : android.R.anim.fade_out;

        Animation anim = AnimationUtils.loadAnimation(getActivity(), animationResourceId);
        if (show && (empty.getVisibility() == View.GONE || empty.getVisibility() == View.INVISIBLE)) {
            empty.setAnimation(anim);
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else if (!show && empty.getVisibility() == View.VISIBLE) {
            empty.setAnimation(anim);
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    public void setReferenceWalletSession(ReferenceAppFermatSession referenceWalletSession) {
        this.appSession = referenceWalletSession;
    }

   /* @Override
   public void onClick(View v) {
        try {
            PaymentRequest paymentRequest = selectedItem;
            int id = v.getId();
            if(id == R.id.btn_refuse_request){

                cryptoWallet.refuseRequest(paymentRequest.getRequestId());
                Toast.makeText(getActivity(),"Denegado",Toast.LENGTH_SHORT).show();
            }
            else if ( id == R.id.btn_accept_request){

                cryptoWallet.approveRequest(paymentRequest.getRequestId()
                        , cryptoWallet.getSelectedActorIdentity().getPublicKey(), BitcoinFee.valueOf("NORMAL").getFee(), FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS);
                Toast.makeText(getActivity(),"Aceptado",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e)
        {
            showMessage(getActivity(), "Cant Accept or Denied Receive Payment Exception- " + e.getMessage());
        }
    }*/


}


