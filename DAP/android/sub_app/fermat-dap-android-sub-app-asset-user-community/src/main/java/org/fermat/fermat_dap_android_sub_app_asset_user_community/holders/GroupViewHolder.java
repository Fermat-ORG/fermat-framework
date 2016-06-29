package org.fermat.fermat_dap_android_sub_app_asset_user_community.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;

/**
 * Created by Penny on 01/08/16.
 */
public class GroupViewHolder extends FermatViewHolder {


    public SquareImageView thumbnail;
    public FermatTextView groupName;
    public FermatTextView groupMembers;

    /**
     * Constructor
     *
     * @param itemView
     */
    public GroupViewHolder(View itemView) {
        super(itemView);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.group_image);
        groupName = (FermatTextView) itemView.findViewById(R.id.group_name);
        groupMembers = (FermatTextView) itemView.findViewById(R.id.group_members);


    }
}
