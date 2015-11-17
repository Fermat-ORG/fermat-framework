package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetCurrentIndexSumaryForStockCurrenciesException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.StockInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.StockStatistics;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSumaryForCurrenciesOfInterestException;

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
        try {
            CustomerBrokerNegotiationInformation negotiation;
            Collection<CustomerBrokerNegotiationInformation> waitingForCustomer = new ArrayList<>();

            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("nelsonalfo", "USD", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);
            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("jorgeegonzalez", "BTC", "Cash in Hand", "USD", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);
            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("neoperol", "USD", "Cash in Hand", "BsF", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);

            return waitingForCustomer;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForBrokerException("Cant get negotiations waiting for the broker", ex, "", "");
        }


    }

    @Override
    public Collection<CustomerBrokerNegotiationInformation> getNegotiationsWaitingForCustomer(int max, int offset) throws CantGetNegotiationsWaitingForCustomerException {
        try {
            CustomerBrokerNegotiationInformation negotiation;
            Collection<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();

            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("Nelson Orlando", "USD", "Bank Transfer", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForBroker.add(negotiation);
            negotiation = new CryptoBrokerWalletModuleCustomerBrokerNegotationInformation("Customer 5", "BsF", "Cash Delivery", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForBroker.add(negotiation);

            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetNegotiationsWaitingForCustomerException("Cant get negotiations waiting for the customers", ex, "", "");
        }
    }

    @Override
    public Collection<ContractBasicInformation> getContractsWaitingForBroker(int max, int offset) throws CantGetContractsWaitingForBrokerException {
        try {
            ContractBasicInformation contract;
            Collection<ContractBasicInformation> waitingForBroker = new ArrayList<>();

            contract = new CryproBrokerWalletModuleContractBasicInformation("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
            waitingForBroker.add(contract);

            return waitingForBroker;

        } catch (Exception ex) {
            throw new CantGetContractsWaitingForBrokerException("Cant get contracts waiting for the broker", ex);
        }
    }

    @Override
    public Collection<ContractBasicInformation> getContractsWaitingForCustomer(int max, int offset) throws CantGetContractsWaitingForCustomerException {
        try {
            ContractBasicInformation contract;
            Collection<ContractBasicInformation> waitingForCustomer = new ArrayList<>();

            contract = new CryproBrokerWalletModuleContractBasicInformation("yalayn", "BTC", "Bank Transfer", "USD", ContractStatus.PENDING_PAYMENT);
            waitingForCustomer.add(contract);
            contract = new CryproBrokerWalletModuleContractBasicInformation("vzlangel", "BsF", "Cash Delivery", "BsF", ContractStatus.PENDING_PAYMENT);
            waitingForCustomer.add(contract);

            return waitingForCustomer;

        } catch (Exception ex) {
            throw new CantGetContractsWaitingForCustomerException("Cant get contracts waiting for the customers", ex);
        }


    }

    @Override
    public Collection<IndexInfoSummary> getCurrentIndexSummaryForStockCurrencies() throws CantGetCurrentIndexSumaryForStockCurrenciesException {
        try {
            IndexInfoSummary indexInfoSummary;
            Collection<IndexInfoSummary> summaryList = new ArrayList<>();

            indexInfoSummary = new CryptoBrokerWalletModuleIndexInfoSummary(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR, 240.62, 235.87);
            summaryList.add(indexInfoSummary);
            indexInfoSummary = new CryptoBrokerWalletModuleIndexInfoSummary(FiatCurrency.VENEZUELAN_BOLIVAR, CryptoCurrency.BITCOIN, 245000, 240000);
            summaryList.add(indexInfoSummary);
            indexInfoSummary = new CryptoBrokerWalletModuleIndexInfoSummary(FiatCurrency.VENEZUELAN_BOLIVAR, FiatCurrency.US_DOLLAR, 840, 800);
            summaryList.add(indexInfoSummary);
            indexInfoSummary = new CryptoBrokerWalletModuleIndexInfoSummary(FiatCurrency.US_DOLLAR, FiatCurrency.EURO, 1.2, 1.1);
            summaryList.add(indexInfoSummary);

            return summaryList;

        } catch (Exception ex) {
            throw new CantGetCurrentIndexSumaryForStockCurrenciesException(ex);
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
