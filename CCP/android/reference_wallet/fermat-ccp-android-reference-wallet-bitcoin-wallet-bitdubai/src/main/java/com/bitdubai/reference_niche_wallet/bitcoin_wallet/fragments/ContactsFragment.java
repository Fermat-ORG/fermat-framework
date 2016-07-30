package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
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

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_android_api.utils.FermatScreenCalculator;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.BitcoinWalletConstants;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CreateContactDialogCallback;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.FermatListViewFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.SubActionButton;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment.IndexBarView;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment.PinnedHeaderAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment.PinnedHeaderListView;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.HeaderTypes;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.ConnectionWithCommunityDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.ContactsTutorialPart1V2;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.CreateContactFragmentDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 19/07/15.
 */

public class ContactsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoWallet>,ResourceProviderManager> implements FermatListViewFragment,DialogInterface.OnDismissListener, Thread.UncaughtExceptionHandler, CreateContactDialogCallback, View.OnClickListener, AbsListView.OnScrollListener {


    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_LOAD_IMAGE = 2;
    public static final int CONTEXT_MENU_CAMERA = 1;
    public static final int CONTEXT_MENU_GALLERY = 2;
    static final int ID_BTN_EXTRA_USER = 12;
    static final int ID_BTN_INTRA_USER = 23;
    private static final int CONTEXT_MENU_NO_PHOTO = 4;
    private static final int UNIQUE_FRAGMENT_GROUP_ID = 17;

    com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton actionButton;
    FloatingActionMenu actionMenu;

    CreateContactFragmentDialog dialog;
    View rootView;
    //Type face font
    Typeface tf;
    /**
     * Wallet session
     */

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
    List<CryptoWalletWalletContact> walletContactRecords = new ArrayList<>();
    /**
     * DealsWithWalletModuleCryptoWallet Interface member variables.
     */
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;
    private Bitmap contactImageBitmap;
    private WalletContact walletContact;
    private FrameLayout contacts_container;
    private boolean connectionDialogIsShow = false;
    private boolean isScrolled = false;
    private Toolbar toolbar;

    FermatWorker fermatWorker;

    private ExecutorService _executor;

    public static ContactsFragment newInstance() {

        ContactsFragment f = new ContactsFragment();

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf");
        errorManager = appSession.getErrorManager();

        _executor = Executors.newFixedThreadPool(2);
        try {
            cryptoWallet = appSession.getModuleManager();
            toolbar = getToolbar();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setElevation(10);
            }


        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "Unexpected error get Contact list - " + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            mSavedInstanceState = savedInstanceState;
            rootView = inflater.inflate(R.layout.main_act, container, false);
            setupViews(rootView);
            setUpFAB();
            walletContactRecords = new ArrayList<>();
            mItems = new ArrayList<>();
            mListSectionPos = new ArrayList<Integer>();
            mListItems = new ArrayList<Object>();

            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable() {
                public void run() {

                    onRefresh();
                    if (walletContactRecords.isEmpty()) {
                        rootView.findViewById(R.id.fragment_container2).setVisibility(View.GONE);
                        try {
                            boolean isHelpEnabled = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isContactsHelpEnabled();

                            if (isHelpEnabled)
                                setUpTutorial(true);
                        } catch (CantGetSettingsException e) {
                            e.printStackTrace();
                        } catch (SettingsNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        rootView.findViewById(R.id.fragment_container2).setVisibility(View.VISIBLE);
                    }
                }
            }, 300);
            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
        return container;
    }

    private SubActionButton button1;
    private SubActionButton button2;
    @SuppressWarnings("ResourceType")
    private void setUpFAB() {
        // in Activity Context
        FrameLayout frameLayout = new FrameLayout(getActivity());
        FrameLayout.LayoutParams lbs = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        frameLayout.setLayoutParams(lbs);

        int padding = FermatScreenCalculator.getPx(getActivity(), 30);
        int width = FermatScreenCalculator.getPx(getActivity(), 56);
        //noinspection SuspiciousNameCombination
        FloatingActionButton.LayoutParams actionButtonParams = new FloatingActionButton.LayoutParams(width, width);
        actionButtonParams.setMargins(0,0,padding,padding);

        ImageView icon = new ImageView(getActivity());
        frameLayout.addView(icon);
        actionButton = new com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.Builder(getActivity())
                .setContentView(frameLayout)
                .setLayoutParams(actionButtonParams)
                .setBackgroundDrawable(R.drawable.btn_contact_selector)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());

