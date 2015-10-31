package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateCryptoBrokerActorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces.CryptoBrokerActor;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces.CryptoBrokerActorManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.interfaces.DealsWithCustomerBrokerSales;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jorge on 30-10-2015.
 */
public class CryptoBrokerActorPluginRoot implements CryptoBrokerActorManager, DealsWithCustomerBrokerSales,DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, Service, Plugin {

    private ErrorManager errorManager;
    private LogManager logManager;
    private UUID pluginId;
    private ServiceStatus status;
    private CustomerBrokerSaleNegotiationManager saleNegotiationManager;

    public CryptoBrokerActorPluginRoot(){
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
    public CryptoBrokerActor createNewCryptoBroker(ActorIdentity identity) throws CantCreateCryptoBrokerActorException {
        return new CryptoBrokerActorImpl(identity, saleNegotiationManager);
    }

    @Override
    public CryptoBrokerActor getCryptoBroker(ActorIdentity identity) {
        return new CryptoBrokerActorImpl(identity, saleNegotiationManager);
    }

    @Override
    public void setCustomerBrokerSaleNegotiationManager(CustomerBrokerSaleNegotiationManager negotiationManager) {
        this.saleNegotiationManager = negotiationManager;
    }
}
