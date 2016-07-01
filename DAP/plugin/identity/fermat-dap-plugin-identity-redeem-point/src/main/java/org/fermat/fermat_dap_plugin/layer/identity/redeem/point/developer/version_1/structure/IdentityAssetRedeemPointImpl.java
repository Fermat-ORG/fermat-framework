package org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.all_definition.enums.Frequency;

import java.io.Serializable;

/**
 * Created by franklin on 02/11/15.
 */
public class IdentityAssetRedeemPointImpl implements RedeemPointIdentity, Serializable {

    private String alias;
    private String publicKey;
    private byte[] profileImage;
    private String privateKey;
    private String contactInformation;
    private String countryName;
    private String provinceName;
    private String cityName;
    private String postalCode;
    private String streetName;
    private String houseNumber;
    private int accuracy;
    private Frequency frequency;

    @Override
    public String getContactInformation() {
        return contactInformation;
    }

    @Override
    public String getCountryName() {
        return countryName;
    }

    @Override
    public String getProvinceName() {
        return provinceName;
    }

    @Override
    public String getCityName() {
        return cityName;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String getStreetName() {
        return streetName;
    }

    @Override
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Constructor
     */
    public IdentityAssetRedeemPointImpl(String alias, String publicKey, String privateKey, byte[] profileImage, int accuracy, Frequency frequency) {

        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.privateKey = privateKey;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    public IdentityAssetRedeemPointImpl(String alias, String publicKey, String privateKey, byte[] profileImage,
                                        String contactInformation,
                                        String countryName, String provinceName, String cityName,
                                        String postalCode, String streetName, String houseNumber,
                                        int accuracy, Frequency frequency) {

        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.privateKey = privateKey;
        this.contactInformation = contactInformation;
        this.countryName = countryName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    public IdentityAssetRedeemPointImpl(String alias, String publicKey, byte[] profileImage, String contactInformation,
                                        String countryName, String provinceName, String cityName,
                                        String postalCode, String streetName, String houseNumber,
                                        int accuracy, Frequency frequency) {

        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.contactInformation = contactInformation;
        this.countryName = countryName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
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

    /**
     * @return an element of Actors enum representing the type of the actor identity.
     */
    @Override
    public Actors getActorType() {
        return Actors.DAP_ASSET_REDEEM_POINT;
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
    public Frequency getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "IdentityAssetRedeemPointImpl{" +
                "alias='" + alias + '\'' +
                ", ActorType='" + getActorType() + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
