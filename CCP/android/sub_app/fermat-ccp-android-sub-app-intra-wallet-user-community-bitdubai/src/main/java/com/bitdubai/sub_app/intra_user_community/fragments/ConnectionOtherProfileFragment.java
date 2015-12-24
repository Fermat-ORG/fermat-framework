package com.bitdubai.sub_app.intra_user_community.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.PhotoType;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraUsersConnectedStateException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUserConnectionStatusException;
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
public class ConnectionOtherProfileFragment extends FermatFragment implements MessageReceiver, View.OnClickListener {

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
    private int MAX = 1;
    private int OFFSET = 0;
    private FermatTextView userPhrase;
    private Button connectionRequestSend;
    private Button connectionRequestRejected;
    private IntraWalletUserActorManager intraWalletUserActorManager;
    private ConnectionState connectionState;

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
        userPhrase = (FermatTextView) rootView.findViewById(R.id.userPhrase);
        userName = (FermatTextView) rootView.findViewById(R.id.username);
        userEmail = (FermatTextView) rootView.findViewById(R.id.email);
        connectionRequestSend = (Button) rootView.findViewById(R.id.btn_connection_request_send);
        connectionRequestRejected = (Button) rootView.findViewById(R.id.btn_connection_request_reject);
        connect = (Button) rootView.findViewById(R.id.btn_conect);
        disconnect = (Button) rootView.findViewById(R.id.btn_disconect);
        connectionRequestSend.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setOnClickListener(this);
        connectionRequestSend.setOnClickListener(this);
        connect.setOnClickListener(this);
        disconnect.setOnClickListener(this);

        switch (intraUserInformation.getConnectionState()) {
                case BLOCKED_LOCALLY:
                case BLOCKED_REMOTELY:
                case CANCELLED_LOCALLY:
                case CANCELLED_REMOTELY:
                    connectionRejected();
                    break;
                case CONNECTED:
                    disconnectRequest();
                    break;
                case NO_CONNECTED:
                case DISCONNECTED_LOCALLY:
                case DISCONNECTED_REMOTELY:
                case ERROR:
                case DENIED_LOCALLY:
                case DENIED_REMOTELY:
                    connectRequest();
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                case PENDING_REMOTELY_ACCEPTANCE:
                    connectionSend();
                    break;
            }

        try {
            userName.setText(intraUserInformation.getName());
            userPhrase.setText(intraUserInformation.getPhrase());
            userPhrase.setTextColor(Color.parseColor("#292929"));
            if (intraUserInformation.getProfileImage() != null) {
                Bitmap bitmap;
                if (intraUserInformation.getProfileImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(intraUserInformation.getProfileImage(), 0, intraUserInformation.getProfileImage().length);
                } else {
                  //  if (intraUserInformation.getPhotoType().getCode().equals(PhotoType.DEFAULT_MALE))
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
//                    else
//                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.drawable.profile_standard_female);
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
                userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
            } else {
                Bitmap bitmap;
              //  if (intraUserInformation.getPhotoType().getCode().equals(PhotoType.DEFAULT_MALE))
                //    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
//                else
//                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.drawable.profile_standard_female);

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
                bitmap = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
                userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_conect) {
            ConnectDialog connectDialog;
            try {
                connectDialog = new ConnectDialog(getActivity(), (IntraUserSubAppSession) appSession, (SubAppResourcesProviderManager) appResourcesProviderManager, intraUserInformation, moduleManager.getActiveIntraUserIdentity());
                connectDialog.setTitle("Connection Request");
                connectDialog.setDescription("Do you want to send ");
                connectDialog.setUsername(intraUserInformation.getName());
                connectDialog.setSecondDescription("a connection request");
                connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                connectDialog.show();
            } catch (CantGetActiveLoginIdentityException e) {
                e.printStackTrace();

            }
        }
        if (i == R.id.btn_disconect) {
            final DisconectDialog disconectDialog;
            try {
                disconectDialog = new DisconectDialog(getActivity(), (IntraUserSubAppSession) appSession, (SubAppResourcesProviderManager) appResourcesProviderManager, intraUserInformation, moduleManager.getActiveIntraUserIdentity());
                disconectDialog.setTitle("Disconnect");
                disconectDialog.setDescription("Want to disconnect from");
                disconectDialog.setUsername(intraUserInformation.getName());
                disconectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                disconectDialog.show();
            } catch (CantGetActiveLoginIdentityException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_connection_request_send) {
            Toast.makeText(getActivity(), "The connection request has been sent\n you need to wait until the user responds", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.btn_connection_request_reject) {
            Toast.makeText(getActivity(), "The connection request has been rejected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateButton() {
        try {
             connectionState = moduleManager.getIntraUsersConnectionStatus(this.intraUserInformation.getPublicKey());
        } catch (CantGetIntraUserConnectionStatusException e) {
            e.printStackTrace();
        }
        switch (connectionState) {
            case BLOCKED_LOCALLY:
            case BLOCKED_REMOTELY:
            case CANCELLED_LOCALLY:
            case CANCELLED_REMOTELY:
                connectionRejected();
                break;
            case CONNECTED:
                disconnectRequest();
                break;
            case NO_CONNECTED:
            case DISCONNECTED_LOCALLY:
            case DISCONNECTED_REMOTELY:
            case ERROR:
            case DENIED_LOCALLY:
            case DENIED_REMOTELY:
                connectRequest();
                break;
            case PENDING_LOCALLY_ACCEPTANCE:
            case PENDING_REMOTELY_ACCEPTANCE:
                connectionSend();
                break;
        }
    }


    private void connectionSend() {
        connectionRequestSend.setVisibility(View.VISIBLE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    private void connectRequest() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.VISIBLE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    private void disconnectRequest() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.VISIBLE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    private void connectionRejected() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.VISIBLE);
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.profile_image);
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
    }

    @Override
    public void onMessageReceive(Context context, Intent data) {
        Bundle extras = data != null ? data.getExtras() : null;
        if (extras != null && extras.containsKey(Constants.BROADCAST_CONNECTED_UPDATE)) {
            connectionSend();
        }
        if (extras != null && extras.containsKey(Constants.BROADCAST_DISCONNECTED_UPDATE)) {
            connectRequest();
        }
    }

    @Override
    public IntentFilter getBroadcastIntentChannel() {
        return new IntentFilter(Constants.LOCAL_BROADCAST_CHANNEL);
    }
}
