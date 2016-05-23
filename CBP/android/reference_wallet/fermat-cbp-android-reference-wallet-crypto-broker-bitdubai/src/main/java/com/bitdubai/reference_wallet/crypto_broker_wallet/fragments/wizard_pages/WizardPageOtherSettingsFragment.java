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
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;


/**
 * Created by Lozadaa on 20/01/16.
 */
public class WizardPageOtherSettingsFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager> {
    private static final String TAG = "WizardPageOtherSettings";
    private int spreadValue;
    private boolean automaticRestock;


    // UI
    boolean hideHelperDialogs = false;
    private View emptyView;
    private RecyclerView recyclerView;

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
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
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            //Delete potential previous configurations made by this wizard page
            //So that they can be reconfigured cleanly
            moduleManager.clearWalletSetting(appSession.getAppPublicKey());

            //If PRESENTATION_SCREEN_ENABLED == true, then user does not want to see more help dialogs inside the wizard
            Object aux = appSession.getData(PresentationDialog.PRESENTATION_SCREEN_ENABLED);
            if (aux != null && aux instanceof Boolean)
                hideHelperDialogs = (boolean) aux;

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

        if (!hideHelperDialogs) {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.banner_crypto_broker)
                    .setIconRes(R.drawable.crypto_broker)
                    .setSubTitle(R.string.cbw_wizard_other_settings_dialog_sub_title)
                    .setBody(R.string.cbw_wizard_other_settings_dialog_body)
                    .setCheckboxText(R.string.cbw_wizard_not_show_text)
                    .build();
            presentationDialog.show();
        }


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
            walletSetting = moduleManager.newEmptyCryptoBrokerWalletSetting();
            walletSetting.setId(null);
            walletSetting.setBrokerPublicKey(appSession.getAppPublicKey());
            walletSetting.setSpread(spreadValue);
            walletSetting.setRestockAutomatic(automaticRestock);
            moduleManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());
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