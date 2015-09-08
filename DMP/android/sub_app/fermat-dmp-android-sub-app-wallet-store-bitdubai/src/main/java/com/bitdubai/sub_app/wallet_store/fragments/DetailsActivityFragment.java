package com.bitdubai.sub_app.wallet_store.fragments;


import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.wallet_store.common.workers.InstallWalletWorker;
import com.bitdubai.sub_app.wallet_store.common.workers.InstallWalletWorkerCallback;
import com.bitdubai.sub_app.wallet_store.util.UtilsFuncs;
import com.bitdubai.sub_app.wallet_store.common.adapters.ImagesAdapter;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.wallet_store.common.workers.UninstallWalletWorker;
import com.bitdubai.sub_app.wallet_store.common.workers.UninstallWalletWorkerCallback;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.DEVELOPER_NAME;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.PREVIEW_IMGS;


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

    private ExecutorService executor;


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

        ActionBar actionBar = getActivity().getActionBar();
        if(actionBar != null){
            actionBar.hide();
            Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.details_toolbar);
        }

        ArrayList<Bitmap> walletPreviewImgList = (ArrayList) subAppsSession.getData(PREVIEW_IMGS);
        final WalletStoreListItem catalogItem = (WalletStoreListItem) subAppsSession.getData(BASIC_DATA);
        final String developerAlias = (String) subAppsSession.getData(DEVELOPER_NAME);


        FermatTextView developerName = (FermatTextView) rootView.findViewById(R.id.wallet_developer_name);
        developerName.setText(developerAlias);


        FermatTextView shortDescription = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        shortDescription.setText(catalogItem.getDescription());


        FermatTextView walletName = (FermatTextView) rootView.findViewById(R.id.wallet_name);
        walletName.setText(catalogItem.getWalletName());


        ImageView walletIcon = (ImageView) rootView.findViewById(R.id.wallet_icon);
        walletIcon.setImageBitmap(catalogItem.getWalletIcon());


        ImageView walletBanner = (ImageView) rootView.findViewById(R.id.wallet_banner);
        walletBanner.setImageBitmap(catalogItem.getWalletIcon()); // TODO Obtener valor correcto


        FermatTextView publisherName = (FermatTextView) rootView.findViewById(R.id.wallet_publisher_name);
        publisherName.setText("Publisher Name"); // TODO Obtener valor correcto


        FermatTextView totalInstalls = (FermatTextView) rootView.findViewById(R.id.wallet_total_installs);
        totalInstalls.setText("10"); // TODO Obtener valor correcto


        FermatTextView readMoreLink = (FermatTextView) rootView.findViewById(R.id.read_more_link);
        readMoreLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MoreDetailsActivityFragment fragment = MoreDetailsActivityFragment.newInstance();
                fragment.setSubAppsSession(subAppsSession);
                fragment.setSubAppSettings(subAppSettings);
                fragment.setSubAppResourcesProviderManager(subAppResourcesProviderManager);

                final FragmentTransaction FT = getActivity().getFragmentManager().beginTransaction();
                FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                FT.replace(R.id.activity_container, fragment);
                FT.addToBackStack(null);
                FT.commit();
            }
        });


        FermatButton installButton = (FermatButton) rootView.findViewById(R.id.wallet_install_button);
        InstallationStatus installStatus = catalogItem.getInstallationStatus();
        int installStatusResId = UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installStatus);
        installStatusResId = (installStatusResId == R.string.wallet_status_installed) ? R.string.wallet_status_open : installStatusResId;
        installButton.setText(installStatusResId);
        installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Installing...", Toast.LENGTH_SHORT).show();

                InstallWalletWorkerCallback callback = new InstallWalletWorkerCallback(getActivity(), errorManager);
                InstallWalletWorker installWalletWorker = new InstallWalletWorker(getActivity(), callback, moduleManager, subAppsSession);
                if (executor != null)
                    executor.shutdownNow();
                executor = null;
                executor = installWalletWorker.execute();
            }
        });


        if (installStatusResId != R.string.wallet_status_install) {
            FermatButton uninstallButton = (FermatButton) rootView.findViewById(R.id.wallet_uninstall_button);
            uninstallButton.setText(R.string.wallet_status_uninstall);
            uninstallButton.setVisibility(View.VISIBLE);
            uninstallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Uninstalling...", Toast.LENGTH_SHORT).show();

                    UninstallWalletWorkerCallback callback = new UninstallWalletWorkerCallback(getActivity(), errorManager);
                    UUID catalogueId = catalogItem.getId();
                    UninstallWalletWorker installWalletWorker = new UninstallWalletWorker(getActivity(), callback, moduleManager, catalogueId);
                    if (executor != null)
                        executor.shutdownNow();
                    executor = null;
                    executor = installWalletWorker.execute();
                }
            });
        }


        RecyclerView previewImagesRecyclerView = (RecyclerView) rootView.findViewById(R.id.wallet_screenshots_recycler_view);
        if (walletPreviewImgList != null) {
            LinearLayoutManager layout = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
            previewImagesRecyclerView.setLayoutManager(layout);

            ImagesAdapter adapter = new ImagesAdapter(getActivity(), walletPreviewImgList);
            previewImagesRecyclerView.setAdapter(adapter);

        } else {
            previewImagesRecyclerView.setVisibility(View.GONE);

            FermatTextView noPreviewImages = (FermatTextView) rootView.findViewById(R.id.no_preview_images);
            noPreviewImages.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

}


