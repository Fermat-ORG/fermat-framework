package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.EntryItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.Item;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.SectionItem;


import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;


/**
 * Created by Matias Furszyfer
 */
public class TransactionsFragment extends Fragment{

    private static final String ARG_POSITION = "position";


    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private CryptoWalletManager cryptoWalletManager;
    private CryptoWallet cryptoWallet;

    /**
     * Deals with error manager
     */
    private ErrorManager errorManager;

    /**
     * Screen views
     */
    private View rootView;
    private ListView listViewTransactions;
    private SwipeRefreshLayout swipeRefreshLayout;


    /**
     * List transactions
     */
    private List<CryptoWalletTransaction> lstTransactions = new ArrayList<CryptoWalletTransaction>();


    /**
     * List of transactions pointers
     */
    //TODO: esto deberia ir en la session
    private int pointerOffset = 0;
    private int cantTransactions = 10;


    /**
     * Fragment Style
     */
    Typeface tf;

    /**
     * List of Items to show in the list
     */
    ArrayList<Item> items = new ArrayList<Item>();

    /**
     * Map of transactions ordered
     */

    Map<Date,Set<CryptoWalletTransaction>> mapTransactionPerDate;

    /**
     * Wallet session
     */
    private WalletSession walletSession;


    public static TransactionsFragment newInstance(int position,WalletSession walletSession) {
        TransactionsFragment f = new TransactionsFragment();
        f.setWalletSession(walletSession);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        cryptoWalletManager = walletSession.getCryptoWalletManager();
        errorManager = walletSession.getErrorManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(),"CantGetCryptoWalletException- " + e.getMessage());

        }

        mapTransactionPerDate= new HashMap<Date, Set<CryptoWalletTransaction>>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_transactions, container, false);
        // Get ListView object from xml
        listViewTransactions = (ListView) rootView.findViewById(R.id.transactionlist);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        //adapter.






        // Create the adapter to convert the array to views

        try {
            lstTransactions=cryptoWallet.getTransactions(cantTransactions,pointerOffset, wallet_id);
        } catch (CantGetTransactionsException e) {
            e.printStackTrace();
        }

        BalanceType balanceType = walletSession.getBalanceTypeSelected();
        lstTransactions=showTransactionListSelected(lstTransactions,balanceType);

        // Set the emptyView to the ListView
        TextView textViewEmptyListView = (TextView) rootView.findViewById(R.id.emptyElement);

        if(lstTransactions.isEmpty()){
            textViewEmptyListView.setTypeface(tf);
            listViewTransactions.setEmptyView(textViewEmptyListView);
        }

        /**
         * Setting swipe Refresh
         */
        swipeRefreshLayout.setColorSchemeColors(R.color.green);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTransactionsContent();
            }
        });



        /*listViewTransactions.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //view.
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //showMessage("holas");
                Toast.makeText(getActivity(),"visible item count:"+visibleItemCount+"\n"
                        +"first vible item:"+firstVisibleItem+"\n"
                        +"total item count:"+totalItemCount,Toast.LENGTH_SHORT).show();

            }
        });
        */

        /**
         * Load transactions
         */
        loadTransactionMap();


        for (Date date: mapTransactionPerDate.keySet()){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            items.add(new SectionItem(sdf.format(date)));
            for(CryptoWalletTransaction cryptoWalletTransaction: mapTransactionPerDate.get(date)){
                items.add(new EntryItem(cryptoWalletTransaction));
            }
        }

        /**
         *
         */
        EntryAdapter adapter = new EntryAdapter(getActivity(), items);
        listViewTransactions.setAdapter(adapter);

        return rootView;
    }

    /**
     *  Order transactions in a map
     */
    private void loadTransactionMap(){
        for(CryptoWalletTransaction transaction:lstTransactions){
            Date date = new Date(transaction.getBitcoinWalletTransaction().getTimestamp());
            if(!mapTransactionPerDate.containsKey(date)){
                Set<CryptoWalletTransaction> cryptoWalletTransactionSet = new HashSet<CryptoWalletTransaction>();
                cryptoWalletTransactionSet.add(transaction);
                mapTransactionPerDate.put(date,cryptoWalletTransactionSet);
            }else{
                mapTransactionPerDate.get(date).add(transaction);
            }
        }
    }

    /**
     *  Obtain only the transactions that the user want
     *
     * @param lstTransactions
     * @param balanceType
     * @return
     */
    private List<CryptoWalletTransaction> showTransactionListSelected(List<CryptoWalletTransaction> lstTransactions, BalanceType balanceType) {
        List<CryptoWalletTransaction> lstToShow = new ArrayList<CryptoWalletTransaction>();
        for (CryptoWalletTransaction t : lstTransactions) {
            if (t.getBitcoinWalletTransaction().getBalanceType()==(balanceType)) {
                lstToShow.add(t);
            }
        }
        return lstToShow;
    }

    /**
     * Refresh
     */
    private void refreshTransactionsContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNewTransactions();
                //transactionArrayAdapter = new TransactionArrayAdapter(getActivity(),lstTransactions);
                //listViewTransactions.setAdapter(transactionArrayAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);

    }

    /**
     *  Update transaction list
     */
    private void loadNewTransactions(){

        if(lstTransactions.isEmpty())
            try {
                List<CryptoWalletTransaction> lst =cryptoWallet.getTransactions(cantTransactions, pointerOffset, wallet_id);
                for(CryptoWalletTransaction transaction: lst){
                    lstTransactions.add(0, transaction);
                }

            } catch (CantGetTransactionsException e)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage(getActivity(),"Cant Get Transactions Exception- " + e.getMessage());
            }
            catch(Exception ex)
            {
                showMessage(getActivity(),"Unexpected error get Transactions - " + ex.getMessage());
            }
        else{
            try {
                List<CryptoWalletTransaction> lst =cryptoWallet.getTransactions(cantTransactions,pointerOffset, wallet_id);
                for(CryptoWalletTransaction transaction: lst){
                   lstTransactions.add(0, transaction);
                }

            } catch (CantGetTransactionsException e)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage(getActivity(),"Cant Get Transactions Exception- " + e.getMessage());
            }
            catch(Exception ex)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                showMessage(getActivity(),"Unexpected error get Transactions - " + ex.getMessage());

            }
        }
        pointerOffset=lstTransactions.size();

        showTransactionListSelected(lstTransactions, walletSession.getBalanceTypeSelected());

    }


    /**
     * List Item adapter
     */

    public class EntryAdapter extends ArrayAdapter<Item> {

        private Context context;
        private ArrayList<Item> items;
        private LayoutInflater vi;

        public EntryAdapter(Context context,ArrayList<Item> items) {
            super(context,0, items);
            this.context = context;
            this.items = items;
            vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            final Item item = items.get(position);
            if (item != null) {
                if(item.isSection()){
                    SectionItem si = (SectionItem)item;
                    v = vi.inflate(R.layout.list_item_section, null);

                    v.setOnClickListener(null);
                    v.setOnLongClickListener(null);
                    v.setLongClickable(false);

                    final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
                    sectionView.setText(si.getTitle());

                }else{
                    EntryItem entryItem = (EntryItem)item;
                    /**
                     * Getting Views
                     */
                    v = vi.inflate(R.layout.wallets_bitcoin_fragment_transactions_list_items2,null);
                    final TextView textView_contact_name =(TextView)v.findViewById(R.id.textView_contact_name);
                    final TextView textView_type =(TextView)v.findViewById(R.id.textView_type);
                    final TextView textView_amount =(TextView)v.findViewById(R.id.textView_amount);
                    final TextView textView_time =(TextView)v.findViewById(R.id.textView_time);
                    final ImageView imageView_contact =(ImageView)v.findViewById(R.id.imageView_contact);

                    /**
                     * Setting values and validations
                     */
                    if (textView_contact_name != null)
                        textView_contact_name.setText(entryItem.cryptoWalletTransaction.getInvolvedActorName());
                    if(textView_amount != null)
                        if(walletSession.getBalanceTypeSelected()==BalanceType.AVAILABLE){
                            textView_amount.setText(WalletUtils.formatBalanceString(entryItem.cryptoWalletTransaction.getBitcoinWalletTransaction().getRunningAvailableBalance()));
                        }else if (walletSession.getBalanceTypeSelected()==BalanceType.BOOK)
                            textView_amount.setText(WalletUtils.formatBalanceString(entryItem.cryptoWalletTransaction.getBitcoinWalletTransaction().getRunningBookBalance()));
                    if(textView_time!=null){
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                        textView_time.setText(sdf.format(entryItem.cryptoWalletTransaction.getBitcoinWalletTransaction().getTimestamp()));
                    }
                    if(textView_type!=null){
                        if(entryItem.cryptoWalletTransaction.getBitcoinWalletTransaction().getTransactionType()==TransactionType.CREDIT){
                            textView_type.setText("Received");
                        }else if(entryItem.cryptoWalletTransaction.getBitcoinWalletTransaction().getTransactionType()==TransactionType.DEBIT){
                            textView_type.setText("Send");
                        }
                    }

                }
            }
            return v;
        }

    }

    /**
     *  Set wallet session inside the fragment when is created
     *
     * @param walletSession
     */
    public void setWalletSession(WalletSession walletSession) {
        this.walletSession = walletSession;
    }

}
