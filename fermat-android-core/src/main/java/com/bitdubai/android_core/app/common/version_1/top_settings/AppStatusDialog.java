package com.bitdubai.android_core.app.common.version_1.top_settings;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FontType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.callback.AppStatusCallbackChanges;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreModule;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreSettings;

/**
 * Created by mati on 2016.02.10..
 */
public class AppStatusDialog extends Dialog{


    private AndroidCoreModule androidCoreModule;
    private AppStatusCallbackChanges appStatusCallbackChanges;
    private AndroidCoreSettings androidCoreSettings;
    RadioButton radioChecked;

    public AppStatusDialog(Context context,AndroidCoreModule androidCoreModule,AppStatusCallbackChanges appStatusCallbackChanges) {
        super(context);
        this.appStatusCallbackChanges = appStatusCallbackChanges;
        this.androidCoreModule = androidCoreModule;
        try {
            androidCoreSettings = androidCoreModule.loadAndGetSettings(ApplicationConstants.SETTINGS_CORE);
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            androidCoreSettings = new AndroidCoreSettings(AppsStatus.ALPHA);
            try {
                androidCoreModule.persistSettings(ApplicationConstants.SETTINGS_CORE, androidCoreSettings);
            } catch (CantPersistSettingsException e1) {
                e1.printStackTrace();
            }
        }
    }

    public AppStatusDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AppStatusDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.app_status_dialog_main);

        final RadioButton radioButton =(RadioButton) findViewById(R.id.radio_release);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getContext(), "No apps in Release for now", Toast.LENGTH_SHORT).show();
                    buttonView.setChecked(false);
//                    androidCoreSettings.setAppsStatus(AppsStatus.RELEASE);
//                    uncheckAllExcept();
//                    buttonView.setChecked(isChecked);
//                    appStatusCallbackChanges.appSoftwareStatusChanges(AppsStatus.RELEASE);
                }
            }
        });



        RadioButton radioButton1 =(RadioButton)findViewById(R.id.radio_beta);
        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getContext(), "No apps in Beta for now", Toast.LENGTH_SHORT).show();
                    buttonView.setChecked(false);
//                    androidCoreSettings.setAppsStatus(AppsStatus.BETA);
//                    uncheckAllExcept();
//                    buttonView.setChecked(isChecked);

//                    appStatusCallbackChanges.appSoftwareStatusChanges(AppsStatus.BETA);
                }

            }
        });

        RadioButton radioButton2 =(RadioButton)findViewById(R.id.radio_alpha);
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    androidCoreSettings.setAppsStatus(AppsStatus.ALPHA);
//                    uncheckAllExcept();
                    radioChecked.setChecked(false);
                    buttonView.setChecked(true);
                    radioChecked = (RadioButton) buttonView;
                    appStatusCallbackChanges.appSoftwareStatusChanges(AppsStatus.ALPHA);
                }

            }
        });

        RadioButton radioButton3 =(RadioButton)findViewById(R.id.radio_dev);
        radioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    androidCoreSettings.setAppsStatus(AppsStatus.DEV);
//                    uncheckAllExcept();
                    radioChecked.setChecked(false);
                    buttonView.setChecked(true);
                    radioChecked = (RadioButton) buttonView;
                    appStatusCallbackChanges.appSoftwareStatusChanges(AppsStatus.DEV);
                }

            }
        });

        switch (androidCoreSettings.getAppsStatus()){
            case RELEASE:
                radioChecked = radioButton;
                radioButton.setChecked(true);

                break;
            case BETA:
                radioChecked = radioButton1;
                radioButton1.setChecked(true);
                break;
            case ALPHA:
                radioChecked = radioButton2;
                radioButton2.setChecked(true);
                break;
            case DEV:
                radioChecked = radioButton3;
                radioButton3.setChecked(true);
                break;
        }


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.txt_release:
                    case R.id.linear_relese:
                        Toast.makeText(getContext(), "No apps in Release for now", Toast.LENGTH_SHORT).show();
                        //androidCoreSettings.setAppsStatus(AppsStatus.RELEASE);
                        break;
                    case R.id.txt_beta:
                    case R.id.linear_beta:
                        Toast.makeText(getContext(), "No apps in Beta for now", Toast.LENGTH_SHORT).show();
                        //androidCoreSettings.setAppsStatus(AppsStatus.BETA);
                        break;
                    case R.id.txt_alpha:
                    case R.id.linear_alpha:
                        androidCoreSettings.setAppsStatus(AppsStatus.ALPHA);
                        break;
                    case R.id.txt_dev:
                    case R.id.linear_develop:
                        androidCoreSettings.setAppsStatus(AppsStatus.DEV);
                        break;
                }
                uncheckAllExcept();
                appStatusCallbackChanges.appSoftwareStatusChanges(androidCoreSettings.getAppsStatus());
                try {
                    androidCoreModule.persistSettings(ApplicationConstants.SETTINGS_CORE, androidCoreSettings);
                } catch (CantPersistSettingsException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        };

        findViewById(R.id.linear_develop).setOnClickListener(onClickListener);
        findViewById(R.id.linear_alpha).setOnClickListener(onClickListener);
        findViewById(R.id.linear_beta).setOnClickListener(onClickListener);
        findViewById(R.id.linear_relese).setOnClickListener(onClickListener);
        findViewById(R.id.txt_release).setOnClickListener(onClickListener);
        findViewById(R.id.txt_beta).setOnClickListener(onClickListener);
        findViewById(R.id.txt_alpha).setOnClickListener(onClickListener);
        findViewById(R.id.txt_dev).setOnClickListener(onClickListener);


        ((FermatTextView)findViewById(R.id.txt_release)).setFont(FontType.CAVIAR_DREAMS);
        ((FermatTextView)findViewById(R.id.txt_beta)).setFont(FontType.CAVIAR_DREAMS);
        ((FermatTextView)findViewById(R.id.txt_alpha)).setFont(FontType.CAVIAR_DREAMS);
        ((FermatTextView)findViewById(R.id.txt_dev)).setFont(FontType.CAVIAR_DREAMS);
        ((FermatTextView)findViewById(R.id.txt_title_app_status)).setFont(FontType.CAVIAR_DREAMS_BOLD);



    }

    private void uncheckAllExcept(){
        for (AppsStatus status : AppsStatus.values()) {
            if(status!=androidCoreSettings.getAppsStatus()){
                switch (status){
                    case RELEASE:
                        ((RadioButton)findViewById(R.id.radio_release)).setChecked(false);
                        break;
                    case BETA:
                        ((RadioButton)findViewById(R.id.radio_beta)).setChecked(false);
                        break;
                    case ALPHA:
                        ((RadioButton)findViewById(R.id.radio_alpha)).setChecked(false);
                        break;
                    case DEV:
                        ((RadioButton)findViewById(R.id.radio_dev)).setChecked(false);
                        break;
                }
            }
        }
    }


}
