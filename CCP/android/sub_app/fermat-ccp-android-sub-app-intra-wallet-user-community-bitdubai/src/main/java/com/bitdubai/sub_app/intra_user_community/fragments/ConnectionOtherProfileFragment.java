package com.bitdubai.sub_app.intra_user_community.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatRoundedImageView;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.common.navigation_drawer.NavigationViewAdapter;
import com.bitdubai.sub_app.intra_user_community.common.popups.ConnectDialog;
import com.bitdubai.sub_app.intra_user_community.common.utils.FragmentsCommons;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;

/**
 * Created by josemanueldsds on 29/11/15.
 */
public class ConnectionOtherProfileFragment extends FermatFragment {

    private View rootView;
    private IntraUserSubAppSession intraUserSubAppSession;
    private FermatRoundedImageView userProfileAvatar;
    private FermatTextView userName;
    private FermatTextView userEmail;
    private IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;
    private IntraUserInformation intraUserInformation;
    private Button connect;
    private Button disconect;
    private CryptoWalletIntraUserActor identity;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ConnectionOtherProfileFragment newInstance() {
        return new ConnectionOtherProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting up  module
        intraUserSubAppSession = ((IntraUserSubAppSession) subAppsSession);
        moduleManager = intraUserSubAppSession.getModuleManager();
        errorManager = subAppsSession.getErrorManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.intra_user_other_profile, container, false);
        userProfileAvatar = (FermatRoundedImageView) rootView.findViewById(R.id.img_user_avatar);
        userName = (FermatTextView) rootView.findViewById(R.id.username);
        userEmail = (FermatTextView) rootView.findViewById(R.id.email);
        connect = (Button) rootView.findViewById(R.id.btn_conect);
        disconect = (Button) rootView.findViewById(R.id.btn_disconect);
        connect.setVisibility(View.VISIBLE);
        disconect.setVisibility(View.GONE);
        /*try {
            userName.setText(intraUserInformation.getName());
            userEmail.setText("Unknow");
            Picasso.with(getActivity())
                    .load(Arrays.toString(intraUserInformation.getProfileImage()))
                    .placeholder(R.drawable.profile_image)
                    .into(userProfileAvatar);
        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }*/
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectDialog connectDialog = null;
                try {
                    connectDialog = new ConnectDialog(getActivity(), (IntraUserSubAppSession) subAppsSession, subAppResourcesProviderManager, intraUserInformation, moduleManager.getActiveIntraUserIdentity());
                    connectDialog.show();
                } catch (CantGetActiveLoginIdentityException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
        /**
         * add navigation header
         */
        addNavigationHeader(FragmentsCommons.setUpHeaderScreen(layoutInflater, getActivity(), intraUserSubAppSession.getModuleManager().getActiveIntraUserIdentity()));

        /**
         * Navigation view items
         */
        NavigationViewAdapter navigationViewAdapter = new NavigationViewAdapter(getActivity(), null);
        setNavigationDrawer(navigationViewAdapter);
    }

}
