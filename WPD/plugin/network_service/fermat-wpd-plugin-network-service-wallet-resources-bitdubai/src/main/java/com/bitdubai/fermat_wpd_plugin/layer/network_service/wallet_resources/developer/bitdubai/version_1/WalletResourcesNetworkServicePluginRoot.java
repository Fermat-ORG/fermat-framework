package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.github.GitHubConnection;
import com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubRepositoryNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetImageResourceException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetLanguageFileException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetSkinFileException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Layout;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.InstalationProgress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenOrientation;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.fermat_wpd_api.all_definition.enums.EventType;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletNavigationStructureDownloadedEvent;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletUninstalledEvent;
import com.bitdubai.fermat_wpd_api.all_definition.exceptions.CantGetWalletNavigationStructureException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.exceptions.CantCreateRepositoryException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.exceptions.WalletResourcesUnninstallException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesInstalationManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkServicesWalletResourcesDAO;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkserviceswalletresourcesDatabaseConstants;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.event_handlers.BegunWalletInstallationEventHandler;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteLayouts;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteRepositoryException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteResource;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteResourcesFromDisk;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteXml;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDonwloadNavigationStructure;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDownloadLanguage;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDownloadLanguageFromRepo;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDownloadLayouts;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDownloadResource;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDownloadResourceFromRepo;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantGetGitHubConnectionException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantGetRepositoryPathRecordException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServicesWalletResourcesDatabaseException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantUninstallWallet;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.Repository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Matias Furszyfer
 */

/**
 * This plugin is designed to look up for the resources needed by a newly installed wallet. We are talking about the
 * navigation structure, plus the images needed by the wallet to be able to run.
 * <p>
 * It will try to gather those resources from other peers or a centralized location provided by the wallet developer
 * if it is not possible.
 * <p>
 * It will also serve other peers with these resources when needed.
 * <p>
 * * * * * * *
 */

