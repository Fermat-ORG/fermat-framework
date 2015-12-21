package com.bitdubai.sub_app.intra_user_community.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.adapters.AppNavigationAdapter;
import com.bitdubai.sub_app.intra_user_community.common.popups.ConnectDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.DisconectDialog;
import com.bitdubai.sub_app.intra_user_community.common.utils.FragmentsCommons;
import com.bitdubai.sub_app.intra_user_community.constants.Constants;
import com.bitdubai.sub_app.intra_user_community.interfaces.MessageReceiver;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;

/**
 * Creado por Jose Manuel De Sousa on 29/11/15.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ConnectionOtherProfileFragment extends FermatFragment implements MessageReceiver {

    public static final String INTRA_USER_SELECTED = "intra_user";
    private Resources res;
    private View rootView;
    private IntraUserSubAppSession intraUserSubAppSession;
    private ImageView userProfileAvatar;
    private FermatTextView userName;
    private FermatTextView userEmail;
    private IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;
    private IntraUserInformation intraUserInformation;
    private Button connect;
    private CryptoWalletIntraUserActor identity;
    private Button disconnect;

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
        intraUserSubAppSession = ((IntraUserSubAppSession) appSession);
        intraUserInformation = (IntraUserInformation) appSession.getData(INTRA_USER_SELECTED);
        moduleManager = intraUserSubAppSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        intraUserInformation = (IntraUserInformation) appSession.getData(ConnectionsWorldFragment.INTRA_USER_SELECTED);

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_connections_other_profile, container, false);
        userProfileAvatar = (ImageView) rootView.findViewById(R.id.img_user_avatar);
        userName = (FermatTextView) rootView.findViewById(R.id.username);
        userEmail = (FermatTextView) rootView.findViewById(R.id.email);
        connect = (Button) rootView.findViewById(R.id.btn_conect);
        disconnect = (Button) rootView.findViewById(R.id.btn_disconect);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        try{
        if(moduleManager.isActorConnected(intraUserInformation.getPublicKey())) {
            disconnect.setVisibility(View.VISIBLE);
            connect.setVisibility(View.GONE);
        }else {
            connect.setVisibility(View.VISIBLE);
            disconnect.setVisibility(View.GONE);
        }
        }catch (CantCreateNewDeveloperException e) {
            e.printStackTrace();
        }

        try {
            userName.setText(intraUserInformation.getName());
            userEmail.setText("Unknow");
            if(intraUserInformation.getProfileImage() != null) {
                Bitmap bitmap;
                if (intraUserInformation.getProfileImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(intraUserInformation.getProfileImage(), 0, intraUserInformation.getProfileImage().length);
                } else {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
                userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
            }else{
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
                bitmap = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
                userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectDialog connectDialog;
                try {
                    connectDialog = new ConnectDialog(getActivity(), (IntraUserSubAppSession) appSession, (SubAppResourcesProviderManager) appResourcesProviderManager, intraUserInformation, moduleManager.getActiveIntraUserIdentity());
                    connectDialog.setTitle("Connection Request");
                    connectDialog.setDescription("Do you want to send ");
                    connectDialog.setUsername(intraUserInformation.getName());
                    connectDialog.setSecondDescription("a connection request");
                    connectDialog.show();
                } catch (CantGetActiveLoginIdentityException e) {
                    e.printStackTrace();
                }
            }
        });
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DisconectDialog disconectDialog;
                try {
                    disconectDialog = new DisconectDialog(getActivity(), (IntraUserSubAppSession) appSession, (SubAppResourcesProviderManager) appResourcesProviderManager, intraUserInformation, moduleManager.getActiveIntraUserIdentity());
                    disconectDialog.setTitle("Disconnect");
                    disconectDialog.setDescription("Want to disconnect from");
                    disconectDialog.setUsername(intraUserInformation.getName());
                    disconectDialog.show();
                } catch (CantGetActiveLoginIdentityException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.profile_image);
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
        /**
         * add navigation header
         */
        addNavigationHeader(FragmentsCommons.setUpHeaderScreen(layoutInflater, getActivity(), intraUserSubAppSession.getModuleManager().getActiveIntraUserIdentity()));

        /**
         * Navigation view items
         */
        AppNavigationAdapter appNavigationAdapter = new AppNavigationAdapter(getActivity(), null);
        setNavigationDrawer(appNavigationAdapter);
    }

    @Override
    public void onMessageReceive(Context context, Intent data) {
        Bundle extras = data != null ? data.getExtras() : null;
        if (extras != null && extras.containsKey(Constants.BROADCAST_CONNECTED_UPDATE)) {
            disconnect.setVisibility(View.VISIBLE);
            connect.setVisibility(View.GONE);
        }
        if(extras != null && extras.containsKey(Constants.BROADCAST_DISCONNECTED_UPDATE)){
            disconnect.setVisibility(View.GONE);
            connect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public IntentFilter getBroadcastIntentChannel() {
        return new IntentFilter(Constants.LOCAL_BROADCAST_CHANNEL);
    }

}
