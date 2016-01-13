package com.bitdubai.sub_app.intra_user_identity.common.popup;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraUserIdentitySettings;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_identity.R;
import com.bitdubai.sub_app.intra_user_identity.session.IntraUserIdentitySubAppSession;

/**
 * @author Jose manuel De Sousa
 */
public class PresentationIntraUserIdentityDialog extends FermatDialog<IntraUserIdentitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    private final Activity activity;
    private FermatButton startCommunity;
    private CheckBox dontShowAgainCheckBox;
    IntraUserIdentityModuleManager moduleManager;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public PresentationIntraUserIdentityDialog(Activity activity, IntraUserIdentitySubAppSession fermatSession, SubAppResourcesProviderManager resources,final IntraUserIdentityModuleManager moduleManager) {
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
                SettingsManager<IntraUserIdentitySettings> settingsManager = moduleManager.getSettingsManager();
                try {
                    if(getSession().getAppPublicKey() != null ){
                        IntraUserIdentitySettings intraUserIdentitySettings = settingsManager.loadAndGetSettings(getSession().getAppPublicKey());
                        intraUserIdentitySettings.setIsPresentationHelpEnabled(!dontShowAgainCheckBox.isChecked());
                        settingsManager.persistSettings(getSession().getAppPublicKey(),intraUserIdentitySettings);

                    }else{
                        //TODO: Joaquin: Lo estoy poniendo con un public key hardcoded porque en este punto no posee public key.
                        IntraUserIdentitySettings intraUserIdentitySettings = settingsManager.loadAndGetSettings("123456789");
                        intraUserIdentitySettings.setIsPresentationHelpEnabled(!dontShowAgainCheckBox.isChecked());
                        if (getSession().getAppPublicKey()!=null){
                            settingsManager.persistSettings(getSession().getAppPublicKey(), intraUserIdentitySettings);
                        }else{
                            settingsManager.persistSettings("123456789", intraUserIdentitySettings);
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
