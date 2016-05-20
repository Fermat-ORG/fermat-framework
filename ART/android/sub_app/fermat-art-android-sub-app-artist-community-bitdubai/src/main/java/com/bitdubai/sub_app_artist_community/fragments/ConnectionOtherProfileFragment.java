package com.bitdubai.sub_app_artist_community.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.commons.popups.AcceptDialog;
import com.bitdubai.sub_app_artist_community.commons.popups.CancelDialog;
import com.bitdubai.sub_app_artist_community.commons.popups.ConnectDialog;
import com.bitdubai.sub_app_artist_community.commons.popups.DisconnectDialog;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSession;

import java.util.List;
import java.util.UUID;


/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class ConnectionOtherProfileFragment extends AbstractFermatFragment<ArtistSubAppSession, SubAppResourcesProviderManager>
        implements Dialog.OnDismissListener, Button.OnClickListener {

    public static final String ACTOR_SELECTED = "actor_selected";
    private Resources res;
    private View rootView;
    private ImageView userProfileAvatar;
    private FermatTextView userName;
    private FermatTextView externalPlatform;
    private ArtistCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArtistCommunityInformation artistCommunityInformation;
    private Button connect;
    private Button disconnect;
    private Button cancel;
    private Button accept;
    private List<ArtistActorConnection> actorConnectionList;
    private UUID requestedActorConnectionId;

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
        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        artistCommunityInformation = (ArtistCommunityInformation) appSession.getData(ConnectionsWorldFragment.ACTOR_SELECTED);

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.aac_fragment_connections_other_profile, container, false);
        userProfileAvatar = (ImageView) rootView.findViewById(R.id.aac_img_user_avatar);
        userName = (FermatTextView) rootView.findViewById(R.id.aac_username);
        externalPlatform = (FermatTextView) rootView.findViewById(R.id.aac_external_platform);
        connect = (Button) rootView.findViewById(R.id.aac_btn_connect);
        connect.setOnClickListener(this);
        disconnect = (Button) rootView.findViewById(R.id.aac_btn_disconnect);
        disconnect.setOnClickListener(this);
        cancel = (Button) rootView.findViewById(R.id.aac_btn_cancel);
        cancel.setOnClickListener(this);
        accept = (Button) rootView.findViewById(R.id.aac_btn_accept);
        accept.setOnClickListener(this);

        //Show connect or disconnect button depending on actor's connection
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);

        ConnectionState connectionState = this.artistCommunityInformation.getConnectionState();

        /*try{
            ArtistCommunitySelectableIdentity selectedIdentity =
                    moduleManager.getSelectedActorIdentity();
            actorConnectionList = moduleManager.getRequestActorConnections(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType(),
                    artistCommunityInformation.getPublicKey());
        } catch (Exception e){
            //No action for now
        }*/

        if(connectionState != null)
        {
            switch (connectionState) {
                case CONNECTED:
                    disconnect.setVisibility(View.VISIBLE);
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    cancel.setVisibility(View.VISIBLE);
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    try{
                        ArtistCommunitySelectableIdentity selectedIdentity =
                                moduleManager.getSelectedActorIdentity();
                        requestedActorConnectionId = moduleManager.getConnectionId(
                                selectedIdentity.getPublicKey(),
                                selectedIdentity.getActorType(),
                                artistCommunityInformation.getPublicKey());
                        accept.setVisibility(View.VISIBLE);
                    } catch (Exception e){
                        connect.setVisibility(View.VISIBLE);
                    }

                    break;
                default:
                    connect.setVisibility(View.VISIBLE);
            }
        }
        else{
            /*try{
                boolean isActorConnectExists = !actorConnectionList.isEmpty();
                if(isActorConnectExists){
                    ArtistActorConnection artistActorConnection = actorConnectionList.get(0);
                    ConnectionState actorConnectionState = artistActorConnection.getConnectionState();
                    switch (actorConnectionState){
                        case PENDING_LOCALLY_ACCEPTANCE:
                            accept.setVisibility(View.VISIBLE);
                            break;
                        default:
                            connect.setVisibility(View.VISIBLE);
                            break;
                        }
                    } else{
                        connect.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                //For now, not other action required
                        connect.setVisibility(View.VISIBLE);
                }*/
            try{
                ArtistCommunitySelectableIdentity selectedIdentity =
                        moduleManager.getSelectedActorIdentity();
                ConnectionState actorConnectionState = moduleManager.getRequestActorConnectionState(
                        selectedIdentity.getPublicKey(),
                        selectedIdentity.getActorType(),
                        artistCommunityInformation.getPublicKey());
                switch (actorConnectionState){
                    case PENDING_LOCALLY_ACCEPTANCE:
                        accept.setVisibility(View.VISIBLE);
                        requestedActorConnectionId = moduleManager.getConnectionId(
                                selectedIdentity.getPublicKey(),
                                selectedIdentity.getActorType(),
                                artistCommunityInformation.getPublicKey());
                        break;
                    default:
                        connect.setVisibility(View.VISIBLE);
                        break;
                }
            } catch (Exception e) {
                //For now, not other action required
                connect.setVisibility(View.VISIBLE);
            }

        }


        //Show user image if it has one, otherwise show default user image
        try {
            userName.setText(artistCommunityInformation.getAlias());
            /*try{
                externalPlatform.setText(getSelectedIdentityExternalPlatform().getFriendlyName());
            }catch (Exception e){

            }*/
            //Shows the external platform
            externalPlatform.setText(
                    artistCommunityInformation
                            .getArtExternalPlatform()
                            .getFriendlyName());
            Bitmap bitmap;

            if(artistCommunityInformation.getImage() != null && artistCommunityInformation.getImage().length > 0)
                bitmap = BitmapFactory.decodeByteArray(artistCommunityInformation.getImage(), 0, artistCommunityInformation.getImage().length);
            else
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aac_profile_image);

            bitmap = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
            userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));

        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    /*private ArtExternalPlatform getSelectedIdentityExternalPlatform(){
        HashMap<ArtExternalPlatform, String> selectedIdentityExternalPlatformMap = artistCommunityInformation.getArtistExternalPlatformInformation().getExternalPlatformInformationMap();
        ArtExternalPlatform selectedIdentityExternalPlatform;
        Iterator<Map.Entry<ArtExternalPlatform, String>> entries = selectedIdentityExternalPlatformMap.entrySet().iterator();
        Map.Entry<ArtExternalPlatform, String> entry = entries.next();
        selectedIdentityExternalPlatform = entry.getKey();
        return selectedIdentityExternalPlatform;
    }*/

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.aac_btn_connect) {
            try {
                ConnectDialog connectDialog = new ConnectDialog(getActivity(), appSession, null,
                        artistCommunityInformation, moduleManager.getSelectedActorIdentity());
                connectDialog.setTitle("Connection Request");
                connectDialog.setDescription("Do you want to send ");
                connectDialog.setUsername(artistCommunityInformation.getAlias());
                connectDialog.setSecondDescription("a connection request");
                connectDialog.setOnDismissListener(this);
                connectDialog.show();
            } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
                errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
            }
        } else if(i == R.id.aac_btn_disconnect) {
            try {
                DisconnectDialog disconnectDialog = new DisconnectDialog(getActivity(), appSession, null,
                        artistCommunityInformation, moduleManager.getSelectedActorIdentity());
                disconnectDialog.setTitle("Disconnect");
                disconnectDialog.setDescription("Want to disconnect from");
                disconnectDialog.setUsername(artistCommunityInformation.getAlias());
                disconnectDialog.setOnDismissListener(this);
                disconnectDialog.show();
            } catch (CantGetSelectedActorIdentityException|ActorIdentityNotSelectedException e) {
                errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
            }
        } else if(i == R.id.aac_btn_cancel) {

            try {
                CancelDialog cancelDialog = new CancelDialog(getActivity(), appSession, null,
                        artistCommunityInformation, moduleManager.getSelectedActorIdentity());
                cancelDialog.setTitle("Cancel");
                cancelDialog.setDescription("Want to cancel connection with");
                cancelDialog.setUsername(artistCommunityInformation.getAlias());
                cancelDialog.setOnDismissListener(this);
                cancelDialog.show();
            } catch (CantGetSelectedActorIdentityException|ActorIdentityNotSelectedException e) {
                errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
            }
        } else if(i == R.id.aac_btn_accept) {
            try {
                UUID connectionId=requestedActorConnectionId;
                String alias=artistCommunityInformation.getAlias();
                /*for(ArtistActorConnection fanActorConnection : actorConnectionList){
                    switch (fanActorConnection.getConnectionState()){
                        case PENDING_LOCALLY_ACCEPTANCE:
                            connectionId = fanActorConnection.getConnectionId();
                            alias = fanActorConnection.getAlias();
                            break;
                        }
                    }*/
                AcceptDialog acceptDialog = new AcceptDialog(
                        getActivity(),
                        appSession,
                        null,
                        connectionId,
                        alias,
                        moduleManager.getSelectedActorIdentity());
                acceptDialog.setTitle("Accept");
                acceptDialog.setOnDismissListener(this);
                acceptDialog.show();
                onBackPressed();
            } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
                    errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                    Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //Get connectionresult flag, and hide/show connect/disconnect buttons
        try {
            int connectionresult = (int) appSession.getData("connectionresult");
            appSession.removeData("connectionresult");

            if(connectionresult == 1) {
                disconnect.setVisibility(View.GONE);
                connect.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
            } else if(connectionresult == 2) {
                disconnect.setVisibility(View.GONE);
                connect.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                accept.setVisibility(View.GONE);
            } else if(connectionresult == 3) {
                disconnect.setVisibility(View.VISIBLE);
                connect.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
            }
        }catch (Exception e) {}

    }

}