package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantClearAssociatedCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantCreateNewCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantRequestBrokerExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.RelationshipNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO please add a description
 *
 * Created by angel on 5/1/16.
 */
public final class CustomerActorManager implements ActorExtraDataManager {

    private final CryptoCustomerActorDao dao                   ;
    private final CryptoBrokerManager    cryptoBrokerANSManager;
    private final ErrorManager           errorManager          ;
    private final PluginVersionReference pluginVersionReference;

    public CustomerActorManager(final CryptoCustomerActorDao dao                   ,
                                final CryptoBrokerManager    cryptoBrokerANSManager,
                                final ErrorManager           errorManager          ,
                                final PluginVersionReference pluginVersionReference) {

        this.dao                    = dao                   ;
        this.cryptoBrokerANSManager = cryptoBrokerANSManager;
        this.errorManager           = errorManager          ;
        this.pluginVersionReference = pluginVersionReference;
    }

   /*==============================================================================================*
    *                                                                                              *
    *   Customer Identity Wallet Relationship                                                      *
    *                                                                                              *
    *==============================================================================================*/

    @Override
    public CustomerIdentityWalletRelationship createNewCustomerIdentityWalletRelationship(final ActorIdentity identity       ,
                                                                                          final String        walletPublicKey) throws CantCreateNewCustomerIdentityWalletRelationshipException {

        try {

            return this.dao.createNewCustomerIdentityWalletRelationship(identity, walletPublicKey);

        } catch (CantCreateNewCustomerIdentityWalletRelationshipException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateNewCustomerIdentityWalletRelationshipException(FermatException.wrapException(e), "identity: "+identity+" - walletPublicKey: "+walletPublicKey, "Unhandled error.");
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

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCustomerIdentityWalletRelationshipException(FermatException.wrapException(e), "", "Unhandled error.");
        }
    }

    @Override
    public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetCustomerIdentityWalletRelationshipException {

        try {

            return this.dao.getCustomerIdentityWalletRelationshipByIdentity(publicKey);

        } catch (CantGetCustomerIdentityWalletRelationshipException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCustomerIdentityWalletRelationshipException(FermatException.wrapException(e), "publicKey: "+publicKey, "Unhandled error.");
        }
    }

    @Override
    public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByWallet(final String walletPublicKey) throws CantGetCustomerIdentityWalletRelationshipException,
                                                                                                                                 RelationshipNotFoundException                      {

        try {

            return this.dao.getCustomerIdentityWalletRelationshipByWallet(walletPublicKey);

        } catch (CantGetCustomerIdentityWalletRelationshipException | RelationshipNotFoundException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCustomerIdentityWalletRelationshipException(FermatException.wrapException(e), "walletPublicKey: "+walletPublicKey, "Unhandled error.");
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

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateNewActorExtraDataException(FermatException.wrapException(e), "actorExtraData: "+actorExtraData, "Unhandled error.");
        }
    }

    @Override
    public void updateCustomerExtraData(final ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {

        try {

            this.dao.updateCustomerExtraData(actorExtraData);

        } catch (CantUpdateActorExtraDataException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateActorExtraDataException(FermatException.wrapException(e), "actorExtraData: "+actorExtraData, "Unhandled error.");
        }
    }

    @Override
    public Collection<ActorExtraData> getAllActorExtraData() throws CantGetListActorExtraDataException {

        try {

            return this.dao.getAllActorExtraData();

        } catch (CantGetListActorExtraDataException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListActorExtraDataException(FermatException.wrapException(e), "", "Unhandled error.");
        }
    }

    @Override
    public Collection<ActorExtraData> getAllActorExtraDataConnected() throws CantGetListActorExtraDataException {

        try {

            return this.dao.getAllActorExtraData();

        } catch (CantGetListActorExtraDataException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListActorExtraDataException(FermatException.wrapException(e), "", "Unhandled error.");
        }
    }

    @Override
    public ActorExtraData getActorExtraDataByIdentity(final String customerPublicKey,
                                                      final String brokerPublicKey  ) throws CantGetListActorExtraDataException {

        try {

            return this.dao.getActorExtraDataByPublicKey(customerPublicKey, brokerPublicKey);

        } catch (CantGetListActorExtraDataException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListActorExtraDataException(FermatException.wrapException(e), "brokerPublicKey: "+brokerPublicKey+" - customerPublicKey: "+customerPublicKey, "Unhandled error.");
        }
    }

    @Override
    public ActorIdentity getActorInformationByPublicKey(final String publicKeyBroker) throws CantGetListActorExtraDataException {

        try {

            return this.dao.getActorInformationByPublicKey(publicKeyBroker);

        } catch (CantGetListActorExtraDataException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetListActorExtraDataException(FermatException.wrapException(e), "publicKeyBroker: "+publicKeyBroker, "Unhandled error.");
        }
    }

    @Override
    public void requestBrokerExtraData(final ActorExtraData actorExtraData) throws CantRequestBrokerExtraDataException {

        try {

            this.createCustomerExtraData(actorExtraData);

            this.cryptoBrokerANSManager.requestQuotes(actorExtraData.getCustomerPublicKey(), Actors.CBP_CRYPTO_CUSTOMER, actorExtraData.getBrokerIdentity().getPublicKey());

        } catch (CantCreateNewActorExtraDataException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestBrokerExtraDataException(e, "actorExtraData: "+actorExtraData, "Error In Customer Actor DAO.");
        } catch (CantRequestQuotesException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestBrokerExtraDataException(e, "actorExtraData: "+actorExtraData, "Error in Broker Actor Network Service.");
        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestBrokerExtraDataException(FermatException.wrapException(e), "actorExtraData: "+actorExtraData, "Unhandled error.");
        }
    }

    @Override
    public Collection<Platforms> getPlatformsSupported(String customerPublicKey, String brokerPublicKey, String paymentCurrency) throws CantGetListActorExtraDataException {
        return this.dao.getPlatformsSupported(customerPublicKey, brokerPublicKey, paymentCurrency);
    }

}