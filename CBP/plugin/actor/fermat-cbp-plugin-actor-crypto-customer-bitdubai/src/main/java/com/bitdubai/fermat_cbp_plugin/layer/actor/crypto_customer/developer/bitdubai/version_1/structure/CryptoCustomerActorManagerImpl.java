package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActorManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantRegisterCryptoCustomerActorException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 07.01.16.
 */
public class CryptoCustomerActorManagerImpl implements CryptoCustomerActorManager {

    private ErrorManager                            errorManager;

    private LogManager                              logManager;

    private PluginFileSystem                        pluginFileSystem;

    private UUID                                    pluginId;
    
    private final CryptoCustomerActorDatabaseDao    cryptoCustomerActorDatabaseDao;
    
    public CryptoCustomerActorManagerImpl(
            ErrorManager                    errorManager,
            LogManager                      logManager,
            PluginFileSystem                pluginFileSystem,
            UUID                            pluginId,
            CryptoCustomerActorDatabaseDao  cryptoCustomerActorDatabaseDao                        
    ){
        this.errorManager                   = errorManager;
        this.logManager                     = logManager;
        this.pluginFileSystem               = pluginFileSystem;
        this.pluginId                       = pluginId;
        this.cryptoCustomerActorDatabaseDao = cryptoCustomerActorDatabaseDao;
        
    }

    @Override
    public CryptoCustomerActor createNewCryptoCustomerActor(String identityPublicKey, String actorName, byte[] actorPhoto) throws CantCreateCryptoCustomerActorException {

        logManager.log(CryptoCustomerActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Creating Crypto Customer...", null, null);

        ECCKeyPair keyPair = new ECCKeyPair();
        String actorPublicKey = keyPair.getPublicKey();
        String actorPrivateKey = keyPair.getPrivateKey();
        CryptoCustomerActor actor = null;

        try {

            persistPrivateKey(actorPrivateKey, actorPublicKey);

            if(actorPhoto!=null) persistNewCryptoCustomerActorProfileImage(actorPublicKey, actorPhoto);

            actor = cryptoCustomerActorDatabaseDao.createRegisterCryptoCustomerActor(identityPublicKey, actorPublicKey, actorPrivateKey, actorName, ConnectionState.CONNECTED);

        } catch (CantRegisterCryptoCustomerActorException e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "");
        }
        logManager.log(CryptoCustomerActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Crypto Customer Created Successfully", null, null);

        return actor;
    }

    @Override
    public CryptoCustomerActor getCryptoCustomerActor(String actorPublicKey) throws CantGetCryptoCustomerActorException {
        logManager.log(CryptoCustomerActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Trying to get an specific crypto customer...", null, null);

        CryptoCustomerActor actor = null;

        try {

            actor = this.cryptoCustomerActorDatabaseDao.getRegisterCryptoCustomerActor(actorPublicKey);
            if(actor == null)
                return actor;

        } catch (CantRegisterCryptoCustomerActorException e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_NEW, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET CRYPTO CUSTOMER ACTOR", "");
        }

        return actor;
    }

    /*PRIVATE METHOD*/
    private void persistPrivateKey(String privateKey, String publicKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + CryptoCustomerActorPluginRoot.ACTOR_CRYPTO_CUSTOMER_PRIVATE_KEYS_DIRECTORY_NAME,
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(privateKey);
            file.persistToMedia();
        } catch (CantPersistFileException | CantCreateFileException e) {
            throw new CantPersistPrivateKeyException(CantPersistPrivateKeyException.DEFAULT_MESSAGE, e, "Error creating or persisting file.", null);
        } catch (Exception e) {
            throw new CantPersistPrivateKeyException(CantPersistPrivateKeyException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    private void  persistNewCryptoCustomerActorProfileImage(String publicKey,byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CryptoCustomerActorPluginRoot.ACTOR_CRYPTO_CUSTOMER_PROFILE_IMAGE_DIRECTORY_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(profileImage);
            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw  new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ",FermatException.wrapException(e),"", "");
        }
    }
    /*END PRIVATE METHOD*/
}
