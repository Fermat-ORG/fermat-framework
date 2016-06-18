package com.bitdubai.sub_app.wallet_manager.popup;

import android.app.Activity;
import android.app.Dialog;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bitdubai.fermat_wpd.wallet_manager.R;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 */

public class CreateUserFragmentDialog extends Dialog implements
        View.OnClickListener {



    private final String userId;
    private Bitmap contactImageBitmap;
    public Activity activity;
    public Dialog d;


    /**
     * Resources
     */


    /**
     *  Contact member
     */

    /**
     *  UI components
     */
    Button save_contact_btn;
    Button cancel_btn;
    TextView contact_name;
    ImageView take_picture_btn;


    /**
     * Allow the zxing engine use the default argument for the margin variable
     */
    private Bitmap contactPicture;
    private EditText txt_address;


    /**
     *
     * @param a
     * @param
     */


    public CreateUserFragmentDialog(Activity a,  String userId, Bitmap contactImageBitmap) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.userId = userId;
        this.contactImageBitmap = contactImageBitmap;

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
            contact_name = (EditText) findViewById(R.id.contact_name);
            take_picture_btn = (ImageView) findViewById(R.id.take_picture_btn);



            cancel_btn.setOnClickListener(this);
            save_contact_btn.setOnClickListener(this);

            if(contactImageBitmap!=null){
                contactImageBitmap = Bitmap.createScaledBitmap(contactImageBitmap,65,65,true);
                take_picture_btn.setBackground(new BitmapDrawable(contactImageBitmap));
                take_picture_btn.setImageDrawable(null);
            }







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
            //saveContact();
        }
    }

//    /**
//     * create contact and save it into database
//     */
//    private void saveContact() {
//        try {
//
//            CryptoWallet cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();
//
//            CryptoAddress validAddress = validateAddress(txt_address.getText().toString(),cryptoWallet );
//
//            if (validAddress != null) {
//
//                // first i add the contact
//                cryptoWallet.createWalletContact(
//                        validAddress,
//                        contact_name.getText().toString(),
//                        null,
//                        null,
//                        Actors.EXTRA_USER,
//                        referenceWalletSession.getWalletSessionType().getWalletPublicKey()
//                );
//
//                Toast.makeText(activity.getApplicationContext(), "Contact saved!", Toast.LENGTH_SHORT).show();
//
//
//                dismiss();
//
//
//            } else {
//                Toast.makeText(activity.getApplicationContext(), "Please enter a valid address...", Toast.LENGTH_SHORT).show();
//            }
//        } catch (CantCreateWalletContactException e) {
//            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
//            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error-" + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
//            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error - " +  e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }








}