package com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantCreateNewIdentityAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantListAssetIssuersException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.database.AssetIssuerIdentityDao;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.exceptions.CantInitializeAssetIssuerIdentityDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.exceptions.CantListAssetIssuerIdentitiesException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class IdentityAssetIssuerManagerImpl implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
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
    public IdentityAssetIssuerManagerImpl(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, DeviceUserManager deviceUserManager) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
    }

    private AssetIssuerIdentityDao getAssetIssuerIdentityDao(){
        AssetIssuerIdentityDao assetIssuerIdentityDao = new AssetIssuerIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
        return assetIssuerIdentityDao;
    }

    public void  initializeDatabase() throws CantInitializeAssetIssuerIdentityDatabaseException{
        getAssetIssuerIdentityDao().initializeDatabase();
    }

    public List<IdentityAssetIssuer> getIdentityAssetIssuersFromCurrentDeviceUser() throws CantListAssetIssuersException {

        try {

            List<IdentityAssetIssuer> assetIssuerList = new ArrayList<IdentityAssetIssuer>();


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            assetIssuerList = getAssetIssuerIdentityDao().getAllIntraUserFromCurrentDeviceUser(loggedUser);


            return assetIssuerList;

        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListAssetIssuersException("CAN'T GET ASSET ISSUER IDENTITIES", e, "Error get logged user device", "");
        } catch (CantListAssetIssuerIdentitiesException e) {
            throw new CantListAssetIssuersException("CAN'T GET ASSET ISSUER  IDENTITIES", e, "", "");
        } catch (Exception e) {
            throw new CantListAssetIssuersException("CAN'T GET ASSET ISSUER IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public IdentityAssetIssuer createNewIdentityAssetIssuer(String alias, byte[] profileImage) throws CantCreateNewIdentityAssetIssuerException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getAssetIssuerIdentityDao().createNewUser(alias, publicKey, privateKey, loggedUser, profileImage);

            IdentityAssetIssuerImpl identityAssetIssuer = new IdentityAssetIssuerImpl(alias, publicKey, privateKey, profileImage, pluginFileSystem, pluginId);

            //TODO:Revisar como registrar con el Network Service
            //registerIdentities();

            return identityAssetIssuer;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateNewIdentityAssetIssuerException("CAN'T CREATE NEW ASSET ISSUER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (CantCreateNewDeveloperException e) {
            throw new CantCreateNewIdentityAssetIssuerException("CAN'T CREATE NEW ASSET ISSUER IDENTITY", e, "Error save user on database", "");
        } catch (Exception e) {
            throw new CantCreateNewIdentityAssetIssuerException("CAN'T CREATE NEW ASSET ISSUER IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    public boolean  hasIntraUserIdentity() throws CantListAssetIssuersException{
        try {

            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            if(getAssetIssuerIdentityDao().getAllIntraUserFromCurrentDeviceUser(loggedUser).size() > 0)
                return true;
            else
                return false;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListAssetIssuersException("CAN'T GET IF ASSET ISSUER IDENTITIES  EXISTS", e, "Error get logged user device", "");
        }  catch (CantListAssetIssuerIdentitiesException e) {
            throw new CantListAssetIssuersException("CAN'T GET IF ASSET ISSUER IDENTITIES EXISTS", e, "", "");
        }catch (Exception e) {
            throw new CantListAssetIssuersException("CAN'T GET ASSET ISSUER USER IDENTITY EXISTS", FermatException.wrapException(e), "", "");
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
