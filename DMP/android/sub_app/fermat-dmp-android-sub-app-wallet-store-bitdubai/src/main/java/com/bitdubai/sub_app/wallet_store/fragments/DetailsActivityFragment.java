package com.bitdubai.sub_app.wallet_store.fragments;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.sub_app.wallet_store.common.models.CatalogueItemDao;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.wallet_store.bitdubai.R;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.CATALOG_ITEM;


/**
 * Fragment que luce como un Activity donde se muestra parte de los detalles de una Wallet seleccionada en el catalogo de MainActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class DetailsActivityFragment extends FermatFragment {
    /**
     * STATIC
     */
    private static final String ARG_POSITION = "position";

    /**
     * MODULE
     */
    private WalletStoreModuleManager moduleManager;


    /**
     * Create a new instance of this fragment
     *
     * @param position tab position
     * @return InstalledFragment instance object
     */
    public static DetailsActivityFragment newInstance(int position) {
        DetailsActivityFragment f = new DetailsActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        f.setArguments(args);
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
        View rootView = inflater.inflate(R.layout.wallet_store_fragment_details_activity, container, false);

        CatalogueItemDao catalogItem = (CatalogueItemDao) subAppsSession.getData(CATALOG_ITEM);

        ImageView walletBanner = (ImageView) rootView.findViewById(R.id.wallet_banner);
        walletBanner.setImageResource(R.drawable.banner_club_1); // TODO Obtener valor de verdad

        ImageView walletIcon = (ImageView) rootView.findViewById(R.id.wallet_icon);
        walletIcon.setImageDrawable(catalogItem.getWalletIcon());

        FermatTextView walletName = (FermatTextView) rootView.findViewById(R.id.wallet_name);
        walletName.setText(catalogItem.getWalletName());

        FermatTextView publisherName = (FermatTextView) rootView.findViewById(R.id.wallet_publisher_name);
        publisherName.setText("Publisher Name"); // TODO Obtener valor de verdad

        FermatTextView developerName = (FermatTextView) rootView.findViewById(R.id.wallet_developer_name);
        developerName.setText(catalogItem.getDeveloperName());

        FermatButton installButton = (FermatButton) rootView.findViewById(R.id.wallet_install_button);
        installButton.setText(catalogItem.getInstallationStatusText());

        FermatTextView totalInstalls = (FermatTextView) rootView.findViewById(R.id.wallet_total_installs);
        totalInstalls.setText("10 install"); // TODO Obtener valor de verdad

        FermatTextView shortDescription = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        shortDescription.setText(catalogItem.getSrcObj().getDescription()); // TODO Verificar si es larga o corta la descripcion

        RecyclerView screenshotsRecylerView = (RecyclerView) rootView.findViewById(R.id.wallet_screenshots_recycler_view);
        // TODO preguntar como montar la lista de screenshots y donde se encuentra eso en los datos de la Wallet

        return rootView;
    }


}
