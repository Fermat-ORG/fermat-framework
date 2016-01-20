package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerSearchResult;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySearch</code>
 * provides the methods to search for a particular crypto broker
 */
public interface CryptoBrokerCommunitySearch {

    void addAlias(String alias);

    List<CryptoBrokerCommunityInformation> getResult() throws CantGetCryptoBrokerSearchResult;

}
