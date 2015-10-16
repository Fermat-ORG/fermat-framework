package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by francisco on 15/10/15.
 */
public class Actor implements ActorAssetUser {

    private String thumbnail;

    public Actor(String name, String thumbnail) {
//        super(name, null, null, null);
        setThumbnail(thumbnail);
    }

    public Actor(String name, String publicKey, byte[] profileImage, Location location) {
//        super(name, publicKey, profileImage, location);
    }

    public Actor(String name, String publicKey, byte[] profileImage, long registrationDate, Genders genders, String age) {
//        super(name, publicKey, profileImage, registrationDate, genders, age);
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * The metho <code>getPublicKey</code> gives us the public key of the represented Asset user
     *
     * @return the public key
     */
    @Override
    public String getPublicKey() {
        return null;
    }

    /**
     * The method <code>getName</code> gives us the name of the represented Asset user
     *
     * @return the name of the intra user
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both Asset users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    @Override
    public long getContactRegistrationDate() {
        return 0;
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
        return null;
    }

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    @Override
    public Location getLocation() {
        return null;
    }

    /**
     * The method <code>getGender</code> gives us the Gender of the represented Asset user
     *
     * @return the Gender of the Asset user
     */
    @Override
    public Genders getGender() {
        return null;
    }

    /**
     * The method <code>getAge</code> gives us the Age of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    @Override
    public String getAge() {
        return null;
    }

    /**
     * returns the crypto address to which it belongs
     *
     * @return CryptoAddress instance.
     */
    @Override
    public CryptoAddress getCryptoAddress() {
        return null;
    }
}
