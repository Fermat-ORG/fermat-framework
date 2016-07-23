package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantClearAssociatedCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantRequestBrokerExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.RelationshipNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantCheckIfExistsException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantGetBrokersConnectedException;

import java.util.Collection;
import java.util.List;


/**
 * TODO please add a description
 * <p/>
 * Created by angel on 5/1/16.
 */
public final class CustomerActorManager implements ActorExtraDataManager {

    private final CryptoCustomerActorDao dao;
    private final CryptoBrokerManager cryptoBrokerANSManager;
    private final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager;
    private final CryptoCustomerActorPluginRoot pluginRoot;

    public CustomerActorManager(final CryptoCustomerActorDao dao,
                                final CryptoBrokerManager cryptoBrokerANSManager,
                                final CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager,
                                final CryptoCustomerActorPluginRoot pluginRoot) {

        this.dao = dao;
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.cryptoBrokerActorConnectionManager = cryptoBrokerActorConnectionManager;
        this.pluginRoot = pluginRoot;
    }

   /*==============================================================================================*
    *                                                                                              *
    *   Customer Identity Wallet Relationship                                                      *
    *                                                                                              *
    *==============================================================================================*/

    @Override
    public CustomerIdentityWalletRelationship createNewCustomerIdentityWalletRelationship(final ActorIdentity identity,
                                                                                          final String walletPublicKey) throws CantCreateNewCustomerIdentityWalletRelationshipException {

        try {

            return this.dao.createNewCustomerIdentityWalletRelationship(identity, walletPublicKey);

        } catch (CantCreateNewCustomerIdentityWalletRelationshipException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateNewCustomerIdentityWalletRelationshipException(FermatException.wrapException(e), new StringBuilder().append("identity: ").append(identity).append(" - walletPublicKey: ").append(walletPublicKey).toString(), "Unhandled error.");
        }
    }


    public void clearAssociatedCustomerIdentityWalletRelationship(String walletPublicKey) throws CantClearAssociatedCustomerIdentityWalletRelationshipException {
        this.dao.clearAssociatedCustomerIdentityWalletRelationship(walletPublicKey);
    }

    @Override
    public Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationship() throws CantGetCustomerIdentityWalletRelationshipException {

        try {

            return this.dao.getAllCustomerIdentityWalletRelationship();

        } catch (CantGetCustomerIdentityWalletRelationshipException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCustomerIdentityWalletRelationshipException(FermatException.wrapException(e), "", "Unhandled error.");
        }
    }

    @Override
    public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetCustomerIdentityWalletRelationshipException {

        try {

            return this.dao.getCustomerIdentityWalletRelationshipByIdentity(publicKey);

        } catch (CantGetCustomerIdentityWalletRelationshipException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCustomerIdentityWalletRelationshipException(FermatException.wrapException(e), new StringBuilder().append("publicKey: ").append(publicKey).toString(), "Unhandled error.");
        }
    }

    @Override
    public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByWallet(final String walletPublicKey) throws CantGetCustomerIdentityWalletRelationshipException,
            RelationshipNotFoundException {

        try {

            return this.dao.getCustomerIdentityWalletRelationshipByWallet(walletPublicKey);

        } catch (CantGetCustomerIdentityWalletRelationshipException | RelationshipNotFoundException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCustomerIdentityWalletRelationshipException(FermatException.wrapException(e), new StringBuilder().append("walletPublicKey: ").append(walletPublicKey).toString(), "Unhandled error.");
        }
    }

   /*==============================================================================================*
    *                                                                                              *
    *   Actor Extra Data                                                                           *
    *                                                                                              *
    *==============================================================================================*/

