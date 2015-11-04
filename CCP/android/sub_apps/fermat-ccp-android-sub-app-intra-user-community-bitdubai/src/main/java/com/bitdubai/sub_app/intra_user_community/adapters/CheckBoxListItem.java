package com.bitdubai.sub_app.intra_user_community.adapters;

import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;

/**
 * Created by Matias Furszyfer on 2015.09.01..
 */
public class CheckBoxListItem {

    private IntraUserLoginIdentity intraUserIdentity;
    private Drawable icon;


    public CheckBoxListItem(Drawable icon, IntraUserLoginIdentity intraUserIdentity) {
        this.icon = icon;
        this.intraUserIdentity = intraUserIdentity;
    }


    public IntraUserLoginIdentity getIntraUserIdentity() {
        return intraUserIdentity;
    }

    public void setIntraUserIdentity(IntraUserLoginIdentity intraUserIdentity) {
        this.intraUserIdentity = intraUserIdentity;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
