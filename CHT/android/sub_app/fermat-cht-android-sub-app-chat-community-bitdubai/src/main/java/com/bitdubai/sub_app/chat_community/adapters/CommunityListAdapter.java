package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantValidateActorConnectionStateException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.common.popups.AcceptDialog;
import com.bitdubai.sub_app.chat_community.common.popups.ConnectDialog;
import com.bitdubai.sub_app.chat_community.common.popups.DisconnectDialog;
import com.bitdubai.sub_app.chat_community.filters.CommunityFilter;
import com.bitdubai.sub_app.chat_community.holders.CommunityWorldHolder;
import com.bitdubai.sub_app.chat_community.util.CommonLogger;

import java.text.DecimalFormat;
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
    private String filterString;
    private final String TAG = "communityadapter";
    private ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession;
    private ChatActorCommunitySubAppModuleManager moduleManager;

    public CommunityListAdapter(Context context) {
        super(context);
    }

    public CommunityListAdapter(Context context, List<ChatActorCommunityInformation> dataSet,
                                ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession,
                                ChatActorCommunitySubAppModuleManager moduleManager) {
        super(context, dataSet);
        this.appSession=appSession;
        this.moduleManager=moduleManager;
    }

    @Override
    protected CommunityWorldHolder createHolder(View itemView, int type) {
        return new CommunityWorldHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cht_comm_world_item;
    }

    private void updateConnectionState(ConnectionState connectionState, CommunityWorldHolder holder)
    {
        if (connectionState != null) {
            switch (connectionState) {
                case CONNECTED:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText("is now a connection");
                    holder.connectedButton.setVisibility(View.VISIBLE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case BLOCKED_LOCALLY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText("is blocked");
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case BLOCKED_REMOTELY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText("is blocked");
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case CANCELLED_LOCALLY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText("is blocked");
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case CANCELLED_REMOTELY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText("is blocked");
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
                    holder.connection_text.setText("is blocked");
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.GONE);
                    break;
                case DENIED_REMOTELY:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText("is blocked");
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
                    holder.connection_text.setText("Pending Acceptance");
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    holder.acceptButton.setVisibility(View.VISIBLE);
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    holder.add_contact_button.setVisibility(View.GONE);
                    holder.connection_text.setVisibility(View.VISIBLE);
                    holder.connection_text.setText("Request sent");
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
        }
    }

    @Override
    protected void bindHolder(final CommunityWorldHolder holder, ChatActorCommunityInformation data, int position) {
        final ConnectionState connectionState = data.getConnectionState();
        updateConnectionState(connectionState, holder);
        holder.name.setText(data.getAlias());
        byte[] profileImage = data.getImage();
        if (profileImage != null && profileImage.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            holder.thumbnail.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        }else
            holder.thumbnail.setImageResource(R.drawable.cht_comm_icon_user);

        Address address= null;
        if(data.getLocation() != null ){
            try {
                if(data.getLocation().getLatitude()!=0 && data.getLocation().getLongitude()!=0)
                    address = moduleManager.getAddressByCoordinate(data.getLocation().getLatitude(), data.getLocation().getLongitude());
            }catch(CantCreateAddressException e){
                address = null;
            }catch(Exception e){
                address = null;
            }
        }
        if (address!=null)
            holder.location_text.setText(address.getCity() + " " + address.getState() + " " + address.getCountry());//TODO: put here location
        else
            holder.location_text.setText("Searching...");//TODO: put here location

        final ChatActorCommunityInformation dat=data;
        holder.add_contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonLogger.info(TAG, "User connection state " +
                        dat.getConnectionState());
                ConnectDialog connectDialog;
                try {
                    connectDialog =
                            new ConnectDialog(context, appSession, null,
                                    dat, moduleManager.getSelectedActorIdentity());
                    connectDialog.setTitle("Connection Request");
                    connectDialog.setDescription("Are you sure you want to send a connection request to this contact?");
                    connectDialog.setUsername(dat.getAlias());
//                    connectDialog.setSecondDescription("a connection request?");
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
                } catch ( CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.connectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonLogger.info(TAG, "User connection state " +
                        dat.getConnectionState());
                final DisconnectDialog disconnectDialog;
                try {
                    disconnectDialog =
                            new DisconnectDialog(context, appSession, null,
                                    dat, moduleManager.getSelectedActorIdentity());
                    disconnectDialog.setTitle("Disconnect");
                    disconnectDialog.setDescription("Do you want to disconnect from");
                    disconnectDialog.setUsername(dat.getAlias()+"?");
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
                } catch ( CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.pendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonLogger.info(TAG, "User connection state "
                        + dat.getConnectionState());
                Toast.makeText(context, "The connection request has been sent\n you need to wait until the user responds", Toast.LENGTH_SHORT).show();
                ConnectDialog connectDialog;
                try {
                    connectDialog =
                            new ConnectDialog(context, appSession, null,
                                    dat, moduleManager.getSelectedActorIdentity());
                    connectDialog.setTitle("Resend Connection Request");
                    connectDialog.setDescription("Do you want to resend ");
                    connectDialog.setUsername(dat.getAlias());
                    connectDialog.setSecondDescription("a connection request?");
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
                } catch ( CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.blockedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonLogger.info(TAG, "User connection state "
                        + dat.getConnectionState());
                Toast.makeText(context, "The connection request has been rejected", Toast.LENGTH_SHORT).show();
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

                } catch ( CantGetSelectedActorIdentityException
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

    public void setData(List<ChatActorCommunityInformation> data) {
        this.filteredData = data;
    }

//    @Override
//    public int getItemCount() {
//        if(filterString!=null)
//            return filteredData == null ? 0 : filteredData.size();
//        else
//            return dataSet == null ? 0 : dataSet.size();
//    }
//
//    @Override
//    public ChatActorCommunityInformation getItem(int position) {
//        if(filterString!=null)
//            return filteredData != null ? (!filteredData.isEmpty()
//                    && position < filteredData.size()) ? filteredData.get(position) : null : null;
//        else
//            return dataSet != null ? (!dataSet.isEmpty()
//                    && position < dataSet.size()) ? dataSet.get(position) : null : null;
//    }

//    @Override
//    public long getId(int position) {
//        return position;
//    }
//
//    public void changeDataSet(List<ChatActorCommunityInformation> data) {
//        this.filteredData = data;
//    }
//
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