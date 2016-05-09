package com.juaco.fermat_contact_fragment.dialog;


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
import android.widget.*;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.juaco.fermat_contact_fragment.R;
import com.juaco.fermat_contact_fragment.adapters.WalletContact;
import com.juaco.fermat_contact_fragment.bar_code_scanner.IntentIntegrator;
import com.juaco.fermat_contact_fragment.interfaces.CreateContactDialogCallback;

import java.io.ByteArrayOutputStream;

/**
 * Created by joaquin carrasquero on 26/02/16.
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
    private FermatSession appSession;

    /**
     * Contact member
     */
    private WalletContact walletContact;
    private String user_address_wallet = "";

    /**
     * UI components
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
     * @param a
     * @param
     */


    public CreateContactFragmentDialog(Activity a, FermatSession appSession, WalletContact walletContact, String userId, Bitmap contactImageBitmap, CreateContactDialogCallback createContactDialogCallback) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.appSession = appSession;
        this.walletContact = walletContact;
        this.userId = userId;
        this.contactImageBitmap = contactImageBitmap;
        this.createContactDialogCallback = createContactDialogCallback;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();

    }

    private void setUpScreenComponents() {

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.create_contact_dialog);


            save_contact_btn = (Button) findViewById(R.id.save_contact_btn);
            cancel_btn = (Button) findViewById(R.id.cancel_btn);
            contact_name = (AutoCompleteTextView) findViewById(R.id.contact_name);
            take_picture_btn = (ImageView) findViewById(R.id.take_picture_btn);
            txt_address = (EditText) findViewById(R.id.txt_address);
            if (walletContact != null) {
                contact_name.setText(walletContact.name);
            }


            cancel_btn.setOnClickListener(this);
            save_contact_btn.setOnClickListener(this);

            if (contactImageBitmap != null) {
                contactImageBitmap = Bitmap.createScaledBitmap(contactImageBitmap, 65, 65, true);
                take_picture_btn.setBackground(new BitmapDrawable(getContext().getResources(), contactImageBitmap));
                take_picture_btn.setImageDrawable(null);
            }

            take_picture_btn.setOnClickListener(this);

            ImageView scanImage = (ImageView) findViewById(R.id.scan_qr);

            scanImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = new IntentIntegrator(activity, (EditText) findViewById(R.id.contact_address_fermat));
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel_btn) {
            //activity.finish();
            dismiss();
        } else if (i == R.id.save_contact_btn) {
            saveContact();
        } else if (i == R.id.take_picture_btn) {
            createContactDialogCallback.openContextImageSelector();
        }
    }

    /**
     * create contact and save it into database
     */
    private void saveContact() {
        //TODO: add saving contact logic here.
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

                //TODO: Apply method to validate the incomming clipboard address.
                    editText.setText(item.getText().toString());
            } else {
                // This enables the paste menu item, since the clipboard contains plain text.
                mPasteItem.setEnabled(false);
            }
        } catch (Exception e) {
            appSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
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