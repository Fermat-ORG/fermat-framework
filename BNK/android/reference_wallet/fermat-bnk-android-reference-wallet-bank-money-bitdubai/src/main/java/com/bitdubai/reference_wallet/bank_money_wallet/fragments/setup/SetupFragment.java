package com.bitdubai.reference_wallet.bank_money_wallet.fragments.setup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.session.BankMoneyWalletSession;

/**
 * Created by memo on 13/01/16.
 */
public class SetupFragment extends AbstractFermatFragment implements View.OnClickListener {

    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    EditText bankName;

    ImageView okBtn;

    public static SetupFragment newInstance(){
        return new SetupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            moduleManager = ((BankMoneyWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
        if(moduleManager.getBankingWallet().getBankName()!=null){
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME,appSession.getAppPublicKey());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.bw_setup,container,false);
        bankName = (EditText) layout.findViewById(R.id.bank_name_edittext);
        okBtn = (ImageView) layout.findViewById(R.id.bw_setup_ok_btn);

        return layout;
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bw_setup_ok_btn){
            moduleManager.getBankingWallet().createBankName(bankName.getText().toString());
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_HOME, appSession.getAppPublicKey());
        }
    }

}
