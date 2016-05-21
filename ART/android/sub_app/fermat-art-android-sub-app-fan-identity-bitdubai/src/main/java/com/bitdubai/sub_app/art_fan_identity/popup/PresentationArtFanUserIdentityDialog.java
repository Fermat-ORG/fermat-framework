package com.bitdubai.sub_app.art_fan_identity.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Fan.FanIdentityManagerModule;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Fan.FanIdentitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.art_fan_identity.R;

import com.bitdubai.sub_app.art_fan_identity.sessions.ArtFanUserIdentitySubAppSession;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/04/16.
 */
public class PresentationArtFanUserIdentityDialog extends
        FermatDialog<
                ArtFanUserIdentitySubAppSession,
                SubAppResourcesProviderManager> implements View.OnClickListener {

    private final Activity activity;
    private FermatButton startCommunity;
    private CheckBox dontShowAgainCheckBox;
    FanIdentityManagerModule moduleManager;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public PresentationArtFanUserIdentityDialog(
            Activity activity,
            ArtFanUserIdentitySubAppSession fermatSession,
            SubAppResourcesProviderManager resources,
            final FanIdentityManagerModule moduleManager) {
        super(activity, fermatSession, resources);
        this.activity = activity;
        this.moduleManager = moduleManager;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startCommunity = (FermatButton) findViewById(R.id.afi_start_community);
        dontShowAgainCheckBox = (CheckBox) findViewById(R.id.afi_checkbox_not_show);
        startCommunity.setOnClickListener(this);
        dontShowAgainCheckBox.setChecked(true);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.tutorial_fan_identity;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.afi_start_community) {
//            SharedPreferences pref = getContext().getSharedPreferences("don't show dialog more", Context.MODE_PRIVATE);
//            SharedPreferences.Editor edit = pref.edit();
            if (dontShowAgainCheckBox.isChecked()) {
//                edit.putBoolean("isChecked", true);
//                edit.apply();
                saveSettings();
                dismiss();
            } else {
//                edit.putBoolean("isChecked", false);
//                edit.apply();
                saveSettings();
                dismiss();
            }
        }
    }

    private void saveSettings(){
        if(dontShowAgainCheckBox.isChecked()){
            SettingsManager<FanIdentitySettings> settingsManager = moduleManager.getSettingsManager();
            try {
                if(getSession().getAppPublicKey() != null ){
                    FanIdentitySettings fanIdentitySettings = settingsManager.loadAndGetSettings(getSession().getAppPublicKey());
                    fanIdentitySettings.setIsPresentationHelpEnabled(!dontShowAgainCheckBox.isChecked());
                    settingsManager.persistSettings(getSession().getAppPublicKey(),fanIdentitySettings);

                }else{
                    FanIdentitySettings tokenlyFanPreferenceSettings =
                            settingsManager.loadAndGetSettings("PresentationArtFanUserIdentityDialog");
                    tokenlyFanPreferenceSettings.setIsPresentationHelpEnabled(
                            !dontShowAgainCheckBox.isChecked());
                    if (getSession().getAppPublicKey()!=null){
                        settingsManager.persistSettings(
                                getSession().getAppPublicKey(), tokenlyFanPreferenceSettings);
                    }else{
                        settingsManager.persistSettings(
                                "PresentationArtFanUserIdentityDialog",
                                tokenlyFanPreferenceSettings);
                    }

                }

            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                e.printStackTrace();
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        }
    }
}

