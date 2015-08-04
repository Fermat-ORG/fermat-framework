package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Layout;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenOrientation;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResources;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesInstalationManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantCreateRepositoryException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetSkinFileException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.NetworkServicesWalletResourcesDAO;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.NetworkserviceswalletresourcesDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.NetworkserviceswalletresourcesDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.Repository;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletNavigationStructureDownloadedEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletResourcesInstalledEvent;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.event_handlers.BegunWalletInstallationEventHandler;

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
 *
 * It will try to gather those resources from other peers or a centralized location provided by the wallet developer 
 * if it is not possible.
 *
 * It will also serve other peers with these resources when needed.
 *
 * * * * * * * 
 */

public class WalletResourcesNetworkServicePluginRoot implements Service, NetworkService,WalletResourcesInstalationManager,WalletResourcesProviderManager,DealsWithPluginDatabaseSystem, DealsWithEvents, DealsWithErrors,DealsWithLogger, DealsWithPluginFileSystem,LogManagerForDevelopers,Plugin {


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
     * Database
     */
    private Database database;

    /**
     * Installed skins repositories
     *
     * SkinId, repository link
     */
    private Map<UUID,Repository> repositoriesName;


    private String REPOSITORY_LINK = "https://raw.githubusercontent.com/bitDubai/fermat-wallet-resources/master/";




    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException{

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
             * Database
             */
//            NetworkserviceswalletresourcesDatabaseFactory networkserviceswalletresourcesDatabaseFactory = new NetworkserviceswalletresourcesDatabaseFactory(pluginDatabaseSystem);
//            database = networkserviceswalletresourcesDatabaseFactory.createDatabase(pluginId, NetworkserviceswalletresourcesDatabaseConstants.DATABASE_NAME);


                    this.serviceStatus = ServiceStatus.STARTED;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setUp(){
        repositoriesName =new HashMap<UUID,Repository>();
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
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    /**
     * WalletResourcesInstalationManager Implementation
     */


    @Override
    public WalletResources getWalletResources(String resourceName,String publicKey,Version version) {
        //pluginFileSystem.getBinaryFile(pluginId,RESOURCES_PATH_LOCATION,)
        return null;
    }



    //el xml de las skin debe estar pegado a una estructura de navegacion

    @Override
    public void installResources(String walletCategory, String walletType,String developer,String screenSize,String screenDensity,String skinName,String languageName,String navigationStructureVersion) {
        String linkToRepo=REPOSITORY_LINK+walletCategory+"/"+walletType+"/"+developer+"/";


        String linkToResources = linkToRepo+"skins/"+skinName+"/"+screenSize+"/";


        Skin skin=null;
        try {

           skin = checkSkinResources(linkToResources);


           Repository repository = new Repository(skinName,navigationStructureVersion,linkToRepo);

            /**
             *  Save repository in memory for use
             */
           repositoriesName.put(skin.getId(),repository);

            /**
            *  Create repository in database
            */

           NetworkServicesWalletResourcesDAO networkServicesWalletResourcesDAO = new NetworkServicesWalletResourcesDAO(database);

           networkServicesWalletResourcesDAO.createRepository(repository, skin.getId());



           /**
           *  download navigation structure
           */

           String linkToNavigationStructure = linkToRepo+"/navigationStructure/";
           donwloadNavigationStructure(linkToNavigationStructure,navigationStructureVersion, skin.getId());

           /**
           *  download resources
           */

           downloadResources(linkToResources,skin,screenDensity);



        } catch (CantCheckResourcesException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        } catch (CantCreateRepositoryException e) {
            e.printStackTrace();
        }

        //installSkinResource("null");
    }

    private void recordNavigationStructure(){

    }

    private void downloadResources(String linkToResources,Skin skin,String screenDensity){

        /**
         * download portrait resources
         */
        String linkToPortraitResources = linkToResources+"portrait/resources/"+screenDensity+"/drawables/";
        downloadResources(linkToPortraitResources,skin.getLstPortraitResources(),skin.getId());

        /**
         * download landscape resources
         */
        String linkToLandscapeResources = linkToResources+"landscape/resources/"+screenDensity+"/drawables/";
        downloadResources(linkToLandscapeResources,skin.getLstLandscapeResources(),skin.getId());

        /**
         * download portrait layouts
         */
        String linkToPortraitLayouts = linkToResources+"portrait/resources/"+screenDensity+"/layouts/";
        donwloadLayouts(linkToPortraitLayouts, skin.getLstPortraitLayouts(), skin.getId());

        /**
         * download landscape layouts
         */
        String linkToLandscapeLayouts = linkToResources+"landscape/resources/"+screenDensity+"/layouts/";
        donwloadLayouts(linkToLandscapeLayouts, skin.getLstLandscapeLayouts(), skin.getId());



        // fire event Wallet resource installed
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_INSTALLED);
        ((WalletResourcesInstalledEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);



    }

    //TODO: preguntar a jorge si se guarda los sonidos y los videos como byte array

    private void downloadResources(String link,Map<String,Resource> resourceMap,UUID skinId){
        try{
            for (Map.Entry<String, Resource> entry : resourceMap.entrySet()) {


                switch (entry.getValue().getResourceType()) {
                    case IMAGE:

                        byte[] image = getRepositoryImageFile(link, entry.getValue().getFileName());
                        try {
                            recordImageResource(image,entry.getKey(),skinId,link);

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void donwloadLayouts(String link,Map<String,Layout> resourceMap,UUID skinId){
        try{
            for (Map.Entry<String, Layout> entry : resourceMap.entrySet()) {

                String layoutXML = getRepositoryStringFile(link,entry.getValue().getFilename());

                try {

                    recordXML(layoutXML, entry.getKey(), skinId, link);

                } catch (CantCheckResourcesException e) {
                    e.printStackTrace();
                } catch (CantPersistFileException e) {
                    e.printStackTrace();
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void donwloadNavigationStructure(String link,String navigationStructureVersion,UUID skinId){
        link+=navigationStructureVersion+"/"+navigationStructureVersion+"/";
        try{


            /**
             *  Download portrait navigation structure
             */
            String navigationStructureXML = getRepositoryStringFile(link,"portrait_navigation_structure.xml");


            PlatformEvent walletNavigationStructureDownloadedEvent = new WalletNavigationStructureDownloadedEvent(navigationStructureXML,link,"portrait_navigation_structure.xml",skinId);
            walletNavigationStructureDownloadedEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
            eventManager.raiseEvent(walletNavigationStructureDownloadedEvent);

            /**
             *  Download landscape navigation structure
             */
            navigationStructureXML = getRepositoryStringFile(link,"landscape_navigation_structure.xml");


            walletNavigationStructureDownloadedEvent = new WalletNavigationStructureDownloadedEvent(navigationStructureXML,link,"landscape_navigation_structure.xml",skinId);
            walletNavigationStructureDownloadedEvent.setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
            eventManager.raiseEvent(walletNavigationStructureDownloadedEvent);



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recordImageResource(byte[] image,String name,UUID skinId,String reponame) throws CantCheckResourcesException,CantPersistFileException {

        PluginBinaryFile imageFile = null;

        String filename= skinId.toString()+"_"+name;

        try{
            imageFile = pluginFileSystem.createBinaryFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        }
        catch(CantCreateFileException cantPersistFileException){
            throw new CantCheckResourcesException("CAN'T CHECK WALLET RESOURCES",cantPersistFileException,"Error persist image file " +filename, "");
        }
        imageFile.setContent(image);
        try{
            imageFile.persistToMedia();
        }
        catch(CantPersistFileException cantPersistFileException){
            throw new CantCheckResourcesException("CAN'T CHECK WALLET RESOURCES",cantPersistFileException,"Error persist image file " + filename, "");

        }

    }
    private void recordXML(String xml, String name, UUID skinId, String reponame)throws CantCheckResourcesException,CantPersistFileException{

        PluginTextFile layoutFile = null;

        String filename= skinId.toString()+"_"+name;

        try{
            layoutFile = pluginFileSystem.createTextFile(pluginId, reponame, filename, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        } catch (CantCreateFileException cantPersistFileException) {
            throw new CantCheckResourcesException("CAN'T CHECK WALLET RESOURCES",cantPersistFileException,"Error persist image file " +filename, "");
        }

        layoutFile.setContent(xml);
        try{
            layoutFile.persistToMedia();
        }
        catch(CantPersistFileException cantPersistFileException){
            throw new CantCheckResourcesException("CAN'T CHECK WALLET RESOURCES",cantPersistFileException,"Error persist image file " + filename, "");

        }

    }

    private Skin checkSkinResources(String linkToSkin) throws CantCheckResourcesException, CantPersistFileException {
        String repoManifest ="";
        String skinFilename="/skin.xml";
        try{
            //connect to repo and get manifest file
            repoManifest = getRepositoryStringFile(linkToSkin,skinFilename);



            Skin skin = new Skin();
            skin=(Skin)XMLParser.parseXML(repoManifest,skin);

            /**
             *  Skin record
             */
            recordXML(repoManifest, skin.getName(), skin.getId(), linkToSkin);

            return skin;

        }
        catch(MalformedURLException|FileNotFoundException e){

            throw new CantCheckResourcesException("CAN'T CHECK WALLET RESOURCES",e,"Http error in connection with the repository to load manifest file", "");

        }catch(IOException e){

            throw new CantCheckResourcesException("CAN'T CHECK WALLET RESOURCES",e,"Error load manifest file ","Repository not exist or manifest file not exist");

        }
    }


    /**
     * <p>This method read wallet manifest file to get resources names, to download from repository.
     * <p>Save file in device memory
     *
     * @throws CantCheckResourcesException
     */






    @Override
    public UUID getResourcesId() {
        return null;
    }

    @Override
    public Skin getSkinFile(String fileName,UUID skinId) throws CantGetSkinFileException, CantGetResourcesException {
        String content = "";
        try {
            //get repo name
            Repository repository= repositoriesName.get(skinId);//= Repositories.getValueFromType(walletType);
            //get image from disk
            PluginTextFile layoutFile;

            String path = repository.getPath()+"/skins/"+repository.getSkinName()+"/";

            layoutFile = pluginFileSystem.getTextFile(pluginId, path, fileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();
        }
        catch(FileNotFoundException e){
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET WALLET RESOURCES:",e,"Error write layout file resource  " , "");

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET WALLET RESOURCES:",e,"Error created image file resource " , "");

        }

        return (Skin) XMLParser.parseXML(content,new Skin());
    }

    @Override
    public String getLanguageFile(String fileName) throws CantGetLanguageFileException {
        return null;
    }


    /**
     * <p>This method return a image file saved in device memory
     *
     * @param imageName Name of resource image file
     * @return byte image object
     * @throws CantGetResourcesException
     */


    @Override
    public byte[] getImageResource(String imageName,UUID skinId) throws CantGetResourcesException {
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
    public String getLayoutResource(String layoutName, ScreenOrientation orientation,UUID skinId) throws CantGetResourcesException {

        String content = "";
        try {
            //get repo name
            String reponame="";//= Repositories.getValueFromType(walletType);
            //get image from disk
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, layoutName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();
        }
        catch(FileNotFoundException e){
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET WALLET RESOURCES:",e,"Error write layout file resource  " , "");

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            throw new CantGetResourcesException("CAN'T GET WALLET RESOURCES:",e,"Error created image file resource " , "");

        }

        return content;
    }

    // Private instances methods declarations.

    /**
     * <p>This method connects to the repository and download string file resource for wallet on byte (Private Method)
     *
     * @return string resource object
     * @throws MalformedURLException
     * @throws IOException
     * @throws FileNotFoundException
     */
    private String getRepositoryStringFile(String link,String filename) throws MalformedURLException, IOException, FileNotFoundException {

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

        return  response;

    }

    /**
     * <p>This method connects to the repository and download resource image file for wallet on byte
     *
     * @param repoResource name of repository where wallet files resources are stored
     * @param fileName Name of resource file
     * @return byte image object
     * @throws MalformedURLException
     * @throws IOException
     * @throws FileNotFoundException
     */

    private byte[] getRepositoryImageFile(String repoResource ,String fileName) throws MalformedURLException, IOException, FileNotFoundException {

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

    private  String getStringFromStream(InputStream stream) throws IOException {
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
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager =errorManager;
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



}
