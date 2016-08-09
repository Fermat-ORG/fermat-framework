package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;

/**
 * Created by franklin on 01/03/16.
 */
public class ChatUserIdentityImpl implements ChatUserIdentity {

    private String alias;
    private String phrase;
    private String publicKey;
    private byte[] image;
    private String privateKey;
    private Actors actors;
    private PlatformComponentType platformComponentType;

    /**
     * Constructor
     */
    public ChatUserIdentityImpl(String alias, String phrase, String publicKey, String privateKey, byte[] image, Actors actors, PlatformComponentType platformComponentType) {
        this.alias = alias;
        this.phrase = phrase;
        this.publicKey = publicKey;
        this.image = image;
        this.privateKey = privateKey;
        this.actors = actors;
        this.platformComponentType = platformComponentType;
//        this.pluginFileSystem = pluginFileSystem;
//        this.pluginId = pluginId;
    }

    /**
     * The method <code>getPhrase</code> returns the phrase created by the intra user
     *
     * @return string phrase object
     */
    @Override
    public String getPhrase() {
        return this.phrase;
    }

    /**
     * The method <code>setNewProfileImage</code> let the user set a new profile image
     *
     * @param newProfileImage the new profile image to set
     * @throws
     */
    @Override
    public void setNewProfileImage(byte[] newProfileImage) {
        this.image = newProfileImage;
    }

    /**
     * This method let an intra user sign a message with his unique private key
     *
     * @param message the message to sign
     * @return the signature
     * @throws
     */
    @Override
    public String createMessageSignature(String message) {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch (Exception e) {
            // throw new CantSignIntraWalletUserMessageException("Fatal Error Signed message", e, "", "");
        }
        return null;
    }

    /**
     * This method let an intra user sign a message with his unique private key
     *
     * @return the platformComponentType
     * @throws
     */
    @Override
    public PlatformComponentType getPlatformComponentType() {
        return this.platformComponentType;
    }

    /**
     * @return a string representing the public key.
     */
    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    /**
     * @return an element of Actors enum representing the type of the actor identity.
     */
    @Override
    public Actors getActorType() {
        return this.actors;
    }

    /**
     * @return a string with the actor identity alias.
     */
    @Override
    public String getAlias() {
        return this.alias;
    }

    /**
     * @return a byte array with the actor identity profile image.
     */
    @Override
    public byte[] getImage() {
        return this.image;
    }
}
