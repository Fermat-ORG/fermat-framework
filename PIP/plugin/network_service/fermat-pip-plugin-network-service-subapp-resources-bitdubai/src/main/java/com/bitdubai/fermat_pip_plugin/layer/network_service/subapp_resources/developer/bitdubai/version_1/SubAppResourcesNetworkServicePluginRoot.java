package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
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
import com.bitdubai.fermat_pip_api.layer.network_service.CantCheckResourcesException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesInstalationManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantInstallCompleteSubAppResourcesException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantInstallSubAppLanguageException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantInstallSubAppSkinException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantUninstallCompleteSubAppException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantUninstallSubAppLanguageException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantUninstallSubAppSkinException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.database.SubAppResourcesInstallationNetworkServiceDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.database.SubAppResourcesNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.estructure.Repository;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.event_handlers.BegunSubAppInstallationEventHandler;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDeleteLayouts;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDeleteRepositoryException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDeleteResource;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDeleteResourcesFromDisk;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDeleteXml;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDonwloadNavigationStructure;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDownloadLanguage;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDownloadLanguageFromRepo;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDownloadLayouts;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDownloadResource;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantDownloadResourceFromRepo;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantGetGitHubConnectionException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantGetRepositoryPathRecordException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServicesSubAppResourcesDatabaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantUninstallSubApp;
import com.bitdubai.fermat_wpd_api.all_definition.enums.EventType;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.exceptions.CantCreateRepositoryException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This plugin is designed to look up for the resources needed by a newly installed wallet. We are talking about the
 * navigation structure, plus the images needed by the wallet to be able to run.
 * <p/>
 * It will try to gather those resources from other peers or a centralized location provided by the wallet developer
 * if it is not possible.
 * <p/>
 * It will also serve other peers with these resources when needed.
 * <p/>
 *
 * Created by Matias Furszyfer on 17/02/15.
 */
