package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;

import com.bitdubai.fermat_android_api.ui.Views.CircularProgressBar;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.BitcoinWalletConstants;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.ReceivetransactionsExpandableAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.animation.AnimationManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.models.GrouperItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer.NavigationViewAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer.NavigationViewPainter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.FragmentsCommons;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 20/10/2015
 */
public class SendTransactionFragment2 extends FermatWalletExpandableListFragment<GrouperItem>
        implements FermatListItemListeners<CryptoWalletTransaction>{


    private int MAX_TRANSACTIONS = 20;

    final TransactionType transactionType = TransactionType.DEBIT;

    // Fermat Managers
    private CryptoWallet moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<GrouperItem> openNegotiationList;
    private TextView txt_type_balance;
    private TextView txt_balance_amount;
    private long balanceAvailable;
    private View rootView;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsAvailable;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsBook;
    private int available_offset=0;
    private int book_offset=0;

    ReferenceWalletSession referenceWalletSession;
    private long bookBalance;
    private LinearLayout emptyListViewsContainer;
    private AnimationManager animationManager;


    public static SendTransactionFragment2 newInstance() {
        return new SendTransactionFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

        lstCryptoWalletTransactionsBook = new ArrayList<>();

        try {
            referenceWalletSession = (ReferenceWalletSession) walletSession;
            moduleManager = referenceWalletSession.getModuleManager().getCryptoWallet();
            errorManager = walletSession.getErrorManager();

            IntraUserLoginIdentity lst = referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity();
            if(lst==null){
                startWizard(WizardTypes.CCP_WALLET_BITCOIN_START_WIZARD.getKey(),walletSession, walletSettings, walletResourcesProviderManager, null);
            }
        } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

        openNegotiationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            animationManager = new AnimationManager(rootView,emptyListViewsContainer);
            getPaintActivtyFeactures().addCollapseAnimation(animationManager);
            getPaintActivtyFeactures().addNavigationView(new NavigationViewPainter(getActivity(), referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity()));
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        setUp(inflater);


        return rootView;
    }
    private void setUp(LayoutInflater inflater){
        try {
            //setUpHeader(inflater);
            setUpDonut(inflater);
            setUpScreen();
        }catch (Exception e){
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                    UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
    }

    private void setUpScreen(){
        int[] emptyOriginalPos = new int[2];
        if(emptyListViewsContainer!=null) {
            emptyListViewsContainer.getLocationOnScreen(emptyOriginalPos);
            if (animationManager != null)
                animationManager.setEmptyOriginalPos(emptyOriginalPos);
        }

    }



    private void setUpDonut(LayoutInflater inflater){
        RelativeLayout container_header_balance = getToolbarHeader();
        try {
            container_header_balance.removeAllViews();
        }catch (Exception e){

        }


        container_header_balance.setBackgroundColor(Color.parseColor("#06356f"));
        container_header_balance.setVisibility(View.VISIBLE);


        View balance_header = inflater.inflate(R.layout.donut_header, container_header_balance, true);

        CircularProgressBar circularProgressBar = (CircularProgressBar) balance_header.findViewById(R.id.progress);

        circularProgressBar.setProgressValue(20);
        circularProgressBar.setProgressValue2(28);
        circularProgressBar.setBackgroundProgressColor(Color.parseColor("#022346"));
        circularProgressBar.setProgressColor(Color.parseColor("#05ddd2"));
        circularProgressBar.setProgressColor2(Color.parseColor("#05537c"));


        txt_type_balance = (TextView) balance_header.findViewById(R.id.txt_type_balance);
        //txt_type_balance.setTypeface(tf);

        //((TextView) balance_header.findViewById(R.id.txt_touch_to_change)).setTypeface(tf);

        TextView txt_amount_type = (TextView) balance_header.findViewById(R.id.txt_balance_amount_type);

        txt_type_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"balance cambiado",Toast.LENGTH_SHORT).show();
                //txt_type_balance.setText(referenceWalletSession.getBalanceTypeSelected());
                changeBalanceType(txt_type_balance, txt_balance_amount);
            }
        });


        txt_balance_amount = (TextView) balance_header.findViewById(R.id.txt_balance_amount);

        txt_balance_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"balance cambiado",Toast.LENGTH_SHORT).show();
                //txt_type_balance.setText(referenceWalletSession.getBalanceTypeSelected());
                changeAmountType(txt_balance_amount);
            }
        });
        txt_amount_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"balance cambiado",Toast.LENGTH_SHORT).show();
                //txt_type_balance.setText(referenceWalletSession.getBalanceTypeSelected());
                changeAmountType(txt_balance_amount);
            }
        });

        txt_balance_amount = (TextView) balance_header.findViewById(R.id.txt_balance_amount);
        //txt_balance_amount.setTypeface(tf);

        try {
            long balance = moduleManager.getBalance(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), referenceWalletSession.getAppPublicKey());
            txt_balance_amount.setText(WalletUtils.formatBalanceString(balance, referenceWalletSession.getTypeAmount()));
        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        menu.add(0, BitcoinWalletConstants.IC_ACTION_SEND, 0, "send").setIcon(R.drawable.ic_actionbar_send)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if(id == BitcoinWalletConstants.IC_ACTION_SEND){
                changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY,referenceWalletSession.getAppPublicKey());
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
            emptyListViewsContainer =(LinearLayout) layout.findViewById(R.id.empty);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    public ExpandableRecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ReceivetransactionsExpandableAdapter(getActivity(), openNegotiationList,getResources());
            // setting up event listeners
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
        return R.layout.home_send_main;
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

        try {
            IntraUserLoginIdentity intraUserLoginIdentity = referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity();
            if(intraUserLoginIdentity!=null) {
                String intraUserPk = intraUserLoginIdentity.getPublicKey();

                List<CryptoWalletTransaction> list = moduleManager.listLastActorTransactionsByTransactionType(BalanceType.AVAILABLE, TransactionType.DEBIT, referenceWalletSession.getAppPublicKey(), intraUserPk, MAX_TRANSACTIONS, available_offset);

                lstCryptoWalletTransactionsAvailable.addAll(list);

                available_offset = lstCryptoWalletTransactionsAvailable.size();

                lstCryptoWalletTransactionsBook.addAll(moduleManager.listLastActorTransactionsByTransactionType(BalanceType.BOOK, TransactionType.DEBIT, referenceWalletSession.getAppPublicKey(), intraUserPk, MAX_TRANSACTIONS, book_offset));

                book_offset = lstCryptoWalletTransactionsBook.size();


                for (CryptoWalletTransaction cryptoWalletTransaction : list) {
                    List<CryptoWalletTransaction> lst = new ArrayList<>();
                     lst = moduleManager.listTransactionsByActorAndType(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), TransactionType.DEBIT, referenceWalletSession.getAppPublicKey(), cryptoWalletTransaction.getActorToPublicKey(), intraUserPk, MAX_TRANSACTIONS, 0);

                    GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction> grouperItem = new GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction>(lst, false, cryptoWalletTransaction);
                    data.add(grouperItem);
                }

            }

        } catch (CantListTransactionsException e) {
            e.printStackTrace();
        } catch (CantGetActiveLoginIdentityException e) {
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
                openNegotiationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(openNegotiationList);
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

    private void changeAmountType(TextView txt_balance_amount){
        referenceWalletSession.setTypeAmount((referenceWalletSession.getTypeAmount()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN);
        updateBalances();
    }



    /**
     * Method to change the balance type
     */
    private void changeBalanceType(TextView txt_type_balance,TextView txt_balance_amount) {

        try {
            if (((ReferenceWalletSession)walletSession).getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                txt_balance_amount.setText(WalletUtils.formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.book_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            } else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                bookBalance = loadBalance(BalanceType.BOOK);
               txt_balance_amount.setText(WalletUtils.formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.available_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }

    private long loadBalance(BalanceType balanceType){
        long balance = 0;
        try {
            balance = referenceWalletSession.getModuleManager().getCryptoWallet().getBalance(balanceType, referenceWalletSession.getAppPublicKey());
        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
        }
        return balance;
    }


    private void updateBalances(){
        bookBalance = loadBalance(BalanceType.BOOK);
        balanceAvailable = loadBalance(BalanceType.AVAILABLE);
        txt_balance_amount.setText(
                WalletUtils.formatBalanceString(
                        (referenceWalletSession.getBalanceTypeSelected() == BalanceType.AVAILABLE.getCode())
                                ? balanceAvailable : bookBalance,
                        referenceWalletSession.getTypeAmount())
        );
    }





}

