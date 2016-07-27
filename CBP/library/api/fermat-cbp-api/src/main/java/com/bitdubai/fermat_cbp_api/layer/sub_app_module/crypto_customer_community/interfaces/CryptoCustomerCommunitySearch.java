package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerSearchResult;

import java.io.Serializable;
import java.util.List;

/**
 * The interface <code>CryptoCustomerCommunitySearch</code>
 * provides the methods to search for a particular crypto customer
 */
public interface CryptoCustomerCommunitySearch extends Serializable {

    void addAlias(String alias);

    List<CryptoCustomerCommunityInformation> getResult(Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult;

    List<CryptoCustomerCommunityInformation> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult;

    List<CryptoCustomerCommunityInformation> getResultLocation(DeviceLocation deviceLocation, Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult;

    List<CryptoCustomerCommunityInformation> getResultDistance(double distance, Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult;

    List<CryptoCustomerCommunityInformation> getResultAlias(String alias, Integer max, Integer offSet) throws CantGetCryptoCustomerSearchResult;

    CryptoCustomerExposingData getResult(final String publicKey) throws CantGetCryptoCustomerSearchResult;
}
