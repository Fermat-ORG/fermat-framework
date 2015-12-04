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
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantCreateActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantCreateNewIdentityAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantListAssetIssuersException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.database.AssetIssuerIdentityDao;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.exceptions.CantInitializeAssetIssuerIdentityDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.exceptions.CantListAssetIssuerIdentitiesException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class IdentityAssetIssuerManagerImpl implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
    //TODO: Documentar
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

    /**
     * DealsWithActorAssetIssuer Interface member variables.
     */
    private ActorAssetIssuerManager actorAssetIssuerManager;

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
    public IdentityAssetIssuerManagerImpl(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, DeviceUserManager deviceUserManager, ActorAssetIssuerManager actorAssetIssuerManager) {
        this.errorManager            = errorManager;
        this.logManager              = logManager;
        this.pluginDatabaseSystem    = pluginDatabaseSystem;
        this.pluginFileSystem        = pluginFileSystem;
        this.pluginId                = pluginId;
        this.deviceUserManager       = deviceUserManager;
        this.actorAssetIssuerManager = actorAssetIssuerManager;
    }

    private AssetIssuerIdentityDao getAssetIssuerIdentityDao() throws CantInitializeAssetIssuerIdentityDatabaseException {
        AssetIssuerIdentityDao assetIssuerIdentityDao = new AssetIssuerIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
        return assetIssuerIdentityDao;
    }

    public List<IdentityAssetIssuer> getIdentityAssetIssuersFromCurrentDeviceUser() throws CantListAssetIssuersException {

        try {

            List<IdentityAssetIssuer> assetIssuerList = new ArrayList<IdentityAssetIssuer>();


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            assetIssuerList = getAssetIssuerIdentityDao().getIdentityAssetIssuersFromCurrentDeviceUser(loggedUser);

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

            registerIdentities();

            return identityAssetIssuer;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateNewIdentityAssetIssuerException("CAN'T CREATE NEW ASSET ISSUER IDENTITY", e, "Error getting current logged in device user", "");
        } catch (CantCreateNewDeveloperException e) {
            throw new CantCreateNewIdentityAssetIssuerException("CAN'T CREATE NEW ASSET ISSUER IDENTITY", e, "Error save user on database", "");
        } catch (Exception e) {
            throw new CantCreateNewIdentityAssetIssuerException("CAN'T CREATE NEW ASSET ISSUER IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    public boolean  hasIntraIssuerIdentity() throws CantListAssetIssuersException{
        try {

            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            if(getAssetIssuerIdentityDao().getIdentityAssetIssuersFromCurrentDeviceUser(loggedUser).size() > 0)
                return true;
            else
                return false;
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListAssetIssuersException("CAN'T GET IF ASSET ISSUER IDENTITIES  EXISTS", e, "Error get logged user device", "");
        }  catch (CantListAssetIssuerIdentitiesException e) {
            throw new CantListAssetIssuersException("CAN'T GET IF ASSET ISSUER IDENTITIES EXISTS", e, "", "");
        }catch (Exception e) {
            throw new CantListAssetIssuersException("CAN'T GET ASSET ISSUER ISSUER IDENTITY EXISTS", FermatException.wrapException(e), "", "");
        }
    }

    public void registerIdentities() throws CantListAssetIssuerIdentitiesException {
        try {
            List<IdentityAssetIssuer> identityAssetIssuers = getAssetIssuerIdentityDao().getIdentityAssetIssuersFromCurrentDeviceUser(deviceUserManager.getLoggedInDeviceUser());
            if (identityAssetIssuers.size() > 0) {
                for (IdentityAssetIssuer identityAssetIssuer : identityAssetIssuers) {
                    actorAssetIssuerManager.createActorAssetIssuerFactory(identityAssetIssuer.getPublicKey(), identityAssetIssuer.getAlias(), identityAssetIssuer.getProfileImage());
                }
            }
        }
        catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListAssetIssuerIdentitiesException("CAN'T GET IF ASSET ISSUER IDENTITIES  EXISTS", e, "Cant Get Logged InDevice User", "");
        } catch (CantListAssetIssuerIdentitiesException e) {
            throw new CantListAssetIssuerIdentitiesException("CAN'T GET IF ASSET ISSUER IDENTITIES  EXISTS", e, "Cant List Asset Issuer Identities", "");
        } catch (CantCreateActorAssetIssuerException e) {
            throw new CantListAssetIssuerIdentitiesException("CAN'T GET IF ASSET ISSUER IDENTITIES  EXISTS", e, "Cant Create ActorAsset Issuer", "");
        } catch (CantInitializeAssetIssuerIdentityDatabaseException e) {
            throw new CantListAssetIssuerIdentitiesException("CAN'T GET IF ASSET ISSUER IDENTITIES  EXISTS", e, "Cant Initialize Asset Issuer Identity Database", "");
        }
    }
}
