package com.bitdubai.wallet_platform_core.layer._9_network_service.wallet_resources.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventType;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._9_network_service.NetworkService;

import java.util.ArrayList;
import java.util.List;
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

public class WalletResourcesPluginRoot implements Service, NetworkService, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem,Plugin {

    

    // Loui: TODO: Debe escuchar el evento BEGUN_WALLET_INSTALLATION y el handler ejecutar el metodo checkResources (tipo de wallet, developer, version, publisher), el tipo de wallet, developer es un enum en definitions
    
    // Loui; TODO: Tiene que disparar un evento cuando obtenga todos los recursos de una nueva wallet por primera vez: WALLET_RESOURCES_INSTALLED 
    
    // Loui; TODO; El AppRuntime, debe escuchar ese evento y ejecutar el mentodo : addToNavigationStructure (String navegationStructure, y los parametros que identifican el tipo de wallet)

    // Loui; TODO: EL AppRuntime, debe disparar el evento NAVIGATION_STRUCTURE_UPDATED con los parametros que identifique el tipo de wallet. 

    // Loui: TODO: El Wallet Manager tambien escucha el evento WALLET_RESOURCES_INSTALLED y cuando se entera, permite actualiza internamente el progreso de la instalacion, que incluye la creacion de su propio wallet

    // Loui: TODO: El Wallet Manager tambien escucha el evento NAVIGATION_STRUCTURE_UPDATED y cuando se entera, permite que el usuario abra la nueva billetera instalada y obviamente setea el estado de la instalacion en un 100%. El handler le ejecuta el metodo: enableWallet ()
    
    // Loui TODO: EL Middleware Wallet tiene que disparar el evento WALLET_CREATED cuando termina de crear su wallet. Notar que es el mismo evento que dispara el Wallet Manager. Para poder distinguirlos, cada quien tiene que setear en el source del evento quien es que lo disparo. Y obviamente el handler tiene que tener en cuenta solo los eventos no disparados por el mismo. Entonces hay que actualizar el disparo del Wallet Manager para que agregue el source, el handler actual que lo toma, y el nuevo que lo dispara tambien tiene que setearse como source.
    
    // Loui; TODO El Wallet Mangager tiene que escuchar el evento WALLET_CREATED enviado por el middleware Wallet y el handler asegurarse que no sea el mismo y ejecutar el metodo que corresponda. Realmente va a considerra la wallet instaladad cuando haya recibido este evento y todos los otros que esperaba.
    
    

    
    
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
     * Service Interface implementation.
     */

    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;
        
/*
        eventListener = eventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION);
        eventHandler = new BegunWalletInstallationEventHandler();
        ((BegunWalletInstallationEventHandler) eventHandler).---------(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);
*/      
        
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
