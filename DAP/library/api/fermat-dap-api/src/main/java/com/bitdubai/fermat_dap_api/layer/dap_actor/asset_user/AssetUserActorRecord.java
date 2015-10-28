package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Nerio on 22/09/15.
 */
public class AssetUserActorRecord implements ActorAssetUser {

    private String publiclinkedIdentity;
    private String publicKey;
    private String name;
    private String age;
    private Genders genders;
    private ConnectionState connectionState;
    private Double locationLatitude;
    private Double locationLongitude;
    private long registrationDate;
    private long lastConnectionDate;
    private CryptoAddress cryptoAddress;

    private byte[] profileImage;

    /**
     * Constructor
     *
     * @param assetUserActorPublicKey
     * @param assetUserActorName
     * @param assetUserActorprofileImage
     * @param locationLatitude
     * @param locationLongitude
     */

    public AssetUserActorRecord(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage, Double locationLatitude, Double locationLongitude) {

    }

    /**
     * Register in Actor Network Service
     */
    public AssetUserActorRecord(String publicKey, String name, byte[] profileImage, Location location) {

        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage.clone();
        this.genders = Genders.INDEFINITE;
//        this.age = age;
//        this.cryptoAddress = cryptoAddress;
        this.connectionState = ConnectionState.CONNECTED;

    }

    public AssetUserActorRecord(String publicKey, String name, String age, Genders genders,
                                ConnectionState connectionState, Double locationLatitude,
                                Double locationLongitude, CryptoAddress cryptoAddress,
                                Long registrationDate, Long lastConnectionDate,
                                byte[] profileImage) {

        this.publicKey = publicKey;
        this.name = name;
        this.age = age;
        this.genders = genders;
        this.connectionState = connectionState;
        this.cryptoAddress = cryptoAddress;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.registrationDate = registrationDate;
        this.lastConnectionDate = lastConnectionDate;
        this.profileImage = profileImage.clone();

    }

    @Override
    public String getPublicLinkedIdentity() {
        return this.publiclinkedIdentity;
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

    @Override
    public String getAge() {
        return this.age;
    }

    public void setName(String name) {
        this.name = name;
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
        if (genders != null)
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
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    public void setConnectionState(ConnectionState connectionState) {
        if (connectionState != null)
            this.connectionState = connectionState;
        else
            this.connectionState = ConnectionState.CONNECTED;
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
        if (profileImage != null)
            this.profileImage = profileImage;
        else
            this.profileImage = profileImage.clone();
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
        if (cryptoAddress != null)
            this.cryptoAddress = cryptoAddress;
    }
}
