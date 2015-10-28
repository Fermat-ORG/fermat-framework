package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Actor Model
 */
public class Actor implements ActorAssetUser {

    private String publicKey;
    private String publicLinkedKey;
    private String name;
    private long registrationDate;
    private long lastConnectionDate;
    private byte[] profileImage;
    private Double latitude;
    private Double longitude;
    private Genders gender;
    private ConnectionState state;
    private CryptoAddress cryptoAddress;
    private String age;

    public Actor(String name, String publicKey, byte[] profileImage, Location location) {
        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public Actor(String name, String publicKey, byte[] profileImage, long registrationDate, long lastConnectionDate, Genders genders, String age) {
        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.registrationDate = registrationDate;
        this.lastConnectionDate = lastConnectionDate;
        this.gender = genders;
        this.age = age;
    }

    @Override
    public String getPublicLinkedIdentity() {
        return publicLinkedKey;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public long getLastConnectionDate() {
        return 0;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setLastConnectionDate(long lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    @Override
    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public ConnectionState getConnectionState() {
        return state;
    }

    @Override
    public Double getLocationLatitude() {
        return latitude;
    }

    @Override
    public Double getLocationLongitude() {
        return longitude;
    }

    @Override
    public Genders getGenders() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    @Override
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    public void setCryptoAddress(CryptoAddress cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setState(ConnectionState state) {
        this.state = state;
    }
}
