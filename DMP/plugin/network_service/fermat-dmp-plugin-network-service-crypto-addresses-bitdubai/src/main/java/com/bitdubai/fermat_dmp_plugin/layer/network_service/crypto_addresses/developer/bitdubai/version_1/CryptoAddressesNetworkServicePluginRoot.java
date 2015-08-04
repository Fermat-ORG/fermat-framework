package com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.enums.AddressExchangeState;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantAcceptAddressExchangeException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantGetCryptoAddessException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantGetCurrentStateException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantGetPendingContactRequestsListException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantRegisterCompatibleListException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantRejectAddressExchangeException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces.PendingContactRequest;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces.RequestHandlerWallet;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
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


    /*
     * CryptoAddressesManager interface method implementation
     */
    @Override
    public void exchangeAddresses(String walletPublicKey, ReferenceWallet referenceWallet, CryptoAddress cryptoAddressSent, String intraUserToContactPublicKey, String intraUserAskingAddressName) {

    }

    @Override
    public void acceptAddressExchange(UUID exchangeId, String walletAcceptingTheRequestPublicKey, ReferenceWallet referenceWallet, CryptoAddress cryptoAddressSent, String intraUserAcceptingTheRequestPublicKey, String intraUserToInformAcceptancePublicKey) throws CantAcceptAddressExchangeException {

    }

    @Override
    public void rejectAddressExchange(UUID exchangeId, String walletThatAskedTheExchangePublicKey, String intraUserThatSentTheRequestPublicKey, String intraUserRejectingTheRequest) throws CantRejectAddressExchangeException {

    }

    @Override
    public void setCompatibleWallets(UUID requestId, List<RequestHandlerWallet> compatibleWallets) throws CantRegisterCompatibleListException {

    }

    @Override
    public List<PendingContactRequest> getPendingRequests(String intraUserLoggedInPublicKey) throws CantGetPendingContactRequestsListException {
        return null;
    }

    @Override
    public AddressExchangeState getCurrentExchangeState(String walletPublicKey, String intraUserAskingAddressPublicKey, String intraUserToContactPublicKey) throws CantGetCurrentStateException {
        return null;
    }

    @Override
    public CryptoAddress getReceivedAddress(String walletPublicKey, String intraUserAskingAddressPublicKey, String intraUserToContactPublicKey) throws CantGetCryptoAddessException {
        return null;
    }
}
