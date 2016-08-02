package com.bitdubai.reference_niche_wallet.fermat_wallet.common.popup;

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
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.CreateContactDialogCallback;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.contacts_list_adapter.WalletContact;

import java.io.ByteArrayOutputStream;

import static com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.WalletUtils.validateAddress;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 */

public class CreateContactFragmentDialog extends Dialog implements
        View.OnClickListener {



    private final String userId;
    private Bitmap contactImageBitmap;
    public Activity activity;
    public Dialog d;

    private CreateContactDialogCallback createContactDialogCallback;


    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;

    private ReferenceAppFermatSession<FermatWallet> fermatWalletSessionReferenceApp;

    BlockchainNetworkType blockchainNetworkType;

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


    public CreateContactFragmentDialog(Activity a, ReferenceAppFermatSession<FermatWallet> fermatWalletSessionReferenceApp, WalletContact walletContact, String userId,Bitmap contactImageBitmap,CreateContactDialogCallback createContactDialogCallback) {

        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.fermatWalletSessionReferenceApp = fermatWalletSessionReferenceApp;
        this.walletContact=walletContact;
        this.userId = userId;
        this.contactImageBitmap = contactImageBitmap;
        this.createContactDialogCallback = createContactDialogCallback;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();
        FermatWalletSettings bitcoinWalletSettings = null;
        try {
            bitcoinWalletSettings = fermatWalletSessionReferenceApp.getModuleManager().loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey());
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        }

        if(bitcoinWalletSettings != null) {

            if (bitcoinWalletSettings.getBlockchainNetworkType() == null) {
                bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            }
            try {
                fermatWalletSessionReferenceApp.getModuleManager().persistSettings(fermatWalletSessionReferenceApp.getAppPublicKey(), bitcoinWalletSettings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }

        }

        try {
            blockchainNetworkType = fermatWalletSessionReferenceApp.getModuleManager().loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey()).getBlockchainNetworkType();
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        }

//        user_address_wallet= getWalletAddress(walletContact.actorPublicKey);
//
//        showQRCodeAndAddress();


    }

    private void setUpScreenComponents(){

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.fermat_wallet_create_contact_dialog);


            save_contact_btn = (Button) findViewById(R.id.save_contact_btn);
            cancel_btn = (Button) findViewById(R.id.cancel_btn);
            contact_name = (AutoCompleteTextView) findViewById(R.id.contact_name);
            take_picture_btn = (ImageView) findViewById(R.id.take_picture_btn);
            txt_address = (EditText) findViewById(R.id.txt_address);
            if(walletContact!=null){
                contact_name.setText(walletContact.name);
            }




            cancel_btn.setOnClickListener(this);
            save_contact_btn.setOnClickListener(this);

            if(contactImageBitmap!=null){
                contactImageBitmap = Bitmap.createScaledBitmap(contactImageBitmap,65,65,true);
                take_picture_btn.setBackground(new BitmapDrawable(getContext().getResources(),contactImageBitmap));
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

            FermatWallet fermatWallet = fermatWalletSessionReferenceApp.getModuleManager();

            CryptoAddress validAddress = validateAddress(txt_address.getText().toString(), fermatWallet);

            String name =contact_name.getText().toString();

            if (validAddress != null && !name.equals("")) {

                // first i add the contact
                //check photo is not null

                if(contactImageBitmap!=null){

                    fermatWallet.createWalletContactWithPhoto(
                            validAddress,
                            name,
                            null,
                            null,
                            Actors.EXTRA_USER,
                            fermatWalletSessionReferenceApp.getAppPublicKey(),
                            toByteArray(contactImageBitmap),
                            blockchainNetworkType
                    );
                }
                else
                {
                    fermatWallet.createWalletContact(
                            validAddress,
                            contact_name.getText().toString(),
                            null,
                            null,
                            Actors.EXTRA_USER,
                            fermatWalletSessionReferenceApp.getAppPublicKey(),
                            blockchainNetworkType
                    );
                }


                Toast.makeText(activity.getApplicationContext(), "Contact saved!", Toast.LENGTH_SHORT).show();
                

                dismiss();


            } else {
                Toast.makeText(activity.getApplicationContext(), "Please enter a valid address...", Toast.LENGTH_SHORT).show();
            }
        } catch (CantCreateWalletContactException e) {
            fermatWalletSessionReferenceApp.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error-" + e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            fermatWalletSessionReferenceApp.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
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
                CryptoAddress validAddress = validateAddress(item.getText().toString(), fermatWalletSessionReferenceApp.getModuleManager());
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
            fermatWalletSessionReferenceApp.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
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


}