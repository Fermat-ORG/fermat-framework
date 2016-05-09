package com.bitdubai.reference_wallet.crypto_customer_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.List;
import java.util.UUID;


public class CryptoCustomerWalletSession extends AbstractFermatSession<InstalledWallet, CryptoCustomerWalletModuleManager, WalletResourcesProviderManager> {

    public static final String CONTRACT_DATA = "CONTRACT_DATA";
    public static final String NEGOTIATION_DATA = "NEGOTIATION_DATA";
    public static final String CONFIGURED_DATA = "CONFIGURED_DATA";
    public static final String LOCATION_LIST = "LOCATION_LIST";
    public static final String BANK_ACCOUNT_LIST = "BANK_ACCOUNT_LIST";
    public static final String BROKER_ACTOR = "BROKER_ACTOR";
    public static final String CURRENCY_TO_BUY = "CURRENCY_TO_BUY";
    public static final String QUOTES = "QUOTES";
    public static final String EXCHANGE_RATES = "EXCHANGE_RATES";
    public static final String NEGOTIATION_ID = "NEGOTIATION_ID";


    public CryptoCustomerWalletSession() {
    }

    public ActorIdentity getSelectedBrokerIdentity() {
        Object data = getData(BROKER_ACTOR);
        return (data != null) ? (ActorIdentity) data : null;
    }

    public void setSelectedBrokerIdentity(ActorIdentity brokerIdentity) {
        setData(BROKER_ACTOR, brokerIdentity);
    }

    public Currency getCurrencyToBuy() {
        Object data = getData(CURRENCY_TO_BUY);
        return (data != null) ? (Currency) data : null;
    }

    public void setCurrencyToBuy(FermatEnum currencyToBuy) {
        setData(CURRENCY_TO_BUY, currencyToBuy);
    }

    public void setQuotes(List<MerchandiseExchangeRate> quotes) {
        setData(QUOTES, quotes);
    }

    @SuppressWarnings("unchecked")
    public List<MerchandiseExchangeRate> getQuotes() {
        Object data = getData(QUOTES);
        return (data != null) ? (List<MerchandiseExchangeRate>) data : null;
    }

    @SuppressWarnings("unchecked")
    public List<IndexInfoSummary> getActualExchangeRates() {
        Object data = getData(EXCHANGE_RATES);
        return (data != null) ? (List<IndexInfoSummary>) data : null;
    }

    public void setActualExchangeRates( List<IndexInfoSummary>  exchangeRates) {
        setData(EXCHANGE_RATES, exchangeRates);
    }

    public void setNegotiationId(UUID  negotiationId) {
        setData(NEGOTIATION_ID, negotiationId);
    }

    public UUID getNegotiationId() {
        Object data = getData(NEGOTIATION_ID);
        return (data != null) ? (UUID) data : null;
    }
}
