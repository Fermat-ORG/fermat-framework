package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.FermatListViewFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment.IndexBarView;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment.PinnedHeaderAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment.PinnedHeaderListView;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.HeaderTypes;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.SortIgnoreCase;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory.ReferenceFragmentsEnumType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 19/07/15.
 */

public class ContactsFragment extends FermatWalletFragment implements FermatListViewFragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_PLATFORM = "platform";

    /** DealsWithWalletModuleCryptoWallet Interface member variables. */
    private CryptoWalletManager cryptoWalletManager;
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    View rootView;
    String walletPublicKey = "reference_wallet";


    //Type face font
    Typeface tf;

    /** Wallet session */
    ReferenceWalletSession referenceWalletSession;

    // unsorted list items
    ArrayList<String> mItems;
    // array list to store section positions
    ArrayList<Integer> mListSectionPos;
    // array list to store listView data
    ArrayList<String> mListItems;
    // custom list view with pinned header
    PinnedHeaderListView mListView;
    // custom adapter
    PinnedHeaderAdapter mPinnedHeaderAdapter;
    // search box
    EditText mSearchView;
    // button to clear search box
    ImageButton mClearSearchImageButton;
    // loading view
    ProgressBar mLoadingView;
    // empty view
    TextView mEmptyView;

    /**
     * Resources
     */
    WalletResourcesProviderManager walletResourcesProviderManager;

    List<CryptoWalletWalletContact> walletContactRecords;

    public static ContactsFragment newInstance() {

        ContactsFragment f = new ContactsFragment();

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        referenceWalletSession =(ReferenceWalletSession) walletSession;

        setRetainInstance(true);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog()
                .penaltyDeath().build());

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        errorManager = walletSession.getErrorManager();

        cryptoWalletManager = referenceWalletSession.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "Unexpected error get Contact list - " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_act, container, false);
        setupViews(rootView);


         walletContactRecords = new ArrayList<>();
        try {
            walletContactRecords = cryptoWallet.listWalletContacts(walletPublicKey);
        } catch (CantGetAllWalletContactsException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());
        }

        mItems = new ArrayList<>();

        for (CryptoWalletWalletContact walletContactRecords1 : walletContactRecords) {
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
                setListAdaptor(null);
            }

            String constraint = savedInstanceState.getString("constraint");
            if (constraint != null && constraint.length() > 0) {
                mSearchView.setText(constraint);
                setIndexBarViewVisibility(constraint);
            }
        } else {
            new Populate().execute(mItems);
        }

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), mListItems.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        mSearchView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                final int visibility = str.isEmpty() ? View.GONE : View.VISIBLE;
                mClearSearchImageButton.setVisibility(visibility);

                if (mPinnedHeaderAdapter != null) {
                    mPinnedHeaderAdapter.getFilter().filter(str);
                    mPinnedHeaderAdapter.notifyDataSetChanged();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        // Apply any required UI change now that the Fragment is visible.
    }


    private void setupViews(View rootView) {
        mSearchView = (EditText) rootView.findViewById(R.id.search_view);
        mClearSearchImageButton = (ImageButton) rootView.findViewById(R.id.clear_search_image_button);
        mLoadingView = (ProgressBar) rootView.findViewById(R.id.loading_view);
        mListView = (PinnedHeaderListView) rootView.findViewById(R.id.list_view);
        mEmptyView = (TextView) rootView.findViewById(R.id.empty_view);

        FloatingActionButton fabAddPerson = (FloatingActionButton) rootView.findViewById(R.id.fab_add_person);
        fabAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddContact("");
            }
        });

        mClearSearchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.getText().clear();
            }
        });
    }


    private void setIndexBarViewVisibility(String constraint) {
        // hide index bar for search results
        if (constraint != null && constraint.length() > 0) {
            mListView.setIndexBarVisibility(false);
        } else {
            mListView.setIndexBarVisibility(true);
        }
    }


    /**
     * @param name
     */
    private void showAddContact(String name) {
//        CreateContactFragment createContactFragment = new CreateContactFragment();
//        createContactFragment.walletSession = walletSession;
//        createContactFragment.setContactName(name);
//
//        FragmentTransaction FT = this.getFragmentManager().beginTransaction();
//        FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        FT.replace(R.id.fragment_container2, createContactFragment);
//        FT.addToBackStack(null);
//        FT.attach(createContactFragment);
//        FT.show(createContactFragment);
//        FT.commit();
        referenceWalletSession.setAccountName(name);
        InstalledWallet installedWallet = walletSession.getWalletSessionType();
        ((FermatScreenSwapper) getActivity()).changeWalletFragment(installedWallet.getWalletCategory().getCode(), installedWallet.getWalletType().getCode(), installedWallet.getWalletPublicKey(), ReferenceFragmentsEnumType.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CREATE_CONTACTS.getKey());
    }


    /**
     * Create and set the PinnedHeaderAdapter
     *
     * @param constrainStr the string of the search, can be null
     */
    private void setListAdaptor(String constrainStr) {
        // create instance of PinnedHeaderAdapter and set adapter to list view
        mPinnedHeaderAdapter = new PinnedHeaderAdapter(
                getActivity(),
                mListItems,
                mListSectionPos,
                constrainStr,
                this);

        mListView.setAdapter(mPinnedHeaderAdapter);

        LayoutInflater inflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        mListView.setOnScrollListener(mPinnedHeaderAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                try {

                    PinnedHeaderAdapter adapter = (PinnedHeaderAdapter) adapterView.getAdapter();
//                    String accountName = String.valueOf(adapter.getItem(position));
//                    Fragment fragment = ContactDetailFragment.newInstance(walletSession,walletResourcesProviderManager);
//                    FragmentManager fragmentManager = getActivity().getFragmentManager();
//                    fragmentManager
//                            .beginTransaction()
//                                    //TODO COMMENTED FOR ERROR WHEN TRYING TO GET CONTACT DETAIL
//                                    //.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//                            .add(R.id.fragment_container2, fragment)
//                            .attach(fragment)sta
//                            .show(fragment)
//                            .commit();
                    referenceWalletSession.setAccountName(String.valueOf(adapter.getItem(position)));
                    CryptoWalletWalletContact cryptoWalletWalletContact;


                    if(position == 1)
                        referenceWalletSession.setLastContactSelected(walletContactRecords.get(position-1));
                    else
                        referenceWalletSession.setLastContactSelected(walletContactRecords.get(position-2));


                    InstalledWallet installedWallet = walletSession.getWalletSessionType();

                    ((FermatScreenSwapper) getActivity()).changeWalletFragment(installedWallet.getWalletCategory().getCode(), installedWallet.getWalletType().getCode(), installedWallet.getWalletPublicKey(), ReferenceFragmentsEnumType.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS.getKey());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // TODO: Testing
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), mListItems.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public ListFilter instanceOfListFilter() {
        return new ListFilter();
    }


    public void setWalletSession(ReferenceWalletSession walletSession) {
        this.walletSession = walletSession;
    }

    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
        this.walletResourcesProviderManager = walletResourcesProviderManager;
    }


    /**
     * Sort array and extract sections f background Thread here we use AsyncTask
     */
    private class Populate extends AsyncTask<ArrayList<String>, Void, Void> {
        private final int TOTAL_CONTACTS_SECTION_POSITION = 0;
        private String constrainStr;


        public Populate() {
            constrainStr = null;
        }

        public Populate(String constrainStr) {
            this();
            if (constrainStr != null) {
                if (!constrainStr.isEmpty()) {
                    this.constrainStr = constrainStr;
                }
            }

        }

        @Override
        protected void onPreExecute() {
            // show loading indicator
            showLoading(mListView, mLoadingView, mEmptyView);
            super.onPreExecute();
        }


        @Override
        @SafeVarargs
        protected final Void doInBackground(ArrayList<String>... params) {
            mListItems.clear();
            mListSectionPos.clear();

            ArrayList<String> items = params[0];

            if (items.size() > 0) {

                // sort array
                Collections.sort(items, new SortIgnoreCase());

                final boolean searchMode = constrainStr != null;
                if (searchMode) {
                    // add this section to the list of items
                    mListItems.add("All Contacts: " + items.size() + " found ");

                    // add the position of this section to the list of section positions
                    mListSectionPos.add(TOTAL_CONTACTS_SECTION_POSITION);

                    // add the items to the list of items
                    mListItems.addAll(items);

                } else {
                    // hashMap to group the items by number (#), symbol (@), and letter (a)
                    HashMap<HeaderTypes, ArrayList<String>> hashMap = new HashMap<>();
                    hashMap.put(HeaderTypes.NUMBER, new ArrayList<String>());
                    hashMap.put(HeaderTypes.SYMBOL, new ArrayList<String>());
                    hashMap.put(HeaderTypes.LETTER, new ArrayList<String>());

                    // list of symbols, numbers and letter items contained in the hashMap
                    final ArrayList<String> symbols = hashMap.get(HeaderTypes.SYMBOL);
                    final ArrayList<String> numbers = hashMap.get(HeaderTypes.NUMBER);
                    final ArrayList<String> letters = hashMap.get(HeaderTypes.LETTER);

                    // Regex for Number and Letters
                    final String numberRegex = HeaderTypes.NUMBER.getRegex();
                    final String letterRegex = HeaderTypes.LETTER.getRegex();

                    // for each item in the list look if is number, symbol o letter and put it in the corresponding list
                    for (String currentItem : items) {
                        String currentSection = currentItem.substring(0, 1);

                        if (currentSection.matches(numberRegex))
                            // is Digit
                            numbers.add(currentItem);
                        else if (currentSection.matches(letterRegex))
                            // is Letter
                            letters.add(currentItem);
                        else
                            // Is other symbol
                            symbols.add(currentItem);
                    }

                    final String symbolCode = HeaderTypes.SYMBOL.getCode();
                    if (!symbols.isEmpty()) {
                        // add the section in the list of items
                        mListItems.add(symbolCode);
                        // add the section position in the list section positions
                        mListSectionPos.add(mListItems.indexOf(symbolCode));
                        // add all the items in this group
                        mListItems.addAll(symbols);
                    }

                    final String numberCode = HeaderTypes.NUMBER.getCode();
                    if (!numbers.isEmpty()) {
                        mListItems.add(numberCode);
                        mListSectionPos.add(mListItems.indexOf(numberCode));
                        mListItems.addAll(numbers);
                    }

                    // add the letters items in the list and his corresponding sections based on its first letter
                    String prevSection = "";
                    for (String currentItem : letters) {
                        String currentSection = currentItem.substring(0, 1).toUpperCase(Locale.getDefault());

                        if (!prevSection.equals(currentSection)) {
                            mListItems.add(currentSection);

                            // array list of section positions
                            mListSectionPos.add(mListItems.indexOf(currentSection));
                            prevSection = currentSection;
                        }

                        mListItems.add(currentItem);
                    }
                }

            }
            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            if (!isCancelled()) {
                if (mListItems.isEmpty()) {
                    showEmptyText(mListView, mLoadingView, mEmptyView);
                } else {
                    setListAdaptor(constrainStr);
                    showContent(mListView, mLoadingView, mEmptyView);
                }
            }
            super.onPostExecute(result);
        }


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
    }


    /**
     * Filter Class for the Items in PinnedHeaderAdapter
     */
    public class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // NOTE: this function is *always* called from a background thread, and not the UI thread.
            FilterResults result = new FilterResults();

            if (constraint != null && constraint.toString().length() > 0) {
                String constraintStr = constraint.toString().toLowerCase(Locale.getDefault());
                ArrayList<String> filterItems = new ArrayList<>();

                synchronized (this) {
                    for (String item : mItems) {
                        if (item.toLowerCase(Locale.getDefault()).contains(constraintStr)) {
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


        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<String> filtered = (ArrayList<String>) results.values;
            final String constrainStr = constraint.toString();
            setIndexBarViewVisibility(constrainStr);

            // sort array and extract sections in background Thread
            new Populate(constrainStr).execute(filtered);
        }

    }

}