package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Nerio on 12/10/15.
 */
public class AssetUserActorNetworkService implements ActorAssetUser {

    private String name;
    private String publicKey;
    private byte[] profileImage;
    private Location location;
    private long registrationDate;
    private ConnectionState connectionState;
    private Genders genders;
    private String age;
    private String cryptoAddress;

    public AssetUserActorNetworkService(String name,String publicKey,byte[] profileImage, Location location){

        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage.clone();
        this.location = location;
//        this.registrationDate = registrationDate;
//        this.genders = genders;
//        this.age = age;
//        this.cryptoAddress = cryptoAddress;

    }

    /**
     * The metho <code>getPublicKey</code> gives us the public key of the represented Asset user
     *
     * @return the public key
     */
    @Override
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * The method <code>getName</code> gives us the name of the represented Asset user
     *
     * @return the name of the intra user
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both Asset users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    @Override
    public long getContactRegistrationDate() {
        return registrationDate;
    }

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented Asset user
     *
     * @return the image
     */
    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    /**
     * The method <code>getConnectionState</code> gives us the ConnectionState state of the represented Asset
     * user
     *
     * @return the contact state
     */
    @Override
    public ConnectionState getConnectionState() {
        return connectionState;
    }

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * The method <code>getGender</code> gives us the Gender of the represented Asset user
     *
     * @return the Gender of the Asset user
     */
    @Override
    public Genders getGender() {
        return genders;
    }

    /**
     * The method <code>getAge</code> gives us the Age of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    @Override
    public String getAge() {
        return age;
    }

    /**
     * returns the crypto address to which it belongs
     *
     * @return CryptoAddress instance.
     */
    @Override
    public String getCryptoAddress() {
        return cryptoAddress;
    }
}
