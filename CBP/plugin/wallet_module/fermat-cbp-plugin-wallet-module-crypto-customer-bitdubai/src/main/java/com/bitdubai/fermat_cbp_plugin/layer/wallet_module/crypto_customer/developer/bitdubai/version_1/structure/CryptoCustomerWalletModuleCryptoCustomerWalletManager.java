package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.interfaces.CryptoCustomerWallet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 11/11/15.
 */
public class CryptoCustomerWalletModuleCryptoCustomerWalletManager implements CryptoCustomerWallet {


    public CryptoCustomerWalletModuleCryptoCustomerWalletManager() {
    }

    @Override
    public CustomerBrokerNegotiationInformation addClause(CustomerBrokerNegotiationInformation negotiation, ClauseInformation clause) {
        return null;
    }

    @Override
    public CustomerBrokerNegotiationInformation changeClause(CustomerBrokerNegotiationInformation negotiation, ClauseInformation clause) {
        return null;
    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException {
        try {
            CustomerBrokerNegotiationInformation negotiation;
            Collection<CustomerBrokerNegotiationInformation> waitingForCustomer = new ArrayList<>();

            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("nelsonalfo", "USD", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);
            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("jorgeegonzalez", "BTC", "Cash in Hand", "USD", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);
            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("neoperol", "USD", "Cash in Hand", "BsF", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);
            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("lnacosta", "BTC", "Crypto Transfer", "LiteCoin", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);

            return waitingForCustomer;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForBrokerException(ex);
        }


    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException {
        try {
            CustomerBrokerNegotiationInformation negotiation;
            Collection<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();

            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("andreaCoronado1", "USD", "Bank Transfer", "BsF", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForBroker.add(negotiation);
            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("Customer 5", "$ Arg", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForBroker.add(negotiation);
            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("CustomerXX", "BTC", "Cash Delivery", "USD", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForBroker.add(negotiation);

            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForCustomerException(ex);
        }
    }

    @Override
    public CustomerBrokerNegotiationInformation confirmNegotiation(CustomerBrokerNegotiationInformation negotiation) {
        return null;
    }

    @Override
    public CustomerBrokerNegotiationInformation cancelNegotiation(CustomerBrokerNegotiationInformation negotiation, String reason) {
        return null;
    }

    @Override
    public List<CryptoBrokerIdentity> getListOfConnectedBrokers(UUID customerId) throws CantGetCryptoBrokerListException {
        return null;
    }

    @Override
    public void startNegotiation(UUID customerId, UUID brokerId, Collection<ClauseInformation> clauses) throws CouldNotStartNegotiationException {

    }

    @Override
    public void associateIdentity(UUID customerId) {

    }

    @Override
    public List<CryptoCustomerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException {
        return null;
    }
}
