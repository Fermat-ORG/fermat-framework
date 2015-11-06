package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.custom_anim.Fx;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by mati on 2015.11.05..
 */
public class SendFormFragment extends FermatWalletFragment{

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_LOAD_IMAGE = 2;
    public static final int CONTEXT_MENU_CAMERA = 1;
    public static final int CONTEXT_MENU_GALLERY = 2;
    private static final int CONTEXT_MENU_NO_PHOTO = 4;
    private static final int UNIQUE_FRAGMENT_GROUP_ID = 15;


    private ReferenceWalletSession referenceWalletSession;
    private CryptoWallet cryptoWallet;
    private WalletContact walletContact;
    private IntraUserModuleManager intraUserModuleManager;

    /**
     * UI
     */
    private View rootView;
    private LinearLayout linear_layout_send_form;
    private AutoCompleteTextView autocompleteContacts;
    private EditText editTextAddress;
    private EditText editTextAmount;

    /**
     * Adapter
     */
    private WalletContactListAdapter contactsAdapter;
    private TextView txt_notes;
    private LinearLayout linear_address;
    private boolean activeAddress= true;;
    private Bitmap contactImageBitmap;


    public static SendFormFragment newInstance() {
        return new SendFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        referenceWalletSession = (ReferenceWalletSession) walletSession;

        intraUserModuleManager = referenceWalletSession.getIntraUserModuleManager();
        try {

            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();

            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        } catch (CantGetCryptoWalletException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {

            rootView = inflater.inflate(R.layout.send_form_base, container, false);


            linear_layout_send_form = (LinearLayout) rootView.findViewById(R.id.send_form);

//            ((com.melnykov.fab.FloatingActionButton) rootView.findViewById(R.id.fab_action)).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    boolean isShow = linear_layout_send_form.isShown();
//                    //linear_layout_send_form.setVisibility(isShow?View.GONE:View.VISIBLE);
//                    if (isShow) {
//                        Fx.slide_up(getActivity(), linear_layout_send_form);
//                        linear_layout_send_form.setVisibility(View.GONE);
//                        empty.setVisibility(View.VISIBLE);
//                    } else {
//                        linear_layout_send_form.setVisibility(View.VISIBLE);
//                        Fx.slide_down(getActivity(), linear_layout_send_form);
//                        empty.setVisibility(View.GONE);
//                    }
//
//                }
//            });



            autocompleteContacts = (AutoCompleteTextView) rootView.findViewById(R.id.contact_name);


            contactsAdapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());

            autocompleteContacts.setAdapter(contactsAdapter);
            //autocompleteContacts.setTypeface(tf);
            autocompleteContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    walletContact = (WalletContact) arg0.getItemAtPosition(position);
                    editTextAddress.setText(walletContact.address);
                    linear_address.setVisibility(View.GONE);

                    //add connection like a wallet contact
                    try
                    {
                        if(walletContact.isConnection)
                            cryptoWallet.convertConnectionToContact(
                                    walletContact.name,
                                    Actors.INTRA_USER,
                                    walletContact.actorPublicKey,
                                    walletContact.profileImage,
                                    Actors.INTRA_USER,
                                    intraUserModuleManager.getActiveIntraUserIdentity().getPublicKey(),
                                    "reference_wallet"/*referenceWalletSession.getWalletSessionType().getWalletPublicKey()*/ ,
                                    CryptoCurrency.BITCOIN,
                                    BlockchainNetworkType.TEST);

                    }
                    catch (CantGetActiveLoginIdentityException e) {
                        referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        showMessage(getActivity(), "CantGetActiveLoginIdentityException- " + e.getMessage());
                    }
                    catch(CantCreateWalletContactException e)
                    {
                        referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        showMessage(getActivity(), "CantCreateWalletContactException- " + e.getMessage());

                    }
                    catch(ContactNameAlreadyExistsException e)
                    {
                        referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        showMessage(getActivity(), "ContactNameAlreadyExistsException- " + e.getMessage());

                    }
                }
            });


            autocompleteContacts.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    linear_address.setVisibility(activeAddress ? View.VISIBLE : View.GONE);
                    // if (!editTextAddress.getText().equals("")) linear_address.setVisibility(View.VISIBLE);
                }
            });

            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_new_contact);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    walletContact = new WalletContact();
                    walletContact.setName(autocompleteContacts.getText().toString());
                    registerForContextMenu(autocompleteContacts);
                    getActivity().openContextMenu(autocompleteContacts);

                }
            });




            /**
             *  Address line
             */
            linear_address = (LinearLayout) rootView.findViewById(R.id.linear_address);


            editTextAddress = (EditText) rootView.findViewById(R.id.address);
            editTextAddress.setText("");

            /**
             * Notes line
             */

            txt_notes = (TextView) rootView.findViewById(R.id.notes);

            /**
             * Amount
             */

            editTextAmount = (EditText) rootView.findViewById(R.id.amount);
            /**
             *  Amount observer
             */
            editTextAmount.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    try {
                        //Long amount = Long.parseLong(editTextAmount.getText().toString());
                        //if (amount > 0) {
                        //long actualBalance = cryptoWallet.getBalance(BalanceType.AVAILABLE,referenceWalletSession.getWalletSessionType().getWalletPublicKey());
                        //editTextAmount.setHint("Available amount: " + actualBalance + " bits");
                        //}
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });


            FermatButton send_button = (FermatButton) rootView.findViewById(R.id.send_button);
            send_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                        im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }

                    if (walletContact != null)
                        sendCrypto();
                    else
                        Toast.makeText(getActivity(), "Contact not found, please add it.", Toast.LENGTH_LONG).show();

                    //testing metadata
                         /*   cryptoWallet.sendMetadataLikeChampion(Long.parseLong("100000"),
                                    null,
                                    "holasdad",
                                    referenceWalletSession.getWalletSessionType().getWalletPublicKey(),
                                    "actor_prueba_juan_public_key",
                                    Actors.INTRA_USER,
                                    "actor_prueba_robert_public_key",
                                    Actors.INTRA_USER);*/

                }
            });

            send_button.selector(R.drawable.bg_home_accept_active, R.drawable.bg_home_accept_normal, R.drawable.bg_home_accept_active);


            /**
             * BarCode Scanner
             */
            rootView.findViewById(R.id.scan_qr).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.address));
                    integrator.initiateScan();
                }
            });

            return rootView;
        }catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //TODO: VER QUE PASA  SI EL CONTACTO NO TIENE UNA WALLET ADDRESS
    private void sendCrypto() {

        CryptoAddress validAddress = null;
        if(editTextAddress!=null) {
            if (!editTextAddress.getText().toString().equals(""))
                validAddress = WalletUtils.validateAddress(editTextAddress.getText().toString(), cryptoWallet);
        }else{
            validAddress = WalletUtils.validateAddress(walletContact.address, cryptoWallet);
        }



        if (validAddress != null) {
            EditText amount = (EditText) rootView.findViewById(R.id.amount);

            if(!amount.getText().toString().equals("") && amount.getText()!=null) {
                try {
                    String notes=null;
                    if(txt_notes.getText().toString().length()!=0){
                        notes = txt_notes.getText().toString();
                    }


                    CryptoWalletWalletContact cryptoWalletWalletContact = cryptoWallet.findWalletContactById(walletContact.contactId,referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey());

                    //TODO: ver que mas puedo usar del cryptoWalletWalletContact


                    cryptoWallet.send(
                            Long.parseLong(amount.getText().toString()),
                            validAddress,
                            notes,
                            referenceWalletSession.getWalletSessionType().getWalletPublicKey(),
                            intraUserModuleManager.getActiveIntraUserIdentity().getPublicKey(),
                            Actors.INTRA_USER,
                            walletContact.actorPublicKey,
                            cryptoWalletWalletContact.getActorType(),
                            ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
                    );
                    Toast.makeText(getActivity(), "Send OK", Toast.LENGTH_LONG).show();
                } catch (InsufficientFundsException e) {
                    Toast.makeText(getActivity(), "Insufficient funds", Toast.LENGTH_LONG).show();
                } catch (CantSendCryptoException e) {
                    referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "Error send satoshis - " + e.getMessage());
                } catch (WalletContactNotFoundException e) {
                    e.printStackTrace();
                } catch (CantFindWalletContactException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"oooopps",Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getActivity(), "Invalid Address", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Obtain the wallet contacts from the cryptoWallet
     *
     * @return
     */
    private List<WalletContact> getWalletContactList() {
        List<WalletContact> contacts = new ArrayList<>();
        try
        {
            List<CryptoWalletWalletContact> walletContactRecords = cryptoWallet.listAllActorContactsAndConnections(referenceWalletSession.getWalletSessionType().getWalletPublicKey(), intraUserModuleManager.getActiveIntraUserIdentity().getPublicKey());
            for (CryptoWalletWalletContact wcr : walletContactRecords) {

                String contactAddress = "";
                if(wcr.getReceivedCryptoAddress().size() > 0)
                    contactAddress = wcr.getReceivedCryptoAddress().get(0).getAddress();
                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), contactAddress,wcr.isConnection(),wcr.getProfilePicture()));
            }
        }
        catch (CantGetAllWalletContactsException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());
        }
        catch (CantGetActiveLoginIdentityException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetActiveLoginIdentityException- " + e.getMessage());
        }
        return contacts;
    }

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
            //this.lauchCreateContactDialogSend(true);

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
                    //this.lauchCreateContactDialogSend(false);
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


}
