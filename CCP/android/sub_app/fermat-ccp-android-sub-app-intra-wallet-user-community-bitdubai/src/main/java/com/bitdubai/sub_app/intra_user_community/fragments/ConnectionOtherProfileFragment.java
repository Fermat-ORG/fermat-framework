package com.bitdubai.sub_app.intra_user_community.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUserConnectionStatusException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.common.popups.AcceptDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.ConnectDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.DisconectDialog;

import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;

/**
 * Creado por Jose Manuel De Sousa on 29/11/15.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ConnectionOtherProfileFragment extends AbstractFermatFragment<ReferenceAppFermatSession<IntraUserModuleManager> ,ResourceProviderManager>implements View.OnClickListener {

    public static final String INTRA_USER_SELECTED = "intra_user";
    private String TAG = "ConnectionOtherProfileFragment";
    private Resources res;
    private View rootView;
    private ReferenceAppFermatSession<IntraUserModuleManager> intraUserSubAppSession;
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
    private FermatTextView userStatus;
    private Button connectionRequestSend;
    private Button connectionRequestRejected;
    private Button accept;
    private IntraWalletUserActorManager intraWalletUserActorManager;
    private ConnectionState connectionState;
    private android.support.v7.widget.Toolbar toolbar;

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
        setHasOptionsMenu(true);
        // setting up  module
        intraUserSubAppSession = appSession;
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
        toolbar = getToolbar();
        if (toolbar != null)
            toolbar.setTitle(intraUserInformation.getName());
        userProfileAvatar = (ImageView) rootView.findViewById(R.id.img_user_avatar);
        userStatus = (FermatTextView) rootView.findViewById(R.id.userPhrase);
        userName = (FermatTextView) rootView.findViewById(R.id.username);
        userEmail = (FermatTextView) rootView.findViewById(R.id.email);
        connectionRequestSend = (Button) rootView.findViewById(R.id.btn_connection_request_send);
        connectionRequestRejected = (Button) rootView.findViewById(R.id.btn_connection_request_reject);
        connect = (Button) rootView.findViewById(R.id.btn_conect);
        accept = (Button) rootView.findViewById(R.id.btn_connection_accept);
        disconnect = (Button) rootView.findViewById(R.id.btn_disconect);
        connectionRequestSend.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setOnClickListener(this);
        connectionRequestSend.setOnClickListener(this);
        connect.setOnClickListener(this);
        disconnect.setOnClickListener(this);
        accept.setOnClickListener(this);

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
                    conectionAccept();
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    connectionSend();
                    break;
            }

        try {
            userName.setText(intraUserInformation.getName());
            userStatus.setText(intraUserInformation.getPhrase());
            userStatus.setTextColor(Color.parseColor("#292929"));
            if (intraUserInformation.getProfileImage() != null) {
                Bitmap bitmap;
                if (intraUserInformation.getProfileImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(intraUserInformation.getProfileImage(), 0, intraUserInformation.getProfileImage().length);
                } else {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
                userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
            } else {
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
                bitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
                userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_conect) {
            CommonLogger.info(TAG, "User connection state " + intraUserInformation.getConnectionState());
            ConnectDialog connectDialog;
            try {
                connectDialog = new ConnectDialog(getActivity(), (ReferenceAppFermatSession) appSession, null, intraUserInformation, moduleManager.getActiveIntraUserIdentity());
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
            CommonLogger.info(TAG, "User connection state " + intraUserInformation.getConnectionState());
            final DisconectDialog disconectDialog;
            try {
                disconectDialog = new DisconectDialog(getActivity(), (ReferenceAppFermatSession) appSession, null, intraUserInformation, moduleManager.getActiveIntraUserIdentity());
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
        if (i == R.id.btn_connection_accept){
            try {

                AcceptDialog notificationAcceptDialog = new AcceptDialog(getActivity(),(ReferenceAppFermatSession) appSession, null, intraUserInformation, moduleManager.getActiveIntraUserIdentity());
                notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                notificationAcceptDialog.show();

            } catch ( CantGetActiveLoginIdentityException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_connection_request_send) {
            CommonLogger.info(TAG, "User connection state " + intraUserInformation.getConnectionState());
            Toast.makeText(getActivity(), "The connection request has been sent\n you need to wait until the user responds", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.btn_connection_request_reject) {
            CommonLogger.info(TAG, "User connection state " + intraUserInformation.getConnectionState());
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
            case PENDING_REMOTELY_ACCEPTANCE:
                connectionSend();
                break;

            case PENDING_LOCALLY_ACCEPTANCE:
                conectionAccept();
                break;
        }
    }


    private void connectionSend() {
        connectionRequestSend.setVisibility(View.VISIBLE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        accept.setVisibility(View.GONE);
    }

    private void conectionAccept(){
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        accept.setVisibility(View.VISIBLE);
       // accept.setBackgroundResource(R.drawable.bg_shape_blue);

    }

    private void connectRequest() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.VISIBLE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        accept.setVisibility(View.GONE);
    }

    private void disconnectRequest() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.VISIBLE);
        connectionRequestRejected.setVisibility(View.GONE);
        accept.setVisibility(View.GONE);
    }

    private void connectionRejected() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.VISIBLE);
        accept.setVisibility(View.GONE);
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.profile_image);
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}
