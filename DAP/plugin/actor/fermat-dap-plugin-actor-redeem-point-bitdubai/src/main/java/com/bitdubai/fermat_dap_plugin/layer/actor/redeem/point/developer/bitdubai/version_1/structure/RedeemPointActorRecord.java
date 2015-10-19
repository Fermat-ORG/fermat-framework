package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

/**
 * Created by Nerio on 22/09/15.
 */
public class RedeemPointActorRecord implements ActorAssetRedeemPoint {

    private String name;
    private String publicKey;
    private byte[] profileImage ;
    private long registrationDate;
    private Double locationLatitude;
    private Double locationLongitude;
    private ConnectionState connectionState;
    private Genders genders;

    /**
     * Constructor
     */
    public RedeemPointActorRecord(String name, String publicKey, byte[] profileImage, Location location) {

        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage.clone();
        this.locationLatitude = location.getLatitude();
        this.locationLongitude = location.getLongitude();
        this.genders = Genders.INDEFINITE;
//        this.age = age;
//        this.cryptoAddress = cryptoAddress;
        this.connectionState = ConnectionState.CONNECTED;

    }

    public RedeemPointActorRecord(String name,String publicKey,byte[] profileImage,long registrationDate, ConnectionState connectionState){

        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = (byte[])profileImage.clone();
        this.registrationDate = registrationDate;
        this.connectionState = connectionState;

    }

    /**
     * The metho <code>getPublicKey</code> gives us the public key of the represented Redeem Point
     *
     * @return the public key
     */
    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    /**
     * The method <code>getName</code> gives us the name of the represented Redeem Point
     *
     * @return the name of the intra user
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both Redeem Points
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    @Override
    public long getContactRegistrationDate() {
        return this.registrationDate;
    }

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Redeem Point
     *
     * @return the image
     */
    @Override
    public byte[] getProfileImage() {
        return this.profileImage.clone();
    }

    @Override
    public Double getLocationLatitude() {
        return this.locationLatitude;
    }

    @Override
    public Double getLocationLongitude() {
        return this.locationLongitude;
    }

    /**
     * The method <code>getConnectionState</code> gives us the contact state of the represented Asset Issuer
     *
     * @return the connection state
     */
    @Override
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }
}
