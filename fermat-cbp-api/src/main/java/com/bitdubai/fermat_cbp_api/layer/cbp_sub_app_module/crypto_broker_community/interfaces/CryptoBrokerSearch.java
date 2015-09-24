package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces;



import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerSearchResult;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoCustomerSearch</code>
 * provides the methods to search for a particular crypto broker
 */
public interface CryptoBrokerSearch {

    void setNameToSearch(String nameToSearch);

    List<CryptoBrokerInformation> getResult() throws CantGetCryptoBrokerSearchResult;
}
