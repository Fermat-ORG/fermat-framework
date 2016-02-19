package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantSendActorNetworkServiceException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 5/1/16.
 */
public class CustomerActorManager implements ActorExtraDataManager {

    private final CryptoCustomerActorDao dao;
    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;

    public CustomerActorManager(
            final CryptoCustomerActorDao dao,
            final ErrorManager errorManager,
            final PluginVersionReference pluginVersionReference
    ){
        this.dao = dao;
        this.pluginVersionReference = pluginVersionReference;
        this.errorManager = errorManager;
    }

    /*==============================================================================================
    *
    *   Customer Identity Wallet Relationship
    *
    *==============================================================================================*/


        @Override
        public CustomerIdentityWalletRelationship createNewCustomerIdentityWalletRelationship(ActorIdentity identity, String walletPublicKey) throws CantCreateNewCustomerIdentityWalletRelationshipException {
            try {
                return this.dao.createNewCustomerIdentityWalletRelationship(identity, walletPublicKey);
            } catch (CantCreateNewCustomerIdentityWalletRelationshipException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateNewCustomerIdentityWalletRelationshipException(e.getMessage(), e,
                        "identity.getPublicKey()= "+identity.getPublicKey()+
                                "walletPublicKey= "+walletPublicKey
                        ,
                        "Cant Create New Customer Identity - Wallet Relationship"
                );
            }
        }

        @Override
        public Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationship() throws CantGetListCustomerIdentityWalletRelationshipException {
            try {
                return this.dao.getAllCustomerIdentityWalletRelationship();
            } catch (CantGetListCustomerIdentityWalletRelationshipException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListCustomerIdentityWalletRelationshipException(e.getMessage(), e, "", "Failed to get records database");
            }
        }

        @Override
        public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetListCustomerIdentityWalletRelationshipException {
            try {
                return this.dao.getCustomerIdentityWalletRelationshipByIdentity(publicKey);
            } catch (CantGetListCustomerIdentityWalletRelationshipException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListCustomerIdentityWalletRelationshipException(e.getMessage(), e, "", "Failed to get records database");
            }
        }

        @Override
        public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByWallet(String walletPublicKey) throws CantGetListCustomerIdentityWalletRelationshipException {
            try {
                return this.dao.getCustomerIdentityWalletRelationshipByWallet(walletPublicKey);
            } catch (CantGetListCustomerIdentityWalletRelationshipException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListCustomerIdentityWalletRelationshipException(e.getMessage(), e, "", "Failed to get records database");
            }
        }

    /*==============================================================================================
    *
    *   Actor Extra Data
    *
    *==============================================================================================*/

        @Override
        public void createCustomerExtraData(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException {
            try {
                this.dao.createCustomerExtraData(actorExtraData);
            } catch (CantCreateNewActorExtraDataException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateNewActorExtraDataException(e.getMessage(), e, "", "Cant Create New Actor Extra Data");
            }
        }

        @Override
        public void updateCustomerExtraData(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {
            try {
                this.dao.updateCustomerExtraData(actorExtraData);
            } catch (CantUpdateActorExtraDataException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantUpdateActorExtraDataException(e.getMessage(), e, "", "Cant Update Actor Extra Data");
            }
        }

        @Override
        public Collection<ActorExtraData> getAllActorExtraDataConnected() throws CantGetListActorExtraDataException {
            try {
                return this.dao.getAllActorExtraData();
            } catch (CantGetListActorExtraDataException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListActorExtraDataException(e.getMessage(), e, "", "Failed to get records database");
            }
        }

        @Override
        public ActorExtraData getActorExtraDataByIdentity(String customerPublicKey, String brokerPublicKey) throws CantGetListActorExtraDataException {
            try {
                return this.dao.getActorExtraDataByPublicKey(customerPublicKey, brokerPublicKey);
            } catch (CantGetListActorExtraDataException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListActorExtraDataException(e.getMessage(), e, "", "Failed to get records database");
            }
        }

        @Override
        public ActorIdentity getActorInformationByPublicKey(String publicKeyBroker) throws CantGetListActorExtraDataException {
            try {
                return this.dao.getActorInformationByPublicKey(publicKeyBroker);
            } catch (CantGetListActorExtraDataException e) {
                this.errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetListActorExtraDataException(e.getMessage(), e, "", "Failed to get records database");
            }
        }

        @Override
        public Collection<Platforms> getPlatformsSupport(String CustomerPublicKey, Currency currency) throws CantGetListPlatformsException {
            return null;
        }
}