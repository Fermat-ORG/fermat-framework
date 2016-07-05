package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SettingsStockManagementMerchandisesAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;

/**
 * Created by Miguel Payarez (miguel_payarez@hotmail.com) on 7/5/16.
 */
public class SettingsFeeManagementFragment extends FermatWalletListFragment<CryptoBrokerWalletAssociatedSetting,ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>,ResourceProviderManager>  {

    // Constants
    private static final String TAG = "SettingsStockManagement";

    //DATA
    private int spreadValue = 0;
    private boolean automaticRestock = false;
    private List<CryptoBrokerWalletAssociatedSetting> associatedSettings = new ArrayList<>();
    private CryptoBrokerWalletSettingSpread spreadSettings;

    //UI
    private SettingsStockManagementMerchandisesAdapter merchandisesAdapter;
    private RadioGroup radioButtonGroup;
    private FermatTextView feeMinerAmount;
    private Spinner feeOrigin;

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;


    public static SettingsFeeManagementFragment newInstance() { return new SettingsFeeManagementFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get managers
        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_THIS_FRAGMENT, ex);
        }

        //Get associated Settings and Spread Settings
        try {
            associatedSettings = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
            spreadSettings = moduleManager.getCryptoBrokerWalletSpreadSetting("walletPublicKeyTest");

            if (spreadSettings != null) {
                spreadValue = (int) spreadSettings.getSpread();
                automaticRestock = spreadSettings.getRestockAutomatic();
            }
        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        configureToolbar();
        View layout = inflater.inflate(R.layout.cbw_settings_fee_management, container, false);


        radioButtonGroup = (RadioGroup)layout.findViewById(R.id.cbw_radio_button_group);
        feeMinerAmount=(FermatTextView) layout.findViewById(R.id.cbw_fee_miner_amount);



        final FermatTextView spreadTextView = (FermatTextView) layout.findViewById(R.id.cbw_spread_value_text);
        spreadTextView.setText(String.format("%1$s %%", spreadValue));


        radioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.cbw_radio_button_slow) {
                    feeMinerAmount.setText("Ha pulsado el botón 1");
                } else if (checkedId == R.id.cbw_radio_button_normal) {
                    feeMinerAmount.setText("Ha pulsado el botón 2");
                } else if (checkedId ==  R.id.cbw_radio_button_fast) {
                    feeMinerAmount.setText("Ha pulsado el botón 3");
                }

            }

        });

        final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingsAndGoBack();
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
            }
        });




        return layout;
    }




    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    private void saveSettingsAndGoBack() {

        try {
            CryptoBrokerWalletSettingSpread walletSetting = moduleManager.newEmptyCryptoBrokerWalletSetting();
            walletSetting.setId(null);
            walletSetting.setBrokerPublicKey(appSession.getAppPublicKey());
            walletSetting.setSpread(spreadValue);
            walletSetting.setRestockAutomatic(automaticRestock);
            moduleManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());

        } catch (FermatException ex) {
            Toast.makeText(SettingsFeeManagementFragment.this.getActivity(), "There was a problem saving your settings", Toast.LENGTH_SHORT).show();

            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
    }



    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.cbw_settings_stock_management;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return 0;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.cbw_selected_stock_wallets_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    public void onPostExecute(Object... result) {

    }

    @Override
    public void onErrorOccurred(Exception ex) {

    }


    @Override
    public List<CryptoBrokerWalletAssociatedSetting> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        try {
            return moduleManager.getCryptoBrokerWalletAssociatedSettings("walletPublicKeyTest");
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public FermatAdapter getAdapter() {
        return null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }
}