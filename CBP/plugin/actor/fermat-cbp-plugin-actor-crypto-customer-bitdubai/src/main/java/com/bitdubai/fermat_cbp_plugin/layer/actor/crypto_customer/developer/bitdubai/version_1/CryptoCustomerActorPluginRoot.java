package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActorManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantRegisterCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorRecordImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

/**
 * TODO THIS PLUG-IN DOES........
 *
 * Created by jorge on 30-10-2015.
 * Updated by lnacosta (laion.cj91@gmail.com) on 18/11/2015.
 * Updated by Yordin Alayn (y.alayn@gmail.com) on 21.11.2015.
 */
public class CryptoCustomerActorPluginRoot extends AbstractPlugin implements CryptoCustomerActorManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.CRYPTO_BROKER_PURCHASE)
    private CustomerBrokerPurchaseNegotiationManager purchaseNegotiationManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    private CryptoCustomerActorDatabaseDao databaseDao;

    public static final String ACTOR_CRYPTO_CUSTOMER_PRIVATE_KEYS_DIRECTORY_NAME = "actorCryptoCustomerPrivateKeys";

    public CryptoCustomerActorPluginRoot(){
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {

        try {

            databaseDao = new CryptoCustomerActorDatabaseDao(pluginDatabaseSystem, pluginId);
            databaseDao.initialize();

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (final CantInitializeCryptoCustomerActorDatabaseException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "pluginDatabaseSystem=" + pluginDatabaseSystem + "pluginId=" + pluginId, "Cannot initialize Crypto Customer Actor Database.");
        }
    }

    @Override
    public CryptoCustomerActor createNewCryptoCustomerActor(String actorLoggedInPublicKey, String actorName, byte[] actorPhoto) throws CantCreateCryptoCustomerActorException {

        // TODO PLEASE CHECK THE OTHER ACTORS, THINK THIS IS WRONG. LET'S THINK TOGETHER.
        // TODO MAKE USE OF THE ERROR MANAGER.
        ECCKeyPair keyPair = new ECCKeyPair();
        String actorPublicKey = keyPair.getPublicKey();
        String actorPrivateKey = keyPair.getPrivateKey();
        try {
            persistPrivateKey(actorPrivateKey, actorPublicKey);
            //TODO verificar parametro
            return databaseDao.createRegisterCryptoCustomerActor(actorLoggedInPublicKey, actorPublicKey, actorName, actorPhoto, ConnectionState.CONNECTED);
        } catch (CantRegisterCryptoCustomerActorException e){
            throw new CantCreateCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "");
        } catch (CantPersistPrivateKeyException e){
            throw new CantCreateCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantCreateCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "");
        }
        //TODO retorna instancia actor
//        return null;
    }

    @Override
    public CryptoCustomerActor getCryptoCustomer(String actorLoggedInPublicKey, String actorPublicKey) throws CantGetCryptoCustomerActorException {

        // TODO PLEASE CHECK THE OTHER ACTORS, THINK THIS IS WRONG. LET'S THINK TOGETHER.
        // TODO MAKE USE OF THE ERROR MANAGER.
        try {
            CryptoCustomerActor actor = this.databaseDao.getRegisterCryptoCustomerActor(actorLoggedInPublicKey, actorPublicKey);
            if(actor == null)
                throw new CantGetCryptoCustomerActorException("", null, ".","Intra User not found");
            return new CryptoCustomerActorRecordImpl(actorPublicKey, "",CryptoCustomerActor., actor.getActorPhoto());
        } catch (CantRegisterCryptoCustomerActorException e){
            throw new CantGetCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            throw new CantGetCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET CRYPTO CUSTOMER ACTOR", "");
        }
        return null;
    }

    private void persistPrivateKey(String privateKey, String publicKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + ACTOR_CRYPTO_CUSTOMER_PRIVATE_KEYS_DIRECTORY_NAME,
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
/*
    @Override
    public Actor createActor(String intraUserLoggedInPublicKey, String actorName, byte[] photo) throws CantCreateIntraUserException{

        ECCKeyPair keyPair = new ECCKeyPair();
        String publicKey = keyPair.getPublicKey();
        String privateKey = keyPair.getPrivateKey();

        try {

            persistPrivateKey(privateKey, publicKey);

            intraWalletUserActorDao.createActorIntraWalletUser(intraUserLoggedInPublicKey, actorName, publicKey, photo, ConnectionState.CONNECTED);
        }
        catch (CantCreateIntraWalletUserException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateIntraUserException(CantCreateIntraUserException.DEFAULT_MESSAGE, e, "Cannot create .", null);

        }
        catch (CantPersistPrivateKeyException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateIntraUserException(CantCreateIntraUserException.DEFAULT_MESSAGE, e, "Cannot persist private key file.", null);
        }
        catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_INTRA_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateIntraUserException(CantCreateIntraUserException.DEFAULT_MESSAGE, FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }

        return new IntraUserActorRecord(publicKey, privateKey, actorName);

    }



    @Override
    public Actor getActorByPublicKey(String intraUserLoggedInPublicKey, String actorPublicKey) throws CantGetIntraUserException, IntraUserNotFoundException{

        try
        {
            // String privateKey = getPrivateKey(actorPublicKey);

            Actor actor = this.intraWalletUserActorDao.getIntraUserActorByPublicKey(intraUserLoggedInPublicKey,actorPublicKey);

            //not found actor
            if(actor == null)

            return new IntraUserActorRecord(actorPublicKey, "",actor.getName(), actor.getPhoto());
        }
        catch(CantGetIntraWalletUserActorException e)
        {
            throw new CantGetIntraUserException("CAN'T GET INTRA USER ACTOR", FermatException.wrapException(e), "", "unknown error");
        }
        catch(Exception e)
        {
            throw new CantGetIntraUserException("CAN'T GET INTRA USER ACTOR", e, "", "");
        }

    }*/
}
