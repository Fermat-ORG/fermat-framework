package org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRegisterActorAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantCreateNewRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantGetRedeemPointIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantListAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantUpdateIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_identity.RedeemPointIdentitySettings;
import org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.database.AssetRedeemPointIdentityDao;
import org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException;
import org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions.CantListAssetRedeemPointIdentitiesException;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class IdentityAssetRedeemPointManagerImpl extends ModuleManagerImpl<RedeemPointIdentitySettings> implements RedeemPointIdentityManager {
    /**
     * IdentityAssetIssuerManagerImpl member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface mmeber variables
     */
    LogManager logManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithDeviceUsers Interface member variables.
     */
    private DeviceUserManager deviceUserManager;

    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;

//    @Override
//    public void setErrorManager(ErrorManager errorManager) {
//        this.errorManager = errorManager;
//    }
//
//    @Override
//    public void setLogManager(LogManager logManager) {
//        this.logManager = logManager;
//    }
//
//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//    }
//
//    @Override
//    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
//        this.pluginFileSystem = pluginFileSystem;
//    }

    /**
     * Constructor
     *
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public IdentityAssetRedeemPointManagerImpl(ErrorManager errorManager,
                                               LogManager logManager,
                                               PluginDatabaseSystem pluginDatabaseSystem,
                                               PluginFileSystem pluginFileSystem,
                                               UUID pluginId,
                                               DeviceUserManager deviceUserManager,
                                               ActorAssetRedeemPointManager actorAssetRedeemPointManager) {

        super(pluginFileSystem, pluginId);

        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
        this.actorAssetRedeemPointManager = actorAssetRedeemPointManager;
    }

    private AssetRedeemPointIdentityDao getAssetRedeemPointIdentityDao() throws CantInitializeAssetRedeemPointIdentityDatabaseException {
        AssetRedeemPointIdentityDao assetRedeemPointIdentityDao = new AssetRedeemPointIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
        return assetRedeemPointIdentityDao;
    }

    public List<RedeemPointIdentity> getIdentityAssetRedeemPointsFromCurrentDeviceUser() throws CantListAssetRedeemPointException {

        try {

            List<RedeemPointIdentity> assetRedeemPointList = new ArrayList<>();


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            assetRedeemPointList = getAssetRedeemPointIdentityDao().getIdentityAssetRedeemPointsFromCurrentDeviceUser(loggedUser);


            return assetRedeemPointList;

        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListAssetRedeemPointException("CAN'T GET ASSET NEW REDEEM POINT IDENTITIES", e, "Error get logged user device", "");
        } catch (CantListAssetRedeemPointIdentitiesException e) {
            throw new CantListAssetRedeemPointException("CAN'T GET ASSET NEW REDEEM POINT  IDENTITIES", e, "", "");
        } catch (Exception e) {
            throw new CantListAssetRedeemPointException("CAN'T GET ASSET NEW REDEEM POINT IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public RedeemPointIdentity getIdentityRedeemPoint() throws CantGetRedeemPointIdentitiesException {
        RedeemPointIdentity redeemPointIdentity = null;
        try {
            redeemPointIdentity = getAssetRedeemPointIdentityDao().getIdentityRedeemPoint();
        } catch (CantInitializeAssetRedeemPointIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return redeemPointIdentity;
    }

    public RedeemPointIdentity createNewIdentityAssetRedeemPoint(String alias, byte[] profileImage) throws CantCreateNewRedeemPointException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getAssetRedeemPointIdentityDao().createNewUser(alias, publicKey, privateKey, loggedUser, profileImage);

            IdentityAssetRedeemPointImpl identityAssetRedeemPoint = new IdentityAssetRedeemPointImpl(alias, publicKey, privateKey, profileImage, pluginFileSystem, pluginId);

            registerIdentities();

            return identityAssetRedeemPoint;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateNewRedeemPointException("CAN'T CREATE NEW REDEEM POINT IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateNewRedeemPointException("CAN'T CREATE NEW REDEEM POINT IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    public RedeemPointIdentity createNewIdentityAssetRedeemPoint(String alias, byte[] profileImage,
                                                                 String contactInformation, String countryName, String provinceName, String cityName,
                                                                 String postalCode, String streetName, String houseNumber) throws CantCreateNewRedeemPointException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getAssetRedeemPointIdentityDao().createNewUser(alias, publicKey, privateKey, loggedUser, profileImage, contactInformation,
                    countryName, provinceName, cityName, postalCode, streetName, houseNumber);

            IdentityAssetRedeemPointImpl identityAssetRedeemPoint = new IdentityAssetRedeemPointImpl(alias, publicKey, privateKey, profileImage, pluginFileSystem, pluginId, contactInformation,
                    countryName, provinceName, cityName, postalCode, streetName, houseNumber);

            registerIdentities();

            return identityAssetRedeemPoint;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateNewRedeemPointException("CAN'T CREATE NEW REDEEM POINT IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateNewRedeemPointException("CAN'T CREATE NEW REDEEM POINT IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public List<RedeemPointIdentity> getRedeemPointsFromCurrentDeviceUser() throws CantListAssetRedeemPointException {
        return null;
    }

    @Override
    public RedeemPointIdentity getIdentityAssetRedeemPoint() throws CantGetRedeemPointIdentitiesException {
        return this.getIdentityRedeemPoint();
    }

    @Override
    public RedeemPointIdentity createNewRedeemPoint(String alias, byte[] profileImage) throws CantCreateNewRedeemPointException {
        return this.createNewIdentityAssetRedeemPoint(alias, profileImage);
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
                                                    String houseNumber) throws CantCreateNewRedeemPointException {

        return this.createNewIdentityAssetRedeemPoint(alias, profileImage, contactInformation,
                countryName, provinceName, cityName, postalCode, streetName, houseNumber);
    }

    @Override
    public void updateIdentityRedeemPoint(String identityPublicKey, String identityAlias, byte[] profileImage,
                                          String contactInformation, String countryName, String provinceName, String cityName,
                                          String postalCode, String streetName, String houseNumber) throws CantUpdateIdentityRedeemPointException {
        try {
            getAssetRedeemPointIdentityDao().updateIdentityAssetUser(identityPublicKey, identityAlias, profileImage, contactInformation,
                    countryName, provinceName, cityName, postalCode, streetName, houseNumber);

            registerIdentities();
        } catch (CantInitializeAssetRedeemPointIdentityDatabaseException e) {
            e.printStackTrace();
        } catch (CantListAssetRedeemPointIdentitiesException e) {
            e.printStackTrace();
        }
    }

    public boolean hasRedeemPointIdentity() throws CantListAssetRedeemPointException {
        try {

            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            if (getAssetRedeemPointIdentityDao().getIdentityAssetRedeemPointsFromCurrentDeviceUser(loggedUser).size() > 0)
                return true;
            else
                return false;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListAssetRedeemPointException("CAN'T GET IF NEW REDEEM POINT IDENTITIES  EXISTS", e, "Error get logged user device", "");
        } catch (CantListAssetRedeemPointIdentitiesException e) {
            throw new CantListAssetRedeemPointException("CAN'T GET IF NEW REDEEM POINT IDENTITIES EXISTS", e, "", "");
        } catch (Exception e) {
            throw new CantListAssetRedeemPointException("CAN'T GET ASSET NEW REDEEM POINT IDENTITY EXISTS", FermatException.wrapException(e), "", "");
        }
    }

    public void registerIdentities() throws CantListAssetRedeemPointIdentitiesException {
        try {
            List<RedeemPointIdentity> redeemPointIdentities = getAssetRedeemPointIdentityDao().getIdentityAssetRedeemPointsFromCurrentDeviceUser(deviceUserManager.getLoggedInDeviceUser());
            if (redeemPointIdentities.size() > 0) {
                for (RedeemPointIdentity identityAssetRedeemPoint : redeemPointIdentities) {
                    actorAssetRedeemPointManager.createActorAssetRedeemPointFactory(identityAssetRedeemPoint.getPublicKey(),
                            identityAssetRedeemPoint.getAlias(),
                            identityAssetRedeemPoint.getImage(),
                            identityAssetRedeemPoint.getContactInformation(),
                            identityAssetRedeemPoint.getCountryName(),
                            identityAssetRedeemPoint.getCityName());
                }
            }
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListAssetRedeemPointIdentitiesException("CAN'T GET IF ASSET REDEEM POINT IDENTITIES  EXISTS", e, "Cant Get Logged InDevice User", "");
        } catch (CantListAssetRedeemPointIdentitiesException e) {
            throw new CantListAssetRedeemPointIdentitiesException("CAN'T GET IF ASSET REDEEM POINT IDENTITIES  EXISTS", e, "Cant List Asset Redeem Point Identities", "");
        } catch (CantCreateActorRedeemPointException e) {
            throw new CantListAssetRedeemPointIdentitiesException("CAN'T GET IF ASSET REDEEM POINT IDENTITIES  EXISTS", e, "Cant Create Actor Redeem Point User", "");
        } catch (CantInitializeAssetRedeemPointIdentityDatabaseException e) {
            throw new CantListAssetRedeemPointIdentitiesException("CAN'T GET IF ASSET REDEEM POINT IDENTITIES  EXISTS", e, "Cant Initialize Asset Redeem Point Identity", "");
        }
    }

    public void registerIdentitiesANS() throws CantRegisterActorAssetRedeemPointException {
        try {
            actorAssetRedeemPointManager.registerActorInActorNetworkService();
        } catch (CantRegisterActorAssetRedeemPointException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        try {
            List<RedeemPointIdentity> identities = this.getIdentityAssetRedeemPointsFromCurrentDeviceUser();
            return (identities == null || identities.isEmpty()) ? null : this.getIdentityAssetRedeemPointsFromCurrentDeviceUser().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

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

        this.createNewIdentityAssetRedeemPoint(name, profile_img, contactInformation,
                countryName, provinceName, cityName, postalCode, streetName, houseNumber);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
