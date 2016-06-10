package com.bitdubai.sub_app.wallet_manager.holder;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp;
import com.bitdubai.fermat_wpd.wallet_manager.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Matias Furszyfer on 2016.03.10..
 */
public class ProfilesHolder extends ChildViewHolder {


    public ImageView img_icon;
    public FermatTextView txtProfile;

    /**
     * Constructor
     *
     * @param itemView
     */
    public ProfilesHolder(View itemView) {
        super(itemView);
        img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
        txtProfile = (FermatTextView) itemView.findViewById(R.id.txt_profile_name);
    }

    public ProfilesHolder bind(InstalledApp childListItem) {
        if(childListItem.getIconResource()!=0){
            Picasso.with(itemView.getContext()).load(childListItem.getIcon()).into(img_icon);
        }
        txtProfile.setText(childListItem.getName());
        return this;
    }
}
