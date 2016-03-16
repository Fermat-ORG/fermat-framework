package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1;

import android.util.Base64;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.NotificationDescriptor;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAskConnectionActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRegisterActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestListActorArtistNetworkServiceRegisteredException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ActorArtistNetworkServiceManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistActor;
import com.bitdubai.fermat_art_api.layer.actor_network_service.util.ArtistActorImp;
import com.bitdubai.fermat_art_api.layer.actor_network_service.util.ArtistActorNetworkServiceRecord;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.ArtistActorNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.ArtistActorNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.ArtistActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.IncomingNotificationDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.communications.OutgoingNotificationDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantCreateActorArtistNotificationException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
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
    protected final static String ART_PROFILE_IMG = "ART_PROFILE_IMG";

    private ArtistActorNetworkServiceDeveloperDatabaseFactory artistActorNetworkServiceDeveloperDatabaseFactory;
    private IncomingNotificationDao incomingNotificationDao;
    private OutgoingNotificationDao outgoingNotificationDao;

    private List<PlatformComponentProfile> actorArtistPendingToRegistration;
    private List<ArtistActor> actorArtistRegistered;
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
        this.actorArtistPendingToRegistration = new ArrayList<>();
        this.actorArtistRegistered = new ArrayList<>();
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
        try {
            //TODO Test this functionality
            for (PlatformComponentProfile platformComponentProfile : actorArtistPendingToRegistration) {
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
                System.out.println("##################################\nArtistActorNetworkServicePluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
            }
        } catch (Exception e) {
            reportUnexpectedError(e);
            e.printStackTrace();
        }
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

    private PlatformComponentProfile constructPlatformComponentProfile(ArtistActor artist, CommunicationsClientConnection communicationsClientConnection) {
   /*
    * Construct the profile
    */
        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ART_PROFILE_IMG, Base64.encodeToString(artist.getProfileImage(), Base64.DEFAULT));

        String extraData = gson.toJson(jsonObject);

        return communicationsClientConnection.constructPlatformComponentProfileFactory(
                artist.getPublicKey(),
                artist.getAlias().toLowerCase().trim(),
                artist.getAlias(),
                NetworkServiceType.ARTIST_ACTOR,
                PlatformComponentType.NETWORK_SERVICE,
                extraData);
    }
    @Override
    public void registerActorArtist(ArtistActor actorArtistNetworkService) throws CantRegisterActorArtistNetworkServiceException {
        try {
            if (isRegister()) {
                final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();

                /*
                 * Construct the profile
                 */

                final PlatformComponentProfile platformComponentProfileActorArtist = constructPlatformComponentProfile(
                        actorArtistNetworkService, communicationsClientConnection);
                /*
                 * ask to the communication cloud client to register
                 */
                /**
                 * I need to add this in a new thread other than the main android thread
                 */
                if (!actorArtistPendingToRegistration.contains(platformComponentProfileActorArtist)) {
                    actorArtistPendingToRegistration.add(platformComponentProfileActorArtist);
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.registerComponentForCommunication(
                                    getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfileActorArtist);
                            onComponentRegistered(platformComponentProfileActorArtist);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                }, "ACTOR ARTIST REGISTER-ACTOR");
                thread.start();
            }else{
                actorArtistPendingToRegistration.add(constructPlatformComponentProfile(
                        actorArtistNetworkService,wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()));
            }
        } catch (Exception e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantRegisterActorArtistNetworkServiceException pluginStartException = new CantRegisterActorArtistNetworkServiceException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);
            errorManager.reportUnexpectedPluginException(Plugins.ACTOR_NETWORK_SERVICE_ARTIST, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void updateActorArtist(ArtistActor actorArtistNetworkService) throws CantRegisterActorArtistNetworkServiceException {

    }

    @Override
    public List<ArtistActor> getListActorArtistRegistered() throws CantRequestListActorArtistNetworkServiceRegisteredException {
        try {
//            if (true) {
            if (actorArtistRegistered != null && !actorArtistRegistered.isEmpty()) {
                actorArtistRegistered.clear();
            }

            DiscoveryQueryParameters discoveryQueryParametersAssetUser = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
                    constructDiscoveryQueryParamsFactory(PlatformComponentType.NETWORK_SERVICE, //applicant = who made the request
                            NetworkServiceType.ARTIST_ACTOR,
                            null,                     // alias
                            null,                     // identityPublicKey
                            null,                     // location
                            null,                     // distance
                            null,                     // name
                            null,                     // extraData
                            null,                     // offset
                            null,                     // max
                            null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                            null);

            List<PlatformComponentProfile> platformComponentProfileRegisteredListRemote = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParametersAssetUser);

            if (platformComponentProfileRegisteredListRemote != null && !platformComponentProfileRegisteredListRemote.isEmpty()) {

                for (PlatformComponentProfile platformComponentProfile : platformComponentProfileRegisteredListRemote) {

                    String profileImage = "";
                    byte[] imageByte = null;
                    if (!platformComponentProfile.getExtraData().equals("") || platformComponentProfile.getExtraData() != null) {
                        try {
                            JsonParser jParser = new JsonParser();
                            JsonObject jsonObject = jParser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();

                            profileImage = jsonObject.get(ART_PROFILE_IMG).getAsString();
                        } catch (Exception e) {
                            profileImage = platformComponentProfile.getExtraData();
                        }
                        imageByte = Base64.decode(profileImage, Base64.DEFAULT);
                    }


                    ArtistActor artistActor = new ArtistActorImp(
                            platformComponentProfile.getIdentityPublicKey(),
                            platformComponentProfile.getName(),
                            imageByte);

                    actorArtistRegistered.add(artistActor);
                }
            } else {
                return actorArtistRegistered;
            }

        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Cant Request List Actor Asset Redeem Point Registered";

            CantRequestListActorArtistNetworkServiceRegisteredException pluginStartException = new CantRequestListActorArtistNetworkServiceRegisteredException(CantRequestListActorArtistNetworkServiceRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
        catch (Exception e){
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Cant Request List Actor Asset Redeem Point Registered";

            CantRequestListActorArtistNetworkServiceRegisteredException pluginStartException = new CantRequestListActorArtistNetworkServiceRegisteredException(CantRequestListActorArtistNetworkServiceRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
        return actorArtistRegistered;
    }

    @Override
    public void askConnectionActorArtist(String senderPublicKey, String senderName, PlatformComponentType senderType, String receiverPublicKey, String receiverName, PlatformComponentType destinationType, byte[] profileImage) throws CantAskConnectionActorArtistNetworkServiceException {
        try {
            UUID newNotificationID = UUID.randomUUID();
            NotificationDescriptor notificationDescriptor = NotificationDescriptor.ASKFORCONNECTION;
            long currentTime = System.currentTimeMillis();
            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            final ArtistActorNetworkServiceRecord artistActorNetworkServiceRecord = outgoingNotificationDao.createNotification(
                    newNotificationID,
                    senderPublicKey,
                    senderType,
                    receiverPublicKey,
                    senderName,
//                    intraUserToAddPhrase,
                    profileImage,
                    destinationType,
                    notificationDescriptor,
                    currentTime,
                    protocolState,
                    false,
                    1,
                    null
            );

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(
                                        artistActorNetworkServiceRecord.getActorSenderPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        artistActorNetworkServiceRecord.getActorSenderType()
                                ),
                                getProfileDestinationToRequestConnection(
                                        artistActorNetworkServiceRecord.getActorDestinationPublicKey(),
                                        NetworkServiceType.UNDEFINED,
                                        artistActorNetworkServiceRecord.getActorDestinationType()
                                ),
                                artistActorNetworkServiceRecord.toJson());
                    } catch (CantSendMessageException e) {
                        reportUnexpectedError(e);
                    }
                }
            });
            // Sending message to the destination
        } catch (final CantCreateActorArtistNotificationException e) {
            reportUnexpectedError(e);
            throw new CantAskConnectionActorArtistNetworkServiceException(e, "actor asset redeem network service", "database corrupted");
        } catch (final Exception e) {
            reportUnexpectedError(e);
            throw new CantAskConnectionActorArtistNetworkServiceException(e, "actor asset redeem network service", "Unhandled error.");
        }
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
        ArtistActorNetworkServiceDeveloperDatabaseFactory dbFactory = new ArtistActorNetworkServiceDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        ArtistActorNetworkServiceDeveloperDatabaseFactory dbFactory = new ArtistActorNetworkServiceDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            ArtistActorNetworkServiceDeveloperDatabaseFactory dbFactory = new ArtistActorNetworkServiceDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);
        } catch (CantInitializeTemplateNetworkServiceDatabaseException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.ACTOR_NETWORK_SERVICE_ARTIST, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }

    @Override
    protected void onComponentRegistered(PlatformComponentProfile platformComponentProfileRegistered) {
        actorArtistPendingToRegistration.clear();
    }

    @Override
    protected void onFailureComponentRegistration(PlatformComponentProfile platformComponentProfile) {
        try {
            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(getNetworkServiceProfile().getNetworkServiceType(), platformComponentProfile);
            System.out.println("##################################\nArtistActorNetworkServicePluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
        } catch (CantRegisterComponentException e) {
            e.printStackTrace();
            reportUnexpectedError(e);
        }

    }
}
