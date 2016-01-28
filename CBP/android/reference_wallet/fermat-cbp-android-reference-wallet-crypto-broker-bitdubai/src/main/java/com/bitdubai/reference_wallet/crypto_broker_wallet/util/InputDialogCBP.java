package com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationCallback;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.lang.ref.WeakReference;

/**
 * Created by richardalexander on 26/01/16.
 */
public class InputDialogCBP extends FermatDialog<FermatSession, SubAppResourcesProviderManager> implements View.OnClickListener{

    private final Activity activity;
    int DialogType;

    public InputDialogCBP(Activity activity, FermatSession fermatSession, SubAppResourcesProviderManager resources) {
        super(activity, fermatSession, resources);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomInfo();
    }
    public void DialogType(int DialogTypeset){DialogType = DialogTypeset;}

    private void CustomInfo(){
        if(DialogType == 1){

        }
    }
    protected int setLayoutId() {
        return R.layout.inputdialogcbp;
    }
    private void saveSettings() {
                 SettingsManager settingsManager = getSession().getModuleManager().getSettingsManager();
                    try {
                        FermatSettings bitcoinWalletSettings = settingsManager.loadAndGetSettings(getSession().getAppPublicKey());
                        bitcoinWalletSettings.setIsPresentationHelpEnabled(false);
                        settingsManager.persistSettings(getSession().getAppPublicKey(), bitcoinWalletSettings);
                    } catch (CantGetSettingsException | SettingsNotFoundException | CantPersistSettingsException e) {

                    }
    }
    public void onClick(View v) {
        int id = v.getId();
        
    }
    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


}
