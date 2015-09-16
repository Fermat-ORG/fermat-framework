package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.github.GithubConnection;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Layout;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenOrientation;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.InstalationProgress;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesInstalationManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetSkinFileException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.WalletResourcesUnninstallException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteRepositoryException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkServicesWalletResourcesDAO;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkserviceswalletresourcesDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.Repository;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.RepositoryNotFoundException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.event_handlers.BegunWalletInstallationEventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletNavigationStructureDownloadedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletUninstalledEvent;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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


public class WalletResourcesNetworkServicePluginRoot implements Service, NetworkService, WalletResourcesInstalationManager, WalletResourcesProviderManager, DealsWithPluginDatabaseSystem, DealsWithEvents, DealsWithErrors, DealsWithLogger, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin {


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    /**
     * Dealing with the repository database
     */
    NetworkServicesWalletResourcesDAO networkServicesWalletResourcesDAO;

    /**
     * Installed skins repositories
     * <p>
     * SkinId, repository link
     */
    private Map<UUID, Repository> repositoriesName;


    private String REPOSITORY_LINK = "https://raw.githubusercontent.com/bitDubai/fermat-wallet-resources/master/";


    private final String LOCAL_STORAGE_PATH="wallet-resources/";


    /**
     *  Wallet instalation progress
     */
    private InstalationProgress instalationProgress;;

    //para testear
    private Map<String, byte[]> imagenes;


    /**
     * Github connection until the main repository be open source
     */
    private GithubConnection githubConnection;

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {

        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            setUp();
            EventListener eventListener;
            EventHandler eventHandler;

            eventListener = eventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION);
            eventHandler = new BegunWalletInstallationEventHandler();
            ((BegunWalletInstallationEventHandler) eventHandler).setWalletResourcesInstalationManager(this);
            eventListener.setEventHandler(eventHandler);
            eventManager.addListener(eventListener);
            listenersAdded.add(eventListener);

            /**
             *  Create repository in database
             */
            networkServicesWalletResourcesDAO = new NetworkServicesWalletResourcesDAO(pluginDatabaseSystem);
            networkServicesWalletResourcesDAO.initializeDatabase(pluginId, NetworkserviceswalletresourcesDatabaseConstants.DATABASE_NAME);

