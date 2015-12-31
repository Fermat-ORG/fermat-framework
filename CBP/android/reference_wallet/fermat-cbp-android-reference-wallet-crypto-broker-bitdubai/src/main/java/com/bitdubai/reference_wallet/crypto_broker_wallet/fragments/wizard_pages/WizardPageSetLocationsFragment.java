package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.LocationsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetLocationsFragment extends AbstractFermatFragment implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<String> {
    // Constants
    private static final String TAG = "WizardPageSetLocations";

    // Data
    private List<String> locationList;

    // UI
    private RecyclerView recyclerView;
    private LocationsAdapter adapter;
    private View emptyView;

    // Fermat Managers
    private CryptoBrokerWalletManager walletManager;
    private ErrorManager errorManager;


    public static WizardPageSetLocationsFragment newInstance() {
        return new WizardPageSetLocationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CryptoBrokerWalletModuleManager moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            Object data = appSession.getData(CryptoBrokerWalletSession.LOCATION_LIST);
            if (data == null) {
                locationList = new ArrayList<>();
                appSession.setData(CryptoBrokerWalletSession.LOCATION_LIST, locationList);
            } else {
                locationList = (List<String>) data;
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.cbw_wizard_step_set_locations, container, false);

        adapter = new LocationsAdapter(getActivity(), locationList);
        adapter.setDeleteButtonListener(this);

        recyclerView = (RecyclerView) layout.findViewById(R.id.cbw_selected_locations_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        emptyView = layout.findViewById(R.id.cbw_selected_locations_empty_view);

        final View addLocationButton = layout.findViewById(R.id.cbw_add_location_button);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_WIZARD, appSession.getAppPublicKey());
            }
        });

        final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
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
    public void deleteButtonClicked(String data, int position) {
        locationList.remove(position);
        adapter.changeDataSet(locationList);

        showOrHideRecyclerView();
    }

    private void saveSettingAndGoNextStep() {
        if (locationList.isEmpty()) {
            Toast.makeText(getActivity(), R.string.cbw_add_location_warning_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            for (String location : locationList) {
                walletManager.createNewLocation(location, appSession.getAppPublicKey());
            }

        } catch (FermatException ex) {
            Toast.makeText(getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }

        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_BANK_ACCOUNT, appSession.getAppPublicKey());
    }

    private void showOrHideRecyclerView() {
        if (locationList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
