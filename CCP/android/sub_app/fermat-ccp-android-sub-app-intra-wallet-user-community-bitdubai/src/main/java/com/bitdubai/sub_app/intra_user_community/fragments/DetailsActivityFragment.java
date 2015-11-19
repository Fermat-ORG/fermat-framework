package com.bitdubai.sub_app.intra_user_community.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.common.adapters.ImagesAdapter;
import com.bitdubai.sub_app.intra_user_community.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.R;

import java.util.ArrayList;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;


/**
 * Fragment que luce como un Activity donde se muestra parte de los detalles de una Wallet seleccionada en el catalogo de MainActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class DetailsActivityFragment extends FermatFragment {
    private final String TAG = "DetailsActivityFragment";

    // MODULE
    private IntraUserModuleManager moduleManager;

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

        IntraUserSubAppSession session = (IntraUserSubAppSession) subAppsSession;
        moduleManager = session.getIntraUserModuleManager();
        errorManager = subAppsSession.getErrorManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wallet_store_fragment_details_activity, container, false);

        WalletStoreListItem catalogItem = (WalletStoreListItem) subAppsSession.getData(IntraUserSubAppSession.BASIC_DATA);
        String developerAlias = (String) subAppsSession.getData(IntraUserSubAppSession.DEVELOPER_NAME);
        ArrayList<Drawable> walletPreviewImgList = (ArrayList<Drawable>) subAppsSession.getData(IntraUserSubAppSession.PREVIEW_IMGS);


        FermatTextView developerName = (FermatTextView) rootView.findViewById(R.id.wallet_developer_name);
        developerName.setText(developerAlias);

        FermatTextView shortDescription = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        shortDescription.setText(catalogItem.getDescription());

        FermatTextView walletName = (FermatTextView) rootView.findViewById(R.id.wallet_name);
        walletName.setText(catalogItem.getWalletName());

        ImageView walletIcon = (ImageView) rootView.findViewById(R.id.wallet_icon);
        walletIcon.setImageDrawable(catalogItem.getWalletIcon());

        ImageView walletBanner = (ImageView) rootView.findViewById(R.id.wallet_banner);
        walletBanner.setImageDrawable(catalogItem.getWalletIcon()); // TODO Obtener valor correcto

        FermatTextView publisherName = (FermatTextView) rootView.findViewById(R.id.wallet_publisher_name);
        publisherName.setText("Publisher Name"); // TODO Obtener valor correcto

        FermatTextView totalInstalls = (FermatTextView) rootView.findViewById(R.id.wallet_total_installs);
        totalInstalls.setText("10"); // TODO Obtener valor correcto

        FermatTextView readMoreLink = (FermatTextView) rootView.findViewById(R.id.read_more_link);
        readMoreLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "To MoreDetailActivity", Toast.LENGTH_SHORT).show();
            }
        });

        FermatButton installButton = (FermatButton) rootView.findViewById(R.id.wallet_install_button);
        InstallationStatus installStatus = catalogItem.getInstallationStatus();
        int resId = 0;//UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installStatus);
        resId = (resId == R.string.wallet_status_installed) ? R.string.wallet_status_open : resId;
        installButton.setText(resId);
        installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Installing...", Toast.LENGTH_SHORT).show();
            }
        });

        if (resId != R.string.wallet_status_install) {
            FermatButton uninstallButton = (FermatButton) rootView.findViewById(R.id.wallet_uninstall_button);
            uninstallButton.setText(R.string.wallet_status_uninstall);
            uninstallButton.setVisibility(View.VISIBLE);
            uninstallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Uninstalling...", Toast.LENGTH_SHORT).show();
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


