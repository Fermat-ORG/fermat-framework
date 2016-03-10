package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_art_api.all_definition.art_actors.artist.ActorArtistNetworkService;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAskConnectionActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRegisterActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestListActorArtistNetworkServiceRegisteredException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ActorArtistNetworkServiceManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistActor;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.ArtistActorNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.ArtistActorNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.ArtistActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.IncomingNotificationDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.OutgoingNotificationDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Gabriel Araujo on 08/03/16.
 */
public class ArtistActorNetworkServicePluginRoot extends AbstractNetworkServiceBase implements
        ActorArtistNetworkServiceManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    private long reprocessTimer = 300000; //five minutes
    private Timer timer = new Timer();
    private Database dataBase;

    private ArtistActorNetworkServiceDeveloperDatabaseFactory artistActorNetworkServiceDeveloperDatabaseFactory;
    private IncomingNotificationDao incomingNotificationDao;
    private OutgoingNotificationDao outgoingNotificationDao;
    /**
     * Executor
     */
    ExecutorService executorService;
    /**
     * Default constructor
     */
    public ArtistActorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                EventSource.ACTOR_NETWORK_SERVICE_ARTIST,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.ARTIST_ACTOR,
                "Actor Network Service Artist",
                null);
    }

    @Override
    protected void onStart() throws CantStartPluginException {

        try {
            initializeDb();
            artistActorNetworkServiceDeveloperDatabaseFactory = new ArtistActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem,pluginId);
            artistActorNetworkServiceDeveloperDatabaseFactory.initializeDatabaseCommunication();
            artistActorNetworkServiceDeveloperDatabaseFactory.initializeDatabase();
            incomingNotificationDao = new IncomingNotificationDao(dataBase,pluginFileSystem,pluginId);
            outgoingNotificationDao = new OutgoingNotificationDao(dataBase,pluginFileSystem,pluginId);
            executorService = Executors.newFixedThreadPool(2);
            this.startTimer();
            System.out.println("########################\nART ACTOR STARTED\n####################################");
        } catch (CantInitializeTemplateNetworkServiceDatabaseException e) {
            e.printStackTrace();
            reportUnexpectedError(e);
        }catch (Exception e){
            e.printStackTrace();
            reportUnexpectedError(e);
        }

    }

    public IncomingNotificationDao getIncomingNotificationDao() {
        return incomingNotificationDao;
    }

    public void setIncomingNotificationDao(IncomingNotificationDao incomingNotificationDao) {
        this.incomingNotificationDao = incomingNotificationDao;
    }

    public OutgoingNotificationDao getOutgoingNotificationDao() {
        return outgoingNotificationDao;
    }

    public void setOutgoingNotificationDao(OutgoingNotificationDao outgoingNotificationDao) {
        this.outgoingNotificationDao = outgoingNotificationDao;
    }

    @Override
    protected void onNetworkServiceRegistered() {
        System.out.println("##########################\n Actor Artist Network Service Registred\nPublic Key: " + getIdentity().getPublicKey() + "\n#######################################3");
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                // reprocessPendingMessage();
            }
        }, 0, reprocessTimer);
    }
    /**
     * This method initialize the database
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    private void initializeDb() throws CantInitializeTemplateNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, ArtistActorNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.ACTOR_NETWORK_SERVICE_ARTIST, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            ArtistActorNetworkServiceDatabaseFactory artistActorNetworkServiceDatabaseFactory = new ArtistActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = artistActorNetworkServiceDatabaseFactory.createDatabase(pluginId, ArtistActorNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                reportUnexpectedError(e);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }
    }

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.ACTOR_NETWORK_SERVICE_ARTIST, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }
    @Override
    public void stop() {
        super.stop();
        executorService.shutdownNow();
    }

    @Override
    public void registerActorArtist(ArtistActor actorArtistNetworkService) throws CantRegisterActorArtistNetworkServiceException {

    }

    @Override
    public void updateActorArtist(ArtistActor actorArtistNetworkService) throws CantRegisterActorArtistNetworkServiceException {

    }

    @Override
    public List<ArtistActor> getListActorArtistRegistered() throws CantRequestListActorArtistNetworkServiceRegisteredException {
        return null;
    }

    @Override
    public void askConnectionActorArtist(String senderPublicKey, String senderName, Actors senderType, String receiverPublicKey, String receiverName, Actors destinationType, byte[] profileImage) throws CantAskConnectionActorArtistNetworkServiceException {

    }

    @Override
    public void acceptConnectionActorAsset(String actorLoggedInPublicKey, String ActorToAddPublicKey) throws CantAskConnectionActorArtistNetworkServiceException {

    }

    @Override
    public void denyConnectionActorAsset(String actorLoggedInPublicKey, String actorAssetToRejectPublicKey) throws CantAskConnectionActorArtistNetworkServiceException {

    }

    @Override
    public void disconnectConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToDisconnectPublicKey) throws CantAskConnectionActorArtistNetworkServiceException {

    }

    @Override
    public void cancelConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToCancelPublicKey) throws CantAskConnectionActorArtistNetworkServiceException {

    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }
}