        padding = FermatScreenCalculator.getPx(getActivity(), 50);
        button1 = itemBuilder
                .setSize(65)
                .setPadding(0,0,padding,0)
                .setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.extra_user_button))
                .setText("External User")
                .setTextColor(Color.WHITE)
                .setTextBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_contacts))
                .build();
        button1.setId(ID_BTN_EXTRA_USER);

        padding = FermatScreenCalculator.getPx(getActivity(), 84);
        button2 = itemBuilder
                .setSize(65)
                .setPadding(0,0,padding,0)
                .setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.intra_user_button))
                .setText("Fermat User")
                .setTextColor(Color.WHITE)
                .setTextBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_contacts))
                .build();
        button2.setId(ID_BTN_INTRA_USER);

        actionMenu = new FloatingActionMenu.Builder(getActivity())
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
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Override
    public void onDrawerOpen() {
        actionButton.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
    }

    @Override
    public void onDrawerClose() {
        FermatAnimationsUtils.showEmpty(getActivity(),true,actionMenu.getActivityContentView());
        actionButton.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mSearchView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String str = s.toString();

                //final int visibility = str.isEmpty() ? View.GONE : View.VISIBLE;
                // mClearSearchImageButton.setVisibility(visibility);

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

        menu.add(0, BitcoinWalletConstants.IC_ACTION_HELP_CONTACT, 0, "help").setIcon(R.drawable.bit_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if (id == 2) {
                setUpTutorial(true);
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onRefresh() {

      fermatWorker = new FermatWorker(getActivity()) {
            @Override
            protected Object doInBackground()  {

                try {
                    walletContactRecords = cryptoWallet.listWalletContacts(appSession.getAppPublicKey(), cryptoWallet.getSelectedActorIdentity().getPublicKey());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return walletContactRecords;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {

                    if (walletContactRecords.isEmpty()) {
                        mEmptyView.setVisibility(View.VISIBLE);
                        mListView.setVisibility(View.GONE);
                    } else {
                        mListView.setVisibility(View.VISIBLE);
                        mEmptyView.setVisibility(View.GONE);
                        rootView.findViewById(R.id.fragment_container2).setVisibility(View.VISIBLE);
                    }
                    refreshAdapter();

                }
                else {
                    makeText(getActivity(), "Cant't Get Contact List.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {

                makeText(getActivity(), "Cant't Get Contact List. " + ex.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        fermatWorker.execute();



    }

    private void setUpTutorial(boolean checkButton) throws CantGetSettingsException, SettingsNotFoundException {
       // if (isHelpEnabled) {
            ContactsTutorialPart1V2 contactsTutorialPart1 = new ContactsTutorialPart1V2(getActivity(), appSession, null, checkButton);
            contactsTutorialPart1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Object b = appSession.getData(SessionConstant.CREATE_EXTRA_USER);
                    if (b != null) {
                        if ((Boolean) b) {
                            lauchCreateContactDialog(false);
                            appSession.removeData(SessionConstant.CREATE_EXTRA_USER);
                        }
                    }

                }
            });
            contactsTutorialPart1.show();
      //  }
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

        //mClearSearchImageButton = (ImageButton) rootView.findViewById(R.id.clear_search_image_button);

        contacts_container = (FrameLayout) rootView.findViewById(R.id.contacts_container);
        mLoadingView = (ProgressBar) rootView.findViewById(R.id.loading_view);
        mListView = (PinnedHeaderListView) rootView.findViewById(R.id.list_view);
        mEmptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);


       /* mClearSearchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.getText().clear();
            }
        });*/

        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionWithCommunityDialog connectionWithCommunityDialog = new ConnectionWithCommunityDialog(
                        getActivity(),
                        appSession,
                        null);

                connectionWithCommunityDialog.show();
                connectionWithCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        connectionDialogIsShow = false;
                        onRefresh();
                    }
                });
                connectionDialogIsShow = true;
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
/*
        // set preview text view
        View previewTextView = inflater.inflate(R.layout.preview_view, mListView, false);
        mListView.setPreviewView(previewTextView);*/


        // for configure pinned header view on onrefresh change
        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                try {

                    PinnedHeaderAdapter adapter = (PinnedHeaderAdapter) adapterView.getAdapter();

                    appSession.setData("AccountName",String.valueOf(adapter.getItem(position)));

                    if (position == 1)
                        appSession.setData("LastContactSelected", adapterView.getItemAtPosition(position));
                    else
                        appSession.setData("LastContactSelected",adapterView.getItemAtPosition(position));


                    Boolean isFromActionBarSend = (Boolean) appSession.getData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS);

                    if (isFromActionBarSend != null) {
                        if (isFromActionBarSend) {
                            appSession.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS, Boolean.FALSE);
                            changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY, appSession.getAppPublicKey());

                        } else {
                            changeActivity(Activities.CCP_BITCOIN_WALLET_CONTACT_DETAIL_ACTIVITY, appSession.getAppPublicKey());
                        }
                    } else {
                        changeActivity(Activities.CCP_BITCOIN_WALLET_CONTACT_DETAIL_ACTIVITY, appSession.getAppPublicKey());
                    }


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


    public ListFilter instanceOfListFilter() {
        return new ListFilter();
    }


    @Override
    public void openContextImageSelector() {
        dialog.dismiss();
        walletContact = new WalletContact();
        walletContact.setName("");
       // registerForContextMenu(mClearSearchImageButton);
        //getActivity().openContextMenu(mClearSearchImageButton);
    }

    public void setWalletSession(ReferenceAppFermatSession appSession) {
        this.appSession = appSession;
    }

    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
        this.walletResourcesProviderManager = walletResourcesProviderManager;
    }


    @Override
    public void onStop() {

        if(fermatWorker != null)
            fermatWorker.shutdownNow();


        super.onStop();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == ID_BTN_EXTRA_USER) {
            walletContact = new WalletContact();
            walletContact.setName("");
            lauchCreateContactDialog(false);
        } else if (id == ID_BTN_INTRA_USER) {
            changeActivity(Activities.CCP_BITCOIN_WALLET_ADD_CONNECTION_ACTIVITY, appSession.getAppPublicKey());
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
                this);
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
        appSession.getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception());
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
            try
            {
                FermatAnimationsUtils.showEmpty(getActivity(), true, emptyView);

            } catch (Exception e) {
                e.printStackTrace();
            }

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
}