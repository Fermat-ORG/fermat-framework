package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments;

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
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models.Actor;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.popup.AcceptDialog;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.popup.ConnectDialog;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.popup.DisconectDialog;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.all_definition.DAPConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Creado por Jinmy Bohorquez on 09/02/16.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class UsersCommunityConnectionOtherProfileFragment extends AbstractFermatFragment
        implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String USER_SELECTED = "user";
    private List<Actor> actors;
    private String TAG = "ConnectionOtherProfileFragment";
    private Resources res;
    private View rootView;
    private AssetUserCommunitySubAppSession assetUserCommunitySubAppSession;
    private ImageView userProfileAvatar;
    private FermatTextView userName;
    //private FermatTextView userEmail;
    private FermatTextView userCryptoAddres;
    private FermatTextView userCryptoCurrency;
    private FermatTextView userBlockchainNetworkType;
    private FermatTextView userRegistrationDate;
    private FermatTextView userLastConnectionDate;
    //private IntraUserModuleManager manager;
    private static AssetUserCommunitySubAppModuleManager manager;
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
    private DAPConnectionState connectionState;
    private android.support.v7.widget.Toolbar toolbar;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static UsersCommunityConnectionOtherProfileFragment newInstance() {
        return new UsersCommunityConnectionOtherProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // setting up  module
        assetUserCommunitySubAppSession = ((AssetUserCommunitySubAppSession) appSession);
        actor = (Actor) appSession.getData(USER_SELECTED);
        manager = assetUserCommunitySubAppSession.getModuleManager();
        errorManager = appSession.getErrorManager();

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_user_community_fragment_connections_other_profile, container, false);
        toolbar = getToolbar();
//        if (toolbar != null)
//            toolbar.setTitle(actor.getName());
        userProfileAvatar = (ImageView) rootView.findViewById(R.id.img_user_avatar);
        //userStatus = (FermatTextView) rootView.findViewById(R.id.userPhrase);
        userName = (FermatTextView) rootView.findViewById(R.id.username);
        //userEmail = (FermatTextView) rootView.findViewById(R.id.email);
        userCryptoAddres = (FermatTextView) rootView.findViewById(R.id.cryptoAddress);
        userCryptoCurrency = (FermatTextView) rootView.findViewById(R.id.cryptoCurrency);
        userBlockchainNetworkType = (FermatTextView) rootView.findViewById(R.id.blockchainNetworkType);
        //userRegistrationDate = (FermatTextView) rootView.findViewById(R.id.userRegistrationDate);
        userLastConnectionDate = (FermatTextView) rootView.findViewById(R.id.userLastConnectionDate);
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

        updateButton();

        try {
            userName.setText(actor.getName());
            //userStatus.setText(actor.getPhrase());
            //userStatus.setText(actor.getName());
            //userStatus.setTextColor(Color.parseColor("#292929"));

            if (actor.getCryptoAddress() != null) {
                userCryptoAddres.setText(actor.getCryptoAddress().getAddress());
                userCryptoCurrency.setText(actor.getCryptoAddress().getCryptoCurrency().getFriendlyName());
                disconnectRequest();
            } else {
                userCryptoAddres.setText("No");
                userCryptoCurrency.setText("None");
                connectRequest();
            }

            if (actor.getBlockchainNetworkType() != null) {
                userBlockchainNetworkType.setText(actor.getBlockchainNetworkType().toString().replace("_", " "));
            } else {
                userBlockchainNetworkType.setText("None");
            }

            //userRegistrationDate.setText(DAPStandardFormats.DATE_FORMAT.format(new Date(actor.getRegistrationDate())));
            userLastConnectionDate.setText(DAPStandardFormats.DATE_FORMAT.format(new Date(actor.getLastConnectionDate())));

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
//            try {
            connectDialog = new ConnectDialog(getActivity(),
                    (AssetUserCommunitySubAppSession) appSession,
                    null,
                    actor,
                    null);
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
//            } catch (CantGetIdentityAssetUserException e) {
//                e.printStackTrace();
//
//            }
        }
        if (i == R.id.btn_disconect) {
            //CommonLogger.info(TAG, "User connection state " + actor.getConnectionState());
            final DisconectDialog disconectDialog;
//            try {
            disconectDialog = new DisconectDialog(getActivity(),
                    (AssetUserCommunitySubAppSession) appSession,
                    null,
                    actor,
                    null);

            disconectDialog.setTitle("Disconnect");
            disconectDialog.setDescription("Want to disconnect from");
            disconectDialog.setUsername(actor.getName());
            disconectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    updateButton();
                }
            });
            disconectDialog.show();
//            } catch (CantGetIdentityAssetUserException e) {
//                e.printStackTrace();
//            }
        }
        if (i == R.id.btn_connection_accept) {
//            try {
            AcceptDialog notificationAcceptDialog = new AcceptDialog(getActivity(),
                    (AssetUserCommunitySubAppSession) appSession,
                    null,
                    actor,
                    null);
            notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    updateButton();
                }
            });
            notificationAcceptDialog.show();

//            } catch (CantGetIdentityAssetUserException e) {
//                e.printStackTrace();
//            }
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
        try {
            connectionState = manager.getActorRegisteredDAPConnectionState(this.actor.getActorPublicKey());
        } catch (CantGetAssetUserActorsException e) {
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
                disconnectRequest();
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
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    private void connectionAccept() {
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

    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code) {
            case DAPConstants.DAP_UPDATE_VIEW_ANDROID:
                onRefresh();
                updateButton();
                break;
            default:
                super.onUpdateViewOnUIThread(code);
        }
    }

    private synchronized List<Actor> getProfileData() {
        List<AssetUserActorRecord> tempActor = new ArrayList<>();
        ActorAssetUser actorAssetUser;
        actors = new ArrayList<>();

        try {
            actorAssetUser = manager.getActorUser(actor.getActorPublicKey());

            tempActor.add(new AssetUserActorRecord(actorAssetUser.getActorPublicKey(),
                    actorAssetUser.getName(),
                    actorAssetUser.getAge(),
                    actorAssetUser.getGenders(),
                    actorAssetUser.getDapConnectionState(),
                    actorAssetUser.getLocationLatitude(),
                    actorAssetUser.getLocationLongitude(),
                    actorAssetUser.getCryptoAddress(),
                    actorAssetUser.getRegistrationDate(),
                    actorAssetUser.getLastConnectionDate(),
                    actorAssetUser.getBlockchainNetworkType(),
                    actorAssetUser.getType(),
                    actorAssetUser.getProfileImage()));

            if (tempActor.size() > 0) {
                for (AssetUserActorRecord record : tempActor) {
                        actors.add((new Actor(record)));
                }
            }
        } catch (CantGetAssetUserActorsException e) {
            e.printStackTrace();
        } catch (CantAssetUserActorNotFoundException e) {
            e.printStackTrace();
        }
        return actors;
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
                actors = (ArrayList<Actor>) result[0];
                if (actors.get(0).getCryptoAddress() != null) {
                    userCryptoAddres.setText(actors.get(0).getCryptoAddress().getAddress());
                    userCryptoCurrency.setText(actors.get(0).getCryptoAddress().getCryptoCurrency().getFriendlyName());
                    disconnectRequest();
                } else {
                    userCryptoAddres.setText("No");
                    userCryptoCurrency.setText("None");
//                connectRequest();
                }
                if (actors.get(0).getBlockchainNetworkType() != null) {
                    userBlockchainNetworkType.setText(actors.get(0).getBlockchainNetworkType().toString().replace("_", " "));
                } else {
                    userBlockchainNetworkType.setText("None");
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
