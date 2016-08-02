package com.bitdubai.reference_niche_wallet.fermat_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.GeneratorQR;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantRequestFermatAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletContact;
import com.google.zxing.WriterException;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 */

public class ReceiveFragmentDialog extends Dialog implements
        View.OnClickListener {


    private final String identityPublicKey;
    private final String walletPublicKey;
    public Activity activity;
    public Dialog d;


    SettingsManager<FermatWalletSettings> settingsManager;
    BlockchainNetworkType blockchainNetworkType;

    /**
     *  Deals with fermat wallet interface
     */

    private FermatWallet fermatWallet;

    /**
     * Deals with error manager interface
     */

    private ErrorManager errorManager;

    /**
     *  Contact member
     */
    private FermatWalletWalletContact walletContact;
    private String user_address_wallet = "";

    /**
     *  UI components
     */
    Button share_btn;
    Button back_btn;
    TextView txtAddress;
    ImageView imageView_qr_code;


    /**
     *  QR image
     */
    final int width = 400;
    final int height = 500;
    final int colorQR = Color.BLACK;
    final int colorBackQR = Color.WHITE;
    /**
     * Allow the zxing engine use the default argument for the margin variable
     */
    static public int MARGIN_AUTOMATIC = -1;

    private Typeface tf;
    /**
     *
     * @param a
     * @param fermatWallet
     */


    public ReceiveFragmentDialog(Activity a,FermatWallet fermatWallet,ErrorManager errorManager,FermatWalletWalletContact walletContact,String identityPublicKey,String walletPublcKey, BlockchainNetworkType blockchainNetworkType) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.fermatWallet=fermatWallet;
        this.walletContact=walletContact;
        this.errorManager=errorManager;
        this.identityPublicKey = identityPublicKey;
        this.walletPublicKey = walletPublcKey;
        this.blockchainNetworkType = blockchainNetworkType;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();

        user_address_wallet= getWalletAddress(walletContact.getActorPublicKey());

        showQRCodeAndAddress();
    }

    private void setUpScreenComponents(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);

        share_btn = (Button) findViewById(R.id.share_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        imageView_qr_code = (ImageView) findViewById(R.id.imageView_qr_code);

        back_btn.setOnClickListener(this);
        share_btn.setOnClickListener(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));

    }

    private String getWalletAddress(String actorPublicKey) {
        String walletAddres="";
        try {
            //TODO parameters deliveredByActorId deliveredByActorType harcoded..
            CryptoAddress cryptoAddress = fermatWallet.requestAddressToKnownUser(
                    identityPublicKey,
                    Actors.INTRA_USER,
                    actorPublicKey,
                    Actors.EXTRA_USER,
                    Platforms.CRYPTO_CURRENCY_PLATFORM,
                    VaultType.CRYPTO_CURRENCY_VAULT,
                    "BITV",
                    walletPublicKey,
                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                    blockchainNetworkType
            );
            walletAddres = cryptoAddress.getAddress();
        } catch (CantRequestFermatAddressException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
        return walletAddres;
    }

    private void showQRCodeAndAddress() {
        try {

            Bitmap bitmapQR = GeneratorQR.generateBitmap(user_address_wallet, width, height, MARGIN_AUTOMATIC, colorQR, colorBackQR);

            /**
             * set qr image
             */
            imageView_qr_code.setImageBitmap(bitmapQR);
            imageView_qr_code.setScaleType(ImageView.ScaleType.FIT_XY);

            // set address
            txtAddress.setText(user_address_wallet);

        } catch (WriterException writerException) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(writerException));
            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            //Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_btn) {
            //activity.finish();
            dismiss();
        }else if( i == R.id.share_btn){
            shareAddress();
        }



            /*else if (i == R.id.btn_no) {
                dismiss();
            } else {
            }*/
        //dismiss();
    }

    public void shareAddress() {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, user_address_wallet);
        activity.startActivity(Intent.createChooser(intent2, "Share via"));
    }



}