package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;

/**
 * Created by mati on 2015.11.27..
 */
public class ContactsTutorialPart1V2 extends FermatDialog<ReferenceAppFermatSession<LossProtectedWallet>,SubAppResourcesProviderManager> implements View.OnClickListener{

    private final Activity activity;
    private FermatButton add_fermat_user;
    private FermatButton add_extra_user;
    private CheckBox checkbox_not_show;
    private boolean checkButton;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public ContactsTutorialPart1V2(Activity activity, ReferenceAppFermatSession<LossProtectedWallet> fermatSession, SubAppResourcesProviderManager resources,boolean checkButton) {
        super(activity, fermatSession, resources);
        this.activity = activity;
        this.checkButton = checkButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        add_fermat_user =(FermatButton) findViewById(R.id.add_fermat_user);
        add_extra_user = (FermatButton) findViewById(R.id.add_extra_user);
        checkbox_not_show = (CheckBox) findViewById(R.id.checkbox_not_show);
        checkbox_not_show.setChecked(checkButton);

        add_fermat_user.setOnClickListener(this);
        add_extra_user.setOnClickListener(this);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.loss_contacts_tutorial_part1_v2;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.add_fermat_user){
            try {
                saveSettings();
                changeActivity(Activities.CCP_BITCOIN_LOSS_PROTECTED_WALLET_ADD_CONNECTION_ACTIVITY);
                dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(id == R.id.add_extra_user){
            getSession().setData(SessionConstant.CREATE_EXTRA_USER,Boolean.TRUE);
            saveSettings();
            dismiss();
        }
    }

    private void changeActivity(Activities ccpBitcoinWalletAddConnectionActivity) {
        ((FermatScreenSwapper)activity).changeActivity(ccpBitcoinWalletAddConnectionActivity.getCode(),getSession().getAppPublicKey());
    }


    private void saveSettings(){
        if(checkButton == checkbox_not_show.isChecked()  || checkButton == !checkbox_not_show.isChecked())
            try {
                LossProtectedWallet lossProtectedWalletManager = getSession().getModuleManager();
                LossProtectedWalletSettings bitcoinWalletSettings = lossProtectedWalletManager.loadAndGetSettings(getSession().getAppPublicKey());
                bitcoinWalletSettings.setIsContactsHelpEnabled((checkbox_not_show.isChecked()) ? false : true);

                lossProtectedWalletManager.persistSettings(getSession().getAppPublicKey(),bitcoinWalletSettings);

            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                e.printStackTrace();
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onBackPressed() {
        saveSettings();
        super.onBackPressed();
    }
}
