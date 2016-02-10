package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;

import java.util.Collection;

/**
 * Created by angel on 7/02/16.
 */
public class CryptoBrokerExtraDataUpdateAgent implements Agent {

    Thread agentThread;
    MonitorAgentExtraData monitor;
    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoCustomerActorDao dao;

    public CryptoBrokerExtraDataUpdateAgent(CryptoBrokerManager cryptoBrokerANSManager,CryptoCustomerActorDao dao){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.dao = dao;
    }

    @Override
    public void start() throws CantStartAgentException {
        monitor = new MonitorAgentExtraData();

        monitor.setNS(this.cryptoBrokerANSManager);
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
            doTheMainTask();
        }
    }

    public void setNS(CryptoBrokerManager cryptoBrokerANSManager){
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
    }

    public void setDao(CryptoCustomerActorDao dao){
        this.dao = dao;
    }

    void doTheMainTask(){
        try {
            Collection<ActorExtraData> actors = this.dao.getAllActorExtraData();
            for(ActorExtraData actor : actors){
                this.cryptoBrokerANSManager.requestQuotes(actor.getCustomerPublicKey(), Actors.CBP_CRYPTO_CUSTOMER, actor.getBrokerIdentity().getPublicKey());
            }
        } catch (CantGetListActorExtraDataException e) {

        } catch (CantRequestQuotesException e) {

        }
    }

}