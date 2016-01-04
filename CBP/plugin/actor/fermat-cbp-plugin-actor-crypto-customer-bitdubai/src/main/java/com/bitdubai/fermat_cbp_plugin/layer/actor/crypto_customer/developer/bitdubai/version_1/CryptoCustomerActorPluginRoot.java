package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActorManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantRegisterCryptoCustomerActorException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The Class <code>CryptoCustomerActorPluginRoot</code>
 * Implements the CryptoCustomerActorManager interface with all his methods.
 * <p/>
 * In this plug-in manages a registry of known Crypto Customer.
 *
 * Created by jorge on 30-10-2015.
 * Updated by lnacosta (laion.cj91@gmail.com) on 18/11/2015.
 * Updated by Yordin Alayn (y.alayn@gmail.com) on 21.11.2015.
 */
public class CryptoCustomerActorPluginRoot extends AbstractPlugin implements
        CryptoCustomerActorManager,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    private CryptoCustomerActorDatabaseDao databaseDao;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public static final String ACTOR_CRYPTO_CUSTOMER_PROFILE_IMAGE_DIRECTORY_NAME = "actorCryptoCustomerProfileImage";
    public static final String ACTOR_CRYPTO_CUSTOMER_PRIVATE_KEYS_DIRECTORY_NAME = "actorCryptoCustomerPrivateKeys";

    public CryptoCustomerActorPluginRoot(){ super(new PluginVersionReference(new Version())); }

    /*IMPLEMENTATION service*/
    @Override
    public void start() throws CantStartPluginException {
        try {
            databaseDao = new CryptoCustomerActorDatabaseDao(pluginDatabaseSystem, pluginFileSystem, pluginId);
            databaseDao.initialize();
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (final CantInitializeCryptoCustomerActorDatabaseException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "pluginDatabaseSystem=" + pluginDatabaseSystem + "pluginId=" + pluginId, "Cannot initialize Crypto Customer Actor Database.");
        }
    }
    /*END IMPLEMENTATION service*/

    /*IMPLEMENTATION CryptoCustomerActorManager*/
    @Override
    public CryptoCustomerActor createNewCryptoCustomerActor(String actorLoggedInPublicKey, String actorName, byte[] actorPhoto) throws CantCreateCryptoCustomerActorException {

        logManager.log(CryptoCustomerActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Creating Crypto Customer...", null, null);

        ECCKeyPair keyPair = new ECCKeyPair();
        String actorPublicKey = keyPair.getPublicKey();
        String actorPrivateKey = keyPair.getPrivateKey();
        CryptoCustomerActor actor = null;

        try {

            persistPrivateKey(actorPrivateKey, actorPublicKey);

            if(actorPhoto!=null) persistNewCryptoCustomerActorProfileImage(actorPublicKey, actorPhoto);

            actor = databaseDao.createRegisterCryptoCustomerActor(actorLoggedInPublicKey, actorPublicKey, actorPrivateKey, actorName, actorPhoto, ConnectionState.CONNECTED);

        } catch (CantRegisterCryptoCustomerActorException e){
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "");
        }
        logManager.log(CryptoCustomerActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Crypto Customer Created Successfully", null, null);

        return actor;
    }

    @Override
    public CryptoCustomerActor getCryptoCustomerActor(String actorLoggedInPublicKey, String actorPublicKey) throws CantGetCryptoCustomerActorException {
        logManager.log(CryptoCustomerActorPluginRoot.getLogLevelByClass(this.getClass().getName()), "Trying to get an specific crypto customer...", null, null);

        CryptoCustomerActor actor = null;

        try {

            actor = this.databaseDao.getRegisterCryptoCustomerActor(actorLoggedInPublicKey, actorPublicKey);
            if(actor == null)
                throw new CantGetCryptoCustomerActorException("", null, ".","Intra User not found");

        } catch (CantRegisterCryptoCustomerActorException e){
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET CRYPTO CUSTOMER ACTOR", "");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoCustomerActorException("CRYPTO CUSTOMER ACTOR", e, "CAN'T GET CRYPTO CUSTOMER ACTOR", "");
        }

        return actor;
    }
    /*END IMPLEMENTATION CryptoCustomerActorManager*/

    /*IMPLEMENTATION DatabaseManagerForDevelopers,*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CryptoCustomerActorDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new CryptoCustomerActorDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try{
            return new CryptoCustomerActorDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    /*END IMPLEMENTATION DatabaseManagerForDevelopers,*/

    /*IMPLEMENTATION LogManagerForDevelopers*/
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorPluginRoot");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        //I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            //if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (CryptoCustomerActorPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoCustomerActorPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoCustomerActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoCustomerActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try{
            //sometimes the classname may be passed dynamically with an $moretext. I need to ignore whats after this.
            String[] correctedClass = className.split((Pattern.quote("$")));
            return CryptoCustomerActorPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            //If I couldn't get the correct logging level, then I will set it to minimal.
            return DEFAULT_LOG_LEVEL;
        }
    }
    /*END IMPLEMENTATION LogManagerForDevelopers*/

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
