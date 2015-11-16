package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantClosePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantDeleteCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdateStatusPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 15.11.15.
 */
public class CryptoCustomerActorDatabaseDao {
    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    public CryptoCustomerActorDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    Database database;

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCryptoCustomerActorDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CryptoCustomerActorDatabaseFactory databaseFactory = new CryptoCustomerActorDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCryptoCustomerActorDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCryptoCustomerActorDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCryptoCustomerActorDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCryptoCustomerActorDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    //### RELATIONSHIP ###
    //CREATE RELATIONSHIP
    public CustomerIdentityWalletRelationship createCustomerIdentityWalletRelationship(CryptoCustomerIdentity identity, UUID Wallet) throws CantCreateCustomerIdentiyWalletRelationshipException{
        return null;
    }

    //UPDATE RELATIONSHIP
    public CustomerIdentityWalletRelationship updateCustomerIdentityWalletRelationship(UUID RelationshipId, CryptoCustomerIdentity identity, UUID Wallet) throws CantUpdateCustomerIdentiyWalletRelationshipException{
        return null;
    }

    //DELETE RELATIONSHIP
    public CustomerIdentityWalletRelationship deleteCustomerIdentityWalletRelationship(UUID RelationshipId) throws CantDeleteCustomerIdentiyWalletRelationshipException{
        return null;
    }

    //GET RELATIONSHIP COLLECTION
    public Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationships() throws CantGetCustomerIdentiyWalletRelationshipException{
        return null;
    }

    //GET RELATIONSHIP ID
    public CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationships(UUID RelationshipId) throws CantGetCustomerIdentiyWalletRelationshipException{
        return null;
    }

    //GET RELATIONSHIP IDENTITY
    public CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByIdentity(CryptoCustomerIdentity identity) throws CantGetCustomerIdentiyWalletRelationshipException{
        return null;
    }

    //GET RELATIONSHIP WALLET
    public CustomerIdentityWalletRelationship getAllCustomerIdentityWalletRelationshipsByWallet(UUID Wallet) throws CantGetCustomerIdentiyWalletRelationshipException{
        return null;
    }


    //### NEGOTIATION
    //CREATE NEGORIATION
    public CustomerBrokerNegotiation createNegotiationPurchase(ActorIdentity cryptoBroker,Collection<Clause> clauses) throws CantCreatePurchaseNegotiationException{
        return null;
    }

    //UPDATE NEGOTIATION
    public CustomerBrokerNegotiation updateNegotiationPurchase(CustomerBrokerNegotiation negotiation) throws CantUpdatePurchaseNegotiationException{
        return null;
    }

    //CLOASE NEGOTIATION
    public CustomerBrokerNegotiation closeNegotiationPurchase(UUID negotiationId) throws CantClosePurchaseNegotiationException{
        return null;
    }

    //GET NEGOTIATION ID
    public CustomerBrokerNegotiation getNegotiationPurchase(UUID negotiationId) throws CantGetPurchaseNegotiationException{
        return null;
    }

    //GET NEGOTIATION COLLECTION
    public Collection<CustomerBrokerNegotiation> getNegotiationPurchases() throws CantGetPurchaseNegotiationException{
        return null;
    }

    //GET NEGOTIATION COLLECTION STATUS
    public Collection<CustomerBrokerNegotiation> getNegotiationPurchases(NegotiationStatus status) throws CantGetPurchaseNegotiationException{
        return null;
    }

    //### CONTRACT
    //CREATE CONTRACT
    public CustomerBrokerContractPurchase createContractPurchase(ActorIdentity cryptoBroker,Collection<Clause> clauses) throws CantCreatePurchaseContractException{
        return null;
    }

    //UPDATE STATUS CONTRACT
    public CustomerBrokerContractPurchase updateContractPurchase(UUID contractId) throws CantUpdateStatusPurchaseContractException{
        return null;
    }

    //GET CONTRACT
    public Collection<CustomerBrokerContractPurchase> getContractPurchases() throws CantGetPurchaseContractException{
        return null;
    }

    //GET CONTRACT
    public CustomerBrokerContractPurchase getContractPurchase(UUID negotiationId) throws CantGetPurchaseContractException{
        return null;
    }

    //GET CONTRACT COLLECTION STATUS
    public Collection<CustomerBrokerContractPurchase> getContractPurchases(NegotiationStatus status) throws CantGetPurchaseContractException{
        return null;
    }

    //OTHERS

}
