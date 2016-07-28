package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantClearBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetRelationBetweenBrokerIdentityAndBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorExtraDataManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDao;

import java.util.Collection;


/**
 * Created by angel on 5/1/16.
 */
public class BrokerActorManager implements CryptoBrokerActorExtraDataManager {

    private final CryptoBrokerActorDao dao;
    private final CryptoBrokerActorPluginRoot pluginRoot;
    private final PluginVersionReference pluginVersionReference;

    public BrokerActorManager(CryptoBrokerActorDao dao, CryptoBrokerActorPluginRoot pluginRoot, PluginVersionReference pluginVersionReference) {
        this.dao = dao;
        this.pluginVersionReference = pluginVersionReference;
        this.pluginRoot = pluginRoot;
    }

    /*==============================================================================================
    *
    *   Broker Identity Wallet Relationship
    *
    *==============================================================================================*/

    @Override
    public BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, String walletPublicKey) throws CantCreateNewBrokerIdentityWalletRelationshipException {
        try {
            return this.dao.createNewBrokerIdentityWalletRelationship(identity, walletPublicKey);
        } catch (CantCreateNewBrokerIdentityWalletRelationshipException e) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateNewBrokerIdentityWalletRelationshipException(e.getMessage(), e,
                    new StringBuilder().append("identity.getPublicKey()= ").append(identity.getPublicKey()).append("walletPublicKey= ").append(walletPublicKey).toString()
                    ,
                    "Invalid Data"
            );
        }
    }

    @Override
    public void clearBrokerIdentityWalletRelationship(String walletPublicKey) throws CantClearBrokerIdentityWalletRelationshipException {
        try {
            this.dao.clearBrokerIdentityWalletRelationship(walletPublicKey);
        } catch (CantClearBrokerIdentityWalletRelationshipException e) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantClearBrokerIdentityWalletRelationshipException(e.getMessage(), e, new StringBuilder().append("walletPublicKey= ").append(walletPublicKey).toString(), "");
        }
    }


    @Override
    public Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException {
        try {
            return this.dao.getAllBrokerIdentityWalletRelationship();
        } catch (CantGetRelationBetweenBrokerIdentityAndBrokerWalletException e) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetRelationBetweenBrokerIdentityAndBrokerWalletException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException {
        try {
            return this.dao.getBrokerIdentityWalletRelationshipByIdentity(publicKey);
        } catch (CantGetRelationBetweenBrokerIdentityAndBrokerWalletException e) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetRelationBetweenBrokerIdentityAndBrokerWalletException(e.getMessage(), e, "", "Failed to get records database");
        }
    }

    @Override
    public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(String walletPublicKey) throws CantGetRelationBetweenBrokerIdentityAndBrokerWalletException {
        try {
            return this.dao.getBrokerIdentityWalletRelationshipByWallet(walletPublicKey);
        } catch (CantGetRelationBetweenBrokerIdentityAndBrokerWalletException e) {
            this.pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetRelationBetweenBrokerIdentityAndBrokerWalletException(e.getMessage(), e, "", "Failed to get records database");
        }
    }
}