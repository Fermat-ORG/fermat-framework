package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
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
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SettingsStockManagementMerchandisesAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.StockDestockAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs.CreateRestockDestockFragmentDialog;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;


/**
 * Created by nelson on 22/12/15.
 */
public class SetttingsStockManagementFragment extends FermatWalletListFragment<CryptoBrokerWalletAssociatedSetting,ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>,ResourceProviderManager> implements FermatListItemListeners<CryptoBrokerWalletAssociatedSetting>, DialogInterface.OnDismissListener, CBPBroadcasterConstants {

    // Constants
    private static final String TAG = "SettingsStockManagement";

    private int spreadValue;
    private boolean automaticRestock;
    private List<CryptoBrokerWalletAssociatedSetting> associatedSettings;
    private CryptoBrokerWalletSettingSpread spreadSettings;
    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private FermatTextView emptyView;
    private SettingsStockManagementMerchandisesAdapter merchandisesAdapter;

    public static SetttingsStockManagementFragment newInstance() {
        return new SetttingsStockManagementFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spreadValue = 0;
        automaticRestock = false;
        associatedSettings = new ArrayList<>();

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_THIS_FRAGMENT, ex);
        }
        try {
            System.out.println("associatedSettings!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            associatedSettings = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
            System.out.println("associatedSettings [" + associatedSettings.size() + "]!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            spreadSettings = moduleManager.getCryptoBrokerWalletSpreadSetting("walletPublicKeyTest");
        } catch (FermatException ex) {
            Toast.makeText(SetttingsStockManagementFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

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
    protected void initViews(View layout) {
        super.initViews(layout);
        configureToolbar();
        emptyView = (FermatTextView) layout.findViewById(R.id.cbw_selected_stock_wallets_empty_view);
        if (spreadSettings != null) {
            spreadValue = (int) spreadSettings.getSpread();
            automaticRestock = spreadSettings.getRestockAutomatic();
        }

        final FermatTextView spreadTextView = (FermatTextView) layout.findViewById(R.id.cbw_spread_value_text);
        spreadTextView.setText(String.format("%1$s %%", spreadValue));

        final FermatCheckBox automaticRestockCheckBox = (FermatCheckBox) layout.findViewById(R.id.cbw_automatic_restock_check_box);
        automaticRestockCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                automaticRestock = automaticRestockCheckBox.isChecked();
            }
        });
        automaticRestockCheckBox.setChecked(automaticRestock);
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
        spreadSeekBar.setProgress(spreadValue);

        final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingAndGoNextStep();
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
            }
        });
        merchandisesAdapter = new SettingsStockManagementMerchandisesAdapter(getActivity(), associatedSettings, moduleManager);

        merchandisesAdapter.setFermatListEventListener(this);
        RecyclerView merchandisesRecyclerView = (RecyclerView) layout.findViewById(R.id.cbw_settings_current_merchandises);
        merchandisesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        merchandisesRecyclerView.setAdapter(merchandisesAdapter);

        showOrHideNoSelectedWalletsView();
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

    private void saveSettingAndGoNextStep() {

        try {
            CryptoBrokerWalletSettingSpread walletSetting = moduleManager.newEmptyCryptoBrokerWalletSetting();
            walletSetting.setId(null);
            walletSetting.setBrokerPublicKey(appSession.getAppPublicKey());
            walletSetting.setSpread(spreadValue);
            walletSetting.setRestockAutomatic(automaticRestock);
            moduleManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());

        } catch (FermatException ex) {
            Toast.makeText(SetttingsStockManagementFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        //TODO: agregar esta instruccion en el try/catch cuando funcione el saveSettingSpread
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
    }

    private void showOrHideNoSelectedWalletsView() {
        if (associatedSettings.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void launchCreateTransactionDialog(CryptoBrokerWalletAssociatedSetting data) {
        final CreateRestockDestockFragmentDialog dialog = new CreateRestockDestockFragmentDialog(getActivity(), (ReferenceAppFermatSession) appSession, data);
        dialog.setOnDismissListener(this);
        dialog.show();
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new StockDestockAdapter(getActivity(), associatedSettings, moduleManager);
            adapter.setFermatListEventListener(this);
        }
        return adapter;
    }

    @Override
    public void onItemClickListener(CryptoBrokerWalletAssociatedSetting data, int position) {
        launchCreateTransactionDialog(data);
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerWalletAssociatedSetting data, int position) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        System.out.println("*************ONDISMISS STOCK DIALOG***********************");
        onRefresh();
    }

    @Override
    protected boolean hasMenu() {
        return false;
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
        isRefreshing = false;
        if (isAttached) {
            if (result != null && result.length > 0) {
                associatedSettings = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(associatedSettings);
                if (merchandisesAdapter != null) {
                    merchandisesAdapter.changeDataSet(associatedSettings);
                }
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {

    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code) {
            case CBW_OPERATION_DEBIT_OR_CREDIT_UPDATE_VIEW:
                onRefresh();
                break;
        }
    }

    @Override
    public List<CryptoBrokerWalletAssociatedSetting> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        try {
            return moduleManager.getCryptoBrokerWalletAssociatedSettings("walletPublicKeyTest");
        } catch (Exception e) {
            return null;
        }

    }

}
