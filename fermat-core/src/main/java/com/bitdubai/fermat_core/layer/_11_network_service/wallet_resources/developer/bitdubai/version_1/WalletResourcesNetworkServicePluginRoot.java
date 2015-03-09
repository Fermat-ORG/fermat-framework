package com.bitdubai.fermat_core.layer._11_network_service.wallet_resources.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._11_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer._11_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer._11_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer._11_network_service.wallet_resources.enums.Repositories;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginDataFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginImageFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
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
import com.bitdubai.fermat_api.layer._11_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_core.layer._11_network_service.wallet_resources.developer.bitdubai.version_1.event_handlers.BegunWalletInstallationEventHandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_INSTALLED);
        ((WalletResourcesInstalledEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        try{

            //get repo name
            String reponame = Repositories.getValueFromType (walletType);
            //conect to repo and get manifest file
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

    }

    @Override
    public byte[] getResources(/*WalletType, Developer, version, publisher*/) throws CantGetResourcesException {

        byte[] imageResource = new byte[16384];
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.WALLET_RESOURCES_INSTALLED);
        ((WalletResourcesInstalledEvent) platformEvent).setSource(EventSource.NETWORK_SERVICE_WALLET_RESOURCES_PLUGIN);
        eventManager.raiseEvent(platformEvent);

        try {
            this.walletType = Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI;
            //get repo name
            String reponame = Repositories.getValueFromType(walletType);
            //get image from disk
            PluginImageFile imageFile;
            imageFile = pluginFileSystem.getImageFile(pluginId, reponame, imageName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            imageResource = imageFile.getContent();
        }
            catch(FileNotFoundException e){
                e.printStackTrace();

         }
        return imageResource;
    }


    public static String getRepositoryStringFile(String repo,String file) throws MalformedURLException, IOException, FileNotFoundException {
        String link = "https://raw.githubusercontent.com/bitDubai/"+repo +"/master/" + file;

        URL crunchifyUrl = new URL(link);
        HttpURLConnection crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();

        Map<String, List<String>> crunchifyHeader = crunchifyHttp.getHeaderFields();
        // If URL is getting 301 and 302 redirection HTTP code then get new URL link.
// This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
        for (String header : crunchifyHeader.get(null)) {
            if (header.contains(" 302 ") || header.contains(" 301 ")) {
                link = crunchifyHeader.get("Location").get(0);
                crunchifyUrl = new URL(link);
                crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
                crunchifyHeader = crunchifyHttp.getHeaderFields();
            }
        }

        InputStream crunchifyStream = crunchifyHttp.getInputStream();
        String crunchifyResponse = crunchifyGetStringFromStream(crunchifyStream);

        return  crunchifyResponse;

    }

    public static byte[] getRepositoryImageFile(String repo,String file) throws MalformedURLException, IOException, FileNotFoundException {

        String link = "https://raw.githubusercontent.com/bitDubai/"+repo +"/master/" + file;

            URL crunchifyUrl = new URL(link);
            HttpURLConnection crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
            BufferedInputStream in = new BufferedInputStream(crunchifyHttp.getInputStream());
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            int c;
            while ((c = in.read()) != -1) {
                byteArrayOut.write(c);
            }

            in.close();
            return byteArrayOut.toByteArray();


    }


    // ConvertStreamToString() Utility - we name it as crunchifyGetStringFromStream()
    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
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
