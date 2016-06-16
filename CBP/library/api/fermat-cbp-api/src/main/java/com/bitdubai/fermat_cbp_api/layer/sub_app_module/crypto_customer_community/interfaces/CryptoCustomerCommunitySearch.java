package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces;



import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerSearchResult;

import java.io.Serializable;
import java.util.List;

/**
 * The interface <code>CryptoCustomerCommunitySearch</code>
 * provides the methods to search for a particular crypto customer
 */
public interface CryptoCustomerCommunitySearch extends Serializable {

    void addAlias(String alias);

    List<CryptoCustomerCommunityInformation> getResult() throws CantGetCryptoCustomerSearchResult;

    List<CryptoCustomerCommunityInformation> getResultLocation(DeviceLocation deviceLocation) throws CantGetCryptoCustomerSearchResult;

    List<CryptoCustomerCommunityInformation> getResultDistance(double distance) throws CantGetCryptoCustomerSearchResult;

    List<CryptoCustomerCommunityInformation> getResultAlias(String alias) throws CantGetCryptoCustomerSearchResult;
}
