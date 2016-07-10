package com.juaco.fermat_contact_fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.juaco.fermat_contact_fragment.adapters.WalletContact;
import com.juaco.fermat_contact_fragment.dialog.CreateContactFragmentDialog;
import com.juaco.fermat_contact_fragment.enums.HeaderTypes;
import com.juaco.fermat_contact_fragment.interfaces.CreateContactDialogCallback;
import com.juaco.fermat_contact_fragment.interfaces.FermatListViewFragment;
import com.juaco.fermat_contact_fragment.utils.BitcoinWalletConstants;
import com.juaco.fermat_contact_fragment.utils.MyComparator;
import com.juaco.fermat_contact_fragment.views.views_contacts_fragment.IndexBarView;
import com.juaco.fermat_contact_fragment.views.views_contacts_fragment.PinnedHeaderAdapter;
import com.juaco.fermat_contact_fragment.views.views_contacts_fragment.PinnedHeaderListView;
import com.juaco.fermat_contact_fragment.views.views_contacts_fragment.SubActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static android.widget.Toast.makeText;

/**
 * Created by Joaquin Carrasquero on 26/02/16.
 */
public abstract class FermatContactFragment <S extends ReferenceAppFermatSession,RE extends ResourceProviderManager> extends AbstractFermatFragment<S,RE>  implements FermatListViewFragment, DialogInterface.OnDismissListener, Thread.UncaughtExceptionHandler, CreateContactDialogCallback, View.OnClickListener, AbsListView.OnScrollListener {


    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_LOAD_IMAGE = 2;
    public static final int CONTEXT_MENU_CAMERA = 1;
    public static final int CONTEXT_MENU_GALLERY = 2;
    static final int ID_BTN_EXTRA_USER = 12;
    static final int ID_BTN_INTRA_USER = 23;
    private static final int CONTEXT_MENU_NO_PHOTO = 4;
    private static final int UNIQUE_FRAGMENT_GROUP_ID = 17;
    CreateContactFragmentDialog dialog;
    View rootView;
    //Type face font
    Typeface tf;
    /**
     * Wallet session
     */

    //TODO: Declare required SessionVariable Here.

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
    LinearLayout mEmptyView;
    Bundle mSavedInstanceState;
    String user_id = UUID.fromString("afd0647a-87de-4c56-9bc9-be736e0c5059").toString();
    /**
     * Resources
     */
    WalletResourcesProviderManager walletResourcesProviderManager;
    List<CryptoWalletWalletContact> walletContactRecords;
    /**
     * DealsWithWalletModuleCryptoWallet Interface member variables.
     */
    private ErrorManager errorManager;
    private Bitmap contactImageBitmap;
    private WalletContact walletContact;
    private FrameLayout contacts_container;
    private boolean connectionDialogIsShow = false;
    private boolean isScrolled = false;

