package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

/**
 * Created by Lozadaa on 20/01/16.
*/
 public class WizardPageOtherSettingsFragment extends AbstractFermatFragment
 {
 private static final String TAG = "WizardPageOtherSettings";
 private int spreadValue;
 private boolean automaticRestock;


 // UI
 private View emptyView;
 private RecyclerView recyclerView;

 // Fermat Managers
 private CryptoBrokerWalletManager walletManager;
 private ErrorManager errorManager;



     public static WizardPageOtherSettingsFragment newInstance() {
        return new WizardPageOtherSettingsFragment();
     }

 @Override
     public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       spreadValue = 0;
       automaticRestock = false;
       try {
         CryptoBrokerWalletModuleManager moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
         walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
         errorManager = appSession.getErrorManager();

       } catch (FermatException ex) {
         Log.e(TAG, ex.getMessage(), ex);
       }
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.cbw_wizard_step_other_settings, container, false);

        final FermatTextView spreadTextView = (FermatTextView) layout.findViewById(R.id.cbw_spread_value_text);
        spreadTextView.setText(String.format("%1$s %%", spreadValue));

         PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                 .setBody("Custom text support for dialog in the wizard Merchandises help")
                 .setSubTitle("Subtitle text of Merchandises dialog help")
                 .setTextFooter("Text footer Merchandises dialog help")
                 .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                 .setBannerRes(R.drawable.banner_crypto_broker)
                 .setIconRes(R.drawable.crypto_broker)
                 .build();

         presentationDialog.show();
        final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         saveSettingAndGoNextStep();

        }
        });
        final FermatCheckBox automaticRestockCheckBox = (FermatCheckBox) layout.findViewById(R.id.cbw_automatic_restock_check_box);
        automaticRestockCheckBox.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        automaticRestock = automaticRestockCheckBox.isChecked();
        }
        });

        final SeekBar spreadSeekBar = (SeekBar) layout.findViewById(R.id.cbw_spread_value_seek_bar);
         spreadSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

          @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
           spreadValue = progress;
           spreadTextView.setText(String.format("%1$s %%", spreadValue));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

     @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            });


     return layout;
     }



     private void saveSettingAndGoNextStep() {
     CryptoBrokerWalletSettingSpread walletSetting = null;
        try {
           walletSetting = walletManager.newEmptyCryptoBrokerWalletSetting();
            walletSetting.setId(null);
            walletSetting.setBrokerPublicKey(appSession.getAppPublicKey());
            walletSetting.setSpread(spreadValue);
            walletSetting.setRestockAutomatic(automaticRestock);
            walletManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());
            appSession.setData(CryptoBrokerWalletSession.CONFIGURED_DATA, true);
            // TODO Solo para testing, eliminar despues
            changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());
           } catch (FermatException ex) {
            Toast.makeText(WizardPageOtherSettingsFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
             errorManager.reportUnexpectedWalletException(
                     Wallets.CBP_CRYPTO_BROKER_WALLET,
                     UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                     ex);
            }
        }


     }


 }