package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;

import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;

import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.provisionalAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.models.GrouperItem;

import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;

/**
 * Created by Joaquin Carrasquero on 05/04/16.
 */
public class SendTransactionFragment3 extends FermatWalletExpandableListFragment<GrouperItem,LossProtectedWalletSession,ResourceProviderManager> implements FermatListItemListeners<LossProtectedWalletTransaction>, View.OnClickListener,onRefreshList {

    /**
     * Session
     */
    LossProtectedWalletSession lossWalletSession;
    /**
     * MANAGERS
     */
    private LossProtectedWallet lossProtectedWalletManager;
    /**
     * DATA
     */
    private List<LossProtectedWalletTransaction> lst;
    private LossProtectedWalletTransaction selectedItem;
    /**
     * Executor Service
     */
    private ExecutorService executor;
    private int MAX_TRANSACTIONS = 20;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;
    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;

    //SettingsManager<LossProtectedWalletSettings> settingsManager;
    LossProtectedWalletSettings lossProtectedWalletSettings;

    BlockchainNetworkType blockchainNetworkType;

    private List<GrouperItem> openNegotiationList;

    List<LossProtectedWalletTransaction> list = new ArrayList<>();


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static SendTransactionFragment3 newInstance() {
        return new SendTransactionFragment3();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        lossWalletSession = appSession;

        lst = new ArrayList<LossProtectedWalletTransaction>();

        try {
            lossProtectedWalletManager = lossWalletSession.getModuleManager();

            try {
                lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(lossWalletSession.getAppPublicKey());
                this.blockchainNetworkType = lossProtectedWalletSettings.getBlockchainNetworkType();
            }catch (Exception e){

            }

            openNegotiationList = (ArrayList<GrouperItem>) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
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
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return container;
    }


    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        if (openNegotiationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer =(LinearLayout) layout.findViewById(R.id.empty);
            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
            //emptyListViewsContainer.setVisibility(View.VISIBLE);
        }
    }

    private void setUp(){
        try {
            setUpScreen();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setUpScreen() {
        int[] emptyOriginalPos = new int[2];
        if (emptyListViewsContainer != null) {
            emptyListViewsContainer.getLocationOnScreen(emptyOriginalPos);
            if (animationManager != null)
                animationManager.setEmptyOriginalPos(emptyOriginalPos);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            lst = new ArrayList<LossProtectedWalletTransaction>();
            animationManager = new AnimationManager(rootView,emptyListViewsContainer);
            getPaintActivtyFeactures().addCollapseAnimation(animationManager);
        } catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            lossWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }


    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.loss_transaction_history_main;
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
    public ExpandableRecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new provisionalAdapter(getActivity(), openNegotiationList,getResources());
            // setting up event listeners
            adapter.setChildItemFermatEventListeners(this);
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
    public List<GrouperItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        list  = new ArrayList<LossProtectedWalletTransaction>();
        ArrayList<GrouperItem> data = new ArrayList<>();

        try {

            if(blockchainNetworkType != null)
            {
                //when refresh offset set 0
                if(refreshType.equals(FermatRefreshTypes.NEW))
                    offset = 0;

                LossProtectedWalletIntraUserIdentity intraUserLoginIdentity = lossWalletSession.getIntraUserModuleManager();
                if(intraUserLoginIdentity!=null) {
                    String intraUserPk = intraUserLoginIdentity.getPublicKey();
                    lst = lossProtectedWalletManager.listAllActorTransactionsByTransactionType(BalanceType.AVAILABLE, TransactionType.DEBIT, lossWalletSession.getAppPublicKey(), intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, 0);
                    offset+=MAX_TRANSACTIONS;

                    list = lst;


                    for (LossProtectedWalletTransaction cryptoWalletTransaction : list) {
                        List<LossProtectedWalletTransaction> list = new ArrayList<>();
                        list.add(cryptoWalletTransaction);
                        GrouperItem<LossProtectedWalletTransaction,LossProtectedWalletTransaction> grouperItem = new GrouperItem<>(list,false,cryptoWalletTransaction);
                        data.add(grouperItem);
                        list.clear();
                    }

                    if(!data.isEmpty()){
                        FermatAnimationsUtils.showEmpty(getActivity(),true,emptyListViewsContainer);
                    }


                }
            }



        } catch (Exception e) {
            lossWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            e.printStackTrace();
        }

        return data;

    }

    @Override
    public void onItemClickListener(LossProtectedWalletTransaction item, int position) {
        selectedItem = item;
    }

    /**
     * On Long item Click Listener
     *
     * @param data
     * @param position
     */
    @Override
    public void onLongItemClickListener(LossProtectedWalletTransaction data, int position) {

    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                openNegotiationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(openNegotiationList);
                if(openNegotiationList.size() > 0)
                    FermatAnimationsUtils.showEmpty(getActivity(), false, emptyListViewsContainer);
            }
            else {

                FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);

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
        this.lossWalletSession = referenceWalletSession;
    }

    @Override
    public void onClick(View v) {

    }
}