    private CreateContactDialogCallback createContactDialogCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf");
        errorManager = appSession.getErrorManager();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            mSavedInstanceState = savedInstanceState;
            rootView = inflater.inflate(R.layout.main_conct_act, container, false);
            setupViews(rootView);
            setUpFAB();
            walletContactRecords = new ArrayList<>();
            mItems = new ArrayList<>();
            mListSectionPos = new ArrayList<Integer>();
            mListItems = new ArrayList<Object>();
            onRefresh();
            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable() {
                public void run() {
                    if (!walletContactRecords.isEmpty()) {
                        rootView.findViewById(R.id.fragment_container2).setVisibility(View.VISIBLE);
                    }
                }
            }, 300);
            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
        return container;
    }

    @SuppressWarnings("ResourceType")
    private void setUpFAB() {
        // in Activity Context
        FrameLayout frameLayout = new FrameLayout(getActivity());

        FrameLayout.LayoutParams lbs = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        frameLayout.setLayoutParams(lbs);

        ImageView icon = new ImageView(getActivity());
        frameLayout.addView(icon);
        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(getActivity())
                .setContentView(frameLayout)
                .setBackgroundDrawable(R.drawable.btn_contact_selector)
                .build();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());
        // repeat many times:
        ImageView itemIcon = new ImageView(getActivity());
        itemIcon.setImageResource(R.drawable.extra_user_button);

        SubActionButton button1 = itemBuilder.setContentView(itemIcon).setBackgroundDrawable(getResources().getDrawable(R.drawable.extra_user_button)).setText("External User").build();
        button1.setId(ID_BTN_EXTRA_USER);


        ImageView itemIcon2 = new ImageView(getActivity());
        itemIcon2.setImageResource(R.drawable.intra_user_button);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).setBackgroundDrawable(getResources().getDrawable(R.drawable.intra_user_button)).setText("Fermat User").build();
        button2.setId(ID_BTN_INTRA_USER);


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(button1)
                .addSubActionView(button2)
                .attachTo(actionButton)
                .build();

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(new Bundle());
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        menu.add(0, BitcoinWalletConstants.IC_ACTION_HELP_CONTACT, 0, "help").setIcon(R.drawable.help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public abstract List<CryptoWalletWalletContact> setRecordList();

    private void onRefresh() {


        // TODO: Method used to fullfill the view. Override with proper data.
            if (this.setRecordList()!= null)
            walletContactRecords = this.setRecordList();

        if (walletContactRecords.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
        refreshAdapter();
    }


    private void refreshAdapter() {
        if (mSavedInstanceState != null) {
            //TODO: no se si esto esta bien
            mListItems = (ArrayList) mSavedInstanceState.getParcelableArrayList("mListItems");
            mListSectionPos = mSavedInstanceState.getIntegerArrayList("mListSectionPos");

            if (mListItems != null && mListItems.size() > 0 && mListSectionPos != null && mListSectionPos.size() > 0) {
                setListAdaptor(null);
            }

            String constraint = mSavedInstanceState.getString("constraint");
            if (constraint != null && constraint.length() > 0) {
                mSearchView.setText(constraint);
                setIndexBarViewVisibility(constraint);
            }
        } else {
            mItems = walletContactRecords;
            isScrolled = false;
            new Populate().execute((ArrayList<CryptoWalletWalletContact>) mItems);
        }

    }


    private void setupViews(View rootView) {
        mSearchView = (EditText) rootView.findViewById(R.id.search_view);
        mClearSearchImageButton = (ImageButton) rootView.findViewById(R.id.clear_search_image_button);
        contacts_container = (FrameLayout) rootView.findViewById(R.id.contacts_container);
        mLoadingView = (ProgressBar) rootView.findViewById(R.id.loading_view);
        mListView = (PinnedHeaderListView) rootView.findViewById(R.id.list_view);
        mEmptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);


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
                this,
                errorManager);

        mListView.setAdapter(mPinnedHeaderAdapter);

        LayoutInflater inflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // set header view
        View pinnedHeaderView = inflater.inflate(R.layout.section_row_view, mListView, false);
        mListView.setPinnedHeaderView(pinnedHeaderView);

        // set index bar view
        IndexBarView indexBarView = (IndexBarView) inflater.inflate(R.layout.index_bar_view, mListView, false);
        indexBarView.setData(mListView, mListItems, mListSectionPos);
        mListView.setIndexBarView(indexBarView);
        // for configure pinned header view on onrefresh change
        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                try {

                    PinnedHeaderAdapter adapter = (PinnedHeaderAdapter) adapterView.getAdapter();
                } catch (Exception ex) {
                    errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, ex);
                    showMessage(getActivity(), "Unexpected error get Contact Detalil - " + ex.getMessage());
                }
            }
        });
        // TODO: Testing
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public  void showMessage(Context context,String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        // alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }


    public ListFilter instanceOfListFilter() {
        return new ListFilter();
    }


    @Override
    public void openContextImageSelector() {
        dialog.dismiss();
        walletContact = new WalletContact();
        walletContact.setName("");
        registerForContextMenu(mClearSearchImageButton);
        getActivity().openContextMenu(mClearSearchImageButton);
    }


    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
        this.walletResourcesProviderManager = walletResourcesProviderManager;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == ID_BTN_EXTRA_USER) {
            walletContact = new WalletContact();
            walletContact.setName("");
            lauchCreateContactDialog(false);
        } else if (id == ID_BTN_INTRA_USER) {
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        isScrolled = true;

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //TODO obtener la [a posicion del scroll antes de hacer populate
        //if (isScrolled) {
        //new Populate().execute((ArrayList<CryptoWalletWalletContact>) mItems);
        //TODO setear la posicion obtenida despues de hacer el populate
        //   mListView.scrollTo(0, mListSectionPos.size() - firstVisibleItem);
//        }
    }

    private void lauchCreateContactDialog(boolean withImage) {
        dialog = new CreateContactFragmentDialog(
                getActivity(),
                appSession,
                walletContact,
                user_id,
                ((withImage) ? contactImageBitmap : null),
                createContactDialogCallback);
        dialog.setOnDismissListener(this);
        dialog.show();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        onRefresh();
    }

    /* Create Contacto Dialog */

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception());
        Toast.makeText(getActivity(), "oooopps", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
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
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getGroupId() == UNIQUE_FRAGMENT_GROUP_ID) {
            switch (item.getItemId()) {
                case CONTEXT_MENU_CAMERA:
                    dispatchTakePictureIntent();
                    break;
                case CONTEXT_MENU_GALLERY:
                    loadImageFromGallery();
                    break;
                case CONTEXT_MENU_NO_PHOTO:
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
            if (!isScrolled)
                showLoading(mListView, mLoadingView, mEmptyView);
            super.onPreExecute();
        }


        @Override
        @SafeVarargs
        protected final Void doInBackground(ArrayList<CryptoWalletWalletContact>... params) {
            mListItems.clear();
            mListSectionPos.clear();

            ArrayList<CryptoWalletWalletContact> items = params[0];

            Map<Integer, CryptoWalletWalletContact> positions = new HashMap<>();

            if (items != null)
                if (items.size() > 0) {
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
                        for (int i = 0; i < items.size(); i++) {//) {
                            CryptoWalletWalletContact cryptoWalletWalletContact = items.get(i);
                            String currentSection = cryptoWalletWalletContact.getActorName().substring(0, 1);
                            if (currentSection.matches(numberRegex))
                                // is Digit
                                numbers.add(cryptoWalletWalletContact.getActorName());
                            else if (currentSection.matches(letterRegex)) {
                                // is Letter
                                letters.add(cryptoWalletWalletContact.getActorName());
                                positions.put(i, cryptoWalletWalletContact);
                            } else
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
                        for (int i = 0; i < letters.size(); i++) {//String currentItem : letters) {
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
            FermatAnimationsUtils.showEmpty(getActivity(), false, emptyView);
        }


        private void showContent(View contentView, View loadingView, View emptyView) {
            contentView.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
            FermatAnimationsUtils.showEmpty(getActivity(), false, emptyView);
        }


        private void showEmptyText(View contentView, View loadingView, View emptyView) {
            contentView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyView);
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
            isScrolled = false;
            new Populate(constrainStr).execute(filtered);
        }

    }

    public abstract Drawable getBackground();


}
