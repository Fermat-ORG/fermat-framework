package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.classes;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;

import java.util.List;
import java.util.UUID;


/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.classes.CryptoBrokerCommunitySubAppModuleInformation</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/12/2015.
 */
public class CryptoBrokerCommunitySubAppModuleInformation implements CryptoBrokerCommunityInformation {

    private final String publicKey;
    private final String alias;
    private final byte[] image;
    private final ConnectionState connectionState;
    private final UUID connectionId;
    private Location location;
    private String country;
    private String place;


    public CryptoBrokerCommunitySubAppModuleInformation(final String publicKey,
                                                        final String alias,
                                                        final byte[] image) {

        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
        this.connectionState = null;
        this.connectionId = null;
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final String publicKey,
                                                        final String alias,
                                                        final byte[] image,
                                                        final ConnectionState connectionState,
                                                        final UUID connectionId,
                                                        final Location location) {

        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
        this.connectionState = connectionState;
        this.connectionId = connectionId;
        this.location = location;
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final String publicKey,
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

    public CryptoBrokerCommunitySubAppModuleInformation(final CryptoBrokerActorConnection actorConnection) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias = actorConnection.getAlias();
        this.image = actorConnection.getImage();
        this.connectionState = actorConnection.getConnectionState();
        this.connectionId = actorConnection.getConnectionId();
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final CryptoBrokerExposingData exposingData) {

        this.publicKey = exposingData.getPublicKey();
        this.alias = exposingData.getAlias();
        this.image = exposingData.getImage();
        this.connectionState = null;
        this.connectionId = null;
        this.location = exposingData.getLocation();
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
    public List listCryptoBrokerWallets() {
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
        return location;
    }

    @Override
    public String toString() {
        return "CryptoBrokerCommunitySubAppModuleInformation{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", connectionState='" + connectionState + '\'' +
                ", connectionId='" + connectionId + '\'' +
                ", image=" + (image != null) +
                '}';
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

        if (!CryptoBrokerCommunityInformation.class.isAssignableFrom(obj.getClass())) return false;

        final CryptoBrokerCommunitySubAppModuleInformation other = (CryptoBrokerCommunitySubAppModuleInformation) obj;

        return !((this.publicKey == null) ? (other.publicKey != null) : !this.publicKey.equals(other.publicKey));

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.publicKey != null ? this.publicKey.hashCode() : 0);
        return hash;
    }
}
