package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.models.ActorIssuer;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.popup.AcceptDialog;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.popup.ConnectDialog;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.AssetIssuerCommunitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.all_definition.DAPConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantAssetIssuerActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Creado por Jinmy Bohorquez on 09/02/16.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class IssuerCommunityConnectionOtherProfileFragment extends AbstractFermatFragment
        implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String ISSUER_SELECTED = "issuer";
    private String TAG = "ConnectionOtherProfileFragment";
    private Resources res;
    private View rootView;
    private AssetIssuerCommunitySubAppSession assetIssuerCommunitySubAppSession;
    private ImageView issuerProfileAvatar;
    private FermatTextView issuerName;
    private FermatTextView issuerExtendedKey;
    private FermatTextView issuerRegistrationDate;
    private FermatTextView issuerLastConnectionDate;

    private static AssetIssuerCommunitySubAppModuleManager manager;
    private ErrorManager errorManager;
    private ActorIssuer actorIssuer;
    private ActorAssetIssuer actorAssetIssuer;
    private Button connect;
    //private Button disconnect;
    private int MAX = 1;
    private int OFFSET = 0;
    //private FermatTextView issuerStatus;
    private Button connectionRequestSend;
    private Button connectionRequestRejected;
    private Button accept;
    private DAPConnectionState connectionState;
    private android.support.v7.widget.Toolbar toolbar;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static IssuerCommunityConnectionOtherProfileFragment newInstance() {
        return new IssuerCommunityConnectionOtherProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // setting up  module
        assetIssuerCommunitySubAppSession = ((AssetIssuerCommunitySubAppSession) appSession);
        actorIssuer = (ActorIssuer) appSession.getData(ISSUER_SELECTED);
        manager = assetIssuerCommunitySubAppSession.getModuleManager();
        errorManager = appSession.getErrorManager();

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_issuer_community_fragment_connections_other_profile, container, false);
        toolbar = getToolbar();
