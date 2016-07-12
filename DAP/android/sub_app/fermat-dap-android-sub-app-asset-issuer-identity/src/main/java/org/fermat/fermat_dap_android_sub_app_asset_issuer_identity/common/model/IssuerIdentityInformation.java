package org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.common.model;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;

import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;

/**
 * Created by nelson on 09/10/15.
 */
public class IssuerIdentityInformation implements IdentityAssetIssuer {

    private String intraUserName;
    private byte[] profileImage;
    private String publicKey;
    private int accuracy;
    private GeoFrequency frequency;

    public IssuerIdentityInformation(String intraUserName, String publicKey, byte[] profileImage, int accuracy, GeoFrequency frequency) {
        this.intraUserName = intraUserName;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    @Override
    public String getAlias() {
        return this.intraUserName;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public Actors getActorType() {
        return Actors.DAP_ASSET_ISSUER;
    }

    @Override
    public byte[] getImage() {
        return this.profileImage;
    }

    @Override
    public void setNewProfileImage(byte[] newProfileImage) {

    }

    @Override
    public String createMessageSignature(String message) {
        return null;
    }

    @Override
    public int getAccuracy() {
        return accuracy;
    }

    @Override
    public GeoFrequency getFrequency() {
        return frequency;
    }
}
