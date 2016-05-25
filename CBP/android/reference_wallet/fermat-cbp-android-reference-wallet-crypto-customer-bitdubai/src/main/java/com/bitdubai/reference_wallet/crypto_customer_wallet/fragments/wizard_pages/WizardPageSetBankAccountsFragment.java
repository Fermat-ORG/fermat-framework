package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.wizard_pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletPreferenceSettings;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.BankAccountsAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.BankAccountData;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetBankAccountsFragment extends AbstractFermatFragment<CryptoCustomerWalletSession, ResourceProviderManager>
        implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<BankAccountNumber> {

    // Constants
    private static final String TAG = "WizardPageSetBank";

    // Data
    private List<BankAccountNumber> bankAccountList;

    // UI
    boolean hideHelperDialogs = false;
    private RecyclerView recyclerView;
    private BankAccountsAdapter adapter;
    private View emptyView;

    // Fermat Managers
    private CryptoCustomerWalletModuleManager moduleManager;
    private ErrorManager errorManager;


    public static WizardPageSetBankAccountsFragment newInstance() {
        return new WizardPageSetBankAccountsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            //Delete potential previous configurations made by this wizard page
            //So that they can be reconfigured cleanly
            moduleManager.clearAllBankAccounts();

            //If PRESENTATION_SCREEN_ENABLED == true, then user does not want to see more help dialogs inside the wizard
            Object aux = appSession.getData(PresentationDialog.PRESENTATION_SCREEN_ENABLED);
            if(aux != null && aux instanceof Boolean)
                hideHelperDialogs = (boolean) aux;

            Object data = appSession.getData(CryptoCustomerWalletSession.BANK_ACCOUNT_LIST);
            if (data == null) {
                bankAccountList = new ArrayList<>();
                appSession.setData(CryptoCustomerWalletSession.BANK_ACCOUNT_LIST, bankAccountList);
            } else
                bankAccountList = (List<BankAccountNumber>) data;

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(!hideHelperDialogs) {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.cbp_banner_crypto_customer_wallet)
                    .setIconRes(R.drawable.cbp_crypto_customer)
                    .setSubTitle(R.string.ccw_wizard_accounts_dialog_sub_title)
                    .setBody(R.string.ccw_wizard_accounts_dialog_body)
                    .setCheckboxText(R.string.ccw_wizard_not_show_text)
                    .build();
            presentationDialog.show();
        }

        View layout = inflater.inflate(R.layout.ccw_wizard_step_set_bank_accounts, container, false);

        configureToolbar();

        adapter = new BankAccountsAdapter(getActivity(), bankAccountList);
        adapter.setDeleteButtonListener(this);

        recyclerView = (RecyclerView) layout.findViewById(R.id.ccw_selected_bank_accounts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        emptyView = layout.findViewById(R.id.ccw_selected_bank_accounts_empty_view);

        final View addLocationButton = layout.findViewById(R.id.ccw_add_bank_account_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CREATE_NEW_BANK_ACCOUNT_IN_WIZARD, appSession.getAppPublicKey());
            }
        });

        final View nextStepButton = layout.findViewById(R.id.ccw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingAndGoNextStep();
            }
        });

        showOrHideRecyclerView();

        return layout;
    }



    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));

        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }


    @Override
    public void deleteButtonClicked(BankAccountNumber data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.ccw_delete_bank_account_dialog_title).setMessage(R.string.ccw_delete_bank_account_dialog_msg);
        builder.setPositiveButton(R.string.ccw_delete_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bankAccountList.remove(position);
                adapter.changeDataSet(bankAccountList);
                showOrHideRecyclerView();
            }
        });
        builder.setNegativeButton(R.string.ccw_cancel_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();


    }

    private void saveSettingAndGoNextStep() {

        try {
            for (BankAccountNumber bankAccount : bankAccountList) {
                BankAccountData bankAccountData = (BankAccountData) bankAccount;
                moduleManager.createNewBankAccount(bankAccountData.toString(), bankAccount.getCurrencyType());
            }


            //Obtain walletSettings and persist isWalletConfigured = true
            final CryptoCustomerWalletModuleManager moduleManager = appSession.getModuleManager();

            CryptoCustomerWalletPreferenceSettings walletSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            walletSettings.setIsWalletConfigured(true);
            moduleManager.persistSettings(appSession.getAppPublicKey(), walletSettings);


        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        }

        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME, appSession.getAppPublicKey());
    }

    private void showOrHideRecyclerView() {
        if (bankAccountList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