//        if (toolbar != null)
//            toolbar.setTitle(actorIssuer.getRecord().getName());
        issuerProfileAvatar = (ImageView) rootView.findViewById(R.id.img_user_avatar);
        issuerName = (FermatTextView) rootView.findViewById(R.id.username);
        issuerExtendedKey = (FermatTextView) rootView.findViewById(R.id.userExtendedKey);
        //issuerRegistrationDate = (FermatTextView) rootView.findViewById(R.id.userRegistrationDate);
        issuerLastConnectionDate = (FermatTextView) rootView.findViewById(R.id.issuerLastConnectionDate);
        connectionRequestSend = (Button) rootView.findViewById(R.id.btn_connection_request_send);
        connectionRequestRejected = (Button) rootView.findViewById(R.id.btn_connection_request_reject);
        connect = (Button) rootView.findViewById(R.id.btn_conect);
        accept = (Button) rootView.findViewById(R.id.btn_connection_accept);
        //disconnect = (Button) rootView.findViewById(R.id.btn_disconect);
        connectionRequestSend.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        //disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setOnClickListener(this);
        connectionRequestSend.setOnClickListener(this);
        connect.setOnClickListener(this);
        //disconnect.setOnClickListener(this);

        updateButton();

        try {
            issuerName.setText(actorIssuer.getRecord().getName());
            //issuerStatus.setText(actorIssuer.getPhrase());
            //issuerStatus.setText(actorIssuer.getRecord().getDescription());
            //issuerStatus.setTextColor(Color.parseColor("#292929"));
            if (actorIssuer.getRecord().getProfileImage() != null) {
                Bitmap bitmap;
                if (actorIssuer.getRecord().getProfileImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(actorIssuer.getRecord().getProfileImage(), 0, actorIssuer.getRecord().getProfileImage().length);
                } else {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
                issuerProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
            } else {
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_image);
                bitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
                issuerProfileAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
            }

            if (actorIssuer.getRecord().getExtendedPublicKey() != null) {
                issuerExtendedKey.setText(actorIssuer.getRecord().getExtendedPublicKey());
            } else {
                issuerExtendedKey.setText("None");
            }

            //issuerRegistrationDate.setText(DAPStandardFormats.DATE_FORMAT.format(new Date(actorIssuer.getRecord().getRegistrationDate())));
            issuerLastConnectionDate.setText(DAPStandardFormats.DATE_FORMAT.format(new Date(actorIssuer.getRecord().getLastConnectionDate())));


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
//            Toast.makeText(getActivity(), "Fixing for your convenience.", Toast.LENGTH_SHORT).show();

            //CommonLogger.info(TAG, "User connection state " + actorIssuer.getConnectionState());
            ConnectDialog connectDialog;
//            try {
                connectDialog = new ConnectDialog(getActivity(),
                        (AssetIssuerCommunitySubAppSession) appSession,
                        null,
                        actorIssuer,
                        null);
//                        manager.getActiveAssetIssuerIdentity());
                connectDialog.setTitle("Connection Request");
                connectDialog.setDescription("Do you want to send ");
                connectDialog.setUsername(actorIssuer.getRecord().getName());
                connectDialog.setSecondDescription("a connection request");
                connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                connectDialog.show();
//            } catch (CantGetIdentityAssetIssuerException e) {
//                e.printStackTrace();
//
//            }
        }
        /*if (i == R.id.btn_disconect) {
            //CommonLogger.info(TAG, "User connection state " + actorIssuer.getConnectionState());
            final DisconectDialog disconectDialog;
            try {
                disconectDialog = new DisconectDialog(getActivity(), (AssetIssuerCommunitySubAppSession) appSession, null, actorIssuer, manager.getActiveAssetIssuerIdentity());
                disconectDialog.setTitle("Disconnect");
                disconectDialog.setDescription("Want to disconnect from");
                disconectDialog.setUsername(actorIssuer.getRecord().getName());
                disconectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                       // connectRequest();
                        // updateButton();
                    }
                });
                disconectDialog.show();
            } catch (CantGetIdentityAssetIssuerException e) {
                e.printStackTrace();
            }
        }*/
        if (i == R.id.btn_connection_accept) {
            Toast.makeText(getActivity(), "The connection was accepted successfully.\n It can not be unmade", Toast.LENGTH_SHORT).show();
//            try {
//                AcceptDialog notificationAcceptDialog = new AcceptDialog(getActivity(),
//                        (AssetIssuerCommunitySubAppSession) appSession,
//                        null,
//                        actorIssuer,
//                        null);
////                        manager.getActiveAssetIssuerIdentity());
//                notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        updateButton();
//                    }
//                });
//                notificationAcceptDialog.show();

//            } catch (CantGetIdentityAssetIssuerException e) {
//                e.printStackTrace();
//            }
        }
        if (i == R.id.btn_connection_request_send) {
            //CommonLogger.info(TAG, "User connection state " + actorIssuer.getConnectionState());
            Toast.makeText(getActivity(), "The connection request has been sent\n you need to wait until the user responds", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.btn_connection_request_reject) {
            // CommonLogger.info(TAG, "User connection state " + actorIssuer.getConnectionState());
            Toast.makeText(getActivity(), "The connection request has been rejected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateButton() {
        try {
            connectionState = manager.getActorIssuerRegisteredDAPConnectionState(this.actorIssuer.getRecord().getActorPublicKey());
        } catch (CantGetAssetIssuerActorsException e) {
            e.printStackTrace();
        }

        updateStateConnection(connectionState);
        onRefresh();
    }

    private void updateStateConnection(DAPConnectionState dapConnectionState) {

        switch (dapConnectionState) {
            case BLOCKED_LOCALLY:
            case BLOCKED_REMOTELY:
            case CANCELLED_LOCALLY:
            case CANCELLED_REMOTELY:
                connectionRejected();
                break;
            case CONNECTED_ONLINE:
            case CONNECTED_OFFLINE:
                connectionAccept();
                break;
            case DISCONNECTED_LOCALLY:
            case DISCONNECTED_REMOTELY:
            case DENIED_LOCALLY:
            case DENIED_REMOTELY:
            case REGISTERED_ONLINE:
            case REGISTERED_OFFLINE:
                connectRequest();
                break;
            case PENDING_LOCALLY:
                connectionAccept();
                break;
            case PENDING_REMOTELY:
            case CONNECTING:
                connectionSend();
                break;
        }
    }

    private void connectionSend() {
        connectionRequestSend.setVisibility(View.VISIBLE);
        connect.setVisibility(View.GONE);
        //disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    private void connectionAccept() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        //disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        accept.setVisibility(View.VISIBLE);

    }

    private void connectRequest() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.VISIBLE);
        //disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    /*private void disconnectRequest() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.VISIBLE);
        connectionRequestRejected.setVisibility(View.GONE);
    }*/

    private void connectionRejected() {
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        //disconnect.setVisibility(View.GONE);
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

    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code) {
            case DAPConstants.DAP_UPDATE_VIEW_ANDROID:
                updateButton();
                break;
            default:
                super.onUpdateViewOnUIThread(code);
        }
    }

    private synchronized ActorAssetIssuer getProfileData() {
        try {
            actorAssetIssuer = manager.getActorIssuer(actorIssuer.getRecord().getActorPublicKey());

        } catch (CantGetAssetIssuerActorsException e) {
            e.printStackTrace();
        } catch (CantAssetIssuerActorNotFoundException e) {
            e.printStackTrace();
        }
        return actorAssetIssuer;
    }
    @Override
    public void onRefresh() {
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                return getProfileData();
            }
        };
        worker.setContext(getActivity());
        worker.setCallBack(new FermatWorkerCallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onPostExecute(Object... result) {
                actorAssetIssuer = (ActorAssetIssuer) result[0];
                if (actorAssetIssuer.getExtendedPublicKey() != null) {
                    issuerExtendedKey.setText(actorAssetIssuer.getExtendedPublicKey());
                } else {
                    issuerExtendedKey.setText("None");
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                if (getActivity() != null)
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        });
        worker.execute();
    }
}