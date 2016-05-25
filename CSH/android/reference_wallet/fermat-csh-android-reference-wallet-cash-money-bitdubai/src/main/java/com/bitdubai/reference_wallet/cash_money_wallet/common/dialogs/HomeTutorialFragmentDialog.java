package com.bitdubai.reference_wallet.cash_money_wallet.common.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.CashMoneyWalletPreferenceSettings;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

/**
 * Created by Alejandro Bicelis on 12/15/2015.
 */

public class HomeTutorialFragmentDialog extends Dialog implements
        View.OnClickListener {

    public Activity activity;
    public Dialog d;

    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;
    private CashMoneyWalletSession walletSession;
    private Resources resources;

    /**
     * Data
     */
    private CashMoneyWalletPreferenceSettings walletSettings;


    /**
     *  UI components
     */
    CheckBox dontShowCheckbox;
    FermatButton dismissButton;

    public HomeTutorialFragmentDialog(Activity a, CashMoneyWalletSession cashMoneyWalletSession, Resources resources) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.walletSession = cashMoneyWalletSession;
        this.resources = resources;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupScreenComponents();

    }

    private void setupScreenComponents(){

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.csh_tutorial_home_dialog);

            dontShowCheckbox = (CheckBox) findViewById(R.id.csh_dont_show_again_checkbox);
            dismissButton = (FermatButton) findViewById(R.id.csh_dismiss_button);

            dismissButton.setOnClickListener(this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }




    @Override
    public void onClick(View v) {
        int i = v.getId();

        if( i == R.id.csh_dismiss_button){
            try{
                final CashMoneyWalletModuleManager moduleManager = walletSession.getModuleManager();

                walletSettings = moduleManager.loadAndGetSettings(walletSession.getAppPublicKey());
                walletSettings.setIsHomeTutorialDialogEnabled(!dontShowCheckbox.isChecked());
                moduleManager.persistSettings(walletSession.getAppPublicKey(), walletSettings);
            } catch (Exception ignore){}

            dismiss();
        }
    }


}