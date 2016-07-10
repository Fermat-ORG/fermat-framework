package org.fermat.fermat_dap_android_sub_app_asset_factory.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.AssetFactorySessionReferenceApp;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/18/16.
 */
public class AssetFactoryWelcomeDialog extends FermatDialog<AssetFactorySessionReferenceApp, SubAppResourcesProviderManager> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final int TYPE_PRESENTATION = 1;
    public static final int TYPE_PRESENTATION_WITHOUT_IDENTITIES = 2;

    private final Activity activity;
    private final int type;
    private final boolean checkButton;

    private CheckBox checkDontShow;
    private ImageView welcomeAppImage;


    public AssetFactoryWelcomeDialog(Activity activity, AssetFactorySessionReferenceApp fermatSession, SubAppResourcesProviderManager resources, int type, boolean checkButton) {
        super(activity, fermatSession, resources);
        this.activity = activity;
        this.type = type;
        this.checkButton = checkButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO setup UI
    }

    private void setUpListeners() {
        //TODO setup listeners
    }

    @Override
    protected int setLayoutId() {
        switch (type) {
            case TYPE_PRESENTATION:
                return 0;//R.layout.presentation_wallet;
            case TYPE_PRESENTATION_WITHOUT_IDENTITIES:
                return 0;//R.layout.presentation_wallet_without_identities;
            default:
                return 0;
        }
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View view) {
//        int id = v.getId();
//
//        if(id == R.id.btn_left){
//            try {
//                getSession().getModuleManager().getCryptoWallet().createIntraUser("John Doe","Available",convertImage(R.drawable.ic_profile_male));
//                getSession().setData(SessionConstant.PRESENTATION_IDENTITY_CREATED, Boolean.TRUE);
//            } catch (CantCreateNewIntraWalletUserException e) {
//                e.printStackTrace();
//            } catch (CantGetCryptoWalletException e) {
//                e.printStackTrace();
//            }
//            saveSettings();
//            dismiss();
//        }
    }

    private void saveSettings() {
//        if(type!=TYPE_PRESENTATION)
//            if(checkButton == checkbox_not_show.isChecked()  || checkButton == !checkbox_not_show.isChecked())
//                if(checkbox_not_show.isChecked()){
//                    SettingsManager<BitcoinWalletSettings> settingsManager = getSession().getModuleManager().getSettingsManager();
//                    try {
//                        BitcoinWalletSettings bitcoinWalletSettings = settingsManager.loadAndGetSettings(getSession().getAppPublicKey());
//                        bitcoinWalletSettings.setIsPresentationHelpEnabled(false);
//                        settingsManager.persistSettings(getSession().getAppPublicKey(),bitcoinWalletSettings);
//                    } catch (CantGetSettingsException e) {
//                        e.printStackTrace();
//                    } catch (SettingsNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (CantPersistSettingsException e) {
//                        e.printStackTrace();
//                    }
//                }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//        if(isChecked){
//            getSession().setData(SessionConstant.PRESENTATION_SCREEN_ENABLED,Boolean.TRUE);
//        }else {
//            getSession().setData(SessionConstant.PRESENTATION_SCREEN_ENABLED,Boolean.FALSE);
//        }
    }

    @Override
    public void onBackPressed() {
        saveSettings();
        super.onBackPressed();
    }
}
