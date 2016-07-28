package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;

import java.util.List;
import java.util.UUID;


/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */

public class CryptoCustomerCommunitySubAppModuleInformation implements CryptoCustomerCommunityInformation {

    private final String publicKey;
    private final String alias;
    private final byte[] image;
    private final ConnectionState connectionState;
    private final UUID connectionId;
    private Location location;
    private String country;
    private String place;
    private ProfileStatus profileStatus;


    public CryptoCustomerCommunitySubAppModuleInformation(String publicKey,
                                                          String alias,
                                                          byte[] image,
                                                          ConnectionState connectionState,
                                                          UUID connectionId,
                                                          Location location,
                                                          ProfileStatus profileStatus) {
        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
        this.connectionState = connectionState;
        this.connectionId = connectionId;
        this.location = location;
        this.profileStatus = profileStatus;
    }

    public CryptoCustomerCommunitySubAppModuleInformation(final String publicKey,
                                                          final String alias,
                                                          final byte[] image,
                                                          final ConnectionState connectionState,
                                                          final UUID connectionId,
                                                          final Location location,
                                                          String country,
                                                          String place) {
        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
        this.connectionState = connectionState;
        this.connectionId = connectionId;
        this.location = location;
        this.country = country;
        this.place = place;
    }

    public CryptoCustomerCommunitySubAppModuleInformation(final CryptoCustomerActorConnection actorConnection, Location location) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias = actorConnection.getAlias();
        this.image = actorConnection.getImage();
        this.connectionState = actorConnection.getConnectionState();
        this.connectionId = actorConnection.getConnectionId();
        this.location = location;

    }

    public CryptoCustomerCommunitySubAppModuleInformation(final CryptoCustomerExposingData exposingData) {

        this.publicKey = exposingData.getPublicKey();
        this.alias = exposingData.getAlias();
        this.image = exposingData.getImage();
        this.connectionState = null;
        this.connectionId = null;
        this.location = exposingData.getLocation();
        if (exposingData.getProfileStatus() != null)
            this.profileStatus = exposingData.getProfileStatus();
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public byte[] getImage() {
        return image;
    }

    @Override
    public List listCryptoCustomerWallets() {
        return null;
    }

    @Override
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    @Override
    public UUID getConnectionId() {
        return this.connectionId;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public ProfileStatus getProfileStatus() {
        return this.profileStatus;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CryptoCustomerCommunitySubAppModuleInformation{")
                .append("publicKey='").append(publicKey)
                .append('\'')
                .append(", alias='").append(alias)
                .append('\'')
                .append(", connectionState='").append(connectionState)
                .append('\'')
                .append(", connectionId='").append(connectionId)
                .append('\'')
                .append(", image=").append(image != null)
                .append('\'')
                .append(", country='").append(country)
                .append('\'')
                .append(", place='").append(place)
                .append('}').toString();
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getPlace() {
        return place;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!CryptoCustomerCommunityInformation.class.isAssignableFrom(obj.getClass()))
            return false;

        final CryptoCustomerCommunitySubAppModuleInformation other = (CryptoCustomerCommunitySubAppModuleInformation) obj;

        return !((this.publicKey == null) ? (other.publicKey != null) : !this.publicKey.equals(other.publicKey));

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.publicKey != null ? this.publicKey.hashCode() : 0);
        return hash;
    }
}
