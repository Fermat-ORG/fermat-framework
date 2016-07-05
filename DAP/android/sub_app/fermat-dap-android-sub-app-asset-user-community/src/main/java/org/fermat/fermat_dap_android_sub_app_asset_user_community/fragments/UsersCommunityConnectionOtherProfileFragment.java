package org.fermat.fermat_dap_android_sub_app_asset_user_community.fragments;

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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Actor;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.popup.AcceptDialog;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.popup.CancelDialog;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.popup.ConnectDialog;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.popup.DisconnectDialog;
import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Creado por Jinmy Bohorquez on 09/02/16.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class UsersCommunityConnectionOtherProfileFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetUserCommunitySubAppModuleManager>, ResourceProviderManager>
        implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String USER_SELECTED = "user";
    private String TAG = "ConnectionOtherProfileFragment";

    private Actor actor;
    private List<Actor> actors;

    private Resources res;
    private View rootView;
    private ImageView userProfileAvatar;
    private FermatTextView userName;
    //private FermatTextView userEmail;
    private FermatTextView userCryptoAddres;
    private FermatTextView userCryptoCurrency;
    private FermatTextView userBlockchainNetworkType;
    private FermatTextView userRegistrationDate;
    private FermatTextView userLastConnectionDate;
    private AssetUserCommunitySubAppModuleManager moduleManager;
    AssetUserSettings settings = null;
    private ErrorManager errorManager;
    private Button connect;
    private Button disconnect;
    private int MAX = 1;
    private int OFFSET = 0;
    //private FermatTextView userStatus;
    private Button connectionRequestSend;
    private Button connectionRequestRejected;
    private Button accept;
    private Button connectionCancel;
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
        actor = (Actor) appSession.getData(USER_SELECTED);

        moduleManager = appSession.getModuleManager();
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
        connectionCancel = (Button) rootView.findViewById(R.id.btn_connection_cancel);
        connectionCancel.setVisibility(View.GONE);
        connectionRequestSend.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setOnClickListener(this);
        connectionRequestSend.setOnClickListener(this);
        connect.setOnClickListener(this);
        disconnect.setOnClickListener(this);
        connectionCancel.setOnClickListener(this);

        updateButton();

        try {
            userName.setText(actor.getName());
            //userStatus.setText(actor.getPhrase());
            //userStatus.setText(actor.getName());
            //userStatus.setTextColor(Color.parseColor("#292929"));

            if (actor.getCryptoAddress() != null) {
                userCryptoAddres.setText(actor.getCryptoAddress().getAddress());
                userCryptoCurrency.setText(actor.getCryptoAddress().getCryptoCurrency().getFriendlyName());
            } else {
                userCryptoAddres.setText("No");
                userCryptoCurrency.setText("None");
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
            //CommonLogger.info(TAG, "User connection state " + actor.getStatus());
//            try {
            ConnectDialog connectDialog = new ConnectDialog(getActivity(),
                    appSession,
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
//            }
        }

        if (i == R.id.btn_disconect) {
            //CommonLogger.info(TAG, "User connection state " + actor.getStatus());
//            try {
            final DisconnectDialog disconnectDialog = new DisconnectDialog(getActivity(),
                    appSession,
                    null,
                    actor,
                    null);

            disconnectDialog.setTitle("Disconnect");
            disconnectDialog.setDescription("Want to disconnect from");
            disconnectDialog.setUsername(actor.getName());
            disconnectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    updateButton();
                }
            });
            disconnectDialog.show();
//            } catch (CantGetIdentityAssetUserException e) {
//                e.printStackTrace();
//            }
        }

        if (i == R.id.btn_connection_cancel) {
//            try {
            CancelDialog cancelDialog = new CancelDialog(getActivity(),
                    appSession,
                    null,
                    actor,
                    null);

            cancelDialog.setTitle("Cancel Request");
            cancelDialog.setDescription("Want to cancel the request to");
            cancelDialog.setUsername(actor.getName());
            cancelDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    updateButton();
                }
            });
            cancelDialog.show();
