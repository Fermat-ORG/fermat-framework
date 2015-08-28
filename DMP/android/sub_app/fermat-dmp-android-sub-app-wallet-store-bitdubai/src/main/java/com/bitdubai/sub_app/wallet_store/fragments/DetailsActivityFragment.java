package com.bitdubai.sub_app.wallet_store.fragments;


import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.wallet_store.common.adapters.ImagesAdapter;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.bitdubai.sub_app.wallet_store.util.CommonLogger;
import com.wallet_store.bitdubai.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.CATALOG_ITEM;


/**
 * Fragment que luce como un Activity donde se muestra parte de los detalles de una Wallet seleccionada en el catalogo de MainActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class DetailsActivityFragment extends FermatFragment {
    private final String TAG = "DetailsActivityFragment";

    // MODULE
    private WalletStoreModuleManager moduleManager;

    private ErrorManager errorManager;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static DetailsActivityFragment newInstance() {
        return new DetailsActivityFragment();
    }

    @Override
    public void setSubAppsSession(SubAppsSession subAppsSession) {
        super.setSubAppsSession(subAppsSession);

        WalletStoreSubAppSession session = (WalletStoreSubAppSession) subAppsSession;
        moduleManager = session.getWalletStoreModuleManager();
        errorManager = subAppsSession.getErrorManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wallet_store_fragment_details_activity, container, false);

        WalletStoreListItem catalogItem = (WalletStoreListItem) subAppsSession.getData(CATALOG_ITEM);

        FermatTextView shortDescription = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        FermatTextView developerName = (FermatTextView) rootView.findViewById(R.id.wallet_developer_name);
        FermatTextView publisherName = (FermatTextView) rootView.findViewById(R.id.wallet_publisher_name);
        FermatTextView totalInstalls = (FermatTextView) rootView.findViewById(R.id.wallet_total_installs);
        FermatTextView walletName = (FermatTextView) rootView.findViewById(R.id.wallet_name);
        ImageView walletBanner = (ImageView) rootView.findViewById(R.id.wallet_banner);
        ImageView walletIcon = (ImageView) rootView.findViewById(R.id.wallet_icon);
        FermatButton installButton = (FermatButton) rootView.findViewById(R.id.wallet_install_button);
        FermatTextView readMoreLink = (FermatTextView) rootView.findViewById(R.id.read_more_link);
        RecyclerView previewImagesRecyclerView = (RecyclerView) rootView.findViewById(R.id.wallet_screenshots_recycler_view);
        FermatTextView noPreviewImages = (FermatTextView) rootView.findViewById(R.id.no_preview_images);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
        previewImagesRecyclerView.setLayoutManager(layout);

        ImagesAdapter adapter = new ImagesAdapter(getActivity());
        previewImagesRecyclerView.setAdapter(adapter);

        shortDescription.setText(catalogItem.getDescription());
        installButton.setText(catalogItem.getInstallationStatusText());
        walletName.setText(catalogItem.getWalletName());
        walletIcon.setImageDrawable(catalogItem.getWalletIcon());
        walletBanner.setImageDrawable(catalogItem.getWalletIcon()); // TODO Obtener valor correcto
        publisherName.setText("Publisher Name"); // TODO Obtener valor correcto
        totalInstalls.setText("10"); // TODO Obtener valor correcto


        DetailedCatalogItem catalogItemDetails = null;
        try {
            catalogItemDetails = moduleManager.getCatalogItemDetails(catalogItem.getId());
        } catch (Exception e) {
            CommonLogger.exception(TAG, e.getMessage(), e);
            errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }


        if (catalogItemDetails != null) {
            DeveloperIdentity developer = catalogItemDetails.getDeveloper();
            developerName.setText(developer.getAlias());

            try {
                Skin skin = catalogItemDetails.getDefaultSkin();
                ArrayList<Drawable> imageList = getDrawablePreviewImageList(skin);
                adapter.changeDataSet(imageList);
            } catch (Exception e) {
                CommonLogger.exception(TAG, e.getMessage(), e);
                errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

                previewImagesRecyclerView.setVisibility(View.GONE);
                noPreviewImages.setVisibility(View.VISIBLE);
            }

        } else {
            developerName.setText("Developer Name");

            ArrayList<Drawable> previewImageDrawableList = new ArrayList<>();
            previewImageDrawableList.add(getResources().getDrawable(R.drawable.wallet_screenshot_1));
            previewImageDrawableList.add(getResources().getDrawable(R.drawable.wallet_screenshot_2));
            previewImageDrawableList.add(getResources().getDrawable(R.drawable.wallet_screenshot_3));
            adapter.changeDataSet(previewImageDrawableList);
        }


        installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Installing...", Toast.LENGTH_SHORT).show();
            }
        });

        readMoreLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "To MoreDetailActivity", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @NonNull
    private ArrayList<Drawable> getDrawablePreviewImageList(Skin skin) {

        ArrayList<Drawable> previewImageDrawableList;
        try {
            previewImageDrawableList = new ArrayList<>();
            List<byte[]> previewImageList = skin.getPreviewImageList();

            for (int i = 0; i < previewImageList.size(); i++) {
                byte[] previewImgBytes = previewImageList.get(i);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(previewImgBytes);
                Drawable img = Drawable.createFromStream(inputStream, "preview_" + i);
                previewImageDrawableList.add(img);
            }
        } catch (Exception e) {
            CommonLogger.exception(TAG, "Unable to load wallet preview images", e);
            errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE, UnexpectedSubAppExceptionSeverity.NOT_IMPORTANT, e);

            previewImageDrawableList = new ArrayList<>();
            previewImageDrawableList.add(getResources().getDrawable(R.drawable.wallet_screenshot_1));
            previewImageDrawableList.add(getResources().getDrawable(R.drawable.wallet_screenshot_2));
            previewImageDrawableList.add(getResources().getDrawable(R.drawable.wallet_screenshot_3));
        }

        return previewImageDrawableList;
    }
}


