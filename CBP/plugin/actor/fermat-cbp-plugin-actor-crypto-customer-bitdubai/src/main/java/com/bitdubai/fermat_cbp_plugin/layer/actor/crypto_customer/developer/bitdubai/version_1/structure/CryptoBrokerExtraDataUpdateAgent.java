package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by angel on 7/02/16.
 */
public class CryptoBrokerExtraDataUpdateAgent extends AbstractAgent {

    private CryptoBrokerManager cryptoBrokerANSManager;
    private CryptoCustomerActorDao dao;
    private CryptoCustomerActorPluginRoot pluginRoot;
    private final PluginVersionReference pluginVersionReference;

    public CryptoBrokerExtraDataUpdateAgent(
            final long sleepTime,
            final TimeUnit timeUnit,
            final CryptoBrokerManager cryptoBrokerANSManager,
            final CryptoCustomerActorDao dao,
            final CryptoCustomerActorPluginRoot pluginRoot,
            final PluginVersionReference pluginVersionReference
    ) {
        super(sleepTime, timeUnit);
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.dao = dao;
        this.pluginRoot = pluginRoot;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    protected Runnable agentJob() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doTheMainTask();
            }
        };
        return runnable;
    }

    @Override
    protected void onErrorOccur() {
        pluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                new Exception(new StringBuilder().append(this.getClass().getName()).append(" Error").toString()));
    }

    void doTheMainTask() {
        try {
            Collection<ActorExtraData> actors = this.dao.getAllActorExtraData();
            for (ActorExtraData actor : actors) {
                if (actor.getQuotes() != null) {
                    this.cryptoBrokerANSManager.requestQuotes(actor.getCustomerPublicKey(), Actors.CBP_CRYPTO_CUSTOMER, actor.getBrokerIdentity().getPublicKey());
                }
            }
        } catch (CantGetListActorExtraDataException | CantRequestQuotesException e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            e.printStackTrace();
        }
    }
}