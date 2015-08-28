package com.bitdubai.sub_app.wallet_store.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.bitdubai.sub_app.wallet_store.common.adapters.ImagesAdapter;
import com.bitdubai.sub_app.wallet_store.common.models.CatalogueItemDao;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.CATALOG_ITEM;
import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;


/**
 * Fragment que luce como un Activity donde se muestra parte de los detalles de una Wallet seleccionada en el catalogo de MainActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class DetailsActivityFragment extends FermatFragment {
    // MODULE
    private WalletStoreModuleManager moduleManager;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static DetailsActivityFragment newInstance() {
        DetailsActivityFragment f = new DetailsActivityFragment();
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
        walletBanner.setImageDrawable(catalogItem.getWalletIcon()); // TODO Obtener valor de verdad

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
        totalInstalls.setText("10"); // TODO Obtener valor de verdad

        FermatTextView shortDescription = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        shortDescription.setText("Una descripcion. Una descripcion. Una descripcion. Una descripcion."); // TODO Colocar data verdadera

        ArrayList<Drawable> screenshotImgs = new ArrayList<>();
        screenshotImgs.add(getResources().getDrawable(R.drawable.wallet_screenshot_1));
        screenshotImgs.add(getResources().getDrawable(R.drawable.wallet_screenshot_2));
        screenshotImgs.add(getResources().getDrawable(R.drawable.wallet_screenshot_3));

        RecyclerView screenshotsRecyclerView = (RecyclerView) rootView.findViewById(R.id.wallet_screenshots_recycler_view);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
        screenshotsRecyclerView.setLayoutManager(layout);

        ImagesAdapter adapter = new ImagesAdapter(getActivity(), screenshotImgs);
        screenshotsRecyclerView.setAdapter(adapter);


        return rootView;
    }
}


