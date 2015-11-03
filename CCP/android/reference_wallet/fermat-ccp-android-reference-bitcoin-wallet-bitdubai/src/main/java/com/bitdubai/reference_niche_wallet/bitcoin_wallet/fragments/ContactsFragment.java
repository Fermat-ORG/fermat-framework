package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.FermatListViewFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment.IndexBarView;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment.PinnedHeaderAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment.PinnedHeaderListView;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.HeaderTypes;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.CreateContactFragmentDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory.ReferenceFragmentsEnumType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 19/07/15.
 */

public class ContactsFragment extends FermatWalletFragment implements FermatListViewFragment,DialogInterface.OnDismissListener, Thread.UncaughtExceptionHandler{

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_LOAD_IMAGE = 2;
    public static final int CONTEXT_MENU_CAMERA = 1;
    public static final int CONTEXT_MENU_GALLERY = 2;
    private static final int CONTEXT_MENU_NO_PHOTO = 4;

    private static final int UNIQUE_FRAGMENT_GROUP_ID = 17;


    /** DealsWithWalletModuleCryptoWallet Interface member variables. */
    private CryptoWalletManager cryptoWalletManager;
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;
    private Bitmap contactImageBitmap;
    private WalletContact walletContact;

    View rootView;
    String walletPublicKey = "reference_wallet";

    // TODO: preguntar de donde saco el user id
    String user_id = UUID.fromString("afd0647a-87de-4c56-9bc9-be736e0c5059").toString();

    //Type face font
    Typeface tf;

    /** Wallet session */
    ReferenceWalletSession referenceWalletSession;

    // unsorted list items
    List<CryptoWalletWalletContact> mItems;
    // array list to store section positions
    ArrayList<Integer> mListSectionPos;
    // array list to store listView data
    ArrayList<Object> mListItems;
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

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf");

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
            walletContactRecords = cryptoWallet.listWalletContacts(referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey());
        }
        catch (CantGetAllWalletContactsException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());
        }
        catch (CantGetActiveLoginIdentityException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());
        }
        catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "unknown error- " + e.getMessage());
        }

        mItems = new ArrayList<>();

        mItems = walletContactRecords;
