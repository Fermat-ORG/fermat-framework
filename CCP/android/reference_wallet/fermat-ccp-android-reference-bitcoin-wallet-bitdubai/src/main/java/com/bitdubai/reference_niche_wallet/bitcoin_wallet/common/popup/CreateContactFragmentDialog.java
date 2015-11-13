package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.CreateContactDialogCallback;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.validateAddress;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 */

public class CreateContactFragmentDialog extends Dialog implements
        View.OnClickListener {



    private final String userId;
    private Bitmap contactImageBitmap;
    public Activity activity;
    public Dialog d;

    private WalletContactListAdapter contactsAdapter;

    private CreateContactDialogCallback createContactDialogCallback;


    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;
    private ReferenceWalletSession referenceWalletSession;

    /**
     *  Contact member
     */
    private WalletContact walletContact;
    private String user_address_wallet = "";

    /**
     *  UI components
     */
    Button save_contact_btn;
    Button cancel_btn;
    AutoCompleteTextView contact_name;
    ImageView take_picture_btn;


    /**
     * Allow the zxing engine use the default argument for the margin variable
     */
    private Bitmap contactPicture;
    private EditText txt_address;

   private Typeface tf;
    /**
     *
     * @param a
     * @param
     */


    public CreateContactFragmentDialog(Activity a, ReferenceWalletSession referenceWalletSession, WalletContact walletContact, String userId,Bitmap contactImageBitmap,CreateContactDialogCallback createContactDialogCallback) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.referenceWalletSession = referenceWalletSession;
        this.walletContact=walletContact;
        this.userId = userId;
        this.contactImageBitmap = contactImageBitmap;
        this.createContactDialogCallback = createContactDialogCallback;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();

//        user_address_wallet= getWalletAddress(walletContact.actorPublicKey);
//
//        showQRCodeAndAddress();


    }

    private void setUpScreenComponents(){

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.create_contact_dialog);


            save_contact_btn = (Button) findViewById(R.id.save_contact_btn);
            cancel_btn = (Button) findViewById(R.id.cancel_btn);
            contact_name = (AutoCompleteTextView) findViewById(R.id.contact_name);
            take_picture_btn = (ImageView) findViewById(R.id.take_picture_btn);
            txt_address = (EditText) findViewById(R.id.txt_address);
            contact_name.setText(walletContact.name);



            cancel_btn.setOnClickListener(this);
            save_contact_btn.setOnClickListener(this);

            if(contactImageBitmap!=null){
                contactImageBitmap = Bitmap.createScaledBitmap(contactImageBitmap,65,65,true);
                take_picture_btn.setBackground(new BitmapDrawable(contactImageBitmap));
                take_picture_btn.setImageDrawable(null);
            }

            take_picture_btn.setOnClickListener(this);

            ImageView scanImage = (ImageView) findViewById(R.id.scan_qr);

            scanImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = new IntentIntegrator(activity, (EditText) findViewById(R.id.contact_address));
                    integrator.initiateScan();
                }
            });

            // paste_button button definition
            ImageView pasteFromClipboardButton = (ImageView) findViewById(R.id.paste_from_clipboard_btn);
            pasteFromClipboardButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pasteFromClipboard();
                }
            });




            contactsAdapter = new WalletContactListAdapter(activity, R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());

            contact_name.setAdapter(contactsAdapter);
            //autocompleteContacts.setTypeface(tf);
            contact_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    walletContact = (WalletContact) arg0.getItemAtPosition(position);

                    //add connection like a wallet contact
                    try
                    {
                        if(walletContact.isConnection)
                            referenceWalletSession.getCryptoWalletManager().getCryptoWallet().convertConnectionToContact(
                                    walletContact.name,
                                    Actors.INTRA_USER,
                                    walletContact.actorPublicKey,
                                    walletContact.profileImage,
                                    Actors.INTRA_USER,
                                    referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey(),
                                    "reference_wallet"/*referenceWalletSession.getWalletSessionType().getWalletPublicKey()*/ ,
                                    CryptoCurrency.BITCOIN,
                                    BlockchainNetworkType.TEST);

                    }
                    catch (CantGetActiveLoginIdentityException e) {
                        referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        showMessage(activity, "CantGetActiveLoginIdentityException- " + e.getMessage());
                    }
                    catch(CantCreateWalletContactException e)
                    {
                        referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        showMessage(activity, "CantCreateWalletContactException- " + e.getMessage());

                    }
                    catch(ContactNameAlreadyExistsException e)
                    {
                        referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        showMessage(activity, "ContactNameAlreadyExistsException- " + e.getMessage());

                    } catch (CantGetCryptoWalletException e) {
                        e.printStackTrace();
                    }
                }
            });


            contact_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    linear_address.setVisibility(activeAddress ? View.VISIBLE : View.GONE);
