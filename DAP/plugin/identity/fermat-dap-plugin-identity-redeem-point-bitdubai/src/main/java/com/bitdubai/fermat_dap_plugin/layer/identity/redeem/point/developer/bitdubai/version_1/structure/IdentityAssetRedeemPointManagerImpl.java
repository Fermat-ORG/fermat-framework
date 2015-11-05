package com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantCreateNewIdentityAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantListAssetUsersException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantCreateNewRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantListAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.database.AssetRedeemPointIdentityDao;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.exceptions.CantListAssetRedeemPointIdentitiesException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class IdentityAssetRedeemPointManagerImpl implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
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

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Constructor
     *
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public IdentityAssetRedeemPointManagerImpl(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, DeviceUserManager deviceUserManager) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
    }

    private AssetRedeemPointIdentityDao getAssetRedeemPointIdentityDao(){
        AssetRedeemPointIdentityDao assetRedeemPointIdentityDao = new AssetRedeemPointIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
        return assetRedeemPointIdentityDao;
    }

    public void  initializeDatabase() throws CantInitializeAssetRedeemPointIdentityDatabaseException {
        getAssetRedeemPointIdentityDao().initializeDatabase();
    }

    public List<RedeemPointIdentity> getIdentityAssetRedeemPointsFromCurrentDeviceUser() throws CantListAssetRedeemPointException {

        try {

            List<RedeemPointIdentity> assetRedeemPointList = new ArrayList<>();


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            assetRedeemPointList = getAssetRedeemPointIdentityDao().getAllIntraUserFromCurrentDeviceUser(loggedUser);


            return assetRedeemPointList;

        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListAssetRedeemPointException("CAN'T GET ASSET ISSUER IDENTITIES", e, "Error get logged user device", "");
        } catch (CantListAssetRedeemPointIdentitiesException e) {
            throw new CantListAssetRedeemPointException("CAN'T GET ASSET ISSUER  IDENTITIES", e, "", "");
        } catch (Exception e) {
            throw new CantListAssetRedeemPointException("CAN'T GET ASSET ISSUER IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public RedeemPointIdentity createNewIdentityAssetRedeemPoint(String alias, byte[] profileImage) throws CantCreateNewRedeemPointException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getAssetRedeemPointIdentityDao().createNewUser(alias, publicKey, privateKey, loggedUser, profileImage);

            IdentityAssetRedeemPointImpl identityAssetRedeemPoint = new IdentityAssetRedeemPointImpl(alias, publicKey, privateKey, profileImage, pluginFileSystem, pluginId);

            //TODO:Revisar como registrar con el Network Service
            //registerIdentities();

            return identityAssetRedeemPoint;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateNewRedeemPointException("CAN'T CREATE NEW ASSET USER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (CantCreateNewDeveloperException e) {
            throw new CantCreateNewRedeemPointException("CAN'T CREATE NEW ASSET USER IDENTITY", e, "Error save user on database", "");
        } catch (Exception e) {
            throw new CantCreateNewRedeemPointException("CAN'T CREATE NEW ASSET USER IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    public boolean  hasIntraUserIdentity() throws CantListAssetRedeemPointException{
        try {

            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            if(getAssetRedeemPointIdentityDao().getAllIntraUserFromCurrentDeviceUser(loggedUser).size() > 0)
                return true;
            else
                return false;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListAssetRedeemPointException("CAN'T GET IF ASSET USER IDENTITIES  EXISTS", e, "Error get logged user device", "");
        }  catch (CantListAssetRedeemPointIdentitiesException e) {
            throw new CantListAssetRedeemPointException("CAN'T GET IF ASSET USER IDENTITIES EXISTS", e, "", "");
        }catch (Exception e) {
            throw new CantListAssetRedeemPointException("CAN'T GET ASSET USER USER IDENTITY EXISTS", FermatException.wrapException(e), "", "");
        }
    }

    public void registerIdentities(){
//        try {
//            List<IntraWalletUser> lstIntraWalletUSer = intraWalletUserIdentityDao.getAllIntraUserFromCurrentDeviceUser(deviceUserManager.getLoggedInDeviceUser());
//            List<Actor> lstActors = new ArrayList<Actor>();
//            for(IntraWalletUser user : lstIntraWalletUSer){
//                lstActors.add(intraActorManager.contructIdentity(user.getPublicKey(), user.getAlias(), Actors.INTRA_USER,user.getProfileImage()));
//            }
//            intraActorManager.registrateActors(lstActors);
//        } catch (CantListIntraWalletUserIdentitiesException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//        } catch (CantGetLoggedInDeviceUserException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//        }
    }
}
