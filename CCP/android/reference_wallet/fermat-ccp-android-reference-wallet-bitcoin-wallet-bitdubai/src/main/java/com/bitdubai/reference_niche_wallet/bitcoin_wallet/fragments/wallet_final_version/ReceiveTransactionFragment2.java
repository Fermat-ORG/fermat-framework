package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.ReceivetransactionsExpandableAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.models.GrouperItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.PresentationBitcoinWalletDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author Matias Furszyfer
 * @since 7/10/2015
 */

public class ReceiveTransactionFragment2 extends FermatWalletExpandableListFragment<GrouperItem,ReferenceAppFermatSession<CryptoWallet>,ResourceProviderManager>

        implements FermatListItemListeners<CryptoWalletTransaction> {

    // Fermat Managers
    private CryptoWallet moduleManager;
    private ErrorManager errorManager;

    // Data
    private ArrayList<GrouperItem> openNegotiationList;
    private View rootView;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsAvailable;

    private View emptyListViewsContainer;
    private BlockchainNetworkType blockchainNetworkType;

    private AnimationManager animationManager;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private Handler mHandler;
    private int offset = 0;

    ActiveActorIdentityInformation intraUserLoginIdentity;


    public static ReceiveTransactionFragment2 newInstance() {
        return new ReceiveTransactionFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstCryptoWalletTransactionsAvailable = new ArrayList<>();
        mHandler = new Handler();
        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            if(appSession.getData(SessionConstant.BLOCKCHANIN_TYPE) != null)
                blockchainNetworkType = (BlockchainNetworkType)appSession.getData(SessionConstant.BLOCKCHANIN_TYPE);
            else
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

            } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }




        try {
            intraUserLoginIdentity = moduleManager.getSelectedActorIdentity();
        } catch (CantGetSelectedActorIdentityException e) {
            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }

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
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.error_std_message), Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if(id == 1){
                changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY,appSession.getAppPublicKey());
                return true;
            }else {
                if (id == 4) {
                    changeActivity(Activities.CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY, appSession.getAppPublicKey());
                    return true;
                }
                else {
                    setUpPresentation();
                    return true;
                }

            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.error_std_message), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        if(openNegotiationList!=null) {
            if (openNegotiationList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyListViewsContainer = layout.findViewById(R.id.empty);
                FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
            }
        }else{
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer = layout.findViewById(R.id.empty);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                /*LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.gravity= Gravity.CENTER_HORIZONTAL;
                emptyListViewsContainer.setLayoutParams(lp);

                ImageView imgview = (ImageView) layout.findViewById(R.id.img_empty);
                imgview.getLayoutParams().height = 60;
                imgview.getLayoutParams().width  = 60;

                FermatTextView textView = (FermatTextView) layout.findViewById(R.id.txt_empty);
                textView.setTextSize(18);*/
            }

            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public ExpandableRecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ReceivetransactionsExpandableAdapter(getActivity(), openNegotiationList,getResources());
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
        return R.layout.home_receive_main;
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
    public List<GrouperItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<GrouperItem> data = new ArrayList<>();
        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

        //noinspection TryWithIdenticalCatches
        try {

            if(intraUserLoginIdentity!=null) {

                String intraUserPk = intraUserLoginIdentity.getPublicKey();


                int MAX_TRANSACTIONS = 20;
                List<CryptoWalletTransaction> list = moduleManager.listLastActorTransactionsByTransactionType(
                        BalanceType.AVAILABLE, TransactionType.CREDIT, appSession.getAppPublicKey(),intraUserPk,
                        blockchainNetworkType, MAX_TRANSACTIONS, 0);

                if(list!=null)
                    lstCryptoWalletTransactionsAvailable.addAll(list);

                // available_offset = lstCryptoWalletTransactionsAvailable.size();
                //get transactions from actor public key to send me btc

                for (CryptoWalletTransaction cryptoWalletTransaction : lstCryptoWalletTransactionsAvailable) {
                    List<CryptoWalletTransaction> lst = moduleManager.listTransactionsByActorAndType(
                            BalanceType.AVAILABLE, TransactionType.CREDIT, appSession.getAppPublicKey(),
                            (cryptoWalletTransaction.getActorToType().equals(Actors.EXTRA_USER) ? cryptoWalletTransaction.getActorToPublicKey() : cryptoWalletTransaction.getActorFromPublicKey()),
                            intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, 0, (cryptoWalletTransaction.getActorToType().equals(Actors.EXTRA_USER)? cryptoWalletTransaction.getActorToType() : cryptoWalletTransaction.getActorFromType() ));

                    GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction> grouperItem = new GrouperItem<>(lst, false, cryptoWalletTransaction);
                    data.add(grouperItem);
                }
                if(!data.isEmpty())
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                        }
                    });


            }
        } catch (CantListTransactionsException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(CryptoWalletTransaction data, int position) {
        //TODO abrir actividad de detalle de contrato abierto
    }

    @Override
    public void onLongItemClickListener(CryptoWalletTransaction data, int position) {
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
                    recyclerView.setVisibility(View.VISIBLE);
                    FermatAnimationsUtils.showEmpty(getActivity(), false, emptyListViewsContainer);
                }
                else
                {
                    recyclerView.setVisibility(View.GONE);
                    FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
                }

            }else {
                recyclerView.setVisibility(View.GONE);
                FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);

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
                            appSession,
                            null,
                            (moduleManager.getActiveIdentities().isEmpty()) ? PresentationBitcoinWalletDialog.TYPE_PRESENTATION : PresentationBitcoinWalletDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES,
                            (Boolean)appSession.getData(SessionConstant.PRESENTATION_HELP_ENABLED));
            presentationBitcoinWalletDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Object o = appSession.getData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                    if (o != null) {
                        if ((Boolean) (o)) {
                            invalidate();
                            appSession.removeData(SessionConstant.PRESENTATION_IDENTITY_CREATED);
                        }
                    }

                }
            });
            presentationBitcoinWalletDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateViewOnUIThread(String code){
        try {
            if(code.equals("BlockchainDownloadComplete")) {
                //update toolbar color
                final Toolbar toolBar = getToolbar();

                toolBar.setBackgroundColor(Color.parseColor("#05CFC2"));
            } else {
                if(code.equals("Btc_arrive"))
                { //update transactions
                    onRefresh();
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

