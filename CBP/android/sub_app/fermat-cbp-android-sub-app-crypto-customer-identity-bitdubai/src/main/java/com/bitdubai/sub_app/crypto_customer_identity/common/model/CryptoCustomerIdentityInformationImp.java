package com.bitdubai.sub_app.crypto_customer_identity.common.model;

import com.bitdubai.fermat_cbp_api.all_definition.enums.IdentityPublished;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;

/**
 * Created by nelson on 09/10/15.
 */
public class CryptoCustomerIdentityInformationImp implements CryptoCustomerIdentityInformation {

    private String brokerName;
    private byte[] profileImage;
    private int profileImageDrawableId;

    public CryptoCustomerIdentityInformationImp(String brokerName, int drawableId) {
        this.brokerName = brokerName;
        this.profileImageDrawableId = drawableId;
        profileImage = new byte[0];
    }

    @Override
    public String getAlias() {
        return brokerName;
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    @Override
    public String getPublicKey() {
        return null;
    }

    @Override
    public boolean isPublished() {
        return false;
    }

    public int getProfileImageDrawableId() {
        return profileImageDrawableId;
    }
}
