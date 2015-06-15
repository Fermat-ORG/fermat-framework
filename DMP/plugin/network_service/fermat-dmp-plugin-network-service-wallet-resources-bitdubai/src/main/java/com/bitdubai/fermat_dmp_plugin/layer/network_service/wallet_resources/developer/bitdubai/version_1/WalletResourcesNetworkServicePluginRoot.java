package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._11_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer._11_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer._11_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer._11_network_service.wallet_resources.enums.Repositories;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.WalletResourcesInstalledEvent;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._11_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by loui on 17/02/15.
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

public class WalletResourcesNetworkServicePluginRoot implements Service, NetworkService,WalletResourcesManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem,Plugin {


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
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    /**
     * Wallet Type
     */

    Wallets walletType;

    String REPOSITORY_LINK = "https://raw.githubusercontent.com/bitDubai/";

    /**
     * Service Interface implementation.
     */

    @Override
    public void start(){
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION);
        eventHandler = new BegunWalletInstallationEventHandler();
        ((BegunWalletInstallationEventHandler) eventHandler).setWalletResourcesManager(this);
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
        return null;
    }

    /**
     * WalletResourcesManager Implementation 
     */


    @Override
    public void setwalletType(Wallets type) {
        this.walletType = type;
    }

    /**
     * <p>This method read wallet manifest file to get resources names, to download from repository.
     * <p>Save file in device memory
     *
     * @throws CantCheckResourcesException
     */

    @Override
    public void checkResources(/*WalletType, Developer, version, publisher*/) throws CantCheckResourcesException {

        //get repo name to wallet type
        String reponame = Repositories.getValueFromType (walletType);

        String repoManifest ="";
        try{
            //connect to repo and get manifest file
            repoManifest = getRepositoryStringFile(reponame, "manifest.txt");
        }
        catch(MalformedURLException|FileNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantCheckResourcesException();
        }catch(IOException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCheckResourcesException();
        }
        //get list of wallet image, split by ,
        String[] fileList = repoManifest.split(",");
        for (int j = 0; j < fileList.length; j++) {
            //get file image in repo, save that on memory
            byte[] image = null;
            try{
                image =  getRepositoryImageFile(reponame, fileList[j].toString());
            }
            catch(MalformedURLException|FileNotFoundException e){
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
                throw new CantCheckResourcesException();

            }catch(IOException e){
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCheckResourcesException();
            }
            PluginBinaryFile imageFile = null;

            try{
                imageFile = pluginFileSystem.createBinaryFile(pluginId, reponame, fileList[j].toString(), FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            }
            catch(CantCreateFileException cantPersistFileException){
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
                throw new CantCheckResourcesException();
            }
            imageFile.setContent(image);
            try{
                imageFile.persistToMedia();
            }
            catch(CantPersistFileException cantPersistFileException){
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
                throw new CantCheckResourcesException();
            }

        }

        //get list of layouts files and save in disk -- incomplete functionality
        String layoutManifest="";
        try {
            layoutManifest = getRepositoryStringFile(reponame, "layout_manifest.txt");
        }
        catch(MalformedURLException|FileNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantCheckResourcesException();
        }catch(IOException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCheckResourcesException();
        }


        String[] layoutList = layoutManifest.split(",");
        for (int j = 0; j < layoutList.length; j++) {

            String file ="";
            try {
                file = getRepositoryStringFile(reponame, layoutList[j].toString());
            }
            catch(MalformedURLException|FileNotFoundException e){
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
                throw new CantCheckResourcesException();

            }catch(IOException e){
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
                throw new CantCheckResourcesException();
            }
            PluginTextFile layoutFile = null;

            try{
                layoutFile = pluginFileSystem.createTextFile(pluginId, reponame, layoutList[j].toString(), FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            } catch (CantCreateFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
                throw new CantCheckResourcesException();
            }

            layoutFile.setContent(file);
            try{
                layoutFile.persistToMedia();
            }
            catch (CantPersistFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
                throw new CantCheckResourcesException();
            }

        }

        // fire event Wallet resource installed
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_INSTALLED);
        ((WalletResourcesInstalledEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);

    }

    /**
     * <p>This method return a image file saved in device memory
     *
     * @param imageName Name of resource image file
     * @return byte image object
     * @throws CantGetResourcesException
     */
    @Override
    public byte[] getImageResource(String imageName) throws CantGetResourcesException {

        byte[] imageResource = new byte[16384];

        try {

            //get repo name to wallet type variable
            String reponame = Repositories.getValueFromType(walletType);
            //get image from disk
            PluginBinaryFile imageFile;
            imageFile = pluginFileSystem.getBinaryFile(pluginId, reponame, imageName, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

            imageResource = imageFile.getContent();
        }
        catch(FileNotFoundException fileNotFoundException){

            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, fileNotFoundException);
            throw new CantGetResourcesException();

        }catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetResourcesException();

        }
        return imageResource;
    }

    /**
     * <p>This method return a layout file saved in device memory
     *
     * @param layoutName Name of layout resource file
     * @return string layout object
     * @throws CantGetResourcesException
     */
    @Override
    public String getLayoutResource(String layoutName) throws CantGetResourcesException {

        String content = "";
        try {
            //get repo name
            String reponame = Repositories.getValueFromType(walletType);
            //get image from disk
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, reponame, layoutName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();
        }
        catch(FileNotFoundException e){
            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetResourcesException();

        } catch (CantCreateFileException e) {
            /**
             * I cant continue if this happens.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetResourcesException();

        }

        return content;
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


}