            /**
             *  Connect with main repository
             */
            githubConnection = new GithubConnection();



            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUp() {
        repositoriesName = new HashMap<UUID, Repository>();

        //for testing purpose
        imagenes = new HashMap<String, byte[]>();
    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {


        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * NetworkService Interface implementation.
     */

    @Override
    public UUID getId() {
        return pluginId;
    }

    /**
     * Dealing with plugin database system
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
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


        String localStoragePath=this.LOCAL_STORAGE_PATH +developer+"/"+walletCategory + "/" + walletType + "/"+ "skins/" + skinName + "/" + screenSize + "/";

        Skin skin = null;

        /**
         * add progress
         */
        addProgress(InstalationProgress.INSTALATION_START);

        try {

            String linkToSkinFile=linkToResources+screenSize+"/";
            skin = checkAndInstallSkinResources(linkToSkinFile, localStoragePath);


            Repository repository = new Repository(skinName, navigationStructureVersion, localStoragePath);

            /**
             *  Save repository in memory for use
             */
            repositoriesName.put(skin.getId(), repository);


            /*NetworkServicesWalletResourcesDAO networkServicesWalletResourcesDAO = new NetworkServicesWalletResourcesDAO(pluginDatabaseSystem);

            try {

                networkServicesWalletResourcesDAO.createRepository(repository, skin.getId());

            } catch (CantCreateRepositoryException e) {
                e.printStackTrace();
            }
            */


            /**
             *  download navigation structure
             */

            String linkToNavigationStructure = linkToRepo + "navigation_structure/" + skin.getNavigationStructureCompatibility() + "/";
            donwloadNavigationStructure(linkToNavigationStructure, skin.getId(), localStoragePath,walletPublicKey);


            /**
             *  download resources
             */

            downloadResourcesFromRepo(linkToResources, skin, localStoragePath, screenSize);

            /**
             *  download language
             */
            String linkToLanguage = linkToRepo + "languages/";
            downloadLanguageFromRepo(linkToLanguage, skin.getId(),languageName, localStoragePath, screenSize);


        } catch (CantCheckResourcesException cantCheckResourcesException) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",cantCheckResourcesException,"Error in skin.mxl file","");
        } catch (CantPersistFileException cantPersistFileException) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",cantPersistFileException,"Error persisting file","");
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
    public void installSkinForWallet(String walletCategory, String walletType, String developer, String screenSize, String skinName, String navigationStructureVersion) throws WalletResourcesInstalationException {
        String linkToRepo = "seed-resources/wallet_resources/"+developer+"/"+walletCategory+"/"+walletType+"/";

        String linkToResources = linkToRepo + "skins/" + skinName + "/";


        String localStoragePath=this.LOCAL_STORAGE_PATH +developer+"/"+walletCategory + "/" + walletType + "/"+ "skins/" + skinName + "/" + screenSize + "/";

        Skin skin = null;

        /**
         * add progress
         */
        addProgress(InstalationProgress.INSTALATION_START);

        try {

            String linkToSkinFile = linkToResources + screenSize + "/";
            skin = checkAndInstallSkinResources(linkToSkinFile, localStoragePath);


            Repository repository = new Repository(skinName, navigationStructureVersion, localStoragePath);

            /*NetworkServicesWalletResourcesDAO networkServicesWalletResourcesDAO = new NetworkServicesWalletResourcesDAO(pluginDatabaseSystem);

            try {

                networkServicesWalletResourcesDAO.createRepository(repository, skin.getId());

            } catch (CantCreateRepositoryException e) {
                e.printStackTrace();
            }
            */


            /**
             *  download resources
             */

            downloadResourcesFromRepo(linkToResources, skin, localStoragePath, screenSize);



        }catch (CantCheckResourcesException cantCheckResourcesException){
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",cantCheckResourcesException,"Error in skin.mxl file","");
        } catch (CantPersistFileException cantPersistFileException) {
            throw new WalletResourcesInstalationException("CAN'T INSTALL WALLET RESOURCES",cantPersistFileException,"Error persisting file","");
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
    public void installLanguageForWallet(String walletCategory, String walletType, String developer, String screenSize, UUID skinId, String languageName) throws WalletResourcesInstalationException {

        String linkToRepo = "seed-resources/wallet_resources/"+developer+"/"+walletCategory+"/"+walletType+"/";
        /**
         *  download language
         */
        String linkToLanguage = linkToRepo + "languages/";
        downloadLanguageFromRepo(linkToLanguage, skinId, languageName, LOCAL_STORAGE_PATH, screenSize);

        /**
         *  Fire event Wallet language installed
         */

        /*PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_UNINSTALLED);
        WalletUninstalledEvent walletUninstalledEvent=  (WalletUninstalledEvent) platformEvent;
        walletUninstalledEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);*/

    }


    @Override
    public void unninstallCompleteWallet(String walletCategory, String walletType, String developer, String skinName, UUID skinId, String screenSize, String navigationStructureVersion, boolean isLastWallet) {

        if(isLastWallet){

            UnninstallWallet( walletCategory, walletType, developer, skinName, skinId,  screenSize, navigationStructureVersion, isLastWallet);

        }

        /**
         *  Fire event Wallet resource installed
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_UNINSTALLED);
        WalletUninstalledEvent walletUninstalledEvent=  (WalletUninstalledEvent) platformEvent;
        walletUninstalledEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);

    }

    /**
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param walletName
     * @param skinId
     * @param screenSize
     * @param navigationStructureVersion
     * @param isLastWallet
     */
    @Override
    public void unninstallSkinForWallet(String walletCategory, String walletType, String developer, String walletName, UUID skinId, String screenSize, String navigationStructureVersion, boolean isLastWallet) throws WalletResourcesUnninstallException {

    }

    /**
     * @param walletCategory
     * @param walletType
     * @param developer
     * @param walletName
     * @param isLastWallet
     */
    @Override
    public void unninstallLanguageForWallet(String walletCategory, String walletType, String developer, String walletName, boolean isLastWallet) throws WalletResourcesUnninstallException {

    }


    @Override
    public InstalationProgress getInstalationProgress(){
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
        System.err.println(this.getClass() + " Method: getResourcesId - TENGO RETURN NULL");
        return null;
    }

    @Override
    public Skin getSkinFile(String fileName, UUID skinId) throws CantGetSkinFileException, CantGetResourcesException {
        String content = "";
        try {
            //get repo name
            Repository repository = repositoriesName.get(skinId);//= Repositories.getValueFromType(walletType);
            //get image from disk
            PluginTextFile layoutFile;

            String path = repository.getPath() + "/skins/" + repository.getSkinName() + "/";

            layoutFile = pluginFileSystem.getTextFile(pluginId, path, fileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();
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

    @Override
    public String getLanguageFile(String fileName) throws CantGetLanguageFileException {
        return "Method: getLanguageFile - NO TIENE valor ASIGNADO para RETURN";
    }


    /**
     * <p>This method return a image file saved in device memory
     *
     * @param imageName Name of resource image file
     * @return byte image object
     * @throws CantGetResourcesException
     */


    @Override
    public byte[] getImageResource(String imageName, UUID skinId) throws CantGetResourcesException {

        // Testing purpose
        return imagenes.get(imageName);

        //TODO: despues tengo que ir a buscar al archivo, esto está así por tema de testeo, abajo está el codigo que lo hace
        /*
        Repository repository= repositoriesName.get(skinId);

        PluginBinaryFile imageFile = null;

        String filename= skinId.toString()+"_"+imageName;

        String path = repository.getPath()+"/skins/"+repository.getSkinName()+"/";

        try {
            imageFile = pluginFileSystem.getBinaryFile(pluginId, path, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CantCreateFileException e) {
            e.printStackTrace();
        }



        return imageFile.getContent();
        */
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
    public String getLayoutResource(String layoutName, ScreenOrientation orientation, UUID skinId) throws CantGetResourcesException {

        String content = "";
        try {
            //get repo name
            String reponame = "";//= Repositories.getValueFromType(walletType);
            //get image from disk
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, layoutName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();
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


    // Private instances methods declarations.


    private void UnninstallWallet(String walletCategory,String walletType,String developer,String skinName,UUID skinId, String screenSize,String navigationStructureVersion,boolean isLastWallet){
        String linkToRepo = REPOSITORY_LINK + walletCategory + "/" + walletType + "/" + developer + "/";


        String linkToResources = linkToRepo + "skins/" + skinName + "/" + screenSize + "/";


        Skin skin = null;
        try {


            skin = checkAndInstallSkinResources(linkToResources, LOCAL_STORAGE_PATH);


            /**
             *  Save repository in memory for use
             */
            repositoriesName.remove(skin.getId());


            NetworkServicesWalletResourcesDAO networkServicesWalletResourcesDAO = new NetworkServicesWalletResourcesDAO(pluginDatabaseSystem);

            try {

                networkServicesWalletResourcesDAO.delete(skin.getId(), linkToRepo);

            } catch (CantDeleteRepositoryException e) {
                e.printStackTrace();
            } catch (ProjectNotFoundException e) {
                e.printStackTrace();
            } catch (RepositoryNotFoundException e) {
                e.printStackTrace();
            }

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




        } catch (CantCheckResourcesException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        }
    }


    private void downloadResourcesFromRepo(String linkToRepo, Skin skin, String localStoragePath,String screenSize) {

        /**
         * download portrait resources
         */
        String linkToResources = linkToRepo + "resources/mdpi/drawables/";
        // this will be used when the main repository be open source
        downloadResourcesFromRepo(linkToResources, skin.getResources(), skin.getId(),localStoragePath);


        /**
         * download portrait layouts
         */
        String linkToPortraitLayouts = linkToRepo +screenSize+ "/portrait/layouts/";
        downloadLayouts(linkToPortraitLayouts, skin.getPortraitLayouts(), skin.getId(),localStoragePath);

        /**
         * download landscape layouts
         */
        String linkToLandscapeLayouts = linkToRepo +screenSize+ "/landscape/layouts/";
        downloadLayouts(linkToLandscapeLayouts, skin.getLandscapeLayouts(), skin.getId(),localStoragePath);


        //TODO: raise a event
        // fire event Wallet resource installed
        /*PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_INSTALLED);
        ((WalletResourcesInstalledEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);
        */

    }

    private void downloadLanguageFromRepo(String linkToLanguage, UUID skinId,String languajeName, String localStoragePath,String screenSize) {

        /**
         * download language
         */
        String linkToLanguageRepo = linkToLanguage+languajeName+".xml";
        downloadLanguage(linkToLanguageRepo, languajeName, skinId, localStoragePath);


        //TODO: raise a event
        // fire event Wallet resource installed
        /*PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_INSTALLED);
        ((WalletResourcesInstalledEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);
        */

    }



    private void downloadResourcesFromRepo(String link, Map<String, Resource> resourceMap, UUID skinId,String localStoragePath) {
        try {
            for (Map.Entry<String, Resource> entry : resourceMap.entrySet()) {


                switch (entry.getValue().getResourceType()) {
                    case IMAGE:
                        // this will be used when the main repository be open source
                        //byte[] image = getRepositoryImageFile(link, entry.getValue().getFileName());
                        // this is used because the main repository is private
                        byte[] image = githubConnection.getImage(link + entry.getValue().getFileName());

                        //testing purpose
                        imagenes.put(entry.getValue().getName(), image);
                        try {
                            recordImageResource(image, entry.getKey(), skinId, localStoragePath);

                        } catch (CantCheckResourcesException e) {
                            e.printStackTrace();
                        } catch (CantPersistFileException e) {
                            e.printStackTrace();
                        }
                        break;
                    case SOUND:

                        break;
                    case VIDEO:
                        break;
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteResources(String link, Map<String, Resource> resourceMap, UUID skinId) {
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
        } catch (CantCheckResourcesException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        }
    }

    private void downloadLayouts(String link, Map<String, Layout> resourceMap, UUID skinId,String localStoragePath) {
        try {
            for (Map.Entry<String, Layout> entry : resourceMap.entrySet()) {

                // this will be when the repository be open source
                //String layoutXML = getRepositoryStringFile(link, entry.getValue().getFilename());

                // this is because the main repository is private
                String layoutXML = githubConnection.getFile(link+entry.getValue().getFilename());

                try {

                    recordXML(layoutXML, entry.getKey(), skinId, localStoragePath);

                } catch (CantCheckResourcesException e) {
                    e.printStackTrace();
                } catch (CantPersistFileException e) {
                    e.printStackTrace();
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void downloadLanguage(String link,String languageName, UUID skinId,String localStoragePath) {
        try {
            String languageXML = githubConnection.getFile(link);

            recordXML(languageXML, languageName, skinId, localStoragePath);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CantCheckResourcesException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        }
    }

    private void deleteLayouts(String link, Map<String, Layout> resourceMap, UUID skinId) {
        try {
            for (Map.Entry<String, Layout> entry : resourceMap.entrySet()) {

                    deleteResource(entry.getKey(), skinId, link);

            }

        } catch (CantCheckResourcesException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        }

    }


    private void donwloadNavigationStructure(String link, UUID skinId,String localStoragePath,String walletPublicKey) {
        try {


            /**
             *  Download portrait navigation structure
             */
            String navigationStructureName="navigation_structure.xml";
            //this will be use when the main repository be open source
            //String navigationStructureXML = getRepositoryStringFile(link, navigationStructureName);

            // this is used because we have a private main repository
            String navigationStructureXML = githubConnection.getFile(link+navigationStructureName);

            try {

                recordXML(navigationStructureXML,navigationStructureName,skinId,localStoragePath);

            } catch (CantCheckResourcesException e) {
                e.printStackTrace();
            } catch (CantPersistFileException e) {
                e.printStackTrace();
            }


            PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED);
            WalletNavigationStructureDownloadedEvent walletNavigationStructureDownloadedEvent=  (WalletNavigationStructureDownloadedEvent) platformEvent;
            walletNavigationStructureDownloadedEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
            walletNavigationStructureDownloadedEvent.setFilename("navigation_structure.xml");
            walletNavigationStructureDownloadedEvent.setSkinId(skinId);
            walletNavigationStructureDownloadedEvent.setXmlText(navigationStructureXML);
            walletNavigationStructureDownloadedEvent.setLinkToRepo(localStoragePath);
            walletNavigationStructureDownloadedEvent.setWalletPublicKey(walletPublicKey);
            eventManager.raiseEvent(platformEvent);



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recordImageResource(byte[] image, String name, UUID skinId, String reponame) throws CantCheckResourcesException, CantPersistFileException {

        PluginBinaryFile imageFile = null;

        String filename = skinId.toString() + "_" + name;


        try {

            imageFile = pluginFileSystem.getBinaryFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        } catch (CantCreateFileException cantPersistFileException) {
            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantPersistFileException, "Error persist image file " + filename, "");
        } catch (FileNotFoundException e) {

            try {
                imageFile = pluginFileSystem.createBinaryFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
                imageFile.setContent(image);
                try {
                    imageFile.persistToMedia();
                } catch (CantPersistFileException cantPersistFileException) {
                    throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantPersistFileException, "Error persist image file " + filename, "");

                }
            } catch (CantCreateFileException cantPersistFileException) {
                throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantPersistFileException, "Error persist image file " + filename, "");
            }
        }
        //aca deberia fijarme que hago si ya existe el archivo


    }

    private void recordXML(String xml, String name, UUID skinId, String reponame) throws CantCheckResourcesException, CantPersistFileException {

        PluginTextFile layoutFile = null;

        String filename = skinId.toString() + "_" + name;

        try {

            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        } catch (CantCreateFileException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            try {

                layoutFile = pluginFileSystem.createTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
                layoutFile.setContent(xml);
                layoutFile.persistToMedia();

            } catch (CantCreateFileException cantPersistFileException) {
                throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantPersistFileException, "Error persist image file " + filename, "");
            }
        }

    }

    private void recordXML(String xml, String name, String skinName, String reponame) throws CantCheckResourcesException, CantPersistFileException {

        PluginTextFile layoutFile = null;

        String filename = skinName + "_" + name;

        try {

            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        } catch (CantCreateFileException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            try {

                layoutFile = pluginFileSystem.createTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
                layoutFile.setContent(xml);
                layoutFile.persistToMedia();

            } catch (CantCreateFileException cantPersistFileException) {
                throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantPersistFileException, "Error persist image file " + filename, "");
            }
        }
    }
    private void deleteXML( String name, UUID skinId, String reponame) throws CantCheckResourcesException, CantPersistFileException {

        String filename = skinId.toString() + "_" + name;

        try {

            pluginFileSystem.deleteTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        } catch (CantCreateFileException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void deleteResource(String name, UUID skinId, String reponame) throws CantCheckResourcesException, CantPersistFileException {

        String filename = skinId.toString() + "_" + name;

        try {

            pluginFileSystem.deleteTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        } catch (CantCreateFileException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    private Skin checkAndInstallSkinResources(String linkToSkin,String localStoragePath) throws CantCheckResourcesException, CantPersistFileException {
        String repoManifest = "";
        String skinFilename = "skin.xml";
        try {
            //connect to repo and get skin file
            // this will work when we have open source repository

            //repoManifest = getRepositoryStringFile(linkToSkin, skinFilename);

            //this work only for the private repository
            repoManifest = githubConnection.getFile(linkToSkin+skinFilename);


            Skin skin = new Skin();
            skin = (Skin) XMLParser.parseXML(repoManifest, skin);

            /**
             *  Skin record
             */
            recordXML(repoManifest, skin.getName(), skin.getId(), localStoragePath);

            return skin;

        } catch (MalformedURLException e) {

            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", e, "Http error in connection with the repository to load manifest file", "");

        } catch (IOException e) {

            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", e, "Error load manifest file ", "Repository not exist or manifest file not exist");

        }
         catch (Exception e) {

            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", e, "Error load manifest file ", "Generic Exception");

        }
    }



    /**
     * <p>This method connects to the repository and download string file resource for wallet on byte (Private Method)
     *
     * @return string resource object
     * @throws MalformedURLException
     * @throws IOException
     * @throws FileNotFoundException
     */
    private String getRepositoryStringFile(String link, String filename) throws MalformedURLException, IOException, FileNotFoundException {

        String reporSource = REPOSITORY_LINK + link + filename;

        URL url = new URL(reporSource);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        Map<String, List<String>> headerFields = http.getHeaderFields();
        // If URL is getting 301 and 302 redirection HTTP code then get new URL link.
        // This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
        for (String header : headerFields.get(null)) {
            if (header.contains(" 302 ") || header.contains(" 301 ")) {
                reporSource = headerFields.get("Location").get(0);
                url = new URL(reporSource);
                http = (HttpURLConnection) url.openConnection();
                headerFields = http.getHeaderFields();
            }
        }

        InputStream crunchifyStream = http.getInputStream();
        String response = getStringFromStream(crunchifyStream);

        return response;

    }

    /**
     * <p>This method connects to the repository and download resource image file for wallet on byte
     *
     * @param repoResource name of repository where wallet files resources are stored
     * @param fileName     Name of resource file
     * @return byte image object
     * @throws MalformedURLException
     * @throws IOException
     * @throws FileNotFoundException
     */

    private byte[] getRepositoryImageFile(String repoResource, String fileName) throws MalformedURLException, IOException, FileNotFoundException {

        String link = REPOSITORY_LINK + repoResource + fileName;

        URL url = new URL(link);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        BufferedInputStream in = new BufferedInputStream(http.getInputStream());
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        int c;
        while ((c = in.read()) != -1) {
            byteArrayOut.write(c);
        }

        in.close();
        return byteArrayOut.toByteArray();

    }

    /**
     * <p> Return the string content from a Stream
     *
     * @param stream
     * @return String Stream Object
     * @throws IOException
     */

    private String getStringFromStream(InputStream stream) throws IOException {
        if (stream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[2048];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                int counter;
                while ((counter = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, counter);
                }
            } finally {
                stream.close();
            }
            return writer.toString();
        } else {
            return "No Contents";
        }
    }


    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }


    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot");
        /**
         * I return the values.
         */
        return returnedClasses;
    }


    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (WalletResourcesNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletResourcesNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletResourcesNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletResourcesNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    private void addProgress(InstalationProgress instalationProgress){
        this.instalationProgress = instalationProgress;
    }


}