public class WalletResourcesNetworkServicePluginRoot extends AbstractPlugin implements
        WalletResourcesInstalationManager,
        WalletResourcesProviderManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    public WalletResourcesNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    // TODO MAKE USE OF THE ERROR MANAGER.

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * Dealing with the repository database
     */
    NetworkServicesWalletResourcesDAO networkServicesWalletResourcesDAO;

    /**
     * Installed skins repositories
     * <p>
     * SkinId, repository link
     */


    private String REPOSITORY_LINK = "https://raw.githubusercontent.com/bitDubai/fermat-wallet-resources/master/";


    private final String LOCAL_STORAGE_PATH = "wallet-resources/";


    /**
     *  Wallet instalation progress
     */
    private InstalationProgress instalationProgress;

    //para testear
    // private Map<String, byte[]> imagenes;
    //private Map<String, String> layouts;


    /**
     * Github connection until the main repository be open source
     */
    private GitHubConnection gitHubConnection;

    private GitHubConnection getGitHubConnection() throws CantGetGitHubConnectionException {


        try {

            if (this.gitHubConnection != null)
                return this.gitHubConnection;

            this.gitHubConnection = new GitHubConnection();
            return this.gitHubConnection;

        }  catch (GitHubNotAuthorizedException e) {

            throw new CantGetGitHubConnectionException(e, null, "Error in github authentication");
        } catch (GitHubRepositoryNotFoundException e) {

            throw new CantGetGitHubConnectionException(e, null, "Error init github repository not found");
        } catch (Exception e) {

            throw new CantGetGitHubConnectionException(e, null, "Unhandled Exception.");

        }
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {

        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */

            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            fermatEventListener = eventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION);
            fermatEventHandler = new BegunWalletInstallationEventHandler();
            ((BegunWalletInstallationEventHandler) fermatEventHandler).setWalletResourcesInstalationManager(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

              /*
         * Listen and handle VPN Connection Close Notification Event
         */
            //   fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
            //   fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
            //  eventManager.addListener(fermatEventListener);
            //  listenersAdded.add(fermatEventListener);

              /*
         * Listen and handle Client Connection Close Notification Event
         */
            //  fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
            //  fermatEventListener.setEventHandler(new ClientConnectionCloseNotificationEventHandler(this));
            //  eventManager.addListener(fermatEventListener);
            //  listenersAdded.add(fermatEventListener);

              /*
         * Listen and handle Client Connection Loose Notification Event
         */
//            fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
//            fermatEventListener.setEventHandler(new ClientConnectionLooseNotificationEventHandler(this));
//            eventManager.addListener(fermatEventListener);
//            listenersAdded.add(fermatEventListener);


        /*
         * Listen and handle Client Connection Success Reconnect Notification Event
         */
//            fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
//            fermatEventListener.setEventHandler(new ClientSuccessfulReconnectNotificationEventHandler(this));
//            eventManager.addListener(fermatEventListener);
//            listenersAdded.add(fermatEventListener);

            /**
             *  Create repository in database
             */
            networkServicesWalletResourcesDAO = new NetworkServicesWalletResourcesDAO(pluginDatabaseSystem);
            networkServicesWalletResourcesDAO.initializeDatabase(pluginId, NetworkserviceswalletresourcesDatabaseConstants.DATABASE_NAME);

            this.serviceStatus = ServiceStatus.STARTED;
        }
        catch(CantInitializeNetworkServicesWalletResourcesDatabaseException e)
        {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null,"Error init plugin data base");

        } catch(Exception  e)
        {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null,"Unhandled Exception.");

        }
    }

    @Override
    public void stop() {


        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * WalletResourcesInstalationManager Implementation
     */

    //el xml de las skin debe estar pegado a una estructura de navegacion
    @Override
    public void installCompleteWallet(String walletCategory, String walletType, String developer, String screenSize, String skinName, String languageName, String navigationStructureVersion,String walletPublicKey) throws WalletResourcesInstalationException {
        // this will be use when the repository be open source
        //String linkToRepo = REPOSITORY_LINK + walletCategory + "/" + walletType + "/" + developer + "/";

        String linkToRepo = "seed-resources/wallet_resources/"+developer+"/"+walletCategory+"/"+walletType+"/";

        String linkToResources = linkToRepo + "skins/" + skinName + "/";


        String localStoragePath=this.LOCAL_STORAGE_PATH + developer +"/" +walletCategory + "/" + walletType + "/"+ "skins/" + skinName + "/" + screenSize + "/";

        Skin skin;

        /**
         * add progress
         */
        addProgress(InstalationProgress.INSTALATION_START);

        try {

            String linkToSkinFile= linkToResources + screenSize +"/";
            skin = checkAndInstallSkinResources(linkToSkinFile, localStoragePath,walletPublicKey);


            Repository repository = new Repository(skinName, navigationStructureVersion, localStoragePath);

            /**
             *  Save skin on Database
             */
            networkServicesWalletResourcesDAO.createRepository(repository, skin.getId());

            /**
             *  download navigation structure
             */

            String linkToNavigationStructure = linkToRepo + "navigation_structure/" + skin.getNavigationStructureCompatibility() + "/";
            downloadNavigationStructure(linkToNavigationStructure, skin.getId(), localStoragePath, walletPublicKey);

            /**
             *  download resources
             */

            downloadResourcesFromRepo(linkToResources, skin, localStoragePath, screenSize, walletPublicKey);

            /**
             *  download language
             */
            String linkToLanguage = linkToRepo + "languages/";

            downloadLanguageFromRepo(linkToLanguage, skin.getId(), languageName, localStoragePath + "languages/", screenSize, walletPublicKey);


        } catch (CantDonwloadNavigationStructure e) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",e,"Error download navigation structure","");
        } catch (CantDownloadResourceFromRepo e) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",e,"Error download Resource fro repo","");
        } catch (CantDownloadLanguageFromRepo e) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",e,"Error download language from repo","");

        }
        catch (CantCreateRepositoryException e) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",e,"Error created repository on database","");

        } catch (CantCheckResourcesException cantCheckResourcesException) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",cantCheckResourcesException,"Error in skin.mxl file","");

        } catch (Exception e) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",e,"unknown error","");
        }

        //installSkinResource("null");
    }

    /**
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param screenSize
     * @param skinName
     * @param navigationStructureVersion
     * @throws WalletResourcesInstalationException
     */
    @Override
    public void installSkinForWallet(String walletCategory, String walletType, String developer, String screenSize, String skinName, String navigationStructureVersion,String walletPublicKey) throws WalletResourcesInstalationException {
        try {
            String linkToRepo = "seed-resources/wallet_resources/"+developer+"/"+walletCategory+"/"+walletType+"/";

            String linkToResources = linkToRepo + "skins/" + skinName + "/";


            String localStoragePath=this.LOCAL_STORAGE_PATH +developer+"/"+walletCategory + "/" + walletType + "/"+ "skins/" + skinName + "/" + screenSize + "/";

            Skin skin;

            /**
             * add progress
             */
            addProgress(InstalationProgress.INSTALATION_START);


            String linkToSkinFile = linkToResources + screenSize + "/";
            skin = checkAndInstallSkinResources(linkToSkinFile, localStoragePath,walletPublicKey);


            Repository repository = new Repository(skinName, navigationStructureVersion, localStoragePath);


            networkServicesWalletResourcesDAO.createRepository(repository, skin.getId());


            /**
             *  download resources
             */

            downloadResourcesFromRepo(linkToResources, skin, localStoragePath, screenSize, walletPublicKey);

        } catch (CantCreateRepositoryException e) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",e,"Error save skin on data base","");
        }
        catch (CantCheckResourcesException cantCheckResourcesException){
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",cantCheckResourcesException,"Error check exception","");
        }  catch (CantDownloadResourceFromRepo cantDownloadResourceFromRepo) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",cantDownloadResourceFromRepo,"Error download resources","");

        }


    }

    /**
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param screenSize
     * @param skinId
     * @param languageName
     * @throws WalletResourcesInstalationException
     */
    @Override
    public void installLanguageForWallet(String walletCategory, String walletType, String developer, String screenSize, UUID skinId, String languageName,String walletPublicKey) throws WalletResourcesInstalationException {

        try {

            Repository repository = networkServicesWalletResourcesDAO.getRepository(skinId);


            /**
             *  download language
             */
            String linkToRepo = "seed-resources/wallet_resources/" + developer + "/" + walletCategory + "/" + walletType + "/";
            /**
             *  download language
             */
            String linkToLanguage = linkToRepo + "languages/";

            downloadLanguageFromRepo(linkToLanguage, skinId, languageName, repository.getPath() + walletPublicKey + "languages/", screenSize, walletPublicKey);

            /**
             *  Fire event Wallet language installed
             */

        /*FermatEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_UNINSTALLED);
        WalletUninstalledEvent walletUninstalledEvent=  (WalletUninstalledEvent) platformEvent;
        walletUninstalledEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);*/
        }
        catch(CantDownloadLanguageFromRepo e) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET LANGUAGE:", e, "Error download language ", "");
        }
        catch(Exception e)
        {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET LANGUAGE:", e, "unknown Error ", "");
        }


    }


    @Override
    public void uninstallCompleteWallet(String walletCategory, String walletType, String developer, String skinName, UUID skinId, String screenSize, String navigationStructureVersion, boolean isLastWallet,String walletPublicKey) throws WalletResourcesUnninstallException{
        try
        {
            if(isLastWallet){

                UninstallWallet(walletCategory, walletType, developer, skinName, skinId, screenSize, navigationStructureVersion, isLastWallet);

            }

            /**
             *  Fire event Wallet resource installed
             */

            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.WALLET_UNINSTALLED);
            WalletUninstalledEvent walletUninstalledEvent=  (WalletUninstalledEvent) fermatEvent;
            walletUninstalledEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
            eventManager.raiseEvent(fermatEvent);
        }
        catch(CantUninstallWallet e) {
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL COMPLETE WALLET:", e, "Error delete wallet resource ", "");
        }
        catch(Exception e)
        {
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL COMPLETE WALLET:", e, "unknown Error ", "");
        }



    }

    @Override
    public void uninstallSkinForWallet( UUID skinId,String walletPublicKey) throws WalletResourcesUnninstallException {

        try {
            //get repo from table
            Repository repository = networkServicesWalletResourcesDAO.getRepository(skinId);
            //get image from disk
            PluginTextFile layoutFile;


            String reponame = repository.getPath() + walletPublicKey +"/";

            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, skinId.toString() + "_" + repository.getSkinName(), FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);


            Skin skin = (Skin) XMLParser.parseXML(layoutFile.getContent(), new Skin());

            /**
             *  delete skin resources
             */

            networkServicesWalletResourcesDAO.delete(skinId, repository.getSkinName());

            deleteResources(repository.getPath(), skin.getResources(), skinId);

        } catch (CantGetRepositoryPathRecordException e) {
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL WALLET SKIN",e,"Error get repository","");

        }catch (CantDeleteRepositoryException cantCheckResourcesException){
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL WALLET SKIN",cantCheckResourcesException,"Error check exception","");

        }  catch (CantDeleteResourcesFromDisk cantDownloadResourceFromRepo) {
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL WALLET SKIN", cantDownloadResourceFromRepo, "Error download resources", "");

        } catch (FileNotFoundException e) {
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL WALLET SKIN",e,"Error get skin file not found","");

        } catch (CantCreateFileException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param skinId
     * @param walletPublicKey
     * @param languageName
     * @throws WalletResourcesUnninstallException
     */
    @Override
    public void uninstallLanguageForWallet(UUID skinId,String walletPublicKey, String languageName) throws WalletResourcesUnninstallException {
        try {

            //get repo from table
            Repository repository = networkServicesWalletResourcesDAO.getRepository(skinId);
            //get image from disk
            PluginTextFile layoutFile;


            String reponame = repository.getPath() + walletPublicKey + "/languages/";

            languageName = skinId.toString() + "_" + languageName;


            pluginFileSystem.deleteTextFile(pluginId, reponame, languageName, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
            /**
             *  Fire event Wallet language installed
             */

        /*FermatEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_UNINSTALLED);
        WalletUninstalledEvent walletUninstalledEvent=  (WalletUninstalledEvent) platformEvent;
        walletUninstalledEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);*/
        }
        catch(CantGetRepositoryPathRecordException e) {
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL WALLET LANGUAGE:", e, "Error get repository on database ", "");
        }
        catch(CantCreateFileException e) {
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL WALLET LANGUAGE:", e, "Error delete language file ", "");
        }
        catch(FileNotFoundException e) {
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL WALLET LANGUAGE:", e, "Error language file not found ", "");
        }
        catch(Exception e)
        {
            throw new WalletResourcesUnninstallException("CAN'T UNINSTALL WALLET LANGUAGE:", e, "unknown Error ", "");
        }
    }


    @Override
    public InstalationProgress getInstallationProgress(){
        return instalationProgress;
    }


    /**
     * <p>This method read wallet manifest file to get resources names, to download from repository.
     * <p>Save file in device memory
     *
     * @throws CantCheckResourcesException
     */


    @Override
    public UUID getResourcesId() {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }



    @Override
    public Skin getSkinFile(UUID skinId, String walletPublicKey) throws CantGetSkinFileException, CantGetResourcesException {

        try {
            //get repo from table
            Repository repository = networkServicesWalletResourcesDAO.getRepository(skinId);
            //get image from disk
            PluginTextFile layoutFile;


            String reponame = repository.getPath() + walletPublicKey +"/";

            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, skinId.toString() + "_" + repository.getSkinName(), FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            return (Skin) XMLParser.parseXML(layoutFile.getContent(), new Skin());

        } catch (FileNotFoundException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET SKIN RESOURCES:", e, "Error write layout file resource  ", "");

        } catch (CantGetRepositoryPathRecordException e) {

            throw new CantGetResourcesException("CAN'T GET SKIN RESOURCES:", e, "Error get repository from database ", "");

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET SKIN RESOURCES:", e, "Error created image file resource ", "");

        }




    }

    @Override
    public String getLanguageFile(UUID skinId,String walletPublicKey,String fileName) throws CantGetLanguageFileException {

        try {
            //get repo from table
            Repository repository = networkServicesWalletResourcesDAO.getRepository(skinId);
            //get image from disk
            PluginTextFile layoutFile;


            String reponame = repository.getPath() + walletPublicKey + "/languages/";

            fileName = skinId.toString() + "_" + fileName;


            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, fileName, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            return layoutFile.getContent();
        } catch (FileNotFoundException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetLanguageFileException("CAN'T GET LANGUAGE FILE:", e, "Error write language file resource  ", "");

        } catch (CantGetRepositoryPathRecordException e) {

            throw new CantGetLanguageFileException("CAN'T GET LANGUAGE FILE:", e, "Error get repository from database ", "");

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetLanguageFileException("CAN'T GET LANGUAGE FILE:", e, "Error created language file resource ", "");

        }


    }


    /**
     * <p>This method return a image file saved in device memory
     *
     * @param imageName Name of resource image file
     * @return byte image object
     * @throws CantGetResourcesException
     */


    @Override
    public byte[] getImageResource(String imageName, UUID skinId, String walletPublicKey) throws CantGetImageResourceException {
        try {

            //get repo from table
            Repository repository = networkServicesWalletResourcesDAO.getRepository(skinId);

            String reponame = repository.getPath() + walletPublicKey +"/"; //+"skins/"+repository.getSkinName()+"/";

            String filename = skinId.toString() + "_" + imageName;

            PluginBinaryFile imageFile;


            imageFile = pluginFileSystem.getBinaryFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            return imageFile.getContent();

        } catch (FileNotFoundException e) {
            throw new CantGetImageResourceException("CAN'T GET IMAGE RESOURCES:", e, "File Not Found image "+ imageName, "");
        } catch (CantCreateFileException e) {
            throw new CantGetImageResourceException("CAN'T GET IMAGE RESOURCES:", e, "cant created image " + imageName, "");
        }  catch (CantGetRepositoryPathRecordException e) {
            throw new CantGetImageResourceException("CAN'T GET IMAGE RESOURCES:", e, "Error get repository from database ", "");
        } catch (Exception e) {
            throw new CantGetImageResourceException("CAN'T GET IMAGE RESOURCES:", e, "unknown error image "+ imageName, "");
        }

    }

    @Override
    public byte[] getVideoResource(String videoName, UUID skinId) throws CantGetResourcesException {
        return new byte[0];
    }

    @Override
    public byte[] getSoundResource(String soundName, UUID skinId) throws CantGetResourcesException {
        return new byte[0];
    }

    @Override
    public String getFontStyle(String styleName, UUID skinId) {
        return "Method: getFontStyle - NO TIENE valor ASIGNADO para RETURN";
    }


    /**
     * <p>This method return a layout file saved in device memory
     *
     * @param layoutName Name of layout resource file
     * @return string layout object
     * @throws CantGetResourcesException
     */
    @Override
    public String getLayoutResource(String layoutName, ScreenOrientation orientation, UUID skinId,String walletPublicKey) throws CantGetResourcesException {


        try {
            //get repo name
            //get repo from table
            Repository repository = networkServicesWalletResourcesDAO.getRepository(skinId);

            String reponame = repository.getPath() + walletPublicKey +"/"; //+"skins/"+repository.getSkinName()+"/";

            String filename = skinId.toString() + "_" + layoutName;


            //reponame+="_"+orientation+"_"
            //get image from disk
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            return layoutFile.getContent();

        } catch (FileNotFoundException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET LAYOUT RESOURCES:", e, "Error write layout file resource  ", "");
        }  catch (CantGetRepositoryPathRecordException e) {
            throw new CantGetResourcesException("CAN'T GET LAYOUT RESOURCES:", e, "Error get repository from database ", "");

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET LAYOUT RESOURCES:", e, "Error created image file resource ", "");

        }


    }


    @Override
    public Language getLanguage(UUID skinId, String walletPublicKey,String languageName) throws CantGetLanguageFileException {
        String content = "";
        try {

            //get repo from table
            Repository repository = networkServicesWalletResourcesDAO.getRepository(skinId);

            String reponame = repository.getPath() +  "languages/" + walletPublicKey ; //+"skins/"+repository.getSkinName()+"/";

            String filename = skinId.toString() + "_" + languageName;

            //get Xml from disk
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();

            return null; //(Language) XMLParser.parseXML(content, new Language());

        } catch (FileNotFoundException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetLanguageFileException("CAN'T GET AppNavigationStructure:", e, "Error write layout file resource  ", "");
        }  catch (CantGetRepositoryPathRecordException e) {
            throw new CantGetLanguageFileException("CAN'T GET AppNavigationStructure:", e, "Error get repository from database ", "");

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetLanguageFileException("CAN'T GET AppNavigationStructure:", e, "Error created image file resource ", "");

        }
    }


    @Override
    public AppNavigationStructure getNavigationStructure(String walletPublicKey, UUID skinId) throws CantGetWalletNavigationStructureException {
        String content = "";
        try {

            //get repo from table
            Repository repository = networkServicesWalletResourcesDAO.getRepository(skinId);

            String reponame = repository.getPath() + walletPublicKey +"/"; //+"skins/"+repository.getSkinName()+"/";

            String filename = skinId.toString() + "_navigation_structure.xml";

            //get Xml from disk
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();

            return (AppNavigationStructure) XMLParser.parseXML(content, new AppNavigationStructure());

        } catch (FileNotFoundException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetWalletNavigationStructureException("CAN'T GET AppNavigationStructure:", e, "Error write layout file resource  ", "");
        }  catch (CantGetRepositoryPathRecordException e) {
            throw new CantGetWalletNavigationStructureException("CAN'T GET AppNavigationStructure:", e, "Error get repository from database ", "");

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetWalletNavigationStructureException("CAN'T GET AppNavigationStructure:", e, "Error created image file resource ", "");

        }
    }


    /**
     *   Private instances methods declarations.
     */



    private void UninstallWallet(String walletCategory,String walletType,String developer,String skinName,UUID skinId, String screenSize,String navigationStructureVersion,boolean isLastWallet) throws CantUninstallWallet {
        String linkToRepo = REPOSITORY_LINK + walletCategory + "/" + walletType + "/" + developer + "/";


        String linkToResources = linkToRepo + "skins/" + skinName + "/" + screenSize + "/";


        Skin skin = null;
        try {


            //skin = checkAndInstallSkinResources(linkToResources, LOCAL_STORAGE_PATH,walletPublicKey);


            networkServicesWalletResourcesDAO.delete(skin.getId(), linkToRepo);



            String linkToNavigationStructure = linkToRepo + "/versions/" + skin.getNavigationStructureCompatibility() + "/";

            /**
             *  delete navigation structure portrait
             */

            String navigationStructureName="navigation_structure.xml";
            deleteXML(navigationStructureName, skinId, linkToNavigationStructure);


            /**
             *  delete resources
             */

            /**
             * delete portrait resources
             */
            String linkToPortraitResources = linkToResources + "portrait/resources/";
            deleteResources(linkToPortraitResources, skin.getResources(), skin.getId());

            /**
             /**
             * delete layouts
             */
            String linkToPortraitLayouts = linkToResources + "/layouts";
            deleteLayouts(linkToPortraitLayouts, skin.getPortraitLayouts(), skin.getId());


        } catch (CantDeleteRepositoryException e) {
            throw new CantUninstallWallet("CAN'T UNINSTALL WALLET:", e, "Error Delete repository ", "");

        } catch (CantDeleteLayouts e) {
            throw new CantUninstallWallet("CAN'T UNINSTALL WALLET:", e, "Error Delete layouts ", "");
        } catch (CantDeleteResourcesFromDisk e) {
            throw new CantUninstallWallet("CAN'T UNINSTALL WALLET:", e, "Error Delete resources from disk ", "");
        } catch (CantDeleteXml e) {
            throw new CantUninstallWallet("CAN'T UNINSTALL WALLET:", e, "Error Delete xml ", "");

        }
    }


    private void downloadResourcesFromRepo(String linkToRepo, Skin skin, String localStoragePath,String screenSize,String walletPublicKey) throws  CantDownloadResourceFromRepo{
        try
        {
            /**
             * download portrait resources
             */
            String linkToResources = linkToRepo + "resources/mdpi/drawables/";
            // this will be used when the main repository be open source
            downloadResources(linkToResources, skin.getResources(), skin.getId(), localStoragePath + "/" + walletPublicKey );


            /**
             * download portrait layouts
             */
            String linkToPortraitLayouts = linkToRepo +screenSize+ "/portrait/layouts/";
            downloadLayouts(linkToPortraitLayouts, skin.getPortraitLayouts(), skin.getId(),localStoragePath,walletPublicKey);

            /**
             * download landscape layouts
             */
            //String linkToLandscapeLayouts = linkToRepo +screenSize+ "/landscape/layouts/";
            // downloadLayouts(linkToLandscapeLayouts, skin.getLandscapeLayouts(), skin.getId(),localStoragePath,walletPublicKey);


            //TODO: raise a event
            // fire event Wallet resource installed
            /*FermatEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_INSTALLED);
            ((WalletResourcesInstalledEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
            eventManager.raiseEvent(platformEvent);
            */
        }
        catch(CantDownloadResource e)
        {
            throw new CantDownloadResourceFromRepo("CAN'T DOWNLOAD RESOURCES FROM REPO", e, "Error downloadImages", "");
        }
        catch(Exception e)
        {
            throw new CantDownloadResourceFromRepo("CAN'T DOWNLOAD RESOURCES FROM REPO", e, "", "");
        }

    }

    private void downloadLanguageFromRepo(String linkToLanguage, UUID skinId,String languageName, String localStoragePath,String screenSize,String walletPublicKey) throws CantDownloadLanguageFromRepo {

        try
        {
            /**
             * download language
             */
            String linkToLanguageRepo = linkToLanguage+languageName+".xml";
            downloadLanguage(linkToLanguageRepo, languageName, skinId, localStoragePath,walletPublicKey);


            //TODO: raise a event
            // fire event Wallet resource installed
        /*FermatEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_INSTALLED);
        ((WalletResourcesInstalledEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);
        */

        }
        catch(CantDownloadLanguage e) {
            throw new CantDownloadLanguageFromRepo("CAN'T DOWNLOAD LANGUAGE FROM REPO", e, "Cant Save language", "");
        }
        catch(Exception e)
        {
            throw new CantDownloadLanguageFromRepo("CAN'T DOWNLOAD LANGUAGE FROM REPO", e, "Generic error", "");
        }

    }



    private void downloadResources(String link, Map<String, Resource> resourceMap, UUID skinId,String localStoragePath) throws CantDownloadResource {
        try {
            for (Map.Entry<String, Resource> entry : resourceMap.entrySet()) {


                switch (entry.getValue().getResourceType()) {
                    case IMAGE:
                        // this will be used when the main repository be open source
                        //byte[] image = getRepositoryImageFile(link, entry.getValue().getFileName());
                        // this is used because the main repository is private
                        byte[] image = getGitHubConnection().getImage(link + entry.getValue().getFileName());

                        //testing purpose
                        // imagenes.put(entry.getValue().getName(), image);

                        recordImageResource(image, entry.getKey(), skinId, localStoragePath);
                        break;
                    case SOUND:

                        break;
                    case VIDEO:
                        break;
                }

            }
        } catch (CantCheckResourcesException e) {
            throw new CantDownloadResource("CAN'T DOWNLOAD RESOURCES", e, "Error check resources", "");

        } catch (MalformedURLException e) {
            throw new CantDownloadResource("CAN'T DOWNLOAD RESOURCES", e, "Error get resources from github, mailformed url", "");
        } catch (IOException e) {
            throw new CantDownloadResource("CAN'T DOWNLOAD RESOURCES", e, "Error get resources from github, io exception", "");
        } catch (CantGetGitHubConnectionException e) {

            throw new CantDownloadResource("CAN'T DOWNLOAD REQUESTED RESOURCES", e, "Error downloading LAYOUTS.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantDownloadResource("CAN'T DOWNLOAD REQUESTED RESOURCES", e, "Error downloading LAYOUTS.", "Unhandled Exception.");
        }
    }

    private void deleteResources(String link, Map<String, Resource> resourceMap, UUID skinId) throws CantDeleteResourcesFromDisk {
        try {
            for (Map.Entry<String, Resource> entry : resourceMap.entrySet()) {


                switch (entry.getValue().getResourceType()) {
                    case IMAGE:
                        //testing purpose
                        deleteResource(entry.getKey(), skinId, link);
                        break;
                    case SOUND:

                        break;
                    case VIDEO:
                        break;
                }

            }
        } catch (CantDeleteResource e) {
            throw new CantDeleteResourcesFromDisk("CAN'T DELETE RESOURCES FROM DISK", e, "Error deleting resources", "");
        } catch (Exception e) {
            throw new CantDeleteResourcesFromDisk("CAN'T DELETE RESOURCES FROM DISK ", e, "Generic error", "");
        }
    }

    private void downloadLayouts(String link, Map<String, Layout> resourceMap, UUID skinId,String localStoragePath,String walletPublicKey) throws CantDownloadLayouts {
        try {
            for (Map.Entry<String, Layout> entry : resourceMap.entrySet()) {

                // this will be when the repository be open source
                //String layoutXML = getRepositoryStringFile(link, entry.getValue().getFilename());

                // this is because the main repository is private
                String layoutXML = getGitHubConnection().getFile(link + entry.getValue().getFilename());

                // layouts.put(entry.getValue().getName(), layoutXML);


                recordXML(layoutXML, entry.getKey(), skinId, localStoragePath, walletPublicKey);


            }
        } catch (CantCheckResourcesException e) {
            throw new CantDownloadLayouts("CAN'T DOWNLOAD LAYOUTS", e, "Error check resources", "");

        } catch (MalformedURLException e) {
            throw new CantDownloadLayouts("CAN'T DOWNLOAD RESOURCES", e, "Malformed url", "");

        } catch (IOException e) {
            throw new CantDownloadLayouts("CAN'T DOWNLOAD RESOURCES", e, "IOException", "");

        } catch (CantGetGitHubConnectionException e) {

            throw new CantDownloadLayouts("CAN'T DOWNLOAD REQUESTED RESOURCES", e, "Error downloading LAYOUTS.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantDownloadLayouts("CAN'T DOWNLOAD REQUESTED RESOURCES", e, "Error downloading LAYOUTS.", "Unhandled Exception.");
        }
    }

    private void downloadLanguage(String link,String languageName, UUID skinId,String localStoragePath,String walletPublicKey) throws CantDownloadLanguage {
        try {
            String languageXML = getGitHubConnection().getFile(link);

            recordXML(languageXML, languageName, skinId, localStoragePath,walletPublicKey);

        } catch (MalformedURLException e) {
            throw new CantDownloadLanguage("CAN'T DOWNLOAD RESOURCES", e, "MalformedURLException", "");
        } catch (IOException e) {
            throw new CantDownloadLanguage("CAN'T DOWNLOAD RESOURCES", e, "IOException", "");
        } catch (CantCheckResourcesException e) {
            throw new CantDownloadLanguage("CAN'T DOWNLOAD RESOURCES", e, "Error saving file", "");
        } catch (CantGetGitHubConnectionException e) {

            throw new CantDownloadLanguage("CAN'T DOWNLOAD REQUESTED RESOURCES", e, "Error downloading language.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantDownloadLanguage("CAN'T DOWNLOAD REQUESTED RESOURCES", e, "Error downloading language.", "Unhandled Exception.");
        }
    }

    private void deleteLayouts(String link, Map<String, Layout> resourceMap, UUID skinId) throws CantDeleteLayouts {
        try {
            for (Map.Entry<String, Layout> entry : resourceMap.entrySet()) {

                deleteResource(entry.getKey(), skinId, link);

            }

        } catch (CantDeleteResource e) {
            throw new CantDeleteLayouts("CAN'T DELETE LAYOUTS", e, "Error deleting file", "");
        } catch (Exception e) {
            throw new CantDeleteLayouts("CAN'T DELETE LAYOUTS", e, "Generic Error", "");
        }

    }


    private void downloadNavigationStructure(String link, UUID skinId,String localStoragePath,String walletPublicKey) throws CantDonwloadNavigationStructure {
        try {


            /**
             *  Download portrait navigation structure
             */
            String navigationStructureName="navigation_structure.xml";
            //this will be use when the main repository be open source
            //String navigationStructureXML = getRepositoryStringFile(link, navigationStructureName);

            // this is used because we have a private main repository
            String navigationStructureXML = getGitHubConnection().getFile(link + navigationStructureName);

            recordXML(navigationStructureXML,navigationStructureName,skinId,localStoragePath,walletPublicKey);

            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED);
            WalletNavigationStructureDownloadedEvent walletNavigationStructureDownloadedEvent=  (WalletNavigationStructureDownloadedEvent) fermatEvent;
            walletNavigationStructureDownloadedEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
            walletNavigationStructureDownloadedEvent.setFilename("navigation_structure.xml");
            walletNavigationStructureDownloadedEvent.setSkinId(skinId);
            walletNavigationStructureDownloadedEvent.setXmlText(navigationStructureXML);
            walletNavigationStructureDownloadedEvent.setLinkToRepo(localStoragePath);
            walletNavigationStructureDownloadedEvent.setWalletPublicKey(walletPublicKey);
            eventManager.raiseEvent(fermatEvent);

        } catch (CantCheckResourcesException e) {
            throw new CantDonwloadNavigationStructure("CAN'T DOWNLOAD RESOURCES", e, "Error save navigation Structure ", "");

        }
        catch (IOException e) {
            throw new CantDonwloadNavigationStructure("CAN'T DOWNLOAD RESOURCES", e, "Error get navigation Structure for github ", "");

        } catch (CantGetGitHubConnectionException e) {

            throw new CantDonwloadNavigationStructure("CAN'T DOWNLOAD REQUESTED RESOURCES", e, "Error downloading navigation Structure.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantDonwloadNavigationStructure("CAN'T DOWNLOAD REQUESTED RESOURCES", e, "Error downloading navigation Structure.", "Unhandled Exception.");
        }
    }

    private void recordImageResource(byte[] image, String name, UUID skinId, String reponame) throws CantCheckResourcesException {
        try {

            PluginBinaryFile imageFile;

            String filename = skinId.toString() + "_" + name;


            imageFile = pluginFileSystem.createBinaryFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
            imageFile.setContent(image);

            imageFile.persistToMedia();
        }
        catch (CantPersistFileException cantPersistFileException) {
            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantPersistFileException, "Error persist image file ", "");

        }

        catch (CantCreateFileException cantPersistFileException) {
            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantPersistFileException, "Error persist image file ", "");
        }


    }

    private void recordXML(String xml, String name, UUID skinId, String reponame,String publicKey) throws CantCheckResourcesException {
        try {
            PluginTextFile layoutFile;

            String filename = skinId.toString() + "_" + name;

            reponame= reponame + publicKey + "/";

            layoutFile = pluginFileSystem.createTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
            layoutFile.setContent(xml);
            layoutFile.persistToMedia();

        } catch (CantPersistFileException cantPersistFileException) {
            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantPersistFileException, "Error persist image file ", "");

        } catch (CantCreateFileException cantCreateFileException) {
            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantCreateFileException, "Error creating image file ", "");
        }


    }



    private void deleteXML( String name, UUID skinId, String reponame) throws CantDeleteXml {

        String filename = skinId.toString() + "_" + name;

        try {

            pluginFileSystem.deleteTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        } catch (CantCreateFileException e) {
            throw new CantDeleteXml("CAN'T DELETE SKIN XML", e, "Error deleting file " + filename, "");

        } catch (FileNotFoundException e) {
            throw new CantDeleteXml("CAN'T DELETE SKIN XML", e, "File not found " + filename, "");

        }

    }

    private void deleteResource(String name, UUID skinId, String reponame) throws CantDeleteResource {

        String filename = skinId.toString() + "_" + name;

        try {

            pluginFileSystem.deleteTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        } catch (CantCreateFileException e) {
            throw new CantDeleteResource("CAN'T DELETE RESOURCE", e, "cant delete file " + filename, "");
        } catch (FileNotFoundException e) {
            throw new CantDeleteResource("CAN'T DELETE RESOURCE", e, "File not found " + filename, "");
        }

    }

    private Skin checkAndInstallSkinResources(String linkToSkin,String localStoragePath,String walletPublicKey) throws CantCheckResourcesException {
        String repoManifest = "";
        String skinFilename = "skin.xml";
        try {
            //connect to repo and get skin file
            // this will work when we have open source repository

            //repoManifest = getRepositoryStringFile(linkToSkin, skinFilename);

            //this work only for the private repository
            repoManifest = getGitHubConnection().getFile(linkToSkin + skinFilename);


            Skin skin = new Skin();
            skin = (Skin) XMLParser.parseXML(repoManifest, skin);

            /**
             *  Skin record
             */
            recordXML(repoManifest, skin.getName(), skin.getId(), localStoragePath, walletPublicKey);

            return skin;

        } catch (MalformedURLException e) {

            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", e, "Http error in connection with the repository to load manifest file", "");

        } catch (IOException e) {

            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", e, "Error load manifest file ", "Repository not exist or manifest file not exist");

        } catch (CantGetGitHubConnectionException e) {

            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", e, "Error checking resources.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", e, "Error checking resources.", "Unhandled Exception.");
        }
    }

    private void addProgress(InstalationProgress instalationProgress){
        this.instalationProgress = instalationProgress;
    }

    @Override
    public Language getLanguage(UUID languageId, String walletPublicKey) throws CantGetLanguageFileException {
        return null;
    }


}