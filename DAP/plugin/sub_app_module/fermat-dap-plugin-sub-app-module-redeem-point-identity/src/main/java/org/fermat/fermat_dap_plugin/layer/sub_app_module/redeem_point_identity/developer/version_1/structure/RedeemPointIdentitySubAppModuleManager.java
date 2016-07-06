package org.fermat.fermat_dap_plugin.layer.sub_app_module.redeem_point_identity.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantCreateNewRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantGetRedeemPointIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantListAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantUpdateIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_identity.RedeemPointIdentitySettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_identity.interfaces.RedeemPointIdentityModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;

import java.util.List;
import java.util.UUID;

/**
 * Created by nerio on 14/5/2016.
 */
public class RedeemPointIdentitySubAppModuleManager extends ModuleManagerImpl<RedeemPointIdentitySettings> implements RedeemPointIdentityModuleManager {

    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;
    private final RedeemPointIdentityManager redeemPointIdentityManager;

    public RedeemPointIdentitySubAppModuleManager(RedeemPointIdentityManager redeemPointIdentityManager, PluginFileSystem pluginFileSystem, UUID pluginId) {
        super(pluginFileSystem, pluginId);

        this.redeemPointIdentityManager = redeemPointIdentityManager;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    @Override
    public List<RedeemPointIdentity> getRedeemPointsFromCurrentDeviceUser() throws CantListAssetRedeemPointException {
        return redeemPointIdentityManager.getRedeemPointsFromCurrentDeviceUser();
    }

    @Override
    public RedeemPointIdentity getIdentityAssetRedeemPoint() throws CantGetRedeemPointIdentitiesException {
        return redeemPointIdentityManager.getIdentityAssetRedeemPoint();
    }

    @Override
    public RedeemPointIdentity createNewRedeemPoint(String alias, byte[] profileImage) throws CantCreateNewRedeemPointException {
        return redeemPointIdentityManager.createNewRedeemPoint(alias, profileImage,
                redeemPointIdentityManager.getAccuracyDataDefault(),
                redeemPointIdentityManager.getFrequencyDataDefault());
    }

    @Override
    public RedeemPointIdentity createNewRedeemPoint(String alias,
                                                    byte[] profileImage,
                                                    String contactInformation,
                                                    String countryName,
                                                    String provinceName,
                                                    String cityName,
                                                    String postalCode,
                                                    String streetName,
                                                    String houseNumber, int accuracy, GeoFrequency frequency) throws CantCreateNewRedeemPointException {

        return redeemPointIdentityManager.createNewRedeemPoint(alias, profileImage, contactInformation, countryName, provinceName, cityName, postalCode, streetName, houseNumber,
                                accuracy,frequency);
    }

    @Override
    public void updateIdentityRedeemPoint(String identityPublicKey,
                                          String identityAlias,
                                          byte[] profileImage,
                                          String contactInformation,
                                          String countryName,
                                          String provinceName,
                                          String cityName,
                                          String postalCode,
                                          String streetName,
                                          String houseNumber, int accuracy, GeoFrequency frequency) throws CantUpdateIdentityRedeemPointException {

        redeemPointIdentityManager.updateIdentityRedeemPoint(identityPublicKey, identityAlias, profileImage, contactInformation, countryName, provinceName, cityName, postalCode, streetName, houseNumber,
                accuracy, frequency);
    }

    @Override
    public boolean hasRedeemPointIdentity() throws CantListAssetRedeemPointException {
        return redeemPointIdentityManager.hasRedeemPointIdentity();
    }

    @Override
    public void createIdentity(String name,
                               byte[] profile_img,
                               String contactInformation,
                               String countryName,
                               String provinceName,
                               String cityName,
                               String postalCode,
                               String streetName,
                               String houseNumber) throws Exception {

        redeemPointIdentityManager.createNewRedeemPoint(name, profile_img, contactInformation, countryName, provinceName, cityName, postalCode, streetName, houseNumber,
                redeemPointIdentityManager.getAccuracyDataDefault(),
                redeemPointIdentityManager.getFrequencyDataDefault());

    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            List<RedeemPointIdentity> identities = redeemPointIdentityManager.getRedeemPointsFromCurrentDeviceUser();
            return (identities == null || identities.isEmpty()) ? null : identities.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        redeemPointIdentityManager.createNewRedeemPoint(name, profile_img,
                redeemPointIdentityManager.getAccuracyDataDefault(),
                redeemPointIdentityManager.getFrequencyDataDefault());
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    @Override
    public int getAccuracyDataDefault() {
    return redeemPointIdentityManager.getAccuracyDataDefault();
    }

    @Override
     public GeoFrequency getFrequencyDataDefault() {
        return redeemPointIdentityManager.getFrequencyDataDefault();
        }
}
