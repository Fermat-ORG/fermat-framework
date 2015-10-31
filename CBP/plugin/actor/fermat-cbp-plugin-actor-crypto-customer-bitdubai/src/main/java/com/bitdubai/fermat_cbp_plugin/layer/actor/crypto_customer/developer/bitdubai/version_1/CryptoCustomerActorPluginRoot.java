package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CryptoCustomerActorManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces.DealsWithCustomerBrokerPurchases;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jorge on 30-10-2015.
 */
public class CryptoCustomerActorPluginRoot implements CryptoCustomerActorManager,DealsWithCustomerBrokerPurchases,DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, Service, Plugin {

    private ErrorManager errorManager;
    private LogManager logManager;
    private UUID pluginId;
    private ServiceStatus status;
    private CustomerBrokerPurchaseNegotiationManager purchaseNegotiationManager;

    public CryptoCustomerActorPluginRoot(){
        this.pluginId = UUID.randomUUID();
        this.status = ServiceStatus.CREATED;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void start() throws CantStartPluginException {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public ServiceStatus getStatus() {
        return this.status;
    }

    @Override
    public void setCustomerBrokerPurchaseNegotiationManager(CustomerBrokerPurchaseNegotiationManager purchaseNegotiationManager) {
        this.purchaseNegotiationManager = purchaseNegotiationManager;
    }

    @Override
    public CryptoCustomerActor createNewCryptoCustomerActor(ActorIdentity identity) throws CantCreateCryptoCustomerActorException {
        return new CryptoCustomerActorImpl(identity, purchaseNegotiationManager);
    }

    @Override
    public CryptoCustomerActor getCryptoCustomer(ActorIdentity identity) {
        return new CryptoCustomerActorImpl(identity, purchaseNegotiationManager);
    }
}
