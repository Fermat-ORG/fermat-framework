package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.classes;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityExtraData;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;

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
    private ProfileStatus profileStatus;
    private CryptoBrokerIdentityExtraData cryptoBrokerIdentityExtraData;


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
                                                        final Location location,
                                                        final ProfileStatus profileStatus) {

        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
        this.connectionState = connectionState;
        this.connectionId = connectionId;
        this.location = location;
        this.profileStatus = profileStatus;
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final String publicKey,
                                                        final String alias,
                                                        final byte[] image,
                                                        final ConnectionState connectionState,
                                                        final UUID connectionId,
                                                        final Location location,
                                                        final ProfileStatus profileStatus,
                                                        CryptoBrokerIdentityExtraData cryptoBrokerIdentityExtraData) {

        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
        this.connectionState = connectionState;
        this.connectionId = connectionId;
        this.location = location;
        this.profileStatus = profileStatus;
        this.cryptoBrokerIdentityExtraData = cryptoBrokerIdentityExtraData;
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final CryptoBrokerActorConnection actorConnection, CryptoBrokerExposingData exposingData) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias = actorConnection.getAlias();
        this.image = actorConnection.getImage();
        this.connectionState = actorConnection.getConnectionState();
        this.connectionId = actorConnection.getConnectionId();
        this.location = exposingData.getLocation();
        this.cryptoBrokerIdentityExtraData = exposingData.getCryptoBrokerIdentityExtraData();
    }

    public CryptoBrokerCommunitySubAppModuleInformation(final CryptoBrokerActorConnection actorConnection, Location location) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias = actorConnection.getAlias();
        this.image = actorConnection.getImage();
        this.connectionState = actorConnection.getConnectionState();
        this.connectionId = actorConnection.getConnectionId();
        this.location = location;
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
        this.cryptoBrokerIdentityExtraData = exposingData.getCryptoBrokerIdentityExtraData();
        if (exposingData.getProfileStatus() != null)
            this.profileStatus = exposingData.getProfileStatus();
    }

    public CryptoBrokerCommunitySubAppModuleInformation(String publicKey,
                                                        String alias,
                                                        byte[] image,
                                                        ConnectionState connectionState,
                                                        UUID connectionId,
                                                        Location location,
                                                        String country,
                                                        String place,
                                                        CryptoBrokerIdentityExtraData brokerExtraData) {
        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
        this.connectionState = connectionState;
        this.connectionId = connectionId;
        this.location = location;
        this.country = country;
        this.place = place;
        this.cryptoBrokerIdentityExtraData = brokerExtraData;
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
        return new StringBuilder()
                .append("CryptoBrokerCommunitySubAppModuleInformation{")
                .append("publicKey='").append(publicKey)
                .append('\'')
                .append(", alias='").append(alias)
                .append('\'')
                .append(", connectionState='").append(connectionState)
                .append('\'')
                .append(", connectionId='").append(connectionId)
                .append('\'')
                .append(", image=").append(image != null)
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

    @Override
    public ProfileStatus getProfileStatus() {
        return profileStatus;
    }

    @Override
    public CryptoBrokerIdentityExtraData getCryptoBrokerIdentityExtraData() {
        if (cryptoBrokerIdentityExtraData == null) {
            //Default CryptoBrokerIdentityExtraData
            this.cryptoBrokerIdentityExtraData = new CryptoBrokerIdentityExtraData(
                    CryptoCurrency.BITCOIN,
                    FiatCurrency.US_DOLLAR,
                    "Not Available Merchandises");
        }
        return this.cryptoBrokerIdentityExtraData;
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
