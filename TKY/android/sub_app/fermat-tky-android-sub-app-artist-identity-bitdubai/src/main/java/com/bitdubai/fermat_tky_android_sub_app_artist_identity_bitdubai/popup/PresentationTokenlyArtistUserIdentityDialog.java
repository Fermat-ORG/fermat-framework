package com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.popup;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;

import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.R;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.session.TkyIdentitySubAppSession;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistIdentityManagerModule;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistPreferenceSettings;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 31/03/16.
 */
public class PresentationTokenlyArtistUserIdentityDialog extends FermatDialog<TkyIdentitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    private final Activity activity;
    private FermatButton startCommunity;
    private CheckBox dontShowAgainCheckBox;
    TokenlyArtistIdentityManagerModule moduleManager;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public PresentationTokenlyArtistUserIdentityDialog(Activity activity, TkyIdentitySubAppSession fermatSession, SubAppResourcesProviderManager resources, final TokenlyArtistIdentityManagerModule moduleManager) {
        super(activity, fermatSession, resources);
        this.activity = activity;
        this.moduleManager = moduleManager;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startCommunity = (FermatButton) findViewById(R.id.start_community);
        dontShowAgainCheckBox = (CheckBox) findViewById(R.id.checkbox_not_show);
        startCommunity.setOnClickListener(this);
        dontShowAgainCheckBox.setChecked(true);


    }

    @Override
    protected int setLayoutId() {
        return R.layout.tutorial_intra_user_identity;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.start_community) {
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
            SettingsManager<TokenlyArtistPreferenceSettings> settingsManager = moduleManager.getSettingsManager();
            try {
                if(getSession().getAppPublicKey() != null ){
                    TokenlyArtistPreferenceSettings tokenlyArtistPreferenceSettings = settingsManager.loadAndGetSettings(getSession().getAppPublicKey());
                    tokenlyArtistPreferenceSettings.setIsPresentationHelpEnabled(!dontShowAgainCheckBox.isChecked());
                    settingsManager.persistSettings(getSession().getAppPublicKey(),tokenlyArtistPreferenceSettings);

                }else{
                    TokenlyArtistPreferenceSettings tokenlyArtistPreferenceSettings = settingsManager.loadAndGetSettings("123456789");
                    tokenlyArtistPreferenceSettings.setIsPresentationHelpEnabled(!dontShowAgainCheckBox.isChecked());
                    if (getSession().getAppPublicKey()!=null){
                        settingsManager.persistSettings(getSession().getAppPublicKey(), tokenlyArtistPreferenceSettings);
                    }else{
                        settingsManager.persistSettings("123456789", tokenlyArtistPreferenceSettings);
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