public class SubAppResourcesNetworkServicePluginRoot extends AbstractPlugin implements SubAppResourcesInstalationManager, SubAppResourcesProviderManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM      , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM      , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API   , layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API   , layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    /**
     * Dealing with the repository database
     */
    SubAppResourcesInstallationNetworkServiceDAO subAppResourcesDAO;

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    public SubAppResourcesNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    // TODO USE ERROR MANAGER PLEASE.
    // TODO USE ERROR MANAGER PLEASE.
    // TODO USE ERROR MANAGER PLEASE.
    // TODO USE ERROR MANAGER PLEASE.

    /**
     * Installed skins repositories
     * <p/>
     * SkinId, repository link
     */
    //private Map<UUID, Repository> repositoriesName;


    private String REPOSITORY_LINK = "https://raw.githubusercontent.com/bitDubai/fermat/master/seed-resources/subApp_resources/";


    private final String LOCAL_STORAGE_PATH = "subApp_resources/";


    /**
     * Wallet installation progress
     */
    private InstalationProgress instalationProgress;

    /**
     * Github connection until the main repository be open source
     */
    private GitHubConnection gitHubConnection;

    /**
     * Service Interface implementation.
     */

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

    @Override
    public void start() throws CantStartPluginException {

        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */

            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            fermatEventListener = eventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION);
            fermatEventHandler = new BegunSubAppInstallationEventHandler();
            ((BegunSubAppInstallationEventHandler) fermatEventHandler).setSubAppResourcesManager(this);
            fermatEventListener.setEventHandler(fermatEventHandler);
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);


            /**
             *  Initialize database clases
             */
            subAppResourcesDAO = new SubAppResourcesInstallationNetworkServiceDAO(pluginDatabaseSystem);
            subAppResourcesDAO.initializeDatabase(pluginId, SubAppResourcesNetworkServiceDatabaseConstants.DATABASE_NAME);

            /**
             *  Connect with main repository
             */


            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantInitializeNetworkServicesSubAppResourcesDatabaseException e) {

            throw new CantStartPluginException(e, null, "Error init plugin data base");

        } catch (Exception e) {

            throw new CantStartPluginException(e, null, "Unhandled Exception.");

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
     * SubAppResourcesInstallationManager Implementation
     */
    @Override
    public void installCompleteSubApp(String subAppType,
                                      String developer,
                                      String screenSize,
                                      String skinName,
                                      String languageName,
                                      String navigationStructureVersion,
                                      String subAppPublickey) throws CantInstallCompleteSubAppResourcesException {

        // this will be use when the repository be open source
        //String linkToRepo = REPOSITORY_LINK + walletCategory + "/" + walletType + "/" + developer + "/";

        String linkToRepo = "seed-resources/subApp_resources/" + developer+ "/"+ subAppType+"/";

        String linkToResources = linkToRepo + "skins/" + skinName + "/";


        String localStoragePath=this.LOCAL_STORAGE_PATH + developer  + "/" + subAppType + "/"+ "skins/" + skinName + "/" + screenSize + "/";

        Skin skin;

        /**
         * add progress
         */
        addProgress(InstalationProgress.INSTALATION_START);

        try {

            String linkToSkinFile= linkToResources + screenSize +"/";
            skin = checkAndInstallSkinResources(linkToSkinFile, localStoragePath,subAppPublickey);


            Repository repository = new Repository(skinName, navigationStructureVersion, localStoragePath);

            /**
             *  Save skin on Database
             */
            subAppResourcesDAO.createRepository(repository, skin.getId());

            /**
             *  download navigation structure
             */

            String linkToNavigationStructure = linkToRepo + "navigation_structure/" + skin.getNavigationStructureCompatibility() + "/";
            downloadNavigationStructure(linkToNavigationStructure, skin.getId(), localStoragePath,subAppPublickey);

            /**
             *  download resources
             */

            downloadResourcesFromRepo(linkToResources, skin, localStoragePath, screenSize, subAppPublickey);

            /**
             *  download language
             */
            String linkToLanguage = linkToRepo + "languages/";
            downloadLanguageFromRepo(linkToLanguage, skin.getId(),languageName, localStoragePath + "languages/", screenSize,subAppPublickey);

        } catch (CantDonwloadNavigationStructure e) {
            throw new CantInstallCompleteSubAppResourcesException("CAN'T INSTALL SUBAPP RESOURCES",e,"Error download navigation structure","");

        } catch (CantDownloadResourceFromRepo e) {
            throw new CantInstallCompleteSubAppResourcesException("CAN'T INSTALL SUBAPP RESOURCES",e,"Error download Resource fro repo","");
        } catch (CantDownloadLanguageFromRepo e) {
            throw new CantInstallCompleteSubAppResourcesException("CAN'T INSTALL SUBAPP RESOURCES",e,"Error download language from repo","");

        } catch (CantCreateRepositoryException e) {
            throw new CantInstallCompleteSubAppResourcesException("CAN'T INSTALL SUBAPP RESOURCES",e,"Error created repository on database","");

        } catch (CantCheckResourcesException cantCheckResourcesException) {
            throw new CantInstallCompleteSubAppResourcesException("CAN'T INSTALL SUBAPP RESOURCES",cantCheckResourcesException,"Error in skin.mxl file","");
        }

        //installSkinResource("null");
    }

    /**
     * @param subAppType
     * @param developer
     * @param screenSize
     * @param skinName
     * @param navigationStructureVersion
     * @param subAppPublicKey
     * @throws CantInstallSubAppSkinException
     */
    @Override
    public void installSkinForSubApp(String subAppType,
                                     String developer,
                                     String screenSize,
                                     String skinName,
                                     String navigationStructureVersion,
                                     String subAppPublicKey) throws CantInstallSubAppSkinException {
        try {
            String linkToRepo = "seed-resources/subApp_resources/" + developer  + "/" + subAppType + "/";

            String linkToResources = linkToRepo + "skins/" + skinName + "/";


            String localStoragePath = this.LOCAL_STORAGE_PATH + developer +  "/" + subAppType + "/" + "skins/" + skinName + "/" + screenSize + "/";

            Skin skin;

            /**
             * add progress
             */
            addProgress(InstalationProgress.INSTALATION_START);


            String linkToSkinFile = linkToResources + screenSize + "/";
            skin = checkAndInstallSkinResources(linkToSkinFile, localStoragePath, subAppPublicKey);


            Repository repository = new Repository(skinName, navigationStructureVersion, localStoragePath);

           // SubAppResourcesInstallationNetworkServiceDAO subAppResourcesDAO = new SubAppResourcesInstallationNetworkServiceDAO(pluginDatabaseSystem);


            subAppResourcesDAO.createRepository(repository, skin.getId());


            /**
             *  download resources
             */

            downloadResourcesFromRepo(linkToResources, skin, localStoragePath, screenSize, subAppPublicKey);

        } catch (CantCreateRepositoryException e) {
            throw new CantInstallSubAppSkinException("CAN'T INSTALL WALLET RESOURCES", e, "Error save skin on data base", "");

        } catch (CantCheckResourcesException cantCheckResourcesException) {
            throw new CantInstallSubAppSkinException("CAN'T INSTALL WALLET RESOURCES", cantCheckResourcesException, "Error check exception", "");
        } catch (CantDownloadResourceFromRepo cantDownloadResourceFromRepo) {
            throw new CantInstallSubAppSkinException("CAN'T INSTALL WALLET RESOURCES", cantDownloadResourceFromRepo, "Error download resources", "");

        }
    }
    /**
     * @param subAppType
     * @param developer
     * @param screenSize
     * @param skinId
     * @param languageName
     * @param subAppPublicKey
     * @throws CantInstallSubAppLanguageException
     */
    @Override
    public void installLanguageForSubApp(String subAppType,
                                         String developer,
                                         String screenSize,
                                         UUID skinId,
                                         String languageName,
                                         String subAppPublicKey) throws CantInstallSubAppLanguageException {
        try {
            String linkToRepo = "seed-resources/subApp_resources/" + developer + "/" + subAppType + "/";

            Repository repository = subAppResourcesDAO.getRepository(skinId);

            /**
             *  download language
             */
            String linkToLanguage = linkToRepo + "languages/";
            downloadLanguageFromRepo(linkToLanguage, skinId, languageName, repository.getPath() + "languages/", screenSize, subAppPublicKey);

            /**
             *  Fire event Wallet language installed
             */

        /*FermatEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_UNINSTALLED);
        WalletUninstalledEvent walletUninstalledEvent=  (WalletUninstalledEvent) platformEvent;
        walletUninstalledEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);*/
        }
        catch(CantDownloadLanguageFromRepo e) {
            throw new CantInstallSubAppLanguageException("CAN'T INSTALL WALLET LANGUAGE:", e, "Error download language ", "");
        }
        catch(Exception e)
        {
            throw new CantInstallSubAppLanguageException("CAN'T INSTALL WALLET LANGUAGE:", e, "unknown Error ", "");
        }

    }

    /**
     * @param subAppType
     * @param developer
     * @param subAppName
     * @param skinId
     * @param screenSize
     * @param navigationStructureVersion
     * @param isLastWallet
     * @param subAppPublicKey
     */
    @Override
    public void uninstallCompleteSubApp(String subAppType,
                                        String developer,
                                        String subAppName,
                                        UUID skinId,
                                        String screenSize,
                                        String navigationStructureVersion,
                                        boolean isLastWallet,
                                        String subAppPublicKey)  throws CantUninstallCompleteSubAppException {
        try
        {
            if(isLastWallet){

                UninstallSubApp(subAppType, developer, subAppName, skinId, screenSize, navigationStructureVersion, isLastWallet);

            }

            /**
             *  Fire event Wallet resource installed
             */

           /* FermatEvent fermatEvent = eventManager.getNewEvent(EventType.WALLET_UNINSTALLED);
            WalletUninstalledEvent walletUninstalledEvent=  (WalletUninstalledEvent) fermatEvent;
            walletUninstalledEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
            eventManager.raiseEvent(fermatEvent);*/
        }
        catch(CantUninstallSubApp e) {
            throw new CantUninstallCompleteSubAppException("CAN'T UNINSTALL COMPLETE WALLET:", e, "Error delete subApp resource ", "");
        }
        catch(Exception e)
        {
            throw new CantUninstallCompleteSubAppException("CAN'T UNINSTALL COMPLETE WALLET:", e, "unknown Error ", "");
        }
    }

    /**
     *
     * @param skinId
     * @param subAppPublicKey
     * @throws CantUninstallSubAppSkinException
     */
    @Override
    public void uninstallSkinForSubApp(UUID skinId,String subAppPublicKey) throws CantUninstallSubAppSkinException {
        try {


            Repository repository = subAppResourcesDAO.getRepository(skinId);
            String linkToRepo = "seed-resources/";

            String repoManifest = getGitHubConnection().getFile(linkToRepo + repository.getPath() + "skin.xml");

            Skin skin = new Skin();
            skin = (Skin) XMLParser.parseXML(repoManifest, skin);


            /**
             *  delete skin resources
             */

            subAppResourcesDAO.delete(skinId, repository.getSkinName());

            deleteResources(repository.getPath(),skin.getResources(),skinId);

        } catch (CantGetRepositoryPathRecordException e) {

            throw new CantUninstallSubAppSkinException("CAN'T UNINSTALL SUBAPP SKIN",e,"Error get skin on data base","");

        }catch (CantDeleteRepositoryException cantCheckResourcesException){

            throw new CantUninstallSubAppSkinException("CAN'T UNINSTALL SUBAPP SKIN",cantCheckResourcesException,"Error delete repository exception","");

        }  catch (CantDeleteResourcesFromDisk cantDownloadResourceFromRepo) {

            throw new CantUninstallSubAppSkinException("CAN'T UNINSTALL SUBAPP SKIN", cantDownloadResourceFromRepo, "Error delete resources", "");
        } catch (IOException e) {

            throw new CantUninstallSubAppSkinException("CAN'T UNINSTALL SUBAPP SKIN", e, "Skin file not found on github", "");

        } catch (CantGetGitHubConnectionException e) {

            throw new CantUninstallSubAppSkinException("CAN'T UNINSTALL SUBAPP SKIN", e, "Error downloading resources.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantUninstallSubAppSkinException("CAN'T UNINSTALL SUBAPP SKIN", e, "Error downloading resources.", "Unhandled Exception.");
        }
    }

    /**
     *
     * @param skinId
     * @param languageName
     * @param subAppPublicKey
     */
    @Override
    public void uninstallLanguageForSubApp(UUID skinId, String languageName,  String subAppPublicKey) throws CantUninstallSubAppLanguageException {
        try {

            //get repo from table
            Repository repository = subAppResourcesDAO.getRepository(skinId);
            //get image from disk
            PluginTextFile layoutFile;


            String reponame = repository.getPath() + subAppPublicKey + "/languages/";

            languageName = skinId.toString() + "_" + languageName;


            pluginFileSystem.deleteTextFile(pluginId, reponame, languageName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            /**
             *  Fire event Wallet language installed
             */

        /*FermatEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_UNINSTALLED);
        WalletUninstalledEvent walletUninstalledEvent=  (WalletUninstalledEvent) platformEvent;
        walletUninstalledEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);*/
        }
        catch(CantGetRepositoryPathRecordException e) {
            throw new CantUninstallSubAppLanguageException("CAN'T UNINSTALL SUBAPP LANGUAGE:", e, "Error get repository on database ", "");
        }
        catch(CantCreateFileException e) {
            throw new CantUninstallSubAppLanguageException("CAN'T UNINSTALL SUBAPP LANGUAGE:", e, "Error delete language file ", "");
        }
        catch(FileNotFoundException e) {
            throw new CantUninstallSubAppLanguageException("CAN'T UNINSTALL SUBAPP LANGUAGE:", e, "Error language file not found ", "");
        }
        catch(Exception e)
        {
            throw new CantUninstallSubAppLanguageException("CAN'T UNINSTALL SUBAPP LANGUAGE:", e, "unknown Error ", "");
        }
    }

    /**
     * Get enum instalation progress
     *
     * @return
     */
    @Override
    public InstalationProgress getInstallationProgress(){
        return instalationProgress;
    }


    /**
     * SubAppResourcesProvider Implementation
     */


    /**
     * This method returns the resourcesId
     *
     * @return the Id of resources being represented
     */
    @Override
    public UUID getResourcesId() {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * This method let us get an skin file referenced by its id
     * @param skinId
     * @param walletPublicKey
     * @return
     * @throws CantGetSkinFileException
     * @throws CantGetResourcesException
     */
    @Override
    public Skin getSkinFile( UUID skinId,String walletPublicKey) throws CantGetSkinFileException, CantGetResourcesException {
        String content = "";
        try {
            //get repo from table
            Repository repository = subAppResourcesDAO.getRepository(skinId);
            //get image from disk
            PluginTextFile layoutFile;

            String reponame = repository.getPath() + walletPublicKey +"/";

            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, skinId.toString() + "_" + repository.getSkinName() , FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();

        } catch (CantGetRepositoryPathRecordException e) {

            throw new CantGetResourcesException("CAN'T GET REQUESTED RESOURCES:", e, "Error getting repository fro database", "");

        } catch (FileNotFoundException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET REQUESTED RESOURCES:", e, "Error write layout file resource  ", "");

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET REQUESTED RESOURCES:", e, "Error created image file resource ", "");

        }

        return (Skin) XMLParser.parseXML(content, new Skin());
    }

    /**
     * This method let us get a language file referenced by a name
     *
     * @param fileName the name of the Language file (without the path structure).
     * @return The content of the file
     * @throws CantGetLanguageFileException
     */
    @Override
    public String getLanguageFile(UUID skinId,String walletPublicKey,String fileName) throws CantGetLanguageFileException {
        try {
            //get repo from table
            Repository repository = subAppResourcesDAO.getRepository(skinId);
            //get image from disk
            PluginTextFile layoutFile;


            String reponame = repository.getPath() + walletPublicKey + "languages/";

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
     * This method let us get an image referenced by a name
     *
     * @param imageName the name of the image resource found in the skin file
     * @param skinId
     * @return the image represented as a byte array
     * @throws CantGetResourcesException
     */
    @Override
    public byte[] getImageResource(String imageName, UUID skinId,String walletPublicKey) throws CantGetImageResourceException {
        try {

            //get repo from table
            Repository repository = subAppResourcesDAO.getRepository(skinId);

            PluginBinaryFile imageFile;
            // String localStoragePath=this.LOCAL_STORAGE_PATH +developer+"/"+walletCategory + "/" + walletType + "/"+ "skins/" + imageName + "/" + screenSize + "/";

            String filename= skinId.toString()+"_"+imageName;

            String path = repository.getPath()+"skins/"+repository.getSkinName()+"/";

            imageFile = pluginFileSystem.getBinaryFile(pluginId, path, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            return imageFile.getContent();

        } catch (FileNotFoundException e) {
            throw new CantGetImageResourceException("CAN'T GET IMAGE RESOURCES:", e, "File Not Found image "+ imageName, "");
        } catch (CantCreateFileException e) {
            throw new CantGetImageResourceException("CAN'T GET IMAGE RESOURCES:", e, "cant created image "+ imageName, "");
        } catch (Exception e) {
            throw new CantGetImageResourceException("CAN'T GET IMAGE RESOURCES:", e, "unknown error image "+ imageName, "");
        }

    }

    /**
     * This method let us get a video referenced by a name
     *
     * @param videoName the name of the video resource found in the skin file
     * @param skinId
     * @return the video represented as a byte array
     * @throws CantGetResourcesException
     */
    @Override
    public byte[] getVideoResource(String videoName, UUID skinId) throws CantGetResourcesException {
        return new byte[0];
    }

    /**
     * This method let us get a sound referenced by a name
     *
     * @param soundName the name of the sound resource found in the skin file
     * @param skinId
     * @return the sound represented as a byte array
     * @throws CantGetResourcesException
     */
    @Override
    public byte[] getSoundResource(String soundName, UUID skinId) throws CantGetResourcesException {
        return new byte[0];
    }

    /**
     * This method let us get a font style referenced by a name
     *
     * @param styleName the name of the font style resource found in the skin file
     * @param skinId
     * @return the font style represented as the content of a ttf file
     * @throws CantGetResourcesException
     */
    @Override
    public String getFontStyle(String styleName, UUID skinId) {
        return null;
    }


    /**
     * <p>This method return a layout file saved in device memory
     *
     * @param layoutName Name of layout resource file
     * @return string layout object
     * @throws CantGetResourcesException
     */
    @Override
    public String getLayoutResource(String layoutName, ScreenOrientation orientation, UUID skinId,String subAppType) throws CantGetResourcesException {


        String content = "";
        try {
            //get repo from table
            Repository repository = subAppResourcesDAO.getRepository(skinId);

            //String localStoragePath=this.LOCAL_STORAGE_PATH +developer+"/"+walletCategory + "/" + walletType + "/"+ "skins/" + layoutName + "/" + screenSize + "/";

            String reponame = repository.getPath()+"skins/"+repository.getSkinName()+"/";

            String filename = skinId.toString() + "_" + layoutName;


            //reponame+="_"+orientation+"_"
            //get image from disk
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();

        } catch (CantGetRepositoryPathRecordException e) {

            throw new CantGetResourcesException("CAN'T GET REQUESTED RESOURCES:", e, "Error getting repository fro database", "");
        } catch (FileNotFoundException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET REQUESTED RESOURCES:", e, "Error write layout file resource  ", "");

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET REQUESTED RESOURCES:", e, "Error created image file resource ", "");

        }

        return content;
    }



    /**
     *   Private instances methods declarations.
     */


    private void UninstallSubApp(String subAppType,String developer,String skinName,UUID skinId, String screenSize,String navigationStructureVersion,boolean isLastSubApp) throws CantUninstallSubApp {

        String linkToRepo = REPOSITORY_LINK +  subAppType + "/" + developer + "/";


        String linkToResources = linkToRepo + "skins/" + skinName + "/" + screenSize + "/";


        Skin skin = null;
        try {


            //skin = checkAndInstallSkinResources(linkToResources, LOCAL_STORAGE_PATH,walletPublicKey);


            subAppResourcesDAO.delete(skin.getId(), linkToRepo);



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
            throw new CantUninstallSubApp("CAN'T UNINSTALL WALLET:", e, "Error Delete repository ", "");

        } catch (CantDeleteLayouts e) {
            throw new CantUninstallSubApp("CAN'T UNINSTALL WALLET:", e, "Error Delete layouts ", "");
        } catch (CantDeleteResourcesFromDisk e) {
            throw new CantUninstallSubApp("CAN'T UNINSTALL WALLET:", e, "Error Delete resources from disk ", "");
        } catch (CantDeleteXml e) {
            throw new CantUninstallSubApp("CAN'T UNINSTALL WALLET:", e, "Error Delete xml ", "");

        }
    }


    private void downloadResourcesFromRepo(String linkToRepo, Skin skin, String localStoragePath,String screenSize,String subAppType) throws  CantDownloadResourceFromRepo{
        try
        {
            /**
             * download portrait resources
             */
            String linkToResources = linkToRepo + "resources/mdpi/drawables/";
            // this will be used when the main repository be open source
            downloadResources(linkToResources, skin.getResources(), skin.getId(), localStoragePath);


            /**
             * download portrait layouts
             */
            String linkToPortraitLayouts = linkToRepo +screenSize+ "/portrait/layouts/";
            downloadLayouts(linkToPortraitLayouts, skin.getPortraitLayouts(), skin.getId(), localStoragePath, subAppType);

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

    private void downloadLanguageFromRepo(String linkToLanguage, UUID skinId,String languageName, String localStoragePath,String screenSize,String subAppType) throws CantDownloadLanguageFromRepo {

        try
        {
            /**
             * download language
             */
            String linkToLanguageRepo = linkToLanguage+languageName+".xml";
            downloadLanguage(linkToLanguageRepo, languageName, skinId, localStoragePath,subAppType);


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

            throw new CantDownloadResource("CAN'T DOWNLOAD RESOURCES", e, "Error downloading resources.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantDownloadResource("CAN'T DOWNLOAD RESOURCES", e, "Error downloading resources.", "Unhandled Exception.");
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


    private void downloadLayouts(String link, Map<String, Layout> resourceMap, UUID skinId,String localStoragePath,String subAppType) throws CantDownloadLayouts {
        try {
            for (Map.Entry<String, Layout> entry : resourceMap.entrySet()) {

                // this will be when the repository be open source
                //String layoutXML = getRepositoryStringFile(link, entry.getValue().getFilename());

                // this is because the main repository is private
                String layoutXML = getGitHubConnection().getFile(link + entry.getValue().getFilename());

                // layouts.put(entry.getValue().getName(), layoutXML);


                recordXML(layoutXML, entry.getKey(), skinId, localStoragePath, subAppType);


            }
        } catch (CantCheckResourcesException e) {
            throw new CantDownloadLayouts("CAN'T DOWNLOAD LAYOUTS", e, "Error check resources", "");

        } catch (MalformedURLException e) {
            throw new CantDownloadLayouts("CAN'T DOWNLOAD LAYOUTS", e, "Malformed url", "");

        } catch (IOException e) {
            throw new CantDownloadLayouts("CAN'T DOWNLOAD LAYOUTS", e, "IOException", "");

        } catch (CantGetGitHubConnectionException e) {

            throw new CantDownloadLayouts("CAN'T DOWNLOAD LAYOUTS", e, "Error downloading layouts.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantDownloadLayouts("CAN'T DOWNLOAD LAYOUTS", e, "Error downloading layouts.", "Unhandled Exception.");
        }
    }

    /**
     * Through the method <code>downloadLanguage</code> TODO COMPLETE
     *
     * @param link TODO COMPLETE
     * @param languageName
     * @param skinId
     * @param localStoragePath
     * @param subAppType
     *
     * @throws CantDownloadLanguage if something goes wrong.
     */
    private void downloadLanguage(final String link            ,
                                  final String languageName    ,
                                  final UUID   skinId          ,
                                  final String localStoragePath,
                                  final String subAppType      ) throws CantDownloadLanguage {

        try {

            String languageXML = getGitHubConnection().getFile(link);

            recordXML(languageXML, languageName, skinId, localStoragePath,subAppType);

        } catch (MalformedURLException e) {

            throw new CantDownloadLanguage("CAN'T DOWNLOAD RESOURCES", e, "MalformedURLException", "");
        } catch (IOException e) {

            throw new CantDownloadLanguage("CAN'T DOWNLOAD RESOURCES", e, "IOException", "");
        } catch (CantCheckResourcesException e) {

            throw new CantDownloadLanguage("CAN'T DOWNLOAD RESOURCES", e, "Can't check resources.", "");
        } catch (CantGetGitHubConnectionException e) {

            throw new CantDownloadLanguage("CAN'T DOWNLOAD RESOURCES", e, "Error downloading navigation structure.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantDownloadLanguage("CAN'T DOWNLOAD RESOURCES", e, "Error downloading navigation structure.", "Unhandled Exception.");
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

            //TODO: este evento tiene que ser creado igual que se encuentra pero para el subAppRuntime

//            FermatEvent fermatEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED);
//            WalletNavigationStructureDownloadedEvent walletNavigationStructureDownloadedEvent=  (WalletNavigationStructureDownloadedEvent) fermatEvent;
//            walletNavigationStructureDownloadedEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
//            walletNavigationStructureDownloadedEvent.setFilename("navigation_structure.xml");
//            walletNavigationStructureDownloadedEvent.setSkinId(skinId);
//            walletNavigationStructureDownloadedEvent.setXmlText(navigationStructureXML);
//            walletNavigationStructureDownloadedEvent.setLinkToRepo(localStoragePath);
//            walletNavigationStructureDownloadedEvent.setWalletPublicKey(walletPublicKey);
//            eventManager.raiseEvent(fermatEvent);

        } catch (CantCheckResourcesException e) {
            throw new CantDonwloadNavigationStructure("CAN'T DOWNLOAD RESOURCES", e, "Error save navigation Structure ", "");

        }
        catch (IOException e) {
            throw new CantDonwloadNavigationStructure("CAN'T DOWNLOAD RESOURCES", e, "Error get navigation Structure for github ", "");

        } catch (CantGetGitHubConnectionException e) {

            throw new CantDonwloadNavigationStructure("CAN'T DOWNLOAD RESOURCES", e, "Error downloading navigation structure.", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantDonwloadNavigationStructure("CAN'T DOWNLOAD RESOURCES", e, "Error downloading navigation structure.", "Unhandled Exception.");
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

            reponame+=publicKey+"";

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
            Object object;
            object=XMLParser.parseXML(repoManifest, skin);
            skin= (Skin) object;
            //skin = (Skin) XMLParser.parseXML(repoManifest, skin);

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

            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", e, "Error load manifest file ", "Can't connect with github.");

        } catch (Exception e) {

            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", e, "Error load manifest file ", "Unhandled Exception.");

        }
    }

    private void addProgress(InstalationProgress instalationProgress){
        this.instalationProgress = instalationProgress;
    }
}
