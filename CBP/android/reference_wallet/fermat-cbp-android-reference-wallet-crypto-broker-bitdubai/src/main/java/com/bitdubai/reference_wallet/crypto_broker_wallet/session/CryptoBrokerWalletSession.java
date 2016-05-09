package com.bitdubai.reference_wallet.crypto_broker_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.List;

public class CryptoBrokerWalletSession extends AbstractFermatSession<InstalledWallet, CryptoBrokerWalletModuleManager, WalletResourcesProviderManager> {
    public static final String NEGOTIATION_DATA = "negotiation_data";
    public static final String CONTRACT_DATA = "contract_data";
    public static final String CONFIGURED_DATA = "configured_data";
    public static final String LOCATION_LIST = "list_of_new_location";
    public static final String EXCHANGE_RATES = "EXCHANGE_RATES";

    public CustomerBrokerNegotiationInformation getNegotiationData() {
        Object data = getData(NEGOTIATION_DATA);
        return (data != null) ? (CustomerBrokerNegotiationInformation) data : null;
    }

    public List<IndexInfoSummary> getActualExchangeRates() {
        Object data = getData(EXCHANGE_RATES);
        return (data != null) ? (List<IndexInfoSummary>) data : null;
    }

    public void setActualExchangeRates(List<IndexInfoSummary> exchangeRates) {
        setData(EXCHANGE_RATES, exchangeRates);
    }
}
