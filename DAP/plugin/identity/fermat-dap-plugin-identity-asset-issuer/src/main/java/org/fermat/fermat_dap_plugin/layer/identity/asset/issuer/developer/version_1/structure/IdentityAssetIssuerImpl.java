package org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;

import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.AssetIssuerIdentityPluginRoot;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class IdentityAssetIssuerImpl implements IdentityAssetIssuer, Serializable {

    private String alias;
    private String publicKey;
    private byte[] profileImage;
    private String privateKey;
    private PluginFileSystem pluginFileSystem;
    private UUID pluginId;

    /**
     * DealWithPluginFileSystem Interface implementation.
     */
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

    /**
     * Constructor
     */
    public IdentityAssetIssuerImpl(String alias, String publicKey, String privateKey, byte[] profileImage, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.privateKey = privateKey;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    public IdentityAssetIssuerImpl(String alias, String publicKey, byte[] profileImage) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
    }

    public IdentityAssetIssuerImpl() {

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
        return Actors.DAP_ASSET_ISSUER;
    }

    @Override
    public byte[] getImage() {
        return this.profileImage;
    }

    @Override
    public void setNewProfileImage(byte[] newProfileImage) {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    AssetIssuerIdentityPluginRoot.ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);


            file.persistToMedia();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        } catch (CantCreateFileException e) {
            e.printStackTrace();
        }
        //TODO: Revisar este manejo de excepciones
//        } catch (CantPersistFileException e) {
//            throw new CantSetAssetIssuerIdentityProfileImagenException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
//
//        } catch (CantCreateFileException e) {
//            throw new CantSetAssetIssuerIdentityProfileImagenException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
//        } catch (Exception e) {
//            throw new CantSetAssetIssuerIdentityProfileImagenException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
//        }
        this.profileImage = newProfileImage;
    }

    @Override
    public String createMessageSignature(String message) {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.privateKey);
        } catch (Exception e) {
            //TODO: Revisar este manejo de excepciones
            e.printStackTrace();
            // throw new CantSignIntraWalletUserMessageException("Fatal Error Signed message", e, "", "");
        }
        return null;
    }
}
