package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

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
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.LossProtectedWalletConstants;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.ReceiveTransactionsExpandableAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.models.GrouperItem;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import static android.widget.Toast.makeText;

/**
 * Created by Gian Barboza on 17/05/16.
 */
public class SendTransactionFragment2 extends FermatWalletExpandableListFragment<GrouperItem,LossProtectedWalletSession,ResourceProviderManager>
        implements FermatListItemListeners<LossProtectedWalletTransaction> {

    // Fermat Managers
    private LossProtectedWallet lossProtectedWallet;
    private ErrorManager errorManager;
    SettingsManager<LossProtectedWalletSettings> settingsManager;

    // Data
    private ArrayList<GrouperItem> openNegotiationList;
    private View rootView;
    private List<LossProtectedWalletTransaction> lstLossProtectedWalletTransactionsAvailable;
    private LossProtectedWalletSession lossProtectedWalletSession;
    private View emptyListViewsContainer;
    private BlockchainNetworkType blockchainNetworkType;

    private AnimationManager animationManager;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private Handler mHandler;

    public static SendTransactionFragment2 newInstance() {
        return new SendTransactionFragment2();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            lossProtectedWalletSession = appSession;
            lossProtectedWallet = lossProtectedWalletSession.getModuleManager().getCryptoWallet();
            errorManager = appSession.getErrorManager();


            settingsManager = lossProtectedWalletSession.getModuleManager().getSettingsManager();


            LossProtectedWalletSettings bitcoinWalletSettings;
            try {
                bitcoinWalletSettings = settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());
            } catch (Exception e) {
                bitcoinWalletSettings = null;
            }
            if (bitcoinWalletSettings == null) {
                bitcoinWalletSettings = new LossProtectedWalletSettings();
                bitcoinWalletSettings.setIsContactsHelpEnabled(true);
                bitcoinWalletSettings.setIsPresentationHelpEnabled(true);
                bitcoinWalletSettings.setNotificationEnabled(true);
                bitcoinWalletSettings.setLossProtectedEnabled(true);

                settingsManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(), bitcoinWalletSettings);
            }

            //default btc network
            if (bitcoinWalletSettings.getBlockchainNetworkType() == null) {
                bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            }

            settingsManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(), bitcoinWalletSettings);

            blockchainNetworkType = settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey()).getBlockchainNetworkType();

            final LossProtectedWalletSettings bitcoinWalletSettingsTemp = bitcoinWalletSettings;


            openNegotiationList = (ArrayList<GrouperItem>) getMoreDataAsync(FermatRefreshTypes.NEW, 0);

        } catch (CantGetSettingsException e) {
            makeText(getActivity(), "Oooops! recovering from system error: CantGetSettingsException", Toast.LENGTH_SHORT).show();
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        } catch (CantPersistSettingsException e) {
            makeText(getActivity(), "Oooops! recovering from system error: CantPersistSettingsException", Toast.LENGTH_SHORT).show();
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        } catch (SettingsNotFoundException e) {
            makeText(getActivity(), "Oooops! recovering from system error: SettingsNotFoundException", Toast.LENGTH_SHORT).show();
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        } catch (CantGetCryptoLossProtectedWalletException e) {
            makeText(getActivity(), "Oooops! recovering from system error: CantGetCryptoLossProtectedWalletException", Toast.LENGTH_SHORT).show();
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //noinspection TryWithIdenticalCatches
        try {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        } catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }
    @Override
    public void onResume() {
        animationManager = new AnimationManager(rootView, emptyListViewsContainer);
        getPaintActivtyFeactures().addCollapseAnimation(animationManager);
        super.onResume();
    }

    @Override
    public void onStop() {
        getPaintActivtyFeactures().removeCollapseAnimation(animationManager);
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, LossProtectedWalletConstants.IC_ACTION_SEND, 0, "send")
                .setIcon(R.drawable.ic_actionbar_send)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(1, LossProtectedWalletConstants.IC_ACTION_HELP_CONTACT, 1, "help")
                .setIcon(R.drawable.loos_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if(id == LossProtectedWalletConstants.IC_ACTION_SEND){
                changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_SEND_FORM_ACTIVITY,lossProtectedWalletSession.getAppPublicKey());
                return true;
            }else if(id == LossProtectedWalletConstants.IC_ACTION_HELP_PRESENTATION){
                //setUpPresentation(settingsManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey()).isPresentationHelpEnabled());
                makeText(getActivity(), "Help Dialog for Transaction",
                        Toast.LENGTH_SHORT).show();
                return true;
            }


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        if (openNegotiationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer = layout.findViewById(R.id.empty);
            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public ExpandableRecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ReceiveTransactionsExpandableAdapter(getActivity(),openNegotiationList,getResources());
            //noinspection unchecked
            adapter.setChildItemFermatEventListeners(this);
        }
        return adapter;
    }


    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null)
            layoutManager = new LinearLayoutManager(getActivity());
        return layoutManager;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.loss_fragment_transaction_main_send;
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
    public List<GrouperItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {

        ArrayList<GrouperItem> data = new ArrayList<>();
        lstLossProtectedWalletTransactionsAvailable = new ArrayList<>();
        List<LossProtectedWalletTransaction> lst = new ArrayList<>();

        try {
            LossProtectedWalletIntraUserIdentity intraUserLoginIdentity = lossProtectedWalletSession.getIntraUserModuleManager();
            String intraUserPk = intraUserLoginIdentity.getPublicKey();
            int MAX_TRANSACTIONS = 20;
            List<LossProtectedWalletTransaction> list = lossProtectedWallet.listAllActorTransactionsByTransactionType(
                    BalanceType.AVAILABLE,
                    TransactionType.DEBIT,
                    lossProtectedWalletSession.getAppPublicKey(),
                    intraUserPk,
                    blockchainNetworkType,
                    MAX_TRANSACTIONS,0);

            if (list!=null)
                lstLossProtectedWalletTransactionsAvailable.addAll(list);

            //get Transaction for expendable child item
            for (LossProtectedWalletTransaction lossProtectedWalletTransaction : lstLossProtectedWalletTransactionsAvailable){

                LossProtectedWalletTransaction item = lossProtectedWallet.getTransaction(
                        lossProtectedWalletTransaction.getTransactionId(),
                        lossProtectedWalletSession.getAppPublicKey(),
                        intraUserPk);

                lst.add(item);

                GrouperItem<LossProtectedWalletTransaction, LossProtectedWalletTransaction> grouperItem = new GrouperItem<>(lst, false, lossProtectedWalletTransaction);
                data.add(grouperItem);
            }
            if(!data.isEmpty())
                FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);

        } catch (CantListCryptoWalletIntraUserIdentityException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error: CantListCryptoWalletIntraUserIdentityException",
                    Toast.LENGTH_SHORT).show();
        } catch (CantGetCryptoLossProtectedWalletException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error: CantGetCryptoLossProtectedWalletException",
                    Toast.LENGTH_SHORT).show();
        } catch (CantListLossProtectedTransactionsException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error: CantListLossProtectedTransactionsException",
                    Toast.LENGTH_SHORT).show();
        }

        return data;
    }


    @Override
    public void onItemClickListener(LossProtectedWalletTransaction data, int position) {

    }

    @Override
    public void onLongItemClickListener(LossProtectedWalletTransaction data, int position) {

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
                //noinspection unchecked
                openNegotiationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(openNegotiationList);

                if(openNegotiationList.size() > 0)
                {
                    recyclerView.setVisibility(View.GONE);
                    FermatAnimationsUtils.showEmpty(getActivity(), false, emptyListViewsContainer);
                }
            }else {

                FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);

            }
        }

    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            errorManager.reportUnexpectedPluginException(Plugins.LOSS_PROTECTED_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
        }
    }



}
