package com.bitdubai.sub_app.crypto_broker_identity.common.model;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;

/**
 * Created by nelson on 09/10/15.
 */
public class CryptoBrokerIdentityInformationImp implements CryptoBrokerIdentityInformation {

    private String brokerName;
    private byte[] profileImage;

    public CryptoBrokerIdentityInformationImp(String brokerName) {
        this.brokerName = brokerName;
        profileImage = null;
    }

    @Override
    public String getPublicKey() {
        return null;
    }

    @Override
    public String getName() {
        return brokerName;
    }

    @Override
    public byte[] getProfileImage() {
        return profileImage;
    }
}
