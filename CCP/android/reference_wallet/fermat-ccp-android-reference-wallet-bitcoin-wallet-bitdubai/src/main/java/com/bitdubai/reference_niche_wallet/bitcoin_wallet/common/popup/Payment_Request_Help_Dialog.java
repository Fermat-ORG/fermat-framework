package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;


        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.FrameLayout;
        import android.widget.ImageView;

        import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
        import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
        import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
        import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
        import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
        import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
        import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
        import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
        import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
        import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
        import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;

        import java.io.ByteArrayOutputStream;



/**
 * Created by root on 12/08/16.
 */
public class Payment_Request_Help_Dialog extends FermatDialog<ReferenceAppFermatSession<CryptoWallet>,ResourceProviderManager> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "WelcomeWallet";

    private final Activity activity;

    /**
     * UI
     */
    private FrameLayout container_john_doe;
    private FrameLayout container_jane_doe;
    private FermatTextView txt_title;
    private ImageView image_banner;
    private FermatTextView txt_sub_title;
    private FermatTextView txt_body;
    private FermatTextView footer_title;
    private CheckBox checkbox_not_show;
    private ImageView image_view_left;
    private ImageView image_view_right;
    private Button btn_left;
    private Button btn_right;
    private FermatButton btn_dismiss;

    private boolean checkButton;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */

    public Payment_Request_Help_Dialog(Activity activity, ReferenceAppFermatSession fermatSession, ResourceProviderManager resources,boolean checkButton) {

        super(activity, fermatSession, resources);
        this.activity = activity;
        this.checkButton = checkButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txt_title = (FermatTextView) findViewById(R.id.txt_title);
        image_banner = (ImageView) findViewById(R.id.image_banner);
        txt_sub_title = (FermatTextView) findViewById(R.id.txt_sub_title);
        txt_body = (FermatTextView) findViewById(R.id.txt_body);
        footer_title = (FermatTextView) findViewById(R.id.footer_title);

        checkbox_not_show = (CheckBox) findViewById(R.id.checkbox_not_show);
        checkbox_not_show.setChecked(true);

        btn_dismiss = (FermatButton) findViewById(R.id.btn_dismiss);
        btn_dismiss.setOnClickListener(this);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_payment_request_help;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_dismiss) {
            saveSettings();
            dismiss();
        }

    }

    private void saveSettings() {
//        if(type!=TYPE_PRESENTATION)
//        if(checkButton == checkbox_not_show.isChecked()  || checkButton == !checkbox_not_show.isChecked())
//        if(checkbox_not_show.isChecked()){
        //noinspection TryWithIdenticalCatches
        try {
            getSession().setData(SessionConstant.PAYMENT_REQUEST_HELP_ENABLED, Boolean.FALSE);
            BitcoinWalletSettings bitcoinWalletSettings = getSession().getModuleManager().loadAndGetSettings(getSession().getAppPublicKey());
            if(bitcoinWalletSettings!=null) {
                bitcoinWalletSettings.setIsPaymentHelpEnabled(!checkbox_not_show.isChecked());

                getSession().getModuleManager().persistSettings(getSession().getAppPublicKey(), bitcoinWalletSettings);
            }else{
                Log.e(TAG, "BitcoinWalletSettings null");
            }
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
//        }
    }

    private byte[] convertImage(int resImage) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        return stream.toByteArray();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            getSession().setData(SessionConstant.PRESENTATION_SCREEN_ENABLED,Boolean.TRUE);
        }else {
            getSession().setData(SessionConstant.PRESENTATION_SCREEN_ENABLED,Boolean.FALSE);
        }
    }

    @Override
    public void onBackPressed() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveSettings();
            }
        }).start();

        super.onBackPressed();
    }
}
