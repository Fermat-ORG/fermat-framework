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

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SettingsStockManagementMerchandisesAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.StockDestockAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs.CreateRestockDestockFragmentDialog;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nelson on 22/12/15.
 */
public class SetttingsStockManagementFragment extends FermatWalletListFragment<CryptoBrokerWalletAssociatedSetting> implements FermatListItemListeners<CryptoBrokerWalletAssociatedSetting>, DialogInterface.OnDismissListener,CBPBroadcasterConstants {

    // Constants
    private static final String TAG = "SettingsStockManagement";

    private int spreadValue;
    private boolean automaticRestock;
    private List<InstalledWallet> stockWallets;
    private Map<String, FiatCurrency> bankCurrencies;
    private Map<String, String> bankAccounts;
    private CreateRestockDestockFragmentDialog dialog;
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
        stockWallets = new ArrayList<>();
        bankCurrencies = new HashMap<>();
        bankAccounts = new HashMap<>();

        try {
            moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
        try {
            System.out.println("associatedSettings!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            associatedSettings = getMoreDataAsync(FermatRefreshTypes.NEW,0);
            System.out.println("associatedSettings ["+ associatedSettings.size()+"]!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            spreadSettings = moduleManager.getCryptoBrokerWalletSpreadSetting("walletPublicKeyTest");
        } catch (FermatException ex) {
            Toast.makeText(SetttingsStockManagementFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }

    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        configureToolbar();
        emptyView = (FermatTextView) layout.findViewById(R.id.cbw_selected_stock_wallets_empty_view);
        if(spreadSettings != null){
            spreadValue = (int)spreadSettings.getSpread();
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
        merchandisesAdapter = new SettingsStockManagementMerchandisesAdapter(getActivity(),associatedSettings,moduleManager);

        merchandisesAdapter.setFermatListEventListener(this);
        RecyclerView merchandisesRecyclerView = (RecyclerView) layout.findViewById(R.id.cbw_settings_current_merchandises);
        merchandisesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
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

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View layout = inflater.inflate(R.layout.cbw_settings_stock_management, container, false);

        emptyView = (FermatTextView) layout.findViewById(R.id.cbw_selected_stock_wallets_empty_view);

        final FermatTextView spreadTextView = (FermatTextView) layout.findViewById(R.id.cbw_spread_value_text);
        spreadTextView.setText(String.format("%1$s %%", spreadValue));

        /*final View cryptoButton = layout.findViewById(R.id.cbw_select_crypto_wallets);
        cryptoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletsDialog(Platforms.CRYPTO_CURRENCY_PLATFORM);
            }
        });

        final View bankButton = layout.findViewById(R.id.cbw_select_bank_wallets);
        bankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletsDialog(Platforms.BANKING_PLATFORM);
            }
        });

        final View cashButton = layout.findViewById(R.id.cbw_select_cash_wallets);
        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletsDialog(Platforms.CASH_PLATFORM);
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


        final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveSettingAndGoNextStep();
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
            }
        });
        showOrHideNoSelectedWalletsView();
        return layout;
    }*/

    private void showWalletsDialog(final Platforms platform) {
        try {
            List<InstalledWallet> installedWallets = moduleManager.getInstallWallets();
            List<InstalledWallet> filteredList = new ArrayList<>();

            for (InstalledWallet wallet : installedWallets) {
                if (wallet.getPlatform().equals(platform))
                    filteredList.add(wallet);
            }

            final SimpleListDialogFragment<InstalledWallet> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("Select a Wallet", filteredList);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<InstalledWallet>() {
                @Override
                public void onItemSelected(InstalledWallet selectedItem) {
                    if (!platform.equals(Platforms.BANKING_PLATFORM)) {

                        if (!containWallet(selectedItem)) {
                            /*stockWallets.add(selectedItem);
                            adapter.changeDataSet(stockWallets);*/
                            showOrHideNoSelectedWalletsView();
                        }

                    } else {
                        showBankAccountsDialog(selectedItem);
                    }
                }
            });

            dialogFragment.show(getFragmentManager(), "WalletsDialog");

        } catch (CantListWalletsException ex) {
            Toast.makeText(SetttingsStockManagementFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }

    private void showBankAccountsDialog(final InstalledWallet selectedWallet) {
        try {
            List<BankAccountNumber> accounts = moduleManager.getAccounts(selectedWallet.getWalletPublicKey());

            SimpleListDialogFragment<BankAccountNumber> accountsDialog = new SimpleListDialogFragment<>();
            accountsDialog.configure("Select an Account", accounts);
            accountsDialog.setListener(new SimpleListDialogFragment.ItemSelectedListener<BankAccountNumber>() {
                @Override
                public void onItemSelected(BankAccountNumber selectedAccount) {

                    FiatCurrency currency = selectedAccount.getCurrencyType();
                    String account = selectedAccount.getAccount();

                    bankCurrencies.put(selectedWallet.getWalletPublicKey(), currency);
                    bankAccounts.put(selectedWallet.getWalletPublicKey(), account);

                    if (!containWallet(selectedWallet)) {
                        /*stockWallets.add(selectedWallet);
                        adapter.changeDataSet(stockWallets);*/
                        showOrHideNoSelectedWalletsView();
                    }
                }
            });

            accountsDialog.show(getFragmentManager(), "accountsDialog");

        } catch (FermatException ex) {
            Toast.makeText(SetttingsStockManagementFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }

    private void saveSettingAndGoNextStep() {

        try {
            CryptoBrokerWalletSettingSpread walletSetting = moduleManager.newEmptyCryptoBrokerWalletSetting();
            walletSetting.setId(null);
            walletSetting.setBrokerPublicKey(appSession.getAppPublicKey());
            walletSetting.setSpread(spreadValue);
            walletSetting.setRestockAutomatic(automaticRestock);
            moduleManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());


            /*for (InstalledWallet wallet : stockWallets) {
                String walletPublicKey = wallet.getWalletPublicKey();
                Platforms platform = wallet.getPlatform();

                CryptoBrokerWalletAssociatedSetting associatedSetting = moduleManager.newEmptyCryptoBrokerWalletAssociatedSetting();
                associatedSetting.setBrokerPublicKey(appSession.getAppPublicKey());
                associatedSetting.setId(UUID.randomUUID());
                associatedSetting.setWalletPublicKey(walletPublicKey);
                associatedSetting.setPlatform(platform);

                if (platform.equals(Platforms.BANKING_PLATFORM)) {
                    associatedSetting.setMoneyType(MoneyType.BANK);
                    associatedSetting.setMerchandise(bankCurrencies.get(walletPublicKey));
                    associatedSetting.setBankAccount(bankAccounts.get(walletPublicKey));

                } else if (platform.equals(Platforms.CRYPTO_CURRENCY_PLATFORM)) {
                    associatedSetting.setMoneyType(MoneyType.CRYPTO);
                    associatedSetting.setMerchandise(wallet.getCryptoCurrency());

                } else {
                    FiatCurrency cashCurrency = moduleManager.getCashCurrency(walletPublicKey);
                    associatedSetting.setMerchandise(cashCurrency);
                    associatedSetting.setMoneyType(MoneyType.CASH_ON_HAND);
                }

                moduleManager.saveWalletSettingAssociated(associatedSetting, appSession.getAppPublicKey());
            }*/

        } catch (FermatException ex) {
            Toast.makeText(SetttingsStockManagementFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }

        //TODO: agregar esta instruccion en el try/catch cuando funcione el saveSettingSpread
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
    }

    /*private void getSettings(){
        try {
            List<CryptoBrokerWalletAssociatedSetting> associatedSettings = moduleManager.getCryptoBrokerWalletAssociatedSettings(this.appSession.getAppPublicKey());

            for(CryptoBrokerWalletAssociatedSetting setting:associatedSettings){
                InstalledWallet installedWallet;
                setting.getPlatform();
                setting.get
                stockWallets.add();
            }

        }catch (FermatException ex) {
            Toast.makeText(SetttingsStockManagementFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }*/

    /*@Override
    public void checkedChanged(InstalledWallet data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.cbw_delete_wallet_dialog_title).setMessage(R.string.cbw_delete_wallet_dialog_msg);
        builder.setPositiveButton(R.string.cbw_delete_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                stockWallets.remove(position);
                adapter.changeDataSet(stockWallets);
                showOrHideNoSelectedWalletsView();
            }
        });
        builder.setNegativeButton(R.string.cbw_cancel_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();
    }*/

    private void showOrHideNoSelectedWalletsView() {
        if (associatedSettings.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private boolean containWallet(InstalledWallet selectedWallet) {
        if (stockWallets.isEmpty())
            return false;

        for (InstalledWallet wallet : stockWallets) {
            String walletPublicKey = wallet.getWalletPublicKey();
            String selectedWalletPublicKey = selectedWallet.getWalletPublicKey();

            if (walletPublicKey.equals(selectedWalletPublicKey))
                return true;
        }

        return false;
    }

    private void launchCreateTransactionDialog(CryptoBrokerWalletAssociatedSetting data) {
        dialog = new CreateRestockDestockFragmentDialog(getActivity(), moduleManager, getResources(), data);
        dialog.setOnDismissListener(this);
        dialog.show();
    }

    @Override
    public FermatAdapter getAdapter() {
        if(adapter == null){
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
                if (merchandisesAdapter !=null){
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
        switch (code){
            case CBW_OPERATION_DEBIT_OR_CREDIT_UPDATE_VIEW:
                onRefresh();
                break;
        }
    }

    @Override
    public List<CryptoBrokerWalletAssociatedSetting> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        try{
            return moduleManager.getCryptoBrokerWalletAssociatedSettings("walletPublicKeyTest");
        }catch (Exception e){
            return null;
        }

    }

}
