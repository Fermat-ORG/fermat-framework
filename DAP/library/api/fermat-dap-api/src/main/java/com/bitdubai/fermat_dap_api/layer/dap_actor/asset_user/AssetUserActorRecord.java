package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
     */

    public AssetUserActorRecord(){

    }

   /**
    *  Register in Actor Network Service
    */
    public AssetUserActorRecord(String name, String publicKey, byte[] profileImage, Location location) {

        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage.clone();
        if(location != null){
            this.locationLatitude = location.getLatitude();
            this.locationLongitude = location.getLongitude();
        }
        this.genders = Genders.INDEFINITE;
//        this.age = age;
//        this.cryptoAddress = cryptoAddress;
        this.connectionState = ConnectionState.CONNECTED;

    }

    public AssetUserActorRecord(String publicKey, String name, String age, Genders genders,
                                ConnectionState connectionState, Double locationLatitude,
                                Double locationLongitude, CryptoAddress cryptoAddress,
                                Long registrationDate, Long lastConnectionDate,
                                byte[] profileImage){

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

    /**
     * The method <code>getPublicKey</code> gives us the public key of the represented Asset User
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
    public long getRegistrationDate() {
        return this.registrationDate;
    }

    @Override
    public long getLastConnectionDate() {
        return this.lastConnectionDate;
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
