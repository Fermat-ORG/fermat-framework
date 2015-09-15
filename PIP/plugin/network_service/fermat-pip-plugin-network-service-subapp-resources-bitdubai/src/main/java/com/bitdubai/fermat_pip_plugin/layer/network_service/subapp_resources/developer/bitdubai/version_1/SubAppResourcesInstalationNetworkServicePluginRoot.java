package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;

import com.bitdubai.fermat_api.layer.all_definition.github.GithubConnection;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.InstalationProgress;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;



import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenOrientation;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;

import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetSkinFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;


import com.bitdubai.fermat_pip_api.layer.pip_network_service.NetworkService;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesInstalationManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.event_handlers.BegunSubAppInstallationEventHandler;

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
 * Created by Matias Furszyfer on 17/02/15.
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

public class SubAppResourcesInstalationNetworkServicePluginRoot implements Service, NetworkService, DealsWithEvents, DealsWithErrors,DealsWithLogger, DealsWithPluginFileSystem,LogManagerForDevelopers,Plugin,SubAppResourcesInstalationManager,SubAppResourcesProviderManager {


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
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    /**
     * SubApp Type
     */

    SubApps subappType;

    /**
     * Installed skins repositories
     * <p>
     * SkinId, repository link
     */
    //private Map<UUID, Repository> repositoriesName;


    private String REPOSITORY_LINK = "https://raw.githubusercontent.com/bitDubai/fermat/master/seed-resources/subApp_resources/";


    private final String LOCAL_STORAGE_PATH="subApp-resources/";


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
    public void start() throws CantStartPluginException{
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION);
        eventHandler = new BegunSubAppInstallationEventHandler();
        ((BegunSubAppInstallationEventHandler) eventHandler).setSubAppResourcesManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);


        this.serviceStatus = ServiceStatus.STARTED;

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
        return this.pluginId;
    }



    /**
     * SubAppResourcesInstalationManager Implementation
     */

    /**
     * @param subApp
     * @param developer
     * @param screenSize
     * @param screenDensity
     * @param skinName
     * @param languageName
     * @param navigationStructureVersion
     */
    @Override
    public void installResources(String subApp, String developer, String screenSize, String screenDensity, String skinName, String languageName, String navigationStructureVersion) {

    }

    /**
     * @param subApp
     */
    @Override
    public void unninstallResources(String subApp) {

    }

    /**
     * Get enum instalation progress
     *
     * @return
     */
    @Override
    public InstalationProgress getInstalationProgress() {
        return null;
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
        return null;
    }

    /**
     * This method let us get an skin file referenced by its name
     *
     * @param fileName the name of the Skin file (without the path structure).
     * @param skinId
     * @return The content of the file
     * @throws CantGetSkinFileException
     */
    @Override
    public Skin getSkinFile(String fileName, UUID skinId) throws CantGetSkinFileException, CantGetResourcesException {
        return null;
    }

    /**
     * This method let us get a language file referenced by a name
     *
     * @param fileName the name of the Language file (without the path structure).
     * @return The content of the file
     * @throws CantGetLanguageFileException
     */
    @Override
    public String getLanguageFile(String fileName) throws CantGetLanguageFileException {
        return null;
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
    public byte[] getImageResource(String imageName, UUID skinId) throws CantGetResourcesException {
        return new byte[0];
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
     * This method let us get a layout referenced by a name
     *
     * @param layoutName  the name of the layout resource found in the skin file
     * @param orientation
     * @param skinId      @return the layiut represented as String
     * @throws CantGetResourcesException
     */
    @Override
    public String getLayoutResource(String layoutName, ScreenOrientation orientation, UUID skinId) throws CantGetResourcesException {
        return null;
    }







    // Private instances methods declarations.

    /**
     * <p>This method connects to the repository and download string file resource for wallet on byte (Private Method)
     *
     * @param repoResource name of repository where wallet files resources are stored
     * @param fileName Name of resource file
     * @return string resource object
     * @throws MalformedURLException
     * @throws IOException
     * @throws FileNotFoundException
     */
    private String getRepositoryStringFile(String repoResource,String fileName) throws MalformedURLException, IOException, FileNotFoundException {
        String link = REPOSITORY_LINK + repoResource +"/master/" + fileName;

        URL url = new URL(link);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        Map<String, List<String>> headerFields = http.getHeaderFields();
        // If URL is getting 301 and 302 redirection HTTP code then get new URL link.
        // This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
        for (String header : headerFields.get(null)) {
            if (header.contains(" 302 ") || header.contains(" 301 ")) {
                link = headerFields.get("Location").get(0);
                url = new URL(link);
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

        String link = REPOSITORY_LINK + repoResource +"/master/" + fileName;

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
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstalationNetworkServicePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.estructure.SubAppResourcesNetworkService");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.estructure.SubAppNavigationStructureNetworkService");


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
            if (SubAppResourcesInstalationNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                SubAppResourcesInstalationNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                SubAppResourcesInstalationNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                SubAppResourcesInstalationNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }



}
