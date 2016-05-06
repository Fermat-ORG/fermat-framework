package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantAgentExtraDataUpdateException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Collection;

/**
 * Created by angel on 7/02/16.
 */
public class CryptoBrokerExtraDataUpdateAgent implements Agent {

    Thread agentThread;
    MonitorAgentExtraData monitor;
    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoCustomerActorDao dao;
    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;

    public CryptoBrokerExtraDataUpdateAgent(
            final CryptoBrokerManager cryptoBrokerANSManager,
            final CryptoCustomerActorDao dao,
            final ErrorManager errorManager,
            final PluginVersionReference pluginVersionReference
    ){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.dao = dao;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public void start() throws CantStartAgentException {
        monitor = new MonitorAgentExtraData();

        monitor.setManagers(
            this.cryptoBrokerANSManager,
            this.errorManager,
            this.pluginVersionReference
        );
        monitor.setDao(this.dao);

        this.agentThread = new Thread(monitor);
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }
}

class MonitorAgentExtraData implements Runnable {

    boolean          threadWorking;
    public final int SLEEP_TIME = 43200000; // Cada 12 horas
    int              iterationNumber = 0;

    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoCustomerActorDao dao;
    private ErrorManager errorManager;
    private PluginVersionReference pluginVersionReference;

    @Override
    public void run() {
        threadWorking=true;
        while(threadWorking){
            iterationNumber++;
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException interruptedException) {
                return;
            }
            try {
                doTheMainTask();
            } catch (CantAgentExtraDataUpdateException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    public void setManagers(
            CryptoBrokerManager cryptoBrokerANSManager,
            ErrorManager errorManager,
            PluginVersionReference pluginVersionReference
    ){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    public void setDao(CryptoCustomerActorDao dao){
        this.dao = dao;
    }

    void doTheMainTask() throws CantAgentExtraDataUpdateException {
        try {
            Collection<ActorExtraData> actors = this.dao.getAllActorExtraData();
            for(ActorExtraData actor : actors){
                if( actor.getQuotes() != null ){
                    this.cryptoBrokerANSManager.requestQuotes(actor.getCustomerPublicKey(), Actors.CBP_CRYPTO_CUSTOMER, actor.getBrokerIdentity().getPublicKey());
                }
            }
        } catch (CantGetListActorExtraDataException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAgentExtraDataUpdateException(e.getMessage(), e, "", "");
        } catch (CantRequestQuotesException e) {
            this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAgentExtraDataUpdateException(e.getMessage(), e, "", "");
        }
    }

}