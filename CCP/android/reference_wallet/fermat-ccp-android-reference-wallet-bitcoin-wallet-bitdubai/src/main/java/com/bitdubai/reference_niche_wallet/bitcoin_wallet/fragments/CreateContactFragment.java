package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.RoundedDrawable;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.squareup.picasso.Picasso;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.validateAddress;

/**
 * Created by natalia on 19/06/15.
 * Modifed by Ronner Velazquez on 07/08/2015
 */
public class CreateContactFragment extends FermatWalletFragment {
    private static final String ARG_POSITION = "position";


    /**
     * Members
     */
    String contactName = "";
    String sufixName = "";
    String prefixName = "";
    String lastName = "";
    String middleName = "";
    String name = "";
    String alias = "";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 2;
    static final int CONTEXT_MENU_CAMERA = 1;
    static final int CONTEXT_MENU_GALLERY = 2;
    static final int CONTEXT_MENU_DELETE = 3;

    boolean detailsVisible = false;
    boolean detailsSeparated = false;
    Bitmap contactPicture;
    /**
     * Fragment style
     */
    Typeface tf;
    /**
     * Screen views
     */
    private View rootView;
    private EditText editAddress;
    private EditText editContactName;
    private EditText editPrefixName;
    private EditText editLastName;
    private EditText editMiddleName;
    private EditText editSufixName;
    private EditText editAlias;
    private TextView editAddressTitle;
    private EditText editAddressType;
    private ImageView takePictureButton;
    /**
     * DealsWithWalletModuleCryptoWallet Interface member variables.
     */
    private CryptoWalletManager cryptoWalletManager;
    private CryptoWallet cryptoWallet;

    /**
     * deals with Error manager
     */
    private ErrorManager errorManager;

    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;
    private ReferenceWalletSession referenceWalletSession;


    /**
     *
     * @return
     */

