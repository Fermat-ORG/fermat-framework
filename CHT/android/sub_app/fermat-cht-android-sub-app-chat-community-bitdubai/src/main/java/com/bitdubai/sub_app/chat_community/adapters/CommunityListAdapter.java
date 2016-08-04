package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantValidateActorConnectionStateException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.common.popups.AcceptDialog;
import com.bitdubai.sub_app.chat_community.common.popups.ConnectDialog;
import com.bitdubai.sub_app.chat_community.common.popups.DisconnectDialog;
import com.bitdubai.sub_app.chat_community.filters.CommunityFilter;
import com.bitdubai.sub_app.chat_community.holders.CommunityWorldHolder;
import com.bitdubai.sub_app.chat_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * CommunityListAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */

@SuppressWarnings("unused")
public class CommunityListAdapter extends FermatAdapter<ChatActorCommunityInformation, CommunityWorldHolder>
        implements Filterable {

    List<ChatActorCommunityInformation> filteredData;
    private String cityAddress;
    private String stateAddress;
    private String countryAddress;
    private String filterString;
    private final String TAG = "communityadapter";
    private ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession;
    private ChatActorCommunitySubAppModuleManager moduleManager;
    ArrayList<ChatActorCommunityInformation> chatMessages = new ArrayList<>();
    private AdapterCallbackList mAdapterCallbackList;


    public CommunityListAdapter(Context context) {
        super(context);
    }

    public CommunityListAdapter(Context context, List<ChatActorCommunityInformation> dataSet,
                                ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession,
                                ChatActorCommunitySubAppModuleManager moduleManager,
                                AdapterCallbackList mAdapterCallbackList) {
        super(context, dataSet);
        this.appSession = appSession;
        this.moduleManager = moduleManager;
        this.mAdapterCallbackList = mAdapterCallbackList;
    }

    @Override
    protected CommunityWorldHolder createHolder(View itemView, int type) {
        return new CommunityWorldHolder(itemView);
    }

    public static interface AdapterCallbackList {
        void onMethodCallbackConnectionStatus(int position, ConnectionState state);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cht_comm_world_item;
    }

    private void updateConnectionState(ConnectionState connectionState, CommunityWorldHolder holder) {
        if (connectionState != null) {
            switch (connectionState) {
                case CONNECTED:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText(context.getResources().getString(R.string.cht_comm_now_conn));
                    holder.connectedButton.setVisibility(View.VISIBLE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case BLOCKED_LOCALLY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText(context.getResources().getString(R.string.cht_comm_blocked));
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case BLOCKED_REMOTELY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText(context.getResources().getString(R.string.cht_comm_blocked));
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case CANCELLED_LOCALLY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText(context.getResources().getString(R.string.cht_comm_blocked));
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case CANCELLED_REMOTELY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText(context.getResources().getString(R.string.cht_comm_blocked));
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case NO_CONNECTED:
                    holder.add_contact_button.setVisibility(View.VISIBLE);
                    holder.connection_text.setVisibility(View.GONE);
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case DENIED_LOCALLY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText(context.getResources().getString(R.string.cht_comm_blocked));
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case DENIED_REMOTELY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText(context.getResources().getString(R.string.cht_comm_blocked));
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case DISCONNECTED_LOCALLY:
                    holder.add_contact_button.setVisibility(View.VISIBLE);
                    holder.connection_text.setVisibility(View.GONE);
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case DISCONNECTED_REMOTELY:
                    holder.add_contact_button.setVisibility(View.VISIBLE);
                    holder.connection_text.setVisibility(View.GONE);
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case ERROR:
                    holder.add_contact_button.setVisibility(View.VISIBLE);
                    holder.connection_text.setVisibility(View.GONE);
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case INTRA_USER_NOT_FOUND:
                    holder.add_contact_button.setVisibility(View.VISIBLE);
                    holder.connection_text.setVisibility(View.GONE);
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText(context.getResources().getString(R.string.cht_comm_pending));
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.VISIBLE);
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText(context.getResources().getString(R.string.cht_comm_sent));
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.VISIBLE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                default:
                    holder.add_contact_button.setVisibility(View.VISIBLE);
                    holder.connection_text.setVisibility(View.GONE);
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
            }
        } else {
            holder.add_contact_button.setVisibility(View.VISIBLE);
            holder.connection_text.setVisibility(View.GONE);
            holder.connectedButton.setVisibility(View.GONE);
            holder.blockedButton.setVisibility(View.GONE);
            holder.pendingButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void bindHolder(final CommunityWorldHolder holder, ChatActorCommunityInformation data, final int position) {
        final ConnectionState connectionState = data.getConnectionState();
        updateConnectionState(connectionState, holder);
        holder.name.setText(data.getAlias());
        byte[] profileImage = data.getImage();
        if (profileImage != null && profileImage.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            holder.thumbnail.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        } else
            holder.thumbnail.setImageResource(R.drawable.cht_comm_icon_user);

//            if (data.getState().equals("null") || data.getState().equals(""))
                stateAddress = "";
//            else stateAddress = data.getState() + " ";
            if (data.getCity().equals("null") || data.getCity().equals("")) cityAddress = "";
            else cityAddress = data.getCity() + ", ";
            if (data.getCountry().equals("null") || data.getCountry().equals("")) countryAddress = "";
            else countryAddress = data.getCountry();
            if (/*stateAddress.equalsIgnoreCase("") &&*/ cityAddress.equalsIgnoreCase("") && countryAddress.equalsIgnoreCase("")) {
                holder.location_text.setText(context.getResources().getString(R.string.cht_comm_not_found));
            }else
                holder.location_text.setText(cityAddress + countryAddress);//+ stateAddress

        if(data.getProfileStatus() != null && data.getProfileStatus().getCode().equalsIgnoreCase("ON"))
            holder.location_text.setTextColor(Color.parseColor("#47BF73"));
        else
            holder.location_text.setTextColor(Color.RED);

        final ChatActorCommunityInformation dat = data;
        holder.add_contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonLogger.info(TAG, context.getResources().getString(R.string.cht_comm_text_state) +
                        dat.getConnectionState());
                ConnectDialog connectDialog;
                try {
                    connectDialog =
                            new ConnectDialog(context, appSession, null,
                                    dat, moduleManager.getSelectedActorIdentity());
                    connectDialog.setTitle(context.getResources().getString(R.string.cht_comm_connection_request));
                    connectDialog.setDescription(context.getResources().getString(R.string.cht_comm_text_connect));
                    connectDialog.setUsername(dat.getAlias());
//                    connectDialog.setSecondDescription("a connection request?");
                    connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            try {
                                ConnectionState cState
                                        = moduleManager.getActorConnectionState(dat.getPublicKey());
                                updateConnectionState(cState, holder);
                                mAdapterCallbackList.onMethodCallbackConnectionStatus(position, cState);
                            } catch (CantValidateActorConnectionStateException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    connectDialog.show();
                } catch (CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.connectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonLogger.info(TAG, context.getResources().getString(R.string.cht_comm_text_state) +
                        dat.getConnectionState());
                final DisconnectDialog disconnectDialog;
                try {
                    disconnectDialog =
                            new DisconnectDialog(context, appSession, null,
                                    dat, moduleManager.getSelectedActorIdentity());
                    disconnectDialog.setTitle(context.getResources().getString(R.string.cht_comm_disconnection_request));
                    disconnectDialog.setDescription(context.getResources().getString(R.string.cht_comm_text_disconnect));
                    disconnectDialog.setUsername(dat.getAlias() + "?");
                    disconnectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            try {
                                ConnectionState cState
                                        = moduleManager.getActorConnectionState(dat.getPublicKey());
                                updateConnectionState(cState, holder);
                            } catch (CantValidateActorConnectionStateException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    disconnectDialog.show();
                } catch (CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.pendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonLogger.info(TAG, context.getResources().getString(R.string.cht_comm_text_state)
                        + dat.getConnectionState());
                Toast.makeText(context, context.getResources().getString(R.string.cht_comm_text_resend_toast), Toast.LENGTH_SHORT).show();
                ConnectDialog connectDialog;
                try {
                    connectDialog =
                            new ConnectDialog(context, appSession, null,
                                    dat, moduleManager.getSelectedActorIdentity());
                    connectDialog.setTitle( context.getResources().getString(R.string.cht_comm_resend_request));
                    connectDialog.setDescription( context.getResources().getString(R.string.cht_comm_text_resend));
                    connectDialog.setUsername(dat.getAlias());
                    connectDialog.setSecondDescription(context.getResources().getString(R.string.cht_comm_text_resend2));
                    connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            try {
                                ConnectionState cState
                                        = moduleManager.getActorConnectionState(dat.getPublicKey());
                                updateConnectionState(cState, holder);
                            } catch (CantValidateActorConnectionStateException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    connectDialog.show();
                } catch (CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.blockedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonLogger.info(TAG, context.getResources().getString(R.string.cht_comm_text_state)
                        + dat.getConnectionState());
                Toast.makeText(context, context.getResources().getString(R.string.cht_comm_text_block_toast), Toast.LENGTH_SHORT).show();
            }
        });

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AcceptDialog notificationAcceptDialog =
                            new AcceptDialog(context, appSession, null,
                                    dat, moduleManager.getSelectedActorIdentity());
                    notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            try {
                                ConnectionState cState
                                        = moduleManager.getActorConnectionState(dat.getPublicKey());
                                updateConnectionState(cState, holder);
                            } catch (CantValidateActorConnectionStateException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    notificationAcceptDialog.show();

                } catch (CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }

    public void refreshEvents(ArrayList<ChatActorCommunityInformation> chatHistory) {
        for (int i = 0; i < chatHistory.size(); i++) {
            ChatActorCommunityInformation message = chatHistory.get(i);
            add(message);
            changeDataSet(chatHistory);
            notifyDataSetChanged();
        }
    }

    public void add(ChatActorCommunityInformation message) {
        chatMessages.add(message);
    }

    public void setData(List<ChatActorCommunityInformation> data) {
        this.filteredData = data;
    }

    public Filter getFilter() {
        return new CommunityFilter(dataSet, this);
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    public String getFilterString() {
        return filterString;
    }
}