//                    // if (!editTextAddress.getText().equals("")) linear_address.setVisibility(View.VISIBLE);
                }
            });

            //getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }catch (Exception e){
            e.printStackTrace();
        }

    }




    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel_btn) {
            //activity.finish();
            dismiss();
        }else if( i == R.id.save_contact_btn){
            saveContact();
        }else if ( i == R.id.take_picture_btn){
            createContactDialogCallback.openContextImageSelector();
        }
    }

    /**
     * create contact and save it into database
     */
    private void saveContact() {
        try {

            CryptoWallet cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();

            CryptoAddress validAddress = validateAddress(txt_address.getText().toString(), cryptoWallet);

            String name =contact_name.getText().toString();

            if (validAddress != null && !name.equals("")) {

                // first i add the contact
                //check photo is not null

                if(contactImageBitmap!=null){

                    cryptoWallet.createWalletContactWithPhoto(
                            validAddress,
                            name,
                            null,
                            null,
                            Actors.EXTRA_USER,
                            referenceWalletSession.getWalletSessionType().getWalletPublicKey(),
                            toByteArray(contactImageBitmap)
                    );
                }
                else
                {
                    cryptoWallet.createWalletContact(
                            validAddress,
                            contact_name.getText().toString(),
                            null,
                            null,
                            Actors.EXTRA_USER,
                            referenceWalletSession.getWalletSessionType().getWalletPublicKey()
                    );
                }


                Toast.makeText(activity.getApplicationContext(), "Contact saved!", Toast.LENGTH_SHORT).show();
                

                dismiss();


            } else {
                Toast.makeText(activity.getApplicationContext(), "Please enter a valid address...", Toast.LENGTH_SHORT).show();
            }
        } catch (CantCreateWalletContactException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error-" + e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error - " +  e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Paste valid clipboard text into a view
     *
     * @param
     */
    private void pasteFromClipboard() {
        try {
            ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

            // Gets the ID of the "paste" menu item
            ImageView mPasteItem = (ImageView) findViewById(R.id.paste_from_clipboard_btn);
            if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                mPasteItem.setEnabled(true);
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                EditText editText = (EditText) findViewById(R.id.txt_address);
                CryptoAddress validAddress = validateAddress(item.getText().toString(), referenceWalletSession.getCryptoWalletManager().getCryptoWallet());
                if (validAddress != null) {
                    editText.setText(validAddress.getAddress());
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Cannot find an address in the clipboard text.\n\n" + item.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                // This enables the paste menu item, since the clipboard contains plain text.
                mPasteItem.setEnabled(false);
            }
        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Bitmap to byte[]
     *
     * @param bitmap Bitmap
     * @return byte array
     */
    private byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
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
            List<CryptoWalletWalletContact> walletContactRecords = referenceWalletSession.getCryptoWalletManager().getCryptoWallet().listAllActorContactsAndConnections(referenceWalletSession.getWalletSessionType().getWalletPublicKey(), referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey());
            for (CryptoWalletWalletContact wcr : walletContactRecords) {

                String contactAddress = "";
                if(wcr.getReceivedCryptoAddress().size() > 0)
                    contactAddress = wcr.getReceivedCryptoAddress().get(0).getAddress();
                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), contactAddress,wcr.isConnection(),wcr.getProfilePicture()));
            }
        }
        catch (CantGetAllWalletContactsException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(activity, "CantGetAllWalletContactsException- " + e.getMessage());
        }
        catch (CantGetActiveLoginIdentityException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(activity, "CantGetActiveLoginIdentityException- " + e.getMessage());
        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
        }
        return contacts;
    }

}