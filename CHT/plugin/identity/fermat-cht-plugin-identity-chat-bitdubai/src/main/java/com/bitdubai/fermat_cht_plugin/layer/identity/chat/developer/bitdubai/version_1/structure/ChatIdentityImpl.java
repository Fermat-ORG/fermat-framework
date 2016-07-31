package com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;

import java.io.Serializable;

/**
 * Created by franklin on 02/11/15.
 * Edited by Miguel Rincon on 19/04/2016
 */
public class ChatIdentityImpl implements ChatIdentity, Serializable {
    private static final String ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME = "chatIdentityProfileImage";
    private static final String ASSET_ISSUER_PRIVATE_KEYS_FILE_NAME = "chatIdentityPrivateKey";
    private ErrorManager errorManager;
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

//    /**
//     * DealsWithPluginFileSystem Interface member variables.
//     */
//    private PluginFileSystem pluginFileSystem;
//
//    /**
//     * DealsWithPluginIdentity Interface member variables.
//     */
//    private UUID pluginId;

//    /**
//     * DealWithPluginFileSystem Interface implementation.
//     */
//    @Override
//    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
//        this.pluginFileSystem = pluginFileSystem;
//
//    }

//    /**
//     * DealsWithPluginIdentity Interface implementation.
//     */
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }

    /**
     * Constructor
     */
    public ChatIdentityImpl(String alias, String publicKey, String privateKey, byte[] profileImage, String country, String state, String city, String connectionState, long accuracy, GeoFrequency frecuency) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.privateKey = privateKey;
//        this.pluginFileSystem = pluginFileSystem;
//        this.pluginId = pluginId;
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
    public void setNewProfileImage(byte[] newProfileImage) {
//        try {
//            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
//                    DeviceDirectory.LOCAL_USERS.getName(),
//                    ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
//                    FilePrivacy.PRIVATE,
//                    FileLifeSpan.PERMANENT
//            );
//
//            file.setContent(profileImage);
//
//
//            file.persistToMedia();
//        } catch (CantPersistFileException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
//        } catch (CantCreateFileException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
//        }
//        //TODO: Revisar este manejo de excepciones
////        } catch (CantPersistFileException e) {
////            throw new CantSetAssetIssuerIdentityProfileImagenException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
////
////        } catch (CantCreateFileException e) {
////            throw new CantSetAssetIssuerIdentityProfileImagenException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
////        } catch (Exception e) {
////            throw new CantSetAssetIssuerIdentityProfileImagenException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
////        }
//        this.profileImage = newProfileImage;
    }

    @Override
    public String createMessageSignature(String message) {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            //TODO: Revisar este manejo de excepciones
            // throw new CantSignIntraWalletUserMessageException("Fatal Error Signed message", e, "", "");
        }
        return null;
    }

    /**
     * This method return boolean
     *
     * @return the boolena
     */
    @Override
    public boolean getIsPaymetForChat() {
        return false;
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
