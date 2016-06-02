package org.fermat.fermat_dap_android_sub_app_asset_user_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.squareup.picasso.Picasso;

import org.fermat.fermat_dap_android_sub_app_asset_user_community.holders.GroupViewHolder;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.interfaces.AdapterChangeListener;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.interfaces.PopupMenu;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Group;

import java.util.List;

public class GroupCommunityAdapter extends FermatAdapter<Group, GroupViewHolder> {

    private PopupMenu menuItemClick;

    private Group groupSelected;

    private AdapterChangeListener<Group> adapterChangeListener;

    public GroupCommunityAdapter(Context context) {
        super(context);
    }

    public GroupCommunityAdapter(Context context, List<Group> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected GroupViewHolder createHolder(View itemView, int type) {
        return new GroupViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_group;
    }

    @Override
    protected void bindHolder(final GroupViewHolder holder, final Group data, final int position) {
        holder.itemView.setVisibility(View.VISIBLE);
        try {
            if (data.getGroupName() != null) {
                holder.groupName.setText(String.format("%s", data.getGroupName()));
            }

            holder.groupMembers.setText(R.string.group_members);
            holder.groupMembers.append(String.format("%s", data.getMembers()));

            Picasso.with(context).load(R.drawable.ic_group_image).into(holder.thumbnail);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setAdapterChangeListener(AdapterChangeListener<Group> adapterChangeListener) {
        this.adapterChangeListener = adapterChangeListener;
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }

    public void setMenuItemClick(PopupMenu menuItemClick) {
        this.menuItemClick = menuItemClick;
    }


}