    @Override
    public void createCustomerExtraData(final ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException {

        try {

            this.dao.createCustomerExtraData(actorExtraData);

        } catch (CantCreateNewActorExtraDataException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateNewActorExtraDataException(FermatException.wrapException(e), new StringBuilder().append("actorExtraData: ").append(actorExtraData).toString(), "Unhandled error.");
        }
    }

    @Override
    public void updateCustomerExtraData(final ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {

        try {

            this.dao.updateCustomerExtraData(actorExtraData);

        } catch (CantUpdateActorExtraDataException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateActorExtraDataException(FermatException.wrapException(e), new StringBuilder().append("actorExtraData: ").append(actorExtraData).toString(), "Unhandled error.");
        }
    }

    @Override
    public Collection<ActorExtraData> getAllActorExtraData() throws CantGetListActorExtraDataException {

        try {

            return this.dao.getAllActorExtraData();

        } catch (CantGetListActorExtraDataException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListActorExtraDataException(FermatException.wrapException(e), "", "Unhandled error.");
        }
    }

    @Override
    public Collection<ActorExtraData> getAllActorExtraDataConnected() throws CantGetListActorExtraDataException {

        try {

            return this.dao.getAllActorExtraData();

        } catch (CantGetListActorExtraDataException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListActorExtraDataException(FermatException.wrapException(e), "", "Unhandled error.");
        }
    }

    @Override
    public ActorExtraData getActorExtraDataByIdentity(final String customerPublicKey,
                                                      final String brokerPublicKey) throws CantGetListActorExtraDataException {

        try {

            return this.dao.getActorExtraDataByPublicKey(customerPublicKey, brokerPublicKey);

        } catch (CantGetListActorExtraDataException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListActorExtraDataException(FermatException.wrapException(e), new StringBuilder().append("brokerPublicKey: ").append(brokerPublicKey).append(" - customerPublicKey: ").append(customerPublicKey).toString(), "Unhandled error.");
        }
    }

    @Override
    public ActorIdentity getActorInformationByPublicKey(final String publicKeyBroker) throws CantGetListActorExtraDataException {

        try {

            return this.dao.getActorInformationByPublicKey(publicKeyBroker);

        } catch (CantGetListActorExtraDataException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListActorExtraDataException(FermatException.wrapException(e), new StringBuilder().append("publicKeyBroker: ").append(publicKeyBroker).toString(), "Unhandled error.");
        }
    }

    @Override
    public void requestBrokerExtraData() throws CantRequestBrokerExtraDataException {

        try {

            Collection<CustomerIdentityWalletRelationship> relationships = dao.getAllCustomerIdentityWalletRelationship();

            for (CustomerIdentityWalletRelationship relationship : relationships) {

                List<CryptoBrokerActorConnection> connections = getBrokersConnected(relationship);

                for (CryptoBrokerActorConnection broker : connections) {

                    if (!this.dao.existBrokerExtraData(relationship.getCryptoCustomer(), broker.getPublicKey())) {

                        if (!this.dao.existBrokerExtraData(broker.getPublicKey(), relationship.getCryptoCustomer())) {

                            ActorIdentity brokerIdentity = new ActorExtraDataIdentity(broker.getAlias(), broker.getPublicKey(), broker.getImage(), 0, GeoFrequency.NONE);

                            this.dao.createCustomerExtraData(new ActorExtraDataInformation(relationship.getCryptoCustomer(), brokerIdentity, null, null));
                        }

                        this.cryptoBrokerANSManager.requestQuotes(relationship.getCryptoCustomer(), Actors.CBP_CRYPTO_CUSTOMER, broker.getPublicKey());
                    }
                }
            }

        } catch (CantGetCustomerIdentityWalletRelationshipException e) {

            throw new CantRequestBrokerExtraDataException(e, "", "Error trying to get the list of customer-wallet relationships.");
        } catch (CantGetBrokersConnectedException e) {

            throw new CantRequestBrokerExtraDataException(e, "", "Error trying to get the list of brokers connected to that customer.");
        } catch (CantCheckIfExistsException e) {

            throw new CantRequestBrokerExtraDataException(e, "", "Error in DAO trying to check if the extra data for the broker exists.");
        } catch (CantCreateNewActorExtraDataException e) {

            throw new CantRequestBrokerExtraDataException(e, "", "Error trying to create a new actor extra data for requesting the quotes.");
        } catch (CantRequestQuotesException e) {

            throw new CantRequestBrokerExtraDataException(e, "", "Cant request quotes through the network service.");
        } catch (Exception e) {

            throw new CantRequestBrokerExtraDataException(e, "", "Unhandled error.");
        }

    }

    private List<CryptoBrokerActorConnection> getBrokersConnected(CustomerIdentityWalletRelationship relationship) throws CantGetBrokersConnectedException {

        try {

            CryptoBrokerLinkedActorIdentity linkedActorIdentity = new CryptoBrokerLinkedActorIdentity(
                    relationship.getCryptoCustomer(),
                    Actors.CBP_CRYPTO_CUSTOMER
            );

            final CryptoBrokerActorConnectionSearch search = cryptoBrokerActorConnectionManager.getSearch(linkedActorIdentity);
            search.addConnectionState(ConnectionState.CONNECTED);

            return search.getResult();

        } catch (CantListActorConnectionsException e) {

            throw new CantGetBrokersConnectedException(e, "", "Error trying to list the broker connections of the customer.");
        }
    }

    @Override
    public Collection<Platforms> getPlatformsSupported(String customerPublicKey, String brokerPublicKey, String paymentCurrency) throws CantGetListActorExtraDataException {
        return this.dao.getPlatformsSupported(customerPublicKey, brokerPublicKey, paymentCurrency);
    }

}