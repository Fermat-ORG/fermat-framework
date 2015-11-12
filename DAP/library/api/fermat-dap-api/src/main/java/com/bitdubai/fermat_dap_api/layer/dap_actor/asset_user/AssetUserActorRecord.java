package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user;

import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.util.Arrays;

/**
 * Created by Nerio on 22/09/15.
 */
public class AssetUserActorRecord implements ActorAssetUser {

    private String              publicLinkedIdentity    ;
    private String              publicKey               ;
    private String              name                    ;
    private String              age                     ;
    private Genders             genders                 ;
    private DAPConnectionState  dapConnectionState      ;
    private Location            location                ;
    private Double              locationLatitude        ;
    private Double              locationLongitude       ;
    private long                registrationDate        ;
    private long                lastConnectionDate      ;
    private CryptoAddress       cryptoAddress           ;
    private byte[]              profileImage            ;

    /**
     * Constructor
     */
    public AssetUserActorRecord(){

    }

    /**
     *  Method for Set Actor in Actor Network Service User
     */
    public AssetUserActorRecord(String publicKey,
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

        this.genders                = Genders.INDEFINITE        ;
//        if(!age.isEmpty())
//            this.age = age;
        this.dapConnectionState        = DAPConnectionState.REGISTERED_ONLINE ;

    }

    public AssetUserActorRecord(String publicKey,
                                String name,
                                String age,
                                Genders genders,
                                DAPConnectionState dapConnectionState,
                                Double locationLatitude,
                                Double locationLongitude,
                                CryptoAddress cryptoAddress,
                                Long registrationDate,
                                Long lastConnectionDate,
                                byte[] profileImage){

        this.publicKey                  = publicKey             ;
        this.name                       = name                  ;
        this.age                        = age                   ;
        this.genders                    = genders               ;
        this.dapConnectionState         = dapConnectionState    ;

        if(cryptoAddress != null)
            this.cryptoAddress          = cryptoAddress         ;

        if (locationLatitude != null)
            this.locationLatitude       = locationLatitude      ;
        if(locationLongitude != null)
            this.locationLongitude      = locationLongitude     ;

        this.registrationDate           = registrationDate      ;
        this.lastConnectionDate         = lastConnectionDate    ;
        this.profileImage               = profileImage.clone()  ;

    }

    public AssetUserActorRecord(final String publicKey,
                                final String name,
                                final String age,
                                final Genders genders,
                                final DAPConnectionState dapConnectionState,
                                final Double locationLatitude,
                                final Double locationLongitude,
                                final Long registrationDate,
                                final Long lastConnectionDate,
                                final byte[] profileImage) {

        this.publicKey              =       publicKey               ;
        this.name                   =       name                    ;
        this.age                    =       age                     ;
        this.genders                =       genders                 ;
        this.dapConnectionState     =       dapConnectionState      ;
        this.locationLatitude       =       locationLatitude        ;
        this.locationLongitude      =       locationLongitude       ;
        this.registrationDate       =       registrationDate        ;
        this.lastConnectionDate     =       lastConnectionDate      ;
        this.profileImage           =       profileImage.clone()    ;

    }

    /**
     * The method <code>getPubliclinkedIdentity</code> gives us the public Linked Identity of the represented Asset User
     *
     * @return the Public Linked Identity
     */
    public String getPublicLinkedIdentity() {
        return publicLinkedIdentity;
    }

    public void setPublicLinkedIdentity(String publicLinkedIdentity) {
        this.publicLinkedIdentity = publicLinkedIdentity;
    }

    /**
     * The method <code>getPublicKey</code> gives us the public key of the represented Asset User
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
     * The method <code>getName</code> gives us the name of the represented Asset User
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
     * The method <code>getAge</code> gives us the Age of the represented Asset user
     *
     * @return the Age of the Asset user
     */
    @Override
    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    /**
     * The method <code>getGenders</code> gives us the Gender of the represented Asset user
     *
     * @return the Gender of the Asset user
     */
    @Override
    public Genders getGenders() {
        return genders;
    }

    public void setGenders(Genders genders) {
        if(genders != null)
           this.genders = genders;
        else
            this.genders = Genders.INDEFINITE;
    }

    /**
     * The method <code>getRegistrationDate</code> gives us the date when both Asset Users
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
     * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented Asset User
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
     * The method <code>getConnectionState</code> gives us the connection state of the represented Asset User
     *
     * @return the Connection state
     */
    @Override
    public DAPConnectionState getDapConnectionState() {
        return this.dapConnectionState;
    }

    public void setConnectionState(DAPConnectionState dapConnectionState) {
        if(dapConnectionState != null)
           this.dapConnectionState = dapConnectionState;
        else
            this.dapConnectionState = DAPConnectionState.REGISTERED_LOCALLY;
    }

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if(location != null) {
            this.location = location;
        }else{
            this.locationLatitude = (double) 0;
            this.locationLongitude = (double) 0;
        }
    }

    /**
     * The method <code>getLocationLatitude</code> gives us the Location of the represented Asset user
     *
     * @return the Location Latitude of the Asset user
     */
    @Override
    public Double getLocationLatitude() {
        return this.locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    /**
     * The method <code>getLocationLongitude</code> gives us the Location of the represented Asset user
     *
     * @return the Location Longitude of the Asset user
     */
    @Override
    public Double getLocationLongitude() {
        return this.locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset User
     *
     * @return the image
     */
    @Override
    public byte[] getProfileImage() {
        return this.profileImage.clone();
    }

    public void setProfileImage(byte[] profileImage) {
        if(profileImage != null)
            this.profileImage = profileImage;
        else
            this.profileImage = new byte[0];
    }

    /**
     * returns the crypto address to which it belongs
     *
     * @return CryptoAddress instance.
     */
    @Override
    public CryptoAddress getCryptoAddress() {
        return this.cryptoAddress;
    }

    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        if(cryptoAddress != null)
            this.cryptoAddress = cryptoAddress;
    }

    @Override
    public String toString() {
        return "AssetUserActorRecord{" +
                "publicLinkedIdentity='" + publicLinkedIdentity + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", genders=" + genders +
                ", dapConnectionState=" + dapConnectionState +
                ", location=" + location +
                ", locationLatitude=" + locationLatitude +
                ", locationLongitude=" + locationLongitude +
                ", registrationDate=" + registrationDate +
                ", lastConnectionDate=" + lastConnectionDate +
                ", cryptoAddress=" + cryptoAddress +
                ", profileImage=" + Arrays.toString(profileImage) +
                '}';
    }
}
