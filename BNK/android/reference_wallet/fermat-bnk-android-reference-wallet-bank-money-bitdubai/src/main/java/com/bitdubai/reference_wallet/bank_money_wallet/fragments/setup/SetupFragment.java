package com.bitdubai.reference_wallet.bank_money_wallet.fragments.setup;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.BankMoneyWalletPreferenceSettings;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;

import static android.widget.Toast.makeText;


/**
 * Created by memo on 13/01/16.
 */
public class SetupFragment extends AbstractFermatFragment<ReferenceAppFermatSession<BankMoneyWalletModuleManager>, ResourceProviderManager> implements View.OnClickListener {

    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    EditText bankName;

    ImageView okBtn;
    LinearLayout setupContainer;

    public static SetupFragment newInstance() {
        return new SetupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
        //Obtain walletSettings or create new wallet settings if first time opening wallet
        BankMoneyWalletPreferenceSettings walletSettings = null;
        try {
            walletSettings = this.moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            walletSettings = new BankMoneyWalletPreferenceSettings();
        }

        walletSettings.setIsPresentationHelpEnabled(true);
        try {
            moduleManager.persistSettings(appSession.getAppPublicKey(), walletSettings);
        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.bw_setup, container, false);
        bankName = (EditText) layout.findViewById(R.id.bank_name_edittext);
        setupContainer = (LinearLayout) layout.findViewById(R.id.setup_container);
        okBtn = (ImageView) layout.findViewById(R.id.bw_setup_ok_btn);
        okBtn.setOnClickListener(this);

        /*if(moduleManager.getBankingWallet().getBankName()!=null){
            getRuntimeManager().changeStartActivity(0);
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME,appSession.getAppPublicKey());
        }*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //If wallet already exists, go directly to wallet
                if (moduleManager.getBankName() != null) {
                    changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME, appSession.getAppPublicKey());
                } else {  //otherwise, fade in setup page
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                    setupContainer.setVisibility(View.VISIBLE);
                    setupContainer.startAnimation(fadeInAnimation);
                }
            }
        }, 800);
        //setupContainer.setVisibility(View.VISIBLE);
        return layout;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bw_setup_ok_btn) {
            if(!bankName.getText().toString().trim().isEmpty()) {
                moduleManager.createBankName(bankName.getText().toString());
                changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME, appSession.getAppPublicKey());
            } else {
                makeText(getActivity(), "Enter a Name valid Bank", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