//            } catch (CantGetIdentityAssetUserException e) {
//                e.printStackTrace();
//            }
        }

        if (i == R.id.btn_connection_accept) {
            try {
                AcceptDialog notificationAcceptDialog;

                notificationAcceptDialog = new AcceptDialog(getActivity(),
                        appSession,
                        null,
                        actor,
                        moduleManager.getActiveAssetUserIdentity());

                notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateButton();
                    }
                });
                notificationAcceptDialog.show();
            } catch (CantGetIdentityAssetUserException e) {
                e.printStackTrace();
            }
        }
        if (i == R.id.btn_connection_request_send) {
            //CommonLogger.info(TAG, "User connection state " + actor.getStatus());
            Toast.makeText(getActivity(), "The connection request has been sent\n you need to wait until the user responds", Toast.LENGTH_SHORT).show();
        }
        if (i == R.id.btn_connection_request_reject) {
            // CommonLogger.info(TAG, "User connection state " + actor.getStatus());
            Toast.makeText(getActivity(), "The connection request has been rejected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateButton() {
        ActorAssetUser actorUser = null;
        try {
            connectionState = moduleManager.getActorRegisteredDAPConnectionState(this.actor.getActorPublicKey());
            actorUser = moduleManager.getActorUser(this.actor.getActorPublicKey());

        } catch (CantGetAssetUserActorsException e) {
            e.printStackTrace();
        } catch (CantAssetUserActorNotFoundException e) {
            e.printStackTrace();
        }
        updateStateConnection(connectionState, actorUser);
        onRefresh();

    }

    private void updateStateConnection(DAPConnectionState dapConnectionState, ActorAssetUser actorUser) {

        switch (dapConnectionState) {
            case BLOCKED_LOCALLY:
            case BLOCKED_REMOTELY:
            case CANCELLED_LOCALLY:
            case CANCELLED_REMOTELY:
                connectionRejected();
                break;
            case CONNECTED_ONLINE:
            case CONNECTED_OFFLINE:
                if (actorUser.getCryptoAddress() != null)
                    disconnectRequest();
                else
                    connectRequest();
                break;
            case DISCONNECTED_LOCALLY:
            case DISCONNECTED_REMOTELY:
            case DENIED_LOCALLY:
            case DENIED_REMOTELY:
            case REGISTERED_ONLINE:
            case REGISTERED_OFFLINE:
                if (actorUser.getCryptoAddress() != null)
                    disconnectRequest();
                else
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
        //connectionRequestSend.setVisibility(View.VISIBLE);
        connectionCancel.setVisibility(View.VISIBLE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    private void connectionAccept() {
        connectionCancel.setVisibility(View.GONE);
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
        accept.setVisibility(View.VISIBLE);
    }

    private void connectRequest() {
        connectionCancel.setVisibility(View.GONE);
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.VISIBLE);
        disconnect.setVisibility(View.GONE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    private void disconnectRequest() {
        connectionCancel.setVisibility(View.GONE);
        connectionRequestSend.setVisibility(View.GONE);
        connect.setVisibility(View.GONE);
        disconnect.setVisibility(View.VISIBLE);
        connectionRequestRejected.setVisibility(View.GONE);
    }

    private void connectionRejected() {
        connectionCancel.setVisibility(View.GONE);
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
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);
//        menu.clear();
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

    private synchronized List<Actor> getProfileData() {
        List<AssetUserActorRecord> tempActor = new ArrayList<>();
        ActorAssetUser actorAssetUser;
        actors = new ArrayList<>();

        try {
            actorAssetUser = moduleManager.getActorUser(actor.getActorPublicKey());

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
                actor = actors.get(0);
                if (actors.get(0).getCryptoAddress() != null) {
                    userCryptoAddres.setText(actors.get(0).getCryptoAddress().getAddress());
                    userCryptoCurrency.setText(actors.get(0).getCryptoAddress().getCryptoCurrency().getFriendlyName());
                } else {
                    userCryptoAddres.setText("No");
                    userCryptoCurrency.setText("None");
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