//        for (CryptoWalletWalletContact walletContactRecords1 : walletContactRecords) {
//            mItems.add(walletContactRecords1.getActorName());
//        }

        // Array to ArrayList
        // mItems = new ArrayList<String>(Arrays.asList(ITEMS));
        mListSectionPos = new ArrayList<Integer>();
        mListItems = new ArrayList<Object>();

        // for handling configuration change
        if (savedInstanceState != null) {
            //TODO: no se si esto esta bien
            mListItems =(ArrayList) savedInstanceState.getParcelableArrayList("mListItems");
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
            new Populate().execute((ArrayList<CryptoWalletWalletContact>)mItems);
        }

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getActivity(), mListItems.get(position).toString(), Toast.LENGTH_SHORT).show();
                //System.out.println(adapterView.getItemAtPosition(position));
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

       final FloatingActionButton fabAddPerson = (FloatingActionButton) rootView.findViewById(R.id.fab_add_person);
        fabAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showAddContact("");
                walletContact = new WalletContact();
                walletContact.setName("");
                registerForContextMenu(fabAddPerson);
                getActivity().openContextMenu(fabAddPerson);
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

                    referenceWalletSession.setAccountName(String.valueOf(adapter.getItem(position)));
                    CryptoWalletWalletContact cryptoWalletWalletContact;

                    System.out.println(adapterView.getItemAtPosition(position).toString());

                    if(position == 1)
                        referenceWalletSession.setLastContactSelected((CryptoWalletWalletContact)adapterView.getItemAtPosition(position));
                    else
                        referenceWalletSession.setLastContactSelected((CryptoWalletWalletContact)adapterView.getItemAtPosition(position));


                    InstalledWallet installedWallet = walletSession.getWalletSessionType();

                    ((FermatScreenSwapper) getActivity()).changeWalletFragment(installedWallet.getWalletCategory().getCode(), installedWallet.getWalletType().getCode(), installedWallet.getWalletPublicKey(), ReferenceFragmentsEnumType.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS.getKey());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    showMessage(getActivity(), "Unexpected error get Contact Detalil - " + ex.getMessage());
                }
            }
        });
        // TODO: Testing
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              //  Toast.makeText(getActivity(), mListItems.get(i).toString(), Toast.LENGTH_SHORT).show();
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
    private class Populate extends AsyncTask<ArrayList<CryptoWalletWalletContact>, Void, Void> {
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
        protected final Void doInBackground(ArrayList<CryptoWalletWalletContact>... params) {
            mListItems.clear();
            mListSectionPos.clear();

            ArrayList<CryptoWalletWalletContact> items = params[0];

            Map<Integer,CryptoWalletWalletContact> positions = new HashMap<>();

            if (items.size() > 0) {

                // sort array
                //Collections.sort(items, new SortIgnoreCase());

                MyComparator icc = new MyComparator();

                Collections.sort(items, icc);

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
                    for (int i=0;i<items.size();i++){//) {

                        CryptoWalletWalletContact cryptoWalletWalletContact = items.get(i);

                        String currentSection = cryptoWalletWalletContact.getActorName().substring(0, 1);

                        if (currentSection.matches(numberRegex))
                            // is Digit
                            numbers.add(cryptoWalletWalletContact.getActorName());
                        else if (currentSection.matches(letterRegex)) {
                            // is Letter
                            letters.add(cryptoWalletWalletContact.getActorName());
                            positions.put(i, cryptoWalletWalletContact);
                        }
                        else
                            // Is other symbol
                            symbols.add(cryptoWalletWalletContact.getActorName());
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
                    for (int i = 0; i<letters.size();i++){//String currentItem : letters) {
                        String currentItem = letters.get(i);
                        String currentSection = currentItem.substring(0, 1).toUpperCase(Locale.getDefault());

                        if (!prevSection.equals(currentSection)) {
                            mListItems.add(currentSection);

                            // array list of section positions
                            mListSectionPos.add(mListItems.indexOf(currentSection));
                            prevSection = currentSection;
                        }

                        mListItems.add(positions.get(i));
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
                ArrayList<CryptoWalletWalletContact> filterItems = new ArrayList<>();

                synchronized (this) {
                    for (CryptoWalletWalletContact item : mItems) {
                        if (item.getActorName().toLowerCase(Locale.getDefault()).contains(constraintStr)) {
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
            ArrayList<CryptoWalletWalletContact> filtered = (ArrayList<CryptoWalletWalletContact>) results.values;
            final String constrainStr = constraint.toString();
            setIndexBarViewVisibility(constrainStr);

            // sort array and extract sections in background Thread
            new Populate(constrainStr).execute(filtered);
        }

    }

    /* Create Contacto Dialog */


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            contactImageBitmap = null;
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    contactImageBitmap = (Bitmap) extras.get("data");
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        contactImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                        //imageBitmap = Bitmap.createScaledBitmap(imageBitmap,take_picture_btn.getWidth(),take_picture_btn.getHeight(),true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            //take_picture_btn.setBackground(new RoundedDrawable(imageBitmap, take_picture_btn));
            //take_picture_btn.setImageDrawable(null);
            //contactPicture = imageBitmap;
            this.lauchCreateContactDialog(true);

        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select contact picture");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
        menu.add(UNIQUE_FRAGMENT_GROUP_ID, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
        menu.add(UNIQUE_FRAGMENT_GROUP_ID, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");
        menu.add(UNIQUE_FRAGMENT_GROUP_ID, CONTEXT_MENU_NO_PHOTO, Menu.NONE, "No photo");
//        if(contactImageBitmap!=null)
//            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //only this fragment's context menus have group ID
        if (item.getGroupId() == UNIQUE_FRAGMENT_GROUP_ID) {
            switch (item.getItemId()) {
                case CONTEXT_MENU_CAMERA:
                    dispatchTakePictureIntent();
                    break;
                case CONTEXT_MENU_GALLERY:
                    loadImageFromGallery();
                    break;
                case CONTEXT_MENU_NO_PHOTO:

//                takePictureButton.setBackground(getActivity().getResources().
//                        getDrawable(R.drawable.rounded_button_green_selector));
//                takePictureButton.setImageResource(R.drawable.ic_camera_green);
//                contactPicture = null;
                    this.lauchCreateContactDialog(false);
                    break;
            }
        }

        return super.onContextItemSelected(item);
    }

    private void loadImageFromGallery() {
        Intent intentLoad = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentLoad, REQUEST_LOAD_IMAGE);
    }

    private void lauchCreateContactDialog(boolean withImage){
        CreateContactFragmentDialog dialog = new CreateContactFragmentDialog(
                getActivity(),
                referenceWalletSession,
                walletContact,
                user_id,
                ((withImage) ? contactImageBitmap : null));
        dialog.setOnDismissListener(this);
        dialog.show();
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {


        Runnable runnable = new Runnable() {
            @Override
            public void run() {


            }
        };
        Thread thread = new Thread(runnable);

        thread.setUncaughtExceptionHandler(this);

        thread.start();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        referenceWalletSession.getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception());
        Toast.makeText(getActivity(),"oooopps",Toast.LENGTH_SHORT).show();
    }

}