package com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version;

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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.PaymentRequest;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters.PaymentRequestHistoryAdapter;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.fermat_wallet.session.FermatWalletSessionReferenceApp;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;

/**
 * Created by mati on 2015.09.30..
 */
public class RequestReceiveHistoryFragment extends FermatWalletListFragment<PaymentRequest,ReferenceAppFermatSession<FermatWallet>,ResourceProviderManager> implements FermatListItemListeners<PaymentRequest>,onRefreshList {

    /**
     * Session
     */
    ReferenceAppFermatSession<FermatWallet> fermatWalletSessionReferenceApp;

    String walletPublicKey = "fermat_wallet";
    /**
     * MANAGERS
     */
    private FermatWallet cryptoWallet;
    private ErrorManager errorManager;
    /**
     * DATA
     */
    private List<PaymentRequest> lstPaymentRequest;
    private PaymentRequest selectedItem;
    /**
     * Executor Service
     */
    private ExecutorService executor;
    private int MAX_TRANSACTIONS = 20;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;


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

        fermatWalletSessionReferenceApp = appSession;

        lstPaymentRequest = new ArrayList<PaymentRequest>();
        try {
            cryptoWallet = fermatWalletSessionReferenceApp.getModuleManager();

            //lstPaymentRequest = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data

          /*  getExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        final Drawable drawable = getResources().getDrawable(R.drawable.background_gradient, null);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    getPaintActivtyFeactures().setActivityBackgroundColor(drawable);
                                }catch (OutOfMemoryError o){
                                    o.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
*/



            FermatWalletSettings fermatWalletSettings;
            try {
                fermatWalletSettings = cryptoWallet.loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey());
                this.blockchainNetworkType = fermatWalletSettings.getBlockchainNetworkType();
            }catch (Exception e){

            }


            onRefresh();
        } catch (Exception ex) {
            ex.printStackTrace();
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error:onCreate", Toast.LENGTH_SHORT).show();

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
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error:onCreateView", Toast.LENGTH_SHORT).show();
        }
        return container;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if(id == 2){
                changeActivity(Activities.CCP_BITCOIN_FERMAT_WALLET_REQUEST_FORM_ACTIVITY, fermatWalletSessionReferenceApp.getAppPublicKey());
                return true;
            }else {
                //setUpPresentation();
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
                changeActivity(Activities.CCP_BITCOIN_FERMAT_WALLET_REQUEST_FORM_ACTIVITY);
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
            makeText(getActivity(), "Oooops! recovering from system error:onActivityCreated", Toast.LENGTH_SHORT).show();
            fermatWalletSessionReferenceApp.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }



    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {

        return R.layout.fermat_wallet_fragment_transaction_main;

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
            adapter = new PaymentRequestHistoryAdapter(getActivity(), lstPaymentRequest,cryptoWallet, fermatWalletSessionReferenceApp,this);
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
    public List<PaymentRequest> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<PaymentRequest> lstPaymentRequest  = new ArrayList<PaymentRequest>();

        try {
            //when refresh offset set 0
            if(refreshType.equals(FermatRefreshTypes.NEW))
                offset = 0;
            lstPaymentRequest = cryptoWallet.listReceivedPaymentRequest(walletPublicKey, this.blockchainNetworkType ,10,offset);
            offset+=1;
        } catch (Exception e) {
            fermatWalletSessionReferenceApp.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
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
            if (result != null && result.length > 0) {
                lstPaymentRequest = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstPaymentRequest);
                if(lstPaymentRequest.isEmpty()) FermatAnimationsUtils.showEmpty(getActivity(),true,empty);
                else FermatAnimationsUtils.showEmpty(getActivity(),false,empty);

            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }



    public void setReferenceWalletSession(FermatWalletSessionReferenceApp fermatWalletSessionReferenceApp) {
        this.fermatWalletSessionReferenceApp = fermatWalletSessionReferenceApp;

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
                        , referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey());
                Toast.makeText(getActivity(),"Aceptado",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e)
        {
            showMessage(getActivity(), "Cant Accept or Denied Receive Payment Exception- " + e.getMessage());
        }
    }*/


}
