package com.bitdubai.sub_app.wallet_store.fragments;


import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.wallet_store.bitdubai.R;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.DEVELOPER_NAME;

/**
 * Fragment que luce como un Activity donde se muestra mas informacion sobre la Wallet mostrada en DetailsActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class MoreDetailsActivityFragment extends FermatFragment {
    // MANAGERS
    private WalletStoreModuleManager moduleManager;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static MoreDetailsActivityFragment newInstance() {
        MoreDetailsActivityFragment f = new MoreDetailsActivityFragment();
        return f;
    }


    @Override
    public void setSubAppsSession(SubAppsSession subAppsSession) {
        super.setSubAppsSession(subAppsSession);

        WalletStoreSubAppSession session = (WalletStoreSubAppSession) subAppsSession;
        moduleManager = session.getWalletStoreModuleManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.wallet_store_fragment_more_details_activity, container, false);
        final WalletStoreListItem catalogItem = (WalletStoreListItem) subAppsSession.getData(BASIC_DATA);
        final String developerAlias = (String) subAppsSession.getData(DEVELOPER_NAME);

        ActionBar actionBar = getActivity().getActionBar();
        if(actionBar != null){
            int color = getResources().getColor(R.color.wallet_store_activities_background);
            actionBar.setBackgroundDrawable(new ColorDrawable(color));
            actionBar.setTitle(catalogItem.getWalletName());
            if(!actionBar.isShowing()) {
                actionBar.show();
            }
        }

        FermatTextView elevatorPitch = (FermatTextView) layout.findViewById(R.id.elevator_pitch);
        elevatorPitch.setText("Elevator Pitch de la Wallet.\nElevetor Pitch de la Wallet Linea 2.");

        FermatTextView description = (FermatTextView) layout.findViewById(R.id.description);
        description.setText(catalogItem.getDescription());

        FermatTextView features = (FermatTextView) layout.findViewById(R.id.features);
        features.setText("* Feature 1\n" +
                "* Feature 1\n" +
                "* Feature 2\n" +
                "* Feature 3\n" +
                "* Feature 4");

        FermatTextView whatsNewDescription = (FermatTextView) layout.findViewById(R.id.whats_new_description);
        whatsNewDescription.setText("Texto de Prueba. Sed ut perspiciatis unde omnis iste natus error sit voluptatem" +
                " accusantium doloremque laudantium, totam rem aperiam\n\n* Item 1\n* Item 2\n\n " +
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium");

        FermatTextView versionNumber = (FermatTextView) layout.findViewById(R.id.version_number);
        versionNumber.setText("1.0.0");

        FermatTextView updateDate = (FermatTextView) layout.findViewById(R.id.update_date);
        updateDate.setText("Aug 15, 2015");

        FermatTextView numInstalls = (FermatTextView) layout.findViewById(R.id.num_installs);
        numInstalls.setText("100+ Installs");

        FermatTextView resourcesSize = (FermatTextView) layout.findViewById(R.id.resources_size);
        resourcesSize.setText("10.21MB");

        FermatTextView walletPublisherName = (FermatTextView) layout.findViewById(R.id.wallet_publisher_name);
        walletPublisherName.setText("Publisher Name");

        FermatTextView developerAddress = (FermatTextView) layout.findViewById(R.id.developer_address);
        developerAddress.setText("Calle America, entre av Cacerez y Bolivar, Edif. Catalina #4-5");

        FermatTextView developerEmail = (FermatTextView) layout.findViewById(R.id.developer_email);
        developerEmail.setText(developerAlias);

        return layout;
    }
}
