package com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletModuleTransaction;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.FermatWalletConstants;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters.ReceivetransactionsAdapter;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.fermat_wallet.session.SessionConstant;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author Matias Furszyfer
 * @since 7/10/2015
 */

public class ReceiveTransactionFragment2 extends FermatWalletListFragment<FermatWalletModuleTransaction,ReferenceAppFermatSession<FermatWallet>,ResourceProviderManager>
        implements FermatListItemListeners<FermatWalletModuleTransaction> {

    // Fermat Managers
    private FermatWallet moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<FermatWalletModuleTransaction> lstFermatWalletTransaction;
    private View rootView;
    private List<FermatWalletModuleTransaction> lstCryptoWalletTransactionsAvailable;

    private ReferenceAppFermatSession<FermatWallet> fermatWalletSessionReferenceApp;

    private View emptyListViewsContainer;
    private BlockchainNetworkType blockchainNetworkType;

    private AnimationManager animationManager;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private Handler mHandler;


    public static ReceiveTransactionFragment2 newInstance() {
        return new ReceiveTransactionFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstCryptoWalletTransactionsAvailable = new ArrayList<>();
        mHandler = new Handler();
        FermatWalletSettings fermatWalletSettings;
        try {
            fermatWalletSessionReferenceApp = appSession;
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            if((moduleManager!=null)) {
                fermatWalletSettings = moduleManager.loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey());

                if (fermatWalletSettings != null) {

                    if (fermatWalletSettings.getBlockchainNetworkType() == null)
                        fermatWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                    moduleManager.persistSettings(fermatWalletSessionReferenceApp.getAppPublicKey(), fermatWalletSettings);

                }

                if (fermatWalletSettings != null) {
                    if (fermatWalletSettings.getBlockchainNetworkType() == null)
                        fermatWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                }
                moduleManager.persistSettings(fermatWalletSessionReferenceApp.getAppPublicKey(), fermatWalletSettings);

                blockchainNetworkType = fermatWalletSettings.getBlockchainNetworkType();
            }



            } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_FERMAT_WALLET_ALL_BITDUBAI,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }


        //list transaction on background
       onRefresh();


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
        //getPaintActivtyFeactures().addCollapseAnimation(animationManager);
        super.onResume();
    }

    @Override
    public void onStop() {
        //getPaintActivtyFeactures().removeCollapseAnimation(animationManager);
        super.onStop();
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
                changeActivity(Activities.CCP_BITCOIN_FERMAT_WALLET_SEND_FORM_ACTIVITY, fermatWalletSessionReferenceApp.getAppPublicKey());
                return true;
            }else if(id == FermatWalletConstants.IC_ACTION_HELP_CONTACT){
                setUpPresentation();
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        //RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.gradient);
       // recyclerView.addItemDecoration(itemDecoration);

        if(lstFermatWalletTransaction!=null) {
            if (lstFermatWalletTransaction.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyListViewsContainer = layout.findViewById(R.id.empty);
                //FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                emptyListViewsContainer.setVisibility(View.VISIBLE);
            }
        }else{
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer = layout.findViewById(R.id.empty);
           // FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ReceivetransactionsAdapter(getActivity(), lstFermatWalletTransaction,getResources());
            // setting up event listeners
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
        return R.layout.fermat_wallet_home_receive_main;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.open_contracts_recycler_view;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    public List<FermatWalletModuleTransaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<FermatWalletModuleTransaction> data = new ArrayList<>();
        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

        //noinspection TryWithIdenticalCatches
        try {
            ActiveActorIdentityInformation intraUserLoginIdentity = moduleManager.getSelectedActorIdentity();
            if(intraUserLoginIdentity!=null) {

                String intraUserPk = intraUserLoginIdentity.getPublicKey();

                BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(
                        moduleManager.loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey()).getBlockchainNetworkType().getCode());

                int MAX_TRANSACTIONS = 20;
                List<FermatWalletModuleTransaction> list = moduleManager.listLastActorTransactionsByTransactionType(
                        BalanceType.AVAILABLE, TransactionType.CREDIT, fermatWalletSessionReferenceApp.getAppPublicKey(),intraUserPk,
                        blockchainNetworkType, MAX_TRANSACTIONS, 0);

                if(list!=null)
                    lstCryptoWalletTransactionsAvailable.addAll(list);

                if(!data.isEmpty())
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                        }
                    });


            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return lstCryptoWalletTransactionsAvailable;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(FermatWalletModuleTransaction data, int position) {

    }

    @Override
    public void onLongItemClickListener(FermatWalletModuleTransaction data, int position) {
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                //noinspection unchecked
                lstFermatWalletTransaction = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstFermatWalletTransaction);

                if(lstFermatWalletTransaction.size() > 0)
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    //FermatAnimationsUtils.showEmpty(getActivity(), false, emptyListViewsContainer);
                }
            }else {
                recyclerView.setVisibility(View.GONE);
                //FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);

            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
        }
    }

    private void setUpPresentation() {
        //noinspection TryWithIdenticalCatches
        try {
            PresentationBitcoinWalletDialog presentationBitcoinWalletDialog =
                    new PresentationBitcoinWalletDialog(
                            getActivity(),
                            fermatWalletSessionReferenceApp,
                            null,
                            (moduleManager.getActiveIdentities().isEmpty()) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES,
                            moduleManager.loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey()).isPresentationHelpEnabled());
            presentationBitcoinWalletDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Object o = fermatWalletSessionReferenceApp.getData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                    if (o != null) {
                        if ((Boolean) (o)) {
                            invalidate();
                            fermatWalletSessionReferenceApp.removeData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                        }
                    }

                }
            });
            presentationBitcoinWalletDialog.show();
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        }
    }
}

