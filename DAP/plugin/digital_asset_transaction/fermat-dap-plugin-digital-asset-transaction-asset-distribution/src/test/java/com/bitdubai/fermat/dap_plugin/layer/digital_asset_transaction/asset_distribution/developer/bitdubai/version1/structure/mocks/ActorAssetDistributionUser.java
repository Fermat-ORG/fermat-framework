package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class ActorAssetDistributionUser implements ActorAssetUser {

    private String name;
    private String actorPublicKey;
    private byte[] profileImage;
    private long registrationDate;
    private long lastConnectionDate;

    @Override
    public String getPublicLinkedIdentity() {
        return new ECCKeyPair().getPublicKey();
    }

    @Override
    public String getActorPublicKey() {
        return this.actorPublicKey;
    }

    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getRegistrationDate() {
        return this.registrationDate;
    }

    @Override
    public long getLastConnectionDate() {
        return this.lastConnectionDate;
    }

    @Override
    public DAPConnectionState getDapConnectionState() {
        return DAPConnectionState.CONNECTED_ONLINE;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setLastConnectionDate(long lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    @Override
    public byte[] getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
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
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
    @Override
    public Double getLocationLatitude() {
        return null;
    }

    @Override
    public Double getLocationLongitude() {
        return null;
    }

    /**
     * The method <code>getLocation</code> gives us the Location of the represented Asset user
     *
     * @return the Location of the Asset user
     */
//    @Override
//    public Location getLocation() {
//        return null;
//    }

    /**
     * The method <code>getGender</code> gives us the Gender of the represented Asset user
     *
     * @return the Gender of the Asset user
     */
    @Override
    public Genders getGenders() {
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


    public void setActorAssetUser(ActorAssetUser actorAssetUser) {
        setRegistrationDate(actorAssetUser.getRegistrationDate());
        setLastConnectionDate(actorAssetUser.getLastConnectionDate());
        setName(actorAssetUser.getName());
        setProfileImage(actorAssetUser.getProfileImage());
        setActorPublicKey(actorAssetUser.getActorPublicKey());
    }
}
