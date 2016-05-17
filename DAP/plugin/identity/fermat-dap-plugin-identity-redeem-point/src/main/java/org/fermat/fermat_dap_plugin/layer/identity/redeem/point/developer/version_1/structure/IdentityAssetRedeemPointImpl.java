package org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.structure;

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

import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class IdentityAssetRedeemPointImpl implements RedeemPointIdentity, Serializable {

    private String alias;
    private String publicKey;
    private byte[] profileImage;
    private String privateKey;
    private String contactInformation;
    private String countryName;
    private String provinceName;
    private String cityName;
    private String postalCode;
    private String streetName;
    private String houseNumber;


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
//    @Override
//    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
//        this.pluginFileSystem = pluginFileSystem;
//    }

    /**
     * DealsWithPluginIdentity Interface implementation.
     */
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }

    @Override
    public String getContactInformation() {
        return contactInformation;
    }

    @Override
    public String getCountryName() {
        return countryName;
    }

    @Override
    public String getProvinceName() {
        return provinceName;
    }

    @Override
    public String getCityName() {
        return cityName;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String getStreetName() {
        return streetName;
    }

    @Override
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Constructor
     */
    public IdentityAssetRedeemPointImpl(String alias, String publicKey, String privateKey, byte[] profileImage,
                                        PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.privateKey = privateKey;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

//    public IdentityAssetRedeemPointImpl(String alias, String publicKey, byte[] profileImage) {
//        this.alias = alias;
//        this.publicKey = publicKey;
//        this.profileImage = profileImage;
//    }

    public IdentityAssetRedeemPointImpl(String alias, String publicKey, String privateKey, byte[] profileImage,
                                        String contactInformation,
                                        String countryName, String provinceName, String cityName,
                                        String postalCode, String streetName, String houseNumber) {

        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.privateKey = privateKey;
        this.contactInformation = contactInformation;
        this.countryName = countryName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    public IdentityAssetRedeemPointImpl(String alias, String publicKey, byte[] profileImage, String contactInformation,
                                        String countryName, String provinceName, String cityName,
                                        String postalCode, String streetName, String houseNumber) {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.contactInformation = contactInformation;
        this.countryName = countryName;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;

    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    /**
     * @return an element of Actors enum representing the type of the actor identity.
     */
    @Override
    public Actors getActorType() {
        return Actors.DAP_ASSET_REDEEM_POINT;
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
                    org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.ReedemPointIdentityPluginRoot.ASSET_REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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
