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
import android.widget.ProgressBar;
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
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
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
public class SetttingsStockManagementFragment extends FermatWalletListFragment<CryptoBrokerWalletAssociatedSetting, ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, ResourceProviderManager> implements FermatListItemListeners<CryptoBrokerWalletAssociatedSetting>, DialogInterface.OnDismissListener, CBPBroadcasterConstants {

    // Constants
    private static final String TAG = "SettingsStockManagement";

    //DATA
    private int spreadValue = 0;
    private boolean automaticRestock = false;
    private List<CryptoBrokerWalletAssociatedSetting> associatedSettings = new ArrayList<>();
    private CryptoBrokerWalletSettingSpread spreadSettings;
    private List<Currency> merchandises = new ArrayList<>();

    //UI
    private ProgressBar processingProgressBar;
    private FermatTextView emptyView;
    private SettingsStockManagementMerchandisesAdapter merchandisesAdapter;
    private RecyclerView merchandisesRecyclerView;

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;


    public static SetttingsStockManagementFragment newInstance() {
        return new SetttingsStockManagementFragment();
    }

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

        for (CryptoBrokerWalletAssociatedSetting x : associatedSettings) {
            if (!merchandises.contains(x.getMerchandise())) {
                merchandises.add(x.getMerchandise());
            }
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        configureToolbar();

        emptyView = (FermatTextView) layout.findViewById(R.id.cbw_selected_stock_wallets_empty_view);
        processingProgressBar = (ProgressBar) layout.findViewById(R.id.cbw_processing_progress_bar);


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
                saveSettingsAndGoBack();
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
            }
        });

        merchandisesAdapter = new SettingsStockManagementMerchandisesAdapter(getActivity(), merchandises, moduleManager);
        //merchandisesAdapter.setFermatListEventListener(this);

        merchandisesRecyclerView = (RecyclerView) layout.findViewById(R.id.cbw_settings_current_merchandises);
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

    private void saveSettingsAndGoBack() {

        try {
            CryptoBrokerWalletSettingSpread walletSetting = moduleManager.newEmptyCryptoBrokerWalletSetting();
            walletSetting.setId(null);
            walletSetting.setBrokerPublicKey(appSession.getAppPublicKey());
            walletSetting.setSpread(spreadValue);
            walletSetting.setRestockAutomatic(automaticRestock);
            moduleManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());

        } catch (FermatException ex) {
            Toast.makeText(SetttingsStockManagementFragment.this.getActivity(), "There was a problem saving your settings", Toast.LENGTH_SHORT).show();

            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

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

        //Launch Restock/Destock dialog
        final CreateRestockDestockFragmentDialog dialog = new CreateRestockDestockFragmentDialog(getActivity(), (ReferenceAppFermatSession) appSession, data);
        dialog.setOnDismissListener(this);
        dialog.show();
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerWalletAssociatedSetting data, int position) {
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        //Show progressbar on restock/destock action
        Object data = appSession.getData(CreateRestockDestockFragmentDialog.TRANSACTION_APPLIED);
        if (data != null) {
            appSession.removeData(CreateRestockDestockFragmentDialog.TRANSACTION_APPLIED);
            processingProgressBar.setVisibility(View.VISIBLE);
        }

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
        isRefreshing = false;
        if (isAttached) {
            if (result != null && result.length > 0) {
                associatedSettings = (ArrayList) result[0];
                if (adapter != null) {
                    adapter.changeDataSet(associatedSettings);
                }

                if (merchandisesAdapter != null) {
                    merchandisesAdapter.changeDataSet(merchandises);

                    //This line is a hack, needed (don't know why) so that the merchandises get refreshed.
                    merchandisesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }
            }
        }
        invalidate();
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
            case CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW_ERROR:
                Toast.makeText(this.getActivity(), "There has been an error processing your request.", Toast.LENGTH_SHORT).show();
                processingProgressBar.setVisibility(View.INVISIBLE);
                onRefresh();
                break;

            case CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW:
                Toast.makeText(this.getActivity(), "Transaction completed.", Toast.LENGTH_SHORT).show();
                processingProgressBar.setVisibility(View.INVISIBLE);
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
