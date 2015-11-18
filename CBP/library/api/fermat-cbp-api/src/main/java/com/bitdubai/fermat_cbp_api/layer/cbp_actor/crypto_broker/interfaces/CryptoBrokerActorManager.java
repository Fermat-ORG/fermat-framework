package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TypeRequest;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateCryptoBrokerActorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetCryptoBrokerActorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantIssuingIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantUnIssuingIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Angel 17-11-15
 */

public interface CryptoBrokerActorManager {

    /* Identity management a broker */

        CryptoBrokerActor createNewCryptoBroker(ActorIdentity identity) throws CantCreateCryptoBrokerActorException;
        CryptoBrokerActor getCryptoBroker(ActorIdentity identity) throws CantGetCryptoBrokerActorException;

        void publishIdentity() throws CantIssuingIdentityException;
        void unPublishIdentity() throws CantUnIssuingIdentityException;

    /* Management of customers of broker */

        Collection<ActorIdentity> createCustomerConnection(ActorIdentity cryptoCustomer);
        Collection<ActorIdentity> getAllConnectedCustomers();
        ActorIdentity getConnectedCustomerByCustomer(ActorIdentity cryptoCustomer);

    /* Management of Associations of the Wallets of broker */

        void createAssociationCryptoBrokerIdentityWallet(CryptoBrokerActorWalletIdentity association);
        Collection<CryptoBrokerActorWalletIdentity> getAllAssociationCryptoBrokerIdentityWallet();
        CryptoBrokerActorWalletIdentity getAssociationCryptoBrokerIdentityWalletByIdentity(ActorIdentity cryptoBroker);
        CryptoBrokerActorWalletIdentity getAssociationCryptoBrokerIdentityWalletByWallet(UUID wallet);

    /* Management of Negotiations of broker */

        CustomerBrokerNegotiation createNegotiationSale(CrptoBrokerActorNegotiation negotiation) throws CantCreateSaleNegotiationException;
        CustomerBrokerNegotiation updateNegotiationSale(CrptoBrokerActorNegotiation negotiation) throws CantGetSaleNegotiationException;
        CustomerBrokerNegotiation closeNegotiationSale(CrptoBrokerActorNegotiation negotiation) throws CantGetSaleNegotiationException;
        void cancelNegotiation(CrptoBrokerActorNegotiation negotiation) throws CantUpdateCustomerBrokerSaleException;
        CustomerBrokerNegotiation               getNegotiationSale(UUID negotiationId) throws CantGetSaleNegotiationException;
        Collection<CustomerBrokerNegotiation>   getAllNegotiationSale() throws CantGetSaleNegotiationException;
        Collection<CustomerBrokerNegotiation>   getNegotiationSaleByStatus(NegotiationStatus status) throws CantGetSaleNegotiationException;

    /* Management of Contracts of broker */

        CustomerBrokerContractSale createContractSale(CryptoBrokerActorContract contract) throws CantCreateCustomerBrokerContractSaleException;
        CustomerBrokerContractSale updateContractSale(CryptoBrokerActorContract contract) throws CantupdateCustomerBrokerContractSaleException;
        CustomerBrokerContractSale               getContractSale(UUID ContractId) throws CantGetListCustomerBrokerContractSaleException;
        Collection<CustomerBrokerContractSale>   getAllContractSale() throws CantGetListCustomerBrokerContractSaleException;
        Collection<CustomerBrokerContractSale>   getContractSaleByStatus(ContractStatus status) throws CantGetListCustomerBrokerContractSaleException;


    /* Communication with the network service */

        void getPendingConnectionNews();

        void sendActorNetworkServiceNegotiationSale(CrptoBrokerActorNegotiation negotiation);
        void receiveActorNetworkServicePurchases(CrptoBrokerActorNegotiation negotiation);

        void sendActorNetworkServiceContractPurchases(CryptoBrokerActorContract negotiation);
        void receiveActorNetworkServiceContractPurchases(CryptoBrokerActorContract negotiation);

        void requestReceivedProcessed(TypeRequest type);
        void requestSentProcessed(TypeRequest type);

}
