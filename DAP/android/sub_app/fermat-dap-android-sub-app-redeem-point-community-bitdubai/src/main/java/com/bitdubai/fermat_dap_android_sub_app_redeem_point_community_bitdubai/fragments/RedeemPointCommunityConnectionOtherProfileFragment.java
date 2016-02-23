package com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.fragments;

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
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.models.Actor;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.popup.AcceptDialog; 
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.popup.ConnectDialog; 
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.popup.DisconectDialog;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.sessions.AssetRedeemPointCommunitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Date;

//import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.AssetUserWalletSubAppModuleManager;
//import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetActiveLoginIdentityException;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.Actor;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
//import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;

/**
 * Creado por Jinmy Bohorquez on 11/02/16.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class RedeemPointCommunityConnectionOtherProfileFragment extends AbstractFermatFragment implements View.OnClickListener {

    public static final String REDEEM_POINT_SELECTED = "redeemPoint";
    private String TAG = "ConnectionOtherProfileFragment";
    private Resources res;
    private View rootView;
    private AssetRedeemPointCommunitySubAppSession assetUserCommunitySubAppSession;
    private ImageView userProfileAvatar;
    private FermatTextView userName;
    //private FermatTextView userEmail;
    private FermatTextView userCryptoAddres;
    private FermatTextView userCryptoCurrency;
    private FermatTextView redeemRegistrationDate;
    private FermatTextView redeemLastConnectionDate;
    //private FermatTextView userBlockchainNetworkType;
    //private IntraUserModuleManager manager;
    private static RedeemPointCommunitySubAppModuleManager manager;
    private ErrorManager errorManager;
    private Actor actor;
    private Button connect;
    private Button disconnect;
    private int MAX = 1;
    private int OFFSET = 0;
    //private FermatTextView userStatus;
    private Button connectionRequestSend;
    private Button connectionRequestRejected;
    private Button accept;
    private ConnectionState connectionState;
    private android.support.v7.widget.Toolbar toolbar;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static RedeemPointCommunityConnectionOtherProfileFragment newInstance() {
        return new RedeemPointCommunityConnectionOtherProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // setting up  module
        assetUserCommunitySubAppSession = ((AssetRedeemPointCommunitySubAppSession) appSession);
        actor = (Actor) appSession.getData(REDEEM_POINT_SELECTED);
        manager = assetUserCommunitySubAppSession.getModuleManager();
        errorManager = appSession.getErrorManager();

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_redeem_point_community_fragment_connections_other_profile, container, false);
        toolbar = getToolbar();
        if (toolbar != null)
            toolbar.setTitle(actor.getName());
        userProfileAvatar = (ImageView) rootView.findViewById(R.id.img_user_avatar);
       // userStatus = (FermatTextView) rootView.findViewById(R.id.userPhrase);
        userName = (FermatTextView) rootView.findViewById(R.id.username);
        //userEmail = (FermatTextView) rootView.findViewById(R.id.email);
        userCryptoAddres = (FermatTextView) rootView.findViewById(R.id.cryptoAddress);
        userCryptoCurrency = (FermatTextView) rootView.findViewById(R.id.cryptoCurrency);
        //redeemRegistrationDate = (FermatTextView) rootView.findViewById(R.id.redeemRegistrationDate);
        redeemLastConnectionDate= (FermatTextView) rootView.findViewById(R.id.redeemLastConnectionDate);
        //userBlockchainNetworkType = (FermatTextView) rootView.findViewById(R.id.blockchainNetworkType);
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

        /*switch (actor.getDapConnectionState()) {
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
            }*/
            connectRequest();

        try {
            userName.setText(actor.getName());
            //userStatus.setText(actor.getPhrase());
            //userStatus.setText(actor.getName());
            //userStatus.setTextColor(Color.parseColor("#292929"));

            if(actor.getCryptoAddress() != null){
                userCryptoAddres.setText(actor.getCryptoAddress().getAddress());
                userCryptoCurrency.setText(actor.getCryptoAddress().getCryptoCurrency().getFriendlyName());
            } else{
                userCryptoAddres.setText("No");
                userCryptoCurrency.setText("None");
            }

            //redeemRegistrationDate.setText(DAPStandardFormats.DATE_FORMAT.format(new Date(actor.getRegistrationDate())));
            redeemLastConnectionDate.setText(DAPStandardFormats.DATE_FORMAT.format(new Date(actor.getRegistrationDate())));
            /*if(actor.getBlockchainNetworkType() != null) {
                userBlockchainNetworkType.setText(actor.getBlockchainNetworkType().getCode());
            }else {
                userBlockchainNetworkType.setText("None");
            }*/
            if (actor.getProfileImage() != null) {
                Bitmap bitmap;
                if (actor.getProfileImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(actor.getProfileImage(), 0, actor.getProfileImage().length);
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
            //CommonLogger.info(TAG, "User connection state " + actor.getConnectionState());
            ConnectDialog connectDialog;
            try {
                connectDialog = new ConnectDialog(getActivity(), (AssetRedeemPointCommunitySubAppSession) appSession, null, actor, manager.getActiveAssetRedeemPointIdentity());
                connectDialog.setTitle("Connection Request");
                connectDialog.setDescription("Do you want to send ");
                connectDialog.setUsername(actor.getName());
                connectDialog.setSecondDescription("a connection request");
                connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                connectDialog.show();
            } catch (CantGetIdentityRedeemPointException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_disconect) {
            //CommonLogger.info(TAG, "User connection state " + actor.getConnectionState());
            final DisconectDialog disconectDialog;
            try {
                disconectDialog = new DisconectDialog(getActivity(), (AssetRedeemPointCommunitySubAppSession) appSession, null, actor, manager.getActiveAssetRedeemPointIdentity());
                disconectDialog.setTitle("Disconnect");
                disconectDialog.setDescription("Want to disconnect from");
                disconectDialog.setUsername(actor.getName());
                disconectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        connectRequest();
                       // updateButton();
                    }
                });
                disconectDialog.show();
            } catch (CantGetIdentityRedeemPointException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_connection_accept){
            try {

                AcceptDialog notificationAcceptDialog = new AcceptDialog(getActivity(),(AssetRedeemPointCommunitySubAppSession) appSession, null, actor, manager.getActiveAssetRedeemPointIdentity());
                notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                notificationAcceptDialog.show();

            } catch (CantGetIdentityRedeemPointException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_connection_request_send) {
            //CommonLogger.info(TAG, "User connection state " + actor.getConnectionState());
            Toast.makeText(getActivity(), "The connection request has been sent\n you need to wait until the user responds", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.btn_connection_request_reject) {
           // CommonLogger.info(TAG, "User connection state " + actor.getConnectionState());
            Toast.makeText(getActivity(), "The connection request has been rejected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateButton() {
        /*try {
            connectionState = manager.getIntraUsersConnectionStatus(this.actor.getPublicKey());
        } catch (CantGetIntraUserConnectionStatusException e) {
            e.printStackTrace();
        }*/
        /*switch (connectionState) {
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
        }*/
        disconnectRequest();
    }


    private void connectionSend() {
        connectionRequestSend.setVisibility(View.VISIBLE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    private void conectionAccept(){
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        accept.setVisibility(View.VISIBLE);

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

    /*private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}
