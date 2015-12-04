package com.bitdubai.sub_app.wallet_store.fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.sub_app.wallet_store.common.adapters.ImagesAdapter;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.wallet_store.common.workers.InstallWalletWorker;
import com.bitdubai.sub_app.wallet_store.common.workers.InstallWalletWorkerCallback;
import com.bitdubai.sub_app.wallet_store.common.workers.UninstallWalletWorker;
import com.bitdubai.sub_app.wallet_store.common.workers.UninstallWalletWorkerCallback;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.bitdubai.sub_app.wallet_store.util.UtilsFuncs;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus.INSTALLED;
import static com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus.NOT_UNINSTALLED;
import static com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus.UPGRADE_AVAILABLE;
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

    private WalletStoreListItem catalogItem;

    // UI
    private FermatTextView developerName;
    private FermatTextView shortDescription;
    private FermatTextView walletName;
    private ImageView walletIcon;
    private ImageView walletBanner;
    private FermatTextView publisherName;
    private FermatTextView totalInstalls;
    private FermatTextView readMoreLink;
    private FermatButton installButton;
    private FermatButton uninstallButton;
    private RecyclerView previewImagesRecyclerView;
    private FermatTextView noPreviewImages;

    private ProgressDialog dialog;


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

        developerName = (FermatTextView) rootView.findViewById(R.id.wallet_developer_name);
        shortDescription = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        walletName = (FermatTextView) rootView.findViewById(R.id.wallet_name);
        walletIcon = (ImageView) rootView.findViewById(R.id.wallet_icon);
        walletBanner = (ImageView) rootView.findViewById(R.id.wallet_banner);
        publisherName = (FermatTextView) rootView.findViewById(R.id.wallet_publisher_name);
        totalInstalls = (FermatTextView) rootView.findViewById(R.id.wallet_total_installs);
        readMoreLink = (FermatTextView) rootView.findViewById(R.id.read_more_link);
        installButton = (FermatButton) rootView.findViewById(R.id.wallet_install_button);
        uninstallButton = (FermatButton) rootView.findViewById(R.id.wallet_uninstall_button);
        previewImagesRecyclerView = (RecyclerView) rootView.findViewById(R.id.wallet_screenshots_recycler_view);
        noPreviewImages = (FermatTextView) rootView.findViewById(R.id.no_preview_images);

        setupDataInViews();

        return rootView;
    }

    private void setupDataInViews() {
        final List<Bitmap> walletPreviewImgList = (List) subAppsSession.getData(PREVIEW_IMGS);
        final String developerAlias = (String) subAppsSession.getData(DEVELOPER_NAME);
        catalogItem = (WalletStoreListItem) subAppsSession.getData(BASIC_DATA);


        walletName.setText(catalogItem.getWalletName());
        walletIcon.setImageBitmap(catalogItem.getWalletIcon());
        developerName.setText(developerAlias);
        shortDescription.setText(catalogItem.getDescription());
        walletBanner.setImageResource(catalogItem.getBannerWalletRes()); // TODO Obtener valor correcto
        publisherName.setText("BitDubai"); // TODO Obtener valor correcto
        totalInstalls.setText("1"); // TODO Obtener valor correcto

        readMoreLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY.getCode(), subAppsSession.getAppPublicKey());
            }
        });

        final InstallationStatus installStatus = catalogItem.getInstallationStatus();
        int resId = UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installStatus);
        resId = (resId == R.string.wallet_status_installed) ? R.string.wallet_status_open : resId;
        installButton.setText(resId);
        installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (installStatus == INSTALLED || installStatus == NOT_UNINSTALLED) {
                    // open wallet
                } else if (installStatus == UPGRADE_AVAILABLE) {
                    // upgrade wallet
                } else {
                    installWallet();
                }
            }
        });

        if (resId != R.string.wallet_status_install) {
            uninstallButton.setText(R.string.wallet_status_uninstall);
            uninstallButton.setVisibility(View.VISIBLE);
            uninstallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UUID catalogueId = catalogItem.getId();
                    uninstallWallet(catalogueId);
                }
            });
        }

        if (walletPreviewImgList != null) {
            LinearLayoutManager layout = new LinearLayoutManager(getActivity(), HORIZONTAL, false);
            previewImagesRecyclerView.setLayoutManager(layout);

            ImagesAdapter adapter = new ImagesAdapter(getActivity(), walletPreviewImgList);
            previewImagesRecyclerView.setAdapter(adapter);

        } else {
            previewImagesRecyclerView.setVisibility(View.GONE);
            noPreviewImages.setVisibility(View.VISIBLE);
        }
    }

    private void installWallet() {
        dialog = UtilsFuncs.INSTANCE.showProgressDialog(dialog, getActivity(),
                R.string.installing_message, R.string.wait_please_message);

        InstallWalletWorkerCallback callback = new InstallWalletWorkerCallback(
                getActivity(), errorManager, dialog, installButton, uninstallButton);

        InstallWalletWorker installWalletWorker = new InstallWalletWorker(
                getActivity(), callback, moduleManager, subAppsSession);

        if (executor != null) {
            executor.shutdownNow();
        }

        executor = null;
        executor = installWalletWorker.execute();
    }

    private void uninstallWallet(UUID catalogueId) {
        UtilsFuncs.INSTANCE.showProgressDialog(dialog, getActivity(),
                R.string.uninstalling_message, R.string.wait_please_message);

        UninstallWalletWorkerCallback callback = new UninstallWalletWorkerCallback(getActivity(), errorManager);

        UninstallWalletWorker uninstallWalletWorker = new UninstallWalletWorker(
                getActivity(), callback, moduleManager, catalogueId);

        if (executor != null) {
            executor.shutdownNow();
        }

        executor = null;
        executor = uninstallWalletWorker.execute();
    }
}


