package com.bitdubai.sub_app.chat_community.fragments;

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
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantValidateActorConnectionStateException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.common.popups.AcceptDialog;
import com.bitdubai.sub_app.chat_community.common.popups.ConnectDialog;
import com.bitdubai.sub_app.chat_community.common.popups.DisconnectDialog;
import com.bitdubai.sub_app.chat_community.util.CommonLogger;

/**
 * ConnectionOtherProfileFragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ConnectionOtherProfileFragment extends AbstractFermatFragment<ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager>, SubAppResourcesProviderManager>
        implements View.OnClickListener {

    public static final String CHAT_USER_SELECTED = "chat_user";
    private String TAG = "ConnectionOtherProfileFragment";
    private Resources res;
    private View rootView;
    private ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> chatUserSubAppSession;
    private ImageView userProfileAvatar;
    private FermatTextView userName;
    private FermatTextView userEmail;
    private ChatActorCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private ChatActorCommunityInformation chatUserInformation;
    private Button connect;
    private ChatActorCommunitySelectableIdentity identity;
    private Button disconnect;
    private int MAX = 1;
    private int OFFSET = 0;
    private FermatTextView userStatus;
    private Button connectionRequestSend;
    private Button connectionRequestRejected;
    private Button accept;
    private ConnectionState connectionState;
    private String strConnectionState = "UNKNOWN";
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
        //chatUserSubAppSession = ((ChatUserSubAppSessionReferenceApp) appSession);
        chatUserInformation = (ChatActorCommunityInformation) appSession.getData(CHAT_USER_SELECTED);
        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        chatUserInformation = (ChatActorCommunityInformation) appSession.getData(ConnectionsWorldFragment.CHAT_USER_SELECTED);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.cht_comm_other_profile_fragment, container, false);
        toolbar = getToolbar();
        if (toolbar != null)
            toolbar.setTitle(chatUserInformation.getAlias());
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

        try {
            userName.setText(chatUserInformation.getAlias());
            connectionState=chatUserInformation.getConnectionState();
            if(connectionState != null) {
                switch (connectionState) {
                    case BLOCKED_LOCALLY:
                    case BLOCKED_REMOTELY:
                    case CANCELLED_LOCALLY:
                    case CANCELLED_REMOTELY:
                        connectionRejected();
                        strConnectionState="BLOCKED";
                        break;
                    case CONNECTED:
                        disconnectRequest();
                        strConnectionState="CONNECTED";
                        break;
                    case NO_CONNECTED:
                    case DISCONNECTED_LOCALLY:
                    case DISCONNECTED_REMOTELY:
                    case ERROR:
                    case DENIED_LOCALLY:
                    case DENIED_REMOTELY:
                        strConnectionState="DISCONNECTED";
                        connectRequest();
                        break;
                    case PENDING_LOCALLY_ACCEPTANCE:
                        conectionAccept();
                        strConnectionState="PENDING ACCEPTANCE";
                        break;
                    case PENDING_REMOTELY_ACCEPTANCE:
                        connectionSend();
                        strConnectionState="PENDING ACCEPTANCE";
                        break;
                }
                userStatus.setText(strConnectionState);//connectionState.toString());
                        userStatus.setTextColor(Color.parseColor("#292929"));
            } else connectRequest();

            if (chatUserInformation.getImage() != null) {
                Bitmap bitmap;
                if (chatUserInformation.getImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(chatUserInformation.getImage(), 0,
                            chatUserInformation.getImage().length);
                } else {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cht_comm_bg_circular_other_profile);//profile_image);
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
                userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
            } else {
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cht_comm_bg_circular_other_profile);//profile_image);
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
            CommonLogger.info(TAG, "User connection state " +
                    chatUserInformation.getConnectionState());
            ConnectDialog connectDialog;
            try {
                connectDialog =
                        new ConnectDialog(getActivity(), appSession, null,
                                chatUserInformation, moduleManager.getSelectedActorIdentity());
                connectDialog.setTitle("Connection Request");
                connectDialog.setDescription("Are you sure you want to send a connection request to this contact?");
                //connectDialog.setUsername(chatUserInformation.getAlias());
                //connectDialog.setSecondDescription("a connection request?");
                connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                connectDialog.show();
            } catch ( CantGetSelectedActorIdentityException
                    | ActorIdentityNotSelectedException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_disconect) {
            CommonLogger.info(TAG, "User connection state " +
                    chatUserInformation.getConnectionState());
            final DisconnectDialog disconnectDialog;
            try {
                disconnectDialog =
                        new DisconnectDialog(getActivity(), appSession, null,
                                chatUserInformation, moduleManager.getSelectedActorIdentity());
                disconnectDialog.setTitle("Confirm Delete Connection");
                disconnectDialog.setDescription("Are you sure you want to delete this connection?" );
//                disconnectDialog.setUsername(chatUserInformation.getAlias()+"?");
                disconnectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                disconnectDialog.show();
            } catch ( CantGetSelectedActorIdentityException
                    | ActorIdentityNotSelectedException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_connection_accept){
            try {
                AcceptDialog notificationAcceptDialog =
                        new AcceptDialog(getActivity(),appSession, null,
                                chatUserInformation, moduleManager.getSelectedActorIdentity());
                notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                notificationAcceptDialog.show();

            } catch ( CantGetSelectedActorIdentityException
                    | ActorIdentityNotSelectedException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_connection_request_send) {
            CommonLogger.info(TAG, "User connection state "
                    + chatUserInformation.getConnectionState());
            Toast.makeText(getActivity(), "The connection request has been sent\n you need to wait until the user responds", Toast.LENGTH_SHORT).show();
            ConnectDialog connectDialog;
            try {
                connectDialog =
                        new ConnectDialog(getActivity(), appSession, null,
                                chatUserInformation, moduleManager.getSelectedActorIdentity());
                connectDialog.setTitle("Resend Connection Request");
                connectDialog.setDescription("Do you want to resend a connection request to this contact?");
                connectDialog.setUsername(chatUserInformation.getAlias());
                //connectDialog.setSecondDescription("a connection request?");
                connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                connectDialog.show();
            } catch ( CantGetSelectedActorIdentityException
                    | ActorIdentityNotSelectedException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_connection_request_reject) {
            CommonLogger.info(TAG, "User connection state "
                    + chatUserInformation.getConnectionState());
            Toast.makeText(getActivity(), "The connection request has been rejected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateButton() {
        try {
            connectionState
                    = moduleManager.getActorConnectionState(chatUserInformation.getPublicKey());
            if(connectionState!=null)  {
                switch (connectionState) {
                    case BLOCKED_LOCALLY:
                    case BLOCKED_REMOTELY:
                    case CANCELLED_LOCALLY:
                    case CANCELLED_REMOTELY:
                        connectionRejected();
                        strConnectionState="BLOCKED";
                        break;
                    case CONNECTED:
                        disconnectRequest();
                        strConnectionState="CONNECTED";
                        break;
                    case NO_CONNECTED:
                    case DISCONNECTED_LOCALLY:
                    case DISCONNECTED_REMOTELY:
                    case ERROR:
                    case DENIED_LOCALLY:
                    case DENIED_REMOTELY:
                        connectRequest();
                        strConnectionState="DISCONNECTED";
                        break;
                    case PENDING_REMOTELY_ACCEPTANCE:
                        connectionSend();
                        strConnectionState="PENDING ACCEPTANCE";
                        break;
                    case PENDING_LOCALLY_ACCEPTANCE:
                        conectionAccept();
                        strConnectionState="PENDING ACCEPTANCE";
                        break;
                }
                userStatus.setText(strConnectionState);//connectionState.toString());
                userStatus.setTextColor(Color.parseColor("#292929"));
            }else  connectRequest();
        } catch (CantValidateActorConnectionStateException e) {
            e.printStackTrace();
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
        accept.setBackgroundResource(R.drawable.cht_comm_bg_shape_blue);
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

        return ImagesUtils.getRoundedBitmap(res, R.drawable.cht_comm_bg_circular_other_profile);//profile_image);
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetChatUserIdentityException {
    }

}
