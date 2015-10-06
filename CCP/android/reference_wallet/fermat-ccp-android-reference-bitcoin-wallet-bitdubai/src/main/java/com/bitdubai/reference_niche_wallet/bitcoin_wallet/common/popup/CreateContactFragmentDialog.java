package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.FactoryProject;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.RoundedDrawable;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

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
    TextView contact_name;
    ImageView take_picture_btn;


    /**
     * Allow the zxing engine use the default argument for the margin variable
     */
    private Bitmap contactPicture;


    /**
     *
     * @param a
     * @param
     */


    public CreateContactFragmentDialog(Activity a, ReferenceWalletSession referenceWalletSession, WalletContact walletContact, String userId,Bitmap contactImageBitmap) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.referenceWalletSession = referenceWalletSession;
        this.walletContact=walletContact;
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


            contact_name.setText(walletContact.name);


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
            saveContact();
        }
    }

    /**
     * create contact and save it into database
     */
    private void saveContact() {
        try {

            CryptoWallet cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();

            CryptoAddress validAddress = validateAddress(walletContact.address,cryptoWallet );

            if (validAddress != null) {

                // first i add the contact
                cryptoWallet.createWalletContact(
                        validAddress,
                        contact_name.getText().toString(),
                        null,
                        null,
                        Actors.EXTRA_USER,
                        referenceWalletSession.getWalletSessionType().getWalletPublicKey()
                );

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





}