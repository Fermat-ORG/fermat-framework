package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantDeleteLocationSaleException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.LocationsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by memo on 06/01/16.
 */
public class SettingsMylocationsFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager>
        implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<String> {

    // Constants
    private static final String TAG = "settingsMyLocations";

    // Data
    private List<String> locationList;
    private List<NegotiationLocations> locationListIds;

    // UI
    private RecyclerView recyclerView;
    private LocationsAdapter adapter;
    private View emptyView;

    // Fermat Managers
    private CryptoBrokerWalletModuleManager walletManager;
    private ErrorManager errorManager;


    public static SettingsMylocationsFragment newInstance() {
        return new SettingsMylocationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            walletManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();


            //Object data = appSession.getData(CryptoBrokerWalletSession.LOCATION_LIST);
            /*
            if (data == null) {
                locationList = new ArrayList<>();
                appSession.setData(CryptoBrokerWalletSession.LOCATION_LIST, locationList);
            } else {
                locationList = (List<String>) data;
            }
            */

            Collection<NegotiationLocations> locations = walletManager.getAllLocations(NegotiationType.SALE);
            locationList = new ArrayList<>();
            locationListIds = new ArrayList<>();

            if( locations != null ){
                if(!locations.isEmpty()) {
                    for(NegotiationLocations l : locations){
                        if (!l.getLocation().equals("settings") && !l.getLocation().equals("wizard")) {
                            locationList.add(l.getLocation());
                            locationListIds.add(l);
                        }
                    }
                }
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
        configureToolbar();
        View layout = inflater.inflate(R.layout.cbw_settings_my_locations, container, false);

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
                //locationList.add("settings");
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CREATE_NEW_LOCATION_IN_SETTINGS, appSession.getAppPublicKey());
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

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    @Override
    public void deleteButtonClicked(String data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.cbw_delete_location_dialog_title).setMessage(R.string.cbw_delete_location_dialog_msg);
        builder.setPositiveButton(R.string.cbw_delete_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    walletManager.deleteLocation(locationListIds.get(position));
                } catch (CantDeleteLocationSaleException e) {
                    if (errorManager != null)
                        errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
                }

                locationList.remove(position);
                locationListIds.remove(position);
                adapter.changeDataSet(locationList);
                showOrHideRecyclerView();

            }
        });
        builder.setNegativeButton(R.string.cbw_cancel_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();
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

        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
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