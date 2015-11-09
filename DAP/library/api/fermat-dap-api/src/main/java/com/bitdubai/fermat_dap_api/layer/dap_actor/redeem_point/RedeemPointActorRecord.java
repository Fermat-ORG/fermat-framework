package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point;


import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.Address;

import java.util.Arrays;

/**
 * Created by Nerio on 22/09/15.
 */
public class RedeemPointActorRecord implements ActorAssetRedeemPoint {

    private String          publicKey;
    private String          name;
    private long            registrationDate;
    private ConnectionState connectionState;
    private CryptoAddress   cryptoAddress;
    private Location        location;
    private Double          locationLatitude;
    private Double          locationLongitude;
    private String          contactInformation;
    private String          hoursOfOperation;
    private Address         address;
    private byte[]          profileImage ;

    /**
     * Constructor
     */

    public RedeemPointActorRecord() {

    }

    /**
     *  Method for Set Actor in Actor Network Service Redeem Point
     */
    public RedeemPointActorRecord(String publicKey,
                                  String name,
                                  byte[] profileImage,
                                  Location location) {

        this.name                   = name                      ;
        this.publicKey              = publicKey                 ;
        this.profileImage           = profileImage.clone()      ;

        if (location != null) {
            this.locationLatitude   = location.getLatitude()    ;
            this.locationLongitude  = location.getLongitude()   ;
        }else{
            this.locationLatitude   = (double) 0                ;
            this.locationLongitude  = (double) 0                ;
        }
    }

    public RedeemPointActorRecord(String name,
                                  String publicKey) {

        this.publicKey  = publicKey ;
        this.name       = name      ;
    }

    public RedeemPointActorRecord(String name,
                                  String publicKey,
                                  byte[] profileImage,
                                  long registrationDate) {

        this.name               = name                      ;
        this.publicKey          = publicKey                 ;
        this.profileImage       = profileImage.clone()      ;
        this.registrationDate   = registrationDate          ;
        this.connectionState    = ConnectionState.CONNECTED ;

    }

    public RedeemPointActorRecord(String publicKey,
                                  String name,
                                  ConnectionState connectionState,
                                  double locationLatitude,
                                  double locationLongitude,
                                  long registrationDate,
                                  byte[] profileImage) {

        this.publicKey          = publicKey             ;
        this.name               = name                  ;
        this.connectionState    = connectionState       ;
        this.locationLatitude   = locationLatitude      ;
        this.locationLongitude  = locationLongitude     ;
        this.registrationDate   = registrationDate      ;
        this.profileImage       = profileImage.clone()  ;
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

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both Redeem Points
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    @Override
    public long getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
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

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }
//    /**
//     *
//     * {@inheritDoc}
//     */
    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Double getLocationLatitude() {
        return this.locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    @Override
    public Double getLocationLongitude() {
        return this.locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String getHoursOfOperation() {
        return hoursOfOperation;
    }

    public void setHoursOfOperation(String hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
    }

    @Override
    public String toString() {
        return "RedeemPointActorRecord{" +
                "publicKey='" + publicKey + '\'' +
                ", name='" + name + '\'' +
                ", registrationDate=" + registrationDate +
                ", connectionState=" + connectionState +
                ", cryptoAddress=" + cryptoAddress +
                ", location=" + location +
                ", locationLatitude=" + locationLatitude +
                ", locationLongitude=" + locationLongitude +
                ", contactInformation='" + contactInformation + '\'' +
                ", hoursOfOperation='" + hoursOfOperation + '\'' +
                ", address=" + address +
                ", profileImage=" + Arrays.toString(profileImage) +
                '}';
    }
}
