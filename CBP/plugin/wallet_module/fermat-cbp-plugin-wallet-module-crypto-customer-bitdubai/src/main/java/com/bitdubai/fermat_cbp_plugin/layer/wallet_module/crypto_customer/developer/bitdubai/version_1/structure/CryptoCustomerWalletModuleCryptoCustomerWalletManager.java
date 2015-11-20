package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSummaryForCurrenciesOfInterestException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWallet;

import java.util.ArrayList;
import java.util.Collection;
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

            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotationInformation("nelsonalfo", "USD", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotationInformation("jorgeegonzalez", "BTC", "Cash in Hand", "USD", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotationInformation("neoperol", "USD", "Cash in Hand", "BsF", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForCustomer.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotationInformation("lnacosta", "BTC", "Crypto Transfer", "LiteCoin", NegotiationStatus.WAITING_FOR_BROKER);
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

            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotationInformation("andreaCoronado1", "USD", "Bank Transfer", "BsF", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForBroker.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotationInformation("Customer 5", "$ Arg", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForBroker.add(negotiation);
            negotiation = new CryptoCustomerWalletModuleCustomerBrokerNegotationInformation("CustomerXX", "BTC", "Cash Delivery", "USD", NegotiationStatus.WAITING_FOR_CUSTOMER);
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
    public Collection<CryptoBrokerIdentity> getListOfConnectedBrokers(UUID customerId) throws CantGetCryptoBrokerListException {
        return null;
    }

    @Override
    public boolean startNegotiation(UUID customerId, UUID brokerId, Collection<ClauseInformation> clauses) throws CouldNotStartNegotiationException {
        return false;
    }

    @Override
    public boolean associateIdentity(UUID customerId) {
        return false;
    }

    @Override
    public Collection<CryptoCustomerIdentity> getListOfIdentities() throws CantGetCryptoCustomerIdentityListException {
        return null;
    }

    @Override
    public Collection<IndexInfoSummary> getCurrentIndexSummaryForCurrenciesOfInterest() throws CantGetCurrentIndexSummaryForCurrenciesOfInterestException {
        try {
            IndexInfoSummary indexInfoSummary;
            Collection<IndexInfoSummary> summaryList = new ArrayList<>();

            indexInfoSummary = new CryptoCustomerWalletModuleIndexInfoSummary(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR, 240.62, 235.87);
            summaryList.add(indexInfoSummary);
            indexInfoSummary = new CryptoCustomerWalletModuleIndexInfoSummary(FiatCurrency.VENEZUELAN_BOLIVAR, CryptoCurrency.BITCOIN, 245000, 240000);
            summaryList.add(indexInfoSummary);
            indexInfoSummary = new CryptoCustomerWalletModuleIndexInfoSummary(FiatCurrency.VENEZUELAN_BOLIVAR, FiatCurrency.US_DOLLAR, 840, 800);
            summaryList.add(indexInfoSummary);
            indexInfoSummary = new CryptoCustomerWalletModuleIndexInfoSummary(FiatCurrency.US_DOLLAR, FiatCurrency.EURO, 1.2, 1.1);
            summaryList.add(indexInfoSummary);

            return summaryList;

        } catch (Exception ex) {
            throw new CantGetCurrentIndexSummaryForCurrenciesOfInterestException(ex);
        }
    }
}
