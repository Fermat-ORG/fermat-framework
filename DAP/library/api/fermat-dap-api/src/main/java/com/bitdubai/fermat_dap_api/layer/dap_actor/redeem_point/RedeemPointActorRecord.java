package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.Address;

import java.util.Arrays;

/**
 * Created by Nerio on 22/09/15.
 */
public class RedeemPointActorRecord implements ActorAssetRedeemPoint {

    private String              actorPublicKey      ;
    private String              name                ;
    private long                registrationDate    ;
    private long                lastConnectionDate  ;
    private DAPConnectionState  dapConnectionState  ;
    private CryptoAddress       cryptoAddress       ;
    private Location            location            ;
    private Double              locationLatitude    ;
    private Double              locationLongitude   ;
    private String              contactInformation  ;
    private String              hoursOfOperation    ;
    private Address             address             ;
    private byte[]              profileImage        ;

    /**
     * Constructor
     */

    public RedeemPointActorRecord() {

    }

    /**
     *  Method for Set Actor in Actor Network Service Redeem Point
     */
    public RedeemPointActorRecord(String actorPublicKey,
                                  String name,
                                  byte[] profileImage,
                                  Location location) {

        this.name                   = name                                  ;
        this.actorPublicKey         = actorPublicKey                        ;
        this.profileImage           = profileImage.clone()                  ;

        if (location != null) {
            this.locationLatitude   = location.getLatitude()                ;
            this.locationLongitude  = location.getLongitude()               ;
        }else{
            this.locationLatitude   = (double) 0                            ;
            this.locationLongitude  = (double) 0                            ;
        }
        this.dapConnectionState     = DAPConnectionState.REGISTERED_ONLINE  ;

    }

    public RedeemPointActorRecord(String name,
                                  String actorPublicKey) {

        this.actorPublicKey = actorPublicKey    ;
        this.name           = name              ;
    }

    public RedeemPointActorRecord(String name,
                                  String actorPublicKey,
                                  byte[] profileImage,
                                  long registrationDate) {

        this.name               = name                                  ;
        this.actorPublicKey     = actorPublicKey                        ;
        this.profileImage       = profileImage.clone()                  ;
        this.registrationDate   = registrationDate                      ;
        this.dapConnectionState = DAPConnectionState.REGISTERED_ONLINE  ;

    }

    public RedeemPointActorRecord(String actorPublicKey,
                                  String name,
                                  DAPConnectionState dapConnectionState,
                                  double locationLatitude,
                                  double locationLongitude,
                                  long registrationDate,
                                  byte[] profileImage) {

        this.actorPublicKey     = actorPublicKey        ;
        this.name               = name                  ;
        this.dapConnectionState = dapConnectionState    ;
        this.locationLatitude   = locationLatitude      ;
        this.locationLongitude  = locationLongitude     ;
        this.registrationDate   = registrationDate      ;
        this.profileImage       = profileImage.clone()  ;
    }

    public RedeemPointActorRecord(final String actorPublicKey,
                                  final String name,
                                  final DAPConnectionState dapConnectionState,
                                  final Double locationLatitude,
                                  final Double locationLongitude,
                                  final Long registrationDate,
                                  final Long lastConnectionDate,
                                  final byte[] profileImage) {

        this.actorPublicKey         =       actorPublicKey          ;
        this.name                   =       name                    ;
        this.dapConnectionState     =       dapConnectionState      ;
        this.locationLatitude       =       locationLatitude        ;
        this.locationLongitude      =       locationLongitude       ;
        this.registrationDate       =       registrationDate        ;
        this.lastConnectionDate     =       lastConnectionDate      ;
        this.profileImage           =       profileImage.clone()    ;

    }

    /**
     * The method <code>getActorPublicKey</code> gives us the public key of the represented Redeem Point
     *
     * @return the public key
     */
    @Override
    public String getActorPublicKey() {
        return this.actorPublicKey;
    }

    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    /**
     * The method <code>getName</code> gives us the name of the represented Redeem Point
     *
     * @return the name of the Actor
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
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the
     * represented Asset Redeem Point
     *
     * @return the Connection Date
     */
    public long getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(long lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
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
    public DAPConnectionState getDapConnectionState() {
        return dapConnectionState;
    }

    public void setDapConnectionState(DAPConnectionState dapConnectionState) {
        this.dapConnectionState = dapConnectionState;
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
                " actorPublicKey='" + actorPublicKey + '\'' +
                ", name='" + name + '\'' +
                ", registrationDate=" + registrationDate +
                ", DAPConnectionState=" + dapConnectionState +
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