    public static CreateContactFragment newInstance() {
        CreateContactFragment f = new CreateContactFragment();

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        referenceWalletSession = (ReferenceWalletSession) walletSession;


        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        errorManager = walletSession.getErrorManager();
        try {
            cryptoWalletManager = referenceWalletSession.getCryptoWalletManager();
            cryptoWallet = cryptoWalletManager.getCryptoWallet();

        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_create_contact, container, false);
        try {

            // save_contact and cancel button definition
            Button saveContactButton = (Button) rootView.findViewById(R.id.save_contact_btn);
            Button cancelButton = (Button) rootView.findViewById(R.id.cancel_btn);

            saveContactButton.setTypeface(tf);
            cancelButton.setTypeface(tf);

            saveContactButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    saveContact();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    returnToContacts();
                }
            });

            //details_button definition
            final ImageButton detailsButton = (ImageButton) rootView.findViewById(R.id.details_btn);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!detailsVisible) {
                        showDetails();
                        Picasso.with(getActivity()).load(R.drawable.ic_arrow_up_grey).into(detailsButton);
                    } else {
                        hideDetails();
                        Picasso.with(getActivity()).load(R.drawable.ic_arrow_down_grey).into(detailsButton);
                    }
                }
            });

            //take_picture_button definition
            takePictureButton = (ImageView) rootView.findViewById(R.id.take_picture_btn);
            takePictureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerForContextMenu(takePictureButton);
                    getActivity().openContextMenu(takePictureButton);
                }
            });


            // paste_button button definition
            ImageView pasteFromClipboardButton = (ImageView) rootView.findViewById(R.id.paste_from_clipboard_btn);
            pasteFromClipboardButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pasteFromClipboard(rootView);
                }
            });

            editContactName = (EditText) rootView.findViewById(R.id.contact_name);
            editLastName = (EditText) rootView.findViewById(R.id.contact_last_name);
            editPrefixName = (EditText) rootView.findViewById(R.id.contact_name_prefix);
            editMiddleName = (EditText) rootView.findViewById(R.id.contact_middle_name);
            editSufixName = (EditText) rootView.findViewById(R.id.contact_name_sufix);
            editAlias = (EditText) rootView.findViewById(R.id.contact_alias);
            editAddressTitle = (TextView) rootView.findViewById(R.id.address_title);
            editAddressType = (EditText) rootView.findViewById(R.id.contact_address_type);

            editContactName.setText(contactName);

            editContactName.setTypeface(tf);
            editLastName.setTypeface(tf);
            editPrefixName.setTypeface(tf);
            editMiddleName.setTypeface(tf);
            editSufixName.setTypeface(tf);
            editAlias.setTypeface(tf);
            editAddressTitle.setTypeface(tf);
            editAddressType.setTypeface(tf);

            editAddress = (EditText) rootView.findViewById(R.id.contact_address);
            editAddress.setTypeface(tf);
           /* editAddress.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (validateAddress(editAddress.getText().toString(), cryptoWallet) != null) {
                        editAddress.setTextColor(Color.parseColor("#72af9c"));
                    } else {
                        editAddress.setTextColor(Color.parseColor("#b46a54"));
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });*/

            ImageView scanImage = (ImageView) rootView.findViewById(R.id.scan_qr);

            scanImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.contact_address));
                    integrator.initiateScan();
                }
            });

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    /**
     * create contact and save it into database
     */
    private void saveContact() {
        try {
            EditText contact_name = (EditText) rootView.findViewById(R.id.contact_name);
            EditText address = (EditText) rootView.findViewById(R.id.contact_address);

            CryptoAddress validAddress = validateAddress(address.getText().toString(), cryptoWallet);

            if (validAddress != null) {

                // first i add the contact
                cryptoWallet.createWalletContact(
                        validAddress,
                        contact_name.getText().toString(),
                        null,
                        null,
                        Actors.EXTRA_USER,
                        walletSession.getWalletSessionType().getWalletPublicKey()
                );

                Toast.makeText(getActivity().getApplicationContext(), "Contact saved!", Toast.LENGTH_SHORT).show();
                returnToContacts();


            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid address...", Toast.LENGTH_SHORT).show();
            }
        } catch (CantCreateWalletContactException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error-" + e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error - " +  e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * back to contacts
     */
    private void returnToContacts() {
        ContactsFragment contactsFragment = new ContactsFragment();
        contactsFragment.setWalletSession(walletSession);

        FragmentTransaction FT = getFragmentManager().beginTransaction();

        FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        FT.replace(R.id.fragment_container2, contactsFragment);
        FT.commit();
    }

    /**
     * Paste valid clipboard text into a view
     *
     * @param rootView
     */
    private void pasteFromClipboard(View rootView) {
        try {
            ClipboardManager clipboard = (ClipboardManager) rootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

            // Gets the ID of the "paste" menu item
            ImageView mPasteItem = (ImageView) rootView.findViewById(R.id.paste_from_clipboard_btn);
            if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                mPasteItem.setEnabled(true);
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                EditText editText = (EditText) rootView.findViewById(R.id.contact_address);
                CryptoAddress validAddress = validateAddress(item.getText().toString(), cryptoWallet);
                if (validAddress != null) {
                    editText.setText(validAddress.getAddress());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Cannot find an address in the clipboard text.\n\n" + item.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                // This enables the paste menu item, since the clipboard contains plain text.
                mPasteItem.setEnabled(false);
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Set wallet session
     *
     * @param walletSession
     */
    public void setWalletSession(ReferenceWalletSession walletSession) {
        this.walletSession = walletSession;
    }

    private void showDetails() {
        if (!detailsSeparated) {
            contactName = editContactName.getText().toString();
            if (prefixName.length() > 0) {
                editPrefixName.setText(prefixName);
                contactName = contactName.replace(prefixName + " ", "");
            }
            if (alias.length() > 0) {
                editAlias.setText(alias);
                contactName = contactName.replace(" " + alias, "");
            }
            if (contactName.contains(",")) {
                sufixName = contactName.substring(contactName.indexOf(",") + 2);
                editSufixName.setText(sufixName);
                contactName = contactName.substring(0, contactName.indexOf(","));
            }
            String[] nameArray = contactName.split(" ");
            switch (nameArray.length) {
                case 0:
                    prefixName = "";
                    name = "";
                    middleName = "";
                    lastName = "";
                    sufixName = "";
                    alias = "";

                    editContactName.setHint("first name");

                    editPrefixName.setText("");
                    editContactName.setText("");
                    editMiddleName.setText("");
                    editLastName.setText("");
                    editSufixName.setText("");
                    editAlias.setText("");
                    break;
                case 1:
                    name = nameArray[0];
                    editContactName.setText(name);
                    break;
                case 2:
                    name = nameArray[0];
                    lastName = nameArray[1];
                    editContactName.setText(name);
                    editLastName.setText(lastName);
                    break;
                case 3:
                    name = nameArray[0];
                    middleName = nameArray[1];
                    lastName = nameArray[2];
                    editContactName.setText(name);
                    editMiddleName.setText(middleName);
                    editLastName.setText(lastName);
                    break;
                default:
                    lastName = nameArray[nameArray.length - 1];
                    middleName = nameArray[nameArray.length - 2];
                    editMiddleName.setText(middleName);
                    editLastName.setText(lastName);
                    name = "";
                    for (int i = 0; i < nameArray.length - 2; i++) {
                        name += nameArray[i] + " ";
                    }
                    editContactName.setText(name.trim());
            }
        } else {
            editPrefixName.setText(prefixName);
            editContactName.setText(contactName);
            editMiddleName.setText(middleName);
            editLastName.setText(lastName);
            editSufixName.setText(sufixName);
            editAlias.setText(alias);
        }
        detailsSeparated = true;
        editLastName.setVisibility(View.VISIBLE);
        editPrefixName.setVisibility(View.VISIBLE);
        editMiddleName.setVisibility(View.VISIBLE);
        editSufixName.setVisibility(View.VISIBLE);
        editAlias.setVisibility(View.VISIBLE);
        detailsVisible = true;
    }

    private void hideDetails() {
        editLastName.setVisibility(View.GONE);
        editPrefixName.setVisibility(View.GONE);
        editMiddleName.setVisibility(View.GONE);
        editSufixName.setVisibility(View.GONE);
        editAlias.setVisibility(View.GONE);
        detailsVisible = false;
        prefixName = editPrefixName.getText().toString();
        name = editContactName.getText().toString();
        middleName = editMiddleName.getText().toString();
        lastName = editLastName.getText().toString();
        sufixName = editSufixName.getText().toString();
        alias = editAlias.getText().toString();

        contactName = ((prefixName.length() > 0) ? prefixName + " " : "") + ((name.length() > 0) ? name + " " : "") +
                ((middleName.length() > 0) ? middleName + " " : "") + ((lastName.length() > 0) ? lastName : "") +
                ((sufixName.length() > 0) ? ", " + sufixName : "");
        editContactName.setText(contactName.trim());
        detailsSeparated = false;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = null;
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        imageBitmap = Bitmap.createScaledBitmap(imageBitmap,takePictureButton.getWidth(),takePictureButton.getHeight(),true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            takePictureButton.setBackground(new RoundedDrawable(imageBitmap, takePictureButton));
            takePictureButton.setImageDrawable(null);
            contactPicture = imageBitmap;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select contact picture");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
        menu.add(Menu.NONE, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
        menu.add(Menu.NONE, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");
        if(contactPicture!=null)
            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case CONTEXT_MENU_CAMERA:
                dispatchTakePictureIntent();
                break;
            case CONTEXT_MENU_GALLERY:
                loadImageFromGallery();
                break;
            case CONTEXT_MENU_DELETE:
                takePictureButton.setBackground(getActivity().getResources().
                        getDrawable(R.drawable.rounded_button_green_selector));
                takePictureButton.setImageResource(R.drawable.ic_camera_green);
                contactPicture = null;
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void loadImageFromGallery() {
        Intent intentLoad = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentLoad, REQUEST_LOAD_IMAGE);
    }

    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
        this.walletResourcesProviderManager = walletResourcesProviderManager;
    }
}
