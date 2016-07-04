package org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;

import java.io.Serializable;

/**
 * Created by franklin on 02/11/15.
 */
public class IdentityAssetIssuerImpl implements IdentityAssetIssuer, Serializable {

    private String alias;
    private String publicKey;
    private byte[] profileImage;
    private String privateKey;
    private int accuracy;
    private GeoFrequency frequency;

    public IdentityAssetIssuerImpl(String alias, String publicKey, String privateKey, byte[] profileImage, int accuracy, GeoFrequency frequency) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.privateKey = privateKey;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    public IdentityAssetIssuerImpl(String alias, String publicKey, byte[] profileImage, int accuracy, GeoFrequency frequency) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    @Override
    public String getAlias() {
        return this.alias;
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
        this.profileImage = newProfileImage;
    }

    @Override
    public String createMessageSignature(String message) {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch (Exception e) {
            //TODO: Revisar este manejo de excepciones
            e.printStackTrace();
            // throw new CantSignIntraWalletUserMessageException("Fatal Error Signed message", e, "", "");
        }
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

    @Override
    public String toString() {
        return "IdentityAssetIssuerImpl{" +
                "alias='" + alias + '\'' +
                ", ActorType='" + getActorType() + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
