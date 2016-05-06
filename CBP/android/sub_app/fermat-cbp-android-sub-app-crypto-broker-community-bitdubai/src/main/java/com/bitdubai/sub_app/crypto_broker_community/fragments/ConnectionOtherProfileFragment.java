package com.bitdubai.sub_app.crypto_broker_community.fragments;

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
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.common.popups.ConnectDialog;
import com.bitdubai.sub_app.crypto_broker_community.common.popups.DisconnectDialog;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ConnectionOtherProfileFragment extends AbstractFermatFragment<CryptoBrokerCommunitySubAppSession, SubAppResourcesProviderManager>
        implements Dialog.OnDismissListener, Button.OnClickListener {

    public static final String ACTOR_SELECTED = "actor_selected";
    private Resources res;
    private View rootView;
    private ImageView userProfileAvatar;
    private FermatTextView userName;
    private FermatTextView currenciesExchangerates;
    private CryptoBrokerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private CryptoBrokerCommunityInformation cryptoBrokerCommunityInformation;
    private Button connect;
    private Button disconnect;
    private Button cancel;

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
        cryptoBrokerCommunityInformation = (CryptoBrokerCommunityInformation) appSession.getData(ConnectionsWorldFragment.ACTOR_SELECTED);

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.cbc_fragment_connections_other_profile, container, false);
        userProfileAvatar = (ImageView) rootView.findViewById(R.id.img_user_avatar);
        userName = (FermatTextView) rootView.findViewById(R.id.username);
        currenciesExchangerates = (FermatTextView) rootView.findViewById(R.id.currenciesexchangerates);
        connect = (Button) rootView.findViewById(R.id.btn_conect);
        connect.setOnClickListener(this);
        disconnect = (Button) rootView.findViewById(R.id.btn_disconect);
        disconnect.setOnClickListener(this);
        cancel = (Button) rootView.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);

        //Show connect or disconnect button depending on actor's connection
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);

        ConnectionState connectionState = this.cryptoBrokerCommunityInformation.getConnectionState();
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
        else
            connect.setVisibility(View.VISIBLE);


        //Show user image if it has one, otherwise show default user image
        try {
            userName.setText(cryptoBrokerCommunityInformation.getAlias());
            currenciesExchangerates.setText("Unknown, for now.");
            Bitmap bitmap;

            if(cryptoBrokerCommunityInformation.getImage() != null && cryptoBrokerCommunityInformation.getImage().length > 0)
                bitmap = BitmapFactory.decodeByteArray(cryptoBrokerCommunityInformation.getImage(), 0, cryptoBrokerCommunityInformation.getImage().length);
             else
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);

            bitmap = Bitmap.createScaledBitmap(bitmap, 110, 110, true);
            userProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));

        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();

        if(i == R.id.btn_conect) {
            try {
                ConnectDialog connectDialog = new ConnectDialog(getActivity(), appSession, null,
                        cryptoBrokerCommunityInformation, moduleManager.getSelectedActorIdentity());
                connectDialog.setTitle("Connection Request");
                connectDialog.setDescription("Do you want to send ");
                connectDialog.setUsername(cryptoBrokerCommunityInformation.getAlias());
                connectDialog.setSecondDescription("a connection request");
                connectDialog.setOnDismissListener(this);
                connectDialog.show();
            } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
                errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
            }
        } else if(i == R.id.btn_disconect) {
            try {
                DisconnectDialog disconnectDialog = new DisconnectDialog(getActivity(), appSession, null,
                        cryptoBrokerCommunityInformation, moduleManager.getSelectedActorIdentity());
                disconnectDialog.setTitle("Disconnect");
                disconnectDialog.setDescription("Want to disconnect from");
                disconnectDialog.setUsername(cryptoBrokerCommunityInformation.getAlias());
                disconnectDialog.setOnDismissListener(this);
                disconnectDialog.show();
            } catch (CantGetSelectedActorIdentityException|ActorIdentityNotSelectedException e) {
                errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
            }
        } else if(i == R.id.btn_cancel) {

            //TODO: verificar el getModuleManager().cancelCryptoBroker(cryptoBrokerCommunityInformation.getConnectionId());
            //TODO: antes de habilitar esto.
//            try {
//                CancelDialog cancelDialog = new CancelDialog(getActivity(), appSession, null,
//                        cryptoBrokerCommunityInformation, moduleManager.getSelectedActorIdentity());
//                cancelDialog.setTitle("Cancel");
//                cancelDialog.setDescription("Want to cancel connection with");
//                cancelDialog.setUsername(cryptoBrokerCommunityInformation.getAlias());
//                cancelDialog.setOnDismissListener(this);
//                cancelDialog.show();
//            } catch (CantGetSelectedActorIdentityException|ActorIdentityNotSelectedException e) {
//                errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
//                Toast.makeText(getContext(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
//            }
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
