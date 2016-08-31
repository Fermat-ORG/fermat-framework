package com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;

import java.io.Serializable;

/**
 * Created by franklin on 02/11/15.
 * Edited by Miguel Rincon on 19/04/2016
 */
public class ChatIdentityImpl implements ChatIdentity, Serializable {

    private String alias;
    private String publicKey;
    private byte[] profileImage;
    private String privateKey;
    private String country;
    private String state;
    private String city;
    private String connectionState;
    private long accuracy;
    private GeoFrequency frecuency;

    /**
     * Constructor
     */
    public ChatIdentityImpl(String alias, String publicKey, String privateKey, byte[] profileImage, String country, String state, String city, String connectionState, long accuracy, GeoFrequency frecuency) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.privateKey = privateKey;
        this.country = country;
        this.state = state;
        this.city = city;
        this.connectionState = connectionState;
        this.accuracy = accuracy;
        this.frecuency = frecuency;
    }

    public ChatIdentityImpl(String alias, String publicKey, byte[] profileImage, String country, String state, String city, String connectionState, long accuracy, GeoFrequency frecuency) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.country = country;
        this.state = state;
        this.city = city;
        this.connectionState = connectionState;
        this.accuracy = accuracy;
        this.frecuency = frecuency;
    }

    public ChatIdentityImpl() {

    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public Actors getActorType() {
        return Actors.CHAT;
    }

    @Override
    public byte[] getImage() {
        return this.profileImage;
    }

    @Override
    public String createMessageSignature(String message) {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch (Exception e) {

            //TODO: Revisar este manejo de excepciones
            // throw new CantSignIntraWalletUserMessageException("Fatal Error Signed message", e, "", "");
        }
        return null;
    }

    /**
     * This method return String with Country
     *
     * @return the String
     */
    @Override
    public String getCountry() {
        return this.country;
    }

    /**
     * This method return String with State
     *
     * @return the String
     */
    @Override
    public String getState() {
        return this.state;
    }

    /**
     * This method return String with City
     *
     * @return the String
     */
    @Override
    public String getCity() {
        return this.city;
    }

    /**
     * This method return String with ConnectionState
     *
     * @return the String
     */
    @Override
    public String getConnectionState() {
        return this.connectionState;
    }

    /**
     * This method return long with Accurancy
     *
     * @return the Long
     */
    @Override
    public long getAccuracy() {
        return this.accuracy;
    }

    /**
     * This method return enum with GeoFrequency
     *
     * @return the Enum GeoFrequency
     */
    @Override
    public GeoFrequency getFrecuency() {
        return this.frecuency;
    }
}
