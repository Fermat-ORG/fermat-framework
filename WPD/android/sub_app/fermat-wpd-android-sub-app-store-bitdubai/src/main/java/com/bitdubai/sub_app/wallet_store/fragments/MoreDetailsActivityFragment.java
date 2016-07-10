package com.bitdubai.sub_app.wallet_store.fragments;


import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.wallet_store.bitdubai.R;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.DEVELOPER_NAME;

/**
 * Fragment que luce como un Activity donde se muestra mas informacion sobre la Wallet mostrada en DetailsActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class MoreDetailsActivityFragment extends AbstractFermatFragment {
    // UI
    private FermatTextView elevatorPitch;
    private FermatTextView description;
    private FermatTextView features;
    private FermatTextView whatsNewDescription;
    private FermatTextView versionNumber;
    private FermatTextView updateDate;
    private FermatTextView numInstalls;
    private FermatTextView resourcesSize;
    private FermatTextView walletPublisherName;
    private FermatTextView developerAddress;
    private FermatTextView developerEmail;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static MoreDetailsActivityFragment newInstance() {
        return new MoreDetailsActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_more_details_wallet, container, false);
        final WalletStoreListItem catalogItem = (WalletStoreListItem) appSession.getData(BASIC_DATA);

        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(catalogItem.getWalletName());
        }

        elevatorPitch = (FermatTextView) layout.findViewById(R.id.wallet_store_elevator_pitch);
        description = (FermatTextView) layout.findViewById(R.id.description);
        features = (FermatTextView) layout.findViewById(R.id.features);
        whatsNewDescription = (FermatTextView) layout.findViewById(R.id.whats_new_description);
        versionNumber = (FermatTextView) layout.findViewById(R.id.version_number);
        updateDate = (FermatTextView) layout.findViewById(R.id.update_date);
        numInstalls = (FermatTextView) layout.findViewById(R.id.num_installs);
        resourcesSize = (FermatTextView) layout.findViewById(R.id.resources_size);
        walletPublisherName = (FermatTextView) layout.findViewById(R.id.ws_wallet_publisher_name);
        developerAddress = (FermatTextView) layout.findViewById(R.id.developer_address);
        developerEmail = (FermatTextView) layout.findViewById(R.id.developer_email);

        setDataInViews(catalogItem);

        return layout;
    }

    private void setDataInViews(WalletStoreListItem catalogItem) {
        final String developerAlias = (String) appSession.getData(DEVELOPER_NAME);

        elevatorPitch.setText("Elevator Pitch de la Wallet.\nElevetor Pitch de la Wallet Linea 2.");
        description.setText(catalogItem.getDescription());

        features.setText("* Feature 1\n" +
                "* Feature 1\n" +
                "* Feature 2\n" +
                "* Feature 3\n" +
                "* Feature 4");

        whatsNewDescription.setText("Texto de Prueba. Sed ut perspiciatis unde omnis iste natus error sit voluptatem" +
                " accusantium doloremque laudantium, totam rem aperiam\n\n* Item 1\n* Item 2\n\n " +
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium");

        versionNumber.setText("1.0.0");
        updateDate.setText("Aug 15, 2015");
        numInstalls.setText("100+ Installs");
        resourcesSize.setText("10.21MB");
        walletPublisherName.setText("Publisher Name");
        developerAddress.setText("Calle America, entre av Cacerez y Bolivar, Edif. Catalina #4-5");
        developerEmail.setText(developerAlias);
    }
}
