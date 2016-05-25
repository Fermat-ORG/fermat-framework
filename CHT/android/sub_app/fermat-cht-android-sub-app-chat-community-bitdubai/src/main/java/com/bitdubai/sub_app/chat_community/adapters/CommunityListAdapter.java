package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.filters.CommunityFilter;
import com.bitdubai.sub_app.chat_community.holders.CommunityWorldHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * CommunityListAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */

@SuppressWarnings("unused")
public class CommunityListAdapter extends FermatAdapter<ChatActorCommunityInformation, CommunityWorldHolder> /*implements Filterable*/ {

    List<ChatActorCommunityInformation> filteredData;
    private String filterString;

    public CommunityListAdapter(Context context) {
        super(context);
    }

    public CommunityListAdapter(Context context, List<ChatActorCommunityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected CommunityWorldHolder createHolder(View itemView, int type) {
        return new CommunityWorldHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cht_comm_world_item;
    }

    @Override
    protected void bindHolder(CommunityWorldHolder holder, ChatActorCommunityInformation data, int position) {
        //holder.connectionState.setVisibility(View.GONE);
        ConnectionState connectionState = data.getConnectionState();
        if(connectionState != null) {
            switch (connectionState) {
                case CONNECTED:
                        holder.connectedButton.setVisibility(View.VISIBLE);
                        holder.blockedButton.setVisibility(View.GONE);
                        holder.pendingButton.setVisibility(View.GONE);
                    break;
                case BLOCKED_LOCALLY:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
                case BLOCKED_REMOTELY:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
                case CANCELLED_LOCALLY:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
                case CANCELLED_REMOTELY:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
//                    if (holder.connectionState.getVisibility() == View.GONE) {
//                        holder.connectionState.setImageResource(R.drawable.cht_comm_btn_conect_background);//icon_contact_no_conect
//                        holder.connectionState.setVisibility(View.VISIBLE);
//                    }
                    break;
                case NO_CONNECTED:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
                case DENIED_LOCALLY:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
                case DENIED_REMOTELY:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.VISIBLE);
                    holder.pendingButton.setVisibility(View.GONE);
//                    if (holder.connectionState.getVisibility() == View.GONE) {
//                        holder.connectionState.setImageResource(R.drawable.cht_comm_btn_conect_background);//icon_contact_no_conect
//                        holder.connectionState.setVisibility(View.VISIBLE);
//                    }
                    break;
                case DISCONNECTED_LOCALLY:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
                case DISCONNECTED_REMOTELY:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
                case ERROR:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
                case INTRA_USER_NOT_FOUND:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.VISIBLE);
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.VISIBLE);
//                    if (holder.connectionState.getVisibility() == View.GONE) {
//                        holder.connectionState.setImageResource(R.drawable.cht_comm_btn_conect_background);//icon_contact_standby
//                        holder.connectionState.setVisibility(View.VISIBLE);
//                    }
                    break;
                default:
                    holder.connectedButton.setVisibility(View.GONE);
                    holder.blockedButton.setVisibility(View.GONE);
                    holder.pendingButton.setVisibility(View.GONE);
//                    if (holder.connectionState.getVisibility() == View.VISIBLE)
//                        holder.connectionState.setVisibility(View.GONE);
                    break;
            }
            //holder.row_connection_state.setText(connectionState.toString());//data.getState()
//            if(data.getConnectionState().toString().equals("Offline"))
//                holder.row_connection_state.setTextColor(Color.RED);
//            else
//                holder.row_connection_state.setTextColor(Color.WHITE);
        }
// else {
//            holder.row_connection_state.setText("Offline");
//            holder.row_connection_state.setTextColor(Color.RED);
//            holder.connectionState.setVisibility(View.GONE);
//        }


        holder.name.setText(data.getAlias());
        byte[] profileImage = data.getImage();
        if (profileImage != null && profileImage.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            holder.thumbnail.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        }

    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
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
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public void changeDataSet(List<ChatActorCommunityInformation> data) {
//        this.filteredData = data;
//    }
//
//    public Filter getFilter() {
//        return new CommunityFilter(dataSet, this);
//    }
//
//    public void setFilterString(String filterString) {
//        this.filterString = filterString;
//    }
//
//    public String getFilterString() {
//        return filterString;
//    }
}