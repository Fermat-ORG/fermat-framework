package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.interfaces.EntryItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.interfaces.FermatListViewFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.interfaces.Item;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.interfaces.SectionItem;


import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**
 * Created by Matias
 */
public class TransactionsFragment extends Fragment{

    private static final String ARG_POSITION = "position";
    View rootView;
    public static Typeface mDefaultTypeface;

    //List TRANSACTIONS = new ArrayList<Transactions>();

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();
    CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");


    List<CryptoWalletTransaction> lstTransactions = new ArrayList<CryptoWalletTransaction>();

    ListView listViewTransactions;
    SwipeRefreshLayout swipeRefreshLayout;
    TransactionArrayAdapter transactionArrayAdapter;

    private int pointerOffset = 0;
    private int cantTransactions = 10;


    //Type face font
    Typeface tf;


    ArrayList<Item> items = new ArrayList<Item>();

    TransactionArrayAdapterBasic transactionArrayAdapterBasic;

    Map<Date,Set<CryptoWalletTransaction>> mapTransactionPerDate;

    public static TransactionsFragment newInstance(int position) {
        TransactionsFragment f = new TransactionsFragment();
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

        cryptoWalletManager = platform.getCryptoWalletManager();
        errorManager = platform.getErrorManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("CantGetCryptoWalletException- " + e.getMessage());

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
        // Set the emptyView to the ListView


        /*TextView textViewEmptyListView = (TextView) rootView.findViewById(R.id.emptyElement);


        if(lstTransactions.isEmpty()){
            textViewEmptyListView.setTypeface(tf);
            listViewTransactions.setEmptyView(textViewEmptyListView);
        }*/





        // Create the adapter to convert the array to views

        //TODO: Esto es lo tagueado
        try {
            lstTransactions=cryptoWallet.getTransactions(cantTransactions,pointerOffset, wallet_id);
        } catch (CantGetTransactionsException e) {
            e.printStackTrace();
        }

        BalanceType balanceType = Platform.TYPE_BALANCE_TYPE_SELECTED;
        lstTransactions=showTransactionListSelected(lstTransactions,balanceType);

        //transactionArrayAdapter = new TransactionArrayAdapter(this.getActivity(),lstTransactions); //showTransactionListSelected(lstTransactions, Platform.TYPE_BALANCE_TYPE_SELECTED));
        //transactionArrayAdapterBasic = new TransactionArrayAdapterBasic(getActivity(),lstTransactions);

        //loadNewTransactions();

        // Assign adapter to ListView
        //listViewTransactions.setAdapter(transactionArrayAdapter);
        //swipeRefreshLayout.setColorSchemeColors(android.R.color);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTransactionsContent();
            }
        });


        //TODO: Fin de lo tagueado






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
        });*/

        //listViewTransactions=(ListView)findViewById(R.id.listView_main);

        items.add(new SectionItem("My Friends"));
        items.add(new EntryItem("Abhi Tripathi", "Champpu"));
        items.add(new EntryItem("Sandeep Pal", "Sandy kaliya"));
        items.add(new EntryItem("Amit Verma", "Budhiya"));
        items.add(new EntryItem("Awadhesh Diwaker ", "Dadda"));

        items.add(new SectionItem("Android Version"));
        items.add(new EntryItem("Jelly Bean", "android 4.2"));
        items.add(new EntryItem("IceCream Sandwich", "android 4.0"));
        items.add(new EntryItem("Honey Comb", "android 3.0"));
        items.add(new EntryItem("Ginger Bread ", "android 2.2"));

        items.add(new SectionItem("Android Phones"));
        items.add(new EntryItem("Samsung", "Gallexy"));
        items.add(new EntryItem("Sony Ericson", "Xperia"));
        items.add(new EntryItem("Nokiya", "Lumia"));



        //TODO:
        loadTransactionMap();



        EntryAdapter adapter = new EntryAdapter(getActivity(), items);
        listViewTransactions.setAdapter(adapter);
        //listViewTransactions.setOnItemClickListener(this);



        return rootView;
    }

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


    private List<CryptoWalletTransaction> showTransactionListSelected(List<CryptoWalletTransaction> lstTransactions, BalanceType balanceType) {
        List<CryptoWalletTransaction> lstToShow = new ArrayList<CryptoWalletTransaction>();
        for (CryptoWalletTransaction t : lstTransactions) {
            if (t.getBitcoinWalletTransaction().getBalanceType()==(balanceType)) {
                lstToShow.add(t);
            }
        }
        return lstToShow;
    }


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

    private void loadNewTransactions(){
        //List<Transactions> lstAux;
        if(lstTransactions.isEmpty())
            //Toast.makeText(getActivity(),"No transactions",Toast.LENGTH_SHORT).show();
            try {
                List<CryptoWalletTransaction> lst =cryptoWallet.getTransactions(cantTransactions, pointerOffset, wallet_id);
                for(CryptoWalletTransaction transaction: lst){
                    lstTransactions.add(0, transaction);
                }

            } catch (CantGetTransactionsException e)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage("Cant Get Transactions Exception- " + e.getMessage());
            }
            catch(Exception ex)
            {
                showMessage("Unexpected error get Transactions - " + ex.getMessage());
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
                showMessage("Cant Get Transactions Exception- " + e.getMessage());
            }
            catch(Exception ex)
            {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                showMessage("Unexpected error get Transactions - " + ex.getMessage());

            }
        }
        pointerOffset=lstTransactions.size();

        showTransactionListSelected(lstTransactions,Platform.TYPE_BALANCE_TYPE_SELECTED);

        //transactionArrayAdapter.notifyDataSetChanged();
        transactionArrayAdapterBasic.notifyDataSetChanged();
    }




    public class TransactionArrayAdapter extends ArrayAdapter<CryptoWalletTransaction> {

        public TransactionArrayAdapter(Context context, List<CryptoWalletTransaction> lstTrasactions) {
            super(context, 0, lstTrasactions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            //get inflater
            LayoutInflater inflater = (LayoutInflater)getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View listItemView = convertView;

            //if view exist
            if (null == convertView) {
                //Si no existe, entonces inflarlo con image_list_view.xml
                listItemView = inflater.inflate(
                        R.layout.wallets_bitcoin_fragment_transactions_list_items2,
                        parent,
                        false);
            }

            //Get TextViews
            TextView txtView_contact_name = (TextView)listItemView.findViewById(R.id.textView_contact_name);
            TextView txtView_amount = (TextView)listItemView.findViewById(R.id.textView_amount);
            //TextView txtView_notes = (TextView)listItemView.findViewById(R.id.textView_notes);
            TextView txtView_when = (TextView)listItemView.findViewById(R.id.textView_time);
            //TextView txtView_type = (TextView)listItemView.findViewById(R.id.textView_status);

            //Getting Transactions instance at the current position
            CryptoWalletTransaction item = getItem(position);



            txtView_contact_name.setText(item.getInvolvedActorName());
            if(Platform.TYPE_BALANCE_TYPE_SELECTED==BalanceType.AVAILABLE){
                txtView_amount.setText(Platform.formatBalanceString(item.getBitcoinWalletTransaction().getRunningAvailableBalance()));
            }else if (Platform.TYPE_BALANCE_TYPE_SELECTED==BalanceType.BOOK)
                txtView_amount.setText(Platform.formatBalanceString(item.getBitcoinWalletTransaction().getRunningBookBalance()));

            /*if(item.getBitcoinWalletTransaction().getMemo()!=null){
                txtView_notes.setText(item.getBitcoinWalletTransaction().getMemo());
            }
            */

            txtView_when.setText((DateFormat.format("hh:mm:ss", item.getBitcoinWalletTransaction().getTimestamp())));
            //txtView_type.setText(item.getBitcoinWalletTransaction().getTransactionType().toString());

            TextView textView_type = (TextView) listItemView.findViewById(R.id.textView_type);
            textView_type.setText("Received");

            return listItemView;

        }
    }

    public class TransactionArrayAdapterBasic extends ArrayAdapter<CryptoWalletTransaction> {

        List<CryptoWalletTransaction> lstTrasactions = new ArrayList<CryptoWalletTransaction>();

        public TransactionArrayAdapterBasic(Context context, List<CryptoWalletTransaction> lstTrasactions) {
            super(context, 0, lstTrasactions);
            this.lstTrasactions=lstTrasactions;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            //get inflater
            LayoutInflater inflater = (LayoutInflater)getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View listItemView = convertView;

            //if view exist
            if (null == convertView) {
                //Si no existe, entonces inflarlo con image_list_view.xml
                listItemView = inflater.inflate(
                        R.layout.wallets_bitcoin_fragment_transactions1,
                        parent,
                        false);
            }

            //Get TextViews
            TextView txtViewDay = (TextView)listItemView.findViewById(R.id.txtViewDay);


            //inflate ListView
            ListView listView = (ListView) listItemView.findViewById(R.id.transactionlist);
            transactionArrayAdapter = new TransactionArrayAdapter(getActivity(),lstTrasactions);

            return listItemView;

        }
    }


    private void showMessage(String text){
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
                Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }


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

            final Item i = items.get(position);
            if (i != null) {
                if(i.isSection()){
                    SectionItem si = (SectionItem)i;
                    v = vi.inflate(R.layout.list_item_section, null);

                    v.setOnClickListener(null);
                    v.setOnLongClickListener(null);
                    v.setLongClickable(false);

                    final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
                    sectionView.setText(si.getTitle());

                }else{
                    EntryItem ei = (EntryItem)i;
                    //v = vi.inflate(R.layout.list_item_enty, null);
                    //final TextView title = (TextView)v.findViewById(R.id.list_item_entry_title);
                    //final TextView subtitle = (TextView)v.findViewById(R.id.list_item_entry_summary);
                    v = vi.inflate(R.layout.wallets_bitcoin_fragment_transactions_list_items2,null);
                    final TextView textView_contact_name =(TextView)v.findViewById(R.id.textView_contact_name);
                    final TextView textView_type =(TextView)v.findViewById(R.id.textView_type);
                    final TextView textView_amount =(TextView)v.findViewById(R.id.textView_amount);
                    final TextView textView_time =(TextView)v.findViewById(R.id.textView_time);
                    final ImageView imageView_contact =(ImageView)v.findViewById(R.id.imageView_contact);


                    if (textView_contact_name != null)
                        textView_contact_name.setText(ei.title);
                    if(textView_amount != null)
                        textView_amount.setText(ei.subtitle);
                }
            }
            return v;
        }

    }

}
