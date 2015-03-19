package com.bitdubai.fermat_core.layer._10_network_service.wallet_resources.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._10_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer._10_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer._10_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer._10_network_service.wallet_resources.enums.Repositories;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginDataFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginImageFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.WalletResourcesInstalledEvent;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._10_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_core.layer._10_network_service.wallet_resources.developer.bitdubai.version_1.event_handlers.BegunWalletInstallationEventHandler;

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

    String imageName;

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
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
    public void setImageName(String name) {
        this.imageName = name;
    }


    @Override
    public void setwalletType(Wallets type) {
        this.walletType = type;
    }

    @Override
    public void checkResources(/*WalletType, Developer, version, publisher*/) throws CantCheckResourcesException {
 
        
        try{

            //get repo name
            String reponame = Repositories.getValueFromType (walletType);
            //connect to repo and get manifest file
            String repoManifest = getRepositoryStringFile(reponame, "manifest.txt");
            //get list of wallet image, split
            String[] fileList = repoManifest.split(",");
            for (int j = 0; j < fileList.length; j++) {
                //get file image in repo
                //save that on memory

                byte[] image =  getRepositoryImageFile(reponame, fileList[j].toString());
                PluginImageFile imageFile;
                
                imageFile = pluginFileSystem.createImageFile(pluginId, reponame, fileList[j].toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                imageFile.setContent(image);
                imageFile.persistToMedia();

            }

            //get list of layouts files and save in disk
            String layoutManifest = getRepositoryStringFile(reponame, "layout_manifest.txt");
            String[] layoutList = layoutManifest.split(",");
            for (int j = 0; j < layoutList.length; j++) {

                String file =  getRepositoryStringFile(reponame, layoutList[j].toString());
                PluginDataFile layoutFile;
                layoutFile = pluginFileSystem.createDataFile(pluginId, reponame, layoutList[j].toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                layoutFile.setContent(file);
                layoutFile.persistToMedia();
            }


        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        }


        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_INSTALLED);
        ((WalletResourcesInstalledEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);

    }

    @Override
    public byte[] getImageResource() throws CantGetResourcesException {

        byte[] imageResource = new byte[16384];

        try {

            //get repo name
            String reponame = Repositories.getValueFromType(walletType);
            //get image from disk
            PluginImageFile imageFile;
            imageFile = pluginFileSystem.getImageFile(pluginId, reponame, imageName, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
// TODO: NATALIA las imagenes deberian estar grabadas en la memoria externa del telefono, osea que FilePrivacy no puede ser PRIVATE
            imageResource = imageFile.getContent();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();

        }
        return imageResource;
    }

    @Override
    public String getLayoutResource() throws CantGetResourcesException {

        String content = "";
        try {
            //get repo name
            String reponame = Repositories.getValueFromType(walletType);
            //get image from disk
            PluginDataFile layoutFile;
            layoutFile = pluginFileSystem.getDataFile(pluginId, reponame, imageName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            content = layoutFile.getContent();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();

        }
        return content;
    }

    private String getRepositoryStringFile(String repo,String file) throws MalformedURLException, IOException, FileNotFoundException {
        String link = "https://raw.githubusercontent.com/bitDubai/"+repo +"/master/" + file;

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
     * connect to repository and download image file
     */


    private byte[] getRepositoryImageFile(String repo,String file) throws MalformedURLException, IOException, FileNotFoundException {

        String link = "https://raw.githubusercontent.com/bitDubai/"+repo +"/master/" + file;

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

    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


}
