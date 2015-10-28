package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point;


import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.Address;

/**
 * Created by Nerio on 22/09/15.
 */
public class RedeemPointActorRecord implements ActorAssetRedeemPoint {

    private String name;
    private String publicKey;
    private byte[] profileImage;
    private long registrationDate;
    private ConnectionState connectionState;
    private CryptoAddress cryptoAddress;
    private Location location;
    private Double locationLatitude;
    private Double locationLongitude;
    private Address address;
    private String contactInformation;
    private String hoursOfOperation;

    /**
     * Constructor
     */

    public RedeemPointActorRecord() {

    }

    public RedeemPointActorRecord(String name, String publicKey, byte[] profileImage, long registrationDate, ConnectionState contactState) {
        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage.clone();
        this.registrationDate = registrationDate;
        this.connectionState = contactState;
    }

    public RedeemPointActorRecord(String name, String publicKey) {
        this.publicKey = publicKey;
        this.name = name;
    }

    public RedeemPointActorRecord(String name, String publicKey, byte[] profileImage, long registrationDate) {

        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage.clone();
        this.registrationDate = registrationDate;
        this.connectionState = ConnectionState.CONNECTED;

    }

    public RedeemPointActorRecord(String publicKey, String name, ConnectionState connectionState,
                                  double locationLatitude, double locationLongitude, long registrationDate,
                                  byte[] profileImage) {
        this.publicKey = publicKey;
        this.name = name;
        this.connectionState = connectionState;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.registrationDate = registrationDate;
        this.profileImage = profileImage.clone();
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
        return Validate.verifyString(this.name);
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


    /**
     * {@inheritDoc}
     */
    @Override
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConnectionState getConnectionState() {
        return connectionState;
    }

//    /**
//     *
//     * {@inheritDoc}
//     */
//    @Override
//    public Location getLocation() {
//        return location;
//    }

    @Override
    public Double getLocationLatitude() {
        return this.locationLatitude;
    }

    @Override
    public Double getLocationLongitude() {
        return this.locationLongitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Address getAddress() {
        return address;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContactInformation() {
        return Validate.verifyString(contactInformation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHoursOfOperation() {
        return Validate.verifyString(hoursOfOperation);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }

    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void setHoursOfOperation(String hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
    }
}
