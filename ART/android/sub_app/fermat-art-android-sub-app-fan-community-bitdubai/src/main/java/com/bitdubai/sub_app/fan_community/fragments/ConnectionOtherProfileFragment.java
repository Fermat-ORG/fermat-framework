package com.bitdubai.sub_app.fan_community.fragments;

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
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.fan_community.R;
import com.bitdubai.sub_app.fan_community.commons.popups.ConnectDialog;
import com.bitdubai.sub_app.fan_community.commons.popups.DisconnectDialog;
import com.bitdubai.sub_app.fan_community.sessions.FanCommunitySubAppSession;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class ConnectionOtherProfileFragment extends
        AbstractFermatFragment<FanCommunitySubAppSession,
                SubAppResourcesProviderManager>
        implements
        Dialog.OnDismissListener,
        Button.OnClickListener {

    public static final String ACTOR_SELECTED = "actor_selected";
    private Resources res;
    private View rootView;
    private ImageView userProfileAvatar;
    private FermatTextView userName;
    private FermatTextView externalPlatform;
    private FanCommunityModuleManager moduleManager;
    private ErrorManager errorManager;
    private FanCommunityInformation fanCommunityInformation;
    private Button connect;
    private Button disconnect;
    private Button cancel;

    /**
     * Create a new instance of this fragment
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
        fanCommunityInformation = (FanCommunityInformation) appSession.getData(
                ConnectionsWorldFragment.ACTOR_SELECTED);

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.afc_fragment_connections_other_profile, container, false);
        userProfileAvatar = (ImageView) rootView.findViewById(R.id.afc_img_user_avatar);
        userName = (FermatTextView) rootView.findViewById(R.id.afc_username);
        externalPlatform = (FermatTextView) rootView.findViewById(R.id.afc_external_platform);
        connect = (Button) rootView.findViewById(R.id.afc_btn_connect);
        connect.setOnClickListener(this);
        disconnect = (Button) rootView.findViewById(R.id.afc_btn_disconnect);
        disconnect.setOnClickListener(this);
        cancel = (Button) rootView.findViewById(R.id.afc_btn_cancel);
        cancel.setOnClickListener(this);

        //Show connect or disconnect button depending on actor's connection
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);

        ConnectionState connectionState = this.fanCommunityInformation.getConnectionState();
        if(connectionState != null)
        {
            switch (connectionState) {
                case CONNECTED:
                    disconnect.setVisibility(View.VISIBLE);
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    cancel.setVisibility(View.VISIBLE);
                    break;
                default:
                    connect.setVisibility(View.VISIBLE);
            }
        }
        else {
            connect.setVisibility(View.VISIBLE);
        }

        //Show user image if it has one, otherwise show default user image
        try {
            userName.setText(fanCommunityInformation.getAlias());
            try{
                externalPlatform.setText(getSelectedIdentityExternalPlatform().getFriendlyName());
            }catch (Exception e){

            }            Bitmap bitmap;

            if(fanCommunityInformation.getImage() != null && fanCommunityInformation.getImage().length > 0)
                bitmap = BitmapFactory.decodeByteArray(
                        fanCommunityInformation.getImage(),
                        0,
                        fanCommunityInformation.getImage().length);
            else
                bitmap = BitmapFactory.decodeResource(
                        getResources(),
                        R.drawable.afc_profile_image);

            bitmap = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
            userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));

        } catch (Exception ex) {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Oooops! recovering from system error",
                    Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }
    private ArtExternalPlatform getSelectedIdentityExternalPlatform(){
        HashMap<ArtExternalPlatform, String> selectedIdentityExternalPlatformMap = fanCommunityInformation.getFanExternalPlatformInformation().getExternalPlatformInformationMap();
        ArtExternalPlatform selectedIdentityExternalPlatform;
        Iterator<Map.Entry<ArtExternalPlatform, String>> entries = selectedIdentityExternalPlatformMap.entrySet().iterator();
        Map.Entry<ArtExternalPlatform, String> entry = entries.next();
        selectedIdentityExternalPlatform = entry.getKey();
        return selectedIdentityExternalPlatform;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.afc_btn_connect) {
            try {
                ConnectDialog connectDialog = new ConnectDialog(getActivity(), appSession, null,
                        fanCommunityInformation, moduleManager.getSelectedActorIdentity());
                connectDialog.setTitle("Connection Request");
                connectDialog.setDescription("Do you want to send ");
                connectDialog.setUsername(fanCommunityInformation.getAlias());
                connectDialog.setSecondDescription("a connection request");
                connectDialog.setOnDismissListener(this);
                connectDialog.show();
            } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
                errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
            }
        } else if(i == R.id.afc_btn_disconnect) {
            try {
                DisconnectDialog disconnectDialog = new DisconnectDialog(
                        getActivity(), appSession, null,
                        fanCommunityInformation, moduleManager.getSelectedActorIdentity());
                disconnectDialog.setTitle("Disconnect");
                disconnectDialog.setDescription("Want to disconnect from");
                disconnectDialog.setUsername(fanCommunityInformation.getAlias());
                disconnectDialog.setOnDismissListener(this);
                disconnectDialog.show();
            } catch (CantGetSelectedActorIdentityException |ActorIdentityNotSelectedException e) {
                errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
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
            } else if(connectionresult == 2) {
                disconnect.setVisibility(View.GONE);
                connect.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
            } else if(connectionresult == 3) {
                disconnect.setVisibility(View.VISIBLE);
                connect.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            }
        }catch (Exception e) {}

    }
}
