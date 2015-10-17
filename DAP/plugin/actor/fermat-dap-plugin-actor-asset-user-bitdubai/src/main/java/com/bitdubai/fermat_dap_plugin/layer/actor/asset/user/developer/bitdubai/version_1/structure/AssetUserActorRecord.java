package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Nerio on 22/09/15.
 */
public class AssetUserActorRecord implements ActorAssetUser {

    private String linkedIdentity;
    private String publicKey;
    private String name;
    private byte[] profileImage;
    private Location location;
    private Double locationLatitude;
    private Double locationLongitude;
    private long registrationDate;
    private ConnectionState connectionState;
    private Genders genders;
    private String age;
    private CryptoAddress cryptoAddress;

    /**
     * Constructor
     */

    public AssetUserActorRecord(String name, String publicKey, byte[] profileImage, Location location) {

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

    public AssetUserActorRecord(String name, String publicKey, byte[] profileImage, long registrationDate, Genders genders, String age) {

        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage.clone();
        this.registrationDate = registrationDate;
//        this.location = location;
        this.genders = genders;
        this.age = age;
//        this.cryptoAddress = cryptoAddress;
        this.connectionState = ConnectionState.CONNECTED;

    }

    public AssetUserActorRecord(String linkedIdentity, String publicKey, String name, Double locationLatitude, Double locationLongitude, Genders genders, String age){

        this.linkedIdentity = linkedIdentity;
        this.publicKey = publicKey;
        this.name = name;
        this.profileImage = profileImage.clone();
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.genders = genders;
        this.age = age;
//        this.cryptoAddress = cryptoAddress;
        this.connectionState = ConnectionState.CONNECTED;

    }


    /**
     * The metho <code>getPublicKey</code> gives us the public key of the represented Asset User
     *
     * @return the public key
     */
    @Override
    public String getPublicKey() {
        return this.publicKey;
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

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both Asset Users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    @Override
    public long getContactRegistrationDate() {
        return this.registrationDate;
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

    /**
     * The method <code>getContactState</code> gives us the contact state of the represented Asset User
     *
     * @return the contact state
     */
    @Override
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    @Override
    public Double getLocationLatitude() {
        return this.locationLatitude;
    }

    @Override
    public Double getLocationLongitude() {
        return this.locationLongitude;
    }

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
//    @Override
//    public Location getLocation() {
//        return this.location;
//    }

    /**
     * The method <code>getGender</code> gives us the Gender of the represented Asset user
     *
     * @return the Gender of the Asset user
     */
    @Override
    public Genders getGender() {
        return this.genders;
    }

    /**
     * The method <code>getAge</code> gives us the Age of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    @Override
    public String getAge() {
        return this.age;
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
}
