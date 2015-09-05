package com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantSetNewProfileImageException;

import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantSignIntraUserMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentity;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraUserIdentityPluginRoot;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity</code>
 * implements the functionality of an IntraUserIdentity.<p/>
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserIdentityIdentity implements DealsWithPluginFileSystem, DealsWithPluginIdentity, IntraUserIdentity {

    private String alias;
    private String publicKey;
    private byte[] profileImage;
    private String privateKey;

    /**
     * Constructor
     */
    public IntraUserIdentityIdentity(String alias, String publicKey, String privateKey, byte[] profileImage, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.privateKey = privateKey;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
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
    public byte[] getProfileImage() {
        return this.profileImage;
    }

    @Override
    public String createMessageSignature(String message) throws CantSignIntraUserMessageException {
        try {
            return AsymmectricCryptography.createMessageSignature(message, this.privateKey);
        } catch (Exception e) {
            throw new CantSignIntraUserMessageException("Fatal Error Signed message", e, "", "");
        }

    }

    /**
     * Set a new profile image for an Intra-User Identity
     * <p/>
     * Overwrite the saved profile image of the user.
     * <p/>
     * The name of the profileImage file is defined like "$publicKey_profileImage"
     *
     * @param newProfileImage the new profile image to set
     * @throws CantSetNewProfileImageException
     */
    @Override
    public void setNewProfileImage(byte[] newProfileImage) throws CantSetNewProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    IntraUserIdentityPluginRoot.INTRA_USERS_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantSetNewProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);

        } catch (CantCreateFileException e) {
            throw new CantSetNewProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw new CantSetNewProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
        }
        this.profileImage = newProfileImage;
    }

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     * DealWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;

    }

    /**
     * DealsWithPluginIdentity Interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}