package com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.enums.ContactRequestState;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantAcceptContactRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantConfirmContactRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantCreateContactRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantDenyContactRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantGetPendingContactRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantGetPendingContactRequestsListException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces.PendingContactRequest;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 20/02/15.
 */
public class CryptoAddressesNetworkServicePluginRoot implements Service, NetworkService, CryptoAddressesManager,DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

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


    @Override
    public void createContactRequest(String walletPublicKey, ReferenceWallet referenceWallet, CryptoAddress cryptoAddressToSend, String intraUserToContactPublicKey, String requesterIntraUserPublicKey, String requesterIntraUserName, String requesterIntraUserProfileImage) throws CantCreateContactRequestException {

    }

    @Override
    public void acceptContactRequest(UUID requestId, String walletAcceptingTheRequestPublicKey, ReferenceWallet referenceWallet, CryptoAddress cryptoAddressReceived, String intraUserAcceptingTheRequestPublicKey) throws CantAcceptContactRequestException {

    }

    @Override
    public void denyContactRequest(UUID requestId) throws CantDenyContactRequestException {

    }

    @Override
    public List<PendingContactRequest> listPendingRequests(String intraUserLoggedInPublicKey, String walletPublicKey, ContactRequestState contactRequestState) throws CantGetPendingContactRequestsListException {
        return null;
    }

    @Override
    public PendingContactRequest getPendingRequest(UUID requestId) throws CantGetPendingContactRequestException {
        return null;
    }

    @Override
    public void confirmContactRequest(UUID requestId) throws CantConfirmContactRequestException {

    }

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
