package com.bitdubai.fermat_ccp_api.layer.identity.intra_user.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;

import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantSetNewProfileImageException;

import java.io.Serializable;

/**
 * The Class <code>IntraWalletUserIdentity</code>
 * implements the functionality of an IntraWalletUser.<p/>
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

//TODO: León porqué carajo esta clase tiene el plugin filesystem? no tiene sentido que una identidad lo tenga....

public class IntraWalletUserIdentity implements com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity,Serializable {

    private String alias;
    private String phrase;
    private String publicKey;
    private byte[] image;
    private String privateKey;

    private long accuracy;
    private Frequency frequency;
    private Location location;


    /**
     * Constructor
     */

    public IntraWalletUserIdentity(String alias, String phrase,String publicKey, String privateKey, byte[] image,long accuracy,Frequency frequency,Location location) {

        this.alias = alias;
        this.phrase = phrase;
        this.publicKey = publicKey;
        this.image = image;
        this.privateKey = privateKey;

        this.frequency = frequency;
        this.accuracy = accuracy;
        this.location = location;

//        this.pluginFileSystem = pluginFileSystem;
//        this.pluginId = pluginId;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public byte[] getImage() {
        return image;
    }

    @Override
    public long getAccuracy() {
        return accuracy;
    }

    @Override
    public Frequency getFrequency() {
        return frequency;
    }

    @Override
    public String getPhrase(){
        return this.phrase;
    }

    @Override
    public void setNewProfileImage(byte[] newProfileImage) {
        this.image = newProfileImage;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public Actors getActorType() {
        return Actors.INTRA_USER;
    }

    @Override
    public String createMessageSignature(String message){ //throws CantSignIntraWalletUserMessageException {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch (Exception e) {
           // throw new CantSignIntraWalletUserMessageException("Fatal Error Signed message", e, "", "");
        }
        return null;
    }

    /**
     * Set a new profile image for an Intra-User Identity
     * <p/>
     * Overwrite the saved profile image of the user.
     * <p/>
     * The name of the profileImage file is defined like "$publicKey_profileImage"
     *
//     * @param newProfileImage the new profile image to set
     * @throws CantSetNewProfileImageException
     */
//    @Override
//    public void setNewProfileImage(byte[] newProfileImage){// throws CantSetNewProfileImageException {
//        try {
//            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
//                    DeviceDirectory.LOCAL_USERS.getName(),
//                    IntraWalletUserIdentityPluginRoot.INTRA_WALLET_USERS_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
//                    FilePrivacy.PRIVATE,
//                    FileLifeSpan.PERMANENT
//            );
//
//            file.setContent(image);
//
//
//            file.persistToMedia();
//        } catch (CantPersistFileException e) {
//            e.printStackTrace();
//        } catch (CantCreateFileException e) {
//            e.printStackTrace();
//        }
////        } catch (CantPersistFileException e) {
////            throw new CantSetNewProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
////
////        } catch (CantCreateFileException e) {
////            throw new CantSetNewProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
////        } catch (Exception e) {
////            throw new CantSetNewProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
////        }
//        this.image = newProfileImage;
//    }
//
//    /**
//     * DealsWithPluginFileSystem Interface member variables.
//     */
//    private PluginFileSystem pluginFileSystem;
//
//    /**
//     * DealsWithPluginIdentity Interface member variables.
//     */
//    private UUID pluginId;
//
//    /**
//     * DealWithPluginFileSystem Interface implementation.
//     */
//    @Override
//    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
//        this.pluginFileSystem = pluginFileSystem;
//
//    }
//
//    /**
//     * DealsWithPluginIdentity Interface implementation.
//     */
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }

    @Override
    public String toString() {
        return "IntraWalletUserIdentity{" +
                "alias='" + alias + '\'' +
                ", phrase='" + phrase + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", Accuracy='" + accuracy + '\'' +
                ", Frequency='" + frequency.getCode() + '\'' +
                '}';
    }
}
