package com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.Address;

/**
 * Actor Model
 */
public class Actor implements ActorAssetRedeemPoint {

    private String publicKey;
    private String name;
    private long contactRegistrationDate;
    private byte[] profileImage;
    private Location location;
    private Double latitude;
    private Double longitude;
    private Address address;
    private ConnectionState state;
    private CryptoAddress cryptoAddress;
    private String contactInformation;
    private String hoursOfOperation;

    public Actor(String name, String publicKey, byte[] profileImage, Location location) {
        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public Actor(String name, String publicKey, byte[] profileImage, long registrationDate, Address address, String contactInformation, String hoursOfOperation) {
        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.contactRegistrationDate = registrationDate;
        this.address = address;
        this.contactInformation = contactInformation;
        this.hoursOfOperation = hoursOfOperation;
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
        return contactRegistrationDate;
    }

    public void setContactRegistrationDate(long contactRegistrationDate) {
        this.contactRegistrationDate = contactRegistrationDate;
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

    /**
     * Metodo {@code getLocation()}:
     * devuelve la ubicacion geografica del RedeemPoint.
     *
     * @return un objeto {@link Location}
     * que contiene las coordenadas y demas valores para ubicar al RedeemPoint en el mapa.
     */
    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String getContactInformation() {
        return this.contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    @Override
    public String getHoursOfOperation() {
        return this.hoursOfOperation;
    }

    public void setHoursOfOperation(String hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
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
