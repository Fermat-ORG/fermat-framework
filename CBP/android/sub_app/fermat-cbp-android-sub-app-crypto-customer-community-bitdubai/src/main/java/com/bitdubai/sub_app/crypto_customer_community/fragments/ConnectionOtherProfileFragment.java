package com.bitdubai.sub_app.crypto_customer_community.fragments;

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
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.popups.DisconnectDialog;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;

/**
 * Created by Alejandro Bicelis on 12/2/2016.
 */
public class ConnectionOtherProfileFragment extends AbstractFermatFragment<CryptoCustomerCommunitySubAppSession, SubAppResourcesProviderManager>
        implements Dialog.OnDismissListener, Button.OnClickListener {

    public static final String ACTOR_SELECTED = "actor_selected";
    private Resources res;
    private View rootView;
    private ImageView userProfileAvatar;
    private FermatTextView userName;
    private FermatTextView currenciesExchangerates;
    private CryptoCustomerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private CryptoCustomerCommunityInformation cryptoCustomerCommunityInformation;
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
        cryptoCustomerCommunityInformation = (CryptoCustomerCommunityInformation) appSession.getData(ConnectionsWorldFragment.ACTOR_SELECTED);

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ccc_fragment_connections_other_profile, container, false);
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

        ConnectionState connectionState = this.cryptoCustomerCommunityInformation.getConnectionState();
        if(connectionState != null)
        {
            switch (connectionState) {
                case CONNECTED:
                    disconnect.setVisibility(View.VISIBLE);
                    break;
                default:
                    //show no button
            }
        }
        else {
            //show no button
        }


        //Show user image if it has one, otherwise show default user image
        try {
            userName.setText(cryptoCustomerCommunityInformation.getAlias());
            currenciesExchangerates.setText("Unknown, for now.");
            Bitmap bitmap;

            if(cryptoCustomerCommunityInformation.getImage() != null && cryptoCustomerCommunityInformation.getImage().length > 0)
                bitmap = BitmapFactory.decodeByteArray(cryptoCustomerCommunityInformation.getImage(), 0, cryptoCustomerCommunityInformation.getImage().length);
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

        if(i == R.id.btn_disconect) {
            try {
                DisconnectDialog disconnectDialog = new DisconnectDialog(getActivity(), appSession, null,
                        cryptoCustomerCommunityInformation, moduleManager.getSelectedActorIdentity());
                disconnectDialog.setTitle("Disconnect");
                disconnectDialog.setDescription("Want to disconnect from");
                disconnectDialog.setUsername(cryptoCustomerCommunityInformation.getAlias());
                disconnectDialog.setOnDismissListener(this);
                disconnectDialog.show();
            } catch (CantGetSelectedActorIdentityException|ActorIdentityNotSelectedException e) {
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

            if(connectionresult == 0) {
                disconnect.setVisibility(View.GONE);
            } else if(connectionresult == 3) {
                disconnect.setVisibility(View.VISIBLE);
            }
        }catch (Exception e) {}

    }

}
