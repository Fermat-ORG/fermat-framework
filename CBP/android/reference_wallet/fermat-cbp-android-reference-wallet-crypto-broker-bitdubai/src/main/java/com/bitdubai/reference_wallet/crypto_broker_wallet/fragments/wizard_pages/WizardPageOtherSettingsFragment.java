package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SeekBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantNewEmptyCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.LocationsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.OtherSettingsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

/**
 * Created by Lozadaa on 20/01/16.
 */
public class WizardPageOtherSettingsFragment extends AbstractFermatFragment {

     // Constants
        private static final String TAG = "WizarPageOtherSettings";
    private int spreadValue;
    private boolean automaticRestock;


        // UI
      //  private RecyclerView recyclerView;
        private LocationsAdapter adapter;
        private View emptyView;

        // Fermat Managers
        private CryptoBrokerWalletManager walletManager;
        private ErrorManager errorManager;
     private RecyclerView recyclerView;


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


            final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
            nextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        saveSettingAndGoNextStep();
                    } catch (CryptoBrokerWalletNotFoundException e) {
                        e.printStackTrace();
                    } catch (CantGetCryptoBrokerWalletSettingException e) {
                        e.printStackTrace();
                    } catch (CantSaveCryptoBrokerWalletSettingException e) {
                        e.printStackTrace();
                    }
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

            showOrHideRecyclerView();

            return layout;
        }



        private void saveSettingAndGoNextStep() throws CryptoBrokerWalletNotFoundException, CantGetCryptoBrokerWalletSettingException, CantSaveCryptoBrokerWalletSettingException {
            CryptoBrokerWalletSettingSpread walletSetting = null;
            try {
                walletSetting = walletManager.newEmptyCryptoBrokerWalletSetting();
            } catch (CantNewEmptyCryptoBrokerWalletSettingException e) {
                e.printStackTrace();
            }
            walletSetting.setId(null);
            walletSetting.setBrokerPublicKey(appSession.getAppPublicKey());
            walletSetting.setSpread(spreadValue);
            walletSetting.setRestockAutomatic(automaticRestock);
            walletManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());


        }

        private void showOrHideRecyclerView() {

          //      emptyView.setVisibility(View.VISIBLE);
            //    recyclerView.setVisibility(View.GONE);

        }
    }


