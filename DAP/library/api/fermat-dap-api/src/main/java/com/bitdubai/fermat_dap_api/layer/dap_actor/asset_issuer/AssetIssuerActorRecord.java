package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;

import java.util.Arrays;

/**
 * Created by Nerio on 22/09/15.
 */
public class AssetIssuerActorRecord implements ActorAssetIssuer, DAPActor {

    private String              publicLinkedIdentity    ;
    private String              actorPublicKey          ;
    private String              name                    ;
    private Actors              type = Actors.DAP_ASSET_ISSUER;
    private String              description             ;
    private long                registrationDate        ;
    private long                lastConnectionDate      ;
    private DAPConnectionState  dapConnectionState      ;
    private Location            location                ;
    private Double              locationLatitude        ;
    private Double              locationLongitude       ;
    private CryptoAddress       cryptoAddress           ;
    private byte[]              profileImage            ;

    /**
     * Constructor
     */

    public AssetIssuerActorRecord() {

    }

    public AssetIssuerActorRecord(String name,
                                  String actorPublicKey) {
        this.name           = name              ;
        this.actorPublicKey = actorPublicKey    ;
    }

    /**
     *  Method for Set Actor in Actor Network Service Issuer
     */
    public AssetIssuerActorRecord(String actorPublicKey,
                                  String name,
                                  byte[] profileImage,
                                  Location location) {

        this.actorPublicKey         = actorPublicKey            ;
        this.name                   = name                      ;
        this.profileImage           = profileImage.clone()      ;
        if (location != null) {
            this.locationLatitude   = location.getLatitude()    ;
            this.locationLongitude  = location.getLongitude()   ;
        }else{
            this.locationLatitude   = (double) 0                ;
            this.locationLongitude  = (double) 0                ;
        }
    }

    public AssetIssuerActorRecord(String actorPublicKey,
                                  String name,
                                  byte[] profileImage,
                                  long registrationDate) {

        this.name               = name                                  ;
        this.actorPublicKey     = actorPublicKey                        ;
        this.profileImage       = profileImage.clone()                  ;
        this.registrationDate   = registrationDate                      ;
        this.dapConnectionState = DAPConnectionState.REGISTERED_ONLINE  ;
    }

    public AssetIssuerActorRecord(String actorPublicKey,
                                  String name,
                                  DAPConnectionState dapConnectionState,
                                  double locationLatitude,
                                  double locationLongitude,
                                  long registrationDate,
                                  byte[] profileImage,
                                  String description) {

        this.actorPublicKey         = actorPublicKey        ;
        this.name                   = name                  ;
        this.dapConnectionState     = dapConnectionState    ;
        this.locationLatitude       = locationLatitude      ;
        this.locationLongitude      = locationLongitude     ;
        this.registrationDate       = registrationDate      ;
        this.profileImage           = profileImage.clone()  ;
        if(!description.isEmpty())
            this.description        = description           ;
    }

    public AssetIssuerActorRecord(final String actorPublicKey,
                                  final String name,
                                  final DAPConnectionState dapConnectionState,
                                  final Double locationLatitude,
                                  final Double locationLongitude,
                                  final Long registrationDate,
                                  final Long lastConnectionDate,
                                  final byte[] profileImage,
                                  final String description) {

        this.actorPublicKey         =       actorPublicKey          ;
        this.name                   =       name                    ;
        this.dapConnectionState     =       dapConnectionState      ;
        this.locationLatitude       =       locationLatitude        ;
        this.locationLongitude      =       locationLongitude       ;
        this.registrationDate       =       registrationDate        ;
        this.lastConnectionDate     =       lastConnectionDate      ;
        this.profileImage           =       profileImage.clone()    ;
        this.description            =       description             ;

    }


    /**
     * The metho <code>getActorPublicKey</code> gives us the public key of the represented Asset Issuer
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
     * The method <code>getName</code> gives us the name of the represented Asset Issuer
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

    @Override
    public Actors getType() {
        return type;
    }

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both  Asset Issuers
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
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented Asset Issuer
     *
     * @return the Connection Date
     */
    @Override
    public long getLastConnectionDate() {
        return this.lastConnectionDate;
    }

    public void setLastConnectionDate(long lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset Issuer
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
     * The method <code>getConnectionState</code> gives us the Coonection state of the represented Asset Issuer
     *
     * @return the Coonection state
     */
    @Override
    public DAPConnectionState getDapConnectionState() {
        return this.dapConnectionState;
    }

    public void setDapConnectionState(DAPConnectionState dapConnectionState) {
        this.dapConnectionState = dapConnectionState;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if(location != null)
            this.location = location;
    }

    @Override
    public Double getLocationLatitude() {
        return locationLatitude;
    }

    @Override
    public Double getLocationLongitude() {
        return locationLongitude;
    }

//    @Override
//    public CryptoAddress getCryptoAddress() {
//        return cryptoAddress;
//    }

    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        if(cryptoAddress != null)
            this.cryptoAddress = cryptoAddress;
    }

    @Override
    public String toString() {
        return "AssetIssuerActorRecord{" +
                "publicLinkedIdentity='" + publicLinkedIdentity + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", actorPublicKey='" + actorPublicKey + '\'' +
                ", registrationDate=" + registrationDate +
                ", DAPConnectionState=" + dapConnectionState +
                ", location=" + location +
                ", locationLatitude=" + locationLatitude +
                ", locationLongitude=" + locationLongitude +
                ", cryptoAddress=" + cryptoAddress +
                ", profileImage=" + Arrays.toString(profileImage) +
                '}';
    }
}
