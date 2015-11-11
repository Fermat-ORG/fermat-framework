package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.StockInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.StockStatistics;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Module Manager of Crypto Broker Module Plugin
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/15
 */
public class CryptoBrokerWalletModuleCryptoBrokerWalletManager implements CryptoBrokerWallet {

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
        CustomerBrokerNegotiationInformation child;
        List<CustomerBrokerNegotiationInformation> waitingForCustomer = new ArrayList<>();

        child = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("nelsonalfo", "USD", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_BROKER);
        waitingForCustomer.add(child);
        child = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("jorgeegonzalez", "BTC", "Cash in Hand", "USD", NegotiationStatus.WAITING_FOR_BROKER);
        waitingForCustomer.add(child);
        child = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("neoperol", "USD", "Cash in Hand", "BsF", NegotiationStatus.WAITING_FOR_BROKER);
        waitingForCustomer.add(child);

        return waitingForCustomer;
    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException {
        try {
            CustomerBrokerNegotiationInformation child;

            List<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
            child = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("Nelson Orlando", "USD", "Bank Transfer", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForBroker.add(child);
            child = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("Customer 5", "BsF", "Cash Delivery", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForBroker.add(child);

            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForCustomerException("Cant get negotiations waiting for the customers", ex, "", "");
        }
    }

    @Override
    public Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset) throws CantGetNegotiationsWaitingForBrokerException {
        List<ContractBasicInformation> waitingForBroker = new ArrayList<>();
        ContractBasicInformation child = new CryproBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
        waitingForBroker.add(child);

        return waitingForBroker;
    }

    @Override
    public Collection<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException {
        List<ContractBasicInformation> waitingForCustomer = new ArrayList<>();
        ContractBasicInformation child = new CryproBrokerWalletModuleContractBasicInformation("yalayn", "BTC", "Bank Transfer", "USD", ContractStatus.PENDING_PAYMENT);
        waitingForCustomer.add(child);
        child = new CryproBrokerWalletModuleContractBasicInformation("vzlangel", "BsF", "Cash Delivery", "BsF", ContractStatus.PENDING_PAYMENT);
        waitingForCustomer.add(child);

        return waitingForCustomer;
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
    public StockInformation getCurrentStock(String stockCurrency) {
        return null;
    }

    @Override
    public StockStatistics getStockStatistics(String stockCurrency) {
        return null;
    }

    @Override
    public void associateIdentity(UUID brokerId) {

    }

    @Override
    public List<CryptoBrokerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException {
        return null;
    }
}
