package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.agent.CBPTransactionAgent;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeCBPAgent;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Yordin Alayn on 08.12.15.
 */
public class CustomerBrokerNewAgent implements
        CBPTransactionAgent,
        DealsWithLogger,
        DealsWithEvents,
        DealsWithErrors,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginIdentity{


    private Thread                              agentThread;

    private LogManager                          logManager;

    private EventManager                        eventManager;

    private ErrorManager                        errorManager;

    private PluginDatabaseSystem                pluginDatabaseSystem;

    private UUID                                pluginId;

    /*Represent the Network Service*/
    private NegotiationTransmissionManager      negotiationTransmissionManager;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiation   customerBrokerPurchaseNegotiation;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiation       customerBrokerSaleNegotiation;

    public CustomerBrokerNewAgent(
            LogManager                          logManager,
            ErrorManager                        errorManager,
            EventManager                        eventManager,
            UUID                                pluginId,
            NegotiationTransmissionManager      negotiationTransmissionManager,
            CustomerBrokerPurchaseNegotiation   customerBrokerPurchaseNegotiation,
            CustomerBrokerSaleNegotiation       customerBrokerSaleNegotiation
    ){
        this.logManager                         = logManager;
        this.errorManager                       = errorManager;
        this.eventManager                       = eventManager;
        this.pluginId                           = pluginId;
        this.negotiationTransmissionManager     = negotiationTransmissionManager;
        this.customerBrokerPurchaseNegotiation  = customerBrokerPurchaseNegotiation;
        this.customerBrokerSaleNegotiation      = customerBrokerSaleNegotiation;
    }

    /*IMPLEMENTATION CBPTransactionAgent*/
    @Override
    public void start() throws CantStartAgentException {

        Logger LOG = Logger.getGlobal();
        LOG.info("CUSTMER BROKER NEW AGENT STARTING...");
        monitorAgent = new MonitorAgent();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        } catch (CantInitializeCBPAgent exception) {
            errorManager.reportUnexpectedPluginException(Plugins.OPEN_CONTRACT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();

    }

    @Override
    public void stop() { this.agentThread.interrupt(); }

    /*IMPLEMENTATION DealsWithErrors*/
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    /*IMPLEMENTATION DealsWithEvents*/
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager=eventManager;
    }

    /*IMPLEMENTATION DealsWithLogger*/
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager=logManager;
    }

    /*IMPLEMENTATION DealsWithPluginDatabaseSystem*/
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) { this.pluginDatabaseSystem=pluginDatabaseSystem; }

    /*IMPLEMENTATION DealsWithPluginIdentity*/
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId=pluginId;
    }


}
