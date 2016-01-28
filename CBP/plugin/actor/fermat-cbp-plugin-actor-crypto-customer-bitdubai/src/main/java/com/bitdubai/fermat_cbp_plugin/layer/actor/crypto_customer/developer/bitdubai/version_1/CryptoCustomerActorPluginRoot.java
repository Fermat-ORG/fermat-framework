package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantDeleteCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerIdentityWalletRelationshipRecord;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorManagerImpl;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerIdentityWalletRelationshipImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,       layer = Layers.PLATFORM_SERVICE,    addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,    layer = Layers.SYSTEM,              addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    private CryptoCustomerActorDatabaseDao                  databaseDao;

    private CryptoCustomerActorManagerImpl                  cryptoCustomerActorManagerImpl;

    private CryptoCustomerIdentityWalletRelationshipImpl    cryptoCustomerIdentityWalletRelationshipImpl;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public static final String ACTOR_CRYPTO_CUSTOMER_PROFILE_IMAGE_DIRECTORY_NAME = "actorCryptoCustomerProfileImage";
    public static final String ACTOR_CRYPTO_CUSTOMER_PRIVATE_KEYS_DIRECTORY_NAME = "actorCryptoCustomerPrivateKeys";

    public CryptoCustomerActorPluginRoot(){ super(new PluginVersionReference(new Version())); }

    /*IMPLEMENTATION service*/
    @Override
    public void start() throws CantStartPluginException {
        try {

            //INITIALIZE DAO
            databaseDao = new CryptoCustomerActorDatabaseDao(pluginDatabaseSystem, pluginFileSystem, pluginId);
            databaseDao.initialize();

            //Initialize manager
            cryptoCustomerActorManagerImpl = new CryptoCustomerActorManagerImpl(
                errorManager,
                logManager,
                pluginFileSystem,
                pluginId,
                databaseDao
            );

            //IDENTITY WALLET RELATIONSHIP
            cryptoCustomerIdentityWalletRelationshipImpl = new CryptoCustomerIdentityWalletRelationshipImpl(databaseDao);

            //TEST ACTOR
            /*createNewCryptoCustomerActorTest(false);
            getCryptoCustomerActorTest(false);*/

            //TEST IDENTITY WALLET RELATIONSHIP
            /*createCustomerIdentityWalletRelationshipTest(false);
            updateCustomerIdentityWalletRelationshipTest(false);
            getAllCustomerIdentityWalletRelationshipsTest(false);
            getCustomerIdentityWalletRelationshipsTest(false);*/

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (final CantInitializeCryptoCustomerActorDatabaseException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "pluginDatabaseSystem=" + pluginDatabaseSystem + "pluginId=" + pluginId, "Cannot initialize Crypto Customer Actor Database.");
        }
    }

    @Override
    public FermatManager getManager() { return cryptoCustomerActorManagerImpl; }
    /*END IMPLEMENTATION service*/

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


    /*TEST MOCK ACTOR*/
    private void createNewCryptoCustomerActorTest(boolean sw){

        if(sw) {
            try {
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. CREATE ACTOR ****\n");
                CryptoCustomerActor cryptoCustomerActor = null;
                cryptoCustomerActor = cryptoCustomerActorManagerImpl.createNewCryptoCustomerActor("identityPublicKey", "actorName", null);
                System.out.print("\n- ACTOR DATE " +
                                "\n- PublicKey: " + cryptoCustomerActor.getActorPublicKey() +
                                "\n- Name: " + cryptoCustomerActor.getActorName() +
                                "\n- Type : " + cryptoCustomerActor.getActorType().getCode()
                );

            } catch (CantCreateCryptoCustomerActorException e){
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. CREATE ACTOR. ERROR CREATE REGISTER. ****\n");
            }
        }else{
            System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. CREATE ACTOR. OFF****\n");
        }

    }

    private void getCryptoCustomerActorTest(boolean sw){
        if(sw) {

            try{
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET ACTOR ****\n");
                CryptoCustomerActor cryptoCustomerActor = null;
                cryptoCustomerActor = cryptoCustomerActorManagerImpl.getCryptoCustomerActor("045B933DF69E8422D8BA29928156A2DE1F9302A5C52B8F0ABC62B3473C460366C4D97D08480F2136390F6BF5B90873ED01DF2A6293221D10E5911692A6B4E65E92");
                if(cryptoCustomerActor != null){
                    System.out.print("\n- ACTOR DATE " +
                                    "\n- PublicKey: " + cryptoCustomerActor.getActorPublicKey() +
                                    "\n- Name: " + cryptoCustomerActor.getActorName() +
                                    "\n- Type : " + cryptoCustomerActor.getActorType().getCode()
                    );
                }else{ System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET ACTOR. ERROR GET REGISTER IS NULL. ****\n"); }
            } catch (CantGetCryptoCustomerActorException e){
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET ACTOR. ERROR GET REGISTER. ****\n");
            }
        }else{
            System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET ACTOR. OFF****\n");
        }
    }


    /*TEST MOCK RELATIONSHIP*/
    private void createCustomerIdentityWalletRelationshipTest(boolean sw){

        if(sw) {
            try {

                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. CREATE RELATIONSHIP ****\n");

                CryptoCustomerIdentityWalletRelationshipRecord relationshipRecord = null;

                relationshipRecord = cryptoCustomerIdentityWalletRelationshipImpl.createCustomerIdentityWalletRelationship("walletPublicKey23", "identityPublicKey23");
                System.out.print("\n- RELATIONSHIP DATE " +
                                "\n- ID: " + relationshipRecord.getRelationship() +
                                "\n- WalletPublicKey: " + relationshipRecord.getWalletPublicKey() +
                                "\n- IdentityPublicKey : " + relationshipRecord.getIdentityPublicKey()
                );

            } catch (CantCreateCustomerIdentiyWalletRelationshipException e) {
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. CREATE RELATIONSHIP. ERROR CREATE REGISTER. ****\n");
            }
        }else{
            System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. CREATE RELATIONSHIP. OFF****\n");
        }

    }
    
    private void updateCustomerIdentityWalletRelationshipTest(boolean sw){

        if(sw) {
            try {
                
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. UPDATE RELATIONSHIP ****\n");
                
                CryptoCustomerIdentityWalletRelationshipRecord relationshipRecord = null;
                
                UUID id = UUID.fromString("8130fdf2-b5b6-4424-b8f5-00c56d8b108b");
                
                relationshipRecord = cryptoCustomerIdentityWalletRelationshipImpl.updateCustomerIdentityWalletRelationship(id, "walletPublicKey2", "identityPublicKey2");
                System.out.print("\n- RELATIONSHIP DATE " +
                                 "\n- ID: " + relationshipRecord.getRelationship() +
                                 "\n- WalletPublicKey: " + relationshipRecord.getWalletPublicKey() +
                                 "\n- IdentityPublicKey : " + relationshipRecord.getIdentityPublicKey()
                );
                
            } catch (CantUpdateCustomerIdentiyWalletRelationshipException e){
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. UPDATE RELATIONSHIP. ERROR UPDATE REGISTER. ****\n");
            }

        }else{
            System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. UPDATE RELATIONSHIP. OFF****\n");
        }
        
    }

    /*private void deleteCustomerIdentityWalletRelationshipTest(String sId){

        if(sId != null) {
            try {

                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. DELETE RELATIONSHIP ****\n");

                CryptoCustomerIdentityWalletRelationshipRecord relationshipRecord = null;

                UUID id = UUID.fromString(sId);

                cryptoCustomerIdentityWalletRelationshipImpl.deleteCustomerIdentityWalletRelationship(id);

                if(cryptoCustomerIdentityWalletRelationshipImpl.getCustomerIdentityWalletRelationships(id) == null){

                    System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. DELETE RELATIONSHIP. REGISTER " + sId + " DELETE. ****\n");

                }

            } catch (CantDeleteCustomerIdentiyWalletRelationshipException e){
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. UPDATE RELATIONSHIP. ERROR UPDATE REGISTER. ****\n");
            }catch (CantGetCustomerIdentiyWalletRelationshipException e){
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET RELATIONSHIP OF DELETE. ERROR GET REGISTER OF DELETE. ****\n");
            }

        }else{
            System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. UPDATE RELATIONSHIP. OFF****\n");
        }

    }*/
    
    private void getAllCustomerIdentityWalletRelationshipsTest(boolean sw){

        if(sw) {
            try{

                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET ALL RELATIONSHIP ****\n");
                
                Collection<CryptoCustomerIdentityWalletRelationshipRecord> getAllRelationship = new ArrayList<CryptoCustomerIdentityWalletRelationshipRecord>();

                getAllRelationship = cryptoCustomerIdentityWalletRelationshipImpl.getAllCustomerIdentityWalletRelationships();

                System.out.print("\n\n\n\n------------------------------- COLLECTION RELATIONSHIP -------------------------------");
                for (CryptoCustomerIdentityWalletRelationshipRecord relationshipRecord : getAllRelationship) {

                    System.out.print("\n- RELATIONSHIP DATE " +
                                    "\n- ID: " + relationshipRecord.getRelationship() +
                                    "\n- WalletPublicKey: " + relationshipRecord.getWalletPublicKey() +
                                    "\n- IdentityPublicKey : " + relationshipRecord.getIdentityPublicKey()
                    );

                }
                System.out.print("\n\n------------------------------- END COLLECTION RELATIONSHIP -------------------------------");
                
            }catch (CantGetCustomerIdentiyWalletRelationshipException e){
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET ALL RELATIONSHIP. ERROR GET ALL REGISTER. ****\n");
            }
        }else{
            System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET ALL RELATIONSHIP. OFF****\n");
        }
    }

    private void getCustomerIdentityWalletRelationshipsTest(boolean sw){

        if(sw) {
            try{

                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET RELATIONSHIP ****\n");

                UUID id = UUID.fromString("7d884ad6-6eda-401b-b5e8-c2406b93de68");

                CryptoCustomerIdentityWalletRelationshipRecord relationshipRecord = null;

                //GET BY ID
                relationshipRecord = cryptoCustomerIdentityWalletRelationshipImpl.getCustomerIdentityWalletRelationships(id);
                if(relationshipRecord != null) {
                    System.out.print("\n- RELATIONSHIP BY ID DATE " +
                                    "\n- ID: " + relationshipRecord.getRelationship() +
                                    "\n- WalletPublicKey: " + relationshipRecord.getWalletPublicKey() +
                                    "\n- IdentityPublicKey : " + relationshipRecord.getIdentityPublicKey()
                    );
                }else{ System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET RELATIONSHIP. ERROR GET REGISTER IS NULL BY ID. ****\n"); }

                //GET BY WALLET
                relationshipRecord = cryptoCustomerIdentityWalletRelationshipImpl.getCustomerIdentityWalletRelationshipsByWallet("walletPublicKey");
                if(relationshipRecord != null) {
                    System.out.print("\n- RELATIONSHIP BY WALLET DATE " +
                                    "\n- ID: " + relationshipRecord.getRelationship() +
                                    "\n- WalletPublicKey: " + relationshipRecord.getWalletPublicKey() +
                                    "\n- IdentityPublicKey : " + relationshipRecord.getIdentityPublicKey()
                    );
                }else{ System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET RELATIONSHIP. ERROR GET REGISTER IS NULL BY WALLET. ****\n"); }

                //GET BY WALLET
                relationshipRecord = cryptoCustomerIdentityWalletRelationshipImpl.getCustomerIdentityWalletRelationshipsByIdentity("identityPublicKey2");
                if(relationshipRecord != null) {
                    System.out.print("\n- RELATIONSHIP BY IDENTITY DATE " +
                                    "\n- ID: " + relationshipRecord.getRelationship() +
                                    "\n- WalletPublicKey: " + relationshipRecord.getWalletPublicKey() +
                                    "\n- IdentityPublicKey : " + relationshipRecord.getIdentityPublicKey()
                    );
                }else{ System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET RELATIONSHIP. ERROR GET REGISTER IS NULL BY IDENTITY. ****\n"); }

            }catch (CantGetCustomerIdentiyWalletRelationshipException e){
                System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET RELATIONSHIP. ERROR GET REGISTER. ****\n");
            }
        }else{
            System.out.print("\n**** MOCK CRYPTO CUSTOMER ACTOR. GET RELATIONSHIP. OFF****\n");
        }
    }
    /*END TEST*/
}