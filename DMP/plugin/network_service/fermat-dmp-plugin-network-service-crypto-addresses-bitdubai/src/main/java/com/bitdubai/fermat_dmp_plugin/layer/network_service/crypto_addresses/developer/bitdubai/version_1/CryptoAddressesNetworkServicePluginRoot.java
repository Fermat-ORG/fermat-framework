package com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
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
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventListener;
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
    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;
    ErrorManager errorManager;
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
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }

    @Override
    public void acceptContactRequest(UUID requestId, String walletAcceptingTheRequestPublicKey, ReferenceWallet referenceWallet, CryptoAddress cryptoAddressReceived, String intraUserAcceptingTheRequestPublicKey) throws CantAcceptContactRequestException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }

    @Override
    public void denyContactRequest(UUID requestId) throws CantDenyContactRequestException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }

    @Override
    public List<PendingContactRequest> listPendingRequests(String intraUserLoggedInPublicKey, String walletPublicKey, ContactRequestState contactRequestState) throws CantGetPendingContactRequestsListException {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public PendingContactRequest getPendingRequest(UUID requestId) throws CantGetPendingContactRequestException {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public void confirmContactRequest(UUID requestId) throws CantConfirmContactRequestException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */
        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;
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

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
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
        this.errorManager = errorManager;
    }


    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


}
