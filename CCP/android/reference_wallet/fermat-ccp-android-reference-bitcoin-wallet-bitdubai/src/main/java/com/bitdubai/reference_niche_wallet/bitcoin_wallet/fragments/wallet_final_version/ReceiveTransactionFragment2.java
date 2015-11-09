package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
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
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.ReceivetransactionsExpandableAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.models.GrouperItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.models.NegotiationInformationTestData;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer.NavigationDrawerArrayAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author Matias Furszyfer
 * @since 7/10/2015
 */
public class ReceiveTransactionFragment2 extends FermatWalletExpandableListFragment<GrouperItem>
        implements FermatListItemListeners<CryptoWalletTransaction> {

    private int MAX_TRANSACTIONS = 20;

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
    //private CryptoBrokerWallet cryptoBrokerWallet;

    ReferenceWalletSession referenceWalletSession;



    public static ReceiveTransactionFragment2 newInstance() {
        return new ReceiveTransactionFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

        lstCryptoWalletTransactionsBook = new ArrayList<>();

        try {
            referenceWalletSession = (ReferenceWalletSession) walletSession;
            moduleManager = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();
            errorManager = walletSession.getErrorManager();
        } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

        openNegotiationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            setUp(inflater);
        } catch (CantGetActiveLoginIdentityException e) {
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }
    private void setUp(LayoutInflater inflater) throws CantGetActiveLoginIdentityException {
        //setUpHeader(inflater);
        //setUpDonut(inflater);
        WalletUtils.setNavigatitDrawer(getPaintActivtyFeactures(),referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity());
    }

    private void setUpDonut(LayoutInflater inflater){
        RelativeLayout container_header_balance = getActivityHeader();
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

//        try {
//            //long balance = cryptoWallet.getBalance(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), referenceWalletSession.getWalletSessionType().getWalletPublicKey());
//            //txt_balance_amount.setText(formatBalanceString(balance, referenceWalletSession.getTypeAmount()));
//        } catch (CantGetBalanceException e) {
//            e.printStackTrace();
//        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            CharSequence itemTitle = item.getTitle();

            if(item.getItemId() ==  R.id.actionbar_send){;
                referenceWalletSession.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS,Boolean.TRUE);
                changeActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);
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

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        if (openNegotiationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            View emptyListViewsContainer = layout.findViewById(R.id.empty);
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
        return R.layout.fragment_open_contracts_tab;
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

            String intraUserPk = referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey();

            List<CryptoWalletTransaction> list =moduleManager.listLastActorTransactionsByTransactionType(BalanceType.AVAILABLE, TransactionType.CREDIT, referenceWalletSession.getWalletSessionType().getWalletPublicKey(),intraUserPk, MAX_TRANSACTIONS, available_offset);

            lstCryptoWalletTransactionsAvailable.addAll(list);

            available_offset = lstCryptoWalletTransactionsAvailable.size();

            lstCryptoWalletTransactionsBook.addAll(moduleManager.listLastActorTransactionsByTransactionType(BalanceType.BOOK, TransactionType.CREDIT, referenceWalletSession.getWalletSessionType().getWalletPublicKey(),intraUserPk, MAX_TRANSACTIONS, book_offset));

            book_offset = lstCryptoWalletTransactionsBook.size();


            for(CryptoWalletTransaction cryptoWalletTransaction : list){
                List<CryptoWalletTransaction> lst = new ArrayList<>();
                lst = moduleManager.getTransactions(intraUserPk,BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), TransactionType.CREDIT, referenceWalletSession.getWalletSessionType().getWalletPublicKey(), MAX_TRANSACTIONS, 0);
                GrouperItem<CryptoWalletTransaction,CryptoWalletTransaction> grouperItem = new GrouperItem<CryptoWalletTransaction,CryptoWalletTransaction>(lst,false,cryptoWalletTransaction);
                data.add(grouperItem);
            }

        } catch (CantListTransactionsException e) {
            e.printStackTrace();
        } catch (CantGetActiveLoginIdentityException e) {
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
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,ex);
        }
    }

    private void changeAmountType(TextView txt_balance_amount){
        //referenceWalletSession.setTypeAmount((referenceWalletSession.getTypeAmount()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN);
        updateBalances();
    }



    /**
     * Method to change the balance type
     */
    private void changeBalanceType(TextView txt_type_balance,TextView txt_balance_amount) {

        try {
            if (((ReferenceWalletSession)walletSession).getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                //txt_balance_amount.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.book_balance);
               // referenceWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            //} else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                //bookBalance = loadBalance(BalanceType.BOOK);
            //    txt_balance_amount.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
           //     txt_type_balance.setText(R.string.available_balance);
               // referenceWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
           // referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }

    private long loadBalance(BalanceType balanceType){
        long balance = 0;
//        try {
//            balance = cryptoWallet.getBalance(balanceType,referenceWalletSession.getWalletSessionType().getWalletPublicKey());
//        } catch (CantGetBalanceException e) {
//            e.printStackTrace();
//        }
        return balance;
    }


    private void updateBalances(){
        //bookBalance = loadBalance(BalanceType.BOOK);
        balanceAvailable = loadBalance(BalanceType.AVAILABLE);
//        txt_balance_amount.setText(
//                WalletUtils.formatBalanceString(
//                        (referenceWalletSession.getBalanceTypeSelected() == BalanceType.AVAILABLE.getCode())
//                                ? balanceAvailable : bookBalance,
//                        referenceWalletSession.getTypeAmount())
//        );
    }

}

