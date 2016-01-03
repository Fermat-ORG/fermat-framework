package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.wizard_pages;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.BankAccountsAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.LocationsAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetBankAccountsFragment extends AbstractFermatFragment
        implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<BankAccountNumber> {

    // Constants
    private static final String TAG = "WizardPageSetBank";

    // Data
    private List<BankAccountNumber> bankAccountList;

    // UI
    private RecyclerView recyclerView;
    private BankAccountsAdapter adapter;
    private View emptyView;

    // Fermat Managers
    private CryptoCustomerWalletManager walletManager;
    private ErrorManager errorManager;


    public static WizardPageSetLocationsFragment newInstance() {
        return new WizardPageSetLocationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CryptoCustomerWalletModuleManager moduleManager = ((CryptoCustomerWalletSession) appSession).getModuleManager();
            walletManager = moduleManager.getCryptoCustomerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

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

        View layout = inflater.inflate(R.layout.ccw_wizard_step_set_bank_accounts, container, false);

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

    @Override
    public void deleteButtonClicked(BankAccountNumber data, int position) {
        bankAccountList.remove(position);
        adapter.changeDataSet(bankAccountList);

        showOrHideRecyclerView();
    }

    private void saveSettingAndGoNextStep() {

        // TODO utilizar los metodos que franklin a de implementar
//        try {
//            for (String location : bankAccountList) {
//                walletManager.createNewLocation(location, appSession.getAppPublicKey());
//            }
//
//        } catch (FermatException ex) {
//            Toast.makeText(getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();
//
//            Log.e(TAG, ex.getMessage(), ex);
//            if (errorManager != null) {
//                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
//                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
//            }
//        }

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
