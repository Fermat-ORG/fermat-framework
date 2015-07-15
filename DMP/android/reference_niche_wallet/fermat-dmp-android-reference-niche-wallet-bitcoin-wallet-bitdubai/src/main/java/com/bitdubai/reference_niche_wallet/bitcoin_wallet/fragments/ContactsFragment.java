package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.WalletContactListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by natalia on 19/06/15.
 */
public class ContactsFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_PLATFORM = "platform";
    View rootView;
    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    EditText inputSearch;
    WalletContactListAdapter adapter;
    TextView textViewEmptyListView;

    List<WalletContact> contacts;

    //Type face font
    Typeface tf ;


    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private CryptoWalletManager cryptoWalletManager;
    private Platform platform;
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    LinearLayout linearLayout;
    ListView listViewContacs;

    ImageView imageViewAddContact;


    // an array of countries to display in the list
    static final String[] ITEMS = new String[] { "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
            "Eritrea", "Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland", "Afghanistan",
            "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica",
            "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
            "Bangladesh", "Barbados", "Belarus", "Belgium", "Monaco", "Mongolia", "Montserrat", "Morocco",
            "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
            "New Caledonia", "New Zealand", "Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras",
            "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy",
            "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia",
            "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Nicaragua", "Niger",
            "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Marianas", "Norway", "Oman", "Pakistan",
            "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",
            "Portugal", "Puerto Rico", "Qatar", "French Southern Territories", "Gabon", "Georgia", "Germany", "Ghana",
            "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea",
            "Guinea-Bissau", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova",
            "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
            "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal", "Seychelles",
            "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa",
            "South Georgia and the South Sandwich Islands", "South Korea", "Spain", "Sri Lanka", "Sudan", "Suriname",
            "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan",
            "Tanzania", "Thailand", "The Bahamas", "The Gambia", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago",
            "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
            "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands",
            "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Virgin Islands",
            "Wallis and Futuna", "Western Sahara", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso",
            "Burundi", "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
            "Central African Republic", "Chad", "Chile", "China", "Reunion", "Romania", "Russia", "Rwanda",
            "Sqo Tome and Principe", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia",
            "Saint Pierre and Miquelon", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Christmas Island",
            "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia", "Cuba",
            "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica",
            "Dominican Republic", "Former Yugoslav Republic of Macedonia", "France", "French Guiana",
            "French Polynesia", "Macau", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
            "Marshall Islands", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe" };

    // unsorted list items
    ArrayList<String> mItems;

    // array list to store section positions
    ArrayList<Integer> mListSectionPos;

    // array list to store listView data
    ArrayList<String> mListItems;

    // custom list view with pinned header
    PinnedHeaderListView mListView;

    // custom adapter
    PinnedHeaderAdapter mAdaptor;

    // search box
    EditText mSearchView;

    // loading view
    ProgressBar mLoadingView;

    // empty view
    TextView mEmptyView;

    //image view button to add contact
    ImageView imageView_add_contact;
    Button btn_add_contact;


    private void setupViews(View rootView) {
        mSearchView = (EditText) rootView.findViewById(R.id.search_view);
        mLoadingView = (ProgressBar) rootView.findViewById(R.id.loading_view);
        mListView = (PinnedHeaderListView) rootView.findViewById(R.id.list_view);
        mEmptyView = (TextView) rootView.findViewById(R.id.empty_view);
        imageView_add_contact = (ImageView)rootView.findViewById(R.id.imageView_add_contact);
        imageView_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddContact("MAti");
            }
        });
        //imageView_add_contact.
    }


    public static ContactsFragment newInstance(int position) {
        ContactsFragment f = new ContactsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putSerializable(ARG_PLATFORM, new Platform());
        f.setArguments(b);
        return f;
    }

    public static ContactsFragment newInstance(final int position, final Platform platform) {
        ContactsFragment f = new ContactsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putSerializable(ARG_PLATFORM, platform);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog()
                .penaltyDeath().build());

        tf=Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        platform = new Platform();
        errorManager = platform.getErrorManager();

        cryptoWalletManager = platform.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("Unexpected error get Contact list - " + e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_act, container, false);
        setupViews(rootView);


        //get contacts list
        List<WalletContactRecord> walletContactRecords = new ArrayList<>();
        try {
            walletContactRecords = cryptoWallet.listWalletContacts(wallet_id);
        } catch (CantGetAllWalletContactsException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("CantGetAllWalletContactsException- " + e.getMessage());
        }

        mItems = new ArrayList<String>();

        for(WalletContactRecord walletContactRecords1:walletContactRecords){
            mItems.add(walletContactRecords1.getActorName());
        }

        // Array to ArrayList
       // mItems = new ArrayList<String>(Arrays.asList(ITEMS));
        mListSectionPos = new ArrayList<Integer>();
        mListItems = new ArrayList<String>();

        // for handling configuration change
        if (savedInstanceState != null) {
            mListItems = savedInstanceState.getStringArrayList("mListItems");
            mListSectionPos = savedInstanceState.getIntegerArrayList("mListSectionPos");

            if (mListItems != null && mListItems.size() > 0 && mListSectionPos != null && mListSectionPos.size() > 0) {
                setListAdaptor();
            }

            String constraint = savedInstanceState.getString("constraint");
            if (constraint != null && constraint.length() > 0) {
                mSearchView.setText(constraint);
                setIndexBarViewVisibility(constraint);
            }
        } else {
            new Poplulate().execute(mItems);
        }

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(),mListItems.get(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }
    @Override
    public void onStart(){
        super.onStart();
        mSearchView.addTextChangedListener(filterTextWatcher);
        // Apply any required UI change now that the Fragment is visible.
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (mAdaptor != null && str != null)
                mAdaptor.getFilter().filter(str);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    };

    private void setListAdaptor() {
        // create instance of PinnedHeaderAdapter and set adapter to list view
        mAdaptor = new PinnedHeaderAdapter(getActivity(), mListItems, mListSectionPos,this);
        mListView.setAdapter(mAdaptor);

        LayoutInflater inflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

        // set header view
        View pinnedHeaderView = inflater.inflate(R.layout.section_row_view, mListView, false);
        mListView.setPinnedHeaderView(pinnedHeaderView);

        // set index bar view
        IndexBarView indexBarView = (IndexBarView) inflater.inflate(R.layout.index_bar_view, mListView, false);
        indexBarView.setData(mListView, mListItems, mListSectionPos);
        mListView.setIndexBarView(indexBarView);

        // set preview text view
        View previewTextView = inflater.inflate(R.layout.preview_view, mListView, false);
        mListView.setPreviewView(previewTextView);

        // for configure pinned header view on scroll change
        mListView.setOnScrollListener(mAdaptor);

        // TODO: Testing
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),mListItems.get(i),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class ListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // NOTE: this function is *always* called from a background thread,
            // and
            // not the UI thread.
            String constraintStr = constraint.toString().toLowerCase(Locale.getDefault());
            FilterResults result = new FilterResults();

            if (constraint != null && constraint.toString().length() > 0) {
                ArrayList<String> filterItems = new ArrayList<String>();

                synchronized (this) {
                    for (String item : mItems) {
                        if (item.toLowerCase(Locale.getDefault()).startsWith(constraintStr)) {
                            filterItems.add(item);
                        }
                    }
                    result.count = filterItems.size();
                    result.values = filterItems;
                }
            } else {
                synchronized (this) {
                    result.count = mItems.size();
                    result.values = mItems;
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<String> filtered = (ArrayList<String>) results.values;
            setIndexBarViewVisibility(constraint.toString());
            // sort array and extract sections in background Thread
            new Poplulate().execute(filtered);
        }

    }

    private void setIndexBarViewVisibility(String constraint) {
        // hide index bar for search results
        if (constraint != null && constraint.length() > 0) {
            mListView.setIndexBarVisibility(false);
        } else {
            mListView.setIndexBarVisibility(true);
        }
    }

    // sort array and extract sections in background Thread here we use
    // AsyncTask
    private class Poplulate extends AsyncTask<ArrayList<String>, Void, Void> {

        private void showLoading(View contentView, View loadingView, View emptyView) {
            contentView.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        private void showContent(View contentView, View loadingView, View emptyView) {
            contentView.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
        }

        private void showEmptyText(View contentView, View loadingView, View emptyView) {
            contentView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPreExecute() {
            // show loading indicator
            showLoading(mListView, mLoadingView, mEmptyView);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(ArrayList<String>... params) {
            mListItems.clear();
            mListSectionPos.clear();
            ArrayList<String> items = params[0];
            if (mItems.size() > 0) {

                // NOT forget to sort array
                Collections.sort(items, new SortIgnoreCase());

                String prev_section = "";
                for (String current_item : items) {
                    String current_section = current_item.substring(0, 1).toUpperCase(Locale.getDefault());

                    if (!prev_section.equals(current_section)) {
                        mListItems.add(current_section);
                        mListItems.add(current_item);
                        // array list of section positions
                        mListSectionPos.add(mListItems.indexOf(current_section));
                        prev_section = current_section;
                    } else {
                        mListItems.add(current_item);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (!isCancelled()) {
                if (mListItems.size() <= 0) {
                    showEmptyText(mListView, mLoadingView, mEmptyView);
                } else {
                    setListAdaptor();
                    showContent(mListView, mLoadingView, mEmptyView);
                }
            }
            super.onPostExecute(result);
        }
    }

    public class SortIgnoreCase implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }
    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mListItems != null && mListItems.size() > 0) {
            outState.putStringArrayList("mListItems", mListItems);
        }
        if (mListSectionPos != null && mListSectionPos.size() > 0) {
            outState.putIntegerArrayList("mListSectionPos", mListSectionPos);
        }
        String searchText = mSearchView.getText().toString();
        if (searchText != null && searchText.length() > 0) {
            outState.putString("constraint", searchText);
        }
        super.onSaveInstanceState(outState);
    }
    */

    private void showAddContact(String name) {
        CreateContactFragment createContactFragment = new CreateContactFragment();

        createContactFragment.setContactName(name);

        FragmentTransaction FT = getFragmentManager().beginTransaction();

        //FT.replace(R.id.contacts_container, createContactFragment);
        //FT.a

        FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //linearLayout.setVisibility(View.GONE);
        //listViewContacs.setVisibility(View.GONE);
        //textViewEmptyListView.setVisibility(View.GONE);
        //createContactFragment.detach(createContactFragment);
        //FT.attach(createContactFragment);
        FT.replace(R.id.fragment_container2,createContactFragment);
        FT.addToBackStack(null);
        FT.attach(createContactFragment);
        FT.show(createContactFragment);
        FT.commit();
    }


    //show alert
    private void showMessage(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }
}